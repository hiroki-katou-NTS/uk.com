/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.f {

    interface IParams {
        type: string;
        selectId: string;
    }

    const API = {
        DISPLAY_BASICSETTING: 'screen/at/kmk004/getDisplayBasicSetting',
        GET_SETTING_WORKPLACE: 'screen/at/kmk004/viewc/wkp/getBaseSetting',
        GET_SETTING_EMPLOYMENT: 'screen/at/kmk004/viewd/emp/getBaseSetting',
        GET_SETTING_EMPLOYEE: 'screen/at/kmk004/viewe/sha/getBaseSetting'
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
        public valueInsurrance: KnockoutObservable<string> = ko.observable('1');
        public valueSurcharges: KnockoutObservable<string> = ko.observable('1');
        public checkCompany: KnockoutObservable<boolean> = ko.observable(true);
        public attendance: KnockoutObservable<boolean> = ko.observable(true);
        public type = '';
        public selectId = '';

        public model: Model = new Model();

        created(params?: IParams) {
            const vm = this;

            vm.type = params.type;
            vm.selectId = params.selectId;

            $(document).ready(function () {
                $('.input-forcus').focus();
            });
        }

        mounted() {
            const vm = this;
            vm.init();
            vm.model.deforWorkSurchargeWeekMonth.valueHasMutated();
            vm.model.outsideSurchargeWeekMonth.valueHasMutated();
            vm.reloadData();
        }

        closeDialog() {
            const vm = this;
            vm.$window.close();
        }

        init() {
            const vm = this;
            vm.model.deforWorkSurchargeWeekMonth.subscribe(() => {
                if (ko.unwrap(vm.model.deforWorkSurchargeWeekMonth)) {
                    vm.valueInsurrance('1');
                } else {
                    vm.valueInsurrance('0');
                }
            })

            vm.model.outsideSurchargeWeekMonth.subscribe(() => {
                if (ko.unwrap(vm.model.outsideSurchargeWeekMonth)) {
                    vm.valueSurcharges('1');
                } else {
                    vm.valueSurcharges('0');
                }
            })
        }

        reloadData() {
            const vm = this;
            switch (vm.type) {
                case 'Com_Company':
                    vm.$blockui('invisible')
                        .then(() => vm.$ajax(API.DISPLAY_BASICSETTING))
                        .then((data: IModel) => {
                            vm.model.update(data)
                            console.log(data);
                        })
                        .then(() => vm.$blockui('clear'));
                    break;
                case 'Com_Workplace':
                    if (ko.unwrap(vm.selectId) !== '') {
                        vm.$blockui('invisible')
                            .then(() => vm.$ajax(API.GET_SETTING_WORKPLACE + "/" + ko.unwrap(vm.selectId)))
                            .then((data: IModel) => {
                                vm.model.update(data)
                            })
                            .then(() => vm.$blockui('clear'));
                    }
                    break;
                case 'Com_Employment':
                    if (ko.unwrap(vm.selectId) !== '') {
                        vm.$blockui('invisible')
                            .then(() => vm.$ajax(API.GET_SETTING_EMPLOYMENT + "/" + ko.unwrap(vm.selectId)))
                            .then((data: IModel) => {
                                vm.model.update(data)
                            })
                            .then(() => vm.$blockui('clear'));
                    }
                    break;
                case 'Com_Person':
                    if (ko.unwrap(vm.selectId) !== '') {
                        vm.$blockui('invisible')
                            .then(() => vm.$ajax(API.GET_SETTING_EMPLOYEE + "/" + ko.unwrap(vm.selectId)))
                            .then((data: IModel) => {
                                vm.model.update(data)
                            })
                            .then(() => vm.$blockui('clear'));
                    }
                    break;
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
    deforWorkSurchargeWeekMonth: KnockoutObservable<boolean> = ko.observable(true);
    deforWorkLegalOverTimeWork: KnockoutObservable<boolean> = ko.observable(false);;
    deforWorkLegalHoliday: KnockoutObservable<boolean> = ko.observable(false);;
    outsideSurchargeWeekMonth: KnockoutObservable<boolean> = ko.observable(true);
    outsidedeforWorkLegalOverTimeWork: KnockoutObservable<boolean> = ko.observable(false);;
    outsidedeforWorkLegalHoliday: KnockoutObservable<boolean> = ko.observable(false);;

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