module nts.uk.at.view.ksm005.c {
    
    import MonthlyPatternDto = service.model.MonthlyPatternDto;
    import MonthlyPatternSettingDto = service.model.MonthlyPatternSettingDto;
    import MonthlyPatternSettingActionDto = service.model.MonthlyPatternSettingActionDto;
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
            monthlyPatternCode: string;
            monthlyPatternSetting: KnockoutObservable<string>;
            employeeName: KnockoutObservable<string>;
            enableSave: KnockoutObservable<boolean>;
            enableDelete: KnockoutObservable<boolean>;
            enableSystemChange: KnockoutObservable<boolean>;
            alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
            isShowNoSelectRow: KnockoutObservable<boolean>;
            employeeList: KnockoutObservableArray<UnitModel>;



            constructor() {
                var self = this;
                self.selectedEmployee = ko.observableArray([]);
                self.baseDate = ko.observable(new Date());
                self.monthlyPatternSetting = ko.observable('');
                self.employeeName = ko.observable('');
                self.enableSave = ko.observable(false);
                self.enableDelete = ko.observable(false);
                self.enableSystemChange = ko.observable(false);
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
                self.alreadySettingList = ko.observableArray([]);
                self.isShowNoSelectRow = ko.observable(false);
                self.employeeList = ko.observableArray<UnitModel>([]);
                self.applyKCP005ContentSearch([]);
                $('#component-items-list').ntsListComponent(self.listComponentOption);

                self.selectedCode.subscribe(function(employeeId: string) {
                    self.applySelectEmployeeId(employeeId);
                });
            }
            /**
             * apply ccg001 search data to kcp005
             */
            public applyKCP005ContentSearch(dataList: EmployeeSearchDto[]): void {
                var self = this;
                self.employeeList([]);
                var employeeSearchs: UnitModel[] = [];
                for (var employeeSearch: EmployeeSearchDto of dataList) {
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
                
                self.findAllByEmployeeIds(self.getAllEmployeeIdBySearch()).done(function(data){
                    self.alreadySettingList(data);                       
                });
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
                    maxRows: 15
                }; 
                
            }

            /**
             * update selected employee kcp005 => detail
             */
            public findByIdEmployee(employeeId: string): UnitModel {
                var employee: UnitModel;
                var self = this;
                for (var employeeSelect: UnitModel of self.employeeList()) {
                    if (employeeSelect.code === employeeId) {
                        employee = employeeSelect;
                        break;
                    }
                }
                return employee;
            }
            
            /**
             * get all employee id by search data CCG out put 
             */
            public getAllEmployeeIdBySearch(): string[]{
                var self = this;
                var employeeIds: string[] = [];
                 for (var employeeSelect: UnitModel of self.employeeList()) {
                    employeeIds.push(employeeSelect.code);
                }
                return employeeIds;
            }
            
             /**
             *  apply info monthly pattern setting 
             */
            public applySelectEmployeeId(employeeId: string){
                var self = this;    
                self.employeeName(self.findByIdEmployee(employeeId).name);
                service.findByIdMonthlyPatternSetting(employeeId).done(function(data: MonthlyPatternSettingDto){
                    if (data.setting) {
                        self.monthlyPatternCode = data.info.code;
                        self.monthlyPatternSetting(data.info.code + ' ' + data.info.name);
                        self.enableSave(true);
                        self.enableDelete(true);
                        self.enableSystemChange(true);
                    } else {
                        self.monthlyPatternCode = '';
                        self.monthlyPatternSetting('');
                        self.enableSave(true);
                        self.enableDelete(false);
                        self.enableSystemChange(true);
                    }
                });
            }

            /**
             * open dialog system change (f)
             */
            public onpenDialogSystemChange(): void {
                var self = this;
                nts.uk.ui.windows.setShared("employeeId",self.selectedCode());
                nts.uk.ui.windows.setShared("monthlyPatternCode",self.monthlyPatternCode);
                nts.uk.ui.windows.sub.modal("/view/ksm/005/f/index.xhtml").onClosed(function(){
                    service.findByIdMonthlyPattern(nts.uk.ui.windows.getShared("monthlyPatternCode")).done(function(data){
                        self.monthlyPatternCode = data.code;
                        self.monthlyPatternSetting(data.code + ' ' + data.name);
                    });
                });
            }

            /**
             * call service find all by employee id
             */
            public findAllByEmployeeIds(employeeIds: string[]) : JQueryPromise<UnitAlreadySettingModel[]> {
                var dfd = $.Deferred();
                var dataRes: UnitAlreadySettingModel[] = [];
                service.findAllMonthlyPatternSetting(employeeIds).done(function(data) {
                    for (var employeeId: string of data) {
                        var setting: UnitAlreadySettingModel;
                        setting = { code: employeeId, isAlreadySetting: true };
                        dataRes.push(setting);
                    }
                    dfd.resolve(dataRes);
                });
                return dfd.promise();
            }
            /**
             * reload page 
             */
            public reloadPage(): void {
                var self = this;
                self.findAllByEmployeeIds(self.getAllEmployeeIdBySearch()).done(function(data) {
                    self.alreadySettingList(data);
                });
                self.applySelectEmployeeId(self.selectedCode());
            }
            
            /**
             * call service save monthly pattern setting by on click button
             */
            public saveMonthlyPatternSetting(): void {
                var self = this;
                var dto : MonthlyPatternSettingActionDto;
                dto = {employeeId: self.selectedCode(), monthlyPatternCode: self.monthlyPatternCode};
                service.saveMonthlyPatternSetting(dto).done(function() {
                    // show message 15
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                        // reload page
                        self.reloadPage();
                    });
                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError(error).then(function() {
                        self.reloadPage();
                    });
                });    
            }
            /**
             * call service delete monthly pattern setting by on click button 
             */
            public deleteMonthlyPatternSetting(): void {
                var self = this;
                var dto: MonthlyPatternSettingActionDto;
                dto = { employeeId: self.selectedCode(), monthlyPatternCode: self.monthlyPatternCode };
                nts.uk.ui.dialog.confirm({ messageId: 'Msg_18' }).ifYes(function() {
                    service.deleteMonthlyPatternSetting(dto).done(function() {
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(function() {
                            // reload page
                            self.reloadPage();
                        });
                    }).fail(function(error) {
                        nts.uk.ui.dialog.alertError(error).then(function() {
                            self.reloadPage();
                        });
                    });
                }).ifNo(function() {
                    self.reloadPage();
                })
            }
        }

    }
}