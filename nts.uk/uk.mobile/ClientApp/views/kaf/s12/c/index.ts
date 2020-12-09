import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    name: 'kafs12c',
    route: '/kaf/s12/c',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KafS12CComponent extends Vue {
    public title: string = 'KafS12C';
}