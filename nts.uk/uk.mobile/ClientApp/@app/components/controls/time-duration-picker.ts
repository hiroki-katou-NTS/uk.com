import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { time, TimeInputType } from '@app/utils';
import * as _ from 'lodash';

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
export class TimeDurationPickerComponent extends Vue {

    @Prop({
        default: {
            value: 0,
            minValue: 0,
            maxValue: 1349
        }
    })
    public readonly params: {
        minValue: number;
        maxValue: number;
        value: number;
    };
    
    public readonly timeUtil = time.timedr;

    public readonly minTimePoint = this.timeUtil.computeTimePoint(this.params.minValue);
    public readonly maxTimePoint = this.timeUtil.computeTimePoint(this.params.maxValue);

    get hourList(): Array<number> {
        let minHour = this.minTimePoint.hour;
        let maxHour = this.maxTimePoint.hour;
        return this.generateArray(minHour, maxHour);
    }

    get minuteList(): Array<number> {
        let minMinute = 0;
        let maxMinute = 59;

        if (this.hourValue == this.minTimePoint.hour) {
            
            if (this.minTimePoint.hour >= 0 ) {
                minMinute = this.minTimePoint.minute;
                if (this.minuteValue < minMinute) {
                    this.minuteValue = minMinute;
                }
            } else {
                maxMinute = this.minTimePoint.minute;
                if (this.minuteValue > maxMinute) {
                    this.minuteValue = minMinute;
                }
            }
            
        }

        if (this.hourValue == this.maxTimePoint.hour) {

            if (this.maxTimePoint.hour >= 0) {
                maxMinute = this.maxTimePoint.minute;
                if (this.minuteValue > maxMinute) {
                    this.minuteValue = maxMinute;
                }
            } else {
                minMinute = this.maxTimePoint.minute;
                if (this.minuteValue < minMinute) {
                    this.minuteValue = minMinute;
                }
            }
            
        }

        if (this.hourValue == 0) {
            minMinute = -59;
            maxMinute = 59;
        }

        return this.generateArray(minMinute, maxMinute);       
    }

    public generateArray(min: number, max: number): Array<number> {
        let minuteList = new Array<number>();
        for (let m = min; m <= max; m++) {
            minuteList.push(m);
        }
        return minuteList;
    }

    public minutes: number = this.params.value;
    public hourValue: number = this.timeUtil.computeHour(this.params.value);
    public minuteValue: number = this.timeUtil.computeMinute(this.params.value);

    public ok() {
        this.$close(this.minutes);
    }

    public cancel() {
        this.$close();
    }

    public whenHourChange() {
        let newHour = ( this.$refs.hour1 as HTMLInputElement).value;
        this.hourValue = Number(newHour);
        _.defer(() => {
            let tmp = this.minuteValue;
            this.minuteValue = undefined;
            this.minuteValue = tmp;
            this.minutes = this.timeUtil.computeValue(this.hourValue, this.minuteValue);
        });
        
    }

    public whenMinuteChange() {
        let newMinute = ( this.$refs.minute1 as HTMLInputElement).value;
        this.minuteValue = Number(newMinute);
        this.minutes = this.timeUtil.computeValue(this.hourValue, this.minuteValue);
    }
    
}