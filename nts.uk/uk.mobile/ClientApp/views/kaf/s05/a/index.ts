import {ParamStartMobile, OvertimeAppAtr, Model} from '../a/define.interface';
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

    public model: Model;

    @Prop() 
    public readonly params: InitParam;

    public get step() {
        return `step_${this.numb}`;
    }

    public created() {
        const vm = this;
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

    public kaf000BChangeDate(objectDate) {
        const vm = this;
        console.log('kaf000BChangeDate');
        vm.changeDate(objectDate.startDate);
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
    
    public kaf000BChangePrePost(prePostAtr) {
        const vm = this;
        console.log('kaf000BChangePrePost');
        vm.application.prePostAtr = prePostAtr;
    }

    public kaf000CChangeReasonCD(opAppStandardReasonCD) {
        const vm = this;
        console.log('kaf000CChangeReasonCD');
        vm.application.opAppStandardReasonCD = opAppStandardReasonCD;
    }

    public kaf000CChangeAppReason(opAppReason) {
        const vm = this;
        console.log('kaf000CChangeAppReason');
        vm.application.opAppReason = opAppReason;
    }

    public toStep(value: number) {
        const vm = this;
        vm.isValidateAll = vm.customValidate(vm);
        vm.$validate();
        if (!vm.$valid || !vm.isValidateAll) {
            window.scrollTo(500, 0);

            return;
        }
        vm.numb = value;
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

    public openKDL002() {
        const self = this;
        let step1 = self.$refs.step1 as KafS05Step1Component;
        self.$modal('worktype', {

            seledtedWkTypeCDs: _.map(_.uniqBy(self.model.displayInfoOverTime.infoBaseDateOutput.worktypes, (e: any) => e.workTypeCode), (item: any) => item.workTypeCode),
            selectedWorkTypeCD: step1.getWorkType(),
            seledtedWkTimeCDs: _.map(self.model.displayInfoOverTime.appDispInfoStartup.appDispInfoWithDateOutput.opWorkTimeLst, (item: any) => item.worktimeCode),
            selectedWorkTimeCD: step1.getWorkTime(),
            isSelectWorkTime: 1,
        })
        .then((f: any) => {
            step1.setWorkCode(
                f.selectedWorkType.workTypeCode,
                f.selectedWorkType.name,
                f.selectedWorkTime.code,
                f.selectedWorkTime.name,
                f.selectedWorkTime.workTime1,
                self.model.displayInfoOverTime
            );
        })
        .catch((err: any) => {
            console.log(err);
        });
    }




}
const API = {
    start: 'at/request/application/overtime/mobile/start',
    changeDate: 'at/request/application/overtime/mobile/changeDate',

    checkBeforeRegisterSample: 'at/request/application/checkBeforeSample',
    registerSample: 'at/request/application/changeDataSample',
    sendMailAfterRegisterSample: ''
};

