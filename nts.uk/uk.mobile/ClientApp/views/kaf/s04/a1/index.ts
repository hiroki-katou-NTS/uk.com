import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    name: 'kafs04a1',
    route: '/kaf/s04/a1',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KafS04A1Component extends Vue {
    public title: string = 'KafS04A1';
}