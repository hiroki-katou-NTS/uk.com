import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    name: 'kafs08c',
    route: '/kaf/s08/c',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KafS08CComponent extends Vue {
    public title: string = 'KafS08C';
}