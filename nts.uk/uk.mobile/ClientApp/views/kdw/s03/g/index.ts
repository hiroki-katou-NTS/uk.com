import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { storage } from '@app/utils';

@component({
    name: 'kdws03g',
    route: '/kdw/s03/g',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KdwS03GComponent extends Vue {
    public title: string = 'KdwS03G';

    @Prop({ default: () => ({ remainOrtime36: 0}) })
    public readonly params: { remainOrtime36: Number };
    public remainNumber: IRemainNumber = {
        yearRemain: 0,
        reserveRemain: 0,
        compensatoryRemain: 0,
        substituteRemain: 0,
        nextGrantDate: null
    };
    public time36: ITime36 = {
        time36: 0,					
        maxTime36: 0,						
        excessNumber: 0,						
        maxExcessNumber: 0						
    };

    public created() {
        let self = this;
        let cache: any = storage.local.getItem('dailyCorrectionState');
        // let employeeId = cache.
        self.$http.post('at', servicePath.getRemain + '').then((result: any) => {
            console.log(result);
            let data = result.data;
            self.remainNumber = {
                yearRemain: data.annualLeave.annualLeaveRemain,
                reserveRemain: data.reserveLeave.remainNumber,
                compensatoryRemain: data.compensatoryLeave.compenLeaveRemain,
                substituteRemain: data.substitutionLeave.holidayRemain,
                nextGrantDate: data.nextGrantDate      
            };
        });
        let param36 = {
            employeeId: 'a6cdd6b2-9434-4e9e-b19a-5335a69f2bf5',
            year: 2019,
            month: 9
        };
        self.$http.post('at', servicePath.get36AgreementInfo, param36).then((result: any) => {
            console.log(result);
            let time = result.data;
            self.time36 = {
                time36: time.agreementTime36 || 0,
                maxTime36: time.maxTime || 0,
                excessNumber: time.excessFrequency || 0,
                maxExcessNumber: time.maxNumber || 0
            };
        });
    }





}
const servicePath = {
    getRemain : 'screen/at/correctionofdailyperformance/getRemainNum/',
    get36AgreementInfo: 'screen/at/dailyperformance/36AgreementInfo'
};
interface IRemainNumber {
    yearRemain: number;//年休残数						
    reserveRemain: number;//積立年休残数						
    compensatoryRemain: number;//代休残数						
    substituteRemain: number;//振休残数						
    nextGrantDate: Date;//次回付与日						
}
interface ITime36 {
    time36: number;//超過時間						
    maxTime36: number;//超過上限時間						
    excessNumber: number;//超過回数						
    maxExcessNumber: number;//超過上限回数						
}
