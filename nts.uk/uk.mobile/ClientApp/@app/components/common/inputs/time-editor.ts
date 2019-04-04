import { Vue } from '@app/provider';
import { TimeInputType, time } from "@app/utils";
import { input, InputComponent } from "./input";
import { Prop, Emit } from '@app/core/component';
@input()
export class TimeComponent extends InputComponent {
    type: string = 'string';

    editable: boolean = false;

    @Prop({
        default: TimeInputType.TimeDuration
    })
    timeInputType: TimeInputType;

    get rawValue() {
        if (this.value == null || this.value == undefined) {
            return '';
        }

        switch (this.timeInputType) {
            case TimeInputType.TimeWithDay:
                return time.timewd.toString(this.value);
            case TimeInputType.TimePoint:
                return time.timept.toString(this.value);
            case TimeInputType.TimeDuration:
                return time.timedr.toString(this.value);
        }
    }

    mounted() {
        this.icons.after = 'far fa-clock';
    }

    @Emit()
    input() {
        let value = (<HTMLInputElement>this.$refs.input).value;

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

    click() {
        var picker = 'time-with-day-picker';
        switch (this.timeInputType) {
            case TimeInputType.TimeWithDay:
                picker = 'time-with-day-picker';
                break;
            case TimeInputType.TimePoint:
                picker = 'time-point-picker';
                break;
            case TimeInputType.TimeDuration:
                picker = 'time-duration-picker';
                break;
        }

        this
            .$modal(picker, {
                value: this.value,
                minValue: this.constraint.minValue,
                maxValue: this.constraint.maxValue,
            }, {
                    type: "popup",
                    title: this.name,
                    animate: {
                        show: 'zoomIn',
                        hide: 'zoomOut'
                    }
                })
            .then(v => {
                if (v !== undefined) {
                    this.$emit('input', v);
                }
            });
    }
}

Vue.component('nts-time-editor', TimeComponent);