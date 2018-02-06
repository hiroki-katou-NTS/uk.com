module nts.uk.com.view.ccg.share.ccg {
    

    export module service {

        // Service paths.
        var servicePath = {
            searchAllEmployee: "basic/organization/employee/allemployee",
            searchEmployeeByLogin: "basic/organization/employee/onlyemployee",
            searchModeEmployee: "basic/organization/employee/advanced",
            searchOfWorkplace: "basic/organization/employee/ofworkplace",
            searchWorkplaceChild: "basic/organization/employee/workplacechild",
            searchWorkplaceOfEmployee: "basic/organization/employee/workplaceemp",
            getOfSelectedEmployee: "basic/organization/employee/getoffselect",
            searchAllWorkType: "at/share/worktype/findNotDeprecated",
            quickSearchEmployee: "basic/organization/employee/quicksearch",
            getRefRangeBySysType: "ctx/sys/auth/role/getrefrangebysystype",
            findClosureListByCurrentMonth: "ctx/at/shared/workrule/closure/findClosureListByCurrentMonth" //TODO: basedate truyen len hay lay system date?
        }

        /**
         * Find person list
         */
        export function searchAllEmployee(baseDate: Date): JQueryPromise<Array<model.EmployeeSearchDto>> {
            return nts.uk.request.ajax('com', servicePath.searchAllEmployee, baseDate);
        }

        export function getRefRangeBySysType(sysType: number): JQueryPromise<number> {
            return nts.uk.request.ajax('com', servicePath.getRefRangeBySysType + '/' + sysType);
        }

        export function findClosureListByCurrentMonth(): JQueryPromise<Array<any>> {
            return nts.uk.request.ajax('at', servicePath.findClosureListByCurrentMonth);
        }

        /**
         * call service get employee by login
         */
        
        export function searchEmployeeByLogin(baseDate: Date): JQueryPromise<Array<model.EmployeeSearchDto>> {
            return nts.uk.request.ajax('com', servicePath.searchEmployeeByLogin, baseDate);
        }

        /**
         *search employee of work place 
         */
         
        export function searchOfWorkplace(baseDate: Date): JQueryPromise<model.EmployeeSearchDto[]> {
            return nts.uk.request.ajax('com', servicePath.searchOfWorkplace, baseDate);
        }
        /**
         * search employee of work place child
         */
        export function searchWorkplaceChild(baseDate: Date): JQueryPromise<model.EmployeeSearchDto[]> {
            return nts.uk.request.ajax('com', servicePath.searchWorkplaceChild, baseDate);
        }

        /**
         * search data mode employee
         */
        export function searchModeEmployee(input: model.EmployeeSearchInDto)
            : JQueryPromise<model.EmployeeSearchDto[]> {
            return nts.uk.request.ajax('com', servicePath.searchModeEmployee, input);
        }

        /**
         * search data of employee
         */
        export function searchWorkplaceOfEmployee(baseDate: Date): JQueryPromise<string[]> {
            return nts.uk.request.ajax('com', servicePath.searchWorkplaceOfEmployee, baseDate);
        }
        
        /**
         * search data of employee
         */
        export function getOfSelectedEmployee(baseDate: Date, employeeIds: string[]): JQueryPromise<model.EmployeeSearchDto[]> {
            return nts.uk.request.ajax('com', servicePath.getOfSelectedEmployee, { baseDate: baseDate, employeeIds: employeeIds });
        }
        
        
        export function searchAllWorkType(): JQueryPromise<any> {
            return nts.uk.request.ajax('at', servicePath.searchAllWorkType);
        }
        
        export function quickSearchEmployee(baseDate: Date): JQueryPromise<any> {
            return nts.uk.request.ajax('com', servicePath.searchAllEmployee, baseDate);
        }
        
        export module model{

            export class EmployeeSearchDto {
                employeeId: string;

                employeeCode: string;

                employeeName: string;

                workplaceCode: string;

                workplaceId: string;

                workplaceName: string;
            }

            export class EmployeeSearchInDto {
                baseDate: Date;
                employmentCodes: string[];
                classificationCodes: string[];
                jobTitleCodes: string[];
                workplaceCodes: string[];
            }

            export interface GroupOption {

                /** Common properties */
                isSelectAllEmployee: boolean; // 検索タイプ
                systemType: number; // システム区分
                isQuickSearchTab: boolean; // クイック検索
                isAdvancedSearchTab: boolean; // 詳細検索
                showBaseDate: boolean; // 基準日利用
                showClosure: boolean; // 就業締め日利用
                showAllClosure: boolean; // 全締め表示
                showPeriod: boolean; // 対象期間利用
                periodAccuracy: number; // 対象期間精度

                /** Required parameter */
                baseDate?: KnockoutObservable<Date>; // 基準日
                periodStartDate?: KnockoutObservable<any>; // 対象期間開始日
                periodEndDate?: KnockoutObservable<any>; // 対象期間終了日
                inService: boolean; // 在職区分
                leaveOfAbsence: boolean; // 休職区分
                closed: boolean; // 休業区分
                retirement: boolean; // 退職区分

                /** Quick search tab options */
                isAllReferableEmployee: boolean; // 参照可能な社員すべて
                isOnlyMe: boolean; // 自分だけ
                isEmployeeOfWorkplace: boolean; // 同じ職場の社員
                isEmployeeWorkplaceFollow: boolean; // 同じ職場とその配下の社員

                /** Advanced search properties */
                showEmployment: boolean; // 雇用条件
                showWorkplace: boolean; // 職場条件
                showClassification: boolean; // 分類条件
                showJobTitle: boolean; // 職位条件
                showWorktype: boolean; // 勤種条件
                isMutipleCheck: boolean; // 選択モード
                // showDepartment: boolean; // 部門条件 not covered
                // showDelivery: boolean; not covered

                /** Data returned */
                onSearchAllClicked: (data: EmployeeSearchDto[]) => void;

                onSearchOnlyClicked: (data: EmployeeSearchDto) => void;
                
                onSearchOfWorkplaceClicked: (data: EmployeeSearchDto[]) => void;
                
                onSearchWorkplaceChildClicked: (data: EmployeeSearchDto[]) => void;
                
                onApplyEmployee: (data: EmployeeSearchDto[]) => void;
            }
    
        }
    }
}