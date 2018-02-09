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
            getEmploymentCodeByClosureId: "ctx/at/shared/workrule/closure/findEmpByClosureId",
            searchEmployee: "query/employee/findAll",
            getRefRangeBySysType: "ctx/sys/auth/role/getrefrangebysystype",
            getClosuresByBaseDate: "ctx/at/shared/workrule/closure/getclosuresbybasedate",
            calculatePeriod: "ctx/at/shared/workrule/closure/calculateperiod",
            getClosureTiedByEmployment: "ctx/at/shared/workrule/closure/getclosuretiedbyemployment",
            getCurrentHistoryItem: "bs/employee/employment/history/getcurrenthistoryitem",
            getPersonalRoleFuturePermit: "ctx/sys/auth/grant/roleindividual/get/futurerefpermit",
            getEmploymentRoleFuturePermit: "at/auth/workplace/employmentrole/get/futurerefpermit",
            getListWorkplaceId: "ctx/sys/auth/role/getListWorkplaceId",
            findRegulationInfoEmployee: "query/employee/find",
        }

        /**
         * Find person list
         */
        export function searchAllEmployee(baseDate: Date): JQueryPromise<Array<model.EmployeeSearchDto>> {
            return nts.uk.request.ajax('com', servicePath.searchAllEmployee, baseDate);
        }

        export function findRegulationInfoEmployee(query: model.EmployeeQueryParam): JQueryPromise<Array<model.EmployeeSearchDto>> {
            return nts.uk.request.ajax('com', servicePath.findRegulationInfoEmployee, query);
        }

        export function getPersonalRoleFuturePermit(): JQueryPromise<boolean> {
            return nts.uk.request.ajax('com', servicePath.getPersonalRoleFuturePermit);
        }

        export function getEmploymentRoleFuturePermit(): JQueryPromise<boolean> {
            return nts.uk.request.ajax('at', servicePath.getEmploymentRoleFuturePermit);
        }

        export function getCurrentHistoryItem(): JQueryPromise<any> {
            return nts.uk.request.ajax('com', servicePath.getCurrentHistoryItem);
        }

        /**
         * Get Reference Range By System Type
         */
        export function getRefRangeBySysType(sysType: number): JQueryPromise<number> {
            let dfd = $.Deferred<number>();
            dfd.resolve(1);
            return dfd.promise();
            //return nts.uk.request.ajax('com', servicePath.getRefRangeBySysType + '/' + sysType);
        }

        export function getClosuresByBaseDate(baseDate: string): JQueryPromise<Array<any>> {
            return nts.uk.request.ajax('at', servicePath.getClosuresByBaseDate + '/' + baseDate);
        }
        
        /**
         * Get Employment Code By ClosureId
         */
        export function getEmploymentCodeByClosureId(closureId: number): JQueryPromise<Array<string>> {
            return nts.uk.request.ajax('at', servicePath.getEmploymentCodeByClosureId + '/' + closureId);
        }

        export function getClosureTiedByEmployment(employmentCd: string): JQueryPromise<number> {
            return nts.uk.request.ajax('at', servicePath.getClosureTiedByEmployment + '/' + employmentCd);
        }

        export function getEmployeeRangeSelection(key: any): JQueryPromise<model.EmployeeRangeSelection> {
            return nts.uk.characteristics.restore(key);
        }

        export function saveEmployeeRangeSelection(data: model.EmployeeRangeSelection): JQueryPromise<void> {
            const key = data.userId + '' + data.companyId;
            return nts.uk.characteristics.save(key, data);
        }

        export function calculatePeriod(closureId: number, yearMonth: number): JQueryPromise<Array<any>> {
            const param = '/' + closureId + '/' + yearMonth;
            return nts.uk.request.ajax('at', servicePath.calculatePeriod + param);
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
         * search WorkPlace of Employee
         */
        export function searchWorkplaceOfEmployee(baseDate: Date): JQueryPromise<string[]> {
            return nts.uk.request.ajax('com', servicePath.searchWorkplaceOfEmployee, baseDate);
        }
        
        /**
         * get of selected Employee
         */
        export function getOfSelectedEmployee(baseDate: Date, employeeIds: string[]): JQueryPromise<model.EmployeeSearchDto[]> {
            return nts.uk.request.ajax('com', servicePath.getOfSelectedEmployee, { baseDate: baseDate, employeeIds: employeeIds });
        }
        
        /**
         * search all worktype
         */
        export function searchAllWorkType(): JQueryPromise<string[]> {
            return nts.uk.request.ajax('at', servicePath.searchAllWorkType);
        }
        
        /**
         * search employee
         */
        export function searchEmployee(param: model.EmployeeQueryParam): JQueryPromise<any> {
            return nts.uk.request.ajax('com', servicePath.searchEmployee, param);
        }
        
        /**
         * get List WorkPlaceId
         */
        export function getListWorkplaceId(baseDate: Date, referenceRange: number): JQueryPromise<any> {
            return nts.uk.request.ajax('com', servicePath.getListWorkplaceId, { baseDate: baseDate, referenceRange: referenceRange });
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
                baseDate?: string; // 基準日
                periodStartDate?: string; // 対象期間開始日
                periodEndDate?: string; // 対象期間終了日
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

            export interface SelectedInformation {
                sortOrder: number; // 前回選択していた並び順
                selectedClosureId: number; // 前回選択していた締め
            }

            export interface EmployeeQueryParam {
                baseDate: any;
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

                periodStart: any;
                periodEnd: any;

                includeIncumbents: boolean;
                includeWorkersOnLeave: boolean;
                includeOccupancy: boolean;
                includeRetirees: boolean;
                retireStart: any;
                retireEnd: any;

                sortOrderNo: number;
                nameType: number;
            }

            export interface EmployeeRangeSelection {
                userId: String; // ユーザID
                companyId: String; // 会社ID
                personnelInfo: SelectedInformation; // 人事の選択している情報
                personalInfo: SelectedInformation; // 個人情報の選択している情報
                employmentInfo: SelectedInformation; // 就業の選択している情報
                salaryInfo: SelectedInformation; // 給与の選択している情報
            }
        }
    }
}