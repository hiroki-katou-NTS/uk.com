import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';
import {IAppDispInfoStartupOutput} from '../../../../../kaf/s04/a/define';

@component({
    name: 'cmms45componentsapp9',
    template: require('./index.vue'),
    validations: {},
    constraints: []
})
export class CmmS45ComponentsApp9Component extends Vue {
    public appDispInfoStartupOutput!: IAppDispInfoStartupOutput;

    @Prop({
        default: () => ({
            appDispInfoStartupOutput: null,
            appDetail: null
        })
    }) public readonly params!: {
        appDispInfoStartupOutput: IAppDispInfoStartupOutput,
        appDetail: any,
    };
    public created() {
        const vm = this;
        vm.params.appDetail = {};

        let params = {
            appId: vm.params.appDispInfoStartupOutput.appDetailScreenInfo.application.appID,
            infoStartup: vm.params.appDispInfoStartupOutput,
        };

        vm.$auth.user.then((user: any) => {
            vm.$http.post('at', API.startDetailBScreen, params).then((res: any) => {
                console.log(res.data);
                vm.params.appDetail = res.data;
            }).catch(() => {

            });
        });

    }


    public mounted() {
        const vm = this;


    }

}

const API = {
    startDetailBScreen: 'at/request/application/lateorleaveearly/initPageB',
};