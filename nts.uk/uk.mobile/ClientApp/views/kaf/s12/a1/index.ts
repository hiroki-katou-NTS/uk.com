import { _, Vue } from '@app/provider';
import { component, Prop, Watch } from '@app/core/component';
import { KafS00AComponent, KafS00BComponent } from 'views/kaf/s00';
import { KafS12AComponent } from '../a';
import {
    ReflectSetting,
    TimeLeaveRemaining,
    TimeLeaveManagement,
    LateEarlyTimeZone,
    OutingTimeZone,
    TimeLeaveAppDetail,
    AppTimeType,
    KafS12LateEarlyComponent,
    KafS12OutingComponent
} from '../shr';

@component({
    name: 'kafs12a1',
    route: '/kaf/s12/a1',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    components: {
        'kafs00-a': KafS00AComponent,
        'kafs00-b': KafS00BComponent,
        'kafs12-late-early': KafS12LateEarlyComponent,
        'kafs12-outing': KafS12OutingComponent,
    },
    validations: {},
    constraints: []
})
export class KafS12A1Component extends Vue {
    @Prop({ default: true })
    public readonly newMode: boolean;
    @Prop({ default: null })
    public readonly appDispInfoStartupOutput: any;
    @Prop({ default: null })
    public readonly reflectSetting: ReflectSetting;
    @Prop({ default: null })
    public readonly timeLeaveManagement: TimeLeaveManagement;
    @Prop({ default: null })
    public readonly timeLeaveRemaining: TimeLeaveRemaining;
    @Prop({ default: null })
    public readonly application: any;
    @Prop({ default: [] })
    public readonly details: Array<TimeLeaveAppDetail>;

    public isValidateAll: boolean = true;
    public lateEarlyTimeZones: Array<LateEarlyTimeZone> = [];
    public outingTimeZones: Array<OutingTimeZone> = [];
    public outingOptions: Array<any> = [
        { id: AppTimeType.PRIVATE, name: 'KAFS12_15' },
        { id: AppTimeType.UNION, name: 'KAFS12_16' }
    ];

    public created() {
        const vm = this;
        for (let type = 0; type < 4; type++) {
            vm.lateEarlyTimeZones.push(new LateEarlyTimeZone(type));
        }
        for (let no = 1; no <= 10; no++) {
            vm.outingTimeZones.push(new OutingTimeZone(no, no <= 3));
        }
        if (!_.isEmpty(vm.details)) {
            vm.details.forEach((i) => {
                if (i.appTimeType < 4) {
                    vm.lateEarlyTimeZones[i.appTimeType].timeValue = i.appTimeType == AppTimeType.ATWORK || i.appTimeType == AppTimeType.ATWORK2 ? i.timeZones[0].endTime : i.timeZones[0].startTime;
                } else {
                    i.timeZones.forEach((j) => {
                        vm.outingTimeZones[j.workNo - 1].appTimeType = i.appTimeType;
                        vm.outingTimeZones[j.workNo - 1].timeZone.start = j.startTime;
                        vm.outingTimeZones[j.workNo - 1].timeZone.end = j.endTime;
                    });
                }
            });
        } else {
            vm.$watch('reflectSetting', (newVal: ReflectSetting, oldVal) => {
                if (!oldVal && newVal) {
                    if (newVal.destination.privateGoingOut == 1 && newVal.destination.unionGoingOut == 0) {
                        vm.outingTimeZones.forEach((i) => {
                            i.appTimeType = AppTimeType.PRIVATE;
                        });
                    } else if (newVal.destination.privateGoingOut == 0 && newVal.destination.unionGoingOut == 1) {
                        vm.outingTimeZones.forEach((i) => {
                            i.appTimeType = AppTimeType.UNION;
                        });
                    }
                }
            });
        }
    }

    get $appContext(): KafS12AComponent {
        const self = this;

        return self.$parent as KafS12AComponent;
    }

    @Watch('appDispInfoStartupOutput.appDispInfoWithDateOutput.opActualContentDisplayLst')
    public appDisplayInfoWatcher(value: Array<any>) {
        const self = this;
        self.updateTime(self.application.prePostAtr, value);
    }

    public kaf000BChangePrePost(prePostAtr) {
        const self = this;
        self.application.prePostAtr = prePostAtr;
        self.updateTime(prePostAtr, self.appDispInfoStartupOutput.appDispInfoWithDateOutput.opActualContentDisplayLst);
    }

    private updateTime(prePostAtr: number, opActualContentDisplayLst: Array<any>) {
        const self = this;
        if (self.newMode && prePostAtr == 1 && opActualContentDisplayLst && opActualContentDisplayLst[0].opAchievementDetail) {
            self.lateEarlyTimeZones.forEach((i: LateEarlyTimeZone) => {
                if ((i.appTimeType === 0 && self.condition2)
                    || (i.appTimeType === 1 && self.condition3)
                    || (i.appTimeType === 2 && self.condition9)
                    || (i.appTimeType === 3 && self.condition12)) {
                    if (i.timeValue == null) {
                        switch (i.appTimeType) {
                            case AppTimeType.ATWORK:
                                i.timeValue = opActualContentDisplayLst[0].opAchievementDetail.opWorkTime;
                                break;
                            case AppTimeType.OFFWORK:
                                i.timeValue = opActualContentDisplayLst[0].opAchievementDetail.opLeaveTime;
                                break;
                            case AppTimeType.ATWORK2:
                                i.timeValue = opActualContentDisplayLst[0].opAchievementDetail.opWorkTime2;
                                break;
                            case AppTimeType.OFFWORK2:
                                i.timeValue = opActualContentDisplayLst[0].opAchievementDetail.opDepartureTime2;
                                break;
                            default:
                                break;
                        }
                    }
                }
            });

            const outingTimes = opActualContentDisplayLst[0].opAchievementDetail.stampRecordOutput.outingTime || [];
            self.outingTimeZones.forEach((i: OutingTimeZone) => {
                outingTimes.forEach((time: any) => {
                    if (time.frameNo == i.workNo && i.timeZone.start == null && i.timeZone.end == null) {
                        i.timeZone.start = time.opStartTime;
                        i.timeZone.end = time.opEndTime;
                        i.appTimeType = time.opGoOutReasonAtr == 3 ? AppTimeType.UNION : AppTimeType.PRIVATE;
                    }
                });
            });
        } else if (self.newMode && prePostAtr == 0) {
            self.lateEarlyTimeZones.forEach((i: LateEarlyTimeZone) => {
                i.timeValue = null;
            });
            self.outingTimeZones.forEach((i: OutingTimeZone) => {
                i.timeZone.start = null;
                i.timeZone.end = null;
            });
        }
    }

    public nextToStep2() {
        const vm = this;
        vm.isValidateAll = true;
        for (let child of vm.$children) {
            child.$validate();
            if (!child.$valid) {
                vm.isValidateAll = false;
            }
        }
        vm.$validate();
        if (!vm.$valid || !vm.isValidateAll) {
            window.scrollTo(0, 0);

            return;
        }
        vm.$emit('next-to-step-two',
            vm.lateEarlyTimeZones,
            vm.outingTimeZones.filter((i: OutingTimeZone) => i.timeZone.start != null && i.timeZone.end != null),
        );
    }

    public handleAddOutingTimeZone() {
        const vm = this;
        for (let no = 1; no <= 10; no++) {
            if (!vm.outingTimeZones[no].display) {
                vm.outingTimeZones[no].display = true;
                break;
            }
        }
    }

    get displayAddButton() {
        const vm = this;

        return vm.outingTimeZones.filter((i: OutingTimeZone) => i.display).length < 10;
    }

    // ※2
    get condition2() {
        const vm = this;

        return !!vm.reflectSetting && vm.reflectSetting.destination.firstBeforeWork == 1;
    }

    // ※3
    get condition3() {
        const vm = this;

        return !!vm.reflectSetting && vm.reflectSetting.destination.firstAfterWork == 1;
    }

    //※7
    get condition7() {
        const vm = this;

        return !!vm.appDispInfoStartupOutput
            && vm.appDispInfoStartupOutput.appDispInfoNoDateOutput.managementMultipleWorkCycles;
    }

    //※8
    get condition8() {
        const vm = this;

        return !!vm.reflectSetting && vm.reflectSetting.destination.secondBeforeWork == 1;
    }

    //※9
    get condition9() {
        const vm = this;

        return vm.condition7 && vm.condition8;
    }

    //※11
    get condition11() {
        const vm = this;

        return !!vm.reflectSetting && vm.reflectSetting.destination.secondAfterWork == 1;
    }

    //※12
    get condition12() {
        const vm = this;

        return vm.condition7 && vm.condition11;
    }

    //※15
    get condition15() {
        const vm = this;

        return !!vm.reflectSetting
            && (vm.reflectSetting.destination.privateGoingOut == 1 || vm.reflectSetting.destination.unionGoingOut == 1);
    }

    //※16
    get condition16() {
        const vm = this;

        return !!vm.reflectSetting
            && vm.reflectSetting.destination.privateGoingOut == 1
            && vm.reflectSetting.destination.unionGoingOut == 1;
    }
}