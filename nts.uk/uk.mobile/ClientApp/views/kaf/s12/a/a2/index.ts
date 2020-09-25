import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    name: 'kafs12aa2',
    route: '/kaf/s12/a/a2',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KafS12AA2Component extends Vue {
    public title: string = 'KafS12AA2';
}