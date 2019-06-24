import { Vue, moment, _ } from '@app/provider';
import { input, InputComponent } from './input';

@input()
export class YearMonthComponent extends InputComponent {

    // DATA 

    private MIN_YEAR: number = 1900;
    private MAX_YEAR: number = 2100;
    private currentYear: Number = moment.utc().year();
    private currentMonth: Number = moment.utc().month() + 1;
    private emptyDisplay: String = '- - - - 年 - -月';
    private dataSource = {
        year: this.generateArray(this.MIN_YEAR, this.MAX_YEAR),
        month: this.generateArray(1, 12)
    };
    private generateArray(min: number, max: number): Array<Object> {
        let result = [];
        for (let value = min; value <= max; value++) {
            result.push({
                text: this.padStart2(value),
                value
            });
        }

        return result;
    }


    public type: string = 'string';
    public editable: boolean = false;

    

    // Hooks

    public mounted() {
        this.icons.after = 'far fa-calendar-alt';
    }

    // Functions

    private get year(): Number {
        return this.value ? +this.value.slice(0, 4) : null;
    }

    private get month(): Number {
        return this.value ? +this.value.slice(4, 6) : null;
    }

    get selected() {
        return {
            year: this.year || this.currentYear,
            month: this.month || this.currentMonth
        };
    }

    get rawValue() {
        if (this.value == null || this.value == undefined) {
            return this.emptyDisplay;
        }

        return this.displayYearMonth(this.year, this.month);
    }

    public click() {
        let self = this;
        this.$picker(self.selected, self.dataSource, self.onSelect, {
            title: self.displayYearMonth(self.selected.year, self.selected.month),
            required: this.constraints.required
        })
            .then((select: any) => {
                if (select != undefined) {
                    self.$emit('input', select.year + this.padStart2(select.month));
                }

            });
    }

    public onSelect(value: any, pkr: { title: string }) {
        let self = this;
        if (value.year !== undefined && value.month !== undefined) {
            pkr.title = self.displayYearMonth(value.year, value.month);
        }
    }

    private displayYearMonth(year: Number, month: Number) {

        return year + '年' + this.padStart2(month) + '月';
    }

    private padStart2(value: any): string {
        return _.padStart(value, 2, '0');
    }

}

Vue.component('nts-year-month', YearMonthComponent);