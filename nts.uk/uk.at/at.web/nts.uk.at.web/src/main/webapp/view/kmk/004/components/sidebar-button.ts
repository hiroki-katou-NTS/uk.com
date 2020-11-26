/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />


const template = `
<div style="display:flex"> 
	<div class="title" data-bind="i18n:screenMode"></div>
	<div style="margin-top: 10px;"> 
		<a class="goback" style="width: 100px;" data-bind="ntsLinkButton: { jump: '../a/index.xhtml' },i18n: 'KMK004_224'"></a>
		<button data-bind=" enable:screenData().yearList().length > 0,click: register,i18n: 'KMK004_225'" class="proceed"></button>
		<button data-bind="enable:screenData().yearList().length > 0 ,click: openRDialog,visible:screenMode != 'Com_Company' ,i18n: 'KMK004_226'"></button>
		<button class="danger" data-bind="enable:screenData().yearList().length > 0,click: remove,i18n: 'KMK004_227'"></button>
	</div>
</div>
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
		vm.$window.modal('/view/kmk/004/r/index.xhtml',{
			screenMode: vm.screenData,
			data: [],
			selectedCode: null,
			alreadySettingList: []
		}).then(() => {

		});
	}

	remove() { }
}
interface ISidebarButtonParam {
	screenData: KnockoutObservable<FlexScreenData>;
	screenMode: string;
}
