import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';

@component({
    name: 'kafs00subp2',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KafS00SubP2Component extends Vue {
    @Prop({ default: () => ({}) })
    public params: OverTimeWorkHoursDto;

    get currentMonthTime36() {
        const vm = this;
        if (vm.params.isCurrentMonth) {
            return vm.params.currentTimeMonth.legalMaxTime.threshold.erAlTime.error;
        } else {
            return vm.params.currentTimeMonth.agreementTime.threshold.erAlTime.error;
        }
    }

    get currentMonthActual() {
        const vm = this;
        if (vm.params.isCurrentMonth) {
            return vm.params.currentTimeMonth.legalMaxTime.agreementTime;
        } else {
            return vm.params.currentTimeMonth.agreementTime.agreementTime;
        }
    }

    get nextMonthTime36() {
        const vm = this;
        if (vm.params.isCurrentMonth) {
            return vm.params.nextTimeMonth.legalMaxTime.threshold.erAlTime.error;
        } else {
            return vm.params.nextTimeMonth.agreementTime.threshold.erAlTime.error;
        }
    }

    get nextMonthActual() {
        const vm = this;
        if (vm.params.isCurrentMonth) {
            return vm.params.nextTimeMonth.legalMaxTime.agreementTime;
        } else {
            return vm.params.nextTimeMonth.agreementTime.agreementTime;
        }
    }
}

export interface OverTimeWorkHoursDto {
    // 当月のありなし
    isCurrentMonth: boolean;
    // 当月の時間外時間
    currentTimeMonth: AgreementTimeOfManagePeriodDto;
    // 当月の年月
    currentMonth: string;
    // 翌月のありなし
    isNextMonth: boolean;
    // 翌月の時間外時間
    nextTimeMonth: AgreementTimeOfManagePeriodDto;
    // 翌月の年月
    nextMonth: string;
}

interface AgreementTimeOfManagePeriodDto {
    // 36協定対象時間
    agreementTime: AgreementTimeOfMonthlyDto;
    // 社員ID
    sid: string;
    // 状態
    status: number;
    // 内訳
    agreementTimeBreakDown: AgreementTimeBreakdownDto;
    // 年月
    yearMonth: string;
    // 法定上限対象時間
    legalMaxTime: AgreementTimeOfMonthlyDto;
}

interface AgreementTimeOfMonthlyDto {
    /** 対象時間 */
    agreementTime: number;
    /** 閾値 */
    threshold: OneMonthTimeDto;
}

interface OneMonthTimeDto {
    /** エラーアラーム時間 */
    erAlTime: OneMonthErrorAlarmTimeDto;
    /** 上限時間 */
    upperLimit: number;
}

interface OneMonthErrorAlarmTimeDto {
    /** エラー時間 */
    error: number;
    /** アラーム時間 */ 
    alarm: number;
}

interface AgreementTimeBreakdownDto {
    /** 残業時間 */
    overTime: number;
    /** 振替残業時間 */
    transferOverTime: number;
    /** 法定内休出時間 */
    legalHolidayWorkTime: number;
    /** 法定内振替時間 */
    legalTransferTime: number;
    /** 法定外休出時間 */
    illegalHolidayWorkTime: number;
    /** 法定外振替時間 */
    illegaltransferTime: number;
    /** 法定内フレックス超過時間 */
    flexLegalTime: number;
    /** 法定外フレックス超過時間 */
    flexIllegalTime: number;
    /** 所定内割増時間 */
    withinPrescribedPremiumTime: number;
    /** 週割増合計時間 */
    weeklyPremiumTime: number;
    /** 月間割増合計時間 */
    monthlyPremiumTime: number;
    /** 臨時時間 */
    temporaryTime: number;
}