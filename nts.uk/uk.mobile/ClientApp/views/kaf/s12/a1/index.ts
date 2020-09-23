import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    name: 'kafs12a1',
    route: '/kaf/s12/a1',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KafS12A1Component extends Vue {
    public title: string = 'KafS12A1';
}