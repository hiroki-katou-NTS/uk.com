import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';
import {KAFS08A1Component} from '../../s08/a1';
import {KafS08A2Component} from '../../s08/a2';
import {KafS08CComponent} from '../../s08/c';
import { StepwizardComponent } from '@app/components';

@component({
    name: 'kafs08a',
    route: '/kaf/s08/a',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    components : {
        'kafs08a1' : KAFS08A1Component,
        'kafs08a2' : KafS08A2Component,
        'kafs08c' : KafS08CComponent,
        'step-wizard': StepwizardComponent,
    },
    constraints: []
})
export class KafS08AComponent extends Vue {
    public step: string = 'KAFS08_10';

    //public paramsFromA1: any | null = null;
    public departureTime: number = null;
    public returnTime: number = null;
    public table: [] = [] ;
    public comment: Object = {};

   
    //thực hiện emit từ component con A1
    public ProcessNextToStepTwo(departureTime: number,returnTime: number,achievementDetails,comment) {
        const vm = this;
        //departureTime và returnTime có được ở màn hình A1
        vm.departureTime = departureTime;
        vm.returnTime = returnTime;
        //table có được ở màn hình A1 chuyển lên.
        vm.table = achievementDetails;
        //lấy giá trị comment set ở A1
        vm.comment = comment;
        //nhảy sang step A2 
        vm.step = 'KAFS08_11';
    }

    //thực hiện emit từ component con A2 đến C
    public ProcessNextToStepThree(paramsA2) {
        const vm = this;
        vm.step = 'KAFS08_12';
    }

    //thực hiện emit từ component con A2 quay trở lại A1
    public ProcessPrevStepOne() {
        const vm = this;
        vm.step = 'KAFS08_10';
    }
}