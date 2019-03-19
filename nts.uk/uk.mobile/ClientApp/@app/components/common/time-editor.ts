import { component, Prop, Watch } from '@app/core/component';
import { Vue } from '@app/provider';

@component({
    template: `
        <div>
            <span>
                <input ref="hour" class="time-editor hour" type="number" :value="hour" @input="whenHourChange"> 
                :    
                <input ref="minute" class="time-editor minute" type="number" :value="minute" @input="whenMinuteChange"/>
            </span>
        <div>
	`
})
export class TimeEditor extends Vue {

    @Prop()
    value: number;

    // to do not change property 'value'
    minutes: number = this.value;

    get hour(): number {
        return  Math.floor(this.minutes / 60);
    }

    set hour(newHour: number) {
        this.updateValue(newHour * 60 + this.minute);
    }

    get minute() : number {
        let hour = Math.floor(this.minutes / 60);
        return this.minutes - hour * 60;
    }

    set minute(newMinute: number) {
        this.updateValue(this.hour * 60 + newMinute);
    } 

    maxLengthHour: number = 2;

    whenHourChange() {
        let newHour = (<HTMLInputElement>this.$refs.hour).value;
        this.hour = Number(newHour);
        if(newHour.length >= this.maxLengthHour) {
            (<HTMLElement>this.$refs.minute).focus();
        }
    }

    whenMinuteChange() {
        let newMinute = (<HTMLInputElement>this.$refs.minute).value;
        this.minute = Number(newMinute);
    }

    updateValue(newValue: number) {
        this.minutes = newValue;
        this.$emit('input', this.minutes);
    }

}

Vue.component('nts-time-editor', TimeEditor);