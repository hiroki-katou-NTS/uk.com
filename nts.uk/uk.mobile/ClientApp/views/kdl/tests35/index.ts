import { Vue } from '@app/provider';
import { component } from '@app/core/component';
import {KdlS35Component} from '../s35/';

@component({
    name: 'kdltests35',
    route: '/kdl/tests35',
    style: require('./style.scss'),
    template: require('./index.vue'),
    components: {
        'kdls35': KdlS35Component
    },
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KdlTests35Component extends Vue {
    public title: string = 'KdlTests35';

    public created() {
        const vm = this;

    }

    public openKDLS35() {
        const vm = this;

        vm.$modal('kdls35',{});
    }
}