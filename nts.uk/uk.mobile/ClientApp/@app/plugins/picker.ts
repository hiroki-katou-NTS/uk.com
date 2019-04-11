import { Vue, VueConstructor } from '@app/provider';
import { obj, dom, browser } from '@app/utils';
import { Language } from '@app/plugins';

//import { PickerComponent } from '@app/components/picker/index';

const Picker = new Vue({
    filters: {
        i18n: Language.i18n
    },
    template: `<transition name="picker-fade">
        <div class="picker-container" v-show="show" v-on:touchmove="preventScroll">
            <div class="pk-btn-groups">
                <button class="btn btn-link" ref="close" v-on:click="close">{{'cancel' | i18n}}</button>
                <div>
                    <button class="btn btn-link mr-4" v-if="deleteAble" v-on:click="remove">{{'delete' | i18n}}</button>
                    <button class="btn btn-link" v-on:click="finish">{{'accept' | i18n}}</button>
                </div>
            </div>
            <div v-if="options.title" class="picker_title p-1">
                {{ (options.title || '') | i18n }}:
            </div>
            <div v-else data-comment="Title of picker"></div>
            <div class="picker_mask" v-on:touchmove="preventScroll">
                <div class="picker_roll" v-on:touchmove="preventScroll">
                <div class="picker-column" v-for="(cols, idx) in dataSources" v-bind:key="idx">
                    <div
                    class="gear"
                    v-bind:id="idx"
                    v-bind:ref="idx"
                    v-on:touchstart="gearTouchStart"
                    v-on:touchmove="gearTouchMove"
                    v-on:touchend="gearTouchEnd"
                    >
                    <div
                        class="tooth"
                        v-for="(item, index) in cols"
                        v-bind:key="index"
                    >{{item[textField] | i18n}}</div>
                    </div>
                    <div class="area_grid"></div>
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
        options: {
            default: () => ({
                text: 'text',
                value: 'value',
                required: false
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
                    refs = self.$refs,
                    opts = self.options,
                    defaultData = self.value;

                if (!show) {
                    if (self.$mask) {
                        self.$mask('hide');
                    }
                    dom.removeClass(document.body, 'modal-open');
                } else {
                    if (self.$mask) {
                        self.$mask('show', 0.01);
                    }

                    refs.close.focus();

                    dom.addClass(document.body, 'modal-open');

                    self.$nextTick(() => {
                        obj.objectForEach(self.dataSources, (key: string, items: any[]) => {
                            let position = 0,
                                columns = refs[key][0] as HTMLElement;

                            if (!defaultData[key]) {
                                self.selects[key] = items[0][opts.value];
                            }

                            for (var i = 0, len = items.length; i < len; i++) {
                                if (defaultData[key] && items[i][opts.value] == defaultData[key]) {
                                    position = -(i * 2);
                                    break;
                                }
                            }

                            columns.style.transform = columns.style['-webkit-transform'] = 'translate3d(0,' + position + 'em,0)';
                            columns.setAttribute('top', position + 'em');
                        });
                    })
                }
            }
        },
        dataSources: {
            deep: true,
            immediate: true,
            handler() {
                let self = this;

                obj.objectForEach(self.dataSources, (key: string, items: any[]) => {
                    self.selects[key] = self.value[key] || items[0];
                });
            }
        }
    },
    computed: {
        deleteAble() {
            return !this.options.required && !!obj.keys(obj.cloneObject(this.value)).length;
        },
        textField() {
            return this.options.text;
        }
    },
    methods: {
        close() {
            this.show = false;
            this.$emit('close');
        },
        remove() {
            this.show = false;
            this.$emit('remove', {});
        },
        finish() {
            this.show = false;
            this.$emit('finish', obj.cloneObject(this.selects));
        },
        gearTouchStart(evt: TouchEvent) {
            evt.preventDefault();
            evt.stopPropagation();

            var target = evt.target as HTMLElement;

            while (true) {
                if (!target.classList.contains('gear')) {
                    target = target.parentElement;
                } else {
                    break;
                }
            }

            let top = target.getAttribute('top');

            clearInterval(target['int_' + target.id]);
            target['o_t_' + target.id] = (new Date()).getTime();
            target['old_' + target.id] = evt.targetTouches[0].screenY;

            if (!top) {
                target['o_d_' + target.id] = 0;
            } else {
                target['o_d_' + target.id] = parseFloat(top.replace(/em/g, ''));
            }

            target.style.webkitTransitionDuration = target.style.transitionDuration = '0ms';
        },
        gearTouchMove(evt: TouchEvent) {
            evt.preventDefault();
            evt.stopPropagation();

            var target = evt.target as HTMLElement;

            while (true) {
                if (!target.classList.contains('gear')) {
                    target = target.parentElement;
                } else {
                    break
                }
            }

            target['new_' + target.id] = evt.targetTouches[0].screenY;
            target['n_t_' + target.id] = (new Date()).getTime();

            var f = (target['new_' + target.id] - target['old_' + target.id]) * 30 / window.innerHeight;

            target['pos_' + target.id] = target['o_d_' + target.id] + f;
            target.style['-webkit-transform'] = 'translate3d(0,' + target['pos_' + target.id] + 'em,0)';
            target.setAttribute('top', target['pos_' + target.id] + 'em');

            if (evt.targetTouches[0].screenY < 1) {
                this.gearTouchEnd(evt);
            }
        },
        gearTouchEnd(evt: TouchEvent) {
            evt.preventDefault();
            evt.stopPropagation();

            var target = evt.target as HTMLElement;

            while (true) {
                let cls: any = target.classList;

                if (cls.contains('gear')) {
                    break;
                } else {
                    target = target.parentElement;
                }
            }

            var flag = (target['new_' + target.id] - target['old_' + target.id]) / (target['n_t_' + target.id] - target['o_t_' + target.id]);

            if (Math.abs(flag) <= 0.2) {
                target['spd_' + target.id] = (flag < 0 ? -0.08 : 0.08);
            } else {
                if (Math.abs(flag) <= 0.5) {
                    target['spd_' + target.id] = (flag < 0 ? -0.16 : 0.16);
                } else {
                    target['spd_' + target.id] = flag / 2;
                }
            }

            if (!target['pos_' + target.id]) {
                target['pos_' + target.id] = 0;
            }

            this.rollGear(target);
        },
        rollGear(target: HTMLElement) {
            var d = 0,
                self = this,
                stopGear = false;

            function setDuration() {
                stopGear = true;
                target.style.webkitTransitionDuration = target.style.transitionDuration = '100ms';
            }

            clearInterval(target['int_' + target.id]);

            target['int_' + target.id] = setInterval(function () {
                var position = target['pos_' + target.id],
                    speed = target['spd_' + target.id] * Math.exp(-0.03 * d),
                    minTop = -(self.dataSources[target.id].length - 1) * 2;

                position += speed;

                if (Math.abs(speed) <= 0.1) {
                    position = Math.round(position / 2) * 2;
                    setDuration();
                }

                if (position > 0) {
                    position = 0;
                    setDuration();
                }

                if (position < minTop) {
                    position = minTop;
                    setDuration();
                }

                if (stopGear) {
                    clearInterval(target['int_' + target.id]);

                    self.setGear(target.id, Math.abs(position) / 2);
                }

                target['pos_' + target.id] = position;
                target.style['-webkit-transform'] = 'translate3d(0,' + position + 'em,0)';
                target.setAttribute('top', position + 'em');

                d++;
            }, 30);
        },
        setGear(key: string, index: number) {
            var self = this,
                opts = self.options,
                items = self.dataSources[key];

            self.selects[key] = items[Math.round(index)][opts.value];
        },
        preventScroll(evt: TouchEvent) {
            evt.preventDefault();
            evt.stopPropagation();
            evt.stopImmediatePropagation();
        }
    }
}), picker = {
    install(vue: VueConstructor<Vue>) {
        Picker.$mount(document.querySelector('body>#uk_picker'));

        vue.prototype.$picker = function (value: string | number | Date | { [key: string]: string | number | Date },
            dataSources: Array<string | number | Date> | { [key: string]: Array<string | number | Date> },
            options: {
                text: string;
                value: string;
                required?: boolean;
            } = { text: 'text', value: 'value', required: false }) {
            const isPrimitive = !obj.isObject(value) && obj.isArray(dataSources);

            value = isPrimitive ? { column: value } : obj.cloneObject(value);
            dataSources = isPrimitive ? { column: dataSources } : obj.cloneObject(dataSources);

            options = obj.cloneObject(options);

            obj.merge(options, { text: 'text', value: 'value', required: false });

            return new Promise(resolve => {
                Picker.value = obj.cloneObject(value);
                Picker.options = obj.cloneObject(options);
                Picker.dataSources = obj.cloneObject(dataSources);

                Picker.$nextTick(() => Picker.show = true);

                Picker
                    .$once('close', () => resolve())
                    .$once('remove', () => resolve(null))
                    .$once('finish', (value: any) => resolve(isPrimitive ? value.column : value));
            });
        };
    }
}

export { picker };