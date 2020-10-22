import { Vue, _ } from '@app/provider';
import { component, Prop, Watch } from '@app/core/component';
import { KafS00ShrComponent, AppType } from 'views/kaf/s00/shr';
import {
    KafS00AComponent,
    KafS00CComponent
} from 'views/kaf/s00';
import { KafS00DComponent } from '../../../kaf/s00/d';
import { ScreenMode } from '../shr';

@component({
    name: 'kafs02c',
    route: '/kaf/s02/c',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {
        date: {
            required: true
        },
        selectedStampCD: {
            required: true
        },
        timeDuration: {
            required: true
        }
    },
    constraints: [],
    components: {
        'kafs00-a': KafS00AComponent,
        'kafs00-c': KafS00CComponent,
        'kafs00d': KafS00DComponent,
    },
})
export class KafS02CComponent extends KafS00ShrComponent {
    public title: string = 'KafS02C';

    @Prop({ default: null })
    public params?: any;

    public data: any = 'data';

    public mode: boolean = true;

    public user: any;

    public isValidateAll: Boolean = true;

    public date: Date = null;

    public timeDuration: number = null;

    public selectedStampCD: string = '0';
    public selectedOutCD: number = 1;

    public stampAtrs: any[] = [
        { code: '0', name: '出勤' }, // ATTENDANCE
        { code: '1', name: '退勤' }, // OFFICE_WORK
        { code: '2', name: '退勤（残業）' }, // OVERTIME
        { code: '3', name: '外出' }, // GO_OUT
        { code: '4', name: '戻り' }, // RETURN
        { code: '5', name: '早出' }, // EARLY
        { code: '6', name: '休出' }, // HOLIDAY
    ];

    public outingTypeAtrs: any[] = [
        { code: 1, name: '私用' },
        { code: 2, name: '公用' },
        { code: 3, name: '有償' },
        { code: 4, name: '組合' }
    ];

    public application: any = {
        version: 1,
        prePostAtr: 1,
        appType: 7,
        appDate: this.$dt(new Date(), 'YYYY/MM/DD'),
        enteredPerson: '1',
        inputDate: this.$dt(new Date(), 'YYYY/MM/DD HH:mm:ss'),
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
    };

    public created() {
        const self = this;
        if (self.params) {
            self.mode = false;
            this.data = self.params;
        }
    }

    public mounted() {
        const self = this;

        if (self.mode) {
            self.fetchStart();
        } else {
            self.application = self.data.appDispInfoStartupOutput.appDetailScreenInfo.application;
            self.fetchDataEdit();
        }
    }

    public fetchDataEdit() {
        const self = this;
        self.$mask('show');

        self.$auth.user.then((usr: any) => {
            self.user = usr;
        }).then(() => {
            self.bindData(self.data);
        });

        self.$mask('hide');
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
                    recoderFlag: true
                };

                return self.$http.post('at', API.startStampApp, command);
            }
        }).then((data: any) => {
            if (data) {
                console.log(data);
                self.bindData(data.data);
            }
        }).then(() => self.$mask('hide'));
    }

    private bindData(data: any) {
        const self = this;

        self.createParamA(data);
        self.createParamC(data);
        self.data = data;

        if (self.data.appRecordImage && self.data.appRecordImage.appStampCombinationAtr) {
            self.selectedStampCD = self.data.appRecordImage.appStampCombinationAtr.toString();
        }

        if (!self.mode) {
            self.date = self.application.appDate;
            if (self.data.appRecordImage) {
                self.selectedOutCD = self.selectedStampCD === '3' ? self.data.appRecordImage.appStampGoOutAtr : 1;
            }
    
            self.timeDuration = self.data.appRecordImage.attendanceTime;
        }
    }

    public register() {
        const self = this;

        let validAll: boolean = true;

        self.$mask('show');
        for (let child of self.$children) {
            child.$validate();
            if (!child.$valid) {
                validAll = false;
            }
        }
        self.isValidateAll = validAll;
        self.$validate();
        if (!self.$valid || !validAll) {
            self.$nextTick(() => {
                self.$mask('hide');
            });

            return;
        }

        self.bindDataApplication();
        self.bindDataRecorder();

        return self.checkBeforeRegister()
            .then((result) => {
                return self.handleConfirmMessage(result.data);
            })
            .then((result) => {
                if (result) {
                    console.log(result);

                    return self.registerData();
                }
            })
            .then((result) => {
                if (result) {
                    console.log(result);
                    self.$mask('hide');
                    self.$goto('kafs02a1', { mode: self.mode ? ScreenMode.NEW : ScreenMode.DETAIL, appID: result.data.appID });
                }
            }).catch((err) => {
                if (err) {
                    console.log(err);
                    self.handleErrorMessage(err);
                    self.$mask('hide');
                }
            });
    }

    private registerData(): any {
        const self = this;

        let command = {
            applicationDto: self.application,
            appStampDto: null,
            appRecordImageDto: self.data.appRecordImage,
            appStampOutputDto: self.data,
            recoderFlag: true
        };

        if (self.mode) {
            return self.$http.post('at', API.register, command);
        } else {
            return self.$http.post('at', API.update, command);
        }
    }

    private bindDataApplication() {
        const self = this;

        if (!self.mode) {
            self.application = self.data.appDispInfoStartupOutput.appDetailScreenInfo.application;
        }
        if (self.mode) {
            self.application.employeeID = self.user.employeeId;
        }

        if (self.kaf000_C_Params.output) {
            self.application.opAppStandardReasonCD = self.kaf000_C_Params.output.opAppStandardReasonCD;
            self.application.opAppReason = self.kaf000_C_Params.output.opAppReason;
        }
        self.application.enteredPerson = self.user.employeeId;
    }

    private bindDataRecorder() {
        const self = this;

        let appRecordImage = {
            appStampCombinationAtr: self.selectedStampCD,
            attendanceTime: self.timeDuration,
            appStampGoOutAtr: self.selectedStampCD === '3' ? self.selectedOutCD : null
        };
        self.data.appRecordImage = appRecordImage;
    }

    public checkBeforeRegister(): any {
        const self = this;
        let command = {
            companyId: self.user.companyId,
            agentAtr: false,
            applicationDto: self.application,
            appStampOutputDto: self.data
        };

        if (self.mode) {
            return self.$http.post('at', API.checkBeforeRegister, command);
        } else {
            return self.$http.post('at', API.checkBeforeUpdate, command);
        }
    }

    public handleConfirmMessage(messages: any) {
        const self = this;

        return new Promise((resolve: any) => {
            if (_.isEmpty(messages)) {
                resolve(true);
            }
            let msg = messages[0].value;

            return self.$modal.confirm({ messageId: msg.messageId })
                .then((value) => {
                    if (value === 'yes') {
                        return self.handleConfirmMessage(messages.data);
                    } else {
                        resolve(false);
                    }
                });
        });
    }

    public handleErrorMessage(res: any) {
        const self = this;
        if (res.messageId) {
            return self.$modal.error({ messageId: res.messageId, messageParams: res.parameterIds });
        } else {
            if (res.errors) {
                if (_.isArray(res.errors)) {
                    return self.$modal.error({ messageId: res.errors[0].messageId, messageParams: res.parameterIds });
                } else {
                    return self.$modal.error({ messageId: res.errors.messageId, messageParams: res.parameterIds });
                }
            }
        }
    }

    @Watch('date')
    public dateWatcher(value) {
        const self = this;

        self.application.appDate = self.$dt.date(self.date, 'YYYY/MM/DD');
        self.application.opAppStartDate = self.$dt.date(self.date, 'YYYY/MM/DD');
        self.application.opAppEndDate = self.$dt.date(self.date, 'YYYY/MM/DD');
        self.$emit('changeDate',
            {
                startDate: value,
                endDate: value
            });

        self.$mask('show');
        let command = {
            companyId: self.user.companyId,
            appStampOutputDto: self.data,
            date: [self.application.appDate],
            recorderFlag: true,
        };
        self.$http.post('at', API.changeDate, command)
            .then((result) => {
                if (result) {
                    console.log(result);
                }
                self.$mask('hide');
            }).catch((error) => {
                console.log(error);
                self.$mask('hide');
            });
    }

    public changeDate(dateObject: any) {
        const self = this;

        if (dateObject.startDate) {
            console.log(dateObject.startDate);
            console.log(dateObject.endDate);
        }
    }

    // -------------------------------------------------------------------------

    public createParamA(data: any) {
        const self = this;

        let appDispInfoWithDateOutput = data.appDispInfoStartupOutput.appDispInfoWithDateOutput;
        let appDispInfoNoDateOutput = data.appDispInfoStartupOutput.appDispInfoNoDateOutput;
        self.kaf000_A_Params = {
            companyID: self.user.companyId,
            employeeID: self.user.employeeId,
            // 申請表示情報．申請表示情報(基準日関係あり)．社員所属雇用履歴を取得．雇用コード
            employmentCD: appDispInfoWithDateOutput.empHistImport.employmentCode,
            // 申請表示情報．申請表示情報(基準日関係あり)．申請承認機能設定．申請利用設定
            applicationUseSetting: appDispInfoWithDateOutput.approvalFunctionSet.appUseSetLst[0],
            // 申請表示情報．申請表示情報(基準日関係なし)．申請設定．受付制限設定
            receptionRestrictionSetting: appDispInfoNoDateOutput.applicationSetting.receptionRestrictionSetting[0],
            // opOvertimeAppAtr: null
        };
    }

    public createParamC(data: any) {
        const self = this;
        // KAFS00_C_起動情報
        let appDispInfoNoDateOutput = data.appDispInfoStartupOutput.appDispInfoNoDateOutput;
        self.kaf000_C_Params = {
            input: {
                // 定型理由の表示
                // 申請表示情報．申請表示情報(基準日関係なし)．定型理由の表示区分
                displayFixedReason: appDispInfoNoDateOutput.displayStandardReason,
                // 申請理由の表示
                // 申請表示情報．申請表示情報(基準日関係なし)．申請理由の表示区分
                displayAppReason: appDispInfoNoDateOutput.displayAppReason,
                // 定型理由一覧
                // 申請表示情報．申請表示情報(基準日関係なし)．定型理由項目一覧
                reasonTypeItemLst: appDispInfoNoDateOutput.reasonTypeItemLst,
                // 申請制限設定
                // 申請表示情報．申請表示情報(基準日関係なし)．申請設定．申請制限設定
                appLimitSetting: appDispInfoNoDateOutput.applicationSetting.appLimitSetting,
                // 選択中の定型理由
                // empty
                // opAppStandardReasonCD: this.mode ? 1 : this.data.appWorkChangeDispInfo.appDispInfoStartupOutput.appDetailScreenInfo.application.opAppReason,
                // 入力中の申請理由
                // empty
                // opAppReason: this.mode ? 'Empty' : this.data.appWorkChangeDispInfo.appDispInfoStartupOutput.appDetailScreenInfo.application.opAppStandardReasonCD
                // 定型理由
                opAppStandardReasonCD: self.mode ? null : data.appDispInfoStartupOutput.appDetailScreenInfo.application.opAppStandardReasonCD,
                // 申請理由
                opAppReason: self.mode ? null : data.appDispInfoStartupOutput.appDetailScreenInfo.application.opAppReason
            },
            output: {
                // 定型理由
                opAppStandardReasonCD: self.mode ? '' : data.appDispInfoStartupOutput.appDetailScreenInfo.application.opAppStandardReasonCD,
                // 申請理由
                opAppReason: self.mode ? '' : data.appDispInfoStartupOutput.appDetailScreenInfo.application.opAppReason
            }
        };
    }

    public kaf000CChangeReasonCD(opAppStandardReasonCD) {
        const self = this;
        self.application.opAppStandardReasonCD = opAppStandardReasonCD;
    }

    public kaf000CChangeAppReason(opAppReason) {
        const self = this;
        self.application.opAppReason = opAppReason;
    }

    get condition1() {
        const self = this;

        // if (self.data.appRecordImage && self.data.appRecordImage.appStampCombinationAtr === 3) {
        //     return true;
        // }
        if (self.selectedStampCD === '3') {
            return true;
        }

        return false;
    }
}

const API = {
    startStampApp: 'at/request/application/stamp/startStampApp',
    changeDate: 'at/request/application/stamp/changeAppDateMobile',
    checkBeforeRegister: 'at/request/application/stamp/checkBeforeRegister',
    checkBeforeUpdate: 'at/request/application/stamp/checkBeforeUpdate',
    register: 'at/request/application/stamp/register',
    update: 'at/request/application/stamp/updateNew'
};