import { Vue } from '@app/provider';
import { component, Prop, Watch } from '@app/core/component';
import { KafS00ShrComponent, AppType } from 'views/kaf/s00/shr';
import {
    KafS00AComponent,
    KafS00CComponent,
    ScreenMode
} from 'views/kaf/s00';
import * as _ from 'lodash';
import * as moment from 'moment';
import { Kdl001Component } from 'views/kdl/001';
import { KDL002Component } from 'views/kdl/002';
import { KdlS35Component } from 'views/kdl/s35';
import { KdlS36Component } from 'views/kdl/s36';

@component({
    name: 'kafs11a',
    route: '/kaf/s11/a',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: [],
    components: {
        'kafs00-a': KafS00AComponent,
        'kafs00-c': KafS00CComponent,
        'kdls01': Kdl001Component,
        'kdls02': KDL002Component,
        'kdls35': KdlS35Component,
        'kdls36': KdlS36Component
    }
})
export class KafS11AComponent extends KafS00ShrComponent {
    @Prop({ default: () => ({}) })
    public params: KAFS11Params;
    public prePostResource: Array<Object> = [];
    public complementLeaveAtrResource: Array<Object> = [];
    public prePostAtr: number = null;
    public complementLeaveAtr: number = ComplementLeaveAtr.COMPLEMENT_LEAVE;
    public complementDate: any = null;
    public leaveDate: any = null;
    public complementWorkInfo: WorkInfo = { 
        workTypeCD: null,
        workTimeCD: null,
        timeRange1: { start: null, end: null },
        timeRange2: { start: null, end: null }
    };
    public leaveWorkInfo: WorkInfo = { 
        workTypeCD: null,
        workTimeCD: null,
        timeRange1: { start: null, end: null },
        timeRange2: { start: null, end: null }
    };
    public opAppStandardReasonCD: string = '';
    public opAppReason: string = '';
    public displayInforWhenStarting: any = null;
    public workTimeLstFullData: Array<any> = [];
    
    public created() {
        const vm = this;
        vm.prePostResource = [{
            code: 0,
            text: 'KAFS00_10'
        }, {
            code: 1,
            text: 'KAFS00_11'
        }];
        vm.complementLeaveAtrResource = [{
            code: 0,
            text: 'KAFS11_5'
        }, {
            code: 1,
            text: 'KAFS11_6'
        }, {
            code: 2,
            text: 'KAFS11_7'
        }];
        vm.$mask('show');
        let user = null;
        vm.$auth.user.then((userData: any) => {
            user = userData;

            return vm.loadCommonSetting(AppType.COMPLEMENT_LEAVE_APPLICATION);
        }).then((data: any) => {
            vm.updateKaf000_A_Params(user);
            vm.updateKaf000_C_Params(true);
            let newMode = vm.mode == ScreenMode.NEW ? true : false,
                employeeID = user.employeeId,
                dateLst = [],
                displayInforWhenStarting = null,
                appDispInfoStartupCmd = vm.appDispInfoStartupOutput,
                command = { newMode, employeeID, dateLst, displayInforWhenStarting, appDispInfoStartupCmd };

            return vm.$http.post('at', API.start, command);
        }).then((data: any) => {
            vm.displayInforWhenStarting = data.data;
            vm.initData();
            let wkTimeCodes = [
                vm.complementWorkInfo.workTimeCD,
                vm.leaveWorkInfo.workTimeCD
            ];

            return vm.$http.post('at', API.getWorkTimeByCDLst, { wkTimeCodes });
        }).then((data: any) => {
            vm.workTimeLstFullData = data.data;
            vm.$mask('hide');
        });
    }

    private initData() {
        const vm = this;
        vm.prePostAtr = vm.displayInforWhenStarting.appDispInfoStartup.appDispInfoWithDateOutput.prePostAtr;
        vm.initDataComplement();
        vm.initDataLeave();
    }

    private initDataComplement() {
        const vm = this;
        if (vm.displayInforWhenStarting.applicationForWorkingDay.workType) {
            vm.complementWorkInfo.workTypeCD = vm.displayInforWhenStarting.applicationForWorkingDay.workType;
        } else {
            vm.complementWorkInfo.workTypeCD = (_.head(vm.displayInforWhenStarting.applicationForWorkingDay.workTypeList) as any).workTypeCode;
        }
        vm.complementWorkInfo.workTimeCD = vm.displayInforWhenStarting.applicationForWorkingDay.workTime;
        vm.complementWorkInfo.timeRange1 = {
            start: vm.displayInforWhenStarting.applicationForWorkingDay.startTime,
            end: vm.displayInforWhenStarting.applicationForWorkingDay.endTime
        };
        vm.complementWorkInfo.timeRange2 = {
            start: vm.displayInforWhenStarting.applicationForWorkingDay.startTime2,
            end: vm.displayInforWhenStarting.applicationForWorkingDay.endTime2
        };
    }

    private initDataLeave() {
        const vm = this;
        if (vm.displayInforWhenStarting.applicationForHoliday.workType) {
            vm.leaveWorkInfo.workTypeCD = vm.displayInforWhenStarting.applicationForHoliday.workType;
        } else {
            vm.leaveWorkInfo.workTypeCD = (_.head(vm.displayInforWhenStarting.applicationForHoliday.workTypeList) as any).workTypeCode;
        }
        vm.leaveWorkInfo.workTimeCD = vm.displayInforWhenStarting.applicationForHoliday.workTime;
        vm.leaveWorkInfo.timeRange1 = {
            start: vm.displayInforWhenStarting.applicationForHoliday.startTime,
            end: vm.displayInforWhenStarting.applicationForHoliday.endTime
        };
        vm.leaveWorkInfo.timeRange2 = {
            start: vm.displayInforWhenStarting.applicationForHoliday.startTime2,
            end: vm.displayInforWhenStarting.applicationForHoliday.endTime2
        };
    }

    public getWorkTypeName(workTypeCD: string, isComplement: boolean) {
        const vm = this;
        if (!vm.displayInforWhenStarting) {
            return '';
        }
        let workTypeLst = [];
        if (isComplement) {
            workTypeLst = vm.displayInforWhenStarting.applicationForWorkingDay.workTypeList;
        } else {
            workTypeLst = vm.displayInforWhenStarting.applicationForHoliday.workTypeList;
        }
        let workType: any = _.find(workTypeLst, (o) => o.workTypeCode == workTypeCD);
        if (workType) {
            return workType.name;
        }

        return workTypeCD + ' ' + vm.$i18n('KAFS11_32');
    }

    public getWorkTimeName(workTimeCD: string) {
        const vm = this;
        if (!vm.displayInforWhenStarting) {
            return '';
        }
        let workTime: any = _.find(vm.displayInforWhenStarting.appDispInfoStartup.appDispInfoWithDateOutput.opWorkTimeLst, 
            (o) => o.worktimeCode == workTimeCD);
        if (workTime) {
            return workTime.workTimeDisplayName.workTimeName;
        }

        return '';
    }

    public getWorkTimeLabel(workTimeCD: string) {
        const vm = this;
        let workTimeFull = _.find(vm.workTimeLstFullData, (o) => o.code == workTimeCD);
        if (!workTimeFull) {
            return '';        
        }
        let result = '<div class="mb-2">' + workTimeFull.workTime1 + '</div>';
        if (workTimeFull.workTime2) {
            result += '<div>' + workTimeFull.workTime2 + '</div>';
        }

        return result;
    }

    get mode() {
        const vm = this;
        if (!vm.params.mode) {
            return ScreenMode.NEW;
        }
        
        return vm.params.mode;
    }

    // ※2
    get dispcomplementLeaveAtr() {
        const vm = this;
        if (!vm.displayInforWhenStarting) {
            return false;
        }

        return vm.displayInforWhenStarting.substituteHdWorkAppSet.simultaneousApplyRequired;
    }

    // ※3-1
    get dispComplementContent() {
        const vm = this;
        if (vm.mode == ScreenMode.DETAIL) {
            return true;
        }

        return vm.complementLeaveAtr == ComplementLeaveAtr.COMPLEMENT_LEAVE || vm.complementLeaveAtr == ComplementLeaveAtr.COMPLEMENT;
    }

    // ※5
    get cdtComplementTimeRange2() {
        const vm = this;
        if (!vm.displayInforWhenStarting) {
            return false;
        }
        let workTimeFull = _.find(vm.workTimeLstFullData, (o) => o.code == vm.complementWorkInfo.workTimeCD);
        if (!workTimeFull) {
            return false;        
        }
        if (!workTimeFull.workTime2) {
            return false;
        }

        return vm.displayInforWhenStarting.appDispInfoStartup.appDispInfoNoDateOutput.managementMultipleWorkCycles;
    }

    // ※5-1
    get dispComplementTimeRange2() {
        const vm = this;

        return vm.dispComplementContent && vm.cdtComplementTimeRange2;
    }

    // ※6
    public cdtSubMngComplementDailyType() {
        const vm = this;
        if (!vm.displayInforWhenStarting) {
            return false;
        }
        let workType: any = _.find(vm.displayInforWhenStarting.applicationForWorkingDay.workTypeList, 
            (o) => o.workTypeCode == vm.complementWorkInfo.workTypeCD);
        if (!workType) {
            return false;
        }
        if (workType.workAtr == 0) {
            return false;
        }
        if ((workType.morningCls == 6 && vm.displayInforWhenStarting.substituteManagement == 1) ||
            (workType.afternoonCls == 6 && vm.displayInforWhenStarting.substituteManagement == 1)) {
            return true;
        }

        return false;
    }

    // ※6-1
    get dispLeaveLinkContent1() {
        const vm = this;

        return vm.dispComplementContent && vm.cdtSubMngComplementDailyType();
    }

    // ※3-2
    get dispLeaveContent() {
        const vm = this;
        if (vm.mode == ScreenMode.DETAIL) {
            return true;
        }

        return vm.complementLeaveAtr == ComplementLeaveAtr.COMPLEMENT_LEAVE || vm.complementLeaveAtr == ComplementLeaveAtr.LEAVE;
    }

    // ※8
    private cdtLeaveDailyType(conditionType: number) {
        const vm = this;
        if (!vm.displayInforWhenStarting) {
            return false;
        }
        let workType: any = _.find(vm.displayInforWhenStarting.applicationForWorkingDay.workTypeList, 
            (o) => o.workTypeCode == vm.complementWorkInfo.workTypeCD);
        if (!workType) {
            return false;
        }
        if (vm.displayInforWhenStarting.workInfoAttendanceReflect.reflectWorkHour == 1) {
            if (workType.workAtr == 0) {
                return false;
            }

            return vm.cdtHalfDay(workType.morningCls, workType.afternoonCls, true);
        }
        if (vm.displayInforWhenStarting.workInfoAttendanceReflect.reflectWorkHour == 2) {
            if (workType.workAtr == 0) {
                return false;
            }
            if (conditionType == 1) {
                return vm.cdtHalfDay(workType.morningCls, workType.afternoonCls, true) || 
                    vm.cdtHalfDay(workType.morningCls, workType.afternoonCls, false);
            }
            
            return vm.cdtHalfDay(workType.morningCls, workType.afternoonCls, true);
        }

        return false;
    }

    private cdtHalfDay(morningCls: number, afternoonCls: number, isHalfDayWork: boolean) {
        if (isHalfDayWork) {
            if ((morningCls == 8 && afternoonCls == 0) || (morningCls == 0 && afternoonCls == 8)) {
                return true;
            }

            return false;
        } else {
            if ((morningCls == 8 && _.includes([1, 2, 3, 4, 5, 6, 9], afternoonCls)) ||
                (afternoonCls == 8 && _.includes([1, 2, 3, 4, 5, 6, 9], morningCls))) {
                return true;
            }

            return false;
        }
    }

    // ※8-3
    get dispLeaveWorkTime() {
        const vm = this;

        return vm.dispLeaveContent && vm.cdtLeaveDailyType(1);
    }

    // ※8-5
    get dispLeaveTimeRange1() {
        const vm = this;

        return vm.dispLeaveContent && vm.cdtLeaveDailyType(2);
    }

    // ※9
    get cdtLeaveTimeRange2() {
        const vm = this;
        let usage = true;
        if (!vm.displayInforWhenStarting) {
            return false;
        }
        let workTimeFull = _.find(vm.workTimeLstFullData, (o) => o.code == vm.leaveWorkInfo.workTimeCD);
        if (!workTimeFull) {
            return false;        
        }
        if (!workTimeFull.workTime2) {
            return false;
        }

        return vm.displayInforWhenStarting.appDispInfoStartup.appDispInfoNoDateOutput.managementMultipleWorkCycles;
    }

    // ※9-1
    get dispLeaveTimeRange2() {
        const vm = this;

        return vm.cdtLeaveTimeRange2 && vm.cdtLeaveDailyType(2) && vm.dispLeaveContent;
    }

    // ※10
    get enableLeaveTimeRange() {
        const vm = this;

        return vm.displayInforWhenStarting.workInfoAttendanceReflect.reflectAttendance == 1;
    }

    // ※11
    private cdtSubMngLeaveDailyType() {
        const vm = this;
        if (!vm.displayInforWhenStarting) {
            return false;
        }
        let workType: any = _.find(vm.displayInforWhenStarting.applicationForHoliday.workTypeList, 
            (o) => o.workTypeCode == vm.leaveWorkInfo.workTypeCD);
        if (!workType) {
            return false;
        }
        if (workType.workAtr == 0) {
            return false;
        }
        if ((workType.morningCls == 6 && vm.displayInforWhenStarting.substituteManagement == 1) ||
            (workType.afternoonCls == 6 && vm.displayInforWhenStarting.substituteManagement == 1)) {
            return true;
        }

        return false;
    }

    // ※11-1
    get dispLeaveLinkContent2() {
        const vm = this;

        return vm.dispLeaveContent && vm.cdtSubMngLeaveDailyType();
    }

    // ※12
    public cdtHdMngLeaveDailyType() {
        const vm = this;
        if (!vm.displayInforWhenStarting) {
            return false;
        }
        let workType: any = _.find(vm.displayInforWhenStarting.applicationForHoliday.workTypeList, 
            (o) => o.workTypeCode == vm.leaveWorkInfo.workTypeCD);
        if (!workType) {
            return false;
        }
        if (workType.workAtr == 0) {
            return false;
        }
        if ((workType.morningCls == 6 && vm.displayInforWhenStarting.holidayManage == 1) ||
            (workType.afternoonCls == 6 && vm.displayInforWhenStarting.holidayManage == 1)) {
            return true;
        }

        return false;
    }

    // ※12-1
    get dispComplementLinkContent() {
        const vm = this;

        return vm.dispLeaveContent && vm.cdtHdMngLeaveDailyType();
    }

    // ※A13
    get dispPrePostAtr() {
        const vm = this;
        if (!vm.appDispInfoStartupOutput) {
            return false;
        }

        return vm.appDispInfoStartupOutput.appDispInfoNoDateOutput.applicationSetting.appDisplaySetting.prePostDisplayAtr == 1;
    }

    // ※A14
    get enablePrePostAtr() {
        const vm = this;
        if (!vm.appDispInfoStartupOutput) {
            return false;
        }
        let appTypeSetting: any = _.find(vm.appDispInfoStartupOutput.appDispInfoNoDateOutput.applicationSetting.appTypeSetting,
            (o) => o.appType == AppType.COMPLEMENT_LEAVE_APPLICATION);
        if (appTypeSetting) {
            return appTypeSetting.canClassificationChange;
        }

        return false;
    }

    public kaf000CChangeReasonCD(opAppStandardReasonCD) {
        const vm = this;
        vm.opAppStandardReasonCD = opAppStandardReasonCD;
    }

    public kaf000CChangeAppReason(opAppReason) {
        const vm = this;
        vm.opAppReason = opAppReason;
    }

    @Watch('complementDate')
    public complementDateWatcher(value) {
        const vm = this;
        let command = {
            workingDate: moment(value).format('YYYY/MM/DD'),
            holidayDate: vm.leaveDate ? moment(vm.leaveDate).format('YYYY/MM/DD') : null,
            displayInforWhenStarting: vm.displayInforWhenStarting
        };
        vm.$mask('show');
        vm.$http.post('at', API.changeRecDate, command).then((data: any) => {
            vm.displayInforWhenStarting = data.data;
            vm.initDataComplement();
            vm.$mask('hide');
        });
    }

    @Watch('leaveDate')
    public leaveDateWatcher(value) {
        const vm = this;
        let command = {
            workingDate: vm.complementDate ? moment(vm.complementDate).format('YYYY/MM/DD') : null,
            holidayDate: moment(value).format('YYYY/MM/DD'),
            displayInforWhenStarting: vm.displayInforWhenStarting
        };
        vm.$mask('show');
        vm.$http.post('at', API.changeAbsDate, command).then((data: any) => {
            vm.displayInforWhenStarting = data.data;
            vm.initDataLeave();
            vm.$mask('hide');
        });
    }

    public openKDLS02(isComplement: boolean) {
        const vm = this;
        let param = {},
            workTimeCDLst = _.map(vm.displayInforWhenStarting.appDispInfoStartup.appDispInfoWithDateOutput.opWorkTimeLst, (o) => o.worktimeCode);
        if (isComplement) {
            param = {
                isAddNone: false,
                seledtedWkTimeCDs: workTimeCDLst,
                selectedWorkTimeCD: vm.complementWorkInfo.workTimeCD,
                selectedWorkTypeCD: vm.complementWorkInfo.workTypeCD,
                isSelectWorkTime: false,
                seledtedWkTypeCDs: _.map(vm.displayInforWhenStarting.applicationForWorkingDay.workTypeList, (o) => o.workTypeCode)
            };
        } else {
            param = {
                isAddNone: false,
                seledtedWkTimeCDs: workTimeCDLst,
                selectedWorkTimeCD: vm.leaveWorkInfo.workTimeCD,
                selectedWorkTypeCD: vm.leaveWorkInfo.workTypeCD,
                isSelectWorkTime: false,
                seledtedWkTypeCDs: _.map(vm.displayInforWhenStarting.applicationForHoliday.workTypeList, (o) => o.workTypeCode)
            };
        }
        vm.$modal('kdls02', param).then((result: any) => {
            if (isComplement) {
                vm.complementWorkInfo.workTypeCD = result.selectedWorkType.workTypeCode;            
            } else {
                vm.leaveWorkInfo.workTypeCD = result.selectedWorkType.workTypeCode; 
            }
        });
    }

    public openKDLS01(isComplement: boolean) {
        const vm = this;
        let param = {},
            workTimeCDLst = _.map(vm.displayInforWhenStarting.appDispInfoStartup.appDispInfoWithDateOutput.opWorkTimeLst, (o) => o.worktimeCode);
        if (isComplement) {
            param = {
                isAddNone: false,
                seledtedWkTimeCDs: workTimeCDLst,
                selectedWorkTimeCD: vm.complementWorkInfo.workTimeCD,
                selectedWorkType: ''
            };
        } else {
            param = {
                isAddNone: false,
                seledtedWkTimeCDs: workTimeCDLst,
                selectedWorkTimeCD: vm.leaveWorkInfo.workTimeCD,
                selectedWorkType: ''
            };
        }
        vm.$modal('kdls01', param).then((result: any) => {
            if (isComplement) {
                vm.complementWorkInfo.workTimeCD = result.selectedWorkTime.code;            
            } else {
                vm.leaveWorkInfo.workTimeCD = result.selectedWorkTime.code; 
            }
            let wkTimeCodes = [
                vm.complementWorkInfo.workTimeCD,
                vm.leaveWorkInfo.workTimeCD
            ];
            vm.$mask('show');

            return vm.$http.post('at', API.getWorkTimeByCDLst, { wkTimeCodes }).then((data: any) => {
                vm.workTimeLstFullData = data.data;
                vm.$mask('hide');
            });
        });
    }

    public openKDLS36Complement() {
        const vm = this;
        let param: any = {
                targetSelectionAtr: 1,
                actualContentDisplayList: vm.appDispInfoStartupOutput.appDispInfoWithDateOutput.opActualContentDisplayLst,
                managementData: [],
            },
            workType: any = _.find(vm.displayInforWhenStarting.applicationForWorkingDay.workTypeList, 
            (o) => o.workTypeCode == vm.complementWorkInfo.workTypeCD);
        if (workType.workAtr == 0) {
            param.daysUnit = 1;
        } else {
            param.daysUnit = 0.5;
        }
        vm.$modal('kdls36', param).then((result: any) => {
            console.log(result);
        });
    }

    public openKDLS36Leave() {
        const vm = this;
        let param: any = {
                targetSelectionAtr: 1,
                actualContentDisplayList: vm.appDispInfoStartupOutput.appDispInfoWithDateOutput.opActualContentDisplayLst,
                managementData: [],
            },
            workType: any = _.find(vm.displayInforWhenStarting.applicationForHoliday.workTypeList, 
            (o) => o.workTypeCode == vm.leaveWorkInfo.workTypeCD);
        if (workType.workAtr == 0) {
            param.daysUnit = 1;
        } else {
            param.daysUnit = 0.5;
        }
        vm.$modal('kdls36', param).then((result: any) => {
            console.log(result);
        });
    }

    public openKDLS35Leave() {
        const vm = this;
        let param: any = {
                targetSelectionAtr: 1,
                actualContentDisplayList: vm.appDispInfoStartupOutput.appDispInfoWithDateOutput.opActualContentDisplayLst,
                managementData: [],
            },
            workType: any = _.find(vm.displayInforWhenStarting.applicationForHoliday.workTypeList, 
            (o) => o.workTypeCode == vm.leaveWorkInfo.workTypeCD);
        if (workType.workAtr == 0) {
            param.daysUnit = 1;
        } else {
            param.daysUnit = 0.5;
        }
        vm.$modal('kdls35', param).then((result: any) => {
            console.log(result);
        });
    }
}

const API = {
    start: 'at/request/application/holidayshipment/mobile/start',
    changeRecDate: 'at/request/application/holidayshipment/changeRecDate',
    changeAbsDate: 'at/request/application/holidayshipment/changeAbsDate',
    getWorkTimeByCDLst: 'at/shared/worktimesetting/get_worktime_by_codes'
};

export interface KAFS11Params {
    mode: ScreenMode;
}

interface WorkInfo {
    workTypeCD: string;
    workTimeCD: string;
    timeRange1: any;
    timeRange2: any;
}

enum ComplementLeaveAtr {
    // 振休＋振出
    COMPLEMENT_LEAVE = 0,
    // 振出
    COMPLEMENT = 1,
    // 振休
    LEAVE = 2
}
