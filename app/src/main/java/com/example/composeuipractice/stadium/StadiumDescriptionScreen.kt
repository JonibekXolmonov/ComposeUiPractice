package com.example.composeuipractice.stadium

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeuipractice.R
import com.example.composeuipractice.data.*
import com.example.composeuipractice.ui.theme.*


@Composable
fun StadiumDescriptionScreen(
    modifier: Modifier = Modifier,
    stadium: Stadium,
    comments: List<Comment>
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        ScaffoldWithTopBar(stadium, comments = comments)
    }
}

@Composable
fun ScaffoldWithTopBar(
    stadium: Stadium,
    comments: List<Comment>,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stadium.stadiumName)
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
                },
                backgroundColor = Color.White,
                contentColor = APPBAR_CONTENT_COLOR,
                elevation = 10.dp
            )
        }, content = {
            Content(paddingValues = it, stadium = stadium, comments = comments)
        })
}

@Composable
fun Content(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    stadium: Stadium,
    comments: List<Comment>
) {

    Column(
        modifier = modifier
            .padding(paddingValues)
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {

        CustomSpacer(height = 8.dp)

        StadiumImages(stadium.images)

        CustomSpacer(height = 20.dp)

        Text(
            text = stadium.stadiumName,
            fontSize = 28.sp,
            fontWeight = FontWeight(400),
            color = NEUTRAL100,
            maxLines = 1,
            modifier = modifier.padding(start = 16.dp)
        )

        CustomSpacer(height = 8.dp)

        StadiumExtraData(iconId = R.drawable.ic_location, title = stadium.locationName)

        StadiumExtraData(iconId = R.drawable.ic_phone, title = stadium.ownerNumber)

        StadiumTimetable(stadium.workingHours)

        StadiumExtraData(iconId = R.drawable.ic_money, title = stadium.pricePerHour)

        CustomSpacer(height = 8.dp)

        FeatureButtons(features = getFeatures())

        CustomSpacer(height = 20.dp)

        CustomSpacer(height = 8.dp, color = NEUTRAL20)

        CustomSpacer(height = 16.dp)

        Text(
            text = "Baholar va sharhlar",
            fontSize = 16.sp,
            fontWeight = FontWeight(500),
            color = NEUTRAL100,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        CustomSpacer(height = 16.dp)

        RateLayout(rates = stadium.rate)

        CustomSpacer(height = 20.dp)

        Comments(comments)
    }
}

@Composable
fun StadiumImages(images: List<Int>, modifier: Modifier = Modifier) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxWidth(),

        ) {
        items(images.size) {
            StadiumImageItem(images[it], it)
        }
    }
}

@Composable
fun StadiumImageItem(imageResId: Int, index: Int, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = imageResId),
        contentDescription = "image",
        modifier = modifier
            .padding(
                start = if (index == 0) 16.dp else 0.dp,
                end = if (index == 3) 16.dp else 0.dp
            )
            .clip(RoundedCornerShape(4.dp))
            .size(width = 240.dp, height = 180.dp),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun StadiumExtraData(
    modifier: Modifier = Modifier,
    @DrawableRes iconId: Int,
    title: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = title,
            modifier = modifier
                .padding(start = 5.dp)
                .size(20.dp),
            tint = NEUTRAL80
        )

        Text(
            text = title, fontSize = 16.sp, fontWeight = FontWeight(400),
            color = NEUTRAL90,
            modifier = modifier
                .weight(1f)
                .padding(start = 18.dp)
        )
    }
}

@Composable
fun StadiumTimetable(
    workingHours: List<WorkingHours>,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_timer),
            contentDescription = "timer",
            modifier = modifier.padding(start = 5.dp),
            tint = NEUTRAL80
        )

        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = GREEN600)) {
                    append("Ochiq")
                }
                withStyle(style = SpanStyle(color = NEUTRAL90)) {
                    append(" · 00:00 da yopiladi")
                }
            },
            modifier = modifier
                .weight(1f)
                .padding(start = 18.dp)
        )
    }
}

@Composable
fun FeatureButtons(
    modifier: Modifier = Modifier,
    activeContentColor: Color = Color.White,
    inactiveContentColor: Color = BLUE600,
    strokeColor: Color = NEUTRAL80,
    strokeWidth: Dp = 1.dp,
    features: List<Feature>
) {

    var selectedFeatureIndex by remember {
        mutableStateOf(0)
    }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(features.size) {
            FeatureButton(
                feature = features[it],
                activeContentColor = activeContentColor,
                inactiveContentColor = inactiveContentColor,
                strokeColor = strokeColor,
                strokeWidth = strokeWidth,
                index = it,
                isSelected = selectedFeatureIndex == it
            ) {
                selectedFeatureIndex = it
            }
        }
    }
}

@Composable
fun FeatureButton(
    modifier: Modifier = Modifier,
    feature: Feature,
    isSelected: Boolean = false,
    activeContentColor: Color,
    inactiveContentColor: Color,
    strokeColor: Color,
    strokeWidth: Dp,
    index: Int,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(100.dp),
        border = if (!isSelected) BorderStroke(strokeWidth, strokeColor) else BorderStroke(
            0.dp,
            Color.Transparent
        ),
        colors = ButtonDefaults.buttonColors(
            contentColor = if (isSelected) activeContentColor else inactiveContentColor,
            backgroundColor = if (isSelected) inactiveContentColor else activeContentColor
        ),
        modifier = modifier.padding(
            start = if (index == 0) 16.dp else 0.dp,
            end = if (index == 3) 16.dp else 0.dp
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = modifier.padding(
                start = 19.dp,
                end = 24.dp,
                top = 4.dp,
                bottom = 4.dp
            )
        ) {
            Icon(
                painter = painterResource(id = feature.iconId), contentDescription = feature.title,
                tint = if (isSelected) activeContentColor else inactiveContentColor
            )
            Text(
                text = feature.title,
                color = if (isSelected) activeContentColor else inactiveContentColor,
                fontSize = 14.sp,
                fontWeight = FontWeight(500),
                modifier = modifier.padding(start = 10.dp)
            )
        }
    }
}

@Composable
fun RateLayout(
    modifier: Modifier = Modifier,
    rates: List<Rate>
) {

    val allVotesNumber = rates.sumOf { it.star.toInt() }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f)
                .fillMaxHeight()
        ) {
            val averageRate =
                rates.sumOf { it.star.toInt() * it.name.toInt() }
                    .toFloat() / allVotesNumber.toFloat()
            Text(
                text = averageRate.toString(),
                fontSize = 45.sp,
                fontWeight = FontWeight(400),
                color = NEUTRAL100,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp),
                textAlign = TextAlign.Center
            )

            CustomSpacer(height = 4.dp)

            Box(
                contentAlignment = Center,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp)
            ) {
                RatingBar(
                    rating = 4f,
                    spaceBetween = 8.dp,
                )
            }

            CustomSpacer(height = 5.67.dp)

            Text(
                text = allVotesNumber.toString().plus(" ta ovoz"), fontSize = 12.sp,
                fontWeight = FontWeight(400),
                color = NEUTRAL80
            )

        }

        Column(
            modifier = modifier
                .fillMaxWidth()
                .weight(2.5f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            rates.forEach { rate ->
                CustomLinearProgress(rate = rate, votes = allVotesNumber)
            }
        }
    }

}

@Composable
fun CustomLinearProgress(
    modifier: Modifier = Modifier,
    rate: Rate,
    votes: Int = 0,
    activeColor: Color = YELLOW600,
    inactiveColor: Color = YELLOW50
) {

    var animationPlayed by remember {
        mutableStateOf(false)
    }

    val rateIndicator = rate.star / votes.toFloat()

    val progress = animateFloatAsState(
        targetValue = if (animationPlayed) rateIndicator else 0f,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = rate.name,
            color = NEUTRAL80,
            fontSize = 12.sp,
            fontWeight = FontWeight(400)
        )

        Spacer(
            modifier = modifier
                .width(width = 8.dp)
                .background(Color.Red)
        )

        LinearProgressIndicator(
            modifier = modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            progress = progress.value,
            color = activeColor,
            backgroundColor = inactiveColor
        )
    }
}

@Composable
private fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Float,
    spaceBetween: Dp = 4.67.dp
) {

    val image = ImageBitmap.imageResource(id = R.drawable.star_empty_18)
    val imageFull = ImageBitmap.imageResource(id = R.drawable.star_fill)

    val totalCount = 5

    val height = LocalDensity.current.run { (image.height).toDp() }
    val width = LocalDensity.current.run { image.width.toDp() }
    val space = LocalDensity.current.run { spaceBetween.toPx() }
    val totalWidth = width * totalCount + spaceBetween * (totalCount - 1)


    Box(
        modifier
            .width(totalWidth)
            .height(height)
            .drawBehind {
                drawRating(rating, image, imageFull, space)
            })
}

private fun DrawScope.drawRating(
    rating: Float,
    image: ImageBitmap,
    imageFull: ImageBitmap,
    space: Float
) {

    val totalCount = 5

    val imageWidth = image.width.toFloat()
    val imageHeight = size.height

    val reminder = rating - rating.toInt()
    val ratingInt = (rating - reminder).toInt()

    for (i in 0 until totalCount) {

        val start = imageWidth * i + space * i

        drawImage(
            image = image,
            topLeft = Offset(start, 0f)
        )
    }

    drawWithLayer {
        for (i in 0 until totalCount) {
            val start = imageWidth * i + space * i
            // Destination
            drawImage(
                image = imageFull,
                topLeft = Offset(start, 0f)
            )
        }

        val end = imageWidth * totalCount + space * (totalCount - 1)
        val start = rating * imageWidth + ratingInt * space
        val size = end - start

        // Source
        drawRect(
            Color.Transparent,
            topLeft = Offset(start, 0f),
            size = Size(size, height = imageHeight),
            blendMode = BlendMode.SrcIn
        )
    }
}

private fun DrawScope.drawWithLayer(block: DrawScope.() -> Unit) {
    with(drawContext.canvas.nativeCanvas) {
        val checkPoint = saveLayer(null, null)
        block()
        restoreToCount(checkPoint)
    }
}

@Composable
fun Comments(
    comments: List<Comment>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        comments.forEach {
            CommentItem(it)
        }
    }
}

@Composable
fun CommentItem(
    comment: Comment,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { }
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(NEUTRAL15)
                .padding(all = 16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.avatar_placeholder),
                    contentDescription = "avatar",
                    contentScale = ContentScale.None,
                    modifier = modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(BLUE50)
                )

                Text(
                    text = comment.fullName,
                    fontSize = 14.sp,
                    fontWeight = FontWeight(500),
                    color = NEUTRAL100,
                    modifier = modifier.padding(horizontal = 16.dp)
                )
            }

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                RatingBar(rating = comment.rate.toFloat(), spaceBetween = 4.67.dp)

                Text(
                    text = comment.date,
                    fontSize = 14.sp,
                    fontWeight = FontWeight(400),
                    color = NEUTRAL80,
                    modifier = modifier.padding(start = 16.dp)
                )
            }

            Text(
                text = stringResource(id = comment.comment),
                fontSize = 14.sp,
                fontWeight = FontWeight(400),
                color = NEUTRAL80,
            )
        }
    }
}

@Composable
fun CustomSpacer(
    modifier: Modifier = Modifier,
    height: Dp,
    color: Color = Color.White
) {
    Spacer(
        modifier = modifier
            .height(height = height)
            .background(color = color)
            .fillMaxWidth()
    )
}
