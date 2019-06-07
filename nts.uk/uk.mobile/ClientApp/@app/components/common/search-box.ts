import { Vue, _ } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { TimeWDPickerComponent } from '@app/components';
import { TimeWithDay, DAYS } from '@app/utils/time';
import { MobilePicker } from '@app/components/picker';
import { Component } from 'vue-property-decorator';
@component({
    template: `
    <div class="time-range-search-box">
        <div class="form-control">
            <span class="start time-input" v-click:1000="selectStartTime">
                {{displayStartTime}}
            </span> 
            <span class="connect-letter">～</span>
            <span class="end time-input" v-click:1000="selectEndTime">
                {{ displayEndTime }}
            </span>   

            <span class="search-button fas fa-search" v-click:500"emitSearch">
            </span>

        </div>
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

            return '--- --:--';
        }

        return TimeWithDay.toString(value);
    }

    public selectStartTime() {

        this.$picker(TimeWithDayPicker.computeSelecteds(this.startTime),
            TimeWithDayPicker.getDataSource(this.startTime),
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
            TimeWithDayPicker.getDataSource(this.endTime),
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

@Component({
    template: `
    <div class="text-search-box">
            
        <input class="input form-control" 
            v-model="value" 
            v-bind:placeholder="placeholder"
            v-on:keydown.13="emitSearch">
        <span class="search-button" v-on:click="emitSearch"> 
            <div class="search-icon fas fa-search"></div>
        </span>
    </div>
    `
})
export class TextSearchBox extends Vue {

    @Prop({ default : () => ''})
    public placeholder: string;

    public value: String = null;

    public emitSearch() {
        this.$emit('search', this.value);
    }
}

class TimeWithDayPicker {

    public static getDataSource(value: number) {

        return {
            day: TimeWithDayPicker.Days, 
            hour: value < 0 ? TimeWithDayPicker.HoursFrom12 : TimeWithDayPicker.HoursFrom0, 
            minute: TimeWithDayPicker.Minutes
        };
    }

    public static Days: Array<Object> = [
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
    public static HoursFrom0: Array<Object> = TimeWithDayPicker.generateArray(0, 24);
    public static HoursFrom12: Array<Object> = TimeWithDayPicker.generateArray(12, 24);
    public static Minutes: Array<Object> = TimeWithDayPicker.generateArray(0, 60);

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

    public static onSelect(value: any, picker: { title: string, dataSources: any, selects: any }) {
        if (value.day === -1) {

            if (picker.dataSources.hour.length !== 12) {
                picker.dataSources.hour = TimeWithDayPicker.HoursFrom12;
            }

            if (value.hour < 12) {
                picker.selects.hour = 12;
            }

        } else {

            if (picker.dataSources.hour.length !== 24) {
                picker.dataSources.hour = TimeWithDayPicker.HoursFrom0;
            }
            
        }
    }
}

Vue.component('time-range-search-box', TimeRangeSearchBoxComponent);
Vue.component('text-search-box', TextSearchBox);