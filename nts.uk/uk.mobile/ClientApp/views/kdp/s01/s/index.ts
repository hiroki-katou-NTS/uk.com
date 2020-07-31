import { component, Prop } from '@app/core/component';
import { _, Vue, moment } from '@app/provider';

const servicePath = {
    getAllWkType: 'at/share/worktype/get_not_remove_work_type',
    checkWorkTimeSettingNeeded: 'at/schedule/basicschedule/isWorkTimeSettingNeeded/'
};

@component({
    name: 'kdpS01s',
    route: '/kdp/s01/s',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    constraints: []
})


export class KdpS01SComponent extends Vue {
    @Prop({ default: () => ({}) })
    public params!: any;

    public setting: any = {
        items: [
            {
                id: 1,
                date: '15(金)',
                symbol: 'w',
                time: '9:25',
                stampType: '出勤',
                textAlign:'left'
            },
            {
                id: 2 ,
                date: '14(木)',
                symbol: 'w',
                time: '12:25',
                stampType: '退勤',
                textAlign:'right'
            },
            {
                id: 3,
                date: '13(木)',
                symbol: 'w',
                time: '15:25',
                stampType: '退門',
                textAlign:'center'
            },
            {
                id: 4,
                date: '12(木)',
                symbol: 'w',
                time: '16:25',
                stampType: '外出(私用)',
                textAlign:'center'
            },
            {
                id: 5,
                date: '11(木)',
                symbol: 'w',
                time: '17:25',
                stampType: '出勤',
                textAlign:'center'
            }
        ]
    };

    public created() {



    }

    public login() {


    }

    public mounted() {
    }
}
interface Iitem {
    id: number;
    date: string;
    symbol: string;
    time: string;
    stampType: string;
}