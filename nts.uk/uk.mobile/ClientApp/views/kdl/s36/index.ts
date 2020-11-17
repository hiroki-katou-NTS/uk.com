import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    name: 'kdls36',
    route: '/kdl/s36',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KdlS36Component extends Vue {
    public title: string = 'KdlS36';
}