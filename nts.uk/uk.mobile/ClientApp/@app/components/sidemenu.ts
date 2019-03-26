import { Vue } from '@app/provider';
import { dom, browser } from '@app/utils';
import { component, Watch } from '@app/core/component';

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
};

@component({
    template: `<nav class="sidebar" v-bind:class="{ show, iphone}" v-if="visible && items && items.length">
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
            <li v-for="(m, i) in items" v-bind:class="{ 'list-unstyled-item': m.childs }">
                <template v-if="m.childs">
                    <a class="dropdown-toggle collapse" v-on:click="toggleDropdown(m)" v-bind:class="{ 'show': active === m }">
                        <span>{{(m.title) | i18n }}</span>
                    </a>
                    <ul class="collapse list-unstyled" v-bind:class="{ 'show': active == m }">
                        <li v-for="(t, j) in m.childs">
                            <router-link :to="t.url">
                                <span v-on:click="hideSideBar">{{t.title | i18n }}</span>
                            </router-link>
                        </li>
                    </ul>
                </template>
                <template v-else>
                    <router-link :to="m.url">
                        <span v-on:click="hideSideBar">{{m.title | i18n }}</span>
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
                return _SideMenu.items;
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
    active: any = {};

    constructor() {
        super();

        if (browser.width >= 992) {
            let shoow = localStorage.getItem('__sidebar__');
            SideMenu.show = shoow === undefined ? true : shoow === 'show';
        }
    }

    hideSideBar() {
        if (browser.width < 992) {
            SideMenu.show = false;
        }
    }

    toggleDropdown(item: any) {
        if (this.active === item) {
            this.active = {};
        } else {
            this.active = item;
        }
    }

    @Watch('show', { immediate: true })
    toggleMaskLayer(show: boolean) {
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
}

export { SideMenu };

SideMenu.items = [{
    url: '/',
    title: 'home'
},
{
    url: '',
    title: 'html',
    childs: [{
        url: '/documents/html',
        title: 'html'
    }, {
        url: '/documents/component',
        title: 'components'
    }, {
        url: '/documents/modal',
        title: 'modal'
    }
    ]
}, {
    url: '/access/login',
    title: 'login'
}];