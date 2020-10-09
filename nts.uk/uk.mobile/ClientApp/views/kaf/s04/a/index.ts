import { component, Prop, Watch } from '@app/core/component';
import { KafS00AComponent, KafS00BComponent, KafS00CComponent } from '../../s00';
import { AppType, KafS00ShrComponent } from '../../s00/shr';
import { KafS04BComponent } from '../b';

import {
    IOutput,
    ITime,
    IAppDispInfoStartupOutput,
    IApplication,
    IData,
    IParamS00A,
    IParamS00B,
    IParamS00C,
    IInfoOutput,
    IRes,
    IParams,
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
    public kafS00BParams: IParamS00B = null;
    public kafS00CParams: IParamS00C = null;
    public data !: IData;
    public appDispInfoStartupOutput: IAppDispInfoStartupOutput;
    public time: ITime = { attendanceTime: null, leaveTime: null, attendanceTime2: null, leaveTime2: null };
    public conditionLateEarlyLeave2Show: boolean = true;
    public application: IApplication = initAppData();
    public infoOutPut: IInfoOutput = initInfoOutput();
    public paramsAComponent: IParams;
    public cbCancelLate: string = 'Attendance';

    @Prop({ default: true }) public readonly mode!: boolean;

    public created() {
        const vm = this;

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
                    // if (vm.application.prePostAtr == 1) {
                    //     const { infoOutPut } = vm;
                    //     const { lateEarlyCancelAppSet } = infoOutPut;
                    //     const { cancelAtr } = lateEarlyCancelAppSet;

                    //     if (cancelAtr == 0) {
                    //         vm.showCheckBox == false;
                    //     }
                    //     if (cancelAtr == 1) {
                    //         alert('abc');
                    //     } else {
                    //         vm.showCheckBox == true;
                    //     }
                    // }
                });
            }
        });
    }

    get showCheckBox() {
        const vm = this;

        return vm.application.prePostAtr == 1;
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

        let input = {
            mode: 0,
            appTypeSetting: vm.data.appDispInfoStartupOutput.appDispInfoNoDateOutput.applicationSetting.appTypeSetting,
            newModeContent: {
                appTypeSetting: vm.data.appDispInfoStartupOutput.appDispInfoNoDateOutput.applicationSetting.appTypeSetting,
                useMultiDaySwitch: false,
                initSelectMultiDay: false
            },
            appDisplaySetting: {
                prePostDisplayAtr: null,
                manualSendMailAtr: null,
            },
            detailModeContent: null
        };

        let output: IOutput = {
            prePostAtr: 0,
            startDate: null,
            endDate: null
        };

        vm.kafS00BParams = {
            input,
            output
        };
    }

    public initComponentC() {
        const vm = this;

        let input = {
            displayFixedReason: vm.data.appDispInfoStartupOutput.appDispInfoNoDateOutput.displayStandardReason,
            displayAppReason: vm.data.appDispInfoStartupOutput.appDispInfoNoDateOutput.displayAppReason,
            reasonTypeItemLst: vm.data.appDispInfoStartupOutput.appDispInfoNoDateOutput.reasonTypeItemLst,
            appLimitSetting: vm.data.appDispInfoStartupOutput.appDispInfoNoDateOutput.applicationSetting.appLimitSetting
        };
        let output = {
            opAppStandardReasonCD: '',
            opAppReason: ''
        };

        vm.kafS00CParams = {
            input,
            output,
        };
    }

    public register() {
        const vm = this;


        vm.infoOutPut.arrivedLateLeaveEarly.lateOrLeaveEarlies = [];

        if (vm.time.attendanceTime != null) {
            vm.infoOutPut.arrivedLateLeaveEarly.lateOrLeaveEarlies.push(
                {
                    lateOrEarlyClassification: 0,
                    timeWithDayAttr: vm.time.attendanceTime,
                    workNo: 1,
                }
            );
        }

        if (vm.time.leaveTime != null) {
            vm.infoOutPut.arrivedLateLeaveEarly.lateOrLeaveEarlies.push(
                {
                    lateOrEarlyClassification: 1,
                    timeWithDayAttr: vm.time.leaveTime,
                    workNo: 1,
                }
            );
        }


        if (vm.time.attendanceTime2 != null) {
            vm.infoOutPut.arrivedLateLeaveEarly.lateOrLeaveEarlies.push(
                {
                    lateOrEarlyClassification: 0,
                    timeWithDayAttr: vm.time.attendanceTime2,
                    workNo: 2,
                }
            );
        }

        if (vm.time.attendanceTime2 != null) {
            vm.infoOutPut.arrivedLateLeaveEarly.lateOrLeaveEarlies.push(
                {
                    lateOrEarlyClassification: 1,
                    timeWithDayAttr: vm.time.leaveTime2,
                    workNo: 2,
                }
            );
        }

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
                    let params = {
                        appType: AppType.EARLY_LEAVE_CANCEL_APPLICATION,
                        application: vm.application,
                        infoOutput: vm.infoOutPut,
                    };
                    vm.$http.post('at', API.register, params).then((res: IRes) => {
                        vm.paramsAComponent = {
                            appID: res.data.appID,
                            mode: vm.mode,
                        };
                        vm.$emit('nextComponentA0', vm.paramsAComponent);
                        vm.$mask('hide');
                    });
                } else {
                    window.scrollTo(500, 0);
                }
            });
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

                console.log(opActualContentDisplayLst);
                opActualContentDisplayLst.forEach((i) => {
                    if (i.opAchievementDetail) {
                        if (i.opAchievementDetail.opWorkTime != null && i.opAchievementDetail.opLeaveTime != null) {
                            vm.time.attendanceTime = i.opAchievementDetail.opWorkTime;
                            vm.time.leaveTime = i.opAchievementDetail.opLeaveTime;
                            vm.time.attendanceTime2 = i.opAchievementDetail.opWorkTime2;
                            vm.time.leaveTime2 = i.opAchievementDetail.opDepartureTime2;
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

                            return;
                        }
                    }
                });
            });
        }
        vm.application.appDate = startDate;
        vm.application.prePostAtr;
        vm.application.opAppStartDate = startDate;
        vm.application.opAppEndDate = startDate;
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
    getMsgList: 'at/request/application/lateorleaveearly/getMsgList'
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

