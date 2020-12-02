import { Vue } from '@app/provider';
import { component } from '@app/core/component';
import {KdlS36Component } from '../s36/';

@component({
    name: 'kdltests36',
    route: '/kdl/tests36',
    style: require('./style.scss'),
    template: require('./index.vue'),
    components: {
        'kdls36': KdlS36Component
    },
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KdlTests36Component extends Vue {
    public title: string = 'KdlTests36';
    public employeeId: string;
    public dateValue: { start: Date | null, end: Date | null } = { start: null, end: null };
    public daysUnit: number;
    public targetSelectionAtr: number;

    public beforeCreate() {
        const self = this;

        self.$auth.user.then((user: any) => {
            self.employeeId = user.employeeId;
        });
        self.daysUnit = 0.5;
        self.targetSelectionAtr = 0;
    }

    public created() {
        const self = this;

    }

    public openKDLS36() {
        const self = this;

        const params: any = {
            // 社員ID
            employeeId: self.employeeId,

            // 申請期間
            period: {
                startDate: self.dateValue.start,
                endDate: self.dateValue.end,
            },

            // 日数単位（1.0 / 0.5）
            daysUnit: self.daysUnit,

            // 対象選択区分（自動 / 申請 / 手動
            targetSelectionAtr: self.targetSelectionAtr,

            // List<表示する実績内容>
            actualContentDisplayList: [],

            // List<振出振休紐付け管理>
            managementData: []
        };

        self.$modal('kdls36', params);
    }
}