import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    name: 'kafs06a1',
    route: '/kaf/s06/a1',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KafS06A1Component extends Vue {
    public title: string = 'KafS06A1';
}