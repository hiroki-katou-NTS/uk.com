import { BreakTime, TimeZoneNew, TimeZoneWithWorkNo, AppOverTime, ParamCalculateMobile, ParamSelectWorkMobile, InfoWithDateApplication, ParamStartMobile, OvertimeAppAtr, Model, DisplayInfoOverTime, NotUseAtr, ApplicationTime, OvertimeApplicationSetting, AttendanceType, HolidayMidNightTime, StaturoryAtrOfHolidayWork, ParamBreakTime, WorkInformation, WorkHoursDto} from '../a/define.interface';
import { _, Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { StepwizardComponent } from '@app/components';
import { KafS05Step1Component } from '../step1';
import { KafS05Step2Component } from '../step2';
import { KafS05Step3Component } from '../step3';
import { KDL002Component } from '../../../kdl/002';
import { Kdl001Component } from '../../../kdl/001';
import { KafS00ShrComponent, AppType, Application, InitParam } from 'views/kaf/s00/shr';

@component({
    name: 'kafs05',
    route: '/kaf/s05/a',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: [],
    components: {
        'step-wizard': StepwizardComponent,
        'kafS05Step1Component': KafS05Step1Component,
        'kafS05Step2Component': KafS05Step2Component,
        'kafS05Step3Component': KafS05Step3Component,
        'worktype': KDL002Component,
        'worktime': Kdl001Component,
    }

})
export class KafS05Component extends KafS00ShrComponent {

    private numb: number = 1;
    public text1: string = null;
    public text2: string = 'step2';
    public isValidateAll: boolean = true;
    public user: any = null;
    public modeNew: boolean = true;
    public application: Application = null;

    public model: Model = {} as Model;

    public date: string;

    public overTimeClf: number;

    @Prop() 
    public readonly params: InitParam;

    public get step() {
        return `step_${this.numb}`;
    }

    // 「残業申請の表示情報．基準日に関係しない情報．残業申請設定．残業休出申請共通設定．時間外表示区分」＝する
    public get c1() {
        const self = this;
        let displayOverTime = self.model.displayInfoOverTime as DisplayInfoOverTime;
        let value = _.get(displayOverTime, 'infoNoBaseDate.overTimeAppSet.overtimeLeaveAppCommonSetting.extratimeDisplayAtr');

        return value == NotUseAtr.USE;
    }
    // 「残業申請の表示情報．基準日に関係しない情報．残業申請設定．申請詳細設定．時刻計算利用区分」＝する
    public get c3() {
        const self = this;
        let displayOverTime = self.model.displayInfoOverTime as DisplayInfoOverTime;
        let value = _.get(displayOverTime, 'infoNoBaseDate.overTimeAppSet.applicationDetailSetting.timeCalUse');

        return value == NotUseAtr.USE;
    }
    // ※表3 = ○　OR　※表3-1-1 = ○
    public get c3_1() {
        const self = this;

        return self.c3_1_1 || self.c3;
    }
    // ※表15 = × AND「残業申請の表示情報．基準日に関係しない情報．残業休日出勤申請の反映．残業申請．事前．休憩・外出を申請反映する」＝する
    // ※表15 = ○ AND「残業申請の表示情報．基準日に関係しない情報．残業休日出勤申請の反映．残業申請．事後．休憩・外出を申請反映する」= する
    public get c3_1_1() {
        const self = this;
        let displayOverTime = self.model.displayInfoOverTime as DisplayInfoOverTime;
        let value1 = _.get(displayOverTime, 'infoNoBaseDate.overTimeReflect.overtimeWorkAppReflect.reflectBeforeBreak');
        let value2 = _.get(displayOverTime, 'infoNoBaseDate.overTimeReflect.overtimeWorkAppReflect.reflectBreakOuting');
        
        return (value1 == NotUseAtr.USE || value2 == NotUseAtr.USE);
    }
    //  c3 AND「残業申請の表示情報．申請表示情報．申請表示情報(基準日関係なし)．複数回勤務の管理」＝true"
    public get c3_2() {
        const self = this;
        let value = _.get(self.model.displayInfoOverTime, 'appDispInfoStartup.appDispInfoNoDateOutput.managementMultipleWorkCycles');
        
        return value;
    }



    // 「残業申請の表示情報．基準日に関する情報．残業申請で利用する残業枠．残業枠一覧」 <> empty
    public get c4() {
        const self = this;
        let displayOverTime = self.model.displayInfoOverTime as DisplayInfoOverTime;
        let value = _.get(displayOverTime, 'infoBaseDateOutput.quotaOutput.overTimeQuotaList');

        return !_.isEmpty(value);
    }
    // 「残業申請の表示情報．基準日に関係しない情報．残業休日出勤申請の反映．時間外深夜時間を反映する」= する
    public get c5() {
        const self = this;
        let displayOverTime = self.model.displayInfoOverTime as DisplayInfoOverTime;
        let value = _.get(displayOverTime, 'infoNoBaseDate.overTimeReflect.nightOvertimeReflectAtr');

        return value == NotUseAtr.USE; 
    }
    // 「残業申請の表示情報．基準日に関する情報．残業申請で利用する残業枠．フレックス時間表示区分」= true
    public get c6() {
        const self = this;
        let displayOverTime = self.model.displayInfoOverTime as DisplayInfoOverTime;
        let value = _.get(displayOverTime, 'infoBaseDateOutput.quotaOutput.flexTimeClf');

        return value;
    }
    // 「残業申請の表示情報．基準日に関係しない情報．利用する乖離理由．NO = 1」 <> empty　AND 乖離理由を選択肢から選ぶ = true
    public get c13() {
        const self = this;
        let displayOverTime = self.model.displayInfoOverTime as DisplayInfoOverTime;
        let findResult = _.find(displayOverTime.infoNoBaseDate.divergenceReasonInputMethod, { divergenceTimeNo: 1 });
        let c13_1 = !_.isNil(findResult);
        let c13_2 = c13_1 ? findResult.divergenceReasonSelected : false;
        
        return c13_1 && c13_2;
    }
    // 「残業申請の表示情報．基準日に関係しない情報．利用する乖離理由．NO = 1」 <> empty　AND　乖離理由を入力する = true
    public get c14() {
        const self = this;
        let displayOverTime = self.model.displayInfoOverTime as DisplayInfoOverTime;
        let findResult = _.find(displayOverTime.infoNoBaseDate.divergenceReasonInputMethod, { divergenceTimeNo: 1 });
        let c14_1 = !_.isNil(findResult);
        let c14_2 = c14_1 ? findResult.divergenceReasonInputed : false;

        return c14_1 && c14_2;
    }
    // （「事前事後区分」が表示する　AND　「事前事後区分」が「事後」を選択している）　OR
    // （「事前事後区分」が表示しない　AND 「残業申請の表示情報．申請表示情報．申請表示情報(基準日関係あり)．事前事後区分」= 「事後」）
    public get c15() {


        return true;
    }
    // ※表16-1 = ○　OR　※表16-2 = ○　OR　※表16-3 = ○　OR　※表16-4 = ○
    public get c16() {
        
        return true;
    }
    // 「残業申請の表示情報．計算結果．申請時間．申請時間．type」= 休出時間 があるの場合
    public get c16_1() {
        const self = this;
        let displayOverTime = self.model.displayInfoOverTime as DisplayInfoOverTime;
        if (!_.isNil(displayOverTime.calculationResultOp)) {
            _.forEach(displayOverTime.calculationResultOp.applicationTimes, (i: ApplicationTime) => {
                _.forEach(i.applicationTime, (item: OvertimeApplicationSetting) => {
                    if (item.attendanceType == AttendanceType.BREAKTIME) {

                        return true;
                    }
                });
            });
        }

        return false;
    }
    // ※表15 = ○　　AND　※表16-1 = ○　
    public get c16_1_2() {

        return true;
    }

    // ※表5 = ○ AND「残業申請の表示情報．計算結果．申請時間．就業時間外深夜時間．休出深夜時間．法定区分」= 法定内休出 があるの場合
    public get c16_2() {
        const self = this;
        let displayOverTime = self.model.displayInfoOverTime as DisplayInfoOverTime;
        let midNightHolidayTimes = _.get(displayOverTime, 'calculationResultOp.applicationTimes[0].overTimeShiftNight.midNightHolidayTimes');
        _.forEach(midNightHolidayTimes, (item: HolidayMidNightTime) => {
            if (item.legalClf == StaturoryAtrOfHolidayWork.WithinPrescribedHolidayWork) {

                return true && self.c5;
            }
        });

        return false;
    }
    // ※表15 = ○　　AND　※表16-2 = ○　
    public get c16_2_2() {
        const self = this;

        return self.c15 && self.c16_2;
    }
    // ※表5 = ○ AND「残業申請の表示情報．計算結果．申請時間．就業時間外深夜時間．休出深夜時間．法定区分」= 法定外休出 があるの場合
    public get c16_3() {
        const self = this;
        let displayOverTime = self.model.displayInfoOverTime as DisplayInfoOverTime;
        let midNightHolidayTimes = _.get(displayOverTime, 'calculationResultOp.applicationTimes[0].overTimeShiftNight.midNightHolidayTimes');
        _.forEach(midNightHolidayTimes, (item: HolidayMidNightTime) => {
            if (item.legalClf == StaturoryAtrOfHolidayWork.ExcessOfStatutoryHolidayWork) {

                return true && self.c5;
            }
        });

        return false;
    }
    // ※表15 = ○　　AND　※表16-3 = ○　
    public get c16_3_2() {
        const self = this;

        return self.c15 && self.c16_3;
    }

    // ※表5 = ○ AND「残業申請の表示情報．計算結果．申請時間．就業時間外深夜時間．休出深夜時間．法定区分」= 祝日休出 があるの場合
    public get c16_4() {
        const self = this;
        let displayOverTime = self.model.displayInfoOverTime as DisplayInfoOverTime;
        let midNightHolidayTimes = _.get(displayOverTime, 'calculationResultOp.applicationTimes[0].overTimeShiftNight.midNightHolidayTimes');
        _.forEach(midNightHolidayTimes, (item: HolidayMidNightTime) => {
            if (item.legalClf == StaturoryAtrOfHolidayWork.PublicHolidayWork) {

                return true && self.c5;
            }
        });

        return false;
    }
    // ※表15 = ○　　AND　※表16-4 = ○　
    public get c16_4_2() {
        const self = this;

        return self.c5 && self.c16_4;
    }
    // ※表20＝○ OR ※表21＝○
    public get c19() {
        const self = this;
        
        return self.c20 || self.c21;
    }
    // 「残業申請の表示情報．基準日に関係しない情報．利用する乖離理由．NO = 2」 <> empty　AND 乖離理由を選択肢から選ぶ = true
    public get c20() {
        const self = this;
        let displayOverTime = self.model.displayInfoOverTime as DisplayInfoOverTime;
        let findResult = _.find(displayOverTime.infoNoBaseDate.divergenceReasonInputMethod, { divergenceTimeNo: 2 });
        let c20_1 = !_.isNil(findResult);
        let c20_2 = c20_1 ? findResult.divergenceReasonSelected : false;
        
        return c20_1 && c20_2;
    }
    // 「残業申請の表示情報．基準日に関係しない情報．利用する乖離理由．NO = 2」 <> empty　AND　乖離理由を入力する = true
    public get c21() {
        const self = this;
        let displayOverTime = self.model.displayInfoOverTime as DisplayInfoOverTime;
        let findResult = _.find(displayOverTime.infoNoBaseDate.divergenceReasonInputMethod, { divergenceTimeNo: 2 });
        let c21_1 = !_.isNil(findResult);
        let c21_2 = c21_1 ? findResult.divergenceReasonInputed : false;

        return c21_1 && c21_2;
    }




    public created() {
        const vm = this;
        
        if (vm.$route.query.a == '0') {
            vm.overTimeClf = 0;
        } else if (vm.$route.query.a == '1') {
            vm.overTimeClf = 1;
        } else {
            vm.overTimeClf = 2;
        }
        if (vm.params) {
            vm.modeNew = false;
            vm.appDispInfoStartupOutput = vm.params.appDispInfoStartupOutput;
        }
        if (vm.modeNew) {
            vm.application = vm.createApplicationInsert(AppType.OVER_TIME_APPLICATION);
        } else {
            vm.application = vm.createApplicationUpdate(vm.params.appDispInfoStartupOutput.appDetailScreenInfo);
        }
        vm.$auth.user.then((user: any) => {
            vm.user = user;
        }).then(() => {
            if (vm.modeNew) {
                return vm.loadCommonSetting(AppType.OVER_TIME_APPLICATION);
            }
            
            return true;
        }).then((loadData: any) => {
            if (loadData) {
                vm.updateKaf000_A_Params(vm.user);
                vm.updateKaf000_B_Params(vm.modeNew);
                vm.updateKaf000_C_Params(vm.modeNew);
                let command = {} as ParamStartMobile;
                let url = self.location.search.split('=')[1];
                command.mode = vm.modeNew;
                command.companyId = vm.user.companyId;
                command.employeeIdOptional = vm.user.employeeId;
                if (url == String(OvertimeAppAtr.EARLY_OVERTIME)) {
                    command.overtimeAppAtr = OvertimeAppAtr.EARLY_OVERTIME;
                } else if (url == String(OvertimeAppAtr.NORMAL_OVERTIME)) {
                    command.overtimeAppAtr = OvertimeAppAtr.NORMAL_OVERTIME;
                } else {
                    command.overtimeAppAtr = OvertimeAppAtr.EARLY_NORMAL_OVERTIME;
                }
                command.appDispInfoStartupOutput = vm.appDispInfoStartupOutput;

                if (vm.modeNew) {
                    return vm.$http.post('at', API.start, command);  
                }
                
                return true;
            }
        }).then((result: any) => {
            if (result) {
                if (vm.modeNew) {
                    vm.model = {} as Model;
                    vm.model.displayInfoOverTime = result.data.displayInfoOverTime;

                    let step1 = vm.$refs.step1 as KafS05Step1Component;
                    step1.loadData(vm.model.displayInfoOverTime);
               
                } else {

                }   
            }
        }).catch((error: any) => {
            vm.handleErrorCustom(error).then((result) => {
                if (result) {
                    vm.handleErrorCommon(error);
                }
            });
        }).then(() => vm.$mask('hide'));
    }

   

    public changeDate(date: string) {
        const self = this;
        self.$mask('show');
        let command = {
            companyId: self.user.companyId,
            date,
            displayInfoOverTime: self.model.displayInfoOverTime
        };
        self.$http.post('at',
            API.changeDate,
            command
            )
            .then((res: any) => {
                self.model.displayInfoOverTime = res.data;
                let step1 = self.$refs.step1 as KafS05Step1Component;
                step1.loadData(self.model.displayInfoOverTime);
                self.$mask('hide');
            })
            .catch((res: any) => {
                self.$mask('hide');
            });
    }
    
    public kaf000BChangeDate(objectDate) {
        const self = this;
        console.log('emit' + objectDate);
        if (objectDate.startDate) {
            if (self.modeNew) {
                self.application.appDate = self.$dt.date(objectDate.startDate, 'YYYY/MM/DD');
                self.application.opAppStartDate = self.$dt.date(objectDate.startDate, 'YYYY/MM/DD');
                self.application.opAppEndDate = self.$dt.date(objectDate.endDate, 'YYYY/MM/DD');
                self.date = objectDate.startDate;
            }
            self.changeDate(objectDate.startDate);
        }
    }
    
    public kaf000BChangePrePost(prePostAtr) {
        const self = this;
        console.log('emit' + prePostAtr);
        self.application.prePostAtr = prePostAtr;
    }

    public kaf000CChangeReasonCD(opAppStandardReasonCD) {
        const self = this;
        console.log('emit' + opAppStandardReasonCD);
        self.application.opAppStandardReasonCD = opAppStandardReasonCD;
        
    }

    public kaf000CChangeAppReason(opAppReason) {
        const self = this;
        console.log('emit' + opAppReason);
        self.application.opAppReason = opAppReason;
    }
    public toAppOverTime() {
        const self = this;
        let step1 = self.$refs.step1 as KafS05Step1Component;
        let appOverTimeInsert = {} as AppOverTime;
        if (step1) {
            if (self.model.displayInfoOverTime.appDispInfoStartup.appDispInfoNoDateOutput.applicationSetting.appDisplaySetting.prePostDisplayAtr != 1) {
                self.application.prePostAtr = self.model.displayInfoOverTime.appDispInfoStartup.appDispInfoWithDateOutput.prePostAtr;
            }
            appOverTimeInsert.application = self.application as any;
            appOverTimeInsert.overTimeClf = self.overTimeClf;
            if (step1.getWorkType()) {
                appOverTimeInsert.workInfoOp = {} as WorkInformation;
                appOverTimeInsert.workInfoOp.workType = step1.getWorkType();
                appOverTimeInsert.workInfoOp.workTime = step1.getWorkTime();
            }
            appOverTimeInsert.workHoursOp = [] as Array<TimeZoneWithWorkNo>;
            {
                let timeZone = {} as TimeZoneWithWorkNo;
                timeZone.workNo = 1;
                timeZone.timeZone = {} as TimeZoneNew;
                timeZone.timeZone.startTime = step1.getWorkHours1().start;
                timeZone.timeZone.endTime = step1.getWorkHours1().end;
                appOverTimeInsert.workHoursOp.push(timeZone);
            }
            appOverTimeInsert.breakTimeOp = [] as Array<TimeZoneWithWorkNo>;
            let breakTimes = step1.getBreakTimes() as Array<BreakTime>;
            _.forEach(breakTimes, (item: BreakTime, index: number) => {
                let start = _.get(item,'valueHours.start');
                let end = _.get(item,'valueHours.end');
                if (!_.isNil(start) && !_.isNil(end)) {
                    let timeZone = {} as TimeZoneWithWorkNo;
                    timeZone.workNo = index + 1;
                    timeZone.timeZone = {} as TimeZoneNew;
                    timeZone.timeZone.startTime = 
                    timeZone.timeZone.endTime = step1.getWorkHours1().end;
                    appOverTimeInsert.breakTimeOp.push(timeZone);
                }
            });

        }


        return appOverTimeInsert;
    }

    public toStep(value: number) {
        const vm = this;
        // step 1 -> step 2
        if (vm.numb == 1 && value == 2) {
            vm.$mask('show');
            let step1 = vm.$refs.step1 as KafS05Step1Component;
            vm.isValidateAll = vm.customValidate(step1);
            vm.$validate();
            if (!vm.$valid || !vm.isValidateAll) {
                window.scrollTo(500, 0);
                vm.$nextTick(() => {
                    vm.$mask('hide');
                });

                return;
            }
            let command = {
    
            } as ParamCalculateMobile;
            vm.model.appOverTime = vm.toAppOverTime();
            command.companyId = vm.user.companyId;
            command.employeeId = vm.user.employeeId;
            command.mode = vm.modeNew;
            command.displayInfoOverTime = vm.model.displayInfoOverTime;
            command.appOverTimeInsert = vm.model.appOverTime;
            command.dateOp = command.appOverTimeInsert.application.appDate;
            vm.$http.post(
                'at', 
                API.calculate,
                command
                )
                    .then((res: any) => {
                        vm.numb = value;
    
                        vm.$nextTick(() => {
                            vm.$mask('hide');
                        });
                    })
                    .catch((res: any) => {
                        vm.$nextTick(() => {
                            vm.$mask('hide');
                        });
                        // xử lý lỗi nghiệp vụ riêng
                        vm.handleErrorCustom(res).then((result: any) => {
                            if (result) {
                                // xử lý lỗi nghiệp vụ chung
                                vm.handleErrorCommon(res);
                            }
                        });
                    });
        } else if (vm.numb == 2 && value == 1) { // step 2 -> step 1
            vm.numb  = value;
            // vm.$mask('show');
            // vm.$nextTick(() => {
            //     let step1 = vm.$refs.step1 as KafS05Step1Component;
            //     step1.loadDataFromStep2();
            //     vm.$mask('hide');
            // });

        } else {
            vm.numb  = value;
        }

    }

    public customValidate(viewModel: any) {
        const vm = this;
        let validAllChild = true;
        for (let child of viewModel.$children) {
            let validChild = true;
            if (child.$children) {
                validChild = vm.customValidate(child); 
            }
            child.$validate();
            if (!child.$valid || !validChild) {
                validAllChild = false;
            }
        }

        return validAllChild;
    }

    public register() {
        const vm = this;
        vm.$mask('show');
        let step2 = vm.$refs.step2 as KafS05Step2Component;
        vm.isValidateAll = vm.customValidate(step2);
        vm.$validate();
        if (!vm.$valid || !vm.isValidateAll) {
            window.scrollTo(500, 0);
            vm.$nextTick(() => vm.$mask('hide'));

            return;
        }
        vm.$http.post('at', API.checkBeforeRegisterSample, ['Msg_260', 'Msg_261'])
        .then((result: any) => {
            if (result) {
                // xử lý confirmMsg
                return vm.handleConfirmMessage(result.data);
            }
        }).then((result: any) => {
            if (result) {
                // đăng kí 
                return vm.$http.post('at', API.registerSample, ['Msg_15']).then(() => {
                    return vm.$modal.info({ messageId: 'Msg_15'}).then(() => {
                        return true;
                    });	
                });
            }
        }).then((result: any) => {
            if (result) {
                // gửi mail sau khi đăng kí
                // return vm.$ajax('at', API.sendMailAfterRegisterSample);
                return true;
            }
        }).catch((failData) => {
            // xử lý lỗi nghiệp vụ riêng
            vm.handleErrorCustom(failData).then((result: any) => {
                if (result) {
                    // xử lý lỗi nghiệp vụ chung
                    vm.handleErrorCommon(failData);
                }
            });
        }).then(() => {
            vm.$nextTick(() => vm.$mask('hide'));  	
        });
    }
    
    public handleErrorCustom(failData: any): any {
        const vm = this;

        return new Promise((resolve) => {
            if (failData.messageId == 'Msg_26') {
                vm.$modal.error({ messageId: failData.messageId, messageParams: failData.parameterIds })
                .then(() => {
                    vm.$goto('ccg008a');
                });

                return resolve(false);		
            }

            return resolve(true);
        });
    }

    public handleConfirmMessage(listMes: any): any {
        const vm = this;

        return new Promise((resolve) => {
            if (_.isEmpty(listMes)) {
                return resolve(true);
            }
            let msg = listMes[0];
    
            return vm.$modal.confirm({ messageId: msg.msgID, messageParams: msg.paramLst })
            .then((value) => {
                if (value === 'yes') {
                    return vm.handleConfirmMessage(_.drop(listMes)).then((result) => {
                        if (result) {
                            return resolve(true);    
                        }

                        return resolve(false);
                    });
                }
                
                return resolve(false);
            });
        });
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

    public openKDL002(name: string) {
        const self = this;
        let step1 = self.$refs.step1 as KafS05Step1Component;
        if (name == 'worktype') {
            self.$modal('worktype', {

                seledtedWkTypeCDs: _.map(_.uniqBy(self.model.displayInfoOverTime.infoBaseDateOutput.worktypes, (e: any) => e.workTypeCode), (item: any) => item.workTypeCode),
                selectedWorkTypeCD: step1.getWorkType(),
                seledtedWkTimeCDs: _.map(self.model.displayInfoOverTime.appDispInfoStartup.appDispInfoWithDateOutput.opWorkTimeLst, (item: any) => item.worktimeCode),
                selectedWorkTimeCD: step1.getWorkTime(),
                isSelectWorkTime: 1,
            })
            .then((f: any) => {
                let workTypeCode;
                let workTimeCode;
                workTypeCode = f.selectedWorkType.workTypeCode;
                workTimeCode = f.selectedWorkTime.code;
                step1.setWorkCode(
                    workTypeCode,
                    f.selectedWorkType.name,
                    workTimeCode,
                    f.selectedWorkTime.name,
                    f.selectedWorkTime.workTime1,
                    self.model.displayInfoOverTime
                );
                let command = {

                } as ParamSelectWorkMobile;
                command.companyId = self.user.companyId;
                command.employeeId = self.user.employeeId;
                command.dateOp = self.date;
                command.workTypeCode = step1.workInfo.workType.code;
                command.workTimeCode = step1.workInfo.workTime.code;
                if (!_.isNil(step1.workHours1)) {
                    command.startTimeSPR = step1.workHours1.start;
                    command.endTimeSPR = step1.workHours1.end;
                }
                command.actualContentDisplay = _.get(self.model.displayInfoOverTime, 'appDispInfoStartup.appDispInfoWithDateOutput.opActualContentDisplayLst[0]');
                command.overtimeAppSet = self.model.displayInfoOverTime.infoNoBaseDate.overTimeAppSet;
                self.$mask('show');

                return self.$http.post(
                    'at',
                    API.selectWorkInfo,
                    command
                         );

            })
            .then((res: any) => {
                let step1 = self.$refs.step1 as KafS05Step1Component;
                // call API select work info
                let infoWithDateApplicationOp = _.get(self.model.displayInfoOverTime, 'infoWithDateApplicationOp') as InfoWithDateApplication;
                if (!_.isNil(infoWithDateApplicationOp)) {
                    infoWithDateApplicationOp.breakTime = res.data.breakTimeZoneSetting;
                    infoWithDateApplicationOp.applicationTime = res.data.applicationTime;
                    infoWithDateApplicationOp.workHours = res.data.workHours;
                    infoWithDateApplicationOp.workTypeCD = step1.workInfo.workType.code;
                    infoWithDateApplicationOp.workTimeCD = step1.workInfo.workTime.code;
                } else {
                    infoWithDateApplicationOp = {} as InfoWithDateApplication;
                    infoWithDateApplicationOp.applicationTime = res.data.applicationTime;
                    infoWithDateApplicationOp.workHours = res.data.workHours;
                    infoWithDateApplicationOp.breakTime = res.data.breakTimeZoneSetting;
                    infoWithDateApplicationOp.workTypeCD = step1.workInfo.workType.code;
                    infoWithDateApplicationOp.workTimeCD = step1.workInfo.workTime.code;
                }
                step1.loadData(self.model.displayInfoOverTime);
                self.$mask('hide');

            })
            .catch((res: any) => {
                self.handleErrorMessage(res);
            });
        } else {
            self.$modal(
                'worktime',
                {
                    isAddNone: 0,
                    seledtedWkTimeCDs: _.map(self.model.displayInfoOverTime.appDispInfoStartup.appDispInfoWithDateOutput.opWorkTimeLst, (item: any) => item.worktimeCode),
                    selectedWorkTimeCD: step1.getWorkTime(),
                    isSelectWorkTime: 1
                }
            ).then((f: any) => {
                if (f) {
                    step1.setWorkTime(
                        f.selectedWorkTime.code,
                        f.selectedWorkTime.name,
                        f.selectedWorkTime.workTime1,
                    );

                    let command = {

                    } as ParamSelectWorkMobile;
                    command.companyId = self.user.companyId;
                    command.employeeId = self.user.employeeId;
                    command.dateOp = self.date;
                    command.workTypeCode = step1.workInfo.workType.code;
                    command.workTimeCode = step1.workInfo.workTime.code;
                    if (!_.isNil(step1.workHours1)) {
                        command.startTimeSPR = step1.workHours1.start;
                        command.endTimeSPR = step1.workHours1.end;
                    }
                    command.actualContentDisplay = _.get(self.model.displayInfoOverTime, 'appDispInfoStartup.appDispInfoWithDateOutput.opActualContentDisplayLst[0]');
                    command.overtimeAppSet = self.model.displayInfoOverTime.infoNoBaseDate.overTimeAppSet;
                    self.$mask('show');
    
                    return self.$http.post(
                        'at',
                        API.selectWorkInfo,
                        command
                             );
                }
            })
            .then((res: any) => {
                // call API select work info
                let step1 = self.$refs.step1 as KafS05Step1Component;
                let infoWithDateApplicationOp = _.get(self.model.displayInfoOverTime, 'infoWithDateApplicationOp') as InfoWithDateApplication;
                if (!_.isNil(infoWithDateApplicationOp)) {
                    infoWithDateApplicationOp.breakTime = res.data.breakTimeZoneSetting;
                    infoWithDateApplicationOp.applicationTime = res.data.applicationTime;
                    infoWithDateApplicationOp.workHours = res.data.workHours;
                    infoWithDateApplicationOp.workTypeCD = step1.workInfo.workType.code;
                    infoWithDateApplicationOp.workTimeCD = step1.workInfo.workTime.code;
                } else {
                    infoWithDateApplicationOp = {} as InfoWithDateApplication;
                    infoWithDateApplicationOp.applicationTime = res.data.applicationTime;
                    infoWithDateApplicationOp.workHours = res.data.workHours;
                    infoWithDateApplicationOp.breakTime = res.data.breakTimeZoneSetting;
                    infoWithDateApplicationOp.workTypeCD = step1.workInfo.workType.code;
                    infoWithDateApplicationOp.workTimeCD = step1.workInfo.workTime.code;
                }
                step1.loadData(self.model.displayInfoOverTime);
                self.$mask('hide');

            })
            .catch((res: any) => {
                self.handleErrorMessage(res);
            });
        }
        
    }

    public getBreakTime(command: ParamBreakTime) {
        const self = this;
        console.log('getBreakTime');
        self.$mask('show');
        self.$http.post(
            'at',
            API.getBreakTime,
            command
            )
            .then((res: any) => {
                let infoWithDateApplicationOp = _.get(self.model.displayInfoOverTime, 'infoWithDateApplicationOp') as InfoWithDateApplication;
                if (!_.isNil(infoWithDateApplicationOp)) {
                    infoWithDateApplicationOp.breakTime.timeZones = res.data.timeZones;
                } else {
                    infoWithDateApplicationOp = {} as InfoWithDateApplication;
                    infoWithDateApplicationOp.breakTime = {} as any;
                    infoWithDateApplicationOp.breakTime.timeZones = res.data.timeZones;
                }
                let step1 = self.$refs.step1 as KafS05Step1Component;
                if (!_.isNil(step1)) {
                    step1.loadBreakTime(res.data.timeZones);
                }   

                self.$mask('hide');
            })
            .catch((res: any) => {
                self.handleErrorMessage(res);
            });
    }




}
const API = {
    start: 'at/request/application/overtime/mobile/start',
    changeDate: 'at/request/application/overtime/mobile/changeDate',
    getBreakTime: 'at/request/application/overtime/mobile/breakTimes',
    selectWorkInfo: 'at/request/application/overtime/mobile/selectWorkInfo',
    calculate: 'at/request/application/overtime/mobile/calculate',

    checkBeforeRegisterSample: 'at/request/application/checkBeforeSample',
    registerSample: 'at/request/application/changeDataSample',
    sendMailAfterRegisterSample: ''
};

