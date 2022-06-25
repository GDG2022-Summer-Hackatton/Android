package com.gdg.chicpick.result.view

import com.gdg.chicpick.R
import com.gdg.chicpick.result.model.ChickenType

fun ChickenType.getImageResource() = when (this) {
    ChickenType.F -> R.drawable.chicken_f
    ChickenType.FS -> R.drawable.chicken_fs
    ChickenType.FSO -> R.drawable.chicken_fso
    ChickenType.FSSO -> R.drawable.chicken_fsso
    ChickenType.R -> R.drawable.chicken_r
    ChickenType.RS -> R.drawable.chicken_rs
    ChickenType.RSO -> R.drawable.chicken_rso
    ChickenType.RSSO -> R.drawable.chicken_rsso
}

fun ChickenType.toKeyword() = when (this) {
    ChickenType.F -> "본뼈튀"
    ChickenType.FS -> "본순튀"
    ChickenType.FSO -> "양뼈튀"
    ChickenType.FSSO -> "양순튀"
    ChickenType.R -> "본뼈구"
    ChickenType.RS -> "본순구"
    ChickenType.RSO -> "양뼈구"
    ChickenType.RSSO -> "양순구"
}