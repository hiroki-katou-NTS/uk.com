import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    name: 'kafs04a',
    route: '/kaf/s04/a',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KafS04AComponent extends Vue {
    public title: string = 'KafS04A';
}