module nts.uk.at.view.kaf004_ref.b.viewmodel {
    import Application = nts.uk.at.view.kaf000.shr.viewmodel.Application;
    import CommonProcess = nts.uk.at.view.kaf000.shr.viewmodel.CommonProcess;
    import WorkManagement = nts.uk.at.view.kaf004_ref.shr.common.viewmodel.WorkManagement;
    import LateOrEarlyInfo = nts.uk.at.view.kaf004_ref.shr.common.viewmodel.LateOrEarlyInfo;
    import ArrivedLateLeaveEarlyInfo = nts.uk.at.view.kaf004_ref.shr.common.viewmodel.ArrivedLateLeaveEarlyInfo;
    import AppType = nts.uk.at.view.kaf000.shr.viewmodel.model.AppType;
    import PrintContentOfEachAppDto = nts.uk.at.view.kaf000.shr.viewmodel.PrintContentOfEachAppDto;

    @component({
        name: 'kaf004-b',
        template: `
        <div>
        <div class="fixed-flex-layout-left">
            <div data-bind="component: { name: 'kaf000-b-component1',
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
            <div class="table">
                <div class="cell" style="width: 825px;" data-bind="component: { name: 'kaf000-b-component4',
                                    params: {
                                        appType: appType,
                                        application: application,
                                        appDispInfoStartupOutput: appDispInfoStartupOutput
                                    } }"></div>
                 <div class="cell" style="position: absolute;" data-bind="component: { name: 'kaf000-b-component9',
                                    params: {
                                        appType: appType,
                                        application: application,
                                        appDispInfoStartupOutput: $vm.appDispInfoStartupOutput
                                    } }"></div>
            </div>
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
            <div class="fixed-flex-layout" style="margin-left: -10px;" data-bind="component: {name: 'kaf004_share'}"></div>
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

    </div>
        `
    })
    class KAF004AViewModel extends ko.ViewModel {
        appType: KnockoutObservable<number> = ko.observable(AppType.EARLY_LEAVE_CANCEL_APPLICATION);
        application: KnockoutObservable<Application>;
        workManagement: WorkManagement;
        arrivedLateLeaveEarlyInfo: any;
        appDispInfoStartupOutput: any;
        managementMultipleWorkCycles: KnockoutObservable<Boolean>;
        lateOrEarlyInfos: KnockoutObservableArray<LateOrEarlyInfo>;
        lateOrEarlyInfo1: KnockoutObservable<LateOrEarlyInfo>;
        lateOrEarlyInfo2: KnockoutObservable<LateOrEarlyInfo>;
        lateOrEarlyInfo3: KnockoutObservable<LateOrEarlyInfo>;
        lateOrEarlyInfo4: KnockoutObservable<LateOrEarlyInfo>;
        isSendMail: KnockoutObservable<Boolean>;
        printContentOfEachAppDto: KnockoutObservable<PrintContentOfEachAppDto>;
        approvalReason: KnockoutObservable<string>;
        cancalAppDispSet: boolean = true;

        created(
            params: {
				appType: any,
                application: any,
                printContentOfEachAppDto: PrintContentOfEachAppDto,
                approvalReason: any,
                appDispInfoStartupOutput: any,
                eventUpdate: (evt: () => void) => void,
				eventReload: (evt: () => void) => void
            }) {
            const vm = this;
            vm.isSendMail = ko.observable(true);
            vm.printContentOfEachAppDto = ko.observable(params.printContentOfEachAppDto);

            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            vm.application = params.application;
			vm.appType = params.appType;

            vm.workManagement = new WorkManagement('--:--', '--:--', '--:--', '--:--', null, null, null, null);

            vm.lateOrEarlyInfo1 = ko.observable(new LateOrEarlyInfo(false, 1, false, false, 0));
            vm.lateOrEarlyInfo2 = ko.observable(new LateOrEarlyInfo(false, 1, false, false, 1));
            vm.lateOrEarlyInfo3 = ko.observable(new LateOrEarlyInfo(false, 2, false, false, 0));
            vm.lateOrEarlyInfo4 = ko.observable(new LateOrEarlyInfo(false, 2, false, false, 1));
            vm.lateOrEarlyInfos = ko.observableArray([]);
            vm.managementMultipleWorkCycles = ko.observable(false);

            // Subcribe when mode change -> clear data if mode is 'before'
            vm.application().prePostAtr.subscribe(() => {
                if (ko.toJS(vm.application().prePostAtr) === 0) {
                    vm.workManagement.clearData();
                }
            });

//            vm.application().appID.subscribe(() => {
//                if (vm.application().appType === AppType.EARLY_LEAVE_CANCEL_APPLICATION) {
//                    vm.createParamKAF004();
//                }
//            });

            vm.createParamKAF004();
            // params.printContentOfEachAppDto.opArrivedLateLeaveEarlyInfo = ko.toJS(vm.arrivedLateLeaveEarlyInfo);
            vm.approvalReason = params.approvalReason;
            // gui event con ra viewmodel cha
            // nhớ dùng bind(vm) để ngữ cảnh lúc thực thi
            // luôn là component
            params.eventUpdate(vm.update.bind(vm));
			params.eventReload(vm.reload.bind(vm));
        }

		reload() {
			const vm = this;
			if(vm.appType() === AppType.EARLY_LEAVE_CANCEL_APPLICATION) {
				vm.createParamKAF004();
			}
		}

        createParamKAF004() {
            const vm = this;
            let command = {
                appId: ko.toJS(vm.application().appID),
                infoStartup: ko.toJS(vm.appDispInfoStartupOutput)
            };

            vm.$blockui("show");
            vm.$ajax(API.initPageB, command)
                .done((res: any) => {
                    this.fetchData(res);
                }).fail((err: any) => {
                    console.log()
                    vm.$dialog.error({ messageId: err.messageId });
                }).always(() => vm.$blockui('hide'));
        }

        mounted() {
            const vm = this;

        }

        fetchData(params: any) {
            const vm = this;
            vm.arrivedLateLeaveEarlyInfo = ko.observable(params);
            vm.appDispInfoStartupOutput(params.appDispInfoStartupOutput);
            vm.appDispInfoStartupOutput.valueHasMutated();
            vm.workManagement.scheAttendanceTime("--:--")
            vm.workManagement.scheWorkTime("--:--")
            vm.workManagement.scheAttendanceTime2("--:--")
            vm.workManagement.scheWorkTime2("--:--")
            vm.workManagement.workTime(null);
            vm.workManagement.leaveTime(null);
            vm.workManagement.workTime2(null);
            vm.workManagement.leaveTime2(null);

            vm.managementMultipleWorkCycles(params.appDispInfoStartupOutput.appDispInfoNoDateOutput.managementMultipleWorkCycles);

            vm.cancalAppDispSet = params.lateEarlyCancelAppSet.cancelAtr !== 0;
            var achiveEarly = vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail;

            var check1 = _.filter(vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly.lateCancelation, { 'workNo': 1, 'lateOrEarlyClassification': 0 });
            var check2 = _.filter(vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly.lateCancelation, { 'workNo': 1, 'lateOrEarlyClassification': 1 });
            var check3 = _.filter(vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly.lateCancelation, { 'workNo': 2, 'lateOrEarlyClassification': 0 });
            var check4 = _.filter(vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly.lateCancelation, { 'workNo': 2, 'lateOrEarlyClassification': 1 });

            vm.lateOrEarlyInfos(vm.arrivedLateLeaveEarlyInfo().earlyInfos);
            if (this.lateOrEarlyInfos().length > 0) {

                if (ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 1, 'category': 0 })).length > 0) {
                    vm.lateOrEarlyInfo1().isActive(_.isEmpty(check1) ? false : true);
                    vm.lateOrEarlyInfo1().isCheck(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 1, 'category': 0 }))[0].isCheck);
                    vm.lateOrEarlyInfo1().isIndicated(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 1, 'category': 0 }))[0].isIndicated);
                    vm.lateOrEarlyInfo1().category(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 1, 'category': 0 }))[0].category);
                    vm.lateOrEarlyInfo1().workNo(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 1, 'category': 0 }))[0].workNo);
                    vm.lateOrEarlyInfo1().isCheck(_.isEmpty(check1) && vm.lateOrEarlyInfo1().isIndicated() !== false ? false : true);
                } else {
                    if (achiveEarly.opWorkTime !== null) {
                        vm.lateOrEarlyInfo1().isActive(true);
                    } else {
                        vm.lateOrEarlyInfo1().isActive(false);
                    }
                }

                if (ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 1, 'category': 1 })).length > 0) {
                    vm.lateOrEarlyInfo2().isActive(_.isEmpty(check2) ? false : true);
                    vm.lateOrEarlyInfo2().isCheck(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 1, 'category': 1 }))[0].isCheck);
                    vm.lateOrEarlyInfo2().isIndicated(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 1, 'category': 1 }))[0].isIndicated);
                    vm.lateOrEarlyInfo2().category(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 1, 'category': 1 }))[0].category);
                    vm.lateOrEarlyInfo2().workNo(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 1, 'category': 1 }))[0].workNo);
                    vm.lateOrEarlyInfo2().isCheck(_.isEmpty(check2) && vm.lateOrEarlyInfo2().isIndicated() !== false ? false : true);
                } else {
                    if (achiveEarly.opLeaveTime !== null) {
                        vm.lateOrEarlyInfo2().isActive(true);
                    } else {
                        vm.lateOrEarlyInfo2().isActive(false);
                    }
                }

                if (ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 2, 'category': 0 })).length > 0) {
                    vm.lateOrEarlyInfo3().isActive(_.isEmpty(check3) ? false : true);
                    vm.lateOrEarlyInfo3().isCheck(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 2, 'category': 0 }))[0].isCheck);
                    vm.lateOrEarlyInfo3().isIndicated(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 2, 'category': 0 }))[0].isIndicated);
                    vm.lateOrEarlyInfo3().category(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 2, 'category': 0 }))[0].category);
                    vm.lateOrEarlyInfo3().workNo(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 2, 'category': 0 }))[0].workNo);
                    vm.lateOrEarlyInfo3().isCheck(_.isEmpty(check3) && vm.lateOrEarlyInfo3().isIndicated() !== false ? false : true);
                } else {
                    if (achiveEarly.opWorkTime2 !== null) {
                        vm.lateOrEarlyInfo3().isActive(true);
                    } else {
                        vm.lateOrEarlyInfo3().isActive(false);
                    }
                }

                if (ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 2, 'category': 1 })).length > 0) {
                    vm.lateOrEarlyInfo4().isActive(_.isEmpty(check4) ? false : true);
                    vm.lateOrEarlyInfo4().isCheck(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 2, 'category': 1 }))[0].isCheck);
                    vm.lateOrEarlyInfo4().isIndicated(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 2, 'category': 1 }))[0].isIndicated);
                    vm.lateOrEarlyInfo4().category(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 2, 'category': 1 }))[0].category);
                    vm.lateOrEarlyInfo4().workNo(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 2, 'category': 1 }))[0].workNo);
                    vm.lateOrEarlyInfo4().isCheck(_.isEmpty(check4) && vm.lateOrEarlyInfo4().isIndicated() !== false ? false : true);
                } else {
                    if (achiveEarly.opDepartureTime2 !== null) {
                        vm.lateOrEarlyInfo4().isActive(true);
                    } else {
                        vm.lateOrEarlyInfo4().isActive(false);
                    }
                }
            }

            if (vm.cancalAppDispSet) {
                vm.lateOrEarlyInfo1().isIndicated(true)
                vm.lateOrEarlyInfo2().isIndicated(true)
                vm.lateOrEarlyInfo3().isIndicated(true)
                vm.lateOrEarlyInfo4().isIndicated(true)
            }

            var lateEarliesApp = params.arrivedLateLeaveEarly.lateOrLeaveEarlies;

            if(vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail !== null) {
                vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.achievementEarly.scheAttendanceTime1 == null ? vm.workManagement.scheAttendanceTime('--:--') :
                    vm.workManagement.scheAttendanceTime(nts.uk.time.format.byId("Clock_Short_HM", vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.achievementEarly.scheAttendanceTime1));
                vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.achievementEarly.scheDepartureTime1 == null ? vm.workManagement.scheWorkTime('--:--') :
                    vm.workManagement.scheWorkTime(nts.uk.time.format.byId("Clock_Short_HM", vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.achievementEarly.scheDepartureTime1));
                vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.achievementEarly.scheAttendanceTime2 == null ? vm.workManagement.scheAttendanceTime2('--:--') :
                    vm.workManagement.scheAttendanceTime2(nts.uk.time.format.byId("Clock_Short_HM", vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.achievementEarly.scheAttendanceTime2));
                vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.achievementEarly.scheDepartureTime2 == null ? vm.workManagement.scheWorkTime2('--:--') :
                    vm.workManagement.scheWorkTime2(nts.uk.time.format.byId("Clock_Short_HM", vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.achievementEarly.scheDepartureTime2));
            }

            if (_.filter(lateEarliesApp, { 'workNo': 1, 'lateOrEarlyClassification': 0 }).length > 0) {
                vm.workManagement.workTime(_.filter(lateEarliesApp, { 'workNo': 1, 'lateOrEarlyClassification': 0 })[0].timeWithDayAttr);
                // vm.lateOrEarlyInfo1().isActive(true);
            } else {
                if (vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail!== null && vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.opWorkTime !== null && vm.application().prePostAtr() == 1) {
                    vm.workManagement.workTime(vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.opWorkTime);
                }
            }

            if (_.filter(lateEarliesApp, { 'workNo': 1, 'lateOrEarlyClassification': 1 }).length > 0) {
                vm.workManagement.leaveTime(_.filter(lateEarliesApp, { 'workNo': 1, 'lateOrEarlyClassification': 1 })[0].timeWithDayAttr);
                // vm.lateOrEarlyInfo2().isActive(true);
            } else {
                if (vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail!== null && vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.opLeaveTime !== null && vm.application().prePostAtr() == 1) {
                    vm.workManagement.leaveTime(vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.opLeaveTime);
                }
            }

            if (_.filter(lateEarliesApp, { 'workNo': 2, 'lateOrEarlyClassification': 0 }).length > 0) {
                vm.workManagement.workTime2(_.filter(lateEarliesApp, { 'workNo': 2, 'lateOrEarlyClassification': 0 })[0].timeWithDayAttr);
                // vm.lateOrEarlyInfo3().isActive(true);
            } else {
                if (vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail!== null && vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.opWorkTime2 !== null && vm.application().prePostAtr() == 1) {
                    vm.workManagement.workTime2(vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.opWorkTime2);
                }
            }

            if (_.filter(lateEarliesApp, { 'workNo': 2, 'lateOrEarlyClassification': 1 }).length > 0) {
                vm.workManagement.leaveTime2(_.filter(lateEarliesApp, { 'workNo': 2, 'lateOrEarlyClassification': 1 })[0].timeWithDayAttr);
                // vm.lateOrEarlyInfo4().isActive(true);
            } else {
                if (vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail!== null && vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.opDepartureTime2 !== null && vm.application().prePostAtr() == 1) {
                    vm.workManagement.leaveTime2(vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.opDepartureTime2);
                }
            }




            if (!vm.workManagement.scheAttendanceTime) {
                vm.workManagement.scheAttendanceTime("--:--");
            }
            if (!vm.workManagement.scheAttendanceTime2) {
                vm.workManagement.scheAttendanceTime2("--:--");
            }
            if (!vm.workManagement.scheWorkTime) {
                vm.workManagement.scheWorkTime("--:--");
            }
            if (!vm.workManagement.scheWorkTime2) {
                vm.workManagement.scheWorkTime2("--:--");
            }

            params.arrivedLateLeaveEarly.lateCancelation = [];
            if (vm.lateOrEarlyInfo1().isCheck()) {
                params.arrivedLateLeaveEarly.lateCancelation.push({ workNo: 1, lateOrEarlyClassification: 0 })
            }
            if (vm.lateOrEarlyInfo2().isCheck()) {
                params.arrivedLateLeaveEarly.lateCancelation.push({ workNo: 1, lateOrEarlyClassification: 1 })
            }
            if (vm.lateOrEarlyInfo3().isCheck()) {
                params.arrivedLateLeaveEarly.lateCancelation.push({ workNo: 2, lateOrEarlyClassification: 0 })
            }
            if (vm.lateOrEarlyInfo4().isCheck()) {
                params.arrivedLateLeaveEarly.lateCancelation.push({ workNo: 2, lateOrEarlyClassification: 1 })
            }
            vm.printContentOfEachAppDto().opArrivedLateLeaveEarlyInfo = params;
        }

        update() {
            const vm = this;
            let application = vm.arrivedLateLeaveEarlyInfo().appDispInfoStartupOutput.appDetailScreenInfo.application;
            application.opAppReason = ko.toJS(vm.application().opAppReason);
            application.opAppStandardReasonCD = ko.toJS(vm.application().opAppStandardReasonCD);
            application.opReversionReason = ko.toJS(vm.application().opReversionReason);
            let arrivedLateLeaveEarlyInfo = ko.toJS(vm.arrivedLateLeaveEarlyInfo);
            arrivedLateLeaveEarlyInfo.arrivedLateLeaveEarly.lateOrLeaveEarlies = [];
            arrivedLateLeaveEarlyInfo.arrivedLateLeaveEarly.lateCancelation = [];
            arrivedLateLeaveEarlyInfo.earlyInfos = [];


            if (vm.cancalAppDispSet) {
                arrivedLateLeaveEarlyInfo.arrivedLateLeaveEarly.lateCancelation.push(ko.toJS(vm.lateOrEarlyInfo1));
                arrivedLateLeaveEarlyInfo.arrivedLateLeaveEarly.lateCancelation.push(ko.toJS(vm.lateOrEarlyInfo2));
                arrivedLateLeaveEarlyInfo.arrivedLateLeaveEarly.lateCancelation.push(ko.toJS(vm.lateOrEarlyInfo3));
                arrivedLateLeaveEarlyInfo.arrivedLateLeaveEarly.lateCancelation.push(ko.toJS(vm.lateOrEarlyInfo4));
            }

            let lateCancelation = [];
                    let lateOrLeaveEarlies = [];

                    if (ko.toJS(vm.workManagement.workTime)) {
                        lateOrLeaveEarlies.push({
                            workNo: 1,
                            lateOrEarlyClassification: 0,
                            timeWithDayAttr: ko.toJS(vm.workManagement.workTime())
                        })
                    }
                    if (ko.toJS(vm.workManagement.leaveTime)) {
                        lateOrLeaveEarlies.push({
                            workNo: 1,
                            lateOrEarlyClassification: 1,
                            timeWithDayAttr: ko.toJS(vm.workManagement.leaveTime())
                        })
                    }

                    if(vm.managementMultipleWorkCycles()) {
                        if (ko.toJS(vm.workManagement.workTime2)) {
                            lateOrLeaveEarlies.push({
                                workNo: 2,
                                lateOrEarlyClassification: 0,
                                timeWithDayAttr: ko.toJS(vm.workManagement.workTime2())
                            })
                        }
                        if (ko.toJS(vm.workManagement.leaveTime2)) {
                            lateOrLeaveEarlies.push({
                                workNo: 2,
                                lateOrEarlyClassification: 1,
                                timeWithDayAttr: ko.toJS(vm.workManagement.leaveTime2())
                            })
                        }
                    }
                    if (ko.toJS(vm.application().prePostAtr) === 1) {
                        if (ko.toJS(vm.lateOrEarlyInfo1().isCheck)) {
                            lateCancelation.push({
                                workNo: 1,
                                lateOrEarlyClassification: 0
                            }),
                                _.remove(lateOrLeaveEarlies, (x) => {
                                    return (x.workNo === 1 && x.lateOrEarlyClassification === 0);
                                });
                        }
                        if (ko.toJS(vm.lateOrEarlyInfo2().isCheck)) {
                            lateCancelation.push({
                                workNo: 1,
                                lateOrEarlyClassification: 1
                            })
                            _.remove(lateOrLeaveEarlies, (x) => {
                                return (x.workNo === 1 && x.lateOrEarlyClassification === 1);
                            });
                        }
                        if (vm.managementMultipleWorkCycles() && ko.toJS(vm.lateOrEarlyInfo3().isCheck)) {
                            lateCancelation.push({
                                workNo: 2,
                                lateOrEarlyClassification: 0
                            })
                            _.remove(lateOrLeaveEarlies, (x) => {
                                return (x.workNo === 2 && x.lateOrEarlyClassification === 0);
                            });
                        }
                        if (vm.managementMultipleWorkCycles() && ko.toJS(vm.lateOrEarlyInfo4().isCheck)) {
                            lateCancelation.push({
                                workNo: 2,
                                lateOrEarlyClassification: 1
                            })
                            _.remove(lateOrLeaveEarlies, (x) => {
                                return (x.workNo === 2 && x.lateOrEarlyClassification === 1);
                            });
                        }
                    }
                    let arrivedLateLeaveEarly = {
                        lateCancelation: lateCancelation,
                        lateOrLeaveEarlies: lateOrLeaveEarlies
                    }

                    arrivedLateLeaveEarlyInfo.arrivedLateLeaveEarly = arrivedLateLeaveEarly;

            vm.$blockui("show");
            return vm.$validate()
                .then((isValid) => {
                    if (isValid) {
                        const command = {
                            agentAtr: true,
                            isNew: false,
                            infoOutput: arrivedLateLeaveEarlyInfo,
                            application: application
                        };

                        return vm.$ajax(API.getMsgList + "/" + ko.toJS(application.appType), command)
                    }
                }).done((success: any) => {
                    if (success) {
                        console.log(success);
                        for (var i = 0; i < success.length; i++) {
                            vm.$dialog.confirm({ messageId: success[i] }).then((result: 'no' | 'yes' | 'cancel') => {
                                if (result !== 'yes') {
                                    return;
                                }
                            });
                        }
                        this.afterRegister(application);
                    }
                }).fail((fail: any) => {
                    if (fail) {
                        vm.$dialog.error({ messageId: fail.messageId })
                            .then(() => {
                                let command = {
                                    appId: ko.toJS(vm.application().appID),
                                    infoStartup: ko.toJS(vm.appDispInfoStartupOutput)
                                };

                                return vm.$ajax(API.initPageB, command)
                                    .done((res: any) => {
                                        this.fetchData(res);
                                    })
                                    .fail((err: any) => {
                                        console.log()
                                        vm.$dialog.error({ messageId: err.messageId });
                                    });
                            })
                    }
                }).always(() => vm.$blockui('hide'));
        }

        register() {
            const vm = this;

            console.log(ko.toJS(vm.application()));
            console.log(vm.workManagement);
        }

        private afterRegister(params?: any) {
            const vm = this;

            vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly.lateOrLeaveEarlies = [];
            vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly.lateCancelation = [];
            if(vm.application().prePostAtr() === 1) {
                if(vm.lateOrEarlyInfo1().isIndicated() && vm.lateOrEarlyInfo1().isActive() && vm.lateOrEarlyInfo1().isCheck()) {
                    vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly.lateCancelation.push({workNo: 1, lateOrEarlyClassification: 0})
                }
                if(vm.lateOrEarlyInfo2().isIndicated() && vm.lateOrEarlyInfo2().isActive() && vm.lateOrEarlyInfo2().isCheck()) {
                    vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly.lateCancelation.push({workNo: 1, lateOrEarlyClassification: 1})
                }
                if(vm.lateOrEarlyInfo3().isIndicated() && vm.lateOrEarlyInfo3().isActive() && vm.lateOrEarlyInfo3().isCheck()) {
                    vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly.lateCancelation.push({workNo: 2, lateOrEarlyClassification: 0})
                }
                if(vm.lateOrEarlyInfo4().isIndicated() && vm.lateOrEarlyInfo4().isActive() && vm.lateOrEarlyInfo4().isCheck()) {
                    vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly.lateCancelation.push({workNo: 2, lateOrEarlyClassification: 1})
                }
            }

            if(!(vm.lateOrEarlyInfo1().isIndicated() && vm.lateOrEarlyInfo1().isActive() && vm.lateOrEarlyInfo1().isCheck())) {
                vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly.lateOrLeaveEarlies.push({workNo: 1, lateOrEarlyClassification: 0, timeWithDayAttr: vm.workManagement.workTime()});
            }
            if(!(vm.lateOrEarlyInfo2().isIndicated() && vm.lateOrEarlyInfo2().isActive() && vm.lateOrEarlyInfo2().isCheck())) {
                vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly.lateOrLeaveEarlies.push({workNo: 1, lateOrEarlyClassification: 1, timeWithDayAttr: vm.workManagement.leaveTime()});
            }
            if(!(vm.lateOrEarlyInfo3().isIndicated() && vm.lateOrEarlyInfo3().isActive() && vm.lateOrEarlyInfo3().isCheck())) {
                vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly.lateOrLeaveEarlies.push({workNo: 2, lateOrEarlyClassification: 0, timeWithDayAttr: vm.workManagement.workTime2()});
            }
            if(!(vm.lateOrEarlyInfo4().isIndicated() && vm.lateOrEarlyInfo4().isActive() && vm.lateOrEarlyInfo4().isCheck())) {
                vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly.lateOrLeaveEarlies.push({workNo: 2, lateOrEarlyClassification: 1, timeWithDayAttr: vm.workManagement.leaveTime2()});
            }
            vm.$ajax(API.updateInfo,
                {
                    application: params,
                    arrivedLateLeaveEarlyDto: vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly,
					appDispInfoStartupDto: vm.appDispInfoStartupOutput()
                }).done((success: any) => {
                    if (success) {
                        vm.$dialog.info({ messageId: "Msg_15" });
                    }
                }).fail((fail: any) => {
                    if (fail) {
                        vm.$dialog.error({ messageId: fail.messageId })
                        console.log(fail);
                        return;
                    }
                });
        }

        // ※2
        public condition2(): boolean {
            const vm = this;

            // 「遅刻早退取消申請起動時の表示情報」.申請表示情報.申請設定（基準日関係なし）.複数回勤務の管理＝true
            return vm.appDispInfoStartupOutput().appDispInfoNoDateOutput.managementMultipleWorkCycles;
            // return true;
        }

        // ※9
        public condition9() {
            // 事前事後区分に「事後」に選択している場合　（事後モード）
            return ko.toJS(this.application().prePostAtr) === 1 && this.cancalAppDispSet;
        }

        // ※8
        public condition8(idItem: number) {
            const vm = this;

            // // return ko.computed(() => {
            // // 事前事後区分に「事後」を選択している場合　（事後モード）  (T/h select "xin sau"「事後」 trên 事前事後区分/Phân loại xin trước xin sau (Mode after/xin sau)
            // if (ko.toJS(this.application().prePostAtr) === 1) {

            //     // 起動したら、実績データがある場合 (Sau khi khởi động t/h có data thực tế)
            //     switch (idItem) {
            //         case IdItem.B6_7: {
            //             return !(this.workManagement.workTime() == null || this.workManagement.workTime() === "");
            //         } case IdItem.B6_13: {
            //             return !(this.workManagement.leaveTime() == null || this.workManagement.leaveTime() === "");
            //         } case IdItem.B6_19: {
            //             return !(this.workManagement.workTime2() == null || this.workManagement.workTime2() === "");
            //         } case IdItem.B6_25: {
            //             return !(this.workManagement.leaveTime2() == null || this.workManagement.leaveTime2() === "");
            //         } default: {
            //             return true;
            //         }
            //     }
            // }

            // return false;
            // });

            return ko.computed(() => {
                if (ko.toJS(this.application().prePostAtr) === 1) {
                    switch (idItem) {
                        case IdItem.B6_7: {
                            return vm.lateOrEarlyInfo1().isIndicated() && vm.lateOrEarlyInfo1().isActive();
                        } case IdItem.B6_13: {
                            return vm.lateOrEarlyInfo2().isIndicated() && vm.lateOrEarlyInfo2().isActive();
                        } case IdItem.B6_19: {
                            return vm.lateOrEarlyInfo3().isIndicated() && vm.lateOrEarlyInfo3().isActive();
                        } case IdItem.B6_25: {
                            return vm.lateOrEarlyInfo4().isIndicated() && vm.lateOrEarlyInfo4().isActive();
                        } default: {
                            return true;
                        }
                    }
                }
            });
        }

        // ※10 display
        public condition10Display(idItem: number) {
            const vm = this;
            return ko.computed(() => {
                // 取り消す初期情報.表示する
                switch (idItem) {
                    case IdItem.B6_7: {
                        if (ko.toJS(vm.lateOrEarlyInfo1) === null) {
                            return false;
                        }
                        return ko.toJS(vm.lateOrEarlyInfo1()) == null ? false : ko.toJS(vm.lateOrEarlyInfo1().isIndicated);
                    } case IdItem.B6_13: {
                        if (ko.toJS(vm.lateOrEarlyInfo2) === null) {
                            return false;
                        }
                        return ko.toJS(vm.lateOrEarlyInfo2()) == null ? false : ko.toJS(vm.lateOrEarlyInfo2().isIndicated);
                    } case IdItem.B6_19: {
                        if (ko.toJS(vm.lateOrEarlyInfo3) === null) {
                            return false;
                        }
                        return ko.toJS(vm.lateOrEarlyInfo3()) == null ? false : ko.toJS(vm.lateOrEarlyInfo3().isIndicated);
                    } case IdItem.B6_25: {
                        if (ko.toJS(vm.lateOrEarlyInfo4) === null) {
                            return false;
                        }
                        return ko.toJS(vm.lateOrEarlyInfo4()) == null ? false : ko.toJS(vm.lateOrEarlyInfo4().isIndicated);
                    } default: {
                        return true;
                    }
                }

                // 取り消す初期情報.表示する
                // return true;
            });

        }

        // ※10 activation
        public condition10Activation(idItem: number) {
            const vm = this;

            // 取り消す初期情報.活性する
            switch (idItem) {
                case IdItem.B6_7: {
                    if (!ko.toJS(vm.lateOrEarlyInfo1)) {
                        return false;
                    }
                    return ko.toJS(vm.lateOrEarlyInfo1().isActive);
                } case IdItem.B6_13: {
                    if (!ko.toJS(vm.lateOrEarlyInfo2)) {
                        return false;
                    }
                    return ko.toJS(vm.lateOrEarlyInfo2().isActive);
                } case IdItem.B6_19: {
                    if (!ko.toJS(vm.lateOrEarlyInfo3)) {
                        return false;
                    }
                    return ko.toJS(vm.lateOrEarlyInfo3().isActive);
                } case IdItem.B6_25: {
                    if (!ko.toJS(vm.lateOrEarlyInfo4)) {
                        return false;
                    }
                    return ko.toJS(vm.lateOrEarlyInfo4().isActive);
                } default: {
                    return false;
                }
            }
        }

        // ※2＆※9&※10
        public condition2_9_10(idItem: number) {
            return this.condition2 && this.condition9() && this.condition10Display(idItem);
        }

        public isDelete(idItem: number) {
            const vm = this;
            return ko.computed(() => {
                switch (idItem) {
                    case IdItem.B6_7: {
                        if (vm.application().prePostAtr() === 0) {
                            if (vm.workManagement.workTime() === null || vm.workManagement.workTime() === "") {
                                return false
                            }
                            return true;
                        } else {
                            return vm.lateOrEarlyInfo1() == null ? true : vm.lateOrEarlyInfo1().isCheck();
                        }
                    } case IdItem.B6_13: {
                        if (vm.application().prePostAtr() === 0) {
                            if (vm.workManagement.leaveTime() === null || vm.workManagement.leaveTime() === "") {
                                return false
                            }
                            return true;
                        } else {
                            return vm.lateOrEarlyInfo2() == null ? true : vm.lateOrEarlyInfo2().isCheck();
                        }
                    } case IdItem.B6_19: {
                        if (vm.application().prePostAtr() === 0) {
                            if (vm.workManagement.workTime2() === null || vm.workManagement.workTime2() === "") {
                                return false
                            }
                            return true;
                        } else {
                            return vm.lateOrEarlyInfo3() == null ? true : vm.lateOrEarlyInfo3().isCheck();
                        }
                    } case IdItem.B6_25: {
                        if (vm.application().prePostAtr() === 0) {
                            if (vm.workManagement.leaveTime2() === null || vm.workManagement.leaveTime2() === "") {
                                return false
                            }
                            return true;
                        } else {
                            return vm.lateOrEarlyInfo4() == null ? true : vm.lateOrEarlyInfo4().isCheck();
                        }
                    } default: {
                        return false;
                    }
                }
            });

        }
    }

    const API = {
        initPageB: "at/request/application/lateorleaveearly/initPageB",
        getMsgList: "at/request/application/lateorleaveearly/getMsgList",
        updateInfo: "at/request/application/lateorleaveearly/updateInfoApp",
        exportFile: "at/request/application/lateorleaveearly/exportFile"
    };

    export class IdItem {
        public static readonly B6_7: number = 1;
        public static readonly B6_13: number = 2;
        public static readonly B6_19: number = 3;
        public static readonly B6_25: number = 4
    }
}
