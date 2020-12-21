import { component, Prop } from '@app/core/component';
import { KafS00ShrComponent, AppType, Application, InitParam } from 'views/kaf/s00/shr';
import { KafS00AComponent, KafS00BComponent, KafS00CComponent } from 'views/kaf/s00';
import { KafS00DComponent } from 'views/kaf/s00/d';
import { DispInfoOfTimeLeaveRequest, GoBackTime } from '../shr/';
import { KafS00SubP1Component, ExcessTimeStatus } from 'views/kaf/s00/sub/p1/';

@component({
    name: 'kafs12a1',
    route: '/kaf/s12/a1',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    components: {
        'kafs00-a': KafS00AComponent,
        'kafs00-b': KafS00BComponent,
        'kafs00-c': KafS00CComponent,
        'kafs00-d': KafS00DComponent,
        'kafs00-p1': KafS00SubP1Component,
    },
    validations: {},
    constraints: []
})
export class KafS12A1Component extends KafS00ShrComponent {
    public title: string = 'KafS12A1';
    public application: Application = null;
    public mode: boolean = true;
    public user: any = null;

    //Disp Infomation Time Leave Request Value
    public DispInfoOfTimeLeaveRequest1 = new DispInfoOfTimeLeaveRequest({
        header: 'KAFS12_5', frame: 0, attendanceTimeLabel: 'KAFS12_6',
        attendanceTime: null, titleOfAttendanceTime: 'KAFS12_5',
        kafS00P1Params: { scheduleDisp: true, scheduleExcess: ExcessTimeStatus.NONE, scheduleTime: null, actualDisp: null, preAppDisp: null },
        numberOfHoursLeft: null
    });
    public DispInfoOfTimeLeaveRequest2 = new DispInfoOfTimeLeaveRequest({
        header: 'KAFS12_7', frame: 1, attendanceTimeLabel: 'KAFS12_8',
        attendanceTime: null, titleOfAttendanceTime: 'KAFS12_7',
        kafS00P1Params: { scheduleDisp: true, scheduleExcess: ExcessTimeStatus.NONE, scheduleTime: null, actualDisp: null, preAppDisp: null },
        numberOfHoursLeft: null
    });
    public DispInfoOfTimeLeaveRequest3 = new DispInfoOfTimeLeaveRequest({
        header: 'KAFS12_9', frame: 2, attendanceTimeLabel: 'KAFS12_10',
        attendanceTime: null, titleOfAttendanceTime: 'KAFS12_9',
        kafS00P1Params: { scheduleDisp: true, scheduleExcess: ExcessTimeStatus.NONE, scheduleTime: null, actualDisp: null, preAppDisp: null },
        numberOfHoursLeft: null
    });
    public DispInfoOfTimeLeaveRequest4 = new DispInfoOfTimeLeaveRequest({
        header: 'KAFS12_11', frame: 3, attendanceTimeLabel: 'KAFS12_12', attendanceTime: null, titleOfAttendanceTime: 'KAFS12_11',
        kafS00P1Params: { scheduleDisp: true, scheduleExcess: ExcessTimeStatus.NONE, scheduleTime: null, actualDisp: null, preAppDisp: null },
        numberOfHoursLeft: null
    });

    //Dis Information Leave Request List
    public DispInfoOfTimeLeaveRequestLst = [
        this.DispInfoOfTimeLeaveRequest1,
        this.DispInfoOfTimeLeaveRequest2,
        this.DispInfoOfTimeLeaveRequest3,
        this.DispInfoOfTimeLeaveRequest4
    ];

    //Go Back Time Value
    public iGoBackTime1 = new GoBackTime({
        frame: 0,
        startTime: null,
        endTime: null,
        name: 'KAFS12_18',
        swtOutClassification: 0,
    });
    public iGoBackTime2 = new GoBackTime({
        frame: 1,
        startTime: null,
        endTime: null,
        name: 'KAFS12_18',
        swtOutClassification: 0,
    });
    public iGoBackTime3 = new GoBackTime({
        frame: 2,
        startTime: null,
        endTime: null,
        name: 'KAFS12_18',
        swtOutClassification: 0,
    });

    public dataSource = [
        { id: 0, name: 'KAFS12_15' },
        { id: 1, name: 'KAFS12_16' },
    ];

    //Go Back Time List
    public GoBackTimeLst = [
        this.iGoBackTime1,
        this.iGoBackTime2,
        this.iGoBackTime3,
    ];

    @Prop({ default: () => { } })
    public readonly params!: InitParam;

    public created() {
        const vm = this;
       
        if (vm.params) {
            vm.mode = false;
            vm.appDispInfoStartupOutput = vm.params.appDispInfoStartupOutput;
        }
        if (vm.mode) {
            vm.application = vm.createApplicationInsert(AppType.ANNUAL_HOLIDAY_APPLICATION);
        } else {
            vm.application = vm.createApplicationUpdate(vm.params.appDispInfoStartupOutput.appDetailScreenInfo);
        }
        vm.$auth.user
            .then((user: any) => {
                vm.user = user;
            })
            .then(() => {
                if (vm.mode) {

                    return vm.loadCommonSetting(AppType.ANNUAL_HOLIDAY_APPLICATION);
                }

                return true;
            })
            .then((loadData: boolean) => {
                if (loadData) {
                    vm.updateKaf000_A_Params(vm.user);
                    vm.updateKaf000_B_Params(vm.mode);
                    vm.updateKaf000_C_Params(vm.mode);
                    vm.kaf000_B_Params.newModeContent.useMultiDaySwitch = false;
                    if (vm.mode) {
                        return vm.$http.post('at', API.initAppNew, {});
                    }

                    return true;
                }
            })
            .then((result: any) => {
                if (result) {

                } else {

                }
            })
            .catch((error: any) => {
                vm.handleErrorCustom(error)
                    .then((result) => {
                        if (result) {
                            vm.handleErrorCommon(error);
                        }
                    });
            })
            .then(() => {
                vm.$mask('hide');
            });
    }

    public nextToStep2() {
        const vm = this;

        vm.$emit('next-to-step-two',{data: 'this is data'});
    }

    public kaf000BChangeDate(objectDate) {
        const vm = this;
        if (objectDate.startDate) {
            if (vm.mode) {
                vm.application.appDate = vm.$dt.date(objectDate.startDate, 'YYYY/MM/DD');
                vm.application.opAppStartDate = vm.$dt.date(objectDate.startDate, 'YYYY/MM/DD');
                vm.application.opAppEndDate = vm.$dt.date(objectDate.endDate, 'YYYY/MM/DD');
                // console.log('changeDateCustom');
            }
        }
    }

    public kaf000BChangePrePost(prePostAtr) {
        const vm = this;
        vm.application.prePostAtr = prePostAtr;
    }

    public customValidate(viewModel: any) {
        const vm = this;
        let validAllChild = true;
        for (let child of viewModel.$children) {
            let validChild = true;
            if (child.$children) {
                validChild = vm.customValidate(child);
            }
            child.$validate();
            if (!child.$valid || !validChild) {
                validAllChild = false;
            }
        }

        return validAllChild;
    }

    public handleErrorCustom(failData: any): any {
        const vm = this;

        return new Promise((resolve) => {
            if (failData.messageId == 'Msg_26') {
                vm.$modal.error({ messageId: failData.messageId, messageParams: failData.parameterIds })
                    .then(() => {
                        vm.$goto('ccg008a');
                    });

                return resolve(false);
            }

            return resolve(true);
        });
    }

    public addNewGoBackTime() {
        const vm = this;

        let currentFrame = vm.GoBackTimeLst.length;

        if (currentFrame < 10) {
            let iGoBackTime = new GoBackTime({
                startTime: null,
                endTime: null,
                frame: currentFrame,
                name: 'KAFS12_18',
                swtOutClassification: 0
            });
            vm.GoBackTimeLst.push(iGoBackTime);
        }
    }
}


const API = {
    initAppNew: 'at/request/application/initApp',
};