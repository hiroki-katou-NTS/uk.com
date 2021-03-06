import { Vue, _ } from '@app/provider';

export class KafS00ShrComponent extends Vue {

    public appDispInfoStartupOutput: any = null;

    public kaf000_A_Params: any = null;

    public kaf000_B_Params: any = null;

    public kaf000_C_Params: any = null;

    public loadCommonSetting(
        appType: number,
        employeeID?: string,
        holidayAppType?: number,
        dateLst?: Array<string>, 
        overtimeAppAtr?: number) {
        const self = this;
        let command = { employeeID, appType, holidayAppType, dateLst, overtimeAppAtr };

        return self.$http.post('at', API.getStartNewMob, command)
        .then((successData: any) => {
            self.appDispInfoStartupOutput = successData.data;
            let useDivision = self.appDispInfoStartupOutput.appDispInfoWithDateOutput.approvalFunctionSet.appUseSetLst[0].useDivision,
                recordDate = self.appDispInfoStartupOutput.appDispInfoNoDateOutput.applicationSetting.recordDate,
                opErrorFlag = self.appDispInfoStartupOutput.appDispInfoWithDateOutput.opErrorFlag,
                msgID = '';
            if (useDivision == 0) {
                self.$modal.error('Msg_323').then(() => {
                    if (recordDate == 0) {
                        self.$goto('ccg008a');   
                    }
                });
                if (recordDate == 0) {
                    return false;
                }

                return true;
            }
            
            if (_.isNull(opErrorFlag)) {
                return true;    
            }
            switch (opErrorFlag) {
                case 1:
                    msgID = 'Msg_324';
                    break;
                case 2: 
                    msgID = 'Msg_238';
                    break;
                case 3:
                    msgID = 'Msg_237';
                    break;
                default: 
                    break;
            }  
            if (_.isEmpty(msgID)) { 
                return true;
            }
            self.$modal.error({ messageId: msgID }).then(() => {
                if (recordDate == 0) {
                    self.$goto('ccg008a');    
                }    
            });

            return true;
        }).catch((res: any) => {
            if (res.messageId == 'Msg_426') {
                self.$modal.error('Msg_426').then(() => {
                    self.$goto('ccg008a');
                });    
            } else {
                self.$modal.error(res.message).then(() => {
                    self.$goto('ccg008a');
                }); 
            }

            return false;
        });
    }

    public handleErrorCommon(failData: any) {
        const vm = this;
        if (failData.messageId == 'Msg_324') {
            vm.$modal.error({ messageId: failData.messageId, messageParams: failData.parameterIds });

            return;
        }	

        if (failData.errors) {
            if (failData.errors.length > 0) {
                let error = failData.errors[0];
                vm.$modal.error({ messageId: error.messageId, messageParams: Array.from(error.parameterIds, (item: string) => item) }).then(() => {
                    failData.errors = _.drop(failData.errors);

                    return vm.handleErrorCommon(failData);
                });
            }
        } else {
            vm.$modal.error({ messageId: failData.messageId, messageParams: failData.parameterIds });
        }
    }

    public createApplicationInsert(appTypeParam): Application {
        return {
            prePostAtr: null,
            employeeIDLst: [],
            appType: appTypeParam,
            appDate: null,
            opAppReason: null,
            opAppStandardReasonCD: null,
            opAppStartDate: null,
            opAppEndDate: null,
            opStampRequestMode: null
        };
    }

    public createApplicationUpdate(appDetailScreenInfo): Application {
        return {
            prePostAtr: null,
            employeeIDLst: [],
            appType: appDetailScreenInfo.appType,
            appDate: null,
            opAppReason: null,
            opAppStandardReasonCD: null,
            opAppStartDate: null,
            opAppEndDate: null,
            opStampRequestMode: null
        };
    }

    public updateKaf000_A_Params(user: any) {
        const vm = this;
        let appDispInfoWithDateOutput = vm.appDispInfoStartupOutput.appDispInfoWithDateOutput,
            appDispInfoNoDateOutput = vm.appDispInfoStartupOutput.appDispInfoNoDateOutput,
            appDetailScreenInfo = vm.appDispInfoStartupOutput.appDetailScreenInfo;
        vm.kaf000_A_Params = {
            companyID: user.companyId,
            employeeID: user.employeeId,
            employmentCD: appDispInfoWithDateOutput.empHistImport.employmentCode,
            applicationUseSetting: appDispInfoWithDateOutput.approvalFunctionSet.appUseSetLst[0],
            receptionRestrictionSetting: appDispInfoNoDateOutput.applicationSetting.receptionRestrictionSetting[0],
            // opOvertimeAppAtr: null
        };
    }

    public updateKaf000_B_Params(modeNew: boolean, appDate?: string) {
        const vm = this;
        let appDispInfoWithDateOutput = vm.appDispInfoStartupOutput.appDispInfoWithDateOutput,
            appDispInfoNoDateOutput = vm.appDispInfoStartupOutput.appDispInfoNoDateOutput,
            appDetailScreenInfo = vm.appDispInfoStartupOutput.appDetailScreenInfo;
        vm.kaf000_B_Params = {
            mode: modeNew ? 0 : 1,
            appDisplaySetting: appDispInfoNoDateOutput.applicationSetting.appDisplaySetting,
            newModeContent: null,
            detailModeContent: null,
            appDispInfoStartupOutput: vm.appDispInfoStartupOutput
        };
        if (modeNew) {
            vm.kaf000_B_Params.newModeContent = {													
                appTypeSetting: appDispInfoNoDateOutput.applicationSetting.appTypeSetting,
                useMultiDaySwitch: true,
                initSelectMultiDay: false
            };
            if (appDate) {
                _.set(vm.kaf000_B_Params.newModeContent, 'appDate', appDate);
            }
        }
        if (!modeNew) {
            vm.kaf000_B_Params.detailModeContent = {
                prePostAtr: appDetailScreenInfo.application.prePostAtr,
                startDate: appDetailScreenInfo.application.opAppStartDate,
                endDate: appDetailScreenInfo.application.opAppEndDate,
                employeeName: _.isEmpty(appDispInfoNoDateOutput.employeeInfoLst) 
                                ? 'empty' 
                                : appDispInfoNoDateOutput.employeeInfoLst[0].bussinessName
            };
        }
    }

    public updateKaf000_C_Params(modeNew: boolean) {
        const vm = this;
        let appDispInfoWithDateOutput = vm.appDispInfoStartupOutput.appDispInfoWithDateOutput,
            appDispInfoNoDateOutput = vm.appDispInfoStartupOutput.appDispInfoNoDateOutput,
            appDetailScreenInfo = vm.appDispInfoStartupOutput.appDetailScreenInfo;
        vm.kaf000_C_Params = {
            displayFixedReason: appDispInfoNoDateOutput.displayStandardReason,
            displayAppReason: appDispInfoNoDateOutput.displayAppReason,
            reasonTypeItemLst: appDispInfoNoDateOutput.reasonTypeItemLst,
            appLimitSetting: appDispInfoNoDateOutput.applicationSetting.appLimitSetting,
            opAppStandardReasonCD: modeNew ? null : appDetailScreenInfo.application.opAppStandardReasonCD,
            opAppReason: modeNew ? null : appDetailScreenInfo.application.opAppReason
        };
    }

}

export enum AppType {
    OVER_TIME_APPLICATION = 0, // ????????????
    ABSENCE_APPLICATION = 1,  // ????????????
    WORK_CHANGE_APPLICATION = 2, // ??????????????????
    BUSINESS_TRIP_APPLICATION = 3, // ????????????
    GO_RETURN_DIRECTLY_APPLICATION = 4, // ??????????????????
    LEAVE_TIME_APPLICATION = 6, // ??????????????????
    STAMP_APPLICATION = 7, // ????????????
    ANNUAL_HOLIDAY_APPLICATION = 8, // ??????????????????
    EARLY_LEAVE_CANCEL_APPLICATION = 9, // ????????????????????????
    COMPLEMENT_LEAVE_APPLICATION = 10, // ??????????????????
    OPTIONAL_ITEM_APPLICATION = 15, // ??????????????????
}

export enum AppTypeName {
    OVER_TIME_APPLICATION = '????????????',
    ABSENCE_APPLICATION = '????????????',
    WORK_CHANGE_APPLICATION = '??????????????????',
    BUSINESS_TRIP_APPLICATION = '????????????',
    GO_RETURN_DIRECTLY_APPLICATION = '??????????????????',
    LEAVE_TIME_APPLICATION = '??????????????????',
    STAMP_APPLICATION = '????????????',
    ANNUAL_HOLIDAY_APPLICATION = '??????????????????',
    EARLY_LEAVE_CANCEL_APPLICATION = '????????????????????????',
    COMPLEMENT_LEAVE_APPLICATION = '??????????????????',
    OPTIONAL_ITEM_APPLICATION = '??????????????????'
}

export interface Application {
    prePostAtr: number;
    employeeIDLst: Array<string>;
    appType: number;
    appDate: string;
    opAppReason: string;
    opAppStandardReasonCD: string;
    opAppStartDate: string;
    opAppEndDate: string;
    opStampRequestMode?: number;   
}

export interface InitParam {
    appDispInfoStartupOutput: any;
    appDetail: any;
    isDetailMode: boolean;
}

const API = {
    getStartNewMob: 'at/request/app/smartphone/getStartNewMob'
};