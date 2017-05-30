module nts.uk.pr.view.kmf001.b {
    export module viewmodel {

        import EnumertionModel = service.model.EnumerationModel;

        export class ScreenModel {

            priority: KnockoutObservableArray<EnumertionModel>;
            selectedPriority: KnockoutObservable<number>;
            enableInputPriority: KnockoutObservable<boolean>;

            annualPaidLeave: KnockoutObservable<number>;
            compensatoryDayOff: KnockoutObservable<number>;
            substituteHoliday: KnockoutObservable<number>;
            fundedPaidHoliday: KnockoutObservable<number>;
            exsessHoliday: KnockoutObservable<number>;
            specialHoliday: KnockoutObservable<number>;

            enableHelpButton: KnockoutObservable<boolean>;

            constructor() {

                let self = this;
                self.priority = ko.observableArray([
                    { value: 0, name: "設定する" },
                    { value: 1, name: "設定しない" }
                ]);

                self.selectedPriority = ko.observable(0);
                self.enableInputPriority = ko.computed(function() {
                    return self.selectedPriority() == 0;
                }, self);

                self.annualPaidLeave = ko.observable(null);
                self.compensatoryDayOff = ko.observable(null);
                self.substituteHoliday = ko.observable(null);
                self.fundedPaidHoliday = ko.observable(null);
                self.exsessHoliday = ko.observable(null);
                self.specialHoliday = ko.observable(null);

                self.enableHelpButton = ko.observable(true);

                // SUBSCRIBE
                self.selectedPriority.subscribe(function() {
                    self.clearError();
                });
                self.closeDialog = function() {
                    nts.uk.ui.windows.close();
                };
            }
            public startPage(): JQueryPromise<any> {

                var self = this;
                var dfd = $.Deferred<void>();
                $.when(self.loadAcquisitionRule()).done(function(res) {
                    if (res) {
                        self.initUI(res);
                    }
                    dfd.resolve();
                });
                return dfd.promise();
            }

            //CLOSE DIALOG
            public closeDialog(): void {
                nts.uk.ui.windows.close();
            }

            //LOAD ACQUISITION RULE
            private loadAcquisitionRule(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                service.findAcquisitionRule().done(function(res: any) {
                    if (res) {
                        self.initUI(res);
                    }
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                });
                return dfd.promise();
            }

            //GET PRIORITY
            private initUI(res: any): any {
                let self = this;
                if (res.settingClass == "Setting") {
                    self.selectedPriority(0);
                } else {
                    self.selectedPriority(1);
                }
                res.vaAcOrders.forEach(item => {
                    if (item.vacationType == 1) {
                        self.annualPaidLeave(item.priority);
                    }
                    if (item.vacationType == 2) {
                        self.compensatoryDayOff(item.priority);
                    }
                    if (item.vacationType == 3) {
                        self.substituteHoliday(item.priority);
                    }
                    if (item.vacationType == 4) {
                        self.fundedPaidHoliday(item.priority);
                    }
                    if (item.vacationType == 5) {
                        self.exsessHoliday(item.priority);
                    }
                    if (item.vacationType == 6) {
                        self.specialHoliday(item.priority);
                    }
                });
            }

            //CLICK SAVE TO DB
            public saveToDb(): void {
                let self = this;
                let dfd = $.Deferred();
                if (!self.validate()) {
                    return;
                } else {
                    let command = self.setList();
                    service.updateAcquisitionRule(command).done(function() {
                        self.loadAcquisitionRule().done(function(res) {
                            // Msg_15
                            nts.uk.ui.dialog.alert("登録しました。").then(function() {
                                self.closeDialog();
                            });
                            dfd.resolve();
                        });
                    }).fail(function(res) {
                        nts.uk.ui.dialog.alert(res.message);
                    });

                }
            }

            //SET DATA
            private setList(): void {
                let self = this;
                let command: any = {};
                if (self.selectedPriority() == 0) {
                    command.settingClassification = "Setting";
                } else {
                    command.settingClassification = "NoSetting";
                }
                let acquisitionOrderList = new Array();

                let acquisitionAnnualPaidLeave: any = {};
                acquisitionAnnualPaidLeave.vacationType = "AnnualPaidLeave";
                acquisitionAnnualPaidLeave.priority = +self.annualPaidLeave();
                acquisitionOrderList.push(acquisitionAnnualPaidLeave);

                let acquisitionCompensatoryDayOff: any = {};
                acquisitionCompensatoryDayOff.vacationType = "CompensatoryDayOff";
                acquisitionCompensatoryDayOff.priority = +self.compensatoryDayOff();
                acquisitionOrderList.push(acquisitionCompensatoryDayOff);

                let acquisitionSubstituteHoliday: any = {};
                acquisitionSubstituteHoliday.vacationType = "SubstituteHoliday";
                acquisitionSubstituteHoliday.priority = +self.substituteHoliday();
                acquisitionOrderList.push(acquisitionSubstituteHoliday);

                let acquisitionFundedPaidHoliday: any = {};
                acquisitionFundedPaidHoliday.vacationType = "FundedPaidHoliday";
                acquisitionFundedPaidHoliday.priority = +self.fundedPaidHoliday();
                acquisitionOrderList.push(acquisitionFundedPaidHoliday);

                let acquisitionExsessHoliday: any = {};
                acquisitionExsessHoliday.vacationType = "ExsessHoliday";
                acquisitionExsessHoliday.priority = +self.exsessHoliday();
                acquisitionOrderList.push(acquisitionExsessHoliday);

                let acquisitionSpecialHoliday: any = {};
                acquisitionSpecialHoliday.vacationType = "SpecialHoliday";
                acquisitionSpecialHoliday.priority = +self.specialHoliday();
                acquisitionOrderList.push(acquisitionSpecialHoliday);

                command.vaAcRule = acquisitionOrderList;
                return command;
            }

            private validate(): boolean {
                let self = this;
                if ($('.nts-input').ntsError('hasError')) {
                    return false;
                }
                if (self.enableInputPriority()) {
                    $('#annual-paid-leave').ntsEditor('validate');
                    $('#compensatory-day-off').ntsEditor('validate');
                    $('#substitute-holiday').ntsEditor('validate');
                    $('#funded-paid-holiday').ntsEditor('validate');
                    $('#exsess-holiday').ntsEditor('validate');
                    $('#special-holiday').ntsEditor('validate');
                }
                return true;
            }
            private clearError(): void {
                if (!$('.nts-input').ntsError('hasError')) {
                    return;
                }
                $('#annual-paid-leave').ntsError('clear');
                $('#compensatory-day-off').ntsError('clear');
                $('#substitute-holiday').ntsError('clear');
                $('#funded-paid-holiday').ntsError('clear');
                $('#exsess-holiday').ntsError('clear');
                $('#special-holiday').ntsError('clear');
            }

        }
        export class AcquisitionOrder {
            vacationType: number;
            priority: number;
        }
    }
}