import { Vue } from '@app/provider';
import { component } from '@app/core/component';
import { KafS04AComponent } from '../a';
import { KafS04A1Component } from '../a1';
import {
    IParams,
} from '../a/define';

@component({
    name: 'kafs04a0',
    route: '/kaf/s04/a0',
    style: require('./style.scss'),
    template: require('./index.vue'),
    components: {
        'kaf-s04-a': KafS04AComponent,
        'kaf-s04-a1': KafS04A1Component,
    },
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KafS04A0Component extends Vue {

    public showComponentA1: boolean = false;
    public showComponentA: boolean = true;

    public paramsAComponent: IParams = {
        appID: '',
        mode: true,
        res: {
            appDispInfoStartupOutput: {
                appDetailScreenInfo: {
                    alternateExpiration: false,
                    application: {
                        version: 1,
                        appID: '',
                        prePostAtr: 1,
                        employeeID: '',
                        appType: 9,
                        appDate: '',
                        enteredPerson: '',
                        inputDate: '',
                        reflectionStatus: {
                            listReflectionStatusOfDay: [],
                        },
                        opStampRequestMode: null,
                        opReversionReason: '',
                        opAppStartDate: '',
                        opAppEndDate: '',
                        opAppReason: '',
                        opAppStandardReasonCD: 1,
                    },
                    approvalATR: 0,
                    approvalLst: [],
                    authorComment:'',
                    authorizableFlags: true,
                    outputMode: 0,
                    reflectPlanState: 0,
                    user: 0,
                },
                appDispInfoNoDateOutput: null,
                appDispInfoWithDateOutput: null,
            },
            arrivedLateLeaveEarly: null,
            earlyInfos: null,
            info: '',
            lateEarlyCancelAppSet: {
                cancelAtr: 1,
                companyId: ''
            }
        }
    };

    public created() {
        const vm = this;

    }

    public handlNextComponentA0(paramsAComponent) {
        const vm = this;

        vm.paramsAComponent = paramsAComponent;
        vm.showComponentA = false;
        vm.showComponentA1 = true;
    }

    public handleShowComponentA(mode, res) {
        const vm = this;

        console.log(res);
        vm.paramsAComponent.res = res;
        vm.paramsAComponent.mode = mode;
        vm.showComponentA1 = false;
        vm.showComponentA = true;
    }
}