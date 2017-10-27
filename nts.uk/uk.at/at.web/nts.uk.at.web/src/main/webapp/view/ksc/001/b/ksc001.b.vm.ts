module nts.uk.at.view.ksc001.b {

    import NtsWizardStep = service.model.NtsWizardStep;
    
    export module viewmodel {
        export class ScreenModel {
            stepList: Array<NtsWizardStep>;
            stepSelected: KnockoutObservable<NtsWizardStep>;
            ccgcomponent: GroupOption;
            showinfoSelectedEmployee: KnockoutObservable<boolean>;

            // Options
            baseDate: KnockoutObservable<Date>;
            selectedEmployee: KnockoutObservableArray<EmployeeSearchDto>;

            selectSchedules: KnockoutObservableArray<any>;
            selectedRuleCode: any;
            checkAllCase: KnockoutObservable<boolean>;
             enable: KnockoutObservable<boolean>;
            required: KnockoutObservable<boolean>;
            dateValue: KnockoutObservable<any>;
            startDateString: KnockoutObservable<string>;
            endDateString: KnockoutObservable<string>;
            constructor() {
                var self = this;
                self.enable = ko.observable(true);
                self.required = ko.observable(true);

                self.startDateString = ko.observable("");
                self.endDateString = ko.observable("");
                self.dateValue = ko.observable({});

                self.startDateString.subscribe(function(value) {
                    self.dateValue().startDate = value;
                    self.dateValue.valueHasMutated();
                });

                self.endDateString.subscribe(function(value) {
                    self.dateValue().endDate = value;
                    self.dateValue.valueHasMutated();
                });
                self.stepList = [
                    { content: '.step-1' },
                    { content: '.step-2' },
                    { content: '.step-3' },
                    { content: '.step-4' },
                    { content: '.step-5' },
                    { content: '.step-6' }
                ];
                self.selectedEmployee = ko.observableArray([]);
                self.showinfoSelectedEmployee = ko.observable(false);
                self.baseDate = ko.observable(new Date());

                self.checkAllCase = ko.observable(true);
                self.ccgcomponent = {
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
                        self.showinfoSelectedEmployee(true);
                        self.selectedEmployee(dataList);
                    },
                    onSearchOnlyClicked: function(data: EmployeeSearchDto) {
                        self.showinfoSelectedEmployee(true);
                        var dataEmployee: EmployeeSearchDto[] = [];
                        dataEmployee.push(data);
                        self.selectedEmployee(dataEmployee);
                    },
                    onSearchOfWorkplaceClicked: function(dataList: EmployeeSearchDto[]) {
                        self.showinfoSelectedEmployee(true);
                        self.selectedEmployee(dataList);
                    },
                    onSearchWorkplaceChildClicked: function(dataList: EmployeeSearchDto[]) {
                        self.showinfoSelectedEmployee(true);
                        self.selectedEmployee(dataList);
                    },
                    onApplyEmployee: function(dataEmployee: EmployeeSearchDto[]) {
                        self.showinfoSelectedEmployee(true);
                        self.selectedEmployee(dataEmployee);
                    }

                }
                self.stepSelected = ko.observable({ id: 'step-1', content: '.step-1' });
                
                
                self.selectSchedules = ko.observableArray([
                    { code: '1', name: nts.uk.resource.getText("KSC001_74") },
                    { code: '2', name: nts.uk.resource.getText("KSC001_75") }
                ]);
                self.selectedRuleCode = ko.observable(1);
                        
            }

            begin() {
                $('#wizard').ntsWizard("begin");
            }
            end() {
                $('#wizard').ntsWizard("end");
            }
            next() {
                $('#wizard').ntsWizard("next");
            }
            previous() {
                $('#wizard').ntsWizard("prev");
            }
            getCurrentStep() {
                alert($('#wizard').ntsWizard("getCurrentStep"));
            }
            goto() {
                var index = this.stepList.indexOf(this.stepSelected());
                $('#wizard').ntsWizard("goto", index);
            }
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