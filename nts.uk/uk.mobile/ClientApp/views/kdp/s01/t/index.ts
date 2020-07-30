import { component, Prop } from '@app/core/component';
import { _, Vue } from '@app/provider';

const servicePath = {
    getAllWkType: 'at/share/worktype/get_not_remove_work_type',
    checkWorkTimeSettingNeeded: 'at/schedule/basicschedule/isWorkTimeSettingNeeded/'
};

@component({
    name: 'kdpS01t',
    route: '/kdp/s01/t',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    constraints: []
})


export class KdpS01TComponent extends Vue {
    @Prop({ default: () => ({}) })
    public params!: any;

    public current: ITime = { date: '2018年 4月 9日（月）', time: '17：58' };
    public comment: string = '打刻入力を忘れず、行って下さい';
    public setting: ISetting = {
        buttons: [
            { type: 1, displayText: '出勤',color:'#0000ff',bgColor:'#E4C9FF' },
            { type: 2, displayText: '退勤' ,color:'#0000ff',bgColor:'#E4C9FF' },
            { type: 3, displayText: '外出' ,color:'#0000ff',bgColor:'#88D8FF' },
            { type: 4, displayText: '戻り' ,color:'#0000ff',bgColor:'#88D8FF' },
            { type: 5, displayText: '応援出勤' ,color:'#0000ff',bgColor:'#FED3C6' },
            { type: 6, displayText: '応援退勤' ,color:'#0000ff',bgColor:'#FED3C6' },
        ]
    };

    public created() {



    }

    public login() {


    }

    public mounted() {
    }
}

interface ITime {
    date: string;
    time: string;
}

interface ISetting {
    buttons: Array<IButton>;
}

interface IButton {
    type: number;
    displayText: string;
    color: string;
    bgColor: string;
}