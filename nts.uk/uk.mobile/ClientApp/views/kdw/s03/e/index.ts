import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { storage } from '@app/utils';

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
        code: '',
        name: '',
        errMsg: ''
    };
    public displayE71: boolean = true;

    public created() {
        let self = this;
        self.$mask('show');
        let cache = storage.local.getItem('dailyCorrectionState');

        let param = {
            employeeId: self.params.employeeId,//社員ID
            date: self.params.date,//日
            errCode: self.params.code//エラーアラームコード
        };
        self.$http.post('at', servicePath.getError, param).then((result: any) => {
            self.$mask('hide');
            console.log(result);
            self.errorInfo = {
                code: result.data.code,
                name: result.data.name,
                errMsg: result.data.errMsg
            };
        }).catch(() => {
            self.$mask('hide');
        });
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
    code: string;//コード
    name: string;//エラー内容
    errMsg: string;//メッセージ
}