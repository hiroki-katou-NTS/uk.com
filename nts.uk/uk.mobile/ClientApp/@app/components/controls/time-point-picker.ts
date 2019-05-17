import { Vue, _ } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { time } from '@app/utils';

@component({
    template: `
        <div class="time-picker">
            <div class="row">
                <div class="col-6">
                    <select class="form-control" :value="hourValue" ref="hour1" @input="whenHourChange">
                        <option v-for="hour in hourList">{{hour}}</option>
                    </select>
                </div>
                    
                <div class="col-6">
                    <select class="form-control" :value="minuteValue" ref="minute1" @input="whenMinuteChange">
                        <option v-for="minute in minuteList">{{minute}}</option>
                    </select>
                </div>
            </div>

            <div class="modal-footer">
                <div class="row">
                    <div class="text-left col-6">
                        <button class="btn btn-link" @click="cancel()">Cancel</button>
                    </div>

                    <div class="text-right col-6">
                        <button class="btn btn-link" @click="ok()">Ok</button>
                    </div>
                </div>
            </div>
        </div>    
    `
})
export class TimePointPickerComponent extends Vue {

    @Prop({
        default: {
            value: 0
        }
    })
    public readonly params: {
        value: number;
    };

    get hourList(): Array<number> {
        let negativeArray = _.range(-12, -24);
        let positiveArray = _.range(0, 72);

        return negativeArray.concat(positiveArray);
    }

    get minuteList(): Array<number> {
         return _.range(0, 59);
    }

    public minutes: number = this.params.value;
    public hourValue: number = time.timept.computeHour(this.params.value);
    public minuteValue: number = time.timept.computeMinute(this.params.value);

    public ok() {
        this.$close(this.minutes);
    }

    public cancel() {
        this.$close();
    }

    public whenHourChange() {
        let newHour = (this.$refs.hour1 as HTMLInputElement).value;
        this.hourValue = Number(newHour);
        this.minutes = time.timept.computeValue(this.hourValue, this.minuteValue);
    }

    public whenMinuteChange() {
        let newMinute = (this.$refs.minute1 as HTMLInputElement).value;
        this.minuteValue = Number(newMinute);
        this.minutes = time.timept.computeValue(this.hourValue, this.minuteValue);
    }

}