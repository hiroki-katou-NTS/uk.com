/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />


const template = `
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
								overflow: hidden scroll;">
						<div id="right-layout" style="margin: 10px 20px;">
							<label id="flex-title" data-bind="i18n:'KMK004_268'"></label>
						<hr style="width: 518px;
						    text-align: left;
						    margin-left: 0;" />
							<div data-bind="component: {
								name: 'basic-settings-company',
								params: {
												screenData:screenData,
												screenMode:screenMode
										}
								}">
							</div>
							<div style=" display: inline-block;" data-bind="component: {
								name: 'monthly-working-hours',
								params: {
											screenData:screenData,
											screenMode:screenMode
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
	DELETE: BASE_G_URL + 'delete',
	CHANGE_SETTING: BASE_G_URL + 'change-setting',
};

@component({
	name: 'screen-g-component',
	template
})

class ScreenGComponent extends ko.ViewModel {

	screenData: KnockoutObservable<FlexScreenData> = ko.observable(new FlexScreenData());

	screenMode = '';

	created(params: any) {
		let vm = this;

		vm.screenMode = params.screenMode;
		vm.$blockui('invisible')
			.then(() => vm.$ajax(API_G_URL.START_PAGE))
			.then((data) => {
				vm.screenData().updateData(data);
			})
			.then(() => vm.$blockui('clear'));

		vm.screenData().selectedYear.subscribe((yearInput) => {
			
			let year = Number(yearInput);
			//check if data has in unsave list => bind data from that
			let unsaveItem = _.find(vm.screenData().unSaveSetComs, ['year', year]);
			
			if (unsaveItem) {
				let isChanged = vm.screenData().saveToUnSaveList();
				if(isChanged){vm.screenData().setUpdateYear(vm.screenData().serverData.year);}
				vm.screenData().serverData = unsaveItem;
				vm.screenData().monthlyWorkTimeSetComs(_.map(unsaveItem.data, (item: nts.uk.at.kmk004.components.flex.IMonthlyWorkTimeSetCom) => { return new nts.uk.at.kmk004.components.flex.MonthlyWorkTimeSetCom(item); }));
			} else {
				let isChanged = vm.screenData().saveToUnSaveList();
				if(isChanged){vm.screenData().setUpdateYear(vm.screenData().serverData.year);}
				//else load data from sever
				vm.$blockui('invisible');
				vm.$ajax(API_G_URL.CHANGE_YEAR + year).done((timeSetComs: Array<nts.uk.at.kmk004.components.flex.IMonthlyWorkTimeSetCom>) => {
					vm.$blockui('clear');
					vm.screenData().serverData = { year: year, data: timeSetComs };
					vm.screenData().monthlyWorkTimeSetComs(_.map(timeSetComs, (item: nts.uk.at.kmk004.components.flex.IMonthlyWorkTimeSetCom) => { return new nts.uk.at.kmk004.components.flex.MonthlyWorkTimeSetCom(item); }));
				});

			}
		});
	}

	

	mounted() {
		$("#year-list").focus();
	}

}



