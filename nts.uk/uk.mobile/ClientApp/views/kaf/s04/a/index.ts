import { component, Prop, Watch } from '@app/core/component';
import * as _ from 'lodash';
import { KafS00AComponent, KafS00BComponent, KafS00CComponent } from '../../s00';
import { AppType, KafS00ShrComponent } from '../../s00/shr';
import { KafS04BComponent } from '../b';
import { KafS00SubP1Component, KAFS00P1Params } from '../../s00/sub/p1';


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
    IParamS00C,
    IParamS00B,
    IResAppDate,
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
        'kaf-s00-p1': KafS00SubP1Component,
    },
    constraints: []
})
export class KafS04AComponent extends KafS00ShrComponent {
    public title: string = 'KafS04A';
    public kafS00AParams: IParamS00A = null;
    public kafS00BParams: IParamS00B;
    public kafS00CParams: IParamS00C;
    public kafS00P1Params1: KAFS00P1Params = {
        preAppDisp: false,
        preAppTime: null,
        preAppExcess: null,
        actualDisp: false,
        actualTime: null,
        actualExcess: null,
        scheduleDisp: true,
        scheduleTime: null,
        scheduleExcess: null
    };
    public kafS00P1Params2: KAFS00P1Params = {
        preAppDisp: false,
        preAppTime: null,
        preAppExcess: null,
        actualDisp: false,
        actualTime: null,
        actualExcess: null,
        scheduleDisp: true,
        scheduleTime: null,
        scheduleExcess: null
    };
    public kafS00P1Params3: KAFS00P1Params = {
        preAppDisp: false,
        preAppTime: null,
        preAppExcess: null,
        actualDisp: false,
        actualTime: null,
        actualExcess: null,
        scheduleDisp: true,
        scheduleTime: null,
        scheduleExcess: null
    };
    public kafS00P1Params4: KAFS00P1Params = {
        preAppDisp: false,
        preAppTime: null,
        preAppExcess: null,
        actualDisp: false,
        actualTime: null,
        actualExcess: null,
        scheduleDisp: true,
        scheduleTime: null,
        scheduleExcess: null
    };
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
            if (vm.time.attendanceTime) {
                vm.check.cbCancelLate.isDisable = false;
            }
            if (vm.time.leaveTime) {
                vm.check.cbCancelEarlyLeave.isDisable = false;
            }
            if (vm.time.attendanceTime2) {
                vm.check.cbCancelLate2.isDisable = false;
            }
            if (vm.time.leaveTime2) {
                vm.check.cbCancelEarlyLeave2.isDisable = false;
            }
            vm.infoOutPut.earlyInfos.forEach((item, index) => {
                if (index == 0) {
                    item.isCheck ? vm.check.cbCancelLate.value : null;
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
                    vm.$mask('hide');
                    vm.data = res.data;
                    vm.initComponentA();
                    vm.initComponetB();
                    vm.initComponentC();
                    vm.initComponentP1();

                    if (!vm.mode) {
                        let schedTime = vm.res.appDispInfoStartupOutput.appDispInfoWithDateOutput.opActualContentDisplayLst;
                        schedTime.forEach((item) => {
                            if (item.opAchievementDetail != null) {
                                vm.kafS00P1Params1.scheduleTime = item.opAchievementDetail.achievementEarly.scheAttendanceTime1;
                                vm.kafS00P1Params2.scheduleTime = item.opAchievementDetail.achievementEarly.scheDepartureTime1;
                                vm.kafS00P1Params3.scheduleTime = item.opAchievementDetail.achievementEarly.scheAttendanceTime2;
                                vm.kafS00P1Params4.scheduleTime = item.opAchievementDetail.achievementEarly.scheDepartureTime2;
                            }
                        });
                    }

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
                vm.check.cbCancelLate.value = '';
                vm.check.cbCancelEarlyLeave.value = '';
                vm.check.cbCancelLate2.value = '';
                vm.check.cbCancelEarlyLeave2.value = '';

                return false;
            }
        }
    }

    public initComponentP1() {
        const vm = this;

        vm.kafS00P1Params1 = {
            preAppDisp: false,
            preAppTime: null,
            preAppExcess: null,
            actualDisp: false,
            actualTime: null,
            actualExcess: null,
            scheduleDisp: true,
            scheduleTime: null,
            scheduleExcess: null
        };
        vm.kafS00P1Params2 = {
            preAppDisp: false,
            preAppTime: null,
            preAppExcess: null,
            actualDisp: false,
            actualTime: null,
            actualExcess: null,
            scheduleDisp: true,
            scheduleTime: null,
            scheduleExcess: null
        };
        vm.kafS00P1Params3 = {
            preAppDisp: false,
            preAppTime: null,
            preAppExcess: null,
            actualDisp: false,
            actualTime: null,
            actualExcess: null,
            scheduleDisp: true,
            scheduleTime: null,
            scheduleExcess: null
        };
        vm.kafS00P1Params4 = {
            preAppDisp: false,
            preAppTime: null,
            preAppExcess: null,
            actualDisp: false,
            actualTime: null,
            actualExcess: null,
            scheduleDisp: true,
            scheduleTime: null,
            scheduleExcess: null
        };
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

        const { data } = vm;
        const { appDispInfoStartupOutput } = data;

        const { appDispInfoNoDateOutput } = appDispInfoStartupOutput;
        const { applicationSetting } = appDispInfoNoDateOutput;

        const { appDisplaySetting, appTypeSetting } = applicationSetting;

        vm.kafS00BParams = {
            input: {
                mode: vm.mode ? 0 : 1,
                appDisplaySetting,
                newModeContent: {
                    // 申請表示情報．申請表示情報(基準日関係なし)．申請設定．申請表示設定																	
                    appTypeSetting,
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
            const { res } = vm;
            const { appDispInfoStartupOutput } = res;

            const { appDetailScreenInfo, appDispInfoNoDateOutput } = appDispInfoStartupOutput;
            const { employeeInfoLst } = appDispInfoNoDateOutput;
            const { application } = appDetailScreenInfo;

            const { prePostAtr, opAppStartDate, opAppEndDate } = application;

            vm.kafS00BParams.input.detailModeContent = {
                prePostAtr,
                startDate: opAppStartDate,
                endDate: opAppEndDate,
                employeeName: _.isEmpty(employeeInfoLst) ? 'empty' : vm.res.appDispInfoStartupOutput.appDispInfoNoDateOutput.employeeInfoLst[0].bussinessName
            };
        }
    }

    public initComponentC() {
        const vm = this;

        const { appDispInfoStartupOutput } = vm;
        const { appDispInfoNoDateOutput } = appDispInfoStartupOutput;

        const { displayStandardReason, displayAppReason, reasonTypeItemLst, applicationSetting } = appDispInfoNoDateOutput;
        const { appLimitSetting } = applicationSetting;

        vm.kafS00CParams = {
            input: {
                // 定型理由の表示
                // 申請表示情報．申請表示情報(基準日関係なし)．定型理由の表示区分
                displayFixedReason: displayStandardReason,
                // 申請理由の表示
                // 申請表示情報．申請表示情報(基準日関係なし)．申請理由の表示区分
                displayAppReason,
                // 定型理由一覧
                // 申請表示情報．申請表示情報(基準日関係なし)．定型理由項目一覧
                reasonTypeItemLst,
                // 申請制限設定
                // 申請表示情報．申請表示情報(基準日関係なし)．申請設定．申請制限設定
                appLimitSetting,
                // 選択中の定型理由
                // empty
                opAppStandardReasonCD: vm.mode ? null : vm.res.appDispInfoStartupOutput.appDetailScreenInfo.application.opAppStandardReasonCD,
                opAppReason: vm.mode ? null : vm.res.appDispInfoStartupOutput.appDetailScreenInfo.application.opAppReason,
            },
            output: {
                // 定型理由
                opAppStandardReasonCD: vm.mode ? '' : vm.res.appDispInfoStartupOutput.appDetailScreenInfo.application.opAppStandardReasonCD,
                // 申請理由
                opAppReason: vm.mode ? '' : vm.res.appDispInfoStartupOutput.appDetailScreenInfo.application.opAppReason
            }
        };
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
            if (vm.application.prePostAtr == 1) {
                if (vm.check.cbCancelLate.value) {
                    vm.infoOutPut.arrivedLateLeaveEarly.lateCancelation.push(
                        {
                            lateOrEarlyClassification: 0,
                            workNo: 1
                        }
                    );
                }
            }
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

            if (vm.application.prePostAtr == 1) {
                if (vm.check.cbCancelEarlyLeave.value) {
                    vm.infoOutPut.arrivedLateLeaveEarly.lateCancelation.push(
                        {
                            lateOrEarlyClassification: 1,
                            workNo: 1
                        }
                    );
                }
            }
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

            if (vm.application.prePostAtr == 1) {
                if (vm.check.cbCancelLate2.value) {
                    vm.infoOutPut.arrivedLateLeaveEarly.lateCancelation.push(
                        {
                            lateOrEarlyClassification: 0,
                            workNo: 2
                        }
                    );
                }
            }
        }

        vm.infoOutPut.earlyInfos.push(
            {
                category: 0,
                isActive: vm.check.cbCancelLate2.isDisable ? false : true,
                isCheck: vm.check.cbCancelLate2.value ? true : false,
                workNo: 2,
                isIndicated: true
            });

        if (vm.time.leaveTime2 != null) {
            vm.infoOutPut.arrivedLateLeaveEarly.lateOrLeaveEarlies.push(
                {
                    lateOrEarlyClassification: 1,
                    timeWithDayAttr: vm.time.leaveTime2,
                    workNo: 2,
                }
            );
            if (vm.application.prePostAtr == 1) {
                if (vm.check.cbCancelEarlyLeave2.value) {
                    vm.infoOutPut.arrivedLateLeaveEarly.lateCancelation.push(
                        {
                            lateOrEarlyClassification: 1,
                            workNo: 2
                        }
                    );
                }
            }
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
        vm.$mask('show');
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
            vm.paramsAComponent = {
                appID: res.data.appID,
                mode: vm.mode,
                res: null,
            };
            vm.$emit('nextComponentA0', vm.paramsAComponent);
            vm.$mask('hide');
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
            vm.$http.post('at', API.changeAppDate, params).then((response: IResAppDate) => {

                response.data.appDispInfoWithDateOutput.opActualContentDisplayLst.forEach((item) => {
                    if (item.opAchievementDetail) {
                        vm.kafS00P1Params1.scheduleTime = item.opAchievementDetail.achievementEarly.scheAttendanceTime1;
                        vm.kafS00P1Params2.scheduleTime = item.opAchievementDetail.achievementEarly.scheDepartureTime1;
                        vm.kafS00P1Params3.scheduleTime = item.opAchievementDetail.achievementEarly.scheAttendanceTime2;
                        vm.kafS00P1Params4.scheduleTime = item.opAchievementDetail.achievementEarly.scheDepartureTime2;
                    } else {
                        vm.kafS00P1Params1.scheduleTime = null;
                        vm.kafS00P1Params2.scheduleTime = null;
                        vm.kafS00P1Params3.scheduleTime = null;
                        vm.kafS00P1Params4.scheduleTime = null;
                    }
                });

                response.data.appDispInfoWithDateOutput.opActualContentDisplayLst.forEach((i) => {
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
            const { res } = vm;
            const { appDispInfoStartupOutput } = res;

            const { appDetailScreenInfo } = appDispInfoStartupOutput;
            const { application } = appDetailScreenInfo;

            const { reflectionStatus, inputDate, appID, appDate, prePostAtr, opAppStartDate, opAppEndDate } = application;

            vm.application.appDate = appDate;
            vm.application.prePostAtr = prePostAtr;
            vm.application.opAppStartDate = opAppStartDate;
            vm.application.opAppEndDate = opAppEndDate;
            vm.application.appID = appID;
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
    appID: null,
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

