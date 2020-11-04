import { component, Prop, Watch } from '@app/core/component';
import * as _ from 'lodash';
import { IOptionalItemAppSet, IOptionalItem, OptionalItemApplication, optionalItems, IControlOfAttendanceItemsDto, IOptionalItemDto } from '../a/define';
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
    public application!: IApplication;
    public optionalItemApplication: OptionalItemApplication[] | null = [];
    public isValidateAll: Boolean = true;

    @Prop({ default: () => [] })
    public readonly settingItems!: IOptionalItemAppSet;

    @Prop({ default: () => true })
    public readonly mode!: boolean;


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

    public beforeCreate() {
        const vm = this;

        vm.$auth.user.then((user: any) => {
            vm.application = {
                appDate: '',
                appID: '',
                appType: AppType.OPTIONAL_ITEM_APPLICATION,
                employeeID: user.employeeId,
                enteredPerson: user.employeeId,
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
        });
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
                        optionalItemNos: settingNoItems,
                    };

                    //     vm.$http
                    //         .post('at', API.getListItemNo, settingNoItems)
                    //         .then((res: any) => {
                    //             vm.$mask('hide');
                    //             vm.optionalItems = res.data;
                    //             console.log(res.data);
                    //         }).catch(() => {
                    //             vm.$mask('hide');
                    //         });
                    // }

                    Promise.all([vm.$http.post('at', API.getControlAttendance, settingNoItems), vm.$http.post('at', API.getListItemNo, settingNoItems)]).then((res: any) => {
                        vm.$mask('hide');

                        let controlAttendances: IControlOfAttendanceItemsDto[] = res[0].data;
                        let optionalNoItems: IOptionalItemDto[] = res[1].data;

                        settingNoItems.forEach((itemNo) => {

                            let optionalItems = optionalNoItems.filter((optionalItem, index, optionalNoItems) => {

                                return optionalItem.optionalItemNo === itemNo;
                            });

                            let controlAttendaces = controlAttendances.filter((controlAttend, index, controlAttendances) => {

                                return controlAttend.itemDailyID == 640 + itemNo;
                            });
                            

                            optionalItems.forEach((optionalItem) => {
                                controlAttendaces.forEach((controlAttendance) => {
                                    vm.optionalItemApplication.push({
                                        lowerCheck: optionalItem.calcResultRange.lowerCheck,
                                        upperCheck: optionalItem.calcResultRange.upperCheck,
                                        amountLower: optionalItem.calcResultRange.amountLower,
                                        amountUpper: optionalItem.calcResultRange.amountUpper,
                                        numberLower: optionalItem.calcResultRange.numberLower,
                                        numberUpper: optionalItem.calcResultRange.numberUpper,
                                        timeLower: optionalItem.calcResultRange.timeLower ? optionalItem.calcResultRange.timeLower : null,
                                        timeUpper: optionalItem.calcResultRange.timeUpper ? optionalItem.calcResultRange.timeUpper : null,
                                        amount: null,
                                        number: null,
                                        time: null,
                                        inputUnitOfTimeItem: controlAttendance.inputUnitOfTimeItem ? controlAttendance.inputUnitOfTimeItem : null,
                                        optionalItemAtr: optionalItem.optionalItemAtr,
                                        optionalItemName: optionalItem.optionalItemName,
                                        optionalItemNo: optionalItem.optionalItemNo,
                                        unit: optionalItem.unit
                                    });
                                });
                            });
                            console.log(vm.optionalItemApplication);
                        });
                        // console.log(controlAttendances);
                        // console.log(optionalNoItems);
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

        let validAll: boolean = true;
        //check validate all
        vm.$mask('show');
        for (let child of vm.$children) {
            child.$validate();
            if (!child.$valid) {
                validAll = false;
            }
        }
        vm.isValidateAll = validAll;
        vm.$validate();
        if (!vm.$valid || !validAll) {
            vm.$nextTick(() => {
                vm.$mask('hide');
            });

            window.scrollTo(50, 100);

            return;
        }

        vm.register();
    }

    public register() {
        const vm = this;

        let optionalItems: optionalItems[] = [];
        vm.optionalItemApplication.forEach((item) => {
            optionalItems.push(({
                amount: item.amount,
                time: item.time,
                times: item.number,
                itemNo: item.optionalItemNo,
            }));
        });
        let params = {
            application: vm.application,
            appDispInfoStartup: vm.appDispInfoStartupOutput,
            optItemAppCommand: {
                code: vm.settingItems.code,
                optionalItems
            }
        };
        vm.$mask('show');

        vm.$http.post('at', API.register, params).then((res: any) => {
            vm.$mask('hide');
            vm.$emit('nextToStep3', res);
        }).catch((error) => {
            vm.$mask('hide');
            vm.$modal.error({ messageId: error.messageId, messageParams: error.parameterIds[0] });
        });

    }

    //handle mess dialog
    public handleErrorMessage(res: any) {
        const vm = this;
        vm.$mask('hide');
        if (res.messageId) {
            return vm.$modal.error({ messageId: res.messageId, messageParams: res.parameterIds });
        } else {

            if (_.isArray(res.errors)) {
                return vm.$modal.error({ messageId: res.errors[0].messageId, messageParams: res.parameterIds });
            } else {
                return vm.$modal.error({ messageId: res.errors.messageId, messageParams: res.parameterIds });
            }
        }
    }

    public handleKaf00BChangePrePost(prePost) {
        const vm = this;

        vm.application.prePostAtr = prePost;
    }

    public handleKaf00BChangeDate(changeDate) {
        const vm = this;

        vm.application.opAppStartDate = vm.$dt.date(changeDate.startDate, 'YYYY/MM/DD');
        vm.application.opAppEndDate = vm.$dt.date(changeDate.endDate, 'YYYY/MM/DD');
        vm.application.appDate = vm.$dt.date(changeDate.startDate, 'YYYY/MM/DD');
    }

    public handleKaf00CChangeAppReason(appReason) {
        const vm = this;

        vm.application.opAppReason = appReason;
    }

    public handleKaf00CChangeReasonCD(appReasonCD) {
        const vm = this;

        vm.application.opAppStandardReasonCD = appReasonCD;
    }
}

const API = {
    register: 'ctx/at/request/application/optionalitem/register',
    getControlAttendance: 'ctx/at/request/application/optionalitem/getControlAttendance',
    getListItemNo: 'ctx/at/record/optionalitem/findByListItemNo'
};