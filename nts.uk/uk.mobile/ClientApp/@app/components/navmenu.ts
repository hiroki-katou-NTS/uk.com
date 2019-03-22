import { Vue } from '@app/provider';
import { dom, browser } from '@app/utils';
import { component, Watch } from '@app/core/component';

const _NavMenu = new Vue({
    data: {
        show: false,
        items: [],
        visible: true
    }
}), NavMenu = {
    get show() {
        return _NavMenu.show;
    },
    set show(value: boolean) {
        _NavMenu.show = value;
    },
    set items(items: Array<any>) {
        _NavMenu.items = items;
    },
    set visible(visible: boolean) {
        _NavMenu.visible = visible;
    }
};

@component({
    template: `<nav class="navbar navbar-expand-lg navbar-dark bg-primary fixed-top" v-if="visible && items && items.length">
        <a v-on:click="void(0)" class="navbar-brand">{{pgName |i18n}}</a>
        <button class="navbar-toggler dropdown-toggle" v-on:click="show = !show"></button>
        <div class="collapse navbar-collapse" v-bind:class="{show}">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item" v-for="(t, i) in items">
                    <router-link :to="t.url" class="nav-link">
                        <span v-on:click="show = false">{{t.title | i18n}}</span>
                    </router-link>
                </li>
            </ul>
            <ul class="navbar-nav">
                <language-bar />
            </ul>
        </div>
    </nav>
    <nav v-else></nav>`,
    computed: {
        show: {
            get() {
                return _NavMenu.show;
            },
            set(value: boolean) {
                _NavMenu.show = value;
            }
        },
        items: {
            get() {
                return _NavMenu.items;
            }
        },
        visible: {
            get() {
                return _NavMenu.visible;
            }
        }
    }
})
export class NavMenuBar extends Vue {
    active: any = {};

    @Watch('show', { immediate: true })
    toggleMaskLayer(show: boolean) {
        let self = this;

        if (!show) {
            self.$mask('hide');

            dom.removeClass(document.body, 'show-menu-top');
        } else {
            self.$mask('show')
                .on(() => NavMenu.show = false);

            dom.addClass(document.body, 'show-menu-top');
        }
    }
}

export { NavMenu };

window['nav'] = NavMenu;



_NavMenu.items.push({
    url: '/',
    title: 'home'
});

_NavMenu.items.push({
    url: '/access/login',
    title: 'login'
});