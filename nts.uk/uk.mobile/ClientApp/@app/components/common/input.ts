import { Vue } from '@app/provider';
import { IRule } from 'declarations';
import { component, Prop, Emit } from '@app/core/component';
import { DatePickerComponent, TimeWDPickerComponent, TimePointPickerComponent, TimeDurationPickerComponent } from '@app/components';

export const input = () => component({
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
        'datepicker': DatePickerComponent,
        'time-point-picker': TimePointPickerComponent,
        'time-duration-picker': TimeDurationPickerComponent,
        'time-with-day-picker': TimeWDPickerComponent
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

    @Prop({ default: () => ({}) })
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
class DropdownComponent extends InputComponent {
    type: string = 'select';

    get rawValue() {
        return this.value;
    }

    mounted() {
        this.icons.after = 'fa fa-caret-down';
        (<HTMLSelectElement>this.$refs.input).parentElement.classList.add("nts-dropdown-list");
    }

    @Emit()
    input() {
        return (<HTMLSelectElement>this.$refs.input).value;
    }
}
Vue.component('nts-input-password', PasswordComponent);

Vue.component('nts-input-number', NumberComponent);

Vue.component('nts-dropdown', DropdownComponent);