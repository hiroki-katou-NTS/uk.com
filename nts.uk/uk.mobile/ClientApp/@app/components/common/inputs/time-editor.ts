import { Vue } from '@app/provider';
import { TimeInputType, time } from '@app/utils';
import { input, InputComponent } from './input';
import { Prop, Emit } from '@app/core/component';
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
                return time.timewd.toString(this.value);
            case TimeInputType.TimePoint:
                return time.timept.toString(this.value);
            case TimeInputType.TimeDuration:
                return time.timedr.toString(this.value);
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

    public created() {
        let self = this;
        this.picker.has = true;

        this.picker.onSelect = function () {
            Vue.set(self.picker, 'dataSources', {});
        };
    }

    public click() {
        this.picker.show = true;

        this.picker.dataSources = {
            h1: [{
                text: '0',
                value: 0
            }, {
                text: '1',
                value: 1
            }, {
                text: '2',
                value: 2
            }],
            h2: [{
                text: '0',
                value: 0
            }, {
                text: '1',
                value: 1
            }, {
                text: '2',
                value: 2
            }],
            m1: [{
                text: '0',
                value: 0
            }, {
                text: '1',
                value: 1
            }, {
                text: '2',
                value: 2
            }],
            m2: [{
                text: '0',
                value: 0
            }, {
                text: '1',
                value: 1
            }, {
                text: '2',
                value: 2
            }]
        };

    }
}

Vue.component('nts-time-editor', TimeComponent);