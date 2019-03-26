import { input, InputComponent } from "@app/components/common/input";
import {TimeInputType, time} from "@app/utils"
import { Prop, Emit } from '@app/core/component';
import { Vue } from '@app/provider';

@input()
export class TimeComponent extends InputComponent {
    type: string = 'string';

    editable: boolean = false;

    @Prop({
        default: TimeInputType.TimeDuration
    })
    timeInputType: TimeInputType;

    get rawValue() {
        //return (this.value || '').toString();
        if (typeof this.value == undefined) {
            return '';
        }

        if (this.timeInputType === TimeInputType.TimeWithDay) {
            var timePoint = time.timewd.computeTimePoint(this.value);
            return timePoint.day + '　' + timePoint.hour + ' ： ' + timePoint.minute;
        } else {
            var timePoint1 = time.timedr.computeTimePoint(this.value);
            return timePoint1.hour + ' : ' + timePoint1.minute;
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
        var picker = this.timeInputType == TimeInputType.TimeWithDay ? 'timewdpicker' : 'timepicker';
        this
            .$modal(picker, {
                value: this.value,
                minValue: this.constraint.minValue,
                maxValue: this.constraint.maxValue
            }, {
                    type: "popup",
                    title: this.name,
                    animate: {
                        show: 'zoomIn',
                        hide: 'zoomOut'
                    }
                })
            .onClose(v => {
                if (v !== undefined) {
                    this.$emit('input', v);
                }
            });
    }
}

Vue.component('nts-time-editor', TimeComponent);