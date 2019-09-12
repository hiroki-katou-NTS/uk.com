import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    name: 'kdws03f',
    route: '/kdw/s03/f',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KdwS03FComponent extends Vue {
    public title: string = 'KdwS03F';



    
}