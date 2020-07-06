import { Vue } from '@app/provider';
import { component } from '@app/core/component';
import { KDL002Component } from '../../../kdl/002';
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
            required: true
        },
        valueWorkHours2: {
            timeRange: true,
            required: true
        }
    },
    constraints: [],
    components: {
        'kafs00-a': KafS00AComponent,
        'kafs00-b': KafS00BComponent,
        'kafs00-c': KafS00CComponent,
        'worktype': KDL002Component
    }
})
export class KafS07AComponent extends Vue {
    public title: string = 'KafS07A';

    // data that is fetched from server 
    public app: AppWorkChange;

    public mode: String = 'create';

    public worktype: Work = new Work();

    public worktime: WorkTime = new WorkTime();

    public switchbox1: number = 1;

    public switchbox2: number = 1;

    public valueWorkHours1: { start: number, end: number } = null;

    public valueWorkHours2: { start: number, end: number } = null;

    // handle visible of view

    public isCondition1: Boolean = false;

    public data: any;

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
        // self.$mask('show');
        // self.$http.post('at', API.startNew, {
        //     empLst: [],
        //     dateLst: []
        // })
        //     .then((resApp: any) => {
        //         self.appDispInfoStartupOutput = resApp.data.appDispInfoStartupOutput;
        //         self.$mask('hide');
        //     }).catch((res: any) => {
        //         self.$mask('hide');


        //     });

        // this.$http.post('at', API.startS07, {

        // })
        //     .then((res: any) => {
        //         if (!res) {
        //             return;
        //         }
        //         let appWorkChangeDispInfo = res.appWorkChangeDispInfo;


        //     }).catch((err: any) => {
        //         this.$mask('hide');
        //     });
    }

    public register() {
        console.log(this.application);
        // check validation 
        this.$validate();
        if (!this.$valid) {
            window.scrollTo(500, 0);

            return;
        }
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

    // visible/ invisible
    // A2_1

    // A4_3  「勤務変更申請の表示情報．就業時間帯の必須区分」が「必須」または「任意」
    public isDisplay1(params: any) {
        // return params.setupType == 1;
        return true;

    }
    // ※1 = ○　AND　「勤務変更申請の表示情報．申請表示情報．申請表示情報(基準日関係なし)．複数回勤務の管理」= true
    public isDisplay2(params: any) {
        // return params.appDispInfoStartupOutput.appDispInfoNoDateOutput.managementMultipleWorkCycles;
        return true;

    }
    // A6_1 「勤務変更申請の表示情報．勤務変更申請の反映.出退勤を反映するか」がする
    public isDisplay3(params: any) {
        // return params.reflectWorkChangeAppDto.whetherReflectAttendance == 1;
        return true;
    }

    // 「勤務変更申請の表示情報．勤務変更申請設定．勤務時間の初期表示」が「空白」 => clear data
    // 「勤務変更申請の表示情報．勤務変更申請設定．勤務時間の初期表示」が「定時」=> transfer from data
    public isDisplay4() {
        return true;

    }
    // // Display error message
    // // UI処理【1】
    // public isDisplay5() {
    //     return true;

    // }
    // handle message dialog



    // bind visible of view 
    public bindVisibleView(data: any) {
        let appWorkChangeDispInfo = data.appWorkChangeDispInfo;

        this.isCondition1 = this.isDisplay1(appWorkChangeDispInfo);

    }
    public openKDL002() {
        this.$modal(
            'worktype',
            {
                seledtedWkTypeCDs: ['001', '02', '03'],
                selectedWorkTypeCD: '001',
                seledtedWkTimeCDs: ['112', '001', '13'],
                selectedWorkTimeCD: '001',
                isSelectWorkTime: true,
            }
        ).then((f: any) => {
            if (f) {
                this.worktype.code = f.selectedWorkType.workTypeCode;
                this.worktype.name = f.selectedWorkType.name;
                this.worktime.code = f.selectedWorkTime.code;
                this.worktime.name = f.selectedWorkTime.name;
                this.worktime.time = f.selectedWorkTime.workTime1;
            }
        }).catch((res: any) => {
            this.$modal.error({ messageId: res.messageId });
        });
    }

}
export class Work {
    public code: String = '';
    public name: String = '';
    constructor() {

    }

}
export class WorkTime extends Work {
    public time: String = '09:30 ~ 17:30';
    constructor() {
        super();
    }
}
// data that is fetched from server 

export class Model extends AppWorkChange {

    constructor(workType: String, workTime: String, workHours1: String, workHours2: String, straight: boolean, bounce: boolean) {
        super(workType, workTime, workHours1, workHours2, straight, bounce);
    }
}

const API = {
    startNew: 'at/request/application/workchange/startNew',
    startS07: 'at/at/request/application/workchange/startMobile',
};
