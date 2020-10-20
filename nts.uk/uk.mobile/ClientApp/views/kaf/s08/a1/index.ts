import { _, Vue } from '@app/provider';
import { component, Prop, Watch } from '@app/core/component';
import { KafS00AComponent } from '../../../kaf/s00/a';
import { StepwizardComponent } from '@app/components';
import { KafS00BComponent } from '../../../kaf/s00/b';
import { KafS00CComponent } from '../../../kaf/s00/c';
import { KafS08A2Component } from '../../../kaf/s08/a2';
import { KafS00ShrComponent, AppType } from '../../../kaf/s00/shr';
import * as moment from 'moment';


@component({
    name: 'kafs08a1',
    route: '/kaf/s08/a1',
    template: require('./index.vue'),
    validations: {

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

    @Prop({ default: true })
    public readonly mode: boolean;

    public isVisible: boolean = false;
    public date: Date = null;
    public listDate: any[] = [];
    public hidden: boolean = false;

    @Prop({ default: null })
    public params?: any;

    public derpartureTime: number = null;

    public returnTime: number = null;

    @Prop({ default: '' }) public readonly appReason!: string;

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
    };


    public created() {
        const vm = this;
        if (vm.params) {
            console.log(vm.params);
            // vm.mode = false;
            this.data = vm.params;
            vm.derpartureTime = vm.params.businessTripDto.departureTime;
            vm.returnTime = vm.params.businessTripDto.returnTime;
        }
        vm.fetchStart();

        vm.$watch('params', (newV, oldV) => {
            if (newV) {
                vm.data.businessTrip = vm.params.businessTripDto;
                vm.data.businessTripInfoOutput = vm.params.businessTripInfoOutputDto;
                vm.createParamsB();
                vm.createParamsC();
                vm.createParamsA();
            }
        });
    }

    public mounted() {
        
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
                let params = vm.mode ? {
                    mode: vm.mode,
                    companyId: vm.user.companyId,
                    employeeId: vm.user.employeeId,
                    listDates: [],
                    businessTripInfoOutput: null,
                    businessTrip: null
                } :
                    {
                        mode: vm.mode,
                        companyId: vm.user.companyId,
                        employeeId: vm.user.employeeId,
                        listDates: [],
                        businessTrip: vm.params.businessTripDto,
                        businessTripInfoOutput: vm.params.businessTripInfoOutputDto
                    };

                return vm.$http.post('at', API.startKAFS08, params);
            }
        }).then((res: any) => {
            if (!res) {
                return;
            }
            vm.data = res.data;
            vm.createParamsB();
            vm.createParamsC();
            vm.createParamsA();
            vm.$mask('hide');

            setTimeout(function () {
                let focusElem;
                if (vm.mode) {
                    focusElem = document.querySelector('[placeholder=\'yyyy-mm-dd\']');
                } else {
                    focusElem = document.querySelector('[placeholder=\'-- --:--\']');
                }
                (focusElem as HTMLElement).focus();
            }, 200);
        }).catch((err: any) => {
            //do something
        });
    }

    public getDateArray = function (startDate, endDate) {
        let dates = [];
        startDate = moment(startDate, 'YYYY/MM/DD');
        dates.push(startDate.format('YYYY/MM/DD'));
        while (!startDate.isSame(endDate)) {
            startDate = startDate.add(1, 'days');
            dates.push(startDate.format('YYYY/MM/DD'));
        }

        return dates;
    };

    //Nhảy đến step tiếp theo
    public nextToStepTwo() {
        const vm = this;
        let validAll: boolean = true;
        for (let child of vm.$children) {
            if (vm.mode || child.$el.className != 'kafs00b') {
                child.$validate();
                if (!child.$valid) {
                    this.hidden = true;
                    validAll = false;
                }
            }
        }

        if (!validAll) {
            window.scrollTo(500, 0);
            
            return;
        } else {
            vm.hidden = false;
        }
        //check date when press next
        if (vm.mode) {
            //let day = this.kaf000_B_Params.output.endDate.getDate() - this.kaf000_B_Params.output.startDate.getDate();
            let Difference_In_Time = this.kaf000_B_Params.output.endDate.getTime() - this.kaf000_B_Params.output.startDate.getTime();
            let Difference_In_Days = Difference_In_Time / (1000 * 3600 * 24);
            //check day > 31 days between 2 Dates
            if (Difference_In_Days > 31) {
                vm.$modal.error({ messageId: 'Msg_277' });

                return;
            }

            //mode new
            let achievementDetails = vm.data.businessTripInfoOutput.businessTripActualContent;
            let businessTripInfoOutput = vm.data;
            //gửi comment sang màn hình A2
            let commentSet = vm.data.businessTripInfoOutput.setting.appCommentSet;
            let appReason = vm.kaf000_C_Params.output.opAppReason;
            
            vm.application.prePostAtr = vm.kaf000_B_Params.output.prePostAtr;
            vm.application.appDate = vm.$dt.date(vm.kaf000_B_Params.output.startDate, 'YYYY/MM/DD');
            vm.application.opAppStartDate = vm.$dt.date(vm.kaf000_B_Params.output.startDate, 'YYYY/MM/DD');
            if (vm.kaf000_B_Params.input.newModeContent.initSelectMultiDay) {
                vm.application.opAppEndDate = vm.$dt.date(vm.kaf000_B_Params.output.endDate, 'YYYY/MM/DD');
            } else {
                vm.application.opAppEndDate = vm.$dt.date(vm.kaf000_B_Params.output.startDate, 'YYYY/MM/DD');
            }

            vm.$mask('show');

            vm.$http.post('at', API.changeAppDate, {
                isNewMode: true,
                isError: 0,
                application: vm.application,
                businessTrip: null,
                businessTripInfoOutput: vm.data.businessTripInfoOutput
                //businessTrip: vm.mode ? null : vm.data.appWorkChange
            }).then((res: any) => {
                let response = res.data;
                if (response.result) {
                    // this.data.businessTripInfoOutput = response.businessTripInfoOutputDto;
                    if (response.confirmMsgOutputs.length != 0) {
                        vm.handleConfirmMessage(response.confirmMsgOutputs, response);
                    }
                    vm.data.businessTripInfoOutput = response.businessTripInfoOutputDto;
                }
                vm.$emit('nextToStepTwo', vm.listDate, vm.application, businessTripInfoOutput, vm.derpartureTime, vm.returnTime, achievementDetails, commentSet, appReason, vm.mode);

                vm.$mask('hide');
            }).catch((err) => {
                vm.handleErrorMessage(err);
                vm.$mask('hide');
            });
        }

        vm.checkNextButton();

        //mode edit
        if (!vm.mode) {
            vm.data.businessTrip = vm.params.businessTripDto;
            vm.data.businessTripInfoOutput = vm.params.businessTripInfoOutputDto;
            let achievementDetails = vm.data.businessTrip ? vm.data.businessTrip.tripInfos || [] : [];
            // let businessTripInfoOutput = vm.data;
            //gửi comment sang màn hình A2
            let commentSet = vm.data.businessTripInfoOutput.setting.appCommentSet;
            //let application = vm.data.businessTripInfoOutput.appDispInfoStartup.appDetailScreenInfo.application;
            let appReason = vm.kaf000_C_Params.output.opAppReason;
            let startDate = vm.data.businessTripInfoOutput.appDispInfoStartup.appDetailScreenInfo.application.opAppStartDate;
            //let startDateFormat = new Date(startDate);
            let endDate = vm.data.businessTripInfoOutput.appDispInfoStartup.appDetailScreenInfo.application.opAppEndDate;
            //let endDateFormat = new Date(endDate);
            let listDateEditMode = vm.getDateArray(startDate, endDate);
            vm.data.businessTrip.departureTime = vm.derpartureTime;
            vm.data.businessTrip.returnTime = vm.returnTime;
            this.$emit('nextToStepTwo', listDateEditMode, vm.application, vm.data, vm.derpartureTime, vm.returnTime, achievementDetails, commentSet, appReason, vm.mode);
        }
    }

    //scroll to Top
    public scrollToTop() {
        window.scrollTo(500, 0);
    }

    //check button next
    public checkNextButton() {
        const vm = this;
        vm.bindBusinessTripRegister();
    }

    //handle message error
    public handleErrorMessage(res: any) {
        const self = this;
        self.$mask('hide');
        if (res.messageId) {
            return self.$modal.error({ messageId: res.messageId, messageParams: res.parameterIds });
        } else {

            if (_.isArray(res.errors)) {
                return self.$modal.error({ messageId: res.errors[0].messageId, messageParams: res.parameterIds });
            } else {
                return self.$modal.error({ messageId: res.errors.messageId, messageParams: res.parameterIds });
            }
        }
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
                startDate: new Date(),
                endDate: new Date(),
            }
        };
        // if mode edit
        if (!vm.mode) {
            paramb.input.newModeContent = null;
            paramb.input.detailModeContent = {
                prePostAtr: vm.data.businessTripInfoOutput.appDispInfoStartup.appDetailScreenInfo.application.prePostAtr,
                startDate: vm.data.businessTripInfoOutput.appDispInfoStartup.appDetailScreenInfo.application.opAppStartDate,
                endDate: vm.data.businessTripInfoOutput.appDispInfoStartup.appDetailScreenInfo.application.opAppEndDate,
                employeeName: _.isEmpty(vm.data.businessTripInfoOutput.appDispInfoStartup.appDispInfoNoDateOutput.employeeInfoLst) ? 'empty' : vm.data.businessTripInfoOutput.appDispInfoStartup.appDispInfoNoDateOutput.employeeInfoLst[0].bussinessName
            };
        }
        vm.kaf000_B_Params = paramb;
        if (vm.mode) {
            vm.$watch('kaf000_B_Params.output.startDate', (newV, oldV) => {
                if (vm.mode) {
                    let startDate = _.clone(vm.kaf000_B_Params.output.startDate);
                    let endDate = _.clone(vm.kaf000_B_Params.output.endDate);
                    if (_.isNull(startDate)) {
                        
                        return;
                    }
                    
                    vm.listDate = [];
                    if (!vm.kaf000_B_Params.input.newModeContent.initSelectMultiDay) {
                        vm.listDate.push(vm.$dt(newV, 'YYYY/MM/DD'));
                    } else {
                        if (!_.isNull(endDate)) {
                            let isCheckDate = startDate.getTime() <= endDate.getTime();
                            if (vm.kaf000_B_Params.input.newModeContent.initSelectMultiDay && isCheckDate) {
                                while (startDate.getTime() <= endDate.getTime()) {
                                    vm.listDate.push(vm.$dt(startDate, 'YYYY/MM/DD'));
                                    startDate.setDate(startDate.getDate() + 1);
                                }
                            }
        
                        }
                    }
                }
            });

            vm.$watch('kaf000_B_Params.output.endDate', (newV, oldV) => {
                if (vm.mode) {
                    if (!vm.kaf000_B_Params.input.newModeContent.initSelectMultiDay) {

                        return;
                    }
                    let startDate = _.clone(vm.kaf000_B_Params.output.startDate);
                    let endDate = _.clone(vm.kaf000_B_Params.output.endDate);
                    if (_.isNull(endDate)) {

                        return;
                    }
                    
                    vm.listDate = [];
                    if (!_.isNull(startDate)) {
                        let isCheckDate = startDate.getTime() <= endDate.getTime();
                        if (vm.kaf000_B_Params.input.newModeContent.initSelectMultiDay && isCheckDate) {
                            while (startDate.getTime() <= endDate.getTime()) {
                                vm.listDate.push(vm.$dt(startDate, 'YYYY/MM/DD'));
                                startDate.setDate(startDate.getDate() + 1);
                            }
                        }
                    }
                }
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
                opAppStandardReasonCD: vm.mode ? '' : vm.data.businessTripInfoOutput.appDispInfoStartup.appDetailScreenInfo.application.opAppStandardReasonCD,

                // 入力中の申請理由
                // empty
                opAppReason: vm.mode ? '' : vm.data.businessTripInfoOutput.appDispInfoStartup.appDetailScreenInfo.application.opAppReason
            },
            output: {
                // 定型理由
                opAppStandardReasonCD: vm.mode ? '' : vm.data.businessTripInfoOutput.appDispInfoStartup.appDetailScreenInfo.application.opAppStandardReasonCD,
                // 申請理由
                opAppReason: vm.mode ? '' : vm.data.businessTripInfoOutput.appDispInfoStartup.appDetailScreenInfo.application.opAppReason
            }
        };
    }

    public handleConfirmMessage(listMes: any, res: any) {

        if (!_.isEmpty(listMes)) {
            let item = listMes.shift();
            this.$modal.confirm({ messageId: item.messageId }).then((value) => {
                this.$mask('hide');
                if (value == 'yes') {
                    if (_.isEmpty(listMes)) {
                        return;
                    } else {
                        this.handleConfirmMessage(listMes, res);
                    }

                }
            });
        }
    }

}

const API = {
    startKAFS08: 'at/request/application/businesstrip/mobile/startMobile',
    checkBeforeRegister: 'at/request/application/businesstrip/mobile/checkBeforeRegister',
    changeAppDate: 'at/request/application/businesstrip/mobile/changeAppDate'
};

