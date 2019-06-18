import { Vue, moment } from '@app/provider';
import { input, InputComponent } from './input';
import { TimeWithDay } from '@app/utils/time';

@input()
export class YearMonthComponent extends InputComponent {

    // DATA 

    private MIN_YEAR: number = 1900;
    private MAX_YEAR: number = 2100;

    public type: string = 'string';

    public editable: boolean = false;

    private dataSource = {
        year: this.generateArray(this.MIN_YEAR, this.MAX_YEAR),
        month: this.generateArray(1, 12)
    };

    private generateArray(min: number, max: number): Array<Object> {
        let result = [];
        for (let value = min; value <= max; value++) {
            result.push({
                text : TimeWithDay.leftpad(value), 
                value
            });
        }

        return result;
    }

    // Hooks

    public mounted() {
        this.icons.after = 'far fa-calendar-alt';
    }

    // Functions

    get selected() {
        return {
            year: this.getYear() || this.getCurrentYear(),
            month: this.getMonth() || this.getCurrentMonth()
        };
    }

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
        let self = this;
        this.$picker(self.selected, self.dataSource)
        .then( (select: any) => {
            if ( select != undefined) {
                self.$emit('input', select.year + TimeWithDay.leftpad(select.month));
            }
            
        });
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
        return year + '年' + TimeWithDay.leftpad(month) + '月';
    }

    private displayEmpty() {
        return '- - - - 年 - -月';
    }
}

Vue.component('nts-year-month', YearMonthComponent);