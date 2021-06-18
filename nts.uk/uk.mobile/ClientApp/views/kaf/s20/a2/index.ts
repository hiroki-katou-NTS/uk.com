import { component, Prop, Watch } from '@app/core/component';
import * as _ from 'lodash';
import { IParams, IOptionalItemAppSet, OptionalItemApplication, optionalItems, IControlOfAttendanceItemsDto, IOptionalItemDto } from '../a/define';
import { KafS00AComponent, KAFS00AParams } from '../../s00/a';
import { KafS00BComponent, KAFS00BParams, ScreenMode } from '../../s00/b';
import { KafS00CComponent, KAFS00CParams } from '../../s00/c';
import { AppType, KafS00ShrComponent } from '../../s00/shr';
import { IAppDispInfoStartupOutput, IApplication, IRes } from '../../s04/a/define';
import { CmmS45CComponent } from '../../../cmm/s45/c/index';

@component({
    name: 'kafs20a2',
    route: '/kaf/s20/a2',
    style: require('./style.scss'),
    template: require('./index.vue'),
    components: {
        'KafS00AComponent': KafS00AComponent,
        'KafS00BComponent': KafS00BComponent,
        'KafS00CComponent': KafS00CComponent,
        'cmms45c': CmmS45CComponent
    },
    resource: require('./resources.json'),
    validations: {
        optionalItemApplication: {
            amount: {
                loop: true,
                constraint: 'AnyItemAmount'
            },
            number: {
                loop: true,
                constraint: 'AnyItemTimes'
            },
            time: {
                loop: true,
                constraint: 'AnyItemTime'
            }
        }
    },
    constraints: [
        'nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemAmount',
        'nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemTimes',
        'nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemTime'
    ]
})
export class KafS20A2Component extends KafS00ShrComponent {
    public title: string = 'KafS20A2';
    public kafS00AParams: KAFS00AParams | null = null;
    public kafS00BParams: KAFS00BParams | null = null;
    public kafS00CParams: KAFS00CParams | null = null;
    public appDispInfoStartupOutput: IAppDispInfoStartupOutput | null = null;
    public application!: IApplication;
    public optionalItemApplication: OptionalItemApplication[] = [];
    public isValidateAll: boolean = true;
    public mode: boolean = true;

    @Prop({ default: () => [] })
    public readonly settingItems!: IOptionalItemAppSet;

    @Prop({default : () => {}})
    public params!: IParams;

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

                if (vm.mode) {
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
                            useMultiDaySwitch: false,
                            initSelectMultiDay: false,
                            appTypeSetting,
                            appDate: null,
                            dateRange: null,
                        },
                        mode: ScreenMode.NEW,
                        detailModeContent: null
                    };

                    vm.kafS00CParams = {
                        displayFixedReason: displayStandardReason,
                        displayAppReason,
                        reasonTypeItemLst,
                        appLimitSetting,
                        opAppStandardReasonCD: vm.application.opAppStandardReasonCD,
                        opAppReason: null
                    };
                }
                if (!vm.mode) {
                    const { params } = vm;
                    const { appDispInfoStartupOutput } = params;
                    const { appDetailScreenInfo, appDispInfoNoDateOutput } = appDispInfoStartupOutput;


                    const { application } = appDetailScreenInfo;
                    const { employeeID, opAppStartDate, opAppEndDate, prePostAtr, opAppStandardReasonCD, opAppReason } = application;
                    const { employeeInfoLst } = appDispInfoNoDateOutput;

                    vm.kafS00AParams = {
                        applicationUseSetting: appUseSetLst[0],
                        companyID: companyId,
                        employeeID,
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
                        mode: ScreenMode.DETAIL,
                        detailModeContent: {
                            startDate: opAppStartDate,
                            endDate: opAppEndDate,
                            employeeName: employeeInfoLst[0].bussinessName,
                            prePostAtr,
                        }
                    };

                    vm.kafS00CParams = {
                        displayFixedReason: displayStandardReason,
                        displayAppReason,
                        reasonTypeItemLst,
                        appLimitSetting,
                        opAppStandardReasonCD,
                        opAppReason,
                    };
                }
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
                opAppStandardReasonCD: '',
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
        vm.initService();
    }

    public initService() {
        const vm = this;
        const { OPTIONAL_ITEM_APPLICATION } = AppType;

        Object.assign(window, { vm });

        //mode edit
        if (vm.params) {
            vm.optionalItemApplication = [];
            vm.mode = false;
            const { params } = vm;
            const { appDetail } = params;
            const { application } = appDetail;
            const { optionalItems } = application;

            appDetail.optionalItems.forEach((optionalItem: any) => {
                let item = _.find(optionalItems, {itemNo: optionalItem.optionalItemNo});
                let controlAttendance = _.find(appDetail.controlOfAttendanceItems, {itemDailyID: optionalItem.optionalItemNo + 640});
                const { calcResultRange, optionalItemAtr, optionalItemName, optionalItemNo, unit, description, dispOrder } = optionalItem;
                const { lowerCheck, upperCheck, amountLower, amountUpper, numberLower, numberUpper, timeLower, timeUpper } = calcResultRange;

                vm.optionalItemApplication.push({
                    lowerCheck,
                    upperCheck,
                    amountLower,
                    amountUpper,
                    numberLower,
                    numberUpper,
                    timeLower,
                    timeUpper,
                    amount: item ? item.amount : null,
                    number: item ? item.times : null,
                    time: item ? item.time : null,
                    inputUnitOfTimeItem: controlAttendance ? controlAttendance.inputUnitOfTimeItem : null,
                    optionalItemAtr,
                    optionalItemName,
                    optionalItemNo,
                    unit,
                    description,
                    dispOrder
                });
            });
            vm.optionalItemApplication.sort((a, b) => a.dispOrder - b.dispOrder);
        }

        vm.$auth.user.then((user: any) => {
        }).then(() => {
            return vm.loadCommonSetting(OPTIONAL_ITEM_APPLICATION);
        }).then((loadData: any) => {
            if (loadData && vm.mode) {
                vm.$mask('show');
                let settingNoItems = vm.settingItems.settingItems.map((settingNoItem) => {

                    return settingNoItem.no;
                });

                let params = {
                    optionalItemNos: settingNoItems,
                };
                const firstreq = vm.$http.post('at', API.getControlAttendance, params);
                const seconreq = vm.$http.post('at', API.getListItemNo, params);

                Promise.all([firstreq, seconreq])
                    .then((res: any) => {
                        vm.$mask('hide');

                        let controlAttendances: IControlOfAttendanceItemsDto[] = res[0].data;
                        let optionalNoItems: IOptionalItemDto[] = res[1].data;

                        settingNoItems.forEach((itemNo: number) => {
                            let optionalItem = optionalNoItems.find((optionalItem) => {

                                return optionalItem.optionalItemNo == itemNo;
                            });
                            let controlAttendance = controlAttendances.find((controlAttendance) => {

                                return itemNo == controlAttendance.itemDailyID - 640;
                            });

                            const { calcResultRange, optionalItemAtr, optionalItemName, optionalItemNo, unit, description } = optionalItem;
                            const { lowerCheck, upperCheck, amountRange, numberRange, timeRange } = calcResultRange;
                            const { dailyAmountRange } = amountRange;

                            const { dailyNumberRange } = numberRange;
                            const { dailyTimeRange } = timeRange;

                            vm.optionalItemApplication.push({
                                lowerCheck,
                                upperCheck,
                                amountLower: dailyAmountRange.lowerLimit,
                                amountUpper: dailyAmountRange.upperLimit,
                                numberLower: dailyNumberRange.lowerLimit,
                                numberUpper: dailyNumberRange.upperLimit,
                                timeLower: dailyTimeRange.lowerLimit,
                                timeUpper: dailyTimeRange.upperLimit,
                                amount: null,
                                number: null,
                                time: null,
                                inputUnitOfTimeItem: controlAttendance ? controlAttendance.inputUnitOfTimeItem : null,
                                optionalItemAtr,
                                optionalItemName,
                                optionalItemNo,
                                unit,
                                description,
                                dispOrder: null
                            });
                        });

                        vm.$updateValidator();

                    }).catch((error) => {
                        vm.handleErrorMessage(error);
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

        vm.$http.post('at', API.register, params).then((res: IRes) => {
            vm.$mask('hide');
            vm.$http.post('at', API.reflectApp, res.data.reflectAppIdLst);
            vm.$emit('nextToStep3', res);
        }).catch((error) => {
            vm.$mask('hide');
            vm.handleErrorMessage(error);
        });

    }

    public updateOptionalItem() {
        const vm = this;

        const { params } = vm;
        const { appDispInfoStartupOutput } = params;
        const { appDetailScreenInfo } = appDispInfoStartupOutput;

        const { application } = appDetailScreenInfo;

        application.opAppReason = vm.application.opAppReason;
        application.opReversionReason = vm.application.opReversionReason;
        application.opAppStandardReasonCD = vm.application.opAppStandardReasonCD;
        let optionalItems: optionalItems[] = [];
        vm.optionalItemApplication.forEach((item) => {
            optionalItems.push({
                amount: item.amount,
                itemNo: item.optionalItemNo,
                time: item.time,
                times: item.number,
            });
        });
        let paramsUpdate = {
            application,
            appDispInfoStartup: appDispInfoStartupOutput,
            optItemAppCommand: {
                code: vm.settingItems.code,
                optionalItems
            }
        };
        vm.$mask('show');
        vm.$http.post('at', API.update, paramsUpdate).then((res: any) => {
            vm.$mask('hide');
            vm.$emit('nextToStep3', res);
        }).catch((error: any) => {
            vm.$mask('hide');
            vm.handleErrorMessage(error);
        });
    }

    //handle mess dialog
    public handleErrorMessage(res: any) {
        const vm = this;
        vm.$mask('hide');
        if (res.messageId == 'Msg_197') {
            vm.$modal.error({ messageId: 'Msg_197', messageParams: [] }).then(() => {
                let appID = vm.params.appDispInfoStartupOutput.appDetailScreenInfo.application.appID;
                vm.$modal('cmms45c', { 'listAppMeta': [appID], 'currentApp': appID }).then((newData: IParams) => {
                    vm.params = newData;
                    vm.initService();
                });
            });

            return;
        }
        if (_.isArray(res.errors)) {
            res.errors.forEach((error) => {
                document.querySelector('.item-' + error.parameterIds[1] + ' input').classList.add('is-invalid');
                document.querySelector('.item-' + error.parameterIds[1] + ' .invalid-feedback').innerHTML = '<span>' + error.errorMessage + '</span>';
            });
        } else {
            return vm.$modal.error(res);
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
    getListItemNo: 'ctx/at/record/optionalitem/findByListItemNo',
    update: 'ctx/at/request/application/optionalitem/update',
    reflectApp: 'at/request/application/reflect-app'
};