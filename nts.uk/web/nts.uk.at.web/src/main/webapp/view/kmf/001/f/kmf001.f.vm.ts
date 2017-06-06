module nts.uk.pr.view.kmf001.f {
    export module viewmodel {

        import Enum = service.model.Enum;
        import RadioEnum = service.model.RadioEnum;

        export class ScreenModel {

            compenManage: KnockoutObservable<number>;
            compenPreApply: KnockoutObservable<number>;
            compenTimeManage: KnockoutObservable<number>;

            expirationDateCode: KnockoutObservable<number>;
            timeUnitCode: KnockoutObservable<number>;

            checkWorkTime: KnockoutObservable<boolean>;
            checkOverTime: KnockoutObservable<boolean>;

            selectedOfWorkTime: KnockoutObservable<number>;
            selectedOfOverTime: KnockoutObservable<number>;

            workOneDay: KnockoutObservable<string>;
            workHalfDay: KnockoutObservable<string>;
            workAll: KnockoutObservable<string>;

            overOneDay: KnockoutObservable<string>;
            overHalfDay: KnockoutObservable<string>;
            overAll: KnockoutObservable<string>;

            inputOption: KnockoutObservable<any>;
            isManageCompen: KnockoutObservable<boolean>;
            isManageTime: KnockoutObservable<boolean>;
            enableWorkArea: KnockoutObservable<boolean>;
            enableOverArea: KnockoutObservable<boolean>;

            enableWorkAll: KnockoutObservable<boolean>;
            enableOverAll: KnockoutObservable<boolean>;
            enableDesignWork: KnockoutObservable<boolean>;
            enableDesignOver: KnockoutObservable<boolean>;

            backUpData: KnockoutObservable<any>;
            //enums
            manageDistinctEnums: KnockoutObservableArray<Enum>;
            applyPermissionEnums: KnockoutObservableArray<Enum>;
            expirationTimeEnums: KnockoutObservableArray<Enum>;
            timeVacationDigestiveUnitEnums: KnockoutObservableArray<Enum>;
            compensatoryOccurrenceDivisionEnums: KnockoutObservableArray<Enum>;
            transferSettingDivisionEnums: KnockoutObservableArray<RadioEnum>;

            //Employment
            employmentBackUpData: KnockoutObservable<any>;
            employmentList: KnockoutObservableArray<ItemModel>;
            columnsSetting: KnockoutObservable<nts.uk.ui.NtsGridListColumn>;
            emSelectedCode: KnockoutObservable<string>;
            
            emCompenManage: KnockoutObservable<number>;
            emExpirationTime: KnockoutObservable<number>;
            emPreApply: KnockoutObservable<number>;
            emTimeManage: KnockoutObservable<number>;
            emTimeUnit: KnockoutObservable<number>;

            constructor() {
                let self = this;
                self.compenManage = ko.observable(0);
                self.compenPreApply = ko.observable(0);
                self.compenTimeManage = ko.observable(0);
                self.expirationDateCode = ko.observable(0);
                self.timeUnitCode = ko.observable(0);
                self.checkWorkTime = ko.observable(true);
                self.checkOverTime = ko.observable(true);
                self.selectedOfWorkTime = ko.observable(1);
                self.selectedOfOverTime = ko.observable(1);

                self.workOneDay = ko.observable('00:00');
                self.workHalfDay = ko.observable('00:00');
                self.workAll = ko.observable('00:00');

                self.overOneDay = ko.observable('00:00');
                self.overHalfDay = ko.observable('00:00');
                self.overAll = ko.observable('00:00');

                self.inputOption = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    filldirection: "right",
                    fillcharacter: "0",
                    width: "50"
                }));
                
                self.backUpData = ko.observable();
                self.manageDistinctEnums = ko.observableArray([]);
                self.applyPermissionEnums = ko.observableArray([]);
                self.expirationTimeEnums = ko.observableArray([]);
                self.timeVacationDigestiveUnitEnums = ko.observableArray([]);
                self.compensatoryOccurrenceDivisionEnums = ko.observableArray([]);
                self.transferSettingDivisionEnums = ko.observableArray([]);

                self.isManageCompen = ko.computed(function() {
                    return self.compenManage() == UseDivision.Use;
                });

                self.isManageTime = ko.computed(function() {
                    return self.isManageCompen() && self.compenTimeManage() == UseDivision.Use;
                });

                self.enableWorkArea = ko.computed(function() {
                    return self.checkWorkTime() && self.isManageCompen();
                });

                self.enableOverArea = ko.computed(function() {
                    return self.checkOverTime() && self.isManageCompen();
                });

                self.enableWorkAll = ko.computed(function() {
                    return self.enableWorkArea() && self.selectedOfWorkTime() == UseDivision.Use;
                });

                self.enableOverAll = ko.computed(function() {
                    return self.enableOverArea() && self.selectedOfOverTime() == UseDivision.Use;
                });

                self.enableDesignWork = ko.computed(function() {
                    return self.enableWorkArea() && self.selectedOfWorkTime() == UseDivision.NotUse;
                });

                self.enableDesignOver = ko.computed(function() {
                    return self.enableOverArea() && self.selectedOfOverTime() == UseDivision.NotUse;
                });

                //employment
                self.employmentBackUpData = ko.observable();
                self.employmentList = ko.observableArray<ItemModel>([]);
                for (let i = 1; i < 4; i++) {
                    self.employmentList.push(new ItemModel('0' + i, '基本給'));
                }
                
                self.columnsSetting = ko.observableArray([
                {headerText: '設定済', key: 'setting', width: 50 },
                    { headerText: 'コード', key: 'code', width: 50 },
                    { headerText: '名称', key: 'name', width: 200 }
                ]);
                self.emSelectedCode = ko.observable('');
                
                self.emCompenManage = ko.observable(0);
                self.emExpirationTime = ko.observable(0);
                self.emPreApply = ko.observable(0);
                self.emTimeManage = ko.observable(0);
                self.emTimeUnit = ko.observable(0);
                
                self.emSelectedCode.subscribe(function(employmentCode: string){
                        self.loadEmploymentSetting(employmentCode);
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
                        if (self.employmentList().length > 0) {
                            self.emSelectedCode(self.employmentList()[0].code);
                        }
                        else {
                            dfd.resolve();
                        }
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

            private loadTransferSettingDivisionEnums(): JQueryPromise<Array<RadioEnum>> {
                let self = this;
                let dfd = $.Deferred();
                service.getEnumTransferSettingDivision().done(function(res: Array<RadioEnum>) {
                    res.forEach(function(item, index) {
                        item.enable = true;
                    });
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
                        self.backUpData(data);
                    }
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                });
                return dfd.promise();
            }
            
            private loadEmploymentSetting(employmentCode: string): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                service.findEmploymentSetting(employmentCode).done(function(data: any) {
                    if (data) {
                        self.loadEmploymentToScreen(data);
                        self.employmentBackUpData(data);
                    }
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                });
                return dfd.promise();
            }
            
            private loadEmploymentToScreen(data: any){
                var self =this;
                self.emCompenManage(data.employmentManageSetting.isManaged);
                self.emExpirationTime(data.employmentManageSetting.expirationTime);
                self.emPreApply(data.employmentManageSetting.preemptionPermit);
                self.emTimeManage(data.employmentTimeManageSetting.isManaged);
                self.emTimeUnit(data.employmentTimeManageSetting.digestiveUnit);
            }

            private loadToScreen(data: any) {
                let self = this;
                self.compenManage(data.isManaged.value);

                self.expirationDateCode(data.normalVacationSetting.expirationTime.value);
                self.compenPreApply(data.normalVacationSetting.preemptionPermit.value);
                self.compenTimeManage(data.normalVacationSetting.isManageByTime.value);
                self.timeUnitCode(data.normalVacationSetting.digestiveUnit.value);

                //if check f3
                if (data.occurrenceVacationSetting.transferSettingDayOffTime.useDivision == true) {
                    //set check box
                    self.checkWorkTime(true);
                }
                else {
                    self.checkWorkTime(false);
                }
                //set data
                self.selectedOfWorkTime(data.occurrenceVacationSetting.transferSettingDayOffTime.transferDivision.value);
                self.workOneDay(self.convertTimeToString(data.occurrenceVacationSetting.transferSettingDayOffTime.oneDayTime));
                self.workHalfDay(self.convertTimeToString(data.occurrenceVacationSetting.transferSettingDayOffTime.halfDayTime));
                self.workAll(self.convertTimeToString(data.occurrenceVacationSetting.transferSettingDayOffTime.certainTime));
                if (data.occurrenceVacationSetting.transferSettingOverTime.useDivision == true) {
                    //set check box
                    self.checkOverTime(true);
                }
                else {//if check f13
                    self.checkOverTime(false);
                }
                //set data
                self.selectedOfOverTime(data.occurrenceVacationSetting.transferSettingOverTime.transferDivision.value);
                self.overOneDay(self.convertTimeToString(data.occurrenceVacationSetting.transferSettingOverTime.oneDayTime));
                self.overHalfDay(self.convertTimeToString(data.occurrenceVacationSetting.transferSettingOverTime.halfDayTime));
                self.overAll(self.convertTimeToString(data.occurrenceVacationSetting.transferSettingOverTime.certainTime));
            }

            private saveData() {
                let self = this;
                self.reCallValidate().done(function() {
                    if (!$('.check_error').ntsError('hasError'))
                        service.update(self.collectData()).done(function() {
                            nts.uk.ui.dialog.alert(nts.uk.resource.getMessage('Msg_15'));
                            self.loadSetting();
                        });
                });
            }
            private reCallValidate(): JQueryPromise<void> {
                var self = this;
                let dfd = $.Deferred<void>();
                if (self.enableDesignWork()) {
                    $('#workOneDay').ntsEditor('validate');
                    $('#workHalfDay').ntsEditor('validate');
                }
                if (self.enableWorkAll()) {
                    $('#workAll').ntsEditor('validate');
                }

                if (self.enableDesignOver()) {
                    $('#overOneDay').ntsEditor('validate');
                    $('#overHalfDay').ntsEditor('validate');
                }
                if (self.enableOverAll()) {
                    $('#overAll').ntsEditor('validate');
                }
                dfd.resolve();
                return dfd.promise();
            }
            private collectData() {
                var self = this;
                var data = self.backUpData();
                return {
                    companyId: "",
                    isManaged: self.compenManage(),
                    normalVacationSetting: {
                        expirationTime: self.isManageCompen()?self.expirationDateCode():data.normalVacationSetting.expirationTime.value,
                        preemptionPermit: self.isManageCompen()?self.compenPreApply():data.normalVacationSetting.preemptionPermit.value,
                        isManageByTime: self.isManageCompen()?self.compenTimeManage():data.normalVacationSetting.isManageByTime.value,
                        digestiveUnit: self.isManageTime()?self.timeUnitCode():data.normalVacationSetting.digestiveUnit.value
                    },
                    occurrenceVacationSetting: {
                        transferSettingOverTime: {
                            certainTime: self.enableOverAll()?self.convertTime(self.overAll()):data.occurrenceVacationSetting.transferSettingOverTime.certainTime,
                            useDivision: self.isManageCompen()?self.checkOverTime():data.occurrenceVacationSetting.transferSettingOverTime.useDivision,
                            oneDayTime: self.enableDesignOver()?self.convertTime(self.overOneDay()):data.occurrenceVacationSetting.transferSettingOverTime.oneDayTime,
                            halfDayTime: self.enableDesignOver()?self.convertTime(self.overHalfDay()):data.occurrenceVacationSetting.transferSettingOverTime.halfDayTime,
                            transferDivision: self.enableOverArea()?self.selectedOfOverTime():data.occurrenceVacationSetting.transferSettingOverTime.transferDivision.value,
                            compensatoryOccurrenceDivision: OccurrenceDivision.OverTime
                        },
                        transferSettingDayOffTime: {
                            certainTime: self.enableWorkAll()?self.convertTime(self.workAll()):data.occurrenceVacationSetting.transferSettingDayOffTime.certainTime,
                            useDivision: self.isManageCompen()?self.checkWorkTime():data.occurrenceVacationSetting.transferSettingDayOffTime.useDivision,
                            oneDayTime: self.enableDesignWork()?self.convertTime(self.workOneDay()):data.occurrenceVacationSetting.transferSettingDayOffTime.oneDayTime,
                            halfDayTime: self.enableDesignWork()?self.convertTime(self.workHalfDay()):data.occurrenceVacationSetting.transferSettingDayOffTime.halfDayTime,
                            transferDivision: self.enableWorkArea()?self.selectedOfWorkTime():data.occurrenceVacationSetting.transferSettingDayOffTime.transferDivision.value,
                            compensatoryOccurrenceDivision: OccurrenceDivision.DayOffTime
                        }
                    }
                };
            }
            private convertTime(time: string) {
                return nts.uk.time.parseTime(time).hours * 100 + nts.uk.time.parseTime(time).minutes;
            }
            private convertTimeToString(timeNumber: number) {
                var timeString = nts.uk.time.parseTime(timeNumber).format();
                if (timeString.length == 4) {
                    timeString = "0" + timeString;
                }
                return timeString;
            }
            
            //Employment
            
            private saveEmployment(){
                var self = this;
                service.updateEmploymentSetting(self.collectEmploymentData()).done(function() {
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                });
            }
            private collectEmploymentData(){
                var self = this;
                return {
                    companyId: '',
                    employmentCode: self.emSelectedCode(),
                    employmentManageSetting: {
                        isManaged: self.emCompenManage(),
                        expirationTime: self.emExpirationTime(),
                        preemptionPermit: self.emPreApply()
                    },
                    employmentTimeManageSetting: {
                        isManaged: self.emTimeManage(),
                        digestiveUnit: self.emTimeUnit()
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
            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }

        export enum OccurrenceDivision {
            OverTime = 0,
            DayOffTime = 1
        }

        export enum UseDivision {
            NotUse = 0,
            Use = 1
        }
    }
}