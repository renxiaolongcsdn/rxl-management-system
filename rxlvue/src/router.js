import Vue from 'vue'
import Router from 'vue-router'
import layout from './layout/layout' /* 平台页面整体布局页面 header 侧边栏  主题内容*/

Vue.use(Router)
export default new Router({
    mode: 'history',
    base: process.env.BASE_URL,
    routes: [
        {
            path: '/',
            name: 'layout',
            component: layout,
            redirect: '/index',    /*路由跳转，当访问 / 会重定向到 /home 路由 */
            meta: {
                requireIsAuth: true
            },
            children: [
                {
                    path: 'index',
                    name: 'Index',
                    component: () => import('@/views/home/homePage'),
                    meta: {
                        requireIsAuth: true
                    }
                },
                {
                    path: 'user',
                    name: 'User',
                    component: () => import('@/views/User'),
                    meta: {
                        requireIsAuth: true
                    }
                },
                {
                    path: 'article',
                    name: 'Article',
                    component: () => import('@/views/article/index'),
                    meta: {
                        requireIsAuth: true
                    }
                },
                {
                    path: 'lable',
                    name: 'Lable',
                    component: () => import('@/views/lable/index'),
                    meta: {
                        requireIsAuth: true
                    }
                },
                {
                    path: 'addarticle',
                    name: 'AddArticle',
                    component: () => import('@/views/article/addArticle'),
                    meta: {
                        requireIsAuth: true
                    }
                },
                {
                    path: 'infoarticle',
                    name: 'InfoArticle',
                    component: () => import('@/views/article/infoArticle'),
                    meta: {
                        requireIsAuth: true
                    }
                },
                {
                    path: 'auditLogIndex',
                    name: 'AuditLogIndex',
                    component: () => import('@/views/auditLog/index'),
                    meta: {
                        requireIsAuth: true
                    }
                }
            ]
        },
        {
            path: '/login',
            name: 'login',
            component: () => import('@/views/login'),
        },
        {
            path: '/blog',
            name: 'Blog',
            component: () => import('@/views/blog/layoutblog'),
            redirect: '/blogIndex',
            children: [
                {
                    path: '/blogIndex',
                    name: 'BlogIndex',
                    component: () => import('@/views/blog/index'),

                },
                {
                    path: '/blogInfo',
                    name: 'BlogInfo',
                    component: () => import('@/views/blog/AddorUpdate'),

                },
            ]
        },
    ]
})
