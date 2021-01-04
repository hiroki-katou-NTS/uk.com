import { _, Vue } from '@app/provider';
import { component, Watch } from '@app/core/component';
import { KafS00SubP3Component } from 'views/kaf/s00/sub/p3';
import { KafS00SubP1Component } from 'views/kaf/s00/sub/p1';
import { KafS00AComponent, KafS00BComponent, KafS00CComponent } from 'views/kaf/s00';
import { TimeZoneWithWorkNo, BreakTime, TimeZoneNew, WorkHoursDto, AppOverTime, InfoWithDateApplication , DisplayInfoOverTime, TimeZone, ParamBreakTime, BreakTimeZoneSetting} from '../a/define.interface';
import { KafS10Component} from '../a/index';

@component({
    name: 'kafs10step1',
    route: '/kaf/s10/step1',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KafS10Step1Component extends Vue {
    public title: string = 'KafS10Step1';
}

interface WorkInfo {
    workType: Work;
    workTime: Work;
}
interface Work {
    code: string;
    name: string;
    time?: string;
}

interface ValueTime {
    start: number;
    end: number;
}
