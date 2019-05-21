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
    let token = csrf.getToken(),
        $modals = document.body.querySelectorAll('.modal.show');

    // fix show modal on switch view (#107642)
    if ($modals && $modals.length) {

        [].slice.call($modals).forEach((modal: HTMLElement) => {

            let $close = modal.querySelector('.modal-header .close, .modal-header .btn-close') as HTMLElement;

            if ($close) {
                $close.click();
            }
        });
    }

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