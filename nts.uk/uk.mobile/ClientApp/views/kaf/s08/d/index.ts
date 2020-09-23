import { Vue, moment } from '@app/provider';
import { component, Prop } from '@app/core/component';
import * as _ from 'lodash';
import { KDL002Component } from '../../../kdl/002';
import { Kdl001Component } from '../../../kdl/001';

interface Parameter {
    rowDate: any;
    businessTripInfoOutput: any;
}

const defaultParam = (): Parameter => ({
    rowDate: {},
    businessTripInfoOutput: {}
});

@component({
    name: 'kafs08d',
    style: require('./style.scss'),
    template: require('./index.vue')
})
export class KafS08DComponent extends Vue {
    @Prop({ default: defaultParam })
    public readonly params!: Parameter;

    public title: string = 'KafS08D';
    public seledtedWkTypeCD = null;
    public seledtedWkTimeCD = null;
    public listWorkDays = [];
    public listHolidays = [];
    public listWorkTimes = [];
    public workDayFlag = true;
    public isAddNone = true;


    public created() {
        const vm = this;

        let currentRow = vm.params.rowDate;

        vm.listWorkDays = vm.params.businessTripInfoOutput.workdays;
        vm.listHolidays = vm.params.businessTripInfoOutput.holidays;
        vm.listWorkTimes = vm.params.businessTripInfoOutput.appDispInfoStartup.appDispInfoWithDateOutput.opWorkTimeLst;

        vm.$http.post('at', API.callKDLS02, {
            businessTripInfoOutputDto: vm.params.businessTripInfoOutput,
            selectedDate: currentRow.date,
        }).then((res: any) => {
            let response = res.data;
            if (response) {
                vm.workDayFlag = true;
            } else {
                vm.workDayFlag = false;
            }
            vm.isAddNone = !response;
        });
    }

    public openA2Screen() {

    }


    public openKDLS02() {
        const vm = this;
        
        let currentRow = vm.params.rowDate;
        let listWorkTimeCodes = vm.params.businessTripInfoOutput.appDispInfoStartup.appDispInfoWithDateOutput.opWorkTimeLst;
        let listInputCode = [];
        
        if (vm.workDayFlag) {
            listInputCode = _.map(vm.listWorkDays, function (obj) {
                return obj.workTypeCode;
            });
        } else {
            listInputCode = _.map(vm.listHolidays, function (obj) {
                return obj.workTypeCode;
            });
        }

        vm.$modal(
            KDL002Component,
            {
                seledtedWkTypeCDs: listInputCode,
                selectedWorkTypeCD: currentRow.opAchievementDetail.workTypeCD,
                seledtedWkTimeCDs: _.map(listWorkTimeCodes, (item: any) => item.worktimeCode),
                selectedWorkTimeCD: currentRow.opAchievementDetail.workTimeCD,
                isSelectWorkTime: 1,
            }
        ).then((f: any) => {
            if (f) {
                currentRow.opAchievementDetail.workTypeCD = f.selectedWorkType.workTypeCode;
                currentRow.opAchievementDetail.opWorkTypeName = f.selectedWorkType.name;
                currentRow.opAchievementDetail.workTimeCD = f.selectedWorkTime.code;
                currentRow.opAchievementDetail.opWorkTimeName = f.selectedWorkTime.name;
                currentRow.opAchievementDetail.opWorkTime = f.selectedWorkTime.workTime1;
            }
        }).catch((res: any) => {
            if (res.messageId) {
                this.$modal.error({ messageId: res.messageId });
            } else {

                if (_.isArray(res.errors)) {
                    this.$modal.error({ messageId: res.errors[0].messageId });
                } else {
                    this.$modal.error({ messageId: res.errors.messageId });
                }
            }
        });
    }

    public openKDLS01() {
        const vm = this;

        let currentRow = vm.params.rowDate;

        vm.$modal(
            Kdl001Component,
            {
                isAddNone: vm.isAddNone ? 1 : 0,
                seledtedWkTimeCDs: _.map(vm.listWorkTimes, (item: any) => item.worktimeCode),
                selectedWorkTimeCD: currentRow.opAchievementDetail.workTimeCD,
                isSelectWorkTime: 1
            }
        ).then((f: any) => {
            if (f) {
                currentRow.opAchievementDetail.workTimeCD = f.selectedWorkTime.code;
                currentRow.opAchievementDetail.opWorkTimeName = f.selectedWorkTime.name;
                currentRow.opAchievementDetail.opWorkTime = f.selectedWorkTime.workTime1;
            }
        }).catch((res: any) => {
            if (res.messageId) {
                this.$modal.error({ messageId: res.messageId });
            } else {

                if (_.isArray(res.errors)) {
                    this.$modal.error({ messageId: res.errors[0].messageId });
                } else {
                    this.$modal.error({ messageId: res.errors.messageId });
                }
            }
        });
    }

    //Đóng dialog
    public close() {
        const vm = this;
        vm.$close();
    }

    public accepEvent() {
        const vm = this ;
        let model = {
            opWorkTime : vm.params.rowDate.opAchievementDetail.opWorkTime,
            opLeaveTime : vm.params.rowDate.opAchievementDetail.opLeaveTime
        };
        vm.$close(model);
    }
}

const API = {
    callKDLS02: 'at/request/application/businesstrip/mobile/startKDLS02'
};

export class Model {

    public workTypeCD: string;

    public workTimeCD: string;

    public workTypeName: string;

    public workTimeName: string;

    public startWorkTime: number;

    public endWorkTime: number;

}

