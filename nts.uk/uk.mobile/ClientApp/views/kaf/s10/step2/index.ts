import { _, Vue } from '@app/provider';
import { component } from '@app/core/component';
import { KafS10Component } from '../a/index';
import { KafS00SubP3Component } from 'views/kaf/s00/sub/p3';
import { KafS00SubP1Component } from 'views/kaf/s00/sub/p1';
import { KafS00AComponent, KafS00BComponent, KafS00CComponent } from 'views/kaf/s00';
import { ExcessTimeStatus } from '../../s00/sub/p1';
import { ReasonDivergence, ExcessStateMidnight, ExcessStateDetail, OutDateApplication, DivergenceReasonSelect, AppOverTime, OvertimeWorkFrame, DivergenceReasonInputMethod, DivergenceTimeRoot, AttendanceType, OvertimeApplicationSetting, HolidayMidNightTime, StaturoryAtrOfHolidayWork, WorkdayoffFrame } from '../a/define.interface';

@component({
    name: 'kafs10step2',
    route: '/kaf/s10/step2',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KafS10Step2Component extends Vue {
    public title: string = 'KafS10Step2';
}

export interface OverTime {
    frameNo: string;
    title: string;
    visible: boolean;
    applicationTime: number;
    preTime: number;
    actualTime: number;
    preApp: ExtraTime;
    actualApp: ExtraTime;
    type: AttendanceType;
}
export interface HolidayTime {
    frameNo: string;
    title: string;
    visible: boolean;
    applicationTime: number;
    preTime: number;
    actualTime: number;
    preApp: ExtraTime;
    actualApp: ExtraTime;
    type: number;
}
export interface Reason {
    title?: string;
    reason: string;
    selectedValue: string;
    titleDrop?: string;
    dropdownList: Array<Object>;
}
export interface ExtraTime {
    preAppDisp?: boolean;
    preAppTime?: number;
    preAppExcess?: ExcessTimeStatus;
    actualDisp?: boolean;
    actualTime?: number;
    actualExcess?: ExcessTimeStatus;
}