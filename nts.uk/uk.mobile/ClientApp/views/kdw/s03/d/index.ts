import { Vue, moment } from '@app/provider';
import { component, Prop } from '@app/core/component';

@component({
    name: 'kdws03d',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KdwS03DComponent extends Vue {
    @Prop({ default: () => ({ employeeName: '', date: new Date(), data: {}, contentType: {} }) })
    public readonly params!: { employeeName: '', date: Date };
    public title: string = 'KdwS03D';
    public rowDatas: Array<RowData> = [];

    public created() {
        let self = this;
        self.$mask('show');
        this.$http.post('at', API.getError, {
            startDate: new Date('2019-08-19'), 
            endDate: new Date('2019-08-19'),
            employeeLst: [{'employeeID': '292ae91c-508c-4c6e-8fe8-3e72277dec16', 'employeeCD': '000001'}],
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

    public mounted() {
        
    }

    public openDialogE(index: number) {

    }
}

const API = {
    getError: 'screen/at/correctionofdailyperformance/getErrorMobile'
};

interface RowData {
    date: Date;
    employeeCD: string;
    name: string;
    employeeID: string;    
}