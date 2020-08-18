module nts.uk.at.view.kaf004_ref.b.viewmodel {
    import Application = nts.uk.at.view.kaf000_ref.shr.viewmodel.Application;
    import CommonProcess = nts.uk.at.view.kaf000_ref.shr.viewmodel.CommonProcess;
    import WorkManagement = nts.uk.at.view.kaf004_ref.shr.common.viewmodel.WorkManagement;
    import LateOrEarlyInfo = nts.uk.at.view.kaf004_ref.shr.common.viewmodel.LateOrEarlyInfo;
    import ArrivedLateLeaveEarlyInfo = nts.uk.at.view.kaf004_ref.shr.common.viewmodel.ArrivedLateLeaveEarlyInfo;
    import AppType = nts.uk.at.view.kaf000_ref.shr.viewmodel.model.AppType;
    import PrintContentOfEachAppDto = nts.uk.at.view.kaf000_ref.shr.viewmodel.PrintContentOfEachAppDto;

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
        printContentOfEachAppDto: KnockoutObservable<PrintContentOfEachAppDto>;

        created(
            params: {
                application: any,
                appDispInfoStartupOutput: any,
                printContentOfEachAppDto: PrintContentOfEachAppDto,
                eventUpdate: (evt: () => void) => void
            }) {
            const vm = this;
            vm.isSendMail = ko.observable(true);
            vm.printContentOfEachAppDto = ko.observable(params.printContentOfEachAppDto);

            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            // vm.application = ko.observable(new Application(vm.appDispInfoStartupOutput().appDetailScreenInfo.application.appID, 1, [], 9, "", "", 0));
            vm.application = params.application;
            // vm.application.appID = params.appDispInfoStartupOutput().appDetailScreenInfo.application.appID;
            // vm.application().prePostAtr(params.appDispInfoStartupOutput().appDetailScreenInfo.application.prePostAtr);
            // vm.application().employeeIDLst(params.appDispInfoStartupOutput().appDetailScreenInfo.application.employeeIDLst);
            // vm.application().appDate(params.appDispInfoStartupOutput().appDetailScreenInfo.application.appDate);
            // vm.application().opAppReason(params.appDispInfoStartupOutput().appDetailScreenInfo.application.opAppReason);
            // vm.application().opAppStandardReasonCD(params.appDispInfoStartupOutput().appDetailScreenInfo.application.opAppStandardReasonCD);
            // vm.application().opReversionReason(params.appDispInfoStartupOutput().appDetailScreenInfo.application.opReversionReason);
            // vm.application().opAppStartDate(params.appDispInfoStartupOutput().appDetailScreenInfo.application.opAppStartDate);
            // vm.application().opAppEndDate(params.appDispInfoStartupOutput().appDetailScreenInfo.application.opAppEndDate);

            vm.workManagement = new WorkManagement('--:--', '--:--', '--:--', '--:--', null, null, null, null);

            // vm.lateOrEarlyInfo1 = ko.observable(new LateOrEarlyInfo(false, 1, false, false, 0));
            // vm.lateOrEarlyInfo2 = ko.observable(new LateOrEarlyInfo(false, 1, false, false, 1));
            // vm.lateOrEarlyInfo3 = ko.observable(new LateOrEarlyInfo(false, 2, false, false, 0));
            // vm.lateOrEarlyInfo4 = ko.observable(new LateOrEarlyInfo(false, 2, false, false, 1));
            vm.lateOrEarlyInfo1 = ko.observable(null);
            vm.lateOrEarlyInfo2 = ko.observable(null);
            vm.lateOrEarlyInfo3 = ko.observable(null);
            vm.lateOrEarlyInfo4 = ko.observable(null);
            vm.lateOrEarlyInfos = ko.observableArray([]);
            vm.managementMultipleWorkCycles = ko.observable(false);

            // Subcribe when mode change -> clear data if mode is 'before'
            vm.application().prePostAtr.subscribe(() => {
                if (ko.toJS(vm.application().prePostAtr) === 0) {
                    vm.workManagement.clearData();
                }
            });


            vm.createParamKAF004();
            // params.printContentOfEachAppDto.opArrivedLateLeaveEarlyInfo = ko.toJS(vm.arrivedLateLeaveEarlyInfo);

            // gui event con ra viewmodel cha
            // nhớ dùng bind(vm) để ngữ cảnh lúc thực thi
            // luôn là component
            params.eventUpdate(vm.update.bind(vm));
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
            vm.printContentOfEachAppDto().opArrivedLateLeaveEarlyInfo = params;

            vm.lateOrEarlyInfos(vm.arrivedLateLeaveEarlyInfo().earlyInfos);
            vm.lateOrEarlyInfo1(ko.toJS(_.filter(vm.lateOrEarlyInfos, { 'workNo': 1, 'category': 0 })));
            vm.lateOrEarlyInfo2(ko.toJS(_.filter(vm.lateOrEarlyInfos, { 'workNo': 1, 'category': 1 })));
            vm.lateOrEarlyInfo3(ko.toJS(_.filter(vm.lateOrEarlyInfos, { 'workNo': 2, 'category': 0 })));
            vm.lateOrEarlyInfo4(ko.toJS(_.filter(vm.lateOrEarlyInfos, { 'workNo': 2, 'category': 1 })));

            if (ko.toJS(_.filter(vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly.lateOrLeaveEarlies, { 'workNo': 1, 'lateOrEarlyClassification': 0 })).length > 0) {
                vm.workManagement.workTime(ko.toJS(_.filter(vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly.lateOrLeaveEarlies, { 'workNo': 1, 'lateOrEarlyClassification': 0 }))[0].timeWithDayAttr);
            }
            if (ko.toJS(_.filter(vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly.lateOrLeaveEarlies, { 'workNo': 2, 'lateOrEarlyClassification': 0 })).length > 0) {
                vm.workManagement.workTime2(ko.toJS(_.filter(vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly.lateOrLeaveEarlies, { 'workNo': 2, 'lateOrEarlyClassification': 0 }))[0].timeWithDayAttr);
            }
            if (ko.toJS(_.filter(vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly.lateOrLeaveEarlies, { 'workNo': 1, 'lateOrEarlyClassification': 1 })).length > 0) {
                vm.workManagement.leaveTime(ko.toJS(_.filter(vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly.lateOrLeaveEarlies, { 'workNo': 1, 'lateOrEarlyClassification': 1 }))[0].timeWithDayAttr);
            }
            if (ko.toJS(_.filter(vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly.lateOrLeaveEarlies, { 'workNo': 2, 'lateOrEarlyClassification': 1 })).length > 0) {
                vm.workManagement.leaveTime2(ko.toJS(_.filter(vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly.lateOrLeaveEarlies, { 'workNo': 2, 'lateOrEarlyClassification': 1 }))[0].timeWithDayAttr);
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

            // Test data
            vm.workManagement.scheAttendanceTime("8:30");
            vm.workManagement.scheWorkTime("17:30");
            vm.workManagement.scheAttendanceTime2("18:00");
            vm.workManagement.scheWorkTime2("23:00");
            // Test data
        }

        update() {
            const vm = this;
            let application = vm.arrivedLateLeaveEarlyInfo().appDispInfoStartupOutput.appDetailScreenInfo.application;
            application.opAppReason = ko.toJS(vm.application().opAppReason);
            application.opAppStandardReasonCD = ko.toJS(vm.application().opAppStandardReasonCD);
            application.opReversionReason = ko.toJS(vm.application().opReversionReason);

            vm.$blockui("show");
            vm.$validate()
                .then((isValid) => {
                    if (isValid) {
                        const command = {
                            agentAtr: true,
                            isNew: false,
                            infoOutput: ko.toJS(vm.arrivedLateLeaveEarlyInfo),
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
                    }

                    this.afterRegister(application);

                }).fail((fail: any) => {
                    if (fail) {
                        vm.$dialog.error({ messageId: fail.messageId });
                        let command = {
                            appId: ko.toJS(vm.application.appID),
                            infoStartup: ko.toJS(vm.appDispInfoStartupOutput)
                        };

                        vm.$ajax(API.initPageB, command)
                            .done((res: any) => {
                                this.fetchData(res);
                            }).fail((err: any) => {
                                console.log()
                                vm.$dialog.error({ messageId: err.messageId });
                            });
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

            vm.$ajax(API.updateInfo,
                {
                    application: params,
                    arrivedLateLeaveEarlyDto: vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly
                }).done((success: any) => {
                    if(success) {
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
                        return this.workManagement.workTime() !== null;
                    } case IdItem.B6_13: {
                        return this.workManagement.leaveTime() !== null;
                    } case IdItem.B6_19: {
                        return this.workManagement.workTime2() !== null;
                    } case IdItem.B6_25: {
                        return this.workManagement.leaveTime2() !== null;
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
                    if (ko.toJS(vm.lateOrEarlyInfo1) === null) {
                        return false;
                    }
                    return ko.toJS(vm.lateOrEarlyInfo1().isIndicate);
                } case IdItem.B6_13: {
                    if (ko.toJS(vm.lateOrEarlyInfo2) === null) {
                        return false;
                    }
                    return ko.toJS(vm.lateOrEarlyInfo2().isIndicate);
                } case IdItem.B6_19: {
                    if (ko.toJS(vm.lateOrEarlyInfo3) === null) {
                        return false;
                    }
                    return ko.toJS(vm.lateOrEarlyInfo3().isIndicate);
                } case IdItem.B6_25: {
                    if (ko.toJS(vm.lateOrEarlyInfo4) === null) {
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
