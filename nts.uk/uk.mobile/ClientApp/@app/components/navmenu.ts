import { Vue } from '@app/provider';
import { dom, browser, $ } from '@app/utils';
import { LanguageBar } from '@app/plugins/i18n';
import { component, Watch } from '@app/core/component';

// tslint:disable-next-line: variable-name
const _NavMenu = Vue.observable({
    show: false,
    items: [],
    visible: true
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
    template: `<nav class="navbar navbar-expand-lg fixed-top" v-if="visible">
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
            let top = 0,
                container = document.body.querySelector('.container-fluid') as HTMLElement;

            if (container) {
                top = Number((container.style.marginTop || '').replace('px', ''));
                container.style.marginTop = '';
            }

            document.body.classList.remove('show-menu-top');

            setTimeout(() => {
                document.scrollingElement.scrollTop = Math.abs(top);
            }, 0);
        } else {
            self.$mask('show')
                .on(() => NavMenu.show = false);

            let top = document.scrollingElement.scrollTop,
                container = document.body.querySelector('.container-fluid') as HTMLElement;

            dom.addClass(document.body, 'show-menu-top');

            if (!container.style.marginTop) {
                container.style.marginTop = `-${top}px`;
            }
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