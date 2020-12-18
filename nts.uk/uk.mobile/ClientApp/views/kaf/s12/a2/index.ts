import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    name: 'kafs12a2',
    route: '/kaf/s12/a2',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KafS12A2Component extends Vue {

    public create() {
        const vm = this;

    }
}
