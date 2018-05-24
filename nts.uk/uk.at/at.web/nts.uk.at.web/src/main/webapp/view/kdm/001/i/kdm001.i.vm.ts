module nts.uk.at.view.kdm001.i.viewmodel {
    import model = kdm001.share.model;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import errors = nts.uk.ui.errors;
    import dialog = nts.uk.ui.dialog;
    export class ScreenModel {
        employeeId: KnockoutObservable<string> = ko.observable('');
        workCode: KnockoutObservable<string> = ko.observable('');
        workplaceName: KnockoutObservable<string> = ko.observable('');
        employeeCode: KnockoutObservable<string> = ko.observable('');
        employeeName: KnockoutObservable<string> = ko.observable('');
        dayRemaining: KnockoutObservable<string> = ko.observable("0");
        checkedHoliday: KnockoutObservable<boolean> = ko.observable(false);;
        checkedSubHoliday: KnockoutObservable<boolean> = ko.observable(false);
        checkedSplit: KnockoutObservable<boolean> = ko.observable(false);
        dateHoliday: KnockoutObservable<string> = ko.observable('');
        duedateHoliday: KnockoutObservable<string> = ko.observable('');
        dateSubHoliday: KnockoutObservable<string> = ko.observable('');
        dateOptionSubHoliday: KnockoutObservable<string> = ko.observable('');
        dateOffOptionSubHoliday: KnockoutObservable<string> = ko.observable('');
        itemListHoliday: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getNumberOfDays());
        selectedCodeHoliday: KnockoutObservable<number> = ko.observable(null);
        itemListSubHoliday: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getNumberOfDays());
        selectedCodeSubHoliday: KnockoutObservable<number> = ko.observable(null);
        itemListOptionSubHoliday: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getNumberOfDays());
        selectedCodeOptionSubHoliday: KnockoutObservable<string> = ko.observable('');
        isOptionSubHolidayEnable: KnockoutObservable<boolean> = ko.observable(false);
        closureId: KnockoutObservable<number> = ko.observable(0);

        numberHoliday: KnockoutObservable<string> = ko.observable('');
        numberSubHoliday: KnockoutObservable<string> = ko.observable('');
        numberSplitHoliday: KnockoutObservable<string> = ko.observable('');
        totalDay: KnockoutObservable<number> = ko.observable(null);

        constructor() {
            let self = this;
            self.initScreen();
            self.checkedSplit.subscribe((v) => {
                if (!v) {
                    self.isOptionSubHolidayEnable(false);
                } else {
                    self.isOptionSubHolidayEnable(true);
                    self.selectedCodeOptionSubHoliday.subscribe((selectSplitHoli) => {
                        self.numberSplitHoliday((selectSplitHoli).toFixed(1));
                        self.totalDay(self.selectedCodeHoliday() - (self.selectedCodeSubHoliday() + parseFloat(self.numberSplitHoliday())));
                        self.dayRemaining(self.totalDay().toString());    
                    });
                }
            });

            self.selectedCodeHoliday.subscribe((selectHoli) => {
                self.numberHoliday((selectHoli).toFixed(1));
                self.totalDay(parseFloat(self.numberHoliday()) - self.selectedCodeSubHoliday());
                self.dayRemaining(self.totalDay().toString());     
            });

            self.selectedCodeSubHoliday.subscribe((selectSubHoli) => {
                self.numberSubHoliday((selectSubHoli).toFixed(1));
                self.totalDay(self.selectedCodeHoliday() - parseFloat(self.numberSubHoliday()));
                self.dayRemaining(self.totalDay().toString());     
            });

           


        }

        initScreen(): void {
            block.invisible();
            let self = this,
                info = getShared("KDM001_I_PARAMS");
            if (info) {
                self.workCode(info.selectedEmployee.workplaceCode);
                self.workplaceName(info.selectedEmployee.workplaceName);
                self.employeeId(info.selectedEmployee.employeeId);
                self.employeeCode(info.selectedEmployee.employeeCode);
                self.employeeName(info.selectedEmployee.employeeName);
                self.closureId(info.closure.closureId);
            }
            block.clear();



        }

        /**
         * closeDialog
         */

        public submitData() {
            let self = this;
            errors.clearAll();
            $(".ntsDatepicker").trigger("validate");
            if (!errors.hasError()) {
                block.invisible();
                let data = {
                    employeeId: self.employeeId(),
                    checkedHoliday: self.checkedHoliday(),
                    dateHoliday: moment.utc(self.dateHoliday(), 'YYYY/MM/DD').toISOString(),
                    selectedCodeHoliday: self.selectedCodeHoliday(),
                    duedateHoliday: moment.utc(self.duedateHoliday(), 'YYYY/MM/DD').toISOString(),
                    checkedSubHoliday: self.checkedSubHoliday(),
                    dateSubHoliday: moment.utc(self.dateSubHoliday(), 'YYYY/MM/DD').toISOString(),
                    selectedCodeSubHoliday: self.selectedCodeSubHoliday(),
                    checkedSplit: self.checkedSplit(),
                    dateOptionSubHoliday: moment.utc(self.dateOptionSubHoliday(), 'YYYY/MM/DD').toISOString(),
                    selectedCodeOptionSubHoliday: self.selectedCodeOptionSubHoliday(),
                    dayRemaining: self.dayRemaining(),
                    closureId: self.closureId()
                };

                console.log(data);
                service.add(data).done(result => {
                    if (result && result.length > 0) {
                        for (let errorId of result) {
                            if (errorId === "Msg_737_holiday") {
                                $('#I6_1').ntsError('set', { messageId: "Msg_737" });
                            }
                            if (errorId === "Msg_728") {
                                $('#I4').ntsError('set', { messageId: errorId });
                            }
                            if (errorId === "Msg_737_sub_holiday") {
                                $('#I11_1').ntsError('set', { messageId: "Msg_737" });
                            }
                        }
                        return;
                    }
                    //情報メッセージ　Msg_15 登録しました。を表示する。
                    dialog.info({ messageId: "Msg_15" }).then(() => {
                        nts.uk.ui.windows.close();
                    });
                }).fail(function(res: any) {

                });
                block.clear();
            }
        }

        public closeDialog() {
            nts.uk.ui.windows.close();
        }
    }


}

