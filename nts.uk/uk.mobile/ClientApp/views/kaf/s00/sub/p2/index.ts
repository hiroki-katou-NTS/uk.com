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
    agreementTime: number;
    threshold: OneMonthTimeDto;
}

interface OneMonthTimeDto {
    erAlTime: OneMonthErrorAlarmTimeDto;
    upperLimit: number;
}

interface OneMonthErrorAlarmTimeDto {
    error: number;
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