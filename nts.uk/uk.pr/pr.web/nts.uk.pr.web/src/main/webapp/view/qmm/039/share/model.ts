module nts.uk.pr.view.qmm039.share.model {
    export module service {
        export module model{

            export interface SearchEmployeeQuery {
                systemType: number;
                code?: string;
                name?: string;
                useClosure?: boolean;
                closureId?: number;
                period?: any;
                referenceDate: any;
            }

            export class EmployeeSearchDto {
                employeeId: string;
                employeeCode: string;
                employeeName: string;
                workplaceCode: string;
                workplaceId: string;
                workplaceName: string;
            }

            export interface GroupOption {

                /** Common properties */
                showEmployeeSelection?: boolean; // 検索タイプ
                systemType: number; // システム区分
                showQuickSearchTab?: boolean; // クイック検索
                showAdvancedSearchTab?: boolean; // 詳細検索
                showBaseDate?: boolean; // 基準日利用
                showClosure?: boolean; // 就業締め日利用
                showAllClosure?: boolean; // 全締め表示
                showPeriod?: boolean; // 対象期間利用
                periodFormatYM?: boolean; // 対象期間精度
                maxPeriodRange?: string; // 最長期間
                showSort?: boolean; // 並び順利用
                nameType?: number; // 氏名の種類

                /** Required parameter */
                baseDate?: any; // 基準日 KnockoutObservable<string> or string
                periodStartDate?: any; // 対象期間開始日 KnockoutObservable<string> or string
                periodEndDate?: any; // 対象期間終了日 KnockoutObservable<string> or string
                dateRangePickerValue?: KnockoutObservable<any>;
                inService: boolean; // 在職区分
                leaveOfAbsence: boolean; // 休職区分
                closed: boolean; // 休業区分
                retirement: boolean; // 退職区分

                /** Quick search tab options */
                showAllReferableEmployee?: boolean; // 参照可能な社員すべて
                showOnlyMe?: boolean; // 自分だけ
                showSameWorkplace?: boolean; // 同じ職場の社員
                showSameWorkplaceAndChild?: boolean; // 同じ職場とその配下の社員

                /** Advanced search properties */
                showEmployment?: boolean; // 雇用条件
                showWorkplace?: boolean; // 職場条件
                showClassification?: boolean; // 分類条件
                showJobTitle?: boolean; // 職位条件
                showWorktype?: boolean; // 勤種条件
                isMutipleCheck?: boolean; // 選択モード
                // showDepartment: boolean; // 部門条件 not covered
                // showDelivery: boolean; not covered

                /** Optional properties */
                isInDialog?: boolean;
                showOnStart?: boolean;
                isTab2Lazy?: boolean;
                tabindex?: number;

                /** Data returned */
                returnDataFromCcg001: (data: Ccg001ReturnedData) => void;
            }

            export interface Ccg001ReturnedData {
                baseDate: string; // 基準日
                closureId?: number; // 締めID
                periodStart: string; // 対象期間（開始)
                periodEnd: string; // 対象期間（終了）
                listEmployee: Array<EmployeeSearchDto>; // 検索結果
            }

            export class SelectedInformation {
                sortOrder: number; // 前回選択していた並び順
                selectedClosureId: number; // 前回選択していた締め
                constructor() {
                    let self = this;
                    self.sortOrder = null;
                    self.selectedClosureId = null;
                }
            }

            export interface EmployeeQueryParam {
                roleId: string;
                baseDate: string;
                referenceRange: number;
                filterByEmployment: boolean;
                employmentCodes: Array<string>;
                filterByDepartment: boolean;
                departmentCodes: Array<number>;
                filterByWorkplace: boolean;
                workplaceCodes: Array<string>;
                filterByClassification: boolean;
                classificationCodes: Array<any>;
                filterByJobTitle: boolean;
                jobTitleCodes: Array<string>;
                filterByWorktype: boolean;
                worktypeCodes: Array<string>;
                filterByClosure: boolean;
                closureIds: Array<number>;

                periodStart: string;
                periodEnd: string;

                includeIncumbents: boolean;
                includeWorkersOnLeave: boolean;
                includeOccupancy: boolean;
                includeRetirees: boolean;
                retireStart: string;
                retireEnd: string;

                sortOrderNo: number;
                nameType: number;
                systemType: number;
            }

            export interface DatePeriodDto {
                startDate: string;
                endDate: string
            }

            export interface BusinessType {
                businessTypeCode: string;
                businessTypeName: string;
            }

            export class EmployeeRangeSelection {
                userId: String; // ユーザID
                companyId: String; // 会社ID
                humanResourceInfo: SelectedInformation; // 人事の選択している情報
                personalInfo: SelectedInformation; // 個人情報の選択している情報
                employmentInfo: SelectedInformation; // 就業の選択している情報
                salaryInfo: SelectedInformation; // 給与の選択している情報
                constructor() {
                    let self = this;
                    self.userId = __viewContext.user.employeeId;
                    self.companyId = __viewContext.user.companyId;
                    self.humanResourceInfo = new SelectedInformation();
                    self.personalInfo = new SelectedInformation();
                    self.employmentInfo = new SelectedInformation();
                    self.salaryInfo = new SelectedInformation();
                }
            }

            export class EmployeeDto {
                employeeID: string;
                employeeCode: string;
                hireDate: string;
                classificationCode: string;
                name: string;
                jobTitleCode: string;
                workplaceCode: string;
                departmentCode: string;
                employmentCode: string;
            }
        }
    }
}