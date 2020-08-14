/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kaf004_ref.a.viewmodel {
    import Application = nts.uk.at.view.kaf000_ref.shr.viewmodel.Application;
    import CommonProcess = nts.uk.at.view.kaf000_ref.shr.viewmodel.CommonProcess;
    import Kaf000AViewModel = nts.uk.at.view.kaf000_ref.a.viewmodel.Kaf000AViewModel;
    import WorkManagement = nts.uk.at.view.kaf004_ref.shr.common.viewmodel.WorkManagement;
    import ApplicationDto = nts.uk.at.view.kaf004_ref.shr.common.viewmodel.ApplicationDto;
    import LateOrEarlyInfo = nts.uk.at.view.kaf004_ref.shr.common.viewmodel.LateOrEarlyInfo;
    import ArrivedLateLeaveEarlyInfo = nts.uk.at.view.kaf004_ref.shr.common.viewmodel.ArrivedLateLeaveEarlyInfo;
    import AppType = nts.uk.at.view.kaf000_ref.shr.viewmodel.model.AppType;

    @bean()
    class KAF004AViewModel extends Kaf000AViewModel {
        application: KnockoutObservable<Application>;
        workManagement: WorkManagement;
        arrivedLateLeaveEarlyInfo: any;
        appDispInfoStartupOutput: any;
        lateOrEarlyInfos: KnockoutObservableArray<LateOrEarlyInfo>;
        lateOrEarlyInfo1: KnockoutObservable<LateOrEarlyInfo>;
        lateOrEarlyInfo2: KnockoutObservable<LateOrEarlyInfo>;
        lateOrEarlyInfo3: KnockoutObservable<LateOrEarlyInfo>;
        lateOrEarlyInfo4: KnockoutObservable<LateOrEarlyInfo>;
        managementMultipleWorkCycles: KnockoutObservable<Boolean>;
        isSendMail: KnockoutObservable<Boolean>;

        created(params: any) {
            const vm = this;

            vm.application = ko.observable(new Application(AppType.EARLY_LEAVE_CANCEL_APPLICATION));
            // vm.application().appDate(moment(new Date()).format("YYYY/MM/DD"));
            // vm.workManagement = new WorkManagement('--:--', '--:--', '--:--', '--:--', null, null, null, null);
            vm.workManagement = new WorkManagement('8:30', '17:30', '--:--', '--:--', null, null, null, null);
            vm.arrivedLateLeaveEarlyInfo = ko.observable(ArrivedLateLeaveEarlyInfo.initArrivedLateLeaveEarlyInfo());
            vm.appDispInfoStartupOutput = ko.observable(CommonProcess.initCommonSetting());

            vm.lateOrEarlyInfo1 = ko.observable(new LateOrEarlyInfo(true, 1, true, true, 0));
            vm.lateOrEarlyInfo2 = ko.observable(new LateOrEarlyInfo(true, 1, true, true, 1));
            vm.lateOrEarlyInfo3 = ko.observable(new LateOrEarlyInfo(true, 2, true, true, 0));
            vm.lateOrEarlyInfo4 = ko.observable(new LateOrEarlyInfo(true, 2, true, true, 1));
            vm.lateOrEarlyInfos = ko.observableArray([]);
            vm.managementMultipleWorkCycles = ko.observable(true);
            vm.isSendMail = ko.observable(true);

            vm.application().prePostAtr.subscribe(() => {
                if (ko.toJS(vm.application().prePostAtr) === 0) {
                    vm.workManagement.clearData();
                }
            });

            vm.$blockui('show');
            let dates: string[] = [];
            if (ko.toJS(vm.application().appDate)) {
                dates.push(ko.toJS(vm.application().appDate));
            }
            vm.loadData([], [], AppType.EARLY_LEAVE_CANCEL_APPLICATION)
                .then((loadDataFlag: any) => {
                    if (loadDataFlag) {
                        let appType = AppType.EARLY_LEAVE_CANCEL_APPLICATION,
                            appDates = dates,
                            appDispInfoStartupDto = ko.toJS(vm.appDispInfoStartupOutput),
                            command = { appType, appDates, appDispInfoStartupDto };
                        return vm.$ajax(API.initPage, command);
                    }
                }).then((successData: any) => {
                    if (successData) {
                        if (successData.info) {

                        }
                        // vm.application().appDate(moment(new Date()).format("YYYY/MM/DD"));

                        vm.arrivedLateLeaveEarlyInfo(successData);
                        vm.appDispInfoStartupOutput(successData.appDispInfoStartupOutput);
                        // vm.lateOrEarlyInfos(vm.arrivedLateLeaveEarlyInfo().earlyInfos);
                        // vm.lateOrEarlyInfo1(ko.toJS(_.filter(vm.lateOrEarlyInfos, { 'workNo': 1, 'category': 0 })));
                        // vm.lateOrEarlyInfo2(ko.toJS(_.filter(vm.lateOrEarlyInfos, { 'workNo': 1, 'category': 1 })));
                        // vm.lateOrEarlyInfo3(ko.toJS(_.filter(vm.lateOrEarlyInfos, { 'workNo': 2, 'category': 0 })));
                        // vm.lateOrEarlyInfo4(ko.toJS(_.filter(vm.lateOrEarlyInfos, { 'workNo': 2, 'category': 1 })));

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
                    }
                }).fail((failData: any) => {
                    console.log(failData);
                }).always(() => vm.$blockui("hide"));

            let ele = $("#kaf000-a-component4-singleDate");
            ele.focusout(() => {
                console.log("focus out detected!");
            });



            vm.application().appDate.subscribe(() => {
                vm.$blockui("show");

                let command = {
                    appType: vm.application().appType,
                    appDates: [ko.toJS(vm.application().appDate)],
                    baseDate: vm.application().appDate,
                    appDispNoDate: vm.appDispInfoStartupOutput().appDispInfoNoDateOutput,
                    appDispWithDate: vm.appDispInfoStartupOutput().appDispInfoWithDateOutput,
                    setting: vm.arrivedLateLeaveEarlyInfo().lateEarlyCancelAppSet
                }
                vm.$ajax(API.changeAppDate, command).done((success: any) => {
                    console.log(success);
                    // Test data
                    vm.workManagement.clearData();
                    vm.workManagement.scheAttendanceTime("--:--")
                    vm.workManagement.scheAttendanceTime2("--:--")
                    vm.workManagement.scheWorkTime("--:--")
                    vm.workManagement.scheWorkTime2("--:--")
                    if(vm.application().appDate() === "2020/08/15") {
                        vm.workManagement.scheAttendanceTime("8:30");
                        vm.workManagement.scheWorkTime("17:30");
                        vm.workManagement.scheAttendanceTime2("18:00");
                        vm.workManagement.scheWorkTime2("23:00");

                        vm.workManagement.workTime(510);
                        vm.workManagement.leaveTime(1030);
                        vm.workManagement.workTime2(1110);
                        vm.workManagement.leaveTime2(1200);
                        return;
                    }
                    // Test data

                    if (success.errorInfo) {
                        const message: any = {
                            messageId: "Msg_1707",
                            messageParams: [ko.toJS(vm.application().appDate)]
                        };

                        vm.$errors("#kaf000-a-component4-singleDate", message);
                    } else {
                        vm.appDispInfoStartupOutput().appDispInfoWithDateOutput = success.appDispInfoWithDateOutput;
                        vm.lateOrEarlyInfos(success.lateOrEarlyInfoLst);
                        vm.lateOrEarlyInfo1(ko.toJS(_.filter(vm.lateOrEarlyInfos, { 'workNo': 1, 'category': 0 })));
                        vm.lateOrEarlyInfo2(ko.toJS(_.filter(vm.lateOrEarlyInfos, { 'workNo': 1, 'category': 1 })));
                        vm.lateOrEarlyInfo3(ko.toJS(_.filter(vm.lateOrEarlyInfos, { 'workNo': 2, 'category': 0 })));
                        vm.lateOrEarlyInfo4(ko.toJS(_.filter(vm.lateOrEarlyInfos, { 'workNo': 2, 'category': 1 })));

                        vm.arrivedLateLeaveEarlyInfo().appDispInfoStartupOutput.appDispInfoWithDateOutput = success.appDispInfoWithDateOutput;
                        // if (!vm.arrivedLateLeaveEarlyInfo().appDispInfoStartupOutput
                        //     || !vm.arrivedLateLeaveEarlyInfo().appDispInfoStartupOutput.appDispInfoWithDateOutput
                        //     || !vm.arrivedLateLeaveEarlyInfo().appDispInfoStartupOutput.appDispInfoWithDateOutput.actualContentDisplay
                        //     || !vm.arrivedLateLeaveEarlyInfo().appDispInfoStartupOutput.appDispInfoWithDateOutput.actualContentDisplay.achievementDetail
                        //     || !vm.arrivedLateLeaveEarlyInfo().appDispInfoStartupOutput.appDispInfoWithDateOutput.actualContentDisplay.achievementDetail.achievementEarly) {
                        //     vm.workManagement.scheAttendanceTime("--:--");
                        //     vm.workManagement.scheAttendanceTime2("--:--");
                        //     vm.workManagement.scheWorkTime("--:--");
                        //     vm.workManagement.scheWorkTime2("--:--");
                        // } else {
                        //     vm.workManagement.scheAttendanceTime(vm.arrivedLateLeaveEarlyInfo().appDispInfoStartupOutput.appDispInfoWithDateOutput.actualContentDisplay.achievementDetail.achievementEarly.scheAttendanceTime1);
                        //     vm.workManagement.scheAttendanceTime2(vm.arrivedLateLeaveEarlyInfo().appDispInfoStartupOutput.appDispInfoWithDateOutput.actualContentDisplay.achievementDetail.achievementEarly.scheAttendanceTime2);
                        //     vm.workManagement.scheWorkTime(vm.arrivedLateLeaveEarlyInfo().appDispInfoStartupOutput.appDispInfoWithDateOutput.actualContentDisplay.achievementDetail.achievementEarly.scheDepartureTime1);
                        //     vm.workManagement.scheWorkTime2(vm.arrivedLateLeaveEarlyInfo().appDispInfoStartupOutput.appDispInfoWithDateOutput.actualContentDisplay.achievementDetail.achievementEarly.scheDepartureTime2);

                        //     vm.workManagement.workTime = vm.arrivedLateLeaveEarlyInfo().appDispInfoStartupOutput.appDispInfoWithDateOutput.actualContentDisplay.achievementDetail.workTime;
                        //     vm.workManagement.workTime2 = vm.arrivedLateLeaveEarlyInfo().appDispInfoStartupOutput.appDispInfoWithDateOutput.actualContentDisplay.achievementDetail.workTime1;
                        //     vm.workManagement.leaveTime = vm.arrivedLateLeaveEarlyInfo().appDispInfoStartupOutput.appDispInfoWithDateOutput.actualContentDisplay.achievementDetail.leaveTime;
                        //     vm.workManagement.leaveTime2 = vm.arrivedLateLeaveEarlyInfo().appDispInfoStartupOutput.appDispInfoWithDateOutput.actualContentDisplay.achievementDetail.leaveTime2;
                        // }

                    }

                }).fail((error: any) => {
                    console.log(error);
                }).always(() => vm.$blockui("hide"));
            });
        }

        mounted() {
            const vm = this;

        }

        register() {
            const vm = this;

            console.log(ko.toJS(vm.application()));
            console.log(vm.workManagement);
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
                    vm.workManagement.workTime(null);
                }
                if (ko.toJS(vm.lateOrEarlyInfo2().isCheck)) {
                    lateCancelation.push({
                        workNo: 1,
                        lateOrEarlyClassification: 1
                    })
                    _.remove(lateOrLeaveEarlies, (x) => {
                        return (x.workNo === 1 && x.lateOrEarlyClassification === 1);
                    });
                    vm.workManagement.leaveTime(null);
                }
                if (ko.toJS(vm.lateOrEarlyInfo3().isCheck)) {
                    lateCancelation.push({
                        workNo: 2,
                        lateOrEarlyClassification: 0
                    })
                    _.remove(lateOrLeaveEarlies, (x) => {
                        return (x.workNo === 2 && x.lateOrEarlyClassification === 0);
                    });
                    vm.workManagement.workTime2(null);
                }
                if (ko.toJS(vm.lateOrEarlyInfo4().isCheck)) {
                    lateCancelation.push({
                        workNo: 2,
                        lateOrEarlyClassification: 1
                    })
                    _.remove(lateOrLeaveEarlies, (x) => {
                        return (x.workNo === 2 && x.lateOrEarlyClassification === 1);
                    });
                    vm.workManagement.leaveTime2(null);
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

                                for (var i = 0; i < success.length; i++) {
                                    vm.$dialog.confirm({ messageId: success[i] }).then((result: 'no' | 'yes' | 'cancel') => {
                                        if (result !== 'yes') {
                                            return;
                                        }
                                    });
                                }

                                this.afterRegister(application);
                            } else {
                                this.afterRegister(application);
                            }
                        }).fail((fail: any) => {
                            console.log(fail);
                            if (fail) {
                                vm.$dialog.error({ messageId: fail.messageId });
                            }
                        })
                    }
                }).always(() => vm.$blockui("hide"));

        }

        private afterRegister(params?: any) {
            const vm = this;

            vm.$ajax(API.register,
                {
                    appType: ko.toJS(vm.application().appType),
                    application: params,
                    infoOutput: ko.toJS(vm.arrivedLateLeaveEarlyInfo)
                }).done((success: any) => {
                    if(success) {
                        vm.$dialog.info({ messageId: "Msg_15" }).then(() => {
                        if (ko.toJS(vm.isSendMail)
                            // && !vm.arrivedLateLeaveEarlyInfo.appDispInfoStartupOutput.appDispInfoNoDateOutput.applicationSetting.appTypeSetting.sendMailWhenRegister)
                            && false
                        ) {
                                vm.$window.storage("KDL030_PARAM", {
                                    appID: success.appID
                                });
                                vm.$window.modal("/view/kdl/030/a/index.xhtml").then((result: any) => {
                                    vm.$window.storage('childData').then(rs => {
                                        console.log(rs);
                                    });
                                });
                            };
                        }).then(() => window.location.reload());
                    }
                }).fail((fail: any) => {
                    console.log(fail);
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
            return ko.toJS(this.application().prePostAtr) === 1;
        }

        // ※13
        public condition13(idItem: number) {

            // 事前事後区分に「事後」を選択している場合　（事後モード）  (T/h select "xin sau"「事後」 trên 事前事後区分/Phân loại xin trước xin sau (Mode after/xin sau)
            if (ko.toJS(this.application().prePostAtr) === 1) {

                // 起動したら、実績データがある場合 (Sau khi khởi động t/h có data thực tế)
                switch (idItem) {
                    case IdItem.A6_7: {
                        return ko.toJS(this.workManagement.workTime) === null;
                    } case IdItem.A6_13: {
                        return ko.toJS(this.workManagement.leaveTime) === null;
                    } case IdItem.A6_19: {
                        return ko.toJS(this.workManagement.workTime2) === null;
                    } case IdItem.A6_25: {
                        return ko.toJS(this.workManagement.leaveTime2) === null;
                    } default: {
                        return false;
                    }
                }
            }
            return true;
        }

        // ※10 display
        public condition10Display(idItem: number) {
            const vm = this;

            // 取り消す初期情報.表示する
            switch (idItem) {
                case IdItem.A6_7: {
                    if (!ko.toJS(vm.lateOrEarlyInfo1)) {
                        return false;
                    }
                    return ko.toJS(vm.lateOrEarlyInfo1().isIndicate);
                } case IdItem.A6_13: {
                    if (!ko.toJS(vm.lateOrEarlyInfo2)) {
                        return false;
                    }
                    return ko.toJS(vm.lateOrEarlyInfo2().isIndicate);
                } case IdItem.A6_19: {
                    if (!ko.toJS(vm.lateOrEarlyInfo3)) {
                        return false;
                    }
                    return ko.toJS(vm.lateOrEarlyInfo3().isIndicate);
                } case IdItem.A6_25: {
                    if (!ko.toJS(vm.lateOrEarlyInfo4)) {
                        return false;
                    }
                    return ko.toJS(vm.lateOrEarlyInfo4().isIndicate);
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

            // 取り消す初期情報.活性する
            switch (idItem) {
                case IdItem.A6_7: {
                    if (!ko.toJS(vm.lateOrEarlyInfo1)) {
                        return false;
                    }
                    return ko.toJS(vm.lateOrEarlyInfo1().isActive);
                } case IdItem.A6_13: {
                    if (!ko.toJS(vm.lateOrEarlyInfo2)) {
                        return false;
                    }
                    return ko.toJS(vm.lateOrEarlyInfo2().isActive);
                } case IdItem.A6_19: {
                    if (!ko.toJS(vm.lateOrEarlyInfo3)) {
                        return false;
                    }
                    return ko.toJS(vm.lateOrEarlyInfo3().isActive);
                } case IdItem.A6_25: {
                    if (!ko.toJS(vm.lateOrEarlyInfo4)) {
                        return false;
                    }
                    return ko.toJS(vm.lateOrEarlyInfo4().isActive);
                } default: {
                    return true;
                }
            }

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
