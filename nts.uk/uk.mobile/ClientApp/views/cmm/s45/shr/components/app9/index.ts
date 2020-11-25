import { _, Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';
import {
    IAppDispInfoStartupOutput,
    IArrivedLateLeaveEarly,
    IEarlyInfos,
    ITime
} from '../../../../../kaf/s04/a/define';
import { KafS00SubP1Component, KAFS00P1Params } from '../../../../../kaf/s00/sub/p1';

@component({
    name: 'cmms45componentsapp9',
    template: require('./index.vue'),
    validations: {},
    components: {
        'kaf-s00-p1': KafS00SubP1Component
    },
    constraints: []
})
export class CmmS45ComponentsApp9Component extends Vue {

    public appDispInfoStartupOutput!: IAppDispInfoStartupOutput;
    public time: ITime = {
        attendanceTime: null,
        leaveTime: null,
        attendanceTime2: null,
        leaveTime2: null,
    };
    public kafS00P1Params1: KAFS00P1Params = {
        actualDisp: false,
        preAppDisp: false,
        scheduleDisp: true,
        actualExcess: null,
        actualTime: null,
        preAppExcess: null,
        preAppTime: null,
        scheduleExcess: null,
        scheduleTime: null
    };
    public kafS00P1Params2: KAFS00P1Params = {
        preAppDisp: false,
        scheduleDisp: true,
        actualExcess: null,
        actualTime: null,
        preAppExcess: null,
        preAppTime: null,
        scheduleExcess: null,
        scheduleTime: null,
        actualDisp: false
    };
    public kafS00P1Params3: KAFS00P1Params = {
        preAppDisp: false,
        scheduleDisp: true,
        actualExcess: null,
        actualTime: null,
        preAppExcess: null,
        preAppTime: null,
        scheduleExcess: null,
        scheduleTime: null,
        actualDisp: false
    };
    public kafS00P1Params4: KAFS00P1Params = {
        preAppDisp: false,
        scheduleDisp: true,
        actualExcess: null,
        actualTime: null,
        preAppExcess: null,
        preAppTime: null,
        scheduleExcess: null,
        scheduleTime: null,
        actualDisp: false
    };
    public condition1: boolean = true;
    public condition2: boolean = true;
    public condition3: boolean = true;
    public showData: boolean = false;
    @Prop({
        default: () => ({
            appDispInfoStartupOutput: null,
            appDetail: null,
        })

    }) public readonly params!: {
        appDispInfoStartupOutput: IAppDispInfoStartupOutput,
        appDetail: IAppDetail,

    };



    public created() {
        const vm = this;

        vm.kafS00P1Params1 = {
            actualDisp: false,
            preAppDisp: false,
            scheduleDisp: true,
            actualExcess: null,
            actualTime: null,
            preAppExcess: null,
            preAppTime: null,
            scheduleExcess: null,
            scheduleTime: null
        };
        vm.kafS00P1Params2 = {
            actualDisp: false,
            preAppDisp: false,
            scheduleDisp: true,
            actualExcess: null,
            actualTime: null,
            preAppExcess: null,
            preAppTime: null,
            scheduleExcess: null,
            scheduleTime: null
        };
        vm.kafS00P1Params3 = {
            actualDisp: false,
            preAppDisp: false,
            scheduleDisp: true,
            actualExcess: null,
            actualTime: null,
            preAppExcess: null,
            preAppTime: null,
            scheduleExcess: null,
            scheduleTime: null
        };
        vm.kafS00P1Params4 = {
            actualDisp: false,
            preAppDisp: false,
            scheduleDisp: true,
            actualExcess: null,
            actualTime: null,
            preAppExcess: null,
            preAppTime: null,
            scheduleExcess: null,
            scheduleTime: null
        };
        let paramsStartB = {
            appId: vm.params.appDispInfoStartupOutput.appDetailScreenInfo.application.appID,
            infoStartup: vm.params.appDispInfoStartupOutput,
        };

        vm.$auth.user.then((user: any) => {
            vm.$mask('show');
            vm.$http.post('at', API.startDetailBScreen, paramsStartB).then((res: any) => {
                vm.$mask('hide');
                vm.params.appDetail = res.data;
                vm.$emit('loading-complete');

                vm.params.appDetail.arrivedLateLeaveEarly.lateOrLeaveEarlies.forEach((item) => {
                    if (item.workNo == 1 && item.lateOrEarlyClassification == 0) {
                        vm.time.attendanceTime = item.timeWithDayAttr;
                    }
                    if (item.workNo == 1 && item.lateOrEarlyClassification == 1) {
                        vm.time.leaveTime = item.timeWithDayAttr;
                    }
                    if (item.workNo == 2 && item.lateOrEarlyClassification == 0) {
                        vm.time.attendanceTime2 = item.timeWithDayAttr;
                    }
                    if (item.workNo == 2 && item.lateOrEarlyClassification == 1) {
                        vm.time.leaveTime2 = item.timeWithDayAttr;
                    }
                });

                // const condition5 = !(_.isEmpty(vm.params.appDetail.arrivedLateLeaveEarly.lateOrLeaveEarlies[2]));
                //const condition6 = !(_.isEmpty(vm.params.appDetail.arrivedLateLeaveEarly.lateCancelation[2]));
                const condition7 = vm.params.appDispInfoStartupOutput.appDispInfoNoDateOutput.managementMultipleWorkCycles;

                // 「遅刻早退取消申請起動時の表示情報.遅刻早退取消申請」に、時刻報告（勤怠No＝２）がEmpty　AND　取消（勤怠No＝２）がEmpty 勤務NO  [ ※4	&& ※1 ]
                if (condition7 == true) {
                    vm.showData = true;
                } else {
                    vm.showData = false;
                }

                //update component S00 P1
                vm.params.appDispInfoStartupOutput.appDispInfoWithDateOutput.opActualContentDisplayLst.forEach((i) => {
                    vm.kafS00P1Params1.scheduleTime = i.opAchievementDetail.achievementEarly.scheAttendanceTime1;
                    vm.kafS00P1Params2.scheduleTime = i.opAchievementDetail.achievementEarly.scheDepartureTime1;
                    vm.kafS00P1Params3.scheduleTime = i.opAchievementDetail.achievementEarly.scheAttendanceTime2;
                    vm.kafS00P1Params4.scheduleTime = i.opAchievementDetail.achievementEarly.scheDepartureTime2;
                });

                if (vm.params.appDetail.arrivedLateLeaveEarly.lateOrLeaveEarlies.length != 0) {

                    vm.params.appDetail.arrivedLateLeaveEarly.lateOrLeaveEarlies.forEach((item) => {
                        if (item.workNo == 1 && item.lateOrEarlyClassification == 0) {
                            vm.time.attendanceTime = item.timeWithDayAttr;
                        }
                        if (item.workNo == 1 && item.lateOrEarlyClassification == 1) {
                            vm.time.leaveTime = item.timeWithDayAttr;
                        }
                        if (item.workNo == 2 && item.lateOrEarlyClassification == 0) {
                            vm.time.attendanceTime2 = item.timeWithDayAttr;
                        }
                        if (item.workNo == 2 && item.lateOrEarlyClassification == 1) {
                            vm.time.leaveTime2 = item.timeWithDayAttr;
                        }
                    });
                } else {
                    vm.appDispInfoStartupOutput.appDispInfoWithDateOutput.opActualContentDisplayLst.forEach((item, index) => {
                        if (index == 0) {
                            vm.time.attendanceTime = item.opAchievementDetail.opWorkTime;
                        }
                        if (index == 1) {
                            vm.time.leaveTime = item.opAchievementDetail.opLeaveTime;
                        }
                        if (index == 2) {
                            vm.time.attendanceTime2 = item.opAchievementDetail.opWorkTime2;
                        }
                        if (index == 3) {
                            vm.time.leaveTime2 = item.opAchievementDetail.opDepartureTime2;
                        }
                    });
                }
            }).catch(() => {
                vm.$emit('loading-complete');
            });
        });
        vm.$watch('params.appDispInfoStartupOutput', (newV, oldV) => {
            vm.$emit('loading-complete');
        });
    }



    public mounted() {
        const vm = this;

    }

    get cond1() {
        const vm = this;
        let temp: boolean | null = null;

        if (vm.params.appDetail) {
            vm.params.appDetail.arrivedLateLeaveEarly.lateOrLeaveEarlies.forEach((i) => {
                if (i.workNo == 1 && i.lateOrEarlyClassification == 0) {
                    temp = true;
                }
            });
            vm.params.appDetail.arrivedLateLeaveEarly.lateCancelation.forEach((i) => {
                if (i.workNo == 1 && i.lateOrEarlyClassification == 0) {
                    temp = false;
                }
            });
        }

        return temp;
    }

    get cond2() {
        const vm = this;
        let temp: boolean | null = null;

        if (vm.params.appDetail) {
            vm.params.appDetail.arrivedLateLeaveEarly.lateOrLeaveEarlies.forEach((i) => {
                if (i.workNo == 1 && i.lateOrEarlyClassification == 1) {
                    temp = true;
                }
            });
            vm.params.appDetail.arrivedLateLeaveEarly.lateCancelation.forEach((i) => {
                if (i.workNo == 1 && i.lateOrEarlyClassification == 1) {
                    temp = false;
                }
            });
        }

        return temp;
    }


    get cond3() {
        const vm = this;
        let temp: boolean | null = null;
        if (vm.params.appDetail) {
            vm.params.appDetail.arrivedLateLeaveEarly.lateOrLeaveEarlies.forEach((i) => {
                if (i.workNo == 2 && i.lateOrEarlyClassification == 0) {
                    temp = true;
                }
            });
            vm.params.appDetail.arrivedLateLeaveEarly.lateCancelation.forEach((i) => {
                if (i.workNo == 2 && i.lateOrEarlyClassification == 0) {
                    temp = false;
                }
            });
        }

        return temp;
    }

    get cond4() {
        const vm = this;
        let temp: boolean | null = null;

        if (vm.params.appDetail) {
            vm.params.appDetail.arrivedLateLeaveEarly.lateOrLeaveEarlies.forEach((i) => {
                if (i.workNo == 2 && i.lateOrEarlyClassification == 1) {
                    temp = true;
                }
            });
            vm.params.appDetail.arrivedLateLeaveEarly.lateCancelation.forEach((i) => {
                if (i.workNo == 2 && i.lateOrEarlyClassification == 1) {
                    temp = false;
                } 
            });
        }

        return temp;
    }

}

const API = {
    startDetailBScreen: 'at/request/application/lateorleaveearly/initPageB',
};

interface IAppDetail {
    appDispInfoStartupOutput: IAppDispInfoStartupOutput;
    arrivedLateLeaveEarly: IArrivedLateLeaveEarly;
    earlyInfos: IEarlyInfos;
    info: string;
    lateEarlyCancelAppSet: ILateEarlyCancelAppSet;

}

interface ILateEarlyCancelAppSet {
    cancelAtr: 0 | 1 | 2;
    companyId: string;
}