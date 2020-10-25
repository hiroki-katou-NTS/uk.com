import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    name: 'kafs20a1',
    route: '/kaf/s20/a1',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KafS20A1Component extends Vue {
    public title: string = 'KafS20A1';
    public lstTest: string[] = ['Test 1','Test 2','Test 3','Test 4','Test 5'];

    public beforeCreate() {
        const vm = this;


    }

    public created() {
        const vm = this;

    }

    public nextToStep2(index) {
        const vm = this;

        vm.$emit('nextToStep2',{
            data: vm.lstTest,
            indext: index,
        });
    }
}