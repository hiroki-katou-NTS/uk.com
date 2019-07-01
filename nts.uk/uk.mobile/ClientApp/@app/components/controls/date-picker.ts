import { _, moment } from '@app/provider';
import { obj } from '@app/utils';

export class DateHelper {

    private static MinYear: number = 1900;
    private static MaxYear: number = 2100;

    private static year = DateHelper.generateArray(DateHelper.MinYear, DateHelper.MaxYear);
    private static month = DateHelper.generateArray(1, 12);
    private static days28 = DateHelper.generateArray(1, 28);
    private static days29 = DateHelper.generateArray(1, 29);
    private static days30 = DateHelper.generateArray(1, 30);
    private static days31 = DateHelper.generateArray(1, 31);

    public static getDataSource(value: Date) {

        let daysInMonth: number;
        if ( value === undefined || value === null) {
            daysInMonth = moment().daysInMonth();
        } else {
            daysInMonth = moment(value).daysInMonth();
        }

        return {
            year: DateHelper.year,
            month: DateHelper.month,
            date: DateHelper.generateDays(daysInMonth)
        };
    }

    public static generateDays(daysInMonth: number): Array<Object> {
        switch (daysInMonth) {
            case 28:
                return DateHelper.days28;
            case 29:
                return DateHelper.days29;
            case 30:
                return DateHelper.days30;
            case 31:
            default:
                return DateHelper.days31;
        }
    }

    private static generateArray(min: number, max: number): Array<Object> {

        return _.range(min, max + 1).map((m: number) => ({ text: _.padStart(`${m}`, 2, '0'), value: m }));

    }

    public static computeSelecteds(value: Date): { year: number, month: number, date: number} {

        let momentValue = null;

        if (value === null || value === undefined) {
            momentValue = moment();
        } else {
            momentValue = moment(value);
        }

        return {
            year: momentValue.year(),
            month: momentValue.month() + 1,
            date:  momentValue.date()
        };
        
    }

    public static onSelect(value: any, picker: { title: string, dataSources: any, selects: any}) {
        if (_.isEmpty(picker.selects)) {
            return;
        }

        let daysInMonth = moment(`${value.year}-${value.month}`, 'YYYY-MM').daysInMonth();

        if ( picker.dataSources.date.lenth !== daysInMonth) {
            picker.dataSources.date = DateHelper.generateDays(daysInMonth);
        }

        if ( picker.selects.date > daysInMonth) {
            picker.selects.date = daysInMonth;
        }

        picker.title = `${value.year}年${value.month}月${value.date}日`;
    }



}