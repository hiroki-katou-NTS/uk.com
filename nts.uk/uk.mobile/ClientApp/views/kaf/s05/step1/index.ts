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

    public model: Model;

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

    public created() {
        const self = this;
        self.loadData();
    }

    get $appContext(): any {
        const self = this;

        return self.$parent;
    }
    public loadData(object?: any) {
        const self = this;
        console.log('loadData');
        let workType = {} as Work;
        workType.code = '001';
        workType.name = 'name';
        let workTime = {} as Work;
        workTime.code = '001';
        workTime.name = 'time';
        let work = {} as WorkInfo;
        work.workType = workType;
        work.workTime = workTime;

        self.model = {

        } as Model;
        self.model.work = work;

    }


}
interface WorkInfo {
    workType: Work;
    workTime: Work;
}
interface Work {
    code: string;
    name: string;
    time?: string;
}
interface Model {
    work: WorkInfo;
}