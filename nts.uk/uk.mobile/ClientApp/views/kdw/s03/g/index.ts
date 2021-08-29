import { Vue, _ } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { storage } from '@app/utils';

@component({
    name: 'kdws03g',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KdwS03GComponent extends Vue {
    @Prop({ default: () => ({ remainDisplay: true }) })
    public readonly params: { 
        remainDisplay: boolean, //0: 休暇残数; 1: 時間外超過
        closureDate: any 
    };
    public remainNumber: IRemainNumber = {
        manageYear: false,
        yearRemain: 0,
        yearRemainTime: 0,
        manageReserve: false,
        reserveRemain: 0,
        manageCompensatory: false,
        compensatoryRemain: 0,
        compensatoryRemainTime: 0,
        manageSubStitute: false,
        substituteRemain: 0,
        nextGrantDate: null,
        manageChildCare: false,
        childCareDay: 0,
        childCareTime: 0,
        manageLongTermCare: false,
        longTermCareDay: 0,
        longTermCareTime: 0,
        grantDays: 0
    };
    public time36: ITime36 = {
        time36: 0,
        maxTime36: 0,
        excessNumber: 0,
        maxExcessNumber: 0,
        showAgreement: false
    };
    public empName: string = '';
    public time36Display: boolean = false;

    public created() {
        let self = this;
        self.$mask('show');
        let cache: any = storage.session.getItem('dailyCorrectionState');
        let employeeIdSel = cache.selectedEmployee;
        self.empName = (_.find(cache.lstEmployee, (c) => c.id == employeeIdSel) || { businessName: '' }).businessName;
        self.time36Display = cache.dPCorrectionMenuDto.timeExcessReferButtonDis;
        //休暇残数
        self.$http.post('at', servicePath.getRemain, {
            employeeId: employeeIdSel,
            closureDate: self.params.closureDate
        }).then((result: any) => {
            self.$mask('hide');
            let data = result.data;
            self.remainNumber = {
                manageYear: data.annualLeave.manageYearOff,
                yearRemain: _.isNull(data.annualLeave.annualLeaveRemain) ? 0 : data.annualLeave.annualLeaveRemain,
                yearRemainTime: _.isNull(data.annualLeave.timeRemain) ? 0 : data.annualLeave.timeRemain,
                manageReserve: data.reserveLeave.manageRemainNumber,
                reserveRemain: data.reserveLeave.remainNumber,
                manageCompensatory: data.compensatoryLeave.manageCompenLeave,
                compensatoryRemain: _.isNull(data.compensatoryLeave.compenLeaveRemain) ? 0 : data.compensatoryLeave.compenLeaveRemain,
                compensatoryRemainTime: _.isNull(data.compensatoryLeave.timeRemain) ? 0 : data.compensatoryLeave.timeRemain,
                manageSubStitute: data.substitutionLeave.manageAtr,
                substituteRemain: data.substitutionLeave.holidayRemain,
                nextGrantDate: data.nextGrantDate,
                manageChildCare: data.childCareVacation.manageNursing,
                childCareDay: _.isNull(data.childCareVacation.remainDays) ? 0 : data.childCareVacation.remainDays,
                childCareTime: data.childCareVacation.remainTime,
                manageLongTermCare: data.longTermCareVacation.manageNursing,
                longTermCareDay: _.isNull(data.longTermCareVacation.remainDays) ? 0 : data.longTermCareVacation.remainDays,
                longTermCareTime: data.longTermCareVacation.remainTime,
                grantDays: data.grantDays
            };
            console.log(self.remainNumber);
        }).catch(() => {
            self.$mask('hide');
        });
        //時間外超過
        if (self.time36Display) {
            let yearMonth = cache.timePeriodAllInfo.yearMonth;
            let param36 = {
                employeeId: employeeIdSel,//社員ID
                year: Math.floor(yearMonth / 100),//年度
                month: Math.floor(yearMonth % 100)//月度
            };
            self.$http.post('at', servicePath.get36AgreementInfo, param36).then((result: any) => {
                self.$mask('hide');
                let time = result.data;
                self.time36 = {
                    time36: time.agreementTime36 || 0,
                    maxTime36: time.maxTime || 0,
                    excessNumber: time.excessFrequency || 0,
                    maxExcessNumber: time.maxNumber || 0,
                    showAgreement: time.showAgreement
                };
            }).catch(() => {
                self.$mask('hide');
            });
        }
    }

    get nextGrantDateStr() {
        const vm = this;

        return (_.isNull(vm.remainNumber.nextGrantDate) ? '' : vm.$dt(vm.remainNumber.nextGrantDate, 'YYYY/MM/DD')) + 
                (_.isNull(vm.remainNumber.grantDays) ? 0 : vm.remainNumber.grantDays) + '日';
    }
}
const servicePath = {
    getRemain: 'screen/at/correctionofdailyperformance/getRemainNum',
    get36AgreementInfo: 'screen/at/dailyperformance/36AgreementInfo'
};
interface IRemainNumber {
    manageYear: boolean;//年休管理する
    yearRemain: number;//年休残数	
    yearRemainTime: number; // 時間年休残数
    manageReserve: boolean;//積休管理する		
    reserveRemain: number;//積立年休残数						
    manageCompensatory: boolean;//代休管理する
    compensatoryRemain: number;//代休残数
    compensatoryRemainTime: number; // 時間代休残数				
    manageSubStitute: boolean;//振休管理する
    substituteRemain: number;//振休残数						
    nextGrantDate: Date;//次回付与日
    manageChildCare: boolean; // 子の育児管理する
    childCareDay: number; // 子の看護残数
    childCareTime: number; // 時間子の看護残数
    manageLongTermCare: boolean; // 介護管理する
    longTermCareDay: number; // 介護残数
    longTermCareTime: number; // 時間介護残数 	 
    grantDays: number; // 次回年休付与日数			
}
interface ITime36 {
    time36: number;//超過時間						
    maxTime36: number;//超過上限時間						
    excessNumber: number;//超過回数						
    maxExcessNumber: number;//超過上限回数
    showAgreement: boolean;//36協定情報を表示する						
}
