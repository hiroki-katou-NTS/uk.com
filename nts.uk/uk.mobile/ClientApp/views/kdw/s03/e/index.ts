import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';

@component({
    name: 'kdws03e',
    route: '/kdw/s03/e',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KdwS03EComponent extends Vue {
    public title: string = 'KdwS03E';

    @Prop({ default: () => ({ employeeId: '292ae91c-508c-4c6e-8fe8-3e72277dec16', empName: 'NV000001', date: new Date('2019/08/01'), code: 'S001'}) })
    public readonly params: { employeeId: string, empName: string, date: Date, code: string };

    public errorInfo: IErrorInfo = {
        code: '001',
        name: 'error1',
        errMsg: 'msg1'
    };
    public displayE71: boolean = true;

    public created() {
        let self = this;
        let param = {
            employeeId: self.params.employeeId,
            date: self.params.date,
            errCode: self.params.code
        };
        self.$http.post('at', servicePath.getError, param).then((result: any) => {
            console.log(result);
            self.errorInfo = {
                code: result.data.code,
                name: result.data.name,
                errMsg: result.data.errMsg
            };
        });
    }
    public backD() {
        this.$close();
    }

    public editData() {
        let self = this;
        let paramOpenB = {
            openB: true,
            employeeId: self.params.employeeId,
            date: self.params.date
        };
        self.$close(paramOpenB);
    }

    
}
const servicePath = {
    getError: 'screen/at/dailyperformance/error-detail'
};

interface IErrorInfo {
    code: string;
    name: string;
    errMsg: string;
}