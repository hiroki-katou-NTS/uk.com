import { _, Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { KafS00CComponent } from 'views/kaf/s00/c';
import { KafS12AComponent } from 'views/kaf/s12/a';
import { ReflectSetting, TimeLeaveManagement, TimeLeaveRemaining, KafS12ApplyTimeComponent, TimeLeaveAppDetail } from '../shr';

@component({
    name: 'kafs12a2',
    route: '/kaf/s12/a2',
    style: require('./style.scss'),
    template: require('./index.vue'),
    components: {
        'kafs00c': KafS00CComponent,
        'kafs12-apply-time': KafS12ApplyTimeComponent,
    },
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KafS12A2Component extends Vue {
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
    @Prop({ default: [] })
    public readonly details: Array<TimeLeaveAppDetail>;

    public isValidateAll: boolean = true;
    public applyTimeData: Array<any> = [];
    public specialLeaveFrame: number = null;

    public created() {
        const vm = this;
        const appTimeTypeNames = ['KAFS12_20', 'KAFS12_26', 'KAFS12_27', 'KAFS12_28', 'KAFS12_29', 'KAFS12_30'];
        for (let i = 0; i < 6; i ++) {
            vm.applyTimeData.push({
                appTimeType: i,
                appTimeTypeName: appTimeTypeNames[i],
                substituteAppTime: 0,
                annualAppTime:  0,
                childNursingAppTime: 0,
                nursingAppTime: 0,
                super60AppTime: 0,
                specialAppTime: 0,
            });
        }
        if (!_.isEmpty(vm.details)) {
            vm.details.forEach((i) => {
                vm.specialLeaveFrame = vm.specialLeaveFrame || i.applyTime.specialLeaveFrameNo;
                vm.applyTimeData[i.appTimeType].substituteAppTime = i.applyTime.substituteAppTime;
                vm.applyTimeData[i.appTimeType].annualAppTime = i.applyTime.annualAppTime;
                vm.applyTimeData[i.appTimeType].childNursingAppTime = i.applyTime.childCareAppTime;
                vm.applyTimeData[i.appTimeType].nursingAppTime = i.applyTime.careAppTime;
                vm.applyTimeData[i.appTimeType].super60AppTime = i.applyTime.super60AppTime;
                vm.applyTimeData[i.appTimeType].specialAppTime = i.applyTime.specialAppTime;
            });
        }
        vm.$watch('timeLeaveManagement', (newVal, oldVal) => {
            if (!oldVal && newVal) {
                if (newVal.timeSpecialLeaveMng.listSpecialFrame && newVal.timeSpecialLeaveMng.listSpecialFrame.length > 0) {
                    vm.specialLeaveFrame = newVal.timeSpecialLeaveMng.listSpecialFrame[0].specialHdFrameNo;
                }
            }
        });
    }

    get $appContext(): KafS12AComponent {
        const self = this;

        return self.$parent as KafS12AComponent;
    }

    public handleChangeSpecialLeaveFrame(value: number) {
        const vm = this;
        if (vm.specialLeaveFrame !== value) {
            vm.specialLeaveFrame = value;
            vm.$mask('show');
            const params = {
                specialLeaveFrameNo: value,
                timeLeaveAppDisplayInfo: {
                    appDispInfoStartupOutput: vm.appDispInfoStartupOutput,
                    timeLeaveManagement: vm.timeLeaveManagement,
                    timeLeaveRemaining: vm.timeLeaveRemaining,
                    reflectSetting: vm.reflectSetting
                }
            };
            params.timeLeaveAppDisplayInfo.timeLeaveRemaining.remainingStart = new Date(params.timeLeaveAppDisplayInfo.timeLeaveRemaining.remainingStart).toISOString();
            params.timeLeaveAppDisplayInfo.timeLeaveRemaining.remainingEnd = new Date(params.timeLeaveAppDisplayInfo.timeLeaveRemaining.remainingEnd).toISOString();
            vm.$http.post('at', API.changeSpecialFrame, params).then((res: any) => {
                if (res) {
                    vm.timeLeaveRemaining.specialTimeFrames = res.data.timeLeaveRemaining.specialTimeFrames;
                }
                vm.$mask('hide');
            }).catch((error: any) => {
                vm.$modal.error(error).then(() => {
                    vm.$mask('hide');
                });
            });
        }
    }

    public nextToStep3() {
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
            return;
        }
        vm.$emit('next-to-step-three', vm.applyTimeData, vm.specialLeaveFrame);
    }

    public backToStepOne() {
        const vm = this;
        vm.$emit('back-to-step-one');
    }
}

const API = {
    changeSpecialFrame: 'at/request/application/timeLeave/changeSpecialFrame',
};