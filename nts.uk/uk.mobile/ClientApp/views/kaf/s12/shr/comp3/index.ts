import { Vue } from '@app/provider';
import { component, Prop, Watch } from '@app/core/component';
import { AppTimeType, ReflectSetting, TimeLeaveManagement, TimeLeaveRemaining } from '../';

@component({
    name: 'kafs12-apply-time',
    template: require('./index.vue'),
    components: {},
    validations: {},
    constraints: [
        'nts.uk.shr.com.time.AttendanceClock'
    ]
})
export class KafS12ApplyTimeComponent extends Vue {
    @Prop({ default: null })
    public readonly reflectSetting: ReflectSetting;
    @Prop({ default: null })
    public readonly timeLeaveManagement: TimeLeaveManagement;
    @Prop({ default: null })
    public readonly timeLeaveRemaining: TimeLeaveRemaining;
    @Prop({ default: null })
    public readonly appDispInfoStartupOutput: any;
    @Prop({ default: null })
    public readonly params: any;
    @Prop({ default: null })
    public readonly specialLeaveFrame: any;
    @Prop({ default: null })
    public readonly calculatedData: any;

    public substituteAppTime: number = 0;
    public annualAppTime: number = 0;
    public childNursingAppTime: number = 0;
    public nursingAppTime: number = 0;
    public super60AppTime: number = 0;
    public specialAppTime: number = 0;
    public comboBoxValue: number = null;

    public created() {
        const self = this;
    }

    get comboBoxOptions() {
        const self = this;

        return self.timeLeaveManagement ? self.timeLeaveManagement.timeSpecialLeaveMng.listSpecialFrame : [];
    }

    // @Watch('params')
    // public paramsWatcher(value: any) {
    //     const self = this;
    //     if (value) {
    //         self.substituteAppTime = value.substituteAppTime;
    //         self.annualAppTime = value.annualAppTime;
    //         self.childNursingAppTime = value.childNursingAppTime;
    //         self.nursingAppTime = value.nursingAppTime;
    //         self.super60AppTime = value.super60AppTime;
    //         self.specialAppTime = value.specialAppTime;
    //     }
    // }

    // @Watch('specialLeaveFrame')
    // public specialLeaveFrameWatcher(value: number) {
    //     const self = this;
    //     self.comboBoxValue = value;
    // }

    @Watch('comboBoxValue')
    public specialLeaveFrameNoWatcher(value: number) {
        const self = this;
        self.$emit('changeSpecialLeaveFrame', value);
    }

    // @Watch('substituteAppTime')
    // public subTimeWatcher(value: number) {
    //     const self = this;
    //     const { annualAppTime, childNursingAppTime, nursingAppTime, super60AppTime, specialAppTime } = self;
    //     self.$emit('changeAppTime', {
    //         appTimeType: self.params.appTimeType,
    //         substituteAppTime: value,
    //         annualAppTime,
    //         childNursingAppTime,
    //         nursingAppTime,
    //         super60AppTime,
    //         specialAppTime
    //     });
    // }
    //
    // @Watch('annualAppTime')
    // public annTimeWatcher(value: number) {
    //     const self = this;
    //     const { substituteAppTime, childNursingAppTime, nursingAppTime, super60AppTime, specialAppTime } = self;
    //     self.$emit('changeAppTime', {
    //         appTimeType: self.params.appTimeType,
    //         substituteAppTime,
    //         annualAppTime: value,
    //         childNursingAppTime,
    //         nursingAppTime,
    //         super60AppTime,
    //         specialAppTime
    //     });
    // }
    //
    // @Watch('childNursingAppTime')
    // public childTimeWatcher(value: number) {
    //     const self = this;
    //     const { substituteAppTime, annualAppTime, nursingAppTime, super60AppTime, specialAppTime } = self;
    //     self.$emit('changeAppTime', {
    //         appTimeType: self.params.appTimeType,
    //         substituteAppTime,
    //         annualAppTime,
    //         childNursingAppTime: value,
    //         nursingAppTime,
    //         super60AppTime,
    //         specialAppTime
    //     });
    // }
    //
    // @Watch('nursingAppTime')
    // public nurTimeWatcher(value: number) {
    //     const self = this;
    //     const { substituteAppTime, annualAppTime, childNursingAppTime, super60AppTime, specialAppTime } = self;
    //     self.$emit('changeAppTime', {
    //         appTimeType: self.params.appTimeType,
    //         substituteAppTime,
    //         annualAppTime,
    //         childNursingAppTime,
    //         nursingAppTime: value,
    //         super60AppTime,
    //         specialAppTime
    //     });
    // }
    //
    // @Watch('super60AppTime')
    // public supTimeWatcher(value: number) {
    //     const self = this;
    //     const { substituteAppTime, annualAppTime, childNursingAppTime, nursingAppTime, specialAppTime } = self;
    //     self.$emit('changeAppTime', {
    //         appTimeType: self.params.appTimeType,
    //         substituteAppTime,
    //         annualAppTime,
    //         childNursingAppTime,
    //         nursingAppTime,
    //         super60AppTime: value,
    //         specialAppTime
    //     });
    // }
    //
    // @Watch('specialAppTime')
    // public speTimeWatcher(value: number) {
    //     const self = this;
    //     const { substituteAppTime, annualAppTime, childNursingAppTime, nursingAppTime, super60AppTime } = self;
    //     self.$emit('changeAppTime', {
    //         appTimeType: self.params.appTimeType,
    //         substituteAppTime,
    //         annualAppTime,
    //         childNursingAppTime,
    //         nursingAppTime,
    //         super60AppTime,
    //         specialAppTime: value
    //     });
    // }

    get requiredAppTime() {
        const self = this;
        if (self.calculatedData) {
            switch (self.params.appTimeType) {
                case AppTimeType.ATWORK:
                    return self.$dt.timept(self.calculatedData.timeBeforeWork1);
                case AppTimeType.OFFWORK:
                    return self.$dt.timept(self.calculatedData.timeAfterWork1);
                case AppTimeType.ATWORK2:
                    return self.$dt.timept(self.calculatedData.timeBeforeWork2);
                case AppTimeType.OFFWORK2:
                    return self.$dt.timept(self.calculatedData.timeAfterWork2);
                case AppTimeType.PRIVATE:
                    return self.$dt.timept(self.calculatedData.privateOutingTime);
                case AppTimeType.UNION:
                    return self.$dt.timept(self.calculatedData.unionOutingTime);
                default:
                    return '0:00';
            }
        }

        return '0:00';
    }

    get totalAppTime() {
        const self = this;

        return self.$dt.timept(self.substituteAppTime
            + self.annualAppTime
            + self.childNursingAppTime
            + self.nursingAppTime
            + self.super60AppTime
            + self.specialAppTime);
    }

    get super60HRemaining() {
        const self = this;
        if (self.timeLeaveRemaining) {
            return self.$dt.timept(self.timeLeaveRemaining.super60HRemainingTime);
        }

        return '0:00';
    }

    get substituteRemaining() {
        const self = this;
        if (self.timeLeaveRemaining) {
            return self.$dt.timept(self.timeLeaveRemaining.subTimeLeaveRemainingTime);
        }

        return '0:00';
    }

    get annualRemaining() {
        const self = this;
        if (self.timeLeaveRemaining) {
            if (self.timeLeaveRemaining.annualTimeLeaveRemainingDays <= 0) {
                return self.$dt.timept(self.timeLeaveRemaining.annualTimeLeaveRemainingTime);
            } else if (self.timeLeaveRemaining.annualTimeLeaveRemainingTime <= 0) {
                return self.$i18n('KAF006_46', self.timeLeaveRemaining.annualTimeLeaveRemainingDays.toString());
            } else {
                return self.$i18n('KDL005_29', [
                    self.timeLeaveRemaining.annualTimeLeaveRemainingDays.toString(),
                    self.$dt.timept(self.timeLeaveRemaining.annualTimeLeaveRemainingTime)
                ]);
            }
        }

        return '0:00';
    }

    get childNursingRemaining() {
        const self = this;
        if (self.timeLeaveRemaining) {
            if (self.timeLeaveRemaining.childCareRemainingDays <= 0) {
                return self.$dt.timept(self.timeLeaveRemaining.childCareRemainingTime);
            } else if (self.timeLeaveRemaining.childCareRemainingTime <= 0) {
                return self.$i18n('KAF006_46', self.timeLeaveRemaining.childCareRemainingDays.toString());
            } else {
                return self.$i18n('KDL005_29', [
                    self.timeLeaveRemaining.childCareRemainingDays.toString(),
                    self.$dt.timept(self.timeLeaveRemaining.childCareRemainingTime)
                ]);
            }
        }

        return '0:00';
    }

    get nursingRemaining() {
        const self = this;
        if (self.timeLeaveRemaining) {
            if (self.timeLeaveRemaining.careRemainingDays <= 0) {
                return self.$dt.timept(self.timeLeaveRemaining.careRemainingTime);
            } else if (self.timeLeaveRemaining.careRemainingTime <= 0) {
                return self.$i18n('KAF006_46', self.timeLeaveRemaining.careRemainingDays.toString());
            } else {
                return self.$i18n('KDL005_29', [
                    self.timeLeaveRemaining.careRemainingDays.toString(),
                    self.$dt.timept(self.timeLeaveRemaining.careRemainingTime)
                ]);
            }
        }

        return '0:00';
    }

    get specialRemaining() {
        return '0:00';
    }

    get display() {
        const self = this;
        switch (self.params.appTimeType) {
            case AppTimeType.ATWORK:
                return self.calculatedData
                    && self.calculatedData.timeBeforeWork1 > 0
                    && self.reflectSetting
                    && self.reflectSetting.destination.firstBeforeWork == 1;
            case AppTimeType.OFFWORK:
                return self.calculatedData
                    && self.calculatedData.timeAfterWork1 > 0
                    && self.reflectSetting
                    && self.reflectSetting.destination.firstAfterWork == 1;
            case AppTimeType.ATWORK2:
                return self.calculatedData
                    && self.calculatedData.timeBeforeWork2 > 0
                    && self.reflectSetting
                    && self.reflectSetting.destination.secondBeforeWork == 1
                    && self.appDispInfoStartupOutput
                    && self.appDispInfoStartupOutput.appDispInfoNoDateOutput.managementMultipleWorkCycles;
            case AppTimeType.OFFWORK2:
                return self.calculatedData
                    && self.calculatedData.timeAfterWork2 > 0
                    && self.reflectSetting
                    && self.reflectSetting.destination.secondAfterWork == 1
                    && self.appDispInfoStartupOutput
                    && self.appDispInfoStartupOutput.appDispInfoNoDateOutput.managementMultipleWorkCycles;
            case AppTimeType.PRIVATE:
                return self.calculatedData
                    && self.calculatedData.privateOutingTime > 0
                    && self.reflectSetting
                    && self.reflectSetting.destination.privateGoingOut == 1;
            case AppTimeType.UNION:
                return self.calculatedData
                    && self.calculatedData.unionOutingTime > 0
                    && self.reflectSetting
                    && self.reflectSetting.destination.unionGoingOut == 1;
            default:
                return false;
        }
    }

    get display60H() {
        const self = this;

        return self.reflectSetting
            && self.reflectSetting.condition.superHoliday60H == 1
            && self.timeLeaveManagement
            && self.timeLeaveManagement.super60HLeaveMng.super60HLeaveMngAtr;
    }

    get displaySubstitute() {
        const self = this;

        return self.reflectSetting
            && self.reflectSetting.condition.substituteLeaveTime == 1
            && self.timeLeaveManagement
            && self.timeLeaveManagement.timeSubstituteLeaveMng.timeSubstituteLeaveMngAtr;
    }

    get displayAnnual() {
        const self = this;

        return self.reflectSetting
            && self.reflectSetting.condition.annualVacationTime == 1
            && self.timeLeaveManagement
            && self.timeLeaveManagement.timeAnnualLeaveMng.timeAnnualLeaveMngAtr;
    }

    get displayChildNursing() {
        const self = this;

        return self.reflectSetting
            && self.reflectSetting.condition.childNursing == 1;
    }

    get displayChildNursingRemaining() {
        const self = this;

        return self.reflectSetting
            && self.reflectSetting.condition.childNursing == 1
            && self.timeLeaveManagement
            && self.timeLeaveManagement.nursingLeaveMng.timeChildCareLeaveMngAtr;
    }

    get displayNursing() {
        const self = this;

        return self.reflectSetting
            && self.reflectSetting.condition.nursing == 1;
    }

    get displayNursingRemaining() {
        const self = this;

        return self.reflectSetting
            && self.reflectSetting.condition.nursing == 1
            && self.timeLeaveManagement
            && self.timeLeaveManagement.nursingLeaveMng.timeCareLeaveMngAtr;
    }

    get displaySpecial() {
        const self = this;

        return self.reflectSetting
            && self.reflectSetting.condition.specialVacationTime == 1
            && self.timeLeaveManagement
            && self.timeLeaveManagement.timeSpecialLeaveMng.timeSpecialLeaveMngAtr;
    }
}
