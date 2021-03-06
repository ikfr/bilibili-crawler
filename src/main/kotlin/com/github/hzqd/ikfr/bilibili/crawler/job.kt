package com.github.hzqd.ikfr.bilibili.crawler

import com.github.hzqd.ikfr.bilibili.crawler.utils.AnimUtils
import com.github.hzqd.ikfr.bilibili.crawler.utils.DocUtils
import com.github.hzqd.ikfr.bilibili.crawler.type.Anim
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.io.File
import java.util.*

fun main() = runBlocking {
    var id = 0
    val top50List: MutableList<Anim> = LinkedList()

    val job1 = async {

        while (id < 500) {
            val doc = DocUtils("https://bangumi.bilibili.com/anime").getDoc(id++)

            if (doc.html().isEmpty()) continue
            else if (doc.select(".media-info-title-t").text() == "") continue

            val anim = Anim(
                title = doc.select(".media-info-title-t").text(),
                playCount = AnimUtils.toPlayCount(doc.select(".media-info-count em").first().text()),
                episode = AnimUtils.toEpisode(doc.select(".media-info-time span")[1].text()),
                cover = doc.select(".bangumi-preview img").attr("src"),
                fans = AnimUtils.toPlayCount(doc.select(".media-info-count-item-fans em").text()),
                date = AnimUtils.toDate(doc.select(".media-info-time span")[0].text())
            )
            println("$id: ${anim.title}")

            synchronized(top50List) {
                top50List.add(anim)
                if (top50List.size == 51) {
                    top50List.sortByDescending { it.playCount }
                    top50List.removeAt(50)
                }
            }
        }

    }

    val job2 = async {
        println("Created")
        val file = File("C:\\Users\\moeKiwiSAMA\\Desktop\\bilibili.html")
        if (!file.exists()) file.createNewFile()
        while (id < 7000) {
            if (top50List.size >= 50) {
                var outStr = ""
                synchronized(top50List) {
                    top50List.forEach {
                        outStr += it.toString()
                    }
                }
                file.outputStream().write(
                    """
                    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css"/>
                    <table class="table table-bordered table-striped">
                    <thead>
                    <tr><th>标题</th><th>播放量</th><th>追番</th><th>封面地址</th><th>集数</th></tr>
                    </thead>
                    <tbody>
                    $outStr
                    </tbody>
                    </table>
                    """.toByteArray()
                )
            }
            println("---")
            delay(5000)
        }
    }

    job1.join()
    job2.join()

    top50List.forEach {
        println(it.toString())
    }
}