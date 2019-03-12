import { Vue, VueConstructor } from '@app/provider';

const modal = {
    install(vue: VueConstructor<Vue>) {
        // prevent error
        vue.mixin({
            methods: {
                $close() { }
            }
        });

        vue.prototype.$modal = function (name: string, params?: any) {
            let self = this,
                $onClose: (data?: any) => void | undefined = undefined,
                components = self.$options.components,
                closeCallback = function (onClose: (data: any) => void) {
                    $onClose = onClose;
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
                            }
                        }
                    });

                    let dlg = document.createElement('div'),
                        vm = Vue.extend({
                            components,
                            data: () => ({ name, params }),
                            template: `<div class="modal show">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h4 class="modal-title">{{name | i18n}}</h4>
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
                                    this.$destroy(true);
                                    document.body.removeChild(this.$el);
                                }
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
    }
};

export { modal };