import { Vue } from '@app/provider';
import { component, Prop, Watch } from '@app/core/component';
import { IAppDispInfoStartupOutput } from '../../../../../kaf/s04/a/define';
import { vmOf } from 'vue/types/umd';

@component({
    name: 'cmms45shrcomponentsapp15',
    route: '/cmm/s45/shr/components/app15',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class CmmS45ShrComponentsApp15Component extends Vue {
    public title: string = 'CmmS45ShrComponentsApp15';
    @Prop({
        default: () => ({
            appDispInfoStartupOutput: null,
            appDetail: null
        })
    })
    public readonly params!: {
        appDispInfoStartupOutput: IAppDispInfoStartupOutput,
        appDetail: any,
    };

    @Watch('params.appDispInfoStartupOutput')
    public appDispInfoStartupOutputWatcher() {
        const vm = this;
        
        vm.fetchData(vm.params);
    }

    public created() {
        const vm = this;

        vm.params.appDetail = {};
        vm.fetchData(vm.params);
    }

    public fetchData(getParams: any) {
        const vm = this;

        vm.$auth.user.then((user: any) => {
            const { companyId } = user;
            const { params } = vm;

            const { appDispInfoStartupOutput } = params;
            const { appDetailScreenInfo } = appDispInfoStartupOutput;

            const { application } = appDetailScreenInfo;
            const { appID } = application;

            vm.$http.post('at', API.startBScreen, {
                companyId,
                applicationId: appID
            }).then((res: any) => {
                vm.params.appDetail = res.data;
                console.log(vm.params.appDetail);
                vm.$emit('loading-complete');
            });
        });
    }
}

const API = {
    startBScreen: 'ctx/at/request/application/optionalitem/getDetail'
};