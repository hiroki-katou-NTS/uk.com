import { Vue, _ } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { TimeWDPickerComponent } from '@app/components';
import { TimeWithDay, DAYS } from '@app/utils/time';
import { MobilePicker } from '@app/components/picker';
@component({
    template: `
    <div class="form-control">
        <span v-on:click="selectStartTime">
            {{displayStartTime}}
        </span> ～   
        <span v-on:click="selectEndTime">
            {{ displayEndTime }}
        </span>   

        <span class="fas fa-search" v-on:click="emitSearch">
        </span>

    </div>
    `,
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
    private startTime: number = this.defaultStartTime || 3360;

    get displayStartTime() {
        return TimeWithDay.toString(this.startTime);
    }

    private endTime: number = this.defaultEndTime || 3361;

    get displayEndTime() {
        return TimeWithDay.toString(this.endTime);
    }

    

    // ====================================== method =========================================================

    public selectStartTime() {

        let selecteds = TimeWithDayPicker.computeSelecteds(this.startTime);

        this.$picker(selecteds,
            TimeWithDayPicker.getDataSource(), 
            TimeWithDayPicker.onSelect, 
            {title: '開始'})
            .then((value: any) => {
                if (value !== undefined) {
                    this.startTime = TimeWithDayPicker.computeNewValue(value);
                }
                
            });
    }

    public selectEndTime() {
        this.$picker(TimeWithDayPicker.computeSelecteds(this.endTime),
            TimeWithDayPicker.getDataSource(), {
                title: '終了'
            })
            .then((value: any) => {
                if (value !== undefined) {
                    this.endTime = TimeWithDayPicker.computeNewValue(value);
                }
            });
    }   

    public emitSearch() {
        this.$emit('search', this.startTime, this.endTime);
    }
}

class TimeWithDayPicker {  

    public static getDataSource() {
        let day = this.generateDays();
        let hour: Array<Object> = this.generateArray(0, 23);
        let minute: Array<Object> = this.generateArray(0, 59);

        return {
            day, hour, minute
        };
    }

    public static computeNewValue(newValue: any): number {

        return TimeWithDay.fromObject(newValue).value;
        
    }

    public static generateArray(min: number, max: number): Array<Object> {

        return _.range(min, max).map((m: number) => ({ text: _.padStart(`${m}`, 2, '0'), value: m }));

    }

    private static generateDays() {
        let days = [];
        days.push({
            text: DAYS.TheDayBefore,
            value: -1
        });

        days.push({
            text: DAYS.Today,
            value: 0
        });
        days.push({
            text: DAYS.NextDay,
            value: 1
        });
        days.push({
            text: DAYS.TwoDaysLater,
            value: 2
        });

        return days;
    }

    public static computeSelecteds(value: number): Object {
        return {
            day : TimeWithDay.getDayNumber(value), 
            hour : TimeWithDay.getHour(value), 
            minute : TimeWithDay.getMinute(value)
        };
    }

    public static onSelect(value: any, picker: { title: string, dataSources: any, value: any }) {
        if (value.day === -1) {
            // dang loi cho nay
            picker.dataSources.hour = TimeWithDayPicker.generateArray(12, 23);
        }
    }
}

Vue.component('time-range-search-box', TimeRangeSearchBoxComponent);