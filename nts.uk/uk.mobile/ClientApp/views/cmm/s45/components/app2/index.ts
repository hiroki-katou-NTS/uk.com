import { Vue, _ } from '@app/provider';
import { component, Prop } from '@app/core/component';

@component({
    name: 'cmms45componentsapp2',
    template: require('./index.vue'),
    style: require('./style.scss'),
    validations: {},
    constraints: []
})
export class CmmS45ComponentsApp2Component extends Vue {
    public title: string = 'CmmS45ComponentsApp1';
    @Prop({ default: () => ({ appWorkChange: new AppWorkChange() }) })
    public readonly params: AppWorkChange;
    public dataFetch: any;

    public isCondition1: boolean = false;
    public isCondition2: boolean = false;
    public  $app() {
        return this.params;
    }
    public created(params: any) {
        // this.params = params;
        let getParams = params;
        // this.fetchData(getParams);
        this.bindCodition(params);

    }
    public mounted() {

    }
    public fetchData(getParams: any) {
        this.$http.post('at', API.start, {
            companyId: '',
            appId: '2',
            appDispInfoStartupDto: getParams.appDispInfoStartupDto
        })
            .then((res: any) => {
                this.dataFetch = res.data;
                this.bindStart();
            });
    }
    public bindStart() {
        let params = this.dataFetch;

        this.bindCodition(params);

        let workTypeCode = params.appWorkChange.opWorkTypeCD;
        let workTypeName = _.find(params.appWorkChangeDispInfo.workTypeLst, (item: any) => item.workTypeCode == workTypeCode).abbreviationName || this.$i18n('KAFS07_10');

        this.$app().workType = workTypeCode + '  ' + workTypeName;

        let workTimeCode = params.appWorkChange.opWorkTimeCD;
        let workTimeName = _.find(params.appWorkChangeDispInfo.appDispInfoStartupOutput.appDispInfoWithDateOutput.opWorkTimeLst, (item: any) => item.worktimeCode == workTimeCode).workTimeDisplayName.workTimeName || this.$i18n('KAFS07_10');

        this.$app().workTime = workTimeCode + '  ' + workTimeName;
        if (_.isEmpty(params.appWorkChange.timeZoneWithWorkNoLst)) {
            let time1 = _.find(params.appWorkChange.timeZoneWithWorkNoLst, (item: any) => item.workNo == 1);
            let time2 = _.find(params.appWorkChange.timeZoneWithWorkNoLst, (item: any) => item.workNo == 2);
            if (time1) {
                this.$app().workHours1 = this.$dt.timedr(time1.timeZone.start) + ' ~ ' + this.$dt.timedr(time1.timeZone.end);
            } else {
                this.$app().isWorkHours1 = false;
            }
            if (time2) {
                this.$app().workHours2 = this.$dt.timedr(time2.timeZone.start) + ' ~ ' + this.$dt.timedr(time2.timeZone.end);
            } else {
                this.$app().isWorkHours2 = false;
            }
        }


    }

    public bindCodition(params: any) {
        // set condition
        this.isCondition1 = this.isDisplay1(params);
        this.isCondition2 = this.isDisplay2(params);
    }
    // 「勤務変更申請の表示情報．勤務変更申請の反映.出退勤を反映するか」がする
    public isDisplay1(params: any) {
        // return params.reflectWorkChangeAppDto.whetherReflectAttendance == 1;
        return true;
    }
    // ※1 = ○　AND　「勤務変更申請の表示情報．申請表示情報．申請表示情報(基準日関係なし)．複数回勤務の管理」= true
    public isDisplay2(params: any) {
        // return params.reflectWorkChangeAppDto.whetherReflectAttendance == 1 && params.appDispInfoStartupOutput.appDispInfoNoDateOutput.managementMultipleWorkCycles;
        return true;

    }

}
// dto 
export class AppWorkChange {

    public workType: string = '';

    public workTime: string = '';

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
    start: 'at/request/application/workchange/startDetailMobile'
};

