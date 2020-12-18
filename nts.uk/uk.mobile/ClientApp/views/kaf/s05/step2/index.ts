import { Vue } from '@app/provider';
import { component } from '@app/core/component';
import { KafS05Component} from '../a/index';
import { KafS00SubP3Component } from 'views/kaf/s00/sub/p3';
import { KafS00SubP1Component } from 'views/kaf/s00/sub/p1';
import { KafS00AComponent, KafS00BComponent, KafS00CComponent } from 'views/kaf/s00';
import { ExcessTimeStatus } from '../../s00/sub/p1';
@component({
    name: 'kafs05step2',
    route: '/kaf/s05/step2',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: [],
    components: {
        'kafs00subp3': KafS00SubP3Component,
        'kafs00subp1': KafS00SubP1Component,
        'kafs00-a': KafS00AComponent,
        'kafs00-b': KafS00BComponent,
        'kafs00-c': KafS00CComponent
    }
})
export class KafS05Step2Component extends Vue {
    public title: string = 'KafS05Step2';
    public overTime: number = null;

    public overTimes: Array<OverTime> = [];
    public holidayTimes: Array<HolidayTime> = [];

    public reason1: null;
    // nts-dropdownにバインドされる値
    public selectedValue: string = '3';
    
    // 各オプションにバインドされるデータソース
    public dropdownList: Array<Object> = [{
        code: '1',
        text: 'The First'
    }, {
        code: '2',
        text: 'The First'
    }, {
        code: '3',
        text: 'The First'
    }, {
        code: '4',
        text: 'The First'
    },{
        code: '5',
        text: 'The First'
    }];

    public created() {
        const self = this;
        self.bindOverTime();
        self.bindHolidayTime();
    }
    public bindOverTime() {
        const self = this;
        {
            let overTime = {} as OverTime;
            overTime.frameNo = '1';
            overTime.title = self.$i18n('KAFS05_70');
            overTime.visible = true;
            overTime.applicationTime = null;
            overTime.preTime = null;
            overTime.actualTime = null;
            overTime.preApp = {
                preAppDisp: true,
                preAppTime: null,
                preAppExcess: ExcessTimeStatus,

            };
            overTime.actualApp = {
                actualDisp: true,
                actualTime: null,
                actualExcess: ExcessTimeStatus
            };
            self.overTimes.push(overTime);
        }
        {
            let overTime = {} as OverTime;
            overTime.frameNo = '2';
            overTime.title = self.$i18n('KAFS05_70');
            overTime.visible = true;
            overTime.applicationTime = null;
            overTime.preTime = null;
            overTime.actualTime = null;
            overTime.preApp = {
                preAppDisp: true,
                preAppTime: null,
                preAppExcess: ExcessTimeStatus,

            };
            overTime.actualApp = {
                actualDisp: true,
                actualTime: null,
                actualExcess: ExcessTimeStatus
            };
            self.overTimes.push(overTime);
        }
        {
            let overTime = {} as OverTime;
            overTime.frameNo = '3';
            overTime.title = self.$i18n('KAFS05_70');
            overTime.visible = true;
            overTime.applicationTime = null;
            overTime.preTime = null;
            overTime.actualTime = null;
            overTime.preApp = {
                preAppDisp: true,
                preAppTime: null,
                preAppExcess: ExcessTimeStatus,

            };
            overTime.actualApp = {
                actualDisp: true,
                actualTime: null,
                actualExcess: ExcessTimeStatus
            };
            self.overTimes.push(overTime);
        }
    }


    public bindHolidayTime() {
        const self = this;
        {
            let holidaytime = {} as HolidayTime;
            holidaytime.frameNo = '1';
            holidaytime.title = self.$i18n('KAFS05_73');
            holidaytime.visible = true;
            holidaytime.applicationTime = null;
            holidaytime.preTime = null;
            holidaytime.actualTime = null;
            holidaytime.preApp = {
                preAppDisp: true,
                preAppTime: null,
                preAppExcess: ExcessTimeStatus,

            };
            holidaytime.actualApp = {
                actualDisp: true,
                actualTime: null,
                actualExcess: ExcessTimeStatus
            };
            self.holidayTimes.push(holidaytime);
        }
        {
            let holidaytime = {} as HolidayTime;
            holidaytime.frameNo = '2';
            holidaytime.title = self.$i18n('KAFS05_73');
            holidaytime.visible = true;
            holidaytime.applicationTime = null;
            holidaytime.preTime = null;
            holidaytime.actualTime = null;
            holidaytime.preApp = {
                preAppDisp: true,
                preAppTime: null,
                preAppExcess: ExcessTimeStatus,

            };
            holidaytime.actualApp = {
                actualDisp: true,
                actualTime: null,
                actualExcess: ExcessTimeStatus
            };
            self.holidayTimes.push(holidaytime);
        }
    }

    public preApp: any = {
        preAppDisp: true,
        preAppTime: null,
        preAppExcess: ExcessTimeStatus
    };
    public actualApp: any = {
        actualDisp: true,
        actualTime: null,
        actualExcess: ExcessTimeStatus
    };
    get $appContext(): KafS05Component {
        const self = this;

        return self.$parent as KafS05Component;
    }
    get nameInsert(): string {
        const self = this;

        return self.$appContext.modeNew ? self.$i18n('KAFS02_11') : self.$i18n('KAFS02_18');
    }
    public backStep1() {
        const self = this;
        self.$appContext.toStep(1);
    }
}
export interface OverTime {
    frameNo: string;
    title: string;
    visible: boolean;
    applicationTime: number;
    preTime: number;
    actualTime: number;
    preApp: any;
    actualApp: any;
}
export interface HolidayTime {
    frameNo: string;
    title: string;
    visible: boolean;
    applicationTime: number;
    preTime: number;
    actualTime: number;
    preApp: any;
    actualApp: any;
}