import axios from 'axios'

const BASE_URL = import.meta.env.VITE_API_BASE || 'http://localhost:8081'

const http = axios.create({
  baseURL: BASE_URL,
  timeout: 10000,
})

export async function fetchArticleSummaries() {
  const { data } = await http.get('/api/articles/summaries')
  return data || []
}

export async function fetchArticleContent(id) {
  const { data } = await http.get(`/api/articles/${id}`)
  return data
}
