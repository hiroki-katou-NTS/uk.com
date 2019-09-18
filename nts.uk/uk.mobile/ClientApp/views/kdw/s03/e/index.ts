import { Vue, _ } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { storage } from '@app/utils';
import { LoDashStatic } from 'lodash';

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

    @Prop({ default: () => ({ employeeId: '', empName: '', date: new Date(), code: '', attendanceItemList: []}) })
    public readonly params: { employeeId: string, empName: string, date: Date, code: string, attendanceItemList: Array<number> };

    public errorInfo: IErrorInfo = {
        code: '',
        name: '',
        errMsg: ''
    };
    public displayE71: boolean = true;

    public created() {
        let self = this;
        self.$mask('show');
        //A画面のキャッシュを取得する
        let cache: any = storage.local.getItem('dailyCorrectionState');
        self.displayE71 = self.checkEsxit(cache.headerLst, self.params.attendanceItemList);
        console.log(self.params.attendanceItemList);
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
            // if ()
        }).catch(() => {
            self.$mask('hide');
        });
    }
    //修正画面に遷移できるかチェックする
    public checkEsxit(lstA: Array<any>, lstErr: Array<number>) {
        for (let k = 0; k < lstErr.length; k++) {
            let esxit1 = false;
            for (let i = 0; i < lstA.length; i++) {
                console.log(lstA[i].key);
                if (lstA[i].key === 'A' + lstErr[k]) {
                    esxit1 = true;
                    break;
                }
            }
            if (!esxit1) {return false;}
        }

        return true;
    }
    //実績を修正する
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