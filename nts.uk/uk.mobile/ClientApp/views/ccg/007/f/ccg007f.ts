import { Vue, _ } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { SideMenu, NavMenu } from '@app/services';

@component({
    route: '/ccg/007/f',
    style: require('./style.scss'),
    template: require('./index.html'),
    validations: {
        model: {
            comp: {
                required: true
            },
            employeeCode: {
                required: true
            }
        }
    }, 
    name: 'mailSent'
})
export class MailSentComponent extends Vue {
    
    @Prop({ default: () => ({}) })
    public params!: any;
    
    public contractCode: string = '';
    public compapyCode: string = '';
    public employeeCode: string = '';
    
    public created() {
        this.contractCode = this.params.contractCode;
        this.compapyCode = this.params.companyCode;
        this.employeeCode = this.params.employeeCode;
    }

    public mounted() {
        // Hide top & side menu
        NavMenu.visible = false;
        SideMenu.visible = false;
    }

    public destroyed() {
        // Show menu
        NavMenu.visible = true;
        SideMenu.visible = true;
    }

    public toLogin() {
        this.$goto({ name: 'login', params: { companyCode: this.compapyCode, 
                                                        employeeCode: this.employeeCode,
                                                        contractCode: this.contractCode
                                                }});
    }
}