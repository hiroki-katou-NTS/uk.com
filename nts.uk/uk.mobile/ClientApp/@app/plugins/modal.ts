import { Vue, VueConstructor } from '@app/provider';
import { obj, dom, browser } from '@app/utils';
import { IModalOptions } from 'declarations';

const modal = {
    install(vue: VueConstructor<Vue>) {
        // prevent error
        vue.mixin({
            methods: {
                $close() { }
            }
        });

        vue.prototype.$modal = function (name: string, params: any, options?: IModalOptions) {
            let self = this,
                $options = self.$options,
                components = $options.components,
                focused = document.querySelector(':focus') as HTMLElement;

            params = obj.toJS(params);

            options = options || {
                title: name,
                size: 'md',
                type: 'modal'
            };

            return {
                onClose(callback: Function) {
                    if (components) {
                        let component = components[name];

                        if (component) {
                            if (component.mixins) {
                                [].slice.call(component.mixins).forEach(mixin => {
                                    if (obj.has(mixin, 'methods')) {
                                        let methods = mixin.methods;

                                        if (methods && obj.has(methods, '$close') && obj.has(mixin, 'mounted')) {
                                            delete mixin.methods;
                                            delete mixin.mounted;
                                        }
                                    }

                                    if (obj.isEmpty(mixin)) {
                                        component.mixins.splice(component.mixins.indexOf(mixin), 1);
                                    }
                                });
                            }

                            (component.mixins || (component.mixins = [])).push({
                                methods: {
                                    $close: function (data?: any) {
                                        this.$emit('callback', data);

                                        this.$destroy(true);
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

                            let dlg = dom.create('div'),
                                vm = Vue.extend({
                                    components,
                                    data: () => ({ name, params }),
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
                                    methods: {
                                        callback(data) {
                                            if (callback) {
                                                callback(data);
                                            }

                                            this.$close();
                                        },
                                        $close() {
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

                                        if (focused) {
                                            focused.focus();
                                        }

                                        setTimeout(() => {
                                            document.body.removeChild(this.$el);

                                            if (document.querySelector('.modal') == null) {
                                                dom.removeClass(document.body, 'modal-open');
                                            }
                                        }, options.type === 'popup' ? 200 : 1000);
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
                                                    <component v-bind:is="name" v-bind:params="params" v-on:callback="callback" />
                                                </div>
                                            </div>
                                        </div>
                                    </div>`
                                });

                            document.body.appendChild(dlg);

                            new vm().$mount(dlg);
                        }
                    }
                }
            };
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