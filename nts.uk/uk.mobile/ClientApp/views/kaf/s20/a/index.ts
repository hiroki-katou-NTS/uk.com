import { Vue } from '@app/provider';
import { component } from '@app/core/component';
import { StepwizardComponent } from '@app/components';
import { KafS20A1Component } from '../a1';
import { KafS20A2Component } from '../a2';
import { KafS20CComponent } from '../c';
import { IOptionalItemAppSet } from './define';

@component({
    name: 'kafs20a',
    route: '/kaf/s20/a',
    style: require('./style.scss'),
    template: require('./index.vue'),
    components: {
        'step-wizard': StepwizardComponent,
        'kaf-s20-a1': KafS20A1Component,
        'kaf-s20-a2': KafS20A2Component,
        'kaf-s20-c': KafS20CComponent,
    },
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KafS20AComponent extends Vue {
    public title: string = 'KafS20A';
    public step: string = 'KAFS20_10';
    public settingNoItems: number[] = [];
    public mode: boolean = false;

    public beforeCreate() {
        const vm = this;

    }

    public created() {
        const vm = this;
    }

    public handleNextToStep2(item: IOptionalItemAppSet) {
        const vm = this;

        vm.step = 'KAFS20_11';
        vm.settingNoItems = item.settingItems.map((settingItem,index,settingItems) => {
            
            return settingItem.no;
        });
    }

    public handleNextToStep3() {
        const vm = this;

        vm.step = 'KAFS20_12';
    }
}