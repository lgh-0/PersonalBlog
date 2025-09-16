<!-- EXPLORER状态栏 -->
<template>
  <!-- 侧边栏主容器 -->
  <div class="sidebar">
    <!-- 包含文件夹部分 -->
    <div class="section">
      
      <!-- 顶部标题栏 -->
      <div class="section-header" @click="toggleSection(sections)">
        <span class="section-icon">
          <i class="mdui-icon material-icons">{{ sections.open ? 'expand_more' : 'chevron_right' }}</i>
        </span>
        <span class="section-title">{{ sections.name }}</span>
        <span class="section-actions">
          <i class="mdui-icon material-icons action-icon" @click.stop="collapseAll">layers</i>
        </span>
      </div>

      <!-- 文件夹树结构 -->
      <div v-if="sections.open" class="section-content">
        <FolderTree 
          v-for="folder in sections.items" 
          :key="folder.id" 
          :folder="folder" 
          :articles-by-folder="articlesByFolder"
          @toggle="toggleFolder"
          @open-article="openArticle"
        />
      </div>

    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import FolderTree from './FolderTree.vue'
import { fetchArticleSummaries } from '../api'

// 事件：向上触发打开文件（文章）事件，注意使用 kebab-case 以匹配父组件监听的 @open-file
const emit = defineEmits(['open-file'])

// 获取文件夹图标
const getFolderIcon = (folder) => {
  if (folder.type === 'folder') {
    return folder.open ? 'folder_open' : 'folder'
  }
  return 'description'
}

// 切换章节展开状态
const toggleSection = (section) => {
  section.open = !section.open
}

// 切换文件夹展开状态
const toggleFolder = (folder) => {
  folder.open = !folder.open
}

// 折叠所有文件夹
const collapseAll = () => {
  const collapseFolder = (folder) => {
    folder.open = false
    if (folder.items) {
      folder.items.forEach(collapseFolder)
    }
  }
  sections.value.items.forEach(collapseFolder)
}

// 打开文章
const openArticle = (article) => {
  emit('open-file', article)
}

// 文章索引：按 folder_id 归组，形如 { 'a0000': [ {id, title, folder_id}, ... ] }
const articlesByFolder = ref({})

onMounted(async () => {
  try {
    const list = await fetchArticleSummaries()
    const map = {}
    for (const a of list) {
      const fid = a.folder_id || a.folderId
      if (!fid) continue
      if (!map[fid]) map[fid] = []
      map[fid].push(a)
    }
    // 可选：每个文件夹下按标题排序
    Object.keys(map).forEach(k => map[k].sort((x, y) => x.title.localeCompare(y.title)))
    articlesByFolder.value = map
  } catch (e) {
    console.error('加载文章列表失败', e)
  }
})

// 定义侧边栏数据结构  最大是5级文件夹
const sections = ref(
  { id:'00000',
    name: 'EXPLORER',
    open: true,
    items: [
      { id:'a0000',
        name: '一、数学基础',
        type: 'folder',
        open: false,
        items: [
          { id:'aa000',name: '1.1 线性代数', type: 'folder', open: false, items: [] },
          { id:'ab000',name: '1.2 微积分', type: 'folder', open: false, items: [] },
          { id:'ac000',name: '1.3 概率论与数理统计', type: 'folder', open: false, items: [] },
          { id:'ad0000',name: '1.4 离散数学', type: 'folder', open: false, items: [] },
          { id:'ae0000',name: '1.5 计算理论', type: 'folder', open: false, items: [] },
          { id:'af000',name: '1.6 高级数学', type: 'folder', open: false, items: [] }
        ]
      },
      { id:'b0000',
        name: '二、核心系统',
        type: 'folder',
        open: false,
        items: [
          { id:'ba000',name: '2.1 计算机体系结构', type: 'folder', open: false, items: [] },
          { id:'bb000',name: '2.2 操作系统', type: 'folder', open: false, items: [
            { id:'bba00',name: '2.2.1 linux', type: 'folder', open: false, items: [] },
          ] 
        },
          { id:'bc000',name: '2.3 网络技术', type: 'folder', open: false, items: [] },
          { id:'bd000',name: '2.4 存储系统', type: 'folder', open: false, items: [] },
          { id:'be000',name: '2.5 云计算', type: 'folder', open: false, items: [] }
        ]
      },
      { id:'c0000',
        name: '三、软件开发',
        type: 'folder',
        open: false,
        items: [
          { id:'ca000',
            name: '3.1 编程语言',
            type: 'folder',
            open: false,
            items: [
              { id:'caa00',name: '3.1.1 C/C++', type: 'folder', open: false, items: [] },
              { id:'cab00',name: '3.1.2 Java', type: 'folder', open: false, items: [] },
              { id:'cac00',name: '3.1.3 go', type: 'folder', open: false, items: [] },
              { id:'cad00',name: '3.1.4 python', type: 'folder', open: false, items: [] },
              { id:'cae00',name: '3.1.5 函数式编程', type: 'folder', open: false, items: [] },
            ]
          },
          { // 入门 基础 进阶 进阶数学
            id:'cb000',
            name: '3.2 数据结构与算法',
            type: 'folder',
            open: false,
            items: [
              { id:'cba00',
                name: '3.2.1 入门篇',
                type: 'folder',
                open: false,
                items: []
              },
              { id:'cbb00',
                name: '3.2.2 基础篇',
                type: 'folder',
                open: false,
                items: []
              },
              { id:'cbc00',
                name: '3.2.3 进阶篇',
                type: 'folder',
                open: false,
                items: [ ]
              }, 
            ]
          },
          { id:'cc000',name: '3.3 软件工程', type: 'folder', open: false, items: [] },
          { id:'cd000',name: '3.4 DevOps', type: 'folder', open: false, items: [] },
          { id:'ce000',name: '3.5 软件测试', type: 'folder', open: false, items: [] },
          { id:'cf000',name: '3.6 编译原理', type: 'folder', open: false, items: [] }
        ]
      },
      { id:'d0000',
        name: '四、数据技术',
        type: 'folder',
        open: false,
        items: [
          { id:'da000',
            name: '4.1 数据库',
            type: 'folder',
            open: false,
            items: [
              { id:'daa00',name: '4.1.1 关系型数据库', type: 'folder', open: false, items: [] },
              { id:'dab00',name: '4.1.2 NoSQL数据库', type: 'folder', open: false, items: [] },
              { id:'dac00',name: '4.1.3 分布式数据库', type: 'folder', open: false, items: [] }
            ]
          },
          { id:'db000',name: '4.2 大数据', type: 'folder', open: false, items: [] }
        ]
      },
      { id:'e0000',
        name: '五、人工智能',
        type: 'folder',
        open: false,
        items: [
          { id:'ea000',name: '5.1 机器学习', type: 'folder', open: false, items: [] },
          { id:'eb000',name: '5.2 深度学习', type: 'folder', open: false, items: [] },
          { id:'ec000',name: '5.3 自然语言处理', type: 'folder', open: false, items: [] },
          { id:'ed000',name: '5.4 计算机视觉', type: 'folder', open: false, items: [] }
        ]
      },
      { id:'f0000',
        name: '六、现代应用',
        type: 'folder',
        open: false,
        items: [
          { id:'fa000',name: '6.1 Web技术', type: 'folder', open: false, items: [] },
          { id:'fb000',
            name: '6.2 前端',
            type: 'folder',
            open: false,
            items: [
              { id:'fba00',name: '6.2.1 前端3件套', type: 'folder', open: false, items: [] },
              { id:'fbb00',name: '6.2.2 vue', type: 'folder', open: false, items: [] },
              { id:'fbc00',name: '6.2.3 react', type: 'folder', open: false, items: [] }
            ]
          },
          { id:'fc000',
            name: '6.3 后端',
            type: 'folder',
            open: false,
            items: [
              { id:'fca00',name: '6.3.1 Spring', type: 'folder', open: false, items: [] },
              { id:'fcb00',name: '6.3.2 Node.js', type: 'folder', open: false, items: [] },
              { id:'fcc00',name: '6.3.3 工具', type: 'folder', open: false, items: [
                { id:'fcca0',name: '6.3.3.1 docker-redis', type: 'folder', open: false, items: [] },
                { id:'fccb0',name: '6.3.3.2 nginx', type: 'folder', open: false, items: [] },
                
              ] },
              { id:'fcd00',name: '6.3.4 go和微服务', type: 'folder', open: false, items: [] }
            ]
          },
          { id:'fd000',name: '6.4 移动开发', type: 'folder', open: false, items: [] }
        ]
      },
      { id:'g0000',
        name: '七、安全',
        type: 'folder',
        open: false,
        items: [
          { id:'ga000',name: '7.1 网络安全', type: 'folder', open: false, items: [] },
          { id:'gb000',name: '7.2 系统安全', type: 'folder', open: false, items: [] }
        ]
      },
      { id:'h0000',
        name: '八、专业应用',
        type: 'folder',
        open: false,
        items: [
          { id:'ha000',name: '8.1 图形学', type: 'folder', open: false, items: [] },
          { id:'hb000',
            name: '8.2 游戏开发',
            type: 'folder',
            open: false,
            items: [
              { id:'hba00',name: '8.2.1 游戏引擎', type: 'folder', open: false, items: [] }
            ]
          },
          { id:'hc000',name: '8.3 嵌入式', type: 'folder', open: false, items: [] }
        ]
      },
      { id:'i0000',
        name: '九、新兴技术',
        type: 'folder',
        open: false,
        items: [
          { id:'ia000',name: '9.1 区块链', type: 'folder', open: false, items: [] },
          
        ]
      },
      { id:'j0000',
        name: '十、过程博客',
        type: 'folder',
        open: false,
        items: [
        ]
      },
      { id:'k0000',
        name: '十一、工具',
        type: 'folder',
        open: false,
        items: [
          { id:'ka000',name: '11.1 git', type: 'folder', open: false, items: [] },
          { id:'kb000',name: '11.2 虚拟化工具', type: 'folder', open: false, items: [] },
          { id:'kc000',name: '11.3 Typst和LeTex', type: 'folder', open: false, items: [] },
          
        ]
      }
    ]
  }
)

</script>

<style scoped>
.sidebar {
  width: 300px;
  background-color: #252526;
  height: calc(100vh - 50px);
  overflow-y: auto;
  color: #cccccc;
  position: relative;
}

.sidebar::after {
  content: '';
  position: absolute;
  top: 0;
  right: 0;
  width: 1px;
  height: 100%;
  background-color: #1e1e1e;
  box-shadow: 1px 0 0 rgba(0, 0, 0, 0.5);
}

.section-header {
  display: flex;
  align-items: center;
  padding: 8px;
  font-size: 11px;
  text-transform: uppercase;
  font-weight: bold;
  cursor: pointer;
  user-select: none;
  color: #cccccc;
}

.section-icon {
  display: flex;
  align-items: center;
  margin-right: 4px;
  min-width: 16px;
}

.section-icon i {
  font-size: 16px;
}

.section-title {
  flex-grow: 1;
}

.section-actions {
  opacity: 0;
  transition: opacity 0.2s;
}

.section-header:hover .section-actions {
  opacity: 1;
}

.action-icon {
  font-size: 16px;
  cursor: pointer;
  padding: 2px;
  border-radius: 3px;
}

.action-icon:hover {
  background-color: #2a2d2e;
}

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
  /* Windows 文件夹颜色 */
}

.folder-contents {
  margin-left: 16px;
}

/* 自定义滚动条样式 */
.sidebar::-webkit-scrollbar {
  width: 8px;
}

.sidebar::-webkit-scrollbar-track {
  background: #1e1e1e;
}

.sidebar::-webkit-scrollbar-thumb {
  background: #424242;
  border-radius: 4px;
}

.sidebar::-webkit-scrollbar-thumb:hover {
  background: #4f4f4f;
}
</style>
