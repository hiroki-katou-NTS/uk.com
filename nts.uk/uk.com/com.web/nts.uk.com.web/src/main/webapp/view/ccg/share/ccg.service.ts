module nts.uk.com.view.ccg.share.ccg {
    

    export module service {

        // Service paths.
        var servicePath = {
            findAllPerson: "basic/person/getallperson",
            getPersonLogin: "basic/person/getpersonlogin",
            searchDataEmployee: "basic/company/organization/employee/workplace/history/searchData",
            searchModeEmployee: "basic/organization/employee/search/advanced"
        }

        /**
         * Find person list
         */
        export function findAllPerson(): JQueryPromise<Array<model.PersonModel>> {
            return nts.uk.request.ajax('com', servicePath.findAllPerson);
        }

        // get person by login employee code
        export function getPersonLogin(): JQueryPromise<model.PersonModel> {
            return nts.uk.request.ajax('com', servicePath.getPersonLogin);
        }

        // search data by service
        export function searchDataEmployee(input: model.WorkplaceHistoryInDto): JQueryPromise<model.WorkplaceHistoryDto[]> {
            return nts.uk.request.ajax('com', servicePath.searchDataEmployee, input);
        }


        export function searchModeEmployee(input: model.EmployeeSearchDto)
            : JQueryPromise<model.ClassificationHistoryDto[]> {
            return nts.uk.request.ajax('com', servicePath.searchModeEmployee, input);
        }
        
        export module model{
            export class PersonModel {
                personId: string;
                personName: string;
            }

            export class WorkplaceHistoryDto {
                /** The star date. */
                starDate: Date;

                /** The end date. */
                endDate: Date;

                /** The employee id. */
                employeeId: string;

                /** The work place id. */
                workplaceId: string;
            }

            export class WorkplaceHistoryInDto {
                baseDate: Date;

                workplaceIds: string[];
            }


            export class ClassificationHistoryDto {
                /** The star date. */
                starDate: Date;

                /** The end date. */
                endDate: Date;

                /** The employee id. */
                employeeId: string;

                classificationCode: string;
            }

            export class ClassificationHistoryInDto {
                baseDate: Date;
                classificationCodes: string[];
            }

            export class EmployeeSearchDto {
                classificationHistory: ClassificationHistoryInDto;
            }

            export interface GroupOption {
                // クイック検索タブ
                isQuickSearchTab: boolean;
                // 参照可能な社員すべて
                isAllReferableEmployee: boolean;
                //自分だけ
                isOnlyMe: boolean;
                //おなじ部門の社員
                isEmployeeOfDepartment: boolean;
                //おなじ＋配下部門の社員
                isEmployeeDepartmentFollow: boolean;


                // 詳細検索タブ
                isAdvancedSearchTab: boolean;
                //複数選択 
                isMutipleCheck: boolean;

                onSearchAllClicked: (data: PersonModel[]) => void;

                onSearchOnlyClicked: (data: PersonModel) => void;
            }
    
        }
    }
}