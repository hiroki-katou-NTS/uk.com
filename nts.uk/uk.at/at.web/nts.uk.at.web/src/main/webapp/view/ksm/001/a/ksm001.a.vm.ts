module nts.uk.at.view.ksm001.a {

    import TargetYearDto = service.model.TargetYearDto;
    import EstimateTimeDto = service.model.EstimateTimeDto;
    import CompanyEstimateTimeDto = service.model.CompanyEstimateTimeDto;

    export module viewmodel {

        export class ScreenModel {
            lstTargetYear: KnockoutObservableArray<TargetYearDto>;
            isCompanySelected: KnockoutObservable<boolean>;
            isEmploymentSelected: KnockoutObservable<boolean>;
            isPersonSelected: KnockoutObservable<boolean>;
            isLoading: KnockoutObservable<boolean>;
            selectedTargetYear: KnockoutObservable<string>;
            companyTimeModel: KnockoutObservable<CompanyEstimateTimeModel>;
            tabs: KnockoutObservableArray<NtsTabPanelModel>;
            employmentTabs: KnockoutObservableArray<NtsTabPanelModel>;
            selectedTab: KnockoutObservable<string>;
            selEmploymentTab: KnockoutObservable<string>;
            
            
            lstEmploymentComponentOption: any;
            selCodeEmployment: KnockoutObservable<string>;
            alreadyEmploymentSettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
            
            ccgcomponentPerson: GroupOption;

            // Options
            baseDate: KnockoutObservable<Date>;
            selectedEmployee: KnockoutObservableArray<EmployeeSearchDto>;

            lstPersonComponentOption: any;
            selectedCode: KnockoutObservable<string>;
            employeeName: KnockoutObservable<string>;
            alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
            isShowNoSelectRow: KnockoutObservable<boolean>;
            employeeList: KnockoutObservableArray<UnitModel>;


            constructor() {
                var self = this;
                
                self.baseDate = ko.observable(new Date());
                self.selectedEmployee = ko.observableArray([]);
                self.lstTargetYear = ko.observableArray([]);
                self.companyTimeModel = ko.observable(new CompanyEstimateTimeModel());
                self.isCompanySelected = ko.observable(true);
                self.isEmploymentSelected = ko.observable(false);
                self.isPersonSelected = ko.observable(false);
                self.isLoading = ko.observable(false);
                self.selectedTargetYear = ko.observable('');
                self.selCodeEmployment = ko.observable('');
                self.alreadySettingList = ko.observableArray([]);
                
                self.lstEmploymentComponentOption = {
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
                
                self.ccgcomponentPerson = {
                    baseDate: self.baseDate,
                    //Show/hide options
                    isQuickSearchTab: true,
                    isAdvancedSearchTab: true,
                    isAllReferableEmployee: true,
                    isOnlyMe: true,
                    isEmployeeOfWorkplace: true,
                    isEmployeeWorkplaceFollow: true,
                    isMutipleCheck: true,
                    isSelectAllEmployee: true,
                    /**
                    * @param dataList: list employee returned from component.
                    * Define how to use this list employee by yourself in the function's body.
                    */
                    onSearchAllClicked: function(dataList: EmployeeSearchDto[]) {
                        self.selectedEmployee(dataList);
                        self.applyKCP005ContentSearch(dataList);
                    },
                    onSearchOnlyClicked: function(data: EmployeeSearchDto) {
                        var dataEmployee: EmployeeSearchDto[] = [];
                        dataEmployee.push(data);
                        self.selectedEmployee(dataEmployee);
                        self.applyKCP005ContentSearch(dataEmployee);
                    },
                    onSearchOfWorkplaceClicked: function(dataList: EmployeeSearchDto[]) {
                        self.selectedEmployee(dataList);
                        self.applyKCP005ContentSearch(dataList);
                    },
                    onSearchWorkplaceChildClicked: function(dataList: EmployeeSearchDto[]) {
                        self.selectedEmployee(dataList);
                        self.applyKCP005ContentSearch(dataList);
                    },
                    onApplyEmployee: function(dataEmployee: EmployeeSearchDto[]) {
                        self.selectedEmployee(dataEmployee);
                        self.applyKCP005ContentSearch(dataEmployee);
                    }

                }
                self.tabs = ko.observableArray([
                    { id: 'person-tab-1', title: nts.uk.resource.getText("KSM001_23"), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'person-tab-2', title: nts.uk.resource.getText("KSM001_24"), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'person-tab-3', title: nts.uk.resource.getText("KSM001_25"), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) }
                ]);
                self.employmentTabs = ko.observableArray([
                    { id: 'emp-tab-1', title: nts.uk.resource.getText("KSM001_23"), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'emp-tab-2', title: nts.uk.resource.getText("KSM001_24"), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'emp-tab-3', title: nts.uk.resource.getText("KSM001_25"), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) }
                ]);
                self.selectedTab = ko.observable('person-tab-1');
                self.selEmploymentTab = ko.observable('emp-tab-1');
                
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
            
            public visibleTabpanel() {
                var self = this;
                
            }
            
            /**
             * on click tab panel company action event
             */
            public onSelectCompany(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                self.isEmploymentSelected(false);
                self.isPersonSelected(false);
                self.isCompanySelected(true);
                self.isLoading(true);
                service.findAllMonthlyEstimateTime(2017).done(function(data) {
                    self.companyTimeModel().updateData(data);
                    
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
                $('#employmentSetting').ntsListComponent(self.lstEmploymentComponentOption)
            }
            /**
             * on click tab panel employment action event
             */
            public onSelectPerson(): void {
                var self = this;
                self.isCompanySelected(false);
                self.isEmploymentSelected(false);
                self.isPersonSelected(true);
                self.isLoading(true);
                service.findAllMonthlyEstimateTime(2017).done(function(data) {
                    self.companyTimeModel().updateData(data);
                    self.isLoading(false);
                    $('#ccgcomponent').ntsGroupComponent(self.ccgcomponentPerson);
                    self.selectedCode = ko.observable('');
                    self.alreadySettingList = ko.observableArray([]);
                    self.isShowNoSelectRow = ko.observable(false);
                    self.employeeList = ko.observableArray<UnitModel>([]);
                    self.applyKCP005ContentSearch([]);

                    $('#employeeSearch').ntsListComponent(self.lstPersonComponentOption);
                    window.setTimeout(function() {
                        $('#' + self.selectedTab()).removeClass('disappear');
                    }, 100);
                });
            }

            
            /**
             * apply ccg001 search data to kcp005
             */
            public applyKCP005ContentSearch(dataList: EmployeeSearchDto[]): void {
                var self = this;
                self.employeeList([]);
                var employeeSearchs: UnitModel[] = [];
                for (var employeeSearch of dataList) {
                    var employee: UnitModel = {
                        code: employeeSearch.employeeId,
                        name: employeeSearch.employeeName,
                        workplaceName: employeeSearch.workplaceName
                    };
                    employeeSearchs.push(employee);
                }
                self.employeeList(employeeSearchs);
                
                if (dataList.length > 0) {
                    self.selectedCode(dataList[0].employeeId);
                }

                self.findAllByEmployeeIds(self.getAllEmployeeIdBySearch()).done(function(data) {
                    self.alreadySettingList(data);
                });
                self.lstPersonComponentOption = {
                    isShowAlreadySet: true,
                    isMultiSelect: false,
                    listType: ListType.EMPLOYEE,
                    employeeInputList: self.employeeList,
                    selectType: SelectType.SELECT_FIRST_ITEM,
                    selectedCode: self.selectedCode,
                    isDialog: false,
                    isShowNoSelectRow: false,
                    alreadySettingList: self.alreadySettingList,
                    isShowWorkPlaceName: true,
                    isShowSelectAllButton: false,
                    maxWidth: 350,
                    maxRows: 12
                };

            }
            
            /**
           * get all employee id by search data CCG out put 
           */
            public getAllEmployeeIdBySearch(): string[] {
                var self = this;
                var employeeIds: string[] = [];
                for (var employeeSelect of self.employeeList()) {
                    employeeIds.push(employeeSelect.code);
                }
                return employeeIds;
            }
            
            /**
             * call service find all by employee id
             */
            public findAllByEmployeeIds(employeeIds: string[]): JQueryPromise<UnitAlreadySettingModel[]> {
                var dfd = $.Deferred();
                var dataRes: UnitAlreadySettingModel[] = [];
                dfd.resolve(dataRes);
                return dfd.promise();
            }
            
            
           /**
            * function on click saveCompanyEstablishment action
            */
            public saveCompanyEstablishment(): void {
                var self = this;
                service.saveCompanyEstimate(2017, self.companyTimeModel().toDto()).done(function(){
                   
                });
            }

        }

        export class EstimateTimeModel {
            month: KnockoutObservable<number>;
            time1st: KnockoutObservable<number>;
            time2nd: KnockoutObservable<number>;
            time3rd: KnockoutObservable<number>;
            time4th: KnockoutObservable<number>;
            time5th: KnockoutObservable<number>;

            constructor() {
                this.month = ko.observable(1);
                this.time1st = ko.observable(0);
                this.time2nd = ko.observable(0);
                this.time3rd = ko.observable(0);
                this.time4th = ko.observable(0);
                this.time5th = ko.observable(0);
            }

            updateData(dto: EstimateTimeDto) {
                this.month(dto.month);
                this.time1st(dto.time1st);
                this.time2nd(dto.time2nd);
                this.time3rd(dto.time3rd);
                this.time4th(dto.time4th);
                this.time5th(dto.time5th);
            }
            
            toDto(): EstimateTimeDto{
                var dto: EstimateTimeDto = {
                    month: this.month(),
                    time1st: this.time1st(),
                    time2nd: this.time2nd(),
                    time3rd: this.time3rd(),
                    time4th: this.time4th(),
                    time5th: this.time5th()
                }
                return dto; 
            }
        }
        
        export class CompanyEstimateTimeModel{
            monthlyEstimates: EstimateTimeModel[];
            yearlyEstimate: EstimateTimeModel;
            
            constructor(){
                this.monthlyEstimates = [];
                this.yearlyEstimate = new EstimateTimeModel();    
            }
            
            updateData(dto: CompanyEstimateTimeDto) {
                this.monthlyEstimates = [];
                for (var item of dto.monthlyEstimates) {
                    var model: EstimateTimeModel = new EstimateTimeModel();
                    model.updateData(item);
                    this.monthlyEstimates.push(model);
                }
                this.yearlyEstimate.updateData(dto.yearlyEstimate);
            }
            
            toDto(): CompanyEstimateTimeDto{
                var monthlyEstimateTime: EstimateTimeDto[] = [];
                for (var item of this.monthlyEstimates) {
                    monthlyEstimateTime.push(item.toDto());
                }
                var dto: CompanyEstimateTimeDto = {
                    monthlyEstimates: monthlyEstimateTime,
                    yearlyEstimate: this.yearlyEstimate.toDto()
                };
                return dto;
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
        
        export interface EmployeeSearchDto {
            employeeId: string;

            employeeCode: string;

            employeeName: string;

            workplaceCode: string;

            workplaceId: string;

            workplaceName: string;
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