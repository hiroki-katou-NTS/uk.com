module nts.uk.at.view.kdr001.b.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import errors = nts.uk.ui.errors;
    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    export class ScreenModel {
        lstHolidays: KnockoutObservableArray<HolidayRemaining> = ko.observableArray([]);
        currentCode: KnockoutObservable<string>;
        currentHoliday: KnockoutObservable<HolidayRemaining> = ko.observable(new HolidayRemaining(null));
        switchOptions: KnockoutObservableArray<any>;
        isNewMode: KnockoutObservable<boolean> = ko.observable(true);
        listSpecialHolidays: KnockoutObservableArray<SpecialHoliday> = ko.observableArray([]);
        constructor() {
            let self = this;
            let params = getShared("KDR001Params");
            //TODO
            self.currentCode = ko.observable(params || '');
            self.currentCode.subscribe(cd => {
                errors.clearAll();
                let lstHolidays = self.lstHolidays();
                if (cd && lstHolidays && lstHolidays.length > 0) {
                    let index: number = 0;
                    if (cd) {
                        index = _.findIndex(lstHolidays, function(x)
                        { return x.cd === cd });
                        if (index === -1) index = 0;
                    }
                    let _holiday = lstHolidays[index];
                    if (_holiday && _holiday.cd) {

                        // self.currentHoliday(_holiday);
                        self.buildHolidayRemaining(_holiday);
                    } else {
                        //self.currentHoliday(null);
                        self.buildHolidayRemaining(null);
                    }
                    self.buildSpecialHoliday();
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

                                self.buildHolidayRemaining(_holiday);
                            } else {
                                self.buildHolidayRemaining(null);
                            }
                        }
                    }
                    // no data
                    else {
                        self.lstHolidays([]);
                        self.currentCode('');
                    }
                    // get special holiday
                    service.findAllSpecialHoliday().done(function(data : any) {
                            if (data.length > 0) {
                                self.listSpecialHolidays(data);
                                ddd
                            }
                            // no data
                            else {
                                self.listSpecialHolidays([]);
                            }
                            nts.uk.ui.block.clear();
                            dfd.resolve();
        
                        })
                        .fail(function(res) {
                            nts.uk.ui.dialog.alertError({ messageId: res.messageId }).then(function() { nts.uk.ui.block.clear(); });
                            nts.uk.ui.block.clear();
                            dfd.rejected();
        
                        });

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
                self.setSpecialHoliday();
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
                        { return x.cd() == currentHoliday.cd() });
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


        buildHolidayRemaining(param: any) {
            let self = this;
            self.currentHoliday = ko.observable(new HolidayRemaining(param));
        }
        
        buildSpecialHoliday() {
            let self = this;
            for(var i = 0; i < self.listSpecialHolidays.length; i++) {
                self.listSpecialHolidays[i].statusCheck(false);
            }
            let _specialHolidays = self.currentHoliday().specialHolidays();
            if (_specialHolidays && _specialHolidays.length > 0) {
                var index;
                for(var i = 0; i < _specialHolidays.length; i++) {
                    index = _.findIndex(self.listSpecialHolidays, function(x)
                    { return x.specialHolidayCode == _specialHolidays[i] });
                    if (index > -1) {
                        self.listSpecialHolidays[index].statusCheck(true);
                    }
                }
            }
        }
        
        setSpecialHoliday() {
            let self = this;
            let specialHolidays: Array<number> = [];
            for(var i = 0; i < self.listSpecialHolidays.length; i++) {
                if (self.listSpecialHolidays[i].statusCheck()) {
                    specialHolidays.push(self.listSpecialHolidays[i].specialHolidayCode());
                }
            }
            self.currentHoliday().specialHolidays().clearAll();
            self.currentHoliday().specialHolidays(specialHolidays);
        }
    }
    export class HolidayRemaining {

        /**
         * 会社ID
         */
        cid: KnockoutObservable<string>;
        /**
          * コード
        */
        cd: KnockoutObservable<string>;
        /**
         * 名称
         */
        name: KnockoutObservable<string>;
        displayCd: string;
        displayName: string;
        /**
         * 介護休暇の項目を出力する
         */
        nursingLeave: KnockoutObservable<boolean>;
        /**
         * 代休残数を出力する
         */
        remainingChargeSubstitute: KnockoutObservable<boolean>;

        /**
         * 代休未消化出力する
         */
        representSubstitute: KnockoutObservable<boolean>;
        /**
         * 代休の項目を出力する
         */
        outputItemSubstitute: KnockoutObservable<boolean>;

        /**
         * 公休繰越数を出力する
         */
        outputholidayforward: KnockoutObservable<boolean>;
        /**
         * 公休月度残を出力する
         */
        monthlyPublic: KnockoutObservable<boolean>;
        /**
         * 公休の項目を出力する
         */
        outputitemsholidays: KnockoutObservable<boolean>;

        /**
         * childNursingLeave
         */
        ChildNursingLeave: KnockoutObservable<boolean>;

        /**
         * 年休の項目出力する
         */
        yearlyHoliday: KnockoutObservable<boolean>;

        /**
         * 内時間年休残数を出力する
         */
        insideHours: KnockoutObservable<boolean>;

        /**
         * ★内半日年休を出力する
         */
        insideHalfDay: KnockoutObservable<boolean>;

        /**
         * 振休残数を出力する
         */
        numberRemainingPause: KnockoutObservable<boolean>;
        /**
         * 振休未消化を出力する
         */
        undigestedPause: KnockoutObservable<boolean>;
        /**
         * 振休の項目を出力する
         */
        pauseItem: KnockoutObservable<boolean>;

        /**
         * 積立年休の項目を出力する
         */
        yearlyReserved: KnockoutObservable<boolean>;
        specialHolidays: KnockoutObservableArray<number>;
        constructor(param: any) {
            let self = this;
            self.cid = ko.observable(param ? param.cid || '' : '');
            self.cd = ko.observable(param ? param.cd || '' : '');
            self.name = ko.observable(param ? param.name || '' : '');
            self.displayCd = param ? param.cd || '' : '';
            self.displayName = param ? param.name || '' : '';
            self.nursingLeave = ko.observable(param ? param.nursingLeave || false : false);
            self.remainingChargeSubstitute = ko.observable(param ? param.remainingChargeSubstitute || false : false);
            self.representSubstitute = ko.observable(param ? param.representSubstitute || false : false);
            self.outputItemSubstitute = ko.observable(param ? param.outputItemSubstitute || false : false);
            self.outputholidayforward = ko.observable(param ? param.outputholidayforward || false : false);
            self.monthlyPublic = ko.observable(param ? param.monthlyPublic || false : false);
            self.outputitemsholidays = ko.observable(param ? param.outputitemsholidays || false : false);
            self.ChildNursingLeave = ko.observable(param ? param.ChildNursingLeave || false : false);
            self.yearlyHoliday = ko.observable(param ? param.yearlyHoliday || false : false);
            self.insideHours = ko.observable(param ? param.insideHours || false : false);
            self.insideHalfDay = ko.observable(param ? param.insideHalfDay || false : false);
            self.numberRemainingPause = ko.observable(param ? param.numberRemainingPause || false : false);
            self.undigestedPause = ko.observable(param ? param.undigestedPause || false : false);
            self.pauseItem = ko.observable(param ? param.pauseItem || false : false);
            self.yearlyReserved = ko.observable(param ? param.yearlyReserved || false : false);
            self.specialHolidays = ko.observableArray(param ? param.specialHolidays || [] : []);
        }
    }

    export class SpecialHoliday {
        /*特別休暇コード*/
        specialHolidayCode: KnockoutObservable<number>;

        /*特別休暇名称*/
        specialHolidayName: KnockoutObservable<string>;
        
        statusCheck: KnockoutObservable<boolean>;
        
        constructor(param: any){
            let self = this;
            self.specialHolidayCode = ko.observable(param ? param.specialHolidayCode || null : null );
            self.specialHolidayName = ko.observable(param ? param.specialHolidayName || '' : '' );
            self.statusCheck = ko.observable(param ? param.statusCheck || false : false );
        }
        
    }

}