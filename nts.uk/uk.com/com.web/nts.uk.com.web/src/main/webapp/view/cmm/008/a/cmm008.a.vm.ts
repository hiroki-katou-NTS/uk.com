module nts.uk.com.view.cmm008.a {
    import EmploymentDto = service.model.EmploymentDto;
    import blockUI = nts.uk.ui.block;

    export module viewmodel {
        export class ScreenModel {
            enableDelete: KnockoutObservable<boolean>;
            employmentModel: KnockoutObservable<EmploymentModel>;
            selectedCode: KnockoutObservable<string>;
            listComponentOption: any;
            empList: KnockoutObservableArray<ItemModel>;
            enableEmpCode: KnockoutObservable<boolean>;
            constructor() {
                var self = this;
                self.enableDelete = ko.observable(true);
                self.employmentModel = ko.observable(new EmploymentModel);
                self.selectedCode = ko.observable("");
                self.selectedCode.subscribe(function(empCode) {
                    if (empCode) {
                        self.clearErrors();
                        self.loadEmployment(empCode);
                        self.enableDelete(true);
                    } else {
                        self.clearData();
                        self.enableDelete(false);
                    }
                });

                // Initial listComponentOption
                self.listComponentOption = {
                    isMultiSelect: false,
                    listType: ListType.EMPLOYMENT,
                    selectType: SelectType.SELECT_BY_SELECTED_CODE,
                    selectedCode: self.selectedCode,
                    isDialog: false,
                };

                //                self.isNewMode = ko.computed(function() {
                //                    return !self.isSelectedEmp() || self.empList().length <= 0;
                //                });
                self.empList = ko.observableArray<ItemModel>([]);
//                self.isNewMode.subscribe(function(data: boolean) {
//                    if (data) {
//                        // Focus on 
//                        $('#empCode').focus();
//                    } else {
//                        // Focus on 
//                        $('#empName').focus();
//                    }
//                });
                self.enableEmpCode = ko.observable(false);
            }

            // Start Page
            public startPage(): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                var self = this;
                //                
                // Load Component
                $('#emp-component').ntsListComponent(self.listComponentOption).done(function() {
                    // Set Focus on Switch Button

                    // Get Data List
                    if (($('#emp-component').getDataList() == undefined) || ($('#emp-component').getDataList().length <= 0)) {
                        self.enableDelete(false);
                        self.clearData();
                    }
                    else {
                        // Get Employment List after Load Component
                        self.empList($('#emp-component').getDataList());

                        // Select first Item in Employment List
                        self.selectedCode(self.empList()[0].code);

                        // Find and bind selected Employment
                        self.loadEmployment(self.selectedCode());
                    }
                });
                // Focus on 
                //                $('#empName').focus();
                dfd.resolve();
                return dfd.promise();
            }

            private loadEmployment(code: string): void {
                let self = this;
                service.findEmployment(self.selectedCode()).done(function(employment) {
                    if (employment) {
                        self.selectedCode(employment.code);
                        self.employmentModel().updateEmpData(employment);
                        self.employmentModel().isEnableCode(false);
                        self.enableDelete(true);
                    }
                });
            }

            /**
             * Clear Data
             */
            private clearData(): void {
                let self = this;
                self.selectedCode("");
                self.employmentModel().resetEmpData();
                self.enableDelete(false);
            }

            /**
             * Create New Employment
             */
            private createNewEmployment(): void {
                let self = this;
                self.clearData();
                self.clearErrors();
                // Focus on 
                $('#empCode').focus();
            }

            private createEmployment(): void {
                let self = this;
                // Validate
                if (self.hasError()) {
                    return;
                }
                var command = {
                    employmentCode: self.employmentModel().employmentCode(),
                    employmentName: self.employmentModel().employmentName(),
                    empExternalCode: self.employmentModel().empExternalCode(),
                    memo: self.employmentModel().memo()
                };
                service.saveEmployment(command).done(() => {
                    // ReLoad Component
                    $('#emp-component').ntsListComponent(self.listComponentOption).done(function() {
                        // Get Employment List after Load Component
                        self.empList($('#emp-component').getDataList());
                        self.enableDelete(true);
                        self.employmentModel().isEnableCode(false);
                        self.selectedCode(self.employmentModel().employmentCode());
                        // Find to Bind Employment
//                        self.loadEmployment();
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                    });
                    // Focus on 
                    if (self.empList().length <= 0) {
                        // Focus on Employment Code
                        $('#empCode').focus();
                    } else {
                        // Focus on Employment name
                        $('#empName').focus();
                    }
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
                    let command = {
                        employmentCode: self.employmentModel().employmentCode()
                    }
                    service.removeEmployment(command).done(() => {
                        // Reload Component
                        $('#emp-component').ntsListComponent(self.listComponentOption).done(function() {
                            // Filter selected Item
                            var existItem = self.empList().filter((item) => {
                                return item.code == self.employmentModel().employmentCode();
                            })[0];

                            // Check if selected item is the last item
//                            let index = self.empList().indexOf(existItem);

                            // Get Data List
                            if (($('#emp-component').getDataList() == undefined) || ($('#emp-component').getDataList().length <= 0)) {
                                self.enableDelete(false);
                            }
                            else {
                                self.enableDelete(true);
                                let index = self.empList().indexOf(existItem);
                                // Get Employment List after Load Component
                                self.empList($('#emp-component').getDataList());
                                let emplistLength = self.empList().length;
                                if (index == (self.empList().length)) {
                                    self.selectedCode(self.empList()[index - 1].code);
                                } else {
                                    self.selectedCode(self.empList()[index].code);
                                }

                                
                                // Find to bind Employment
                                self.loadEmployment(self.selectedCode());
                            }
                            nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                            
                            // Focus on 
                            if (self.empList().length <= 0) {
                                // Focus on Employment Code
                                $('#empCode').focus();
                            } else {
                                // Focus on Employment name
                                $('#empName').focus();
                            }
                        });

                        self.loadEmployment(self.selectedCode());
                        blockUI.clear();
                    }).fail((res) => {
                        nts.uk.ui.dialog.alertError(res.message).then(() => { nts.uk.ui.block.clear(); });
                    });
                });
            }
            
            private resetData(): void {
                let self = this;
                
            }
            
            private reloadPage(): void {
                let self = this;
            }

            /**
             * Check the last Item in Employment List
             */
            //            public isLastItem(selectedCode: string): boolean {
            //                let self = this;
            //                let index: number = 0;
            //                for(let item of self.empList()){
            //                    index++;
            //                    if(index == self.empList().length && selectedCode === item.code){
            //                        return true;
            //              //                }
            //                return false;
            //            }


            /**
             * Check Errors all input.
             */
            private hasError(): boolean {
                var self = this;
                self.clearErrors();
                $('#empCode').ntsEditor("validate");
                $('#empName').ntsEditor("validate");
                if ($('.nts-input').ntsError('hasError')) {
                    return true;
                }
                return false;
                //                return $('.nts-editor').ntsError('hasError');
            }

            // Clear Errors
            private clearErrors(): void {
                var self = this;
                //                // Clear errors
                $('#empCode').ntsError('clear');
                $('#empName').ntsError('clear');
                $('#extCode').ntsError('clear');
                $('#memo').ntsError('clear');
                // Clear error inputs
                $('.nts-input').ntsError('clear');
            }

        }

        /**
         * EmploymentModel
         */
        export class EmploymentModel {
            employmentCode: KnockoutObservable<string>;
            employmentName: KnockoutObservable<string>;
            empExternalCode: KnockoutObservable<string>;
            memo: KnockoutObservable<string>;
            isEnableCode: KnockoutObservable<boolean>;
            
            constructor() {
                this.employmentCode = ko.observable("");
                this.employmentName = ko.observable("");
                this.empExternalCode = ko.observable("");
                this.memo = ko.observable("");
                this.isEnableCode = ko.observable(true);
            }
            
            resetEmpData() {
                this.employmentCode('');
                this.employmentName('');
                this.empExternalCode('');
                this.memo('');
                this.isEnableCode(true);
                this.employmentCode.subscribe(function() {
                    
                });
            }
            
            updateEmpData(dto: EmploymentDto) {
                this.employmentCode(dto.code);
                this.employmentName(dto.name);
                this.empExternalCode(dto.empExternalCode);
                this.memo(dto.memo);
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