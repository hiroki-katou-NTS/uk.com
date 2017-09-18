module nts.uk.com.view.cmm008.a {
    import EmploymentFindDto = service.model.EmploymentFindDto;
    import blockUI = nts.uk.ui.block;

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
                    if (empCode) {
                        self.clearErrors();
                        self.loadEmployment();
                    } else {
                        self.clearData();
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
                    selectType: SelectType.SELECT_BY_SELECTED_CODE,
                    selectedCode: self.employmentCode,
                    isDialog: false,
                };

                //                self.isNewMode = ko.computed(function() {
                //                    return !self.isSelectedEmp() || self.empList().length <= 0;
                //                });
                self.empList = ko.observableArray<ItemModel>([]);
                self.isNewMode.subscribe(function(data: boolean) {
                    if (data) {
                        // Focus on 
                        $('#empCode').focus();
                    } else {
                        // Focus on 
                        $('#empName').focus();
                    }
                });
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
                        self.isNewMode(true);
                    }
                    else {
                        // Get Employment List after Load Component
                        self.empList($('#emp-component').getDataList());

                        // Select first Item in Employment List
                        self.employmentCode(self.empList()[0].code);

                        // Find and bind selected Employment
                        self.loadEmployment();
                    }
                });
                // Focus on 
                //                $('#empName').focus();
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
            //                    self.employmentN          //                    self.empExternalCode("");
            //                    self.memo("");
            //                    self.isNewMode(true);
            //                }
            //            }

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
                    employmentCode: self.employmentCode(),
                    employmentName: self.employmentName(),
                    empExternalCode: self.empExternalCode(),
                    memo: self.memo()
                };
                service.saveEmployment(command).done(() => {
                    self.isNewMode(false);

                    // ReLoad Component
                    //                    self.listComponentOption.selectType = SelectType.SELECT_BY_SELECTED_CODE;
                    $('#emp-component').ntsListComponent(self.listComponentOption).done(function() {
                        // Get Employment List after Load Component
                        self.empList($('#emp-component').getDataList());
                        // Find to Bind Employment
                        self.loadEmployment();
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
                        employmentCode: self.employmentCode()
                    }
                    service.removeEmployment(command).done(() => {
                        self.isNewMode(false);

                        // Filter selected Item
                        var existItem = self.empList().filter((item) => {
                            return item.code == self.employmentCode();
                        })[0];

                        // Check if selected item is the last item
                        let index = self.empList().indexOf(existItem);
                        let emplistLength = self.empList().length;
                        if (index == (self.empList().length - 1)) {
                            self.employmentCode(self.empList()[index - 1].code);
                            //                            self.listComponentOption.selectedCode = self.empList()[index - 1].code;
                        } else {
                            self.employmentCode(self.empList()[index + 1].code);
                        }

                        // Reload Component
                        //                        self.listComponentOption.selectType = SelectType.SELECT_BY_SELECTED_CODE;
                        $('#emp-component').ntsListComponent(self.listComponentOption).done(function() {
                            // Get Data List
                            if (($('#emp-component').getDataList() == undefined) || ($('#emp-component').getDataList().length <= 0)) {
                                self.isNewMode(true);
                            }
                            else {
                                // Get Employment List after Load Component
                                self.empList($('#emp-component').getDataList());
                                // Find to bind Employment
                                self.loadEmployment();
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

                        // Find ClassificationBasicWork
                        self.loadEmployment();
                        blockUI.clear();
                    }).fail((res) => {
                        nts.uk.ui.dialog.alertError(res.message).then(() => { nts.uk.ui.block.clear(); });
                    });
                });
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