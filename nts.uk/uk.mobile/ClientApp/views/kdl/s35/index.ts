import { _, Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { vmOf } from 'vue/types/umd';

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
        const self = this;

        self.setParam(this.params);
        self.startPage();
    }

    private setParam(params: IParam) {
        const self = this;

        if (!params) {

            return;
        }

        self.employeeId = params.employeeId || self.employeeId;
        self.startDate = params.period.startDate || self.startDate;
        self.endDate = params.period.endDate || self.endDate;
        self.daysUnit = params.daysUnit || self.daysUnit;
        self.targetSelectionAtr = params.targetSelectionAtr || self.targetSelectionAtr;
        self.actualContentDisplayList = params.actualContentDisplayList || self.actualContentDisplayList;
        self.managementData = params.managementData || self.managementData;
    }

    get displayedRequiredNumberOfDays() {
        const self = this;

        return self.requiredNumberOfDays;
    }

    get requiredNumberOfDays() {
        const self = this;

        const required = self.substituteHolidayList.length * self.daysUnit;
        let selected = 0;
        self.substituteWorkInfoList.forEach((info) => {
            if (info.checked) {
                selected += info.remainingNumber;
            }
        }); 

        return required - selected < 0 ? 0 : required - selected;
    }

    public back() {
        const self = this;

        self.$close();
    }

    private startPage() {
        const self = this;

        self.$http.post('at', servicesPath.init, {
            employeeId: self.employeeId,
            startDate: new Date(self.startDate),
            endDate: new Date(self.endDate),
            daysUnit: self.daysUnit,
            targetSelectionAtr: self.targetSelectionAtr,
            actualContentDisplayList: self.actualContentDisplayList,
            managementData: self.managementData,
        }).then((result: { data: ParamsData }) => {
            self.$mask('hide');

            self.startDate = self.$dt(new Date(self.startDate), 'YYYY/MM/DD');
            self.endDate = self.$dt(new Date(self.endDate), 'YYYY/MM/DD');
            self.daysUnit = result.data.daysUnit;
            self.targetSelectionAtr = result.data.targetSelectionAtr;
            self.substituteHolidayList = result.data.substituteHolidayList;
            self.substituteWorkInfoList = result.data.substituteWorkInfoList;

            const managementDataTmp = self.managementData.map((management) => management.outbreakDay);
            console.log(result.data);
        }).catch((error: any) => {
            self.showError(error);
        });
    }



    private showError(res: any) {
        let self = this;

        self.$mask('hide');
        if (!_.isEqual(res.message, 'can not found message id')) {
            self.$modal.error({ messageId: res.messageId, messageParams: res.parameterIds });
        } else {
            self.$modal.error(res.message);
        }
    }

    public mounted() {
        const self = this;


    }

    public decide() {
        const self = this;

        alert('You clicked button decide');
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