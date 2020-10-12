import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';
import {
    IAppDispInfoStartupOutput,
    IArrivedLateLeaveEarly,
    IEarlyInfos,
    ITime
} from '../../../../../kaf/s04/a/define';

@component({
    name: 'cmms45componentsapp9',
    template: require('./index.vue'),
    validations: {},
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
                    vm.appDispInfoStartupOutput.appDispInfoWithDateOutput.opActualContentDisplayLst.forEach((item,index) => {
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