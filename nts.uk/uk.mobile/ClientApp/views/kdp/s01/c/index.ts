import { component, Prop } from '@app/core/component';
import { _, Vue } from '@app/provider';

const servicePath = {
    getAllWkType: 'at/share/worktype/get_not_remove_work_type',
    checkWorkTimeSettingNeeded: 'at/schedule/basicschedule/isWorkTimeSettingNeeded/'
};

@component({
    name: 'kdpS01c',
    route: '/kdp/s01/c',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    constraints: []
})


export class KdpS01CComponent extends Vue {
    @Prop({ default: () => ({}) })
    public params!: any;
    public screenData: IScreenData = {
        employeeCode: '000001', employeeName: '日通　太郎', date: '2018/03/10（火）',
        time: '17：58', stampTypeText: '出勤', localtion: '000　本社',
         workTypeName: '勤務種類の名称', workTimeName: '就業時間帯の名称'
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
    localtion: string;
    workTypeName: string;
    workTimeName: string;
}

