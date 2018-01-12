module nts.uk.at.view.kal003.b.viewmodel{
    import block = nts.uk.ui.block;
    import errors = nts.uk.ui.errors;
    import dialog = nts.uk.ui.dialog;
    import windows = nts.uk.ui.windows;
    import resource = nts.uk.resource;
    import sharemodel = nts.uk.at.view.kal003.share.model;
    import shareutils = nts.uk.at.view.kal003.share.kal003utils;

    export class ScreenModel {
        currentErrAlaCheckCondition: KnockoutObservable<sharemodel.ErrorAlarmCondition>;
        // list item check
        listTypeCheckWorkRecords    : KnockoutObservableArray<model.EnumModel> = ko.observableArray([]);
        listSingleValueCompareTypes : KnockoutObservableArray<model.EnumModel> = ko.observableArray([]);
        listRangeCompareTypes       : KnockoutObservableArray<model.EnumModel> = ko.observableArray([]);
        listCompareTypes            : KnockoutObservableArray<model.EnumModel> = ko.observableArray([]);
        itemListTargetServiceType_BA1_2         : KnockoutObservableArray<model.EnumModel> = ko.observableArray([]);
        itemListTargetSelectionRange_BA1_5         : KnockoutObservableArray<model.EnumModel> = ko.observableArray([]);
        listAllWorkType         : Array<string> = ([]);
        listAllAttdItem         : Array<string> = ([]);
        listAllSettingTimeZone  : Array<string> = ([]);

        displayWorkTypeSelections_BA1_4         : KnockoutObservable<string> = ko.observable('');
        displayWorkTimeItemSelections_BA2_3     : KnockoutObservable<string> = ko.observable('');
        displayWorkingTimeZoneSelections_BA5_3  : KnockoutObservable<string> = ko.observable('');
        
        targetServiceTypeSelected_BA1_2         : KnockoutObservable<number> = ko.observable(1);
        targetSelectionRangeSelected_BA1_5      : KnockoutObservable<number> = ko.observable(0);
        
        private setting : sharemodel.ErrorAlarmCondition;
        swANDOR_B5_3 : KnockoutObservableArray<model.EnumModel> = ko.observableArray([]);
        swANDOR_B6_3 : KnockoutObservableArray<model.EnumModel> = ko.observableArray([]);
        swANDOR_B7_2 : KnockoutObservableArray<model.EnumModel> = ko.observableArray([]);
        enableComparisonMaxValue : KnockoutObservable<boolean> = ko.observable(false);
        constructor() {
            let self = this;
            let option = windows.getShared('inputKal003b');
            self.setting = $.extend({}, shareutils.getDefaultErrorAlarmCondition(0), option);
            self.currentErrAlaCheckCondition = ko.observable(self.setting);
            // change select item check
            self.currentErrAlaCheckCondition().checkItem.subscribe((itemCheck) => {
                errors.clearAll();
                if ((itemCheck && itemCheck != undefined) || itemCheck === 0) {
                    self.initialScreen();
                }
            });

            self.currentErrAlaCheckCondition().comparisonOperator.subscribe((comparisonOperatorId) => {
                errors.clearAll();
                self.settingEnableComparisonMaxValueField();
            });
        }

        //initial screen
        start(): JQueryPromise<any> {
            
            let self = this,
                dfd = $.Deferred();
            errors.clearAll();
            self.getAllEnums().done(function() {
                //initial screen - in case update
                self.initialScreen().done(() => {
                    dfd.resolve();
                }).always(() => {
                    dfd.reject();
                });
           }).always(() => {
               dfd.reject();
           });
            return dfd.promise();
        }

        /**
         * setting Enable/Disable Comparison of Max Value Field
         */
        private settingEnableComparisonMaxValueField() {
            let self = this;
            self.enableComparisonMaxValue(self.currentErrAlaCheckCondition().comparisonOperator() > 5);
        }

        /**
         * initial screen
         */
        private initialScreen() : JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            switch (self.currentErrAlaCheckCondition().category) {
            case enCategory.Daily:
                self.initialDaily().done(() => {
                    self.settingEnableComparisonMaxValueField();
                    dfd.resolve();
                });
                break;
            case enCategory.Weekly:
                dfd.resolve();
                break;
            case enCategory.Monthly:
                dfd.resolve();
                break;
            default:
                dfd.resolve();
                break;
            }
            return dfd.promise();
        }
        
        // ===========common begin ===================
        private getAllEnums() : JQueryPromise<any> {
            let self = this,
            dfd = $.Deferred();

            $.when(service.getEnumSingleValueCompareTypse(),
                    service.getEnumRangeCompareType(),
                    service.getEnumTypeCheckWorkRecord(),
                    service.getEnumTargetSelectionRange(),
                    service.getEnumTargetServiceType(),
                    service.getEnumLogicalOperator()).done((
                            listSingleValueCompareTypse : Array<model.EnumModel>,
                            lstRangeCompareType : Array<model.EnumModel>,
                            listTypeCheckWorkRecord : Array<model.EnumModel>,
                            listTargetSelectionRange : Array<model.EnumModel>,
                            listTargetServiceType : Array<model.EnumModel>,
                            listLogicalOperator : Array<model.EnumModel>) => {
                    self.listSingleValueCompareTypes(self.getLocalizedNameForEnum(listSingleValueCompareTypse));
                    self.listRangeCompareTypes(self.getLocalizedNameForEnum(lstRangeCompareType));
                    self.listTypeCheckWorkRecords(self.getLocalizedNameForEnum(listTypeCheckWorkRecord));
                    var listTargetRangeWithName = self.getLocalizedNameForEnum(listTargetSelectionRange);
                    self.itemListTargetSelectionRange_BA1_5(listTargetRangeWithName);
                    self.itemListTargetServiceType_BA1_2(self.getLocalizedNameForEnum(listTargetServiceType));
                    self.buildListCompareTypes();
                    var listANDOR = self.getLocalizedNameForEnum(listLogicalOperator)
                  //ENUM 論理演算子
                    self.swANDOR_B5_3 = ko.observableArray(listANDOR);
                    //ENUM 論理演算子
                    self.swANDOR_B6_3 = ko.observableArray(listANDOR);
                  //ENUM 論理演算子
                    self.swANDOR_B7_2 = ko.observableArray(listANDOR);
                    dfd.resolve();
               
            }).always(() => {
                dfd.resolve();
            });
            return dfd.promise();
        }
        
        /**
         * build List of Compare Types
         */
        private buildListCompareTypes() {
            let self = this;
            var listCompareTypes = self.listSingleValueCompareTypes().concat(self.listRangeCompareTypes());
            self.listCompareTypes(listCompareTypes);
        }
        
        /**
         * get Localize name by Enum
         * @param listEnum
         */
        private getLocalizedNameForEnum(listEnum : Array<model.EnumModel>) : Array<model.EnumModel> {
            if (listEnum) {
                for (var i = 0; i < listEnum.length; i++) {
                    if (listEnum[i].localizedName) {
                        listEnum[i].localizedName = resource.getText(listEnum[i].localizedName);
                    }
                }
                return listEnum;
            }
            return [];
        }

        /**
         * Initial Group Condition
         * @param listGroupCondition
         */
        private initGroupCondition(listGroupCondition : Array<sharemodel.ErAlAtdItemCondition>) : Array<sharemodel.ErAlAtdItemCondition> {
            let listCondition : Array<sharemodel.ErAlAtdItemCondition> = [];
            for(var i = 0; i < listGroupCondition.length && i < 3; i++) {
                listCondition.push(listGroupCondition[i]);
            }
            if (listCondition.length < 3) {
                for(var i = listCondition.length; i < 3; i++) {
                    listCondition.push(shareutils.getDefaultCondition(i-1));
                }
            }
            return listCondition;
        }
        
        /**
         * Initial Compound Group Condition
         */
        private initCompoundGroupCondition() {
            let self = this, currentErrAlaCheckCondition = self.currentErrAlaCheckCondition();
            let compoundCondition = currentErrAlaCheckCondition.compoundCondition();
            let listGr1 = self.initGroupCondition(compoundCondition.group1Condition().groupListCondition());
            compoundCondition.group1Condition().groupListCondition(listGr1);
            let listGr2 = self.initGroupCondition(compoundCondition.group2Condition().groupListCondition());
            compoundCondition.group2Condition().groupListCondition(listGr2);
        }
     // ============build enum for combobox BA2-5: end ==============
        // ===========common end =====================
        //==========Daily section Begin====================
        /**
         * Initial Daily
         */
        private initialDaily() : JQueryPromise<any> {
            let self = this,
            dfd = $.Deferred();
            switch (self.currentErrAlaCheckCondition().checkItem()) {
                case enItemCheck.Time:          //時間
                case enItemCheck.Times:         //回数
                case enItemCheck.AmountOfMoney: //金額
                case enItemCheck.TimeOfDate:    //時刻の場合
                    self.initialDailyItemChkItemComparison();
                    dfd.resolve();
                    break;
                case enItemCheck.CountinuousTime:   //連続時間
                    self.initialDailyItemChkCountinuousTime();
                    dfd.resolve();
                case enItemCheck.CountinuousWork:   //連続時間帯
                    self.initialDailyItemChkCountinuousWork();
                    dfd.resolve();
                case enItemCheck.CountinuousTimeZone: //連続勤務
                    self.initialDailyItemChkCountinuousTimeZone();
                    dfd.resolve();
                    break;
                case enItemCheck.CompoundCondition: //複合条件
                    self.initCompoundGroupCondition();
                    dfd.resolve();
                    break;
                default:
                    
                    dfd.resolve();
                    break;
            }
            return dfd.promise();
        }
        
        /**
         * initial in case check item : Time, Times, Amount of money, Time of day
         */
        private initialDailyItemChkItemComparison() {
            let self = this,
            currentErrAlaCheckCondition = self.currentErrAlaCheckCondition();
            //ドメインモデル「日次の勤怠項目」を取得する - Acquire domain model "DailyAttendanceItem"
            service.getDailyItemChkItemComparison(currentErrAlaCheckCondition.checkItem()).done((itemAttendances : Array<any>) => {
                self.listAllAttdItem = self.getListAttendanceIdFromDtos(itemAttendances);
                
                // build name of Attendance Item
                let listWorkTimeItemSelectedCode = self.currentErrAlaCheckCondition().workTimeItemSelections();
                self.generateNameCorrespondingToAttendanceItem(listWorkTimeItemSelectedCode).done((names) => {
                            self.displayWorkTimeItemSelections_BA2_3(names);
                });
                
                //ドメインモデル「勤務種類」を取得する - Acquire domain model "WorkType"
                self.initialWorkTypes();
            });
        }
        
        /**
         * Initial in case Daily Item Check Continuous Time
         */
        private initialDailyItemChkCountinuousTime() {
            let self = this;
            //ドメインモデル「日次の勤怠項目」を取得する - Acquire domain model "DailyAttendanceItem"
            service.getAttendCoutinousTime().done((itemAttendances) => {
                self.listAllAttdItem = self.getListAttendanceIdFromDtos(itemAttendances);

                // build name of Attendance Item
                let listWorkTimeItemSelectedCode = self.currentErrAlaCheckCondition().workTimeItemSelections();
                self.generateNameCorrespondingToAttendanceItem(listWorkTimeItemSelectedCode).done((names) => {
                    self.displayWorkTimeItemSelections_BA2_3(names);
                });
                
                //ドメインモデル「勤務種類」を取得する - Acquire domain model "WorkType"
                self.initialWorkTypes();
            });
        }
        
        /**
         * Initial in case Daily Item Check Continuous Work
         */
        private initialDailyItemChkCountinuousWork() {
            let self = this;
            //ドメインモデル「勤務種類」を取得する - Acquire domain model "WorkType"
            self.initialWorkTypes();
        }
        
        /**
         * Initial in case Daily Item Check Continuous Time zone
         */
        private initialDailyItemChkCountinuousTimeZone() {
            let self = this;
            //ドメインモデル「就業時間帯の設定」を取得する - Acquire domain model "WorkTimeSetting"
            service.getAttendCoutinousTimeZone().done((settingTimeZones) => {
                self.initialSettingTimeZoneCodesFromDtos(settingTimeZones);
              //ドメインモデル「勤務種類」を取得する - Acquire domain model "WorkType"
                self.initialWorkTypes();
            });
        }
        
        //TODO
        private initialDailyItemChkCompound() {
            let self = this,
            currentErrAlaCheckCondition = self.currentErrAlaCheckCondition();
            //アルゴリズム「複合条件の項目取得」を実行する - Execute the algorithm "item acquisition of compound condition"
            service.getAttendCompound(currentErrAlaCheckCondition.erAlCheckId).done((data) => {
                if (data) {
                    //TODO
                }
                //self.getAttendanceItemName();
            });
        }

        /**
         * アルゴリズム「勤怠項目に対応する名称を生成する」を実行する - Execute algorithm "Generate name corresponding to attendance item"
         * @param List<itemAttendanceId>
         */
        private generateNameCorrespondingToAttendanceItem(listAttendanceItemCode : Array<any>) : JQueryPromise<any> {
            let self = this,
            dfd = $.Deferred();
            if (listAttendanceItemCode && listAttendanceItemCode.length > 0) {
                /*convert to number array
                if (typeof listAttendanceItemCode[0] === 'string') {
                    for(var i=0; i<myArray.length; i++){
                        myArray[i] = parseInt(myArray[i], 10);
                    } 
                }
                */
                service.getAttendNameByIds(listAttendanceItemCode).done((dailyAttendanceItemNames) => {
                    if (dailyAttendanceItemNames && dailyAttendanceItemNames.length > 0) {
                        var attendanceName : string = '';
                        for(var i = 0; i < dailyAttendanceItemNames.length; i++) {
                            if (attendanceName) {
                                attendanceName = attendanceName + "," + dailyAttendanceItemNames[i].attendanceItemName;
                            } else {
                                attendanceName = dailyAttendanceItemNames[i].attendanceItemName;
                            }
                        }
                        dfd.resolve(attendanceName);
                    } else {
                        dfd.resolve('');
                    }
                }).always(() => {
                    dfd.resolve('');
                });
            } else {
                dfd.resolve('');
            }
            return dfd.promise();
        }

        /**
         * Build list of Attendance Item Name from List<AttdItemDto>
         * @param listAttendanceItemCode
         */
        private buildItemName(listItem : Array<any>) : string {
            let self = this, retNames : string = '';
            if (listItem) {
                for(var i = 0; i < listItem.length; i++) {
                    if (retNames) {
                        retNames = retNames + "," + listItem[i].name;
                    } else {
                        retNames = listItem[i].name;
                    }
                }
            }
            return retNames;
        }
        
        /**
         * //ドメインモデル「勤務種類」を取得する - Acquire domain model "WorkType"
         * 
         */
        private initialWorkTypes() : void {
            let self =this;
            self.listAllWorkType = [];
            service.getAttendCoutinousWork().done((workTypes) => {
                if (workTypes && workTypes != undefined) {
                    for(var i = 0; i < workTypes.length; i++) {
                        self.listAllWorkType.push(workTypes[i].workTypeCode);
                    }
                    self.displayWorkTypeSelections_BA1_4(self.buildItemName(workTypes));
                } else {
                    self.displayWorkTypeSelections_BA1_4('');
                }
            });
        }
        
        /**
         * Get list of attendance id from list Dtos
         * @param itemAttendances
         */
        private getListAttendanceIdFromDtos(itemAttendances : Array<any>) : Array<string> {
            let listAllAttdItemCode : Array<string> = [];
            if (itemAttendances && itemAttendances != undefined) {
                for(var i = 0; i < itemAttendances.length; i++) {
                    listAllAttdItemCode.push(itemAttendances[i].attendanceItemId);
                }
            }
            return listAllAttdItemCode;
        }
        
        /**
         * initial list of Setting Time Zone Code, selected name from list Dtos
         * @param settingTimeZones
         */
        private initialSettingTimeZoneCodesFromDtos (settingTimeZones : Array<any>) {
            let self = this;
            let names : string = '';
            if (settingTimeZones && settingTimeZones != undefined) {
                for(var i = 0; i < settingTimeZones.length; i++) {
                    self.listAllSettingTimeZone.push(settingTimeZones[i].worktimeCode);
                    if (names) {
                        names = names + "," + settingTimeZones[i].worktimeName;
                    } else {
                        names = settingTimeZones[i].worktimeName;
                    }
                }
            }
            self.displayWorkingTimeZoneSelections_BA5_3(names);
        }
          //==========Daily session End====================

        /**
         * Get list code from list codes that return from selected dialog
         * @param listKdl002Model
         */
        private getListCode(listKdl002Model : Array<model.ItemModelKdl002>) : Array<string>{
            let retListCode : Array<string> = [];
            if (listKdl002Model == null || listKdl002Model == undefined) {
                return retListCode;
            }
            for(var i = 0; i < listKdl002Model.length; i++) {
                retListCode.push(listKdl002Model[i].code);
            }
            return retListCode;
        }
        /**
         * open dialog for select working type
         */
        btnSettingBA1_3_click () {
            let self = this,
                currentErrAlaCheckCondition = self.currentErrAlaCheckCondition();

            block.invisible();
            let lstSelectedCode = currentErrAlaCheckCondition.workTypeSelections();
            //let lstSelectableCode = "001,002,003,006,111,112,113,114,115,116,117,118,119,120,121,122, 123,124,125,126,127,999".split(",");

            windows.setShared("KDL002_Multiple", true);
            //all possible items
            windows.setShared("KDL002_AllItemObj", self.listAllWorkType);
            //selected items
            windows.setShared("KDL002_SelectedItemId", lstSelectedCode);
            windows.sub.modal("/view/kdl/002/a/index.xhtml", 
                    { title: "乖離時間の登録＞対象項目", dialogClass: "no-close"}).onClosed(function(): any {
              //get data from share window
                let listItems : Array<any> = windows.getShared("KDL002_SelectedNewItem");
                if (listItems != null && listItems != undefined) {
                    let listCodes : Array<string> = self.getListCode(listItems);
                    currentErrAlaCheckCondition.workTypeSelections(listCodes);
                    let names : string = self.buildItemName(listItems);
                    self.displayWorkTypeSelections_BA1_4(names);
                    
                }
                block.clear();
            });
        }

        /**
         * open dialog for select working time zone (KDL002)
         */
        btnSettingBA5_2_click() {
            let self = this,
                currentErrAlaCheckCondition = self.currentErrAlaCheckCondition();

            block.invisible();
            let lstSelectedCode = currentErrAlaCheckCondition.workingTimeZoneSelections();
            windows.setShared("kml001multiSelectMode", true);
            //all possible items
            windows.setShared("kml001selectAbleCodeList", self.listAllSettingTimeZone);
            //selected items
            windows.setShared("kml001selectedCodeList", lstSelectedCode);
            windows.sub.modal("/view/kdl/001/a/index.xhtml", 
                    { title: "割増項目の設定", dialogClass: "no-close"}).onClosed(function(): any {
              //get data from share window
                let listItems : Array<any> = windows.getShared("kml001selectedCodeList");
                if (listItems != null && listItems != undefined) {
                    let listCodes : Array<string> = self.getListCode(listItems);
                    currentErrAlaCheckCondition.workingTimeZoneSelections(listCodes);
                    let names : string = self.buildItemName(listItems);
                    self.displayWorkingTimeZoneSelections_BA5_3(names);
                }
                block.clear();
            });
        }

        /**
         * open dialog for select working time zone
         */
        btnSettingBA2_2_click() {
            let self = this,
            currentErrAlaCheckCondition = self.currentErrAlaCheckCondition();

            block.invisible();
            let lstSelectedCode = currentErrAlaCheckCondition.workTimeItemSelections();
            windows.setShared("Multiple", true);
            //all possible items
            windows.setShared("AllAttendanceObj", self.listAllAttdItem);
            //selected items
            windows.setShared("SelectedAttendanceId", lstSelectedCode);
            windows.sub.modal('/view/kdl/021/a/index.xhtml',
                    {dialogClass: 'no-close'}).onClosed(function(): any {
              //get data from share window
                let listItems = windows.getShared('selectedChildAttendace');
                if (listItems != null && listItems != undefined) {
                    let listCodes : Array<string> = self.getListCode(listItems);
                    currentErrAlaCheckCondition.workTimeItemSelections(listCodes);
                    /*
                    self.generateNameCorrespondingToAttendanceItem(listAttendenceItemCode).done((data) => {
                        self.displayWorkTimeItemSelections_BA2_3(data);
                    });
                    */
                    let names = self.buildItemName(listItems);
                    self.displayWorkTimeItemSelections_BA2_3(names);
                }
                block.clear();
            });
        }
        
        /**
         * Open dialog C for group condition 1
         */
        btnSettingB5_7_click () {
            let self = this,
                currentErrAlaCheckCondition = self.currentErrAlaCheckCondition();

            alert("open dialog B5_7");

            block.invisible();

            let lstSelectedCode = currentErrAlaCheckCondition.workTypeSelections();
            let lstSelectableCode = [];
            
            windows.setShared('KAL003C_Multiple',false,true);
            //all possible items
            windows.setShared('KAL003C_AllItemObj',lstSelectableCode,true);
            //selected items
            windows.setShared('KAL003C_SelectedItemId',lstSelectedCode,true);
            
            windows.sub.modal('/view/kal/003/c/index.xhtml', { title: '乖離時間の登録＞対象項目'}).onClosed(function(): any {
              //get data from share window
                let listCds = windows.getShared('KAL003C_SelectedNewItem');
                if (listCds != null && listCds != undefined) {
                    //
                }
                block.clear();
            });
        }
        /**
         * Open dialog C for group condition 2
         */
        btnSettingB16_5_click () {
            let self = this,
                currentErrAlaCheckCondition = self.currentErrAlaCheckCondition();

            alert("open dialog B16_5");

            block.invisible();

            let lstSelectedCode = currentErrAlaCheckCondition.workTypeSelections();
            let lstSelectableCode = [];
            
            windows.setShared('KAL003C_Multiple',false,true);
            //all possible items
            windows.setShared('KAL003C_AllItemObj',lstSelectableCode,true);
            //selected items
            windows.setShared('KAL003C_SelectedItemId',lstSelectedCode,true);
            windows.sub.modal('/view/kal/003/c/index.xhtml', { title: '乖離時間の登録＞対象項目'}).onClosed(function(): any {
              //get data from share window
                let listCds = windows.getShared('KAL003C_SelectedNewItem');
                if (listCds != null && listCds != undefined) {
                    //
                }
                block.clear();
            });
        }
        
        
        /**
         * close dialog B and return result
         */
        btnDecision() {
            let self = this;
            windows.setShared('outputKal003b', self.currentErrAlaCheckCondition());
            windows.close();
        }
        /**
         * close dialog B and return result
         */
        closeDialog() {
            windows.setShared('outputKal003b', undefined);
            windows.close();
        }
    }

    /**
     * The enum of ROLE TYPE 
     */
    export enum enCategory {
        Daily   = 0,
        Weekly  = 1,
        Monthly = 2

    }
    export enum enItemCheck {
        Time                = 0, // 時間
        Times               = 1, // 回数
        AmountOfMoney       = 2, // 金額
        TimeOfDate          = 3, // 時刻
        CountinuousTime     = 4, // 連続時間
        CountinuousWork     = 5, // 連続勤務
        CountinuousTimeZone = 6, // 連続時間帯
        CompoundCondition   = 7// 複合条件
    }
    module model {
        export class ItemModelKdl002 {
            code: string;
            name: string;
            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }
        export interface IEnumModel {
            value : number;
            fieldName: string;
            localizedName: string;
        }
        
        export class EnumModel {
            value : KnockoutObservable<number>;
            fieldName: string;
            localizedName: string;
            constructor(param: IEnumModel) {
                let self = this;
                self.value =  ko.observable(param.value || -1);
                self.fieldName = param.fieldName || '';
                self.localizedName = param.localizedName || '';
            }
        }
        
        export interface IAttdItemDto {
            attendanceItemId            : number;
            attendanceItemName          : string;
            attendanceItemDisplayNumber : number;
            nameLineFeedPosition        : number;
            dailyAttendanceAtr          : number;
        }
        export class AttdItemDto {
            attendanceItemId            : number;
            attendanceItemName          : string;
            attendanceItemDisplayNumber : number;
            nameLineFeedPosition        : number;
            dailyAttendanceAtr          : number;
            constructor(param : IAttdItemDto) {
                let self = this;
                self.attendanceItemId           = param.attendanceItemId    || 0;
                self.attendanceItemName         = param.attendanceItemName  || '';
                self.attendanceItemDisplayNumber = param.attendanceItemDisplayNumber || 0;
                self.nameLineFeedPosition       = param.nameLineFeedPosition || 0;
                self.dailyAttendanceAtr         = param.dailyAttendanceAtr  || 0;
            }
        }
        
        export interface IWorkTypeDto {
            workTypeCode        : string;
            name                : string;
            abbreviationName    : string;
            symbolicName        : string;
            abolishAtr          : number;
            workAtr             : number;
            oneDayCls           : number;
            morningCls          : number;
            afternoonCls        : number;
            calculatorMethod    : number;
        }

        export class WorkTypeDto {
            workTypeCode        : string;
            name                : string;
            abbreviationName    : string;
            symbolicName        : string;
            abolishAtr          : number;
            workAtr             : number;
            oneDayCls           : number;
            morningCls          : number;
            afternoonCls        : number;
            calculatorMethod    : number;
            constructor(param   : IWorkTypeDto) {
                let self = this;
                self.workTypeCode       = param.workTypeCode    || '';
                self.name               = param.name            || '';
                self.abbreviationName   = param.abbreviationName || '';
                self.symbolicName       = param.symbolicName    || '';
                self.abolishAtr         = param.abolishAtr      || 0;
                self.workAtr            = param.workAtr         || 0;
                self.oneDayCls          = param.oneDayCls       || 0;
                self.morningCls         = param.morningCls      || 0;
                self.afternoonCls       = param.afternoonCls    || 0;
                self.calculatorMethod   = param.calculatorMethod || 0;
            }
        }
        
        export interface IDailyAttendanceNameDto {
            attendanceItemId    : number;
            attendanceItemName  : string;
            attendanceItemDisplayNumber : number;
        }
        
        export class DailyAttendanceNameDto {
            attendanceItemId    : number;
            attendanceItemName  : string;
            attendanceItemDisplayNumber : number;
            constructor(param : IDailyAttendanceNameDto) {
                let self = this;
                self.attendanceItemId   = param.attendanceItemId || 0;
                self.attendanceItemName = param.attendanceItemName || '';
                self.attendanceItemDisplayNumber = param.attendanceItemDisplayNumber || 0;
            }
        }
    }
}
