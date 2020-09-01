import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { storage, auth } from '@app/utils';
import { NavMenu, SideMenu } from '@app/services';

@component({
    route: '/ccg/007/a',
    template: require('./index.vue'),
    validations: {
        model: {
            contractCode: {
                required: true
            },
            password: {
                required: true
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
        this.$nextTick(() => {
            this.$parent.$el.querySelector('.container-fluid').classList.add('uk-bg-process');
        });
    }

    public authContract() {
        this.$validate();
        if (!this.$valid) {
            return;
        }
        this.$http.post('ctx/sys/gateway/login/submitcontract', this.model).then((response) => {
            console.log('success', response);

            return;
            storage.local.setItem('contract', { code: this.model.contractCode, password: this.model.password });
            this.$router.go(-1);
        }).catch((error) => {
            console.log('failed', error);

            return;
        });
    }

    public beforeDestroy() {
        this.$parent.$el.querySelector('.container-fluid').classList.remove('uk-bg-process');
    }

}