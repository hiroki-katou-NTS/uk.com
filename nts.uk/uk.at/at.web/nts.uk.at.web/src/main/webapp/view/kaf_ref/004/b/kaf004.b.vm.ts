module nts.uk.at.view.kaf004_ref.b.viewmodel {
    import Application = nts.uk.at.view.kaf000_ref.shr.viewmodel.Application;
    import CommonProcess = nts.uk.at.view.kaf000_ref.shr.viewmodel.CommonProcess;
    import WorkManagement = nts.uk.at.view.kaf004_ref.shr.common.viewmodel.WorkManagement;
    import LateOrEarlyInfo = nts.uk.at.view.kaf004_ref.shr.common.viewmodel.LateOrEarlyInfo;
    import ArrivedLateLeaveEarlyInfo = nts.uk.at.view.kaf004_ref.shr.common.viewmodel.ArrivedLateLeaveEarlyInfo;
    import AppType = nts.uk.at.view.kaf000_ref.shr.viewmodel.model.AppType;

    @component({
        name: 'kaf004-b',
        template: '/nts.uk.at.web/view/kaf_ref/004/b/index.html'
    })
    class KAF004AViewModel extends ko.ViewModel {
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

        created(
            params: {
                appDispInfoStartupOutput: any,
                eventUpdate: (evt: () => void) => void
            }) {
            const vm = this;
            vm.isSendMail = ko.observable(true);

            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            // vm.application = ko.observable(new Application(vm.appDispInfoStartupOutput().appDetailScreenInfo.application.appID, 1, [], 9, "", "", 0));
            vm.application = ko.observable(new Application(AppType.EARLY_LEAVE_CANCEL_APPLICATION));
            vm.application.appID = params.appDispInfoStartupOutput().appDetailScreenInfo.application.appID;
            vm.application().prePostAtr(params.appDispInfoStartupOutput().appDetailScreenInfo.application.prePostAtr);
            vm.application().employeeIDLst(params.appDispInfoStartupOutput().appDetailScreenInfo.application.employeeIDLst);
            vm.application().appDate(params.appDispInfoStartupOutput().appDetailScreenInfo.application.appDate);
            vm.application().opAppReason(params.appDispInfoStartupOutput().appDetailScreenInfo.application.opAppReason);
            vm.application().opAppStandardReasonCD(params.appDispInfoStartupOutput().appDetailScreenInfo.application.opAppStandardReasonCD);
            vm.application().opReversionReason(params.appDispInfoStartupOutput().appDetailScreenInfo.application.opReversionReason);
            vm.application().opAppStartDate(params.appDispInfoStartupOutput().appDetailScreenInfo.application.opAppStartDate);
            vm.application().opAppEndDate(params.appDispInfoStartupOutput().appDetailScreenInfo.application.opAppEndDate);

            vm.workManagement = new WorkManagement('--:--', '--:--', '--:--', '--:--', null, null, null, null);

            vm.lateOrEarlyInfo1 = ko.observable(new LateOrEarlyInfo(true, 1, true, true, 0));
            vm.lateOrEarlyInfo2 = ko.observable(new LateOrEarlyInfo(true, 1, true, true, 1));
            vm.lateOrEarlyInfo3 = ko.observable(new LateOrEarlyInfo(false, 2, false, true, 0));
            vm.lateOrEarlyInfo4 = ko.observable(new LateOrEarlyInfo(false, 2, true, true, 1));
            vm.lateOrEarlyInfos = ko.observableArray([]);
            vm.managementMultipleWorkCycles = ko.observable(true);

            // Subcribe when mode change -> clear data if mode is 'before'
            vm.application().prePostAtr.subscribe(() => {
                if (ko.toJS(vm.application().prePostAtr) === 0) {
                    vm.workManagement.clearData();
                }
            });

            vm.createParamKAF004();

            // gui event con ra viewmodel cha
            // nhớ dùng bind(vm) để ngữ cảnh lúc thực thi
            // luôn là component
            params.eventUpdate(vm.update.bind(vm));
        }

        createParamKAF004() {
            const vm = this;
            let command = {
                appId: ko.toJS(vm.application.appID),
                infoStartup: ko.toJS(vm.appDispInfoStartupOutput)
            };

            vm.$blockui("show");
            vm.$ajax(API.initPageB, command)
                .done((res: any) => {
                    vm.arrivedLateLeaveEarlyInfo = ko.observable(res);
                    vm.appDispInfoStartupOutput = ko.observable(res.appDispInfoStartupOutput);

                    vm.lateOrEarlyInfos(vm.arrivedLateLeaveEarlyInfo.earlyInfos);
                    vm.lateOrEarlyInfo1(ko.toJS(_.filter(vm.lateOrEarlyInfos, { 'workNo': 1, 'category': 0 })));
                    vm.lateOrEarlyInfo2(ko.toJS(_.filter(vm.lateOrEarlyInfos, { 'workNo': 1, 'category': 1 })));
                    vm.lateOrEarlyInfo3(ko.toJS(_.filter(vm.lateOrEarlyInfos, { 'workNo': 2, 'category': 0 })));
                    vm.lateOrEarlyInfo4(ko.toJS(_.filter(vm.lateOrEarlyInfos, { 'workNo': 2, 'category': 1 })));

                    // if (!ko.toJS(vm.appDispInfoStartupOutput())
                    //     || !ko.toJS(vm.appDispInfoStartupOutput().appDispInfoWithDateOutput)
                    //     || !ko.toJS(vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.actualContentDisplay)
                    //     || !ko.toJS(vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.actualContentDisplay.achievementDetail)
                    //     || !ko.toJS(vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.actualContentDisplay.achievementDetail.achievementEarly)) {
                    //     vm.workManagement.scheAttendanceTime("--:--");
                    //     vm.workManagement.scheAttendanceTime2("--:--");
                    //     vm.workManagement.scheWorkTime("--:--");
                    //     vm.workManagement.scheWorkTime2("--:--");
                    // } else {
                    //     vm.workManagement.scheAttendanceTime(vm.appDispInfoStartupOutput.appDispInfoWithDateOutput.actualContentDisplay.achievementDetail.achievementEarly.scheAttendanceTime1);
                    //     vm.workManagement.scheAttendanceTime2(vm.appDispInfoStartupOutput.appDispInfoWithDateOutput.actualContentDisplay.achievementDetail.achievementEarly.scheAttendanceTime2);
                    //     vm.workManagement.scheWorkTime(vm.appDispInfoStartupOutput.appDispInfoWithDateOutput.actualContentDisplay.achievementDetail.achievementEarly.scheDepartureTime1);
                    //     vm.workManagement.scheWorkTime2(vm.appDispInfoStartupOutput.appDispInfoWithDateOutput.actualContentDisplay.achievementDetail.achievementEarly.scheDepartureTime2);

                    //     vm.workManagement.workTime = vm.appDispInfoStartupOutput.appDispInfoWithDateOutput.actualContentDisplay.achievementDetail.workTime;
                    //     vm.workManagement.workTime2 = vm.appDispInfoStartupOutput.appDispInfoWithDateOutput.actualContentDisplay.achievementDetail.workTime1;
                    //     vm.workManagement.leaveTime = vm.appDispInfoStartupOutput.appDispInfoWithDateOutput.actualContentDisplay.achievementDetail.leaveTime;
                    //     vm.workManagement.leaveTime2 = vm.appDispInfoStartupOutput.appDispInfoWithDateOutput.actualContentDisplay.achievementDetail.leaveTime2;
                    // }

                    vm.workManagement.workTime(ko.toJS(_.filter(vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly.lateOrLeaveEarlies, { 'workNo': 1, 'lateOrEarlyClassification': 0 }))[0].timeWithDayAttr);
                    vm.workManagement.workTime2(ko.toJS(_.filter(vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly.lateOrLeaveEarlies, { 'workNo': 1, 'lateOrEarlyClassification': 1 }))[0].timeWithDayAttr);
                    vm.workManagement.leaveTime(ko.toJS(_.filter(vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly.lateOrLeaveEarlies, { 'workNo': 2, 'lateOrEarlyClassification': 0 }))[0].timeWithDayAttr);
                    vm.workManagement.leaveTime2(ko.toJS(_.filter(vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly.lateOrLeaveEarlies, { 'workNo': 2, 'lateOrEarlyClassification': 1 }))[0].timeWithDayAttr);

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
                }).fail((err: any) => {
                    console.log()
                    vm.$dialog.error({messageId: err.msgId});
                }).always(() => vm.$blockui('hide'));
        }

        mounted() {
            const vm = this;

        }

        update() {
            const vm = this;
            let application = vm.appDispInfoStartupOutput().appDetailScreenInfo.application;

            vm.$blockui("show");
            vm.$validate()
            .then((isValid) => {
                if(isValid) {
                    const command = {
                        agentAtr: true,
                        isNew: false,
                        infoOutput:  ko.toJS(vm.arrivedLateLeaveEarlyInfo),
                        application: application
                    };

                    return vm.$ajax(API.getMsgList + "/" + ko.toJS(application.appType), command)
                }
            }).done((success: any) => {
                if(success) {
                    console.log(success);
                    for (var i = 0; i < success.length; i++) {
                        vm.$dialog.confirm({ messageId: success[i] }).then((result: 'no' | 'yes' | 'cancel') => {
                            if (result !== 'yes') {
                                return;
                            }
                        });
                    }
                }

                this.afterRegister(application);

            }).always(() => vm.$blockui('hide'));
        }

        register() {
            const vm = this;

            console.log(ko.toJS(vm.application()));
            console.log(vm.workManagement);
        }

        private afterRegister(params?: any) {
            const vm = this;

            vm.$ajax(API.updateInfo,
                {
                    application: params,
                    arrivedLateLeaveEarlyDto: vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly
                }).done((success: any) => {
                    if (ko.toJS(vm.isSendMail)
                        // && !vm.arrivedLateLeaveEarlyInfo.appDispInfoStartupOutput.appDispInfoNoDateOutput.applicationSetting.appTypeSetting.sendMailWhenRegister)
                        && true
                    )
                        {
                        vm.$dialog.info({ messageId: "Msg_15" }).then(() => {
                            vm.$window.storage("KDL030_PARAM", {
                                appID: success.appID
                            });
                            vm.$window.modal("/view/kdl/030/a/index.xhtml").then((result: any) => {
                                vm.$window.storage('childData').then(rs => {
                                    console.log(rs);
                                });
                            });
                        });
                    }
                }).fail((fail: any) => {
                    console.log(fail);
                    return;
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
        public condition9(idItem: number) {
            // 事前事後区分に「事後」に選択している場合　（事後モード）
            return ko.toJS(this.application().prePostAtr) === 0;
        }

        // ※8
        public condition8(idItem: number) {

            // 事前事後区分に「事後」を選択している場合　（事後モード）  (T/h select "xin sau"「事後」 trên 事前事後区分/Phân loại xin trước xin sau (Mode after/xin sau)
            if (ko.toJS(this.application().prePostAtr) === 1) {

                // 起動したら、実績データがある場合 (Sau khi khởi động t/h có data thực tế)
                switch (idItem) {
                    case IdItem.B6_7: {
                        return !!ko.toJS(this.workManagement.workTime);
                    } case IdItem.B6_13: {
                        return !!ko.toJS(this.workManagement.leaveTime);
                    } case IdItem.B6_19: {
                        return !!ko.toJS(this.workManagement.workTime2);
                    } case IdItem.B6_25: {
                        return !!ko.toJS(this.workManagement.leaveTime2);
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
                case IdItem.B6_7: {
                    if (!ko.toJS(vm.lateOrEarlyInfo1)) {
                        return false;
                    }
                    return ko.toJS(vm.lateOrEarlyInfo1().isIndicate);
                } case IdItem.B6_13: {
                    if (!ko.toJS(vm.lateOrEarlyInfo2)) {
                        return false;
                    }
                    return ko.toJS(vm.lateOrEarlyInfo2().isIndicate);
                } case IdItem.B6_19: {
                    if (!ko.toJS(vm.lateOrEarlyInfo3)) {
                        return false;
                    }
                    return ko.toJS(vm.lateOrEarlyInfo3().isIndicate);
                } case IdItem.B6_25: {
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
            return this.condition2 && this.condition9(idItem) && this.condition10Display(idItem);
        }

        // public getValueDatePick(idItem: number) {
        //     const vm = this,
        //         lateOrLeaveEarlies = ko.toJS(vm.arrivedLateLeaveEarlyInfo())
        //             .arrivedLateLeaveEarly.lateOrLeaveEarlies,
        //         achievementOutputLst = ko.toJS(vm.arrivedLateLeaveEarlyInfo())
        //             .appDispInfoStartupOutput.appDispInfoWithDateOutput.achievementOutputLst;

        //     switch (idItem) {
        //         case 1: {
        //             if (!!_.filter(lateOrLeaveEarlies, { 'workNo': 1, 'category': 0 }).timeWithDayAttr) {
        //                 return _.filter(lateOrLeaveEarlies, { 'workNo': 1, 'category': 0 }).timeWithDayAttr;
        //             }

        //             return _.filter(achievementOutputLst, { 'achivementDatail.recordClassification': '日別実績' }).achivementDatail.workTime5;
        //         } case 2: {
        //             if (!!_.filter(lateOrLeaveEarlies, { 'workNo': 1, 'category': 1 }).timeWithDayAttr) {
        //                 return _.filter(lateOrLeaveEarlies, { 'workNo': 1, 'category': 1 }).timeWithDayAttr;
        //             }

        //             return _.filter(achievementOutputLst, { 'achivementDatail.recordClassification': '日別実績' }).achivementDatail.leaveTime6;
        //         } case 3: {
        //             if (!!_.filter(lateOrLeaveEarlies, { 'workNo': 2, 'category': 0 }).timeWithDayAttr) {
        //                 return _.filter(lateOrLeaveEarlies, { 'workNo': 2, 'category': 0 }).timeWithDayAttr;
        //             }

        //             return _.filter(achievementOutputLst, { 'achivementDatail.recordClassification': '日別実績' }).achivementDatail.workTime9_2;
        //         } case 4: {
        //             if (!!_.filter(lateOrLeaveEarlies, { 'workNo': 2, 'category': 1 }).timeWithDayAttr) {
        //                 return _.filter(lateOrLeaveEarlies, { 'workNo': 2, 'category': 1 }).timeWithDayAttr;
        //             }

        //             return _.filter(achievementOutputLst, { 'achivementDatail.recordClassification': '日別実績' }).achivementDatail.departureTime10_2;
        //         } default: {
        //             return '';
        //         }
        //     }

        // }
    }

    const API = {
        initPageB: "at/request/application/lateorleaveearly/initPageB",
        getMsgList: "at/request/application/lateorleaveearly/getMsgList",
        updateInfo: "at/request/application/lateorleaveearly/updateInfoApp"
    };

    export class IdItem {
        public static readonly B6_7: number = 1;
        public static readonly B6_13: number = 2;
        public static readonly B6_19: number = 3;
        public static readonly B6_25: number = 4
    }
}
