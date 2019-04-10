import { Vue, VueConstructor } from '@app/provider';

const MaskLayer = new Vue({
    data: {
        show: false,
        showOne: false,
        callback: {
            hide: undefined,
            click: undefined
        },
        opacity: null
    },
    template: `<div v-bind:style="{ opacity }" v-bind:class="toggle" v-on:touchmove="preventTouch" v-on:click="onClick" data-comment="UK Masklayer" ></div>`,
    computed: {
        toggle: {
            get() {
                return this.show || this.showOne ? 'modal-backdrop show' : '';
            }
        }
    },
    methods: {
        onClick(evt: MouseEvent) {
            this.showOne = false;

            if (this.callback.click) {
                this.callback.click();
            }

            evt.preventDefault();
            evt.stopPropagation();
        },
        preventTouch(evt: TouchEvent) {
            evt.preventDefault();
            evt.stopPropagation();
        }
    },
    watch: {
        toggle(show: string) {
            if (!show) {
                this.opacity = null;
                if (this.callback.hide) {
                    this.callback.hide();
                }
            }
        }
    }
}), mask = {
    install(vue: VueConstructor<Vue>) {
        MaskLayer.$mount(document.querySelector('body>#masklayer'));

        vue.prototype.$mask = function (act: 'hide' | 'show' | 'showonce', opacity: number = 0.2) {
            if (act === "hide") {
                MaskLayer.show = false;
                MaskLayer.showOne = false;
            } else {
                MaskLayer.opacity = opacity;

                MaskLayer.show = act === "show";
                MaskLayer.showOne = act === "showonce";
            }

            return {
                on: function (click: () => void, hide: () => void) {
                    if (MaskLayer.toggle) {
                        MaskLayer.callback.hide = hide;
                        MaskLayer.callback.click = click;
                    }
                }
            };
        };
    }
};

export { mask };