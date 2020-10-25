import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    name: 'kafs20a2',
    route: '/kaf/s20/a2',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KafS20A2Component extends Vue {
    public title: string = 'KafS20A2';
}