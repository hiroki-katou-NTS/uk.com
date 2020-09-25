import { Vue, _ } from '@app/provider';
import { component, Prop } from '@app/core/component';

@component({
    name: 'cmms45componentsapp4',
    template: require('./index.vue'),
    style: require('./style.scss'),
    validations: {},
    constraints: []
})
export class CmmS45ComponentsApp4Component extends Vue {
    public title: string = 'CmmS45ComponentsApp4';
    @Prop({
        default: () => ({
            appDispInfoStartupOutput: null,
            appDetail: null
        })
    })
    public readonly params: {
        appDispInfoStartupOutput: any,
        appDetail: any
    };
    public dataOutput: any;

    public isCondition1: boolean = false;
    public isCondition2: boolean = false;

    public appWorkChange: any = new AppWorkChange();

    public user: any;
    public $app() {

        return this.appWorkChange;
    }
    public created() {
        const self = this;
        self.params.appDetail = {};
        self.$auth.user.then((usr: any) => {
            self.user = usr;
        }).then((res: any) => {
            this.fetchData(self.params);
        });
    }


    public mounted() {

    }
    public fetchData(getParams: any) {
        const self = this;
        this.$http.post('at', API.start, {
            companyId: self.user.companyId,
            applicationId: self.params.appDispInfoStartupOutput.appDetailScreenInfo.application.appID,
            appDispInfoStartupDto: this.params.appDispInfoStartupOutput
        }).then((res: any) => {
                this.dataOutput = res.data;
                this.bindStart();
                this.params.appDetail = this.dataOutput;
            });
    }
    public bindStart() {
        let params = this.dataOutput;

        this.bindCodition(params);

        let workTypeCode = params.goBackApplication.dataWork.workType;
        let workType = _.find(params.lstWorkType, (item: any) => item.workTypeCode == workTypeCode);
        let workTypeName = workType ? workType.abbreviationName : this.$i18n('KAFS07_10');
        this.$app().workType = workTypeCode + '  ' + workTypeName;

        let workTimeCode = params.goBackApplication.dataWork.workTime;
        let workTime = _.find(params.appDispInfoStartup.appDispInfoWithDateOutput.opWorkTimeLst, (item: any) => item.worktimeCode == workTimeCode);
        let workTimeName = workTime ?  workTime.workTimeDisplayName.workTimeName : this.$i18n('KAFS07_10');
        if (!workTimeCode) {
            workTimeCode = this.$i18n('KAFS07_9');
            workTimeName = '';
        }
        this.$app().workTime = workTimeCode + '  ' + workTimeName;
        this.$app().straight = params.goBackApplication.straightDistinction == 1 ? true : false;
        this.$app().bounce = params.goBackApplication.straightLine == 1 ? true : false;


    }

    public bindCodition(params: any) {
        // set condition
        this.isCondition1 = this.isDisplay1(params);
        this.isCondition2 = this.isDisplay2(params);
    }
    // 「勤務変更申請の表示情報．勤務変更申請の反映.出退勤を反映するか」がする
    public isDisplay1(params: any) {

        return true;
    }
    // ※1 = ○　AND　「勤務変更申請の表示情報．申請表示情報．申請表示情報(基準日関係なし)．複数回勤務の管理」= true
    public isDisplay2(params: any) {

        return true;

    }

}
// dto 
class AppWorkChange {

    public workType: string = 'workType';

    public workTime: string = 'workTime';

    public workHours1: string = '';

    public isWorkHours1: boolean = true;

    public workHours2: string = '';

    public isWorkHours2: boolean = true;

    public straight: boolean = true;

    public bounce: boolean = true;

    constructor() {

    }
}
const API = {
    start: 'at/request/application/gobackdirectly/getDetail'
};

