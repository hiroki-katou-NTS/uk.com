import { _, Vue } from '@app/provider';
import { component, Prop, Watch } from '@app/core/component';
import { KafS00AComponent } from '../../../kaf/s00/a';
import { StepwizardComponent } from '@app/components';
import { KafS00BComponent } from '../../../kaf/s00/b';
import { KafS00CComponent } from '../../../kaf/s00/c';
import { KafS00ShrComponent, AppType } from '../../../kaf/s00/shr';

@component({
    name: 'kafs08a1',
    route: '/kaf/s08/a1',
    template: require('./index.vue'),
    style: require('./style.scss'),
    components: {
        'kafs00-a': KafS00AComponent,
        'step-wizard': StepwizardComponent,
        'kafs00-b': KafS00BComponent,
        'kafs00-c': KafS00CComponent,
    }
})
export class KAFS08A1ViewModel extends KafS00ShrComponent {
    public kaf000_A_Params: any = null;
    public kaf000_B_Params: any = null;
    public kaf000_C_Params: any = null;
    public timeWithDay: number | null = null;

    @Prop({ default: null })
    public params?: any;

    public user: any;
    public step = 'KAFS08_10';
    public title: String = 'KafS08A1';
    public mode: Boolean = true;
    public data: any = 'data';

    public created() {
        const self = this;

        if (self.params) {
            console.log(self.params);
            self.mode = false;
            this.data = self.params;
        }

        self.fetchStart();
    }

    public nextToScreenA2() {
        const vm = this;
        //vm.$goto("kafs08a2",{});
        vm.step = 'KAFS08_11';
    }

    public fetchStart() {
        const self = this;

        self.$mask('show');

        self.$auth.user.then((usr: any) => {
            self.user = usr;
        }).then(() => {
            return self.loadCommonSetting(AppType.WORK_CHANGE_APPLICATION);
        }).then((res: any) => {
            // if (!res) {
            //     return;
            // }
            self.data = res.data;
            self.createParamsB();
            self.createParamsC();
            self.createParamsA();
            self.$mask('hide');
        }).catch((err: any) => {
            self.$mask('hide');
        });
    }

    public createParamsA() {
        const self = this;
        let appDispInfoWithDateOutput = self.appDispInfoStartupOutput.appDispInfoWithDateOutput;
        let appDispInfoNoDateOutput = self.appDispInfoStartupOutput.appDispInfoNoDateOutput;
        self.kaf000_A_Params = {
            companyID: self.user.companyId,
            employeeID: self.user.employeeId,
            // 申請表示情報．申請表示情報(基準日関係あり)．社員所属雇用履歴を取得．雇用コード
            employmentCD: appDispInfoWithDateOutput.empHistImport.employmentCode,
            // 申請表示情報．申請表示情報(基準日関係あり)．申請承認機能設定．申請利用設定
            applicationUseSetting: appDispInfoWithDateOutput.approvalFunctionSet.appUseSetLst[0],
            // 申請表示情報．申請表示情報(基準日関係なし)．申請設定．受付制限設定
            receptionRestrictionSetting: appDispInfoNoDateOutput.applicationSetting.receptionRestrictionSetting,
            // opOvertimeAppAtr: null
        };
    }

    public createParamsB() {
        const vm = this;
        let paramb = {
            input: {
                // mode: 0,

                // appDisplaySetting: {
                //     prePostDisplayAtr: 1,
                //     manualSendMailAtr: 0
                // },
                // newModeContent: {
                //     appTypeSetting: {
                //         appType: 2,
                //         sendMailWhenRegister: false,
                //         sendMailWhenApproval: false,
                //         displayInitialSegment: 1,
                //         canClassificationChange: true
                //     },
                //     useMultiDaySwitch: true,
                //     initSelectMultiDay: true
                // }
                mode: vm.mode ? 0 : 1,
                appDisplaySetting: vm.appDispInfoStartupOutput.appDispInfoNoDateOutput.applicationSetting.appDisplaySetting,
                newModeContent: {
                    // 申請表示情報．申請表示情報(基準日関係なし)．申請設定．申請表示設定																	
                    appTypeSetting: vm.appDispInfoStartupOutput.appDispInfoNoDateOutput.applicationSetting.appTypeSetting,
                    useMultiDaySwitch: true,
                    initSelectMultiDay: false
                },
                detailModeContent: null


            },
            output: {
                prePostAtr: 0,
                startDate: null,
                endDate: null
            }
        };
        vm.kaf000_B_Params = paramb;
    }

    // onComponentChange(data: any) {
    //     const vm = this;

    //     console.log(data);
    // }

    public createParamsC() {
        const vm = this;

        // KAFS00_C_起動情報
        const { appDispInfoStartupOutput } = vm;
        const { appDispInfoNoDateOutput } = appDispInfoStartupOutput;

        vm.kaf000_C_Params = {
            input: {
                // 定型理由の表示
                // 申請表示情報．申請表示情報(基準日関係なし)．定型理由の表示区分
                displayFixedReason: appDispInfoNoDateOutput.displayFixedReason,
                // 申請理由の表示
                // 申請表示情報．申請表示情報(基準日関係なし)．申請理由の表示区分
                displayAppReason: true,
                // 定型理由一覧
                // 申請表示情報．申請表示情報(基準日関係なし)．定型理由項目一覧
                reasonTypeItemLst: appDispInfoNoDateOutput.reasonTypeItemLst,
                // 申請制限設定
                // 申請表示情報．申請表示情報(基準日関係なし)．申請設定．申請制限設定
                appLimitSetting: appDispInfoNoDateOutput.applicationSetting.appLimitSetting,
                // 選択中の定型理由
                // empty
                // opAppStandardReasonCD: this.mode ? 1 : this.data.appWorkChangeDispInfo.appDispInfoStartupOutput.appDetailScreenInfo.application.opAppReason,
                // 入力中の申請理由
                // empty
                // opAppReason: this.mode ? 'Empty' : this.data.appWorkChangeDispInfo.appDispInfoStartupOutput.appDetailScreenInfo.application.opAppStandardReasonCD
            },
            output: {
                // 定型理由
                opAppStandardReasonCD: vm.mode ? 1 : vm.appDispInfoStartupOutput.appDetailScreenInfo.application.opAppStandardReasonCD,
                // 申請理由
                opAppReason: vm.mode ? '' : vm.appDispInfoStartupOutput.appDetailScreenInfo.application.opAppReason
            }
        };
    }

    public mounted() {
        let self = this;
    }

}

