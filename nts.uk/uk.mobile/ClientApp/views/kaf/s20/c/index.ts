import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    name: 'kafs20c',
    route: '/kaf/s20/c',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KafS20CComponent extends Vue {
    public title: string = 'KafS20C';
}