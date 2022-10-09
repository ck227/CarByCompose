package com.ck.car2.data.home

import com.ck.car2.model.HotIcon

val homeIcon0 = HotIcon(
    id = 0,
    title = "蔬菜豆制品",
    url = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fi.zw3e.com%2Fzw_news_map%2F550%2F2016051%2F1462758254047351778.jpg&refer=http%3A%2F%2Fi.zw3e.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1667833006&t=a78e37d8b0805eac24c91a080b06671f"
)

val homeIcon1 = homeIcon0.copy(id = 1, title = "肉禽蛋")

val homeIcon2 = homeIcon0.copy(id = 2, title = "海鲜水产")
val homeIcon3 = homeIcon0.copy(id = 3, title = "水果鲜花")
val homeIcon4 = homeIcon0.copy(id = 4, title = "乳品烘焙")
val homeIcon5 = homeIcon0.copy(id = 5, title = "冷冻面点")
val homeIcon6 = homeIcon0.copy(id = 6, title = "粮油调味")
val homeIcon7 = homeIcon0.copy(id = 7, title = "酒水饮料")
val homeIcon8 = homeIcon0.copy(id = 8, title = "休闲零食")
val homeIcon9 = homeIcon0.copy(id = 9, title = "熟食预制菜")

val homeIcons =
    listOf(
        homeIcon0,
        homeIcon1,
        homeIcon2,
        homeIcon3,
        homeIcon4,
        homeIcon5,
        homeIcon6,
        homeIcon7,
        homeIcon8,
        homeIcon9
    )