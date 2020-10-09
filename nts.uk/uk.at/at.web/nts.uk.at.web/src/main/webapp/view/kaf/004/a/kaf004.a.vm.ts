/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kaf004_ref.a.viewmodel {
    import Application = nts.uk.at.view.kaf000.shr.viewmodel.Application;
    import CommonProcess = nts.uk.at.view.kaf000.shr.viewmodel.CommonProcess;
    import Kaf000AViewModel = nts.uk.at.view.kaf000.a.viewmodel.Kaf000AViewModel;
    import WorkManagement = nts.uk.at.view.kaf004_ref.shr.common.viewmodel.WorkManagement;
    import ApplicationDto = nts.uk.at.view.kaf004_ref.shr.common.viewmodel.ApplicationDto;
    import LateOrEarlyInfo = nts.uk.at.view.kaf004_ref.shr.common.viewmodel.LateOrEarlyInfo;
    import ArrivedLateLeaveEarlyInfo = nts.uk.at.view.kaf004_ref.shr.common.viewmodel.ArrivedLateLeaveEarlyInfo;
    import AppType = nts.uk.at.view.kaf000.shr.viewmodel.model.AppType;

    @bean()
    class KAF004AViewModel extends Kaf000AViewModel {
        appType: KnockoutObservable<number> = ko.observable(AppType.EARLY_LEAVE_CANCEL_APPLICATION);
        application: KnockoutObservable<Application>;
        workManagement: WorkManagement;
        workManagementTemp: WorkManagement;
        arrivedLateLeaveEarlyInfo: any;
        appDispInfoStartupOutput: any;
        lateOrEarlyInfos: KnockoutObservableArray<LateOrEarlyInfo>;
        lateOrEarlyInfo1: KnockoutObservable<LateOrEarlyInfo>;
        lateOrEarlyInfo2: KnockoutObservable<LateOrEarlyInfo>;
        lateOrEarlyInfo3: KnockoutObservable<LateOrEarlyInfo>;
        lateOrEarlyInfo4: KnockoutObservable<LateOrEarlyInfo>;
        managementMultipleWorkCycles: KnockoutObservable<Boolean>;
        isSendMail: KnockoutObservable<Boolean>;
        isEnable1: KnockoutObservable<Boolean> = ko.observable(false);
        isEnable2: KnockoutObservable<Boolean> = ko.observable(false);
        isEnable3: KnockoutObservable<Boolean> = ko.observable(false);
        isEnable4: KnockoutObservable<Boolean> = ko.observable(false);
        cancalAppDispSet: boolean = true;

        created(params: any) {
            const vm = this;

            vm.application = ko.observable(new Application(vm.appType()));
            vm.workManagement = new WorkManagement('--:--', '--:--', '--:--', '--:--', null, null, null, null);
            vm.workManagementTemp = new WorkManagement('--:--', '--:--', '--:--', '--:--', null, null, null, null);
            vm.arrivedLateLeaveEarlyInfo = ko.observable(ArrivedLateLeaveEarlyInfo.initArrivedLateLeaveEarlyInfo());
            vm.appDispInfoStartupOutput = ko.observable(CommonProcess.initCommonSetting());

            vm.lateOrEarlyInfo1 = ko.observable(new LateOrEarlyInfo(false, 1, false, false, 0));
            vm.lateOrEarlyInfo2 = ko.observable(new LateOrEarlyInfo(false, 1, false, false, 1));
            vm.lateOrEarlyInfo3 = ko.observable(new LateOrEarlyInfo(false, 2, false, false, 0));
            vm.lateOrEarlyInfo4 = ko.observable(new LateOrEarlyInfo(false, 2, false, false, 1));
            vm.lateOrEarlyInfos = ko.observableArray([]);
            vm.managementMultipleWorkCycles = ko.observable(false);
            vm.isSendMail = ko.observable(false);

            // vm.application().prePostAtr.subscribe(() => {
            //     if (ko.toJS(vm.application().prePostAtr) === 0) {
            //         vm.workManagement.clearData();
            //     }
            // });

            vm.$blockui('show');
            let dates: string[] = [];
            if (ko.toJS(vm.application().appDate)) {
                dates.push(ko.toJS(vm.application().appDate));
            }
            vm.loadData([], [], vm.appType())
                .then((loadDataFlag: any) => {
                    let appType = vm.appType,
                        appDates = dates,
                        appDispInfoStartupDto = ko.toJS(vm.appDispInfoStartupOutput),
                        command = { appType, appDates, appDispInfoStartupDto };
                    return vm.$ajax(API.initPage, command);
                }).then((successData: any) => {
                    if (successData) {
                        if (successData.info) {

                        }

                        vm.cancalAppDispSet = successData.lateEarlyCancelAppSet.cancelAtr !== 0;

                        vm.arrivedLateLeaveEarlyInfo(successData);
                        vm.appDispInfoStartupOutput(successData.appDispInfoStartupOutput);
                        vm.lateOrEarlyInfos(vm.arrivedLateLeaveEarlyInfo().earlyInfos);
                        if (vm.lateOrEarlyInfos().length > 0) {

                            vm.lateOrEarlyInfo1().isActive(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 1, 'category': 0 }))[0].isActive);
                            vm.lateOrEarlyInfo1().isCheck(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 1, 'category': 0 }))[0].isCheck);
                            vm.lateOrEarlyInfo1().isIndicated(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 1, 'category': 0 }))[0].isIndicated);
                            vm.lateOrEarlyInfo1().category(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 1, 'category': 0 }))[0].category);
                            vm.lateOrEarlyInfo1().workNo(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 1, 'category': 0 }))[0].workNo);

                            vm.lateOrEarlyInfo2().isActive(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 1, 'category': 1 }))[0].isActive);
                            vm.lateOrEarlyInfo2().isCheck(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 1, 'category': 1 }))[0].isCheck);
                            vm.lateOrEarlyInfo2().isIndicated(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 1, 'category': 1 }))[0].isIndicated);
                            vm.lateOrEarlyInfo2().category(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 1, 'category': 1 }))[0].category);
                            vm.lateOrEarlyInfo2().workNo(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 1, 'category': 1 }))[0].workNo);

                            vm.lateOrEarlyInfo3().isActive(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 2, 'category': 0 }))[0].isActive);
                            vm.lateOrEarlyInfo3().isCheck(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 2, 'category': 0 }))[0].isCheck);
                            vm.lateOrEarlyInfo3().isIndicated(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 2, 'category': 0 }))[0].isIndicated);
                            vm.lateOrEarlyInfo3().category(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 2, 'category': 0 }))[0].category);
                            vm.lateOrEarlyInfo3().workNo(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 2, 'category': 0 }))[0].workNo);

                            vm.lateOrEarlyInfo4().isActive(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 2, 'category': 1 }))[0].isActive);
                            vm.lateOrEarlyInfo4().isCheck(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 2, 'category': 1 }))[0].isCheck);
                            vm.lateOrEarlyInfo4().isIndicated(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 2, 'category': 1 }))[0].isIndicated);
                            vm.lateOrEarlyInfo4().category(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 2, 'category': 1 }))[0].category);
                            vm.lateOrEarlyInfo4().workNo(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 2, 'category': 1 }))[0].workNo);
                        }
                        else {
                            var initDelete = successData.lateEarlyCancelAppSet.cancelAtr;
                            if (initDelete === 1) {
                                vm.lateOrEarlyInfo1().isActive(true);
                                vm.lateOrEarlyInfo1().isCheck(false);
                                vm.lateOrEarlyInfo1().isIndicated(true);
                                vm.lateOrEarlyInfo1().category(0);
                                vm.lateOrEarlyInfo1().workNo(1);

                                vm.lateOrEarlyInfo2().isActive(true);
                                vm.lateOrEarlyInfo2().isCheck(false);
                                vm.lateOrEarlyInfo2().isIndicated(true);
                                vm.lateOrEarlyInfo2().category(1);
                                vm.lateOrEarlyInfo2().workNo(1);

                                vm.lateOrEarlyInfo3().isActive(true);
                                vm.lateOrEarlyInfo3().isCheck(false);
                                vm.lateOrEarlyInfo3().isIndicated(true);
                                vm.lateOrEarlyInfo3().category(0);
                                vm.lateOrEarlyInfo3().workNo(2);

                                vm.lateOrEarlyInfo4().isActive(true);
                                vm.lateOrEarlyInfo4().isCheck(false);
                                vm.lateOrEarlyInfo4().isIndicated(true);
                                vm.lateOrEarlyInfo4().category(1);
                                vm.lateOrEarlyInfo4().workNo(2);
                            } else if (initDelete === 2) {
                                vm.lateOrEarlyInfo1().isActive(true);
                                vm.lateOrEarlyInfo1().isCheck(true);
                                vm.lateOrEarlyInfo1().isIndicated(true);
                                vm.lateOrEarlyInfo1().category(0);
                                vm.lateOrEarlyInfo1().workNo(1);

                                vm.lateOrEarlyInfo2().isActive(true);
                                vm.lateOrEarlyInfo2().isCheck(true);
                                vm.lateOrEarlyInfo2().isIndicated(true);
                                vm.lateOrEarlyInfo2().category(1);
                                vm.lateOrEarlyInfo2().workNo(1);

                                vm.lateOrEarlyInfo3().isActive(true);
                                vm.lateOrEarlyInfo3().isCheck(true);
                                vm.lateOrEarlyInfo3().isIndicated(true);
                                vm.lateOrEarlyInfo3().category(0);
                                vm.lateOrEarlyInfo3().workNo(2);

                                vm.lateOrEarlyInfo4().isActive(true);
                                vm.lateOrEarlyInfo4().isCheck(true);
                                vm.lateOrEarlyInfo4().isIndicated(true);
                                vm.lateOrEarlyInfo4().category(1);
                                vm.lateOrEarlyInfo4().workNo(2);
                            }
                        }

                        if (!vm.arrivedLateLeaveEarlyInfo().appDispInfoStartupOutput
                            || !vm.arrivedLateLeaveEarlyInfo().appDispInfoStartupOutput.appDispInfoWithDateOutput
                            || !vm.arrivedLateLeaveEarlyInfo().appDispInfoStartupOutput.appDispInfoWithDateOutput.actualContentDisplay
                            || !vm.arrivedLateLeaveEarlyInfo().appDispInfoStartupOutput.appDispInfoWithDateOutput.actualContentDisplay.achievementDetail
                            || !vm.arrivedLateLeaveEarlyInfo().appDispInfoStartupOutput.appDispInfoWithDateOutput.actualContentDisplay.achievementDetail.achievementEarly) {
                            vm.workManagement.scheAttendanceTime("--:--");
                            vm.workManagement.scheAttendanceTime2("--:--");
                            vm.workManagement.scheWorkTime("--:--");
                            vm.workManagement.scheWorkTime2("--:--");
                        } else {
                            vm.workManagement.scheAttendanceTime(vm.arrivedLateLeaveEarlyInfo().appDispInfoStartupOutput.appDispInfoWithDateOutput.opAchievementOutputLst.achievementDetail.achievementEarly.scheAttendanceTime1);
                            vm.workManagement.scheAttendanceTime2(vm.arrivedLateLeaveEarlyInfo().appDispInfoStartupOutput.appDispInfoWithDateOutput.actualContentDisplay.achievementDetail.achievementEarly.scheAttendanceTime2);
                            vm.workManagement.scheWorkTime(vm.arrivedLateLeaveEarlyInfo().appDispInfoStartupOutput.appDispInfoWithDateOutput.actualContentDisplay.achievementDetail.achievementEarly.scheDepartureTime1);
                            vm.workManagement.scheWorkTime2(vm.arrivedLateLeaveEarlyInfo().appDispInfoStartupOutput.appDispInfoWithDateOutput.actualContentDisplay.achievementDetail.achievementEarly.scheDepartureTime2);

                            vm.workManagement.workTime = vm.arrivedLateLeaveEarlyInfo().appDispInfoStartupOutput.appDispInfoWithDateOutput.opAchievementOutputLst.startTime1;
                            vm.workManagement.workTime2 = vm.arrivedLateLeaveEarlyInfo().appDispInfoStartupOutput.appDispInfoWithDateOutput.opAchievementOutputLst.startTime2;
                            vm.workManagement.leaveTime = vm.arrivedLateLeaveEarlyInfo().appDispInfoStartupOutput.appDispInfoWithDateOutput.opAchievementOutputLst.endTime1;
                            vm.workManagement.leaveTime2 = vm.arrivedLateLeaveEarlyInfo().appDispInfoStartupOutput.appDispInfoWithDateOutput.opAchievementOutputLst.endTime2;

                            vm.workManagementTemp.workTime = vm.arrivedLateLeaveEarlyInfo().appDispInfoStartupOutput.appDispInfoWithDateOutput.opAchievementOutputLst.startTime1;
                            vm.workManagementTemp.workTime2 = vm.arrivedLateLeaveEarlyInfo().appDispInfoStartupOutput.appDispInfoWithDateOutput.opAchievementOutputLst.startTime2;
                            vm.workManagementTemp.leaveTime = vm.arrivedLateLeaveEarlyInfo().appDispInfoStartupOutput.appDispInfoWithDateOutput.opAchievementOutputLst.endTime1;
                            vm.workManagementTemp.leaveTime2 = vm.arrivedLateLeaveEarlyInfo().appDispInfoStartupOutput.appDispInfoWithDateOutput.opAchievementOutputLst.endTime2;
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

                        if (_.isEmpty(vm.workManagement.workTime())) {
                            vm.lateOrEarlyInfo1().isActive(false);
                        }
                        if (_.isEmpty(vm.workManagement.leaveTime())) {
                            vm.lateOrEarlyInfo2().isActive(false);
                        }
                        if (_.isEmpty(vm.workManagement.workTime2())) {
                            vm.lateOrEarlyInfo3().isActive(false);
                        }
                        if (_.isEmpty(vm.workManagement.leaveTime2())) {
                            vm.lateOrEarlyInfo4().isActive(false);
                        }

                        // vm.application().prePostAtr(successData.appDispInfoStartupOutput.appDispInfoNoDateOutput.applicationSetting.appTypeSetting.displayInitialSegment);
                    }
                }).fail((failData: any) => {
                    console.log(failData);

                }).always(() => vm.$blockui("hide"));

            vm.application().appDate.subscribe(() => {
                vm.$blockui("show");

                let command = {
                    appType: vm.application().appType,
                    appDates: [ko.toJS(vm.application().appDate)],
                    baseDate: vm.application().appDate(),
                    appDispNoDate: vm.appDispInfoStartupOutput().appDispInfoNoDateOutput,
                    appDispWithDate: vm.appDispInfoStartupOutput().appDispInfoWithDateOutput,
                    setting: vm.arrivedLateLeaveEarlyInfo().lateEarlyCancelAppSet
                }
                vm.$ajax(API.changeAppDate, command).done((success: any) => {
                    console.log(success);

                    if (success.errorInfo) {
                        if (vm.application().prePostAtr() === 1) {
                            const message: any = {
                                messageId: success.errorInfo,
                                messageParams: [ko.toJS(vm.application().appDate)]
                            };
                            vm.$errors("#kaf000-a-component4-singleDate", message);
                        }
                        vm.workManagement.scheWorkTime("--:--");
                        vm.workManagement.scheWorkTime2("--:--");
                        vm.workManagement.scheAttendanceTime("--:--");
                        vm.workManagement.scheAttendanceTime2("--:--");
                        vm.workManagement.workTime(null);
                        vm.workManagement.workTime2(null);
                        vm.workManagement.leaveTime(null);
                        vm.workManagement.leaveTime2(null);

                        vm.workManagementTemp.workTime(null);
                        vm.workManagementTemp.leaveTime(null);
                        vm.workManagementTemp.workTime2(null);
                        vm.workManagementTemp.leaveTime2(null);

                        vm.arrivedLateLeaveEarlyInfo().info = success.errorInfo;
                    } else {
                        vm.arrivedLateLeaveEarlyInfo().info = null;

                        vm.appDispInfoStartupOutput().appDispInfoWithDateOutput = success.appDispInfoWithDateOutput;
                        vm.lateOrEarlyInfos(success.lateOrEarlyInfoLst);

                        success.appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.achievementEarly.scheAttendanceTime1 == null ? vm.workManagement.scheAttendanceTime('--:--') :
                        vm.workManagement.scheAttendanceTime(nts.uk.time.format.byId("Clock_Short_HM", success.appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.achievementEarly.scheAttendanceTime1));
                        success.appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.achievementEarly.scheDepartureTime1 == null ? vm.workManagement.scheWorkTime('--:--') :
                        vm.workManagement.scheWorkTime(nts.uk.time.format.byId("Clock_Short_HM", success.appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.achievementEarly.scheDepartureTime1));
                        success.appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.achievementEarly.scheAttendanceTime2 == null ? vm.workManagement.scheAttendanceTime2('--:--') :
                        vm.workManagement.scheAttendanceTime2(nts.uk.time.format.byId("Clock_Short_HM", success.appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.achievementEarly.scheAttendanceTime2));
                        success.appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.achievementEarly.scheDepartureTime2 == null ? vm.workManagement.scheWorkTime2('--:--') :
                        vm.workManagement.scheWorkTime2(nts.uk.time.format.byId("Clock_Short_HM", success.appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.achievementEarly.scheDepartureTime2));

                        vm.workManagement.workTime(null);
                        vm.workManagement.leaveTime(null);
                        vm.workManagement.workTime2(null);
                        vm.workManagement.leaveTime2(null);

                        if(vm.application().prePostAtr() == 1) {
                            vm.workManagement.workTime(success.appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.opWorkTime);
                            vm.workManagement.leaveTime(success.appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.opLeaveTime);
                            vm.workManagement.workTime2(success.appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.opWorkTime2);
                            vm.workManagement.leaveTime2(success.appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.opDepartureTime2);
                        }

                        vm.workManagementTemp.workTime(success.appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.opWorkTime);
                        vm.workManagementTemp.leaveTime(success.appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.opLeaveTime);
                        vm.workManagementTemp.workTime2(success.appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.opWorkTime2);
                        vm.workManagementTemp.leaveTime2(success.appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.opDepartureTime2);

                        if (vm.lateOrEarlyInfos().length > 0) {
                            if (ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 1, 'category': 0 }).length > 0)) {
                                vm.lateOrEarlyInfo1().isActive(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 1, 'category': 0 }))[0].isActive);
                                vm.lateOrEarlyInfo1().isCheck(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 1, 'category': 0 }))[0].isCheck);
                                vm.lateOrEarlyInfo1().isIndicated(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 1, 'category': 0 }))[0].isIndicated);
                                vm.lateOrEarlyInfo1().category(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 1, 'category': 0 }))[0].category);
                                vm.lateOrEarlyInfo1().workNo(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 1, 'category': 0 }))[0].workNo);
                            } else {
                                vm.lateOrEarlyInfo1().isIndicated(false);
                            }

                            if (ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 1, 'category': 1 }).length > 0)) {
                                vm.lateOrEarlyInfo2().isActive(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 1, 'category': 1 }))[0].isActive);
                                vm.lateOrEarlyInfo2().isCheck(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 1, 'category': 1 }))[0].isCheck);
                                vm.lateOrEarlyInfo2().isIndicated(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 1, 'category': 1 }))[0].isIndicated);
                                vm.lateOrEarlyInfo2().category(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 1, 'category': 1 }))[0].category);
                                vm.lateOrEarlyInfo2().workNo(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 1, 'category': 1 }))[0].workNo);
                            } else {
                                vm.lateOrEarlyInfo2().isIndicated(false);
                            }

                            if (ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 2, 'category': 0 }).length > 0)) {
                                vm.lateOrEarlyInfo3().isActive(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 2, 'category': 0 }))[0].isActive);
                                vm.lateOrEarlyInfo3().isCheck(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 2, 'category': 0 }))[0].isCheck);
                                vm.lateOrEarlyInfo3().isIndicated(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 2, 'category': 0 }))[0].isIndicated);
                                vm.lateOrEarlyInfo3().category(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 2, 'category': 0 }))[0].category);
                                vm.lateOrEarlyInfo3().workNo(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 2, 'category': 0 }))[0].workNo);
                            } else {
                                vm.lateOrEarlyInfo3().isIndicated(false);
                            }

                            if (ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 2, 'category': 1 }).length > 0)) {
                                vm.lateOrEarlyInfo4().isActive(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 2, 'category': 1 }))[0].isActive);
                                vm.lateOrEarlyInfo4().isCheck(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 2, 'category': 1 }))[0].isCheck);
                                vm.lateOrEarlyInfo4().isIndicated(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 2, 'category': 1 }))[0].isIndicated);
                                vm.lateOrEarlyInfo4().category(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 2, 'category': 1 }))[0].category);
                                vm.lateOrEarlyInfo4().workNo(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 2, 'category': 1 }))[0].workNo);
                            } else {
                                vm.lateOrEarlyInfo4().isIndicated(false);
                            }

                        }
                        vm.arrivedLateLeaveEarlyInfo().appDispInfoStartupOutput.appDispInfoWithDateOutput = success.appDispInfoWithDateOutput;
                    }

                    if (vm.workManagement.workTime() === null || vm.workManagement.workTime() === "") {
                        vm.lateOrEarlyInfo1().isIndicated(true);
                        vm.lateOrEarlyInfo1().isActive(false);
                        vm.lateOrEarlyInfo1().isCheck(false);
                        vm.isEnable1(false);
                    } else {
                        vm.lateOrEarlyInfo1().isActive(true);
                        vm.isEnable1(true);
                    }
                    if (vm.workManagement.leaveTime() === null || vm.workManagement.leaveTime() === "") {
                        vm.lateOrEarlyInfo2().isIndicated(true);
                        vm.lateOrEarlyInfo2().isActive(false);
                        vm.lateOrEarlyInfo2().isCheck(false);
                        vm.isEnable2(false);
                    } else {
                        vm.lateOrEarlyInfo2().isActive(true);
                        vm.isEnable2(true);
                    }
                    if (vm.workManagement.workTime2() === null || vm.workManagement.workTime2() === "") {
                        vm.lateOrEarlyInfo3().isIndicated(true);
                        vm.lateOrEarlyInfo3().isActive(false);
                        vm.lateOrEarlyInfo3().isCheck(false);
                        vm.isEnable3(false);
                    } else {
                        vm.lateOrEarlyInfo3().isActive(true);
                        vm.isEnable3(true);
                    }
                    if (vm.workManagement.leaveTime2() === null || vm.workManagement.leaveTime2() === "") {
                        vm.lateOrEarlyInfo4().isIndicated(true);
                        vm.lateOrEarlyInfo4().isActive(false);
                        vm.lateOrEarlyInfo4().isCheck(false);
                        vm.isEnable4(false);
                    } else {
                        vm.lateOrEarlyInfo4().isActive(true);
                        vm.isEnable4(true);
                    }

                }).fail((error: any) => {
                    console.log(error);
                    const message: any = {
                        messageId: error.messageId,
                        messageParams: [ko.toJS(vm.application().appDate)]
                    };
                    vm.arrivedLateLeaveEarlyInfo().info = error.messageId;
                    vm.$dialog.error(message);
                }).always(() => vm.$blockui("hide"));
            });

            vm.isEnable1(ko.toJS(vm.condition13(1)));
            vm.isEnable2(ko.toJS(vm.condition13(2)));
            vm.isEnable3(ko.toJS(vm.condition13(3)));
            vm.isEnable4(ko.toJS(vm.condition13(4)));

        }

        mounted() {
            const vm = this;

            if (vm.workManagement.workTime() === null) {
                vm.lateOrEarlyInfo1().isActive(false);
            } else {
                vm.lateOrEarlyInfo1().isActive(true);
            }

            if (vm.workManagement.leaveTime() === null) {
                vm.lateOrEarlyInfo2().isActive(false);
            } else {
                vm.lateOrEarlyInfo2().isActive(true);
            }

            if (vm.workManagement.workTime2() === null) {
                vm.lateOrEarlyInfo3().isActive(false);
            } else {
                vm.lateOrEarlyInfo3().isActive(true);
            }

            if (vm.workManagement.leaveTime2() === null) {
                vm.lateOrEarlyInfo4().isActive(false);
            } else {
                vm.lateOrEarlyInfo4().isActive(true);
            }

            vm.application().prePostAtr.subscribe(() => {
                if (vm.application().prePostAtr() === 0) {
                    vm.workManagement.workTime(null);
                    vm.workManagement.leaveTime(null);
                    vm.workManagement.workTime2(null);
                    vm.workManagement.leaveTime2(null);

                    vm.isEnable1(false);
                    vm.isEnable2(false);
                    vm.isEnable3(false);
                    vm.isEnable4(false);
                    vm.$errors("clear", ["#kaf000-a-component4-singleDate"]);
                } else {
                    vm.workManagement.workTime(vm.workManagementTemp.workTime());
                    vm.workManagement.leaveTime(vm.workManagementTemp.leaveTime());
                    vm.workManagement.workTime2(vm.workManagementTemp.workTime2());
                    vm.workManagement.leaveTime2(vm.workManagementTemp.leaveTime2());

                    if(vm.workManagementTemp.workTime() !== null && vm.workManagementTemp.workTime() !== "") {
                        vm.isEnable1(true);
                        vm.lateOrEarlyInfo1().isActive(true);
                        vm.lateOrEarlyInfo1().isCheck(true);
                    } else {
                        vm.isEnable1(false)
                    }
                    if(vm.workManagementTemp.leaveTime() !== null && vm.workManagementTemp.leaveTime() !== "") {
                        vm.isEnable2(true);
                        vm.lateOrEarlyInfo2().isActive(true);
                        vm.lateOrEarlyInfo2().isCheck(true);
                    } else {
                        vm.isEnable2(false)
                    }
                    if(vm.workManagementTemp.workTime2() !== null && vm.workManagementTemp.workTime2() !== "") {
                        vm.isEnable3(true);
                        vm.lateOrEarlyInfo3().isActive(true);
                        vm.lateOrEarlyInfo3().isCheck(true);
                    } else {
                        vm.isEnable3(false)
                    }
                    if(vm.workManagementTemp.leaveTime2() !== null && vm.workManagementTemp.leaveTime2() !== "") {
                        vm.isEnable4(true);
                        vm.lateOrEarlyInfo4().isActive(true);
                        vm.lateOrEarlyInfo4().isCheck(true);
                    } else {
                        vm.isEnable4(false)
                    }

                    if (vm.arrivedLateLeaveEarlyInfo().info && vm.arrivedLateLeaveEarlyInfo().info === "Msg_1707") {
                        const message: any = {
                            messageId: vm.arrivedLateLeaveEarlyInfo().info,
                            messageParams: [ko.toJS(vm.application().appDate)]
                        };
                        vm.$errors("#kaf000-a-component4-singleDate", message);
                    }
                }
            });

            vm.lateOrEarlyInfo1().isCheck.subscribe(() => {
                if (vm.lateOrEarlyInfo1().isIndicated() && ko.toJS(vm.condition10Activation(1)) && vm.lateOrEarlyInfo1().isCheck()) {
                    vm.isEnable1(true);
                } else {
                    vm.isEnable1(false);
                }
            });
            vm.condition10Activation(1).subscribe(() => {
                if (vm.lateOrEarlyInfo1().isIndicated() && ko.toJS(vm.condition10Activation(1)) && vm.lateOrEarlyInfo1().isCheck()) {
                    vm.isEnable1(true);
                }
            });

            vm.lateOrEarlyInfo2().isCheck.subscribe(() => {
                if (vm.lateOrEarlyInfo2().isIndicated() && ko.toJS(vm.condition10Activation(2)) && vm.lateOrEarlyInfo2().isCheck()) {
                    vm.isEnable2(true);
                } else {
                    vm.isEnable2(false);
                }
            });
            vm.condition10Activation(2).subscribe(() => {
                if (vm.lateOrEarlyInfo2().isIndicated() && ko.toJS(vm.condition10Activation(2)) && vm.lateOrEarlyInfo2().isCheck()) {
                    vm.isEnable2(true);
                }
            });

            vm.lateOrEarlyInfo3().isCheck.subscribe(() => {
                if (vm.lateOrEarlyInfo3().isIndicated() && ko.toJS(vm.condition10Activation(3)) && vm.lateOrEarlyInfo3().isCheck()) {
                    vm.isEnable3(true);
                } else {
                    vm.isEnable3(false);
                }
            });
            vm.condition10Activation(3).subscribe(() => {
                if (vm.lateOrEarlyInfo3().isIndicated() && ko.toJS(vm.condition10Activation(3)) && vm.lateOrEarlyInfo3().isCheck()) {
                    vm.isEnable3(true);
                }
            });

            vm.lateOrEarlyInfo4().isCheck.subscribe(() => {
                if (vm.lateOrEarlyInfo4().isIndicated() && ko.toJS(vm.condition10Activation(4)) && vm.lateOrEarlyInfo4().isCheck()) {
                    vm.isEnable4(true);
                } else {
                    vm.isEnable4(false);
                }
            });
            vm.condition10Activation(4).subscribe(() => {
                if (vm.lateOrEarlyInfo4().isIndicated() && ko.toJS(vm.condition10Activation(4)) && vm.lateOrEarlyInfo4().isCheck()) {
                    vm.isEnable4(true);
                }
            });
        }

        register() {
            const vm = this;

            if (vm.application().prePostAtr() === 1 && vm.arrivedLateLeaveEarlyInfo().info && vm.arrivedLateLeaveEarlyInfo().info === "Msg_1707") {
                const message: any = {
                    messageId: vm.arrivedLateLeaveEarlyInfo().info,
                    messageParams: [ko.toJS(vm.application().appDate)]
                };
                vm.$errors("#kaf000-a-component4-singleDate", message);

                return;
            }
            vm.$validate([
                '.ntsControl',
                '.nts-input'
            ]).then((valid: boolean) => {
                if (valid) {

                    vm.application.prototype.inputDate = ko.observable(moment(new Date()).format("yyyy/MM/dd HH:mm:ss"));

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
                        if (ko.toJS(vm.lateOrEarlyInfo3().isCheck)) {
                            lateCancelation.push({
                                workNo: 2,
                                lateOrEarlyClassification: 0
                            })
                            _.remove(lateOrLeaveEarlies, (x) => {
                                return (x.workNo === 2 && x.lateOrEarlyClassification === 0);
                            });
                        }
                        if (ko.toJS(vm.lateOrEarlyInfo4().isCheck)) {
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

                    vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly = arrivedLateLeaveEarly;

                    let application: ApplicationDto = new ApplicationDto(null, null, ko.toJS(vm.application().prePostAtr), vm.appDispInfoStartupOutput().appDispInfoNoDateOutput.employeeInfoLst[0].sid,
                        ko.toJS(vm.application().appType), ko.toJS(vm.application().appDate), null, null, null, null, ko.toJS(vm.application().opReversionReason), ko.toJS(vm.application().appDate), ko.toJS(vm.application().appDate), ko.toJS(vm.application().opAppReason), ko.toJS(vm.application().opAppStandardReasonCD));
                    let command = {
                        agentAtr: true,
                        isNew: true,
                        application: application,
                        infoOutput: ko.toJS(vm.arrivedLateLeaveEarlyInfo)
                    };

                    vm.$blockui("show");
                    vm.$validate("#kaf000-a-component4-singleDate", ".nts-input", "#kaf000-a-component3-prePost")
                        .then(isValid => {
                            if (isValid) {
                                return true;
                            }
                        }).then(result => {
                            if (result) {
                                vm.$ajax(API.getMsgList + "/" + ko.toJS(vm.application().appType), command
                                ).done((success: any) => {
                                    if (success) {
                                        console.log(success);

                                        // for (var i = 0; i < success.length; i++) {
                                        //     vm.$dialog.confirm({ messageId: success[i] }).then((result: 'no' | 'yes' | 'cancel') => {
                                        //         if (result !== 'yes') {
                                        //             return;
                                        //         }
                                        //     });
                                        // }
                                        vm.showConfirmResult(success, vm);

                                        this.afterRegister(application);
                                    } else {
                                        this.afterRegister(application);
                                    }
                                }).fail((fail: any) => {
                                    console.log(fail);
                                    if (fail) {
                                        const message: any = {
                                            messageId: fail.messageId,
                                            messageParams: [ko.toJS(vm.application().appDate)]
                                        };
                                        vm.$dialog.error(message);
                                    }
                                })
                            }
                        }).always(() => vm.$blockui("hide"));
                }
            });


        }

        private afterRegister(params?: any) {
            const vm = this;

            if (ko.toJS(vm.application().prePostAtr) === 1) {
                if (ko.toJS(vm.lateOrEarlyInfo1().isCheck)) {
                    vm.workManagement.workTime(null);
                }
                if (ko.toJS(vm.lateOrEarlyInfo2().isCheck)) {
                    vm.workManagement.leaveTime(null);
                }
                if (ko.toJS(vm.lateOrEarlyInfo3().isCheck)) {
                    vm.workManagement.workTime2(null);
                }
                if (ko.toJS(vm.lateOrEarlyInfo4().isCheck)) {
                    vm.workManagement.leaveTime2(null);
                }
            }

            vm.arrivedLateLeaveEarlyInfo().earlyInfos = [];
            if(vm.cancalAppDispSet) {
                vm.arrivedLateLeaveEarlyInfo().earlyInfos.push(ko.toJS(vm.lateOrEarlyInfo1));
                vm.arrivedLateLeaveEarlyInfo().earlyInfos.push(ko.toJS(vm.lateOrEarlyInfo2));
                vm.arrivedLateLeaveEarlyInfo().earlyInfos.push(ko.toJS(vm.lateOrEarlyInfo3));
                vm.arrivedLateLeaveEarlyInfo().earlyInfos.push(ko.toJS(vm.lateOrEarlyInfo4));
            }

            vm.$ajax(API.register,
                {
                    appType: ko.toJS(vm.application().appType),
                    application: params,
                    infoOutput: ko.toJS(vm.arrivedLateLeaveEarlyInfo)
                }).done((success: any) => {
                    if (success) {
                        vm.$dialog.info({ messageId: "Msg_15" }).then(() => {
                            if (ko.toJS(vm.isSendMail)
                                && !vm.arrivedLateLeaveEarlyInfo().appDispInfoStartupOutput.appDispInfoNoDateOutput.applicationSetting.appTypeSetting.sendMailWhenRegister
                                // && false
                            ) {
                                vm.$window.storage("KDL030_PARAM", {
                                    appID: success.appID
                                });
                                nts.uk.ui.windows.sub.modal("/view/kdl/030/a/index.xhtml")
                                    .onClosed(() => { location.reload() });
                            };
                        }).then(() => {
                            window.location.reload();
                        });
                    }
                }).fail((fail: any) => {
                    console.log(fail);
                    const message: any = {
                        messageId: fail.messageId,
                        messageParams: [ko.toJS(vm.application().appDate)]
                    };
                    vm.$dialog.error(message);

                    return;
                })
        }

        // ※2
        public condition2(): boolean {
            const vm = this;

            vm.managementMultipleWorkCycles(vm.appDispInfoStartupOutput().appDispInfoNoDateOutput.managementMultipleWorkCycles);

            // 「遅刻早退取消申請起動時の表示情報」.申請表示情報.申請設定（基準日関係なし）.複数回勤務の管理＝true
            return ko.toJS(vm.managementMultipleWorkCycles());
        }

        // ※8
        public condition8() {
            // 事前事後区分に「事後」に選択している場合　（事後モード）
            return ko.toJS(this.application().prePostAtr) === 1 && this.cancalAppDispSet;
        }

        // ※13
        public condition13(idItem: number) {
            const vm = this;
            return ko.computed(() => {
                if (vm.application().prePostAtr() === 1) {
                    switch (idItem) {
                        case IdItem.A6_7: {
                            return vm.workManagement.workTime() !== null && vm.lateOrEarlyInfo1().isIndicated() && vm.lateOrEarlyInfo1().isActive() && vm.lateOrEarlyInfo1().isCheck();
                            // return _.isEmpty(ko.toJS(this.workManagement.workTime));
                        } case IdItem.A6_13: {
                            return vm.workManagement.leaveTime() !== null && vm.lateOrEarlyInfo2().isIndicated() && vm.lateOrEarlyInfo2().isActive() && vm.lateOrEarlyInfo2().isCheck();
                            // return _.isEmpty(ko.toJS(this.workManagement.leaveTime));
                        } case IdItem.A6_19: {
                            return vm.workManagement.workTime2() !== null && vm.lateOrEarlyInfo3().isIndicated() && vm.lateOrEarlyInfo3().isActive() && vm.lateOrEarlyInfo3().isCheck();
                            // return _.isEmpty(ko.toJS(this.workManagement.workTime2));
                        } case IdItem.A6_25: {
                            return vm.workManagement.leaveTime2() !== null && vm.lateOrEarlyInfo4().isIndicated() && vm.lateOrEarlyInfo4().isActive() && vm.lateOrEarlyInfo4().isCheck();
                            // return _.isEmpty(ko.toJS(this.workManagement.leaveTime2));
                        } default: {
                            return false;
                        }
                    }
                }

                return false;
            });
        }

        // ※10 display
        public condition10Display(idItem: number) {
            const vm = this;

            // 取り消す初期情報.表示する
            switch (idItem) {
                case IdItem.A6_7: {
                    if (ko.toJS(vm.lateOrEarlyInfo1) === null) {
                        return false;
                    }
                    return ko.toJS(vm.lateOrEarlyInfo1().isIndicated);
                } case IdItem.A6_13: {
                    if (!ko.toJS(vm.lateOrEarlyInfo2) === null) {
                        return false;
                    }
                    return ko.toJS(vm.lateOrEarlyInfo2().isIndicated);
                } case IdItem.A6_19: {
                    if (!ko.toJS(vm.lateOrEarlyInfo3) === null) {
                        return false;
                    }
                    return ko.toJS(vm.lateOrEarlyInfo3().isIndicated);
                } case IdItem.A6_25: {
                    if (!ko.toJS(vm.lateOrEarlyInfo4) === null) {
                        return false;
                    }
                    return ko.toJS(vm.lateOrEarlyInfo4().isIndicated
                    );
                } default: {
                    return true;
                }
            }

            // 取り消す初期情報.表示する
            // return true;
        }

        // ※10 activation
        public condition10Activation(idItem: number) {
            const vm = this;

            return ko.computed(() => {
                if (vm.application().prePostAtr() === 1) {
                    // 取り消す初期情報.活性する
                    switch (idItem) {
                        case IdItem.A6_7: {
                            // if (ko.toJS(vm.lateOrEarlyInfo1) == null) {
                            //     return false;
                            // }
                            // if (vm.workManagement.workTime() == null || vm.workManagement.workTime() === "") {
                            //     return false;
                            // }
                            return ko.toJS(vm.lateOrEarlyInfo1().isActive);
                        } case IdItem.A6_13: {
                            // if (ko.toJS(vm.lateOrEarlyInfo2) == null) {
                            //     return false;
                            // }
                            // if (vm.workManagement.leaveTime() == null || vm.workManagement.leaveTime() === "") {
                            //     return false;
                            // }
                            return ko.toJS(vm.lateOrEarlyInfo2().isActive);
                        } case IdItem.A6_19: {
                            // if (ko.toJS(vm.lateOrEarlyInfo3) == null) {
                            //     return false;
                            // }
                            // if (vm.workManagement.workTime2() == null || vm.workManagement.workTime2() === "") {
                            //     return false;
                            // }
                            return ko.toJS(vm.lateOrEarlyInfo3().isActive);
                        } case IdItem.A6_25: {
                            // if (ko.toJS(vm.lateOrEarlyInfo4) == null) {
                            //     return false;
                            // }
                            // if (vm.workManagement.leaveTime2() == null || vm.workManagement.leaveTime2() === "") {
                            //     return false;
                            // }
                            return ko.toJS(vm.lateOrEarlyInfo4().isActive);
                        } default: {
                            return true;
                        }
                    }
                } else {
                    return false;
                }
            }, vm);

            // 取り消す初期情報.活性する
            // return true;
        }

        // ※8＆※10
        public condition8_10(idItem: number) {
            return this.condition8() && this.condition10Display(idItem);
            // return true;
        }

        // ※2＆※８&※10
        public condition2_8_10(idItem: number) {
            // return true;
            return this.condition2() && this.condition8() && this.condition10Display(idItem);
        }

        public showConfirmResult(messages: Array<any>, vm: any) {
			return new Promise((resolve: any) => {
				if(_.isEmpty(messages)) {
					resolve(true);
				}
				let msg = messages[0].value,
					type = messages[0].type;
				return vm.$dialog.confirm(msg).then((result: 'no' | 'yes' | 'cancel') => {
					if (result === 'yes') {
		            	return vm.showConfirmResult(_.slice(messages, 1), vm);
		            }
					resolve();
	        	});	
            }).then((data: any) => {
				if(data) {

                }		
			});
		}
    }

    const API = {
        initPage: "at/request/application/lateorleaveearly/initPage",
        changeAppDate: "at/request/application/lateorleaveearly/changeAppDate",
        getMsgList: "at/request/application/lateorleaveearly/getMsgList",
        register: "at/request/application/lateorleaveearly/register"
    };

    export class IdItem {
        public static readonly A6_7: number = 1;
        public static readonly A6_13: number = 2;
        public static readonly A6_19: number = 3;
        public static readonly A6_25: number = 4
    }
}
