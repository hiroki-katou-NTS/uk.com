/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.b {

    const API = {
        DISPLAY_BASICSETTING: 'screen/at/kmk004/getDisplayBasicSetting',
    }

    const template = `
        <div class="table-view">
            <table>
                <tbody>
                    <tr class= "label-row">
                        <th>
                            <div class="content-tab" data-bind="i18n: 'KMK004_213'"></div>
                        </th>
                        <th>
                            <div class="content-tab" data-bind="i18n: 'KMK004_214'"></div>
                        </th>
                    </tr>
                    <tr class= "label-row">
                        <td class="label-column">
                            <div class="content-tab" data-bind="i18n: tabSetting.daily"></div>
                        </td>
                        <td class="label-column">
                            <div class="content-tab" data-bind="i18n: tabSetting.weekly"></div>
                        </td>
                    </tr>
                    <tr class= "label-row">
                        <th colspan="3">
                            <div data-bind="i18n: 'KMK004_215'"></div>
                        </th>
                    </tr>
                    <tr class= "label-row">
                        <td class="label-column">
                            <div class="content-tab" data-bind="i18n: model.surcharge1"></div>
                        </td>
                        <td class="label-column">
                            <div class="content-tab" data-bind="i18n: model.surcharge2"></div>
                        </td>
                        <td class="label-column">
                            <div class="content-tab" data-bind="i18n: model.surcharge3"></div>
                        </td>
                    </tr>
                    <tr class= "label-row">
                        <th colspan="3">
                            <div data-bind="i18n: 'KMK004_220'"></div>
                        </th>
                    </tr>
                    <tr class= "label-row">
                        <td class="label-column">
                            <div class="content-tab" data-bind="i18n: model.surchargeOvertime1"></div>
                        </td>
                        <td class="label-column">
                            <div class="content-tab" data-bind="i18n: model.surchargeOvertime2"></div>
                        </td>
                        <td class="label-column">
                            <div class="content-tab" data-bind="i18n: model.surchargeOvertime3"></div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <style type="text/css" rel="stylesheet">
            .table-view{
                padding: 20px;
                border: 2px solid #B1B1B1;
                border-radius: 15px;
                margin-left: 15px;
            }

            .table-view table {
                border-collapse: collapse;
            }
            
            .table-view th {
                background: #E0F59E;
            }

            .table-view .label-row, th {
                border: 1px solid gray;
            }

            .table-view .label-row .label-column {
                border: 1px solid gray;
              }

            .table-view .content-tab {
                padding: 3px;
                text-align: center;
            }
        </style>
        <style type="text/css" rel="stylesheet" data-bind="html: $component.style"></style>
    `;

    @component({
        name: 'basic-setting',
        template
    })

    class Setting extends ko.ViewModel {
        public model = new DataModel();
        public tabSetting = new TabSetting();
        public modeCheckChangeSetting: KnockoutObservable<string>;

        created(params: any) {
            const vm = this;

            vm.reloadData();

            vm.modeCheckChangeSetting = params.modeCheckChangeSetting;
            vm.modeCheckChangeSetting
                .subscribe(() => {
                    vm.reloadData();
                });
        }

        mounted() {
            const vm = this;

            if (!ko.unwrap(vm.tabSetting.deforWorkSurchargeWeekMonth)) {
                vm.model.surcharge1('KMK004_245');
            }
            if (!ko.unwrap(vm.tabSetting.deforWorkLegalOverTimeWork)) {
                vm.model.surcharge2('KMK004_217');
            }
            if (!ko.unwrap(vm.tabSetting.deforWorkLegalHoliday)) {
                vm.model.surcharge3('KMK004_219');
            }
            if (!ko.unwrap(vm.tabSetting.outsideSurchargeWeekMonth)) {
                vm.model.surchargeOvertime1('KMK004_251');
            }
            if (!ko.unwrap(vm.tabSetting.outsidedeforWorkLegalOverTimeWork)) {
                vm.model.surchargeOvertime2('KMK004_217');
            }
            if (!ko.unwrap(vm.tabSetting.outsidedeforWorkLegalHoliday)) {
                vm.model.surchargeOvertime3('KMK004_219');
            }

        }

        reloadData() {
            const vm = this;

            vm.$blockui('invisible')
                .then(() => vm.$ajax(API.DISPLAY_BASICSETTING))
                .then((data: ITabSetting) => {
                    vm.tabSetting.create(data)
                })
                .then(() => vm.$blockui('clear'));
        }
    }

    interface IDataModel {
        surcharge1: string;
        surcharge2: string;
        surcharge3: string;
        surchargeOvertime1: string;
        surchargeOvertime2: string;
        surchargeOvertime3: string;
    }

    class DataModel {
        surcharge1: KnockoutObservable<string> = ko.observable('KMK004_244'); //245
        surcharge2: KnockoutObservable<string> = ko.observable('KMK004_216'); //217
        surcharge3: KnockoutObservable<string> = ko.observable('KMK004_218'); //219
        surchargeOvertime1: KnockoutObservable<string> = ko.observable('KMK004_250'); //251
        surchargeOvertime2: KnockoutObservable<string> = ko.observable('KMK004_216'); //217
        surchargeOvertime3: KnockoutObservable<string> = ko.observable('KMK004_218'); //219

        public create(params?: IDataModel) {
            const self = this;

            if (params) {
                self.update(params);
            }
        }

        public update(params?: IDataModel) {
            const self = this;

            if (params) {
                self.surcharge1(params.surcharge1);
                self.surcharge2(params.surcharge2);
                self.surcharge3(params.surcharge3);
                self.surchargeOvertime1(params.surchargeOvertime1);
                self.surchargeOvertime2(params.surchargeOvertime2);
                self.surchargeOvertime3(params.surchargeOvertime3);
            }
        }
    }

    interface ITabSetting {
        daily: number;
        weekly: number;
        deforWorkSurchargeWeekMonth: boolean;
        deforWorkLegalOverTimeWork: boolean;
        deforWorkLegalHoliday: boolean;
        outsideSurchargeWeekMonth: boolean
        outsidedeforWorkLegalOverTimeWork: boolean;
        outsidedeforWorkLegalHoliday: boolean;
    }

    class TabSetting {
        daily: KnockoutObservable<string> = ko.observable('');
        weekly: KnockoutObservable<string> = ko.observable('');
        deforWorkSurchargeWeekMonth: KnockoutObservable<boolean> = ko.observable(true);
        deforWorkLegalOverTimeWork: KnockoutObservable<boolean> = ko.observable(true);;
        deforWorkLegalHoliday: KnockoutObservable<boolean> = ko.observable(true);;
        outsideSurchargeWeekMonth: KnockoutObservable<boolean> = ko.observable(true);
        outsidedeforWorkLegalOverTimeWork: KnockoutObservable<boolean> = ko.observable(true);;
        outsidedeforWorkLegalHoliday: KnockoutObservable<boolean> = ko.observable(true);;

        public create(params?: ITabSetting) {
            const self = this;

            if (params) {
                var firstDaily = '';
                var lastDaily = params.daily % 60 + '';

                if (params.daily / 60 > 1) {
                    firstDaily = Math.floor(params.daily / 60) + '';
                }

                if (lastDaily.length < 2) {
                    lastDaily = '0' + lastDaily;
                }
                var firstWeekly = '';

                if (params.weekly / 60 > 1) {
                    firstWeekly = Math.floor(params.weekly / 60) + '';
                }

                var lastWeekly = params.weekly % 60 + '';
                if (lastWeekly.length < 2) {
                    lastWeekly = '0' + lastWeekly;
                }
                self.daily(firstDaily + ':' + lastDaily);
                self.weekly(firstWeekly + ':' + lastWeekly);
                self.deforWorkSurchargeWeekMonth(params.deforWorkSurchargeWeekMonth);
                self.deforWorkLegalOverTimeWork(params.deforWorkLegalOverTimeWork);
                self.deforWorkLegalHoliday(params.deforWorkLegalHoliday);
                self.outsideSurchargeWeekMonth(params.outsideSurchargeWeekMonth);
                self.outsidedeforWorkLegalOverTimeWork(params.outsidedeforWorkLegalOverTimeWork);
                self.outsidedeforWorkLegalHoliday(params.outsidedeforWorkLegalHoliday);
            }
        }
    }
}
