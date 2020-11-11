import { Vue } from '@app/provider';
import { component, Prop, Watch } from '@app/core/component';
import { IAppDispInfoStartupOutput } from '../../../../../kaf/s04/a/define';
import { OptionalItemApplication } from '../../../../../kaf/s20/a/define';

@component({
    name: 'cmms45shrcomponentsapp15',
    route: '/cmm/s45/shr/components/app15',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class CmmS45ShrComponentsApp15Component extends Vue {
    public title: string = 'CmmS45ShrComponentsApp15';
    public optionalItemApplication: OptionalItemApplication[] | null = [];

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
                    vm.optionalItemApplication.push({
                        lowerCheck: optionalItem.calcResultRange.lowerCheck,
                        upperCheck: optionalItem.calcResultRange.upperCheck,
                        amountLower: optionalItem.calcResultRange.amountLower,
                        amountUpper: optionalItem.calcResultRange.amountUpper,
                        numberLower: optionalItem.calcResultRange.numberLower,
                        numberUpper: optionalItem.calcResultRange.numberUpper,
                        timeLower: optionalItem.calcResultRange.timeLower ? optionalItem.calcResultRange.timeLower : null,
                        timeUpper: optionalItem.calcResultRange.timeUpper ? optionalItem.calcResultRange.timeUpper : null,
                        amount: item.amount,
                        number: item.times,
                        time: item.time,
                        inputUnitOfTimeItem: controlAttendance.inputUnitOfTimeItem ? controlAttendance.inputUnitOfTimeItem : null,
                        optionalItemAtr: optionalItem.optionalItemAtr,
                        optionalItemName: optionalItem.optionalItemName,
                        optionalItemNo: optionalItem.optionalItemNo,
                        unit: optionalItem.unit
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