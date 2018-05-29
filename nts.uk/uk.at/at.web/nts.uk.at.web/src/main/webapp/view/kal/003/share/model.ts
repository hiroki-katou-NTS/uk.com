module nts.uk.at.view.kal003.share.model {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import modal = nts.uk.ui.windows.sub.modal;
    import getText = nts.uk.resource.getText;

    export function getListCategory(): Array<ItemModel> {
        return [
//            new ItemModel(0, getText('Enum_AlarmCategory_SCHEDULE_DAILY')),
//            new ItemModel(1, getText('Enum_AlarmCategory_SCHEDULE_WEEKLY')),
            new ItemModel(2, getText('Enum_AlarmCategory_SCHEDULE_4WEEK')),
//            new ItemModel(3, getText('Enum_AlarmCategory_SCHEDULE_MONTHLY')),
//            new ItemModel(4, getText('Enum_AlarmCategory_SCHEDULE_YEAR')),
            new ItemModel(5, getText('Enum_AlarmCategory_DAILY')),
//            new ItemModel(6, getText('Enum_AlarmCategory_WEEKLY')),
            new ItemModel(7, getText('Enum_AlarmCategory_MONTHLY')),
//            new ItemModel(8, getText('Enum_AlarmCategory_APPLICATION_APPROVAL')),
//            new ItemModel(9, getText('Enum_AlarmCategory_MULTIPLE_MONTH')),
//            new ItemModel(10, getText('Enum_AlarmCategory_ANY_PERIOD')),
//            new ItemModel(11, getText('Enum_AlarmCategory_ATTENDANCE_RATE_FOR_HOLIDAY')),
            new ItemModel(12, getText('Enum_AlarmCategory_AGREEMENT')),
//            new ItemModel(13, getText('Enum_AlarmCategory_MAN_HOUR_CHECK'))
        ];
    }

    export function getConditionToExtractDaily(): Array<ItemModel> {
        return [
            new model.ItemModel(0, getText('Enum_ConExtractedDaily_ALL')),
            new model.ItemModel(1, getText('Enum_ConExtractedDaily_CONFIRMED_DATA')),
            new model.ItemModel(2, getText('Enum_ConExtractedDaily_UNCONFIRMER_DATA'))
        ];
    }

    export function getSchedule4WeekAlarmCheckCondition(): Array<ItemModel> {
        return [
            new model.ItemModel(0, '実績のみで4週4休をチェックする'),
            new model.ItemModel(1, 'スケジュールと実績で4週4休をチェックする')
        ];
    }

    export function getErrorClassification(): Array<ItemModel> {
        return [
            new model.ItemModel(0, getText('Enum_ErrorAlarmClassification_Error')),
            new model.ItemModel(1, getText('Enum_ErrorAlarmClassification_Alarm')),
            new model.ItemModel(2, getText('Enum_ErrorAlarmClassification_Other'))
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
        dailyAlarmCheckCondition: KnockoutObservable<DailyAlarmCheckCondition> = ko.observable(new DailyAlarmCheckCondition(DATA_CONDITION_TO_EXTRACT.ALL, false, [], [], []));
        schedule4WeekAlarmCheckCondition: KnockoutObservable<Schedule4WeekAlarmCheckCondition> = ko.observable(new Schedule4WeekAlarmCheckCondition(SCHEDULE_4_WEEK_CHECK_CONDITION.FOR_ACTUAL_RESULTS_ONLY));
        action: KnockoutObservable<number> = ko.observable(0);
        condAgree36: KnockoutObservable<Agreement36> = ko.observable(new Agreement36([], []));
        monAlarmCheckCon: KnockoutObservable<MonAlarmCheckCon> = ko.observable(new MonAlarmCheckCon([]));
        constructor(code: string, name: string, category: ItemModel, availableRoles: Array<string>, targetCondition: AlarmCheckTargetCondition) {
            this.code = ko.observable(code);
            this.name = ko.observable(name);
            this.category = ko.observable(category != null ? category.code : null);
            this.displayCode = code;
            this.displayName = name;
            this.displayCategory = category != null ? category.name : "";
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
            this.displayTargetEmployment = ko.observable("");
            this.displayTargetClassification = ko.observable("");
            this.displayTargetJobTitle = ko.observable("");
            this.displayTargetBusinessType = ko.observable("");

            this.targetEmployment.subscribe((data) => {
                kal003.a.service.getEmpNameByCodes(data).done((result: Array<string>) => {
                    this.displayTargetEmployment(result.join(", "));
                }).fail(() => {
                    this.displayTargetEmployment(this.targetClassification().join(", "));
                });
            });

            this.targetClassification.subscribe((data) => {
                kal003.a.service.getClsNameByCodes(data).done((result: Array<string>) => {
                    this.displayTargetClassification(result.join(", "));
                }).fail(() => {
                    this.displayTargetClassification(this.targetClassification().join(", "));
                });
            });

            this.targetJobTitle.subscribe((data) => {
                kal003.a.service.getJobNamesByIds(data).done((result: Array<string>) => {
                    this.displayTargetJobTitle(result.join(", "));
                }).fail(() => {
                    this.displayTargetJobTitle(this.targetClassification().join(", "));
                });
            });

            this.targetBusinessType.subscribe((data) => {
                kal003.a.service.getBusTypeNamesByCodes(data).done((result: Array<string>) => {
                    this.displayTargetBusinessType(result.join(", "));
                }).fail(() => {
                    this.displayTargetBusinessType(this.targetClassification().join(", "));
                });
            });
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
        conditionToExtractDaily: KnockoutObservable<number>;//main screen
        addApplication: KnockoutObservable<boolean>;//tab daily
        listErrorAlarmCode: KnockoutObservableArray<string>;//tab daily
        listExtractConditionWorkRecork: KnockoutObservableArray<WorkRecordExtractingCondition>;//tab check condition
        listFixedExtractConditionWorkRecord: KnockoutObservableArray<FixedConditionWorkRecord>;//tab  fixed

        constructor(conditionToExtractDaily: number, addApplication: boolean, listErrorAlarmCode: Array<string>, listWorkRecordExtractingConditions: Array<WorkRecordExtractingCondition>, listFixedConditionWorkRecord: Array<FixedConditionWorkRecord>) {
            this.conditionToExtractDaily = ko.observable(conditionToExtractDaily);
            this.addApplication = ko.observable(addApplication);
            this.listErrorAlarmCode = ko.observableArray(listErrorAlarmCode);
            this.listExtractConditionWorkRecork = ko.observableArray(listWorkRecordExtractingConditions);
            this.listFixedExtractConditionWorkRecord = ko.observableArray(listFixedConditionWorkRecord);
        }
    }

    export class Schedule4WeekAlarmCheckCondition {
        schedule4WeekCheckCondition: KnockoutObservable<number>;

        constructor(schedule4WeekCheckCondition: number) {
            this.schedule4WeekCheckCondition = ko.observable(schedule4WeekCheckCondition);
        }
    }

    export class Agreement36 {
        listCondOt: KnockoutObservableArray<AgreeCondOt>;//tab agreement hour
        listCondError: KnockoutObservableArray<AgreeConditionErrorDto>;//tab agreement error

        constructor(listCondOt: Array<AgreeCondOt>, listCondError: Array<AgreeConditionErrorDto>) {
            this.listCondOt = ko.observableArray(listCondOt);
            this.listCondError = ko.observableArray(listCondError);
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

    export enum ERROR_CLASSIFICATION {
        ERROR = 0,
        ALARM = 1,
        OTHER = 2
    }
    // class and interface : ExtraResultMonthly
    export interface IExtraResultMonthly{
        errorAlarmCheckID : string;
        sortBy : number;
        nameAlarmExtraCon : string;
        useAtr : boolean;
        typeCheckItem : number;
        messageBold : boolean;
        messageColor : string;
        displayMessage : string;
        checkConMonthly : IAttendanceItemCondition;
        rowId: number;
        specHolidayCheckCon : ISpecHolidayCheckCon;
        checkRemainNumberMon : ICheckRemainNumberMon;
        agreementCheckCon36 : IAgreementCheckCon36;
        
    }
    
    export class ExtraResultMonthly{
        errorAlarmCheckID : string;
        sortBy : number;
        nameAlarmExtraCon : KnockoutObservable<string> = ko.observable('');
        useAtr : KnockoutObservable<boolean> = ko.observable(false);
        typeCheckItem : KnockoutObservable<number> = ko.observable(0);
        messageBold : KnockoutObservable<boolean> = ko.observable(false);
        messageColor : KnockoutObservable<string> = ko.observable('');
        displayMessage : KnockoutObservable<string> = ko.observable('');
        checkConMonthly : KnockoutObservable<AttendanceItemCondition>;
        rowId: KnockoutObservable<number> = ko.observable(0);
        specHolidayCheckCon : KnockoutObservable<SpecHolidayCheckCon> = ko.observable(null);
        checkRemainNumberMon : KnockoutObservable<CheckRemainNumberMon> = ko.observable(null);
        agreementCheckCon36 : KnockoutObservable<AgreementCheckCon36> = ko.observable(null); 
        constructor(param: IExtraResultMonthly) {
            let self = this;
            self.errorAlarmCheckID = param.errorAlarmCheckID || '';
            self.sortBy = param.sortBy || 0;
            self.nameAlarmExtraCon(param.nameAlarmExtraCon);
            self.useAtr(param.useAtr || false);
            self.typeCheckItem(param.typeCheckItem || 0);
            self.messageBold(param.messageBold);
            self.messageColor(param.messageColor);
            self.displayMessage(param.displayMessage || '');
            self.checkConMonthly = ko.observable(param && param.checkConMonthly ? new AttendanceItemCondition(param.checkConMonthly) : null);
            self.rowId(param.rowId || 0);
            self.specHolidayCheckCon( param && param.specHolidayCheckCon?new SpecHolidayCheckCon(param.specHolidayCheckCon) :null);
            self.checkRemainNumberMon( param && param.checkRemainNumberMon?new CheckRemainNumberMon (param.checkRemainNumberMon) :null);
            self.agreementCheckCon36( param && param.agreementCheckCon36?  new AgreementCheckCon36(param.agreementCheckCon36) : null);
        }
    }
    
    export interface ISpecHolidayCheckCon{
        errorAlarmCheckID : string ;
        compareOperator : number;
        numberDayDiffHoliday1 : number;
        numberDayDiffHoliday2 : number;
    }
    
    export class SpecHolidayCheckCon{
        errorAlarmCheckID : string ;
        compareOperator : KnockoutObservable<number> = ko.observable(0);
        numberDayDiffHoliday1 : KnockoutObservable<number> = ko.observable(0);
        numberDayDiffHoliday2 : KnockoutObservable<number> = ko.observable(null);
        constructor(data : ISpecHolidayCheckCon){
            this.errorAlarmCheckID = data.errorAlarmCheckID;
            this.compareOperator(data.compareOperator || 0);
            this.numberDayDiffHoliday1(data.numberDayDiffHoliday1 || 0);
            this.numberDayDiffHoliday2(data.numberDayDiffHoliday2 || null);    
        }
    }
    
    
    
    export interface ICheckRemainNumberMon{
        errorAlarmCheckID : string ;
        checkVacation : number;
        checkOperatorType : number;
        compareRangeEx : ICompareRangeImport;
        compareSingleValueEx : ICompareSingleValueImport;
    }
    
    export class CheckRemainNumberMon{
        errorAlarmCheckID : string ;
        checkVacation : KnockoutObservable<number> = ko.observable(0);
        checkOperatorType : KnockoutObservable<number> = ko.observable(0);
        compareRangeEx : KnockoutObservable<CompareRangeImport> = ko.observable(null); 
        compareSingleValueEx : KnockoutObservable<CompareSingleValueImport> = ko.observable(null);
        constructor(data : ICheckRemainNumberMon){
            this.errorAlarmCheckID = data.errorAlarmCheckID;
            this.checkVacation(data.checkVacation || 0);
            this.checkOperatorType(data.checkOperatorType || 0);
            this.compareRangeEx(data && data.compareRangeEx?new CompareRangeImport(data.compareRangeEx) : null);
            this.compareSingleValueEx(data && data.compareSingleValueEx? new CompareSingleValueImport( data.compareSingleValueEx) : null);
            
        }
    }
    
    export interface ICompareRangeImport{
        compareOperator : number;
        startValue : ICheckConValueRemainNumberImport;
        endValue :  ICheckConValueRemainNumberImport;
    }
    
    export class CompareRangeImport{
        compareOperator : KnockoutObservable<number> = ko.observable(0);
        startValue : KnockoutObservable<CheckConValueRemainNumberImport> = ko.observable(null);
        endValue : KnockoutObservable<CheckConValueRemainNumberImport> = ko.observable(null);   
        constructor(data : ICompareRangeImport ){
        this.compareOperator(data.compareOperator ||0);
            this.startValue(data && data.startValue?new CheckConValueRemainNumberImport(data.startValue) : null);    
            this.endValue(data && data.endValue?new CheckConValueRemainNumberImport(data.endValue) : null);
        }
    }
    
    export interface ICompareSingleValueImport{
        compareOperator : number;
        value : ICheckConValueRemainNumberImport;
    }
    
    export class CompareSingleValueImport{
        compareOperator : KnockoutObservable<number> = ko.observable(0);
        value : KnockoutObservable<CheckConValueRemainNumberImport> = ko.observable(null);
        constructor(data : ICompareSingleValueImport){
        this.compareOperator(data.compareOperator ||0);
        this.value(data && data.value?new CheckConValueRemainNumberImport(data.value) : null);
        }
    }
    
    export interface ICheckConValueRemainNumberImport{
        daysValue : number;
        timeValue :number;
    }
    
    export class CheckConValueRemainNumberImport {
        daysValue : KnockoutObservable<number> = ko.observable(0);
        timeValue : KnockoutObservable<number> = ko.observable(0);
        constructor(data : ICheckConValueRemainNumberImport){
            this.daysValue(data.daysValue || 0);
            this.timeValue(data.timeValue || null); 
        }
    }
    
    export interface IAgreementCheckCon36{
        errorAlarmCheckID: string;
        classification : number;
        compareOperator : number;
        eralBeforeTime : number;
    }
    
    export class AgreementCheckCon36{
        errorAlarmCheckID: string;
        classification : KnockoutObservable<number> = ko.observable(0);
        compareOperator : KnockoutObservable<number> = ko.observable(0);
        eralBeforeTime : KnockoutObservable<number> = ko.observable(0);
        constructor(data : IAgreementCheckCon36){
            this.errorAlarmCheckID = data.errorAlarmCheckID;
            this.classification(data.classification || 0);
            this.compareOperator(data.compareOperator || 0);
            this.eralBeforeTime(data.eralBeforeTime || 0);
        }
    }

    export interface IWorkRecordExtractingCondition {
        errorAlarmCheckID: string;
        checkItem: number;
        messageBold: boolean;
        messageColor: string;
        sortOrderBy: number;
        useAtr: boolean;
        nameWKRecord: string;
        errorAlarmCondition: ErrorAlarmCondition;
        rowId: number;
    }

    export class WorkRecordExtractingCondition {
        errorAlarmCheckID: string;
        checkItem: KnockoutObservable<number> = ko.observable(0);
        messageBold: KnockoutObservable<boolean> = ko.observable(false);
        messageColor: KnockoutObservable<string> = ko.observable('');
        sortOrderBy: number;
        useAtr: KnockoutObservable<boolean> = ko.observable(false);
        nameWKRecord: KnockoutObservable<string> = ko.observable('');
        errorAlarmCondition: KnockoutObservable<ErrorAlarmCondition>;
        rowId: KnockoutObservable<number> = ko.observable(0);
        constructor(param: IWorkRecordExtractingCondition) {
            let self = this;
            self.errorAlarmCheckID = param.errorAlarmCheckID || '';
            self.checkItem(param.checkItem || 0);
            self.messageBold(param.messageBold);
            self.messageColor(param.messageColor);
            self.sortOrderBy = param.sortOrderBy || 0;
            self.useAtr(param.useAtr || false);
            self.nameWKRecord(param.nameWKRecord || '');
            self.errorAlarmCondition = ko.observable(param.errorAlarmCondition);
            self.rowId(param.rowId || 0);
        }
    }

    //---------------- KAL003 - B begin----------------//
    //Condition of group (C screen) ErAlAtdItemCondition
    // 勤怠項目のエラーアラーム条件
    export interface IErAlAtdItemCondition {
        targetNO: number;
        conditionAtr: number;
        useAtr: boolean;
        uncountableAtdItem: number;
        countableAddAtdItems: Array<number>;
        countableSubAtdItems: Array<number>;
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
        countableAddAtdItems: KnockoutObservableArray<number> = ko.observableArray([]);
        countableSubAtdItems: KnockoutObservableArray<number> = ko.observableArray([]);
        conditionType: KnockoutObservable<number>;
        compareOperator: KnockoutObservable<number>;
        singleAtdItem: KnockoutObservable<number>;
        compareStartValue: KnockoutObservable<number>;
        compareEndValue: KnockoutObservable<number>;

        displayLeftCompare: KnockoutObservable<any>;
        displayLeftOperator: KnockoutObservable<any>;
        displayTarget: KnockoutObservable<any>;
        displayRightCompare: KnockoutObservable<any>;
        displayRightOperator: KnockoutObservable<any>;

        constructor(NO, param: IErAlAtdItemCondition) {
            let self = this;
            self.targetNO = ko.observable(NO);
            self.conditionAtr = param ? ko.observable(param.conditionAtr) : ko.observable(0);
            self.useAtr = param ? ko.observable(param.useAtr) : ko.observable(true);
            self.uncountableAtdItem = param ? ko.observable(param.uncountableAtdItem) : ko.observable(null);
            self.countableAddAtdItems(param && param.countableAddAtdItems ? param.countableAddAtdItems : []);
            self.countableSubAtdItems(param && param.countableSubAtdItems ? param.countableSubAtdItems : []);
            self.conditionType = param ? ko.observable(param.conditionType) : ko.observable(1);   //1: 勤怠項目 - AttendanceItem, 0: fix
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
                let textDisplayLeftCompare = (conditionAtr === 0 || conditionAtr === 3) ? rawStartValue : nts.uk.time.parseTime(rawStartValue, true).format();
                let textDisplayRightCompare = (conditionAtr === 0 || conditionAtr === 3) ? rawEndValue : nts.uk.time.parseTime(rawEndValue, true).format();
                self.displayLeftCompare(textDisplayLeftCompare);
                self.displayRightCompare(textDisplayRightCompare);
            } else {
                // Compare with single value
                if (self.conditionType() === 0) {
                    // If is compare with a fixed value
                    let rawValue = self.compareStartValue();
                    let textDisplayLeftCompare = (conditionAtr === 0 || conditionAtr === 3) ? rawValue : nts.uk.time.parseTime(rawValue, true).format();
                    self.displayLeftCompare(textDisplayLeftCompare);
                    self.displayRightCompare("");
                } else {
                    // If is compare with a attendance item
                    if (self.singleAtdItem()) {
                        //nts.uk.at.view.kal003.b.service.getAttendanceItemByCodes([self.singleAtdItem()]).done((lstItems) => {
                        self.getAttendanceItemByCodes([self.singleAtdItem()]).done((lstItems) => {
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
                    //nts.uk.at.view.kal003.b.service.getAttendanceItemByCodes([self.uncountableAtdItem()]).done((lstItems) => {
                    self.getAttendanceItemByCodes([self.uncountableAtdItem()]).done((lstItems) => {
                        if (lstItems && lstItems.length > 0) {
                            self.displayTarget(lstItems[0].attendanceItemName);
                        }
                    });
                }
            } else {
                if (self.countableAddAtdItems().length > 0) {
                    //nts.uk.at.view.kal003.b.service.getAttendanceItemByCodes(self.countableAddAtdItems()).done((lstItems) => {
                    self.getAttendanceItemByCodes(self.countableAddAtdItems()).done((lstItems) => {
                        if (lstItems && lstItems.length > 0) {
                            for (let i = 0; i < lstItems.length; i++) {
                                let operator = (i === (lstItems.length - 1)) ? "" : " + ";
                                self.displayTarget(self.displayTarget() + lstItems[i].attendanceItemName + operator);
                            }
                        }
                    }).then(() => {
                        if (self.countableSubAtdItems().length > 0) {
                            //nts.uk.at.view.kal003.b.service.getAttendanceItemByCodes(self.countableSubAtdItems()).done((lstItems) => {
                            self.getAttendanceItemByCodes(self.countableSubAtdItems()).done((lstItems) => {
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
                    //nts.uk.at.view.kal003.b.service.getAttendanceItemByCodes(self.countableSubAtdItems()).done((lstItems) => {
                    self.getAttendanceItemByCodes(self.countableSubAtdItems()).done((lstItems) => {
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
            if (self.compareStartValue() == null) self.compareStartValue(0);
            if (self.compareEndValue() == null) self.compareEndValue(0);
            let param = ko.mapping.toJS(self);

            nts.uk.ui.windows.setShared("KDW007BParams", param, true);
            nts.uk.ui.windows.sub.modal("at", "/view/kdw/007/b/index.xhtml", { title: "計算式の設定" }).onClosed(() => {
                let output = getShared("KDW007BResult");
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
        //the same kdw007
        getAttendanceItemByCodes(codes): JQueryPromise<any> {
            return nts.uk.request.ajax("at", "at/record/divergencetime/AttendanceDivergenceName", codes);
        }
    }
    // group condition
    export interface IErAlConditionsAttendanceItem {
        atdItemConGroupId: string;
        conditionOperator: number; //0: OR|1: AND
        lstErAlAtdItemCon: Array<IErAlAtdItemCondition>;// max 3
    }

    export class ErAlConditionsAttendanceItem {
        atdItemConGroupId: KnockoutObservable<string>;
        conditionOperator: KnockoutObservable<number>; //OR|AND B15-3, B17-3
        lstErAlAtdItemCon: KnockoutObservableArray<ErAlAtdItemCondition>;// max 3 item, B16-1 -> B16-4
        constructor(param: IErAlConditionsAttendanceItem) {
            let self = this;
            self.atdItemConGroupId = ko.observable(param ? param.atdItemConGroupId || '' : '');
            self.conditionOperator = ko.observable(param ? param.conditionOperator || 0 : 0);
            self.lstErAlAtdItemCon = ko.observableArray(param ? _.map(param.lstErAlAtdItemCon,acc =>{ return new ErAlAtdItemCondition(acc); }) || [] : []);
        }
    }

    // BA2_3 - group1 and group2UseAtr is false
    export interface IAttendanceItemCondition {
        group1: IErAlConditionsAttendanceItem;
        group2UseAtr: boolean; // B17-1
        group2: IErAlConditionsAttendanceItem;
        operatorBetweenGroups: number; // B18-2: 0: OR, 1: AND
    }
    export class AttendanceItemCondition {
        group1: KnockoutObservable<ErAlConditionsAttendanceItem>;
        group2UseAtr: KnockoutObservable<boolean> = ko.observable(false);
        group2: KnockoutObservable<ErAlConditionsAttendanceItem>;
        operatorBetweenGroups: KnockoutObservable<number> = ko.observable(0);
        constructor(param: IAttendanceItemCondition) {
            let self = this;
            self.group1 = ko.observable(param ? new ErAlConditionsAttendanceItem(param.group1) : null);
            self.group2UseAtr(param ? param.group2UseAtr || false : false);
            self.group2 = ko.observable(param ? new ErAlConditionsAttendanceItem(param.group2) : null);
            self.operatorBetweenGroups(param ? param.operatorBetweenGroups || 0 : 0);
        }
    }

    // アラームチェック対象者の条件
    export interface IAlCheckTargetCondition {
        filterByBusinessType: boolean;
        filterByJobTitle: boolean;
        filterByEmployment: boolean;
        filterByClassification: boolean;
        lstBusinessTypeCode: Array<string>;
        lstJobTitleId: Array<string>;
        lstEmploymentCode: Array<string>;
        lstClassificationCode: Array<string>;
    }

    export class AlCheckTargetCondition {
        filterByBusinessType: boolean;
        filterByJobTitle: boolean;
        filterByEmployment: boolean;
        filterByClassification: boolean;
        lstBusinessTypeCode: Array<string>;
        lstJobTitleId: Array<string>;
        lstEmploymentCode: Array<string>;
        lstClassificationCode: Array<string>;
        constructor(param) {
            let self = this;
            self.filterByBusinessType = param.filterByBusinessType || false;
            self.filterByJobTitle = param.filterByJobTitle || false;
            self.filterByEmployment = param.filterByEmployment || false;
            self.filterByClassification = param.filterByClassification || false;
            self.lstBusinessTypeCode = param.lstBusinessTypeCode || [];
            self.lstJobTitleId = param.lstJobTitleId || [];
            self.lstEmploymentCode = param.lstEmploymentCode || [];
            self.lstClassificationCode = param.lstClassificationCode || [];
        }
    }

    //BA5_3
    export interface IWorkTimeCondition {
        useAtr: boolean;
        comparePlanAndActual: number;
        planFilterAtr: boolean;
        planLstWorkTime: Array<string>;
        actualFilterAtr: boolean;
        actualLstWorkTime: Array<string>;
    }
    export class WorkTimeCondition {
        useAtr: boolean;
        comparePlanAndActual: KnockoutObservable<number> = ko.observable(0);
        planFilterAtr: boolean;
        planLstWorkTime: KnockoutObservableArray<string> = ko.observableArray([]);
        actualFilterAtr: boolean;
        actualLstWorkTime: Array<string>;

        constructor(param: IWorkTimeCondition) {
            let self = this;
            self.useAtr = param && param.useAtr ? param.useAtr : false;
            self.comparePlanAndActual(param && param.comparePlanAndActual ? param.comparePlanAndActual : 0);
            self.planFilterAtr = param && param.planFilterAtr ? param.planFilterAtr : false;
            self.planLstWorkTime(param && param.planLstWorkTime ? param.planLstWorkTime : []);
            self.actualFilterAtr = param && param.actualFilterAtr ? param.actualFilterAtr : false;
            self.actualLstWorkTime = param && param.actualLstWorkTime ? param.actualLstWorkTime : [];
        }
    }
    //BA1-4
    export interface IWorkTypeCondition {
        useAtr: boolean;
        comparePlanAndActual: number;
        planFilterAtr: boolean;
        planLstWorkType: Array<string>;
        actualFilterAtr: boolean;
        actualLstWorkType: Array<string>;
    }

    export class WorkTypeCondition {
        useAtr: boolean;
        comparePlanAndActual: KnockoutObservable<number> = ko.observable(0);
        planFilterAtr: boolean;
        planLstWorkType: KnockoutObservableArray<string> = ko.observableArray([]);
        actualFilterAtr: boolean;
        actualLstWorkType: Array<string>;
        constructor(param: IWorkTypeCondition) {
            let self = this;
            self.useAtr = param && param.useAtr ? param.useAtr : false;
            self.comparePlanAndActual(param ? (param.comparePlanAndActual == 0 ? 0 : param.comparePlanAndActual || 1) : 1); //default is 1
            self.planFilterAtr = param && param.planFilterAtr ? param.planFilterAtr : false;
            self.planLstWorkType(param && param.planLstWorkType ? param.planLstWorkType : []);
            self.actualFilterAtr = param && param.actualFilterAtr ? param.actualFilterAtr : false;
            self.actualLstWorkType = param && param.actualLstWorkType ? param.actualLstWorkType : [];
        }
    }

    //勤務実績のエラーアラームチェック
    export interface IErrorAlarmCondition {
        errorAlarmCheckID: string;
        displayMessage: string;
        alCheckTargetCondition: AlCheckTargetCondition;
        workTypeCondition: WorkTypeCondition; // 勤務種類の条件
        workTimeCondition: WorkTimeCondition; // 勤怠項目のエラーアラーム条件
        atdItemCondition: AttendanceItemCondition;
        continuousPeriod: number; //連続期間
    }

    export class ErrorAlarmCondition {
        errorAlarmCheckID: KnockoutObservable<string> = ko.observable('');
        displayMessage: KnockoutObservable<string> = ko.observable('');
        alCheckTargetCondition: KnockoutObservable<AlCheckTargetCondition>;
        workTypeCondition: KnockoutObservable<WorkTypeCondition>;
        workTimeCondition: KnockoutObservable<WorkTimeCondition>;
        atdItemCondition: KnockoutObservable<AttendanceItemCondition>;
        continuousPeriod: KnockoutObservable<number> = ko.observable(null); //連続期間
        constructor(param: IErrorAlarmCondition) {
            let self = this;
            self.errorAlarmCheckID(param && param.errorAlarmCheckID ? param.errorAlarmCheckID : '')
            self.displayMessage(param && param.displayMessage ? param.displayMessage : '');
            self.alCheckTargetCondition = ko.observable(param && param.alCheckTargetCondition ? param.alCheckTargetCondition : null);
            self.workTypeCondition = ko.observable(param && param.workTypeCondition ? param.workTypeCondition : null);
            self.workTimeCondition = ko.observable(param && param.workTimeCondition ? param.workTimeCondition : null);
            self.atdItemCondition = ko.observable(param && param.atdItemCondition ? param.atdItemCondition : null);
            self.continuousPeriod(param.continuousPeriod || 0); //連続期間
        }
    }

    //---------------- KAL003 - B end------------------//

    export class DailyErrorAlarmCheck {
        code: string;
        name: string;
        classification: number;
        displayClassification: string;
        message: string;
        constructor(code: string, name: string, classification: number, message: string) {
            this.code = code;
            this.name = name;
            let item = _.find(ko.toJS(getErrorClassification()), (x: model.ItemModel) => x.code == classification);
            this.classification = item.code;
            this.displayClassification = item.name;
            this.message = message;
        }
    }

    //interface FixedConditionWorkRecord
    export interface IFixedConditionWorkRecord {
        dailyAlarmConID: string;
        checkName: string;
        fixConWorkRecordNo: number;
        message: string;
        useAtr: boolean;
    }



    //class FixedConditionWorkRecord
    export class FixedConditionWorkRecord {
        dailyAlarmConID: string;
        fixConWorkRecordNo: KnockoutObservable<number>;
        checkName: string;
        message: KnockoutObservable<string>;
        useAtr: KnockoutObservable<boolean>;
        constructor(data: IFixedConditionWorkRecord) {
            this.dailyAlarmConID = data.dailyAlarmConID;
            this.fixConWorkRecordNo = ko.observable(data.fixConWorkRecordNo);
            this.message = ko.observable(data.message);
            this.useAtr = ko.observable(data.useAtr);
            this.checkName = data.checkName;
        }
    }

    export interface IAgreeCondOt {
        category: number;
        code: string;
        id: string;
        no: number;
        ot36: number;
        excessNum: number;
        messageDisp: string;
    }

    export class AgreeCondOt {
        category: number;
        code: string;
        id: string;
        no: number;
        ot36: KnockoutObservable<number>;
        excessNum: KnockoutObservable<number>;
        messageDisp: KnockoutObservable<string>;

        constructor(param: IAgreeCondOt) {
            this.category = param.category;
            this.code = param.code;
            this.id = param.id;
            this.no = param.no;
            this.ot36 = ko.observable(param.ot36);
            this.excessNum = ko.observable(param.excessNum);
            this.messageDisp = ko.observable(param.messageDisp);
        }
    }

    export interface IAgreeConditionErrorDto {
        category: number;
        code: string;
        id: string;
        useAtr: number;
        period: number;
        errorAlarm: number;
        messageDisp: string;
        name: string;
    }

    export class AgreeConditionErrorDto {
        category: number;
        code: string;
        id: string;
        useAtr: KnockoutObservable<number>;
        period: number;
        errorAlarm: number;
        messageDisp: KnockoutObservable<string>;
        name: string;

        constructor(param: IAgreeConditionErrorDto) {
            this.category = param.category;
            this.code = param.code;
            this.id = param.id;
            this.useAtr = ko.observable(param.useAtr);
            this.period = param.period;
            this.errorAlarm = param.errorAlarm;
            this.messageDisp = ko.observable(param.messageDisp);
            this.name = param.name;
        }
    }

    //monthly
    export class MonAlarmCheckCon {
        listFixExtraMon: KnockoutObservableArray<FixedExtraMonFun>;
        listExtraResultMonthly : KnockoutObservableArray<ExtraResultMonthly>;
        constructor(listFixExtraMon: KnockoutObservableArray<FixedExtraMonFun>,listExtraResultMonthly : KnockoutObservableArray<ExtraResultMonthly>
            ) {
            this.listFixExtraMon = ko.observableArray(listFixExtraMon);
            this.listExtraResultMonthly = ko.observableArray(listExtraResultMonthly);
        }
    }

    //interface FixedExtraMonFun
    export interface IFixedExtraMonFun {
        monAlarmCheckID: string;
        monAlarmCheckName: string;
        fixedExtraItemMonNo: number;
        message: string;
        useAtr: boolean;
    }

    //class FixedExtraMonFun
    export class FixedExtraMonFun {
        monAlarmCheckID: string;
        fixedExtraItemMonNo: KnockoutObservable<number>;
        monAlarmCheckName: string;
        message: KnockoutObservable<string>;
        useAtr: KnockoutObservable<boolean>;
        constructor(data: IFixedExtraMonFun) {
            this.monAlarmCheckID = data.monAlarmCheckID;
            this.fixedExtraItemMonNo = ko.observable(data.fixedExtraItemMonNo);
            this.message = ko.observable(data.message);
            this.useAtr = ko.observable(data.useAtr);
            this.monAlarmCheckName = data.monAlarmCheckName;
        }
    }


}