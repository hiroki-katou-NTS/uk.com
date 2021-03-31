/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.kdr001.b.viewmodel {

    import getShared = nts.uk.ui.windows.getShared;
    const paths: any = {
        findAll: "at/function/holidaysremaining/findAll",
        findByLayOutId: "at/function/holidaysremaining/findByLayOutId/{0}",
        addHoliday: "at/function/holidaysremaining/add",
        updateHoliday: "at/function/holidaysremaining/update",
        removeHoliday: "at/function/holidaysremaining/remove",
        findAnnualPaidLeave: "ctx/at/share/vacation/setting/annualpaidleave/find/setting",
        findRetentionYearly: "ctx/at/shared/vacation/setting/retentionyearly/find",
        findCompensatory: "ctx/at/shared/vacation/setting/compensatoryleave/find",
        findSubstVacation: "ctx/at/shared/vacation/setting/substvacation/com/find",
        findAllSpecialHoliday: "shared/specialholiday/findByCid",
        getVariousVacationControl: "at/function/holidaysremaining/getVariousVacationControl"
    };

    @bean()
    export class ScreenModel extends ko.ViewModel {
        lstHolidays: KnockoutObservableArray<HolidayRemaining> = ko.observableArray([]);
        currentCode: KnockoutObservable<string>;
        currentHoliday: KnockoutObservable<HolidayRemaining> = ko.observable(new HolidayRemaining(null));

        switchOptions: KnockoutObservableArray<any>;
        isNewMode: KnockoutObservable<boolean> = ko.observable(true);
        allSpecialHolidays: KnockoutObservableArray<SpecialHoliday> = ko.observableArray([]);
        listSpecialHoliday: KnockoutObservableArray<any> = ko.observableArray([]);
        listSpecialHolidayEnable: KnockoutObservableArray<any> = ko.observableArray([]);
        vacationControl: IVariousVacationControl;
        constructor() {
            super();
            const vm = this;
            let params = getShared("KDR001Params");
            vm.currentCode = ko.observable(params || '');
            vm.currentCode.subscribe((code) => {
                if (code) {
                    vm.$blockui("show");
                    vm.$ajax(paths.findByLayOutId).done((data: HolidayRemaining) => {
                        if (data) {
                            let item = new HolidayRemaining(data);
                            vm.currentHoliday(item);
                            vm.setData();
                            vm.isNewMode(false);
                            vm.setFocus();
                            vm.setSpecialHolidayStyle();
                        }
                        _.defer(() => { vm.$errors("clear"); });
                    }).fail((error) => {
                        vm.$dialog.error({ messageId: error })
                    }).always(() => {
                        vm.$blockui("hide");
                    });
                }
                else {
                    vm.settingCreateMode();
                }
            });
        }
        /**
         * 開始
         **/
        start(): JQueryPromise<any> {
            const vm = this;
            let dfd = $.Deferred();
            vm.$blockui("show");

            $.when(vm.$ajax(paths.getVariousVacationControl),
                vm.$ajax(paths.findAll
            ).done((
                vacationControl: IVariousVacationControl,
                holidayRemainings: Array<HolidayRemaining>
            ) => {
                vm.vacationControl = vacationControl;
                if (!vacationControl || vacationControl.annualHolidaySetting == false) {
                    $('#rowYearlyHoliday').addClass("hidden");
                }

                if (!vacationControl || vacationControl.yearlyReservedSetting == false) {
                    $('#rowYearlyReserved').addClass("hidden");
                }

                if (!vacationControl || vacationControl.substituteHolidaySetting == false) {
                    $('#rowSubstituteHoliday').addClass("hidden");
                }

                if (!vacationControl || vacationControl.pauseItemHolidaySetting == false) {
                    $('#rowPauseItemHoliday').addClass("hidden");
                }

                if (!vacationControl || vacationControl.childNursingSetting == false) {
                    $('#rowChildNursingHoliday').addClass("hidden");
                }

                if (!vacationControl || vacationControl.nursingCareSetting == false) {
                    $('#rowNursingCareHoliday').addClass("hidden");
                }

                if (!vacationControl || vacationControl.listSpecialHoliday.length == 0) {
                    $('#rowSpecialHoliday').addClass("hidden");
                }
                else {
                    for (let i = 1; i < 21; i++) {
                        let item = _.find(vacationControl.listSpecialHoliday, x => { return x.specialHolidayCode == i; });
                        if (item) {
                            vm.allSpecialHolidays.push(new SpecialHoliday({ specialHolidayCode: i, specialHolidayName: item.specialHolidayName, enable: true }));
                            vm.listSpecialHolidayEnable.push(i);
                        }
                        else {
                            vm.allSpecialHolidays.push(new SpecialHoliday({ specialHolidayCode: i, specialHolidayName: "", enable: false }));
                        }
                    }
                }

                if (holidayRemainings && holidayRemainings.length) {
                    holidayRemainings = _.sortBy(holidayRemainings, ['cd']);

                    let _rsList: Array<HolidayRemaining> = _.map(holidayRemainings, result => {
                        return new HolidayRemaining(result);
                    });

                    vm.lstHolidays(_rsList);
                    if (!vm.currentCode()) {
                        vm.currentCode(_rsList[0].cd());
                    }
                    else {
                        vm.currentCode.valueHasMutated();
                    }
                }

                dfd.resolve();
            }).fail((res) => {
                vm.$dialog.error({messageId: res.messageId});
                dfd.reject();
            }).always(() => {
                vm.$blockui("hide");
            }));
            return dfd.promise();
        }
        
        setData() {
            const vm = this;
            let vacationControl = vm.vacationControl;
            if (!vacationControl || vacationControl.annualHolidaySetting == false) {
                vm.currentHoliday().yearlyHoliday(false);
            }

            if (!vacationControl || vacationControl.yearlyReservedSetting == false) {
                vm.currentHoliday().yearlyReserved(false);
            }

            if (!vacationControl || vacationControl.substituteHolidaySetting == false) {
                vm.currentHoliday().outputItemSubstitute(false);
            }

            if (!vacationControl || vacationControl.pauseItemHolidaySetting == false) {
                vm.currentHoliday().pauseItem(false);
            }

            if (!vacationControl || vacationControl.childNursingSetting == false) {
                vm.currentHoliday().childNursingLeave(false);
            }

            if (!vacationControl || vacationControl.nursingCareSetting == false) {
                vm.currentHoliday().nursingLeave(false);
            }

            if (!vacationControl || vacationControl.listSpecialHoliday.length == 0) {
                vm.currentHoliday().listSpecialHoliday([]);
            }
            else {
                let listSpecialHoliday: Array<number> = [];
                _.forEach(vm.currentHoliday().listSpecialHoliday(), function(item) {
                    if (_.find(vacationControl.listSpecialHoliday, x => { return x.specialHolidayCode == item; }))
                        listSpecialHoliday.push(item);
                });
                vm.currentHoliday().listSpecialHoliday(listSpecialHoliday);
            }
        }
        
        getAllData(code?: string): JQueryPromise<any> {
            const vm = this;
            let dfd = $.Deferred();
            vm.$blockui("show");
            vm.lstHolidays.removeAll();
            vm.$ajax(paths.findAll).done((data: Array<HolidayRemaining>) => {
                if (data && data.length) {
                    data = _.sortBy(data, ['cd']);
                    let _rsList: Array<HolidayRemaining> = _.map(data, result => {
                        return new HolidayRemaining(result);
                    });
                    if (code) {
                        if (code == vm.currentCode())
                            vm.currentCode.valueHasMutated();
                        else
                            vm.currentCode(code);
                    }
                    else {
                        vm.currentCode(_rsList[0].cd());
                    }
                    vm.lstHolidays(_rsList);
                }
                else {
                    vm.$errors("clear");
                    vm.currentCode('');
                    vm.currentHoliday(new HolidayRemaining(null));
                    vm.isNewMode(true);
                }
                vm.$blockui("hide");
                dfd.resolve();
            }).fail((res) => {
                vm.$dialog.error({messageId: res.messageId});
            }).always(() => {
                vm.$blockui("hide");
                dfd.reject();
            });
            return dfd.promise();
        }

        /**
         * create a new Holiday Remaining
         * 
         */
        settingCreateMode() {
            const vm = this;
            // clear selected holiday set
            vm.currentCode('');
            // clear holiday setting
            vm.currentHoliday(new HolidayRemaining(null));
            // Set new mode
            vm.isNewMode(true);
            //focus
            vm.setFocus();
            vm.setSpecialHolidayStyle();
            vm.$errors("clear")
        }

        setFocus() {
            const vm = this;
            if (vm.isNewMode()) {
                $('#holidayCode').focus();
            } else {
                $('#holidayName').focus();
            }
        }

        setSpecialHolidayStyle() {
            $("#rowSpecialHoliday > td > div > div > label > span.label").addClass("label-checkbox limited-label");
        }

        /**
         * Save
         */
        saveHoliday() {
            const vm = this,
                currentHoliday: HolidayRemaining = vm.currentHoliday();
            $('.nts-input').trigger("validate");
            if (nts.uk.ui.errors.hasError() === false) {

                vm.$blockui("show");

                if (vm.isNewMode()) {
                    // create new holiday
                    vm.$ajax(paths.addHoliday(ko.toJS(currentHoliday)).done(() => {
                        vm.$ajax(paths.getAllData(currentHoliday.cd()) .done(() => {
                            vm.$dialog.info({ messageId: "Msg_15" }).then(() => {
                                vm.setFocus();
                            });
                        }));
                    }).fail((error: any) => {
                        if (error.messageId == 'Msg_880') {
                            vm.$dialog.error({ messageId: error.messageId });
                        } else if (error.messageId == 'Msg_3') {
                            $('#holidayCode').ntsError('set', error);
                            $('#holidayName').focus();
                            vm.$dialog.error({ messageId: error.messageId });
                        }
                    }).always(() => {
                        vm.setFocus();
                        vm.$blockui("hide");
                    }));
                } else {
                    // update
                    vm.$ajax(paths.updateHoliday(ko.toJS(currentHoliday)).done(() => {
                        vm.$ajax(paths.getAllData(currentHoliday.cd()) .done(() => {
                            vm.$dialog.info({ messageId: "Msg_15" }).then(() => {
                                vm.setFocus();
                            });
                        }));
                    }).fail((error : any) => {
                        if (error.messageId == 'Msg_880') {
                            vm.$dialog.error({ messageId: error.messageId });
                        } else {
                            vm.$dialog.error({ messageId: error.messageId });
                        }
                    }).always(() => {
                        vm.setFocus();
                        vm.$blockui("hide");
                    }));
                }
            }
        }

        /**
         * delete the Holoday Remaining
         */
        deleteHoliday() {
            const vm = this,
                lstHolidays = vm.lstHolidays,
                currentHoliday: HolidayRemaining = vm.currentHoliday();
            vm.$blockui("show");
            vm.$dialog.confirm({ messageId: "Msg_18" }).then((result: 'no' | 'yes' | 'cancel') => {
                if (result === 'yes') {
                    if (currentHoliday.cd()) {
                        let index: number = _.findIndex(lstHolidays(), function (x) {
                            return x.cd() == currentHoliday.cd()
                        });
                        vm.$ajax(paths.removeHoliday(ko.toJS(currentHoliday)).done(() => {
                            vm.$ajax(paths.getAllData(vm.currentCode()).done(() => {
                                vm.setSpecialHolidayStyle();
                                vm.$dialog.info({messageId: "Msg_16"}).then(() => {
                                    if (vm.lstHolidays().length == 0) {
                                        vm.currentCode('');
                                        vm.isNewMode(true);
                                        vm.setFocus();
                                        vm.$errors("clear");
                                    } else {
                                        if (index == vm.lstHolidays().length) {
                                            vm.currentCode(vm.lstHolidays()[index - 1].cd());
                                        } else {
                                            vm.currentCode(vm.lstHolidays()[index].cd());
                                        }
                                    }
                                });
                            }));

                        }).fail((error : any) => {
                            vm.$dialog.error({messageId: error.messageId});
                            vm.$blockui("hide");
                        }).always(() => {
                            vm.$blockui("hide");
                        }));
                    } else {
                        vm.$blockui("hide");
                    }
                }
            }).then(() => {
                vm.$errors("clear");
                vm.$blockui("hide");
            });
        }

        /**
         * close the dialog
         */
        closeDialog() {
            const vm = this;
            setShared('KDR001B2A_cd', vm.currentCode());
            nts.uk.ui.windows.close()
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
        outputHolidayForward: KnockoutObservable<boolean>;
        /**
         * 公休月度残を出力する
         */
        monthlyPublic: KnockoutObservable<boolean>;
        /**
         * 公休の項目を出力する
         */
        outputItemsHolidays: KnockoutObservable<boolean>;

        /**
         * childNursingLeave
         */
        childNursingLeave: KnockoutObservable<boolean>;

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
        unDigestedPause: KnockoutObservable<boolean>;
        /**
         * 振休の項目を出力する
         */
        pauseItem: KnockoutObservable<boolean>;

        /**
         * 積立年休の項目を出力する
         */
        yearlyReserved: KnockoutObservable<boolean>;

        listSpecialHoliday: KnockoutObservableArray<number>;

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
            self.outputHolidayForward = ko.observable(param ? param.outputholidayforward || false : false);
            self.monthlyPublic = ko.observable(param ? param.monthlyPublic || false : false);
            self.outputItemsHolidays = ko.observable(param ? param.outputitemsholidays || false : false);
            self.childNursingLeave = ko.observable(param ? param.childNursingLeave || false : false);
            self.yearlyHoliday = ko.observable(param ? param.yearlyHoliday || false : false);
            self.insideHours = ko.observable(param ? param.insideHours || false : false);
            self.insideHalfDay = ko.observable(param ? param.insideHalfDay || false : false);
            self.numberRemainingPause = ko.observable(param ? param.numberRemainingPause || false : false);
            self.unDigestedPause = ko.observable(param ? param.unDigestedPause || false : false);
            self.pauseItem = ko.observable(param ? param.pauseItem || false : false);
            self.yearlyReserved = ko.observable(param ? param.yearlyReserved || false : false);
            self.listSpecialHoliday = ko.observableArray(param ? param.listSpecialHoliday || [] : []);

            self.outputItemSubstitute.subscribe((isCheck) => {
                if (isCheck === false) {
                    self.representSubstitute(false);
                    self.remainingChargeSubstitute(false);
                }
            });

            self.pauseItem.subscribe((isCheck) => {
                if (isCheck === false) {
                    self.unDigestedPause(false);
                    self.numberRemainingPause(false);
                }
            });
        }
    }

    export class SpecialHoliday {

        /*特別休暇コード*/
        specialHolidayCode: KnockoutObservable<number>;

        /*特別休暇名称*/
        specialHolidayName: KnockoutObservable<string>;

        enable: KnockoutObservable<boolean>;

        constructor(param: any) {
            let self = this;
            self.specialHolidayCode = ko.observable(param ? param.specialHolidayCode || null : null);
            self.specialHolidayName = ko.observable(param ? param.specialHolidayName || '' : '');
            self.enable = ko.observable(param ? param.enable || false : false);
        }
    }

    export interface ISpecialHoliday {

        /*特別休暇コード*/
        specialHolidayCode: number;

        /*特別休暇名称*/
        specialHolidayName: string;
    }

    export interface IVariousVacationControl {

        annualHolidaySetting: boolean;
        yearlyReservedSetting: boolean;
        substituteHolidaySetting: boolean;
        pauseItemHolidaySetting: boolean;
        childNursingSetting: boolean;
        nursingCareSetting: boolean;
        listSpecialHoliday: Array<ISpecialHoliday>;
    }
}