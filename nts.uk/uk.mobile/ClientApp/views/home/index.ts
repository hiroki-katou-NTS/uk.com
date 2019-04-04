import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    route: '/',
    style: require('./style.scss'),
    template: require('./index.html'),
    resource: require('./resources.json'),
    directives: {
        'focus': focus
    },
    validations: {
        model: {
            name: {
                required: true,
                constraint: 'EmployeeCode'
            },
            addrs: {
                required: true,
                constraint: 'LoginId'
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
    model = {
        name: 'fsdfsd',
        addrs: 'world'
    };

    constructor() {
        super();
        let self = this;

        window['vm'] = self;
    }

    created() {
        this.model.name = '';
    }
}