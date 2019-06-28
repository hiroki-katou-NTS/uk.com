import { $ } from '@app/utils';
import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { InputComponent } from '@app/components/common/inputs/input';

@component({
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
                <nts-date-input
                    v-model="start"
                    v-bind:icons="icons"
                    v-bind:tabindex="tabindex"
                    v-bind:placeholder="placeholder"
                    class="form-group-date mb-0"
                    v-bind:key="'startrange'" />
            </div>
            <div class="col-6">
                <nts-date-input
                    v-model="end"
                    v-bind:icons="icons"
                    v-bind:tabindex="tabindex"
                    v-bind:placeholder="placeholder"
                    class="form-group-date mb-0" 
                    v-bind:key="'endrange'" />
            </div>
        </div>
        <v-errors v-for="(error, k) in ($errors || errorsAlways || {})" v-bind:key="k" v-bind:data="error" v-bind:name="name" />
    </div>
</div>`
})
export class DateRangeEditorComponent extends InputComponent {
    get start() {
        return this.value && this.value.start || null;
    }

    set start(start: Date) {
        this.$emit('input', {
            start,
            end: this.end
        });
    }

    get end() {
        return this.value && this.value.end || null;
    }

    set end(end: Date) {
        this.$emit('input', {
            start: this.start,
            end
        });
    }

    get invalid() {
        return $.size(this.$errors) || $.size(this.errorsAlways);
    }
}

Vue.component('nts-date-range-input', DateRangeEditorComponent);