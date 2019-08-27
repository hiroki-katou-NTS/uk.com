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
        message: false,
        opacity: null,
        className: ''
    }),
    template: `<div v-bind:style="{ opacity }" class="modal-backdrop show" v-bind:class="className" v-on:touchstart.stop.prevent="onClick" v-on:click.stop.prevent="onClick">
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
            } else {
                let top = document.scrollingElement.scrollTop,
                    container = document.body.querySelector('.container-fluid') as HTMLElement;

                dom.addClass(document.body, 'modal-open');

                if (!container.style.marginTop) {
                    container.style.marginTop = `-${top}px`;
                }
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

        if (dom.modals.length <= 1) {
            let top = 0,
                container = document.body.querySelector('.container-fluid') as HTMLElement;

            if (container) {
                top = Number((container.style.marginTop || '').replace('px', ''));
                container.style.marginTop = '';
            }

            document.body.classList.remove('modal-open');

            document.scrollingElement.scrollTop = Math.abs(top);
        }
    }
}), mask = {
    install(vue: VueConstructor<Vue>) {
        vue.prototype.$mask = function (act: 'hide' | 'show', options: number | { color?: string; opacity?: number; message?: boolean | string; } = 0.2) {
            let self = this;

            if (act === 'hide') {
                if (self.$$mask) {
                    if (!!self.$$mask.show) {
                        self.$$mask.show = false;
                    } else {
                        self.$$mask.$destroy(true);
                    }

                    delete self.$$mask;
                }
            } else if (act === 'show') {
                if (!self.$$mask) {
                    self.$$mask = new vm();
                    self.$$mask.$mount(dom.create('div'));

                    if (dom.hasClass(self.$el, 'modal-body')) {
                        self.$$mask.className = 'modal-frontdrop';
                    }

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

Vue.use(mask);