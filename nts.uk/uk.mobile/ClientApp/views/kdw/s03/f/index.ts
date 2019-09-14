import { Vue, _ } from '@app/provider';
import { component } from '@app/core/component';
import { storage } from '@app/utils';

@component({
    name: 'kdws03f',
    route: '/kdw/s03/f',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KdwS03FComponent extends Vue {
    public empName: string = '';
    public date: number;

    public monthData: Array<IMonthData> = [];
    
    public created() {
        let self = this;
        let cache: any = storage.local.getItem('dailyCorrectionState');
        self.date = cache.timePeriodAllInfo.yearMonth;
        self.empName = (_.find(cache.lstEmployee, (emp) => emp.id == cache.selectedEmployee) || { businessName: '' }).businessName;
        let param = {
            employeeId: cache.selectedEmployee,
            formatCode: cache.autBussCode,
            yearMonth: cache.timePeriodAllInfo.yearMonth,
            closureId: cache.timePeriodAllInfo.closureId,
            closureDate: new Date(cache.timePeriodAllInfo.targetRange.endDate)
        };
        self.$http.post('at', servicePath.getMonthlyPer, param).then((result: any) => {
            console.log(result);
            _.each(result.data, (item) => {
                self.monthData.push({
                    id: item.itemId,
                    name: item.name,
                    value: self.valueDis(item.value, item.type)
                });
            });
        });
    }
    public valueDis(value: string, type: number) {
        if (value == null) {return '';}
        if (type == ValueType.CLOCK || type == ValueType.TIME) {
            return this.$dt.timedr(Number(value));
        }
        if (type == ValueType.DATE) {
            return this.$dt.date(new Date(value));
        }

        return value;
    }
}
const servicePath = {
    getMonthlyPer: 'screen/at/dailyperformance/monthly-perfomance'
};
interface IMonthData {
    id: string;
    name: string;
    value: any;
}

enum ValueType {
    TIME = 1,// "TIME", "時間"
    CLOCK = 2,//"CLOCK", "時刻"
    CODE = 3,//"CODE", "コード"
    TEXT = 4,//"TEXT", "文字"
    DATE = 5,//"DATE", "年月日"
    RATE = 6,//"DOUBLE", "率"
    COUNT = 7,//"COUNT", "回数"
    COUNT_WITH_DECIMAL = 8,//"COUNT", "回数"
    FLAG = 9,//"FLAG", "フラグ"
    ATTR = 10,//"ATTR", "区分"
    UNKNOWN = 11,// "UNKNOWN", "UNKNOWN"
    DAYS = 12,//"DAYS", "日数"
    AMOUNT = 13,//"AMOUNT", "金額"
    NUMBER = 14,//"NUMBER", "数"
    TIME_WITH_DAY = 15// "TIME_WITH_DAY", "時刻（日区分付き）"
}