<!-- 递归文件夹组件 -->
<template>
  <div class="folder" v-if="folder.type === 'folder'">
    <!-- 文件夹头部 -->
    <div class="folder-header" @click="toggleFolder(folder)">
      <span class="folder-icon">
        <i class="mdui-icon material-icons">{{ getFolderIcon(folder) }}</i>
      </span>
      {{ folder.name }}
    </div>

    <!-- 展开后：先显示子文件夹，再显示该文件夹下的文章列表 -->
    <div v-if="folder.open" class="folder-contents">
      <!-- 子文件夹 -->
      <FolderTree
        v-for="item in folder.items"
        :key="item.id"
        :folder="item"
        :articles-by-folder="articlesByFolder"
        @toggle="handleToggle"
        @open-article="forwardOpenArticle"
      />

      <!-- 文章列表 -->
      <div v-if="articles.length" class="articles">
        <div
          v-for="a in articles"
          :key="a.id"
          class="article-row"
          @click.stop="openArticle(a)"
          title="点击查看文章"
        >
          <span class="article-icon">
            <i class="mdui-icon material-icons">description</i>
          </span>
          <span class="article-title">{{ a.title }}</span>
        </div>
      </div>
    </div>
  </div>

  <div v-else class="file">
    <div class="file-header">
      <span class="file-icon">
        <i class="mdui-icon material-icons">insert_drive_file</i>
      </span>
      {{ folder.name }}
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'

// 定义组件属性
const props = defineProps({
  folder: { type: Object, required: true },
  articlesByFolder: { type: Object, default: () => ({}) }
})

// 定义事件
const emit = defineEmits(['toggle', 'open-article'])

// 获取文件夹图标
const getFolderIcon = (folder) => {
  return folder.open ? 'folder_open' : 'folder'
}

// 切换文件夹状态
const toggleFolder = (folder) => {
  emit('toggle', folder)
}

// 处理子文件夹的切换事件
const handleToggle = (folder) => {
  emit('toggle', folder)
}

// 该文件夹下的文章
const articles = computed(() => props.articlesByFolder[props.folder.id] || [])

// 点击文章
const openArticle = (article) => {
  emit('open-article', article)
}

// 透传 open-article 事件
const forwardOpenArticle = (article) => emit('open-article', article)
</script>

<style scoped>
.folder-header {
  display: flex;
  align-items: center;
  padding: 4px 8px;
  cursor: pointer;
  user-select: none;
  font-size: 13px;
  color: #cccccc;
}

.folder-header:hover {
  background-color: #2a2d2e;
}

.folder-icon {
  display: flex;
  align-items: center;
  margin-right: 4px;
  min-width: 16px;
}

.folder-icon i {
  font-size: 16px;
  color: #dcb67a;
}

.folder-contents {
  margin-left: 16px;
}

.articles {
  margin-top: 4px;
}

.article-row {
  display: flex;
  align-items: center;
  padding: 2px 8px;
  cursor: pointer;
  user-select: none;
  font-size: 13px;
  color: #d4d4d4;
  border-left: 2px solid transparent;
}

.article-row:hover {
  background-color: #2a2d2e;
  border-left-color: #646cff;
}

.article-icon {
  display: flex;
  align-items: center;
  margin-right: 6px;
  min-width: 16px;
}

.article-icon i {
  font-size: 16px;
  color: #6fb5ff;
}

.article-title {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.file-header {
  display: flex;
  align-items: center;
  padding: 4px 8px;
  cursor: pointer;
  user-select: none;
  font-size: 13px;
  color: #cccccc;
}

.file-icon {
  display: flex;
  align-items: center;
  margin-right: 4px;
  min-width: 16px;
}

.file-icon i {
  font-size: 16px;
  color: #dcb67a;
}
</style>
