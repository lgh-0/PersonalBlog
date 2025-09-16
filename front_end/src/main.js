import { createApp } from 'vue'
// import './style.css'
import App from './App.vue'
import 'mdui'
import '@mdui/icons/newspaper.js'
import '@mdui/icons/search.js'
import '@mdi/font/css/materialdesignicons.css'
import 'material-icons/iconfont/material-icons.css'
// Markdown and code highlight styles
import 'github-markdown-css/github-markdown-dark.css'
import 'highlight.js/styles/github-dark.css'

const app = createApp(App)
app.mount('#app')
