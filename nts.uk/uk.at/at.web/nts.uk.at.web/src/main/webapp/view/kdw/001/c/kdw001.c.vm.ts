module nts.uk.at.view.kdw001.c {
    import getText = nts.uk.resource.getText;
    
    export module viewmodel {
        export class ScreenModel {
            
            //Declare screenName flag to forward screen B or screen C
            screenName: string;
            
            //Declare kcp005 list properties
            listComponentOption: any;
            selectedCode: KnockoutObservable<string>;
            multiSelectedCode: KnockoutObservableArray<string>;
            isShowAlreadySet: KnockoutObservable<boolean>;
            alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
            isDialog: KnockoutObservable<boolean>;
            isShowNoSelectRow: KnockoutObservable<boolean>;
            isMultiSelect: KnockoutObservable<boolean>;
            isShowWorkPlaceName: KnockoutObservable<boolean>;
            isShowSelectAllButton: KnockoutObservable<boolean>;
            employeeList: KnockoutObservableArray<UnitModel>;

            //Declare time range input
            enable: KnockoutObservable<boolean>;
            required: KnockoutObservable<boolean>;
            dateValue: KnockoutObservable<any>;
            startDateString: KnockoutObservable<string>;
            endDateString: KnockoutObservable<string>;

            //Declare employee filter component
            ccgcomponent: GroupOption;
            showinfoSelectedEmployee: KnockoutObservable<boolean>;
            // Options
            baseDate: KnockoutObservable<Date>;
            selectedEmployee: KnockoutObservableArray<EmployeeSearchDto>;

            constructor() {

                var self = this;
                
                //Get screenName value from a screen
                __viewContext.transferred.ifPresent(data => {
                    self.screenName = data.screenName;
                });
                
                //Init kcp005 properties
                self.baseDate = ko.observable(new Date());
                self.selectedCode = ko.observable('2');
                self.multiSelectedCode = ko.observableArray(['0', '1', '4']);
                self.isShowAlreadySet = ko.observable(false);
                self.alreadySettingList = ko.observableArray([
                    { code: '1', isAlreadySetting: true },
                    { code: '2', isAlreadySetting: true }
                ]);
                self.isDialog = ko.observable(true);
                self.isShowNoSelectRow = ko.observable(false);
                self.isMultiSelect = ko.observable(false);
                self.isShowWorkPlaceName = ko.observable(true);
                self.isShowSelectAllButton = ko.observable(false);
    
                this.employeeList = ko.observableArray<UnitModel>([]);
                
                self.listComponentOption = {
                    isShowAlreadySet: self.isShowAlreadySet(),
                    isMultiSelect: true,
                    listType: ListType.EMPLOYEE,
                    employeeInputList: self.employeeList,
                    selectType: SelectType.SELECT_BY_SELECTED_CODE,
                    selectedCode: self.selectedCode,
                    isDialog: self.isDialog(),
                    isShowNoSelectRow: self.isShowNoSelectRow(),
                    alreadySettingList: self.alreadySettingList,
                    isShowWorkPlaceName: self.isShowWorkPlaceName(),
                    isShowSelectAllButton: self.isShowSelectAllButton()
                };

             

                //Init time range input
                self.enable = ko.observable(true);
                self.required = ko.observable(true);

                let today = new Date;
                self.dateValue = ko.observable({});
                self.dateValue().startDate = new Date(today.getFullYear() - 1, today.getMonth(), today.getDate() + 2);
                self.dateValue().endDate = today;

                self.startDateString = ko.observable("");
                self.endDateString = ko.observable("");

                self.startDateString.subscribe(function(value) {
                    self.dateValue().startDate = value;
                    self.dateValue.valueHasMutated();
                });

                self.endDateString.subscribe(function(value) {
                    self.dateValue().endDate = value;
                    self.dateValue.valueHasMutated();
                });

                //Init employee filter component
                self.selectedEmployee = ko.observableArray([]);
                self.showinfoSelectedEmployee = ko.observable(false);
                self.baseDate = ko.observable(new Date());
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

                        //Convert list Object from server to view model list
                        let items = _.map(dataList, item => {
                            return new UnitModel(item);
                        });
                        self.employeeList(items);
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

                        //Convert list Object from server to view model list
                        let items = _.map(dataList, item => {
                            return new UnitModel(item);
                        });
                        self.employeeList(items);
                    },
                    onSearchWorkplaceChildClicked: function(dataList: EmployeeSearchDto[]) {
                        self.showinfoSelectedEmployee(true);
                        self.selectedEmployee(dataList);

                        //Convert list Object from server to view model list
                        let items = _.map(dataList, item => {
                            return new UnitModel(item);
                        });
                        self.employeeList(items);
                    },
                    onApplyEmployee: function(dataEmployee: EmployeeSearchDto[]) {
                        self.showinfoSelectedEmployee(true);
                        self.selectedEmployee(dataEmployee);

                        //Convert list Object from server to view model list
                        let items = _.map(dataEmployee, item => {
                            return new UnitModel(item);
                        });
                        self.employeeList(items);
                    }

                }


            }
            
            opendScreenBorJ() {
//                let self = this;
//                if(self.screenName == "B"){
//                    nts.uk.request.jump("/view/kdw/001/b/index.xhtml", {"activeStep": 1});    
//                }else{
//                    nts.uk.request.jump("/view/kdw/001/j/index.xhtml", {"activeStep": 1});
//                }
                $("#wizard").ntsWizard("next");        
            }
            
            start() {
                var self = this;
                $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent); 
                $('#component-items-list').ntsListComponent(self.listComponentOption);
            }

        }

        //Object for kcp005
        export class ListType {
            static EMPLOYMENT = 1;
            static Classification = 2;
            static JOB_TITLE = 3;
            static EMPLOYEE = 4;
        }

        export class UnitModel {
            code: string;
            name: string;
            workplaceName: string;
            isAlreadySetting: boolean;
            constructor(x: EmployeeSearchDto) {
                let self = this;
                if (x) {
                    self.code = x.employeeCode;
                    self.name = x.employeeName;
                    self.workplaceName = x.workplaceName;
                    self.isAlreadySetting = false;
                } else {
                    self.code = "";
                    self.name = "";
                    self.workplaceName = "";
                    self.isAlreadySetting = false;
                }
            }
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

        //Object for filter component        
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

        export interface EmployeeSearchDto {
            employeeId: string;

            employeeCode: string;

            employeeName: string;

            workplaceCode: string;

            workplaceId: string;

            workplaceName: string;
        }

    }
}
