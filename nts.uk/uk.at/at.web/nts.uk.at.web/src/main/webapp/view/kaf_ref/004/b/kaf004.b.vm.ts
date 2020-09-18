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

        created(
            params: {
                application: any,
                printContentOfEachAppDto: PrintContentOfEachAppDto,
                approvalReason: any,
                appDispInfoStartupOutput: any,
                eventUpdate: (evt: () => void) => void
            }) {
            const vm = this;
            vm.isSendMail = ko.observable(true);
            vm.printContentOfEachAppDto = ko.observable(params.printContentOfEachAppDto);

            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            vm.application = params.application;

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

            vm.application().appID.subscribe(() => {
                if(vm.application().appType === AppType.EARLY_LEAVE_CANCEL_APPLICATION) {
                    vm.createParamKAF004();
                }
            });

            vm.createParamKAF004();
            // params.printContentOfEachAppDto.opArrivedLateLeaveEarlyInfo = ko.toJS(vm.arrivedLateLeaveEarlyInfo);
            vm.approvalReason = params.approvalReason;
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

            var check1 = _.filter(vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly.lateCancelation, { 'workNo': 1, 'lateOrEarlyClassification': 0 });
            var check2 = _.filter(vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly.lateCancelation, { 'workNo': 1, 'lateOrEarlyClassification': 1 });
            var check3 = _.filter(vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly.lateCancelation, { 'workNo': 2, 'lateOrEarlyClassification': 0 });
            var check4 = _.filter(vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly.lateCancelation, { 'workNo': 2, 'lateOrEarlyClassification': 1 });

            vm.lateOrEarlyInfos(vm.arrivedLateLeaveEarlyInfo().earlyInfos);
            if (this.lateOrEarlyInfos().length > 0) {
                if (ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 1, 'category': 0 })).length > 0) {
                    vm.lateOrEarlyInfo1().isActive(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 1, 'category': 0 }))[0].isActive);
                    vm.lateOrEarlyInfo1().isCheck(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 1, 'category': 0 }))[0].isCheck);
                    vm.lateOrEarlyInfo1().isIndicated(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 1, 'category': 0 }))[0].isIndicated);
                    vm.lateOrEarlyInfo1().category(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 1, 'category': 0 }))[0].category);
                    vm.lateOrEarlyInfo1().workNo(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 1, 'category': 0 }))[0].workNo);
                    vm.lateOrEarlyInfo1().isCheck(_.isEmpty(check1) && vm.lateOrEarlyInfo1().isIndicated() !== false ? false : true);
                }

                if (ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 1, 'category': 1 })).length > 0) {
                    vm.lateOrEarlyInfo2().isActive(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 1, 'category': 1 }))[0].isActive);
                    vm.lateOrEarlyInfo2().isCheck(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 1, 'category': 1 }))[0].isCheck);
                    vm.lateOrEarlyInfo2().isIndicated(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 1, 'category': 1 }))[0].isIndicated);
                    vm.lateOrEarlyInfo2().category(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 1, 'category': 1 }))[0].category);
                    vm.lateOrEarlyInfo2().workNo(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 1, 'category': 1 }))[0].workNo);
                    vm.lateOrEarlyInfo2().isCheck(_.isEmpty(check2) && vm.lateOrEarlyInfo2().isIndicated() !== false ? false : true);
                }

                if (ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 2, 'category': 0 })).length > 0) {
                    vm.lateOrEarlyInfo3().isActive(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 2, 'category': 0 }))[0].isActive);
                    vm.lateOrEarlyInfo3().isCheck(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 2, 'category': 0 }))[0].isCheck);
                    vm.lateOrEarlyInfo3().isIndicated(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 2, 'category': 0 }))[0].isIndicated);
                    vm.lateOrEarlyInfo3().category(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 2, 'category': 0 }))[0].category);
                    vm.lateOrEarlyInfo3().workNo(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 2, 'category': 0 }))[0].workNo);
                    vm.lateOrEarlyInfo3().isCheck(_.isEmpty(check3) && vm.lateOrEarlyInfo3().isIndicated() !== false ? false : true);
                }

                if (ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 2, 'category': 1 })).length > 0) {
                    vm.lateOrEarlyInfo4().isActive(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 2, 'category': 1 }))[0].isActive);
                    vm.lateOrEarlyInfo4().isCheck(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 2, 'category': 1 }))[0].isCheck);
                    vm.lateOrEarlyInfo4().isIndicated(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 2, 'category': 1 }))[0].isIndicated);
                    vm.lateOrEarlyInfo4().category(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 2, 'category': 1 }))[0].category);
                    vm.lateOrEarlyInfo4().workNo(ko.toJS(_.filter(vm.lateOrEarlyInfos(), { 'workNo': 2, 'category': 1 }))[0].workNo);
                    vm.lateOrEarlyInfo4().isCheck(_.isEmpty(check4) && vm.lateOrEarlyInfo4().isIndicated() !== false ? false : true);
                }
            }

            var lateEarliesApp = params.arrivedLateLeaveEarly.lateOrLeaveEarlies;

            vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.achievementEarly.scheAttendanceTime1 == null ? vm.workManagement.scheAttendanceTime('--:--') :
                vm.workManagement.scheAttendanceTime(nts.uk.time.format.byId("Clock_Short_HM", vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.achievementEarly.scheAttendanceTime1));
            vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.achievementEarly.scheDepartureTime1 == null ? vm.workManagement.scheWorkTime('--:--') :
                vm.workManagement.scheWorkTime(nts.uk.time.format.byId("Clock_Short_HM", vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.achievementEarly.scheDepartureTime1));
            vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.achievementEarly.scheAttendanceTime2 == null ? vm.workManagement.scheAttendanceTime2('--:--') :
                vm.workManagement.scheAttendanceTime2(nts.uk.time.format.byId("Clock_Short_HM", vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.achievementEarly.scheAttendanceTime2));
            vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.achievementEarly.scheDepartureTime2 == null ? vm.workManagement.scheWorkTime2('--:--') :
                vm.workManagement.scheWorkTime2(nts.uk.time.format.byId("Clock_Short_HM", vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.achievementEarly.scheDepartureTime2));


            // if (_.filter(lateEarliesApp, { 'workNo': 1, 'lateOrEarlyClassification': 0 }).length > 0) {
            //     vm.workManagement.workTime(nts.uk.time.format.byId("Clock_Short_HM", _.filter(lateEarliesApp, { 'workNo': 1, 'lateOrEarlyClassification': 0 })[0].timeWithDayAttr));
            // } else {
            //     if (vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.opWorkTime !== null) {
            //         vm.workManagement.workTime(nts.uk.time.format.byId("Clock_Short_HM", vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.opWorkTime));
            //     }
            // }

            // if (_.filter(lateEarliesApp, { 'workNo': 1, 'lateOrEarlyClassification': 1 }).length > 0) {
            //     vm.workManagement.leaveTime(nts.uk.time.format.byId("Clock_Short_HM", _.filter(lateEarliesApp, { 'workNo': 1, 'lateOrEarlyClassification': 1 })[0].timeWithDayAttr));
            // } else {
            //     if (vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.opLeaveTime !== null) {
            //         vm.workManagement.leaveTime(nts.uk.time.format.byId("Clock_Short_HM", vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.opLeaveTime));
            //     }
            // }

            // if (_.filter(lateEarliesApp, { 'workNo': 2, 'lateOrEarlyClassification': 0 }).length > 0) {
            //     vm.workManagement.workTime2(nts.uk.time.format.byId("Clock_Short_HM", _.filter(lateEarliesApp, { 'workNo': 2, 'lateOrEarlyClassification': 0 })[0].timeWithDayAttr));
            // } else {
            //     if (vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.opWorkTime2 !== null) {
            //         vm.workManagement.workTime2(nts.uk.time.format.byId("Clock_Short_HM", vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.opWorkTime2));
            //     }
            // }

            // if (_.filter(lateEarliesApp, { 'workNo': 1, 'lateOrEarlyClassification': 1 }).length > 0) {
            //     vm.workManagement.leaveTime2(nts.uk.time.format.byId("Clock_Short_HM", _.filter(lateEarliesApp, { 'workNo': 1, 'lateOrEarlyClassification': 1 })[0].timeWithDayAttr));
            // } else {
            //     if (vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.opDepartureTime2 !== null) {
            //         vm.workManagement.leaveTime2(nts.uk.time.format.byId("Clock_Short_HM", vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.opDepartureTime2));
            //     }
            // }

            if (_.filter(lateEarliesApp, { 'workNo': 1, 'lateOrEarlyClassification': 0 }).length > 0) {
                vm.workManagement.workTime(_.filter(lateEarliesApp, { 'workNo': 1, 'lateOrEarlyClassification': 0 })[0].timeWithDayAttr);
            } else {
                if (vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.opWorkTime !== null) {
                    vm.workManagement.workTime(vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.opWorkTime);
                }
            }

            if (_.filter(lateEarliesApp, { 'workNo': 1, 'lateOrEarlyClassification': 1 }).length > 0) {
                vm.workManagement.leaveTime(_.filter(lateEarliesApp, { 'workNo': 1, 'lateOrEarlyClassification': 1 })[0].timeWithDayAttr);
            } else {
                if (vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.opLeaveTime !== null) {
                    vm.workManagement.leaveTime(vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.opLeaveTime);
                }
            }

            if (_.filter(lateEarliesApp, { 'workNo': 2, 'lateOrEarlyClassification': 0 }).length > 0) {
                vm.workManagement.workTime2(_.filter(lateEarliesApp, { 'workNo': 2, 'lateOrEarlyClassification': 0 })[0].timeWithDayAttr);
            } else {
                if (vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.opWorkTime2 !== null) {
                    vm.workManagement.workTime2(vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.opWorkTime2);
                }
            }

            if (_.filter(lateEarliesApp, { 'workNo': 1, 'lateOrEarlyClassification': 1 }).length > 0) {
                vm.workManagement.leaveTime2(_.filter(lateEarliesApp, { 'workNo': 1, 'lateOrEarlyClassification': 1 })[0].timeWithDayAttr);
            } else {
                if (vm.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.opDepartureTime2 !== null) {
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

            vm.$blockui("show");
            return vm.$validate()
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

            vm.$ajax(API.updateInfo,
                {
                    application: params,
                    arrivedLateLeaveEarlyDto: vm.arrivedLateLeaveEarlyInfo().arrivedLateLeaveEarly
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
            return ko.toJS(this.application().prePostAtr) === 1;
        }

        // ※8
        public condition8(idItem: number) {

            // return ko.computed(() => {
            // 事前事後区分に「事後」を選択している場合　（事後モード）  (T/h select "xin sau"「事後」 trên 事前事後区分/Phân loại xin trước xin sau (Mode after/xin sau)
            if (ko.toJS(this.application().prePostAtr) === 1) {

                // 起動したら、実績データがある場合 (Sau khi khởi động t/h có data thực tế)
                switch (idItem) {
                    case IdItem.B6_7: {
                        return !(this.workManagement.workTime() == null || this.workManagement.workTime() === "");
                    } case IdItem.B6_13: {
                        return !(this.workManagement.leaveTime() == null || this.workManagement.leaveTime() === "");
                    } case IdItem.B6_19: {
                        return !(this.workManagement.workTime2() == null || this.workManagement.workTime2() === "");
                    } case IdItem.B6_25: {
                        return !(this.workManagement.leaveTime2() == null || this.workManagement.leaveTime2() === "");
                    } default: {
                        return true;
                    }
                }
            }

            return false;
            // });
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
