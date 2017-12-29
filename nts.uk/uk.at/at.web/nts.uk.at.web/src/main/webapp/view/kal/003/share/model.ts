module nts.uk.at.view.kal003.share.model {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import modal = nts.uk.ui.windows.sub.modal;

    export class AlarmCheckConditionByCategory {
        code: KnockoutObservable<string>;
        name: KnockoutObservable<string>;
        category: KnockoutObservable<string>;
        displayCode: string;
        displayName: string;
        displayCategory: string;
        availableRoles: KnockoutObservableArray<string>;
        targetCondition: KnockoutObservable<AlarmCheckTargetCondition>;
        displayAvailableRoles: KnockoutObservable<string>;
        conditionToExtractDaily: KnockoutObservable<number>;

        constructor(code: string, name: string, category: string, availableRoles: Array<string>, targetCondition: AlarmCheckTargetCondition) {
            this.code = ko.observable(code);
            this.name = ko.observable(name);
            this.category = ko.observable(category);
            this.displayCode = code;
            this.displayName = name;
            this.displayCategory = category;
            this.availableRoles = ko.observableArray(availableRoles);
            this.targetCondition = ko.observable(targetCondition);
            this.conditionToExtractDaily = ko.observable(DATA_CONDITION_TO_EXTRACT.ALL);
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

        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;
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
    export interface ICondition{
        itemCheck:              number;
        target:                 number;
        operatorCd:             string;
        comparisonOperatorId:   number;
        itemConditionId:        string;
    
    }
    export class Condition{
        itemCheck:              number;
        target:                 number;
        operatorCd:             string;
        comparisonOperatorId:   number;
        itemConditionId:        string;
        constructor(param: ICondition) {
            let self = this;
            self.itemCheck  = param.itemCheck;
            self.target     = param.target;
            self.operatorCd = param.operatorCd;
            self.comparisonOperatorId = param.comparisonOperatorId;
            self.itemConditionId = param.itemConditionId;
        }
    
    }
    // group condition
    export interface IGroupCondition {
        groupOperator:      number; //0: OR|1: AND
        groupListCondition: Array<Condition>;// max 3
    }
    
    export class GroupCondition {
        groupOperator:      KnockoutObservable<number>; //OR|AND B15-3, B17-3
        groupListCondition: KnockoutObservableArray<Condition>;// max 3 item, B16-1 -> B16-4
        constructor(param: IGroupCondition) {
            let self = this;
            self.groupOperator = ko.observable(param.groupOperator || 0);
            self.groupListCondition = ko.observableArray(param.groupListCondition || []);
        }
    }
    
    export interface ICompoundCondition {
        group1Condition:    GroupCondition;
        hasGroup2:          boolean; // B17-1
        group2Condition:    GroupCondition;
        operatorBetweenG1AndG2: number; // B18-2: 0: OR, 1: AND
    }
    export class CompoundCondition {
        group1Condition:    KnockoutObservable<GroupCondition>;
        hasGroup2:          KnockoutObservable<boolean>;
        group2Condition:    KnockoutObservable<GroupCondition>;
        operatorBetweenG1AndG2: KnockoutObservable<number>;
        constructor(param: ICompoundCondition) {
            let self = this;
            self.group1Condition    = ko.observable(param.group1Condition);
            self.hasGroup2          = ko.observable(param.hasGroup2 || false);
            self.group2Condition    = ko.observable(param.group2Condition);
            self.operatorBetweenG1AndG2 = ko.observable(param.operatorBetweenG1AndG2);
        }
    }
    
    
    
    export interface ISettingCdlKal003B {
        category:               number;
        errAlaCheckId:          string;
        checkItem:              number;
        workTypeRange:          string;
        workTypeSelections:     Array<string>;
        workTimeItemSelections: Array<string>;
        comparisonOperator:     number;
        minimumValue:           string;
        maximumValue:           string;
        continuousPeriodInput:  string;
        workingTimeZoneSelections: Array<string>;
        color:                  string;
        message:                string;
        isBold:                 boolean;
        compoundCondition :     CompoundCondition;
        dailyAttendanceItemId:  string;
    }
    
    export class SettingCdlKal003B {
        category:               number;
        errAlaCheckId:          string;
        checkItem:              KnockoutObservable<number>;
        workTypeRange:          KnockoutObservable<string>;
        workTypeSelections:     KnockoutObservableArray<string>;
        workTimeItemSelections: KnockoutObservableArray<string>;
        comparisonOperator:     KnockoutObservable<number>;
        minimumValue:           KnockoutObservable<string>;
        maximumValue:           KnockoutObservable<string>;
        continuousPeriodInput:  KnockoutObservable<string>;
        workingTimeZoneSelections: KnockoutObservableArray<string>;
        color:                  KnockoutObservable<string>;
        message:                KnockoutObservable<string>;
        isBold:                 KnockoutObservable<boolean>;
        compoundCondition :     KnockoutObservable<CompoundCondition>;
        dailyAttendanceItemId:  KnockoutObservable<string>;
        constructor(param : ISettingCdlKal003B) {
            let self = this;
            self.category               = param.category || 0;
            self.errAlaCheckId          = param.errAlaCheckId || '';
            self.checkItem              = ko.observable(param.checkItem || 0);
            self.workTypeRange          = ko.observable(param.workTypeRange || '');
            self.workTypeSelections     = ko.observableArray(param.workTypeSelections || []);
            self.workTimeItemSelections = ko.observableArray(param.workTimeItemSelections || []);
            self.comparisonOperator     = ko.observable( param.comparisonOperator || 0);
            self.minimumValue           = ko.observable(param.minimumValue || '');
            self.maximumValue           = ko.observable(param.maximumValue || '');
            self.continuousPeriodInput  = ko.observable(param.continuousPeriodInput || '');
            self.workingTimeZoneSelections = ko.observableArray(param.workingTimeZoneSelections || []);
            self.color                  = ko.observable(param.color || '');
            self.message                = ko.observable(param.message || '');
            self.isBold                 = ko.observable(param.isBold || false);
            self.compoundCondition      = ko.observable(param.compoundCondition);
            self.dailyAttendanceItemId  = ko.observable(param.dailyAttendanceItemId);
        }
    }
    //---------------- KAL003 - B end------------------//

}