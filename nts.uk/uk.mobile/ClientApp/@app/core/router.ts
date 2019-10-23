import { routes } from '@app/core/routes';
import { VueRouter } from '@app/provider';
import { csrf } from '@app/utils';
// import { HomeComponent } from '../../views/home';
import { Page404Component } from '@app/components';

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes: [{
        path: '*',
        name: 'page404',
        component: Page404Component
    }, /* {
        path: '/',
        name: 'home',
        component: HomeComponent
    }, */
    ...routes
    ]
});

router.beforeEach((to, from, next) => {
    let token = csrf.getToken();

    // if login or documents page
    if (to.path.indexOf('ccg/007') >= 0 || to.path.indexOf('/documents/') === 0) {
        next();
    } else {
        if (token) {
            next();
        } else {
            next('/ccg/007/b');
        }
    }
});

export { router };