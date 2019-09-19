import { Vue, moment } from '@app/provider';
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
    public title: string = 'KdwS03D';
    public rowDatas: Array<RowData> = [];

    public created() {
        let self = this;
        self.$mask('show');
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

    public openDialogE(rowData: RowData) {
        let self = this;
        self.$modal('kdws03e', { 
            employeeId: self.params.employeeID, 
            empName: self.params.employeeName, 
            date: new Date(rowData.date), 
            code: rowData.code,
            attendanceItemList: rowData.attendanceItemList
        }, { type : 'dropback' } )
        .then((v) => {

        });
    }
}

const API = {
    getError: 'screen/at/correctionofdailyperformance/getErrorMobile'
};

interface RowData {
    date: Date;
    code: string;
    name: string;
    employeeID: string; 
    attendanceItemList: Array<number>;   
}