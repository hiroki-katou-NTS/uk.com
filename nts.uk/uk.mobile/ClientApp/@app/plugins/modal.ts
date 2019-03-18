import { Vue, VueConstructor } from '@app/provider';
import { dom, browser } from '@app/utils';
import { IModalOptions } from 'declarations';

const modal = {
    install(vue: VueConstructor<Vue>) {
        // prevent error
        vue.mixin({
            methods: {
                $close() { }
            }
        });

        vue.prototype.$modal = function (name: string, params?: any, options?: IModalOptions) {
            let self = this,
                focused = document.querySelector(':focus') as HTMLElement,
                $onClose: (data?: any) => void | undefined = undefined,
                components = self.$options.components,
                closeCallback = function (onClose: (data: any) => void) {
                    $onClose = onClose;
                };

            options = options || {
                title: name,
                size: 'md',
                type: 'modal'
            };

            if (components) {
                let component = components[name];

                if (component) {
                    (component.mixins || (component.mixins = [])).push({
                        methods: {
                            $close: function (data?: any) {
                                let el: HTMLElement = this.$el,
                                    modal = el.closest('.modal.show');

                                if (modal) {
                                    let close = modal.querySelector('.modal-header>button.close');

                                    if (close) {
                                        close.dispatchEvent(new Event('click'));
                                    }
                                }

                                if ($onClose) {
                                    $onClose.apply(self, [data]);
                                }

                                if (focused) {
                                    focused.focus();
                                }

                                this.$destroy();
                            }
                        },
                        mounted() {
                            let el = this.$el as HTMLElement,
                                footer = el.querySelector('.modal-footer') as HTMLElement;

                            if (footer) {
                                let mcontent = el.closest('.modal-content');

                                if (mcontent) {
                                    if (!dom.hasClass(footer, 'top')) {
                                        mcontent.append(footer);
                                    } else {
                                        let body = mcontent.querySelector('.modal-body') as HTMLElement;

                                        if (body) {
                                            mcontent.insertBefore(footer, body);
                                        } else {
                                            mcontent.append(footer);
                                        }
                                    }
                                }
                            }
                        }
                    });

                    let dlg = document.createElement('div'),
                        vm = Vue.extend({
                            components,
                            data: () => ({
                                name,
                                params
                            }),
                            computed: {
                                _title: {
                                    get() {
                                        return options.title || name;
                                    }
                                },
                                _class: {
                                    get() {
                                        let classNames: Array<string> = [];

                                        if (options.type === 'modal') {
                                            classNames.push('modal-dialog-scrollable');
                                        } else {
                                            classNames.push('modal-popup modal-dialog-centered');
                                        }

                                        if (!options.animate) {
                                            if (browser.mobile && !browser.landscapse) {
                                                classNames.push('slideInRight');
                                            } else {
                                                classNames.push('slideInDown');
                                            }
                                        } else {
                                            classNames.push(options.animate.show);
                                        }

                                        return classNames.join(' ');
                                    }
                                }
                            },
                            template: `<div class="modal fade show">
                                    <div ref="dialog" class="modal-dialog animated" v-bind:class="_class">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h4 class="modal-title">
                                                    <span>{{_title | i18n}}</span>
                                                </h4>
                                                <button type="button" v-on:click="$close()" class="close">&times;</button>
                                            </div>
                                            <div class="modal-body">
                                                <component v-bind:is="name" v-bind:params="params" />
                                            </div>
                                        </div>
                                    </div>
                                </div>`,
                            methods: {
                                $close: function () {
                                    let dialog: HTMLElement = this.$refs.dialog;

                                    if (dialog) {
                                        dom.removeClass(dialog, 'slideInDown');
                                        dom.removeClass(dialog, 'slideInRight');

                                        if (options.animate) {
                                            dom.removeClass(dialog, options.animate.show);
                                        }

                                        if (!options.animate) {
                                            if (browser.landscapse) {
                                                dom.addClass(dialog, 'slideOutUp');
                                            } else {
                                                dom.addClass(dialog, 'slideOutRight');
                                            }
                                        } else {
                                            dom.addClass(dialog, options.animate.hide);
                                        }
                                    }
                                    // destroy modal app
                                    this.$destroy(true);
                                }
                            },
                            mounted() {
                                dom.addClass(document.body, 'modal-open');
                            },
                            beforeMount() {
                                let inputs = document.querySelectorAll('a, input, select, button, textarea');

                                [].slice.call(inputs).forEach((element: HTMLElement) => {
                                    if (!element.getAttribute('data-tabindex')) {
                                        let tabindex = element.getAttribute('tabindex');

                                        element.setAttribute('tabindex', '-1');

                                        if (tabindex) {
                                            element.setAttribute('data-tabindex', tabindex);
                                        }
                                    }
                                });
                            },
                            destroyed() {
                                let inputs = document.querySelectorAll('a, input, select, button, textarea');

                                [].slice.call(inputs).forEach((element: HTMLElement) => {
                                    let tabindex = element.getAttribute('data-tabindex');

                                    element.removeAttribute('data-tabindex');

                                    if (!tabindex) {
                                        element.removeAttribute('tabindex');
                                    } else {
                                        element.setAttribute('tabindex', tabindex);
                                    }
                                });

                                setTimeout(() => {
                                    document.body.removeChild(this.$el);

                                    if (document.querySelector('.modal') == null) {
                                        dom.removeClass(document.body, 'modal-open');
                                    }
                                }, options.type === 'popup' ? 200 : 1000);
                            }
                        });

                    document.body.appendChild(dlg);

                    new vm().$mount(dlg);

                    return {
                        onClose: closeCallback
                    };
                }
            } else {
                console.error('Cannot find any component on this context!');
            }
        };

        // hack view height for modal
        window.addEventListener('resize', () => {
            // We execute the same script as before
            let vh = window.innerHeight * 0.01;
            document.documentElement.style.setProperty('--vh', `${vh}px`);
        });

        window.dispatchEvent(new Event('resize'));
    }
};

export { modal };