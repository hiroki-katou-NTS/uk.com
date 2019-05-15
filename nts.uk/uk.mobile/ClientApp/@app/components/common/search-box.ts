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
    private startTime: number = this.defaultStartTime || null;

    get displayStartTime() {
        return this.displayTime(this.startTime);
    }

    private endTime: number = this.defaultEndTime || null;

    get displayEndTime() {
        return this.displayTime(this.endTime);
    }

    // ====================================== method =========================================================

    private displayTime(value: number) {
        if (value === null) {

            return 'ーーー --:--';
        }

        return TimeWithDay.toString(value);
    }

    public selectStartTime() {

        this.$picker(TimeWithDayPicker.computeSelecteds(this.startTime),
            TimeWithDayPicker.getDataSource(),
            TimeWithDayPicker.onSelect,
            { title: '開始' })
            .then((value: any) => {
                if (value !== undefined) {
                    this.startTime = TimeWithDay.fromObject(value).value;
                }

            });
    }

    public selectEndTime() {
        this.$picker(TimeWithDayPicker.computeSelecteds(this.endTime),
            TimeWithDayPicker.getDataSource(),
            TimeWithDayPicker.onSelect,
            { title: '終了' })
            .then((value: any) => {
                if (value !== undefined) {
                    this.endTime = TimeWithDay.fromObject(value).value;
                }
            });
    }

    public emitSearch() {
        this.$emit('search', this.startTime, this.endTime);
    }
}

class TimeWithDayPicker {

    public static getDataSource() {
        let day = [
            {
                text: DAYS.TheDayBefore,
                value: -1
            }, {
                text: DAYS.Today,
                value: 0
            }, {
                text: DAYS.NextDay,
                value: 1
            }, {
                text: DAYS.TwoDaysLater,
                value: 2
            }
        ];
        let hour: Array<Object> = this.generateArray(0, 23);
        let minute: Array<Object> = this.generateArray(0, 59);

        return {
            day, hour, minute
        };
    }

    public static generateArray(min: number, max: number): Array<Object> {

        return _.range(min, max).map((m: number) => ({ text: _.padStart(`${m}`, 2, '0'), value: m }));

    }

    public static computeSelecteds(value: number): Object {

        if (value === null) {

            return {
                day: 0,
                hour: 8,
                minute: 0
            };
        }

        return TimeWithDay.toObject(value);
    }

    public static onSelect(value: any, picker: { title: string, dataSources: any, value: any }) {
        if (picker.value.day === -1) {
            // dang loi cho nay
            picker.dataSources.hour = TimeWithDayPicker.generateArray(12, 23);
        }
    }
}

Vue.component('time-range-search-box', TimeRangeSearchBoxComponent);