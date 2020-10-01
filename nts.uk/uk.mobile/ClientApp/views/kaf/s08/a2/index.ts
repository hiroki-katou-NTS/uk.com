import { moment, Vue, DirectiveBinding } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { StepwizardComponent } from '@app/components';
import { FixTableComponent } from '@app/components/fix-table';
import { KafS08DComponent } from '../../../kaf/s08/d';
import { KafS00ShrComponent, AppType } from 'views/kaf/s00/shr';
import * as _ from 'lodash';



// import abc from './mock_data.json';

@component({
    name: 'kafs08a2',
    route: '/kaf/s08/a2',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    components: {
        'step-wizard': StepwizardComponent,
        'fix-table': FixTableComponent,
    },
    directives: {
        'date': {
            bind(el: HTMLElement, binding: DirectiveBinding) {
                const mm = moment(binding.value, 'YYYY/MM/DD');
                el.innerHTML = mm.format('MM/DD(ddd)');
                el.className = mm.clone().locale('en').format('dddd').toLocaleLowerCase();
            }
        }
    },
    constraints: [],
})
export class KafS08A2Component extends KafS00ShrComponent {
    //lúc không truyển props thì nó nhảy vào default
    @Prop({ default: () => ({}) })

    //A2 nhận về là props là array table
    @Prop({ default: () => [] }) public readonly table!: [];

    //A2 nhận về props params là một Object ITimes
    @Prop({ default: () => null }) public derpartureTime!: number;

    @Prop({ default: () => null }) public returnTime!: number;

    //A2 nhận về props comment là một Object comment
    @Prop({ default: {} }) public readonly comment!: Object;

    //A2 nhan ve props businessTripInfoOutput
    @Prop({ default: {} }) public readonly businessTripInfoOutput !: any;

    //A2 nhan ve props application
    @Prop({ default: {} }) public readonly application!: Object;

    //A2 nhan ve props listDate
    @Prop({ default: [] }) public readonly listDate!: [];

    //A2 nhan props app reason
    @Prop({ default: '' }) public readonly appReason!: string;

    @Prop({ default: () => { } }) public readonly params!: any;

    @Prop({ default: true })
    public readonly mode!: boolean;

    public user: any;
    public data: any;
    public hidden: boolean = false;
    public businessTripActualContent: any[] = [];
    public appID: string = ' ';
    // public lstWorkDay: any[] = [];


    public created() {
        const vm = this;
        vm.data = vm.businessTripInfoOutput;
        if (vm.mode) {
            vm.fetchStart();
        } else {
            vm.businessTripActualContent = vm.data.businessTrip.tripInfos.map((item: any) => {
                const workTime = vm.data.businessTripInfoOutput.appDispInfoStartup.appDispInfoWithDateOutput.opWorkTimeLst.find((i: any) => i.worktimeCode == item.wkTimeCd);
                const workType = vm.data.businessTripInfoOutput.infoBeforeChange.find((i: any) => i.date == item.date).workTypeDto;
                
                return {
                    date: item.date,
                    opAchievementDetail: {
                        workTypeCD: item.wkTypeCd,
                        workTimeCD: item.wkTimeCd,
                        opWorkTypeName: workType.name,
                        opWorkTimeName: workTime ? workTime.workTimeDisplayName.workTimeName : null,
                        opWorkTime: item.startWorkTime,
                        opLeaveTime: item.endWorkTime
                    }
                };
            });
        }
    }

    public fetchStart() {
        const vm = this;

        vm.$mask('show');

        vm.$auth.user.then((usr: any) => {
            vm.user = usr;
        }).then(() => {
            return vm.loadCommonSetting(AppType.BUSINESS_TRIP_APPLICATION);
        }).then((loadData: any) => {
            if (loadData) {
                vm.$mask('show');

                return vm.$http.post('at', API.startKAFS08, {
                    mode: vm.mode,
                    companyId: vm.user.companyId,
                    employeeId: vm.user.employeeId,
                    listDates: vm.listDate,
                    businessTripInfoOutput: vm.data.businessTripInfoOutput,
                    businessTrip: vm.data.businessTrip
                }).then((res: any) => {
                    vm.data = res.data;
                    vm.businessTripActualContent = vm.data.businessTripInfoOutput.businessTripActualContent;
                    vm.$mask('hide');
                });
            }

            setTimeout(function () {
                let focusElem = document.getElementById('table-a10');
                (focusElem as HTMLElement).focus();
            }, 200);
        });
    }

    //update business trip
    public updateBusinessTrip() {
        const vm = this;
        let params = {
            businessTrip: vm.data.businessTrip,
            businessTripInfoOutput: vm.data.businessTripInfoOutput,
            application: vm.application,
        };
        vm.$mask('show');

        return vm.$http.post('at', API.updateBusinessTrip, params).then((res: any) => {
            vm.$mask('hide');
            vm.$emit('nextToStepThree', res.data.appID);
        }).catch(() => {
            vm.$mask('hide');
            vm.$modal.error({ messageId: 'Msg_1912' });
        });
    }

    //hàm xử lý gọi dialog
    public selectRowDate(rowDate) {
        const vm = this;
        const { businessTripInfoOutput } = vm.data;
        const { opWorkTime, opLeaveTime } = rowDate.opAchievementDetail;
        if (opWorkTime && opLeaveTime) {
            rowDate.opAchievementDetail.opWorkTime1 = vm.$dt.timewd(opWorkTime) + ' ~ ' + vm.$dt.timewd(opLeaveTime);
        }

        vm.$modal(KafS08DComponent, {
            rowDate,
            businessTripInfoOutput,
            startWorkTime: opWorkTime,
            endWorkTime: opLeaveTime
        }).then((model: {
            date: string,
            opWorkTypeName: '',
            opWorkTimeName: '',
            startWorkTime: number,
            endWorkTime: number,
            workTypeCD: string,
            workTimeCD: string
        }) => {
            if (model) {
                vm.businessTripActualContent.forEach((i) => {
                    if (i.date == model.date) {
                        i.opAchievementDetail.opWorkTypeName = model.opWorkTypeName ;
                        i.opAchievementDetail.opWorkTimeName = model.opWorkTimeName ;
                        i.opAchievementDetail.workTypeCD = model.workTypeCD;
                        i.opAchievementDetail.workTimeCD = model.workTimeCD;
                        i.opAchievementDetail.opWorkTime = model.startWorkTime;
                        i.opAchievementDetail.opLeaveTime = model.endWorkTime ;
                    }
                });
                if (!vm.mode) {
                    vm.data.businessTrip.tripInfos = vm.businessTripActualContent.map((item: any) => {
                        return {
                            date: item.date,
                            wkTypeCd: item.opAchievementDetail.workTypeCD,
                            wkTimeCd: item.opAchievementDetail.workTimeCD,
                            startWorkTime: item.opAchievementDetail.opWorkTime,
                            endWorkTime: item.opAchievementDetail.opLeaveTime
                        };
                    });
                }
                // vm.$emit('changeTime', model.derpartureTime, model.returnTime);
            }           
        });
    }

    //nhảy đến step three với các điều kiện
    public nextToStepThree() {
        const vm = this;
        vm.checkBeforeRegister();
        //vm.toggleErrorAlert();
        //this.$emit('nextToStepThree');
    }
   
    //quay trở lại step one
    public prevStepOne() {
        const vm = this;
        vm.$emit('prevStepOne', vm.derpartureTime, vm.returnTime, vm.appReason);
    }

    //hàm check trước khi register
    public checkBeforeRegister() {
        const vm = this;
        let tripInfos: Array<BusinessTripInfo> = _.map(vm.businessTripActualContent, function (item: any) {
            return {
                date: item.date,
                wkTypeCd: item.opAchievementDetail.workTypeCD,
                wkTimeCd: item.opAchievementDetail.workTimeCD,
                startWorkTime: item.opAchievementDetail.opWorkTime,
                endWorkTime: item.opAchievementDetail.opLeaveTime
            };
        });
        let paramsBusinessTrip = {
            departureTime: vm.derpartureTime,
            returnTime: vm.returnTime,
            tripInfos,
        };
        vm.$mask('show');
        // check before registering application
        vm.$http.post('at', API.checkBeforeApply, {
            businessTripInfoOutputDto: vm.data.businessTripInfoOutput,
            businessTripDto: paramsBusinessTrip
        }).then((res: any) => {
            vm.mode ? vm.registerData() : vm.updateBusinessTrip();
        }).catch((err: any) => {
            vm.$mask('hide');  
            let param;

            if (err.messageId == 'Msg_23' || err.messageId == 'Msg_24' || err.messageId == 'Msg_1912' || err.messageId == 'Msg_1913' ) {
                err.message = err.parameterIds[0] + err.message;
                param = err;

                return vm.$modal.error(param);
            } else {
                vm.handleErrorMessage(err);
            }
        });
    }

    //handle mess dialog
    public handleErrorMessage(res: any) {
        const vm = this;
        vm.$mask('hide');
        if (res.messageId) {
            return vm.$modal.error({ messageId: res.messageId, messageParams: res.parameterIds });
        } else {

            if (_.isArray(res.errors)) {
                return vm.$modal.error({ messageId: res.errors[0].messageId, messageParams: res.parameterIds });
            } else {
                return vm.$modal.error({ messageId: res.errors.messageId, messageParams: res.parameterIds });
            }
        }
    }

    //hàm register when click A50_2 button
    public registerData() {
        const vm = this;
        vm.$mask('show');
        let tripInfos: Array<BusinessTripInfo> = _.map(vm.businessTripActualContent, function (item: any) {
            return {
                date: item.date,
                wkTypeCd: item.opAchievementDetail.workTypeCD,
                wkTimeCd: item.opAchievementDetail.workTimeCD,
                startWorkTime: item.opAchievementDetail.opWorkTime,
                endWorkTime: item.opAchievementDetail.opLeaveTime
            };
        });
        let paramsBusinessTrip = {
            departureTime: vm.derpartureTime,
            returnTime: vm.returnTime,
            tripInfos,
        };

        if (paramsBusinessTrip.tripInfos.length != 0) {
            vm.$http.post('at', API.register, {
                businessTrip: paramsBusinessTrip,
                businessTripInfoOutput: vm.data.businessTripInfoOutput,
                application: vm.application
            }).then((res: any) => {
                //vm.appID = res.data.appID;
                if (res) {
                    vm.$emit('nextToStepThree', res.data.appID);
                } else {
                    vm.$modal.error({ messageId: 'Msg_1912' });
                }
                vm.$mask('hide');
            }).catch(() => {
                vm.$mask('hide');
                vm.$modal.error({ messageId: 'Msg_1912' });
            });
        } else {
            vm.$modal.error({ messageId: 'Msg_1703' });
        }
    }
}

export enum ScreenMode {
    // 新規モード
    NEW = 0,
    // 詳細モード
    DETAIL = 1
}

export interface BusinessTripInfo {
    date: String;
    wkTypeCd: String;
    wkTimeCd: String;
    startWorkTime: number;
    endWorkTime: number;
}

const API = {
    startKAFS08: 'at/request/application/businesstrip/mobile/startMobile',
    checkBeforeApply: 'at/request/application/businesstrip/mobile/checkBeforeRegister',
    register: 'at/request/application/businesstrip/mobile/register',
    updateBusinessTrip: 'at/request/application/businesstrip/mobile/updateBusinessTrip',
};

