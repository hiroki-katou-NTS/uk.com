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
}
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