import { _, Vue } from '@app/provider';
import { component, Prop, Watch } from '@app/core/component';
import { KafS00AComponent } from '../../../kaf/s00/a';
import { StepwizardComponent } from '@app/components';
import { KafS00BComponent } from '../../../kaf/s00/b';
import { KafS00CComponent } from '../../../kaf/s00/c';
import { KafS08A2Component } from '../../../kaf/s08/a2';
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
        'kafs08-a2': KafS08A2Component,
    }
})
export class KAFS08A1Component extends KafS00ShrComponent {
    public kaf000_A_Params: any = null;
    public kaf000_B_Params: any = null;
    public kaf000_C_Params: any = null;
    //private seen: boolean = true;
    public step: string = 'KAFS08_10';
    public mode: Boolean = true;
    public departureTime: number = null;
    public returnTime: number = null;

    @Prop({ default:() => ({}) })
    public params?: any;

    public user: any;
    public title: String = 'KafS08A1';
    public data: any = 'data';

    public created() {
        const vm = this;

        if (vm.params) {
            console.log(vm.params);
            //vm.mode = false;
            this.data = vm.params;
        }

        vm.fetchStart();
    }

    //Nhảy đến step tiếp theo
    public nextToStepTwo() {
        const vm = this;
        //kiểm tra nghiệp vụ trước khi nhảy đến step tiếp theo
        //if (vm.params1.derpartureTime == null || vm.params1.returnTime == null ) {
        //    return ;
        //}
        //gửi table sang màn hình A2
        let achievementDetails = vm.data.businessTripInfoOutput.businessTripActualContent;
        //gửi comment sang màn hình A2
        let commentSet = vm.data.businessTripInfoOutput.setting.appCommentSet;
        this.$emit('nextToStepTwo',vm.departureTime,vm.returnTime,achievementDetails,commentSet);
    }

    public fetchStart() {
        const vm = this;

        vm.$mask('show');

        vm.$auth.user.then((usr: any) => {
            vm.user = usr;
        }).then(() => {
            return vm.loadCommonSetting(AppType.BUSINESS_TRIP_APPLICATION);
        }).then((loadData: any) => {
            if (loadData) {
                return vm.$http.post('at', API.startKAFS08, {
                    mode: vm.mode,
                    companyId: vm.user.companyId,
                    employeeId: vm.user.employeeId,
                    listDates: ['2020/03/12','2020/03/13','2020/03/14','2020/03/15','2020/03/16'],
                    businessTripInfoOutput: vm.mode ? null : vm.data,
                    //businessTrip: vm.mode ? null : vm.data.appWorkChange
                }).then((res: any) => {
                    if (!res) {
                        return;
                    }
                    vm.data = res.data;
                    vm.createParamsB();
                    vm.createParamsC();
                    vm.createParamsA();
                    vm.$mask('hide');
                });
            }
        }).catch((err: any) => {
            vm.$mask('hide');
        });
    }

    public createParamsA() {
        const vm = this;
        let appDispInfoWithDateOutput = vm.data.businessTripInfoOutput.appDispInfoStartup.appDispInfoWithDateOutput;
        let appDispInfoNoDateOutput = vm.data.businessTripInfoOutput.appDispInfoStartup.appDispInfoNoDateOutput;
        vm.kaf000_A_Params = {
            companyID: vm.user.companyId,
            employeeID: vm.user.employeeId,
            // 申請表示情報．申請表示情報(基準日関係あり)．社員所属雇用履歴を取得．雇用コード
            employmentCD: appDispInfoWithDateOutput.empHistImport.employmentCode,
            // 申請表示情報．申請表示情報(基準日関係あり)．申請承認機能設定．申請利用設定
            applicationUseSetting: appDispInfoWithDateOutput.approvalFunctionSet.appUseSetLst[0],
            // 申請表示情報．申請表示情報(基準日関係なし)．申請設定．受付制限設定
            receptionRestrictionSetting: appDispInfoNoDateOutput.applicationSetting.receptionRestrictionSetting[0],
            // opOvertimeAppAtr: null
        };
    }

    public createParamsB() {
        const vm = this;
        let paramb = {
            input: {
                mode: vm.mode ? 0 : 1,
                appDisplaySetting: vm.data.businessTripInfoOutput.appDispInfoStartup.appDispInfoNoDateOutput.applicationSetting.appDisplaySetting,
                newModeContent: {
                    // 申請表示情報．申請表示情報(基準日関係なし)．申請設定．申請表示設定																	
                    appTypeSetting: vm.data.businessTripInfoOutput.appDispInfoStartup.appDispInfoNoDateOutput.applicationSetting.appTypeSetting,
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


    public createParamsC() {
        const vm = this;
        // KAFS00_C_起動情報
        let appDispInfoNoDateOutput = vm.data.businessTripInfoOutput.appDispInfoStartup.appDispInfoNoDateOutput;
        vm.kaf000_C_Params = {
            input: {
                // 定型理由の表示
                // 申請表示情報．申請表示情報(基準日関係なし)．定型理由の表示区分
                displayFixedReason: appDispInfoNoDateOutput.displayStandardReason,
                // 申請理由の表示
                // 申請表示情報．申請表示情報(基準日関係なし)．申請理由の表示区分
                displayAppReason: appDispInfoNoDateOutput.displayAppReason,
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
                opAppStandardReasonCD: vm.mode ? '' : null,
                // 申請理由
                opAppReason: vm.mode ? '' : null
            }
        };
    }

    public mounted() {
        let vm = this;
    }
}

const API = {
    startKAFS08: 'at/request/application/businesstrip/mobile/startMobile',
    checkBeforeRegister: 'at/request/application/businesstrip/mobile/checkBeforeRegister'
};


