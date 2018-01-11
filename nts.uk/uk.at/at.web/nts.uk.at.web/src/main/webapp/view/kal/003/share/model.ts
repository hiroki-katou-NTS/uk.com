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

    export function getListConditionToExtractDaily(): Array<ItemModel> {
        return [
            new model.ItemModel(0, '全て'),
            new model.ItemModel(1, '確認済のデータ'),
            new model.ItemModel(2, '未確認のデータ')
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

    export enum SCREEN_MODE {
        NEW = 0,
        UPDATE = 1
    }
    //---------------- KAL003 - B begin----------------//
    //Condition of group (C screen)
    export interface ICondition {
        itemCheck: number;
        target: number;
        operatorCd: string;
        comparisonOperatorId: number;
        itemConditionId: string;

    }
    export class Condition {
        itemCheck: number;
        target: number;
        operatorCd: string;
        comparisonOperatorId: number;
        itemConditionId: string;
        constructor(param: ICondition) {
            let self = this;
            self.itemCheck = param.itemCheck || 0;
            self.target = param.target || 0;
            self.operatorCd = param.operatorCd || '';
            self.comparisonOperatorId = param.comparisonOperatorId || 0;
            self.itemConditionId = param.itemConditionId || '';
        }
    }
    // group condition
    export interface IGroupCondition {
        groupOperator: number; //0: OR|1: AND
        groupListCondition: Array<Condition>;// max 3
    }

    export class GroupCondition {
        groupOperator: KnockoutObservable<number>; //OR|AND B15-3, B17-3
        groupListCondition: KnockoutObservableArray<Condition>;// max 3 item, B16-1 -> B16-4
        constructor(param: IGroupCondition) {
            let self = this;
            self.groupOperator = ko.observable(param.groupOperator || 0);
            self.groupListCondition = ko.observableArray(param.groupListCondition || []);
        }
    }

    export interface ICompoundCondition {
        group1Condition: GroupCondition;
        hasGroup2: boolean; // B17-1
        group2Condition: GroupCondition;
        operatorBetweenG1AndG2: number; // B18-2: 0: OR, 1: AND
    }
    export class CompoundCondition {
        group1Condition:    KnockoutObservable<GroupCondition> = ko.observable(null);
        hasGroup2:          KnockoutObservable<boolean> = ko.observable(false);
        group2Condition:    KnockoutObservable<GroupCondition> = ko.observable(null);
        operatorBetweenG1AndG2: KnockoutObservable<number> = ko.observable(0);
        constructor(param: ICompoundCondition) {
            let self = this;
            self.group1Condition(param.group1Condition);
            self.hasGroup2 (param.hasGroup2 || false);
            self.group2Condition(param.group2Condition);
            self.operatorBetweenG1AndG2(param.operatorBetweenG1AndG2);
        }
    }

    export interface IErrorAlarmCondition {
        category: number;
        erAlCheckId: string;
        checkItem: number;
        workTypeRange: string;
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
        compoundCondition: CompoundCondition;
    }

    export class ErrorAlarmCondition {
        category:               number;
        erAlCheckId:            string;
        checkItem:              KnockoutObservable<number> = ko.observable(0);
        workTypeRange:          KnockoutObservable<string> = ko.observable('');
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
        compoundCondition :     KnockoutObservable<CompoundCondition> = ko.observable(null);
        constructor(param : IErrorAlarmCondition) {
            let self = this;
            self.category               = param.category || 0;
            self.erAlCheckId            = param.erAlCheckId || '';
            self.checkItem(param.checkItem || 0);
            self.workTypeRange(param.workTypeRange || '');
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
            self.compoundCondition(param.compoundCondition);
        }
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
    //---------------- KAL003 - B end------------------//
}