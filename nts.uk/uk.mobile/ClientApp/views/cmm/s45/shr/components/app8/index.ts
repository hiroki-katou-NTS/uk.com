import { Vue } from '@app/provider';
import { component, Prop, Watch } from '@app/core/component';
import { InitParam } from 'views/kaf/s00';

@component({
    name: 'cmms45shrcomponentsapp8',
    // style: require('./style.scss'),
    template: require('./index.vue'),
    // resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class CmmS45ShrComponentsApp8Component extends Vue {
    @Prop({default: () => ({}) })
    public readonly params: InitParam;

    public user: any = null;

    public created() {
        const vm = this;
    }

    public mounted() {
        const vm = this;
        vm.$auth.user.then((usr: any) => {
            vm.user = usr;
        }).then((res: any) => {
            this.fetchData(vm.params);
        });
    }

    @Watch('params.appDispInfoStartupOutput')
    public appDispInfoWatcher(value: any) {
        const vm = this;
        vm.fetchData(value);
    }

    public fetchData(appDispInfoStartupOutput: any) {
        const vm = this;
        const initParams = {
            appId: appDispInfoStartupOutput.appDetailScreenInfo.application.appID,
            appDispInfoStartupOutput
        };
        vm.$http.post('at', API.init, initParams).then((res: any) => {
            if (res) {
                vm.params.appDetail = res.data;
                vm.$emit('loading-complete');
            }
        }).catch((error: any) => {
            vm.$modal.error(error);
        });
    }
}

const API = {
    init: 'at/request/application/timeLeave/init',
};