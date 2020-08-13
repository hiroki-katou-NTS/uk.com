module nts.uk.at.view.kmf022.a.viewmodel {
    import service = nts.uk.at.view.kmf022.company.service;
    import getText = nts.uk.resource.getText;
    let __viewContext: any = window["__viewContext"] || {};

    export class ScreenModelA {
        companyId: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean>;
        isEditable: KnockoutObservable<boolean>;

        /*A4 - Deadline Settings Table*/
        dataDeadlineSettings: KnockoutObservableArray<DeadlineSetting>;
        itemListA4_7: KnockoutObservableArray<ItemModel>;
        itemListA4_8: KnockoutObservableArray<ItemModel>;

        /*A7 - Reception Restriction Settings Table*/
        dataReceptionRestrictionSettings: KnockoutObservableArray<ReceptionRestrictionSetting>;
        itemListA7_20: KnockoutObservableArray<ItemModel>;

        /* A11 - Application Limit Settings Table*/
        itemListA11_8: KnockoutObservableArray<ItemModel>;
        appLimitSetting: KnockoutObservable<AppLimitSetting>; // A20 also

        /* A17 Approval settings Table*/
        itemListA17_4: KnockoutObservableArray<ItemModel>;
        itemListA17_7: KnockoutObservableArray<ItemModel>;
        itemListA14_3: KnockoutObservableArray<ItemModel>;
        approvalSetting: KnockoutObservable<ItemA17>;

        /* A19 HolidatOvertimeWorkApplicationReflect Table*/
        itemListA11_12: KnockoutObservableArray<ItemModel>;
        nightOvertimeReflect: KnockoutObservable<number>;
        /*A12 Table*/
        prePostDisplayAtr: KnockoutObservable<number>;
        itemListA12_5: KnockoutObservableArray<ItemModel>;

        /* A22 Application Type Setting Table*/
        appTypeSettings: KnockoutObservableArray<AppTypeSetting>;
        itemListA22_2: KnockoutObservableArray<ItemModel>;

        /*A8*/
        listDataA8: KnockoutObservableArray<DisplayReasonSetting>;

        /*A21*/
        itemListA21_2: KnockoutObservableArray<ItemModel>;
        appReflectCondition: KnockoutObservable<AppReflectExeCondition>;

        constructor() {
            const self = this;
            self.companyId = ko.observable("");
            self.isEnable = ko.observable(true);
            self.isEditable = ko.observable(false);

            // A4
            self.dataDeadlineSettings = ko.observableArray([]);
            self.itemListA4_7 = ko.observableArray([
                new ItemModel(0, getText('KAF022_321')),
                new ItemModel(1, getText('KAF022_322')),
            ]);
            self.itemListA4_8 = ko.observableArray([
                new ItemModel(0, getText('KAF022_323')),
                new ItemModel(1, getText('KAF022_324')),
                new ItemModel(2, getText('KAF022_325')),
                new ItemModel(3, getText('KAF022_326')),
                new ItemModel(4, getText('KAF022_327')),
                new ItemModel(5, getText('KAF022_328')),
                new ItemModel(6, getText('KAF022_329')),
                new ItemModel(7, getText('KAF022_330')),
                new ItemModel(8, getText('KAF022_331')),
                new ItemModel(9, getText('KAF022_332')),
                new ItemModel(10, getText('KAF022_333')),
                new ItemModel(11, getText('KAF022_334')),
                new ItemModel(12, getText('KAF022_335')),
                new ItemModel(13, getText('KAF022_336')),
                new ItemModel(14, getText('KAF022_337')),
                new ItemModel(15, getText('KAF022_338')),
                new ItemModel(16, getText('KAF022_339')),
                new ItemModel(17, getText('KAF022_340')),
                new ItemModel(18, getText('KAF022_341')),
                new ItemModel(19, getText('KAF022_342')),
                new ItemModel(20, getText('KAF022_343')),
                new ItemModel(21, getText('KAF022_344')),
                new ItemModel(22, getText('KAF022_345')),
                new ItemModel(23, getText('KAF022_346')),
                new ItemModel(24, getText('KAF022_347')),
                new ItemModel(25, getText('KAF022_348')),
                new ItemModel(26, getText('KAF022_349')),
                new ItemModel(27, getText('KAF022_350')),
                new ItemModel(28, getText('KAF022_351')),
                new ItemModel(29, getText('KAF022_352')),
                new ItemModel(30, getText('KAF022_353')),
                new ItemModel(31, getText('KAF022_354'))
            ]);

            // A7
            self.dataReceptionRestrictionSettings = ko.observableArray([]);
            self.itemListA7_20 = ko.observableArray([
                new ItemModel(0, getText('KAF022_323')),
                new ItemModel(1, getText('KAF022_357')),
                new ItemModel(2, getText('KAF022_358')),
                new ItemModel(3, getText('KAF022_359')),
                new ItemModel(4, getText('KAF022_360')),
                new ItemModel(5, getText('KAF022_361')),
                new ItemModel(6, getText('KAF022_362')),
                new ItemModel(7, getText('KAF022_363')),
            ]);

            // A11
            self.itemListA11_8 = ko.observableArray([
                new ItemModel(1, getText('KAF022_437')),
                new ItemModel(0, getText('KAF022_438'))
            ]);
            self.appLimitSetting = ko.observable(new AppLimitSetting(null));

            // A17
            self.itemListA17_4 = ko.observableArray([
                new ItemModel(1, getText('KAF022_272')),
                new ItemModel(0, getText('KAF022_273')),
            ]);
            self.itemListA17_7 = ko.observableArray([
                new ItemModel(0, getText('KAF022_403')),
                new ItemModel(1, getText('KAF022_404')),
            ]);
            self.itemListA14_3 = ko.observableArray([
                new ItemModel(1, getText('KAF022_75')),
                new ItemModel(0, getText('KAF022_82'))
            ]);
            self.approvalSetting = ko.observable(new ItemA17(0, 0, 0));

            // A19
            self.nightOvertimeReflect = ko.observable(0);
            self.itemListA11_12 = ko.observableArray([
                new ItemModel(1, getText('KAF022_100')),
                new ItemModel(0, getText('KAF022_101'))
            ]);

            // A12
            self.prePostDisplayAtr = ko.observable(0);
            self.itemListA12_5 = ko.observableArray([
                new ItemModel(1, getText('KAF022_36')),
                new ItemModel(0, getText('KAF022_37'))
            ]);

            // A22
            self.appTypeSettings = ko.observableArray([]);
            self.itemListA22_2 = ko.observableArray([
                new ItemModel(0, getText('KAF022_775')),
                new ItemModel(1, getText('KAF022_776')),
                new ItemModel(2, getText('KAF022_777'))
            ]);

            // A8
            self.listDataA8 = ko.observableArray([]);


            // A21
            self.itemListA21_2 = ko.observableArray([
                new ItemModel(1, getText('KAF022_44')),
                new ItemModel(0, getText('KAF022_396'))
            ]);
            self.appReflectCondition = ko.observable(new AppReflectExeCondition(null));

            $("#fixed-table-a4").ntsFixedTable({});
            $("#fixed-table-a7").ntsFixedTable({});
            $("#fixed-table-a11").ntsFixedTable({});
            $("#fixed-table-a17").ntsFixedTable({});
            $("#fixed-table-a19").ntsFixedTable({});
            $("#fixed-table-a12").ntsFixedTable({});
            $("#fixed-table-a22").ntsFixedTable({});
            $("#fixed-table-a20").ntsFixedTable({});
            $("#fixed-table-a8").ntsFixedTable({});
            $("#fixed-table-a21").ntsFixedTable({});
        }

        initData(allData: any): void {
            const self = this;
            self.initDataA4(allData);
            self.initDataA7(allData);
            self.initDataA11(allData);
            self.initDataA17(allData);
            self.nightOvertimeReflect(allData.nightOvertimeReflectAtr);
            self.prePostDisplayAtr(allData.applicationSetting.appDisplaySetting.prePostDisplayAtr);
            self.initDataA22(allData);
            self.initDataA8(allData);
            self.initDataA21(allData);
        }

        initDataA4(allData: any): void {
            let self = this;
            // init data A4
            self.dataDeadlineSettings([]);
            for (let i = 1; i <= 5; i++) {
                const data: Array<Closure> = allData.allClosure || [];
                const appDeadlineSetLst: Array<any> = allData.applicationSetting.appDeadlineSetLst || [];
                const closure = _.find(data, ['id', i]);
                const deadline: any = _.find(appDeadlineSetLst, ['closureId', i]);
                self.dataDeadlineSettings.push(new DeadlineSetting(
                    i,
                    i + "." + (closure ? closure.name : ""),
                    deadline ? deadline.useAtr : 0,
                    deadline ? deadline.deadlineCriteria : 0,
                    deadline ? deadline.deadline : 0
                ));
            }
        }

        initDataA7(allData: any): void {
            let self = this;
            let listAppType = __viewContext.enums.ApplicationType;
            self.dataReceptionRestrictionSettings([]);
            let data: Array<any> = allData.applicationSetting.receptionRestrictionSetting;
            if (data) {
                _.forEach(listAppType, (appType: any) => {
                    let obj: any = _.find(data, ['appType', appType.value]);
                    if (obj) {
                        self.dataReceptionRestrictionSettings.push(
                            new ReceptionRestrictionSetting(
                                self.companyId(),
                                appType.name,
                                appType.value,
                                appType.value != 0 ? obj.beforehandRestriction.toUse : obj.otAppBeforeAccepRestric.toUse,
                                obj.otAppBeforeAccepRestric.methodCheck,
                                appType.value != 0 ? obj.beforehandRestriction.dateBeforehandRestrictions : obj.otAppBeforeAccepRestric.dateBeforehandRestrictions,
                                obj.otAppBeforeAccepRestric.opEarlyOvertime,
                                obj.otAppBeforeAccepRestric.opNormalOvertime,
                                obj.otAppBeforeAccepRestric.opEarlyNormalOvertime,
                                obj.afterhandRestriction.allowFutureDay
                            )
                        );
                    } else {
                        self.dataReceptionRestrictionSettings.push(new ReceptionRestrictionSetting(self.companyId(), appType.name, appType.value, 0, 1, 0, 0, 0, 0, 0));
                    }
                });
            }
        }

        initDataA11(allData: any): void {
            const self = this;
            self.appLimitSetting(new AppLimitSetting(allData.appLimitSetting));
        }

        initDataA17(allData: any): void {
            let self = this;
            self.approvalSetting(new ItemA17(allData.applicationSetting.recordDate, allData.approvalSettingDto.prinFlg, allData.jobAssign.isConcurrently));
        }

        initDataA22(allData: any): void {
            const self = this;
            let listAppType = __viewContext.enums.ApplicationType;
            self.appTypeSettings([]);
            let data: Array<any> = allData.applicationSetting.appTypeSetting;
            if (data) {
                _.forEach(listAppType, (appType: any) => {
                    let obj: any = _.find(data, ['appType', appType.value]);
                    if (obj) {
                        self.appTypeSettings.push(
                            new AppTypeSetting(
                                appType.value,
                                appType.name,
                                obj.canClassificationChange,
                                obj.displayInitialSegment
                            )
                        );
                    } else {
                        self.appTypeSettings.push(new AppTypeSetting(appType.value, appType.name,  false, 0));
                    }
                });
            }
        }

        initDataA8(allData: any): void {
            let self = this;
            let listAppType = __viewContext.enums.ApplicationType;
            let listHdType = __viewContext.enums.HolidayAppType;
            self.listDataA8([]);
            let data: Array<any> = allData.displayReason;
            if (data) {
                _.forEach(listAppType, (appType: any) => {
                    let obj1: any = _.find(data, ['appType', appType.value]);
                    if (obj1) {
                        self.listDataA8.push(new DisplayReasonSetting(appType.value, appType.name, obj1.displayFixedReason, obj1.displayAppReason, 0));
                    } else {
                        self.listDataA8.push(new DisplayReasonSetting(appType.value, appType.name,0, 0, 0));
                    }
                    if (appType.value == 1) {
                        _.forEach(listHdType, (hdType: any)=>{
                            let obj2: any = _.find(data, ['holidayAppType', hdType.value]);
                            if (obj2) {
                                self.listDataA8.push(new DisplayReasonSetting(hdType.value, hdType.name, obj2.displayFixedReason, obj2.displayAppReason, 1));
                            } else {
                                self.listDataA8.push(new DisplayReasonSetting(hdType.value, hdType.name,0, 0, 1));
                            }
                        });
                    }
                });
            }
        }

        initDataA21(allData: any): void {
            const self = this;
            self.appReflectCondition(new AppReflectExeCondition(allData.appReflectCondition));
        }

        openScreenS(): void {
            alert("not implemented yet!");
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

    class DeadlineSetting {
        index: number;
        closureName: string;
        useAtr: KnockoutObservable<boolean>;
        deadlineCriteria: KnockoutObservable<number>;
        deadline: KnockoutObservable<number>;
        constructor(index: number, closureName: string, useAtr: number, deadlineCriteria: number, deadline: number) {
            this.index = index;
            this.closureName = closureName;
            this.useAtr = ko.observable(useAtr == 1);
            this.deadlineCriteria = ko.observable(deadlineCriteria);
            this.deadline = ko.observable(deadline);
        }
    }

    interface Closure {
        id: string;
        name: string;
    }

    class ReceptionRestrictionSetting {
        appType: number;
        appTypeName: string;
        retrictPreMethodFlg: KnockoutObservable<number>;
        retrictPreUseFlg: KnockoutObservable<boolean>;
        retrictPreDay: KnockoutObservable<number>;
        retrictPreTimeDay: KnockoutObservable<number>;
        retrictPostAllowFutureFlg: KnockoutObservable<boolean>;

        preOtTime: KnockoutObservable<number>;
        normalOtTime: KnockoutObservable<number>;
        requiredA7_23: KnockoutObservable<boolean>;
        constructor(companyId: string, appTypeName: string, appType: number, retrictPreUseFlg: number, retrictPreMethodFlg: number,
                    retrictPreDay: number, preOtTime: number, normalOtTime: number, retrictPreTimeDay: number, retrictPostAllowFutureFlg: number) {
            this.appTypeName = appTypeName;
            this.appType = appType;
            this.retrictPreMethodFlg = ko.observable(retrictPreMethodFlg);
            this.retrictPreUseFlg = ko.observable(retrictPreUseFlg == 1);
            this.retrictPreDay = ko.observable(retrictPreDay);
            this.retrictPreTimeDay = ko.observable(retrictPreTimeDay);
            this.retrictPostAllowFutureFlg = ko.observable(retrictPostAllowFutureFlg == 1);
            this.preOtTime = ko.observable(preOtTime);
            this.normalOtTime = ko.observable(normalOtTime);
            this.requiredA7_23 = ko.observable(retrictPreMethodFlg == 1);
            this.retrictPreMethodFlg.subscribe((value) => {
                if (value == 1) {
                    nts.uk.ui.errors.clearAll();
                    this.requiredA7_23(false);
                } else {
                    nts.uk.ui.errors.clearAll();
                    this.requiredA7_23(true);
                    $('#a7_23').trigger("validate");
                    $('#a7_23_2').trigger("validate");
                    $('#a7_23_3').trigger("validate");
                }
            });
            this.preOtTime.subscribe((value) => {
                if (value) {
                    $('#a7_23').ntsError('clear');
                    this.requiredA7_23.valueHasMutated();
                }
            });
            this.normalOtTime.subscribe((value) => {
                if (value) {
                    $('#a7_23_2').ntsError('clear');
                    this.requiredA7_23.valueHasMutated();
                }
            });
            this.retrictPreTimeDay.subscribe((value) => {
                if (value) {
                    $('#a7_23_3').ntsError('clear');
                    this.requiredA7_23.valueHasMutated();
                }
            });
        }
    }

    class AppLimitSetting {
        canAppAchievementMonthConfirm: KnockoutObservable<number>;
        canAppAchievementLock: KnockoutObservable<number>;
        canAppFinishWork: KnockoutObservable<number>;
        canAppAchievementConfirm: KnockoutObservable<number>;
        standardReasonRequired: KnockoutObservable<number>;
        requiredAppReason: KnockoutObservable<number>;

        constructor(appLimitSetting: any) {
            this.canAppAchievementMonthConfirm = ko.observable(appLimitSetting && appLimitSetting.canAppAchievementMonthConfirm ? 1 : 0);
            this.standardReasonRequired = ko.observable(appLimitSetting && appLimitSetting.standardReasonRequired ? 1 : 0);
            this.canAppAchievementLock = ko.observable(appLimitSetting && appLimitSetting.canAppAchievementLock ? 1 : 0);
            this.canAppFinishWork = ko.observable(appLimitSetting && appLimitSetting.canAppFinishWork ? 1 : 0);
            this.requiredAppReason = ko.observable(appLimitSetting && appLimitSetting.requiredAppReason ? 1 : 0);
            this.canAppAchievementConfirm = ko.observable(appLimitSetting && appLimitSetting.canAppAchievementConfirm ? 1 : 0);
        }
    }

    class ItemA17 {
        baseDateAtr: KnockoutObservable<number>;
        approvalByPersonAtr: KnockoutObservable<number>;
        includeConcurrentPersonel: KnockoutObservable<number>;
        constructor(baseDateAtr: number, approvalByPersonAtr: number, includeConcurrentPersonel: number) {
            this.baseDateAtr = ko.observable(baseDateAtr);
            this.approvalByPersonAtr = ko.observable(approvalByPersonAtr);
            this.includeConcurrentPersonel = ko.observable(includeConcurrentPersonel);
        }
    }

    class AppTypeSetting {
        appType: number;
        appTypeName: string;
        canClassificationChange: KnockoutObservable<boolean>;
        displayInitialSegment: KnockoutObservable<number>;
        // sendMailWhenApproval: true
        // sendMailWhenRegister: true
        constructor(appType: number, appName: string, canChangeCls: boolean, displayInitSegment: number) {
            this.appType = appType;
            this.appTypeName = appName;
            this.canClassificationChange = ko.observable(canChangeCls);
            this.displayInitialSegment = ko.observable(displayInitSegment);
        }
    }

    class DisplayReasonSetting {
        appType: number;
        displayFixedReason: KnockoutObservable<boolean>;
        displayAppReason: KnockoutObservable<boolean>;
        appTypeName: KnockoutObservable<string>;
        // 1: is domain DisplayReason
        flg: KnockoutObservable<number>;
        // disable or enable
        // disableA8: KnockoutObservable<boolean> = ko.observable(true);
        constructor(appType: number, appTypeName: string, displayFixedReason: number, displayAppReason: number, flg: number) {
            this.appType = appType;
            this.displayFixedReason = ko.observable(displayFixedReason == 1 ? true : false);
            this.displayAppReason = ko.observable(displayAppReason == 1 ? true : false);
            this.appTypeName = ko.observable(appTypeName);
            this.flg = ko.observable(flg);
        }
    }

    class AppReflectExeCondition {
        applyBeforeSchedule: KnockoutObservable<number>;
        evenIfScheduleConfirmed: KnockoutObservable<number>;
        evenIfRecordConfirmed: KnockoutObservable<number>;

        constructor (appReflectExeCondition: any) {
            this.applyBeforeSchedule = ko.observable(appReflectExeCondition ? appReflectExeCondition.applyBeforeSchedule : 0);
            this.evenIfScheduleConfirmed = ko.observable(appReflectExeCondition ? appReflectExeCondition.evenIfScheduleConfirmed : 0);
            this.evenIfRecordConfirmed = ko.observable(appReflectExeCondition ? appReflectExeCondition.evenIfRecordConfirmed : 0);
        }
    }

}