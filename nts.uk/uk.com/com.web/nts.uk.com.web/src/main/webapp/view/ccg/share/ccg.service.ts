module nts.uk.com.view.ccg.share.ccg {
    

    export module service {

        // Service paths.
        var servicePath = {
            searchAllEmployee: "basic/organization/employee/search/allemployee",
            searchEmployeeByLogin: "basic/organization/employee/search/onlyemployee",
            searchModeEmployee: "basic/organization/employee/search/advanced",
            searchOfWorkplace: "basic/organization/employee/search/ofworkplace",
            searchWorkplaceChild: "basic/organization/employee/search/workplacechild",
            searchWorkplaceOfEmployee: "basic/organization/employee/search/workplaceemp",
            getOfSelectedEmployee: "basic/organization/employee/search/getoffselect",
            
        }

        /**
         * Find person list
         */
        export function searchAllEmployee(baseDate: Date): JQueryPromise<Array<model.EmployeeSearchDto>> {
            return nts.uk.request.ajax('com', servicePath.searchAllEmployee, baseDate);
        }

        // get person by login employee code
        export function searchEmployeeByLogin(baseDate: Date): JQueryPromise<Array<model.EmployeeSearchDto>> {
            return nts.uk.request.ajax('com', servicePath.searchEmployeeByLogin, baseDate);
        }

        export function searchOfWorkplace(baseDate: Date): JQueryPromise<model.EmployeeSearchDto[]> {
            return nts.uk.request.ajax('com', servicePath.searchOfWorkplace, baseDate);
        }
        export function searchWorkplaceChild(baseDate: Date): JQueryPromise<model.EmployeeSearchDto[]> {
            return nts.uk.request.ajax('com', servicePath.searchWorkplaceChild, baseDate);
        }

        export function searchModeEmployee(input: model.EmployeeSearchInDto)
            : JQueryPromise<model.EmployeeSearchDto[]> {
            return nts.uk.request.ajax('com', servicePath.searchModeEmployee, input);
        }

        export function searchWorkplaceOfEmployee(baseDate: Date): JQueryPromise<string[]> {
            return nts.uk.request.ajax('com', servicePath.searchWorkplaceOfEmployee, baseDate);
        }
        
        export function getOfSelectedEmployee(baseDate: Date, employeeIds: string[]){
              return nts.uk.request.ajax('com', servicePath.getOfSelectedEmployee, {baseDate: baseDate, employeeIds: employeeIds});  
        }
        
        
        export module model{
            
            export class PersonModel {
                personId: string;
                personName: string;
            }

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