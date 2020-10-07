import { Vue } from '@app/provider';
import { component } from '@app/core/component';
import { KafS00DComponent } from '../../s00/d';
import { ScreenMode } from '../../s00/b';

@component({
    name: 'kafs04a1',
    route: '/kaf/s04/a1',
    style: require('./style.scss'),
    template: require('./index.vue'),
    components: {
        'kaf-s00-d': KafS00DComponent
    },
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KafS04A1Component extends Vue {
    
    public kafS00DParams: IParamS00D = null;

    public created() {
        const vm = this;

        vm.initS00DComponent();   
    }

    public initS00DComponent() {
        const vm = this;
        
        vm.kafS00DParams = {
            appID: '50ba21bc-1b6d-4cb8-b706-828c136ba210',
            mode: ScreenMode.NEW,
        };
    }
}

interface IParamS00D {
     // 画面モード
     mode: ScreenMode;
     // 申請ID
     appID: string;
}
