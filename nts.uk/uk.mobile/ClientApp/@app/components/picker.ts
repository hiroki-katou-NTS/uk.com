import { Vue } from '@app/provider';
import { obj, dom } from '@app/utils';

import { InfinityPicker } from './infinity-picker';

export const MobilePicker = {
    components: {
        'ipkr': InfinityPicker
    },
    template: `<transition name="picker-fade" v-on:after-leave="destroyPicker">
        <div class="modal show" v-show="show" v-on:touchmove="preventScroll">
            <div class="modal-dialog modal-md modal-popup modal-dialog-centered" v-on:touchmove="preventScroll">
                <div class="modal-content">
                    <div class="modal-body infinity-picker android" v-bind:class="className">
                        <div class="ipkr-caption" v-if="title">
                            <div class="ipkr-caption-item">{{title}}</div>
                        </div>
                        <div class="ipkr-content">
                            <ipkr v-for="(cols, idx) in dataSources" v-bind:key="idx"
                                v-model="selects[idx]"
                                v-bind:data-sources="cols"
                                v-bind:option-text="options.text"
                                v-bind:option-value="options.value"
                                v-on:input="value => onInput(value, idx)" />
                        </div>
                        <div class="ipkr-navbar">
                            <a class="ipkr-navbar-btn text-grey-800" ref="close" v-on:click="close">{{'cancel' | i18n}}</a>
                            <template v-if="deleteAble" v-bind:key="'deleteAble'">
                                <a class="ipkr-navbar-btn text-danger" v-on:click="remove">{{'remove' | i18n}}</a>
                            </template>
                            <template v-else v-bind:key="'notDeleteAble'" />
                            <a class="ipkr-navbar-btn text-primary" v-on:click="finish">{{'accept' | i18n}}</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </transition>`,
    props: {
        show: {
            default: false
        },
        value: {
            default: () => ({})
        },
        dataSources: {
            default: () => ({})
        },
        title: {
            default: () => ''
        },
        options: {
            default: () => ({
                text: 'text',
                value: 'value',
                required: false,
                className: ''
            })
        }
    },
    data: () => ({
        selects: {}
    }),
    watch: {
        show: {
            deep: true,
            immediate: true,
            handler(show: boolean) {
                let self = this,
                    opts = self.options,
                    defaultData = self.value;

                if (!show) {
                    dom.removeClass(document.body, 'modal-open');
                } else {
                    dom.addClass(document.body, 'modal-open');

                    obj.objectForEach(self.dataSources, (key: string, items: any[]) => {
                        if (defaultData[key] !== undefined) {
                            self.selects[key] = defaultData[key];
                        } else {
                            self.selects[key] = items[0][opts.value];
                        }
                    });
                }
            }
        },
        dataSources: {
            deep: true,
            handler(dataSources: any[]) {
                let self = this,
                    opts = self.options;

                obj.objectForEach(dataSources, (key: string, items: any[]) => {
                    if (!obj.has(self.selects, key)) {
                        self.selects[key] = obj.has(self.value, key) ? self.value[key] : (items[0] && items[0][opts.value]);
                    }
                });
            }
        },
        selects: {
            deep: true,
            immediate: true,
            handler(value: { [key: string]: any }) {
                let self = this;

                self.$emit('select', self.toJS(value));
            }
        }
    },
    computed: {
        deleteAble() {
            return !this.options.required && !!obj.keys(obj.cloneObject(this.value)).length;
        },
        className() {
            return this.options.className;
        }
    },
    methods: {
        onInput(value: any, idx: string) {
            let self = this,
                selects = self.toJS(self.selects);

            selects[idx] = value;

            Vue.set(self, 'selects', selects);
        },
        close() {
            this.show = false;
            this.$emit('close');

            this.$nextTick(() => {
                this.selects = {};
            });
        },
        remove() {
            this.show = false;
            this.$emit('input', {});
            this.$emit('remove', {});
            this.$emit('close');

            this.$nextTick(() => {
                this.selects = {};
            });
        },
        finish() {
            this.show = false;
            this.$emit('input', obj.cloneObject(this.selects));
            this.$emit('finish', obj.cloneObject(this.selects));
            this.$emit('close');

            this.$nextTick(() => {
                this.selects = {};
            });
        },
        preventScroll(evt: TouchEvent) {
            evt.preventDefault();
            evt.stopPropagation();
            evt.stopImmediatePropagation();
        },
        destroyPicker() { }
    }
};