import { Vue } from '@app/provider';
import { component } from '@app/core/component';
import {
    KafS00AComponent,
    KafS00BComponent,
    KafS00CComponent
} from 'views/kaf/s00';

@component({
    name: 'kafs07a',
    route: '/kaf/s07/a',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: [],
    components: {
        'kafs00-a': KafS00AComponent,
        'kafs00-b': KafS00BComponent,
        'kafs00-c': KafS00CComponent
    }
})
export class KafS07AComponent extends Vue {
    public title: string = 'KafS07A';
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
    }
}

const API = {
    startNew: 'at/request/application/workchange/startNew'
};