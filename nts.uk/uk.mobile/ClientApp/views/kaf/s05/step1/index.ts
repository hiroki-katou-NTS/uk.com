import { Vue } from '@app/provider';
import { component } from '@app/core/component';
import { KafS00SubP3Component } from 'views/kaf/s00/sub/p3';
import { KafS00SubP1Component } from 'views/kaf/s00/sub/p1';
import { KafS00AComponent, KafS00BComponent, KafS00CComponent } from 'views/kaf/s00';

@component({
    name: 'kafs05step1',
    route: '/kaf/s05/step1',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: [],
    components: {
        'kafs00subp3': KafS00SubP3Component,
        'kafs00subp1': KafS00SubP1Component,
        'kafs00-a': KafS00AComponent,
        'kafs00-b': KafS00BComponent,
        'kafs00-c': KafS00CComponent
    }
})
export class KafS05Step1Component extends Vue {
    public title: string = 'KafS05Step1';

    public valueWorkHours1: { start: number, end: number } = null;

    public valueWorkHours2: { start: number, end: number } = null;

    public kafS00P1Params1: any = {
        preAppDisp: false,
        preAppTime: null,
        preAppExcess: null,
        actualDisp: false,
        actualTime: null,
        actualExcess: null,
        scheduleDisp: true,
        scheduleTime: null,
        scheduleExcess: null
    };

    get $appContext(): any {
        return this.$parent;
    }
}