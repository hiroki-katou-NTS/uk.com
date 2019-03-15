import { Vue } from '@app/provider';
import { component } from '@app/core/component';

import { focus } from '@app/directives';
import { SampleComponent } from '@app/components';

@component({
    route: '/',
    style: require('./style.scss'),
    template: require('./index.html'),
    resource: require('./resources.json'),
    directives: {
        'focus': focus
    },
    components: {
        'vuong': SampleComponent,
    },
    validations: {
        title: {
            required: true,
            minLength: 10,
            constraint: 'EmployeeCode',
            employeeCode: {
                test: /\d+/,
                message: ''
            }
        },
        resource: {
            required: true,
            alpha: true
        },
        model: {
            name: {
                required: true
            },
            addrs: {
                required: true,
                self_validator: {
                    test: /^\d{3,5}$/,
                    message: 'xxxx'
                },
                dev_def: {
                    test: function (value) {
                        return false;
                    },
                    message: 'msg_90'
                }
            },
            address: {
                province: {
                    required: true
                },
                district: {
                    required: true,

                }
            },
            office: {
                head: {
                    required: true
                }
            }
        }
    },
    constraints: [
        'nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeCode',
        'nts.uk.ctx.sys.gateway.dom.login.UserName',
        'nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber',
        'nts.uk.ctx.sys.gateway.dom.login.LoginId'
    ]
})
export class HomeComponent extends Vue {
    title: string = 'home';

    resource: string = '';

    model = {
        name: '',
        addrs: 'world'
    };


    disabled: boolean = false;

    constructor() {
        super();
        let self = this;
    }



    alertNow() {
        //console.log(this.validations);
        //alert(this.$i18n(this.title));
        //this.$router.push({ path: '/about/me' });

        //this.disabled = true;
        /*
        this.$http
            .get('/about/me')
            .then((value: any) => {
                debugger;
            });*/

        this.$updateValidator({
            title: {
                minLength: 100
            }
        });

        this.$modal('vuong', { id: 100, name: 'Nguyen Van A' })
            .onClose((data: any) => {
                console.log(data);
            });
    }
}