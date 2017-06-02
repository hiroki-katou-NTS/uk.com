module nts.uk.pr.view.kmf001.d {
    
    import UpperLimitSettingFindDto = service.model.UpperLimitSettingFindDto;
    import RetentionYearlyFindDto = service.model.RetentionYearlyFindDto;
    import RetentionYearlyDto = service.model.RetentionYearlyDto;
    import UpperLimitSettingDto = service.model.UpperLimitSettingDto;
    import EmploymentSettingDto = service.model.EmploymentSettingDto;
    import EmploymentSettingFindDto = service.model.EmploymentSettingFindDto;
    
    export module viewmodel {
        export class ScreenModel {
            
            retentionYearsAmount: KnockoutObservable<number>;
            maxDaysCumulation: KnockoutObservable<number>;
            textEditorOption: KnockoutObservable<any>;
            yearsAmountByEmp: KnockoutObservable<number>;
            maxDaysCumulationByEmp: KnockoutObservable<number>;
            isManaged: KnockoutObservable<boolean>;
            annualManage: KnockoutObservable<number>;
            
            employmentList: KnockoutObservableArray<ItemModel>;
            columnsSetting: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
            selectedCode: KnockoutObservable<string>;
            managementOption: KnockoutObservableArray<ManagementModel>;
            selectedManagement: KnockoutObservable<number>;
            hasSelectedEmp: KnockoutObservable<boolean>;

            // Dirty checker
            dirtyChecker: nts.uk.ui.DirtyChecker;

            constructor() {
                var self = this;
                self.retentionYearsAmount = ko.observable(null);
                self.maxDaysCumulation = ko.observable(null);
                self.yearsAmountByEmp = ko.observable(null);
                self.maxDaysCumulationByEmp = ko.observable(null);
                self.textEditorOption = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    width: "50px",
                    textmode: "text",
                    textalign: "left"
                }));
                self.employmentList = ko.observableArray<ItemModel>([]);
                for (let i = 1; i < 4; i++) {
                    self.employmentList.push(new ItemModel('0' + i, '基本給'));
                }
                
                self.columnsSetting = ko.observableArray([
                    { headerText: 'コード', key: 'code', width: 100 },
                    { headerText: '名称', key: 'name', width: 300 }
                ]);
                self.selectedCode = ko.observable('');
                self.managementOption = ko.observableArray<ManagementModel>([
                    new ManagementModel(1, '管理する'),
                    new ManagementModel(0, '管理しない')
                ]);
                self.selectedManagement = ko.observable(1);
                self.hasSelectedEmp = ko.observable(false);
                self.isManaged = ko.computed(function() {
                    return self.selectedManagement() == 1;
                }, self);
                
                self.selectedCode.subscribe(function(data: string) {
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
            }
            
            public startPage(): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                var self = this;
                self.findIsManaged().done(function() {
                    service.findRetentionYearly().done(function(data: RetentionYearlyFindDto) {
                        if (data == null) {
                            self.retentionYearsAmount(1);
                            self.maxDaysCumulation(40);
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
                    self.yearsAmountByEmp();
                    self.maxDaysCumulationByEmp();
                    self.selectedManagement(0);
                }
                self.yearsAmountByEmp(data.upperLimitSetting.retentionYearsAmount);
                self.maxDaysCumulationByEmp(data.upperLimitSetting.maxDaysCumulation);
                self.selectedManagement(data.managementCategory);
            }
            
            initializeWholeCompanyData(data: RetentionYearlyFindDto): void {
                var self = this;
                self.retentionYearsAmount(data.upperLimitSetting.retentionYearsAmount);
                self.maxDaysCumulation(data.upperLimitSetting.maxDaysCumulation);
            }
            
            
            private collectWholeCompanyData(): RetentionYearlyDto {
                var self = this;
                var dto: RetentionYearlyDto = new RetentionYearlyDto();
                var upperDto: UpperLimitSettingDto = new  UpperLimitSettingDto();
                upperDto.retentionYearsAmount = self.retentionYearsAmount();
                upperDto.maxDaysCumulation = self.maxDaysCumulation();
                dto.upperLimitSettingDto = upperDto;
                return dto;
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
                    nts.uk.ui.dialog.alert('登録しました。');
                })
                    .fail((res) => {
                        nts.uk.ui.dialog.alert(res.message);
                    });
            }
            
            private collectDataByEmployment(): EmploymentSettingDto {
                var self = this;
                var dto: EmploymentSettingDto = new EmploymentSettingDto();
                var upperLimitDto: UpperLimitSettingDto = new  UpperLimitSettingDto();
                upperLimitDto.retentionYearsAmount = self.yearsAmountByEmp();
                upperLimitDto.maxDaysCumulation = self.maxDaysCumulationByEmp();
                dto.upperLimitSetting = upperLimitDto;
                dto.employmentCode = self.selectedCode();
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
                    nts.uk.ui.dialog.alert('登録しました。');
                })
                    .fail((res) => {
                        nts.uk.ui.dialog.alert(res.message);
                    });
            }
            
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
    }
}