import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    name: 'kdws03e',
    route: '/kdw/s03/e',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KdwS03EComponent extends Vue {
    public title: string = 'KdwS03E';
}