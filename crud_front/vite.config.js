import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  base: '/frontend/',  // important for Tomcat context path
  plugins: [react()]
})

