import { _, Vue } from '@app/provider';
import { OverTimeShiftNight, BreakTime, TimeZoneNew, TimeZoneWithWorkNo, AppOverTime, ParamCalculateMobile, ParamSelectWorkMobile, InfoWithDateApplication, ParamStartMobile, OvertimeAppAtr, Model, DisplayInfoOverTime, NotUseAtr, ApplicationTime, OvertimeApplicationSetting, AttendanceType, HolidayMidNightTime, StaturoryAtrOfHolidayWork, ParamBreakTime, WorkInformation, WorkHoursDto } from '../a/define.interface';
import { component, Prop } from '@app/core/component';
import { StepwizardComponent } from '@app/components';
import { KafS10Step1Component } from '../step1';
import { HolidayTime, KafS10Step2Component } from '../step2';
import { KafS10Step3Component } from '../step3';
import { KDL002Component } from '../../../kdl/002';
import { Kdl001Component } from '../../../kdl/001';
import { KafS00ShrComponent, AppType, Application, InitParam } from 'views/kaf/s00/shr';
import { OverTime } from '../step2/index';

@component({
    name: 'kafs10a',
    route: '/kaf/s10/a',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: [],
    components: {
        'step-wizard': StepwizardComponent,
        'kafS05Step1Component': KafS10Step1Component,
        'kafS05Step2Component': KafS10Step2Component,
        'kafS05Step3Component': KafS10Step3Component,
        'worktype': KDL002Component,
        'worktime': Kdl001Component,
    }
})
export class KafS10Component extends KafS00ShrComponent {
    public title: string = 'KafS10A';

    private numb: number = 1;

    public modeNew: boolean = true;
    public appId: string;
    
    @Prop()
    public readonly params: InitParam;

    public get step() {
        return `step_${this.numb}`;
    }
}