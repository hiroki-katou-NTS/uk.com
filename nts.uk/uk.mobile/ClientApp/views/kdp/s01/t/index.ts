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

    public setting: ISetting = {
        buttons: [
            { type: 1, displayText: '打刻申請' },
            { type: 2, displayText: '残業申請' },
            { type: 3, displayText: '休暇申請' },
            { type: 4, displayText: '休日出勤時間申請'},
            { type: 5, displayText: '遅刻・早退取消申請' },
            { type: 6, displayText: '時間年休申請' },
        ]
    };

    public created() {



    }

    public login() {


    }

    public mounted() {
    }
}

interface ISetting {
    buttons: Array<IButton>;
}

interface IButton {
    type: number;
    displayText: string;
}