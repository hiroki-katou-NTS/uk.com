import { Vue } from '@app/provider';
import { Language } from '@app/plugins';
import { dom, browser } from '@app/utils';
import { component, Watch } from '@app/core/component';

// tslint:disable-next-line: variable-name
const _SideMenu = new Vue({
    data: {
        show: false,
        items: [],
        visible: true
    }
}), SideMenu = {
    get show() {
        return _SideMenu.show;
    },
    set show(value: boolean) {
        _SideMenu.show = value;
    },
    get items() {
        return _SideMenu.items;
    },
    set items(items: Array<any>) {
        _SideMenu.items = items;
    },
    set visible(visible: boolean) {
        _SideMenu.visible = visible;
    }
}, resize = () => {
    if (!browser.mobile) {
        SideMenu.show = false;
    }
};
@component({
    template: `<nav class="sidebar" v-bind:class="{ show, iphone}" v-if="visible" v-on:touchmove="preventScroll">
        <div class="sidebar-header">
            <h3>
                <router-link to="/">
                    <span v-on:click="show = false">{{'app_name' | i18n}}</span>
                </router-link>
            </h3>
            <button type="button" class="navbar-btn" v-on:click="show = !show">
                <span></span>
                <span></span>
                <span></span>
            </button>
        </div>
        <ul class="list-unstyled list-group components">
            <li><nts-search-box v-model="filter" class="mt-2 mr-2 ml-2" /></li>
            <li v-for="(m, i) in items" v-bind:class="{ 'list-unstyled-item': m.childs }" v-bind:key="i">
                <template v-if="m.hasc">
                    <a class="dropdown-toggle collapse" v-on:click="toggleDropdown(m)" v-bind:class="{ 'show': active === m || (!!filter && m.hasc) }">
                        <span><i class="fas fa-code mr-2"></i>{{(m.title) | i18n }}</span>
                    </a>
                    <transition name="collapse">
                        <ul class="list-unstyled" v-show="active == m || (!!filter && m.hasc)">
                            <li v-for="(t, j) in m.childs" v-bind:key="j">
                                <router-link :to="t.url">
                                    <span v-on:click="hideSideBar"><i class="far fa-file-alt mr-2"></i>{{t.title | i18n }}</span>
                                </router-link>
                            </li>
                        </ul>
                    </transition>
                </template>
                <template v-else>
                    <router-link :to="m.url">
                        <span v-on:click="hideSideBar"><i class="far fa-file-alt mr-2"></i>{{m.title | i18n }}</span>
                    </router-link>
                </template>
            </li>
        </ul>
    </nav>
    <nav v-else></nav>`,
    computed: {
        show: {
            get() {
                return _SideMenu.show;
            },
            set(value: boolean) {
                _SideMenu.show = value;
            }
        },
        items: {
            get() {
                let kwo = this.filter.toLowerCase().trim(),
                    i18n = Language.i18n,
                    items = SideMenu.items
                        .map((m) => ({
                            url: m.url,
                            title: m.title,
                            childs: i18n(m.title).toLowerCase().indexOf(kwo) > -1 ? (m.childs || []) : (m.childs || []).filter((c) => i18n(c.title).toLowerCase().indexOf(kwo) > -1),
                            hasc: !!(m.childs || []).length
                        }))
                        .filter((f) => {
                            return i18n(f.title).toLowerCase().indexOf(kwo) > -1 || !!f.childs.length;
                        });

                return items;
            }
        },
        iphone: {
            get() {
                return browser.ios ? 'iphone' : '';
            }
        },
        visible: {
            get() {
                return _SideMenu.visible;
            }
        }
    }
})
export class SideMenuBar extends Vue {
    public active: any = {};
    public filter: string = '';

    constructor() {
        super();

        if (browser.width >= 992) {
            let shoow = localStorage.getItem('__sidebar__');
            SideMenu.show = shoow === undefined ? true : shoow === 'show';
        }
    }

    public hideSideBar() {
        if (browser.width < 992) {
            SideMenu.show = false;
        }
    }

    public toggleDropdown(item: any) {
        if (this.active === item) {
            this.active = {};
        } else {
            this.active = item;
        }
    }

    public preventScroll(evt: TouchEvent) {
        // evt.preventDefault();
        evt.stopPropagation();
        evt.stopImmediatePropagation();
    }

    @Watch('show', { immediate: true })
    public toggleMaskLayer(show: boolean) {
        let self = this;

        if (!show) {
            self.$mask('hide');

            dom.removeClass(document.body, 'show-side-bar');
        } else {
            if (browser.width < 992) {
                self.$mask('show')
                    .on(() => SideMenu.show = false);
            }

            dom.addClass(document.body, 'show-side-bar');
        }

        localStorage.setItem('__sidebar__', show ? 'show' : '');
    }

    public created() {
        dom.registerEventHandler(window, 'resize', resize);
    }

    public destroyed() {
        dom.removeEventHandler(window, 'resize', resize);
    }
}

export { SideMenu };

SideMenu.items = [{
    url: '/',
    title: 'home'
}, {
    url: '/ccg/007/b',
    title: 'login'
}];