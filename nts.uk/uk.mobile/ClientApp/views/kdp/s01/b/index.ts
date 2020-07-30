import { component, Prop } from '@app/core/component';
import { _, Vue } from '@app/provider';

const servicePath = {
    getAllWkType: 'at/share/worktype/get_not_remove_work_type',
    checkWorkTimeSettingNeeded: 'at/schedule/basicschedule/isWorkTimeSettingNeeded/'
};

@component({
    name: 'kdpS01b',
    route: '/kdp/s01/b',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    constraints: []
})


export class KdpS01BComponent extends Vue {
    @Prop({ default: () => ({}) })
    public params!: any;

    public screenData: IScreenData = {
        employeeCode: '000001', employeeName: '日通　太郎', date: '2018/03/10（火）',
        time: '17：58', stampTypeText: '出勤', stampCard: 'A1234567890', localtion: '000　本社',
        cdTime: 10, comment: '秒で自動的に打刻入力画面に戻ります'
    };


    public created() {



    }

    public login() {


    }

    public mounted() {
    }
}

interface IScreenData {
    employeeCode: string;
    employeeName: string;
    date: string;
    time: string;
    stampTypeText: string;
    stampCard: string;
    localtion: string;
    cdTime: number;
    comment: string;
}

