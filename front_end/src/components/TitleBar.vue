<!-- 顶部状态栏 -->
<template>
  <div class="title-bar">
    <div class="window-controls">
      <!-- 这个 div 同时具有 control 和 red 类 -->
      <div class="control red"></div>
      <div class="control yellow"></div>
      <div class="control green"></div>
    </div>
    <!-- 菜单栏 -->
    <div class="menu-items">
      <span>File</span>
      <span>Edit</span>
      <span>View</span>
      <span>Go</span>
      <span>Run</span>
      <span>Terminal</span>
      <span>Help</span>
      <span id="lgh">Lgh-blog</span>
    </div>
  </div>
</template>

<style scoped>
.title-bar {
  height: 28px;
  background-color: #3c3c3c;
  display: flex;
  align-items: center;
  padding: 0 10px;
  -webkit-app-region: drag;
  user-select: none;
  /* font-family: 'Cascadia Code', Consolas, 'Courier New', monospace;  */
}

#lgh {
  font-weight: bold;
  color: #646cff;
  position: absolute;
  right: 0; /* 将它固定在右侧 */
  margin-right: 1rem; /* 添加右侧间距 */
}
.window-controls {
  display: flex;
  gap: 8px;
  margin-right: 20px;
}

.control {
  width: 12px;
  height: 12px;
  border-radius: 50%;
  -webkit-app-region: no-drag;
}

.red { background-color: #ff5f57; }
.yellow { background-color: #febc2e; }
.green { background-color: #28c840; }

.menu-items {
  display: flex;
  gap: 20px;
  color: #cccccc;
  font-size: 13px;
  -webkit-app-region: no-drag;
}
/* 后代选择器使用空格分隔不同的选择器部分。 */
.menu-items span {
  cursor: pointer;
}

.menu-items span:hover {
  color: #ffffff;
}
</style>
