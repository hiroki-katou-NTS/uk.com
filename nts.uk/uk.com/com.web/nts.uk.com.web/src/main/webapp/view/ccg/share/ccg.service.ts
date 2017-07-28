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
            
        }

        /**
         * Find person list
         */
        export function searchAllEmployee(baseDate: Date): JQueryPromise<Array<model.EmployeeSearchDto>> {
            return nts.uk.request.ajax('com', servicePath.searchAllEmployee, baseDate);
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
                baseDate?: KnockoutObservable<Date>;
                // クイック検索タブ
                isQuickSearchTab: boolean;
                // 参照可能な社員すべて
                isAllReferableEmployee: boolean;
                //自分だけ
                isOnlyMe: boolean;
                //おなじ部門の社員
                isEmployeeOfWorkplace: boolean;
                //おなじ＋配下部門の社員
                isEmployeeWorkplaceFollow: boolean;

                
                // 詳細検索タブ
                isAdvancedSearchTab: boolean;
                //複数選択 
                isMutipleCheck: boolean;
                
                //社員指定タイプ or 全社員タイプ
                isSelectAllEmployee: boolean;

                onSearchAllClicked: (data: EmployeeSearchDto[]) => void;

                onSearchOnlyClicked: (data: EmployeeSearchDto) => void;
                
                onSearchOfWorkplaceClicked: (data: EmployeeSearchDto[]) => void;
                
                onSearchWorkplaceChildClicked: (data: EmployeeSearchDto[]) => void;
                
                onApplyEmployee: (data: EmployeeSearchDto[]) => void;
            }
    
        }
    }
}