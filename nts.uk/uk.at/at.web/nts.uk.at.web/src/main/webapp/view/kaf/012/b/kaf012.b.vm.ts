module nts.uk.at.view.kaf012.b.viewmodel {
    import Application = nts.uk.at.view.kaf000.shr.viewmodel.Application;
    import AppType = nts.uk.at.view.kaf000.shr.viewmodel.model.AppType;
	import PrintContentOfEachAppDto = nts.uk.at.view.kaf000.shr.viewmodel.PrintContentOfEachAppDto;
    import DataModel = nts.uk.at.view.kaf012.shr.viewmodel2.DataModel;
    import AppTimeType = nts.uk.at.view.kaf012.shr.viewmodel2.AppTimeType;
    import LeaveType = nts.uk.at.view.kaf012.shr.viewmodel1.LeaveType;
	import CommonProcess = nts.uk.at.view.kaf000.shr.viewmodel.CommonProcess;

    const API = {
        checkRegister: "at/request/application/timeLeave/checkBeforeRegister",
        updateApplication: "at/request/application/timeLeave/update",
        getDetail: "at/request/application/timeLeave/init"
    };

    const template = `
        <div>
            <div data-bind="component: { name: 'kaf000-b-component1', 
                                        params: {
                                            appType: appType,
                                            appDispInfoStartupOutput: appDispInfoStartupOutput	
                                        } }"></div>
            <div data-bind="component: { name: 'kaf000-b-component2', 
                                        params: {
                                            appType: appType,
                                            appDispInfoStartupOutput: appDispInfoStartupOutput
                                        } }"></div>
            <div data-bind="component: { name: 'kaf000-b-component3', 
                                        params: {
                                            appType: appType,
                                            approvalReason: approvalReason,
                                            appDispInfoStartupOutput: appDispInfoStartupOutput
                                        } }"></div>
            <div data-bind="component: { name: 'kaf012-share-component1',
                                                    params: {
                                                        reflectSetting: reflectSetting,
                                                        timeLeaveManagement: timeLeaveManagement,
                                                        timeLeaveRemaining: timeLeaveRemaining,
                                                        leaveType: leaveType,
                                                        application: application,
                                                        specialLeaveFrame: specialLeaveFrame
                                                    }}"/>
            <div class="table">
                <div class="cell" style="min-width: 825px; padding-right: 10px;">
                    <div data-bind="component: { name: 'kaf000-b-component4',
							params: {
								appType: appType,
								application: application,
								appDispInfoStartupOutput: appDispInfoStartupOutput
							} }"></div>
                    <div data-bind="component: { name: 'kaf000-b-component5', 
                                                params: {
                                                    appType: appType,
                                                    application: application,
                                                    appDispInfoStartupOutput: appDispInfoStartupOutput
                                                } }"></div>
                    <div data-bind="component: { name: 'kaf000-b-component6', 
                                                params: {
                                                    appType: appType,
                                                    application: application,
                                                    appDispInfoStartupOutput: appDispInfoStartupOutput
                                                } }"></div>
                    <div data-bind="component: { name: 'kaf012-share-component2',
                                        params: {
                                            reflectSetting: reflectSetting,
                                            timeLeaveManagement: timeLeaveManagement,
                                            timeLeaveRemaining: timeLeaveRemaining,
                                            leaveType: leaveType,
                                            appDispInfoStartupOutput: appDispInfoStartupOutput,
                                            application: application,
                                            applyTimeData: applyTimeData,
                                            specialLeaveFrame: specialLeaveFrame,
                                            eventCalc: eventCalc
                                        }}"/>
                    <div data-bind="component: { name: 'kaf000-b-component7', 
                                                params: {
                                                    appType: appType,
                                                    application: application,
                                                    appDispInfoStartupOutput: appDispInfoStartupOutput
                                                } }"></div>
                    <div data-bind="component: { name: 'kaf000-b-component8', 
                                                params: {
                                                    appType: appType,
                                                    appDispInfoStartupOutput: appDispInfoStartupOutput
                                                } }"></div>
                </div>
                <div class="cell" style="position: absolute;" data-bind="component: { name: 'kaf000-b-component9',
							params: {
								appType: appType,
								application: application,
								appDispInfoStartupOutput: $vm.appDispInfoStartupOutput
							} }"></div>
            </div>
        </div>
    `
    @component({
        name: 'kaf012-b',
        template: template
    })
    class Kaf012BViewModel extends ko.ViewModel {

		appType: KnockoutObservable<number> = ko.observable(AppType.ANNUAL_HOLIDAY_APPLICATION);
        appDispInfoStartupOutput: KnockoutObservable<any>;
        approvalReason: KnockoutObservable<string>;
        printContentOfEachAppDto: KnockoutObservable<PrintContentOfEachAppDto>;
        application: KnockoutObservable<Application>;
        reflectSetting: KnockoutObservable<any> = ko.observable(null);
        timeLeaveManagement: KnockoutObservable<any> = ko.observable(null);
        timeLeaveRemaining: KnockoutObservable<any> = ko.observable(null);
        leaveType: KnockoutObservable<number> = ko.observable(null);

        applyTimeData: KnockoutObservableArray<DataModel> = ko.observableArray([]);
        specialLeaveFrame: KnockoutObservable<number> = ko.observable(null);

        childCalcEvent: () => any;
        eventCalc: any;

        created(
            params: {
				appType: any,
				application: any,
				printContentOfEachAppDto: PrintContentOfEachAppDto,
            	approvalReason: any,
                appDispInfoStartupOutput: any,
                eventUpdate: (evt: () => void ) => void,
				eventReload: (evt: () => void) => void
            }
        ) {
            const vm = this;
            vm.printContentOfEachAppDto = ko.observable(params.printContentOfEachAppDto);
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            vm.application = params.application;
			vm.appType = params.appType;
			vm.approvalReason = params.approvalReason;
            for (let i = 0; i < 5; i++) {
                vm.applyTimeData.push(new DataModel(i, vm.reflectSetting, vm.appDispInfoStartupOutput, vm.application));
            }
            vm.getAppData();

            // gui event con ra viewmodel cha
            // nhớ dùng bind(vm) để ngữ cảnh lúc thực thi
            // luôn là component
            params.eventUpdate(vm.update.bind(vm));
			params.eventReload(vm.reload.bind(vm));

			vm.eventCalc = function(a: any) { vm.getChildCalcEvent.apply(vm, [a]) };
        }

		reload() {
			const vm = this;
			if(vm.appType() === AppType.ANNUAL_HOLIDAY_APPLICATION) {
			    // clear old data
                vm.applyTimeData().forEach(row => {
                    row.timeZones.forEach(z => {
                        z.startTime(null);
                        z.endTime(null);
                    });
                    row.applyTime.forEach(i => {
                        i.substituteAppTime(0);
                        i.childCareAppTime(0);
                        i.careAppTime(0);
                        i.super60AppTime(0);
                        i.annualAppTime(0);
                        i.specialAppTime(0);
                    })
                });
				vm.getAppData();
			}
		}

        getAppData() {
            let vm = this;
            vm.$blockui('show');
            return vm.$ajax(API.getDetail, {
                appId: vm.application().appID(),
                appDispInfoStartupOutput: vm.appDispInfoStartupOutput()
            }).done(res => {
                if (res) {
                    vm.reflectSetting(res.reflectSetting);
                    vm.timeLeaveRemaining(res.timeLeaveRemaining);
                    vm.timeLeaveManagement(res.timeLeaveManagement);
                    let totalAppTime: Array<number> = [0, 0, 0, 0, 0, 0], specialFrame: number = null;
                    res.details.forEach((detail: TimeLeaveAppDetail) => {
                        detail.timeZones.forEach(z => {
                            const index = detail.appTimeType < 4 ? 0 : z.workNo - 1;
                            const startTime = detail.appTimeType >= 4
                                            || detail.appTimeType == AppTimeType.ATWORK
                                            || detail.appTimeType == AppTimeType.ATWORK2 ? z.startTime : z.endTime;
                            const endTime = detail.appTimeType < 4 ? null : z.endTime;
                            if (detail.appTimeType >= 4) {
                                if (vm.applyTimeData()[4].timeZones[index].displayCombobox())
                                    vm.applyTimeData()[4].timeZones[index].appTimeType(detail.appTimeType);
                                if (vm.applyTimeData()[4].timeZones[index].appTimeType() == detail.appTimeType) {
                                    vm.applyTimeData()[4].timeZones[index].startTime(startTime);
                                    vm.applyTimeData()[4].timeZones[index].endTime(endTime);
                                }
                            } else {
                                vm.applyTimeData()[detail.appTimeType].timeZones[index].startTime(startTime);
                                vm.applyTimeData()[detail.appTimeType].timeZones[index].endTime(endTime);
                            }
                        });
                        const index1 = Math.min(detail.appTimeType, 4);
                        const index2 = detail.appTimeType <= 4 ? 0 : 1;
                        vm.applyTimeData()[index1].applyTime[index2].substituteAppTime(detail.applyTime.substituteAppTime);
                        vm.applyTimeData()[index1].applyTime[index2].annualAppTime(detail.applyTime.annualAppTime);
                        vm.applyTimeData()[index1].applyTime[index2].childCareAppTime(detail.applyTime.childCareAppTime);
                        vm.applyTimeData()[index1].applyTime[index2].careAppTime(detail.applyTime.careAppTime);
                        vm.applyTimeData()[index1].applyTime[index2].super60AppTime(detail.applyTime.super60AppTime);
                        vm.applyTimeData()[index1].applyTime[index2].specialAppTime(detail.applyTime.specialAppTime);
                        vm.applyTimeData()[index1].applyTime[index2].calculatedTime(
                            detail.applyTime.substituteAppTime
                            + detail.applyTime.annualAppTime
                            + detail.applyTime.childCareAppTime
                            + detail.applyTime.careAppTime
                            + detail.applyTime.super60AppTime
                            + detail.applyTime.specialAppTime
                        );
                        totalAppTime[LeaveType.SUBSTITUTE] += detail.applyTime.substituteAppTime;
                        totalAppTime[LeaveType.ANNUAL] += detail.applyTime.annualAppTime;
                        totalAppTime[LeaveType.CHILD_NURSING] += detail.applyTime.childCareAppTime;
                        totalAppTime[LeaveType.NURSING] += detail.applyTime.careAppTime;
                        totalAppTime[LeaveType.SUPER_60H] += detail.applyTime.super60AppTime;
                        totalAppTime[LeaveType.SPECIAL] += detail.applyTime.specialAppTime;
                        if (detail.applyTime.specialAppTime > 0) specialFrame = detail.applyTime.specialLeaveFrameNo;
                    });
                    if (totalAppTime.filter(i => i > 0).length > 1) {
                        vm.leaveType(LeaveType.COMBINATION);
                    } else {
                        vm.leaveType(_.findIndex(totalAppTime, i => i > 0));
                    }
                    if (specialFrame != null) vm.specialLeaveFrame(specialFrame);
                    vm.printContentOfEachAppDto().opPrintContentOfTimeLeave = res.details;

                    if(vm.leaveType() == LeaveType.COMBINATION && _.isFunction(vm.childCalcEvent)) {
                        vm.childCalcEvent();
                    }

                    vm.$nextTick(() => {
                        $("#updateKAF000").focus();
                    });
                }
            }).fail(err => {
                vm.$dialog.error(err).then(() => {
                    if (err.messageId == "Msg_474") {
                        nts.uk.request.jumpToTopPage();
                    }
                });
            }).always(() => vm.$blockui('hide'));
        }

        mounted() {
            const vm = this;
        }

        // event update cần gọi lại ở button của view cha
        update() {
            const vm = this;
            if (!vm.appDispInfoStartupOutput().appDetailScreenInfo) {
                return;
            }
            const details: Array<any> = [];
            vm.applyTimeData().forEach((row : DataModel) => {
                if (row.display()) {
                    if (row.appTimeType < 4) {
                        const applyTime = {
                            substituteAppTime: vm.leaveType() == LeaveType.SUBSTITUTE || vm.leaveType() == LeaveType.COMBINATION ? row.applyTime[0].substituteAppTime() : 0,
                            annualAppTime: vm.leaveType() == LeaveType.ANNUAL || vm.leaveType() == LeaveType.COMBINATION ? row.applyTime[0].annualAppTime() : 0,
                            childCareAppTime: vm.leaveType() == LeaveType.CHILD_NURSING || vm.leaveType() == LeaveType.COMBINATION ? row.applyTime[0].childCareAppTime() : 0,
                            careAppTime: vm.leaveType() == LeaveType.NURSING || vm.leaveType() == LeaveType.COMBINATION ? row.applyTime[0].careAppTime() : 0,
                            super60AppTime: vm.leaveType() == LeaveType.SUPER_60H || vm.leaveType() == LeaveType.COMBINATION ? row.applyTime[0].super60AppTime() : 0,
                            specialAppTime: vm.leaveType() == LeaveType.SPECIAL || vm.leaveType() == LeaveType.COMBINATION ? row.applyTime[0].specialAppTime() : 0,
                            specialLeaveFrameNo: vm.leaveType() == LeaveType.SPECIAL || (vm.leaveType() == LeaveType.COMBINATION && row.applyTime[0].specialAppTime() > 0) ? vm.specialLeaveFrame() : null,
                        };
                        if (applyTime.substituteAppTime > 0
                            || applyTime.annualAppTime > 0
                            || applyTime.childCareAppTime > 0
                            || applyTime.careAppTime > 0
                            || applyTime.super60AppTime > 0
                            || applyTime.specialAppTime > 0) {
                            details.push({
                                appTimeType: row.appTimeType,
                                timeZones: [{
                                    workNo: row.appTimeType == AppTimeType.ATWORK || row.appTimeType == AppTimeType.OFFWORK ? 1 : 2,
                                    startTime: row.appTimeType == AppTimeType.ATWORK || row.appTimeType == AppTimeType.ATWORK2 ? row.scheduledTime() : row.timeZones[0].startTime(),
                                    endTime: row.appTimeType == AppTimeType.ATWORK || row.appTimeType == AppTimeType.ATWORK2 ? row.timeZones[0].startTime() : row.scheduledTime(),
                                }],
                                applyTime: applyTime
                            });
                        }
                    } else {
                        const privateTimeZones = row.timeZones.filter(z => z.appTimeType() == AppTimeType.PRIVATE && (!!z.startTime() || !!z.endTime()));
                        const privateApplyTime = {
                            substituteAppTime: vm.leaveType() == LeaveType.SUBSTITUTE || vm.leaveType() == LeaveType.COMBINATION ? row.applyTime[0].substituteAppTime() : 0,
                            annualAppTime: vm.leaveType() == LeaveType.ANNUAL || vm.leaveType() == LeaveType.COMBINATION ? row.applyTime[0].annualAppTime() : 0,
                            childCareAppTime: vm.leaveType() == LeaveType.CHILD_NURSING || vm.leaveType() == LeaveType.COMBINATION ? row.applyTime[0].childCareAppTime() : 0,
                            careAppTime: vm.leaveType() == LeaveType.NURSING || vm.leaveType() == LeaveType.COMBINATION ? row.applyTime[0].careAppTime() : 0,
                            super60AppTime: vm.leaveType() == LeaveType.SUPER_60H || vm.leaveType() == LeaveType.COMBINATION ? row.applyTime[0].super60AppTime() : 0,
                            specialAppTime: vm.leaveType() == LeaveType.SPECIAL || vm.leaveType() == LeaveType.COMBINATION ? row.applyTime[0].specialAppTime() : 0,
                            specialLeaveFrameNo: vm.leaveType() == LeaveType.SPECIAL || (vm.leaveType() == LeaveType.COMBINATION && row.applyTime[0].specialAppTime() > 0) ? vm.specialLeaveFrame() : null,
                        };
                        if (privateApplyTime.substituteAppTime > 0
                            || privateApplyTime.annualAppTime > 0
                            || privateApplyTime.childCareAppTime > 0
                            || privateApplyTime.careAppTime > 0
                            || privateApplyTime.super60AppTime > 0
                            || privateApplyTime.specialAppTime > 0) {
                            details.push({
                                appTimeType: AppTimeType.PRIVATE,
                                timeZones: privateTimeZones.map(z => ({workNo: z.workNo, startTime: z.startTime(), endTime: z.endTime()})),
                                applyTime: privateApplyTime
                            });
                        }
                        const unionTimeZones = row.timeZones.filter(z => z.appTimeType() == AppTimeType.UNION && (!!z.startTime() || !!z.endTime()));
                        const unionApplyTime = {
                            substituteAppTime: vm.leaveType() == LeaveType.SUBSTITUTE || vm.leaveType() == LeaveType.COMBINATION ? row.applyTime[1].substituteAppTime() : 0,
                            annualAppTime: vm.leaveType() == LeaveType.ANNUAL || vm.leaveType() == LeaveType.COMBINATION ? row.applyTime[1].annualAppTime() : 0,
                            childCareAppTime: vm.leaveType() == LeaveType.CHILD_NURSING || vm.leaveType() == LeaveType.COMBINATION ? row.applyTime[1].childCareAppTime() : 0,
                            careAppTime: vm.leaveType() == LeaveType.NURSING || vm.leaveType() == LeaveType.COMBINATION ? row.applyTime[1].careAppTime() : 0,
                            super60AppTime: vm.leaveType() == LeaveType.SUPER_60H || vm.leaveType() == LeaveType.COMBINATION ? row.applyTime[1].super60AppTime() : 0,
                            specialAppTime: vm.leaveType() == LeaveType.SPECIAL || vm.leaveType() == LeaveType.COMBINATION ? row.applyTime[1].specialAppTime() : 0,
                            specialLeaveFrameNo: vm.leaveType() == LeaveType.SPECIAL || (vm.leaveType() == LeaveType.COMBINATION && row.applyTime[1].specialAppTime() > 0) ? vm.specialLeaveFrame() : null,
                        };
                        if (unionApplyTime.substituteAppTime > 0
                            || unionApplyTime.annualAppTime > 0
                            || unionApplyTime.childCareAppTime > 0
                            || unionApplyTime.careAppTime > 0
                            || unionApplyTime.super60AppTime > 0
                            || unionApplyTime.specialAppTime > 0) {
                            details.push({
                                appTimeType: AppTimeType.UNION,
                                timeZones: unionTimeZones.map(z => ({workNo: z.workNo, startTime: z.startTime(), endTime: z.endTime()})),
                                applyTime: unionApplyTime
                            });
                        }
                    }
                }
            });

            vm.$blockui("show");
            return vm.$validate('.nts-input', '#kaf000-b-component3-prePost', '#kaf000-b-component5-comboReason')
                .then(isValid => {
                    let timeZoneError = false;
                    details.forEach(d => {
                        if (d.appTimeType >= 4) {
                            d.timeZones.forEach((tz: any) => {
                                if (tz.startTime > tz.endTime) {
                                    timeZoneError = true;
                                    $("#endTime-" + tz.workNo).ntsError("set", {messageId: "Msg_857"});
                                }
                            });
                        }
                    });
                    if (isValid && !timeZoneError) {
                        const params = {
                            timeDigestAppType: vm.leaveType(),
                            applicationUpdate: ko.toJS(vm.application),
                            details: details,
                            timeLeaveAppDisplayInfo: {
                                appDispInfoStartupOutput: vm.appDispInfoStartupOutput(),
                                timeLeaveManagement: vm.timeLeaveManagement(),
                                timeLeaveRemaining: vm.timeLeaveRemaining(),
                                reflectSetting: vm.reflectSetting()
                            },
                            agentMode: false
                        };
                        params.timeLeaveAppDisplayInfo.timeLeaveRemaining.remainingStart = new Date(params.timeLeaveAppDisplayInfo.timeLeaveRemaining.remainingStart).toISOString();
                        params.timeLeaveAppDisplayInfo.timeLeaveRemaining.remainingEnd = new Date(params.timeLeaveAppDisplayInfo.timeLeaveRemaining.remainingEnd).toISOString();
                        return vm.$ajax(API.checkRegister, params);
                    }
                }).then(res => {
                    if (res == undefined) return;
                    if ( _.isEmpty( res ) ) {
                        return vm.registerData( details );
                    } else {
                        let listTemp = _.clone( res );
                        return vm.handleConfirmMessage( listTemp, details );

                    }
                }).done(result => {
                    if (result != undefined) {
                        vm.$dialog.info( { messageId: "Msg_15" } ).then(() => {
							CommonProcess.handleMailResult(result, vm);
						});
                    }
                }).fail(err => {
                    // vm.handleError(err);
                    if (err.messageId == "Msg_1687") {
                        $(vm.$el).find('leave-type-switch').focus();
                    }
                }).always(() => vm.$blockui("hide"));
        }

        handleConfirmMessage(listMes: any, res: any): any {
            let vm = this;
            if (!_.isEmpty(listMes)) {
                let item = listMes.shift();
                return vm.$dialog.confirm({ messageId: item.msgID }).then((value) => {
                    if (value == 'yes') {
                        if (_.isEmpty(listMes)) {
                            return vm.registerData(res);
                        } else {
                            return vm.handleConfirmMessage(listMes, res);
                        }

                    }
                });
            }
        }

        registerData(details: Array<any>) {
            const vm = this;
            const paramsRegister = {
                timeLeaveAppDisplayInfo: {
                    appDispInfoStartupOutput: vm.appDispInfoStartupOutput(),
                    timeLeaveManagement: vm.timeLeaveManagement(),
                    timeLeaveRemaining: vm.timeLeaveRemaining(),
                    reflectSetting: vm.reflectSetting()
                },
                application: ko.toJS(vm.application),
                details: details
            };
            paramsRegister.timeLeaveAppDisplayInfo.timeLeaveRemaining.remainingStart = new Date(paramsRegister.timeLeaveAppDisplayInfo.timeLeaveRemaining.remainingStart).toISOString();
            paramsRegister.timeLeaveAppDisplayInfo.timeLeaveRemaining.remainingEnd = new Date(paramsRegister.timeLeaveAppDisplayInfo.timeLeaveRemaining.remainingEnd).toISOString();
            return vm.$ajax(API.updateApplication, paramsRegister);
        }

        // public handleError(err: any) {
        //     const vm = this;
        //     let param;
        //     if (err.message && err.messageId) {
        //         param = {messageId: err.messageId, messageParams: err.parameterIds};
        //     } else {
        //         if (err.message) {
        //             param = {message: err.message, messageParams: err.parameterIds};
        //         } else {
        //             param = {messageId: err.messageId, messageParams: err.parameterIds};
        //         }
        //     }
        //     vm.$dialog.error(param).then((err: any) => {
        //         if (err.messageId == 'Msg_197') {
        //         	ko.contextFor($('#contents-area')[0]).$vm.loadData();
        //         }
        //     });
        // }

        getChildCalcEvent(evt: () => void) {
            const vm = this;
            vm.childCalcEvent = evt;
        }
    }

    interface TimeLeaveAppDetail {
        appTimeType: number,
        applyTime: {
            annualAppTime: number,
            super60AppTime: number,
            careAppTime: number,
            childCareAppTime: number,
            substituteAppTime: number,
            specialAppTime: number,
            specialLeaveFrameNo: number
        }
        timeZones: Array<{workNo: number, startTime: number, endTime: number}>
    }

}