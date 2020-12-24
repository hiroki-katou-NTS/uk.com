import { component, Prop, Watch } from '@app/core/component';
import { KafS00ShrComponent, AppType, Application, InitParam } from 'views/kaf/s00/shr';
import { KafS00AComponent, KafS00BComponent, KafS00CComponent } from 'views/kaf/s00';
import { KafS00DComponent } from 'views/kaf/s00/d';
import { DispInfoOfTimeLeaveRequest, GoBackTime } from '../shr/';
import { KafS00SubP1Component, ExcessTimeStatus } from 'views/kaf/s00/sub/p1/';
import { IAppDispInfoStartupOutput, IOpActualContentDisplayLst } from '../../s04/a/define';
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
    public condition: ICondition = null;
    public isValidateAll: boolean = true;

    //Disp Infomation Time Leave Request Value
    public DispInfoOfTimeLeaveRequest1 = new DispInfoOfTimeLeaveRequest({
        header: 'KAFS12_5', frame: 0, attendanceTimeLabel: 'KAFS12_6',
        attendanceTime: null, titleOfAttendanceTime: 'KAFS12_5',
        kafS00P1Params: { scheduleDisp: true, scheduleExcess: ExcessTimeStatus.NONE, scheduleTime: null, actualDisp: null, preAppDisp: null },
        numberOfHoursLeft: null,
        destination: null,
    });
    public DispInfoOfTimeLeaveRequest2 = new DispInfoOfTimeLeaveRequest({
        header: 'KAFS12_7', frame: 1, attendanceTimeLabel: 'KAFS12_8',
        attendanceTime: null, titleOfAttendanceTime: 'KAFS12_7',
        kafS00P1Params: { scheduleDisp: true, scheduleExcess: ExcessTimeStatus.NONE, scheduleTime: null, actualDisp: null, preAppDisp: null },
        numberOfHoursLeft: null,
        destination: null,
    });
    public DispInfoOfTimeLeaveRequest3 = new DispInfoOfTimeLeaveRequest({
        header: 'KAFS12_9', frame: 2, attendanceTimeLabel: 'KAFS12_10',
        attendanceTime: null, titleOfAttendanceTime: 'KAFS12_9',
        kafS00P1Params: { scheduleDisp: true, scheduleExcess: ExcessTimeStatus.NONE, scheduleTime: null, actualDisp: null, preAppDisp: null },
        numberOfHoursLeft: null,
        destination: null,
    });
    public DispInfoOfTimeLeaveRequest4 = new DispInfoOfTimeLeaveRequest({
        header: 'KAFS12_11', frame: 3, attendanceTimeLabel: 'KAFS12_12', attendanceTime: null, titleOfAttendanceTime: 'KAFS12_11',
        kafS00P1Params: { scheduleDisp: true, scheduleExcess: ExcessTimeStatus.NONE, scheduleTime: null, actualDisp: null, preAppDisp: null },
        numberOfHoursLeft: null,
        destination: null,
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
                        return vm.$http.post('at', API.initAppNew, vm.appDispInfoStartupOutput);
                    }

                    return true;
                }
            })
            .then((result: { data: IResult }) => {
                if (result) {
                    const { data } = result;
                    const { reflectSetting, appDispInfoStartupOutput } = data;
                    const { appDispInfoNoDateOutput } = appDispInfoStartupOutput;
                    const { managementMultipleWorkCycles } = appDispInfoNoDateOutput;
                    const { destination } = reflectSetting;
                    const { firstAfterWork, firstBeforeWork, privateGoingOut, secondAfterWork, secondBeforeWork, unionGoingOut } = destination;

                    vm.condition = {
                        firstAfterWork,
                        firstBeforeWork,
                        privateGoingOut,
                        secondAfterWork,
                        secondBeforeWork,
                        unionGoingOut,
                        managementMultipleWorkCycles
                    };
                    console.log(vm.condition);
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

        vm.isValidateAll = vm.customValidate(vm);
        vm.$validate();
        if (!vm.$valid || !vm.isValidateAll) {

            window.scrollTo(0, 100);

            return;
        }
        vm.$emit('next-to-step-two');
    }

    public kaf000BChangeDate(objectDate: IObjectChangeDate) {
        const vm = this;
        if (objectDate.startDate) {
            if (vm.mode) {
                vm.application.appDate = vm.$dt.date(objectDate.startDate, 'YYYY/MM/DD');
                vm.application.opAppStartDate = vm.$dt.date(objectDate.startDate, 'YYYY/MM/DD');
                vm.application.opAppEndDate = vm.$dt.date(objectDate.endDate, 'YYYY/MM/DD');
                // console.log('changeDateCustom');
            }
        }
        const { DispInfoOfTimeLeaveRequestLst } = vm;
        const { appDispInfoStartupOutput } = objectDate;
        const { appDispInfoWithDateOutput } = appDispInfoStartupOutput;
        const { opActualContentDisplayLst } = appDispInfoWithDateOutput;

        DispInfoOfTimeLeaveRequestLst.forEach((i) => {
            const { frame } = i;
            opActualContentDisplayLst.forEach((f) => {
                if (f.opAchievementDetail) {
                    const { opAchievementDetail } = f;
                    const { opWorkTime, opLeaveTime, opWorkTime2, opDepartureTime2, achievementEarly, stampRecordOutput } = opAchievementDetail;
                    const { outingTime } = stampRecordOutput;
                    const { scheAttendanceTime1, scheAttendanceTime2, scheDepartureTime1, scheDepartureTime2 } = achievementEarly;

                    if (frame === 0) {
                        i.attendanceTime = opWorkTime ? opWorkTime : null;
                        i.kafS00P1Params.scheduleTime = scheAttendanceTime1;
                    }
                    if (frame === 1) {
                        i.attendanceTime = opLeaveTime ? opLeaveTime : null;
                        i.kafS00P1Params.scheduleTime = scheDepartureTime1;
                    }
                    if (frame === 2) {
                        i.attendanceTime = opWorkTime2 ? opWorkTime2 : null;
                        i.kafS00P1Params.scheduleTime = scheAttendanceTime2;
                    }
                    if (frame === 3) {
                        i.attendanceTime = opDepartureTime2 ? opDepartureTime2 : null;
                        i.kafS00P1Params.scheduleTime = scheDepartureTime2;
                    }
                    
                    vm.GoBackTimeLst = [];

                    outingTime.forEach((i) => {
                        vm.GoBackTimeLst.push({
                            frame: i.frameNo - 1,
                            goBackTime: {
                                start: i.opStartTime,
                                end: i.opEndTime
                            },
                            name: 'KAFS12_18',
                            swtOutClassification: i.opGoOutReasonAtr
                        });
                    });
                } else {
                    i.kafS00P1Params.scheduleTime = null;
                    i.attendanceTime = null;
                    vm.GoBackTimeLst = [this.iGoBackTime1,this.iGoBackTime2,this.iGoBackTime3];
                }
            });
        });
    }


    // ※2
    get condition2() {
        const vm = this;

        if (vm.condition) {
            if (vm.condition.firstBeforeWork === 1) {

                return true;
            }

            return false;
        }
    }

    // ※3
    get condition3() {
        const vm = this;

        if (vm.condition) {
            if (vm.condition.firstAfterWork === 1) {

                return true;
            }

            return false;
        }
    }

    //※7
    get condition7() {
        const vm = this;

        if (vm.condition) {
            if (vm.condition.managementMultipleWorkCycles) {
                return true;
            }

            return false;
        }
    }

    //※8
    get condition8() {
        const vm = this;

        if (vm.condition) {
            if (vm.condition.secondBeforeWork === 1) {

                return true;
            }

            return false;
        }
    }

    //※9
    get condition9() {
        const vm = this;

        return vm.condition7 && vm.condition8;
    }

    //※11
    get condition11() {
        const vm = this;

        if (vm.condition) {
            if (vm.condition.secondAfterWork === 1) {

                return true;
            }

            return false;
        }
    }

    //※12
    get condition12() {
        const vm = this;

        return vm.condition7 && vm.condition11;
    }

    //※15
    get condition15() {
        const vm = this;

        if (vm.condition) {
            const { condition } = vm;
            const { unionGoingOut, privateGoingOut } = condition;

            if (unionGoingOut === 1 || privateGoingOut === 1) {

                return true;
            }

            return false;
        }
    }
    //※16
    get condition16() {
        const vm = this;

        if (vm.condition) {
            const { condition } = vm;
            const { unionGoingOut, privateGoingOut } = condition;

            if (unionGoingOut === 1 && privateGoingOut === 1) {

                return true;
            }

            return false;
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
    get showAddButton(): boolean {
        const vm = this;

        if (vm.GoBackTimeLst && vm.GoBackTimeLst.length === 10) {

            return false;
        }

        return true;
    }
}

const API = {
    initAppNew: 'at/request/application/timeLeave/initNewApp',
};

export interface IObjectChangeDate {
    startDate: Date;
    endDate: Date;
    appDispInfoStartupOutput: IAppDispInfoStartupOutput;
}

export interface IResult {
    appDispInfoStartupOutput: IAppDispInfoStartupOutput;
    reflectSetting: {
        condition: {},
        destination: {
            firstAfterWork: 0 | 1
            firstBeforeWork: 0 | 1
            privateGoingOut: 0 | 1;
            secondAfterWork: 0 | 1;
            secondBeforeWork: 0 | 1;
            unionGoingOut: 0 | 1;
        }
    };
    reflectActualTimeZone: number;
}

export interface ICondition {
    firstAfterWork: number;
    firstBeforeWork: number;
    privateGoingOut: number;
    secondAfterWork: number;
    secondBeforeWork: number;
    unionGoingOut: number;
    managementMultipleWorkCycles: boolean;
}