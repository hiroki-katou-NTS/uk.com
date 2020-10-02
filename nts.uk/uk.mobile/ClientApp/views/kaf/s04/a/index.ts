import { Vue } from '@app/provider';
import { component } from '@app/core/component';
import { KafS00AComponent, KafS00BComponent, KafS00CComponent } from '../../s00';
import { AppType, KafS00ShrComponent } from '../../s00/shr';

@component({
    name: 'kafs04a',
    route: '/kaf/s04/a',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    components: {
        'kaf-s00-a': KafS00AComponent,
        'kaf-s00-b': KafS00BComponent,
        'kaf-s00-c': KafS00CComponent,
        'kaf-s00-shr': KafS00ShrComponent,
    },
    constraints: []
})
export class KafS04AComponent extends KafS00ShrComponent {
    public title: string = 'KafS04A';
    public kafS00AParams !: IParamS00A;
    public kafS00BParams !: IParamS00B;
    public kafS00CParams !: IParamS00C;
    public user !: any;
    public data !: any;
    public appDispInfoStartupOutput: any;


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
            return vm.loadCommonSetting(AppType.EARLY_LEAVE_CANCEL_APPLICATION);
        }).then((response: any) => {
            if (response) {
                //thuc hien goi api start KAFS04
                vm.$mask('show');
                let params = {
                    appType: AppType.EARLY_LEAVE_CANCEL_APPLICATION,
                    appDates: [],
                    appDispInfoStartupDto: vm.appDispInfoStartupOutput,
                }
                vm.$http.post('at', API.startKAFS04, params).then((res: any) => {
                    vm.data = res;
                })
                vm.initComponentA();
                vm.initComponetB();
                vm.initComponentC();
            }
        });
    }

    public initComponentA() {
        const vm = this;
        if (vm.kafS00AParams == null) {
            vm.kafS00AParams = {
                companyID: vm.user.companyId,
                employeeID: vm.user.employeeId,
                employmentCD: vm.user.employeeCode,
                applicationUseSetting: vm.data.appDispInfoWithDateOutput.approvalFunctionSet.appUseSetLst[0],
                receptionRestrictionSetting: vm.data.appDispInfoNoDateOutput.applicationSetting.receptionRestrictionSetting[0],
            };
        }
    }


    public initComponetB() {

    }


    public initComponentC() {

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
    sid: string,
    scd: string,
    bussinessName: string
}

interface IAppLimitSetting {
    canAppAchievementMonthConfirm: boolean,
    canAppAchievementLock: boolean,
    canAppFinishWork: boolean,
    requiredAppReason: boolean,
    standardReasonRequired: boolean,
    canAppAchievementConfirm: boolean
}

interface IAppTypeSetting {
    appType: number | null,
    sendMailWhenRegister: boolean,
    sendMailWhenApproval: boolean,
    displayInitialSegment: number | null,
    canClassificationChange: boolean
}

interface IAppDeadlineSetLst {
    useAtr: number | null,
    closureId: number | null,
    deadline: number | null,
    deadlineCriteria: number | null
}

interface IReceptionRestrictionSetting {
    otAppBeforeAccepRestric: null,
    afterhandRestriction: {
        allowFutureDay: boolean
    },
    beforehandRestriction: {
        dateBeforehandRestrictions: number | null,
        toUse: boolean
    },
    appType: number | null
}

interface IAppUseSetLst {
    useDivision: number | null,
    appType: number | null,
    memo: string
}

interface IEmpHistImport {
    employeeId: string,
    employmentCode: string,
    employmentName: string,
    startDate: string,
    endDate: string
}

interface ITargetWorkTypeByAppLst {
    appType: number | null,
    displayWorkType: boolean,
    workTypeLst: any[],
    opBreakOrRestTime: null,
    opHolidayTypeUse: boolean,
    opHolidayAppType: number | null,
    opBusinessTripAppWorkType: null
}

interface IListApprover {
    approverID: string,
    approvalAtrValue: number | null,
    approvalAtrName: string,
    agentID: string,
    approverName: string,
    representerID: string,
    representerName: string,
    approvalDate: null,
    approvalReason: string,
    approverMail: string,
    representerMail: string,
    approverInListOrder: number | null
}

interface IListApprovalFrame {
    frameOrder: number | null,
    listApprover: IListApprover[],
    confirmAtr: number | null,
    appDate: string
}

interface IOpListApprovalPhaseState {
    phaseOrder: number | null,
    approvalAtrValue: number | null,
    approvalAtrName: string,
    approvalFormValue: number | null,
    listApprovalFrame: IListApprovalFrame[]
}

interface opWorkTimeLst {
    companyId: string,
    worktimeCode: string,
    workTimeDivision: {
        workTimeDailyAtr: number | null,
        workTimeMethodSet: number | null
    },
    isAbolish: boolean,
    colorCode: string,
    workTimeDisplayName: {
        workTimeName: string,
        workTimeAbName: string,
        workTimeSymbol: string
    },
    memo: string,
    note: string
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
    },
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
            employmentCD: "01",
            targetWorkTypeByAppLst: ITargetWorkTypeByAppLst[],
        },
        opListApprovalPhaseState: IOpListApprovalPhaseState[],
        opErrorFlag: number | null,
        opActualContentDisplayLst: null,
        opPreAppContentDispDtoLst: null,
        opAppDeadline: string,
        opWorkTimeLst: opWorkTimeLst[],
    },
    appDetailScreenInfo: null
}

