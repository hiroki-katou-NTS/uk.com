module nts.uk.com.view.cmm008.a {
    
    export module viewmodel {
        export class ScreenModel {
            isDelete: KnockoutObservable<boolean>;
            employmentCode: KnockoutObservable<string>;
            employmentName: KnockoutObservable<string>;
            empExternalCode: KnockoutObservable<string>;
            memo: KnockoutObservable<string>;
            
            listComponentOption: any;
            
            constructor() {
                var self = this;
                self.employmentCode = ko.observable("empCode");
                self.employmentName = ko.observable("empName");
                self.empExternalCode = ko.observable("ExtCode");
                self.memo = ko.observable("Memo");
                // Initial listComponentOption
                self.listComponentOption = {
                    isMultiSelect: false,
                    listType: ListType.EMPLOYMENT,
                    selectType: SelectType.SELECT_FIRST_ITEM,
                    selectedCode: self.employmentCode,
                    isDialog: false,
                };
                self.isDelete = ko.observable(true);
            }
            
            // Start Page
            public startPage(): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                var self = this;
                dfd.resolve();
                return dfd.promise();
            }
            
            
            createNewEmployment() {
                
            }
            
            createEmployment() {
                
            }
            
            deleteEmployment() {
                
            }
            // Clear Errors Company Tab
            private clearErrors(): void {
                var self = this;
                // Clear errors
//                $('#year-amount-company').ntsError('clear');
//                $('#max-days-company').ntsError('clear');
            }
            
        }
        
        /**
        * List Type
        */
        export class ListType {
            static EMPLOYMENT = 1;
            static Classification = 2;
            static JOB_TITLE = 3;
            static EMPLOYEE = 4;
        }
        
        /**
         * SelectType
         */
        export class SelectType {
            static SELECT_BY_SELECTED_CODE = 1;
            static SELECT_ALL = 2;
            static SELECT_FIRST_ITEM = 3;
            static NO_SELECT = 4;
        }
        
    }
}