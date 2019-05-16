import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { time, DAYS } from '@app/utils/time';
import * as _ from 'lodash';

@component({
    template: `
        <div class="time-picker">
            <div class="row">

                <div class="col-4">
                    <select class="form-control text-center" :value="dayValue" ref="day" @input="whenDayChange">
                        <option v-for="day in dayList">{{day}}</option>
                    </select>
                </div>

                <div class="col-4">
                    <select class="form-control text-center" :value="hourValue" ref="hour" @input="whenHourChange">
                        <option v-for="hour in hourList">{{hour}}</option>
                    </select>
                </div>
                    
                <div class="col-4">
                    <select class="form-control text-center" :value="minuteValue" ref="minute" @input="whenMinuteChange">
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
export class TimeWDPickerComponent extends Vue {

    @Prop({
        default: {
            value: 0
        }
    })
    public readonly params: {
        value: number;
    };

    public minutes: number = this.params.value;
    public dayValue: string = time.timewd.computeDay(this.params.value);
    public hourValue: number = time.timewd.computeHour(this.params.value);
    public minuteValue: number = time.timewd.computeMinute(this.params.value);

    get dayList(): Array<String> {

        return new Array(DAYS.TheDayBefore, DAYS.Today, DAYS.NextDay, DAYS.TwoDaysLater);
    }

    get hourList(): Array<number> {
        
        if (this.dayValue == DAYS.TheDayBefore) {
            if (this.hourValue < 12) {
                this.hourValue = 12;
            }

            return _.range(12, 24);
        }

        return _.range(0, 24);
    }

    get minuteList(): Array<number> {
        
        return _.range(0, 59);
    }

    public ok() {
        this.$close(this.minutes);
    }

    public cancel() {
        this.$close();
    }

    public whenDayChange() {
        let newDay = (this.$refs.day as HTMLInputElement).value;
        this.dayValue = newDay;
        _.defer(() => {
            let tmp = this.hourValue;
            this.hourValue = undefined;
            this.hourValue = tmp;
            this.minutes = time.timewd.computeValue(this.dayValue, this.hourValue, this.minuteValue);
        });
    }

    public whenHourChange() {
        let newHour = (this.$refs.hour as HTMLInputElement).value;
        this.hourValue = Number(newHour);
        _.defer(() => {
            let tmp = this.minuteValue;
            this.minuteValue = undefined;
            this.minuteValue = tmp;
            this.minutes = time.timewd.computeValue(this.dayValue, this.hourValue, this.minuteValue);
        });
    }

    public whenMinuteChange() {
        let newMinute = (this.$refs.minute as HTMLInputElement).value;
        this.minuteValue = Number(newMinute);
        this.minutes = time.timewd.computeValue(this.dayValue, this.hourValue, this.minuteValue);
    }

    

}

