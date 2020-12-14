import { _, Vue } from '@app/provider';
import { component } from '@app/core/component';
import { KafS00SubP3Component } from 'views/kaf/s00/sub/p3';
import { KafS00SubP1Component } from 'views/kaf/s00/sub/p1';
import { KafS00AComponent, KafS00BComponent, KafS00CComponent } from 'views/kaf/s00';
import { InfoWithDateApplication , DisplayInfoOverTime, TimeZone} from '../a/define.interface';
@component({
    name: 'kafs05step1',
    route: '/kaf/s05/step1',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {
        workHours1: {
            required: true
        }
    },
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

    public workInfo: WorkInfo = {} as WorkInfo;

    public workHours1: { start: number, end: number } = null;

    public workHours2?: { start: number, end: number } = null;

    public breakTimes: Array<BreakTime> = [];

    public displayNumberBreakTime = 3;

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

    public mounted() {
        const self = this;
    }

    public createBreakTime() {
        const self = this;
        for (let i = 0; i < 10; i++) {
            let item = {} as BreakTime;
            item.valueHours = null as ValueTime;
            item.frameNo = i + 1;
            self.breakTimes.push(item);
        }
    }
    public getWorkType() {
        const self = this;

        return self.workInfo.workType.code;
    }

    public getWorkTime() {
        const self = this;
        
        return self.workInfo.workTime.code;
    }

    public setWorkCode(
        workTypeCD?: string,
        workTypeName?: string,
        workTimeCD?: string,
        workTimeName?: string,
        workTimeHours?: string,
        displayOver?: DisplayInfoOverTime) {
        
        const self = this;
        let workType = {} as Work;
        workType.code = workTypeCD;
        workType.name = workTypeName;
        let workTime = {} as Work;
        workTime.code = workTimeCD;
        workTime.name = workTimeName;
        let workInfo = {} as WorkInfo;
        workInfo.workType = workType;
        workInfo.workTime = workTime;
        self.workInfo = workInfo;  

        return;
    }

    get $appContext(): any {
        const self = this;

        return self.$parent;
    }

    public loadBreakTime(object?: DisplayInfoOverTime) {
        const self = this;
        if (!_.isNil(object.infoWithDateApplicationOp)) {
            let breakTime = object.infoWithDateApplicationOp.breakTime;
            if (!_.isNil(breakTime)) {
                let timeZone = breakTime.timeZones;
                if (!_.isEmpty(timeZone)) {
                    _.forEach(timeZone, (item: any, index: number) => {
                        let resultBreakTime = _.find(self.breakTimes, (i: any) => i.frameNo == (index + 1)) as BreakTime;
                        if (!_.isNil(resultBreakTime)) {
                            resultBreakTime.valueHours = {} as ValueTime;
                            resultBreakTime.valueHours.start = item.start;
                            resultBreakTime.valueHours.end = item.end;
                        } else {
                            self.breakTimes[index].valueHours = null as ValueTime;
                        }
                    });
                    
                    return;
                }


            }
        }

        _.forEach(self.breakTimes, (item: any) => {
            item.valueHours = null as ValueTime;
        });
    }

    public loadWorkHours(object?: DisplayInfoOverTime) {
        const self = this;
        if (!_.isNil(object)) {
            if (!_.isNil(object.infoWithDateApplicationOp)) {
                let workHours = object.infoWithDateApplicationOp.workHours;
                if (!_.isNil(workHours)) {
                    if (!_.isNil(workHours.startTimeOp1)) {
                        if (_.isNil(self.workHours1)) {
                            self.workHours1 = {} as ValueTime;
                        }
                        this.workHours1.start = workHours.startTimeOp1;
                    }

                    if (!_.isNil(workHours.endTimeOp1)) {
                        if (_.isNil(self.workHours1)) {
                            self.workHours1 = {} as ValueTime;
                        }
                        this.workHours1.end = workHours.endTimeOp1;
                    }

                    if (!_.isNil(workHours.startTimeOp2)) {
                        if (_.isNil(self.workHours2)) {
                            self.workHours2 = {} as ValueTime;
                        }
                        this.workHours2.start = workHours.startTimeOp2;
                    }

                    if (!_.isNil(workHours.endTimeOp2)) {
                        if (_.isNil(self.workHours2)) {
                            self.workHours2 = {} as ValueTime;
                        }
                        this.workHours2.end = workHours.endTimeOp2;
                    }
                }
            }
        }
    }
    public loadData(object?: DisplayInfoOverTime) {
        const self = this;
        console.log('loadData');
        if (!_.isNil(object)) {
            let workType = {} as Work;
            if (!_.isNil(object.infoWithDateApplicationOp)) {
                workType.code = object.infoWithDateApplicationOp.workTypeCD;
            }
            if (!_.isNil(workType.code)) {
                let workTypes = object.infoBaseDateOutput.worktypes;
                let resultWorkType = 
                        _.find(
                                workTypes,
                                (i: any) => i.workTypeCode == workType.code);
                if (!_.isNil(resultWorkType)) {
                    workType.name = resultWorkType.name;
                }                
                
            }

            let workTime = {} as Work;
            if (!_.isNil(object.infoWithDateApplicationOp)) {
                workTime.code = object.infoWithDateApplicationOp.workTimeCD;
            }
            if (!_.isNil(workTime.code)) {
                let workTimes = object.appDispInfoStartup.appDispInfoWithDateOutput.opWorkTimeLst;
                let resultWorkTime = 
                        _.find(
                                workTimes,
                                (i: any) => i.worktimeCode == workTime.code);
                if (!_.isNil(resultWorkTime)) {
                    workTime.name = resultWorkTime.workTimeDisplayName.workTimeName;
                }                
                
            }
            self.workInfo.workType = workType;
            self.workInfo.workTime = workTime;

            let workInfo = {} as WorkInfo;
            workInfo.workType = workType;
            workInfo.workTime = workTime;
            self.workInfo = workInfo;


            // load break time
            self.loadBreakTime(object);
            // load work hours
            self.loadWorkHours(object);

            return;
        } 

        // loading first time
        self.workInfo.workType = {
            code: '',
            name: ''
        } as Work;
        self.workInfo.workTime = {
            code: '',
            name: '',
            time: '12:00 ~ 20:00'
        } as Work;


        self.createBreakTime();
    }

    public addBreakHour() {
        const self= this;
        if (self.displayNumberBreakTime < 10) {
            self.displayNumberBreakTime ++;
        }
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

interface BreakTime {
    valueHours: any;
    title: string;
    frameNo: number;
}

interface ValueTime {
    start: number;
    end: number;
}
