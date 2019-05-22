import { obj, dom } from '@app/utils';
import { Vue, VueConstructor } from '@app/provider';

const vm = Vue.extend({
    data: () => ({
        show: false,
        callback: {
            hide: Function,
            click: Function
        },
        opacity: null
    }),
    template: `<div v-bind:style="{ opacity }" class="modal-backdrop show" v-on:touchmove="preventTouch" v-on:click="onClick"></div>`,
    methods: {
        onClick(evt: MouseEvent) {
            if (this.callback.click) {
                this.callback.click();
            }

            evt.preventDefault();
            evt.stopPropagation();
        },
        preventTouch(evt: TouchEvent) {
            evt.preventDefault();
            evt.stopPropagation();
            evt.stopImmediatePropagation();
        }
    },
    watch: {
        show(show: string) {
            if (!show) {
                this.opacity = null;

                if (this.callback.hide > 0) {
                    this.callback.hide();
                }

                this.$destroy(true);
            }
        }
    },
    destroyed() {
        let self = this;

        document.body.removeChild(self.$el);
    }
}), mask = {
    install(vue: VueConstructor<Vue>) {
        vue.prototype.$mask = function (act: 'hide' | 'show', opacity: number = 0.2) {
            let self = this;

            if (act === 'hide') {
                if (self.$$mask) {
                    self.$$mask.show = false;

                    delete self.$$mask;
                }
            } else if (act === 'show') {
                if (!self.$$mask) {
                    self.$$mask = new vm();
                    self.$$mask.$mount(dom.create('div'));

                    self.$$mask.show = true;
                    self.$$mask.opacity = opacity;

                    document.body.appendChild(self.$$mask.$el);
                }

                return {
                    on(click: () => void, hide: () => void) {
                        if (obj.isFunction(hide)) {
                            self.$$mask.callback.hide = hide;
                        }

                        if (obj.isFunction(click)) {
                            self.$$mask.callback.click = click;
                        }
                    }
                };
            }
        };
    }
};

export { mask };