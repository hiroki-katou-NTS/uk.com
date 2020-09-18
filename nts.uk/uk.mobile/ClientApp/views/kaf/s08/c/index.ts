import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';
import {KafS00DComponent} from '../../s00/d';
import { ScreenMode } from 'views/kaf/s00/b';

@component({
    name: 'kafs08c',
    route: '/kaf/s08/c',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    components : {
        'kafs00d' : KafS00DComponent
    },
    constraints: []
})
export class KafS08CComponent extends Vue {
    @Prop({default: () => ({})})
    public title: string = 'KafS08C';
    public kafS00DParams: any = null;
    public params?: any;

    public created() {
        const vm = this;
        vm.kafS00DParams = {
            mode : ScreenMode.NEW,
            appID : '7e4314ed-4776-460c-81c1-20bac9a98093'
        };
    }
}
