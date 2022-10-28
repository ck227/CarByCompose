
# 这是一个使用compose构建的安卓APP项目
https://user-images.githubusercontent.com/4733762/198687586-4fe3988f-9751-4de2-97db-6a63daa7f2d7.mov

尝试去按google推荐的方式去搭建项目，参考compose-samples，目前仅处理了首页部分界面，后续逐步完善中

## 知识点

分别列出来目前使用到的一些小的知识，以及未使用到，后续需要持续了解的点

### 自定义布局
自定义跟随手势滑动的布局

### 主题
目前完全使用自定义的主题，对每个最小composable使用独立的color，优点是适配深色模式非常方便，缺点是添加颜色比较费时，考虑是否有这个必要？

### 动画
暂时没有用到动画，只用到了跟随手势的渐变



## License
```
Copyright 2022 The Android Open Source Project

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
