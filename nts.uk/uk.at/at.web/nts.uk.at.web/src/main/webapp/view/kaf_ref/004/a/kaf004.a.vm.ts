 /// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kaf004_ref.a.viewmodel {
    import Application = nts.uk.at.view.kaf000_ref.shr.viewmodel.Application;
    import CommonProcess = nts.uk.at.view.kaf000_ref.shr.viewmodel.CommonProcess;
    import WorkManagement = nts.uk.at.view.kaf004_ref.shr.common.viewmodel.WorkManagement;
    import LateOrEarlyInfo = nts.uk.at.view.kaf004_ref.shr.common.viewmodel.LateOrEarlyInfo;
    import ArrivedLateLeaveEarlyInfo = nts.uk.at.view.kaf004_ref.shr.common.viewmodel.ArrivedLateLeaveEarlyInfo;

    @bean()
    class KAF004AViewModel extends ko.ViewModel {
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

        mode: KnockoutObservable<MODE>;


        created(params: any) {
            const vm = this;

            vm.application = ko.observable(new Application("", 1, [], 7, "", "", 0));
            vm.application().appDate(moment(new Date()).format("YYYY/MM/DD"));
            // vm.application().appDate(moment(new Date()).format("YYYY/MM/DD"));
            vm.workManagement = new WorkManagement('--:--', '--:--', '--:--', '--:--', 850, 1725, null, null);
            vm.arrivedLateLeaveEarlyInfo = ko.observable(ArrivedLateLeaveEarlyInfo.initArrivedLateLeaveEarlyInfo());
            vm.appDispInfoStartupOutput = ko.observable(CommonProcess.initCommonSetting());

            vm.lateOrEarlyInfo1 = ko.observable(new LateOrEarlyInfo(true, 1, true, true, 0));
            vm.lateOrEarlyInfo2 = ko.observable(new LateOrEarlyInfo(true, 1, true, true, 1));
            vm.lateOrEarlyInfo3 = ko.observable(new LateOrEarlyInfo(false, 2, false, true, 0));
            vm.lateOrEarlyInfo4 = ko.observable(new LateOrEarlyInfo(false, 2, true, true, 1));
            vm.lateOrEarlyInfos = ko.observableArray([]);
            vm.mode = ko.observable('after');
            vm.managementMultipleWorkCycles = ko.observable(true);

            // Subcribe when mode change -> clear data if mode is 'before'
            vm.mode.subscribe(() => {
                if(ko.toJS(vm.mode) === 'before') {
                    vm.workManagement.clearData();
                }
            })
        }

        mounted() {
            const vm = this;
            vm.$blockui('show');
            vm.$ajax(API.initPage + "/" + ko.toJS(vm.application().appType), [ko.toJS(vm.application().appDate)])
                .done((successData: any) => {
                    vm.arrivedLateLeaveEarlyInfo(successData);
                    vm.appDispInfoStartupOutput(successData.appDispInfoStartupOutput);
                    vm.lateOrEarlyInfos(vm.arrivedLateLeaveEarlyInfo.earlyInfos);
                    vm.lateOrEarlyInfo1(ko.toJS(_.filter(vm.lateOrEarlyInfos, {'workNo': 1, 'category': 0})));
                    vm.lateOrEarlyInfo2(ko.toJS(_.filter(vm.lateOrEarlyInfos, {'workNo': 1, 'category': 1})));
                    vm.lateOrEarlyInfo3(ko.toJS(_.filter(vm.lateOrEarlyInfos, {'workNo': 2, 'category': 0})));
                    vm.lateOrEarlyInfo4(ko.toJS(_.filter(vm.lateOrEarlyInfos, {'workNo': 2, 'category': 1})));

                    if(!vm.arrivedLateLeaveEarlyInfo.appDispInfoStartupOutput
                        || !vm.arrivedLateLeaveEarlyInfo.appDispInfoStartupOutput.appDispInfoWithDateOutput
                        || !vm.arrivedLateLeaveEarlyInfo.appDispInfoStartupOutput.appDispInfoWithDateOutput.actualContentDisplay
                        || !vm.arrivedLateLeaveEarlyInfo.appDispInfoStartupOutput.appDispInfoWithDateOutput.actualContentDisplay.achievementDetail
                        || !vm.arrivedLateLeaveEarlyInfo.appDispInfoStartupOutput.appDispInfoWithDateOutput.actualContentDisplay.achievementDetail.achievementEarly) {
                            vm.workManagement.scheAttendanceTime("--:--");
                            vm.workManagement.scheAttendanceTime2("--:--");
                            vm.workManagement.scheWorkTime("--:--");
                            vm.workManagement.scheWorkTime2("--:--");
                        } else {
                            vm.workManagement.scheAttendanceTime(vm.arrivedLateLeaveEarlyInfo.appDispInfoStartupOutput.appDispInfoWithDateOutput.actualContentDisplay.achievementDetail.achievementEarly.scheAttendanceTime1);
                            vm.workManagement.scheAttendanceTime2(vm.arrivedLateLeaveEarlyInfo.appDispInfoStartupOutput.appDispInfoWithDateOutput.actualContentDisplay.achievementDetail.achievementEarly.scheAttendanceTime2);
                            vm.workManagement.scheWorkTime(vm.arrivedLateLeaveEarlyInfo.appDispInfoStartupOutput.appDispInfoWithDateOutput.actualContentDisplay.achievementDetail.achievementEarly.scheDepartureTime1);
                            vm.workManagement.scheWorkTime2(vm.arrivedLateLeaveEarlyInfo.appDispInfoStartupOutput.appDispInfoWithDateOutput.actualContentDisplay.achievementDetail.achievementEarly.scheDepartureTime2);

                            vm.workManagement.workTime = vm.arrivedLateLeaveEarlyInfo.appDispInfoStartupOutput.appDispInfoWithDateOutput.actualContentDisplay.achievementDetail.workTime;
                            vm.workManagement.workTime2 = vm.arrivedLateLeaveEarlyInfo.appDispInfoStartupOutput.appDispInfoWithDateOutput.actualContentDisplay.achievementDetail.workTime1;
                            vm.workManagement.leaveTime = vm.arrivedLateLeaveEarlyInfo.appDispInfoStartupOutput.appDispInfoWithDateOutput.actualContentDisplay.achievementDetail.leaveTime;
                            vm.workManagement.leaveTime2 = vm.arrivedLateLeaveEarlyInfo.appDispInfoStartupOutput.appDispInfoWithDateOutput.actualContentDisplay.achievementDetail.leaveTime2;
                        }

                    if(!vm.workManagement.scheAttendanceTime) {
                        vm.workManagement.scheAttendanceTime("--:--");
                    }
                    if(!vm.workManagement.scheAttendanceTime2) {
                        vm.workManagement.scheAttendanceTime2("--:--");
                    }
                    if(!vm.workManagement.scheWorkTime) {
                        vm.workManagement.scheWorkTime("--:--");
                    }
                    if(!vm.workManagement.scheWorkTime2) {
                        vm.workManagement.scheWorkTime2("--:--");
                    }


                    vm.$blockui('hide');
                }).fail((failData: any) => {
                    vm.$blockui("hide");
                });

        }

        register() {
            const vm = this;

            console.log(ko.toJS(vm.application()));
            console.log(vm.workManagement);
        }

        // ※2
        public condition2(): boolean {
            const vm = this;

            if(vm.arrivedLateLeaveEarlyInfo.appDispInfoStartupOutput) {
                vm.managementMultipleWorkCycles(vm.arrivedLateLeaveEarlyInfo.appDispInfoStartupOutput.appDispInfoNoDateOutput.managementMultipleWorkCycles);
            }

            // 「遅刻早退取消申請起動時の表示情報」.申請表示情報.申請設定（基準日関係なし）.複数回勤務の管理＝true
            return ko.toJS(vm.managementMultipleWorkCycles());
        }

        // ※8
        public condition8() {
            // 事前事後区分に「事後」に選択している場合　（事後モード）
            return ko.toJS(this.mode) === 'after';
        }

        // ※13
        public condition13(idItem: number) {

            // 事前事後区分に「事後」を選択している場合　（事後モード）  (T/h select "xin sau"「事後」 trên 事前事後区分/Phân loại xin trước xin sau (Mode after/xin sau)
            if (ko.toJS(this.mode) === 'after') {

                // 起動したら、実績データがある場合 (Sau khi khởi động t/h có data thực tế)
                switch (idItem) {
                    case IdItem.A6_7: {
                        return !!ko.toJS(this.workManagement.workTime);
                    } case IdItem.A6_13: {
                        return !!ko.toJS(this.workManagement.leaveTime);
                    } case IdItem.A6_19: {
                        return !!ko.toJS(this.workManagement.workTime2);
                    } case IdItem.A6_25: {
                        return !!ko.toJS(this.workManagement.leaveTime2);
                    } default: {
                        return false;
                    }
                }
                // return false;
            }


            return true;
        }

        // ※10 display
        public condition10Display(idItem: number) {
            const vm = this;

            // 取り消す初期情報.表示する
            switch(idItem) {
                case IdItem.A6_7: {
                    if(!ko.toJS(vm.lateOrEarlyInfo1)) {
                        return false;
                    }
                    return ko.toJS(vm.lateOrEarlyInfo1().isIndicate);
                } case IdItem.A6_13: {
                    if(!ko.toJS(vm.lateOrEarlyInfo2)) {
                        return false;
                    }
                    return ko.toJS(vm.lateOrEarlyInfo2().isIndicate);
                } case IdItem.A6_19: {
                    if(!ko.toJS(vm.lateOrEarlyInfo3)) {
                        return false;
                    }
                    return ko.toJS(vm.lateOrEarlyInfo3().isIndicate);
                } case IdItem.A6_25: {
                    if(!ko.toJS(vm.lateOrEarlyInfo4)) {
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
            switch(idItem) {
                case IdItem.A6_7: {
                    if(!ko.toJS(vm.lateOrEarlyInfo1)) {
                        return false;
                    }
                    return ko.toJS(vm.lateOrEarlyInfo1().isActive);
                } case IdItem.A6_13: {
                    if(!ko.toJS(vm.lateOrEarlyInfo2)) {
                        return false;
                    }
                    return ko.toJS(vm.lateOrEarlyInfo2().isActive);
                } case IdItem.A6_19: {
                    if(!ko.toJS(vm.lateOrEarlyInfo3)) {
                        return false;
                    }
                    return ko.toJS(vm.lateOrEarlyInfo3().isActive);
                } case IdItem.A6_25: {
                    if(!ko.toJS(vm.lateOrEarlyInfo4)) {
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
        }

        // ※2＆※８&※10
        public condition2_8_10(idItem: number) {
            return this.condition2() && this.condition8() && this.condition10Display(idItem);
        }
    }

    type MODE = 'before' | 'after';

    const API = {
        initPage: "at/request/application/lateorleaveearly/initPage",
        changeAppDate: "at/request/application/lateorleaveearly/changeAppDate",
        getMsgList: "at/request/application/lateorleaveearly/getMsgList",
        register: "at/request/application/lateorleaveearly/register"
    };

    export class IdItem {
        public static readonly A6_7 : number = 1;
        public static readonly A6_13 : number = 2;
        public static readonly A6_19 : number = 3;
        public static readonly A6_25 : number = 4
    }
}
