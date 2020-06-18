import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    name: 'kafs07b',
    route: '/kaf/s07/b',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KafS07BComponent extends Vue {
    public title: string = 'KafS07B';
}