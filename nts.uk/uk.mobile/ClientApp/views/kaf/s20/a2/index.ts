import { component, Prop, Watch } from '@app/core/component';
import * as _ from 'lodash';
import { IOptionalItemAppSet, IOptionalItemDto } from '../a/define';
import { KafS00AComponent, KAFS00AParams } from '../../s00/a';
import { KafS00BComponent, KAFS00BParams } from '../../s00/b';
import { KafS00CComponent, KAFS00CParams } from '../../s00/c';
import { AppType, KafS00ShrComponent } from '../../s00/shr';
import { IAppDispInfoStartupOutput, IApplication } from '../../s04/a/define';

@component({
    name: 'kafs20a2',
    route: '/kaf/s20/a2',
    style: require('./style.scss'),
    template: require('./index.vue'),
    components: {
        'KafS00AComponent': KafS00AComponent,
        'KafS00BComponent': KafS00BComponent,
        'KafS00CComponent': KafS00CComponent,
    },
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class KafS20A2Component extends KafS00ShrComponent {
    public title: string = 'KafS20A2';
    public kafS00AParams: KAFS00AParams | null = null;
    public kafS00BParams: KAFS00BParams | null = null;
    public kafS00CParams: KAFS00CParams | null = null;
    public appDispInfoStartupOutput: IAppDispInfoStartupOutput | null = null;
    public time: number = null;
    public number: number = 10;
    public optionalItems: IOptionalItemDto[] | null = null;
    public application!: IApplication;
    public textConvert: string = '';

    @Prop({ default: () => [] })
    public readonly settingItems!: IOptionalItemAppSet;

    @Prop({ default: () => true })
    public readonly mode!: boolean;

    // @Watch('optionalItems',{deep: true, immediate: true})
    // public optionalItemsWatcher(value: IOptionalItemDto[] | null) {
    //     const vm = this;

    //     if (value) {

    //     }
    // }

    @Watch('appDispInfoStartupOutput', { deep: true, immediate: true })
    public appDispInfoStartupOutputWatcher(value: IAppDispInfoStartupOutput | null) {
        const vm = this;

        vm.$auth.user.then((user: any) => {
            if (value) {
                const { companyId, employeeId } = user;
                const { appDispInfoWithDateOutput, appDispInfoNoDateOutput } = value;
                const { approvalFunctionSet, empHistImport } = appDispInfoWithDateOutput;

                const { appUseSetLst } = approvalFunctionSet;
                const { employmentCode } = empHistImport;
                const { applicationSetting, displayStandardReason, displayAppReason, reasonTypeItemLst } = appDispInfoNoDateOutput;

                const { appDisplaySetting, appTypeSetting, appLimitSetting } = applicationSetting;
                const { receptionRestrictionSetting } = applicationSetting;

                vm.kafS00AParams = {
                    applicationUseSetting: appUseSetLst[0],
                    companyID: companyId,
                    employeeID: employeeId,
                    employmentCD: employmentCode,
                    receptionRestrictionSetting: receptionRestrictionSetting[0],
                    opOvertimeAppAtr: null,
                };
                vm.kafS00BParams = {
                    appDisplaySetting,
                    newModeContent: {
                        useMultiDaySwitch: true,
                        initSelectMultiDay: false,
                        appTypeSetting,
                        appDate: null,
                        dateRange: null,
                    },
                    mode: 0,
                    detailModeContent: null
                };
                vm.kafS00CParams = {
                    displayFixedReason: displayStandardReason,
                    displayAppReason,
                    reasonTypeItemLst,
                    appLimitSetting,
                    opAppStandardReasonCD: null,
                    opAppReason: null,
                };
            }
        });
    }

    get condition(): string {
        const vm = this;

        vm.textConvert = vm.optionalItems.reduce((text, optionalItem, index, optionalItems) => {
            const { optionalItemDto } = optionalItem;
            const { calcResultRange, unit } = optionalItemDto;
            const { lowerCheck, upperCheck, numberLower, numberUpper } = calcResultRange;

            if (lowerCheck && upperCheck) {

                return text = `（入力範囲 ${numberLower} ～ ${numberUpper} 単位 ${unit} `;
            }
        }, '');
        console.log(vm.textConvert);

        return vm.textConvert;
    }

    public beforeCreate() {
        const vm = this;

        vm.application = {
            appDate: '',
            appID: '',
            appType: 0,
            employeeID: '',
            enteredPerson: '',
            inputDate: '',
            opAppEndDate: '',
            opAppReason: '',
            opAppStandardReasonCD: 0,
            opAppStartDate: '',
            opReversionReason: null,
            opStampRequestMode: null,
            prePostAtr: 0,
            reflectionStatus: null,
            version: null,
        };
    }

    public created() {
        const vm = this;
        const { OPTIONAL_ITEM_APPLICATION } = AppType;

        vm.$auth.user
            .then((user: any) => {
            }).then(() => {
                return vm.loadCommonSetting(OPTIONAL_ITEM_APPLICATION);
            }).then((loadData: any) => {
                if (loadData) {
                    vm.$mask('show');
                    let settingNoItems = vm.settingItems.settingItems.map((settingNoItem, index, settingItems) => {

                        return settingNoItem.no;
                    });

                    let params = {
                        settingItemNoList: settingNoItems,
                    };

                    vm.$http
                        .post('at', API.startA2Screen, params)
                        .then((res: any) => {
                            vm.$mask('hide');
                            vm.optionalItems = res.data;
                        }).catch(() => {
                            vm.$mask('hide');
                        });
                }
            });
    }

    public backToStep1() {
        const vm = this;

        vm.$emit('backToStep1');
    }

    public nextToStep3() {
        const vm = this;

        vm.$emit('nextToStep3');
    }
}

const API = {
    startA2Screen: 'screen/at/kaf020/b/get',
    register: 'screen/at/kaf020/b/register',
};