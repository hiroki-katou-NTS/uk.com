import { Vue } from '@app/provider';
import { StepwizardComponent } from '@app/components';
import { component } from '@app/core/component';
import { KafS12A1Component } from '../a1';
import { KafS12A2Component } from '../a2';
import { KafS12CComponent } from '../c';
import {ITimeLeaveAppDispInfo} from './define';
@component({
    name: 'kafs12a',
    route: '/kaf/s12/a',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    components: {
        'step-wizard': StepwizardComponent,
        'kaf-s12-a1': KafS12A1Component,
        'kaf-s12-a2': KafS12A2Component,
        'kaf-s12-c': KafS12CComponent,
    },
    validations: {},
    constraints: []
})
export class KafS12AComponent extends Vue {
    public step: string = 'KAFS12_1';
    public mode: boolean = true;
    public timeLeaveAppDispInfo: ITimeLeaveAppDispInfo;

    public handleNextToStepTwo(data: ITimeLeaveAppDispInfo) {
        const vm = this;

        vm.timeLeaveAppDispInfo = data;
        vm.step = 'KAFS12_2';
    }

    public handleNextToStepThree() {
        const vm = this;

        vm.step = 'KAFS12_3';
    }
}