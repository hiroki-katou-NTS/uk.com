import { Vue } from '@app/provider';
import { dom, browser, $ } from '@app/utils';
import { LanguageBar } from '@app/plugins/i18n';
import { component, Watch } from '@app/core/component';
import { Ccgs03AComponent } from 'views/ccg/s03/a';
import EventBus from './sidemenu';

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
        <a v-on:click="" class="navbar-brand mr-n2">{{pgName |i18n}}</a>
        <div class="d-flex justify-content-end align-items-center">
            <div class="div-ccgs08">
                <img :class="isNewNotice ? 'left-style' : ''" :src="iconNotice" class="img-notice" @click="showCcg003()">
                <img v-if="isNewNotice" :src="redCircle" class="img-red-circle">
            </div>
            <button class="navbar-toggler dropdown-toggle" v-on:click="show = !show"></button>
        </div>
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
    public isNewNotice: boolean = false;
    public iconNotice: string = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACYAAAAsCAYAAAAJpsrIAAAFKUlEQVRYR7VZ908qTRS9qzRFnx3sLRGNQcUSif7/RBSNCfYSsYKCBRVR6ryc+byblY9l2X04vzx9OzP3zC3nFhWyuMrlsmhqalKSyaS4ubmhfD5PTU1N1NLSQj6fj1wul8J7rIhQrBziM6enp+Lj44N6enpIUf67SghBFxcXtLy8TO3t7Zbvt3ywWCyKUChEExMTVC6XJSCAw8/QXDwep/X1dcv3Wz54fn4uAMZms0lA+BkLoLAeHh5oZWUFv1uSYXioVCoJFtzc3KzuD4fDor+/XzUfmxd7AS6ZTNLo6Ch5PB55hu/5Bm8o13ADTGaz2dTLo9EofX5+0p8/f+BDqqZYYxDM/vby8iL3Dg4O0vj4uLyjUCgIu91uKNdwA0dWKpUSh4eHNDQ0RHa7XWqlWCzKfwGKgTEorXnf3t7o6+uLFhYWqLm5WZ4xMrEhMGjs9fWVTk5O5MtxKRycI5A1xM5fTXMAmcvlKJVK0draGvtiTdmGwHK5nAiHwz+iT+tPDJA1VUk/DBjfobVsNkuLi4uGcg03HB4ewifI4XBImdCWHgg9TmRwTCOzs7PwUfMa0zJ2KBQSiK5CoaD6078AQzDArPPz8wpHajV/q4qagV1dXYlMJkNtbW2qT/HrzWQMLflC83d3d7S0tCQfqqUg7Z1VgeElOBCJRATSDZOmFVBaH+RIBY2MjY1Rd3e3bj6tCoy5ZmNjQyAScSFTgxlN8V48CI8rlUqSLuAW8FWfz2cO2LeTi0gkQmB3LQVYBVaZtt7f38nv9+sGgO6HbDYr9vf3yev1WopEPdrAI6G1RCJBwWDQPLB0Oi1QvsDH/lVjWpAM7OrqCtWHOefHRYlEQjw9Pf3Ih1bMWE1zqEhisZgExnm4cp+uKmOxmABToyJt5OJSCcCCwSA5nc6qGHSBHR8fo3RWGb9R4BjY9fU1BQIBcrvd5oAhFYEMofZGLvYxkOzc3BzI2xywg4MD0draajovGj2COQ3+Ozk5SZ2dneaAQWPwL7N5sR5guBMki2LT6/WaA7a9vS08Ho9k60aC05ZBSOgzMzP1A8vn82Jzc1Pms98EhpwZCATqB/b4+CjJFenot4CB/W9vb1HR1gaGigL+gaoCjSzMx62Zkd+Y+c7ODypCBw8ug0yUWt8RK4GqaLXd0NbWlhgYGJA50krFWgsoA8PD0Uu4XC6amppSIF/bpPwAhg+oyQ8ODoiBWa3BjMpsDih07DBnZTWr/gfqLYfDIYvD3t7eH7OIRkZlZeGYTqcJ8qAIYIDvQZ4ExuXt5eWlTNzYyL1io0Fpi0et1vx+v2xQ2NdUU56dnQmELypWkN9vAaosgbhPhUmnp6ehFAlOyWQy4ujoSKqwr6/vVxzeKBh43oH82dHRAdIlZXd3VyA1gBp+23xGtAKAmLc5nU5SdnZ2BMpnEKnR0o4BzJq63uiGaTFKUMBZ6Bt5HlELHA9QtPMLo8dwFNZzhh+LXlaJx+MCnXG9GuCXwyeNFvcKZtIaFCTpyuhy7XftQBhdDjfCtcgUkaZXPteS/b8cVWszp61oNKrWatp+UXsWoEGeKJ2Gh4frnmBzzjalMQa2t7cnq1tEMg+Dqw3u7u/vkQepq6vLlJwfSbwek3KWwGwfzQTzXqXWvpOxrB5WV1fl1Xptmp5cUy+Bj8GRMUPFXANOioalkv+gSdRaKDRhSl56k51q4EwB014As2K24Xa7Ze0OLUFz+AsJRukAjaFJPZZoODCYBx378/Oz+icbsPbIyIhuv1gv0L+7301f5WpnEwAAAABJRU5ErkJggg==';
    public redCircle: string = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAwAAAANCAYAAACdKY9CAAAAQ0lEQVQoU2NkQANvZVT+IwsJP7nDiMyHc9AVohsE0wjWQEgxTDNIE+kaiDUdZgvjcNBA+2CFBReh0EKJaeRkQCgtAQBEFSrqz4IE/AAAAABJRU5ErkJggg==';

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
        const vm = this;
        dom.registerEventHandler(window, 'resize', resize);
        vm.$mask('show', { message: true });
        EventBus.$on('hideSideBar', vm.checkIsNewMsg);
    }

    public mounted() {
        const vm = this;
        vm.checkIsNewMsg();
    }

    private checkIsNewMsg() {
        const vm = this;

        vm.$auth
            .token
            .then((tk: string | null) => {
                if (!tk) {
                    return { data: false };
                }

                return vm.$http.post('com', servicePath.isNewNotice);
            })
            .then((res: any) => vm.isNewNotice = res.data)
            .then(() => vm.$mask('hide'))
            .catch(() => vm.$mask('hide'));
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

    public showCcg003() {
        const vm = this;

        vm.$modal(Ccgs03AComponent)
            .then((result: any) => {
                if (result === 'back') {
                    vm.checkIsNewMsg();
                }
            });
    }
}

export { NavMenu };

const servicePath = {
    isNewNotice: 'sys/portal/notice/is-new-notice'
};