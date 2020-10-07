import { component, Prop, Watch } from '@app/core/component';
import { data } from 'jquery';
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
    IRes
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
    public user !: any;
    public data !: IData;
    public appDispInfoStartupOutput: IAppDispInfoStartupOutput;
    public time: ITime = { attendanceTime: null, leaveTime: null, attendanceTime2: null, leaveTime2: null };
    public conditionLateEarlyLeave2Show: boolean = true;
    public application: IApplication = initAppData();
    public infoOutPut: IInfoOutput = initInfoOutput();
    public startDate: string = null;
    public opAppReason: string = null;
    public reasonCD: number = 0;
    public prePostArt: number = 0;

    public created() {
        const vm = this;

        vm.fetchStart();
    }

    public fetchStart() {
        const vm = this;

        vm.$mask('show');
        vm.$auth.user.then((usr: any) => {
            vm.user = usr;
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
                });
            }
        });
    }

    get showCheckBox() {
        const vm = this;

        if (vm.kafS00BParams != null) {
            return vm.kafS00BParams.output.prePostAtr === 1;
        }
    }

    public initComponentA() {
        const vm = this;

        vm.kafS00AParams = {
            companyID: vm.user.companyId,
            employeeID: vm.user.employeeId,
            employmentCD: vm.data.appDispInfoStartupOutput.appDispInfoWithDateOutput.empHistImport.employmentCode,
            applicationUseSetting: vm.data.appDispInfoStartupOutput.appDispInfoWithDateOutput.approvalFunctionSet.appUseSetLst[0],
            receptionRestrictionSetting: vm.data.appDispInfoStartupOutput.appDispInfoNoDateOutput.applicationSetting.receptionRestrictionSetting[0],
        };
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
                    vm.$http.post('at', API.register,params).then((res: IRes) => {
                        console.log(res.data.appID);
                        alert('qua man tiep theo');
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
        
        vm.startDate = vm.$dt(paramsDate.startDate,'YYYY/MM/DD');
        vm.application.appDate = vm.startDate;
        vm.application.prePostAtr = vm.prePostArt;
        vm.application.opAppStartDate = vm.startDate;
        vm.application.opAppEndDate = vm.startDate;
    }

    public handleChangeAppReason(appReason) {
        const vm = this;

        vm.opAppReason = appReason;
        vm.application.opAppReason = vm.opAppReason;
    }

    public handleChangeReasonCD(reasonCD) {
        const vm = this;

        vm.reasonCD = reasonCD;
        vm.application.opAppStandardReasonCD = vm.reasonCD;
    }

    public handleChangePrePost(prePost) {
        const vm = this;

        vm.prePostArt = prePost;
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
        lateOrLeaveEarlies: [{
            lateOrEarlyClassification: 0,
            timeWithDayAttr: 0,
            workNo: 0,
        }],
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
