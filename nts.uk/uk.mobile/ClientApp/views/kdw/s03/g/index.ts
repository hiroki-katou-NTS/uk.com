import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    name: 'kdws03g',
    route: '/kdw/s03/g',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KdwS03GComponent extends Vue {
    public title: string = 'KdwS03G';
}