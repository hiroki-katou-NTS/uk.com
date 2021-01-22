/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.f {

    interface IParams {
        type: string;
        selectId: string;
        nameSynthetic: string;
        isSetting: boolean;
    }

    const API = {
        DISPLAY_BASICSETTING: 'screen/at/kmk004/getDisplayBasicSetting',
        GET_SETTING_WORKPLACE: 'screen/at/kmk004/viewc/wkp/getBaseSetting',
        GET_SETTING_EMPLOYMENT: 'screen/at/kmk004/viewd/emp/getBaseSetting',
        GET_SETTING_EMPLOYEE: 'screen/at/kmk004/viewe/sha/getBaseSetting',

        ADD_OR_UPDATE_COM: 'screen/at/kmk004/viewf/com/setting/update',
        DELETE_COM: 'screen/at/kmk004/viewf/com/setting/delete',

        ADD_OR_UPDATE_WORKPLACE: 'screen/at/kmk004/viewf/wkp/setting/update',
        DELETE_WORKPLACE: 'screen/at/kmk004/viewf/wkp/setting/delete',

        ADD_OR_UPDATE_EMPLOYMENT: 'screen/at/kmk004/viewf/emp/setting/update',
        DELETE_EMPLOYMENT: 'screen/at/kmk004/viewf/emp/setting/delete',

        ADD_OR_UPDATE_EMPLOYEE: 'screen/at/kmk004/viewf/sha/setting/update',
        DELETE_EMPLOYEE: 'screen/at/kmk004/viewf/sha/setting/delete',
    }

    @bean()
    export class ViewModel extends ko.ViewModel {

        public swInsurrance: KnockoutObservableArray<ISwitch> = ko.observableArray([
            { code: '1', name: this.$i18n('KMK004_244') },
            { code: '0', name: this.$i18n('KMK004_245') }
        ]);

        public surcharges: ISwitch[] = [
            { code: '1', name: this.$i18n('KMK004_250') },
            { code: '0', name: this.$i18n('KMK004_251') }
        ];
        public valueInsurrance: KnockoutObservable<string> = ko.observable('0');
        public valueSurcharges: KnockoutObservable<string> = ko.observable('0');
        public checkCompany: KnockoutObservable<boolean> = ko.observable(false);
        public checkWorkPlace: KnockoutObservable<boolean> = ko.observable(true);
        public checkEmployment: KnockoutObservable<boolean> = ko.observable(true);
        public checkEmployee: KnockoutObservable<boolean> = ko.observable(true);
        public attendance: KnockoutObservable<boolean> = ko.observable(false);
        public nameSynthetic: KnockoutObservable<string> = ko.observable('    ');


        public type = '';
        public selectId = '';

        public model: Model = new Model();

        created(params?: IParams) {
            const vm = this;

            vm.$blockui('grayout')
                .then(() => {
                    vm.type = params.type;
                    vm.selectId = params.selectId;
                    vm.nameSynthetic(params.nameSynthetic);
                    vm.attendance(params.isSetting);

                    vm.init();
                    vm.reloadData();

                    switch (vm.type) {
                        case 'Com_Company':
                            vm.checkCompany(true);
                            vm.attendance(true);
                            break;
                        case 'Com_Workplace':
                            vm.checkWorkPlace(false);
                            break;
                        case 'Com_Employment':
                            vm.checkEmployment(false);
                            break;
                        case 'Com_Person':
                            vm.checkEmployee(false);
                            break;
                    }
                })
                .then(() => {
                    vm.$blockui('clear');
                });
        }

        mounted() {
            const vm = this;

            $(document).ready(function () {
                $('.input-forcus').focus();
            });
        }

        closeDialog() {
            const vm = this;
            vm.$window.close();
        }

        add() {
            const vm = this;
            switch (ko.unwrap(vm.valueInsurrance)) {
                case '0':
                    vm.model.deforWorkSurchargeWeekMonth(false);
                    break;
                case '1':
                    vm.model.deforWorkSurchargeWeekMonth(true);
                    break
            }
            switch (ko.unwrap(vm.valueSurcharges)) {
                case '0':
                    vm.model.outsideSurchargeWeekMonth(false);
                    break;
                case '1':
                    vm.model.outsideSurchargeWeekMonth(true);
                    break
            }

            const input = {
                daily: ko.unwrap(vm.model.daily),
                weekly: ko.unwrap(vm.model.weekly),
                deforWorkSurchargeWeekMonth: ko.unwrap(vm.model.deforWorkSurchargeWeekMonth),
                deforWorkLegalOverTimeWork: ko.unwrap(vm.model.deforWorkLegalOverTimeWork),
                deforWorkLegalHoliday: ko.unwrap(vm.model.deforWorkLegalHoliday),
                outsideSurchargeWeekMonth: ko.unwrap(vm.model.outsideSurchargeWeekMonth),
                outsidedeforWorkLegalOverTimeWork: ko.unwrap(vm.model.outsidedeforWorkLegalOverTimeWork),
                outsidedeforWorkLegalHoliday: ko.unwrap(vm.model.outsidedeforWorkLegalHoliday),
            }

            const inputById = {
                id: ko.unwrap(vm.selectId),
                handlerCommon: input
            }

            vm.$blockui('invisible')
                .then(() => {
                    vm.validate()
                        .then((valid: boolean) => {
                            if (valid) {

                                switch (vm.type) {
                                    case 'Com_Company':
                                        vm.$ajax(API.ADD_OR_UPDATE_COM, input)
                                            .then(() => vm.$dialog.info({ messageId: 'Msg_15' }))
                                            .then(vm.$window.close);
                                        break;
                                    case 'Com_Workplace':
                                        vm.$ajax(API.ADD_OR_UPDATE_WORKPLACE, inputById)
                                            .then(() => vm.$dialog.info({ messageId: 'Msg_15' }))
                                            .then(vm.$window.close);
                                        break;
                                    case 'Com_Employment':
                                        vm.$ajax(API.ADD_OR_UPDATE_EMPLOYMENT, inputById)
                                            .then(() => vm.$dialog.info({ messageId: 'Msg_15' }))
                                            .then(vm.$window.close);
                                        break;
                                    case 'Com_Person':
                                        vm.$ajax(API.ADD_OR_UPDATE_EMPLOYEE, inputById)
                                            .then(() => vm.$dialog.info({ messageId: 'Msg_15' }))
                                            .then(vm.$window.close);
                                        break;
                                }
                            }
                        });
                })
                .always(() => vm.$blockui('clear'));
        }

        remote() {
            const vm = this;
            const inputByIdDelete = {
                id: ko.unwrap(vm.selectId)
            }
            nts.uk.ui.dialog
                .confirm({ messageId: "Msg_18" })
                .ifYes(() => {
                    vm.$blockui("invisible")
                        .then(() => {
                            switch (vm.type) {
                                case 'Com_Workplace':
                                    vm.$ajax(API.DELETE_WORKPLACE, inputByIdDelete)
                                        .then(() => vm.$dialog.info({ messageId: 'Msg_16' }))
                                        .then(vm.$window.close);
                                    break;
                                case 'Com_Employment':
                                    vm.$ajax(API.DELETE_EMPLOYMENT, inputByIdDelete)
                                        .then(() => vm.$dialog.info({ messageId: 'Msg_16' }))
                                        .then(vm.$window.close);
                                    break;
                                case 'Com_Person':
                                    vm.$ajax(API.DELETE_EMPLOYEE, inputByIdDelete)
                                        .then(() => vm.$dialog.info({ messageId: 'Msg_16' }))
                                        .then(vm.$window.close);
                                    break;
                            }
                        })
                        .always(() => vm.$blockui("clear"));
                });
        }

        init() {
            const vm = this;
            vm.model.deforWorkSurchargeWeekMonth.subscribe(() => {
                if (ko.unwrap(vm.model.deforWorkSurchargeWeekMonth)) {
                    vm.valueInsurrance('1');
                } else {
                    vm.valueInsurrance('0');
                }
            });

            vm.model.outsideSurchargeWeekMonth.subscribe(() => {
                if (ko.unwrap(vm.model.outsideSurchargeWeekMonth)) {
                    vm.valueSurcharges('1');
                } else {
                    vm.valueSurcharges('0');
                }
            });
        }

        reloadData() {
            const vm = this;
            switch (vm.type) {
                case 'Com_Company':
                    vm.$blockui('grayout')
                        .then(() => vm.$ajax(API.DISPLAY_BASICSETTING))
                        .then((data: IModel) => {
                            vm.model.update(data);
                        })
                        .then(() => vm.$blockui('clear'));
                    break;
                case 'Com_Workplace':
                    if (ko.unwrap(vm.selectId) !== '') {
                        vm.$blockui('grayout')
                            .then(() => vm.$ajax(API.GET_SETTING_WORKPLACE + "/" + ko.unwrap(vm.selectId)))
                            .then((data: IModel) => {
                                vm.model.update(data);
                            })
                            .then(() => vm.$blockui('clear'));
                    }
                    break;
                case 'Com_Employment':
                    if (ko.unwrap(vm.selectId) !== '') {
                        vm.$blockui('grayout')
                            .then(() => vm.$ajax(API.GET_SETTING_EMPLOYMENT + "/" + ko.unwrap(vm.selectId)))
                            .then((data: IModel) => {
                                vm.model.update(data);
                            })
                            .then(() => vm.$blockui('clear'));
                    }
                    break;
                case 'Com_Person':
                    if (ko.unwrap(vm.selectId) !== '') {
                        vm.$blockui('grayout')
                            .then(() => vm.$ajax(API.GET_SETTING_EMPLOYEE + "/" + ko.unwrap(vm.selectId)))
                            .then((data: IModel) => {
                                vm.model.update(data);
                            })
                            .then(() => vm.$blockui('clear'));
                    }
                    break;
            }
        }

        public validate(action: 'clear' | undefined = undefined) {
            if (action === 'clear') {
                return $.Deferred().resolve()
                    .then(() => $('.nts-input').ntsError('clear'));
            } else {
                return $.Deferred().resolve()
                    .then(() => $('.nts-input').trigger("validate"))
                    .then(() => !$('.nts-input').ntsError('hasError'));
            }
        }
    }
}

interface ISwitch {
    code: string;
    name: string;
}

interface IModel {
    daily: number;
    weekly: number;
    deforWorkSurchargeWeekMonth: boolean;
    deforWorkLegalOverTimeWork: boolean;
    deforWorkLegalHoliday: boolean;
    outsideSurchargeWeekMonth: boolean
    outsidedeforWorkLegalOverTimeWork: boolean;
    outsidedeforWorkLegalHoliday: boolean;
}

class Model {
    daily: KnockoutObservable<number> = ko.observable(0);
    weekly: KnockoutObservable<number> = ko.observable(0);
    deforWorkSurchargeWeekMonth: KnockoutObservable<boolean> = ko.observable(false);
    deforWorkLegalOverTimeWork: KnockoutObservable<boolean> = ko.observable(false);
    deforWorkLegalHoliday: KnockoutObservable<boolean> = ko.observable(false);
    outsideSurchargeWeekMonth: KnockoutObservable<boolean> = ko.observable(false);
    outsidedeforWorkLegalOverTimeWork: KnockoutObservable<boolean> = ko.observable(false);
    outsidedeforWorkLegalHoliday: KnockoutObservable<boolean> = ko.observable(false);

    constructor(params?: IModel) {
        this.update(params);
    }

    public update(params?: IModel) {
        const self = this;

        if (params) {
            self.daily(params.daily);
            self.weekly(params.weekly);
            self.deforWorkSurchargeWeekMonth(params.deforWorkSurchargeWeekMonth);
            self.deforWorkLegalOverTimeWork(params.deforWorkLegalOverTimeWork);
            self.deforWorkLegalHoliday(params.deforWorkLegalHoliday);
            self.outsideSurchargeWeekMonth(params.outsideSurchargeWeekMonth);
            self.outsidedeforWorkLegalOverTimeWork(params.outsidedeforWorkLegalOverTimeWork);
            self.outsidedeforWorkLegalHoliday(params.outsidedeforWorkLegalHoliday);
        }
    }
}