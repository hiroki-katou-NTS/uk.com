/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />


const template = `
		<div class="title" data-bind="i18n:screenMode"></div>
		<a class="goback"  data-bind="ntsLinkButton: { jump: '../a/index.xhtml' },i18n: 'KMK004_224'"></a>
		<button data-bind=" enable:screenData().yearList().length > 0,click: register,i18n: 'KMK004_225'" class="proceed"></button>
		<button data-bind="enable:screenData().yearList().length > 0 ,click: openRDialog,visible:screenMode != 'Com_Company' ,i18n: 'KMK004_226'"></button>
		<button class="danger" data-bind="enable:screenData().yearList().length > 0,click: remove,i18n: 'KMK004_227'"></button>
	`;

const COMPONENT_NAME = 'sidebar-button';

@component({
	name: COMPONENT_NAME,
	template
})

class SidebarButton extends ko.ViewModel {
	screenMode: string;
	screenData: KnockoutObservable<FlexScreenData> = ko.observable(new FlexScreenData());

	created(params?: ISidebarButtonParam) {
		let vm = this;
		vm.screenData = params.screenData;
		vm.screenMode = params.screenMode;
	}

	register() {

	}

	openRDialog() {
		const vm = this;

		let datas = [];

		if (vm.screenMode == 'Com_Workplace') {
			datas = $('#work-place-list').getDataList();
		}
		if (vm.screenMode == 'Com_Employment') {
			datas = $('#empt-list-setting').getDataList();
		}
		if (vm.screenMode == 'Com_Person') {
			datas = $('#employee-list').getDataList();
		}

		vm.$window.modal('/view/kmk/004/r/index.xhtml', {
			screenMode: vm.screenMode,
			data: datas,
			selectedCode: vm.screenData().selected(),
			alreadySettingList: vm.screenData().alreadySettingList()
		}).then(() => {

		});
	}

	remove() { }
}
interface ISidebarButtonParam {
	screenData: KnockoutObservable<FlexScreenData>;
	screenMode: string;
}
