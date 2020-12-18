import { _, Vue } from '@app/provider';
import { component } from '@app/core/component';
import { KafS05Component} from '../a/index';
import { KafS00SubP3Component } from 'views/kaf/s00/sub/p3';
import { KafS00SubP1Component } from 'views/kaf/s00/sub/p1';
import { KafS00AComponent, KafS00BComponent, KafS00CComponent } from 'views/kaf/s00';
import { ExcessTimeStatus } from '../../s00/sub/p1';
import { DivergenceReasonSelect, DisplayInfoOverTime, DivergenceReasonInputMethod, DivergenceTimeRoot } from '../a/define.interface';
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

    public reason1: Reason = {
        title: '',
        reason: null,
        selectedValue: null,
        dropdownList: [{
            code: null,
            text: this.$i18n('KAFS05_54')
        }]
    } as Reason;

    public reason2: Reason = {
        title: '',
        reason: null,
        selectedValue: null,
        dropdownList: [{
            code: null,
            text: this.$i18n('KAFS05_54')
        }]
    } as Reason;
    

    public created() {
        const self = this;
        self.createOverTime();
        self.createHolidayTime();
    }
    public mounted() {

    }
    public bindOverTime() {
        const self = this;

        let displayInfoOverTime = self.$appContext.model.displayInfoOverTime;

        return null;
    }
    public createOverTime() {
        const self = this;
        {
            let overTime = {} as OverTime;
            overTime.frameNo = '1';
            overTime.title = self.$i18n('KAFS05_70') + overTime.frameNo;
            overTime.visible = true;
            overTime.applicationTime = 0;
            overTime.preApp = {
                preAppDisp: true,
                preAppTime: 0,
                preAppExcess: ExcessTimeStatus.NONE,

            };
            overTime.actualApp = {
                actualDisp: true,
                actualTime: 0,
                actualExcess: ExcessTimeStatus.NONE
            };
            self.overTimes.push(overTime);
        }
        
    }


    public createHolidayTime() {
        const self = this;
        {
            let holidaytime = {} as HolidayTime;
            holidaytime.frameNo = '1';
            holidaytime.title = self.$i18n('KAFS05_73');
            holidaytime.visible = true;
            holidaytime.applicationTime = 0;
            holidaytime.preApp = {
                preAppDisp: true,
                preAppTime: null,
                preAppExcess: ExcessTimeStatus.NONE,

            };
            holidaytime.actualApp = {
                actualDisp: true,
                actualTime: null,
                actualExcess: ExcessTimeStatus.NONE
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


    public bindAllReason() {
        const self = this;
        let divergenceTimeRoot1 = _.find(self.$appContext.model.displayInfoOverTime.infoNoBaseDate.divergenceTimeRoot, (item: DivergenceTimeRoot) => item.divergenceTimeNo == 1);
        let divergenceTimeRoot2 = _.find(self.$appContext.model.displayInfoOverTime.infoNoBaseDate.divergenceTimeRoot, (item: DivergenceTimeRoot) => item.divergenceTimeNo == 2);
        let divergenceReasonInputMethod1 = _.find(self.$appContext.model.displayInfoOverTime.infoNoBaseDate.divergenceReasonInputMethod, (item: DivergenceReasonInputMethod) => item.divergenceTimeNo == 1);
        let divergenceReasonInputMethod2 = _.find(self.$appContext.model.displayInfoOverTime.infoNoBaseDate.divergenceReasonInputMethod, (item: DivergenceReasonInputMethod) => item.divergenceTimeNo == 2);
        
        let reason1 = self.bindReason(divergenceTimeRoot1, divergenceReasonInputMethod1);
        let reason2 = self.bindReason(divergenceTimeRoot2, divergenceReasonInputMethod2);

        self.reason1 = reason1;
        self.reason2 = reason2;

    }

    public bindReason(divergenceTimeRoot: DivergenceTimeRoot, divergenceReasonInputMethod: DivergenceReasonInputMethod) {
        const self = this;
        let reason = {} as Reason;
        reason.title = '';
        reason.reason = null;
        reason.selectedValue = null;
        if (!_.isNil(divergenceTimeRoot)) {
            reason.title = divergenceTimeRoot.divTimeName;
        }
        reason.dropdownList = [] as Array<Object>;
        reason.dropdownList.push({
            code: null,
            text: self.$i18n('KAFS05_54')
        });
        _.forEach(divergenceReasonInputMethod.reasons, (item: DivergenceReasonSelect) => {
            let code = item.divergenceReasonCode;
            let text = item.divergenceReasonCode + ' ' + item.reason;
            reason.dropdownList.push({
                code,
                text
            });
            
        });

        return reason;

    }

    public loadAllData() {
        const self = this;
        self.bindAllReason();
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
export interface Reason {
    title?: string;
    reason: string;
    selectedValue: string;
    titleDrop?: string;
    dropdownList: Array<Object>;
}