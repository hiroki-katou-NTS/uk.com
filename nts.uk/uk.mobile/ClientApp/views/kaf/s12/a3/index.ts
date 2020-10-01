import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    name: 'kafs12a3',
    route: '/kaf/s12/a3',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KafS12A3Component extends Vue {
    public title: string = 'KafS12A3';
}