module nts.uk.at.view.kaf022.b.viewmodel {
    import getText = nts.uk.resource.getText;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModelB {
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
        overtimeAppReflect: KnockoutObservable<OvertimeAppReflect>;

        overtimeWorkFrames: KnockoutObservableArray<any> = ko.observableArray([]);

        constructor() {
            const self = this;
            self.overtimeLeaveAppCommonSetting = ko.observable(new OvertimeLeaveAppCommonSetting());
            self.appDetailSetting = ko.observable(new AppDetailSetting());
            self.overtimeAppReflect = ko.observable(new OvertimeAppReflect());

            $("#fixed-table-b1").ntsFixedTable({});
            $("#fixed-table-b5").ntsFixedTable({});
            $("#fixed-table-b3").ntsFixedTable({});
        }

        initData(allData: any): void {
            const self = this;
            self.overtimeAppReflect(new OvertimeAppReflect(allData.overtimeAppReflect));
            if (allData.overtimeAppSetting) {
                self.overtimeLeaveAppCommonSetting(new OvertimeLeaveAppCommonSetting(allData.overtimeAppSetting.overtimeLeaveAppCommonSetting));
                self.appDetailSetting(new AppDetailSetting(allData.overtimeAppSetting.applicationDetailSetting));
            }
            self.overtimeWorkFrames(allData.overtimeWorkFrames || []);
        }

        collectData(): any {
            const self = this;
            return {
                overtimeApplicationSetting: {
                    overtimeLeaveAppCommonSetting: ko.toJS(self.overtimeLeaveAppCommonSetting),
                    applicationDetailSetting: ko.toJS(self.appDetailSetting),
                    overTimeQuotaSettings: []
                },
                overtimeApplicationReflect: ko.toJS(self.overtimeAppReflect)
            };
        }

        openODialog(): void {
            const self = this;
            setShared("overtimeWorkFrames", self.overtimeWorkFrames());
            modal('/view/kaf/022/o/index.xhtml');
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

    class OvertimeAppReflect {
        reflectActualWorkAtr: KnockoutObservable<number>;
        reflectWorkInfoAtr: KnockoutObservable<number>;
        reflectActualOvertimeHourAtr: KnockoutObservable<number>;
        reflectBeforeBreak: KnockoutObservable<number>;
        workReflect: KnockoutObservable<number>;
        reflectPaytime: KnockoutObservable<number>;
        reflectOptional: KnockoutObservable<number>;
        reflectDivergence: KnockoutObservable<number>;
        reflectBreakOuting: KnockoutObservable<number>;

        constructor(overtimeAppReflect?: any) {
            this.reflectActualWorkAtr = ko.observable(overtimeAppReflect ? overtimeAppReflect.reflectActualWorkAtr : 1);
            this.reflectWorkInfoAtr = ko.observable(overtimeAppReflect ? overtimeAppReflect.reflectWorkInfoAtr : 1);
            this.reflectActualOvertimeHourAtr = ko.observable(overtimeAppReflect ? overtimeAppReflect.reflectActualOvertimeHourAtr : 1);
            this.reflectBeforeBreak = ko.observable(overtimeAppReflect ? overtimeAppReflect.reflectBeforeBreak : 1);
            this.workReflect = ko.observable(overtimeAppReflect ? overtimeAppReflect.workReflect : 1);
            this.reflectPaytime = ko.observable(overtimeAppReflect ? overtimeAppReflect.reflectPaytime : 1);
            this.reflectOptional = ko.observable(overtimeAppReflect ? overtimeAppReflect.reflectOptional : 1);
            this.reflectDivergence = ko.observable(overtimeAppReflect ? overtimeAppReflect.reflectDivergence : 1);
            this.reflectBreakOuting = ko.observable(overtimeAppReflect ? overtimeAppReflect.reflectBreakOuting : 1);
        }
    }

}