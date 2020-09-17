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
    validations : {
        derpartureTime : {
            required : true
        },
        returnTime : {
            required : true
        }
    },
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
    public isVisible: boolean = false;
    public derpartureTime: number = null;
    public returnTime: number = null;
    public isValidateAll: Boolean = true;
    public date: Date = null;
    public listDate: any[] = [] ;

    @Prop({ default: (): IParamsB => ({output : {startDate : null,prePostAtr : 0, endDate : null}}) })
    public readonly params?: IParamsB;
    public user: any;
    public title: String = 'KafS08A1';
    public data: any = 'data';
    public application: any = {
        version: 1,
        // appID: '939a963d-2923-4387-a067-4ca9ee8808zz',
        prePostAtr: 1,
        // employeeID: '',
        appType: 3,
        appDate: this.$dt(new Date(), 'YYYY/MM/DD'),
        enteredPerson: '1',
        inputDate: this.$dt(new Date(), 'YYYY/MM/DD HH:mm:ss'),
        reflectionStatus: {
            listReflectionStatusOfDay: [{
                actualReflectStatus: 1,
                scheReflectStatus: 1,
                targetDate: '2020/01/07',
                opUpdateStatusAppReflect: {
                    opActualReflectDateTime: '2020/01/07 20:11:11',
                    opScheReflectDateTime: '2020/01/07 20:11:11',
                    opReasonActualCantReflect: 1,
                    opReasonScheCantReflect: 0

                },
                opUpdateStatusAppCancel: {
                    opActualReflectDateTime: '2020/01/07 20:11:11',
                    opScheReflectDateTime: '2020/01/07 20:11:11',
                    opReasonActualCantReflect: 1,
                    opReasonScheCantReflect: 0
                }
            }]
        },
        // opStampRequestMode: 1,
        // opReversionReason: '1',
        // opAppStartDate: '2020/08/07',
        // opAppEndDate: '2020/08/08',
        // opAppReason: 'jdjadja',
        // opAppStandardReasonCD: 1


    };

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
        // if (vm.derpartureTime == null || vm.returnTime == null ) {
        //    vm.toggleErrorAlert();
        //    vm.scrollToTop();

        //    return ;
        // }
        //gửi table sang màn hình A2
        vm.checkNextButton();
        let achievementDetails = vm.data.businessTripInfoOutput.businessTripActualContent;
        let businessTripInfoOutput = vm.data;
        //gửi comment sang màn hình A2
        let commentSet = vm.data.businessTripInfoOutput.setting.appCommentSet;
        this.$emit('nextToStepTwo',vm.listDate,this.application, businessTripInfoOutput, vm.derpartureTime, vm.returnTime, achievementDetails, commentSet);
    }

    //scroll to Top
    public scrollToTop() {
        window.scrollTo(500,0);
    }

    //check button next
    public checkNextButton() {
        const vm = this;
        // let validAll: boolean = true;
        // vm.isValidateAll = validAll;
        // console.log(validAll);
        // console.log(vm.application);

        // // check validation 
        // this.$validate();
        // if (!this.$valid || !validAll) {
        //     window.scrollTo(500, 0);

        //     return;
        // }
        // if (this.$valid && validAll) {
        //     this.$mask('show');
        // }
        vm.bindBusinessTripRegister();
        //console.log(this.appWorkChangeDto);
    }

    //thực hiện ẩn/hiện alert error
    public toggleErrorAlert() {
        let x = document.getElementById('error');
        if (x.style.display === 'none') {
            x.style.display = 'block';
        } else {
            x.style.display = 'none';
        }
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
                    listDates: [],
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

    public bindBusinessTripRegister() {
        if (this.kaf000_B_Params) {
            if (this.mode) {
                this.application.appDate = this.$dt.date(this.kaf000_B_Params.output.startDate, 'YYYY/MM/DD');
                this.application.opAppStartDate = this.$dt.date(this.kaf000_B_Params.output.startDate, 'YYYY/MM/DD');
                if (this.kaf000_B_Params.input.newModeContent.initSelectMultiDay) {
                    this.application.opAppEndDate = this.$dt.date(this.kaf000_B_Params.output.endDate, 'YYYY/MM/DD');
                } else {
                    this.application.opAppEndDate = this.$dt.date(this.kaf000_B_Params.output.startDate, 'YYYY/MM/DD');
                }
            }

            this.application.prePostAtr = this.kaf000_B_Params.output.prePostAtr;
        }

        if (this.kaf000_C_Params.output) {
            this.application.opAppStandardReasonCD = this.kaf000_C_Params.output.opAppStandardReasonCD;
            this.application.opAppReason = this.kaf000_C_Params.output.opAppReason;
        }
        this.application.enteredPerson = this.user.employeeId;
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
        if (vm.mode) {
            vm.$watch('kaf000_B_Params.output.startDate', (newV, oldV) => {
                console.log('changedate' + oldV + '--' + newV);
                let startDate = _.clone(vm.kaf000_B_Params.output.startDate);
                let endDate = _.clone(vm.kaf000_B_Params.output.endDate);
                if (_.isNull(startDate)) {

                    return;
                }
                //let listDate = [];
                if (!vm.kaf000_B_Params.input.newModeContent.initSelectMultiDay) {
                    vm.listDate.push(vm.$dt(newV, 'YYYY/MM/DD'));
                }

                if (!_.isNull(endDate)) {
                    let isCheckDate = startDate.getTime() <= endDate.getTime();
                    if (vm.kaf000_B_Params.input.newModeContent.initSelectMultiDay && isCheckDate) {
                        while (startDate.getTime() <= endDate.getTime()) {
                            vm.listDate.push(vm.$dt(startDate, 'YYYY/MM/DD'));
                            startDate.setDate(startDate.getDate() + 1);
                        }
                    }

                }

            });

            vm.$watch('kaf000_B_Params.output.endDate', (newV, oldV) => {
                if (!vm.kaf000_B_Params.input.newModeContent.initSelectMultiDay) {

                    return;
                }
                let startDate = _.clone(vm.kaf000_B_Params.output.startDate);
                let endDate = _.clone(vm.kaf000_B_Params.output.endDate);
                if (_.isNull(endDate)) {

                    return;
                }
                //let listDate = [];
                if (!_.isNull(startDate)) {
                    let isCheckDate = startDate.getTime() <= endDate.getTime();
                    if (vm.kaf000_B_Params.input.newModeContent.initSelectMultiDay && isCheckDate) {
                        while (startDate.getTime() <= endDate.getTime()) {
                            vm.listDate.push(vm.$dt(startDate, 'YYYY/MM/DD'));
                            startDate.setDate(startDate.getDate() + 1);
                        }
                    }
                }

                return vm.listDate;
            });
            vm.$watch('kaf000_B_Params.input.newModeContent.initSelectMultiDay', (newV, oldV) => {
                console.log(newV + ':' + oldV);
            });
        }
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

interface IParamsB {
    output: {
        startDate: Date | null,
        prePostAtr: number,
        endDate: Date | null,
    };
}

