module nts.uk.pr.view.kmf001.d {
    
    import UpperLimitSettingFindDto = service.model.UpperLimitSettingFindDto;
    import RetentionYearlyFindDto = service.model.RetentionYearlyFindDto;
    import RetentionYearlyDto = service.model.RetentionYearlyDto;
    import UpperLimitSettingDto = service.model.UpperLimitSettingDto;
    
    export module viewmodel {
        export class ScreenModel {
            
            retentionYearsAmount: KnockoutObservable<number>;
            maxDaysCumulation: KnockoutObservable<number>;
            textEditorOption: KnockoutObservable<any>;
            
            empList: KnockoutObservableArray<ItemModel>;
            columnsSetting: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
            selectedCode: KnockoutObservable<string>;
            managementOption: KnockoutObservableArray<ManagementModel>;
            selectedManagement: KnockoutObservable<number>;
            hasEmp: KnockoutObservable<boolean>;

            // Dirty checker
            dirtyChecker: nts.uk.ui.DirtyChecker;

            constructor() {
                var self = this;
                self.retentionYearsAmount = ko.observable(null);
                self.maxDaysCumulation = ko.observable(null);
                self.textEditorOption = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    width: "50px",
                    textmode: "text",
                    placeholder: "Not Empty",
                    textalign: "left"
                }));
                self.empList = ko.observableArray<ItemModel>([]);
                for (let i = 1; i < 10; i++) {
                    self.empList.push(new ItemModel('00' + i, '基本給', i % 3 === 0));
                }
                self.columnsSetting = ko.observableArray([
                    { headerText: 'コード', key: 'code', width: 100 },
                    { headerText: '名称', key: 'name', width: 150 },
                    { headerText: '設定済', key: 'alreadySet', width: 150 }
                ]);
                self.selectedCode = ko.observable('');
                self.managementOption = ko.observableArray<ManagementModel>([
                    new ManagementModel(1, '管理す'),
                    new ManagementModel(0, '管理しな')
                ]);
                self.selectedManagement = ko.observable(1);
                self.hasEmp = ko.computed(function() {
                    return self.empList().length > 0;
                });
            }
            
            public startPage(): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                var self = this;
                service.findRetentionYearly().done(function(data: RetentionYearlyFindDto) {
                    if(data == null) {
                        self.retentionYearsAmount(1);
                        self.maxDaysCumulation(40);
                    }
                    else {
                       self.initializeData(data);
                    }
                    dfd.resolve();
                })
                return dfd.promise();
            }
            
            initializeData(data: RetentionYearlyFindDto): void {
                var self = this;
                self.retentionYearsAmount(data.upperLimitSetting.retentionYearsAmount);
                self.maxDaysCumulation(data.upperLimitSetting.maxDaysCumulation);
            }
            
            public backToHistorySelection() {
                
            }
            
            public register(): void {
                var self = this;
                /*
                // Validate.
                $('.nts-input').ntsEditor('validate');
                if ($('.nts-input').ntsError('hasError')) {
                    return;
                }
                */
                service.saveRetentionYearly(self.collectData()).done(function() {
                    nts.uk.ui.dialog.alert('登録しました。');
                })
                .fail((res) => {
                        nts.uk.ui.dialog.alert(res.message);
                    });
            }
            
            public collectData(): RetentionYearlyDto {
                var self = this;
                var dto: RetentionYearlyDto = new RetentionYearlyDto();
                var upperDto: UpperLimitSettingDto = new  UpperLimitSettingDto();
                upperDto.retentionYearsAmount = self.retentionYearsAmount();
                upperDto.maxDaysCumulation = self.maxDaysCumulation();
                dto.upperLimitSettingDto = upperDto;
                return dto;
            }
                
        }
        
        class ItemModel {

            code: string;
            name: string;
            alreadySet: boolean;
            constructor(code: string, name: string, alreadySet: boolean) {
                this.code = code;
                this.name = name;
                this.alreadySet = alreadySet;
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