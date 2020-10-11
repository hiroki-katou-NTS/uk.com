import { component, Prop, Watch } from '@app/core/component';
import * as _ from 'lodash';
import { KafS00AComponent, KafS00BComponent, KafS00CComponent } from '../../s00';
import { AppType, KafS00ShrComponent } from '../../s00/shr';
import { KafS04BComponent } from '../b';

import {
    ITime,
    IAppDispInfoStartupOutput,
    IApplication,
    IData,
    IParamS00A,
    IInfoOutput,
    IRes,
    IParams,
    ICheck,
    ILateOrLeaveEarlies,
    IResDetail,
} from './define';

@component({
    name: 'kafs04a',
    route: '/kaf/s04/a',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {
        time: {
            attendanceTime: { required: true },
            leaveTime: { required: true },
        }
    },
    components: {
        'kaf-s00-a': KafS00AComponent,
        'kaf-s00-b': KafS00BComponent,
        'kaf-s00-c': KafS00CComponent,
        'kaf-s00-shr': KafS00ShrComponent,
        'kaf-s04-b': KafS04BComponent,
    },
    constraints: []
})
export class KafS04AComponent extends KafS00ShrComponent {
    public title: string = 'KafS04A';
    public kafS00AParams: IParamS00A = null;
    public kafS00BParams: any;
    public kafS00CParams: any;
    public data !: IData;
    public appDispInfoStartupOutput: IAppDispInfoStartupOutput;
    public time: ITime = { attendanceTime: null, leaveTime: null, attendanceTime2: null, leaveTime2: null };
    public conditionLateEarlyLeave2Show: boolean = true;
    public lateOrLeaveEarlies: ILateOrLeaveEarlies;
    public application: IApplication = initAppData();
    public infoOutPut: IInfoOutput = initInfoOutput();
    public paramsAComponent: IParams;
    public cancelAtr: number;
    public check: ICheck = {
        cbCancelLate: {
            value: 'Attendance',
            isDisable: true,
        },
        cbCancelEarlyLeave: {
            value: 'Early',
            isDisable: true,
        },
        cbCancelLate2: {
            value: 'Attendance2',
            isDisable: true,
        },
        cbCancelEarlyLeave2: {
            value: 'Early2',
            isDisable: true,
        }
    };
    public isCheck: boolean = false;


    @Prop({ default: true }) public readonly mode!: boolean;

    @Prop({ default: () => ({}) }) public readonly res!: IResDetail;

    public created() {
        const vm = this;

        if (!vm.mode) {
            // vm.time.attendanceTime = vm.res.arrivedLateLeaveEarly.lateOrLeaveEarlies[0].timeWithDayAttr;
            // vm.time.leaveTime = vm.res.arrivedLateLeaveEarly.lateOrLeaveEarlies[1].timeWithDayAttr;
            vm.res.arrivedLateLeaveEarly.lateOrLeaveEarlies.forEach((item, idx) => {
                if (idx == 0) {
                    vm.time.attendanceTime = item.timeWithDayAttr;
                }
                if (idx == 1) {
                    vm.time.leaveTime = item.timeWithDayAttr;
                }
                if (idx == 2) {
                    vm.time.attendanceTime2 = item.timeWithDayAttr;
                }
                if (idx == 3) {
                    vm.time.leaveTime2 = item.timeWithDayAttr;
                }
            });
        }
        vm.fetchStart();
    }

    public fetchStart() {
        const vm = this;

        vm.$mask('show');
        vm.$auth.user.then((usr) => {
            const { infoOutPut, application } = vm;
            const { employeeId, companyId } = usr;

            application.employeeID = employeeId;
            application.enteredPerson = employeeId;

            infoOutPut.lateEarlyCancelAppSet.companyId = companyId;
        }).then(() => {
            vm.$mask('show');

            return vm.loadCommonSetting(AppType.EARLY_LEAVE_CANCEL_APPLICATION);
        }).then((response: any) => {
            vm.$mask('hide');
            if (response) {
                //thuc hien goi api start KAFS04
                vm.$mask('hide');
                let params = {
                    appDates: [],
                    appDispInfoStartupDto: vm.appDispInfoStartupOutput,
                };
                vm.$mask('show');
                vm.$http.post('at', API.startKAFS04, params).then((res: any) => {
                    vm.data = res.data;
                    vm.initComponentA();
                    vm.initComponetB();
                    vm.initComponentC();
                    vm.$mask('hide');

                    if (!vm.appDispInfoStartupOutput.appDispInfoNoDateOutput.managementMultipleWorkCycles) {
                        vm.conditionLateEarlyLeave2Show = false;
                    } else {
                        vm.conditionLateEarlyLeave2Show = true;
                    }

                    const { data } = vm;
                    const { lateEarlyCancelAppSet } = data;
                    const { cancelAtr } = lateEarlyCancelAppSet;

                    vm.cancelAtr = cancelAtr;

                });
            }
        });
    }

    get showCheckBox() {
        const vm = this;

        if (!vm.mode) {

        }

        if (vm.application.prePostAtr == 1) {
            if (vm.cancelAtr == 1 || vm.cancelAtr == 2) {
                if (vm.cancelAtr == 1) {
                    vm.check.cbCancelLate.value = '';
                    vm.check.cbCancelEarlyLeave.value = '';
                    vm.check.cbCancelLate2.value = '';
                    vm.check.cbCancelEarlyLeave2.value = '';
                }
                if (vm.cancelAtr == 2) {
                    vm.check.cbCancelLate.value = 'Attendance';
                    vm.check.cbCancelEarlyLeave.value = 'Early';
                    vm.check.cbCancelLate2.value = 'Attendance2';
                    vm.check.cbCancelEarlyLeave2.value = 'Early2';
                }

                return true;
            } else {

                return false;
            }
        }
    }

    public initComponentA() {
        const vm = this;
        const { data } = vm;
        const { appDispInfoStartupOutput } = data;

        const { appDispInfoWithDateOutput, appDispInfoNoDateOutput } = appDispInfoStartupOutput;
        const { empHistImport, approvalFunctionSet } = appDispInfoWithDateOutput;

        const [applicationUseSetting] = approvalFunctionSet.appUseSetLst;
        const [receptionRestrictionSetting] = appDispInfoNoDateOutput.applicationSetting.receptionRestrictionSetting;

        vm.$auth.user.then((usr) => {
            const { employeeId, companyId } = usr;

            vm.kafS00AParams = {
                companyID: companyId,
                employeeID: employeeId,
                employmentCD: empHistImport.employmentCode,
                applicationUseSetting,
                receptionRestrictionSetting
            };
        });
    }


    public initComponetB() {
        const vm = this;

        vm.kafS00BParams = null;
        let paramb = {

            input: {
                mode: vm.mode ? 0 : 1,
                appDisplaySetting: vm.data.appDispInfoStartupOutput.appDispInfoNoDateOutput.applicationSetting.appDisplaySetting,
                newModeContent: {
                    // 申請表示情報．申請表示情報(基準日関係なし)．申請設定．申請表示設定																	
                    appTypeSetting: vm.data.appDispInfoStartupOutput.appDispInfoNoDateOutput.applicationSetting.appTypeSetting,
                    useMultiDaySwitch: false,
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

        if (!vm.mode) {
            paramb.input.detailModeContent = {
                prePostAtr: vm.res.appDispInfoStartupOutput.appDetailScreenInfo.application.prePostAtr,
                startDate: vm.res.appDispInfoStartupOutput.appDetailScreenInfo.application.opAppStartDate,
                endDate: vm.res.appDispInfoStartupOutput.appDetailScreenInfo.application.opAppEndDate,
                employeeName: _.isEmpty(vm.res.appDispInfoStartupOutput.appDispInfoNoDateOutput.employeeInfoLst) ? 'empty' : vm.res.appDispInfoStartupOutput.appDispInfoNoDateOutput.employeeInfoLst[0].bussinessName
            };
        }
        vm.kafS00BParams = paramb;
    }

    public initComponentC() {
        const vm = this;

        vm.kafS00CParams = {
            input: {
                // 定型理由の表示
                // 申請表示情報．申請表示情報(基準日関係なし)．定型理由の表示区分
                displayFixedReason: vm.appDispInfoStartupOutput.appDispInfoNoDateOutput.displayStandardReason,
                // 申請理由の表示
                // 申請表示情報．申請表示情報(基準日関係なし)．申請理由の表示区分
                displayAppReason: vm.appDispInfoStartupOutput.appDispInfoNoDateOutput.displayAppReason,
                // 定型理由一覧
                // 申請表示情報．申請表示情報(基準日関係なし)．定型理由項目一覧
                reasonTypeItemLst: vm.appDispInfoStartupOutput.appDispInfoNoDateOutput.reasonTypeItemLst,
                // 申請制限設定
                // 申請表示情報．申請表示情報(基準日関係なし)．申請設定．申請制限設定
                appLimitSetting: vm.appDispInfoStartupOutput.appDispInfoNoDateOutput.applicationSetting.appLimitSetting,
                // 選択中の定型理由
                // empty
                // opAppStandardReasonCD: this.mode ? 1 : this.data.appWorkChangeDispInfo.appDispInfoStartupOutput.appDetailScreenInfo.application.opAppReason,
                // 入力中の申請理由
                // empty
                // opAppReason: this.mode ? 'Empty' : this.data.appWorkChangeDispInfo.appDispInfoStartupOutput.appDetailScreenInfo.application.opAppStandardReasonCD
            },
            output: {
                // 定型理由
                opAppStandardReasonCD: vm.mode ? '' : vm.res.appDispInfoStartupOutput.appDetailScreenInfo.application.opAppStandardReasonCD,
                // 申請理由
                opAppReason: vm.mode ? '' : vm.res.appDispInfoStartupOutput.appDetailScreenInfo.application.opAppReason
            }
        };
        if (vm.kafS00CParams.output) {
            vm.kafS00CParams.output.opAppReason = vm.res.appDispInfoStartupOutput.appDetailScreenInfo.application.opAppReason;
            vm.kafS00CParams.output.opAppStandardReasonCD = vm.res.appDispInfoStartupOutput.appDetailScreenInfo.application.opAppStandardReasonCD;
        }
    }

    public checkBeforeRegister() {
        const vm = this;

        vm.infoOutPut.arrivedLateLeaveEarly.lateOrLeaveEarlies = [];
        vm.infoOutPut.earlyInfos = [];
        vm.infoOutPut.arrivedLateLeaveEarly.lateCancelation = [];

        if (vm.time.attendanceTime != null) {
            vm.infoOutPut.arrivedLateLeaveEarly.lateOrLeaveEarlies.push(
                {
                    lateOrEarlyClassification: 0,
                    timeWithDayAttr: vm.time.attendanceTime,
                    workNo: 1,
                }
            );
            vm.infoOutPut.arrivedLateLeaveEarly.lateCancelation.push(
                {
                    lateOrEarlyClassification: 0,
                    workNo: 1
                }
            );
        }

        vm.infoOutPut.earlyInfos.push(
            {
                category: 0,
                isActive: vm.check.cbCancelLate.isDisable ? false : true,
                isCheck: vm.check.cbCancelLate.value ? true : false,
                workNo: 1,
                isIndicated: true
            }
        );

        if (vm.time.leaveTime != null) {
            vm.infoOutPut.arrivedLateLeaveEarly.lateOrLeaveEarlies.push(
                {
                    lateOrEarlyClassification: 1,
                    timeWithDayAttr: vm.time.leaveTime,
                    workNo: 1,
                }
            );
            vm.infoOutPut.arrivedLateLeaveEarly.lateCancelation.push(
                {
                    lateOrEarlyClassification: 1,
                    workNo: 1
                }
            );
        }

        vm.infoOutPut.earlyInfos.push(
            {
                category: 1,
                isActive: vm.check.cbCancelEarlyLeave.isDisable ? false : true,
                isCheck: vm.check.cbCancelEarlyLeave.value ? true : false,
                workNo: 1,
                isIndicated: true
            });


        if (vm.time.attendanceTime2 != null) {
            vm.infoOutPut.arrivedLateLeaveEarly.lateOrLeaveEarlies.push(
                {
                    lateOrEarlyClassification: 0,
                    timeWithDayAttr: vm.time.attendanceTime2,
                    workNo: 2,
                }
            );
            vm.infoOutPut.arrivedLateLeaveEarly.lateCancelation.push(
                {
                    lateOrEarlyClassification: 0,
                    workNo: 2
                }
            );
        }

        vm.infoOutPut.earlyInfos.push(
            {
                category: 0,
                isActive: vm.check.cbCancelLate2.isDisable ? false : true,
                isCheck: vm.check.cbCancelLate2.value ? true : false,
                workNo: 2,
                isIndicated: true
            });

        if (vm.time.attendanceTime2 != null) {
            vm.infoOutPut.arrivedLateLeaveEarly.lateOrLeaveEarlies.push(
                {
                    lateOrEarlyClassification: 1,
                    timeWithDayAttr: vm.time.leaveTime2,
                    workNo: 2,
                }
            );
            vm.infoOutPut.arrivedLateLeaveEarly.lateCancelation.push(
                {
                    lateOrEarlyClassification: 1,
                    workNo: 2
                }
            );
        }

        vm.infoOutPut.earlyInfos.push(
            {
                category: 1,
                isActive: vm.check.cbCancelEarlyLeave2.isDisable ? false : true,
                isCheck: vm.check.cbCancelEarlyLeave2.value ? true : false,
                workNo: 2,
                isIndicated: true
            });

        vm.checkValidAll();
    }

    public checkValidAll() {
        const vm = this;

        Promise
            .resolve(true)
            .then(() => {
                vm.$validate('time.attendanceTime');
                vm.$validate('time.leaveTime');
            })
            .then(() => vm.$children.forEach((v) => v.$validate()))
            .then(() => {
                if (vm.validAll) {
                    vm.$mask('show');
                    vm.infoOutPut.appDispInfoStartupOutput = vm.data.appDispInfoStartupOutput;

                    let paramsErrorLst = {
                        agentAtr: true,
                        isNew: true,
                        infoOutput: vm.infoOutPut,
                        application: vm.mode ? vm.application : vm.res.appDispInfoStartupOutput.appDetailScreenInfo.application,
                    };

                    vm.$mask('show');
                    vm.$http.post('at', API.getMsgList + '/' + AppType.EARLY_LEAVE_CANCEL_APPLICATION, paramsErrorLst).then((res: any) => {
                        vm.mode ? vm.register() : vm.update();
                        vm.$mask('hide');
                    }).catch((err: any) => {
                        vm.$mask('hide');

                        return vm.handleErrorMessage(err);
                    });
                } else {
                    window.scrollTo(500, 0);
                }
            });
    }

    public register() {
        const vm = this;

        let params = {
            appType: AppType.EARLY_LEAVE_CANCEL_APPLICATION,
            application: vm.application,
            infoOutput: vm.infoOutPut,
        };
        vm.$http.post('at', API.register, params).then((res: IRes) => {
            vm.paramsAComponent = {
                appID: res.data.appID,
                mode: vm.mode,
                res: null,
            };
            vm.$emit('nextComponentA0', vm.paramsAComponent);
            vm.$mask('hide');
        });
    }

    public update() {
        const vm = this;

        let paramsUpdate = {
            application: vm.application,
            arrivedLateLeaveEarlyDto: vm.infoOutPut.arrivedLateLeaveEarly,
            appDispInfoStartupDto: vm.res.appDispInfoStartupOutput
        };
        vm.$mask('show');
        vm.$http.post('at', API.updateApp, paramsUpdate).then((res: any) => {
            vm.$mask('hide');
            vm.paramsAComponent = {
                appID: res.data.appID,
                mode: vm.mode,
                res: null,
            };
            vm.$emit('nextComponentA0', vm.paramsAComponent);
        });
        //do something
    }

    //handle mess dialog
    public handleErrorMessage(res: any) {
        const vm = this;
        vm.$mask('hide');
        if (res.messageId) {
            return vm.$modal.error({ messageId: res.messageId, messageParams: res.parameterIds });
        } else {

            if (_.isArray(res.errors)) {
                return vm.$modal.error({ messageId: res.errors[0].messageId, messageParams: res.parameterIds });
            } else {
                return vm.$modal.error({ messageId: res.errors.messageId, messageParams: res.parameterIds });
            }
        }
    }

    get validAll() {
        const vm = this;

        if (!vm.$valid) {
            vm.$modal.error('Msg_1681');
        }

        return vm.$valid && !vm.$children.some((m) => !m.$valid);
    }

    public handleChangeDate(paramsDate) {
        const vm = this;
        let appDatesLst = [];


        const startDate = vm.$dt(paramsDate.startDate, 'YYYY/MM/DD');

        appDatesLst.push(startDate);

        let params = {
            appDates: appDatesLst,
            appDispNoDate: vm.data.appDispInfoStartupOutput.appDispInfoNoDateOutput,
            appDispWithDate: vm.appDispInfoStartupOutput.appDispInfoWithDateOutput,
            appType: AppType.EARLY_LEAVE_CANCEL_APPLICATION,
            baseDate: startDate,
            setting: vm.infoOutPut.lateEarlyCancelAppSet
        };
        if (paramsDate.startDate) {
            vm.$http.post('at', API.changeAppDate, params).then((res: any) => {
                const { data } = res;
                const { appDispInfoWithDateOutput } = data;
                const { opActualContentDisplayLst } = appDispInfoWithDateOutput;

                opActualContentDisplayLst.forEach((i) => {
                    if (i.opAchievementDetail) {
                        if (i.opAchievementDetail.opWorkTime != null && i.opAchievementDetail.opLeaveTime != null) {
                            vm.time.attendanceTime = i.opAchievementDetail.opWorkTime;
                            vm.time.leaveTime = i.opAchievementDetail.opLeaveTime;
                            vm.time.attendanceTime2 = i.opAchievementDetail.opWorkTime2;
                            vm.time.leaveTime2 = i.opAchievementDetail.opDepartureTime2;

                            vm.check.cbCancelLate.isDisable = false;
                            vm.check.cbCancelEarlyLeave.isDisable = false;
                        }
                    } else {
                        if (vm.application.prePostAtr == 0) {
                            vm.$updateValidator('vm.time.attendanceTime', { required: false });
                            vm.$updateValidator('vm.time.leaveTime', { required: false });
                            vm.time.attendanceTime = null;
                            vm.time.leaveTime = null;
                        } else {
                            vm.$updateValidator('vm.time.attendanceTime', { required: false });
                            vm.$updateValidator('vm.time.leaveTime', { required: false });
                            vm.time.attendanceTime = null;
                            vm.time.leaveTime = null;
                            vm.$modal.error('Msg_1707');
                            vm.check.cbCancelLate.isDisable = true;
                            vm.check.cbCancelEarlyLeave.isDisable = true;

                            return;
                        }
                    }
                });
            });
        }
        if (vm.mode) {
            vm.application.appDate = startDate;
            vm.application.prePostAtr;
            vm.application.opAppStartDate = startDate;
            vm.application.opAppEndDate = startDate;
        }
        if (!vm.mode) {
            vm.res.appDispInfoStartupOutput.appDetailScreenInfo.application;
            const {res} = vm;
            const {appDispInfoStartupOutput} = res;
            
            const {appDetailScreenInfo} = appDispInfoStartupOutput;
            const {application} = appDetailScreenInfo;

            const {reflectionStatus,inputDate,appID,appDate,prePostAtr,opAppStartDate,opAppEndDate} = application;

            vm.application.appDate = appDate;
            vm.application.prePostAtr = prePostAtr;
            vm.application.opAppStartDate = opAppStartDate;
            vm.application.opAppEndDate = opAppEndDate;
            vm.application.appId = appID;
            vm.application.inputDate = inputDate;
            vm.application.reflectionStatus = reflectionStatus;
        }

    }

    public handleChangeAppReason(appReason) {
        const vm = this;

        vm.application.opAppReason = appReason;
    }

    public handleChangeReasonCD(reasonCD) {
        const vm = this;

        vm.application.opAppStandardReasonCD = reasonCD;
    }

    public handleChangePrePost(prePost) {
        const vm = this;

        vm.application.prePostAtr = prePost;
    }

    public mounted() {
        const vm = this;

    }
}

const API = {
    startKAFS04: 'at/request/application/lateorleaveearly/initPage',
    changeAppDate: 'at/request/application/lateorleaveearly/changeAppDate',
    register: 'at/request/application/lateorleaveearly/register',
    getMsgList: 'at/request/application/lateorleaveearly/getMsgList',
    updateApp: 'at/request/application/lateorleaveearly/updateInfoApp',
};

const initAppData = (): IApplication => ({
    appDate: '',
    appId: null,
    appType: AppType.EARLY_LEAVE_CANCEL_APPLICATION,
    employeeID: '',
    enteredPerson: null,
    inputDate: null,
    opAppEndDate: '',
    opAppReason: '',
    opAppStandardReasonCD: null,
    opAppStartDate: '',
    opReversionReason: null,
    opStampRequestMode: null,
    prePostAtr: null,
    reflectionStatus: null,
    version: null
});

const initInfoOutput = (): IInfoOutput => ({
    appDispInfoStartupOutput: null,
    arrivedLateLeaveEarly: {
        lateCancelation: [],
        lateOrLeaveEarlies: [],
    },
    earlyInfos: [{
        category: 0,
        isActive: true,
        isCheck: false,
        isIndicated: true,
        workNo: 0,
    }],
    info: '',
    lateEarlyCancelAppSet: {
        cancelAtr: 0,
        companyId: '',
    }
});

