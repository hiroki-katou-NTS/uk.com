import { _, Vue } from '@app/provider';
import { component } from '@app/core/component';
import { KDL002Component } from '../../../kdl/002';
import {
    KafS00AComponent,
    KafS00BComponent,
    KafS00CComponent
} from 'views/kaf/s00';
// import { AppWorkChange } from '../../../cmm/s45/components/app2/index';
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

    public model: Model = new Model();

    // data that is fetched from server 
    // public app: AppWorkChange;

    public mode: Boolean = true;

    // public worktype: Work = new Work();

    // public worktime: WorkTime = new WorkTime();

    // public switchbox1: number = 1;

    // public switchbox2: number = 1;

    public valueWorkHours1: { start: number, end: number } = null;

    public valueWorkHours2: { start: number, end: number } = null;

    // handle visible of view

    public isCondition1: Boolean = false;

    public isCondition2: Boolean = false;

    public isCondition3: Boolean = false;

    public isCondition4: Boolean = false;

    // data is fetched service
    public data: any = 'data';

    public application: any = {};
    //   {
    //     employeeName: 'employee',
    //     appDate: new Date(),
    //     dateRange: {
    //         start: new Date(),
    //         end: new Date()
    //     },
    //     useRangeDate: true, // 複数日切り替えを利用する
    //     isRangeDate: false, // 複数日を初期選択する
    //     prePostAtr: 0,
    //     selectedReason: '',
    //     reason: 'KAF000C',
    //     appType: 2
    // };
    public appWorkChangeDto: any = {};
    public appDispInfoStartupOutput: any = {};

    public created() {
        console.log('created');
        // this.worktype.code = 'dd';
        // this.worktype.name = 'worktype';
        // this.worktime.code = 'dd';
        // this.worktime.name = 'worktime';
    }

    public mounted() {
        let self = this;
        this.$http.post('at', API.startS07, {
            mode: this.mode,
            companyId: '000000000000-0117',
            employeeId: '292ae91c-508c-4c6e-8fe8-3e72277dec16',
            listDates: ['2020/07/08'],
            appWorkChangeOutputDto: null,
            appWorkChangeDto: null
        })
            .then((res: any) => {
                if (!res) {
                    return;
                }
                this.data = res.data;
                let appWorkChangeDispInfo = res.data.appWorkChangeDispInfo;
                // this.bindVisibleView(appWorkChangeDispInfo);
                // this.bindCommon(appWorkChangeDispInfo);

                // *4
                // if (!this.isCondition4) {
                //     this.valueWorkHours1.start = _.find(appWorkChangeDispInfo.predetemineTimeSetting.lstTimezone, (item: any) => item.workNo == 1).startTime;
                //     this.valueWorkHours1.end = _.find(appWorkChangeDispInfo.predetemineTimeSetting.lstTimezone, (item: any) => item.workNo == 1).endTime;
                //     this.valueWorkHours2.start = _.find(appWorkChangeDispInfo.predetemineTimeSetting.lstTimezone, (item: any) => item.workNo == 2).startTime;
                //     this.valueWorkHours2.end = _.find(appWorkChangeDispInfo.predetemineTimeSetting.lstTimezone, (item: any) => item.workNo == 2).endTime;
                // }
                let appWorkChange = res.data.appWorkChange;
                this.model.workType.code = appWorkChangeDispInfo.workTypeCD;
                this.model.workType.name = _.find(appWorkChangeDispInfo.workTypeLst, (item: any) => item.workTypeCode == appWorkChangeDispInfo.workTypeCD).abbreviationName;

                this.model.workTime.code = appWorkChangeDispInfo.workTimeCD;
                // _.find(appWorkChangeDispInfo.appDispInfoStartupOutput.appDispInfoWithDateOutput.opWorkTimeLst, (item: any) => item.worktimeCode == appWorkChangeDispInfo.workTimeCD).workTimeDisplayName;
                this.model.workTime.name = 'workTimeCD';
                this.model.workTime.time = '';
                // _.find(appWorkChangeDispInfo.predetemineTimeSetting.lstTimezone, (item: any) => item.workNo == 1).startTime + '~'+ _.find(appWorkChangeDispInfo.predetemineTimeSetting.lstTimezone, (item: any) => item.workNo == 1).endTime;

                this.$mask('hide');
            }).catch((err: any) => {
                this.$mask('hide');
            });
    }
    public bindCommon(params: any) {
        // bind appDispInfoStartupOutput to common component
        this.appDispInfoStartupOutput.appDispInfoNoDateOutput = params.appWorkChangeDispInfo.appDispInfoStartupOutput.appDispInfoNoDateOutput;
        this.appDispInfoStartupOutput.appDispInfoWithDateOutput = params.appWorkChangeDispInfo.appDispInfoStartupOutput.appDispInfoWithDateOutput;
        this.appDispInfoStartupOutput.appDetailScreenInfo = params.appWorkChangeDispInfo.appDispInfoStartupOutput.appDetailScreenInfo;
    }
    public bindAppWorkChangeRegister() {
        this.appWorkChangeDto.straightGo = this.model.straight;
        this.appWorkChangeDto.bounce = this.model.bounce;
        this.appWorkChangeDto.opWorkTypeCD = this.model.workType.code;
        this.appWorkChangeDto.opWorkTimeCD = this.model.workTime.code;
        this.appWorkChangeDto.timeZoneWithWorkNoLst = [{
            workNo: 1,
            timeZone: {
                startTime: this.valueWorkHours1.start,
                endTime: this.valueWorkHours1.end
            }
        }, {
            workNo: 2,
            timeZone: {
                startTime: this.valueWorkHours2.start,
                endTime: this.valueWorkHours2.end
            }
        }];

    }

    public changeDate() {
        let params = {
            companyId: '000000000000-0117',
            listDates: ['2020/01/01'],
            appWorkChangeOutputDto: this.data.appWorkChangeDispInfo
        };
        this.$http.post('at', API.updateAppWorkChange, params)
            .then((res: any) => {
                this.data.appWorkChangeDispInfo = res.data.appWorkChangeDispInfo;
            })
            .catch((res: any) => {
                this.$modal.error({ messageId: res.messageId });
            });

    }
    public register() {
        console.log(this.application);
        // check validation 
        this.$validate();
        if (!this.$valid) {
            window.scrollTo(500, 0);

            return;
        }
        this.bindAppWorkChangeRegister();
        console.log(this.appWorkChangeDto);


        // check before registering application
        this.$http.post('at', API.checkBeforRegister, {
            mode: this.mode,
            companyId: '000000000000-0117',
            applicationDto: this.application,
            appWorkChangeDto: this.appWorkChangeDto,
            // 申請表示情報．申請表示情報(基準日関係あり)．承認ルートエラー情報
            isError: this.data.appWorkChangeDispInfo.appDispInfoWithDateOutput.opErrorFlag
        }).then((res: any) => {
            // confirmMsgLst
            // holidayDateLst
            let isConfirm = true;
            if (!_.isEmpty(res)) {
                this.$modal.confirm({ messageId: res.messageId }).then((value) => {
                    if (value == 'yes') {
                        isConfirm = true;
                    } else {
                        isConfirm = false;
                        this.$mask('hide');
                    }
                });

            }
            // 勤務変更申請の登録処理
            // register application
            if (isConfirm) {
                this.$http.post('at', API.registerAppWorkChange, {
                    mode: this.mode,
                    companyId: '000000000000-0117',
                    applicationDto: this.application,
                    appWorkChangeDto: this.appWorkChangeDto,
                    holidayDates: res.holidayDateLst,
                    isMail: true
                }).then((res: any) => {
                    // KAFS00_D_申請登録後画面に移動する
                }).catch((res: any) => {
                    this.$modal.error({ messageId: res.messageId });
                });
            }


        }).catch((res: any) => {
            // show message error
            this.$modal.error({ messageId: res.messageId });
        });

    }

    // visible/ invisible
    // A2_1

    // A4_3  「勤務変更申請の表示情報．就業時間帯の必須区分」が「必須」または「任意」
    public isDisplay1(params: any) {
        return params.setupType == 1;
    }
    // ※1 = ○　AND　「勤務変更申請の表示情報．申請表示情報．申請表示情報(基準日関係なし)．複数回勤務の管理」= true
    public isDisplay2(params: any) {
        return params.appDispInfoStartupOutput.appDispInfoNoDateOutput.managementMultipleWorkCycles;

    }
    // A6_1 「勤務変更申請の表示情報．勤務変更申請の反映.出退勤を反映するか」がする
    public isDisplay3(params: any) {
        return params.reflectWorkChangeAppDto.whetherReflectAttendance == 1;
    }

    // 「勤務変更申請の表示情報．勤務変更申請設定．勤務時間の初期表示」が「空白」 => clear data ,true
    // 「勤務変更申請の表示情報．勤務変更申請設定．勤務時間の初期表示」が「定時」=> transfer from data ,false
    public isDisplay4(params: any) {
        return params.appWorkChangeSet.initDisplayWorktimeAtr == 1;

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
        this.isCondition2 = this.isDisplay2(appWorkChangeDispInfo);
        this.isCondition3 = this.isDisplay3(appWorkChangeDispInfo);
        this.isCondition4 = this.isDisplay4(appWorkChangeDispInfo);

    }
    public openKDL002() {
        this.$modal(
            'worktype',
            {
                seledtedWkTypeCDs: _.map(_.uniqBy(this.data.appWorkChangeDispInfo.workTypeLst, (e: any) => e.workTypeCode), (item: any) => item.workTypeCode),
                selectedWorkTypeCD: this.model.workType.code,
                seledtedWkTimeCDs: ['112', '001', '13'],
                selectedWorkTimeCD: '001',
                isSelectWorkTime: true,
            }
        ).then((f: any) => {
            if (f) {
                this.model.workType.code = f.selectedWorkType.workTypeCode;
                this.model.workType.name = f.selectedWorkType.name;
                this.model.workTime.code = f.selectedWorkTime.code;
                this.model.workTime.name = f.selectedWorkTime.name;
                this.model.workTime.time = f.selectedWorkTime.workTime1;
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

export class Model {

    public workType: Work = new Work();

    public workTime: WorkTime = new WorkTime();

    // public workHours1: String = '';

    // public workHours2: String = '';

    public straight: number = 1;

    public bounce: number = 1;

    // public valueWorkHours1: { start: number, end: number } = null;

    // public valueWorkHours2: { start: number, end: number } = null;
    constructor() {

    }
}

const API = {
    startNew: 'at/request/application/workchange/startNew',
    startS07: 'at/request/application/workchange/startMobile',
    checkBeforRegister: 'at/request/application/workchange/checkBeforeRegister_New',
    registerAppWorkChange: 'at/request/application/workchange/addWorkChange_New',
    updateAppWorkChange: 'at/request/application/workchange/changeDateKAFS07'
};
