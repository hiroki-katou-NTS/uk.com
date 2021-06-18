/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

import IMonthlyWorkTimeSetCom = nts.uk.at.kmk004.components.flex.IMonthlyWorkTimeSetCom;
import MonthlyWorkTimeSetCom = nts.uk.at.kmk004.components.flex.MonthlyWorkTimeSetCom;
import IMonthlyLaborTime = nts.uk.at.kmk004.components.flex.IMonthlyLaborTime;
const g_template = `
					<div class="sidebar-content-header">
					<!-- ko component: {
										    name: "sidebar-button",
										    params: {
													screenData:screenData ,
													screenMode:screenMode 
											}
										} -->
					<!-- /ko -->
								
					</div>
					<div style="height: calc(100vh - 143px);
								overflow-y: scroll;">
						<div id="right-layout" style="margin: 10px 20px;">
							<label id="flex-title" data-bind="i18n:'KMK004_268'"></label>
						<hr style=" text-align: left;
						    margin-left: 0;" />
							<div data-bind="component: {
								name: 'basic-settings-company',
								params: {
												screenData:screenData,
												screenMode:screenMode
										}
								}">
							</div>
							<div style=" margin-top:15px; display: inline-block;" data-bind="component: {
								name: 'monthly-working-hours',
								params: {
											screenData:screenData,
											screenMode:screenMode,
											startYM:startYM
										}
								}">
							</div>
						</div>
					</div>
	`;

const BASE_G_URL = 'screen/at/kmk004/g/';

const API_G_URL = {
	START_PAGE: BASE_G_URL + 'init-screen',
	CHANGE_YEAR: BASE_G_URL + 'change-year/',
	UPDATE: BASE_G_URL + 'update',
	REGISTER: BASE_G_URL + 'register',
	DELETE: BASE_G_URL + 'delete/',
	CHANGE_SETTING: BASE_G_URL + 'change-setting',
};

@component({
	name: 'screen-g-component',
	template: g_template
})

class ScreenGComponent extends ko.ViewModel {

	screenData: KnockoutObservable<FlexScreenData> = ko.observable(new FlexScreenData());

	screenMode = '';

	startYM: KnockoutObservable<number> = ko.observable();

	created(params: any) {
		let vm = this;

		vm.screenMode = params.screenMode;
		vm.startYM = params.startYM;
		vm.$blockui('invisible')
			.then(() => vm.$ajax(API_G_URL.START_PAGE))
			.done((startData) => {
				vm.$blockui('invisible');
				vm.$ajax(API_G_URL.CHANGE_YEAR + 2020).done((data: Array<IMonthlyWorkTimeSetCom>) => {
					vm.startYM(data[0].yearMonth);
					vm.screenData().updateData(startData);
					vm.screenData().initDumpData(data[0].yearMonth);
					vm.screenData().setFocus('load');
					vm.screenData().flexMonthActCalSet(startData.flexBasicSetting.comFlexMonthActCalSet);
				}).always(() => { vm.$blockui('clear'); });
			})
			.always(() => vm.$blockui('clear'));

		vm.screenData().selectedYear.subscribe((yearInput) => {

			if (!yearInput) {
				_.forEach(vm.screenData().monthlyWorkTimeSetComs(), (item) => {
					item.laborTime().checkbox(false);
					item.laborTime().legalLaborTime(null);
					item.laborTime().weekAvgTime(null);
					item.laborTime().withinLaborTime(null);
				});

				vm.screenData().serverData = null;
				vm.screenData().unSaveSetComs = [];
				return;
			}

			let year = Number(yearInput);
			//check if data has in unsave list => bind data from that
			let unsaveItem = _.find(vm.screenData().unSaveSetComs, ['year', year]);

			if (unsaveItem) {
				let isChanged = vm.screenData().saveToUnSaveList();
				if (isChanged) { vm.screenData().setUpdateYear(vm.screenData().serverData.year); }
				vm.screenData().serverData = unsaveItem;
				vm.screenData().monthlyWorkTimeSetComs(_.map(unsaveItem.data, (item: IMonthlyWorkTimeSetCom) => { return new MonthlyWorkTimeSetCom(vm.screenData(), item); }));
			} else {
				let isChanged = vm.screenData().saveToUnSaveList();
				if (isChanged) { vm.screenData().setUpdateYear(vm.screenData().serverData.year); }

				//else load data from sever
				vm.$blockui('invisible');
				vm.$ajax(API_G_URL.CHANGE_YEAR + year).done((data: Array<IMonthlyWorkTimeSetCom>) => {
					vm.startYM(data[0].yearMonth);
					vm.screenData().setYM(year, data);
				}).always(() => { vm.$blockui('clear'); });

			}
		});
	}

	mounted() {
        const vm = this;
            
	}

}



