import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';

@component({
    template: `<div class="time-range-editor">
    <time-range-search-box />
    </div>`
})
export class TimeRangeEditorComponent extends Vue {
    @Prop({ default: () => 'TimeRangeEditor'})
    public readonly title!: string;
}

Vue.component('nts-time-range', TimeRangeEditorComponent);