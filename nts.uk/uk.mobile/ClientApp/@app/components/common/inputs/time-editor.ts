import { Vue } from '@app/provider';
import { TimeInputType, TimeWithDay, TimePoint, TimeDuration } from '@app/utils';
import { input, InputComponent } from './input';
import { Prop, Emit } from '@app/core/component';
import { TimeWithDayHelper, TimePointHelper, TimeDurationHelper } from '@app/components/controls/time-picker';

@input()
export class TimeComponent extends InputComponent {
    public type: string = 'string';

    public editable: boolean = false;

    @Prop({
        default: TimeInputType.TimeDuration
    })
    public timeInputType: TimeInputType;

    get rawValue() {
        if (this.value == null || this.value == undefined) {
            return '';
        }

        switch (this.timeInputType) {
            case TimeInputType.TimeWithDay:
                return TimeWithDay.toString(this.value);

            case TimeInputType.TimePoint:
                return TimePoint.toString(this.value);
            case TimeInputType.TimeDuration:
            default:
                return TimeDuration.toString(this.value);
        }
    }

    public mounted() {
        this.icons.after = 'far fa-clock';
    }

    @Emit()
    public input() {
        let value = (this.$refs.input as HTMLInputElement).value;

        if (value) {
            let numb = Number(value);

            if (!isNaN(numb)) {
                return numb;
            } else {
                return null;
            }
        }

        return null;
    }

    public click() {
        let helper = null;
        let utils = null;
        switch (this.timeInputType) {
            case TimeInputType.TimeWithDay:
                helper = TimeWithDayHelper;
                utils = TimeWithDay;
                break;
            case TimeInputType.TimePoint:
                helper = TimePointHelper;
                utils = TimePoint;
                break;
            case TimeInputType.TimeDuration:
            default:
                helper = TimeDurationHelper;
                utils = TimeDuration;
                break;
        }

        this.$picker(helper.computeSelecteds(this.value), helper.getDataSource(this.value), helper.onSelect)
        .then((value: any) => {
            if (value !== undefined) {
                this.$emit('input', utils.fromObject(value).value);
            }
        });

        
    }
}

Vue.component('nts-time-editor', TimeComponent);