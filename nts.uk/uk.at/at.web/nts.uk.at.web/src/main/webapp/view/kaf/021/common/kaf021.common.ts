/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kaf021.common {
    import parseTime = nts.uk.time.parseTime;

    export class CellState {
        rowId: string;
        columnKey: string;
        state: Array<any>
        constructor(rowId: string, columnKey: string, state: Array<any>) {
            this.rowId = rowId;
            this.columnKey = columnKey;
            this.state = state;
        }
    }

    export class AppType {
        value: AppTypeEnum;
        name: string;

        constructor(value: AppTypeEnum, name: string) {
            this.value = value;
            this.name = name;
        }
    }

    export enum AppTypeEnum {
        CURRENT_MONTH = 1,
        NEXT_MONTH = 2,
        YEARLY = 3
    }

    export class EmployeeAgreementTime {
        employeeId: string;
        checked: boolean;
        status: string;
        wkpName: string;
        employeeName: string;

        month1Str: any;
        month1Time: any;
        month1MaxTime: any;
        month1TimeStr: any;
        month1MaxTimeStr: any;
        month1Status: AgreementTimeStatusOfMonthly;
        month1Error: any;
        month1Alarm: any;

        month2Str: any;
        month2Time: any;
        month2MaxTime: any;
        month2TimeStr: any;
        month2MaxTimeStr: any;
        month2Status: AgreementTimeStatusOfMonthly;
        month2Error: any;
        month2Alarm: any;

        month3Str: any;
        month3Time: any;
        month3MaxTime: any;
        month3TimeStr: any;
        month3MaxTimeStr: any;
        month3Status: AgreementTimeStatusOfMonthly;
        month3Error: any;
        month3Alarm: any;

        month4Str: any;
        month4Time: any;
        month4MaxTime: any;
        month4TimeStr: any;
        month4MaxTimeStr: any;
        month4Status: AgreementTimeStatusOfMonthly;
        month4Error: any;
        month4Alarm: any;

        month5Str: any;
        month5Time: any;
        month5MaxTime: any;
        month5TimeStr: any;
        month5MaxTimeStr: any;
        month5Status: AgreementTimeStatusOfMonthly;
        month5Error: any;
        month5Alarm: any;

        month6Str: any;
        month6Time: any;
        month6MaxTime: any;
        month6TimeStr: any;
        month6MaxTimeStr: any;
        month6Status: AgreementTimeStatusOfMonthly;
        month6Error: any;
        month6Alarm: any;

        month7Str: any;
        month7Time: any;
        month7MaxTime: any;
        month7TimeStr: any;
        month7MaxTimeStr: any;
        month7Status: AgreementTimeStatusOfMonthly;
        month7Error: any;
        month7Alarm: any;

        month8Str: any;
        month8Time: any;
        month8MaxTime: any;
        month8TimeStr: any;
        month8MaxTimeStr: any;
        month8Status: AgreementTimeStatusOfMonthly;
        month8Error: any;
        month8Alarm: any;

        month9Str: any;
        month9Time: any;
        month9MaxTime: any;
        month9TimeStr: any;
        month9MaxTimeStr: any;
        month9Status: AgreementTimeStatusOfMonthly;
        month9Error: any;
        month9Alarm: any;

        month10Str: any;
        month10Time: any;
        month10MaxTime: any;
        month10TimeStr: any;
        month10MaxTimeStr: any;
        month10Status: AgreementTimeStatusOfMonthly;
        month10Error: any;
        month10Alarm: any;

        month11Str: any;
        month11Time: any;
        month11MaxTime: any;
        month11TimeStr: any;
        month11MaxTimeStr: any;
        month11Status: AgreementTimeStatusOfMonthly;
        month11Error: any;
        month11Alarm: any;

        month12Str: any;
        month12Time: any;
        month12MaxTime: any;
        month12TimeStr: any;
        month12MaxTimeStr: any;
        month12Status: AgreementTimeStatusOfMonthly;
        month12Error: any;
        month12Alarm: any;

        yearStr: any;
        yearTime: any;
        yearMaxTime: any;
        yearTimeStr: any;
        yearMaxTimeStr: any;
        yearStatus: AgreTimeYearStatusOfMonthly
        yearError: any;
        yearAlarm: any;

        monthAverage2: any;
        monthAverage2Str: any;
        monthAverage2Status: AgreMaxTimeStatusOfMonthly

        monthAverage3: any;
        monthAverage3Str: any;
        monthAverage3Status: AgreMaxTimeStatusOfMonthly

        monthAverage4: any;
        monthAverage4Str: any;
        monthAverage4Status: AgreMaxTimeStatusOfMonthly

        monthAverage5: any;
        monthAverage5Str: any;
        monthAverage5Status: AgreMaxTimeStatusOfMonthly

        monthAverage6: any;
        monthAverage6Str: any;
        monthAverage6Status: AgreMaxTimeStatusOfMonthly

        exceededNumber: number;

        constructor(data: IEmployeeAgreementTime) {
            this.employeeId = data.employeeId;
            this.checked = false;
            this.status = "status";
            this.wkpName = data.affiliationName;
            this.employeeName = data.employeeCode + "　" + data.employeeName;

            this.month1Time = data.month1?.time;
            this.month1MaxTime = data.month1?.maxTime;
            this.month1TimeStr = parseTime(this.month1Time, true).format();
            this.month1MaxTimeStr = parseTime(this.month1MaxTime, true).format();
            this.month1Str = EmployeeAgreementTime.getCellTime(this.month1Time, this.month1MaxTime);
            this.month1Status = data.month1?.status;
            this.month1Error = data.month1?.error;
            this.month1Alarm = data.month1?.alarm;

            this.month2Time = data.month2?.time;
            this.month2MaxTime = data.month2?.maxTime;
            this.month2TimeStr = parseTime(this.month2Time, true).format();
            this.month2MaxTimeStr = parseTime(this.month2MaxTime, true).format();
            this.month2Str = EmployeeAgreementTime.getCellTime(this.month2Time, this.month2MaxTime);
            this.month2Status = data.month2?.status;
            this.month2Error = data.month2?.error;
            this.month2Alarm = data.month2?.alarm;

            this.month3Time = data.month3?.time;
            this.month3MaxTime = data.month3?.maxTime;
            this.month3TimeStr = parseTime(this.month3Time, true).format();
            this.month3MaxTimeStr = parseTime(this.month3MaxTime, true).format();
            this.month3Str = EmployeeAgreementTime.getCellTime(this.month3Time, this.month3MaxTime);
            this.month3Status = data.month3?.status;
            this.month3Error = data.month3?.error;
            this.month3Alarm = data.month3?.alarm;

            this.month4Time = data.month4?.time;
            this.month4MaxTime = data.month4?.maxTime;
            this.month4TimeStr = parseTime(this.month4Time, true).format();
            this.month4MaxTimeStr = parseTime(this.month4MaxTime, true).format();
            this.month4Str = EmployeeAgreementTime.getCellTime(this.month4Time, this.month4MaxTime);
            this.month4Status = data.month4?.status;
            this.month4Error = data.month4?.error;
            this.month4Alarm = data.month4?.alarm;

            this.month5Time = data.month5?.time;
            this.month5MaxTime = data.month5?.maxTime;
            this.month5TimeStr = parseTime(this.month1Time, true).format();
            this.month5MaxTimeStr = parseTime(this.month1MaxTime, true).format();
            this.month5Str = EmployeeAgreementTime.getCellTime(this.month5Time, this.month5MaxTime);
            this.month5Status = data.month5?.status;
            this.month5Error = data.month5?.error;
            this.month5Alarm = data.month5?.alarm;

            this.month6Time = data.month6?.time;
            this.month6MaxTime = data.month6?.maxTime;
            this.month6TimeStr = parseTime(this.month6Time, true).format();
            this.month6MaxTimeStr = parseTime(this.month6MaxTime, true).format();
            this.month6Str = EmployeeAgreementTime.getCellTime(this.month6Time, this.month6MaxTime);
            this.month6Status = data.month6?.status;
            this.month6Error = data.month6?.error;
            this.month6Alarm = data.month6?.alarm;

            this.month7Time = data.month7?.time;
            this.month7MaxTime = data.month7?.maxTime;
            this.month7TimeStr = parseTime(this.month7Time, true).format();
            this.month7MaxTimeStr = parseTime(this.month7MaxTime, true).format();
            this.month7Str = EmployeeAgreementTime.getCellTime(this.month7Time, this.month7MaxTime);
            this.month7Status = data.month7?.status;
            this.month7Error = data.month7?.error;
            this.month7Alarm = data.month7?.alarm;

            this.month8Time = data.month8?.time;
            this.month8MaxTime = data.month8?.maxTime;
            this.month8TimeStr = parseTime(this.month8Time, true).format();
            this.month8MaxTimeStr = parseTime(this.month8MaxTime, true).format();
            this.month8Str = EmployeeAgreementTime.getCellTime(this.month8Time, this.month8MaxTime);
            this.month8Status = data.month8?.status;
            this.month8Error = data.month8?.error;
            this.month8Alarm = data.month8?.alarm;

            this.month9Time = data.month9?.time;
            this.month9MaxTime = data.month9?.maxTime;
            this.month9TimeStr = parseTime(this.month9Time, true).format();
            this.month9MaxTimeStr = parseTime(this.month9MaxTime, true).format();
            this.month9Str = EmployeeAgreementTime.getCellTime(this.month9Time, this.month9MaxTime);
            this.month9Status = data.month9?.status;
            this.month9Error = data.month9?.error;
            this.month9Alarm = data.month9?.alarm;

            this.month10Time = data.month10?.time;
            this.month10MaxTime = data.month10?.maxTime;
            this.month10TimeStr = parseTime(this.month10Time, true).format();
            this.month10MaxTimeStr = parseTime(this.month10MaxTime, true).format();
            this.month10Str = EmployeeAgreementTime.getCellTime(this.month10Time, this.month10MaxTime);
            this.month10Status = data.month10?.status;
            this.month10Error = data.month10?.error;
            this.month10Alarm = data.month10?.alarm;

            this.month11Time = data.month11?.time;
            this.month11MaxTime = data.month11?.maxTime;
            this.month11TimeStr = parseTime(this.month11Time, true).format();
            this.month11MaxTimeStr = parseTime(this.month11MaxTime, true).format();
            this.month11Str = EmployeeAgreementTime.getCellTime(this.month11Time, this.month11MaxTime);
            this.month11Status = data.month11?.status;
            this.month11Error = data.month11?.error;
            this.month11Alarm = data.month11?.alarm;

            this.month12Time = data.month12?.time;
            this.month12MaxTime = data.month12?.maxTime;
            this.month12TimeStr = parseTime(this.month12Time, true).format();
            this.month12MaxTimeStr = parseTime(this.month12MaxTime, true).format();
            this.month12Str = EmployeeAgreementTime.getCellTime(this.month12Time, this.month12MaxTime);
            this.month12Status = data.month12?.status;
            this.month12Error = data.month12?.error;
            this.month12Alarm = data.month12?.alarm;

            this.yearTime = data.year?.time;
            this.yearMaxTime = data.year?.limitTime;
            this.yearTimeStr = parseTime(this.yearTime, true).format();
            this.yearMaxTimeStr = parseTime(this.yearMaxTime, true).format();
            this.yearStr = EmployeeAgreementTime.getCellTime(this.yearTime, this.yearMaxTime);
            this.yearStatus = data.year?.status;
            this.yearError = data.year?.error;
            this.yearAlarm = data.year?.alarm;

            this.monthAverage2 = data.monthAverage2?.time;
            this.monthAverage2Str = parseTime(this.monthAverage2, true).format();
            this.monthAverage3Status = data.monthAverage3?.status;

            this.monthAverage3 = data.monthAverage3?.time;
            this.monthAverage3Str = parseTime(this.monthAverage3, true).format();
            this.monthAverage3Status = data.monthAverage3?.status;

            this.monthAverage4 = data.monthAverage4?.time;
            this.monthAverage4Str = parseTime(this.monthAverage4, true).format();
            this.monthAverage4Status = data.monthAverage4?.status;

            this.monthAverage5 = data.monthAverage5?.time;
            this.monthAverage5Str = parseTime(this.monthAverage5, true).format();
            this.monthAverage5Status = data.monthAverage5?.status;

            this.monthAverage6 = data.monthAverage6?.time;
            this.monthAverage6Str = parseTime(this.monthAverage6, true).format();
            this.monthAverage6Status = data.monthAverage6?.status;

            this.exceededNumber = data.exceededNumber;
        }

        static fromApp(data: Array<IEmployeeAgreementTime>): Array<EmployeeAgreementTime> {
            return _.map(data, (item: IEmployeeAgreementTime) => { return new EmployeeAgreementTime(item) })
        }

        static getCellTime(time?: number, maxTime?: number) {
            let result = parseTime(time, true).format();
            if (maxTime != null) {
                result += "<br>(" + parseTime(maxTime, true).format() + ")";
            }
            return result;
        }
    }

    export interface IEmployeeAgreementTime {
        /**
         * 社員ID
         */
        employeeId: string;
        /**
         * 社員コード
         */
        employeeCode: string;
        /**
         * 社員名
         */
        employeeName: string;
        /**
         * 所属CD
         */
        affiliationCode: string;
        /**
         * 所属ID
         */
        affiliationId: string;
        /**
         * 所属名称
         */
        affiliationName: string;
        /**
         * 1月度
         */
        month1: IAgreementTimeMonth;
        /**
         * 2月度
         */
        month2: IAgreementTimeMonth;
        /**
         * 3月度
         */
        month3: IAgreementTimeMonth;
        /**
         * 4月度
         */
        month4: IAgreementTimeMonth;
        /**
         * 5月度
         */
        month5: IAgreementTimeMonth;
        /**
         * 6月度
         */
        month6: IAgreementTimeMonth;
        /**
         * 7月度
         */
        month7: IAgreementTimeMonth;
        /**
         * 8月度
         */
        month8: IAgreementTimeMonth;
        /**
         * 9月度
         */
        month9: IAgreementTimeMonth;
        /**
         * 10月度
         */
        month10: IAgreementTimeMonth;
        /**
         * 11月度
         */
        month11: IAgreementTimeMonth;
        /**
         * 12月度
         */
        month12: IAgreementTimeMonth;
        /**
         * 年間
         */
        year: IAgreementTimeYear;
        /**
         * 直近2ヵ月平均
         */
        monthAverage2: IAgreementMaxAverageTime;
        /**
         * 直近3ヵ月平均
         */
        monthAverage3: IAgreementMaxAverageTime;
        /**
         * 直近4ヵ月平均
         */
        monthAverage4: IAgreementMaxAverageTime;
        /**
         * 直近5ヵ月平均
         */
        monthAverage5: IAgreementMaxAverageTime;
        /**
         * 直近6ヵ月平均
         */
        monthAverage6: IAgreementMaxAverageTime;
        /**
         * 36協定超過情報.超過回数
         */
        exceededNumber: number;
    }

    interface IAgreementTimeMonth {
        /**
         * 月度
         */
        yearMonth: number;
        /**
         * 36協定時間
         */
        time: number;
        /**
         * 上限時間
         */
        maxTime: number;
        /**
         * 状態
         */
        status: AgreementTimeStatusOfMonthly;
        /**
         * エラー時間
         */
        error: number;
        /**
         * アラーム時間
         */
        alarm: number;
    }

    interface IAgreementTime {
        
    }

    /**
     * 月別実績の36協定時間状態
     */
    export enum AgreementTimeStatusOfMonthly {
        /** 正常 */
        NORMAL = 0,
        /** 限度エラー時間超過 */
        EXCESS_LIMIT_ERROR = 1,
        /** 限度アラーム時間超過 */
        EXCESS_LIMIT_ALARM = 2,
        /** 特例限度エラー時間超過 */
        EXCESS_EXCEPTION_LIMIT_ERROR = 3,
        /** 特例限度アラーム時間超過 */
        EXCESS_EXCEPTION_LIMIT_ALARM = 4,
        /** 正常（特例あり） */
        NORMAL_SPECIAL = 5,
        /** 限度エラー時間超過（特例あり） */
        EXCESS_LIMIT_ERROR_SP = 6,
        /** 限度アラーム時間超過（特例あり） */
        EXCESS_LIMIT_ALARM_SP = 7,
        /** tính Tổng hiệp định 36） */
        EXCESS_BG_GRAY = 8
    }

    interface IAgreementTimeYear {
        /**
         * 限度時間
         */
        limitTime: number;
        /**
         * 実績時間
         */
        time: number;
        /**
         * 状態
         */
        status: AgreTimeYearStatusOfMonthly;
        /**
         * エラー時間
         */
        error: number;
        /**
         * アラーム時間
         */
        alarm: number;
    }

    export enum AgreTimeYearStatusOfMonthly {
        /** 正常 */
        NORMAL = 0,
        /** 限度超過 */
        EXCESS_LIMIT = 1
    }

    interface IAgreementMaxAverageTime {
        /**
        * 合計時間
        */
        totalTime: number;
        /**
         * 平均時間
         */
        time: number;
        /**
         * 状態
         */
        status: AgreMaxTimeStatusOfMonthly;
    }

    /**
     * 月別実績の36協定上限時間状態
     */
    export enum AgreMaxTimeStatusOfMonthly {
        /** 正常 */
        NORMAL = 0,
        /** 上限時間超過 */
        EXCESS_MAXTIME = 1
    }
}
