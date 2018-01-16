module nts.uk.at.view.kal003.share.model {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import modal = nts.uk.ui.windows.sub.modal;

    export function getListCategory(): Array<ItemModel> {
        return [
            new ItemModel(0, 'スケジュール日次'),
            new ItemModel(1, 'スケジュール週次'),
            new ItemModel(2, 'スケジュール4週'),
            new ItemModel(3, 'スケジュール月次'),
            new ItemModel(4, 'スケジュール年間'),
            new ItemModel(5, '日次'),
            new ItemModel(6, '週次'),
            new ItemModel(7, '月次'),
            new ItemModel(8, '申請承認'),
            new ItemModel(9, '複数月'),
            new ItemModel(10, '任意期間'),
            new ItemModel(11, '年休付与用出勤率'),
            new ItemModel(12, '３６協定'),
            new ItemModel(13, '工数チェック')
        ];
    }

    export function getConditionToExtractDaily(): Array<ItemModel> {
        return [
            new model.ItemModel(0, '全て'),
            new model.ItemModel(1, '確認済のデータ'),
            new model.ItemModel(2, '未確認のデータ')
        ];
    }
    
    export function getSchedule4WeekAlarmCheckCondition(): Array<ItemModel> {
        return [
            new model.ItemModel(0, '実績のみで4週4休をチェックする'),
            new model.ItemModel(1, 'スケジュールと実績で4週4休をチェックする')
        ];
    }

    export class AlarmCheckConditionByCategory {
        code: KnockoutObservable<string>;
        name: KnockoutObservable<string>;
        category: KnockoutObservable<number>;
        displayCode: string;
        displayName: string;
        displayCategory: string;
        availableRoles: KnockoutObservableArray<string>;
        targetCondition: KnockoutObservable<AlarmCheckTargetCondition>;
        displayAvailableRoles: KnockoutObservable<string>;
        dailyAlarmCheckCondition: KnockoutObservable<DailyAlarmCheckCondition> = ko.observable(new DailyAlarmCheckCondition(DATA_CONDITION_TO_EXTRACT.ALL, []));
        schedule4WeekAlarmCheckCondition: KnockoutObservable<Schedule4WeekAlarmCheckCondition> = ko.observable(new Schedule4WeekAlarmCheckCondition(SCHEDULE_4_WEEK_CHECK_CONDITION.FOR_ACTUAL_RESULTS_ONLY));

        constructor(code: string, name: string, category: ItemModel, availableRoles: Array<string>, targetCondition: AlarmCheckTargetCondition) {
            this.code = ko.observable(code);
            this.name = ko.observable(name);
            this.category = ko.observable(category.code);
            this.displayCode = code;
            this.displayName = name;
            this.displayCategory = category.name;
            this.availableRoles = ko.observableArray(availableRoles);
            this.targetCondition = ko.observable(targetCondition);
            this.displayAvailableRoles = ko.computed(function() {
                return this.availableRoles().join(", ");
            }, this);
        }
    }

    export class AlarmCheckTargetCondition {
        filterByEmployment: KnockoutObservable<boolean>;
        filterByClassification: KnockoutObservable<boolean>;
        filterByJobTitle: KnockoutObservable<boolean>;
        filterByBusinessType: KnockoutObservable<boolean>;
        targetEmployment: KnockoutObservableArray<string>;
        targetClassification: KnockoutObservableArray<string>;
        targetJobTitle: KnockoutObservableArray<string>;
        targetBusinessType: KnockoutObservableArray<string>;
        displayTargetEmployment: KnockoutObservable<string>;
        displayTargetClassification: KnockoutObservable<string>;
        displayTargetJobTitle: KnockoutObservable<string>;
        displayTargetBusinessType: KnockoutObservable<string>;

        constructor(fbe: boolean, fbc: boolean, fbjt: boolean, fbbt: boolean, targetEmp: Array<string>, targetCls: Array<string>, targetJob: Array<string>, targetBus: Array<string>) {
            this.filterByEmployment = ko.observable(fbe);
            this.filterByClassification = ko.observable(fbc);
            this.filterByJobTitle = ko.observable(fbjt);
            this.filterByBusinessType = ko.observable(fbbt);
            this.targetEmployment = ko.observableArray(targetEmp);
            this.targetClassification = ko.observableArray(targetCls);
            this.targetJobTitle = ko.observableArray(targetJob);
            this.targetBusinessType = ko.observableArray(targetBus);
            this.displayTargetEmployment = ko.computed(function() {
                return this.targetEmployment().join(", ");
            }, this);
            this.displayTargetClassification = ko.computed(function() {
                return this.targetClassification().join(", ");
            }, this);
            this.displayTargetJobTitle = ko.computed(function() {
                return this.targetJobTitle().join(", ");
            }, this);
            this.displayTargetBusinessType = ko.computed(function() {
                return this.targetBusinessType().join(", ");
            }, this);
        }

        // Open Dialog CDL002
        private openCDL002Dialog() {
            let self = this;
            setShared('CDL002Params', {
                isMultiple: true,
                selectedCodes: self.targetEmployment(),
                showNoSelection: false,
            }, true);

            modal("com", "/view/cdl/002/a/index.xhtml").onClosed(() => {
                var output = getShared('CDL002Output');
                if (output) {
                    self.targetEmployment(output);
                }
            });
        }

        // Open Dialog CDL003
        private openCDL003Dialog() {
            let self = this;
            setShared('inputCDL003', {
                selectedCodes: self.targetClassification(),
                showNoSelection: false,
                isMultiple: true
            }, true);

            modal("com", "/view/cdl/003/a/index.xhtml").onClosed(() => {
                var output = getShared('outputCDL003');
                if (output) {
                    self.targetClassification(output);
                }
            })
        }

        // Open Dialog CDL004
        private openCDL004Dialog(): void {
            let self = this;
            setShared('inputCDL004', {
                baseDate: new Date(),
                selectedCodes: self.targetJobTitle(),
                showNoSelection: false,
                isMultiple: true
            }, true);

            modal("com", "/view/cdl/004/a/index.xhtml").onClosed(() => {
                var output = getShared('outputCDL004');
                if (output) {
                    self.targetJobTitle(output);
                }
            })
        }

        // Open Dialog CDL024
        private openCDL024Dialog() {
            let self = this;
            setShared("CDL024", { codeList: self.targetBusinessType() });

            modal("com", "/view/cdl/024/index.xhtml").onClosed(() => {
                var output = getShared("currentCodeList");
                if (output) {
                    self.targetBusinessType(output);
                }
            });
        }

    }

    export class ItemModel {
        code: number;
        name: string;
        description: string = "";

        constructor(code: number, name: string, description?: string) {
            this.code = code;
            this.name = name;
            if (description) {
                this.description = description;
            }
        }
    }
    
    export class DailyAlarmCheckCondition {
        conditionToExtractDaily: KnockoutObservable<number>;
        listWorkRecordExtractingConditions: KnockoutObservableArray<WorkRecordExtractingCondition>;
        
        constructor(conditionToExtractDaily: number, listWorkRecordExtractingConditions: Array<WorkRecordExtractingCondition>) {
            this.conditionToExtractDaily = ko.observable(conditionToExtractDaily);
            this.listWorkRecordExtractingConditions = ko.observableArray(listWorkRecordExtractingConditions);
        } 
    }

    export class Schedule4WeekAlarmCheckCondition {
        schedule4WeekCheckCondition: KnockoutObservable<number>;
        
        constructor(schedule4WeekCheckCondition: number) {
            this.schedule4WeekCheckCondition = ko.observable(schedule4WeekCheckCondition);
        } 
    }
    
    export enum CATEGORY {
        SCHEDULE_DAILY = 0,
        SCHEDULE_WEEKLY = 1,
        SCHEDULE_4_WEEK = 2,
        SCHEDULE_MONTHLY = 3,
        SCHEDULE_YEAR = 4,
        DAILY = 5,
        WEEKLY = 6,
        MONTHLY = 7,
        APPLICATION_APPROVAL = 8,
        MULTIPLE_MONTHS = 9,
        ANY_PERIOD = 10,
        ATTENDANCE_RATE_FOR_ANNUAL_HOLIDAYS = 11,
        _36_AGREEMENT = 12,
        MAN_HOUR_CHECK = 13
    }

    export enum DATA_CONDITION_TO_EXTRACT {
        ALL = 0,
        CONFIRMED = 1,
        UNCONFIRMED = 2
    }
    
    export enum SCHEDULE_4_WEEK_CHECK_CONDITION {
        FOR_ACTUAL_RESULTS_ONLY = 0,
        WITH_SCHEDULE_AND_ACTUAL_RESULTS = 1
    }

    export enum SCREEN_MODE {
        NEW = 0,
        UPDATE = 1
    }
    
    export interface IWorkRecordExtractingCondition {
        errorAlarmCheckID   : string;
        checkItem           : number;
        sortOrderBy         : number;
        useAtr              : boolean;
        nameWKRecord        : string;
        errorAlarmCondition  : ErrorAlarmCondition;
        rowId               : number;
    }

    export class WorkRecordExtractingCondition {
        errorAlarmCheckID   : string;
        checkItem           : KnockoutObservable<number> = ko.observable(0);
        sortOrderBy         : number;
        useAtr              : KnockoutObservable<boolean> = ko.observable(false);
        nameWKRecord        : KnockoutObservable<string>  = ko.observable('');
        errorAlarmCondition : KnockoutObservable<ErrorAlarmCondition> = ko.observable(null);
        rowId               : KnockoutObservable<number> = ko.observable(0);
        constructor(param : IWorkRecordExtractingCondition) {
            let self = this;
            self.errorAlarmCheckID   = param.errorAlarmCheckID || '';
            self.checkItem           (param.checkItem || 0);
            self.sortOrderBy         = param.sortOrderBy || 0;
            self.useAtr               (param.useAtr || false);
            self.nameWKRecord         (param.nameWKRecord || '');
            self.errorAlarmCondition  (param.errorAlarmCondition); // || kal003utils.getDefaultErrorAlarmCondition());
            self.rowId                (param.rowId || 0);
        }
    }
    
    //---------------- KAL003 - B begin----------------//
    //Condition of group (C screen) ErAlAtdItemCondition
    export interface IErAlAtdItemCondition {
        targetNO: number;
        conditionAtr: number;
        useAtr: boolean;
        uncountableAtdItem: number;
        countableAddAtdItems: number;
        countableSubAtdItems: number;
        conditionType: number;
        compareOperator: number;
        singleAtdItem: number;
        compareStartValue: number;
        compareEndValue: number;
        
        displayLeftCompare: string;
        displayLeftOperator: string;
        displayTarget: string;
        displayRightCompare: string;
        displayRightOperator: string;
    }

    export class ErAlAtdItemCondition {

        targetNO: KnockoutObservable<number>;
        conditionAtr: KnockoutObservable<number>;
        useAtr: KnockoutObservable<boolean>;
        uncountableAtdItem: KnockoutObservable<number>;
        countableAddAtdItems: KnockoutObservableArray<number>;
        countableSubAtdItems: KnockoutObservableArray<number>;
        conditionType: KnockoutObservable<number>;
        compareOperator: KnockoutObservable<number>;
        singleAtdItem: KnockoutObservable<number>;
        compareStartValue: KnockoutObservable<number>;
        compareEndValue: KnockoutObservable<number>;

        displayLeftCompare: KnockoutObservable<string>;
        displayLeftOperator: KnockoutObservable<string>;
        displayTarget: KnockoutObservable<string>;
        displayRightCompare: KnockoutObservable<string>;
        displayRightOperator: KnockoutObservable<string>;

        constructor(NO, param) {
            let self = this;
            self.targetNO = ko.observable(NO);
            self.conditionAtr = param ? ko.observable(param.conditionAtr) : ko.observable(0);
            self.useAtr = param ? ko.observable(param.useAtr) : ko.observable(false);
            self.uncountableAtdItem = param ? ko.observable(param.uncountableAtdItem) : ko.observable(null);
            self.countableAddAtdItems = param && param.countableAddAtdItems ? ko.observableArray(param.countableAddAtdItems) : ko.observableArray([]);
            self.countableSubAtdItems = param && param.countableSubAtdItems ? ko.observableArray(param.countableSubAtdItems) : ko.observableArray([]);
            self.conditionType = param ? ko.observable(param.conditionType) : ko.observable(0);
            self.singleAtdItem = param ? ko.observable(param.singleAtdItem) : ko.observable(null);
            self.compareStartValue = param ? ko.observable(param.compareStartValue) : ko.observable(0);
            self.compareEndValue = param ? ko.observable(param.compareEndValue) : ko.observable(0);
            self.compareOperator = param ? ko.observable(param.compareOperator) : ko.observable(0);
            self.displayLeftCompare = ko.observable("");
            self.displayLeftOperator = ko.observable("");
            self.displayTarget = ko.observable("");
            self.displayRightCompare = ko.observable("");
            self.displayRightOperator = ko.observable("");
            self.setTextDisplay();
        }

        setTextDisplay() {
            let self = this;
            if (self.useAtr()) {
                self.setDisplayTarget();
                self.setDisplayOperator();
                self.setDisplayCompare();
            } else {
                self.displayLeftCompare("");
                self.displayLeftOperator("");
                self.displayTarget("");
                self.displayRightCompare("");
                self.displayRightOperator("");
            }
        }

        setDisplayOperator() {
            let self = this;
            self.displayLeftOperator("");
            self.displayRightOperator("");
            switch (self.compareOperator()) {
                case 0:
                    self.displayLeftOperator("＝");
                    break;
                case 1:
                    self.displayLeftOperator("≠");
                    break;
                case 2:
                    self.displayLeftOperator("＞");
                    break;
                case 3:
                    self.displayLeftOperator("≧");
                    break;
                case 4:
                    self.displayLeftOperator("＜");
                    break;
                case 5:
                    self.displayLeftOperator("≦");
                    break;
                case 6:
                    self.displayLeftOperator("＜");
                    self.displayRightOperator("＜");
                    break;
                case 7:
                    self.displayLeftOperator("≦");
                    self.displayRightOperator("≦");
                    break;
                case 8:
                    self.displayLeftOperator("＜");
                    self.displayRightOperator("＜");
                    break;
                case 9:
                    self.displayLeftOperator("≦");
                    self.displayRightOperator("≦");
                    break;
            }
        }

        setDisplayCompare() {
            let self = this;
            let conditionAtr = self.conditionAtr();
            if (self.compareOperator() > 5) {
                // Compare with a range
                let rawStartValue = self.compareStartValue();
                let rawEndValue = self.compareEndValue();
                let textDisplayLeftCompare = (conditionAtr === 0 || conditionAtr === 3) ? rawStartValue : nts.uk.time.parseTime(parseInt(rawStartValue), true).format();
                let textDisplayRightCompare = (conditionAtr === 0 || conditionAtr === 3) ? rawEndValue : nts.uk.time.parseTime(parseInt(rawEndValue), true).format();
                self.displayLeftCompare(textDisplayLeftCompare);
                self.displayRightCompare(textDisplayRightCompare);
            } else {
                // Compare with single value
                if (self.conditionType() === 0) {
                    // If is compare with a fixed value
                    let rawValue = self.compareStartValue();
                    let textDisplayLeftCompare = (conditionAtr === 0 || conditionAtr === 3) ? rawValue : nts.uk.time.parseTime(parseInt(rawValue), true).format();
                    self.displayLeftCompare(textDisplayLeftCompare);
                    self.displayRightCompare("");
                } else {
                    // If is compare with a attendance item
                    if (self.singleAtdItem()) {
                        nts.uk.at.view.kal003.b.service.getAttendanceItemByCodes([self.singleAtdItem()]).done((lstItems) => {
                            if (lstItems && lstItems.length > 0) {
                                self.displayLeftCompare(lstItems[0].attendanceItemName);
                                self.displayRightCompare("");
                            }
                        });
                    }
                }
            }
        }

        setDisplayTarget() {
            let self = this;
            self.displayTarget("");
            if (self.conditionAtr() === 2) {
                if (self.uncountableAtdItem()) {
                    nts.uk.at.view.kal003.b.service.getAttendanceItemByCodes([self.uncountableAtdItem()]).done((lstItems) => {
                        if (lstItems && lstItems.length > 0) {
                            self.displayTarget(lstItems[0].attendanceItemName);
                        }
                    });
                }
            } else {
                if (self.countableAddAtdItems().length > 0) {
                    nts.uk.at.view.kal003.b.service.getAttendanceItemByCodes(self.countableAddAtdItems()).done((lstItems) => {
                        if (lstItems && lstItems.length > 0) {
                            for (let i = 0; i < lstItems.length; i++) {
                                let operator = (i === (lstItems.length - 1)) ? "" : " + ";
                                self.displayTarget(self.displayTarget() + lstItems[i].attendanceItemName + operator);
                            }
                        }
                    }).then(() => {
                        if (self.countableSubAtdItems().length > 0) {
                            nts.uk.at.view.kal003.b.service.getAttendanceItemByCodes(self.countableSubAtdItems()).done((lstItems) => {
                                if (lstItems && lstItems.length > 0) {
                                    for (let i = 0; i < lstItems.length; i++) {
                                        let operator = (i === (lstItems.length - 1)) ? "" : " - ";
                                        let beforeOperator = (i === 0) ? " - " : "";
                                        self.displayTarget(self.displayTarget() + beforeOperator + lstItems[i].attendanceItemName + operator);
                                    }
                                }
                            })
                        }
                    });
                } else if (self.countableSubAtdItems().length > 0) {
                    nts.uk.at.view.kal003.b.service.getAttendanceItemByCodes(self.countableSubAtdItems()).done((lstItems) => {
                        if (lstItems && lstItems.length > 0) {
                            for (let i = 0; i < lstItems.length; i++) {
                                let operator = (i === (lstItems.length - 1)) ? "" : " - ";
                                let beforeOperator = (i === 0) ? " - " : "";
                                self.displayTarget(self.displayTarget() + beforeOperator + lstItems[i].attendanceItemName + operator);
                            }
                        }
                    })
                }

            }
        }

        openAtdItemConditionDialog() {
            let self = this;
            let param = ko.mapping.toJS(self);
            nts.uk.ui.windows.setShared("KAL003CParams", param, true);
            nts.uk.ui.windows.sub.modal("at", "/view/kal/003/c/index.xhtml", {}).onClosed(() => {
                let output = getShared("KAL003CResult");
                if (output) {
                    self.targetNO(output.targetNO);
                    self.conditionAtr(output.conditionAtr);
                    self.useAtr(true);
                    self.uncountableAtdItem(output.uncountableAtdItem);
                    self.countableAddAtdItems(output.countableAddAtdItems);
                    self.countableSubAtdItems(output.countableSubAtdItems);
                    self.conditionType(output.conditionType);
                    self.singleAtdItem(output.singleAtdItem);
                    self.compareStartValue(output.compareStartValue);
                    self.compareEndValue(output.compareEndValue);
                    self.compareOperator(output.compareOperator);
                }
                self.setTextDisplay();
            });
        }

        setData(NO, param) {
            let self = this;
            self.targetNO(NO);
            self.conditionAtr(param ? param.conditionAtr : 0);
            self.useAtr(param ? param.useAtr : false);
            self.uncountableAtdItem(param ? param.uncountableAtdItem : null);
            self.countableAddAtdItems(param && param.countableAddAtdItems ? param.countableAddAtdItems : []);
            self.countableSubAtdItems(param && param.countableSubAtdItems ? param.countableSubAtdItems : []);
            self.conditionType(param ? param.conditionType : 0);
            self.singleAtdItem(param ? param.singleAtdItem : null);
            self.compareStartValue(param && param.compareStartValue ? param.compareStartValue : 0);
            self.compareEndValue(param && param.compareEndValue ? param.compareEndValue : 0);
            self.compareOperator(param ? param.compareOperator : 0);
            self.setTextDisplay();
        }
    }
    // group condition
    export interface IErAlConditionsAttendanceItem {
        atdItemConGroupId: string;
        conditionOperator: number; //0: OR|1: AND
        lstErAlAtdItemCon: Array<ErAlAtdItemCondition>;// max 3
    }

    export class ErAlConditionsAttendanceItem {
        atdItemConGroupId: KnockoutObservable<string>;
        conditionOperator: KnockoutObservable<number>; //OR|AND B15-3, B17-3
        lstErAlAtdItemCon: KnockoutObservableArray<ErAlAtdItemCondition>;// max 3 item, B16-1 -> B16-4
        constructor(param: IErAlConditionsAttendanceItem) {
            let self = this;
            self.atdItemConGroupId = ko.observable(param.atdItemConGroupId || '');
            self.conditionOperator = ko.observable(param.conditionOperator || 0);
            self.lstErAlAtdItemCon = ko.observableArray(param.lstErAlAtdItemCon || []);
        }
    }

    export interface IAttendanceItemCondition {
        group1:         ErAlConditionsAttendanceItem;
        group2UseAtr:   boolean; // B17-1
        group2:         ErAlConditionsAttendanceItem;
        operatorBetweenGroups: number; // B18-2: 0: OR, 1: AND
    }
    export class AttendanceItemCondition {
        group1:         KnockoutObservable<ErAlConditionsAttendanceItem>    = ko.observable(null);
        group2UseAtr:   KnockoutObservable<boolean>                         = ko.observable(false);
        group2:         KnockoutObservable<ErAlConditionsAttendanceItem>    = ko.observable(null);
        operatorBetweenGroups: KnockoutObservable<number>                   = ko.observable(0);
        constructor(param: IAttendanceItemCondition) {
            let self = this;
            self.group1(param.group1);
            self.group2UseAtr(param.group2UseAtr || false);
            self.group2(param.group2);
            self.operatorBetweenGroups(param.operatorBetweenGroups);
        }
    }

    export interface IErrorAlarmCondition { 
        category: number;
        erAlCheckId: string;
        checkItem: number;
        workTypeSelections: Array<string>;
        workTimeItemSelections: Array<string>;
        comparisonOperator: number;
        minimumValue: string;
        maximumValue: string;
        continuousPeriodInput: string;
        workingTimeZoneSelections: Array<string>;
        color: string;
        message: string;
        isBold: boolean;
        workTypeCondition: number;
        workTimeCondition: number;
        atdItemCondition: AttendanceItemCondition;
    }

    export class ErrorAlarmCondition {
        category:               number;
        erAlCheckId:            string;
        checkItem:              KnockoutObservable<number> = ko.observable(0);
        workTypeSelections:     KnockoutObservableArray<string> = ko.observableArray([]);
        workTimeItemSelections: KnockoutObservableArray<string> = ko.observableArray([]);
        comparisonOperator:     KnockoutObservable<number> = ko.observable(0);
        minimumValue:           KnockoutObservable<string> = ko.observable('');
        maximumValue:           KnockoutObservable<string> = ko.observable('');
        continuousPeriodInput:  KnockoutObservable<string> = ko.observable('');
        workingTimeZoneSelections: KnockoutObservableArray<string> = ko.observableArray([]);
        color:                  KnockoutObservable<string> = ko.observable('');
        message:                KnockoutObservable<string> = ko.observable('');
        isBold:                 KnockoutObservable<boolean> = ko.observable(false);
        
        workTypeCondition :     KnockoutObservable<number> = ko.observable(1);
        workTimeCondition:      KnockoutObservable<number> = ko.observable(0);
        
        atdItemCondition :     KnockoutObservable<AttendanceItemCondition> = ko.observable(null);
        constructor(param : IErrorAlarmCondition) {
            let self = this;
            self.category               = param.category || 0;
            self.erAlCheckId            = param.erAlCheckId || '';
            self.checkItem(param.checkItem || 0);

            self.workTypeSelections(param.workTypeSelections || []);
            self.workTimeItemSelections(param.workTimeItemSelections || []);
            self.comparisonOperator( param.comparisonOperator || 0);
            self.minimumValue(param.minimumValue || '');
            self.maximumValue(param.maximumValue || '');
            self.continuousPeriodInput(param.continuousPeriodInput || '');
            self.workingTimeZoneSelections(param.workingTimeZoneSelections || []);
            self.color(param.color || '');
            self.message(param.message || '');
            self.isBold(param.isBold || false);
            self.workTypeCondition(param.workTypeCondition || 1);
            self.workTimeCondition(param.workTimeCondition || 0);
            self.atdItemCondition(param.atdItemCondition);
        }
    }
    
    //---------------- KAL003 - B end------------------//
    //interface WorkRecordExtraCon
        export interface IWorkRecordExtraCon {
            errorAlarmCheckID: string;
            checkItem: number;
            messageBold: boolean;
            messageColor: string;
            sortOrderBy: number;
            useAtr?: boolean;
            nameWKRecord: string;
        }
    
    //class WorkRecordExtraCon
    export class WorkRecordExtraCon {
        errorAlarmCheckID: string;
        checkItem: number;
        messageBold: boolean;
        messageColor: string;
        sortOrderBy: number;
        useAtr: KnockoutObservable<boolean>;
        nameWKRecord: string;
        constructor(data: IWorkRecordExtraCon) {
            this.errorAlarmCheckID = data.errorAlarmCheckID;
            this.checkItem = data.checkItem;
            this.messageBold = data.messageBold;
            this.messageColor = data.messageColor;
            this.sortOrderBy = data.sortOrderBy;
            this.useAtr = ko.observable(data.useAtr);
            this.nameWKRecord = data.nameWKRecord;
        }
    }//end class WorkRecordExtraCon
    
    //interface FixedConditionWorkRecord
    export interface IFixedConditionWorkRecord {
        errorAlarmCode: string;
        fixConWorkRecordNo: number;
        message: string;
        useAtr?: boolean;
    }
    //class FixedConditionWorkRecord
    export class FixedConditionWorkRecord {
        errorAlarmCode: string;
        fixConWorkRecordNo: number;
        message: string;
        useAtr: KnockoutObservable<boolean>;
        constructor(data: IFixedConditionWorkRecord) {
            this.errorAlarmCode = data.errorAlarmCode;
            this.fixConWorkRecordNo = data.fixConWorkRecordNo;
            this.message = data.message;
            this.useAtr = ko.observable(data.useAtr);
        }
    }
}