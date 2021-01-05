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
interface RemainVacationInfoDto {
    // 年休管理
    annualLeaveManagement: any;

    // 積休管理
    accumulatedRestManagement: any;

    // 代休管理
    substituteLeaveManagement: any;

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
    hdAppSet: any;

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