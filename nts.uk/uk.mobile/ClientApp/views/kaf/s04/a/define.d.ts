
export interface IParamS00A {
    companyID: string;
    employeeID: string;
    employmentCD: string;
    applicationUseSetting: IApplicationUseSetting;
    receptionRestrictionSetting: IReceptionRestrictionSetting;
}

export interface IApplicationUseSetting {
    useDivision: number | null;
    appType: number | null;
    memo: string;
}

export interface IReceptionRestrictionSetting {
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


export interface IParamS00B {
    input: IInput;
    output: IOutput;
}

export interface IOutput {
    prePostAtr: 1 | 0;
    startDate: string;
    endDate: string;
}

export interface IInput {
    mode: 0 | 1;
    appDisplaySetting: {
        prePostDisplayAtr: number | null,
        manualSendMailAtr: number | null
    };
    newModeContent: {
        appTypeSetting: IAppTypeSetting[],
        useMultiDaySwitch: boolean,
        initSelectMultiDay: boolean
    };
    detailModeContent: {
        prePostAtr: number | null;
        startDate: string;
        endDate: string;
        employeeName: string;
    }
}

export interface IAppTypeSetting {
    appType: number | null;
    sendMailWhenRegister: boolean;
    sendMailWhenApproval: boolean;
    displayInitialSegment: number | null;
    canClassificationChange: boolean;
}

export interface IParamS00C {
    input: IInputCPrams;
    output: IOutPutCParams;
}

export interface IInputCPrams {
    displayFixedReason: number | null;
    displayAppReason: number | null;
    reasonTypeItemLst: any[];
    appLimitSetting: {
        canAppAchievementMonthConfirm: boolean;
        canAppAchievementLock: boolean;
        canAppFinishWork: boolean;
        requiredAppReason: boolean;
        standardReasonRequired: boolean;
        canAppAchievementConfirm: boolean;
    }
    opAppReason: string;
    opAppStandardReasonCD: number | string | null;
}

export interface IOutPutCParams {
    opAppReason: string;
    opAppStandardReasonCD: number | string | null;
}

export interface IAppLimitSetting {
    canAppAchievementMonthConfirm: boolean;
    canAppAchievementLock: boolean;
    canAppFinishWork: boolean;
    requiredAppReason: boolean;
    standardReasonRequired: boolean;
    canAppAchievementConfirm: boolean;
}

export interface IEmployeeInfoLst {
    sid: string;
    scd: string;
    bussinessName: string;
}

export interface IAppLimitSetting {
    canAppAchievementMonthConfirm: boolean;
    canAppAchievementLock: boolean;
    canAppFinishWork: boolean;
    requiredAppReason: boolean;
    standardReasonRequired: boolean;
    canAppAchievementConfirm: boolean;
}

export interface IAppTypeSetting {
    appType: number | null;
    sendMailWhenRegister: boolean;
    sendMailWhenApproval: boolean;
    displayInitialSegment: number | null;
    canClassificationChange: boolean;
}

export interface IAppDeadlineSetLst {
    useAtr: number | null;
    closureId: number | null;
    deadline: number | null;
    deadlineCriteria: number | null;
}

export interface IReceptionRestrictionSetting {
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

export interface IAppUseSetLst {
    useDivision: number | null;
    appType: number | null;
    memo: string;
}

export interface IEmpHistImport {
    employeeId: string;
    employmentCode: string;
    employmentName: string;
    startDate: string;
    endDate: string;
}

export interface ITargetWorkTypeByAppLst {
    appType: number | null;
    displayWorkType: boolean;
    workTypeLst: any[];
    opBreakOrRestTime: null;
    opHolidayTypeUse: boolean;
    opHolidayAppType: number | null;
    opBusinessTripAppWorkType: null;
}

export interface IListApprover {
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

export interface IListApprovalFrame {
    frameOrder: number | null;
    listApprover: IListApprover[];
    confirmAtr: number | null;
    appDate: string;
}

export interface IOpListApprovalPhaseState {
    phaseOrder: number | null;
    approvalAtrValue: number | null;
    approvalAtrName: string;
    approvalFormValue: number | null;
    listApprovalFrame: IListApprovalFrame[];
}

export interface IOpWorkTimeLst {
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

export interface IAppDispInfoStartupOutput {
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
    appDispInfoWithDateOutput: IAppDispInfoWithDateOutput;
    appDetailScreenInfo: {
        application: {
            version: number | null;
            appID: string;
            prePostAtr: 0 | 1;
            employeeID: string;
            appType: number | null;
            appDate: string;
            enteredPerson: string;
            inputDate: string;
            reflectionStatus: any;
            opStampRequestMode: number | null;
            opReversionReason: string;
            opAppStartDate: string;
            opAppEndDate: string;
            opAppReason: string;
            opAppStandardReasonCD: 1;
        };
        approvalLst: any[];
        authorComment: string;
        user: number | null;
        reflectPlanState: number | null;
        outputMode: number | null;
        authorizableFlags: boolean;
        approvalATR: number | null;
        alternateExpiration: boolean;
    };
}

export interface IOpActualContentDisplayLst {
    date: string;
    opAchievementDetail: {
        achievementEarly: {
            scheAttendanceTime1: number | null;
            scheAttendanceTime2: number | null;
            scheDepartureTime1: number | null;
            scheDepartureTime2: number | null;
        }
        opWorkTime: number | null;
        opWorkTime2: number | null;
        opLeaveTime: number | null;
        opDepartureTime2: number | null;
    }
}

export interface IData {
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

export interface ITime {
    attendanceTime: number | null;
    leaveTime: number | null;
    attendanceTime2: number | null;
    leaveTime2: number | null;
}

export interface IApplication {
    appDate: string,
    appID: string;
    appType: number | null;
    employeeID: string;
    enteredPerson: string;
    inputDate: string;
    opAppEndDate: string;
    opAppReason: string;
    opAppStandardReasonCD: number | string | null;
    opAppStartDate: string;
    opReversionReason: null;
    opStampRequestMode: null;
    prePostAtr: number | null;
    reflectionStatus: null;
    version: null;
}
export interface IInfoOutput {
    appDispInfoStartupOutput: IAppDispInfoStartupOutput | null;
    arrivedLateLeaveEarly: IArrivedLateLeaveEarly;
    earlyInfos: IEarlyInfos[];
    info: string;
    lateEarlyCancelAppSet: {
        cancelAtr: number | null;
        companyId: string;
    },
}

export interface IArrivedLateLeaveEarly {
    lateCancelation: ILateCancelation[];
    lateOrLeaveEarlies: ILateOrLeaveEarlies[];
}

export interface ILateOrLeaveEarlies {
    lateOrEarlyClassification: number | null;
    timeWithDayAttr: number | null;
    workNo: number | null;
}

export interface ILateCancelation {
    lateOrEarlyClassification: number | null;
    workNo: number | null;
}

export interface IEarlyInfos {
    category: number | null;
    isActive: boolean;
    isCheck: boolean;
    isIndicated: boolean;
    workNo: number | null;
}

export interface IRes {
    data: {
        autoSuccessMail: [];
        autoFailMail: [];
        autoFailServer: [];
        appID: "";
        reflectAppId: "";
        processDone: boolean;
        autoSendMail: boolean;
    }
}

export interface IParams {
    appID: string;
    mode: boolean;
    res: {
        appDispInfoStartupOutput: IAppDispInfoStartupOutput;
        arrivedLateLeaveEarly: IArrivedLateLeaveEarly;
        earlyInfos: IEarlyInfos;
        info: string;
        lateEarlyCancelAppSet: {
            cancelAtr: number | null;
            companyId: string;
        }
    }
}

export interface ICheck {
    cbCancelLate: {
        value: string;
        isDisable: boolean;
    }
    cbCancelEarlyLeave: {
        value: string;
        isDisable: boolean;
    }
    cbCancelLate2: {
        value: string;
        isDisable: boolean;
    }
    cbCancelEarlyLeave2: {
        value: string;
        isDisable: boolean;
    }
}

export interface IResDetail {
    appDispInfoStartupOutput: IAppDispInfoStartupOutput;
    arrivedLateLeaveEarly: {
        lateCancelation: ILateCancelation[];
        lateOrLeaveEarlies: ILateOrLeaveEarlies[];
    }
    earlyInfos: IEarlyInfos[];
    info: string;
    lateEarlyCancelAppSet: {
        cancelAtr: number | null;
        companyId: string;
    }
}

export interface IAppDispInfoWithDateOutput {
    approvalFunctionSet: {
        appUseSetLst: IAppUseSetLst[]
    },
    prePostAtr: 0 | 1;
    baseDate: string;
    empHistImport: IEmpHistImport;
    appDeadlineUseCategory: number | null;
    opEmploymentSet: {
        companyID: string;
        employmentCD: string;
        targetWorkTypeByAppLst: ITargetWorkTypeByAppLst[];
    },
    opListApprovalPhaseState: IOpListApprovalPhaseState[];
    opErrorFlag: number | null;
    opActualContentDisplayLst: IOpActualContentDisplayLst[];
    opPreAppContentDispDtoLst: null;
    opAppDeadline: string;
    opWorkTimeLst: IOpWorkTimeLst[];
}

export interface IResAppDate {
    data: {
        appDispInfoWithDateOutput: IAppDispInfoWithDateOutput;
        errorInfo: string;
        lateOrEarlyInfoLst: ILateOrLeaveEarlies;
    }
}