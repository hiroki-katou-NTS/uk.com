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
    public iconNotice: string = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACYAAAAsCAYAAAAJpsrIAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAQ5SURBVFhHxZnHTzM9EMZnQ+8gmkQRcKDcOHDi/7/BAVEEQhRRREf03vLmN8qsnGi9Lcn3PZLljXc8fjyeGXudoFiC5ADdgiCQm5sbOT09la+vLykUCtLR0SFzc3PS1tYWyuRBbmJgb29PXl9fZXBwMCSAusPDQ1laWpLu7m5ty4NCuc6M399fubi4UFJ/f3/y8/OjbdTT09OyublZlsyH3MSOjo5kbGxMyRiwVlNTk1qvublZf+dFIjGswQDULm5vb6WlpUVJuAR4pg1/Q8ZgetKSTfQxLIIVAMo3Njbk/f1dent7paenJxzIVWP+dn9/r7JYdmpqSttYaqyZhERivGYgZr+9vS3j4+NqKSzCINTImBoj5Vry6elJPj4+ZHFxUSdJH5PzIZXFHh8fZXd3V2eOUltWlwzPLhngkvz8/NTUsry8HLbFIdHHILa1tSUTExPhbwY3IkbGJWEFmCx5bWhoSNbX18N3cUgkdnBwoJaKGtwQNxDvjBzJF8s9Pz+X3/oRSQwlBhy4vb09tFStGBgY0FQDLFKjEGuxk5MT6e/vVwUWmbWAYOnq6tJI5Rn4rB1rsevrazU/v63ELVsSmByEWltbdTktoqMQScyiDiUW3rTVQoq+uAM1+c9NvlGIJGbbCYQApOw5L8za6CIPchoBvsl6RyMhWiefudMCPa4bUHMqiYOXGDOyrcM3qyyo1oF+c5koeIkROThpI2AWjFuJ2KXE8WtdxjgQDD54iZGh65G7fLDU4YOXWD0i0QdWIS6HAe/IdGqUxdDNpp7LYvWIRB8gRpLFXXzwEnt7e2uo8xPxHBB8iCT2/f2t6aLRIPJ9iCTGURgfaCRw/rjJh8SIQsvEd3d3em6yTbfeMJ24iY3pPoOQGC8sPTw8POjhMCmkawF6CQC+2gGkXCNULCXCbK7UVhoB080noB1/qlemAFOEjPHOzo6MjIxUmLWeYAx30p2dnXJ+fq7t5DXjoxajkTPS8fGxLp8tKQLVM6kHXHJ9fX067svLS8WXffhdub+/r3mFLyLSRSMIVYOhMQJWwmrz8/N6SaPESkyLLB/JdHh4WIVsWf8LKInSWBA8OztTCy4sLEiwtrZWxAntOG2C/wcYl+Ajh6rzWzI15tRRxWSszlLS9AGkEK4kgpWVlSI3fxBMAqRdv0iLtH2MHIEQlJyuyC5vjUmwpU5zJEIWZNlBIM8dR6Y7WCPFrQ3XnFghDsgTaXm+HYISQ+WWNAhg5liK+1W+0CFJiZob+tjaSNbcqdmkkmDLXUA4DSkX1scu8KiJagrP9ptMzl2F9UkD9Gkp/04FUz46OlpxyMMaVmzGyEKMKIv7GvIhMzEGIxGzO9ifDgbeU1hukuXMzEzYlhSR1cjk/C6wwurqqi4VCRqCEIDs1dWVRtbs7GxZOjtqIoZlLi8v9WBp1iNZT05O6qkhP0T+AVvo7eLA/2ZnAAAAAElFTkSuQmCC';
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