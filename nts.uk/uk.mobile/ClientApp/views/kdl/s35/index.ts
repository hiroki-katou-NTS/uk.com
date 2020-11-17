import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    name: 'kdls35',
    route: '/kdl/s35',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KdlS35Component extends Vue {
    public title: string = 'KdlS35';
}