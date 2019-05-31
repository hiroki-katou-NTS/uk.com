import { obj, dom } from '@app/utils';
import { Vue, VueConstructor } from '@app/provider';

const vm = Vue.extend({
    data: () => ({
        show: false,
        callback: {
            hide: Function,
            click: Function
        },
        color: null,
        message: 'plz_wait',
        opacity: null
    }),
    template: `<div v-bind:style="{ opacity }" class="modal-backdrop show" v-on:touchmove="preventTouch" v-on:click="onClick">
        <template v-if="message">
            <div ref="spinner" class="spinner">
                <div></div>
                <div></div>
                <div></div>
                <div></div>
                <div></div>
                <div></div>
                <div></div>
                <div></div>
                <div></div>
                <div></div>
                <div></div>
                <div></div>
                <span ref="message">{{ ((message != true && message) || 'plz_wait') | i18n }}</span>
            </div>
        </template>
        <template v-else></template>
    </div>`,
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

                if (this.callback.hide) {
                    this.callback.hide();
                }

                this.$destroy(true);
                let modal = document.querySelectorAll('.modal.show, .modal-backdrop.show');

                if (!modal.length) {
                    dom.removeClass(document.body, 'modal-open');
                }
            } else {
                dom.addClass(document.body, 'modal-open');
            }
        }
    },
    mounted() {
        setTimeout(() => {
            let spinner: HTMLElement = this.$refs.spinner,
                message: HTMLElement = this.$refs.message;

            if (spinner && message) {
                message.style.marginLeft = -((message.scrollWidth - spinner.clientWidth) / 2) + 'px';
            }
        }, 5);
    },
    destroyed() {
        let self = this;

        document.body.removeChild(self.$el);
    }
}), mask = {
    install(vue: VueConstructor<Vue>) {
        vue.prototype.$mask = function (act: 'hide' | 'show', options: number | { color?: string; opacity?: number; message?: boolean | string; } = 0.2) {
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

                    if (typeof options === 'number') {
                        self.$$mask.opacity = options;
                    } else {
                        self.$$mask.color = options.color;
                        self.$$mask.opacity = options.opacity;
                        self.$$mask.message = options.message || false;
                    }

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