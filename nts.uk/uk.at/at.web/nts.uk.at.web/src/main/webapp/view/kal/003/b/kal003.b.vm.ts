module nts.uk.at.view.kal003.b.viewmodel{
    import block = nts.uk.ui.block;
    import errors = nts.uk.ui.errors;
    import dialog = nts.uk.ui.dialog;
    import windows = nts.uk.ui.windows;
    import resource = nts.uk.resource;
    import sharemodel = nts.uk.at.view.kal003.share.model;
    import shareutils = nts.uk.at.view.kal003.share.kal003utils;

    export class ScreenModel {
        workRecordExtractingCondition: KnockoutObservable<sharemodel.WorkRecordExtractingCondition>;
        // list item check
        listTypeCheckWorkRecords    : KnockoutObservableArray<model.EnumModel> = ko.observableArray([]);
        listSingleValueCompareTypes : KnockoutObservableArray<model.EnumModel> = ko.observableArray([]);
        listRangeCompareTypes       : KnockoutObservableArray<model.EnumModel> = ko.observableArray([]);
        listCompareTypes            : KnockoutObservableArray<model.EnumModel> = ko.observableArray([]);
        itemListTargetServiceType_BA1_2         : KnockoutObservableArray<model.EnumModel> = ko.observableArray([]);
        itemListTargetSelectionRange_BA1_5         : KnockoutObservableArray<model.EnumModel> = ko.observableArray([]);
        listAllWorkType         : Array<string> = ([]);
        listAllAttdItem         : Array<string> = ([]);
        listAllWorkingTime  : Array<string> = ([]);

        displayWorkTypeSelections_BA1_4         : KnockoutObservable<string> = ko.observable('');
        displayAttendanceItemSelections_BA2_3     : KnockoutObservable<string> = ko.observable('');
        displayWorkingTimeSelections_BA5_3  : KnockoutObservable<string> = ko.observable('');
               
        private setting : sharemodel.WorkRecordExtractingCondition;
        swANDOR_B5_3 : KnockoutObservableArray<model.EnumModel> = ko.observableArray([]);
        swANDOR_B6_3 : KnockoutObservableArray<model.EnumModel> = ko.observableArray([]);
        swANDOR_B7_2 : KnockoutObservableArray<model.EnumModel> = ko.observableArray([]);
        enableComparisonMaxValue : KnockoutObservable<boolean> = ko.observable(false);
        comparisonRange : KnockoutObservable<model.ComparisonValueRange>;

        constructor() {
            let self = this;
            let option = windows.getShared('inputKal003b');
            self.setting = $.extend({}, shareutils.getDefaultWorkRecordExtractingCondition(0), option);
            self.workRecordExtractingCondition = ko.observable(self.setting);
            // setting comparison value range
            
            let erAlAtdItemCondition = self.workRecordExtractingCondition().errorAlarmCondition().atdItemCondition().group1().lstErAlAtdItemCon()[0];
            self.comparisonRange = ko.observable(new model.ComparisonValueRange(
                self.workRecordExtractingCondition().checkItem
                , erAlAtdItemCondition.compareOperator
                , erAlAtdItemCondition.compareStartValue()
                , erAlAtdItemCondition.compareEndValue()));
                
            // change select item check
            self.workRecordExtractingCondition().checkItem.subscribe((itemCheck) => {
                errors.clearAll();
                if ((itemCheck && itemCheck != undefined) || itemCheck === 0) {
                    self.initialScreen();
                }
            });

            /*
            self.currentErrAlaCheckCondition().minimumValue.subscribe((minimumValue) => {
                
                self.checkValidOfRange(0); //min
            });
            self.currentErrAlaCheckCondition().maximumValue.subscribe((maximumValue) => {
                self.checkValidOfRange(1); //max
            });
            */
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
            self.enableComparisonMaxValue(
                self.workRecordExtractingCondition().errorAlarmCondition().atdItemCondition().group1().lstErAlAtdItemCon[0]().compareOperator() > 5);
        }
        
        /**
         * valid range of comparision 
         */
        /*
        private checkValidOfRange(textBox : number) : boolean {
            let self = this,
                currentErrAlaCheckCondition = self.currentErrAlaCheckCondition();
            let isValid : boolean = true;
            
            if (currentErrAlaCheckCondition.comparisonOperator() > 5) {
                let minValue : number = undefined;
                let maxValue : number = undefined;
                switch (currentErrAlaCheckCondition.checkItem()) {
                    case enItemCheck.Time:          //時間 - 0: check time
                    case enItemCheck.CountinuousTime:   //連続時間 - 4:  check time
                        minValue = nts.uk.time.parseTime(currentErrAlaCheckCondition.minimumValue()).toValue();
                        maxValue = nts.uk.time.parseTime(currentErrAlaCheckCondition.maximumValue()).toValue();
                        break;
                    case enItemCheck.Times:         //回数 - 1: check times
                        minValue = parseInt(currentErrAlaCheckCondition.minimumValue());
                        maxValue = parseInt(currentErrAlaCheckCondition.maximumValue());
                        break;
                    case enItemCheck.AmountOfMoney: //金額 - 2: check amount of money
                        minValue = parseFloat(currentErrAlaCheckCondition.minimumValue());
                        maxValue = parseFloat(currentErrAlaCheckCondition.maximumValue());
                        break;
                    case enItemCheck.TimeOfDate:    //時刻の場合 - 3: time within day
                        minValue = nts.uk.time.parseTimeOfTheDay(currentErrAlaCheckCondition.minimumValue()).toValue();
                        maxValue = nts.uk.time.parseTimeOfTheDay(currentErrAlaCheckCondition.maximumValue()).toValue();
                        break
                    default:
                        break;
                }
                
                if (minValue != undefined && maxValue != undefined) {
                    isValid = self.compareValid(currentErrAlaCheckCondition.comparisonOperator(), minValue, maxValue);
                }
            }
            if (!isValid) {
                dialog.info({ messageId: "Msg_927" });
                if(textBox === 1) { //max
                    $('#[KAL003_65]').ntsError('set', {messageId:"Msg_927"});
                    $('#[KAL003_65]').focus();
                } else {
                    $('#[KAL003_64]').ntsError('set', {messageId:"Msg_927"});
                    $('#[KAL003_64]').focus();
                }
            }
            return isValid;
        }
        */
        /**
         * execute check valid of range
         */
        /*
        private compareValid(comOper : number, minValue : number, maxValue: number): boolean {
             switch (comOper) {
                case 6: // 範囲の間（境界値を含まない）（＜＞）
                case 8: // 範囲の外（境界値を含まない）（＞＜）
                    return (minValue >= maxValue);
                case 7: // 範囲の間（境界値を含む）（≦≧）
                case 9: // 範囲の外（境界値を含む）（≧≦）
                    return (minValue > maxValue);
                default:
                    break;
            }
            return true;
        }
        */
        /**
         * initial screen
         */
        private initialScreen() : JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            self.initialDaily().done(() => {
                self.settingEnableComparisonMaxValueField();
                dfd.resolve();
            });
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
                    let listTargetRangeWithName = self.getLocalizedNameForEnum(listTargetSelectionRange);
                    self.itemListTargetSelectionRange_BA1_5(listTargetRangeWithName);
                    self.itemListTargetServiceType_BA1_2(self.getLocalizedNameForEnum(listTargetServiceType));
                    self.buildListCompareTypes();
                    let listANDOR = self.getLocalizedNameForEnum(listLogicalOperator)
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
            let maxRow = 3;
            if (listGroupCondition && listGroupCondition != undefined) {
                for(var i = 0; i < listGroupCondition.length && i < maxRow; i++) {
                    listCondition.push(listGroupCondition[i]);
                }
            }
            if (listCondition.length < maxRow) {
                for(var i = listCondition.length; i < maxRow; i++) {
                    listCondition.push(shareutils.getDefaultCondition(i-1));
                }
            }
            return listCondition;
        }
 
        /**
         * Initial Compound Group Condition
         */
        private initCompoundGroupCondition() {
            let self = this,
                errorAlarmCondition = self.workRecordExtractingCondition().errorAlarmCondition();
            let compoundCondition = errorAlarmCondition.atdItemCondition();
            if (!compoundCondition || compoundCondition == undefined) {
                compoundCondition = shareutils.getDefaultAttendanceItemCondition();
                errorAlarmCondition.atdItemCondition(compoundCondition);
            }
            let listGr1 = self.initGroupCondition(compoundCondition.group1().lstErAlAtdItemCon());
            compoundCondition.group1().lstErAlAtdItemCon(listGr1);
            let listGr2 = self.initGroupCondition(compoundCondition.group2().lstErAlAtdItemCon());
            compoundCondition.group2().lstErAlAtdItemCon(listGr2);
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
            switch (self.workRecordExtractingCondition().checkItem()) {
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
            workRecordExtractingCondition = self.workRecordExtractingCondition();
            //ドメインモデル「日次の勤怠項目」を取得する - Acquire domain model "DailyAttendanceItem"
            service.getDailyItemChkItemComparison(workRecordExtractingCondition.checkItem()).done((itemAttendances : Array<any>) => {
                self.listAllAttdItem = self.getListAttendanceIdFromDtos(itemAttendances);
                
                // build name of Attendance Item
               let listAttendanceItemSelectedCode = self.getListAttendanceItemCode();//勤怠項目の加算減算式
                self.generateNameCorrespondingToAttendanceItem(listAttendanceItemSelectedCode).done((names) => {
                            self.displayAttendanceItemSelections_BA2_3(names);
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
                let listWorkTimeItemSelectedCode = self.getListAttendanceItemCode();//勤怠項目の加算減算式
                self.generateNameCorrespondingToAttendanceItem(listWorkTimeItemSelectedCode).done((names) => {
                    self.displayAttendanceItemSelections_BA2_3(names);
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
                self.initialWorkTimeCodesFromDtos(settingTimeZones);
              //ドメインモデル「勤務種類」を取得する - Acquire domain model "WorkType"
                self.initialWorkTypes();
            });
        }
        
        //TODO
        private initialDailyItemChkCompound() {
            let self = this,
            workRecordExtractingCondition = self.workRecordExtractingCondition();
            //アルゴリズム「複合条件の項目取得」を実行する - Execute the algorithm "item acquisition of compound condition"
            service.getAttendCompound(workRecordExtractingCondition.errorAlarmCheckID).done((data) => {
                if (data) {
                    //TODO
                }
                //self.getAttendanceItemName();
            });
        }

        private getListAttendanceItemCode() : Array<any> {
            let self = this,
                workRecordExtractingCondition = self.workRecordExtractingCondition();
            let lstErAlAtdItemCon = workRecordExtractingCondition.errorAlarmCondition()
                .atdItemCondition().group1().lstErAlAtdItemCon();
            let listWorkTimeItemSelectedCode = lstErAlAtdItemCon[0].countableAddAtdItems() || [];//勤怠項目の加算減算式
            
            return listWorkTimeItemSelectedCode;
        }
        private setListAttendanceItemCode(listWorkTimeItemSelectedCode : Array<any>) {
            let self = this,
                workRecordExtractingCondition = self.workRecordExtractingCondition();
            let lstErAlAtdItemCon = workRecordExtractingCondition.errorAlarmCondition()
                .atdItemCondition().group1().lstErAlAtdItemCon(listWorkTimeItemSelectedCode || []);//勤怠項目の加算減算式
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
            let self =this,
                workTypeCondition = self.workRecordExtractingCondition().errorAlarmCondition().workTypeCondition();
            self.listAllWorkType = [];
            // get all Work type
            service.getAttendCoutinousWork().done((workTypes) => {
                if (workTypes && workTypes != undefined) {
                    for(var i = 0; i < workTypes.length; i++) {
                        self.listAllWorkType.push(workTypes[i].workTypeCode);
                    }
                }
            });
            // get Name of selected work type.
            let wkTypeSelected = workTypeCondition.planLstWorkType();
            if (wkTypeSelected && wkTypeSelected.length > 0) {
                service.findWorkTypeByCodes(wkTypeSelected).done((listWrkTypes) => {
                    let names : string = self.buildItemName(listWrkTypes);
                    self.displayWorkTypeSelections_BA1_4(names);
                });
            } else {
                self.displayWorkTypeSelections_BA1_4("");
            }
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
         * initial list of Setting Work Time Code, selected name from list Dtos
         * @param settingTimeZones
         */
        /*
        private initialWorkTimeZoneCodesFromDtos (settingTimeZones : Array<any>) {
            let self = this;
            let names : string = '';
            if (settingTimeZones && settingTimeZones != undefined) {
                for(var i = 0; i < settingTimeZones.length; i++) {
                    self.listAllWorkTime.push(settingTimeZones[i].worktimeCode);
                    if (names) {
                        names = names + "," + settingTimeZones[i].worktimeName;
                    } else {
                        names = settingTimeZones[i].worktimeName;
                    }
                }
            }
            self.displayWorkTimeSelections_BA5_3(names);
        }
        */
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
                workTypeCondition = self.workRecordExtractingCondition().errorAlarmCondition().workTypeCondition();

            block.invisible();
            let lstSelectedCode = workTypeCondition.planLstWorkType();
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
                    workTypeCondition.planLstWorkType(listCodes);
                    // get name
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
                workTimeCondition = self.workRecordExtractingCondition().errorAlarmCondition().workTimeCondition();

            block.invisible();
            let lstSelectedCode = workTimeCondition.planLstWorkTime();
            windows.setShared("kml001multiSelectMode", true);
            //all possible items
            windows.setShared("kml001selectAbleCodeList", self.listAllWorkingTime);
            //selected items
            windows.setShared("kml001selectedCodeList", lstSelectedCode);
            windows.sub.modal("/view/kdl/001/a/index.xhtml", 
                    { title: "割増項目の設定", dialogClass: "no-close"}).onClosed(function(): any {
              //get data from share window
                let listItems : Array<any> = windows.getShared("kml001selectedCodeList");
                if (listItems != null && listItems != undefined) {
                    workTimeCondition.planLstWorkTime(listItems);
                    //get name
                    self.generateNameCorrespondingToAttendanceItem(listItems).done((data) => {
                        self.displayWorkingTimeSelections_BA5_3(data);
                    });
                }
                block.clear();
            });
        }

        /**
         * open dialog for select working time zone
         */
        btnSettingBA2_2_click() {
            let self = this;

            block.invisible();
            let lstSelectedCode = self.getListAttendanceItemCode();
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
                    self.setListAttendanceItemCode(listItems);
                    // get name
                    self.generateNameCorrespondingToAttendanceItem(listItems).done((data) => {
                        self.displayAttendanceItemSelections_BA2_3(data);
                    });
                    
                   let group1 = self.workRecordExtractingCondition().errorAlarmCondition().atdItemCondition().group1();
                    let listErAlAtdItemCondition = group1.lstErAlAtdItemCon();
                    let erAlAtdItemCondition = listErAlAtdItemCondition[0];
                    self.comparisonRange().comparisonOperator(erAlAtdItemCondition.compareOperator());
                    self.comparisonRange().minValue(erAlAtdItemCondition.compareStartValue());
                    self.comparisonRange().maxValue(erAlAtdItemCondition.compareEndValue());
                }
                block.clear();
            });
        }
        
        /**
         * Open dialog C for group condition 1
         */
        /*
        btnSettingB5_7_click () {
            let self = this,
                workRecordExtractingCondition = self.workRecordExtractingCondition();

            alert("open dialog B5_7");

            block.invisible();

            let lstSelectedCode = workRecordExtractingCondition.workTypeSelections();
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
        */
        /**
         * Open dialog C for group condition 2
         */
        /*
        btnSettingB16_5_click () {
            let self = this,
                workRecordExtractingCondition = self.workRecordExtractingCondition();

            alert("open dialog B16_5");

            block.invisible();

            let lstSelectedCode = workRecordExtractingCondition.workTypeSelections();
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
        
        */
        /**
         * close dialog B and return result
         */
        btnDecision() {
            let self = this,
                workRecordExtractingCondition = self.workRecordExtractingCondition();
                
             // validate comparison range
            let group1 = workRecordExtractingCondition.errorAlarmCondition().atdItemCondition().group1();
            let listErAlAtdItemCondition = group1.lstErAlAtdItemCon();
            let erAlAtdItemCondition = listErAlAtdItemCondition[0];
            if (self.comparisonRange().checkValidOfRange(
                workRecordExtractingCondition.checkItem()
                , erAlAtdItemCondition.compareOperator()
                , 1)) {
                
                erAlAtdItemCondition.compareStartValue(self.comparisonRange().minValue());
                erAlAtdItemCondition.compareEndValue(self.comparisonRange().maxValue());
                
                windows.setShared('outputKal003b', workRecordExtractingCondition);
                windows.close();
            }
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
        /*
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
        */
        /**
         * ComparisonValueRange
         */
        export class ComparisonValueRange {
            minTimeValue: KnockoutObservable<string> =  ko.observable('');
            maxTimeValue: KnockoutObservable<string> =  ko.observable('');
            
            minTimesValue: KnockoutObservable<string> =  ko.observable('0');
            maxTimesValue: KnockoutObservable<string> =  ko.observable('0');
            
            minAmountOfMoneyValue: KnockoutObservable<string> =  ko.observable('0');
            maxAmountOfMoneyValue: KnockoutObservable<string> =  ko.observable('0');
            
            minTimeWithinDayValue: KnockoutObservable<string> =  ko.observable('0');
            maxTimeWithinDayValue: KnockoutObservable<string> =  ko.observable('0');
            minValue : KnockoutObservable<string> =  ko.observable('');
            maxValue : KnockoutObservable<string> =  ko.observable('');
            
            checkItem : KnockoutObservable<number> =  ko.observable(0);
            comparisonOperator : KnockoutObservable<number> =  ko.observable(0);
            constructor(checkItem : KnockoutObservable<number>, comOper : KnockoutObservable<number>, minValue : string, maxValue: string) {
                let self = this;
                self.minValue(minValue || '');
                self.maxTimeValue(maxValue || '');
                self.comparisonOperator = checkItem;
                self.comparisonOperator = comOper;
                switch(checkItem()) {
                    case enItemCheck.Time:              //時間 - 0: check time
                    case enItemCheck.CountinuousTime:   //連続時間 - 4:  check time
                        self.minTimeValue(minValue);
                        self.maxTimeValue(maxValue);
                        break;
                    case enItemCheck.Times:         //回数 - 1: check times
                        self.minTimesValue(minValue);
                        self.maxTimesValue(maxValue);
                        break;
                    case enItemCheck.AmountOfMoney: //金額 - 2: check amount of money
                        self.minAmountOfMoneyValue(minValue);
                        self.maxAmountOfMoneyValue(maxValue);
                        break;
                    case enItemCheck.TimeOfDate:    //時刻の場合 - 3: time within day
                        self.minTimeWithinDayValue(minValue);
                        self.maxTimeWithinDayValue(maxValue);
                        break;
                    default:
                        break;
               }
               //時間 - 0: check time
               //連続時間 - 4:  check time
               self.minTimeValue.subscribe((minValue) => {
                    if (self.checkValidOfRange(checkItem(), comOper(), 0)) { //min
                        self.minValue(self.minTimeValue());
                        self.maxValue(self.maxTimeValue());
                    }
                });
                self.maxTimeValue.subscribe((maxValue) => {
                    if (self.checkValidOfRange(checkItem(), comOper(), 1)) { //max
                        self.minValue(self.minTimeValue());
                        self.maxValue(self.maxTimeValue());
                    }
                });

                //回数 - 1: check times
                self.minTimesValue.subscribe((minValue) => {
                    if (self.checkValidOfRange(checkItem(), comOper(), 0)) { //min
                        self.minValue(self.minTimesValue());
                        self.maxValue(self.maxTimesValue());
                    }
                });
                self.maxTimesValue.subscribe((maxValue) => {
                    if (self.checkValidOfRange(checkItem(), comOper(), 1)) { //max
                        self.minValue(self.minTimesValue());
                        self.maxValue(self.maxTimesValue());
                    }
                });

                //金額 - 2: check amount of money
                self.minAmountOfMoneyValue.subscribe((minValue) => {
                    if (self.checkValidOfRange(checkItem(), comOper(), 0)) { //min
                        self.minValue(self.minAmountOfMoneyValue());
                        self.maxValue(self.maxAmountOfMoneyValue());
                    }
                });
                self.maxAmountOfMoneyValue.subscribe((maxValue) => {
                    if (self.checkValidOfRange(checkItem(), comOper(), 1)) { //max
                        self.minValue(self.minAmountOfMoneyValue());
                        self.maxValue(self.maxAmountOfMoneyValue());
                    }
                });
                
                //時刻の場合 - 3: time within day
                self.minTimeWithinDayValue.subscribe((minValue) => {
                    if (self.checkValidOfRange(checkItem(), comOper(), 0)) { //min
                        self.minValue(self.minTimeWithinDayValue());
                        self.maxValue(self.maxTimeWithinDayValue());
                    }
                });
                self.maxTimeWithinDayValue.subscribe((maxValue) => {
                    if (self.checkValidOfRange(checkItem(), comOper(), 1)) { //max
                        self.minValue(self.minTimeWithinDayValue());
                        self.maxValue(self.maxTimeWithinDayValue());
                    }
                });
            }
            
            /**
             * valid range of comparision 
             */
            checkValidOfRange(checkItem: number, comOper: number, textBox : number) : boolean {
                let self = this;
                let isValid : boolean = true;
                
                if (comOper > 5) {
                    let minValue : number = undefined;
                    let maxValue : number = undefined;
                    switch (checkItem) {
                        case enItemCheck.Time:          //時間 - 0: check time
                        case enItemCheck.CountinuousTime:   //連続時間 - 4:  check time
                            minValue = nts.uk.time.parseTime(self.minTimeValue()).toValue();
                            maxValue = nts.uk.time.parseTime(self.maxTimeValue()).toValue();
                            break;
                        case enItemCheck.Times:         //回数 - 1: check times
                            minValue = parseInt(self.minTimesValue());
                            maxValue = parseInt(self.maxTimesValue());
                            break;
                        case enItemCheck.AmountOfMoney: //金額 - 2: check amount of money
                            minValue = parseFloat(self.minAmountOfMoneyValue());
                            maxValue = parseFloat(self.maxAmountOfMoneyValue());
                            break;
                        case enItemCheck.TimeOfDate:    //時刻の場合 - 3: time within day
                            minValue = nts.uk.time.parseTimeOfTheDay(self.minTimeWithinDayValue()).toValue();
                            maxValue = nts.uk.time.parseTimeOfTheDay(self.maxTimeWithinDayValue()).toValue();
                            break
                        default:
                            break;
                    }
                    
                    if (minValue != undefined && maxValue != undefined) {
                        isValid = self.compareValid(comOper, minValue, maxValue);
                    }
                }
                if (!isValid) {
                    dialog.info({ messageId: "Msg_927" });
                    if(textBox === 1) { //max
                        $('#[KAL003_65]').ntsError('set', {messageId:"Msg_927"});
                        $('#[KAL003_65]').focus();
                    } else {
                        $('#[KAL003_64]').ntsError('set', {messageId:"Msg_927"});
                        $('#[KAL003_64]').focus();
                    }
                }
                return isValid;
            }
            /**
             * execute check valid of range
             */
            private compareValid(comOper : number, minValue : number, maxValue: number): boolean {
                 switch (comOper) {
                    case 6: // 範囲の間（境界値を含まない）（＜＞）
                    case 8: // 範囲の外（境界値を含まない）（＞＜）
                        return (minValue >= maxValue);
                    case 7: // 範囲の間（境界値を含む）（≦≧）
                    case 9: // 範囲の外（境界値を含む）（≧≦）
                        return (minValue > maxValue);
                    default:
                        break;
                }
                return true;
            }
        }
     }
}
