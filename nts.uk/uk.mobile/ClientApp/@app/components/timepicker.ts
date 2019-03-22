import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { time } from '@app/utils';
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
export class TimePickerComponent extends Vue {

    @Prop({
        default: {
            value: 0,
            minValue: 0,
            maxValue: 1349
        }
    })
    readonly params: {
        minValue: number;
        maxValue: number;
        value: number;
    };

    readonly minTimePoint = time.timedr.computeTimePoint(this.params.minValue);
    readonly maxTimePoint = time.timedr.computeTimePoint(this.params.maxValue);

    get hourList(): Array<number> {
        return this.generateArray(this.minTimePoint.hour, this.maxTimePoint.hour);
    }

    get minuteList(): Array<number> {
        var minMinute = 0;
        var maxMinute = 59;

        if(this.hourValue == this.minTimePoint.hour) {
            
            if(this.minTimePoint.hour >= 0 ) {
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

        if(this.hourValue == this.maxTimePoint.hour) {

            if (this.maxTimePoint.hour >= 0) {
                maxMinute = this.maxTimePoint.minute;
                if (this.minuteValue > maxMinute) {
                    this.minuteValue = maxMinute;
                }
            } else {
                minMinute = this.maxTimePoint.minute;
                if(this.minuteValue < minMinute) {
                    this.minuteValue = minMinute;
                }
            }
            
        }
        return this.generateArray(minMinute, maxMinute);       
    }

    generateArray(min: number, max: number): Array<number> {
        var minuteList = new Array<number>();
        for (var m = min; m<= max; m++) {
            minuteList.push(m);
        }
        return minuteList;
    }

    minutes: number = this.params.value;
    hourValue: number = time.timedr.computeHour(this.params.value);
    minuteValue: number = time.timedr.computeMinute(this.params.value);

    ok() {
        this.$close(this.minutes);
    }

    cancel() {
        this.$close();
    }

    whenHourChange() {
        let newHour = (<HTMLInputElement>this.$refs.hour1).value;
        this.hourValue = Number(newHour);
        _.defer(() => {
            var tmp = this.minuteValue;
            this.minuteValue = undefined;
            this.minuteValue = tmp;
            this.minutes = time.timedr.computeValue(this.hourValue, this.minuteValue);
        });
        
    }

    whenMinuteChange() {
        let newMinute = (<HTMLInputElement>this.$refs.minute1).value;
        this.minuteValue = Number(newMinute);
        this.minutes = time.timedr.computeValue(this.hourValue, this.minuteValue);
    }
    
}