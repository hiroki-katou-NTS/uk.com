import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    route: '/',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class HomeComponent extends Vue { }