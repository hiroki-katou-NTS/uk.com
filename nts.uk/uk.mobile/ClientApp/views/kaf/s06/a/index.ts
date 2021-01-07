import { _, Vue } from '@app/provider';
import { component, Prop, Watch } from '@app/core/component';
import { KafS00AComponent, KafS00BComponent, KafS00CComponent } from 'views/kaf/s00';
import { KafS00ShrComponent, AppType, Application, InitParam } from 'views/kaf/s00/shr';
import { WorkTypeDto, MaxNumberDayType, AppAbsenceStartInfoDto, StartMobileParam, NotUseAtr, TimeZoneUseDto, HolidayAppTypeDispNameDto, ManageDistinct, TargetWorkTypeByApp, ApplicationType, HolidayAppType, DateSpecHdRelationOutput } from '../a/define.interface';

@component({
    name: 'kafs06a',
    route: '/kaf/s06/a',
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
export class KafS06AComponent extends KafS00ShrComponent {
    public text1: string = null;
    public isValidateAll: boolean = true;
    public user: any = null;
    public modeNew: boolean = true;
    public application: Application = null;
    public workHours1: {start: number, end: number} = null;
    public workHours2: {start: number, end: number} = null;

    public selectedValue = null;
    @Watch('selectedValue', {deep: true})
    public selectHolidayType(data: any) {
        const self = this;
        self.selectedHolidayType(data);
    }
    public selectedRelationship = null;
    public time: number = 0;
    public checkBoxC7: false;
    public text: null;
    public model: Model = {} as Model;
    public dropdownList = [
        {
            code: null,
            text: 'Select ---'
        },
        {
            code: '01',
            text: 'abcd'
        }
    ];

    public dropdownRelationship = [
        {
            code: null,
            text: 'Select ---'
        },
        {
            code: '01',
            text: 'abcd'
        }
    ];
    public workType = {
        code: null,
        name: '',
        time: ''
    } as WorkInfo;
    public workTime = {
        code: null,
        name: '',
        time: ''
    } as WorkInfo;


    @Prop() 
    public readonly params: InitParam;
    @Watch('c9') 
    public updateValidate(data: boolean) {
        const self = this;
        if (data) {
            self.$updateValidator('workHours1', {
                required: true,
                timeRange: true
            });
        }
    }
    public get A9_2() {
        const self = this;
        let model = self.model as Model;
        let time = _.get(model, 'appAbsenceStartInfoDto.requiredVacationTime') || 0;

        return self.$dt.timewd(time);
    }

    public get A9_3() {
        const self = this;
        let model = self.model as Model;
        let time = 0;

        return self.$dt.timewd(time);
    }
    // 【補足資料1】を参照する todo
    // 休暇残数情報．60H超休残時間
    public get A9_5() {
        const self = this;
        let model = self.model as Model;
        let time = _.get(model, 'appAbsenceStartInfoDto.remainVacationInfo.over60HHourRemain') || 0;

        return self.$dt.timewd(time);
    }
    // 休暇残数情報．代休残時間
    public get A9_7() {
        const self = this;
        let model = self.model as Model;
        let time = _.get(model, 'appAbsenceStartInfoDto.remainVacationInfo.subVacaHourRemain') || 0;

        return self.$dt.timewd(time);
    }
    // 休暇残数情報．年休残数
    // 休暇残数情報．年休残時間
    public get A9_9() {
        const self = this;
        let model = self.model as Model;
        let time = 0;

        return self.$dt.timewd(time);
    }
    // 休暇残数情報．子看護残数
    // 休暇残数情報．子看護残時間
    public get A9_11() {
        const self = this;
        let model = self.model as Model;
        let time = 0;

        return self.$dt.timewd(time);
    }
    // 休暇残数情報．介護残数
    // 休暇残数情報．介護残時間
    public get A9_13() {
        const self = this;
        let model = self.model as Model;
        let time = 0;

        return self.$dt.timewd(time);
    }

    public get HolidayAppType() {

        return HolidayAppType;
    }
    // ※2 = ○　OR　※3 = ○　OR　※4 = ○　OR　※5 = ○　
    public get c1() {
        const self = this;

        return self.c2 || self.c3 || self.c4 || self.c5;
    }
    // 休暇申請起動時の表示情報．休暇残数情報．代休管理区分　＝　管理する
    public get c2() {
        const self = this;
        let model = self.model as Model;
        let c2 = _.get(model, 'appAbsenceStartInfoDto.remainVacationInfo.substituteLeaveManagement') == ManageDistinct.YES;
        
        return c2;
    }
    // 休暇申請起動時の表示情報．休暇残数情報．振休管理区分　＝　管理する
    public get c3() {
        const self = this;
        let model = self.model as Model;
        let c3 = _.get(model, 'appAbsenceStartInfoDto.remainVacationInfo.holidayManagement') == ManageDistinct.YES;

        return c3;
    }
    // 休暇申請起動時の表示情報．休暇残数情報．年休管理区分　＝　管理する
    public get c4() {
        const self = this;
        let model = self.model as Model;
        let c4 = _.get(model, 'appAbsenceStartInfoDto.remainVacationInfo.annualLeaveManagement') == ManageDistinct.YES;

        return c4;
    }
    // 休暇申請起動時の表示情報．休暇残数情報．積休管理区分　＝　管理する
    public get c5() {
        const self = this;
        let model = self.model as Model;
        let c5 = _.get(model, 'appAbsenceStartInfoDto.remainVacationInfo.accumulatedRestManagement') == ManageDistinct.YES;

        return c5;
    }
    // UI処理【1】
    public get c6() {

        return true;
    }
    // 「A6_7」にチェックある場合
    public get c7() {
        const self = this;

        return self.checkBoxC7 || false;
    }
    // 休暇申請起動時の表示情報．就業時間帯表示フラグ = true
    public get c9() {
        const self = this;
        let model = self.model as Model;
        let c9 = _.get(model, 'appAbsenceStartInfoDto.workHoursDisp');

        return c9;
    }
    // ※9 = ○　AND　※10-1 = ○　AND　※10-2 = ○
    public get c10() {
        const self = this;

        return self.c9 && self.c10_1 && self.c10_2;
    }
    // 「休暇申請起動時の表示情報．申請表示情報．申請表示情報(基準日関係なし)．複数回勤務の管理」= true　
    public get c10_1() {
        const self = this;
        let model = self.model as Model;
        let c10_1 = _.get(model, 'appAbsenceStartInfoDto.appDispInfoStartupOutput.appDispInfoNoDateOutput.managementMultipleWorkCycles');
        
        return c10_1;
    }
    // 「休暇申請起動時の表示情報．勤務時間帯一覧．勤務NO = 2」がある
    public get c10_2() {
        const self = this;
        let model = self.model as Model;
        let c10_2 = _.find(_.get(model, 'appAbsenceStartInfoDto.workTimeLst'), (item: TimeZoneUseDto) => item.workNo == 2);

        return !_.isNil(c10_2);
    }
    // ※7 = ○　AND「休暇申請起動時の表示情報．休暇申請の反映．勤務情報、出退勤を反映する．出退勤を反映する」=する
    public get c11() {
        const self = this;
        let model = self.model as Model;
        let c11 = _.get(model, 'appAbsenceStartInfoDto.vacationApplicationReflect.workAttendanceReflect.reflectAttendance') == NotUseAtr.USE;
        
        return self.c7 || c11;
    }
    // 「A4_3」が「時間消化」を選択している
    public get c12() {
        const self = this;

        return self.selectedValue == HolidayAppType.DIGESTION_TIME;
    }
    // ※12 = ○　AND　※13-1 = ○　AND　※13-2 = ○
    public get c13() {
        const self = this;
        let model = self.model as Model;
        
        return self.c12 && self.c13_1 && self.c13_2;
    }
    // 休暇申請起動時の表示情報．休暇申請の反映．時間休暇を反映する．60H超休 = する
    public get c13_1() {
        const self = this;
        let model = self.model as Model;
        let c13_1 = _.get(model, 'appAbsenceStartInfoDto.vacationApplicationReflect.timeLeaveReflect.superHoliday60H') == NotUseAtr.USE;
        
        return c13_1;
    }
    // 休暇申請起動時の表示情報．休暇残数情報．60H超休管理．60H超休管理区分 = true
    public get c13_2() {
        const self = this;
        let model = self.model as Model;
        let c13_2 = _.get(model, 'appAbsenceStartInfoDto.remainVacationInfo.overtime60hManagement.overrest60HManagement') == NotUseAtr.USE;
        
        return c13_2;
    }
    // ※12 = ○　AND　※14-1 = ○　AND　※14-2 = ○
    public get c14() {
        const self = this;
        let model = self.model as Model;
        
        return self.c12 && self.c14_1 && self.c14_2;
    }
    // 休暇申請起動時の表示情報．休暇申請の反映．時間休暇を反映する．時間代休 = する
    public get c14_1() {
        const self = this;
        let model = self.model as Model;
        let c14_1 = _.get(model, 'appAbsenceStartInfoDto.vacationApplicationReflect.timeLeaveReflect.substituteLeaveTime') == NotUseAtr.USE;
       
        return c14_1;
    }
    // 休暇申請起動時の表示情報．休暇残数情報．代休管理区分．時間代休管理区分 = true
    public get c14_2() {
        const self = this;
        let model = self.model as Model;
        let c14_2 = _.get(model, 'appAbsenceStartInfoDto.remainVacationInfo.substituteLeaveManagement.timeAllowanceManagement') == ManageDistinct.YES;
        
        return c14_2;
    }
    // ※12 = ○　AND　※15-1 = ○　AND　※15-2 = ○
    public get c15() {
        const self = this;
        let model = self.model as Model;
        
        return self.c12 && self.c15_1 && self.c15_2;
    }
    // 休暇申請起動時の表示情報．休暇申請の反映．時間休暇を反映する．時間年休=する
    public get c15_1() {
        const self = this;
        let model = self.model as Model;
        let c15_1 = _.get(model, 'appAbsenceStartInfoDto.vacationApplicationReflect.timeLeaveReflect.annualVacationTime') == NotUseAtr.USE;
        
        return c15_1;
    }
    // 休暇申請起動時の表示情報．休暇残数情報．年休管理．時間年休管理区分 = true
    public get c15_2() {
        const self = this;
        let model = self.model as Model;
        let c15_2 = _.get(model, 'appAbsenceStartInfoDto.remainVacationInfo.annualLeaveManagement.timeAnnualLeaveManage') == ManageDistinct.YES;
        
        return c15_2;
    }

    // ※12 = ○　AND　※16-1 = ○　AND　※16-2 = ○　AND　※16-3 = ○
    public get c16() {
        const self = this;
        let model = self.model as Model;
        
        return self.c12 && self.c16_1 && self.c16_2 && self.c16_3;
    }
    // 休暇申請起動時の表示情報．休暇申請の反映．時間休暇を反映する．子看護= する
    public get c16_1() {
        const self = this;
        let model = self.model as Model;
        let c16_1 = _.get(model, 'appAbsenceStartInfoDto.vacationApplicationReflect.timeLeaveReflect.childNursing') == NotUseAtr.USE;

        return c16_1;
    }
    // 休暇申請起動時の表示情報．休暇残数情報．介護看護休暇管理．子の看護管理区分 = true
    public get c16_2() {
        const self = this;
        let model = self.model as Model;
        let c16_2 = _.get(model, 'appAbsenceStartInfoDto.remainVacationInfo.nursingCareLeaveManagement.childNursingManagement') == ManageDistinct.YES;
        
        return c16_2;
    }
    // 休暇申請起動時の表示情報．休暇残数情報．介護看護休暇管理．時間子の看護の管理区分 = true
    public get c16_3() {
        const self = this;
        let model = self.model as Model;
        let c16_3 = _.get(model, 'appAbsenceStartInfoDto.remainVacationInfo.nursingCareLeaveManagement.timeChildNursingManagement') == ManageDistinct.YES; 
        
        return c16_3;
    }
    // ※12 = ○　AND　※17-1 = ○　AND　※17-2 = ○　AND　※17-3 = ○
    public get c17() {
        const self = this;
        let model = self.model as Model;
        
        return self.c12 && self.c17_1 && self.c17_2 && self.c17_3;
    }
    // 休暇申請起動時の表示情報．休暇申請の反映．時間休暇を反映する．介護 = する
    public get c17_1() {
        const self = this;
        let model = self.model as Model;
        let c17_1 = _.get(model, 'appAbsenceStartInfoDto.vacationApplicationReflect.timeLeaveReflect.nursing') == NotUseAtr.USE;
        
        return c17_1;
    }
    // 休暇申請起動時の表示情報．休暇残数情報．介護看護休暇管理．介護管理区分 = true
    public get c17_2() {
        const self = this;
        let model = self.model as Model;
        let c17_2 = _.get(model, 'appAbsenceStartInfoDto.remainVacationInfo.nursingCareLeaveManagement.longTermCareManagement') == ManageDistinct.YES;

        return c17_2;
    }
    // 休暇申請起動時の表示情報．休暇残数情報．介護看護休暇管理．時間介護の管理区分 = true
    public get c17_3() {
        const self = this;
        let model = self.model as Model;
        let c17_3 = _.get(model, 'appAbsenceStartInfoDto.remainVacationInfo.nursingCareLeaveManagement.timeCareManagement') == ManageDistinct.YES;
        
        return c17_3;
    }
    // ※18-1 = ○　AND　※18-2 = ○　AND　※18-3 = ○
    public get c18() {
        const self = this;
        let model = self.model as Model;
        
        return self.c18_1 && self.c18_2 && self.c18_3;
    }
    // 「休暇種類」が「特別休暇」を選択している
    public get c18_1() {
        const self = this;
        
        return self.selectedValue == HolidayAppType.SPECIAL_HOLIDAY;
    }
    // 休暇申請起動時の表示情報．特別休暇表情報．事象に応じた特休フラグ = true 
    public get c18_2() {
        const self = this;
        let model = self.model as Model;
        let c18_2 = _.get(model, 'appAbsenceStartInfoDto.specAbsenceDispInfo.specHdForEventFlag');

        return c18_2 || false;
    }
    // 休暇申請起動時の表示情報．特別休暇表情報．事象に対する特別休暇．上限日数．種類 =「続柄ごとに上限を設定する」
    public get c18_3() {
        const self = this;
        let model = self.model as Model;
        let c18_3 = _.get(model, 'appAbsenceStartInfoDto.specAbsenceDispInfo.specHdEvent.maxNumberDay') == MaxNumberDayType.REFER_RELATIONSHIP;

        return c18_3;
    }
    // ※18 = ○　AND　休暇申請起動時の表示情報．特別休暇表情報．事象に対する特別休暇．忌引とする = true
    public get c19() {
        const self = this;
        let model = self.model as Model;
        let c19 = _.get(model, 'appAbsenceStartInfoDto.specAbsenceDispInfo.specHdEvent.makeInvitation') == NotUseAtr.USE;
        
        return self.c18 && c19;
    }
    // ※18 = ○　AND　休暇申請起動時の表示情報．特別休暇表情報．続柄毎の上限日数リスト．3親等以内とする = true
    public get c20() {
        const self = this;
        let model = self.model as Model;
        let c20 = _.get(model, 'appAbsenceStartInfoDto.specAbsenceDispInfo.dateSpecHdRelationLst[0].threeParentOrLess');
        
        return self.c18 && (c20 || false);
    }
    // ※21-1 = ○　AND　※21-2 = ○
    public get c21() {
        const self = this;
        let model = self.model as Model;
        
        return self.c21_1 && self.c21_2;
    }
    // 休暇申請起動時の表示情報. 休暇残数情報．代休管理．紐づけ管理区分 = 管理する
    public get c21_1() {
        const self = this;
        let model = self.model as Model;
        let c21_1 = _.get(model, 'appAbsenceStartInfoDto.remainVacationInfo.substituteLeaveManagement.linkingManagement') == ManageDistinct.YES;
        
        return c21_1;
    }
    // todo
    public get c21_2() {
        const self = this;
        let model = self.model as Model;
        
        return true;
    }
    // ※22-1 = ○　AND　※22-2 = ○
    public get c22() {
        const self = this;
        let model = self.model as Model;
        
        return self.c22_1 && self.c22_2;

    }
    // 休暇申請起動時の表示情報. 休暇残数情報．振休管理．紐づけ管理区分」= 管理する
    public get c22_1() {
        const self = this;
        let model = self.model as Model;
        let c22_1 = _.get(model, 'appAbsenceStartInfoDto.remainVacationInfo.holidayManagement.linkingManagement') == ManageDistinct.YES;
        
        return c22_1;
    }
    // todo
    public get c22_2() {
        const self = this;
        let model = self.model as Model;
        
        return true;

    }

    // 申請日が選択されている場合
    public get c23() {
        const self = this;
        let model = self.model as Model;
        
        return true;

    }



    public created() {
        const vm = this;
        if (vm.params) {
            vm.modeNew = false;
            vm.appDispInfoStartupOutput = vm.params.appDispInfoStartupOutput;
        }
        if (vm.modeNew) {
            vm.application = vm.createApplicationInsert(AppType.ABSENCE_APPLICATION);
        } else {
            vm.application = vm.params.appDispInfoStartupOutput.appDetailScreenInfo.application;
        }
        vm.$auth.user.then((user: any) => {
            vm.user = user;
        }).then(() => {
            if (vm.modeNew) {
                return vm.loadCommonSetting(AppType.ABSENCE_APPLICATION);
            }
            
            return true;
        }).then((loadData: any) => {
            if (loadData) {
                vm.updateKaf000_A_Params(vm.user);
                vm.updateKaf000_B_Params(vm.modeNew);
                vm.updateKaf000_C_Params(vm.modeNew);
                let command = {

                } as StartMobileParam;
                command.mode = vm.modeNew ? MODE_NEW : MODE_UPDATE;
                command.companyId = vm.user.companyId;
                command.employeeIdOp = vm.user.employeeId;
                command.datesOp = [];
                command.appDispInfoStartupOutput = vm.appDispInfoStartupOutput;
                if (vm.modeNew) {
                    return vm.$http.post('at', API.start, command);  
                }
                
                return true;
            }
        }).then((result: any) => {
            if (result) {
                let data = result.data as Model;
                if (vm.modeNew) {
                    vm.model = data;
                    vm.bindComponent();
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

    public kaf000BChangeDate(objectDate) {
        const vm = this;
        if (objectDate.startDate) {
            if (vm.modeNew) {
                vm.application.appDate = vm.$dt.date(objectDate.startDate, 'YYYY/MM/DD');
                vm.application.opAppStartDate = vm.$dt.date(objectDate.startDate, 'YYYY/MM/DD');
                vm.application.opAppEndDate = vm.$dt.date(objectDate.endDate, 'YYYY/MM/DD');
            }
        }
    }
    
    public kaf000BChangePrePost(prePostAtr) {
        const vm = this;
        vm.application.prePostAtr = prePostAtr;
    }

    public kaf000CChangeReasonCD(opAppStandardReasonCD) {
        const vm = this;
        vm.application.opAppStandardReasonCD = opAppStandardReasonCD;
    }

    public kaf000CChangeAppReason(opAppReason) {
        const vm = this;
        vm.application.opAppReason = opAppReason;
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
        vm.isValidateAll = vm.customValidate(vm);
        vm.$validate();
        if (!vm.$valid || !vm.isValidateAll) {

            return;
        }
        vm.$mask('show');
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
            vm.$mask('hide');    	
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

    public bindComponent() {
        const self = this;
        self.bindHolidayType();
        self.bindWorkInfo();
        self.bindWorkHours();
        self.bindRelationship();
    }

    public bindRelationship() {
        const self = this;
        // 特別休暇表示情報．続柄毎の上限日数リスト．続柄名
        // 特別休暇表示情報．続柄毎の上限日数リスト．コード
        let dropdownRelationship = [] as Array<any>;
        let dateSpecHdRelationLst = _.get(self.model, 'appAbsenceStartInfoDto.specAbsenceDispInfo.dateSpecHdRelationLst') as Array<DateSpecHdRelationOutput>;
        _.forEach(dateSpecHdRelationLst, (item: DateSpecHdRelationOutput) => {
            dropdownRelationship.push({
                code: item.relationCD,
                text: item.relationName
            });
        });
        self.dropdownRelationship = dropdownRelationship;
        if (!_.isEmpty(dropdownRelationship)) {
            self.selectedRelationship = dropdownRelationship[0].code;
        }

    }
    public bindWorkHours(mode?: boolean) {
        const self = this;
 
        let workTimeLst = _.get(self.model, 'appAbsenceStartInfoDto.workTimeLst') as Array<TimeZoneUseDto>;
        
        let result1 = _.find(workTimeLst, (item: TimeZoneUseDto) => item.workNo == 1) as TimeZoneUseDto;
        let result2 = _.find(workTimeLst, (item: TimeZoneUseDto) => item.workNo == 2 && item.useAtr == NotUseAtr.USE) as TimeZoneUseDto;

        if (!_.isNil(result1)) {
            self.workHours1 = {start: result1.startTime, end: result1.endTime};
        }
        if (!_.isNil(result2)) {
            self.workHours2 = {start: result2.startTime, end: result2.endTime};
        }

    } 

    public bindWorkInfo() {
        const self = this;
        // NULLの場合：「休暇申請起動時の表示情報．勤務種類一覧」の1個目を選択する
        let workTypeCode = self.model.appAbsenceStartInfoDto.selectedWorkTypeCD || _.get(self.model, 'appAbsenceStartInfoDto.workTypeLst[0].workTypeCode') || null;
        let workTypeName = '';
        if (!_.isNil(workTypeCode)) {
            let findResult = _.find(_.get(self.model, 'appAbsenceStartInfoDto.workTypeLst'), (item: WorkTypeDto) => item.workTypeCode == workTypeCode) as WorkTypeDto;
            workTypeName = !_.isNil(findResult) ? findResult.name : '';
        }

        let workTimeCode = self.model.appAbsenceStartInfoDto.selectedWorkTimeCD || null;
        let workTimeName = '';
        if (!_.isNil(workTimeCode)) {
            let findResult = _.find(_.get(self.model, 'appAbsenceStartInfoDto.appDispInfoStartupOutput.appDispInfoWithDateOutput.opWorkTimeLst'), (item: any) => item.worktimeCode == workTimeCode) as any;
            workTimeName = !_.isNil(findResult) ? (!_.isNil(findResult.workTimeDisplayName) ? findResult.workTimeDisplayName.workTimeName : '') : '';
        }
        // 表示形式：開始+"～"+終了
        let workTimeTime = '';
        let existTime = _.find(self.model.appAbsenceStartInfoDto.workTimeLst, (item: TimeZoneUseDto) => item.workNo == 1) as TimeZoneUseDto;
        if (!_.isNil(existTime)) {
            workTimeTime = self.handleTimeWithDay(existTime.startTime) + '～' + self.handleTimeWithDay(existTime.endTime);
        }

    }
    public handleTimeWithDay(time: number) {
        const self = this;
        const nameTime = '当日';
        if (!time) {

            return;
        }

        return (0 <= time && time < 1440) ? nameTime + self.$dt.timewd(time) : self.$dt.timewd(time);
    }

    public bindHolidayType() {
        const self = this;
        if (!self.model.appAbsenceStartInfoDto) {

            return;
        }
        let dropDownList = [];
        // 休暇申請起動時の表示情報．休暇申請設定．休暇申請種類表示名．表示名
        let dispNames = self.model.appAbsenceStartInfoDto.hdAppSet.dispNames as Array<HolidayAppTypeDispNameDto>;
        dropDownList = _.map(dispNames, (item: HolidayAppTypeDispNameDto) => {

            return {
                code: item.holidayAppType,
                text: item.displayName
            };
        });
        dropDownList.unshift({
            code: null,
            text: '--- 選択してください ---'
        });
        self.dropdownList = dropDownList;
        self.selectedValue = self.getSelectedValue();
        // 

    }

    public getSelectedValue(): HolidayAppType {
        const self = this;
        if (!self.model.appAbsenceStartInfoDto) {

            return;
        }
        // 休暇申請起動時の表示情報．休暇残数情報．年休管理区分　＝　管理する
        let c1_1 = self.model.appAbsenceStartInfoDto.remainVacationInfo.annualLeaveManagement.annualLeaveManageDistinct == ManageDistinct.YES;
        // 休暇申請起動時の表示情報．申請表示情報．申請表示情報(基準日関係あり)．雇用別申請承認設定．申請別対象勤務種類．休暇種類を利用しない = false
        // AND　休暇申請起動時の表示情報．申請表示情報．申請表示情報(基準日関係あり)．雇用別申請承認設定．申請別対象勤務種類．休暇申請の種類 = 年次有休
        let c1_2 = 
        !_.isNil(_.find(_.get(self.model, 'appAbsenceStartInfoDto.appDispInfoStartupOutput.appDispInfoWithDateOutput.opEmploymentSet.targetWorkTypeByAppLst')
        , (item: TargetWorkTypeByApp) => item.appType == ApplicationType.ABSENCE_APPLICATION && item.opHolidayAppType == HolidayAppType.ANNUAL_PAID_LEAVE && !item.opHolidayTypeUse));
        // 休暇申請起動時の表示情報．休暇残数情報．代休管理区分　＝　管理する
        let c1_3 = self.model.appAbsenceStartInfoDto.remainVacationInfo.substituteLeaveManagement.substituteLeaveManagement == ManageDistinct.YES;
        //休暇申請起動時の表示情報．申請表示情報．申請表示情報(基準日関係あり)．雇用別申請承認設定．申請別対象勤務種類．休暇種類を利用しない = false
        // AND　休暇申請起動時の表示情報．申請表示情報．申請表示情報(基準日関係あり)．雇用別申請承認設定．申請別対象勤務種類．休暇申請の種類 = 代休
        let c1_4 = 
        !_.isNil(_.find(_.get(self.model, 'appAbsenceStartInfoDto.appDispInfoStartupOutput.appDispInfoWithDateOutput.opEmploymentSet.targetWorkTypeByAppLst')
        , (item: TargetWorkTypeByApp) => item.appType == ApplicationType.ABSENCE_APPLICATION && item.opHolidayAppType == HolidayAppType.SUBSTITUTE_HOLIDAY && !item.opHolidayTypeUse));
        // 休暇申請起動時の表示情報．申請表示情報．申請表示情報(基準日関係あり)．雇用別申請承認設定．申請別対象勤務種類．休暇種類を利用しない = false
        // AND　休暇申請起動時の表示情報．申請表示情報．申請表示情報(基準日関係あり)．雇用別申請承認設定．申請別対象勤務種類．休暇申請の種類 = 欠勤
        let c1_5 =
        !_.isNil(_.find(_.get(self.model, 'appAbsenceStartInfoDto.appDispInfoStartupOutput.appDispInfoWithDateOutput.opEmploymentSet.targetWorkTypeByAppLst')
        , (item: TargetWorkTypeByApp) => item.appType == ApplicationType.ABSENCE_APPLICATION && item.opHolidayAppType == HolidayAppType.ABSENCE && !item.opHolidayTypeUse));
        // 休暇申請起動時の表示情報．申請表示情報．申請表示情報(基準日関係あり)．雇用別申請承認設定．申請別対象勤務種類．休暇種類を利用しない = false
        // AND　休暇申請起動時の表示情報．申請表示情報．申請表示情報(基準日関係あり)．雇用別申請承認設定．申請別対象勤務種類．休暇申請の種類 = 特別休暇
        let c1_6 =
        !_.isNil(_.find(_.get(self.model, 'appAbsenceStartInfoDto.appDispInfoStartupOutput.appDispInfoWithDateOutput.opEmploymentSet.targetWorkTypeByAppLst')
        , (item: TargetWorkTypeByApp) => item.appType == ApplicationType.ABSENCE_APPLICATION && item.opHolidayAppType == HolidayAppType.SPECIAL_HOLIDAY && !item.opHolidayTypeUse));
        // 休暇申請起動時の表示情報．休暇残数情報．積休管理区分　＝　管理する
        let c1_7 = self.model.appAbsenceStartInfoDto.remainVacationInfo.accumulatedRestManagement.accumulatedManage == ManageDistinct.YES;
        // 休暇申請起動時の表示情報．申請表示情報．申請表示情報(基準日関係あり)．雇用別申請承認設定．申請別対象勤務種類．休暇種類を利用しない = false
        // AND　休暇申請起動時の表示情報．申請表示情報．申請表示情報(基準日関係あり)．雇用別申請承認設定．申請別対象勤務種類．休暇申請の種類 = 積立年休
        let c1_8 = 
        !_.isNil(_.find(_.get(self.model, 'appAbsenceStartInfoDto.appDispInfoStartupOutput.appDispInfoWithDateOutput.opEmploymentSet.targetWorkTypeByAppLst')
        , (item: TargetWorkTypeByApp) => item.appType == ApplicationType.ABSENCE_APPLICATION && item.opHolidayAppType == HolidayAppType.YEARLY_RESERVE && !item.opHolidayTypeUse));
        // 休暇申請起動時の表示情報．申請表示情報．申請表示情報(基準日関係あり)．雇用別申請承認設定．申請別対象勤務種類．休暇種類を利用しない = false
        // AND　休暇申請起動時の表示情報．申請表示情報．申請表示情報(基準日関係あり)．雇用別申請承認設定．申請別対象勤務種類．休暇申請の種類 = 休日
        let c1_9 =
        !_.isNil(_.find(_.get(self.model, 'appAbsenceStartInfoDto.appDispInfoStartupOutput.appDispInfoWithDateOutput.opEmploymentSet.targetWorkTypeByAppLst')
        , (item: TargetWorkTypeByApp) => item.appType == ApplicationType.ABSENCE_APPLICATION && item.opHolidayAppType == HolidayAppType.HOLIDAY && !item.opHolidayTypeUse));
        // 休暇申請起動時の表示情報．申請表示情報．申請表示情報(基準日関係あり)．雇用別申請承認設定．申請別対象勤務種類．休暇種類を利用しない = false
        // AND　休暇申請起動時の表示情報．申請表示情報．申請表示情報(基準日関係あり)．雇用別申請承認設定．申請別対象勤務種類．休暇申請の種類 = 時間消化
        let c1_10 =
        !_.isNil(_.find(_.get(self.model, 'appAbsenceStartInfoDto.appDispInfoStartupOutput.appDispInfoWithDateOutput.opEmploymentSet.targetWorkTypeByAppLst')
        , (item: TargetWorkTypeByApp) => item.appType == ApplicationType.ABSENCE_APPLICATION && item.opHolidayAppType == HolidayAppType.DIGESTION_TIME && !item.opHolidayTypeUse));

        // ※1-1 = ○　AND　※1-2 = ○ -> 年次有給
        if (c1_1 && c1_2) {

            return HolidayAppType.ANNUAL_PAID_LEAVE;
        }
        // ※1-3 = ○　AND　※1-4 = ○ -> 代休
        if (c1_3 && c1_4) {

            return HolidayAppType.SUBSTITUTE_HOLIDAY;
        }
        // ※1-5 = ○ -> 欠勤
        if (c1_5) {

            return HolidayAppType.ABSENCE;
        }
        // ※1-6 = ○ -> 特別休暇
        if (c1_6) {

            return HolidayAppType.SPECIAL_HOLIDAY;
        }
        // ※1-7 = ○　AND　※8 = ○ -> 積立年休
        if (c1_7 && c1_8) {

            return HolidayAppType.YEARLY_RESERVE;
        }
        // ※1-9 = ○ -> 休日
        if (c1_9) {

            return HolidayAppType.HOLIDAY;
        }
        // ※1-10 = ○ -> 時間消化
        if (c1_10) {

            return HolidayAppType.DIGESTION_TIME;
        }
    }

    public openKDL002(type?: string) {
        const self = this;
        
    }

    public openCDL() {
        const self = this;
        console.log('openCDL');
    }

    public selectedHolidayType(data: any) {
        const self = this;
        let command = {

        } as any;

    }


    
}
interface Model {
    appAbsenceStartInfoDto: AppAbsenceStartInfoDto;
    applyForLeaveDto: any;
}
interface WorkInfo {
    code: string;
    name: string;
    time?: string;
}
const API = {
    start: 'at/request/application/appforleave/mobile/start',
    checkBeforeRegisterSample: 'at/request/application/checkBeforeSample',
    registerSample: 'at/request/application/changeDataSample',
    sendMailAfterRegisterSample: ''
};
const MODE_NEW = 0;
const MODE_UPDATE = 1;
