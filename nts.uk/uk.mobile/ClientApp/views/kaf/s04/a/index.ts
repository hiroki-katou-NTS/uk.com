import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { KafS00AComponent, KafS00BComponent, KafS00CComponent } from '../../s00';
import { AppType, KafS00ShrComponent } from '../../s00/shr';
import { vmOf } from 'vue/types/umd';
import { KafS04BComponent } from '../b';

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
    //public validAll: boolean = true;
    public isValidateAll: Boolean = true;


    public created() {
        const vm = this;

        vm.fetchStart();
        //khoi tao time
    }

    public fetchStart() {
        const vm = this;

        vm.$mask('show');
        vm.$auth.user.then((usr: any) => {
            vm.user = usr;
        }).then(() => {
            return vm.loadCommonSetting(AppType.EARLY_LEAVE_CANCEL_APPLICATION);
        }).then((response: any) => {
            if (response) {
                //thuc hien goi api start KAFS04
                vm.$mask('hide');
                let params = {
                    appDates: [],
                    appDispInfoStartupDto: vm.appDispInfoStartupOutput,
                };
                vm.$http.post('at', API.startKAFS04, params).then((res: any) => {
                    vm.data = res.data;
                    vm.initComponentA();
                    vm.initComponetB();
                    vm.initComponentC();
                });
            }
        });
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
                useMultiDaySwitch: true,
                initSelectMultiDay: false
            },
            appDisplaySetting: {
                prePostDisplayAtr: null,
                manualSendMailAtr: null,
            },
            detailModeContent: null
        };
        let output = {
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
                    alert('qua man tiep theo');
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


    public mounted() {
        const vm = this;

    }
}

const API = {
    startKAFS04: 'at/request/application/lateorleaveearly/initPage',
    changeAppDate: 'at/request/application/lateorleaveearly/changeAppDate',
    register: 'at/request/application/lateorleaveearly/register'
};


interface IParamS00A {
    companyID: string;
    employeeID: string;
    employmentCD: string;
    applicationUseSetting: IApplicationUseSetting;
    receptionRestrictionSetting: IReceptionRestrictionSetting;
}

interface IApplicationUseSetting {
    useDivision: number | null;
    appType: number | null;
    memo: string;
}

interface IReceptionRestrictionSetting {
    otAppBeforeAccepRestric: null;
    afterhandRestriction: {
        allowFutureDay: boolean
    };
    beforehandRestriction: {
        dateBeforehandRestrictions: number | null,
        toUse: boolean
    };
    appType: number | null;
}


interface IParamS00B {
    input: IInput;
    output: IOutput;
}

interface IOutput {
    prePostAtr: number | null;
    startDate: null;
    endDate: null;
}

interface IInput {
    mode: number | null;
    appDisplaySetting: {
        prePostDisplayAtr: number | null,
        manualSendMailAtr: number | null
    };
    newModeContent: {
        appTypeSetting: IAppTypeSetting[],
        useMultiDaySwitch: boolean,
        initSelectMultiDay: boolean
    };
    detailModeContent: null;
}

interface IAppTypeSetting {
    appType: number | null;
    sendMailWhenRegister: boolean;
    sendMailWhenApproval: boolean;
    displayInitialSegment: number | null;
    canClassificationChange: boolean;
}

interface IParamS00C {
    input: {
        displayFixedReason: number | null,
        displayAppReason: number | null,
        reasonTypeItemLst: any[],
        appLimitSetting: IAppLimitSetting
    };
    output: {
        opAppStandardReasonCD: string,
        opAppReason: string
    };
}

interface IAppLimitSetting {
    canAppAchievementMonthConfirm: boolean;
    canAppAchievementLock: boolean;
    canAppFinishWork: boolean;
    requiredAppReason: boolean;
    standardReasonRequired: boolean;
    canAppAchievementConfirm: boolean;
}

interface IEmployeeInfoLst {
    sid: string;
    scd: string;
    bussinessName: string;
}

interface IAppLimitSetting {
    canAppAchievementMonthConfirm: boolean;
    canAppAchievementLock: boolean;
    canAppFinishWork: boolean;
    requiredAppReason: boolean;
    standardReasonRequired: boolean;
    canAppAchievementConfirm: boolean;
}

interface IAppTypeSetting {
    appType: number | null;
    sendMailWhenRegister: boolean;
    sendMailWhenApproval: boolean;
    displayInitialSegment: number | null;
    canClassificationChange: boolean;
}

interface IAppDeadlineSetLst {
    useAtr: number | null;
    closureId: number | null;
    deadline: number | null;
    deadlineCriteria: number | null;
}

interface IReceptionRestrictionSetting {
    otAppBeforeAccepRestric: null;
    afterhandRestriction: {
        allowFutureDay: boolean
    };
    beforehandRestriction: {
        dateBeforehandRestrictions: number | null,
        toUse: boolean
    };
    appType: number | null;
}

interface IAppUseSetLst {
    useDivision: number | null;
    appType: number | null;
    memo: string;
}

interface IEmpHistImport {
    employeeId: string;
    employmentCode: string;
    employmentName: string;
    startDate: string;
    endDate: string;
}

interface ITargetWorkTypeByAppLst {
    appType: number | null;
    displayWorkType: boolean;
    workTypeLst: any[];
    opBreakOrRestTime: null;
    opHolidayTypeUse: boolean;
    opHolidayAppType: number | null;
    opBusinessTripAppWorkType: null;
}

interface IListApprover {
    approverID: string;
    approvalAtrValue: number | null;
    approvalAtrName: string;
    agentID: string;
    approverName: string;
    representerID: string;
    representerName: string;
    approvalDate: null;
    approvalReason: string;
    approverMail: string;
    representerMail: string;
    approverInListOrder: number | null;
}

interface IListApprovalFrame {
    frameOrder: number | null;
    listApprover: IListApprover[];
    confirmAtr: number | null;
    appDate: string;
}

interface IOpListApprovalPhaseState {
    phaseOrder: number | null;
    approvalAtrValue: number | null;
    approvalAtrName: string;
    approvalFormValue: number | null;
    listApprovalFrame: IListApprovalFrame[];
}

interface IOpWorkTimeLst {
    companyId: string;
    worktimeCode: string;
    workTimeDivision: {
        workTimeDailyAtr: number | null,
        workTimeMethodSet: number | null
    };
    isAbolish: boolean;
    colorCode: string;
    workTimeDisplayName: {
        workTimeName: string,
        workTimeAbName: string,
        workTimeSymbol: string
    };
    memo: string;
    note: string;
}

interface IAppDispInfoStartupOutput {
    appDispInfoNoDateOutput: {
        mailServerSet: boolean,
        advanceAppAcceptanceLimit: number | null,
        employeeInfoLst: IEmployeeInfoLst[],
        applicationSetting: {
            companyID: string,
            appLimitSetting: IAppLimitSetting,
            appTypeSetting: IAppTypeSetting[],
            appSetForProxyApp: any[],
            appDeadlineSetLst: IAppDeadlineSetLst[],
            appDisplaySetting: {
                prePostDisplayAtr: number | null,
                manualSendMailAtr: number | null
            },
            receptionRestrictionSetting: IReceptionRestrictionSetting[],
            recordDate: number | null
        },
        appReasonStandardLst: any[],
        displayAppReason: number | null,
        displayStandardReason: number | number,
        reasonTypeItemLst: any[],
        managementMultipleWorkCycles: boolean,
        opAdvanceReceptionHours: null,
        opAdvanceReceptionDate: null,
        opEmployeeInfo: null
    };
    appDispInfoWithDateOutput: {
        approvalFunctionSet: {
            appUseSetLst: IAppUseSetLst[]
        },
        prePostAtr: number | null,
        baseDate: string,
        empHistImport: IEmpHistImport,
        appDeadlineUseCategory: number | null,
        opEmploymentSet: {
            companyID: string,
            employmentCD: '01',
            targetWorkTypeByAppLst: ITargetWorkTypeByAppLst[],
        },
        opListApprovalPhaseState: IOpListApprovalPhaseState[],
        opErrorFlag: number | null,
        opActualContentDisplayLst: null,
        opPreAppContentDispDtoLst: null,
        opAppDeadline: string,
        opWorkTimeLst: IOpWorkTimeLst[],
    };
    appDetailScreenInfo: null;
}

interface IData {
    appDispInfoStartupOutput: IAppDispInfoStartupOutput;
    arrivedLateLeaveEarly: null;
    earlyInfos: any[];
    info: null;
    lateEarlyCancelAppSet: {
        companyId: '',
        cancelAtr: number | null
    };
    cancelAtr: number | null;
    companyId: '';
}

interface ITime {
    attendanceTime: number | null;
    leaveTime: number | null;
    attendanceTime2: number | null;
    leaveTime2: number | null;
}