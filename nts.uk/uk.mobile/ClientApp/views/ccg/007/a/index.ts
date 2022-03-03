import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { storage, auth } from '@app/utils';
import { NavMenu, SideMenu } from '@app/services';

@component({
    route: '/ccg/007/a',
    style: require('./style.scss'),
    template: require('./index.vue'),
    validations: {
        model: {
            contractCode: {
                required: false
            },
            password: {
                required: false
            }
        }
    },
    name: 'ccg007a'
})
export class Ccg007AComponent extends Vue {

    @Prop({ default: () => ({}) })
    public params!: any;

    public model = {
        contractCode: '',
        password: ''
    };

    public mounted() {
        const vm = this;

        NavMenu.visible = false;
        SideMenu.visible = false;

        vm.$nextTick(() => {
            vm.$parent.$el.querySelector('.container-fluid').classList.add('uk-bg-process');
            // オンプレミスかをチェックする
            vm.$http.post('at', servicePath.getIsCloud).then((response: any) => {
                if (!response.data) {
                    // 契約コードに関するlocalstrageに登録する
                    vm.$http.post('at', servicePath.getContractCode).then((response: any) => {
                        storage.local.setItem('contract', { code: response.data.code, password: '' });
                        vm.model.contractCode = response.data.code;
                        vm.model.password = '';
                        vm.$goto('ccg007b');
                    });
                } else {
                    // loaclstorageに契約コードが設定されているかをチェックする
                    const contract = storage.local.getItem('contract') as any;
                    if (!!contract && contract.code !== null && contract.password !== null) {
                        vm.model.contractCode = contract.code;
                        vm.model.password = contract.password;
                        vm.$goto('ccg007b');
                    }
                }
            });
        });
    }

    public authContract() {
        const vm = this;
        vm.$mask('show', { message: true });
        vm.$validate();
        if (!vm.$valid) {
            vm.$mask('hide');

            return;
        }
        vm.$http.post(servicePath.submitcontract, vm.model).then((response) => {
            vm.$mask('hide');
            storage.local.setItem('contract', { code: vm.model.contractCode, password: vm.model.password });
            vm.$goto('ccg007b');
        }).catch((error) => {
            vm.$mask('hide');
            vm.$modal.error({ messageId: error.messageId });
        });
    }

    public beforeDestroy() {
        const vm = this;
        vm.$parent.$el.querySelector('.container-fluid').classList.remove('uk-bg-process');
    }

}

const servicePath = {
    submitcontract: 'ctx/sys/gateway/login/submitcontract',
    getIsCloud: 'at/record/stamp/finger/get-isCloud',
    getContractCode: 'at/record/stamp/finger/get-contractCode'
};