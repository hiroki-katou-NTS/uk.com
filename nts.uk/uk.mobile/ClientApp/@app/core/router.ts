import { routes } from '@app/core/routes';
import { Vue, VueRouter, Route } from '@app/provider';
import { obj, auth } from '@app/utils';
// import { HomeComponent } from '../../views/home';
import { Page404Component } from '@app/components';

const router = new VueRouter({
    mode: 'history',
    base: document.querySelector('head>base').getAttribute('href'),
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

router.beforeEach((to: Route, from: Route, next: (to?: string) => void) => {
    if (to.path.match(/^\/$/)) {
        next('/ccg/008/a');
    } else if (to.path.indexOf('ccg/007') >= 0 || to.path.match(/\/documents/)) {
        // if login or documents page
        next();
    } else {
        if (auth.valid) {
            next();
        } else {
            next('/ccg/007/b');
        }
    }
});

router.afterEach((to: Route, from: Route) => {
    let $modals = document.body.querySelectorAll('.modal.show'),
        $modalbs = document.body.querySelectorAll('.modal-backdrop.show');

    // fix show modal on switch view (#107642)
    if ($modals && $modals.length) {
        [].slice.call($modals).forEach((modal: HTMLElement) => {
            let vm = Vue.vmOf(modal);

            if (obj.has(vm, 'show')) {
                vm.show = false;
            } else if (vm.$vnode && vm.$vnode.context) {
                let ctx = vm.$vnode.context;

                if (obj.has(ctx, 'show')) {
                    ctx.show = false;
                }
            }
        });
    }

    if ($modalbs && $modalbs.length) {
        [].slice.call($modalbs).forEach((modal: HTMLElement) => {
            let vm = Vue.vmOf(modal);

            if (obj.has(vm, 'show')) {
                vm.show = false;
            } else if (vm.$vnode && vm.$vnode.context) {
                let ctx = vm.$vnode.context;

                if (obj.has(ctx, 'show')) {
                    ctx.show = false;
                }
            }
        });
    }

    // scroll to top
    document.scrollingElement.scrollTop = 0;
});

export { router };