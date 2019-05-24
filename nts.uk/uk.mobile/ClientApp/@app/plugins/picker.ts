import { obj, dom } from '@app/utils';
import { Vue, VueConstructor } from '@app/provider';
import { MobilePicker } from '@app/components/picker';

const vm = Vue
    .extend(MobilePicker)
    .extend({
        methods: {
            destroyPicker() {
                this.$destroy(true);
            }
        },
        destroyed() {
            document.body.removeChild(this.$el);
        }
    }), picker = {
        install(vue: VueConstructor<Vue>) {
            vue.prototype.$picker = function (value: string | number | Date | { [key: string]: string | number | Date },
                // tslint:disable-next-line: align
                dataSources: Array<string | number | Date> | { [key: string]: Array<string | number | Date> },
                // tslint:disable-next-line: align
                options: {
                    text: string;
                    value: string;
                    required?: boolean;
                    onSelect?: Function;
                } = { text: 'text', value: 'value', required: false }) {
                value = obj.cloneObject(value);
                options = obj.cloneObject(options);
                dataSources = obj.cloneObject(dataSources);

                obj.merge(options, { text: 'text', value: 'value', required: false });

                return new Promise((resolve) => {
                    let Picker = new vm(),
                        pkr = dom.create('div');

                    document.body.appendChild(pkr);

                    Picker.$mount(pkr);

                    Picker.value = obj.cloneObject(value);
                    Picker.options = obj.cloneObject(options);
                    Picker.dataSources = obj.cloneObject(dataSources);

                    Picker.$nextTick(() => Picker.show = true);

                    Picker
                        .$on('select', (value) => {
                            if (obj.isFunction(options.onSelect)) {
                                options.onSelect.apply(null, [obj.cloneObject(value), Picker]);
                            }
                        })
                        .$on('close', () => resolve())
                        .$on('remove', () => resolve(null))
                        .$on('finish', (value: any) => resolve(value));
                });
            };
        }
    };

export { picker };
