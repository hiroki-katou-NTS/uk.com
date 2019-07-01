import { $ } from '@app/utils';
import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { InputComponent } from '@app/components/common/inputs/input';

@component({
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
            <time-range-search-box 
                v-bind:required="required"
                v-bind:class="{ 'is-invalid': invalid }"
                v-bind:invalid="invalid"
                v-bind:default-start-time="startTime"
                v-bind:default-end-time="endTime"
                v-bind:icons="iconsClass"
                v-bind:disabled="disabled"
                v-on:input="onInput" />
            <v-errors v-for="(error, k) in ($errors || errorsAlways || {})" v-bind:key="k" v-bind:data="error" v-bind:name="name" />
        </div>
    </div>`
})
export class TimeRangeEditorComponent extends InputComponent {
    get iconsClass() {
        return this.icons && (this.icons.before || this.icons.after) ? this.icons : { before: '', after: 'fas fa-clock' };
    }

    get startTime() {
        return this.value && this.value.start;
    }

    get endTime() {
        return this.value && this.value.end;
    }

    get invalid() {
        return $.size(this.$errors) || $.size(this.errorsAlways);
    }

    get required() {
        return this.constraints && this.constraints.required;
    }

    public onInput(value: { start: number | null; end: number | null; }) {
        this.$emit('input', value);
    }
}

Vue.component('nts-time-range-input', TimeRangeEditorComponent);