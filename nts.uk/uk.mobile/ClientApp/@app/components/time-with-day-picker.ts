import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';
import {time, DAYS, TimeWithDayPoint } from '@app/utils/time'
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
    readonly params: {
        minValue: number;
        maxValue: number;
        value: number;
    };

    private maxPoint: TimeWithDayPoint = time.timewd.computeTimePoint(this.params.maxValue);
    private minPoint: TimeWithDayPoint = time.timewd.computeTimePoint(this.params.minValue);

    minutes: number = this.params.value;
    dayValue: string = time.timewd.computeDay(this.params.value);
    hourValue: number = time.timewd.computeHour(this.params.value);
    minuteValue: number = time.timewd.computeMinute(this.params.value);

    ok() {
        this.$close(this.minutes);
    }

    cancel() {
        this.$close();
    }

    whenDayChange() {
        let newDay = (<HTMLInputElement>this.$refs.day).value;
        this.dayValue = newDay;
        _.defer(()=> {
            let tmp = this.hourValue;
            this.hourValue = undefined;
            this.hourValue = tmp;
            this.minutes = time.timewd.computeValue(this.dayValue, this.hourValue, this.minuteValue);
        })
    }

    whenHourChange() {
        let newHour = (<HTMLInputElement>this.$refs.hour).value;
        this.hourValue = Number(newHour);
        _.defer(()=> {
            let tmp = this.minuteValue;
            this.minuteValue = undefined;
            this.minuteValue = tmp;
            this.minutes = time.timewd.computeValue(this.dayValue, this.hourValue, this.minuteValue);
        })
    }

    whenMinuteChange() {
        let newMinute = (<HTMLInputElement>this.$refs.minute).value;
        this.minuteValue = Number(newMinute);
        this.minutes = time.timewd.computeValue(this.dayValue, this.hourValue, this.minuteValue);
    }

    get dayList(): Array<String> {
        var dayList = new Array(DAYS.TheDayBefore, DAYS.Today, DAYS.NextDay, DAYS.TwoDaysLater);
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
                this.removeElement(dayList, [DAYS.TheDayBefore, DAYS.Today, DAYS.NextDay])
                break;
        }

        switch (this.maxPoint.day) {
            case DAYS.TheDayBefore:
                this.removeElement(dayList, [DAYS.Today, DAYS.NextDay, DAYS.TwoDaysLater])
                break;
            case DAYS.Today:
                this.removeElement(dayList, [DAYS.NextDay, DAYS.TwoDaysLater])
                break;
            case DAYS.NextDay:
                this.removeElement(dayList, [DAYS.TwoDaysLater])
                break;
            case DAYS.TwoDaysLater:
                // do nothing
                break;
        }
        return dayList;
    }

    private removeElement(bigArray: Array<string>, smallArray: Array<string>) {
        var newArray = new Array<string>();
        for (var outElement in bigArray) {
            for (var inElement in smallArray) {
                if (outElement == inElement) {
                    var i = bigArray.indexOf(outElement);
                    bigArray.splice(i, 1);
                }
            }
        }
    }

    get hourList(): Array<number> {

        var minHour = 0;
        var maxHour = 23;
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
        var minMinute = 0;
        var maxMinute = 59;

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
        var minuteList = new Array<number>();
        for (var m = min; m <= max; m++) {
            minuteList.push(m);
        }
        return minuteList;
    }

}

