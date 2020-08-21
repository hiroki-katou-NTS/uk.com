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
    public date: string = '1/1/2020';

    @Prop({ default: () => ({ id: 0, name: ''})})
    public readonly params!: ParamData;
    public params1!: IDate;

    public onChangeTitle(str: string)  {
        const vm = this;

        vm.title = str;

        console.log(vm.params.name);
    }

    public changeDateSetting(dates: string) {
        const vm = this;
        vm.date = dates;
    }
}

interface ParamData {
    id: number; 
    name: string;
}

interface IDate {
    id: number;
    date: string;
}