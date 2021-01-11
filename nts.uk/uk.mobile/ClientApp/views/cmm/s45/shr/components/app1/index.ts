import { Vue, _ } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { AppForLeaveStartOutputDto } from 'views/kaf/s06/a/define.interface';

@component({
    name: 'cmms45shrcomponentsapp1',
    route: '/cmm/s45/shr/components/app1',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class CmmS45ShrComponentsApp1Component extends Vue {
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
    public title: string = 'CmmS45ShrComponentsApp1';

    public dataOutput = {} as AppForLeaveStartOutputDto;

    public user: any;
    public created() {
        const self = this;

        
    }

    public mounted() {
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

        let command = {} as any;
        self.$http.post('at', API.getDetailInfo, command)
                    .then((res: any) => {
                        self.dataOutput = res.data;

                        self.bindComponent();
                    })
                    .catch((res: any) => {
                        self.$modal.error({ messageId: res.messageId, messageParams: res.parameterIds });
                    })
                    .then(() => self.$emit('loading-complete'));
    }
    public bindComponent() {
        const self = this;


    }
}
const API = {
    getDetailInfo: 'at/request/application/appforleave/mobile/getDetailInfo'
};