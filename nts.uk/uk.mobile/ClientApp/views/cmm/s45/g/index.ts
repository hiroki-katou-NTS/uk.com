import { Vue, _ } from '@app/provider';
import { component, Prop } from '@app/core/component';

@component({
    name: 'cmms45g',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class CmmS45GComponent extends Vue {
    @Prop({ default: () => ({ action: 0, listAppMeta: [], currentApp: '' }) })
    public readonly params: any;
    public title: string = 'CmmS45G';
    public isPreError: boolean = true;
    public topComment: string = '';
    public bottomComment: string = '';
    public appContent: string = '';

    public created() {
        const vm = this;
        vm.appContent = _.escape(vm.params.appContent).replace(/\n/g, '<br/>');
        if (vm.params.opBackgroundColor == 'bg-pre-application-excess') {
            vm.topComment = _.escape(vm.$i18n('CMMS45_97')).replace(/\n/g, '<br/>');
            vm.bottomComment = vm.$i18n('CMMS45_98');
            vm.isPreError = true;
        } else {
            vm.topComment = _.escape(vm.$i18n('CMMS45_99')).replace(/\n/g, '<br/>');
            vm.bottomComment = vm.$i18n('CMMS45_100');
            vm.isPreError = false;
        }
    }

    public confirm() {
        const vm = this;
        vm.$close({
            confirm: true,
            confirmAllPreApp: vm.params.confirmAllPreApp, 
            notConfirmAllPreApp: vm.params.notConfirmAllPreApp, 
            confirmAllActual: vm.params.confirmAllActual, 
            notConfirmAllActual: vm.params.notConfirmAllActual
        });
    }

    public notConfirm() {
        const vm = this;
        vm.$close({
            confirm: false,
            confirmAllPreApp: vm.params.confirmAllPreApp, 
            notConfirmAllPreApp: vm.params.notConfirmAllPreApp, 
            confirmAllActual: vm.params.confirmAllActual, 
            notConfirmAllActual: vm.params.notConfirmAllActual
        });
    }
    
    public confirmAll() {
        const vm = this;
        vm.$close({
            confirm: true,
            confirmAllPreApp: vm.params.opBackgroundColor == 'bg-pre-application-excess' ? true : vm.params.confirmAllPreApp, 
            notConfirmAllPreApp: vm.params.notConfirmAllPreApp, 
            confirmAllActual: vm.params.opBackgroundColor != 'bg-pre-application-excess' ? true : vm.params.confirmAllActual, 
            notConfirmAllActual: vm.params.notConfirmAllActual
        });
    }
    
    public notConfirmAll() {
        const vm = this;
        vm.$close({
            confirm: false,
            confirmAllPreApp: vm.params.confirmAllPreApp, 
            notConfirmAllPreApp: vm.params.opBackgroundColor == 'bg-pre-application-excess' ? true : vm.params.notConfirmAllPreApp, 
            confirmAllActual: vm.params.confirmAllActual, 
            notConfirmAllActual: vm.params.opBackgroundColor != 'bg-pre-application-excess' ? true : vm.params.notConfirmAllActual
        });
    }
}