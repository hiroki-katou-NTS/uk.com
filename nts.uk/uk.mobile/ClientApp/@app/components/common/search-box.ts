import { Vue, _ } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { TimeWithDay, DAYS } from '@app/utils/time';
import { MobilePicker } from '@app/components/picker';
import { Component } from 'vue-property-decorator';
import { TimeWithDayHelper } from '@app/components/controls/time-picker';
@component({
    template: `<div class="form-group mb-1">
        <div class="input-group input-group-time">
            <div class="form-control">
                <div class="row m-0">
                    <div class="col-5 text-center" v-click:1000="selectStartTime">{{displayStartTime}}</div>
                    <div class="col-2 text-center">～</div>
                    <div class="col-5 text-center" v-click:1000="selectEndTime">{{displayEndTime}}</div>
                </div>
            </div>
            <div class="input-group-append" v-click:500="emitSearch">
                <span class="input-group-text" v-bind:class="icon"></span>
            </div>
        </div>
    </div>`,
    components: {
        'picker': MobilePicker,
    }
})
export class TimeRangeSearchBoxComponent extends Vue {
    // ========================================= props ============================================================
    @Prop({ default: 'fa fa-search' })
    public readonly icon!: string;

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
                if (value === undefined) {
                    // do nothing
                } else if (value === null) {
                    this.startTime = null;
                } else {
                    this.startTime = TimeWithDay.fromObject(value).value;
                }
            }).then(() => {
                // emit value to model binding
                if (this.startTime && this.endTime) {
                    this.$emit('input', { start: this.startTime, end: this.endTime });
                }
            });
    }

    public selectEndTime() {
        this.showPicker(this.endTime)
            .then((value: any) => {
                if (value === undefined) {
                    // do nothing
                } else if (value === null) {
                    this.endTime = null;
                } else {
                    this.endTime = TimeWithDay.fromObject(value).value;
                }
            }).then(() => {
                // emit value to model binding
                if (this.startTime && this.endTime) {
                    this.$emit('input', { start: this.startTime, end: this.endTime });
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

Vue.component('time-range-search-box', TimeRangeSearchBoxComponent);