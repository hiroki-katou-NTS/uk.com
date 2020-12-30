import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { KafS00DComponent } from '../../s00/d';
import { ScreenMode } from '../../s00/b';
@component({
    name: 'kafs12c',
    route: '/kaf/s12/c',
    style: require('./style.scss'),
    template: require('./index.vue'),
    components: {
        'kaf-s00-d': KafS00DComponent
    },
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KafS12CComponent extends Vue {
    public kafS00DParam: IPramsS00D;

    @Prop({default:() => ({mode: true,data: 'havent data'})})
    public readonly mode!: boolean;

    public beforeCreate() {
        const vm = this;

        vm.kafS00DParam = {
            appID: '',
            mode: ScreenMode.NEW
        };
    }

    public created() {
        const vm = this;

    }
}
export interface IPramsS00D {
    // 画面モード
    mode: ScreenMode;
    appID: string;
}