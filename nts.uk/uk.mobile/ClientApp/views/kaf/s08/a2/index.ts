import { Vue } from '@app/provider';
import { component } from '@app/core/component';
import { StepwizardComponent } from '@app/components';
import { FixTableComponent } from '@app/components/fix-table';
import {KafS08DComponent} from '../../../kaf/s08/d';

@component({
    name: 'kafs08a2',
    route: '/kaf/s08/a2',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    components: {
        'step-wizard': StepwizardComponent,
        'fix-table': FixTableComponent,
        'showDialog' : KafS08DComponent
    },
    constraints: []
})


export class KafS08A2Component extends Vue {
    public title: string = 'KafS08A2';
    public name: string = 'hello my dialog';
    public step = 'KAFS08_11';
    public showModal(type) {
        let name = this.name;
        this.$modal('showDialog', { name }, {type} );
    }
}