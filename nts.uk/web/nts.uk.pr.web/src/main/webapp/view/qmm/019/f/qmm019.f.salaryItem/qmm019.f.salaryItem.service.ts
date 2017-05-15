module qmm019.f.salaryItem.service {
    export module model {
        export class ItemSalaryModel {
            taxAtr: number;
            socialInsAtr: number;
            laborInsAtr: number;
            fixPayAtr: number;
            applyForAllEmpFlg: number;
            applyForMonthlyPayEmp: number;
            applyForDaymonthlyPayEmp: number;
            applyForDaylyPayEmp: number;
            applyForHourlyPayEmp: number;
            avePayAtr: number;
            errRangeLowAtr: number;
            errRangeLow: number;
            errRangeHighAtr: number;
            errRangeHigh: number;
            alRangeLowAtr: number;
            alRangeLow: number;
            alRangeHighAtr: number;
            alRangeHigh: number;
            memo: string;
            limitMnyAtr: number;
            limitMnyRefItemCode: string;
            limitMny: number;
        }
    }
}