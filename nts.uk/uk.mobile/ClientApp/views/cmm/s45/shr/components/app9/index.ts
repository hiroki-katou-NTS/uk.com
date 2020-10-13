import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';
import {
    IAppDispInfoStartupOutput,
    IArrivedLateLeaveEarly,
    IEarlyInfos,
    ITime
} from '../../../../../kaf/s04/a/define';
import * as _ from 'lodash';

@component({
    name: 'cmms45componentsapp9',
    template: require('./index.vue'),
    validations: {},
    constraints: []
})
export class CmmS45ComponentsApp9Component extends Vue {

    public appDispInfoStartupOutput!: IAppDispInfoStartupOutput;
    public time: ITime = {
        attendanceTime: 0,
        leaveTime: 0,
        attendanceTime2: 0,
        leaveTime2: 0,
    };
    public condition1: boolean = true;
    public condition2: boolean = true;

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

        let paramsStartB = {
            appId: vm.params.appDispInfoStartupOutput.appDetailScreenInfo.application.appID,
            infoStartup: vm.params.appDispInfoStartupOutput,
        };

        vm.$auth.user.then((user: any) => {
            vm.$mask('show');
            vm.$http.post('at', API.startDetailBScreen, paramsStartB).then((res: any) => {
                vm.$mask('hide');
                vm.params.appDetail = res.data;
                //事後モード && ※3			「補足資料」Sheetの「２．取り消す初期情報」に記載している。
                if (vm.params.appDetail.appDispInfoStartupOutput.appDetailScreenInfo.application.prePostAtr == 1) {
                    // check B1_3 show
                    if (!(_.isEmpty(vm.params.appDetail.arrivedLateLeaveEarly.lateCancelation[0]))) {
                        if (vm.params.appDetail.arrivedLateLeaveEarly.lateCancelation[0].lateOrEarlyClassification == 0) {
                            vm.condition1 = false;
                        }
                        if (vm.params.appDetail.arrivedLateLeaveEarly.lateCancelation[0].lateOrEarlyClassification == 1) {
                            vm.condition2 = false;
                        }
                    }
                    // check B1_6 show
                    if (!(_.isEmpty(vm.params.appDetail.arrivedLateLeaveEarly.lateCancelation[1]))) {
                        vm.condition2 = false;
                    } else {
                        vm.condition2 = true;
                    }
                }

                // 「遅刻早退取消申請起動時の表示情報.遅刻早退取消申請」に、時刻報告（勤怠No＝２）がEmpty　AND　取消（勤怠No＝２）がEmpty 勤務NO  [ ※4	&& ※1 ]
                if (vm.params.appDispInfoStartupOutput.appDispInfoNoDateOutput.managementMultipleWorkCycles == true && !(_.isEmpty(vm.params.appDetail.arrivedLateLeaveEarly.lateOrLeaveEarlies[2])) || !(_.isEmpty(vm.params.appDetail.arrivedLateLeaveEarly.lateCancelation[2]))) {
                    vm.showData = true;
                } else {
                    vm.showData = false;
                }

                if (vm.params.appDetail.arrivedLateLeaveEarly.lateOrLeaveEarlies.length != 0) {

                    vm.params.appDetail.arrivedLateLeaveEarly.lateOrLeaveEarlies.forEach((item, index) => {
                        if (index == 0) {
                            vm.time.attendanceTime = item.timeWithDayAttr;
                        }
                        if (index == 1) {
                            vm.time.leaveTime = item.timeWithDayAttr;
                        }
                        if (index == 2) {
                            vm.time.attendanceTime2 = item.timeWithDayAttr;
                        }
                        if (index == 3) {
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

            });
        });
    }


    public mounted() {
        const vm = this;

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