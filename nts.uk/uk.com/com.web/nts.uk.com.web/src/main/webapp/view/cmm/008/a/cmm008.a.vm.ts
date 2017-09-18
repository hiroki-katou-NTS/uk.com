module nts.uk.com.view.cmm008.a {
    import EmploymentFindDto = service.model.EmploymentFindDto;
    
    export module viewmodel {
        export class ScreenModel {
            isNewMode: KnockoutObservable<boolean>;
            employmentCode: KnockoutObservable<string>;
            employmentName: KnockoutObservable<string>;
            empExternalCode: KnockoutObservable<string>;
            memo: KnockoutObservable<string>;
            listComponentOption: any;
            empList: KnockoutObservableArray<ItemModel>;
            employment: EmploymentSaveDto;
            
            constructor() {
                var self = this;
                self.isNewMode = ko.observable(true);
                self.employmentCode = ko.observable("");
                self.empExternalCode = ko.observable("");
                self.employmentCode.subscribe(function(empCode) {
                    if(empCode) {
                        self.clearErrors();
                        self.loadEmployment();
                    } else {
                        self.employmentCode("");
                        self.employmentName("");
                        self.empExternalCode("");
                        self.memo("");
                        self.isNewMode(true);
                        return;
                    }
                });
                self.employmentName = ko.observable("");
                self.empExternalCode = ko.observable("");
                self.memo = ko.observable("");
                
                // Initial listComponentOption
                self.listComponentOption = {
                    isMultiSelect: false,
                    listType: ListType.EMPLOYMENT,
                    selectType: SelectType.SELECT_FIRST_ITEM,
                    selectedCode: self.employmentCode,
                    isDialog: false,
                };
                
//                self.isNewMode = ko.computed(function() {
//                    return !self.isSelectedEmp() || self.empList().length <= 0;
//                });
                self.empList =  ko.observableArray<ItemModel>([]);
            }
            
            // Start Page
            public startPage(): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                var self = this;
//                
                // Load Component
                $('#emp-component').ntsListComponent(self.listComponentOption).done(function() {
                    // Set Focus on Switch Button
//                    $('#switch-btn').focus();
                    
                    // Get Data List
                    if (($('#emp-component').getDataList() == undefined) || ($('#emp-component').getDataList().length <= 0)) {
                        self.isNewMode(true);
                    }
                    else {
                        // Get Employment List after Load Component
                        self.empList($('#emp-component').getDataList());
                        self.loadEmployment();
                    }
                });
                
                dfd.resolve();
                return dfd.promise();
            }
            
            private loadEmployment(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                service.findEmployment(self.employmentCode()).done(function(employment) {
                    if (employment) {
                        self.employmentCode(employment.code);
                        self.employmentName(employment.name);
                        self.empExternalCode(employment.empExternalCode);
                        self.memo(employment.memo);
                        self.isNewMode(false);
                    } else {
//                        self.employmentCode("");
//                        self.employmentName("");
//                        self.empExternalCode("");
//                        self.memo("");
                        self.isNewMode(true);
                    }
                    dfd.resolve();
                });
                return dfd.promise();
            }
            
            private clearData(): void {
                let self = this;
                self.isNewMode(true);
                self.employmentCode("");
                self.employmentName("");
                self.empExternalCode("");
                self.memo("");
            }
            
            
//            
//            /**
//             * Bind Employment
//             */
//            private bindEmployment(employment: any): void {
//                let self = this;
//                if (employment) {
//                    self.employmentCode(employment.code);
//                    self.employmentName(employment.name);
//                    self.empExternalCode(employment.empExternalCode);
//                    self.memo(employment.memo);
//                    self.isNewMode(false);
//                } else {
//                    self.employmentCode("");
//                    self.employmentName("");
//                    self.empExternalCode("");
//                    self.memo("");
//                    self.isNewMode(true);
//                }
//            }
            
            private createNewEmployment(): void {
                let self = this;
                self.clearData();
                self.clearErrors();
            }
            
            private createEmployment(): void {
                let self = this;
                // Validate
                if (self.hasError()) {
                    return;
                }
                var command = {
                    employmentCode: self.employmentCode(),
                    employmentName: self.employmentName(),
                    empExternalCode: self.empExternalCode(),
                    memo: self.memo()
                };
                service.saveEmployment(command).done(() => {
                    self.isNewMode(false);
                    
//                    self.listComponentOption.selectType = SelectType.SELECT_BY_SELECTED_CODE;
//                    self.listComponentOption.selectedCode = self.employmentCode();
                    // Load Component
                    $('#emp-component').ntsListComponent(self.listComponentOption).done(function() {
                        // Find ClassificationBasicWork
                        self.loadEmployment();
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                    });
//                    // Find ClassificationBasicWork
//                    self.loadEmployment();
                }).fail(error => {
                    nts.uk.ui.dialog.alertError(error);
                });
            }
            
            private deleteEmployment(): void {
                let self = this;
                // Validate
                if (self.hasError()) {
                    return;
                }

                // Remove
                nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                    service.removeEmployment(self.employmentCode()).done(() => {
                        self.isNewMode(false);
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                        // Find Employment

                        // Find ClassificationBasicWork
                        self.loadEmployment();
                        blockUI.clear();
                    }).fail((res) => {
                        nts.uk.ui.dialog.alertError(res.message).then(() => { nts.uk.ui.block.clear(); });
                    });
                });
            }
            
            /**
             * Check Errors all input.
             */
            private hasError(): boolean {
                return $('.nts-editor').ntsError('hasError');
            }
            
            // Clear Errors Company Tab
            private clearErrors(): void {
                var self = this;
//                // Clear errors
//                $('#empCode').ntsError('clear');
//                $('#empName').ntsError('clear');
//                $('#extCode').ntsError('clear');
//                $('#memo').ntsError('clear');
                // Clear error inputs
                $('.nts-input').ntsError('clear');
            }
            
        }
        
        export class EmploymentSaveDto {
            employmentCode: KnockoutObservable<string>;
            employmentName: KnockoutObservable<string>;
            empExternalCode: KnockoutObservable<string>;
            memo: KnockoutObservable<string>;
            
            constructor() {
                let self = this;
                self.employmentCode = ko.observable('');
                self.employmentName = ko.observable('');
                self.empExternalCode = ko.observable('');
                self.memo = ko.observable('');
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
        
        // Class ItemModel
        class ItemModel {
            code: string;
            name: string;
            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }
    }
}