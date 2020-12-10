import { Vue } from '@app/provider';
import {StepwizardComponent} from '@app/components';
import { component } from '@app/core/component';

@component({
    name: 'kafs12a',
    route: '/kaf/s12/a',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    components: {
        'step-wizard' : StepwizardComponent
    },
    validations: {},
    constraints: []
})
export class KafS12AComponent extends Vue {
    public step: string = 'KAFS12_1';
    public mode: boolean = true;
}