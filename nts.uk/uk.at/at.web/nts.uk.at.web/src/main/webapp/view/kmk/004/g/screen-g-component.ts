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
					<div id="right-layout" style="margin: 10px 20px;">
						<label id="flex-title" data-bind="i18n:'KMK004_268'"></label>
						<hr/>
						<div data-bind="component: {
							name: 'basic-settings-company',
							params: {
											screenData:screenData,
											screenMode:screenMode
									}
							}">
						</div>
						<div data-bind="component: {
							name: 'monthly-working-hours',
							params: {
										screenData:screenData,
										screenMode:screenMode
									}
							}">
						</div>
					</div>
	`;
const COMPONENT_NAME = 'screen-g-component';

const API_URL = {
	START_PAGE: 'screen/at/kmk004/h/start-page'
};

@component({
	name: COMPONENT_NAME,
	template
})

class ScreenGComponent extends ko.ViewModel {

	screenData: KnockoutObservable<FlexScreenData> = ko.observable(new FlexScreenData());

	screenMode = '';

	created(params: any) {
		let vm = this;

		vm.screenMode = params.screenMode;
		/*	vm.$blockui('invisible')
				.then(() => vm.$ajax(API_URL.START_PAGE))
				.then((data: IScreenData) => {
					vm.screenData(new FlexScreenData(data));
				})
				.then(() => vm.$blockui('clear'));*/

	}

	mounted() {
		$("#year-list").focus();
	}

}

