import { Vue } from '@app/provider';
import { IRule } from 'declarations';
import { component, Prop, Emit } from '@app/core/component';

import { $ } from '@app/utils';
import { TimePickerComponent, DatePickerComponent } from '@app/components';

const input = () => component({
    template: `<div class="form-group row">
        <template v-if="showTitle">
            <div v-bind:class="columns.title">
                <v-label v-bind:constraint="constraint">{{ name | i18n }}</v-label>
            </div>
        </template>
        <template v-else />
        <div v-bind:class="columns.input">
            <div class="input-group input-group-transparent">
                <template v-if="type !== 'select'">
                    <template v-if="icons.before">
                        <div class="input-group-prepend">
                            <span class="input-group-text" v-bind:class="iconsClass.before">{{  !iconsClass.before ? icons.before : '' }}</span>
                        </div>
                    </template>
                    <template v-else />
                    <template v-if="icons.after">
                        <div class="input-group-append">
                            <span class="input-group-text" v-bind:class="iconsClass.after">{{ !iconsClass.after ? icons.after : ''}}</span>
                        </div>
                    </template>
                    <template v-else />
                    <input class="form-control"
                        ref="input"
                        v-bind:type="type"
                        v-validate="{
                            always: !!errorsAlways,
                            errors: (errors || errorsAlways || {})
                        }"
                        v-bind:disabled="disabled"
                        v-bind:readonly="!editable"
                        v-bind:value="rawValue"
                        v-on:click="click()"
                        v-on:keydown.13="click()"
                        v-on:input="input()"
                    />
                </template>
                <template v-else>
                    <template v-if="icons.after">
                        <div class="input-group-append">
                            <span class="input-group-text" v-bind:class="iconsClass.after">{{ !iconsClass.after ? icons.after : ''}}</span>
                        </div>
                    </template>
                    <select class="form-control"
                        ref="input"
                        v-validate="{
                            always: !!errorsAlways,
                            errors: (errors || errorsAlways || {})
                        }"
                        v-bind:disabled="disabled"
                        v-bind:value="rawValue"
                        v-on:change="input()">
                        <slot />
                    </select>
                </template>
                <v-errors v-for="(error, k) in (errors || errorsAlways || {})" v-bind:key="k" v-bind:data="error" v-bind:name="name" />
            </div>
        </div>
    </div>`,
    components: {
        'timepicker': TimePickerComponent,
        'datepicker': DatePickerComponent
    }
});

export class InputComponent extends Vue {
    @Prop({ default: () => '' })
    readonly name: string;

    @Prop({ default: () => '' })
    readonly value: any;

    @Prop({ default: () => false })
    readonly disabled?: boolean;

    @Prop({ default: () => null })
    readonly errors!: any;

    @Prop({ default: () => null })
    readonly errorsAlways!: any;

    @Prop({ default: () => { } })
    readonly constraint!: IRule;

    @Prop({ default: () => true })
    readonly showTitle!: boolean;

    @Prop({ default: () => ({ before: '', after: '' }) })
    readonly icons!: { before: string; after: string }

    @Prop({ default: () => ({ title: 'col-md-12', input: 'col-md-12' }) })
    readonly columns!: { title: string; input: string };

    editable: boolean = true;

    get iconsClass() {
        let self = this,
            classess = ['fa', 'fas', 'fab'],
            isClass = (icon: string) => {
                return !!classess.filter(f => icon.indexOf(f) > -1).length;
            };

        return {
            before: isClass(self.icons.before) ? self.icons.before : '',
            after: isClass(self.icons.after) ? self.icons.after : ''
        };
    }

    click() {

    }
}

@input()
class StringComponent extends InputComponent {
    type: string = 'text';

    get rawValue() {
        return (this.value || '');
    }

    @Emit()
    input() {
        return (<HTMLInputElement>this.$refs.input).value;
    }
}

@input()
class PasswordComponent extends InputComponent {
    type: string = 'password';

    get rawValue() {
        return (this.value || '');
    }

    @Emit()
    input() {
        return (<HTMLInputElement>this.$refs.input).value;
    }
}

@input()
class NumberComponent extends InputComponent {
    type: string = 'number';

    get rawValue() {
        return (this.value || '').toString();
    }

    @Emit()
    input() {
        let value = (<HTMLInputElement>this.$refs.input).value;

        if (value) {
            let numb = Number(value);

            if (!isNaN(numb)) {
                return numb;
            } else {
                return null;
            }
        }

        return null;
    }
}

@input()
export class TimeComponent extends InputComponent {
    type: string = 'string';

    editable: boolean = false;

    get rawValue() {
        //return (this.value || '').toString();
        if (typeof this.value == undefined) {
            return '';
        }

        var hour: number, minute: number;
        if (this.value >= 0) {
            hour = Math.floor(this.value / 60)
            minute = this.value - hour * 60;
        } else {
            hour = 0 - Math.floor(Math.abs(this.value) / 60);
            minute = Math.abs(this.value) + hour * 60;
        }

        return hour + ' : ' + minute;

    }

    mounted() {
        this.icons.after = 'far fa-clock';
    }

    @Emit()
    input() {
        let value = (<HTMLInputElement>this.$refs.input).value;

        if (value) {
            let numb = Number(value);

            if (!isNaN(numb)) {
                return numb;
            } else {
                return null;
            }
        }

        return null;
    }

    click() {
        this
            .$modal('timepicker', {
                value: this.value,
                minValue: this.constraint.minValue,
                maxValue: this.constraint.maxValue
            }, {
                    type: "popup",
                    title: this.name,
                    animate: {
                        show: 'zoomIn',
                        hide: 'zoomOut'
                    }
                })
            .onClose(v => {
                if (v !== undefined) {
                    this.$emit('input', v);
                }
            });
    }
}

@input()
class DateComponent extends InputComponent {
    type: string = 'string';

    editable: boolean = false;

    mounted() {
        this.icons.after = 'far fa-calendar-alt';
    }

    get rawValue() {
        return (this.value || '').toString();
    }

    @Emit()
    input() {
        let value = (<HTMLInputElement>this.$refs.input).value;

        if (value) {
            let numb = new Date(value);

            if (!isNaN(numb.getTime())) {
                return numb;
            } else {
                return null;
            }
        }

        return null;
    }

    click() {
        this.$modal('datepicker', {
            value: this.value
        }, {
                type: "popup",
                title: this.name,
                animate: {
                    show: 'zoomIn',
                    hide: 'zoomOut'
                }
            }).onClose(v => {
                if (v !== undefined) {
                    this.$emit('input', v);
                }
            });
    }
}

@input()
class DropdownComponent extends InputComponent {
    type: string = 'select';

    get rawValue() {
        return this.value;
    }

    mounted() {
        this.icons.after = 'fa fa-caret-down';
    }

    @Emit()
    input() {
        return (<HTMLSelectElement>this.$refs.input).value;
    }
}

Vue.component('v-input', StringComponent);
Vue.component('v-input-string', StringComponent);
Vue.component('v-input-password', PasswordComponent);

Vue.component('v-input-time', TimeComponent);
Vue.component('v-input-date', DateComponent);
Vue.component('v-input-number', NumberComponent);

Vue.component('nts-dropdown', DropdownComponent);