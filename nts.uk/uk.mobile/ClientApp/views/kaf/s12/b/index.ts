import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    name: 'kafs12b',
    route: '/kaf/s12/b',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KafS12BComponent extends Vue {
    public title: string = 'KafS12B';
}