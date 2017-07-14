module nts.uk.at.view.ksm005.c {
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

    export module viewmodel {

        export class ScreenModel {
            ccgcomponent: GroupOption;

            // Options
            baseDate: KnockoutObservable<Date>;
            selectedEmployee: KnockoutObservableArray<EmployeeSearchDto>;
            
           listComponentOption: any;
           selectedCode: KnockoutObservable<string>;
           alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
           isShowNoSelectRow: KnockoutObservable<boolean>;
           employeeList: KnockoutObservableArray<UnitModel>;
                                                


           constructor() {
               var self = this;
               self.selectedEmployee = ko.observableArray([]);
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

               $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent);

               self.selectedCode = ko.observable('');
               self.alreadySettingList = ko.observableArray([
                   { code: '1', isAlreadySetting: true },
                   { code: '2', isAlreadySetting: true }
               ]);
               self.isShowNoSelectRow = ko.observable(false);
               self.employeeList = ko.observableArray<UnitModel>([]);
               self.applyKCP005ContentSearch([]);
               $('#component-items-list').ntsListComponent(self.listComponentOption);

           }
            public applyKCP005ContentSearch(dataList: EmployeeSearchDto[]): void {
                var self = this;
                self.employeeList([]);
                var employeeSearchs: UnitModel[] = [];
                for(var employeeSearch: EmployeeSearchDto of dataList){
                    var employee: UnitModel = {
                        code:  employeeSearch.employeeCode,
                        name:  employeeSearch.employeeName,
                        workplaceName: employeeSearch.workplaceName
                    }; 
                    employeeSearchs.push(employee);
                }
                self.employeeList(employeeSearchs);
                if(dataList.length > 0){
                    self.selectedCode(dataList[0].employeeCode);    
                }
                self.listComponentOption = {
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
                    maxRows :15
                };
                
            }
        
        }

    }
}