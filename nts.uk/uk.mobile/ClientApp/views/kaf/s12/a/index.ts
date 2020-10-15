import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    name: 'kafs12a',
    route: '/kaf/s12/a',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KafS12AComponent extends Vue {
    public title: string = 'KafS12A';
}