import { Vue } from '@app/provider';
import { component, Prop, Watch } from '@app/core/component';
import { IAppDispInfoStartupOutput } from '../../../../../kaf/s04/a/define';
import { OptionalItemApplication } from '../../../../../kaf/s20/a/define';

@component({
    name: 'CmmS45ComponentsApp15Component',
    style: require('./style.scss'),
    template: require('./index.vue'),
    validations: {},
    constraints: []
})
export class CmmS45ShrComponentsApp15Component extends Vue {

    public optionalItemApplication: OptionalItemApplication[] | null = [];
    public optionalItemSetting: ISettings = {
        name: '',
        note: '',
        code: ''
    };

    @Prop({
        default: () => ({
            appDispInfoStartupOutput: null,
            appDetail: null
        })
    })
    public readonly params!: {
        appDispInfoStartupOutput: IAppDispInfoStartupOutput,
        appDetail: any,
    };

    @Watch('params.appDispInfoStartupOutput')
    public appDispInfoStartupOutputWatcher() {
        const vm = this;

        vm.fetchData(vm.params);
    }

    public created() {
        const vm = this;

        vm.params.appDetail = {};
        vm.fetchData(vm.params);
    }

    public fetchData(getParams: any) {
        const vm = this;

        vm.$auth.user.then((user: any) => {
            const { companyId } = user;
            const { params } = vm;

            const { appDispInfoStartupOutput } = params;
            const { appDetailScreenInfo } = appDispInfoStartupOutput;

            const { application } = appDetailScreenInfo;
            const { appID } = application;

            vm.$http.post('at', API.startBScreen, {
                companyId,
                applicationId: appID
            }).then((res: any) => {
                vm.params.appDetail = res.data;
                vm.optionalItemSetting = vm.params.appDetail.application;
                const { params } = vm;
                const { appDetail } = params;

                const { application } = appDetail;
                const { optionalItems } = application;

                optionalItems.forEach((item) => {
                    let optionalItem = appDetail.optionalItems.find((optionalItem) => {

                        return optionalItem.optionalItemNo == item.itemNo - 640;
                    });
                    let controlAttendance = appDetail.controlOfAttendanceItems.find((controlAttendance) => {

                        return item.itemNo == controlAttendance.itemDailyID;
                    });

                    const { calcResultRange, optionalItemAtr, optionalItemName, optionalItemNo, unit,description } = optionalItem;
                    const { lowerCheck, upperCheck, amountLower, amountUpper, numberLower, numberUpper, timeLower, timeUpper } = calcResultRange;

                    const { amount, times, time } = item;

                    vm.optionalItemApplication.push({
                        lowerCheck,
                        upperCheck,
                        amountLower,
                        amountUpper,
                        numberLower,
                        numberUpper,
                        timeLower ,
                        timeUpper ,
                        amount,
                        number: times,
                        time,
                        inputUnitOfTimeItem: controlAttendance ? controlAttendance.inputUnitOfTimeItem : null,
                        optionalItemAtr,
                        optionalItemName,
                        optionalItemNo,
                        unit,
                        description
                    });
                    vm.$emit('loading-complete');
                });
            });
        });
    }
}

const API = {
    startBScreen: 'ctx/at/request/application/optionalitem/getDetail'
};

export interface ISettings {
    code: string;
    name: string;
    note: string;
}
