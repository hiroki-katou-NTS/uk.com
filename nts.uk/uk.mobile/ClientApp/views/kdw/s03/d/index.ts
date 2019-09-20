import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { KdwS03EComponent } from 'views/kdw/s03/e';

@component({
    name: 'kdws03d',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: [],
    components: {
        'kdws03e': KdwS03EComponent,
    },
})
export class KdwS03DComponent extends Vue {
    @Prop({ default: () => ({ employeeID: '', employeeName: '', startDate: new Date(), endDate: new Date() }) })
    public readonly params!: { employeeID: string, employeeName: string, startDate: Date, endDate: Date };
    public rowDatas: Array<RowData> = [];//エラー一覧

    public created() {
        let self = this;
        self.$mask('show');
        //get list error/alarm
        this.$http.post('at', API.getError, {
            startDate: self.params.startDate, 
            endDate: self.params.endDate,
            employeeIDLst: [ self.params.employeeID ],
            attendanceItemID: null
        }).then((data: any) => {
            self.rowDatas = data.data;
            self.$mask('hide');
        }).catch((res: any) => {
            self.$mask('hide');
            self.$modal.error(res.messageId)
                .then(() => {
                    self.$close();
                });
        });
    }

    //エラーを選択する
    public openDialogE(rowData: RowData) {
        let self = this;
        self.$modal('kdws03e', { 
            employeeId: self.params.employeeID, 
            empName: self.params.employeeName, 
            date: new Date(rowData.date), 
            code: rowData.code,
            attendanceItemList: rowData.attendanceItemList
        }, { type : 'dropback' } )
        .then((paramOpenB: any) => {
            if (paramOpenB != undefined && paramOpenB.openB) {
                self.$close(paramOpenB);
            }
            if (paramOpenB != 'NotCloseMenu') {
                this.$close();
            }
        });
    }
}

const API = {
    getError: 'screen/at/correctionofdailyperformance/getErrorMobile'
};

interface RowData {
    date: Date;//日付
    code: string;//コード
    name: string;//エラー内容
    employeeID: string; 
    attendanceItemList: Array<number>;   
}