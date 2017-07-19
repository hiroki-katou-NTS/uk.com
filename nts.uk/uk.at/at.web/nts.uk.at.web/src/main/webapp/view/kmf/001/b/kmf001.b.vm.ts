module nts.uk.pr.view.kmf001.b {
    export module viewmodel {

        import Enum = service.model.Enum;
        
        export class ScreenModel {
            textEditorOption: KnockoutObservable<any>;
            categoryEnums: KnockoutObservableArray<Enum>;
            acquisitionTypeEnums: KnockoutObservableArray<Enum>;
            selectedPriority: KnockoutObservable<number>;
            enableInputPriority: KnockoutObservable<boolean>;

            annualPaidLeave: KnockoutObservable<number>;
            compensatoryDayOff: KnockoutObservable<number>;
            substituteHoliday: KnockoutObservable<number>;
            fundedPaidHoliday: KnockoutObservable<number>;
            exsessHoliday: KnockoutObservable<number>;
            specialHoliday: KnockoutObservable<number>;
            
            paidLeaveSetting: KnockoutObservable<boolean>;
            retentionYearlySetting: KnockoutObservable<boolean>;
            compensLeaveComSetSetting: KnockoutObservable<boolean>;
            comSubtSetting: KnockoutObservable<boolean>;
            com60HSetting: KnockoutObservable<boolean>;
            nursingSetting: KnockoutObservable<boolean>;

            enableHelpButton: KnockoutObservable<boolean>;

            constructor() {

                let self = this;

                self.textEditorOption = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    width: "111px",
                    textalign: "right"
                }));

                self.categoryEnums = ko.observableArray([]);
                
                self.acquisitionTypeEnums = ko.observableArray([]);

                self.selectedPriority = ko.observable(1);
                self.enableInputPriority = ko.computed(function() {
                    return self.selectedPriority() == 1;
                }, self);

                self.annualPaidLeave = ko.observable(null);
                self.compensatoryDayOff = ko.observable(null);
                self.substituteHoliday = ko.observable(null);
                self.fundedPaidHoliday = ko.observable(null);
                self.exsessHoliday = ko.observable(null);
                self.specialHoliday = ko.observable(null);
                
                self.paidLeaveSetting = ko.observable(false);
                self.retentionYearlySetting = ko.observable(false);
                self.compensLeaveComSetSetting = ko.observable(false);
                self.comSubtSetting = ko.observable(false);
                self.com60HSetting = ko.observable(false);
                self.nursingSetting = ko.observable(false);

                self.enableHelpButton = ko.observable(true);
            }

            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<void>();
                $.when(self.loadApplySetting(),self.loadCategoryEnums(), self.loadAcquisitionTypeEnums()).done(function(res) {
                    self.loadAcquisitionRule();
                    $('#priority').focus();
                    dfd.resolve();
                });
                return dfd.promise();
            }
            
            private loadApplySetting() : JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                service.findSettingAll().done(function(res: any){
                     if(res.paidLeaveSetting){
                         self.paidLeaveSetting(true);
                     }
                    if(res.paidLeaveSetting){
                         self.retentionYearlySetting(true);
                     }
                    if(res.compensLeaveComSetSetting){
                         self.compensLeaveComSetSetting(true);
                     }
                    if(res.comSubtSetting){
                         self.comSubtSetting(true);
                     }
                    if(res.com60HSetting){
                         self.com60HSetting(true);
                     }
                    if(res.nursingSetting){
                         self.nursingSetting(true);
                     }
                     dfd.resolve();
                }).fail(function(res){
                    nts.uk.ui.dialog.alertError(res.message);
                });
            }
            
            private loadCategoryEnums(): JQueryPromise<Array<Enum>> {
                let self = this;

                let dfd = $.Deferred();
                service.categoryEnum().done(function(res: Array<Enum>) {
                    self.categoryEnums(res);
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                });

                return dfd.promise();
            }
            
            private loadAcquisitionTypeEnums(): JQueryPromise<Array<Enum>> {
                let self = this;

                let dfd = $.Deferred();
                service.acquisitionTypeEnum().done(function(res: Array<Enum>) {
                    self.acquisitionTypeEnums(res);
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                });

                return dfd.promise();
            }
            
            //CLOSE DIALOG
            public closeDialog(): void {
                nts.uk.ui.windows.close();
            }

            //LOAD ACQUISITION RULE WHEN START DIALOG
            private loadAcquisitionRule(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                //  find data on db
                service.findAcquisitionRule().done(function(res: any) {
                    self.initUI(res);
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                });
                return dfd.promise();
            }

            //get data to dialog
            private initUI(res: any): void {
                let self = this;
                //if find data exist
                if (res) {
                    //if use Priority
                    self.selectedPriority(res.category);
                    //set list priority
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
                } else {
                    //if find data null
                    //Selected default button is "No Setting"
                    self.selectedPriority(0);

                    //List priority default all value = 1.
                    self.annualPaidLeave(1);
                    self.compensatoryDayOff(1);
                    self.substituteHoliday(1);
                    self.fundedPaidHoliday(1);
                    self.exsessHoliday(1);
                    self.specialHoliday(1);
                }

                //when change button Select
                self.selectedPriority.subscribe(function(value) {
                    //if click button "No Setting"
                    if (value == 0) {
                        nts.uk.ui.errors.clearAll();
                    }
                });
            }

            //CLICK SAVE TO DB
            public saveToDb(): void {
                let self = this;
                let dfd = $.Deferred();
                //validate input priority
                if (self.selectedPriority() == 1) {
                    if (self.validate()) {
                        return;
                    } else {
                        let command = self.setList();
                        service.updateAcquisitionRule(command).done(function() {
                            self.loadAcquisitionRule().done(function(res) {
                                // Msg_15
                                nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                                    self.closeDialog();
                                });
                                dfd.resolve();
                            });
                        }).fail(function(res) {
                            nts.uk.ui.dialog.alertError(res.message);
                        });

                    }
                } else {
                    let command = self.setList();
                    service.updateAcquisitionRule(command).done(function() {
                        self.loadAcquisitionRule().done(function(res) {
                            // Msg_15
                            nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                                self.closeDialog();
                            });
                            dfd.resolve();
                        });
                    }).fail(function(res) {
                        nts.uk.ui.dialog.alertError(res.message);
                    });

                }
            }
            
            //GET DATA
            private setList(): void {
                let self = this;
                let command: any = {};
                if (self.selectedPriority() == 0) {
                    command.category = 0;
                } else {
                    command.category = 1;
                }
                //Create Acquisition List
                let acquisitionOrderList = new Array();

                //Set data Acquisition Annua lPaid Leave
                let acquisitionAnnualPaidLeave: any = {};
                acquisitionAnnualPaidLeave.vacationType = "AnnualPaidLeave";
                if (self.paidLeaveSetting()) {
                    acquisitionAnnualPaidLeave.priority = +self.annualPaidLeave();
                } else {
                    acquisitionAnnualPaidLeave.priority = 1;
                }
                acquisitionOrderList.push(acquisitionAnnualPaidLeave);

                //Set data Acquisition Compensatory Day Off
                let acquisitionCompensatoryDayOff: any = {};
                acquisitionCompensatoryDayOff.vacationType = "CompensatoryDayOff";
                if (self.retentionYearlySetting()) {
                    acquisitionCompensatoryDayOff.priority = +self.compensatoryDayOff();
                } else {
                    acquisitionCompensatoryDayOff.priority = 1;
                }
                acquisitionOrderList.push(acquisitionCompensatoryDayOff);

                //Set data Acquisition Substitute Holiday
                let acquisitionSubstituteHoliday: any = {};
                acquisitionSubstituteHoliday.vacationType = "SubstituteHoliday";
                if (self.compensLeaveComSetSetting()) {
                    acquisitionSubstituteHoliday.priority = +self.substituteHoliday();
                } else {
                    acquisitionSubstituteHoliday.priority = 1;
                }
                acquisitionOrderList.push(acquisitionSubstituteHoliday);

                //Set data Acquisition Funded Paid Holiday
                let acquisitionFundedPaidHoliday: any = {};
                acquisitionFundedPaidHoliday.vacationType = "FundedPaidHoliday";
                if (self.comSubtSetting()) {
                    acquisitionFundedPaidHoliday.priority = +self.fundedPaidHoliday();
                } else {
                    acquisitionFundedPaidHoliday.priority = 1;
                }
                acquisitionOrderList.push(acquisitionFundedPaidHoliday);

                //Set data Acquisition Exsess Holiday
                let acquisitionExsessHoliday: any = {};
                acquisitionExsessHoliday.vacationType = "ExsessHoliday";
                if (self.com60HSetting()) {
                    acquisitionExsessHoliday.priority = +self.exsessHoliday();
                } else {
                    acquisitionExsessHoliday.priority = 1;
                }
                acquisitionOrderList.push(acquisitionExsessHoliday);

                //Set data Acquisition Special Holiday
                let acquisitionSpecialHoliday: any = {};
                acquisitionSpecialHoliday.vacationType = "SpecialHoliday";
                if (self.nursingSetting()) {
                    acquisitionSpecialHoliday.priority = +self.specialHoliday();
                } else {
                    acquisitionSpecialHoliday.priority = 1;
                }
                acquisitionOrderList.push(acquisitionSpecialHoliday);

                command.vaAcRule = acquisitionOrderList;
                return command;
            }

            private validate(): boolean {
                let self = this;
                $('#annual-paid-leave').ntsEditor('validate');
                $('#compensatory-day-off').ntsEditor('validate');
                $('#substitute-holiday').ntsEditor('validate');
                $('#funded-paid-holiday').ntsEditor('validate');
                $('#exsess-holiday').ntsEditor('validate');
                $('#special-holiday').ntsEditor('validate');
                return $('.nts-input').ntsError('hasError');
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
