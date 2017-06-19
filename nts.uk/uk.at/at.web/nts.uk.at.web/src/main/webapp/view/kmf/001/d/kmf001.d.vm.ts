module nts.uk.pr.view.kmf001.d {
    
    import UpperLimitSettingFindDto = service.model.UpperLimitSettingFindDto;
    import RetentionYearlyFindDto = service.model.RetentionYearlyFindDto;
    import RetentionYearlyDto = service.model.RetentionYearlyDto;
    import UpperLimitSettingDto = service.model.UpperLimitSettingDto;
    import EmploymentSettingDto = service.model.EmploymentSettingDto;
    import EmploymentSettingFindDto = service.model.EmploymentSettingFindDto;
    
    
    export module viewmodel {
        export class ScreenModel {
            selectedItem: KnockoutObservable<string>;
            listComponentOption: KnockoutObservable<any>;
            alreadySettingList: KnockoutObservableArray<any>;
            
            retentionYearsAmount: KnockoutObservable<number>;
            maxDaysCumulation: KnockoutObservable<number>;
            textEditorOption: KnockoutObservable<any>;
            yearsAmountByEmp: KnockoutObservable<number>;
            maxDaysCumulationByEmp: KnockoutObservable<number>;
            isManaged: KnockoutObservable<boolean>;
            annualManage: KnockoutObservable<number>;
            
            employmentList: KnockoutObservableArray<ItemModel>;
//            columnsSetting: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
//            selectedCode: KnockoutObservable<string>;
            managementOption: KnockoutObservableArray<ManagementModel>;
            selectedManagement: KnockoutObservable<number>;
            hasSelectedEmp: KnockoutObservable<boolean>;
            leaveAsWorkDays: KnockoutObservable<boolean>;
            leaveAsWorkDaysOpt: KnockoutObservableArray<LeaveAsWorkDaysModel>;

            // Dirty checker
            dirtyChecker: nts.uk.ui.DirtyChecker;

            constructor() {
                var self = this;
                this.selectedItem = ko.observable(null);
                self.alreadySettingList = ko.observableArray([]);

                self.employmentList = ko.observableArray<ItemModel>([]);
                
                this.listComponentOption = {
                    isShowAlreadySet: true, // is show already setting column.
                    isMultiSelect: false, // is multiselect.
                    listType: ListType.EMPLOYMENT,
                    selectedCode: self.selectedItem,
                    isDialog: false,
                    alreadySettingList: self.alreadySettingList
                };
                
                self.retentionYearsAmount = ko.observable(null);
                self.maxDaysCumulation = ko.observable(null);
                self.yearsAmountByEmp = ko.observable(null);
                self.maxDaysCumulationByEmp = ko.observable(null);
                self.textEditorOption = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    width: "50px",
                    textmode: "text",
                    textalign: "left"
                }));
                
//                self.employmentList.subscribe(function(data) {
//                    service.findAllByEmployment().done(function(data: any) {
//                        for (var i = 0; i < data.length; i++) {
//                            self.alreadySettingList.push(data[i].code);
//                        }
//                    });
//                });
                

                self.managementOption = ko.observableArray<ManagementModel>([
                    new ManagementModel(1, '管理する'),
                    new ManagementModel(0, '管理しない')
                ]);
                self.selectedManagement = ko.observable(1);
                self.hasSelectedEmp = ko.observable(false);
                self.isManaged = ko.computed(function() {
                    return self.selectedManagement() == 1;
                }, self);
                
                self.selectedItem.subscribe(function(data: string) {
                    service.findByEmployment(data).done(function(data1: EmploymentSettingFindDto) {
                        self.hasSelectedEmp(true);
                        $('#switch-btn').focus();
                        self.bindEmploymentSettingData(data1);
                        
                    });
                    
                });
                
                self.annualManage = ko.observable(1);
                self.isManaged = ko.computed(function() {
                    return self.annualManage() == 1;
                }, self);
                self.leaveAsWorkDaysOpt = ko.observableArray<LeaveAsWorkDaysModel>([
                    new LeaveAsWorkDaysModel(true, '管理する'),
                    new LeaveAsWorkDaysModel(false, '管理しない')
                ]);
                self.leaveAsWorkDays = ko.observable(null);
            }
            
            public startPage(): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                var self = this;
                self.findIsManaged().done(function() {
                    service.findRetentionYearly().done(function(data: RetentionYearlyFindDto) {
                        if (data == null) {
                            self.retentionYearsAmount(1);
                            self.maxDaysCumulation(40);
                            self.leaveAsWorkDays(false);
                        }
                        else {
                            self.initializeWholeCompanyData(data);
                        }
                        dfd.resolve();
                         $('#year-amount-company').focus();
                    });
                })
                .fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                });
                return dfd.promise();
            }
            
            private findIsManaged(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                service.findIsManaged().done(function(data: any) {
                    
                    if (data) {
                        if (data == undefined) {
                            self.annualManage(0);
                        }
                        else {
                            self.annualManage(data.annualManage);
                        }
                    }
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                });
                return dfd.promise();
            }
            
            private backToHistorySelection() {
                nts.uk.request.jump("/view/kmf/001/a/index.xhtml");
            }
            
            private bindEmploymentSettingData(data: EmploymentSettingFindDto): void {
                var self = this;
                if(data == undefined) {
                    self.yearsAmountByEmp(null);
                    self.maxDaysCumulationByEmp(null);
                    self.selectedManagement(0);
                }
                else {
                    self.yearsAmountByEmp(data.upperLimitSetting.retentionYearsAmount);
                    self.maxDaysCumulationByEmp(data.upperLimitSetting.maxDaysCumulation);
                    self.selectedManagement(data.managementCategory);
                }
            }
            
            initializeWholeCompanyData(data: RetentionYearlyFindDto): void {
                var self = this;
                self.retentionYearsAmount(data.upperLimitSetting.retentionYearsAmount);
                self.maxDaysCumulation(data.upperLimitSetting.maxDaysCumulation);
                self.leaveAsWorkDays(data.leaveAsWorkDays);
            }
            
            
            private collectWholeCompanyData(): RetentionYearlyDto {
                var self = this;
                var dto: RetentionYearlyDto = new RetentionYearlyDto();
                var upperDto: UpperLimitSettingDto = new  UpperLimitSettingDto();
                upperDto.retentionYearsAmount = self.retentionYearsAmount();
                upperDto.maxDaysCumulation = self.maxDaysCumulation();
                dto.upperLimitSettingDto = upperDto;
                dto.leaveAsWorkDays = self.leaveAsWorkDays();
                return dto;
            }
            
            private switchToEmploymentTab(): void {
                var self = this;
                service.findAllByEmployment().done(function(data: any) {
                    for (var i = 0; i < data.length; i++) {
                        self.alreadySettingList.push(data[i].employmentCode);
                    }
                });
                // employmentList...
                $('#left-content').ntsListComponent(self.listComponentOption).done(function() {
                    self.employmentList($('#left-content').getDataList());
                    
                    // Selected Item
                    self.selectedItem(self.employmentList()[0].code);
                    if((self.employmentList() == undefined) || (self.employmentList().length <= 0)) {
                        self.hasSelectedEmp(false);
                        nts.uk.ui.dialog.alertError({ messageId: "Msg_146", messageParams: []})
                    }
                    else {
                        self.hasSelectedEmp(true);
                    }
                });
            }
            
            private registerWholeCompany(): void {
                var self = this;
                // Clear errors
                $('#year-amount-company').ntsError('clear');
                $('#max-days-company').ntsError('clear');
                
                // Validate. 
                $('#year-amount-company').ntsEditor('validate');
                $('#max-days-company').ntsEditor('validate');
//                $('.nts-input').ntsEditor('validate');
                if ($('.nts-input').ntsError('hasError')) {
                    return;
                }
                
                service.saveRetentionYearly(self.collectWholeCompanyData()).done(function() {
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                })
                    .fail((res) => {
                        nts.uk.ui.dialog.alertError(res.message);
                    });
            }
            
            private collectDataByEmployment(): EmploymentSettingDto {
                var self = this;
                var dto: EmploymentSettingDto = new EmploymentSettingDto();
                var upperLimitDto: UpperLimitSettingDto = new  UpperLimitSettingDto();
                upperLimitDto.retentionYearsAmount = self.yearsAmountByEmp();
                upperLimitDto.maxDaysCumulation = self.maxDaysCumulationByEmp();
                dto.upperLimitSetting = upperLimitDto;
                dto.employmentCode = self.selectedItem();
                dto.managementCategory = self.selectedManagement();
                return dto;
            }
            
            
            private registerByEmployment(): void {
                var self = this;
                // Clear errors
                $('#year-amount-emp').ntsError('clear');
                $('#max-days-emp').ntsError('clear');
                
                // Validate. 
                $('#year-amount-emp').ntsEditor('validate');
                $('#max-days-emp').ntsEditor('validate');
//                $('.nts-input').ntsEditor('validate');
                if ($('.nts-input').ntsError('hasError')) {
                    return;
                }
                
                service.saveByEmployment(self.collectDataByEmployment()).done(function() {
//                    nts.uk.ui.dialog.alert('登録しました。');
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                })
                    .fail((res) => {
                        nts.uk.ui.dialog.alertError(res.message);
                    });
            }
            
        }
        
        export class ListType {
            static EMPLOYMENT = 1;
            static Classification = 2;
            static JOB_TITLE = 3;
            static EMPLOYEE = 4;
        }
        
        export interface UnitModel {
            code: string;
            name?: string;
            workplaceName?: string;
            isAlreadySetting?: boolean;
        }
        
        class ItemModel {

            code: string;
            name: string;
            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }
        
        class ManagementModel {
            code: number;
            name: string;
            constructor(code: number, name: string) {
                this.code = code;
                this.name = name;
            }
        }
        
        class LeaveAsWorkDaysModel {
            value: boolean;
            name: string;
            constructor(value: boolean, name: string) {
                this.value = value;
                this.name = name;
            }
        }
    }
}