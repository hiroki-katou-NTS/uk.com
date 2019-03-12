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

export { router };

console.log(routes);