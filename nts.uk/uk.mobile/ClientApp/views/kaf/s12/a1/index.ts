import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';
import {KafS00ShrComponent,AppType,Application,InitParam} from 'views/kaf/s00/shr';
import { KafS00AComponent, KafS00BComponent, KafS00CComponent } from 'views/kaf/s00';

@component({
    name: 'kafs12a1',
    route: '/kaf/s12/a1',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    components: {
        'kafs00-a': KafS00AComponent,
        'kafs00-b': KafS00BComponent,
        'kafs00-c': KafS00CComponent
    },
    validations: {},
    constraints: []
})
export class KafS12A1Component extends KafS00ShrComponent {
    public title: string = 'KafS12A1';
    public application: Application = null;
    public mode: boolean = true;
    public user: any = null;

    @Prop({default: () => {}})
    public readonly params!: InitParam;

    public created() {
        const vm = this;

        if (vm.params) {
            vm.mode = false;
            vm.appDispInfoStartupOutput = vm.params.appDispInfoStartupOutput;
        }
        if (vm.mode) {
            vm.application = vm.createApplicationInsert(AppType.ANNUAL_HOLIDAY_APPLICATION);
        } else {
            vm.application = vm.createApplicationUpdate(vm.params.appDispInfoStartupOutput.appDetailScreenInfo);
        }
        vm.$auth.user
        .then((user: any) => {
            vm.user = user;
        })
        .then(() => {
            if (vm.mode) {

                return vm.loadCommonSetting(AppType.ANNUAL_HOLIDAY_APPLICATION);
            }

            return true;
        })
        .then((loadData: boolean) => {
            if (loadData) {
                vm.updateKaf000_A_Params(vm.user);
                vm.updateKaf000_B_Params(vm.mode);
                vm.updateKaf000_C_Params(vm.mode);
                if (vm.mode) {
                    return vm.$http.post('at', API.initAppNew, {});  
                }

                return true;
            }
        })
        .then((result: any) => {
            if (result) {

            } else {

            }
        })
        .catch((error: any) => {
            vm.handleErrorCustom(error)
            .then((result) => {
                if (result) {
                    vm.handleErrorCommon(error);
                }
            });
        })
        .then(() => {
            vm.$mask('hide');
        });
    }

    public kaf000BChangeDate(objectDate) {
        const vm = this;
        if (objectDate.startDate) {
            if (vm.mode) {
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
}

const API = {
    initAppNew: 'at/request/application/initApp',
};