package com.github.hzqd.ikfr.bilibili.crawler.utils

import java.util.*

object AnimUtils {
    fun toDate(dateStr: String): Date {
        return Date()
    }

    fun toEpisode(eStr: String): Int = when {
        eStr.contains("连载中") -> -1
        eStr.contains("即将开播") -> 0
        else -> try {
            eStr.replace("已完结, 全", "").replace("话", "").toInt()
        } catch (e: Exception) {
            -1
        }
    }


    fun toPlayCount(pcStr: String): Long = when {
        pcStr.contains("万") -> (pcStr.replace("万", "").toDouble() * 100_00L).toLong()
        pcStr.contains("亿") -> (pcStr.replace("亿", "").toDouble() * 100_000_000L).toLong()
        pcStr == "-" -> 0L
        else -> pcStr.toLong()
    }
}