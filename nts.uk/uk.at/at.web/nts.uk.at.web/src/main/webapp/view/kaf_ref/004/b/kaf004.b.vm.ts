module nts.uk.at.view.kaf004_ref.b.viewmodel {
    import Application = nts.uk.at.view.kaf000_ref.shr.viewmodel.Application;
    import CommonProcess = nts.uk.at.view.kaf000_ref.shr.viewmodel.CommonProcess;
    import WorkManagement = nts.uk.at.view.kaf004_ref.shr.common.viewmodel.WorkManagement;
    import LateOrEarlyInfo = nts.uk.at.view.kaf004_ref.shr.common.viewmodel.LateOrEarlyInfo;
    import ArrivedLateLeaveEarlyInfo = nts.uk.at.view.kaf004_ref.shr.common.viewmodel.ArrivedLateLeaveEarlyInfo;

    @bean()
    class KAF004AViewModel extends ko.ViewModel {
        application: KnockoutObservable<Application>;
        workManagement: WorkManagement;
        arrivedLateLeaveEarlyInfo: KnockoutObservable<ArrivedLateLeaveEarlyInfo>;
        lateOrEarlyInfos: KnockoutObservableArray<LateOrEarlyInfo>;
        lateOrEarlyInfo1: KnockoutObservable<LateOrEarlyInfo>;
        lateOrEarlyInfo2: KnockoutObservable<LateOrEarlyInfo>;
        lateOrEarlyInfo3: KnockoutObservable<LateOrEarlyInfo>;
        lateOrEarlyInfo4: KnockoutObservable<LateOrEarlyInfo>;

        mode: KnockoutObservable<MODE>;


        created(params: any) {
            const vm = this;

            vm.application = ko.observable(new Application("", 1, 2, ""));
            vm.workManagement = new WorkManagement('8:30', '17:30', '8:30', '17:30', null, 1750, 900, 1750);
            vm.arrivedLateLeaveEarlyInfo = ko.observable(ArrivedLateLeaveEarlyInfo.initArrivedLateLeaveEarlyInfo());

            vm.mode = ko.observable('before');

            // Subcribe when mode change -> clear data if mode is 'before'
            vm.mode.subscribe(() => {
                if(ko.toJS(vm.mode) === 'before') {
                    vm.workManagement.clearData();
                }
            })
        }

        mounted() {
            const vm = this;

            vm.application().appDate(moment(new Date()).format("YYYY/MM/DD"));
            vm.lateOrEarlyInfos = vm.arrivedLateLeaveEarlyInfo().earlyInfos;

            // vm.lateOrEarlyInfo1 = ko.observable(_.filter(vm.lateOrEarlyInfos, {'workNo': 1, 'category': 0}));
            // vm.lateOrEarlyInfo2 = ko.observable(_.filter(vm.lateOrEarlyInfos, {'workNo': 1, 'category': 1}));
            // vm.lateOrEarlyInfo3 = ko.observable(_.filter(vm.lateOrEarlyInfos, {'workNo': 2, 'category': 0}));
            // vm.lateOrEarlyInfo4 = ko.observable(_.filter(vm.lateOrEarlyInfos, {'workNo': 2, 'category': 1}));

            vm.lateOrEarlyInfo1 = ko.observable(new LateOrEarlyInfo(true, 1, true, true, 0));
            vm.lateOrEarlyInfo2 = ko.observable(new LateOrEarlyInfo(true, 1, true, true, 1));
            vm.lateOrEarlyInfo3 = ko.observable(new LateOrEarlyInfo(false, 2, false, true, 0));
            vm.lateOrEarlyInfo4 = ko.observable(new LateOrEarlyInfo(false, 2, true, true, 1));
        }

        register() {
            const vm = this;

            console.log(ko.toJS(vm.application()));
            console.log(vm.workManagement);
        }

        // ※2
        public condition2(): boolean {
            const vm = this;

            // 「遅刻早退取消申請起動時の表示情報」.申請表示情報.申請設定（基準日関係なし）.複数回勤務の管理＝true
            // return vm.arrivedLateLeaveEarlyInfo.appDispInfoStartupOutput.appDispInfoNoDateOutput.
            return true;
        }

        // ※9
        public condition9(idItem: number) {
            // 事前事後区分に「事後」に選択している場合　（事後モード）
            return ko.toJS(this.mode) === 'before';
        }

        // ※8
        public condition8(idItem: number) {

            // 事前事後区分に「事後」を選択している場合　（事後モード）  (T/h select "xin sau"「事後」 trên 事前事後区分/Phân loại xin trước xin sau (Mode after/xin sau)
            if (ko.toJS(this.mode) === 'after') {

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
            switch(idItem) {
                case IdItem.B6_7: {
                    if(!ko.toJS(vm.lateOrEarlyInfo1)) {
                        return false;
                    }
                    return ko.toJS(vm.lateOrEarlyInfo1().isIndicate);
                } case IdItem.B6_13: {
                    if(!ko.toJS(vm.lateOrEarlyInfo2)) {
                        return false;
                    }
                    return ko.toJS(vm.lateOrEarlyInfo2().isIndicate);
                } case IdItem.B6_19: {
                    if(!ko.toJS(vm.lateOrEarlyInfo3)) {
                        return false;
                    }
                    return ko.toJS(vm.lateOrEarlyInfo3().isIndicate);
                } case IdItem.B6_25: {
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
                case IdItem.B6_7: {
                    if(!ko.toJS(vm.lateOrEarlyInfo1)) {
                        return false;
                    }
                    return ko.toJS(vm.lateOrEarlyInfo1().isActive);
                } case IdItem.B6_13: {
                    if(!ko.toJS(vm.lateOrEarlyInfo2)) {
                        return false;
                    }
                    return ko.toJS(vm.lateOrEarlyInfo2().isActive);
                } case IdItem.B6_19: {
                    if(!ko.toJS(vm.lateOrEarlyInfo3)) {
                        return false;
                    }
                    return ko.toJS(vm.lateOrEarlyInfo3().isActive);
                } case IdItem.B6_25: {
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

        // ※2＆※9&※10
        public condition2_9_10(idItem: number) {
            return this.condition2 && this.condition9(idItem) && this.condition10Display(idItem);
        }

        public getValueDatePick(idItem: number) {
            const vm = this,
                lateOrLeaveEarlies = ko.toJS(vm.arrivedLateLeaveEarlyInfo())
                    .arrivedLateLeaveEarly.lateOrLeaveEarlies,
                achievementOutputLst = ko.toJS(vm.arrivedLateLeaveEarlyInfo())
                    .appDispInfoStartupOutput.appDispInfoWithDateOutput.achievementOutputLst;

            switch(idItem) {
                case 1: {
                    if(!!_.filter(lateOrLeaveEarlies, {'workNo': 1, 'category': 0}).timeWithDayAttr) {
                        return _.filter(lateOrLeaveEarlies, {'workNo': 1, 'category': 0}).timeWithDayAttr;
                    }

                    return _.filter(achievementOutputLst, {'achivementDatail.recordClassification': '日別実績'}).achivementDatail.workTime5;
                } case 2: {
                    if(!!_.filter(lateOrLeaveEarlies, {'workNo': 1, 'category': 1}).timeWithDayAttr) {
                        return _.filter(lateOrLeaveEarlies, {'workNo': 1, 'category': 1}).timeWithDayAttr;
                    }

                    return _.filter(achievementOutputLst, {'achivementDatail.recordClassification': '日別実績'}).achivementDatail.leaveTime6;
                } case 3: {
                    if(!!_.filter(lateOrLeaveEarlies, {'workNo': 2, 'category': 0}).timeWithDayAttr) {
                        return _.filter(lateOrLeaveEarlies, {'workNo': 2, 'category': 0}).timeWithDayAttr;
                    }

                    return _.filter(achievementOutputLst, {'achivementDatail.recordClassification': '日別実績'}).achivementDatail.workTime9_2;
                } case 4: {
                    if(!!_.filter(lateOrLeaveEarlies, {'workNo': 2, 'category': 1}).timeWithDayAttr) {
                        return _.filter(lateOrLeaveEarlies, {'workNo': 2, 'category': 1}).timeWithDayAttr;
                    }

                    return _.filter(achievementOutputLst, {'achivementDatail.recordClassification': '日別実績'}).achivementDatail.departureTime10_2;
                } default: {
                    return '';
                }
            }

        }
    }

    type MODE = 'before' | 'after';

    const API = {
        startNew: "at/request/application/workchange/startNew"
    };

    export class IdItem {
        public static readonly B6_7 : number = 1;
        public static readonly B6_13 : number = 2;
        public static readonly B6_19 : number = 3;
        public static readonly B6_25 : number = 4
    }
}
