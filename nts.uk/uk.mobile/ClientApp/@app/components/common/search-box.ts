import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { TimeWDPickerComponent } from '@app/components';
import { time, DAYS, date } from '@app/utils';
import { MobilePicker } from '@app/components/picker';
@component({
    template: `
    <div class="container form-control">
        <div class="row" >
            <div class="col-4"v-on:click="selectStartTime">
                {{displayStartTime}}
            </div>        
            <div class="col-2">
            ～
            </div>
            <div class="col-4" v-on:click="selectEndTime">
                {{ displayEndTime }}
            </div>   

            <div class="col-2 fas fa-search" v-on:click="emitSearch">
            </div>

        </div>

    </div>`,
    components: {
        'picker': MobilePicker,
        'time-with-day-picker': TimeWDPickerComponent
    }
})
export class TimeRangeSearchBoxComponent extends Vue {

    // ========================================= props ============================================================
    @Prop()
    public defaultStartTime: number;
    @Prop()
    public defaultEndTime: number;

    //========================================= data and computed ====================================================
    private startTime: number = this.defaultStartTime || 0;

    get displayStartTime() {
        return time.timewd.toString(this.startTime);
    }

    private endTime: number = this.defaultEndTime || 480;

    get displayEndTime() {
        return time.timewd.toString(this.endTime);
    }

    private picker = new TimeWithDayPicker();

    // ====================================== method =========================================================

    public selectStartTime() {

        let selecteds = this.picker.computeSelecteds(this.startTime);

        this.$picker(selecteds,
            this.picker.dataSources, 
            this.picker.onSelect, 
            {title: this.picker.getTitle(selecteds)})
            .then((value: any) => {
                if (value !== undefined) {
                    this.startTime = this.picker.computeNewValue(value);
                }
                
            });
    }

    public selectEndTime() {
        this.$picker(this.picker.computeSelecteds(this.endTime),
            this.picker.dataSources)
            .then((value: any) => {
                if (value !== undefined) {
                    this.endTime = this.picker.computeNewValue(value);
                }
            });
    }   

    public emitSearch() {
        this.$emit('search', this.startTime, this.endTime);
    }
}

class TimeWithDayPicker {  

    private date = this.generateDates();

    private hour: Array<Object> = this.generateArray(0, 23);

    private minute: Array<Object> = this.generateArray(0, 59);

    public dataSources = {
        date : this.date, 
        hour : this.hour, 
        minute: this.minute
    };

    public computeNewValue(newValue): number {

        return time.timewd.computeValue(DAYS.Today, newValue.hour, newValue.minute);
        
    }

    private generateArray(min: number, max: number): Array<Object> {
        let result = [];
        for (let value = min; value <= max; value++) {
            let text = time.leftpad(value);
            result.push({text, value});
        }

        return result;
    }

    private generateDates() {
        let date = [];
        date.push({
            text: DAYS.TheDayBefore,
            value: -1
        });

        date.push({
            text: DAYS.Today,
            value: 0
        });
        date.push({
            text: DAYS.NextDay,
            value: 1
        });
        date.push({
            text: DAYS.TwoDaysLater,
            value: 2
        });

        return date;
    }

    public computeSelecteds(value: number): Object {
        let date = time.timewd.computeDay(value);
        let hour = time.timewd.computeHour(value);     
        let minute = time.timewd.computeMinute(value);

        return {
            date, hour, minute
        };
    }

    public onSelect(value: any, pkr: { title: string, dataSources: any }) {
        if (value) {
            let hour = time.leftpad(value.hour);
            let minute = time.leftpad(value.minute);

            pkr.title = `${value.date}  ${hour}:${minute}`;
        }
    }

    public getTitle(value) {
        let hour = time.leftpad(value.hour);
        let minute = time.leftpad(value.minute);

        return `${value.date}  ${hour}:${minute}`;
    }
}

Vue.component('time-range-search-box', TimeRangeSearchBoxComponent);