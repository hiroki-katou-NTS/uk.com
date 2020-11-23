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
    public checkbox: number = 1;
    public date: Date = new Date();

    public created() {
        const vm = this;


    }

    public mounted() {
        const vm = this;

        
    }

    public back() {
        const vm = this;

        vm.$close();
    }

    public decide() {
        const vm = this;

        alert('You clicked button decide');
    }
}