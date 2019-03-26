import { routes } from '@app/core/routes';
import { VueRouter } from '@app/provider';

import { Page404Component } from '@app/components';

routes.push({
    path: '*',
    name: 'page404',
    component: Page404Component
});

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes: routes
});

router.beforeEach((to, from, next) => {
    let token = "";

    // if login or documents page
    if (to.path === '/access/login' || to.path.indexOf('/documents/') === 0) {
        next();
    } else {
        if (token) {
            next();
        } else {
            next('/access/login');
        }
    }
});

export { router };