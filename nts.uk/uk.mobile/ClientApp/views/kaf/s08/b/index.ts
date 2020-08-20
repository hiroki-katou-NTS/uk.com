import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';

import { CpComponent } from './child';

@component({
    name: 'kafs08b',
    route: '/kaf/s08/b',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: [],
    components: {
        child: CpComponent
    }
})
export class KafS08BComponent extends Vue {
    public title: string = 'KafS08B';

    @Prop({ default: () => ({ id: 0, name: ''})})
    public readonly params!: ParamData;

    public onChangeTitle(str: string)  {
        const vm = this;

        vm.title = str;

        vm.params.id
    }
}

interface ParamData {
    id: number; 
    name: string;
}