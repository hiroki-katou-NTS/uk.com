import { _, Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { KafS00AComponent, KafS00BComponent, KafS00CComponent } from 'views/kaf/s00';
import { KafS00ShrComponent, AppType, Application, InitParam } from 'views/kaf/s00/shr';
import { AppAbsenceStartInfoDto, StartMobileParam, NotUseAtr, TimeZoneUseDto } from '../a/define.interface';

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
    public time: number = 0;
    public checkBox: Array<number> = [1];
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
    @Prop() 
    public readonly params: InitParam;

    // ※2 = ○　OR　※3 = ○　OR　※4 = ○　OR　※5 = ○　
    public get c1() {
        const self = this;

        return self.c2 || self.c3 || self.c4 || self.c5;
    }
    // 休暇申請起動時の表示情報．休暇残数情報．代休管理区分　＝　管理する
    public get c2() {
        const self = this;
        let model = self.model as Model;
        let c2 = _.get(model, 'appAbsenceStartInfoDto.remainVacationInfo.substituteLeaveManagement');
        
        return c2 == NotUseAtr.USE;
    }
    // 休暇申請起動時の表示情報．休暇残数情報．振休管理区分　＝　管理する
    public get c3() {
        const self = this;
        let model = self.model as Model;
        let c3 = _.get(model, 'appAbsenceStartInfoDto.remainVacationInfo.holidayManagement');

        return c3 == NotUseAtr.USE;
    }
    // 休暇申請起動時の表示情報．休暇残数情報．年休管理区分　＝　管理する
    public get c4() {
        const self = this;
        let model = self.model as Model;
        let c4 = _.get(model, 'appAbsenceStartInfoDto.remainVacationInfo.annualLeaveManagement');

        return c4 == NotUseAtr.USE;
    }
    // 休暇申請起動時の表示情報．休暇残数情報．積休管理区分　＝　管理する
    public get c5() {
        const self = this;
        let model = self.model as Model;
        let c5 = _.get(model, 'appAbsenceStartInfoDto.remainVacationInfo.accumulatedRestManagement');

        return c5 == NotUseAtr.USE;
    }

    public get c6() {

        return true;
    }

    public get c7() {

        return true;
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
        let model = self.model as Model;

        return true;
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
        let c13_1 = _.get(model, 'appAbsenceStartInfoDto.vacationApplicationReflect.timeLeaveReflect.superHoliday60H');
        
        return c13_1;
    }
    // 休暇申請起動時の表示情報．休暇残数情報．60H超休管理．60H超休管理区分 = true
    public get c13_2() {
        const self = this;
        let model = self.model as Model;
        let c13_2 = _.get(model, 'appAbsenceStartInfoDto.remainVacationInfo.overtime60hManagement.overrest60HManagement');
        
        return c13_2;
    }
    // ※12 = ○　AND　※14-1 = ○　AND　※14-2 = ○
    public get c14() {
        const self = this;
        let model = self.model as Model;
        
        return true;
    }
    // 休暇申請起動時の表示情報．休暇申請の反映．時間休暇を反映する．時間代休 = する
    public get c14_1() {
        const self = this;
        let model = self.model as Model;
        
        return true;
    }
    // 休暇申請起動時の表示情報．休暇残数情報．代休管理区分．時間代休管理区分 = true
    public get c14_2() {
        const self = this;
        let model = self.model as Model;
        
        return true;
    }
    // ※12 = ○　AND　※15-1 = ○　AND　※15-2 = ○
    public get c15() {
        const self = this;
        let model = self.model as Model;
        
        return true;
    }
    // 休暇申請起動時の表示情報．休暇申請の反映．時間休暇を反映する．時間年休=する
    public get c15_1() {
        const self = this;
        let model = self.model as Model;
        
        return true;
    }
    // 休暇申請起動時の表示情報．休暇残数情報．年休管理．時間年休管理区分 = true
    public get c15_2() {
        const self = this;
        let model = self.model as Model;
        
        return true;
    }

    // ※12 = ○　AND　※16-1 = ○　AND　※16-2 = ○　AND　※16-3 = ○
    public get c16() {
        const self = this;
        let model = self.model as Model;
        
        return true;
    }
    // 休暇申請起動時の表示情報．休暇申請の反映．時間休暇を反映する．子看護= する
    public get c16_1() {
        const self = this;
        let model = self.model as Model;
        
        return true;
    }
    // 休暇申請起動時の表示情報．休暇残数情報．介護看護休暇管理．子の看護管理区分 = true
    public get c16_2() {
        const self = this;
        let model = self.model as Model;
        
        return true;
    }
    // 休暇申請起動時の表示情報．休暇残数情報．介護看護休暇管理．時間子の看護の管理区分 = true
    public get c16_3() {
        const self = this;
        let model = self.model as Model;
        
        return true;
    }
    // ※12 = ○　AND　※17-1 = ○　AND　※17-2 = ○　AND　※17-3 = ○
    public get c17() {
        const self = this;
        let model = self.model as Model;
        
        return true;
    }
    // 休暇申請起動時の表示情報．休暇申請の反映．時間休暇を反映する．介護 = する
    public get c17_1() {
        const self = this;
        let model = self.model as Model;
        
        return true;
    }
    // 休暇申請起動時の表示情報．休暇残数情報．介護看護休暇管理．介護管理区分 = true
    public get c17_2() {
        const self = this;
        let model = self.model as Model;
        
        return true;
    }
    // 休暇申請起動時の表示情報．休暇残数情報．介護看護休暇管理．時間介護の管理区分 = true
    public get c17_3() {
        const self = this;
        let model = self.model as Model;
        
        return true;
    }
    // ※18-1 = ○　AND　※18-2 = ○　AND　※18-3 = ○
    public get c18() {
        const self = this;
        let model = self.model as Model;
        
        return true;
    }
    // 「休暇種類」が「特別休暇」を選択している
    public get c18_1() {
        const self = this;
        let model = self.model as Model;
        
        return true;
    }
    // 休暇申請起動時の表示情報．特別休暇表情報．事象に応じた特休フラグ = true 
    public get c18_2() {
        const self = this;
        let model = self.model as Model;
        
        return true;
    }
    // 休暇申請起動時の表示情報．特別休暇表情報．事象に対する特別休暇．上限日数．種類 =「続柄ごとに上限を設定する」
    public get c18_3() {
        const self = this;
        let model = self.model as Model;
        
        return true;
    }
    // ※18 = ○　AND　休暇申請起動時の表示情報．特別休暇表情報．事象に対する特別休暇．忌引とする = true
    public get c19() {
        const self = this;
        let model = self.model as Model;
        
        return true;
    }
    // ※18 = ○　AND　休暇申請起動時の表示情報．特別休暇表情報．続柄毎の上限日数リスト．3親等以内とする = true
    public get c20() {
        const self = this;
        let model = self.model as Model;
        
        return true;
    }
    // ※21-1 = ○　AND　※21-2 = ○
    public get c21() {
        const self = this;
        let model = self.model as Model;
        
        return true;
    }
    // 休暇申請起動時の表示情報. 休暇残数情報．代休管理．紐づけ管理区分 = 管理する
    public get c21_1() {
        const self = this;
        let model = self.model as Model;
        
        return true;
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
        
        return true;

    }
    // 休暇申請起動時の表示情報. 休暇残数情報．振休管理．紐づけ管理区分」= 管理する
    public get c22_1() {
        const self = this;
        let model = self.model as Model;
        
        return true;

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
                // console.log('changeDateCustom');
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

    }

    public openKDL002(type?: string) {
        const self = this;
        
    }


    
}
interface Model {
    appAbsenceStartInfoDto: AppAbsenceStartInfoDto;
    applyForLeaveDto: any;
}
const API = {
    start: 'at/request/application/appforleave/mobile/start',
    checkBeforeRegisterSample: 'at/request/application/checkBeforeSample',
    registerSample: 'at/request/application/changeDataSample',
    sendMailAfterRegisterSample: ''
};
const MODE_NEW = 0;
const MODE_UPDATE = 1;
