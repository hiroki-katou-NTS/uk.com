/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004 {
    
    /*interface Params {
        model: SettingParam;
    }*/

    const template = `
        <div class="table-view">
            <table>
                <tbody>
                    <tr>
                        <th>
                            <div class="content1" data-bind="i18n: 'KMK004_213'"></div>
                        </th>
                        <th>
                            <div class="content1" data-bind="i18n: 'KMK004_214'"></div>
                        </th>
						<th>
                            <div class="content1" data-bind="i18n: 'KMK004_293'"></div>
                        </th>
						<th>
                            <div class="content1" data-bind="i18n: 'KMK004_294'"></div>
                        </th>
						<th>
                            <div class="content1" data-bind="i18n: 'KMK004_295'"></div>
                        </th>
                    </tr>
                    <tr>
                        <td>
                            <div class="content1" data-bind="text: settingParam.daily"></div>
                        </td>
                        <td>
                            <div class="content1" data-bind="text: settingParam.weekly"></div>
                        </td>
						<td>
                            <div class="content1" data-bind="text: settingParam.period"></div>
                        </td>
						<td>
                            <div class="content1" data-bind="text: settingParam.startMonth"></div>
                        </td>
						<td>
                            <div class="content1" data-bind="i18n: settingParam.iteration"></div>
                        </td>
                    </tr>
                    <tr>
                        <th colspan="2">
                            <div data-bind="i18n: 'KMK004_215'"></div>
                        </th>
 						<th colspan="3">
                            <div data-bind="i18n: 'KMK004_220'"></div>
                        </th>
                    </tr>
					
                    <tr>
                        <td>
                            <div class="content1" data-bind="i18n: model.surcharge1"></div>
                        </td>
                        <td>
                            <div class="content1" data-bind="i18n: model.surcharge2"></div>
                        </td>
                        <td>
                            <div class="content1" data-bind="i18n: model.surchargeOvertime1"></div>
                        </td>
                        <td colspan="2">
                            <div class="content1" data-bind="i18n: model.surchargeOvertime2"></div>
                        </td>
                    </tr> 
                </tbody>
            </table>
        </div>
        <style type="text/css" rel="stylesheet">
            .table-view {
                padding: 15px;
                border: 1px solid #AAAAAA;
                border-radius: 15px;
				width: fit-content;
				margin: 15px;
            }

            .table-view table {
                border-collapse: collapse;
            }
            
            .table-view th {
                background: #E0F59E;
            }

            .table-view tr {
                border: 1px solid #AAAAAA;
            }

            .table-view tr, .table-view td {
                border: 1px solid #AAAAAA;
                padding: 3px;
              }

			.table-view th {
                border: 1px solid #AAAAAA;
            }
            
        </style>
        <style type="text/css" rel="stylesheet" data-bind="html: $component.style"></style>
    `;

    @component({
        name: 'view-l-basic-setting',
        template
    })

    class BasicSetting extends ko.ViewModel {
        public model = new DataModel();
        public settingParam = new SettingParam();
        public params!: Params;

        created(params: Params) {
            const vm = this;

            vm.params = params;
            /*vm.settingParam = params.model;*/
        }

        mounted() {
            const vm = this;

            if (!ko.unwrap(vm.settingParam.deforWorkLegalOverTimeWork)) {
                vm.model.surcharge1('KMK004_217');
            }
            if (!ko.unwrap(vm.settingParam.deforWorkLegalHoliday)) {
                vm.model.surcharge2('KMK004_219');
            }
          
            if (!ko.unwrap(vm.settingParam.outsidedeforWorkLegalOverTimeWork)) {
                vm.model.surchargeOvertime1('KMK004_217');
            }
            if (!ko.unwrap(vm.settingParam.outsidedeforWorkLegalHoliday)) {
                vm.model.surchargeOvertime2('KMK004_219');
            }

        }
    }

    interface IDataModel {
        surcharge1: string;
        surcharge2: string;
        surchargeOvertime1: string;
        surchargeOvertime2: string;
    }

    class DataModel {
        surcharge1: KnockoutObservable<string> = ko.observable('KMK004_216'); //217
        surcharge2: KnockoutObservable<string> = ko.observable('KMK004_218'); //219
        surchargeOvertime1: KnockoutObservable<string> = ko.observable('KMK004_216'); //217
        surchargeOvertime2: KnockoutObservable<string> = ko.observable('KMK004_218'); //219

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
                self.surchargeOvertime1(params.surchargeOvertime1);
                self.surchargeOvertime2(params.surchargeOvertime2);
            }
        }
    }

    export interface ISetting{
		daily: string;
        weekly: string;
        period: string;
        startMonth: string;
        iteration: string;
       	deforWorkSurchargeWeekMonth: boolean;
        deforWorkLegalOverTimeWork: boolean;
        deforWorkLegalHoliday: boolean;
        outsideSurchargeWeekMonth: boolean
        outsidedeforWorkLegalOverTimeWork: boolean;
        outsidedeforWorkLegalHoliday: boolean;
    }

    export class SettingParam {
        daily: KnockoutObservable<string> = ko.observable('8:00');
        weekly: KnockoutObservable<string> = ko.observable('40:00');
        deforWorkSurchargeWeekMonth: KnockoutObservable<boolean> = ko.observable(true);
        deforWorkLegalOverTimeWork: KnockoutObservable<boolean> = ko.observable(true);
        deforWorkLegalHoliday: KnockoutObservable<boolean> = ko.observable(true);
        outsideSurchargeWeekMonth: KnockoutObservable<boolean> = ko.observable(true);
        outsidedeforWorkLegalOverTimeWork: KnockoutObservable<boolean> = ko.observable(true);
        outsidedeforWorkLegalHoliday: KnockoutObservable<boolean> = ko.observable(true);
 		period: KnockoutObservable<string> = ko.observable('2ヶ月');
 		startMonth: KnockoutObservable<string> = ko.observable('4月');
 		iteration: KnockoutObservable<string> = ko.observable('KMK004_296');

        public create(params?: ISetting) {
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
				self.period(params.startMonth);
                self.startMonth(params.weekly);
				self.iteration(params.iteration);
            }
        }
    }
}
