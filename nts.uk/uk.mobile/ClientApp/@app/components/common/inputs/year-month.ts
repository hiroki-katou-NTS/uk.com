import { Vue, moment, _ } from '@app/provider';
import { input, InputComponent } from './input';

@input()
export class YearMonthComponent extends InputComponent {
    public type: string = 'string';
    public editable: boolean = false;

    private get dataSource() {
        return {
            year: _.range(1900, 2101, 1).map((value: number) => ({ value, text: `${value}` })),
            month: _.range(1, 13, 1).map((value: number) => ({ value, text: _.padStart(`${value}`, 2, '0') }))
        };
    }

    // Functions
    private get year(): number {
        return this.value ? + Math.floor(this.value / 100) : moment.utc().year();
    }

    private get month(): number {
        return this.value ? + Math.floor(this.value % 100) : moment.utc().month() + 1;
    }

    public get rawValue() {
        let self = this,
            { year, month } = self;

        if (_.isNil(self.value)) {
            return this.$i18n('year_month', ['----', '--']);
        }

        return this.displayYearMonth({ year, month });
    }

    // Hooks
    public mounted() {
        this.icons.after = 'far fa-calendar-alt';
    }

    public click() {
        let self = this,
            { year, month } = self;

        self.$picker({ year, month },
            self.dataSource,
            self.onSelect, {
                title: self.displayYearMonth({ year, month }),
                required: self.constraints && self.constraints.required
            })
            .then((select: any) => {
                if (select === undefined) {

                } else if (select === null) {
                    self.$emit('input', null);
                } else {
                    self.$emit('input', select.year * 100 + select.month);
                }
            });
    }

    public onSelect(value: { year: number; month: number; }, pkr: { title: string }) {
        if (value.year !== undefined && value.month !== undefined) {
            pkr.title = this.displayYearMonth(value);
        }
    }

    private displayYearMonth(value: { year: number; month: number; }) {
        return this.$i18n('year_month', [value.year.toString(), value.month.toString()]);
    }
}

Vue.component('nts-year-month', YearMonthComponent);