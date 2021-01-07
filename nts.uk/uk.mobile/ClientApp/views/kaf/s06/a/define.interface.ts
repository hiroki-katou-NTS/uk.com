
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
interface RemainVacationInfoDto {
    // 年休管理
    annualLeaveManagement: AnualLeaveManagementDto;

    // 積休管理
    accumulatedRestManagement: any;

    // 代休管理
    substituteLeaveManagement: SubstituteLeaveManagementDto;

    // 振休管理
    holidayManagement: any;

    // 60H超休管理
    overtime60hManagement: Overtime60HManagementDto;

    // 介護看護休暇管理
    nursingCareLeaveManagement: any;

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
    workTypeLst: Array<any>;

    // 勤務時間帯一覧
    workTimeLst: Array<TimeZoneUseDto>;

    // 勤務種類マスタ未登録
    workTypeNotRegister: boolean;

    // 特別休暇表示情報
    specAbsenceDispInfo: any;

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