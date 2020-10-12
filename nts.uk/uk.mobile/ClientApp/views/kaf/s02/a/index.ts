import { _,Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { TotopComponent } from '@app/components/totop';
import { KDL002Component } from '../../../kdl/002';
import { Kdl001Component } from '../../../kdl/001';
import { KafS00DComponent } from '../../../kaf/s00/d';
import { WorkHour, GoBackHour } from '../shr/index';
import {
    KafS00AComponent,
    KafS00BComponent,
    KafS00CComponent
} from 'views/kaf/s00';
import {
    KafS00SubP3Component
} from 'views/kaf/s00/sub/p3';
import { KafS00ShrComponent, AppType } from 'views/kaf/s00/shr';

@component({
    name: 'kafs02a',
    route: '/kaf/s02/a',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {
        workHour1: {
            timeRange: true,
            required: false
        }
    },
    constraints: [
        'nts.uk.shr.com.time.TimeWithDayAttr'
    ],
    components: {
        'kafs00-a': KafS00AComponent,
        'kafs00-b': KafS00BComponent,
        'kafs00-c': KafS00CComponent,
        'worktype': KDL002Component,
        'kafs00d': KafS00DComponent,
        'worktime': Kdl001Component,
        'to-top': TotopComponent,
        'kafs00subp3': KafS00SubP3Component,
    },
})
export class KafS02AComponent extends KafS00ShrComponent {
    public title: string = 'KafS02A';
    
    @Prop({ default: null })
    public params?: any;

    public data: any = 'data';

    public mode: boolean = true;

    public user: any;

    public dataSource = [
        { id: 1, name: '私用' },
        { id: 2, name: '公用' },
        { id: 3, name: '有償' },
        { id: 4, name: '組合' }
    ];

    // value workHours
    public workHour1 = new WorkHour(100, 200, 1, 'KAFS02_4', true, false, false, null, null);
    public workHour2 = new WorkHour(null, null, 2, 'KAFS02_6', true, false, false, null, null);
    public tempWorkHour1 = new WorkHour(null, null, 1, 'KAFS02_7', true, false, true, null, null);
    public tempWorkHour2 = new WorkHour(null, null, 2, 'KAFS02_7', true, false, true, null, null);
    public tempWorkHour3 = new WorkHour(null, null, 3, 'KAFS02_7', true, false, true, null, null);

    public workHourLst = [this.workHour1, this.workHour2];
    public tempWorkHourLst = [this.tempWorkHour1, this.tempWorkHour2, this.tempWorkHour3];

    // value goOut hour
    public goOut1 = new GoBackHour(null, null, 1, 1, 'KAFS02_9', true, false, false, null, null);
    public goOut2 = new GoBackHour(null, null, 2, 1, 'KAFS02_9', true, false, false, null, null);

    public goOutLst = [this.goOut1, this.goOut2];

    // value break time
    public break1 = new WorkHour(null, null, 1, 'KAFS02_12', true, false, false, null, null);
    public break2 = new WorkHour(null, null, 2, 'KAFS02_12', true, false, false, null, null);

    public breakLst = [this.break1, this.break2];

    // childCare time
    public childCareTime1 = new WorkHour(null, null, 1, 'KAFS02_14', true, false, false, null, null);
    public childCareTime2 = new WorkHour(null, null, 2, 'KAFS02_14', true, false, false, null, null);

    public childCareLst = [this.childCareTime1, this.childCareTime2];

    // long-term care time
    public longTermTime1 = new WorkHour(null, null, 1, 'KAFS02_16', true, false, false, null, null);
    public longTermTime2 = new WorkHour(null, null, 2, 'KAFS02_16', true, false, false, null, null);

    public longTermLst = [this.longTermTime1, this.longTermTime2];

    public created() {
        const self = this;
        if (self.params) {
            self.mode = false;
            this.data = self.params;
        }
    }

    public mounted() {
        const self = this;
        self.fetchStart();
    }

    public fetchStart() {
        const self = this;
        self.$mask('show');

        self.$auth.user.then((usr: any) => {
            self.user = usr;
        }).then(() => {
            return self.loadCommonSetting(AppType.STAMP_APPLICATION);
        }).then((data: any) => {
            if (data) {
                let command = {
                    companyId: self.user.companyId,
                    date: '',
                    appDispInfoStartupDto: self.appDispInfoStartupOutput,
                    recoderFlag: false
                };

                return self.$http.post('at', API.startStampApp, command);
            }
        }).then((data) => {
            if ( data ) {
                console.log(data);
            }
        }).then(() => self.$mask('hide'));
    }

    public addGooutHour() {
        const self = this;

        let currentFrame = self.goOutLst.length;
        if (currentFrame < 10) {
            let goOutHour = new GoBackHour(null, null, (currentFrame + 1), 1, 'KAFS02_9', true, false, false);
    
            self.goOutLst.push(goOutHour);
        }
    }

    public addBreakHour() {
        const self = this;

        let currentFrame = self.breakLst.length;
        if (currentFrame < 10) {
            let breakTime = new WorkHour(null, null, (currentFrame + 1), 'KAFS02_12', true, false, false);
    
            self.breakLst.push(breakTime);
        }
    }
}

const API = {
    startStampApp : 'at/request/application/stamp/startStampApp'
};