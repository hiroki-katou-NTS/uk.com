import { Vue } from '@app/provider';
import { component } from '@app/core/component';
import { IOptionalItemAppSet } from '../a/define';
import { KafS20ModalComponent } from '../modal';

@component({
    name: 'kafs20a1',
    route: '/kaf/s20/a1',
    style: require('./style.scss'),
    template: require('./index.vue'),
    components: {
        'ModalDescription': KafS20ModalComponent
    },
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KafS20A1Component extends Vue {
    public title: string = 'KafS20A1';
    //public lstTest: string[] = ['Test 1', 'Test 2', 'Test 3', 'Test 4', 'Test 5'];
    public listOptionalItemAppSet: IOptionalItemAppSet[] = [];
    
    public beforeCreate() {
        const vm = this;


    }

    public created() {
        const vm = this;

        vm.$mask('show');
        vm.$http.post('at', API.startA1Screen).then((res: any) => {
            vm.$mask('hide');

            vm.listOptionalItemAppSet = res.data;
        });
    }

    public nextToStep2() {
        const vm = this;

        vm.$emit('nextToStep2');
    }

    public showDescription(item) {
        const vm = this;

        vm.$modal('ModalDescription',item,{ size: 'sm', animate: 'left',type: 'dropback'})
        .then(() => {

        });
    }
}

const API = {
    startA1Screen: 'screen/at/kaf020/a/get',
};