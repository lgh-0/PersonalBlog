<!-- 底部状态栏 -->
<template>
  <div class="status-bar">
    <div class="left-items">
      <span class="status-item">🌿 main</span>
      <span class="status-item">📡 Connected</span>
    </div>
    <div class="right-items">
      <span class="status-item">Ln 1, Col 1</span>
      <span class="status-item">Spaces: 2</span>
      <span class="status-item">UTF-8</span>
      <span class="status-item">🌐 React</span>
    </div>
  </div>
</template>

<style scoped>
.status-bar {
  height: 22px;
  background-color: #007acc;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 10px;
  font-size: 12px;
  grid-column: 1 / -1;
}

.left-items,
.right-items {
  display: flex;
  gap: 16px;
}

.status-item {
  display: flex;
  align-items: center;
  gap: 4px;
}
</style>
