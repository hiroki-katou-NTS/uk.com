
module nts.uk.at.view.kdl017.a.service {
  var paths: any = {
    getEmployee : 'at/request/dialog/employmentsystem/getEmployee',
    get60hOvertimeDisplayInfoDetail : 'at/record/dialog/sixtyhour/get60hOvertimeDisplayInfoDetail/{0}/{1}',
  }

  export function getEmployee(param: EmployeeParam): JQueryPromise<Array<EmployeeBasicInfoDto>> {
    return nts.uk.request.ajax(paths.getEmployee, param);
  }

  /**
   * アルゴリズム「60H超休の表示」を実行する.
   * 
   * @param employeeId 社員ID
   * @param baseDate the base date
   */
  export function get60hOvertimeDisplayInfoDetail(employeeId: string, baseDate: string)
                                  : JQueryPromise<SixtyHourHolidayDto> {
    const path = nts.uk.text.format(paths.get60hOvertimeDisplayInfoDetail, employeeId, baseDate);
    return nts.uk.request.ajax(path);
  }

  export interface EmployeeBasicInfoDto {
    personId: string,
    employeeId: string,
    businessName: string,
    gender: number,
    birthday: string,
    employeeCode: string,
    jobEntryDate: string,
    retirementDate: string
  }

  export interface EmployeeParam {
    employeeIds: Array<string>,
    baseDate: string
  }

  // 60超過時間表示情報詳細
  export interface SixtyHourHolidayDto {
    /** 60H超休管理区分 */
    departmentOvertime60H: boolean;

    /** 残数情報 */
    remainHourDetailDtos: RemainHourDetailDto[];

    /** 繰越数 */
    carryoverNumber: number;

    /** 使用数 */
    usageNumber: number;

    /** 締め期間 */
    startPeriod: string;
    endPeriod: string;

    /** 残数 */
    residual: number;
  }

  // 残数詳細
  export interface RemainHourDetailDto {
    /** 発生月 */
    occurrenceMonth: number;

    /** 発生時間 */
    occurrenceTime: number;

    /** 期限日 */
    deadline: string;

    usageDateDtos: UsageDateDto[];
  }

  export class UsageDateDto {
    /** 使用日 */
    usageDate: string;

    /** 使用時間 */
    usageTime: number;

    /** 作成区分 */
    creationCategory: number;
  }

  // 紐付け管理
  export interface PegManagementDto {
    /** 発生月 */
    occurrenceMonth: number;
      
    /** 使用日 */
    usageDate: string;

    /** 使用数 */
    usageNumber: number;
  }
}