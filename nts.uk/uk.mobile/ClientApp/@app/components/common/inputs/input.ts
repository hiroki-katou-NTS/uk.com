import { obj } from '@app/utils';
import { Vue } from '@app/provider';
import { IRule } from 'declarations';
import { component, Prop, Watch } from '@app/core/component';
import { MobilePicker } from '@app/components/picker';
import { TimeWDPickerComponent, TimePointPickerComponent, TimeDurationPickerComponent } from '@app/components';

export const input = (tagName: 'input' | 'textarea' | 'select' = 'input') => component({
    template: `<div class="form-group row">
        <template v-if="showTitle && showTitle !== 'false'">
            <div v-bind:class="columns.title">
                <nts-label 
                    v-bind:constraint="constraints"
                    v-bind:show-constraint="showConstraint"
                    v-bind:class="{ 'control-label-inline': inlineTitle && inlineTitle !== 'false' }"
                    >{{ name | i18n }}</nts-label>
            </div>
        </template>
        <div v-bind:class="columns.input">
            <div class="input-group input-group-transparent">                
                <template v-if="icons.before" key="show">
                    <div class="input-group-prepend">
                        <span class="input-group-text" v-bind:class="iconsClass.before">{{ !iconsClass.before ? icons.before : '' }}</span>
                    </div>
                </template>
                <template v-else key="hide"></template>
                <template v-if="icons.after" key="show">
                    <div class="input-group-append">
                        <span class="input-group-text" v-bind:class="iconsClass.after">{{ !iconsClass.after ? icons.after : ''}}</span>
                    </div>
                </template>
                <template v-else key="hide"></template>       
                ${
        tagName === 'select' ?
            `<select class="form-control" 
                            ref="input"
                            v-validate="{
                                always: !!errorsAlways,
                                errors: ($errors || errorsAlways || {})
                            }"
                            v-bind:disabled="disabled"
                            v-bind:value="rawValue"
                            v-on:change="input()">
                        <slot />
                    </select>`
            :
            `<${tagName} class="form-control"
                        ref="input"
                        v-bind:type="type"
                        v-validate="{
                            always: !!errorsAlways,
                            errors: ($errors || errorsAlways || {})
                        }"
                        v-bind:rows="rows"
                        v-bind:disabled="disabled"
                        v-bind:readonly="!editable"
                        v-bind:value="rawValue"
                        v-bind:min="minValue"
                        v-bind:max="maxValue"
                        v-bind:max-length="maxLength"
                        v-bind:tabindex="tabIndex"
                        v-on:click="click()"
                        v-on:keydown.13="click()"
                        v-on:input="input()"
                        v-on:keydown="evt => $emit('keydown', evt)"
                        v-on:keypress="evt => $emit('keypress', evt)"
                        v-on:keyup="evt => $emit('keyup', evt)"
                        v-on:focus="evt => $emit('focus', evt)"
                        v-on:blur="evt => $emit('blur', evt)"
                        v-on:click="evt => $emit('click', evt)"
                        v-on:dblclick="evt => $emit('dblclick', evt)"
                    />`
        }
                <v-errors v-for="(error, k) in ($errors || errorsAlways || {})" v-bind:key="k" v-bind:data="error" v-bind:name="name" />
            </div>
            <template v-if="picker.has" key="showpicker">
                <picker v-model="picker.select" 
                        v-bind:title="name" 
                        v-bind:show="picker.show" 
                        v-bind:data-sources="picker.dataSources" 
                        v-bind:options="picker.options"
                        v-on:close="picker.show = false" 
                        v-on:select="picker.onSelect"
                        v-on:finish="picker.onFinish"
                        v-on:remove="picker.onRemove" />
            </template>
            <template v-else key="hidepicker"></template>
        </div>
    </div>`,
    components: {
        'picker': MobilePicker,
        'time-point-picker': TimePointPickerComponent,
        'time-duration-picker': TimeDurationPickerComponent,
        'time-with-day-picker': TimeWDPickerComponent
    },
    resource: {
        vi: {
            before_today: 'Hôm qua',
            today: 'Hôm nay',
            after_today: 'Ngày mai',
            next_after_tody: 'Ngày kia'
        },
        jp: {
            before_today: '前日',
            today: '当日',
            after_today: '翌日',
            next_after_tody: '翌々日'
        }
    }
});

export class InputComponent extends Vue {
    public click() { }

    public type: string = '';
    public rows: number | null = null;

    public editable: boolean = true;

    public readonly picker: {
        has: boolean;
        show: boolean;
        select: { [key: string]: any };
        dataSources: { [key: string]: any[] };
        options: { [key: string]: any };
        onSelect: (value: { [key: string]: any }) => void;
        onFinish: () => void;
        onRemove: () => void;
    } = {
            has: false,
            show: false,
            select: {},
            dataSources: {},
            options: {},
            onSelect(v) { },
            onFinish() { },
            onRemove() { }
        };

    @Prop({ default: () => '' })
    public readonly name: string;

    @Prop({ default: () => '' })
    public readonly value: any;

    @Prop({ default: () => false })
    public readonly disabled?: boolean;

    @Prop({ default: () => null })
    public readonly errors!: any;

    @Prop({ default: () => null })
    public readonly errorsAlways!: any;

    @Prop({ default: () => ({}) })
    public readonly constraint!: IRule;

    @Prop({ default: () => true })
    public readonly showTitle!: 'true' | 'false' | boolean;

    @Prop({ default: () => true })
    public readonly showConstraint!: 'true' | 'fasle' | boolean;

    @Prop({ default: () => false })
    public readonly inlineTitle!: boolean;

    @Prop()
    public readonly tabIndex: number;

    @Prop({ default: () => ({ before: '', after: '' }) })
    public readonly icons!: { before: string; after: string };

    @Prop({ default: () => ({ title: 'col-md-12', input: 'col-md-12' }) })
    public readonly columns!: { title: string; input: string };

    @Prop({ default: () => undefined })
    public readonly min: string | number | Date | undefined;

    @Prop({ default: () => undefined })
    public readonly max: string | number | Date | undefined;

    get minValue() {
        return undefined;
    }

    get maxValue() {
        return undefined;
    }

    get maxLength() {
        return undefined;
    }

    get iconsClass() {
        let self = this,
            classess = ['fa', 'fas', 'fab'],
            isClass = (icon: string) => {
                return !!classess.filter((f) => icon.indexOf(f) > -1).length;
            };

        return {
            before: isClass(self.icons.before) ? self.icons.before : '',
            after: isClass(self.icons.after) ? self.icons.after : ''
        };
    }

    public readonly $errors: any = {};
    public readonly constraints: IRule = {};

    @Watch('errors', { deep: true })
    public wSErrs(newErrs: any) {
        let self = this;

        Vue.set(self, '$errors', newErrs);
    }

    @Watch('errorsAlways', { deep: true })
    public wSErrAs(newErrs: any) {
        let self = this;

        if (!obj.isBoolean(newErrs)) {
            Vue.set(self, '$errors', newErrs);
        }
    }

    @Watch('$parent.$errors', { deep: true })
    public wErrs(newErrs: any) {
        let self = this,
            regex = new RegExp('\\[.+\\]\\.'),
            props = self.$vnode.componentOptions.propsData,
            exprs = (((self.$vnode.data as any) || { model: { expression: '' } }).model || { expression: '' }).expression,
            exprs2 = exprs.replace(regex, '.'),
            exprs3 = exprs.replace(regex, `.$${self.$vnode.key}.`);

        if (obj.has(self.$parent, exprs3) && !obj.has(props, 'errors') &&
            (obj.isBoolean(self.errorsAlways) || self.errorsAlways == undefined)) {
            Vue.set(self, '$errors', obj.get(newErrs, exprs2));
        }
    }

    @Watch('constraint', { immediate: true, deep: true })
    public wSConsts(newValidts: any) {
        let self = this;

        Vue.set(self, 'constraints', newValidts);
    }

    @Watch('$parent.validations', { immediate: true, deep: true })
    public wConsts(newValidts: any) {
        let self = this,
            regex = new RegExp('\\[.+\\]\\.'),
            props = self.$vnode.componentOptions.propsData,
            exprs = (((self.$vnode.data as any) || { model: { expression: '' } }).model || { expression: '' }).expression,
            exprs2 = exprs.replace(regex, '.'),
            exprs3 = exprs.replace(regex, `.$${self.$vnode.key}.`);

        if (obj.has(self.$parent, exprs3) && !obj.has(props, 'constraint')) {
            Vue.set(self, 'constraints', obj.get(newValidts, exprs2));
        }
    }
}