module nts.uk.at.view.kaf022.g.viewmodel {
    import getText = nts.uk.resource.getText;
    import modal = nts.uk.ui.windows.sub.modal;

    export class ScreenModelG {
        itemListB4: KnockoutObservableArray<ItemModel> = ko.observableArray([
            {code: 1, name: getText('KAF022_420')},
            {code: 0, name: getText('KAF022_421')}
        ]);
        itemListB6: KnockoutObservableArray<ItemModel> = ko.observableArray([
            {code: 1, name: getText('KAF022_36')},
            {code: 0, name: getText('KAF022_37')}
        ]);
        itemListB8: KnockoutObservableArray<ItemModel> = ko.observableArray([
            {code: 1, name: getText('KAF022_100')},
            {code: 0, name: getText('KAF022_101')}
        ]);
        itemListB82: KnockoutObservableArray<ItemModel> = ko.observableArray([
            {code: 0, name: getText('KAF022_173')},
            {code: 1, name: getText('KAF022_175')}
        ]);
        itemListB24: KnockoutObservableArray<ItemModel> = ko.observableArray([
            {code: 0, name: getText('KAF022_173')},
            {code: 1, name: getText('KAF022_174')}
        ]);
        itemListB30: KnockoutObservableArray<ItemModel> = ko.observableArray([
            {code: 0, name: getText('KAF022_173')},
            {code: 1, name: getText('KAF022_175')},
            {code: 2, name: getText('KAF022_651')}
        ]);
        itemListB26: KnockoutObservableArray<ItemModel> = ko.observableArray([
            {code: 0, name: getText('KAF022_173')},
            {code: 1, name: getText('KAF022_174')},
            {code: 2, name: getText('KAF022_175')}
        ]);
        itemListB28: KnockoutObservableArray<ItemModel> = ko.observableArray([
            {code: 0, name: getText('KAF022_709')},
            {code: 1, name: getText('KAF022_710')},
        ]);

        itemListB242: KnockoutObservableArray<ItemModel> = ko.observableArray([
            {code: 1, name: getText('KAF022_75')},
            {code: 0, name: getText('KAF022_82')},
        ]);

        itemListB362: KnockoutObservableArray<ItemModel> = ko.observableArray([
            {code: 0, name: getText('KAF022_173')},
            {code: 1, name: getText('KAF022_712')},
        ]);

        itemListB372: KnockoutObservableArray<ItemModel> = ko.observableArray([
            {code: 0, name: getText('KAF022_173')},
            {code: 1, name: getText('KAF022_174')},
        ]);

        itemListB5: KnockoutObservableArray<ItemModel> = ko.observableArray([
            {code: 1, name: getText('KAF022_44')},
            {code: 0, name: getText('KAF022_396')},
        ]);

        itemListWorkTimeBeginDisplay: KnockoutObservableArray<ItemModel> = ko.observableArray([
            new ItemModel(0, getText("KAF022_37")),
            new ItemModel(1, getText("KAF022_301")),
            new ItemModel(2, getText("KAF022_302")),
            new ItemModel(3, getText("KAF022_303"))
        ]);

        overtimeLeaveAppCommonSetting: KnockoutObservable<OvertimeLeaveAppCommonSetting>;
        appDetailSetting: KnockoutObservable<AppDetailSetting>;
        overtimeAppReflect: KnockoutObservable<HolidayWorkAppReflect>;

        calcStampMiss: KnockoutObservable<number>;
        useDirectBounceFunction: KnockoutObservable<number>;

        constructor() {
            const self = this;
            self.overtimeLeaveAppCommonSetting = ko.observable(new OvertimeLeaveAppCommonSetting());
            self.appDetailSetting = ko.observable(new AppDetailSetting());
            self.overtimeAppReflect = ko.observable(new HolidayWorkAppReflect());
            self.calcStampMiss = ko.observable(0);
            self.useDirectBounceFunction = ko.observable(0);

            $("#fixed-table-g1").ntsFixedTable({});
            $("#fixed-table-g5").ntsFixedTable({});
            $("#fixed-table-g3").ntsFixedTable({});
        }

        initData(allData: any): void {
            const self = this;
            self.overtimeAppReflect(new HolidayWorkAppReflect(allData.holidayWorkApplicationReflect));
            if (allData.holidayWorkApplicationSetting) {
                self.calcStampMiss(allData.holidayWorkApplicationSetting.calcStampMiss);
                self.useDirectBounceFunction(allData.holidayWorkApplicationSetting.useDirectBounceFunction);
                self.overtimeLeaveAppCommonSetting(new OvertimeLeaveAppCommonSetting(allData.holidayWorkApplicationSetting.overtimeLeaveAppCommonSetting));
                self.appDetailSetting(new AppDetailSetting(allData.holidayWorkApplicationSetting.applicationDetailSetting));
            }
        }

        collectData(): any {
            const self = this;
            return {
                holidayWorkApplicationSetting: {
                    overtimeLeaveAppCommonSet: ko.toJS(self.overtimeLeaveAppCommonSetting),
                    applicationDetailSetting: ko.toJS(self.appDetailSetting),
                    calcStampMiss: self.calcStampMiss(),
                    useDirectBounceFunction: self.useDirectBounceFunction()
                },
                holidayWorkApplicationReflect: ko.toJS(self.overtimeAppReflect)
            };
        }

    }

    class ItemModel {
        code: number;
        name: string;

        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    class OvertimeLeaveAppCommonSetting {
        preExcessDisplaySetting: KnockoutObservable<number>;
        extratimeExcessAtr: KnockoutObservable<number>;
        extratimeDisplayAtr: KnockoutObservable<number>;
        performanceExcessAtr: KnockoutObservable<number>;
        checkOvertimeInstructionRegister: KnockoutObservable<number>;
        checkDeviationRegister: KnockoutObservable<number>;
        overrideSet: KnockoutObservable<number>;

        constructor(otLeaveAppCommonSet?: any) {
            this.preExcessDisplaySetting = ko.observable(otLeaveAppCommonSet ? otLeaveAppCommonSet.preExcessDisplaySetting : 0);
            this.extratimeExcessAtr = ko.observable(otLeaveAppCommonSet ? otLeaveAppCommonSet.extratimeExcessAtr : 0);
            this.extratimeDisplayAtr = ko.observable(otLeaveAppCommonSet ? otLeaveAppCommonSet.extratimeDisplayAtr : 1);
            this.performanceExcessAtr = ko.observable(otLeaveAppCommonSet ? otLeaveAppCommonSet.performanceExcessAtr : 0);
            this.checkOvertimeInstructionRegister = ko.observable(otLeaveAppCommonSet ? otLeaveAppCommonSet.checkOvertimeInstructionRegister : 0);
            this.checkDeviationRegister = ko.observable(otLeaveAppCommonSet ? otLeaveAppCommonSet.checkDeviationRegister : 0);
            this.overrideSet = ko.observable(otLeaveAppCommonSet ? otLeaveAppCommonSet.overrideSet : 1);
        }
    }

    class AppDetailSetting {
        requiredInstruction: KnockoutObservable<number>;
        preRequireSet: KnockoutObservable<number>;
        timeInputUse: KnockoutObservable<number>;
        timeCalUse: KnockoutObservable<number>;
        atworkTimeBeginDisp: KnockoutObservable<number>;
        dispSystemTimeWhenNoWorkTime: KnockoutObservable<number>;

        constructor(appDetailSetting?: any) {
            this.requiredInstruction = ko.observable(appDetailSetting ? appDetailSetting.requiredInstruction : 0);
            this.preRequireSet = ko.observable(appDetailSetting ? appDetailSetting.preRequireSet : 0);
            this.timeInputUse = ko.observable(appDetailSetting ? appDetailSetting.timeInputUse : 1);
            this.timeCalUse = ko.observable(appDetailSetting ? appDetailSetting.timeCalUse : 1);
            this.atworkTimeBeginDisp = ko.observable(appDetailSetting ? appDetailSetting.atworkTimeBeginDisp : 1);
            this.dispSystemTimeWhenNoWorkTime = ko.observable(appDetailSetting ? appDetailSetting.dispSystemTimeWhenNoWorkTime : 0);
        }
    }

    class HolidayWorkAppReflect {
        reflectActualHolidayWorkAtr: KnockoutObservable<number>;
        workReflect: KnockoutObservable<number>;
        reflectPaytime: KnockoutObservable<number>;
        reflectOptional: KnockoutObservable<number>;
        reflectDivergence: KnockoutObservable<number>;
        reflectBreakOuting: KnockoutObservable<number>;

        constructor(holidayWorkAppReflect?: any) {
            this.reflectActualHolidayWorkAtr = ko.observable(holidayWorkAppReflect ? holidayWorkAppReflect.reflectActualHolidayWorkAtr : 0);
            this.workReflect = ko.observable(holidayWorkAppReflect ? holidayWorkAppReflect.workReflect : 1);
            this.reflectPaytime = ko.observable(holidayWorkAppReflect ? holidayWorkAppReflect.reflectPaytime : 1);
            this.reflectOptional = ko.observable(holidayWorkAppReflect ? holidayWorkAppReflect.reflectOptional : 1);
            this.reflectDivergence = ko.observable(holidayWorkAppReflect ? holidayWorkAppReflect.reflectDivergence : 1);
            this.reflectBreakOuting = ko.observable(holidayWorkAppReflect ? holidayWorkAppReflect.reflectBreakOuting : 1);
        }
    }

}