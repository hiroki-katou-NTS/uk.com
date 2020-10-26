import { Vue } from '@app/provider';
import { component } from '@app/core/component';
import {KafS00AComponent,KAFS00AParams} from '../../s00/a';
import {KafS00BComponent,KAFS00BParams} from '../../s00/b';
import {KafS00CComponent,KAFS00CParams} from '../../s00/c';
import {AppType, KafS00ShrComponent} from '../../s00/shr';
import {IApplication} from '../../s04/a/define';
@component({
    name: 'kafs20a2',
    route: '/kaf/s20/a2',
    style: require('./style.scss'),
    template: require('./index.vue'),
    components: {
        'KafS00AComponent': KafS00AComponent,
        'KafS00BComponent': KafS00BComponent,
        'KafS00CComponent': KafS00CComponent,
    },
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KafS20A2Component extends KafS00ShrComponent {
    public title: string = 'KafS20A2';
    public kafS00AParams!: KAFS00AParams;
    public kafS00BParams!: KAFS00BParams;
    public kafS00CParams!: KAFS00CParams;
    public application!: IApplication;

    public beforeCreate() {
        const vm = this;

        vm.application = {
            appDate: '',
            appID: '',
            appType: 0,
            employeeID: '',
            enteredPerson: '',
            inputDate: '',
            opAppEndDate: '',
            opAppReason: '',
            opAppStandardReasonCD: 0,
            opAppStartDate: '',
            opReversionReason: null,
            opStampRequestMode: null,
            prePostAtr: 0,
            reflectionStatus: null,
            version: null,
        };
    }

    public created() {
        const vm = this;

        vm.$mask('show');
        vm.$auth.user.then((user: any) => {
            vm.$mask('hide');
        }).then(() => {

            return vm.loadCommonSetting(AppType.OPTIONAL_ITEM_APPLICATION);
        }).then((loadData) => {
            if (loadData) {
                //goi API start A2
            }
        });
    }

    public initComponentA() {
        const vm = this;


    }

    public initComponentB() {
        const vm = this;


    }

    public initComponentC() {
        const vm = this;

    }
}