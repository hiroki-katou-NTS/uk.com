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

const BASE_URL = 'screen/at/kmk004/g/';

const API_G_URL = {
	START_PAGE: BASE_URL + 'init-screen',
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
				console.log(data);
				//vm.screenData(new FlexScreenData(data));
			})
			.then(() => vm.$blockui('clear'));

	}

	mounted() {
		$("#year-list").focus();
	}

}

