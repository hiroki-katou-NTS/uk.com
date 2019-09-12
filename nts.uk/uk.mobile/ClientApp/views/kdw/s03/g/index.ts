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
    public readonly params: { remainOrtime36: Number };//0: 休暇残数; 1: 時間外超過
    public remainNumber: IRemainNumber = {
        manageYear: false,
        yearRemain: 0,
        manageReserve: false,
        reserveRemain: 0,
        manageCompensatory: false,
        compensatoryRemain: 0,
        manageSubStitute:false,
        substituteRemain: 0,
        nextGrantDate: null
    };
    public time36: ITime36 = {
        time36: 0,					
        maxTime36: 0,						
        excessNumber: 0,						
        maxExcessNumber: 0,
        showAgreement: false					
    };

    public created() {
        let self = this;
        let cache: any = storage.local.getItem('dailyCorrectionState');
        let employeeIdSel = cache.selectedEmployee;
        if (this.params.remainOrtime36 == 0) {//休暇残数
            self.$http.post('at', servicePath.getRemain + employeeIdSel).then((result: any) => {
                let data = result.data;
                self.remainNumber = {
                    manageYear: data.annualLeave.manageYearOff,
                    yearRemain: data.annualLeave.annualLeaveRemain,
                    manageReserve: data.reserveLeave.manageRemainNumber,
                    reserveRemain: data.reserveLeave.remainNumber,
                    manageCompensatory: data.compenLeaveRemain.manageCompenLeave,
                    compensatoryRemain: data.compensatoryLeave.compenLeaveRemain,
                    manageSubStitute: data.substituteRemain.manageAtr,
                    substituteRemain: data.substitutionLeave.holidayRemain,
                    nextGrantDate: data.nextGrantDate
                };
            });
        } else {//時間外超過
            let yearMonth = cache.timePeriodAllInfo.yearMonth;
            let param36 = {
                employeeId: employeeIdSel,
                year: Math.floor(yearMonth / 100),
                month: Math.floor(yearMonth % 100)
            };
            self.$http.post('at', servicePath.get36AgreementInfo, param36).then((result: any) => {
                let time = result.data;
                self.time36 = {
                    time36: time.agreementTime36 || 0,
                    maxTime36: time.maxTime || 0,
                    excessNumber: time.excessFrequency || 0,
                    maxExcessNumber: time.maxNumber || 0,
                    showAgreement: time.showAgreement
                };
            });
        }
    }
}
const servicePath = {
    getRemain : 'screen/at/correctionofdailyperformance/getRemainNum/',
    get36AgreementInfo: 'screen/at/dailyperformance/36AgreementInfo'
};
interface IRemainNumber {
    manageYear: boolean;//年休管理する
    yearRemain: number;//年休残数	
    manageReserve: boolean;//積休管理する		
    reserveRemain: number;//積立年休残数						
    manageCompensatory: boolean;//代休管理する
    compensatoryRemain: number;//代休残数						
    manageSubStitute: boolean;//振休管理する
    substituteRemain: number;//振休残数						
    nextGrantDate: Date;//次回付与日						
}
interface ITime36 {
    time36: number;//超過時間						
    maxTime36: number;//超過上限時間						
    excessNumber: number;//超過回数						
    maxExcessNumber: number;//超過上限回数
    showAgreement: boolean;//36協定情報を表示する						
}
