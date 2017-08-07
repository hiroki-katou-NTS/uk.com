module nts.uk.at.view.ksm001.a {

    import TargetYearDto = service.model.TargetYearDto;
    import MonthlyDto = service.model.MonthlyDto;

    export module viewmodel {

        export class ScreenModel {
            lstTargetYear: KnockoutObservableArray<TargetYearDto>;
            isCompanySelected: KnockoutObservable<boolean>;
            isEmploymentSelected: KnockoutObservable<boolean>;
            isPersonSelected: KnockoutObservable<boolean>;
            isLoading: KnockoutObservable<boolean>;
            selectedTargetYear: KnockoutObservable<string>;
            lstMonthly: KnockoutObservableArray<MonthlyModel>;
            beginMonthly: KnockoutObservable<MonthlyModel>;
            tabs: KnockoutObservableArray<NtsTabPanelModel>;
            employmentTabs: KnockoutObservableArray<NtsTabPanelModel>;
            selectedTab: KnockoutObservable<string>;
            selEmploymentTab: KnockoutObservable<string>;
            
            
            listComponentOption: any;
            selCodeEmployment: KnockoutObservable<string>;
            alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;


            constructor() {
                var self = this;
                
                self.lstTargetYear = ko.observableArray([]);
                self.lstMonthly = ko.observableArray([]);
                self.beginMonthly = ko.observable(new MonthlyModel());
                self.isCompanySelected = ko.observable(true);
                self.isEmploymentSelected = ko.observable(false);
                self.isPersonSelected = ko.observable(false);
                self.isLoading = ko.observable(false);
                self.selectedTargetYear = ko.observable('');
                self.selCodeEmployment = ko.observable('');
                self.alreadySettingList = ko.observableArray([]);
                
                self.listComponentOption = {
                    isShowAlreadySet: true,
                    isMultiSelect: false,
                    listType: ListType.EMPLOYMENT,
                    selectType: SelectType.SELECT_FIRST_ITEM,
                    selectedCode: self.selCodeEmployment,
                    isDialog: false,
                    isShowNoSelectRow: false,
                    alreadySettingList: self.alreadySettingList,
                    maxRows: 12
                };
                self.tabs = ko.observableArray([
                    { id: 'tab-1', title: nts.uk.resource.getText("KSM001_23"), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: nts.uk.resource.getText("KSM001_24"), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-3', title: nts.uk.resource.getText("KSM001_25"), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) }
                ]);
                self.employmentTabs = ko.observableArray([
                    { id: 'emp-tab-1', title: nts.uk.resource.getText("KSM001_23"), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'emp-tab-2', title: nts.uk.resource.getText("KSM001_24"), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'emp-tab-3', title: nts.uk.resource.getText("KSM001_25"), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) }
                ]);
                self.selectedTab = ko.observable('tab-1');
                self.selEmploymentTab = ko.observable('emp-tap-1');
            }
            /**
            * start page data 
            */
            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                var arrTargetYear: TargetYearDto[] = [];
                var targetOne: TargetYearDto = { code: '2017', name: 2017 };
                var targetTwo: TargetYearDto = { code: '2018', name: 2018 };
                arrTargetYear.push(targetOne);
                arrTargetYear.push(targetTwo);
                self.lstTargetYear(arrTargetYear);
                self.selectedTargetYear('2017');
                self.onSelectCompany().done(function(){
                    dfd.resolve(self);    
                });                
                return dfd.promise();
            }
            
            /**
             * on click tab panel company action event
             */
            public onSelectCompany(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred();
                self.isCompanySelected(true);
                self.isEmploymentSelected(false);
                self.isEmploymentSelected(false);
                self.isPersonSelected(false);
                self.isLoading(true);
                service.findAllMonthly().done(function(data: MonthlyDto[]) {
                    var dataModel: MonthlyModel[] = [];
                    for (var monthly: MonthlyDto of data) {
                        var monthlyModel: MonthlyModel = new MonthlyModel();
                        monthlyModel.updateDate(monthly);
                        dataModel.push(monthlyModel);
                        self.beginMonthly(monthlyModel);
                    }
                    self.lstMonthly(dataModel);
                    self.isLoading(false);
                    dfd.resolve();
                });
                return dfd.promise();
            }
            
            /**
             * on click tab panel employment action event
             */
            public onSelectEmployment(): void {
                var self = this;
                self.isCompanySelected(false);
                self.isPersonSelected(false);
                self.isEmploymentSelected(true);
                self.isLoading(false);
                $('#employmentSetting').ntsListComponent(self.listComponentOption);
            }
            /**
             * on click tab panel employment action event
             */
            public onSelectPerson(): void {
                var self = this;
                self.isCompanySelected(false);
                self.isPersonSelected(true);
                self.isEmploymentSelected(false);
                self.isLoading(true);
                service.findAllMonthly().done(function(data: MonthlyDto[]) {
                    var dataModel: MonthlyModel[] = [];
                    for (var monthly: MonthlyDto of data) {
                        var monthlyModel: MonthlyModel = new MonthlyModel();
                        monthlyModel.updateDate(monthly);
                        dataModel.push(monthlyModel);
                        self.beginMonthly(monthlyModel);
                    }
                    self.lstMonthly(dataModel);
                    self.isLoading(false);
                });
            }


        }

        export class MonthlyModel {
            month: number;
            time001: KnockoutObservable<number>;
            time002: KnockoutObservable<number>;
            time003: KnockoutObservable<number>;
            time004: KnockoutObservable<number>;
            time005: KnockoutObservable<number>;

            constructor() {
                this.month = 1;
                this.time001 = ko.observable(0);
                this.time002 = ko.observable(0);
                this.time003 = ko.observable(0);
                this.time004 = ko.observable(0);
                this.time005 = ko.observable(0);
            }

            updateDate(dto: MonthlyDto) {
                this.month = dto.month;
                this.time001(dto.time001);
                this.time002(dto.time002);
                this.time003(dto.time003);
                this.time004(dto.time004);
                this.time005(dto.time005);
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

        export class SelectType {
            static SELECT_BY_SELECTED_CODE = 1;
            static SELECT_ALL = 2;
            static SELECT_FIRST_ITEM = 3;
            static NO_SELECT = 4;
        }

        export interface UnitAlreadySettingModel {
            code: string;
            isAlreadySetting: boolean;
        }
    }
}