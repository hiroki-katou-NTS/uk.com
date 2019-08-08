import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    name: 'cmms45f',
    route: '/cmm/s45/f',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class CmmS45FComponent extends Vue {
    public title: string = 'CmmS45F';
}