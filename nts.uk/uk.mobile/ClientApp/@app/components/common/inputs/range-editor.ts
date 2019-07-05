import { $, TimeInputType } from '@app/utils';
import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { InputComponent } from '@app/components/common/inputs/input';

const range = (tagName: string) => component({
    template: `<div class="form-group row">
    <template v-if="showTitle && showTitle !== 'false' && name" v-bind:key="'showtitle'">
        <div v-bind:class="columns.title">
            <nts-label 
                v-bind:constraint="constraints"
                v-bind:show-constraint="showConstraint"
                v-bind:class="{ 'control-label-inline': inlineTitle && inlineTitle !== 'false' }"
                >{{ name | i18n }}</nts-label>
        </div>
    </template>
    <template v-else v-bind:key="'hidetitle'"></template>
    <div v-bind:class="columns.input">
        <div class="row form-group form-group-range mb-0" v-bind:class="{ 'is-invalid': invalid }">
            <div class="col-6">
                <${tagName}
                    v-model="start"
                    v-bind:icons="icons"
                    v-bind:disabled="disabled"
                    v-bind:readonly="!editable"
                    v-bind:tabindex="tabindex"
                    v-bind:errors="$rangeErrors.start"
                    v-bind:constraint="constraints"
                    v-bind:show-error="false"
                    v-bind:placeholder="placeholder"
                    v-bind:time-input-type="timeInputType"
                    class="form-group-date mb-0"
                    v-bind:key="'startrange'" />
            </div>
            <div class="col-6">
                <${tagName}
                    v-model="end"
                    v-bind:icons="icons"
                    v-bind:disabled="disabled"
                    v-bind:readonly="!editable"
                    v-bind:tabindex="tabindex"
                    v-bind:errors="$rangeErrors.end"
                    v-bind:constraint="constraints"
                    v-bind:show-error="false"
                    v-bind:placeholder="placeholder"
                    v-bind:time-input-type="timeInputType"
                    class="form-group-date mb-0" 
                    v-bind:key="'endrange'" />
            </div>
        </div>
        <template v-if="showError" v-bind:key="'showError'">
            <v-errors v-for="(error, k) in ($errors || errorsAlways || {})" v-bind:key="k" v-bind:data="error" v-bind:name="name" />
        </template>
        <template v-else v-bind:key="'hideError'"></template>
    </div>
</div>`
});

export class RangeEditorComponent extends InputComponent {
    @Prop({
        default: TimeInputType.TimeWithDay
    })
    public timeInputType: TimeInputType;

    get start() {
        return this.value && this.value.start || null;
    }

    set start(start: Date | number) {
        this.$emit('input', {
            start,
            end: this.end
        });
    }

    get end() {
        return this.value && this.value.end || null;
    }

    set end(end: Date | number) {
        this.$emit('input', {
            start: this.start,
            end
        });
    }

    get invalid() {
        return !!($.size(this.$errors) || $.size(this.errorsAlways));
    }

    get $rangeErrors() {
        let self = this,
            $errors: { required: string } = self.$errors;

        if ($errors && $errors.required) {
            return {
                start: this.start ? {} : $errors,
                end: this.end ? {} : $errors
            };
        }

        return {
            start: $errors,
            end: $errors
        };
    }
}

@range('nts-date-input')
export class DateRangeEditorComponent extends RangeEditorComponent { }

@range('nts-time-editor')
export class TimeRangeEditorComponent extends RangeEditorComponent { }

Vue.component('nts-date-range-input', DateRangeEditorComponent);
Vue.component('nts-time-range-input', TimeRangeEditorComponent);