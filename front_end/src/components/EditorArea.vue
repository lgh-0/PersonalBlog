<!-- 编辑区 -->
<template>
  <div class="editor-area">
    <div v-if="loading" class="placeholder">正在加载文章内容...</div>
    <div v-else-if="error" class="placeholder error">{{ error }}</div>
    <div v-else-if="!contentHtml" class="placeholder">从左侧选择一篇文章以查看内容</div>
    <div v-else class="markdown-body content" v-html="contentHtml"></div>
  </div>
</template>
<script setup>
import { ref, nextTick } from 'vue'
import MarkdownIt from 'markdown-it'
import hljs from 'highlight.js'
import { fetchArticleContent } from '../api'

const md = new MarkdownIt({
  html: true,
  linkify: true,
  typographer: true,
})

const contentHtml = ref('')
const loading = ref(false)
const error = ref('')

async function openFile(article) {
  if (!article || !article.id) return
  loading.value = true
  error.value = ''
  contentHtml.value = ''
  try {
    const data = await fetchArticleContent(article.id)
    const mdText = data?.content || ''
    contentHtml.value = md.render(mdText)
    await nextTick()
    document.querySelectorAll('.editor-area pre code').forEach((el) => {
      try { hljs.highlightElement(el) } catch (e) {}
    })
  } catch (e) {
    console.error(e)
    error.value = '加载文章失败，请稍后重试'
  } finally {
    loading.value = false
  }
}

defineExpose({ openFile })
</script>



<style scoped>
.editor-area {
  flex: 1;
  width: 100%;
  height: 100%;
  background-color: #1e1e1e;
  overflow-y: auto;
  overflow-x: hidden;
  min-height: 0; /* 作为 flex 子项，允许内部滚动 */
}

.content {
  box-sizing: border-box;
  padding: 16px 24px;
  max-width: 980px;
  margin: 0 auto;
}

.placeholder {
  color: #9aa0a6;
  padding: 24px;
}

.placeholder.error {
  color: #ff6b6b;
}

</style>
