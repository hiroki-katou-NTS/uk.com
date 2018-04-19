module nts.uk.at.view.kdr001.b.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import errors = nts.uk.ui.errors;
    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    export class ScreenModel {
        lstHolidays: KnockoutObservableArray<HolidayRemaining> = ko.observableArray([]);
        currentCode: KnockoutObservable<String>;
        currentHoliday: KnockoutObservable<HolidayRemaining> = ko.observable(new HolidayRemaining(null));
        switchOptions: KnockoutObservableArray<any>;
        isNewMode: KnockoutObservable<boolean> = ko.observable(true);
        holidayCode : KnockoutObservableArray<String>;
        constructor() {
            let self = this;
            let params = getShared("KDR001Params");
            //TODO
            self.holidayCode = ko.observable('aaa');
            self.currentCode = ko.observable(params || '');
            self.currentCode.subscribe(cd => {
                errors.clearAll();
                let lstHolidays = self.lstHolidays();
                if (cd && lstHolidays && lstHolidays.length > 0) {
                    let index: number = 0;
                    if (cd) {
                        index = _.findIndex(lstHolidays, function(x)
                        { return x.cd == cd });
                        if (index === -1) index = 0;
                    }
                    let _holiday = lstHolidays[index];
                    if (_holiday && _holiday.cd) {

                        self.currentHoliday(_holiday);
                    } else {
                        self.currentHoliday(null);
                    }
                }
            });
        }
        /**
         * 開始
         **/
        private start(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            nts.uk.ui.block.invisible();
            service.findAll()
                .done(function(data: Array<HolidayRemaining>) {
                    if (data.length > 0) {
                        self.lstHolidays(data);
                        if (!self.currentCode()) {
                            self.currentCode(data[0].cd);
                        } else {
                            index = _.findIndex(lstHolidays, function(x)
                            { return x.cd == cd });
                            if (index === -1) index = 0;
                            let _holiday = lstHolidays[index];
                            if (_holiday && _holiday.cd) {
        
                                self.currentHoliday(_holiday);
                            } else {
                                self.currentHoliday(null);
                            }
                        }
                    }
                    // no data
                    else {
                        self.lstHolidays([]);
                        self.currentCode('');
                    }
                    nts.uk.ui.block.clear();
                    dfd.resolve();
                    
                })
                .fail(function(res) {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId }).then(function() { nts.uk.ui.block.clear(); });
                    nts.uk.ui.block.clear();
                    dfd.rejected();
                    
                });
            return dfd.promise();
            
        }

        /**
         * create a new Holiday Remaining
         * 
         */
        settingCreateMode() {
            let self = this,
                currentHoliday: HolidayRemaining = self.currentHoliday();
            // clear selected holiday set
            self.currentCode('');

            // Set new mode
            self.isNewMode(true);

            //focus
            self.setFocus();
        }

        setFocus() {
            let self = this;
            if (self.isNewMode()) {
                $('#holidayCode').focus();
            } else {
                $('#holidayName').focus();
            }
            errors.clearAll();
        }

        /**
         * Save
         */
        saveHoliday() {
            let self = this,
                currentHoliday: HolidayRemaining = self.currentHoliday();
            $('.nts-input').trigger("validate");
            if (errors.hasError() === false) {
                block.invisible();
                if (self.isNewMode()) {
                    // create new holiday
                    service.addHolidayRemaining(ko.toJS(currentHoliday)).done(() => {
                        dialog.info({ messageId: "Msg_15" });
                        // refresh - initial screen TODO
                    service.findAll()
                        .done(function(data: HolidayRemaining) {
                            if (data.length > 0) {
                                self.lstHolidays(data);
                             }
                            // no data
                            else {
                                self.lstHolidays([]);
                                self.currentCode('');
                            }
                        });
                    }).fail(function(error) {

                        if (error.messageId == 'Msg_880') {
                            dialog.alertError({ messageId: error.messageId });
                        } else if (error.messageId == 'Msg_3') {
                            $('#holidayCode').ntsError('set', error);
                            $('#holidayName').focus();
                            dialog.alertError({ messageId: error.messageId });
                        }
                    }).always(function() {
                        block.clear();
                    });
                } else {
                    // update
                    service.updateHolidayRemaining(ko.toJS(currentHoliday)).done(() => {
                        dialog.info({ messageId: "Msg_15" });
                    }).fail(function(error) {
                        if (error.messageId == 'Msg_880') {
                            dialog.alertError({ messageId: error.messageId });
                        } else {
                            dialog.alertError({ messageId: error.messageId });
                        }
                    }).always(function() {
                        block.clear();
                    });
                }
            }
        }

        /**
         * delete the Holoday Remaining
         */
        deleteHoliday() {
            let self = this,
                lstHolidays = self.lstHolidays,
                currentHoliday: HolidayRemaining = self.currentHoliday();
            block.invisible();

            dialog.confirmDanger({ messageId: "Msg_18" }).ifYes(() => {
                if (currentHoliday.cd()) {
                    var object: any = { roleSetCd: currentHoliday.cd() };
                    service.removeHolidayRemaining(ko.toJS(object)).done(function() {
                        dialog.info({ messageId: "Msg_16" });
                        //select next Role Set
                        let index: number = _.findIndex(lstHolidays(), function(x)
                        { return x.cd == currentHoliday.cd() });
                        // remove the deleted item out of list
                        if (index > -1) {
                            self.lstHolidays.splice(index, 1);
                            if (index >= lstHolidays().length) {
                                index = lstHolidays().length - 1;
                            }
                            if (lstHolidays().length > 0) {
                                self.currentCode(lstHolidays[index].cd());
                                //Setting update mode
                                self.isNewMode(false);
                                //focus
                                self.setFocus();
                            } else {
                                self.currentCode('');
                                self.isNewMode(true);
                                //focus
                                self.setFocus();
                            }
                        }
                    }).fail(function(error) {
                        dialog.alertError({ messageId: error.messageId });
                    }).always(function() {
                        block.clear();
                    });
                } else {
                    block.clear();
                }
            }).then(() => {
                block.clear();
            });;
        }
        
        /**
         * close the dialog
         */
        closeDialog() {
            nts.uk.ui.windows.close()
        }


    }
    class HolidayRemaining {
        cid: KnockoutObservable<String>;
        cd: KnockoutObservable<String>;
        name: KnockoutObservable<String>;
        displayCd: String;
        displayName: String;
        yearlyHoliday: KnockoutObservable<boolean>;
        insideHalfDay: KnockoutObservable<boolean>;
        insideHours: KnockoutObservable<boolean>;
        yearlyReserved: KnockoutObservable<boolean>;
        outItemSub: KnockoutObservable<boolean>;
        representSub: KnockoutObservable<boolean>;
        remainChargeSub: KnockoutObservable<boolean>;
        pauseItem: KnockoutObservable<boolean>;
        undigestedPause: KnockoutObservable<boolean>;
        numRemainPause: KnockoutObservable<boolean>;
        outputItemsHolidays: KnockoutObservable<boolean>;
        outputHolidayForward: KnockoutObservable<boolean>;
        monthlyPublic: KnockoutObservable<boolean>;
        childCareLeave: KnockoutObservable<boolean>;
        nursingCareLeave: KnockoutObservable<boolean>;
        constructor(param: any) {
            let self = this;
            self.cid = ko.observable(param ? param.cid || '' : '');
            self.cd = ko.observable(param ? param.cd || '' : '');
            self.name = ko.observable(param ? param.name || '' : '');
            self.displayCd = param ? param.cd || '' : '';
            self.displayName = param ? param.name || '' : '';
            self.yearlyHoliday = ko.observable(param ? param.yearlyHoliday || false : false);
            self.insideHalfDay = ko.observable(param ? param.insideHalfDay || false : false);
            self.insideHours = ko.observable(param ? param.insideHours || false : false);
            self.yearlyReserved = ko.observable(param ? param.yearlyReserved || false : false);
            self.outItemSub = ko.observable(param ? param.outItemSub || false : false);
            self.representSub = ko.observable(param ? param.representSub || false : false);
            self.remainChargeSub = ko.observable(param ? param.remainChargeSub || false : false);
            self.pauseItem = ko.observable(param ? param.pauseItem || false : false);
            self.undigestedPause = ko.observable(param ? param.undigestedPause || false : false);
            self.numRemainPause = ko.observable(param ? param.numRemainPause || false : false);
            self.outputItemsHolidays = ko.observable(param ? param.outputItemsHolidays || false : false);
            self.outputHolidayForward = ko.observable(param ? param.outputHolidayForward || false : false);
            self.monthlyPublic = ko.observable(param ? param.monthlyPublic || false : false);
            self.childCareLeave = ko.observable(param ? param.childCareLeave || false : false);
            self.nursingCareLeave = ko.observable(param ? param.nursingCareLeave || false : false);
        }
    }
}