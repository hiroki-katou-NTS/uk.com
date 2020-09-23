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
    @Prop({ default: ' ' }) public readonly departureTime!: string;

    @Prop({ default: ' ' }) public readonly returnTime!: string;

    //A2 nhận về props comment là một Object comment
    @Prop({ default: {} }) public readonly comment!: Object;

    //A2 nhan ve props businessTripInfoOutput
    @Prop({ default: {} }) public readonly businessTripInfoOutput !: any;

    //A2 nhan ve props application
    @Prop({ default: {} }) public readonly application!: Object;

    //A2 nhan ve props listDate
    @Prop({ default: [] }) public readonly listDate!: [];

    //A2 nhan props app reason
    @Prop({default : ''}) public readonly appReason!: string ;

    @Prop({default : () => {}}) public readonly params!: any;

    //public readonly params!: any;
    public name: string = 'hello my dialog';
    //public date: Date = new Date(2020,2,14);
    public mtable = require('./mock_data.json');
    public mode: boolean = true;
    public user: any;
    public data: any;
    public hidden: boolean = false;
    public businessTripActualContent: [] = [];
    public appID: string = ' ' ;
    public lstWorkDay: any[] = [ ]; 


    public created() {
        const vm = this;
        if (vm.businessTripInfoOutput.businessTripInfoOutput.appDispInfoStartup.appDetailScreenInfo !=  null) {
            vm.mode = false ;
        }
        console.log(vm.application);
        console.log(vm.businessTripInfoOutput);
        vm.fetchStart();
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
                return vm.$http.post('at', API.startKAFS08, {
                    mode: true,
                    companyId: vm.user.companyId,
                    employeeId: vm.user.employeeId,
                    listDates: vm.listDate,
                    businessTripInfoOutput: vm.mode ? null : vm.data,
                }).then((res: any) => {
                    vm.data = res.data;
                    vm.businessTripActualContent = vm.data.businessTripInfoOutput.businessTripActualContent;
                    vm.lstWorkDay = vm.data.businessTripInfoOutput.workdays;
                    //console.log(vm.businessTripActualContent.length);
                });
            }
        });
    }

    //update business trip
    public updateBusinessTrip() {
        const vm = this;
        let params = {
            businessTrip : vm.businessTripInfoOutput.businessTrip,
            businessTripInfoOutput: vm.businessTripInfoOutput.businessTripInfoOutput,
            application : vm.application,
        };

        return vm.$http.post('at', API.updateBusinessTrip, params).then((res: any) => {
            vm.$emit('nextToStepThree',res.data.appID);
        }).catch(() => {
            vm.$modal.error({ messageId: 'Msg_1912' });
        });
    }

    //hàm xử lý gọi dialog
    public selectRowDate(data) {
        const vm = this;
        vm.$modal(KafS08DComponent, {
            rowDate : data,
            lstWorkDay:vm.lstWorkDay,
            businessTripInfoOutput : vm.data.businessTripInfoOutput,
            departureTime : vm.departureTime,
            returnTime : vm.returnTime,
        }).then((model: any) => {
            console.log(model);
            //opAchievementDetail.opWorkTime = model.opWorkTime;
            //opAchievementDetail.opWorkTime = model.opLeaveTime;
        });
    }

    //nhảy đến step three với các điều kiện
    public nextToStepThree() {
        const vm = this;
        vm.mode ? vm.registerData() : vm.updateBusinessTrip();
        //vm.checkBeforeRegister();
        //vm.toggleErrorAlert();
        //this.$emit('nextToStepThree');
    }
    //hàm xử lý ẩn/hiện alert error
    public toggleErrorAlert() {
        let x = document.getElementById('error');
        if (x.style.display === 'none') {
            x.style.display = 'block';
        } else {
            x.style.display = 'none';
        }

        return;
    }

    //quay trở lại step one
    public prevStepOne() {
        const vm = this;
        vm.$emit('prevStepOne',vm.departureTime,vm.returnTime,vm.appReason);
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
            departureTime: vm.departureTime,
            returnTime: vm.returnTime,
            tripInfos
        };
        // check before registering application
        vm.$http.post('at', API.checkBeforeApply, {
            businessTripInfoOutputDto: vm.data.businessTripInfoOutput,
            businessTripDto: paramsBusinessTrip
        }).then((res: any) => {
            vm.registerData();
        }).catch((res: any) => {
            vm.hidden = true;
            vm.$mask('hide');
            if (_.isEmpty(res.data)) {
                vm.$modal.error({ messageId: 'Msg_1703', messageParams: ['Com_Employment'] }).then(() => vm.$close());
            }

            return;
        }
        );
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
            departureTime: vm.departureTime,
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
                vm.$emit('nextToStepThree',res.data.appID);
                vm.$mask('hide');
            }).catch(() => {
                vm.$modal.error({ messageId: 'Msg_1912' });
            });
        } else {
            vm.$modal.error({ messageId: 'Msg_1703' });

            return ;
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
    updateBusinessTrip : 'at/request/application/businesstrip/mobile/updateBusinessTrip',
};

