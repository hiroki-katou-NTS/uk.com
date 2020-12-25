import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { CalculationResult } from '../shr';
import { KafS00CComponent } from 'views/kaf/s00/c';
import { KafS00ShrComponent, AppType, Application } from 'views/kaf/s00/shr';
import {ITimeLeaveAppDispInfo} from '../a/define';
@component({
    name: 'kafs12a2',
    route: '/kaf/s12/a2',
    style: require('./style.scss'),
    template: require('./index.vue'),
    components: {
        'kafs00c': KafS00CComponent
    },
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KafS12A2Component extends KafS00ShrComponent {
    public application: Application = null;
    public hoursOfWorkTypeFromA1: number = null;
    public hoursOfWorkTypeFromA12: number = null;

    @Prop({ default: () => ({ mode: true }) })
    public readonly mode!: boolean;

    @Prop({ default: (): ITimeLeaveAppDispInfo => ({
        appDispInfoStartupOutput: null,
        reflectSetting: null,
        timeLeaveManagement: null,
        timeLeaveRemaining: null,
        workingConditionItem: null,
    })})
    public readonly timeLeaveAppDispInfo!: ITimeLeaveAppDispInfo;

    //Calculate result value
    public calculateResult1 = new CalculationResult({
        frame: 0,
        workHeader: 'KAFS12_20',
        requiredTime: 1,
        applicationTime: 2,
        appliesTime: [
            {
                frame: 0,
                hoursOfWorkType: null,
                title: 'Com_ExsessHoliday'
            },
            {
                frame: 1,
                hoursOfWorkType: null,
                title: 'KAFS12_24'
            },
            {
                frame: 2,
                hoursOfWorkType: null,
                title: 'KAFS12_25'
            },
            {
                frame: 3,
                hoursOfWorkType: null,
                title: 'Com_ChildNurseHoliday'
            },
            {
                frame: 4,
                hoursOfWorkType: null,
                title: 'Com_CareHoliday'
            }
        ]
    });
    public calculateResult2 = new CalculationResult({
        frame: 1,
        workHeader: 'KAFS12_20',
        requiredTime: 1,
        applicationTime: 2,
        appliesTime: [
            {
                frame: 0,
                hoursOfWorkType: null,
                title: 'Com_ExsessHoliday'
            },
            {
                frame: 1,
                hoursOfWorkType: null,
                title: 'KAFS12_24'
            },
            {
                frame: 2,
                hoursOfWorkType: null,
                title: 'KAFS12_25'
            },
            {
                frame: 3,
                hoursOfWorkType: null,
                title: 'Com_ChildNurseHoliday'
            },
            {
                frame: 4,
                hoursOfWorkType: null,
                title: 'Com_CareHoliday'
            },
        ]
    });
    public calculateResult3 = new CalculationResult({
        frame: 2,
        workHeader: 'KAFS12_20',
        requiredTime: 1,
        applicationTime: 2,
        appliesTime: [
            {
                frame: 0,
                hoursOfWorkType: null,
                title: 'Com_ExsessHoliday'
            },
            {
                frame: 1,
                hoursOfWorkType: null,
                title: 'KAFS12_24'
            },
            {
                frame: 2,
                hoursOfWorkType: null,
                title: 'KAFS12_25'
            },
            {
                frame: 3,
                hoursOfWorkType: null,
                title: 'Com_ChildNurseHoliday'
            },
            {
                frame: 4,
                hoursOfWorkType: null,
                title: 'Com_CareHoliday'
            }
        ]
    });
    public calculateResult4 = new CalculationResult({
        frame: 3,
        workHeader: 'KAFS12_20',
        requiredTime: 1,
        applicationTime: 2,
        appliesTime: [
            {
                frame: 0,
                hoursOfWorkType: null,
                title: 'Com_ExsessHoliday'
            },
            {
                frame: 1,
                hoursOfWorkType: null,
                title: 'KAFS12_24'
            },
            {
                frame: 2,
                hoursOfWorkType: null,
                title: 'KAFS12_25'
            },
            {
                frame: 3,
                hoursOfWorkType: null,
                title: 'Com_ChildNurseHoliday'
            },
            {
                frame: 4,
                hoursOfWorkType: null,
                title: 'Com_CareHoliday'
            }
        ]
    });
    public calculateResult5 = new CalculationResult({
        frame: 4,
        workHeader: 'KAFS12_20',
        requiredTime: 1,
        applicationTime: 2,
        appliesTime: [
            {
                frame: 0,
                hoursOfWorkType: null,
                title: 'Com_ExsessHoliday'
            },
            {
                frame: 1,
                hoursOfWorkType: null,
                title: 'KAFS12_24'
            },
            {
                frame: 2,
                hoursOfWorkType: null,
                title: 'KAFS12_25'
            },
            {
                frame: 3,
                hoursOfWorkType: null,
                title: 'Com_ChildNurseHoliday'
            },
            {
                frame: 4,
                hoursOfWorkType: null,
                title: 'Com_CareHoliday'
            }
        ]
    });
    public calculateResult6 = new CalculationResult({
        frame: 5,
        workHeader: 'KAFS12_20',
        requiredTime: 1,
        applicationTime: 2,
        appliesTime: [
            {
                frame: 0,
                hoursOfWorkType: null,
                title: 'Com_ExsessHoliday'
            },
            {
                frame: 1,
                hoursOfWorkType: null,
                title: 'KAFS12_24'
            },
            {
                frame: 2,
                hoursOfWorkType: null,
                title: 'KAFS12_25'
            },
            {
                frame: 3,
                hoursOfWorkType: null,
                title: 'Com_ChildNurseHoliday'
            },
            {
                frame: 4,
                hoursOfWorkType: null,
                title: 'Com_CareHoliday'
            }
        ]
    });

    public calculateResultLst = [
        this.calculateResult1,
        this.calculateResult2,
        this.calculateResult3,
        this.calculateResult4,
        this.calculateResult5,
        this.calculateResult6,
    ];

    public dropDownList: any[] = [{
        code: 1,
        name: 'Test 1'
    }, {
        code: 2,
        name: 'Test 2'
    }, {
        code: 3,
        name: 'Test 3'
    }];

    public created() {
        const vm = this;

        vm.application = vm.createApplicationInsert(AppType.ANNUAL_HOLIDAY_APPLICATION);
        vm.$auth.user
            .then((user: any) => { })
            .then(() => {

                return vm.loadCommonSetting(AppType.ANNUAL_HOLIDAY_APPLICATION);
            })
            .then((loadData: boolean) => {
                if (loadData) {
                    vm.updateKaf000_C_Params(true);
                }
            });
    }

    public nextToStep3() {
        const vm = this;

        vm.$emit('next-to-step-three', {});
    }

    public kaf000CChangeReasonCD(opAppStandardReasonCD) {
        const vm = this;
        vm.application.opAppStandardReasonCD = opAppStandardReasonCD;
    }

    public kaf000CChangeAppReason(opAppReason) {
        const vm = this;
        vm.application.opAppReason = opAppReason;
    }
}
