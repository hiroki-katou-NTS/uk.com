module nts.uk.at.view.kaf006.c.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import dialog = nts.uk.ui.dialog;
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;

    export class KAF006CViewModel {
        dispMultDate: KnockoutObservable<boolean> = ko.observable(true);
        dispListReasons: KnockoutObservable<boolean> = ko.observable(true);
        dispReason: KnockoutObservable<boolean> = ko.observable(true);
        appType: any = 1;
        application: any = null;
        appAbsenceStartInfoOutput: any = null;
        appDispInfoStartupOutput: any = ko.observable(null);
        applyForleave: any;
        requireLabel1: KnockoutObservable<boolean> = ko.observable(true);
        requireLabel2: KnockoutObservable<boolean> = ko.observable(true);
        reasonLst: any[] = [];
        selectedReason: KnockoutObservable<any> = ko.observable(null);
        appReason: KnockoutObservable<string> = ko.observable(null);
        appDate: KnockoutObservable<string> = ko.observable(null);
        dateRange: KnockoutObservable<DateRange> = ko.observable(new DateRange({ startDate: null, endDate: null }));

        constructor() {
            let vm = this;

            const params: KAF006CParam = getShared("KAF006C_PARAMS");

            if (params) {
                vm.applyForleave = params.applyForLeave;
                vm.application = params.appAbsenceStartInfoOutput.appDispInfoStartupOutput.appDetailScreenInfo.application;
                vm.appType = vm.application.appType;

                let startDate = vm.application.opAppStartDate;
                let endDate = vm.application.opAppEndDate;
                if (startDate === endDate) {
                    vm.dispMultDate(false);
                    vm.appDate(startDate);
                } else {
                    vm.dispMultDate(true);
                    vm.dateRange().startDate = startDate;
                    vm.dateRange().endDate = endDate;
                    vm.dateRange.valueHasMutated();
                }

                vm.appAbsenceStartInfoOutput = params.appAbsenceStartInfoOutput;
                vm.appDispInfoStartupOutput(params.appAbsenceStartInfoOutput.appDispInfoStartupOutput);
                vm.reasonLst = vm.appDispInfoStartupOutput().appDispInfoNoDateOutput.reasonTypeItemLst;
                vm.selectedReason(vm.application.opAppStandardReasonCD);
                vm.appReason(vm.application.opAppReason);
                vm.requireLabel1(vm.appDispInfoStartupOutput().appDispInfoNoDateOutput.applicationSetting.appLimitSetting.standardReasonRequired);
                vm.requireLabel2(vm.appDispInfoStartupOutput().appDispInfoNoDateOutput.applicationSetting.appLimitSetting.requiredAppReason);
                vm.dispListReasons(vm.appDispInfoStartupOutput().appDispInfoNoDateOutput.displayStandardReason === 0 ? false : true);
                vm.dispReason(vm.appDispInfoStartupOutput().appDispInfoNoDateOutput.displayAppReason === 0 ? false : true)
            }

            vm.dispMultDate.subscribe(() => {
                const vm = this;
                if (!vm.dispMultDate()) {
                    vm.appDate(vm.dateRange().startDate);
                } else {
                    vm.dateRange().startDate = vm.appDate();
                    vm.dateRange().endDate = vm.appDate();
                    vm.dateRange.valueHasMutated();
                }
            });

            vm.appDate.subscribe(() => {
                if (!vm.dispMultDate()) {
                    let startDate = vm.appDate();
                    let endDate = vm.appDate();
    
                    let holidayDates = [];
                    if (startDate) {
                        holidayDates.push(nts.uk.time.formatDate(new Date(startDate), "yyyy-MM-dd"));
                    }
                    if (endDate) {
                        holidayDates.push(nts.uk.time.formatDate(new Date(endDate), "yyyy-MM-dd"));
                    }

                    if (_.isEmpty(holidayDates)) {
                        return;
                    }
    
                    let command = {
                        holidayDates: holidayDates,
                        appAbsenceStartInfoDto: vm.appAbsenceStartInfoOutput
                    }
                    command.appAbsenceStartInfoDto.leaveComDayOffManas = _.map(command.appAbsenceStartInfoDto.leaveComDayOffManas, (x: any) => {
                        x.dateOfUse = new Date(x.dateOfUse).toISOString();
                        x.outbreakDay = new Date(x.outbreakDay).toISOString();
                        return x;
                    });
                    command.appAbsenceStartInfoDto.payoutSubofHDManas = _.map(command.appAbsenceStartInfoDto.payoutSubofHDManas, (x: any) => {
                        x.dateOfUse = new Date(x.dateOfUse).toISOString();
                        x.outbreakDay = new Date(x.outbreakDay).toISOString();
                        return x;
                    });
    
                    block.invisible();
                    service.changeHolidayDates(command)
                        .done((success) => {
                            if (success) {
                                vm.appDispInfoStartupOutput(success.appDispInfoStartupOutput);
                                return true;
                            }
                        }).then((data) => {
                            if (data) {
    
                                return true;
                            }
                        }).fail((error) => {
                            if (error) {
                                nts.uk.ui.dialog.error({ messageId: error.messageId });
                            }
                        }).always(() => {
                            block.clear();
                        });
                }
            });
            
            vm.dateRange.subscribe((range) => {
                if (vm.dispMultDate) {
                    let startDate = range.startDate;
                    let endDate = range.endDate;
    
                    let holidayDates = [];
                    if (startDate) {
                        holidayDates.push(nts.uk.time.formatDate(new Date(startDate), "yyyy-MM-dd"));
                    }
                    if (endDate) {
                        holidayDates.push(nts.uk.time.formatDate(new Date(endDate), "yyyy-MM-dd"));
                    }

                    if (_.isEmpty(holidayDates)) {
                        return;
                    }
    
                    let command = {
                        holidayDates: holidayDates,
                        appAbsenceStartInfoDto: vm.appAbsenceStartInfoOutput
                    }
                    command.appAbsenceStartInfoDto.leaveComDayOffManas = _.map(command.appAbsenceStartInfoDto.leaveComDayOffManas, (x: any) => {
                        x.dateOfUse = new Date(x.dateOfUse).toISOString();
                        x.outbreakDay = new Date(x.outbreakDay).toISOString();
                        return x;
                    });
                    command.appAbsenceStartInfoDto.payoutSubofHDManas = _.map(command.appAbsenceStartInfoDto.payoutSubofHDManas, (x: any) => {
                        x.dateOfUse = new Date(x.dateOfUse).toISOString();
                        x.outbreakDay = new Date(x.outbreakDay).toISOString();
                        return x;
                    });
    
                    block.invisible();
                    service.changeHolidayDates(command)
                        .done((success) => {
                            if (success) {
                                vm.appDispInfoStartupOutput(success.appDispInfoStartupOutput);
                                return true;
                            }
                        }).then((data) => {
                            if (data) {
    
                                return true;
                            }
                        }).fail((error) => {
                            if (error) {
                                nts.uk.ui.dialog.error({ messageId: error.messageId });
                            }
                        }).always(() => {
                            block.clear();
                        });
                }
            });
        }

        startPage(): JQueryPromise<any> {
            const vm = this;
            let dfd = $.Deferred();

            dfd.resolve();
            return dfd.promise();
        }

        register() {
            const vm = this;

            let holidayAppDates: any[] = [];

            let newApplication = _.clone(vm.application);
            newApplication.opAppStartDate = vm.dispMultDate() ? vm.dateRange().startDate : vm.appDate();
            newApplication.opAppEndDate = vm.dispMultDate() ? vm.dateRange().endDate : vm.appDate();
            newApplication.opAppStandardReasonCD = vm.selectedReason();
            newApplication.opAppReason = vm.appReason();

            let commandCheck = {
                oldApplication: vm.application,
                newApplication: newApplication,
                appAbsenceStartInfoDto: vm.appAbsenceStartInfoOutput,
                originApplyForLeave: vm.applyForleave,
                newApplyForLeave: vm.applyForleave
            };
            let commandRegister = {
                oldApplication: vm.application,
                newApplication: newApplication,
                newApplyForLeave: vm.applyForleave,
                originApplyForLeave: vm.applyForleave, 
                holidayDates: holidayAppDates,
                appAbsenceStartInfoDto: vm.appAbsenceStartInfoOutput
            };
            block.invisible();
            service.checkBeforeRegisterHolidayDates(commandCheck)
                .done((success) => {
                    if (success) {
                        holidayAppDates = success.holidayDateLst;
                        commandRegister.holidayDates = holidayAppDates;
                        // xử lý confirmMsg
                        return vm.handleConfirmMessage(success.confirmMsgLst);
                    }
                }).then((data) => {
                    if (data) {

                        return true;
                    }
                }).then((data) => {
                    if (data) {
                        return service.registerHolidayDates(commandRegister);
                    }
                }).fail((error) => {
                    if (error) {
                        nts.uk.ui.dialog.error({ messageId: error.messageId });
                    }
                }).always(() => {
                    block.clear();
                });
        }

        handleConfirmMessage(listMes: any): any {
			const vm = this;
			if(_.isEmpty(listMes)) {
				return $.Deferred().resolve(true);
			}
			let msg = listMes[0];

			return nts.uk.ui.dialog.confirm({ messageId: msg.msgID, messageParams: msg.paramLst })
			.then((value: any) => {
				if (value === 'yes') {
					return vm.handleConfirmMessage(_.drop(listMes));
				} else {
					return $.Deferred().resolve(false);
				}
			});
		}

        closeDialog() {
            nts.uk.ui.windows.close();
        }
    }

    export interface IDateRange {
        startDate: string;

        endDate: string;
    }

    export class DateRange {
        startDate: string;

        endDate: string;

        constructor (range: IDateRange) {
            this.startDate = range.startDate,
            this.endDate = range.endDate
        }
    }

    export class KAF006CParam {
        // 休暇申請起動時の表示情報
        appAbsenceStartInfoOutput: any;

        // 元の休暇申請
        applyForLeave: any;
    }
}