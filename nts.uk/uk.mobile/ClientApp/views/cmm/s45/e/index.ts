import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    name: 'cmms45e',
    route: '/cmm/s45/e',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class CmmS45EComponent extends Vue {
    public title: string = 'CmmS45E';
}