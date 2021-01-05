import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { KafS00ShrComponent, AppType } from 'views/kaf/s00/shr';
import {
    KafS00AComponent,
    KafS00CComponent
} from 'views/kaf/s00';

@component({
    name: 'kafs11a',
    route: '/kaf/s11/a',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: [],
    components: {
        'kafs00-a': KafS00AComponent,
        'kafs00-c': KafS00CComponent,
    }
})
export class KafS11AComponent extends KafS00ShrComponent {
    @Prop({ default: () => ({}) })
    public params: KAFS11Params;
    public prePostResource: Array<Object> = [];
    public complementLeaveAtrResource: Array<Object> = [];
    public prePostAtr: number = null;
    public complementLeaveAtr: number = null;
    public complementDate: any = null;
    public leaveDate: any = null;
    public complementTimeRange1: { start: number, end: number } = { start: null, end: null};
    public complementTimeRange2: { start: number, end: number } = { start: null, end: null};
    public leaveTimeRange1: { start: number, end: number } = { start: null, end: null};
    public leaveTimeRange2: { start: number, end: number } = { start: null, end: null};
    public complementWorkType: WorkType = { code: '001', name: '001'};
    public complementWorkTime: WorkTime = { code: '002', name: '002', time: '08:00-17:30' };
    public leaveWorkType: WorkType = { code: '001', name: '001'};
    public leaveWorkTime: WorkTime = { code: '002', name: '002', time: '08:00-17:30' };
    public opAppStandardReasonCD: string = '';
    public opAppReason: string = '';
    
    public created() {
        const vm = this;
        vm.prePostResource = [{
            code: 0,
            text: 'KAFS00_10'
        }, {
            code: 1,
            text: 'KAFS00_11'
        }];
        vm.complementLeaveAtrResource = [{
            code: 0,
            text: 'KAFS11_5'
        }, {
            code: 1,
            text: 'KAFS11_6'
        }, {
            code: 2,
            text: 'KAFS11_7'
        }];
        let user = null;
        vm.$auth.user.then((userData: any) => {
            user = userData;

            return vm.loadCommonSetting(AppType.COMPLEMENT_LEAVE_APPLICATION);
        }).then((data: any) => {
            vm.updateKaf000_A_Params(user);
            vm.updateKaf000_C_Params(true);
        });
    }

    public kaf000CChangeReasonCD(opAppStandardReasonCD) {
        const vm = this;
        vm.opAppStandardReasonCD = opAppStandardReasonCD;
    }

    public kaf000CChangeAppReason(opAppReason) {
        const vm = this;
        vm.opAppReason = opAppReason;
    }
}

export interface KAFS11Params {
    date: any;
}

interface WorkType {
    code: string;
    name: string;
}

interface WorkTime {
    code: string;
    name: string;
    time: string;
}