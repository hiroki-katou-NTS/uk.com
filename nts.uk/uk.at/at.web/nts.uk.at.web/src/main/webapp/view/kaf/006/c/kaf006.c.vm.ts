module nts.uk.at.view.kaf006.c.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import dialog = nts.uk.ui.dialog;
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
	import CommonProcess = nts.uk.at.view.kaf000.shr.viewmodel.CommonProcess;

    @bean()
    export class KAF006CViewModel extends ko.ViewModel {
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
        appDateTmp: KnockoutObservable<string> = ko.observable(null);
        dateRangeTmp: KnockoutObservable<DateRange> = ko.observable(new DateRange({ startDate: null, endDate: null }));
        indexApprover: number = 0;
        approvalRootState: KnockoutObservableArray<any>;

        constructor() {
            super();
            let vm = this;
            vm.approvalRootState = ko.observableArray([]);
            
            vm.appDispInfoStartupOutput.subscribe(value => {
                if(!_.isEmpty(value.appDispInfoWithDateOutput.opListApprovalPhaseState)) {
                    vm.approvalRootState(ko.mapping.fromJS(value.appDispInfoWithDateOutput.opListApprovalPhaseState)());
                } else {
                    vm.approvalRootState([]);
                }
            });

            const params: KAF006CParam = getShared("KAF006C_PARAMS");

            if (params) {
                vm.applyForleave = params.applyForLeave;
                vm.application = params.appAbsenceStartInfoOutput.appDispInfoStartupOutput.appDetailScreenInfo.application;
                vm.appType = vm.application.appType;

                let startDate = vm.application.opAppStartDate;
                let endDate = vm.application.opAppEndDate;
                if (startDate === endDate) {
                    vm.dispMultDate(false);
                    vm.appDateTmp(startDate);
                } else {
                    vm.dispMultDate(true);
                    vm.dateRangeTmp().startDate = startDate;
                    vm.dateRangeTmp().endDate = endDate;
                    // vm.dateRange.valueHasMutated();
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
                if (!vm.dispMultDate() && !$('#appDate').ntsError('hasError')) {
                    let startDate = vm.appDate();
                    let endDate = vm.appDate();
    
                    let holidayDates = [];
                    if (startDate) {
                        holidayDates.push(nts.uk.time.formatDate(new Date(startDate), "yyyy-MM-dd"));
                    }
                    if (endDate && endDate !== startDate) {
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
                                vm.indexApprover = 0;
                                vm.appDispInfoStartupOutput(success.appDispInfoStartupOutput);
                                vm.appAbsenceStartInfoOutput.appDispInfoStartupOutput = success.appDispInfoStartupOutput;
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
                            $("#appDate").focus();
                            block.clear();
                        });
                }
            });
            
            vm.dateRange.subscribe((range) => {
                if (vm.dispMultDate && !$('#dateRange').ntsError('hasError')) {
                    let startDate = range.startDate;
                    let endDate = range.endDate;
    
                    let holidayDates = [];
                    if (startDate) {
                        holidayDates.push(nts.uk.time.formatDate(new Date(startDate), "yyyy-MM-dd"));
                    }
                    if (endDate && startDate !== endDate) {
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
                                vm.appAbsenceStartInfoOutput.appDispInfoStartupOutput = success.appDispInfoStartupOutput;
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
                            $("#dateRange .ntsStartDatePicker").focus();
                            block.clear();
                        });
                }
            });

            if (!vm.dispMultDate()) {
                vm.appDate(vm.appDateTmp());
            } else {
                vm.dateRange().startDate = vm.dateRangeTmp().startDate;
                vm.dateRange().endDate = vm.dateRangeTmp().endDate;
                vm.dateRange.valueHasMutated();
            }
        }

        register() {
            const vm = this;

                
                let holidayAppDates: any[] = [];
    
                let newApplication = _.clone(vm.application);
                newApplication.appDate = vm.dispMultDate() ? moment(new Date(vm.dateRange().startDate)).format("YYYY/MM/DD") : moment(new Date(vm.appDate())).format("YYYY/MM/DD");
                newApplication.opAppStartDate = vm.dispMultDate() ? moment(new Date(vm.dateRange().startDate)).format("YYYY/MM/DD") : moment(new Date(vm.appDate())).format("YYYY/MM/DD");
                newApplication.opAppEndDate = vm.dispMultDate() ? moment(new Date(vm.dateRange().endDate)).format("YYYY/MM/DD") : moment(new Date(vm.appDate())).format("YYYY/MM/DD");
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
                commandCheck.newApplyForLeave.vacationInfo.info.datePeriod = {
                    startDate: vm.application.opAppStartDate,
                    endDate: vm.application.opAppEndDate
                }
                commandRegister.newApplyForLeave.vacationInfo.info.datePeriod = {
                    startDate: vm.application.opAppStartDate,
                    endDate: vm.application.opAppEndDate
                }
    
                block.invisible();
                vm.validate().then((valid) => {
                    if (valid) {
                        return service.checkBeforeRegisterHolidayDates(commandCheck);
                    }
                }).done((success) => {
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
                }).done((result) => {
                    if (result) {
                        return vm.$dialog.info({ messageId: "Msg_15"}).then(() => {
							service.reflectApp(result.reflectAppIdLst);
							return CommonProcess.handleMailResult(result, vm).then(() => {
								nts.uk.ui.windows.setShared('KAF006C_RESULT', { appID: result.appIDLst[0] });
								vm.closeDialog();
	                            return true;
							});
                        });	
                    }
                }).fail((error) => {
                    if (error) {
                        if (error.messageId === "Msg_1715" || error.messageId === "Msg_1521") {
                            nts.uk.ui.dialog.error({ messageId: error.messageId, messageParams: [error.parameterIds[0], error.parameterIds[1]] });
                        } else {
                            nts.uk.ui.dialog.error({ messageId: error.messageId, messageParams: error.parameterIds });
                        }
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

        validate(): JQueryPromise<any> {
            const vm = this;
            let dfd = $.Deferred();
            let dfds = [];
            if (vm.dispListReasons() && vm.requireLabel1()) {
              dfds.push(vm.$validate("#reasonLst")) ;
            } else dfds.push($.Deferred().resolve(true));

            if (vm.dispReason() && vm.requireLabel2()) {
                dfds.push(vm.$validate("#reasonTxt")) ;
            } else dfds.push($.Deferred().resolve(true));

            $.when.apply($, dfds).done(function (d1: any, d2: any) {
                 dfd.resolve(d1 && d2);
            });

            return dfd.promise();
        }

        /**
         * Component 6
         */
         isFirstIndexFrame(loopPhase: any, loopFrame: any, loopApprover: any) {
            if(_.size(loopFrame.listApprover()) > 1) {
                return _.findIndex(loopFrame.listApprover(), o => o == loopApprover) == 0;
            }
            let firstIndex = _.chain(loopPhase.listApprovalFrame()).filter(x => _.size(x.listApprover()) > 0).orderBy(x => x.frameOrder()).first().value().frameOrder();
            let approver: any = _.find(loopPhase.listApprovalFrame(), o => o == loopFrame);
            if(approver) {
                return approver.frameOrder() == firstIndex;
            }
            return false;
        }

        getFrameIndex(loopPhase: any, loopFrame: any, loopApprover: any) {
            if(_.size(loopFrame.listApprover()) > 1) {
                return _.findIndex(loopFrame.listApprover(), o => o == loopApprover);
            }
            return loopFrame.frameOrder();
        }

        frameCount(listFrame: any) {
            const vm = this;
            let listExist = _.filter(listFrame, x => _.size(x.listApprover()) > 0);
            if(_.size(listExist) > 1) {
                return _.size(listExist);
            }
            return _.chain(listExist).map(o => vm.approverCount(o.listApprover())).value()[0];
        }

        approverCount(listApprover: any) {
            return _.chain(listApprover).countBy().values().value()[0];
        }

        getApproverAtr(approver: any) {
            if(approver.approvalAtrName() !='未承認'){
                if(approver.agentName().length > 0){
                    if(approver.agentMail().length > 0){
                        return approver.agentName() + '(@)';
                    } else {
                        return approver.agentName();
                    }
                } else {
                    if(approver.approverMail().length > 0){
                        return approver.approverName() + '(@)';
                    } else {
                        return approver.approverName();
                    }
                }
            } else {
                var s = '';
                s = s + approver.approverName();
                if(approver.approverMail().length > 0){
                    s = s + '(@)';
                }
                if(approver.representerName().length > 0){
                    if(approver.representerMail().length > 0){
                        s = s + '(' + approver.representerName() + '(@))';
                    } else {
                        s = s + '(' + approver.representerName() + ')';
                    }
                }
                return s;
            }
        }

        getPhaseLabel(phaseOrder: any) {
            const vm = this;
            switch(phaseOrder) {
                case 1: return vm.$i18n("KAF000_4");
                case 2: return vm.$i18n("KAF000_5");
                case 3: return vm.$i18n("KAF000_6");
                case 4: return vm.$i18n("KAF000_7");
                case 5: return vm.$i18n("KAF000_8");
                default: return "";
            }
        }

        getApproverLabelByIndex() {
			const vm = this;
			vm.indexApprover++;
			return vm.$i18n("KAF000_9",[vm.indexApprover+'']);
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