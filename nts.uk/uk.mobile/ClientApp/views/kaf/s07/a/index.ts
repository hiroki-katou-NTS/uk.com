import { Vue } from '@app/provider';
import { component } from '@app/core/component';
import {
    KafS00AComponent,
    KafS00BComponent,
    KafS00CComponent
} from 'views/kaf/s00';
import { AppWorkChange } from '../../../cmm/s45/components/app2/index';
@component({
    name: 'kafs07a',
    route: '/kaf/s07/a',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {
        valueWorkHours1: {
            timeRange: true,
            require: true
        },
        valueWorkHours2: {
            timeRange: true,
            require: true
        }
    },
    constraints: [],
    components: {
        'kafs00-a': KafS00AComponent,
        'kafs00-b': KafS00BComponent,
        'kafs00-c': KafS00CComponent
    }
})
export class KafS07AComponent extends Vue {
    public title: string = 'KafS07A';

    public app: AppWorkChange;

    public worktype: Work = new Work();

    public worktime: Work = new Work();

    public switchbox1: number = 1;

    public switchbox2: number = 1;

    public valueWorkHours1: { start: number, end: number } = null;

    public valueWorkHours2: { start: number, end: number } = null;

    public application: any = {
        employeeName: 'employee',
        appDate: new Date(),
        dateRange: {
            start: new Date(),
            end: new Date()
        },
        useRangeDate: true, // 複数日切り替えを利用する
        isRangeDate: false, // 複数日を初期選択する
        prePostAtr: 0,
        selectedReason: '',
        reason: 'KAF000C',
        appType: 2
    };
    public appDispInfoStartupOutput: any = {
        appDetailScreenInfo: {
            appID: ''
        }
    };

    public created() {
        console.log('created');
        this.worktype.code = 'dd';
        this.worktype.name = 'worktype';
        this.worktime.code = 'dd';
        this.worktime.name = 'worktime';

    }

    public mounted() {
        let self = this;
        self.$mask('show');
        self.$http.post('at', API.startNew, {
            empLst: [],
            dateLst: []
        })
            .then((resApp: any) => {
                self.appDispInfoStartupOutput = resApp.data.appDispInfoStartupOutput;
                self.$mask('hide');
            }).catch((res: any) => {
                self.$mask('hide');


            });
    }

    public register() {
        let self = this;
        console.log(self.application);
        // check validation 
        this.app = new AppWorkChange(
            this.worktype.code + '  ' + this.worktype.name,
            this.worktime.code + '  ' + this.worktime.name,
            this.valueWorkHours1.start + ' ~ ' + this.valueWorkHours1.end,
            this.valueWorkHours2.start + ' ~ ' + this.valueWorkHours2.end,
            this.switchbox1 == 1,
            this.switchbox2 == 1
            );

        console.log(this.app);
    }
}
export class Work {
    public code: String = '';
    public name: String = '';
    constructor() {

    }

}

const API = {
    startNew: 'at/request/application/workchange/startNew'
};
