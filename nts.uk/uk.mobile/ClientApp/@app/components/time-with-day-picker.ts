import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';
import {time, DAYS, TimeWithDayPoint } from '@app/utils/time';
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

    private maxPoint: TimeWithDayPoint = time.timewd.computeTimePoint(this.params.maxValue);
    private minPoint: TimeWithDayPoint = time.timewd.computeTimePoint(this.params.minValue);

    public minutes: number = this.params.value;
    public dayValue: string = time.timewd.computeDay(this.params.value);
    public hourValue: number = time.timewd.computeHour(this.params.value);
    public minuteValue: number = time.timewd.computeMinute(this.params.value);

    public ok() {
        this.$close(this.minutes);
    }

    public cancel() {
        this.$close();
    }

    public whenDayChange() {
        let newDay = ( this.$refs.day as HTMLInputElement).value;
        this.dayValue = newDay;
        _.defer(() => {
            let tmp = this.hourValue;
            this.hourValue = undefined;
            this.hourValue = tmp;
            this.minutes = time.timewd.computeValue(this.dayValue, this.hourValue, this.minuteValue);
        });
    }

    public whenHourChange() {
        let newHour = ( this.$refs.hour as HTMLInputElement).value;
        this.hourValue = Number(newHour);
        _.defer(() => {
            let tmp = this.minuteValue;
            this.minuteValue = undefined;
            this.minuteValue = tmp;
            this.minutes = time.timewd.computeValue(this.dayValue, this.hourValue, this.minuteValue);
        });
    }

    public whenMinuteChange() {
        let newMinute = ( this.$refs.minute as HTMLInputElement).value;
        this.minuteValue = Number(newMinute);
        this.minutes = time.timewd.computeValue(this.dayValue, this.hourValue, this.minuteValue);
    }

    get dayList(): Array<String> {
        let dayList = new Array(DAYS.TheDayBefore, DAYS.Today, DAYS.NextDay, DAYS.TwoDaysLater);
        switch (this.minPoint.day) {
            case DAYS.TheDayBefore:
                // do nothing
                break;
            case DAYS.Today:
                this.removeElement(dayList, [DAYS.TheDayBefore]);
                break;
            case DAYS.NextDay:
                this.removeElement(dayList, [DAYS.TheDayBefore, DAYS.Today]);
                break;
            case DAYS.TwoDaysLater:
                this.removeElement(dayList, [DAYS.TheDayBefore, DAYS.Today, DAYS.NextDay]);
                break;
        }

        switch (this.maxPoint.day) {
            case DAYS.TheDayBefore:
                this.removeElement(dayList, [DAYS.Today, DAYS.NextDay, DAYS.TwoDaysLater]);
                break;
            case DAYS.Today:
                this.removeElement(dayList, [DAYS.NextDay, DAYS.TwoDaysLater]);
                break;
            case DAYS.NextDay:
                this.removeElement(dayList, [DAYS.TwoDaysLater]);
                break;
            case DAYS.TwoDaysLater:
                // do nothing
                break;
        }
        return dayList;
    }

    private removeElement(bigArray: Array<string>, smallArray: Array<string>) {
        let newArray = new Array<string>();
        for (let outElement in bigArray) {
            for (let inElement in smallArray) {
                if (outElement == inElement) {
                    let i = bigArray.indexOf(outElement);
                    bigArray.splice(i, 1);
                }
            }
        }
    }

    get hourList(): Array<number> {

        let minHour = 0;
        let maxHour = 23;
        if (this.dayValue == this.minPoint.day) {
            minHour = this.minPoint.hour;
            if (this.hourValue < minHour) {
                this.hourValue = minHour;
            }
        }

        if (this.dayValue == this.maxPoint.day) {
            maxHour = this.maxPoint.hour;
            if (this.hourValue > maxHour) {
                this.hourValue = maxHour;
            }
        }

       
        return this.generateArray(minHour, maxHour);
    }

    get minuteList(): Array<number> {
        let minMinute = 0;
        let maxMinute = 59;

        if (this.dayValue == this.minPoint.day && this.hourValue == this.minPoint.hour) {
            minMinute = this.minPoint.minute;
            if (this.minuteValue < minMinute) {
                this.minuteValue = minMinute;
            }
        }

        if (this.dayValue == this.maxPoint.day && this.hourValue == this.maxPoint.hour) {
            maxMinute = this.maxPoint.minute;
            if (this.minuteValue > maxMinute) {
                this.minuteValue = maxMinute;
            }
        }

        return this.generateArray(minMinute, maxMinute);
    }

    private generateArray(min: number, max: number): Array<number> {
        let minuteList = new Array<number>();
        for (let m = min; m <= max; m++) {
            minuteList.push(m);
        }
        return minuteList;
    }

}

