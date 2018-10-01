module nts.uk.at.view.ksm005.c {
    
    import MonthlyPatternDto = service.model.MonthlyPatternDto;
    import MonthlyPatternSettingDto = service.model.MonthlyPatternSettingDto;
    import CopyMonthlyPatternSettingDto = service.model.CopyMonthlyPatternSettingDto;
    import HistoryDto = service.model.HistoryDto;
    import blockUI = nts.uk.ui.block;
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
            periodStartDate: KnockoutObservable<moment.Moment>;
            periodEndDate: KnockoutObservable<moment.Moment>;

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
            optionalColumnDatasource: KnockoutObservableArray<any>;
            
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
            
            filtedSids: KnockoutObservableArray<string>;

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
                self.optionalColumnDatasource = ko.observableArray([]);
                
                // list monthly pattern
                self.monthlyPatternList = ko.observableArray([]);
                self.selectedmonthlyPattern = ko.observable('');
                self.isEnableListMonthlyPattern = ko.observable(true);
                self.isEditableListMonthlyPattern = ko.observable(false);
                
                self.selectedEmployee = ko.observableArray([]);
                self.baseDate = ko.observable(new Date());
                self.periodStartDate = ko.observable(moment());
                self.periodEndDate = ko.observable(moment());
                
                self.monthlyPatternSetting = ko.observable('');
                self.employeeName = ko.observable('');
                self.enableDelete = ko.observable(false);
                self.enableSystemChange = ko.observable(false);
                self.filtedSids = ko.observableArray([]);
                self.ccgcomponent = {
                     
                    /** Common properties */
                    systemType: 1, // システム区分
                    showEmployeeSelection: false, // 検索タイプ
                    showQuickSearchTab: false, // クイック検索
                    showAdvancedSearchTab: true, // 詳細検索
                    showBaseDate: false, // 基準日利用
                    showClosure: false, // 就業締め日利用
                    showAllClosure: false, // 全締め表示
                    showPeriod: false, // 対象期間利用
                    periodFormatYM: true, // 対象期間精度

                    /** Required parameter */
                    baseDate: self.baseDate().toISOString(), // 基準日
                    periodStartDate: moment(self.periodStartDate()).format("YYYY-MM-DD"), // 対象期間開始日
                    periodEndDate: moment(self.periodStartDate()).format("YYYY-MM-DD"), // 対象期間終了日
                    inService: true, // 在職区分
                    leaveOfAbsence: true, // 休職区分
                    closed: true, // 休業区分
                    retirement: true, // 退職区分
                    
                    /** Quick search tab options */
                    showAllReferableEmployee: true, // 参照可能な社員すべて
                    showOnlyMe: true, // 自分だけ
                    showSameWorkplace: true, // 同じ職場の社員
                    showSameWorkplaceAndChild: true, // 同じ職場とその配下の社員

                    /** Advanced search properties */
                    showEmployment: true, // 雇用条件
                    showWorkplace: true, // 職場条件
                    showClassification: true, // 分類条件
                    showJobTitle: true, // 職位条件
                    showWorktype: true, // 勤種条件
                    isMutipleCheck: true, // 選択モード
                    
                    /**
                    * @param dataList: list employee returned from component.
                    * Define how to use this list employee by yourself in the function's body.
                    */
//                    onSearchAllClicked: function(dataList: EmployeeSearchDto[]) {
//                        self.selectedEmployee(dataList);
//                        self.applyKCP005ContentSearch(dataList);
//                    },
//                    onSearchOnlyClicked: function(data: EmployeeSearchDto) {
//                        var dataEmployee: EmployeeSearchDto[] = [];
//                        dataEmployee.push(data);
//                        self.selectedEmployee(dataEmployee);
//                        self.applyKCP005ContentSearch(dataEmployee);
//                    },
//                    onSearchOfWorkplaceClicked: function(dataList: EmployeeSearchDto[]) {
//                        self.selectedEmployee(dataList);
//                        self.applyKCP005ContentSearch(dataList);
//                    },
//                    onSearchWorkplaceChildClicked: function(dataList: EmployeeSearchDto[]) {
//                        self.selectedEmployee(dataList);
//                        self.applyKCP005ContentSearch(dataList);
//                    },
//                    onApplyEmployee: function(dataEmployee: EmployeeSearchDto[]) {
//                        self.selectedEmployee(dataEmployee);
//                        self.applyKCP005ContentSearch(dataEmployee);
//                    }
                    
                    /** Return data */
                    returnDataFromCcg001: function(data: Ccg001ReturnedData) {
                        let sids: any = _.map(data.listEmployee, 'employeeId');
                        let tempList = [];
                        self.filterSids(sids).done((filterData:any)=>{
                            _.forEach(data.listEmployee,(item:any)=>{
                                if (!(filterData.indexOf(item.employeeId) > -1)) {
                                    tempList.push(item);
                                }
                            });
                            data.listEmployee = tempList;
                            self.selectedEmployee(data.listEmployee);
                            self.applyKCP005ContentSearch(data.listEmployee);
                        });
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
                                self.selectedHists(self.histList()[0].historyId);
                            } else {
                                self.selectedHists(null);
                                self.selectedHists.valueHasMutated();                              
                            }
                        });
                    } else {
                        self.selectedHists(null);
                        self.isEnableListHist(false);
                        self.histList([]);
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
//                        self.selectedHists(null);
                        self.enableSave(false);
                        self.enableDelete(false);
                        self.enableCopy(false);
                    } else {
                        self.enableSave(true);
                        self.enableDelete(true);
                        self.enableCopy(true);
                    }
                    self.findMonthlyPatternSetting(newValue);
                });
            }
            
            public filterSids(sids: any): JQueryPromise<any> {
                let dfd = $.Deferred();
                service.findWorkConditionBySids(sids).done((data: any) => {
                    dfd.resolve(data);
                });
                return dfd.promise();
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
                        id: employeeSearch.employeeId,
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
                    if (data.length > 0){
                        self.alreadySettingList(data); 
                    }
                    self.listComponentOption = {
                        isShowAlreadySet: false,
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
                        maxWidth: 450,
                        showOptionalColumn: true,
                        optionalColumnName: nts.uk.resource.getText('KSM005_18'),
                        optionalColumnDatasource: self.optionalColumnDatasource
                    }; 
                    //$('#component-items-list').ntsListComponent(self.listComponentOption);                    
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
                        if(data != null){
                             data.forEach(function(item){
                              textDisplay = item.period.startDate + " " + nts.uk.resource.getText("CMM011_26") + " " + item.period.endDate;
                              historyData.push(new HistModel(item.historyId, textDisplay));
                            });
                            self.isEnableListMonthlyPattern(true);
                            self.isEnableListHist(true);
                        } else {
                            self.isEnableListMonthlyPattern(true);
                            self.isEnableListHist(false);
                            self.enableSave(false);
                            self.enableCopy(false);
                            self.enableDelete(false);
                        }
                        self.histList(historyData);
                        dfd.resolve();
                    });
                }
                return dfd.promise(); 
            }
            
            public findMonthlyPatternSetting(historyId: string): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                service.findByIdMonthlyPatternSetting(historyId).done(function(data: MonthlyPatternSettingDto) {
                    if (data != null) {
                        if (data.monthlyPatternCode != "") {
                            if (self.monthlyPatternList().filter(e => e.code == data.monthlyPatternCode).length <= 0) {
                                var dto: MonthlyPatternSettingDto;
                                dto = { 
                                        employeeId: self.findEmployeeIdByCode(self.selectedCode()),
                                        historyId: self.selectedHists(), 
                                        monthlyPatternCode: self.selectedmonthlyPattern() };
                                service.deleteMonthlyPatternSetting(dto).done(function() {
                                    // reload page
                                    self.reloadPage();
                                    self.enableCopy(false);
                                    self.enableDelete(false);
                                    self.selectedmonthlyPattern('000');
                                    if (self.histList().length > 0){
                                        self.selectedHists(self.histList()[0].historyId);
                                    }
                                }).fail(function(error) {
                                    nts.uk.ui.dialog.alertError(error);
                                });
                                return; 
                            }
                            self.selectedmonthlyPattern(data.monthlyPatternCode);                    
//                            self.enableDelete(true);
//                            self.enableSystemChange(true);
//                            self.enableCopy(true);
                            //self.selectedHists(data.historyId);
                        } else {
                            self.selectedmonthlyPattern('000');
                            self.enableDelete(false);
                            self.enableCopy(false);
                            self.enableSystemChange(false);   
                        }
                    } else {
                        self.selectedmonthlyPattern('000');                    
                        self.enableDelete(false);
                        self.enableCopy(false);
                        self.enableSystemChange(true);
                        //self.selectedHists(null);
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
                var dataSource: any = [];
                var self = this;
                var monthlyPatternCodes: string[] = [];
                self.monthlyPatternList().forEach(e => monthlyPatternCodes.push(e.code));
                service.findAllMonthlyPatternSetting(employeeIds, monthlyPatternCodes).done(function(data) {
                    if(data != null){
                        data.forEach(function(item){
                            var setting: UnitAlreadySettingModel;
                            var setingContent : OptionalColumnDataSource;
                            
                            var monthlyPatternName : string;
                            self.monthlyPatternList().length > 0 ? monthlyPatternName = self.monthlyPatternList().filter(ob => ob.code == item.monthlyPatternCode)[0].name : monthlyPatternName = "";
                            
                            setingContent = {empId: self.findEmployeeCodeById(item.employeeId) , content: monthlyPatternName};
                            dataSource.push(setingContent);
                            
                            setting = { code: self.findEmployeeCodeById(item.employeeId), isAlreadySetting: true }; 
                            dataRes.push(setting);    
                        });
                        self.alreadySettingList(dataRes);
                    }
                    self.optionalColumnDatasource(dataSource);
                    dfd.resolve(dataRes);
                });

                return dfd.promise();
            }
            /**
             * reload page 
             */
            public reloadPage(): JQueryPromise<void> {
                var self = this;
                var dfd =$.Deferred<void>();
                self.findAllByEmployeeIds(self.getAllEmployeeIdBySearch()).done(function(data) {
                    self.alreadySettingList(data);
                    self.applySelectEmployeeCode(self.selectedCode());
                    dfd.resolve();
                });
                
                return dfd.promise();
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
                blockUI.grayout();
                service.saveMonthlyPatternSetting(dto).done(function() {
                    // reload page
                    self.reloadPage().done(() => {
                        // show message 15
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {

                            self.enableCopy(true);
                            self.enableDelete(true);
                        });
                    }).always(()=> blockUI.clear());
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
                
                let dataSource = self.employeeList();
                let itemListSetting = _.map(self.alreadySettingList(), item => {
                    return _.find(dataSource, i => i.code == item.code).id;
                });
                
                let object: IObjectDuplication = {
                    code: self.selectedCode(),
                    name: dataSource.filter(e => e.code == self.selectedCode())[0].name,
                    targetType: TargetType.WORKPLACE_PERSONAL,
                    itemListSetting: itemListSetting,
                    baseDate: self.baseDate()
                };
                
                // create object has data type IObjectDuplication and use:
                nts.uk.ui.windows.setShared("CDL023Input", object);
                
                // open dialog
                nts.uk.ui.windows.sub.modal('com','/view/cdl/023/a/index.xhtml').onClosed(() => {
                    // show data respond
                    let lstSelection: any = nts.uk.ui.windows.getShared("CDL023Output");                 
                    if (!nts.uk.util.isNullOrEmpty(lstSelection)) {
                        self.listDestSid(lstSelection);
                        self.copyMonthlyPatternSetting();
                    }
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
                            self.enableCopy(false);
                            self.enableDelete(false);
                            self.selectedmonthlyPattern('000');
                            if (self.histList().length > 0){
                                self.selectedHists(self.histList()[0].historyId);
                            }
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
    
     export class EmployeeSearchDto {
        employeeId: string;

        employeeCode: string;

        employeeName: string;

        workplaceName: string;
    }

    export interface GroupOption {
         /** Common properties */
        showEmployeeSelection: boolean; // 検索タイプ
        systemType: number; // システム区分
        showQuickSearchTab: boolean; // クイック検索
        showAdvancedSearchTab: boolean; // 詳細検索
        showBaseDate: boolean; // 基準日利用
        showClosure: boolean; // 就業締め日利用
        showAllClosure: boolean; // 全締め表示
        showPeriod: boolean; // 対象期間利用
        periodFormatYM: boolean; // 対象期間精度

        /** Required parameter */
        baseDate?: string; // 基準日
        periodStartDate?: string; // 対象期間開始日
        periodEndDate?: string; // 対象期間終了日
        inService: boolean; // 在職区分
        leaveOfAbsence: boolean; // 休職区分
        closed: boolean; // 休業区分
        retirement: boolean; // 退職区分

        /** Quick search tab options */
        showAllReferableEmployee: boolean; // 参照可能な社員すべて
        showOnlyMe: boolean; // 自分だけ
        showSameWorkplace: boolean; // 同じ職場の社員
        showSameWorkplaceAndChild: boolean; // 同じ職場とその配下の社員

        /** Advanced search properties */
        showEmployment: boolean; // 雇用条件
        showWorkplace: boolean; // 職場条件
        showClassification: boolean; // 分類条件
        showJobTitle: boolean; // 職位条件
        showWorktype: boolean; // 勤種条件
        isMutipleCheck: boolean; // 選択モード
        // showDepartment: boolean; // 部門条件 not covered
        // showDelivery: boolean; not covered

        /** Data returned */
        returnDataFromCcg001: (data: Ccg001ReturnedData) => void;
    }
    
    export interface Ccg001ReturnedData {
        baseDate: string; // 基準日
        closureId?: number; // 締めID
        periodStart: string; // 対象期間（開始)
        periodEnd: string; // 対象期間（終了）
        listEmployee: Array<EmployeeSearchDto>; // 検索結果
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
        id: string;
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
    
    export interface OptionalColumnDataSource {
        empId: string;
        content: any;
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