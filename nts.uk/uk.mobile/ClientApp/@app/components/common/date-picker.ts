import { component, Prop } from '@app/core/component';
import { Vue } from '@app/provider';

@component({
    template: `
		<div class="nts-datepicker">
			<input type="date" :min="min" :max="max" :name="name"/>
		</div>
	`
})
export class DatePicker extends Vue {

    @Prop()
    min: any;

    @Prop()
    max: any;

    @Prop()
    name: any;
}

Vue.component('nts-date-picker', DatePicker);