import { _, Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';

@component({
    name: 'kdls35',
    route: '/kdl/s35',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KdlS35Component extends Vue {
    @Prop({ default: () => ({}) })
    public params!: any;
    public title: string = 'KdlS35';
    public startDate: string = null;
    public endDate: string = null;
    public daysUnit: number = 0;
    public targetSelectionAtr: TargetSelectionAtr = 0;
    public actualContentDisplayList: any[] = null;
    public managementData: SubWorkSubHolidayLinkingMng[] = null;
    public employeeId: string = '';
    public substituteHolidayList: string[] = [];
    public substituteWorkInfoList: ISubstituteWorkInfo[] = [];
    public displayedPeriod: string = '';

    public created() {
        const vm = this;

        vm.setParam(vm.params);
        vm.startPage();
    }

    private setParam(params: IParam) {
        const vm = this;

        if (!params) {

            return;
        }

        vm.employeeId = params.employeeId || vm.employeeId;
        vm.startDate = params.period.startDate || vm.startDate;
        vm.endDate = params.period.endDate || vm.endDate;
        vm.daysUnit = params.daysUnit || vm.daysUnit;
        vm.targetSelectionAtr = params.targetSelectionAtr || vm.targetSelectionAtr;
        vm.actualContentDisplayList = params.actualContentDisplayList || vm.actualContentDisplayList;
        vm.managementData = params.managementData || vm.managementData;
    }

    get displayedRequiredNumberOfDays() {
        const vm = this;

        return vm.requiredNumberOfDays;
    }

    get requiredNumberOfDays() {
        const vm = this;

        const { substituteWorkInfoList } = vm;
        const required = vm.substituteHolidayList.length * vm.daysUnit;
        let selected = 0;

        substituteWorkInfoList.forEach((m) => {
            if (m.checked) {
                selected += m.remainingNumber;
                if (required - selected < 0) {
                    vm.$modal
                        .warn({ messageId: 'Msg_1761' })
                        .then(() => {
                            m.checked = false;
                        });
                }
            }
        });

        return required - selected < 0 ? 0 : required - selected;

        // return substituteWorkInfoList
        //     .map((m) => m.checked ? m.remainingNumber : 0)
        //     .reduce((p, c) => p + c, required);
    }

    public back() {
        const vm = this;

        vm.$close();
    }

    private startPage() {
        const vm = this;

        vm.$http.post('at', servicesPath.init, {
            employeeId: vm.employeeId,
            startDate: new Date(vm.startDate),
            endDate: new Date(vm.endDate),
            daysUnit: vm.daysUnit,
            targetSelectionAtr: vm.targetSelectionAtr,
            actualContentDisplayList: vm.actualContentDisplayList,
            managementData: vm.managementData,
        }).then((result: { data: ParamsData }) => {
            vm.$mask('hide');

            vm.startDate = vm.$dt(new Date(vm.startDate), 'YYYY/MM/DD');
            vm.endDate = vm.$dt(new Date(vm.endDate), 'YYYY/MM/DD');
            const { data } = result;
            const { daysUnit, targetSelectionAtr, substituteHolidayList, substituteWorkInfoList } = data;

            vm.daysUnit = daysUnit;
            vm.targetSelectionAtr = targetSelectionAtr;
            vm.substituteHolidayList = substituteHolidayList;
            vm.substituteWorkInfoList = substituteWorkInfoList
                .map((m, index) => ({ ...m, checked: false }));
        }).catch((error: any) => {
            vm.showError(error);
        });
    }



    private showError(res: any) {
        const vm = this;

        vm.$mask('hide');
        if (!_.isEqual(res.message, 'can not found message id')) {
            vm.$modal.error({ messageId: res.messageId, messageParams: res.parameterIds });
        } else {
            vm.$modal.error(res.message);
        }
    }

    public mounted() {
        const self = this;


    }

    public decide() {
        const vm = this;

        if (vm.requiredNumberOfDays > 0) {
            vm.$modal.warn({ messageId: 'Msg_1762' });

            return;
        }

        const data: ParamsData = {
            daysUnit: vm.daysUnit,
            employeeId: vm.employeeId,
            substituteHolidayList: vm.substituteHolidayList
            .map((m) => new Date(m).toISOString()),
            targetSelectionAtr: vm.targetSelectionAtr,
            substituteWorkInfoList: vm.substituteWorkInfoList
            .filter((item) => item.checked)
            .map((m) => ({...m}))
        };
        data.substituteWorkInfoList.forEach((f) => {
            f.expirationDate = new Date(f.expirationDate).toISOString();
            f.substituteWorkDate = new Date(f.substituteWorkDate).toISOString();
        });

        vm.$mask('show');
        vm.$http
        .post('at',servicesPath.associate,data)
        .then((msgData: SubWorkSubHolidayLinkingMng[]) => {
            vm.$mask('hide');
            vm.back();

        })
        .catch((error: any) => {
            vm.showError(error);
        });
    }
}

interface IParam {
    //社員ID								
    employeeId: string;

    //申請期間
    period: DatePeriod;

    //日数単位 (0.5/1.0)
    daysUnit: number;

    //対象選択区分
    targetSelectionAtr: TargetSelectionAtr;

    //List<表示する実績内容>
    actualContentDisplayList: any[];

    //List<振休振出紐付け管理>
    managementData: SubWorkSubHolidayLinkingMng[];
}

interface DatePeriod {
    startDate: string;
    endDate: string;
}

enum TargetSelectionAtr {
    //自動
    AUTOMATIC = 0,

    //申請
    REQUEST = 1,

    //手動
    MANUAL = 2,
}

interface SubWorkSubHolidayLinkingMng {
    // 社員ID
    employeeId: string;

    // 逐次休暇の紐付け情報 . 発生日
    outbreakDay: string;

    // 逐次休暇の紐付け情報 . 使用日
    dateOfUse: string;

    //逐次休暇の紐付け情報 . 使用日数
    dayNumberUsed: number;

    //逐次休暇の紐付け情報 . 対象選択区分
    targetSelectionAtr: number;
}

interface ParamsData {
    employeeId: string;

    // 日数単位
    daysUnit: number;

    // 振休日リスト
    substituteHolidayList: string[];

    // 対象選択区分
    targetSelectionAtr: TargetSelectionAtr;

    // List<振出データ>
    substituteWorkInfoList: Array<ISubstituteWorkInfo>;
}

interface ISubstituteWorkInfo {
    dataType: DataType;
    expirationDate: string;
    expiringThisMonth: boolean;
    remainingNumber: number;
    substituteWorkDate: string;
    checked: boolean;
}

enum DataType {
    ACTUAL = 0,
    APPLICATION_OR_SCHEDULE = 1
}

const servicesPath = {
    init: 'screen/at/kdl035/init',
    associate: 'screen/at/kdl035/associate',
};