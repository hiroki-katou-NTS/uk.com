import { Vue } from '@app/provider';
import { dom, browser } from '@app/utils';
import { LanguageBar } from '@app/plugins';
import { component, Watch } from '@app/core/component';

// tslint:disable-next-line: variable-name
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
}, resize = () => {
    if (window.innerWidth >= 992 && NavMenu.show) {
        NavMenu.show = false;
    }
};

@component({
    template: `<nav class="navbar navbar-expand-lg navbar-dark bg-primary fixed-top" v-if="visible && items && items.length">
        <a v-on:click="" class="navbar-brand">{{pgName |i18n}}</a>
        <button class="navbar-toggler dropdown-toggle" v-on:click="show = !show"></button>
        <transition name="collapse-long" v-on:before-enter="beforeEnter" v-on:after-leave="afterLeave">
            <div ref="nav" class="collapse navbar-collapse" v-show="show">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item" v-for="(t, i) in items">
                        <router-link :to="t.url" class="nav-link">
                            <span v-on:click="show = false">{{t.title | i18n}}</span>
                        </router-link>
                    </li>
                </ul>
                <ul class="navbar-nav">
                    <language-bar v-on:change="show = false" />
                </ul>
            </div>
        </transition>
    </nav>
    <nav v-else data-comment="Navigation bar"></nav>`,
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
    },
    components: {
        'language-bar': LanguageBar
    }
})
export class NavMenuBar extends Vue {
    public active: any = {};

    @Watch('show', { immediate: true })
    public toggleMaskLayer(show: boolean) {
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

    public created() {
        dom.registerEventHandler(window, 'resize', resize);
    }

    public destroyed() {
        dom.removeEventHandler(window, 'resize', resize);
    }

    public beforeEnter() {
        let nav = this.$refs.nav as HTMLElement;

        dom.addClass(nav, 'show');
    }

    public afterLeave() {
        let nav = this.$refs.nav as HTMLElement;

        dom.removeClass(nav, 'show');
    }
}

export { NavMenu };

NavMenu.items = [{
    url: '/',
    title: 'home'
}, {
    url: '/documents',
    title: 'documents'
}];