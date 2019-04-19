import { Vue, _ } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { SideMenu, NavMenu } from '@app/services';

@component({
    route: '/ccg/007/f',
    style: require('./style.scss'),
    resource: require('./resources.json'),
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
    params!: any;
    
    contractCode: string = '';
    compapyCode: string = '';
    employeeCode: string = '';
    
    created() {
        this.contractCode = this.params.contractCode;
        this.compapyCode = this.params.companyCode;
        this.employeeCode = this.params.employeeCode;
    }

    mounted(){
        // Hide top & side menu
        NavMenu.visible = false;
        SideMenu.visible = false;
    }

    destroyed() {
        // Show menu
        NavMenu.visible = true;
        SideMenu.visible = true;
    }

    toLogin() {
        this.$goto({ name: 'login', params: { companyCode: this.compapyCode, 
                                                        employeeCode: this.employeeCode,
                                                        contractCode: this.contractCode
                                                }});
    }
}