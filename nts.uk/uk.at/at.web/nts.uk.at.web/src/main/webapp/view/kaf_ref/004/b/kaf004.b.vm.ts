module nts.uk.at.view.kaf004_ref.b.viewmodel {
    import Application = nts.uk.at.view.kaf000_ref.shr.viewmodel.Application;
    import CommonProcess = nts.uk.at.view.kaf000_ref.shr.viewmodel.CommonProcess;
    import WorkManagement = nts.uk.at.view.kaf004_ref.shr.viewmodel.WorkManagement;

    @bean()
    class KAF004AViewModel extends ko.ViewModel {
        application: KnockoutObservable<Application>;
        commonSetting: any;
        workManagement: KnockoutObservable<WorkManagement>;

        mode: KnockoutObservable<MODE>;


        created(params: any) {
            const vm = this;

            vm.application = ko.observable(new Application("", 1, 2, ""));
            vm.commonSetting = ko.observable(CommonProcess.initCommonSetting);
            vm.workManagement = ko.observable(new WorkManagement('--:--', '--:--', '--:--', '--:--', '', '', '', ''));

            vm.mode = ko.observable('before');
        }

        mounted() {
            const vm = this;

            vm.application().appDate(moment(new Date()).format("YYYY/MM/DD"));

        }

        register() {
            const vm = this;

            console.log(ko.toJS(vm.application()));
            console.log(ko.toJS(vm.workManagement()));
        }

        // ※2
        public condition2(): boolean {
            // 「遅刻早退取消申請起動時の表示情報」.申請表示情報.申請設定（基準日関係なし）.複数回勤務の管理＝true
            return false;
        }

        // ※8
        public condition8() {
            // 事前事後区分に「事後」に選択している場合　（事後モード）
            return true;
        }

        // ※13
        public condition13() {

            // 事前事後区分に「事後」を選択している場合　（事後モード）  (T/h select "xin sau"「事後」 trên 事前事後区分/Phân loại xin trước xin sau (Mode after/xin sau)
            if (ko.toJS(this.mode) === 'after') {

                // 起動したら、実績データがある場合 (Sau khi khởi động t/h có data thực tế)
                return false;
            }

            return true;
        }

        // ※10 display
        public condition10Display() {
            // 取り消す初期情報.表示する
            return true;
        }

        // ※10 activation
        public condition10Activation() {
            // 取り消す初期情報.活性する
            return true;
        }

        // ※8＆※10
        public condition8_10() {
            return this.condition8 && this.condition10Display;
        }

        // ※2＆※８&※10
        public condition2_8_10() {
            return this.condition2 && this.condition8 && this.condition10Display;
        }
    }

    type MODE = 'before' | 'after';

    const API = {
        startNew: "at/request/application/workchange/startNew"
    };
}
