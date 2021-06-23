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
         <div id="kaf004-b">
            <div id="contents-area" style="background-color: inherit; height: calc(100vh - 137px);">
                <div class="two-panel" style="height: 100%; width: 1260px">
                    <div class="left-panel" style="width: calc(1260px - 388px); padding-bottom: 5px; height: inherit;">
                        <div style="border: 1px solid #CCC; height: inherit; background-color: #fff; padding: 0 10px; overflow-y: auto; overflow-x: hidden">
                            <div class="table" style="border-bottom: 2px solid #B1B1B1; padding-bottom: 30px; margin-bottom: 30px; width: 100%;">
                                <div class="cell" style="vertical-align: middle;">
                                    <div data-bind="component: { name: 'kaf000-b-component4',
                                                        params: {
                                                            appType: appType,
                                                            application: application,
                                                            appDispInfoStartupOutput: appDispInfoStartupOutput
                                                        } }"></div>
                                </div>
                                <div class="cell" style="text-align: right; vertical-align: middle;">
                                    <div data-bind="component: { name: 'kaf000-b-component8', 
                                                        params: {
                                                            appType: appType,
                                                            appDispInfoStartupOutput: appDispInfoStartupOutput
                                                        } }"></div>
                                </div>
                            </div>
							<div data-bind="component: { name: 'kaf000-b-component2', 
														params: {
															appType: appType,
															appDispInfoStartupOutput: appDispInfoStartupOutput
														} }"></div>
                            <div data-bind="component: { name: 'kaf000-b-component5', 
                                                        params: {
                                                            appType: appType,
                                                            application: application,
                                                            appDispInfoStartupOutput: appDispInfoStartupOutput
                                                        } }" style="margin-bottom: -15px"></div>
                            <div data-bind="component: { name: 'kaf000-b-component6', 
                                                        params: {
                                                            appType: appType,
                                                            application: application,
                                                            appDispInfoStartupOutput: appDispInfoStartupOutput
                                                        } }" style="width: fit-content; display: inline-block; vertical-align: middle; margin-bottom: -10px"></div>
                            <div data-bind="component: {name: 'kaf004_share'}" style="margin: 7px 0"></div>
                            <div class="mv-13" data-bind="component: { name: 'kaf000-b-component7', 
                                                        params: {
                                                            appType: appType,
                                                            application: application,
                                                            appDispInfoStartupOutput: appDispInfoStartupOutput
                                                        } }"></div>
                        </div>
                    </div>
                    <div class="right-panel" style="width: 388px; padding-bottom: 5px; height: inherit; padding-right: 0px">
                        <div style="border: 1px solid #CCC; height: inherit; background-color: #fff; overflow-y: auto; overflow-x: hidden">
                            <div style="margin: 10px" data-bind="component: { name: 'kaf000-b-component1', 
                                    params: {
                                        appType: appType,
                                        appDispInfoStartupOutput: appDispInfoStartupOutput	
                                    } }"></div>
                            <div style="margin: 10px" data-bind="component: { name: 'kaf000-b-component9',
                                    params: {
                                        appType: appType,
                                        application: application,
                                        appDispInfoStartupOutput: appDispInfoStartupOutput
                                    } }"></div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- <div class="fixed-flex-layout" style="margin-left: -10px;" data-bind="component: {name: 'kaf004_share'}"></div> -->
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
        delete1: KnockoutObservable<boolean> = ko.observable(false);
        delete2: KnockoutObservable<boolean> = ko.observable(false);
        delete3: KnockoutObservable<boolean> = ko.observable(false);
        delete4: KnockoutObservable<boolean> = ko.observable(false);
        isSendMail: KnockoutObservable<Boolean>;
        printContentOfEachAppDto: KnockoutObservable<PrintContentOfEachAppDto>;
        approvalReason: KnockoutObservable<string>;
        cancalAppDispSet: KnockoutObservable<boolean> = ko.observable(true);
        outputMode: KnockoutObservable<number> = ko.observable(0);

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

            vm.managementMultipleWorkCycles = ko.observable(false);

            // Subcribe when mode change -> clear data if mode is 'before'
            vm.application().prePostAtr.subscribe(() => {
                if (ko.toJS(vm.application().prePostAtr) === 0) {
                    vm.workManagement.clearData();
                }
            });
            vm.createParamKAF004();
            vm.approvalReason = params.approvalReason;
            // gui event con ra viewmodel cha
            // nhớ dùng bind(vm) để ngữ cảnh lúc thực thi
            // luôn là component
            params.eventUpdate(vm.update.bind(vm));
            params.eventReload(vm.reload.bind(vm));
        }

        reload() {
            const vm = this;
            if (vm.appType() === AppType.EARLY_LEAVE_CANCEL_APPLICATION) {
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
                    vm.$dialog.error({ messageId: err.messageId });
                    console.log()
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
            vm.outputMode(vm.appDispInfoStartupOutput().appDetailScreenInfo.outputMode);

            vm.managementMultipleWorkCycles(params.appDispInfoStartupOutput.appDispInfoNoDateOutput.managementMultipleWorkCycles);

            vm.cancalAppDispSet(params.lateEarlyCancelAppSet.cancelAtr !== 0);
            var achiveEarly = vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail;
            var lateOrLeaveEarlies = params.arrivedLateLeaveEarly.lateOrLeaveEarlies;
            var start1 = _.filter(lateOrLeaveEarlies, { 'workNo': 1, 'lateOrEarlyClassification': 0 });
            var end1 = _.filter(lateOrLeaveEarlies, { 'workNo': 1, 'lateOrEarlyClassification': 1 });
            var start2 = _.filter(lateOrLeaveEarlies, { 'workNo': 2, 'lateOrEarlyClassification': 0 });
            var end2 = _.filter(lateOrLeaveEarlies, { 'workNo': 2, 'lateOrEarlyClassification': 1 });

            if (start1.length > 0) {
                vm.workManagement.workTime(start1[0].timeWithDayAttr);
            } else {
                if (achiveEarly !== null && achiveEarly.opWorkTime !== null && vm.application().prePostAtr() == 1
                    && achiveEarly.trackRecordAtr === 0) {
                    vm.workManagement.workTime(achiveEarly.opWorkTime);
                }
            }
            if (end1.length > 0) {
                vm.workManagement.leaveTime(end1[0].timeWithDayAttr);
            } else {
                if (achiveEarly !== null && achiveEarly.opLeaveTime !== null && vm.application().prePostAtr() == 1
                    && achiveEarly.trackRecordAtr === 0) {
                    vm.workManagement.leaveTime(achiveEarly.opLeaveTime);
                }
            }
            if (start2.length > 0) {
                vm.workManagement.workTime2(start2[0].timeWithDayAttr);
            } else {
                if (achiveEarly !== null && achiveEarly.opWorkTime2 !== null && vm.application().prePostAtr() == 1
                    && achiveEarly.trackRecordAtr === 0) {
                    vm.workManagement.workTime2(achiveEarly.opWorkTime2);
                }
            }
            if (end2.length > 0) {
                vm.workManagement.leaveTime2(end2[0].timeWithDayAttr);
            } else {
                if (achiveEarly !== null && achiveEarly.opDepartureTime2 !== null && vm.application().prePostAtr() == 1
                    && achiveEarly.trackRecordAtr === 0) {
                    vm.workManagement.leaveTime2(achiveEarly.opDepartureTime2);
                }
            }

            var check1 = _.filter(vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly.lateCancelation, { 'workNo': 1, 'lateOrEarlyClassification': 0 });
            var check2 = _.filter(vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly.lateCancelation, { 'workNo': 1, 'lateOrEarlyClassification': 1 });
            var check3 = _.filter(vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly.lateCancelation, { 'workNo': 2, 'lateOrEarlyClassification': 0 });
            var check4 = _.filter(vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly.lateCancelation, { 'workNo': 2, 'lateOrEarlyClassification': 1 });

            if (!_.isEmpty(check1)) {
                vm.delete1(true);
            }
            if (!_.isEmpty(check2)) {
                vm.delete2(true);
            }
            if (!_.isEmpty(check3)) {
                vm.delete3(true);
            }
            if (!_.isEmpty(check4)) {
                vm.delete4(true);
            }

            if (achiveEarly !== null) {
                achiveEarly.achievementEarly.scheAttendanceTime1 == null ? vm.workManagement.scheAttendanceTime('--:--') :
                    vm.workManagement.scheAttendanceTime(nts.uk.time.format.byId("Clock_Short_HM", achiveEarly.achievementEarly.scheAttendanceTime1));
                achiveEarly.achievementEarly.scheDepartureTime1 == null ? vm.workManagement.scheWorkTime('--:--') :
                    vm.workManagement.scheWorkTime(nts.uk.time.format.byId("Clock_Short_HM", achiveEarly.achievementEarly.scheDepartureTime1));
                achiveEarly.achievementEarly.scheAttendanceTime2 == null ? vm.workManagement.scheAttendanceTime2('--:--') :
                    vm.workManagement.scheAttendanceTime2(nts.uk.time.format.byId("Clock_Short_HM", achiveEarly.achievementEarly.scheAttendanceTime2));
                achiveEarly.achievementEarly.scheDepartureTime2 == null ? vm.workManagement.scheWorkTime2('--:--') :
                    vm.workManagement.scheWorkTime2(nts.uk.time.format.byId("Clock_Short_HM", achiveEarly.achievementEarly.scheDepartureTime2));
            }

            // if (achiveEarly !== null && achiveEarly.opWorkTime !== null && vm.application().prePostAtr() == 1
            //     && achiveEarly.trackRecordAtr === 0) {
            //     vm.workManagement.workTime(achiveEarly.opWorkTime);
            // }

            // if (achiveEarly !== null && achiveEarly.opLeaveTime !== null && vm.application().prePostAtr() == 1
            //     && achiveEarly.trackRecordAtr === 0) {
            //     vm.workManagement.leaveTime(achiveEarly.opLeaveTime);
            // }

            // if (achiveEarly !== null && achiveEarly.opWorkTime2 !== null && vm.application().prePostAtr() == 1
            //     && achiveEarly.trackRecordAtr === 0) {
            //     vm.workManagement.workTime2(achiveEarly.opWorkTime2);
            // }

            // if (achiveEarly !== null && achiveEarly.opDepartureTime2 !== null && vm.application().prePostAtr() == 1
            //     && achiveEarly.trackRecordAtr === 0) {
            //     vm.workManagement.leaveTime2(achiveEarly.opDepartureTime2);
            // }

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

            let lateCancelation = [];
            let lateOrLeaveEarlies = [];

            if (ko.toJS(vm.workManagement.workTime) != null && ko.toJS(vm.workManagement.workTime) != "") {
                lateOrLeaveEarlies.push({
                    workNo: 1,
                    lateOrEarlyClassification: 0,
                    timeWithDayAttr: ko.toJS(vm.workManagement.workTime())
                })
            }
            if (ko.toJS(vm.workManagement.leaveTime) != null && ko.toJS(vm.workManagement.leaveTime) != "") {
                lateOrLeaveEarlies.push({
                    workNo: 1,
                    lateOrEarlyClassification: 1,
                    timeWithDayAttr: ko.toJS(vm.workManagement.leaveTime())
                })
            }

            if (vm.managementMultipleWorkCycles()) {
                if (ko.toJS(vm.workManagement.workTime2) != null && ko.toJS(vm.workManagement.workTime2) != "") {
                    lateOrLeaveEarlies.push({
                        workNo: 2,
                        lateOrEarlyClassification: 0,
                        timeWithDayAttr: ko.toJS(vm.workManagement.workTime2())
                    })
                }
                if (ko.toJS(vm.workManagement.leaveTime2) != null && ko.toJS(vm.workManagement.leaveTime2) != "") {
                    lateOrLeaveEarlies.push({
                        workNo: 2,
                        lateOrEarlyClassification: 1,
                        timeWithDayAttr: ko.toJS(vm.workManagement.leaveTime2())
                    })
                }
            }
            if (ko.toJS(vm.application().prePostAtr) === 1) {
                if (vm.delete1()) {
                    lateCancelation.push({
                        workNo: 1,
                        lateOrEarlyClassification: 0
                    }),
                        _.remove(lateOrLeaveEarlies, (x) => {
                            return (x.workNo === 1 && x.lateOrEarlyClassification === 0);
                        });
                }
                if (vm.delete2()) {
                    lateCancelation.push({
                        workNo: 1,
                        lateOrEarlyClassification: 1
                    })
                    _.remove(lateOrLeaveEarlies, (x) => {
                        return (x.workNo === 1 && x.lateOrEarlyClassification === 1);
                    });
                }
                if (vm.managementMultipleWorkCycles() && vm.delete3()) {
                    lateCancelation.push({
                        workNo: 2,
                        lateOrEarlyClassification: 0
                    })
                    _.remove(lateOrLeaveEarlies, (x) => {
                        return (x.workNo === 2 && x.lateOrEarlyClassification === 0);
                    });
                }
                if (vm.managementMultipleWorkCycles() && vm.delete4()) {
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
            if (vm.application().prePostAtr() === 1) {
                if (vm.delete1()) {
                    vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly.lateCancelation.push({ workNo: 1, lateOrEarlyClassification: 0 })
                }
                if (vm.delete2()) {
                    vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly.lateCancelation.push({ workNo: 1, lateOrEarlyClassification: 1 })
                }
                if (vm.delete3()) {
                    vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly.lateCancelation.push({ workNo: 2, lateOrEarlyClassification: 0 })
                }
                if (vm.delete4()) {
                    vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly.lateCancelation.push({ workNo: 2, lateOrEarlyClassification: 1 })
                }
            }

            if (!vm.delete1()) {
                vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly.lateOrLeaveEarlies.push({ 
                    workNo: 1, 
                    lateOrEarlyClassification: 0, 
                    timeWithDayAttr: vm.workManagement.workTime() 
                });
            }
            if (!vm.delete2()) {
                vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly.lateOrLeaveEarlies.push({ 
                    workNo: 1, 
                    lateOrEarlyClassification: 1, 
                    timeWithDayAttr: vm.workManagement.leaveTime() 
                });
            }
            if (!vm.delete3() && vm.condition2()) {
                vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly.lateOrLeaveEarlies.push({ 
                    workNo: 2, 
                    lateOrEarlyClassification: 0, 
                    timeWithDayAttr: vm.workManagement.workTime2() 
                });
            }
            if (!vm.delete4() && vm.condition2()) {
                vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly.lateOrLeaveEarlies.push({ 
                    workNo: 2, 
                    lateOrEarlyClassification: 1, 
                    timeWithDayAttr: vm.workManagement.leaveTime2() 
                });
            }
            vm.$ajax(API.updateInfo,
                {
                    application: params,
                    arrivedLateLeaveEarlyDto: vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly,
                    appDispInfoStartupDto: vm.appDispInfoStartupOutput()
                }).done((success: any) => {
                    if (success) {
                        vm.$dialog.info({ messageId: "Msg_15" }).then(() => {
                            return CommonProcess.handleMailResult(success, vm);
                        });
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
        }

        // ※9
        public condition9() {
            // 事前事後区分に「事後」に選択している場合　（事後モード）
            return this.application().prePostAtr() === 1 && this.cancalAppDispSet();
        }
    }

    const API = {
        initPageB: "at/request/application/lateorleaveearly/initPageB",
        getMsgList: "at/request/application/lateorleaveearly/getMsgList",
        updateInfo: "at/request/application/lateorleaveearly/updateInfoApp",
        exportFile: "at/request/application/lateorleaveearly/exportFile"
    };
}
