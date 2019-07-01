import { $ } from '@app/utils';
import { Vue, moment } from '@app/provider';
import { input, InputComponent } from './input';
import { DateHelper as helper} from '@app/components/controls/date-picker';

@input()
class DateComponent extends InputComponent {
    public type: string = 'string';

    public editable: boolean = false;

    public mounted() {
        this.icons.after = 'far fa-calendar-alt';
    }

    get rawValue() {
       if ($.isNil(this.value)) {
           return '';
       }

       return this.computeRawValue(this.value);
    }

    private computeRawValue(value: Date): string {
        let year: number = value.getFullYear();
        let month: number = value.getMonth() + 1;
        let date: number = value.getDate();
        let monthText = month < 10 ? '0' + month : month;
        let dateText = date < 10 ? '0' + date : date;

        return year + '-' + monthText + '-' + dateText;
    }

    public click() {
        let selecteds = helper.computeSelecteds(this.value);
        this.$picker(selecteds, 
            helper.getDataSource(this.value),
            helper.onSelect, {
                title: `${selecteds.year}年${selecteds.month}月${selecteds.date}日`,
                required: this.constraints.required
            })
            .then( (select: any) => {
                if ( select === undefined) {

                } else if (select === null) {
                    this.$emit('input', null);
                } else {
                    let momentValue = moment.utc(`${select.year}-${select.month}-${select.date}`, 'YYYY-MM-DD');
                    this.$emit('input', momentValue.toDate());
                }
            });

    }
}

Vue.component('nts-date-input', DateComponent);