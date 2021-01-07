
export interface AppForLeaveStartOutputDto {
    appAbsenceStartInfoDto: AppAbsenceStartInfoDto;
    applyForLeaveDto: any;
}
export interface TimeZoneUseDto {
    useAtr: number;
    workNo: number;
    startTime: number;
    endTime: number;
}
export interface Overtime60HManagementDto {
    // 60H超休管理区分
    overrest60HManagement: number;

    // 60H超休消化単位
    super60HDigestion: number;
}
export enum HolidayAppType {
    ANNUAL_PAID_LEAVE, // 年次有休
    SUBSTITUTE_HOLIDAY, // 代休
    ABSENCE, // 欠勤
    SPECIAL_HOLIDAY, // 特別休暇
    YEARLY_RESERVE, // 積立年休
    HOLIDAY, // 休日
    DIGESTION_TIME, // 時間消化
    REST_TIME // 振休
}

export enum ApplicationType {
    /**
     * 0: 残業申請
     */
    OVER_TIME_APPLICATION,

    /**
     * 1: 休暇申請
     */
    ABSENCE_APPLICATION,

    /**
     * 2: 勤務変更申請
     */
    WORK_CHANGE_APPLICATION,

    /**
     * 3: 出張申請
     */
    BUSINESS_TRIP_APPLICATION,

    /**
     * 4: 直行直帰申請
     */
    GO_RETURN_DIRECTLY_APPLICATION,

    /**
     * 6: 休出時間申請
     */
    HOLIDAY_WORK_APPLICATION,

    /**
     * 7: 打刻申請
     */
    STAMP_APPLICATION,

    /**
     * 8: 時間休暇申請
     */
    ANNUAL_HOLIDAY_APPLICATION,

    /**
     * 9: 遅刻早退取消申請
     */
    EARLY_LEAVE_CANCEL_APPLICATION,

    /**
     * 10: 振休振出申請
     */
    COMPLEMENT_LEAVE_APPLICATION,

    /**
     * 15: 任意項目申請
     */
    OPTIONAL_ITEM_APPLICATION
}
export interface TargetWorkTypeByApp {
    /**
     * 申請種類
     */
    appType: ApplicationType;

    /**
     * 表示する勤務種類を設定する
     */
    displayWorkType: boolean;

    /**
     * 勤務種類リスト
     */
    workTypeLst: Array<string>;

    /**
     * 振休振出区分
     */
    opBreakOrRestTime?: number;

    /**
     * 休暇種類を利用しない
     */
    opHolidayTypeUse?: boolean;

    /**
     * 休暇申請種類
     */
    opHolidayAppType?: HolidayAppType;

    /**
     * 出張申請勤務種類
     */
    opBusinessTripAppWorkType?: number;
}
export enum ManageDistinct {
    NO, // 管理する
    YES // 管理しない
}
export interface AnualLeaveManagementDto {
    // 時間年休消化単位
    timeAnnualLeave: number;
    
    // 時間年休管理区分
    timeAnnualLeaveManage: number;
    
    // 年休管理区分
    annualLeaveManageDistinct: number;
}
export interface SubstituteLeaveManagementDto {
    // 時間代休消化単位
    timeDigestiveUnit: number;
    
    // 時間代休管理区分
    timeAllowanceManagement: number;
    
    // 紐づけ管理区分
    linkingManagement: number;
    
    // 代休管理区分
    substituteLeaveManagement: number;
}
export interface AccumulatedRestManagementDto {
    // 積休管理区分
    accumulatedManage: number;
}
export interface NursingCareLeaveManagementDto {
    // 子の看護管理区分
    childNursingManagement: number;

    // 時間介護の消化単位
    timeCareDigestive: number;

    // 時間介護の管理区分
    timeCareManagement: number;

    // 時間子の看護の消化単位
    timeChildNursingDigestive: number;

    // 時間子の看護の管理区分
    timeChildNursingManagement: number;

    // 介護管理区分
    longTermCareManagement: number;
}

export interface HolidayManagementDto {
    // 紐づけ管理区分
    linkingManagement: number;
    
    // 振休管理区分
    holidayManagement: number;
}
interface RemainVacationInfoDto {
    // 年休管理
    annualLeaveManagement: AnualLeaveManagementDto;

    // 積休管理
    accumulatedRestManagement: AccumulatedRestManagementDto;

    // 代休管理
    substituteLeaveManagement: SubstituteLeaveManagementDto;

    // 振休管理
    holidayManagement: HolidayManagementDto;

    // 60H超休管理
    overtime60hManagement: Overtime60HManagementDto;

    // 介護看護休暇管理
    nursingCareLeaveManagement: NursingCareLeaveManagementDto;

    // 年休残数
    yearRemain: number;

    // 年休残時間
    yearHourRemain: number;

    // 代休残数
    subHdRemain: number;

    // 振休残数
    subVacaRemain: number;

    // 代休残時間
    subVacaHourRemain: number;

    // 積休残数
    remainingHours: number;

    // 60H超休残時間
    over60HHourRemain: number;

    // 子看護残数
    childNursingRemain: number;

    // 子看護残時間
    childNursingHourRemain: number;

    // 介護残数
    nursingRemain: number;

    // 介護残時間
    nirsingHourRemain: number;
}
export interface VacationAppReflectOptionCommand {

    // 1日休暇の場合は出退勤を削除
    oneDayLeaveDeleteAttendance: number;
    // 出退勤を反映する
    reflectAttendance: number;
    // 就業時間帯を反映する
    reflectWorkHour: number;
}
export interface HolidayApplicationReflectCommand {

    companyId: string;
    // 勤務情報、出退勤を反映する
    workAttendanceReflect: VacationAppReflectOptionCommand;
    // 時間休暇を反映する
    timeLeaveReflect: TimeLeaveAppReflectCondition;

}
export interface TimeLeaveAppReflectCondition {
    // 60H超休
    superHoliday60H: number;

    // 介護
    nursing: number;

    // 子看護
    childNursing: number;

    // 時間代休
    substituteLeaveTime: number;

    // 時間年休
    annualVacationTime: number;

    // 時間特別休暇
    specialVacationTime: number;
}

export interface HolidayAppTypeDispNameDto {
    // 休暇申請種類
    holidayAppType: number;
    // 表示名
    displayName: string;
}
export interface HolidayApplicationSettingDto {
    // 半日年休の使用上限チェック
    halfDayAnnualLeaveUsageLimitCheck: number;
    // 休暇申請種類表示名
    dispNames: Array<HolidayAppTypeDispNameDto>;
}
export enum MaxNumberDayType {
    LIMIT_FIXED_DAY = 1,
    REFER_RELATIONSHIP = 2
}
export interface SpecialHolidayEventDto {
    /* 会社ID */
    companyId: string;

    /* 特別休暇枠NO */
    specialHolidayEventNo: number;

    /* 上限日数の設定方法 */
    maxNumberDay: MaxNumberDayType;

    /* 固定上限日数 */
    fixedDayGrant: number;

    /* 忌引とする */
    makeInvitation: number;

    /* 休日を取得日に含める */
    includeHolidays: number;

    ageLimit: number;

    /* 性別条件 */
    genderRestrict: number;

    /* 雇用条件 */
    restrictEmployment: number;

    /* 分類条件 */
    restrictClassification: number;

    /* 性別 */
    gender: number;

    /* 年齢範囲 */
    ageRange: any;

    /* 年齢基準 */
    ageStandard: number;

    /* 年齢基準 */
    ageStandardBaseDate: number;

    /* メモ */
    memo: string;

    /* 分類一覧 */
    clsList: Array<any>;

    /* 雇用一覧 */
    empList: Array<any>;
}
export interface DateSpecHdRelationOutput {
    //コード
    relationCD: string;
    //続柄名
    relationName: string;
    //上限日数
    maxDate: number;
    /* 3親等以内とする */
    threeParentOrLess: boolean;
}
export interface SpecAbsenceDispInfoDto {
    /**
     * 事象に応じた特休フラグ
     */
    specHdForEventFlag: boolean;

    /**
     * 事象に対する特別休暇
     */
    specHdEvent: SpecialHolidayEventDto;

    /**
     * 特休枠NO
     */
    frameNo: number;

    /**
     * 上限日数
     */
    maxDay: number;

    /**
     * 喪主加算日数
     */
    dayOfRela: number;

    /**
     * 続柄毎の上限日数リスト
     */
    dateSpecHdRelationLst: Array<DateSpecHdRelationOutput>;
}
export interface WorkTypeDto {
    /* 勤務種類コード */
    workTypeCode: string;
    /* 勤務種類名称 */
    name: string;
    /* 勤務種類略名 */
    abbreviationName: string;
    /* 勤務種類記号名 */
    symbolicName: string;
    /* 廃止区分 */
    abolishAtr: number;
    /* 勤務種類備考 */
    memo: string;
    /* 勤務の単位 */
    workAtr: number;
    /* 1日 */
    oneDayCls: number;
    /* 午前 */
    morningCls: number;
    /* 午後 */
    afternoonCls: number;
    /* 出勤率の計算方法 */
    calculatorMethod: number;

    dispOrder: number;

    workTypeSets: Array<any>;
}
export interface AppAbsenceStartInfoDto {
    // 休出代休紐付け管理
    leaveComDayOffManas: Array<any>;

    // 振出振休紐付け管理
    payoutSubofHDManas: Array<any>;

    // 休暇申請の反映
    vacationApplicationReflect: HolidayApplicationReflectCommand;

    // 申請表示情報
    appDispInfoStartupOutput: any;

    // 休暇申請設定
    hdAppSet: HolidayApplicationSettingDto;

    // 申請理由表示
    displayReason: any;

    // 休暇残数情報
    remainVacationInfo: RemainVacationInfoDto;

    // 就業時間帯表示フラグ
    workHoursDisp: boolean;

    // 勤務種類一覧
    workTypeLst: Array<WorkTypeDto>;

    // 勤務時間帯一覧
    workTimeLst: Array<TimeZoneUseDto>;

    // 勤務種類マスタ未登録
    workTypeNotRegister: boolean;

    // 特別休暇表示情報
    specAbsenceDispInfo: SpecAbsenceDispInfoDto;

    // 選択中の勤務種類
    selectedWorkTypeCD: string;

    // 選択中の就業時間帯
    selectedWorkTimeCD: string;

    // 必要休暇時間
    requiredVacationTime: number;

    holidayAppTypeName: Array<any>;
}
export interface StartMobileParam {
    mode: number;
    companyId: string;
    employeeIdOp: string;
    datesOp: Array<string>;
    appAbsenceStartInfoOutputOp: any;
    applyForLeaveOp: any;
    appDispInfoStartupOutput: any;
}
export enum NotUseAtr {
    Not_USE,
    USE
}