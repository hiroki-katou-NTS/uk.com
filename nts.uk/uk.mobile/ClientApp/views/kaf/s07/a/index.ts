import { _, Vue } from '@app/provider';
import { component, Watch } from '@app/core/component';
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

    public mode: Boolean = true;

    public valueWorkHours1: { start: number, end: number };

    public valueWorkHours2: { start: number, end: number };

    // handle visible of view

    public isCondition1: boolean = false;

    public isCondition2: boolean = false;

    public isCondition3: boolean = false;

    public isCondition4: boolean = false;

    // data is fetched service
    public data: any = 'data';

    public kaf000_B_Params: any = {};

    public kaf000_A_Params: any = {};

    public user: any;
    public application: any = {
        version: 1,
        appID: 'dddd',
        prePostAtr: 1,
        employeeID: '292ae91c-508c-4c6e-8fe8-3e72277dec16',
        appType: 2,
        appDate: '2020/01/07',
        enteredPerson: '1',
        inputDate: '2020/01/07 20:11:11',
        reflectionStatus: {
            listReflectionStatusOfDay: [{
                actualReflectStatus: 1,
                scheReflectStatus: 1,
                targetDate: '2020/01/07',
                opUpdateStatusAppReflect: {
                    opActualReflectDateTime: '2020/01/07 20:11:11',
                    opScheReflectDateTime: '2020/01/07 20:11:11',
                    opReasonActualCantReflect: 1,
                    opReasonScheCantReflect: 0

                },
                opUpdateStatusAppCancel: {
                    opActualReflectDateTime: '2020/01/07 20:11:11',
                    opScheReflectDateTime: '2020/01/07 20:11:11',
                    opReasonActualCantReflect: 1,
                    opReasonScheCantReflect: 0
                }
            }]
        },
        opStampRequestMode: 1,
        opReversionReason: '1',
        opAppStartDate: '2020/08/07',
        opAppEndDate: '2020/08/07',
        opAppReason: 'jdjadja',
        opAppStandardReasonCD: 1


    };
    public appWorkChangeDto: any = {};
    public appDispInfoStartupOutput: any = {};

    public created() {
        const self = this;
        self.kaf000_B_Params = {
            input: {
                mode: 0,
                appDisplaySetting: {
                    prePostDisplayAtr: 1,
                    manualSendMailAtr: 0
                },
                newModeContent: {
                    appTypeSetting: {
                        appType: 2,
                        sendMailWhenRegister: false,
                        sendMailWhenApproval: false,
                        displayInitialSegment: 1,
                        canClassificationChange: true
                    },
                    useMultiDaySwitch: true,
                    initSelectMultiDay: true
                }
            },
            output: {
                prePostAtr: 0,
                startDate: new Date(),
                endDate: new Date()
            }
        };

        self.kaf000_A_Params = {
            companyID: '',
            employeeID: '',
            employmentCD: '',
            applicationUseSetting: '',
            receptionRestrictionSetting: '',
            opOvertimeAppAtr: '',
        };

    }

    get application1() {
        const self = this;

        return {
            prePostAtr: self.kaf000_B_Params.output.prePostAtr
        };
    }

    public mounted() {
        let self = this;
        this.fetchStart();

    }

    public async fetchStart() {
        await this.$auth.user.then((usr: any) => {
            this.user = usr;
        });


        this.$http.post('at', API.startS07, {
            mode: this.mode,
            companyId: this.user.companyId,
            employeeId: this.user.employeeId,
            listDates: ['2020/07/08'],
            appWorkChangeOutputDto: null,
            appWorkChangeDto: null
        })
            .then((res: any) => {
                if (!res) {
                    return;
                }
                this.data = res.data;
                // let appWorkChange = res.data.appWorkChange;
                this.bindStart();
                this.$mask('hide');
            }).catch((err: any) => {
                this.$mask('hide');
            });
    }
    public bindStart() {
        let appWorkChangeDispInfo = this.data.appWorkChangeDispInfo;
        this.bindVisibleView(appWorkChangeDispInfo);
        this.bindCommon(appWorkChangeDispInfo);
        this.bindValueWorkHours(appWorkChangeDispInfo);
        this.bindWork(appWorkChangeDispInfo);
        this.$updateValidator('valueWorkHours1', {
            timeRange: true,
            required: this.isCondition3 && this.isCondition4
        });
        this.$updateValidator('valueWorkHours2', {
            timeRange: true,
            required: this.isCondition3 && this.isCondition4
        });
        // this.$validate('checked');

    }
    public bindWork(params: any) {
        this.model.workType.code = params.workTypeCD;
        this.model.workType.name = _.find(params.workTypeLst, (item: any) => item.workTypeCode == params.workTypeCD).abbreviationName || this.$i18n('KAFS07_10');

        this.model.workTime.code = params.workTimeCD;
        this.model.workTime.name = _.find(params.appDispInfoStartupOutput.appDispInfoWithDateOutput.opWorkTimeLst, (item: any) => item.worktimeCode == params.workTimeCD).workTimeDisplayName.workTimeName || this.$i18n('KAFS07_10');
        this.bindWorkTimeWithCondition4(params);
    }
    public bindWorkTimeWithCondition4(params: any) {
        if (this.isCondition4) {
            this.model.workTime.time = '';
        } else {
            let startTime = _.find(params.predetemineTimeSetting.prescribedTimezoneSetting.lstTimezone, (item: any) => item.workNo == 1).start;
            let endTime = _.find(params.predetemineTimeSetting.prescribedTimezoneSetting.lstTimezone, (item: any) => item.workNo == 1).end;
            this.model.workTime.time = this.$dt.timedr(startTime) + '~' + this.$dt.timedr(endTime);
        }
    }
    public bindValueWorkHours(params: any) {
        // *4
        if (!this.isCondition4) {
            let time1 = _.find(params.predetemineTimeSetting.prescribedTimezoneSetting.lstTimezone, (item: any) => item.workNo == 1);
            let time2 = _.find(params.predetemineTimeSetting.prescribedTimezoneSetting.lstTimezone, (item: any) => item.workNo == 2);
            if (this.valueWorkHours1 && this.isCondition1) {
                this.valueWorkHours1.start = time1.start;
                this.valueWorkHours1.end = time1.end;
            } else {
                this.valueWorkHours1 = {
                    start: time1.start,
                    end: time1.end
                };
            }
            if (this.valueWorkHours2 && this.isCondition2) {
                this.valueWorkHours2.start = time2.start;
                this.valueWorkHours2.end = time2.end;
            } else {
                this.valueWorkHours2 = {
                    start: time2.start,
                    end: time2.end
                };
            }
        }
    }

    public bindCommon(params: any) {
        // bind appDispInfoStartupOutput to common component
        this.appDispInfoStartupOutput.appDispInfoNoDateOutput = params.appDispInfoStartupOutput.appDispInfoNoDateOutput;
        this.appDispInfoStartupOutput.appDispInfoWithDateOutput = params.appDispInfoStartupOutput.appDispInfoWithDateOutput;
        this.appDispInfoStartupOutput.appDetailScreenInfo = params.appDispInfoStartupOutput.appDetailScreenInfo;
    }
    public bindAppWorkChangeRegister() {
        this.appWorkChangeDto.straightGo = this.model.straight == 2 ? 0 : 1;
        this.appWorkChangeDto.bounce = this.model.bounce == 2 ? 0 : 1;
        this.appWorkChangeDto.opWorkTypeCD = this.model.workType.code;
        this.appWorkChangeDto.opWorkTimeCD = this.model.workTime.code;
        if (this.isCondition3) {
            this.appWorkChangeDto.timeZoneWithWorkNoLst = [];
            let a = {
                workNo: 1,
                timeZone: {
                    startTime: this.valueWorkHours1.start,
                    endTime: this.valueWorkHours1.end
                }
            };
            let b = {
                workNo: 2,
                timeZone: {
                    startTime: this.valueWorkHours2.start,
                    endTime: this.valueWorkHours2.end
                }
            };
            if (this.isCondition1) {
                this.appWorkChangeDto.timeZoneWithWorkNoLst.push(a);
            }
            if (this.isCondition2) {
                this.appWorkChangeDto.timeZoneWithWorkNoLst.push(b);
            }
        }


    }

    public changeDate() {
        let params = {
            companyId: this.user.companyId,
            listDates: ['2020/01/01'],
            appWorkChangeDispInfo: this.data.appWorkChangeDispInfo
        };
        this.$http.post('at', API.updateAppWorkChange, params)
            .then((res: any) => {
                this.data.appWorkChangeDispInfo = res.data;
                this.bindStart();
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
            companyId: this.user.companyId,
            applicationDto: this.application,
            appWorkChangeDto: this.appWorkChangeDto,
            // 申請表示情報．申請表示情報(基準日関係あり)．承認ルートエラー情報
            // this.data.appWorkChangeDispInfo.appDispInfoWithDateOutput.opErrorFlag
            isError: 1
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
                    companyId: this.user.companyId,
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
        // return true;
    }
    // ※1 = ○　AND　「勤務変更申請の表示情報．申請表示情報．申請表示情報(基準日関係なし)．複数回勤務の管理」= true
    public isDisplay2(params: any) {
        return params.appDispInfoStartupOutput.appDispInfoNoDateOutput.managementMultipleWorkCycles;
        // return false;

    }
    // A6_1 「勤務変更申請の表示情報．勤務変更申請の反映.出退勤を反映するか」がする
    public isDisplay3(params: any) {
        return params.reflectWorkChangeAppDto.whetherReflectAttendance == 1;
    }

    // 「勤務変更申請の表示情報．勤務変更申請設定．勤務時間の初期表示」が「空白」 => clear data ,true
    // 「勤務変更申請の表示情報．勤務変更申請設定．勤務時間の初期表示」が「定時」=> transfer from data ,false
    public isDisplay4(params: any) {
        // return false;
        return params.appWorkChangeSet.initDisplayWorktimeAtr == 1;

    }
    // // Display error message
    // // UI処理【1】
    // public isDisplay5() {
    //     return true;

    // }
    // handle message dialog



    // bind visible of view 
    public bindVisibleView(params: any) {
        let appWorkChangeDispInfo = params;

        this.isCondition1 = this.isDisplay1(appWorkChangeDispInfo);
        this.isCondition2 = this.isDisplay2(appWorkChangeDispInfo);
        this.isCondition3 = this.isDisplay3(appWorkChangeDispInfo);
        this.isCondition4 = this.isDisplay4(appWorkChangeDispInfo);

    }
    public openKDL002() {
        console.log(_.map(this.data.appWorkChangeDispInfo.appDispInfoStartupOutput.appDispInfoWithDateOutput.opWorkTimeLst, (item: any) => item.worktimeCode));
        this.$modal(
            'worktype',
            {
                seledtedWkTypeCDs: _.map(_.uniqBy(this.data.appWorkChangeDispInfo.workTypeLst, (e: any) => e.workTypeCode), (item: any) => item.workTypeCode),
                selectedWorkTypeCD: this.model.workType.code,
                seledtedWkTimeCDs: _.map(this.data.appWorkChangeDispInfo.appDispInfoStartupOutput.appDispInfoWithDateOutput.opWorkTimeLst, (item: any) => item.worktimeCode),
                selectedWorkTimeCD: this.model.workTime.code,
                isSelectWorkTime: '1',
            }
        ).then((f: any) => {
            if (f) {
                this.model.workType.code = f.selectedWorkType.workTypeCode;
                this.model.workType.name = f.selectedWorkType.name;
                this.model.workTime.code = f.selectedWorkTime.code;
                this.model.workTime.name = f.selectedWorkTime.name;
                if (!this.isCondition4) {
                    this.model.workTime.time = f.selectedWorkTime.workTime1;
                }
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
    public time: String = '項目移送';
    constructor() {
        super();
    }
}

export class Model {

    public workType: Work = new Work();

    public workTime: WorkTime = new WorkTime();

    public straight: number = 2;

    public bounce: number = 2;

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
