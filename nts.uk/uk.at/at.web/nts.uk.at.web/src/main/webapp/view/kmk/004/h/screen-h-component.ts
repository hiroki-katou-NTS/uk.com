/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />


const template = `
					<div class="sidebar-content-header">
								<div
									data-bind="component: {
								name: 'sidebar-button',
								params: {isShowCopyButton: true , header:header }
							}"></div>
					</div>
					<div style="padding: 10px;">
						<div data-bind="component: {
							name: 'basic-settings-company',
							params: {screenData:screenData}
							}">
						</div>
						<div data-bind="component: {
							name: 'monthly-working-hours',
							params: {
										screenData:screenData,
										isShowCheckbox:false
									}
							}">
						</div>
					</div>
	`;
const COMPONENT_NAME = 'screen-h-component';

const API_URL = {
	START_PAGE: 'screen/at/kmk004/h/start-page'
};

@component({
	name: COMPONENT_NAME,
	template
})

class ScreenHComponent extends ko.ViewModel {

	screenData: KnockoutObservable<FlexScreenData> = ko.observable(new FlexScreenData());

	header = '';

	created(params: any) {
		let vm = this;

		vm.header = params.header;
		/*	vm.$blockui('invisible')
				.then(() => vm.$ajax(API_URL.START_PAGE))
				.then((data: IScreenData) => {
					vm.screenData(new FlexScreenData(data));
				})
				.then(() => vm.$blockui('clear'));*/

	}

}

