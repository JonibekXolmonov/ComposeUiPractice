package com.example.composeuipractice.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.composeuipractice.R
import kotlin.random.Random

data class Location(
    val long: Float = 49.5774f,
    val lat: Float = 64.68685f
)

data class Stadium(
    val id: String = "just_id",
    val stadiumName: String = "Acme futbol maydoni",
    @DrawableRes val images: List<Int> = listOf(
        R.drawable.im1,
        R.drawable.im2,
        R.drawable.im1,
        R.drawable.im2
    ),
    val locationName: String = "Amir Temur shoh ko’chasi, 14-uy",
    val location: Location = Location(),
    val ownerNumber: String = "+998 99 777 66 55",
    val workingHours: List<WorkingHours> = mutableListOf(
        WorkingHours(),
        WorkingHours(),
        WorkingHours(),
        WorkingHours(),
        WorkingHours(),
        WorkingHours(),
        WorkingHours(),
    ),
    val pricePerHour: String = "50 000 so’m/soat",
    val rate: List<Rate> = listOf(
        Rate(15f, "5"),
        Rate(5f, "4"),
        Rate(8f, "3"),
        Rate(2f, "2"),
        Rate(1f, "1")
    )
)

data class Rate(
    val star: Float,
    val name: String
)

data class WorkingHours(
    val start: String = "08:00",
    val finish: String = "23:30",
    val day: DAYS = DAYS.MONDAY
)

enum class DAYS(name: String) {
    MONDAY("Dushanba"),
    TUESDAY("Seshanba"),
    WEDNESDAY("Chorshanba"),
    THURSDAY("Payshanba"),
    FRIDAY("Juma"),
    SATURDAY("Shanba"),
    SUNDAY("Yakshanba"),
}

data class Comment(
    @DrawableRes val avatar: Int = R.drawable.avatar_placeholder,
    val fullName: String = "Azizjon Akramov",
    val rate: Int = Random.nextInt(2, 6),
    val date: String = "17.04.2022",
    @StringRes val comment: Int
)

data class Feature(
    @DrawableRes val iconId: Int,
    val title: String,
)

fun getFeatures() = listOf(
    Feature(
        iconId = R.drawable.ic_calendar,
        title = "Book",
    ),
    Feature(
        iconId = R.drawable.ic_route,
        title = "Route",
    ),
    Feature(
        iconId = R.drawable.ic_add_fav,
        title = "Make favorite",
    ),
    Feature(
        iconId = R.drawable.ic_share,
        title = "Share",
    ),
)