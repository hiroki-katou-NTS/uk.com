module nts.uk.at.view.ksm005.c {
    
    import MonthlyPatternDto = service.model.MonthlyPatternDto;
    import MonthlyPatternSettingDto = service.model.MonthlyPatternSettingDto;
    import CopyMonthlyPatternSettingDto = service.model.CopyMonthlyPatternSettingDto;
    import HistoryDto = service.model.HistoryDto;

    export module viewmodel {

        export class ScreenModel {
            ccgcomponent: GroupOption;
            
            enableSave: KnockoutObservable<boolean>;
            enableCopy: KnockoutObservable<boolean>;
            
            //copy mode
            listDestSid: KnockoutObservableArray<string[]>;

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
                
                //copy mode
                self.listDestSid = ko.observableArray([]);
                
                self.enableSave = ko.observable(true);
                self.enableCopy = ko.observable(false);
                
                // list hist
                self.histList = ko.observableArray([]);
                self.histName = ko.observable('');
                self.currentHist = ko.observable(3);
                self.selectedHist = ko.observable(null)
                self.isEnableListHist = ko.observable(false);
                self.selectedHists = ko.observableArray([]);
                
                // list monthly pattern
                self.monthlyPatternList = ko.observableArray([]);
                self.selectedmonthlyPattern = ko.observable('');
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
                        self.applySelectEmployeeCode(employeeCode).done(function(){
                            if (self.histList().length > 0){
                                self.selectedHists(self.histList()[self.histList().length - 1].historyId);
                            }
                        });
                    }else {
                        self.enableDelete(false);
                        self.enableSystemChange(false);
                        self.employeeName('');  
                        self.monthlyPatternSetting('');
                        self.selectedmonthlyPattern(self.monthlyPatternList()[0]);
                        self.enableCopy(false);
                        self.enableSave(false);
                    }
                });
                
                self.selectedHists.subscribe(function(newValue) {
                    
                    if(self.histList().filter(e => e.historyId == newValue && e.textDisplay.indexOf("9999/12/31") == -1).length > 0){
                        self.enableSave(false);
                    }
                    self.findMonthlyPatternSetting(newValue);
                });
            }
            
            public start_page(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                var monthlyPatternData: MonthlyPatterModel[] = [new MonthlyPatterModel('000', 'なし') ];
                 service.getListMonthlyPattern().done(function(data) {
                        data.forEach(function(item){
                            monthlyPatternData.push(new MonthlyPatterModel(item.code, item.name));
                        });
                        self.monthlyPatternList(monthlyPatternData);
                        $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent);
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
                
                self.findAllByEmployeeIds(self.getAllEmployeeIdBySearch()).done(function(data){
                    if (data != null){
                        self.alreadySettingList(data);
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
                        maxRows: 15,
                        maxWidth: 450
                    }; 
                    $('#component-items-list').ntsListComponent(self.listComponentOption);                    
                });
                
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
            public applySelectEmployeeCode(employeeCode: string): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                var historyData: HistModel[] = [];
                var textDisplay = "";
                if (employeeCode) {
                    self.employeeName((self.findByCodeEmployee(employeeCode)).name);
                    service.getListHistory(self.findEmployeeIdByCode(employeeCode)).done(function (data){
                        console.log(data);
                        if(data != null){
                             data.forEach(function(item){
                              textDisplay = item.period.startDate + " " + nts.uk.resource.getText("CMM011_26") + " " + item.period.endDate;
                              historyData.push(new HistModel(item.historyId, textDisplay));
                            });
                            self.isEnableListMonthlyPattern(true);
                            self.isEnableListHist(true);
                            self.enableSave(true);
                        } else {
                            self.isEnableListMonthlyPattern(true);
                            self.isEnableListHist(false);
                            self.enableSave(false);
                            self.enableCopy(false);
                            self.enableDelete(false);
                        }
                        self.histList(historyData);
//                        self.findMonthlyPatternSetting(self.selectedHists());
                        dfd.resolve();
                    });
                }
                return dfd.promise(); 
            }
            
            public findMonthlyPatternSetting(historyId: string): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                service.findByIdMonthlyPatternSetting(historyId).done(function(data: MonthlyPatternSettingDto) {
                    console.log(data);
                    if (data != null) {
                        if (data.monthlyPatternCode != "") {
                            self.selectedmonthlyPattern(data.monthlyPatternCode);
                            self.selectedHists(data.historyId);
                            self.enableDelete(true);
                            self.enableSystemChange(true);
                            self.enableCopy(true);
                        }else {
                            self.selectedmonthlyPattern('000');
                            self.enableDelete(false);
                            self.enableCopy(false);
                            self.enableSystemChange(false);   
                        }
                    } else {
                        self.selectedmonthlyPattern('000');
                        self.selectedHists(null);
                        self.enableDelete(false);
                        self.enableCopy(false);
                        self.enableSystemChange(true);
                    }
                    dfd.resolve();
                });   
                return dfd.promise(); 
            }
            
            /**
             * call service find all by employee id
             */
            public findAllByEmployeeIds(employeeIds: string[]) : JQueryPromise<UnitAlreadySettingModel[]> {
                var dfd = $.Deferred();
                var dataRes: UnitAlreadySettingModel[] = [];
                var self = this;
                service.findAllMonthlyPatternSetting(employeeIds).done(function(data) {
                    console.log(data);
                    if(data != null){
                        data.forEach(function(item){
                            var setting: UnitAlreadySettingModel;
                            setting = { code: self.findEmployeeCodeById(item.employeeId), isAlreadySetting: true };
                            dataRes.push(setting);
                        })
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
                self.applySelectEmployeeCode(self.selectedCode());
            }
            
            /**
             * call service save monthly pattern setting by on click button
             */
            public saveMonthlyPatternSetting(): void {
                var self = this;
                
                if (!self.selectedHists()) {
//                    nts.uk.ui.dialog.alertError({ messageId: "Msg_189" });
                    return;
                }
                
                if (!self.selectedCode()) {
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_189" });
                    return;
                }
                if (self.selectedmonthlyPattern() == "000") {
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_190" });
                    return;
                }
                var dto = {employeeId: self.findEmployeeIdByCode(self.selectedCode()), historyId: self.selectedHists(), monthlyPatternCode: self.selectedmonthlyPattern()};
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
             * open dialog copy monthly pattern setting by on click button
             */
            public openDialogCopy(): void {
                var self = this;
                if (!self.selectedCode()) {
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_189" });
                    return;
                }
                
                let dataSource = $('#component-items-list').getDataList();
                let itemListSetting = dataSource.filter(e => e.isAlreadySetting == true).map(e => self.findEmployeeIdByCode(e.code));
                
                let object: IObjectDuplication = {
                    code: self.selectedCode(),
                    name: dataSource.filter(e => e.code == self.selectedCode())[0].name,
                    targetType: TargetType.WORKPLACE_PERSONAL,
                    itemListSetting: itemListSetting,
                    baseDate: new Date()
                };
                
                // create object has data type IObjectDuplication and use:
                nts.uk.ui.windows.setShared("CDL023Input", object);
                
                // open dialog
                nts.uk.ui.windows.sub.modal('com','/view/cdl/023/a/index.xhtml').onClosed(() => {
                    // show data respond
                    let lstSelection: Array<string> = nts.uk.ui.windows.getShared("CDL023Output");
                    self.listDestSid(lstSelection);
                    console.log(lstSelection);
                });
            }
            
             /**
             * call service copy monthly pattern setting
             */
            public copyMonthlyPatternSetting(): void {
                var self = this;
                if (!self.selectedCode()) {
                    return;
                }
                var dto : CopyMonthlyPatternSettingDto = {
                    destSid: self.listDestSid(),
                    sourceSid: self.findEmployeeIdByCode(self.selectedCode()),
                    isOverwrite: 0
                };
                
                service.copyMonthlyPatternSetting(dto).done(function() {
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
                dto = { employeeId: self.findEmployeeIdByCode(self.selectedCode()),historyId: self.selectedHists(), monthlyPatternCode: self.selectedmonthlyPattern() };
                nts.uk.ui.dialog.confirm({ messageId: 'Msg_18' }).ifYes(function() {
                    service.deleteMonthlyPatternSetting(dto).done(function() {
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(function() {
                            // reload page
                            self.reloadPage();
                            self.selectedmonthlyPattern('000');
                            self.selectedHists(null);
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
    
    /**
     * IObjectDuplication
     */
    export interface IObjectDuplication {
        code: string;
        name: string;
        targetType: number;
        itemListSetting: Array<string>;
        baseDate?: Date;
    }
    
    /**
     * TargetType
     */
    export class TargetType {
        
        // 雇用
        static EMPLOYMENT = 1;
        
        // 分類
        static CLASSIFICATION = 2;
        
        // 職位
        static JOB_TITLE = 3;
        
        // 職場
        static WORKPLACE = 4;
        
        // 部門
        static DEPARTMENT = 5;
        
        // 職場個人
        static WORKPLACE_PERSONAL = 6;
        
        // 部門個人
        static DEPARTMENT_PERSONAL = 7;
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