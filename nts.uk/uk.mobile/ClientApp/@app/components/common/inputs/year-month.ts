import { Vue, moment } from '@app/provider';
import { input, InputComponent } from './input';

@input()
export class YearMonthComponent extends InputComponent {

    // DATA 

    private MIN_YEAR: number = 1900;
    private MAX_YEAR: number = 2100;

    public type: string = 'string';

    public editable: boolean = false;

    private years = this.generateArray(this.MIN_YEAR, this.MAX_YEAR);
    private months = this.generateArray(1, 12);

    private generateArray(min: number, max: number): Array<Object> {
        let result = [];
        for (let y = min; y <= max; y++) {
            result.push({
                text: y + '',
                value: y
            });
        }
        return result;
    }

    // Hooks

    public created() {
        let self = this;
        this.picker.has = true;

        this.picker.onFinish = () => {
            self.$emit('input', this.picker.select.year + '' + this.picker.select.month);
        };

        this.picker.onRemove = () => {
            self.$emit('input', null);
        };
    }

    public mounted() {
        this.icons.after = 'far fa-calendar-alt';
    }

    // Functions

    get rawValue() {
        if (this.value == null || this.value == undefined) {
            if (this.constraint.required) {
                let today = moment.utc();
                return this.displayYearMonth(today.year(), today.month() + 1);
            } else {
                return this.displayEmpty();
            }
        }

        return this.displayYearMonth(this.getYear(), this.getMonth());
    }

    public click() {
        this.picker.options = {
            text: 'text',
            value: 'value',
            required: this.constraint.required
        };
        this.picker.dataSources = {
            year: this.years,
            month: this.months
        };
        this.picker.select = {
            year: this.getYear() || this.getCurrentYear(),
            month: this.getMonth() || this.getCurrentMonth()
        };
        this.picker.show = true;
    }

    private getYear(): Number {
        return this.value ? +this.value.slice(0, 4) : null;
    }

    private getCurrentYear(): Number {
        return moment.utc().year();
    }

    private getMonth(): Number {
        return this.value ? +this.value.slice(4, 6) : null;
    }

    private getCurrentMonth(): Number {
        return moment.utc().month() + 1;
    }

    private displayYearMonth(year: Number, month: Number) {
        return year + '年' + month + '月';
    }

    private displayEmpty() {
        return '- - - -年- -月';
    }
}

Vue.component('nts-year-month', YearMonthComponent);