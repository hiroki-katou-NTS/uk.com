/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.kmk004_new.components {
    
    interface Params {
        model: ParamsTabSetting;
    }

    const template = `
        <div class="table-view">
            <table>
                <tbody>
                    <tr>
                        <th>
                            <div class="content" data-bind="i18n: 'KMK004_213'"></div>
                        </th>
                        <th>
                            <div class="content" data-bind="i18n: 'KMK004_214'"></div>
                        </th>
                    </tr>
                    <tr>
                        <td>
                            <div class="content" data-bind="i18n: paramsTabSetting.daily"></div>
                        </td>
                        <td>
                            <div class="content" data-bind="i18n: paramsTabSetting.weekly"></div>
                        </td>
                    </tr>
                    <tr>
                        <th colspan="3">
                            <div data-bind="i18n: 'KMK004_215'"></div>
                        </th>
                    </tr>
                    <tr>
                        <td>
                            <div class="content" data-bind="i18n: model.surcharge1"></div>
                        </td>
                        <td>
                            <div class="content" data-bind="i18n: model.surcharge2"></div>
                        </td>
                        <td>
                            <div class="content" data-bind="i18n: model.surcharge3"></div>
                        </td>
                    </tr>
                    <tr>
                        <th colspan="3">
                            <div data-bind="i18n: 'KMK004_220'"></div>
                        </th>
                    </tr>
                    <tr>
                        <td>
                            <div class="content" data-bind="i18n: model.surchargeOvertime1"></div>
                        </td>
                        <td>
                            <div class="content" data-bind="i18n: model.surchargeOvertime2"></div>
                        </td>
                        <td>
                            <div class="content" data-bind="i18n: model.surchargeOvertime3"></div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <style type="text/css" rel="stylesheet">
            .table-view{
                padding: 20px;
                border: 1px solid #AAAAAA;
                border-radius: 15px;
            }

            table {
                border-collapse: collapse;
            }
            
            th {
                background: #97D155;
            }

            tr, th {
                border: 1px solid #AAAAAA;
            }

            tr, td {
                border: 1px solid #AAAAAA;
                padding: 3px;
              }
            .content {
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
        public paramsTabSetting = new ParamsTabSetting();
        public params!: Params;

        created(params: Params) {
            const vm = this;

            vm.params = params;
            vm.paramsTabSetting = params.model;
        }

        mounted() {
            const vm = this;

            if (!ko.unwrap(vm.paramsTabSetting.deforWorkSurchargeWeekMonth)) {
                vm.model.surcharge1('KMK004_245');
            }
            if (!ko.unwrap(vm.paramsTabSetting.deforWorkLegalOverTimeWork)) {
                vm.model.surcharge2('KMK004_217');
            }
            if (!ko.unwrap(vm.paramsTabSetting.deforWorkLegalHoliday)) {
                vm.model.surcharge3('KMK004_219');
            }
            if (!ko.unwrap(vm.paramsTabSetting.outsideSurchargeWeekMonth)) {
                vm.model.surchargeOvertime1('KMK004_251');
            }
            if (!ko.unwrap(vm.paramsTabSetting.outsidedeforWorkLegalOverTimeWork)) {
                vm.model.surchargeOvertime2('KMK004_217');
            }
            if (!ko.unwrap(vm.paramsTabSetting.outsidedeforWorkLegalHoliday)) {
                vm.model.surchargeOvertime3('KMK004_219');
            }

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

    export interface IParamsTabSetting {
        daily: number;
        weekly: number;
        deforWorkSurchargeWeekMonth: boolean;
        deforWorkLegalOverTimeWork: boolean;
        deforWorkLegalHoliday: boolean;
        outsideSurchargeWeekMonth: boolean
        outsidedeforWorkLegalOverTimeWork: boolean;
        outsidedeforWorkLegalHoliday: boolean;
    }

    export class ParamsTabSetting {
        daily: KnockoutObservable<number> = ko.observable(8);
        weekly: KnockoutObservable<number> = ko.observable(40);
        deforWorkSurchargeWeekMonth: KnockoutObservable<boolean> = ko.observable(true);
        deforWorkLegalOverTimeWork: KnockoutObservable<boolean> = ko.observable(true);;
        deforWorkLegalHoliday: KnockoutObservable<boolean> = ko.observable(true);;
        outsideSurchargeWeekMonth: KnockoutObservable<boolean> = ko.observable(true);
        outsidedeforWorkLegalOverTimeWork: KnockoutObservable<boolean> = ko.observable(true);;
        outsidedeforWorkLegalHoliday: KnockoutObservable<boolean> = ko.observable(true);;

        public create(params?: IParamsTabSetting) {
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
}
