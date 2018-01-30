module nts.uk.at.view.ksm005.c {
    
    import MonthlyPatternDto = service.model.MonthlyPatternDto;
    import MonthlyPatternSettingDto = service.model.MonthlyPatternSettingDto;
    import HistoryDto = service.model.HistoryDto;

    export module viewmodel {

        export class ScreenModel {
            ccgcomponent: GroupOption;
            
            enableSave: KnockoutObservable<boolean>;

            // Options
            baseDate: KnockoutObservable<Date>;
            selectedEmployee: KnockoutObservableArray<any>;

            listComponentOption: any;
            selectedCode: KnockoutObservable<any>;
            monthlyPatternCode: string;
            monthlyPatternSetting: KnockoutObservable<string>;
            employeeName: KnockoutObservable<string>;
            enableDelete: KnockoutObservable<boolean>;
            enableSystemChange: KnockoutObservable<boolean>;
            alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
            isShowNoSelectRow: KnockoutObservable<boolean>;
            employeeList: KnockoutObservableArray<UnitModel>;
            
            // list hist
            histList: KnockoutObservableArray<any>;
            histName: KnockoutObservable<string>;
            currentHist: KnockoutObservable<number>
            selectedHist: KnockoutObservable<string>;
            selectedHists: KnockoutObservableArray<any>;
            isEnableListHist: KnockoutObservable<boolean>;
            
            // monthly pattern list
            monthlyPatternList: KnockoutObservableArray<any>;
            selectedmonthlyPattern: KnockoutObservable<string>;
            isEnableListMonthlyPattern: KnockoutObservable<boolean>;
            isEditableListMonthlyPattern: KnockoutObservable<boolean>;

            constructor() {
                var self = this;
                
                self.enableSave = ko.observable(true);
                
                // list hist
                self.histList = ko.observableArray([]);
                self.histName = ko.observable('');
                self.currentHist = ko.observable(3);
                self.selectedHist = ko.observable(null)
                self.isEnableListHist = ko.observable(true);
                self.selectedHists = ko.observableArray([]);
                
                // list monthly pattern
                self.monthlyPatternList = ko.observableArray([]);
                self.selectedmonthlyPattern = ko.observable('1');
                self.isEnableListMonthlyPattern = ko.observable(true);
                self.isEditableListMonthlyPattern = ko.observable(false);
                
                self.selectedEmployee = ko.observableArray([]);
                self.baseDate = ko.observable(new Date());
                self.monthlyPatternSetting = ko.observable('');
                self.employeeName = ko.observable('');
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

//                $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent);

                self.selectedCode = ko.observable('');
                self.alreadySettingList = ko.observableArray([]);
                self.isShowNoSelectRow = ko.observable(false);
                self.employeeList = ko.observableArray<UnitModel>([]);
                self.applyKCP005ContentSearch([]);
//                $('#component-items-list').ntsListComponent(self.listComponentOption);

                self.selectedCode.subscribe(function(employeeCode: string) {
                    if (employeeCode) {
                        self.applySelectEmployeeCode(employeeCode);
                    }else {
                        self.enableDelete(false);
                        self.enableSystemChange(false);  
                        self.employeeName('');  
                        self.monthlyPatternSetting('');
                    }
                });
            }
            
            public start_page(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                var monthlyPatternData: MonthlyPatterModel[] = [];
                 $.when(service.getListMonthlyPattern())
                    .done(function(data) {
                        data.forEach(function(item){
                            monthlyPatternData.push(new MonthlyPatterModel(item.code, item.name));
                        });
                        self.monthlyPatternList(monthlyPatternData);
                        $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent);
                        $('#component-items-list').ntsListComponent(self.listComponentOption);
                        dfd.resolve();
                }); 
                
                return dfd.promise();
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
                        code: employeeSearch.employeeCode,
                        name: employeeSearch.employeeName,
                        workplaceName: employeeSearch.workplaceName
                    };
                    employeeSearchs.push(employee);
                }
                self.employeeList(employeeSearchs);
                if (dataList.length > 0) {
                    self.selectedCode(dataList[0].employeeCode);
                }
                
//                self.findAllByEmployeeIds(self.getAllEmployeeIdBySearch()).done(function(data){
//                    self.alreadySettingList(data);                       
//                });
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
                    maxRows: 15,
                    maxWidth: 450
                }; 
                
            }

            
            /**
             * update selected employee kcp005 => detail
             */
            public findByCodeEmployee(employeeCode: string): UnitModel {
                var employee: UnitModel;
                var self = this;
                for (var employeeSelect of self.employeeList()) {
                    if (employeeSelect.code === employeeCode) {
                        employee = employeeSelect;
                        break;
                    }
                }
                return employee;
            }
            
            /**
             * get all employee id by search data CCG out put 
             */
            public getAllEmployeeIdBySearch(): string[] {
                var self = this;
                var employeeIds: string[] = [];
                for (var employeeSelect of self.employeeList()) {
                    employeeIds.push(self.findEmployeeIdByCode(employeeSelect.code));
                }
                return employeeIds;
            }
            
            /**
             * find employee id in selected
             */
            public findEmployeeIdByCode(employeeCode: string): string{
                var self = this;
                var employeeId = '';
                for (var employee of self.selectedEmployee()) {
                    if(employee.employeeCode === employeeCode){
                        employeeId = employee.employeeId;
                    }
                }
                return employeeId;
            }
            /**
             * find employee code in selected
             */
            public findEmployeeCodeById(employeeId: string): string{
                var self = this;
                var employeeCode = '';
                for (var employee of self.selectedEmployee()) {
                    if(employee.employeeId === employeeId){
                        employeeCode = employee.employeeCode;
                    }
                }
                return employeeCode;
            }
            
             /**
             *  apply info monthly pattern setting 
             */
            public applySelectEmployeeCode(employeeCode: string){
                var self = this;
                var historyData: HistModel[] = [];
                var textDisplay = "";
                if (employeeCode) {
                    self.employeeName((self.findByCodeEmployee(employeeCode)).name);
                    service.getListHistory(self.findEmployeeIdByCode(employeeCode)).done(function (data){
                        console.log(data);
                        if(data != null){
                             data.forEach(function(item){
                              textDisplay = item.period.startDate + " - " + item.period.endDate;
                              historyData.push(new HistModel(item.historyId, textDisplay));
                            });
                            self.isEnableListMonthlyPattern(true);
                            self.isEnableListHist(true);
                            self.enableSave(true);
                        } else {
                            self.isEnableListMonthlyPattern(false);
                            self.isEnableListHist(false);
                            self.enableSave(false);
                        }
                        self.histList(historyData);
                    });
                    service.findByIdMonthlyPatternSetting(self.findEmployeeIdByCode(employeeCode), self.selectedHist()).done(function(data: MonthlyPatternSettingDto) {
                        console.log(data);
                        if (data != null) {
//                            if (data.info && data.info.code) {
                                self.selectedmonthlyPattern(data.monthlyPatternCode);
                                self.selectedHist(data.historyId);
                                self.enableDelete(true);
                                self.enableSystemChange(true);
//                            }else {
//                                self.monthlyPatternCode = '';
//                                self.monthlyPatternSetting('');
//                                self.enableDelete(true);
//                                self.enableSystemChange(true);   
//                            }
                        } else {
//                            self.monthlyPatternCode = '';
//                            self.monthlyPatternSetting('');
                            self.enableDelete(false);
                            self.enableSystemChange(true);
                        }
                    });
                }
            }
            /**
             * open dialog system change (f)
             */
            public onpenDialogSystemChange(): void {
                var self = this;
                nts.uk.ui.windows.setShared("employeeId",self.selectedCode());
                nts.uk.ui.windows.setShared("monthlyPatternCode",self.monthlyPatternCode);
                nts.uk.ui.windows.sub.modal("/view/ksm/005/f/index.xhtml").onClosed(function(){
                    var isCancel: boolean = nts.uk.ui.windows.getShared("isCancel");
                    if (!isCancel) {
                        service.findByIdMonthlyPattern(nts.uk.ui.windows.getShared("monthlyPatternCode")).done(function(data) {
                            self.monthlyPatternCode = data.code;
                            self.monthlyPatternSetting(data.code + ' ' + data.name);
                        });
                    }
                });
            }

            /**
             * call service find all by employee id
             */
            public findAllByEmployeeIds(employeeIds: string[]) : JQueryPromise<UnitAlreadySettingModel[]> {
                var dfd = $.Deferred();
                var dataRes: UnitAlreadySettingModel[] = [];
                var self = this;
                service.findAllMonthlyPatternSetting(employeeIds).done(function(data) {
                    for (var employeeId of data) {
                        var setting: UnitAlreadySettingModel;
                        setting = { code: self.findEmployeeCodeById(employeeId), isAlreadySetting: true };
                        dataRes.push(setting);
                    }
                    dfd.resolve(dataRes);
                });
                dfd.resolve();
                return dfd.promise();
            }
            /**
             * reload page 
             */
            public reloadPage(): void {
                var self = this;
//                self.findAllByEmployeeIds(self.getAllEmployeeIdBySearch()).done(function(data) {
//                    self.alreadySettingList(data);
//                });
                self.applySelectEmployeeCode(self.selectedCode());
            }
            
            /**
             * call service save monthly pattern setting by on click button
             */
            public saveMonthlyPatternSetting(): void {
                var self = this;
                var dto : MonthlyPatternSettingDto;
                if (!self.selectedCode()) {
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_189" });
                    return;
                }
                dto = {employeeId: self.findEmployeeIdByCode(self.selectedCode()), historyId: self.selectedHist(), monthlyPatternCode: self.monthlyPatternCode};
                service.saveMonthlyPatternSetting(dto).done(function() {
                    // show message 15
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                        // reload page
                        self.reloadPage();
                    });
                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError(error);
                });    
            }
            /**
             * call service delete monthly pattern setting by on click button 
             */
            public deleteMonthlyPatternSetting(): void {
                var self = this;
                var dto: MonthlyPatternSettingDto;
                dto = { employeeId: self.findEmployeeIdByCode(self.selectedCode()),historyId: self.selectedHist(), monthlyPatternCode: self.monthlyPatternCode };
                nts.uk.ui.dialog.confirm({ messageId: 'Msg_18' }).ifYes(function() {
                    service.deleteMonthlyPatternSetting(dto).done(function() {
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(function() {
                            // reload page
                            self.reloadPage();
                        });
                    }).fail(function(error) {
                        nts.uk.ui.dialog.alertError(error);
                    });
                }).ifNo(function() {
                    self.reloadPage();
                })
            }
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
    
    export class HistModel {
        historyId: string;
        textDisplay: string;
        
        constructor(historyId: string, textDisplay: string) {
            this.historyId = historyId;
            this.textDisplay = textDisplay;
        }
    }
    
    export class MonthlyPatterModel {
        code: string;
        name: string;
       
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
}