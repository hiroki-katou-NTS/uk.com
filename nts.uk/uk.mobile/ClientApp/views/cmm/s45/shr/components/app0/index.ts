import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';

@component({
    name: 'cmms45shrcomponentsapp0',
    route: '/cmm/s45/shr/components/app0',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class CmmS45ShrComponentsApp0Component extends Vue {
    public title: string = 'CmmS45ShrComponentsApp0';
    @Prop({
        default: () => ({
            appDispInfoStartupOutput: null,
            appDetail: null
        })
    })
    public readonly params: {
        appDispInfoStartupOutput: any,
        appDetail: any
    };
    public user: any;
    public dataOutput: any;

    public created() {
        const self = this;
        self.$auth.user.then((usr: any) => {
            self.user = usr;
        }).then((res: any) => {
            self.fetchData(self.params);
        });
        self.$watch('params.appDispInfoStartupOutput', (newV, oldV) => {
            self.fetchData(self.params);
        });
    }
    public fetchData(getParams: any) {
        const self = this;
        self.$http.post('at', API.start, self.commandStart()).then((res: any) => {
            self.dataOutput = res.data;
            self.bindComponent();
            self.params.appDetail = self.dataOutput;
            self.$emit('loading-complete');
        }).catch((res: any) => {
            self.$modal.error({ messageId: res.messageId, messageParams: res.parameterIds });
            self.$emit('loading-complete');
        });
    }
    public bindComponent() {

    }
    public commandStart() {
        const self = this;

        return {
            companyId: self.user.companyId,
            appId: self.params.appDispInfoStartupOutput.appDetailScreenInfo.application.appID,
            appDispInfoStartup: self.params.appDispInfoStartupOutput
        };
    }
}
const API = {
    start: 'at/request/application/overtime/mobile/getDetail'
};