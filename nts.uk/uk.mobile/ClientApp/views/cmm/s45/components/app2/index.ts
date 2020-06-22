import { Vue } from '@app/provider';
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
    @Prop({ default: () => ({ appWorkChange: new AppWorkChange('', '', '', '', true, true) }) })
    public readonly params: AppWorkChange;
    public $app() {
        return this.params;
    }
    public created(params: any) {
        // this.params = params;
    }
    // 「勤務変更申請の表示情報．勤務変更申請の反映.出退勤を反映するか」がする ※1
    // @Watch
    public isDisplay1() {
        return true;
    }
    // ※1 = ○　AND　「勤務変更申請の表示情報．申請表示情報．申請表示情報(基準日関係なし)．複数回勤務の管理」= true
    // @Watch
    public isDisplay2() {
        return true;
    }

}
// dto 
export class AppWorkChange {

    public workType: String;

    public workTime: String;

    public workHours1: String;

    public workHours2: String;

    public straight: boolean;

    public bounce: boolean;

    constructor(workType: String, workTime: String, workHours1: String, workHours2: String, straight: boolean, bounce: boolean) {
        this.workType = workType;
        this.workTime = workTime;
        this.workHours1 = workHours1;
        this.workHours2 = workHours2;
        this.straight = straight;
        this.bounce = bounce;
    }
}