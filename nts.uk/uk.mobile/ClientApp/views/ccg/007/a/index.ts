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
            storage.local.setItem('contract', { code: this.model.contractCode, password: this.model.password });
            this.$router.go(-1);
        }).catch((error) => {
            this.$modal.error({ messageId: error.messageId });
        });
    }

    public beforeDestroy() {
        this.$parent.$el.querySelector('.container-fluid').classList.remove('uk-bg-process');
    }

}