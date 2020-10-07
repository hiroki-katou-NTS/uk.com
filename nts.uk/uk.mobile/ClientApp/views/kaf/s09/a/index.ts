import { _, Vue } from '@app/provider';
import { component, Prop, Watch } from '@app/core/component';
import { KDL002Component } from '../../../kdl/002';
import { Kdl001Component } from '../../../kdl/001';
import { KafS00DComponent } from '../../../kaf/s00/d';
import {
    KafS00AComponent,
    KafS00BComponent,
    KafS00CComponent
} from 'views/kaf/s00';
import { KafS00ShrComponent, AppType } from 'views/kaf/s00/shr';
@component({
    name: 'kafs09a',
    route: '/kaf/s09/a',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    constraints: [],
    components: {
        'kafs00-a': KafS00AComponent,
        'kafs00-b': KafS00BComponent,
        'kafs00-c': KafS00CComponent,
        'worktype': KDL002Component,
        'kafs00d': KafS00DComponent,
        'worktime': Kdl001Component,
    },

})
export class KafS09AComponent extends KafS00ShrComponent {
    // to edit
    @Prop({ default: null })
    public params?: any;
    public title: string = 'KafS09A';

    public model: Model = new Model();

    public mode: Boolean = true;

    public isValidateAll: Boolean = true;

    // data is fetched service
    public data: any = 'data';

    public kaf000_A_Params: any = null;

    public kaf000_B_Params: any = null;

    public kaf000_C_Params: any = null;

    public isChangeDate: boolean = false;
    public user: any;
    public application: any = {
        version: 1,
        prePostAtr: 1,
        appType: 4,
        appDate: this.$dt(new Date(), 'YYYY/MM/DD'),
        enteredPerson: '',
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
    public appWorkChangeDto: any = {};
    public dataOutput;
    public appDispInfoStartupOutput: any = {};

    public created() {
        const self = this;
        if (self.params) {
            console.log(self.params);
            self.mode = false;
            this.dataOutput = self.params;
        }
        self.fetchStart();

    }
    public mounted() {
        let self = this;

    }

    public fetchStart() {
        const self = this;
        self.$mask('show');
        self.$auth.user.then((usr: any) => {
            self.user = usr;
        }).then(() => {
            return self.loadCommonSetting(AppType.GO_RETURN_DIRECTLY_APPLICATION);
        }).then((loadData: any) => {
            if (loadData) {
                return self.$http.post('at', API.startS09, {
                    companyId: this.user.companyId,
                    employeeId: this.user.employeeId,
                    dates: [],
                    mode: self.mode,
                    inforGoBackCommonDirectDto: self.dataOutput ? self.dataOutput : null,
                    appDispInfoStartupDto: self.dataOutput ? self.dataOutput.appDispInfoStartup : self.appDispInfoStartupOutput
                });
            }
            if (self.appDispInfoStartupOutput) {
                if (self.appDispInfoStartupOutput.appDispInfoWithDateOutput.opErrorFlag != 0) {
                    return self.$http.post('at', API.startS09, {
                        companyId: this.user.companyId,
                        employeeId: this.user.employeeId,
                        dates: [],
                        mode: self.mode,
                        inforGoBackCommonDirectDto: self.dataOutput ? self.dataOutput : null,
                        appDispInfoStartupDto: this.appDispInfoStartupOutput
                    });
                }
            }

        }).then((res: any) => {
            if (!res) {
                return;
            }
            self.dataOutput = res.data;
            self.appDispInfoStartupOutput = self.dataOutput.appDispInfoStartup;
            self.createParamA();
            self.createParamB();
            self.createParamC();
            self.bindStart();
            self.$mask('hide');
        }).catch((err: any) => {
            self.handleErrorMessage(err).then((res: any) => {
                if (err.messageId == 'Msg_43') {
                    self.$goto('ccg008a');
                }
            });
        });
    }


    // bind params to components
    public createParamA() {
        const self = this;
        let appDispInfoWithDateOutput = self.appDispInfoStartupOutput.appDispInfoWithDateOutput;
        let appDispInfoNoDateOutput = self.appDispInfoStartupOutput.appDispInfoNoDateOutput;
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
    public createParamB() {
        const self = this;
        self.kaf000_B_Params = null;
        let paramb = {
            input: {
                mode: self.mode ? 0 : 1,
                appDisplaySetting: self.appDispInfoStartupOutput.appDispInfoNoDateOutput.applicationSetting.appDisplaySetting,
                newModeContent: {
                    // 申請表示情報．申請表示情報(基準日関係なし)．申請設定．申請表示設定																	
                    appTypeSetting: self.appDispInfoStartupOutput.appDispInfoNoDateOutput.applicationSetting.appTypeSetting,
                    useMultiDaySwitch: false,
                    initSelectMultiDay: false
                },
                detailModeContent: null


            },
            output: {
                prePostAtr: 0,
                startDate: null,
                endDate: null
            }
        };
        if (!self.mode) {
            paramb.input.detailModeContent = {
                prePostAtr: self.appDispInfoStartupOutput.appDetailScreenInfo.application.prePostAtr,
                startDate: self.appDispInfoStartupOutput.appDetailScreenInfo.application.opAppStartDate,
                endDate: self.appDispInfoStartupOutput.appDetailScreenInfo.application.opAppEndDate,
                employeeName: _.isEmpty(self.appDispInfoStartupOutput.appDispInfoNoDateOutput.employeeInfoLst) ? '' : self.appDispInfoStartupOutput.appDispInfoNoDateOutput.employeeInfoLst[0].bussinessName
            };
        }
        self.kaf000_B_Params = paramb;
        if (self.mode) {
            self.$watch('kaf000_B_Params.output.startDate', (newV, oldV) => {
                let startDate = _.clone(self.kaf000_B_Params.output.startDate);
                let endDate = _.clone(self.kaf000_B_Params.output.endDate);
                if (_.isNull(startDate)) {

                    return;
                }
                let listDate = [];
                if (!self.kaf000_B_Params.input.newModeContent.initSelectMultiDay) {
                    listDate.push(self.$dt(newV, 'YYYY/MM/DD'));
                }

                if (!_.isNull(endDate)) {
                    let isCheckDate = startDate.getTime() <= endDate.getTime();
                    if (self.kaf000_B_Params.input.newModeContent.initSelectMultiDay && isCheckDate) {
                        while (startDate.getTime() <= endDate.getTime()) {
                            listDate.push(self.$dt(startDate, 'YYYY/MM/DD'));
                            startDate.setDate(startDate.getDate() + 1);
                        }
                    }

                }
                self.changeDate(listDate);
            });

            self.$watch('kaf000_B_Params.output.endDate', (newV, oldV) => {
                if (!self.kaf000_B_Params.input.newModeContent.initSelectMultiDay) {

                    return;
                }
                let startDate = _.clone(self.kaf000_B_Params.output.startDate);
                let endDate = _.clone(self.kaf000_B_Params.output.endDate);
                if (_.isNull(endDate)) {

                    return;
                }
                let listDate = [];
                if (!_.isNull(startDate)) {
                    let isCheckDate = startDate.getTime() <= endDate.getTime();
                    if (self.kaf000_B_Params.input.newModeContent.initSelectMultiDay && isCheckDate) {
                        while (startDate.getTime() <= endDate.getTime()) {
                            listDate.push(self.$dt(startDate, 'YYYY/MM/DD'));
                            startDate.setDate(startDate.getDate() + 1);
                        }
                    }
                }

                self.changeDate(listDate);
            });
            self.$watch('kaf000_B_Params.input.newModeContent.initSelectMultiDay', (newV, oldV) => {
                console.log(newV + ':' + oldV);
            });

        }


    }
    public createParamC() {
        const self = this;
        // KAFS00_C_起動情報
        let appDispInfoNoDateOutput = self.appDispInfoStartupOutput.appDispInfoNoDateOutput;
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
                opAppStandardReasonCD: self.mode ? '' : self.appDispInfoStartupOutput.appDetailScreenInfo.application.opAppStandardReasonCD,
                // 申請理由
                opAppReason: self.mode ? '' : self.appDispInfoStartupOutput.appDetailScreenInfo.application.opAppReason
            },
            output: {
                // 定型理由
                opAppStandardReasonCD: self.mode ? '' : self.appDispInfoStartupOutput.appDetailScreenInfo.application.opAppStandardReasonCD,
                // 申請理由
                opAppReason: self.mode ? '' : self.appDispInfoStartupOutput.appDetailScreenInfo.application.opAppReason
            }
        };
    }


    public bindStart() {
        const self = this;
        self.bindVisibleView();
        self.bindCommon(self.dataOutput);
        self.bindWork();
        self.bindDirectBounce();
        self.checkChangeWork();
    }

    public bindDirectBounce() {
        const self = this;
        if (!self.mode) {
            self.model.straight = self.dataOutput.goBackApplication.straightDistinction == 1 ? 1 : 2;
            self.model.bounce = self.dataOutput.goBackApplication.straightLine == 1 ? 1 : 2;
        }
    }
    public bindWork() {
        const self = this;
        let params = self.dataOutput;
        let goBackDirect = self.dataOutput;
        if (!goBackDirect && !self.isChangeDate) {

            return;
        }
        self.isChangeDate = false;
        let workType, workTime;
        if (goBackDirect.goBackApplication) {
            let dataWork = goBackDirect.goBackApplication.dataWork;
            if (dataWork) {
                workType = goBackDirect.goBackApplication.dataWork.workType;
                workTime = goBackDirect.goBackApplication.dataWork.workTime;
            }
        }
        self.model.workType.code = self.mode ? goBackDirect.workType : (goBackDirect.goBackApplication ? workType : null);
        let isExist = _.find(goBackDirect.lstWorkType, (item: any) => item.workTypeCode == self.model.workType.code);
        self.model.workType.name = isExist ? isExist.abbreviationName : self.$i18n('KAFS07_10');

        self.model.workTime.code = self.mode ? goBackDirect.workTime : (goBackDirect.goBackApplication ? workTime : null);
        isExist = _.find(goBackDirect.appDispInfoStartup.appDispInfoWithDateOutput.opWorkTimeLst, (item: any) => item.worktimeCode == self.model.workTime.code);
        self.model.workTime.name = isExist ? isExist.workTimeDisplayName.workTimeName : self.$i18n('KAFS07_10');
        self.bindWorkTime(goBackDirect);

    }
    public bindWorkTime(params: any) {
        const self = this;
        if (params.predetemineTimeSetting) {
            let startTime = _.find(params.predetemineTimeSetting.prescribedTimezoneSetting.lstTimezone, (item: any) => item.workNo == 1).start;
            let endTime = _.find(params.predetemineTimeSetting.prescribedTimezoneSetting.lstTimezone, (item: any) => item.workNo == 1).end;
            self.model.workTime.time = self.$dt.timedr(startTime) + self.$i18n('KAFS09_12') + self.$dt.timedr(endTime);
        }
    }


    public bindCommon(params: any) {
        this.appDispInfoStartupOutput.appDispInfoNoDateOutput = params.appDispInfoStartup.appDispInfoNoDateOutput;
        this.appDispInfoStartupOutput.appDispInfoWithDateOutput = params.appDispInfoStartup.appDispInfoWithDateOutput;
        this.appDispInfoStartupOutput.appDetailScreenInfo = params.appDispInfoStartup.appDetailScreenInfo;
    }
    public appGoBackDirect: any;
    public bindAppWorkChangeRegister() {
        const self = this;
        self.appGoBackDirect = {};
        self.appGoBackDirect.straightDistinction = self.model.straight == 2 ? 0 : 1;
        self.appGoBackDirect.straightLine = self.model.bounce == 2 ? 0 : 1;
        self.appGoBackDirect.isChangedWork = self.model.changeWork == 2 ? 0 : 1;
        if (self.C1) {
            if (self.model.changeWork == 1) {
                self.appGoBackDirect.dataWork = {
                    workType: self.model.workType.code,
                    workTime: self.model.workTime.code
                };
            }
        } else {
            self.appGoBackDirect.isChangedWork = null;
        }
        if (!self.mode) {
            self.application = self.dataOutput.appDispInfoStartup.appDetailScreenInfo.application;
        }
        if (self.mode) {
            self.application.employeeID = self.user.employeeId;
        }

        if (self.kaf000_B_Params) {
            if (self.mode) {
                self.application.appDate = self.$dt.date(self.kaf000_B_Params.output.startDate, 'YYYY/MM/DD');
                self.application.opAppStartDate = self.$dt.date(self.kaf000_B_Params.output.startDate, 'YYYY/MM/DD');
                if (self.kaf000_B_Params.input.newModeContent.initSelectMultiDay) {
                    self.application.opAppEndDate = self.$dt.date(self.kaf000_B_Params.output.endDate, 'YYYY/MM/DD');
                } else {
                    self.application.opAppEndDate = self.$dt.date(self.kaf000_B_Params.output.startDate, 'YYYY/MM/DD');
                }
            }

            self.application.prePostAtr = self.kaf000_B_Params.output.prePostAtr;

        }

        if (self.kaf000_C_Params.output) {
            self.application.opAppStandardReasonCD = self.kaf000_C_Params.output.opAppStandardReasonCD;
            self.application.opAppReason = self.kaf000_C_Params.output.opAppReason;
        }
        self.application.enteredPerson = self.user.employeeId;


    }

    public changeDate(dates: any) {
        const self = this;
        self.$mask('show');
        let params = {
            companyId: self.user.companyId,
            appDates: dates,
            employeeIds: [self.user.employeeId],
            inforGoBackCommonDirectDto: self.dataOutput
        };
        self.$http.post('at', API.updateAppWorkChange, params)
            .then((res: any) => {
                self.isChangeDate = true;
                self.dataOutput = res.data;
                self.appDispInfoStartupOutput = self.dataOutput.appDispInfoStartup;
                self.bindWork();
                let useDivision = self.appDispInfoStartupOutput.appDispInfoWithDateOutput.approvalFunctionSet.appUseSetLst[0].useDivision,
                recordDate = self.appDispInfoStartupOutput.appDispInfoNoDateOutput.applicationSetting.recordDate,
                opErrorFlag = self.appDispInfoStartupOutput.appDispInfoWithDateOutput.opErrorFlag,
                msgID = '';
                if (useDivision == 0) {
                    self.$modal.error('Msg_323').then(() => {
                        if (recordDate == 0) {
                            self.$goto('ccg008a');   
                        }
                    });
                    if (recordDate == 0) {
                        self.$mask('hide');

                        return false;
                    }
                    self.$mask('hide');

                    return true;
                }
            
                if (_.isNull(opErrorFlag)) {
                    self.$mask('hide');

                    return true;    
                }
                switch (opErrorFlag) {
                    case 1:
                        msgID = 'Msg_324';
                        break;
                    case 2: 
                        msgID = 'Msg_238';
                        break;
                    case 3:
                        msgID = 'Msg_237';
                        break;
                    default: 
                        break;
                }
                if (_.isEmpty(msgID)) { 
                    self.$mask('hide');

                    return true;
                }
                self.$modal.error({ messageId: msgID }).then(() => {
                    if (recordDate == 0) {
                        self.$goto('ccg008a');    
                    }    
                });
                self.$mask('hide');

                return false;
                
            })
            .catch((res: any) => {
                self.$mask('hide');
                self.handleErrorMessage(res).then((msgId: any) => {
                    if (res.messageId == 'Msg_426') {
                        self.$goto('ccg008a');
                    }
                });
            }); 

    }
    public registerData(res: any) {
        const self = this;
        self.$mask('show');
        if (self.mode) {
            self.$http.post('at', API.registerAppGoBackDirect, {
                companyId: self.user.companyId,
                applicationDto: self.application,
                goBackDirectlyDto: self.appGoBackDirect,
                inforGoBackCommonDirectDto: self.dataOutput,
                mode: self.mode,
            }).then((res: any) => {
                self.$mask('hide');
                // KAFS00_D_申請登録後画面に移動する
                // self.$modal('kafs00d', { mode: self.mode ? ScreenMode.NEW : ScreenMode.DETAIL, appID: res.data.appID }).then((res: any) => {
                //     self.dataOutput = res;
                //     // self.bindCommon(self.data);
                //     self.mode = false;
                //     self.fetchStart();
                //     self.$forceUpdate();
                // });
                self.$goto('kafs09a1', { mode: self.mode ? ScreenMode.NEW : ScreenMode.DETAIL, appID: res.data.appID });
            }).catch((res: any) => {
                self.handleErrorMessage(res);
            });

        } else {
            self.$http.post('at', API.updateApp, {
                applicationDto: self.application,
                goBackDirectlyDto: self.appGoBackDirect,
                inforGoBackCommonDirectDto: self.dataOutput,
            }).then((res: any) => {
                self.$mask('hide');
                // KAFS00_D_申請登録後画面に移動する
                // self.$modal('kafs00d', { mode: self.mode ? ScreenMode.NEW : ScreenMode.DETAIL, appID: res.data.appID }).then((res: any) => {
                //     self.dataOutput = res;
                //     // self.bindCommon(self.data);
                //     self.mode = false;
                //     self.fetchStart();
                //     self.$forceUpdate();
                // });
                self.$goto('kafs09a1', { mode: self.mode ? ScreenMode.NEW : ScreenMode.DETAIL, appID: res.data.appID });
            }).catch((res: any) => {
                self.handleErrorMessage(res);
            });
        }
    }
    public handleConfirmMessage(listMes: any, res: any) {

        if (!_.isEmpty(listMes)) {
            let item = listMes.shift();
            this.$modal.confirm({ messageId: item.messageId }).then((value) => {
                if (value == 'yes') {
                    if (_.isEmpty(listMes)) {
                        this.registerData(res);
                    } else {
                        this.handleConfirmMessage(listMes, res);
                    }

                }
            });
        }
    }
    public register() {
        const self = this;
        if (self.model.straight == 2 && self.model.bounce == 2) {

            return;
        }
        self.$mask('show');
        let validAll: boolean = true;
        for (let child of self.$children) {
            child.$validate();
            if (!child.$valid) {
                validAll = false;
            }
        }
        self.isValidateAll = validAll;

        // check validation 
        self.$validate();
        if (!self.$valid || !validAll) {
            window.scrollTo(500, 0);
            self.$nextTick(() => {
                self.$mask('hide');
            });

            return;
        }
        // if (self.$valid && validAll) {
        //     self.$mask('show');
        // }
        self.bindAppWorkChangeRegister();

        // check before registering application
        self.$http.post('at', API.checkBeforRegister, {
            companyId: self.user.companyId,
            agentAtr: false,
            applicationDto: self.application,
            goBackDirectlyDto: self.appGoBackDirect,
            inforGoBackCommonDirectDto: self.dataOutput,
            mode: self.mode
        }).then((res: any) => {
            self.$mask('hide');
            let isConfirm = true;
            if (!_.isEmpty(res)) {
                // display list confirm message
                if (!_.isEmpty(res.data.confirmMsgLst)) {
                    let listTemp = _.clone(res.data.confirmMsgLst);
                    self.handleConfirmMessage(listTemp, res);

                } else {
                    self.registerData(res);
                }


            }

        }).catch((res: any) => {
            self.handleErrorMessage(res);
        });

    }


    public C1: boolean = true;
    public isC1() {
        const self = this;
        let param = self.dataOutput.goBackReflect.reflectApplication;
        if (param == ApplicationStatus.DO_NOT_REFLECT || param == ApplicationStatus.DO_REFLECT) {
            return false;
        } else {
            return true;
        }
    }
    public C2: boolean = true;
    public isC2() {
        const self = this;
        let param = self.dataOutput.goBackReflect.reflectApplication;
        if (param == ApplicationStatus.DO_NOT_REFLECT) {
            return false;
        } else {
            return true;
        }
    }

    public isC3() {

    }
    public checkChangeWork() {
        const self = this;
        self.model.changeWork = self.dataOutput.goBackReflect.reflectApplication == ApplicationStatus.DO_REFLECT_1 ? 1 : 2;
        if (!self.mode) {
            if (self.isC1) {
                self.model.changeWork = self.dataOutput.goBackApplication.isChangedWork == 1 ? 1 : 2;
            }
        }
    }

    // bind visible of view 
    public bindVisibleView() {
        const self = this;
        self.C1 = self.isC1();
        self.C2 = self.isC2();
    }
    public openKDL002(name: string) {
        const self = this;
        console.log(_.map(self.appDispInfoStartupOutput.appDispInfoWithDateOutput.opWorkTimeLst, (item: any) => item.worktimeCode));
        if (name == 'worktype') {
            this.$modal(
                'worktype',
                {
                    seledtedWkTypeCDs: _.map(_.uniqBy(self.dataOutput.lstWorkType, (e: any) => e.workTypeCode), (item: any) => item.workTypeCode),
                    selectedWorkTypeCD: this.model.workType.code,
                    seledtedWkTimeCDs: _.map(self.appDispInfoStartupOutput.appDispInfoWithDateOutput.opWorkTimeLst, (item: any) => item.worktimeCode),
                    selectedWorkTimeCD: this.model.workTime.code,
                    isSelectWorkTime: 1,
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
                self.handleErrorMessage(res);
            });
        } else {
            this.$modal(
                'worktime',
                {
                    isAddNone: 0,
                    seledtedWkTimeCDs: _.map(self.appDispInfoStartupOutput.appDispInfoWithDateOutput.opWorkTimeLst, (item: any) => item.worktimeCode),
                    selectedWorkTimeCD: this.model.workTime.code,
                    isSelectWorkTime: 1
                }
            ).then((f: any) => {
                if (f) {
                    this.model.workTime.code = f.selectedWorkTime.code;
                    this.model.workTime.name = f.selectedWorkTime.name;
                    this.model.workTime.time = f.selectedWorkTime.workTime1;
                }
            }).catch((res: any) => {
                self.handleErrorMessage(res);
            });
        }




    }
    public handleErrorMessage(res: any) {
        const self = this;
        self.$mask('hide');
        if (res.messageId) {
            return self.$modal.error({ messageId: res.messageId, messageParams: res.parameterIds });
        } else {
            
            if (_.isArray(res.errors)) {
                return self.$modal.error({ messageId: res.errors[0].messageId, messageParams: res.parameterIds});
            } else {
                return self.$modal.error({ messageId: res.errors.messageId, messageParams: res.parameterIds });
            }
        }
    }

    public dataFetch() {
        this.$http.post('at', API.startS09, {
            companyId: this.user.companyId,
            employeeId: this.user.employeeId,
            dates: [],
            mode: true,
            inforGoBackCommonDirectDto: null,
            appDispInfoStartupDto: this.appDispInfoStartupOutput
        }).then((res: any) => {
            this.dataOutput = res.data;
            this.bindStart();
        }).catch((err: any) => {
            if (err.messageId) {
                this.$modal.error({ messageId: err.messageId });
            } else {

                if (_.isArray(err.errors)) {
                    this.$modal.error({ messageId: err.errors[0].messageId });
                } else {
                    this.$modal.error({ messageId: err.errors.messageId });
                }
            }
        });
    }

    public kaf000BChangeDate(objectDate) {
        console.log('emit' + objectDate);
    }
    
    public kaf000BChangePrePost(prePostAtr) {
        console.log('emit' + prePostAtr);
    }

    public kaf000CChangeReasonCD(opAppStandardReasonCD) {
        console.log('emit' + opAppStandardReasonCD);
    }

    public kaf000CChangeAppReason(opAppReason) {
        console.log('emit' + opAppReason);
    }

}
export class Work {
    public code: String = '';
    public name: String = '';
    constructor() {

    }

}
export class WorkTime extends Work {
    public time: String = '';
    constructor() {
        super();
    }
}

export class Model {

    public workType: Work = new Work();

    public workTime: WorkTime = new WorkTime();

    public straight: number = 1;

    public bounce: number = 1;

    public changeWork: number = 1;

    constructor() {

    }
}

// 画面モード
export enum ScreenMode {
    // 新規モード
    NEW = 0,
    // 詳細モード
    DETAIL = 1
}
export enum ApplicationStatus {
    // 反映しない
    DO_NOT_REFLECT = 0,
    // 反映する
    DO_REFLECT = 1,
    // 申請時に決める(初期値：反映しない)
    DO_NOT_REFLECT_1 = 2,
    // 申請時に決める(初期値：反映する)
    DO_REFLECT_1 = 3
}

const API = {
    checkBeforRegister: 'at/request/application/gobackdirectly/checkBeforeRegisterNew',
    registerAppGoBackDirect: 'at/request/application/gobackdirectly/registerNewKAF009',
    updateAppWorkChange: 'at/request/application/gobackdirectly/mobile/getAppDataByDate',
    startS09: 'at/request/application/gobackdirectly/mobile/start',
    updateApp: 'at/request/application/gobackdirectly/updateNewKAF009'


};
