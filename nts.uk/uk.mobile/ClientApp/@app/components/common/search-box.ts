import { Vue, _ } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { TimeWithDay, DAYS } from '@app/utils/time';
import { MobilePicker } from '@app/components/picker';
import { Component } from 'vue-property-decorator';
import { TimeWithDayHelper } from '@app/components/controls/time-picker';
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

            <span class="search-button fas fa-search" v-click:500="emitSearch">
            </span>

        </div>
    </div>
    `,
    components: {
        'picker': MobilePicker,
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

        this.showPicker(this.startTime)
            .then((value: any) => {
                if (value !== undefined) {
                    this.startTime = TimeWithDay.fromObject(value).value;
                }

            });
    }

    public selectEndTime() {
        this.showPicker(this.endTime)
            .then((value: any) => {
                if (value !== undefined) {
                    this.endTime = TimeWithDay.fromObject(value).value;
                }
            });
    }

    private showPicker(value: number): Promise<{}> {
        let selected = TimeWithDayHelper.computeSelecteds(value);
        let title = TimeWithDay.fromObject(selected).toString();

        return this.$picker(selected,
            TimeWithDayHelper.getDataSource(value),
            TimeWithDayHelper.onSelect,
            { title });
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

Vue.component('time-range-search-box', TimeRangeSearchBoxComponent);
Vue.component('text-search-box', TextSearchBox);