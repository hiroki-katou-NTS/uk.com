import { obj } from '@app/utils';
import { Vue, VueConstructor } from '@app/provider';

const vm = Vue.extend({
    data: () => ({
        show: false,
        callback: {
            hide: [],
            click: []
        },
        opacity: null
    }),
    template: `<div v-bind:style="{ opacity }" class="modal-backdrop show" v-on:touchmove="preventTouch" v-on:click="onClick"></div>`,
    methods: {
        onClick(evt: MouseEvent) {
            this.callback.click
                .forEach((click) => {
                    click();
                });

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

                while (this.callback.hide.length > 0) {
                    this.callback.hide.shift()();
                }
            }
        }
    }
}), mask = {
    install(vue: VueConstructor<Vue>) {
        let $mask: {
            show: boolean;
            opacity: number;
            readonly toggle: boolean;
            callback: {
                hide: Array<Function>;
                click: Array<Function>;
            };
            $el: HTMLElement;
            $once(act: 'remove', callback: () => void);
            $mount(to: HTMLElement);
        } | undefined;

        vue.prototype.$mask = function (act: 'hide' | 'show', opacity: number = 0.2) {
            if (act === 'hide') {
                if ($mask) {
                    $mask.show = false;

                    document.body.removeChild($mask.$el);

                    $mask = undefined;
                }
            } else {
                if (!$mask) {
                    let msk = document.createElement('div');
                    document.body.appendChild(msk);

                    {
                        $mask = new vm();
                        $mask.$mount(msk);

                        $mask.show = true;
                        $mask.opacity = opacity;

                        $mask.$once('remove', () => $mask = undefined);
                    }
                }

                return {
                    on (click: () => void, hide: () => void) {
                        if (obj.isFunction(hide)) {
                            $mask.callback.hide.push(hide);
                        }

                        if (obj.isFunction(click)) {
                            $mask.callback.click.push(click);
                        }
                    }
                };
            }
        };
    }
};

export { mask };