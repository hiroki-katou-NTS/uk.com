module nts.uk.pr.view.kmf001.f {
    export module viewmodel {

        import EnumertionModel = service.model.EnumerationModel;
        import Enum = service.model.Enum;
        export class ScreenModel {

            applyOptions: KnockoutObservableArray<EnumertionModel>;
            compenManage: KnockoutObservable<string>;
            compenPreApply: KnockoutObservable<number>;
            compenTimeManage: KnockoutObservable<number>;

            expirationDateList: KnockoutObservableArray<ItemModel>;
            expirationDateCode: KnockoutObservable<string>;

            timeUnitList: KnockoutObservableArray<ItemModel>;
            timeUnitCode: KnockoutObservable<string>;

            checkWorkTime: KnockoutObservable<boolean>;
            checkOverTime: KnockoutObservable<boolean>;

            copenWorkTimeOptions: KnockoutObservableArray<BoxModel>;
            selectedOfWorkTime: KnockoutObservable<number>;
            selectedOfOverTime: KnockoutObservable<number>;

            itemName: KnockoutObservable<string>;
            currentCode: KnockoutObservable<number>
            selectedCode: KnockoutObservable<string>;

            workOneDay: KnockoutObservable<string>;
            workHalfDay: KnockoutObservable<string>;
            workAll: KnockoutObservable<string>;

            overOneDay: KnockoutObservable<string>;
            overHalfDay: KnockoutObservable<string>;
            overAll: KnockoutObservable<string>;

            inputOption: KnockoutObservable<any>;
            isManageCompen: KnockoutObservable<boolean>;
            enableWorkArea: KnockoutObservable<boolean>;
            enableOverArea: KnockoutObservable<boolean>;

            enableWorkAll: KnockoutObservable<boolean>;
            enableOverAll: KnockoutObservable<boolean>;
            enableDesignWork: KnockoutObservable<boolean>;
            enableDesignOver: KnockoutObservable<boolean>;

            //enums
            manageDistinctEnums: KnockoutObservableArray<Enum>;
            applyPermissionEnums: KnockoutObservableArray<Enum>;
            expirationTimeEnums: KnockoutObservableArray<Enum>;
            timeVacationDigestiveUnitEnums: KnockoutObservableArray<Enum>;
            compensatoryOccurrenceDivisionEnums: KnockoutObservableArray<Enum>;
            transferSettingDivisionEnums: KnockoutObservableArray<Enum>;

            constructor() {
                let self = this;
                self.compenManage = ko.observable('');
                self.compenPreApply = ko.observable(0);
                self.compenTimeManage = ko.observable(0);
                self.expirationDateCode = ko.observable('');
                self.timeUnitCode = ko.observable('');
                self.checkWorkTime = ko.observable(true);
                self.checkOverTime = ko.observable(false);
                self.copenWorkTimeOptions = ko.observableArray([
                    new BoxModel(1, '指定した時間を代休とする'),
                    new BoxModel(2, '一定時間を超えたら代休とする')]);
                self.selectedOfWorkTime = ko.observable(1);
                self.selectedOfOverTime = ko.observable(1);

                self.itemName = ko.observable('');
                self.currentCode = ko.observable(3);
                self.selectedCode = ko.observable('1');

                self.workOneDay = ko.observable('0:00');
                self.workHalfDay = ko.observable('0:00');
                self.workAll = ko.observable('0:00');

                self.overOneDay = ko.observable('0:00');
                self.overHalfDay = ko.observable('0:00');
                self.overAll = ko.observable('0:00');

                self.inputOption = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    filldirection: "right",
                    fillcharacter: "0",
                    width: "50"
                }));

                self.manageDistinctEnums = ko.observableArray([]);
                self.applyPermissionEnums = ko.observableArray([]);
                self.expirationTimeEnums = ko.observableArray([]);
                self.timeVacationDigestiveUnitEnums = ko.observableArray([]);
                self.compensatoryOccurrenceDivisionEnums = ko.observableArray([]);
                self.transferSettingDivisionEnums = ko.observableArray([]);

                self.isManageCompen = ko.computed(function() {
                    return self.compenManage() == "YES";
                }, self);

                self.enableWorkArea = ko.computed(function() {
                    return self.checkWorkTime() && self.isManageCompen();
                }, self);

                self.enableOverArea = ko.computed(function() {
                    return self.checkOverTime() && self.isManageCompen();
                }, self);

                self.enableWorkAll = ko.computed(function() {
                    return self.enableWorkArea() && self.selectedOfWorkTime() == 2;
                }, self);

                self.enableOverAll = ko.computed(function() {
                    return self.enableOverArea() && self.selectedOfOverTime() == 2;
                }, self);

                self.enableDesignWork = ko.computed(function() {
                    return self.enableWorkArea() && self.selectedOfWorkTime() == 1;
                }, self);

                self.enableDesignOver = ko.computed(function() {
                    return self.enableOverArea() && self.selectedOfOverTime() == 1;
                }, self);

                self.checkWorkTime.subscribe(function(data: boolean) {
                    if (data == true) {
                        self.checkOverTime(false);
                    }
                });
                self.checkOverTime.subscribe(function(data: boolean) {
                    if (data == true) {
                        self.checkWorkTime(false);
                    }
                });
            }

            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                $.when(self.loadManageDistinctEnums(), self.loadApplyPermissionEnums(), self.loadExpirationTimeEnums(), self.loadTimeVacationDigestiveUnitEnums(),
                    self.loadCompensatoryOccurrenceDivisionEnums(), self.loadTransferSettingDivisionEnums()).done(function() {
                        self.loadSetting().done(function() {
                            dfd.resolve();
                        });
                    });
                return dfd.promise();
            }

            private loadManageDistinctEnums(): JQueryPromise<Array<Enum>> {
                let self = this;
                let dfd = $.Deferred();
                service.getEnumManageDistinct().done(function(res: Array<Enum>) {
                    self.manageDistinctEnums(res);
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                });
                return dfd.promise();
            }

            private loadApplyPermissionEnums(): JQueryPromise<Array<Enum>> {
                let self = this;
                let dfd = $.Deferred();
                service.getEnumApplyPermission().done(function(res: Array<Enum>) {
                    self.applyPermissionEnums(res);
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                });
                return dfd.promise();
            }

            private loadExpirationTimeEnums(): JQueryPromise<Array<Enum>> {
                let self = this;
                let dfd = $.Deferred();
                service.getEnumExpirationTime().done(function(res: Array<Enum>) {
                    self.expirationTimeEnums(res);
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                });
                return dfd.promise();
            }

            private loadTimeVacationDigestiveUnitEnums(): JQueryPromise<Array<Enum>> {
                let self = this;
                let dfd = $.Deferred();
                service.getEnumTimeVacationDigestiveUnit().done(function(res: Array<Enum>) {
                    self.timeVacationDigestiveUnitEnums(res);
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                });
                return dfd.promise();
            }

            private loadCompensatoryOccurrenceDivisionEnums(): JQueryPromise<Array<Enum>> {
                let self = this;
                let dfd = $.Deferred();
                service.getEnumCompensatoryOccurrenceDivision().done(function(res: Array<Enum>) {
                    self.compensatoryOccurrenceDivisionEnums(res);
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                });
                return dfd.promise();
            }

            private loadTransferSettingDivisionEnums(): JQueryPromise<Array<Enum>> {
                let self = this;
                let dfd = $.Deferred();
                service.getEnumTransferSettingDivision().done(function(res: Array<Enum>) {
                    self.transferSettingDivisionEnums(res);
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                });
                return dfd.promise();
            }

            private loadSetting(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                service.find().done(function(data: any) {
                    if (data) {
                        self.loadToScreen(data);
                    }
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                });
                return dfd.promise();
            }

            private loadToScreen(data: any) {
                let self = this;
                self.compenManage(data.isManaged);

                self.expirationDateCode(data.normalVacationSetting.expirationTime);

                self.compenPreApply(data.normalVacationSetting.preemptionPermit);
                self.compenTimeManage(data.normalVacationSetting.isManageByTime);
                self.timeUnitCode(data.normalVacationSetting.digestiveUnit);

                //TODO if check f3
                if (data.occurrenceVacationSetting.occurrenceDivision == self.compensatoryOccurrenceDivisionEnums()[1].fieldName) {
                    //set data work
                    self.selectedOfWorkTime(data.occurrenceVacationSetting.transferDivision);
                    self.workOneDay(nts.uk.time.parseTime(data.occurrenceVacationSetting.transferSetting.oneDayTime).format());
                    self.workHalfDay(nts.uk.time.parseTime(data.occurrenceVacationSetting.transferSetting.halfDayTime).format());
                    self.workAll(nts.uk.time.parseTime(data.occurrenceVacationSetting.transferSetting.certainTime).format());
                }
                else {//TODO if check f13
                    self.selectedOfOverTime(data.occurrenceVacationSetting.transferDivision);
                    self.overOneDay(nts.uk.time.parseTime(data.occurrenceVacationSetting.transferSetting.oneDayTime).format());
                    self.overHalfDay(nts.uk.time.parseTime(data.occurrenceVacationSetting.transferSetting.halfDayTime).format());
                    self.overAll(nts.uk.time.parseTime(data.occurrenceVacationSetting.transferSetting.certainTime).format());
                }
            }

            private saveData() {
                let self = this;
                service.update(self.collectData()).done(function() {
                    //TODO
                    alert();
                });
            }
            private collectData() {
                //TODO wait domain
                return {
                    companyId: "cpnID",
                    isManaged: 0,
                    normalVacationSetting: {
                        expirationTime: 0,
                        preemptionPermit: 0,
                        isManageByTime: 0,
                        digestiveUnit: 0
                    },
                    occurrenceVacationSetting: {
                        transferSetting: {
                            certainTime: 1200,
                            useDivision: true,
                            oneDayTime: 1200,
                            halfDayTime: 1300,
                            transferDivision: 0
                        },
                        occurrenceDivision: 1
                    }
                };
            }

            private gotoVacationSetting() {
                alert();
            }

            private gotoParent() {
                nts.uk.request.jump("/view/kmf/001/a/index.xhtml");
            }
        }
        class ItemModel {
            code: string;
            name: string;
            label: string;

            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }
        class BoxModel {
            id: number;
            name: string;
            constructor(id: number, name: string) {
                this.id = id;
                this.name = name;
            }
        }
    }
}