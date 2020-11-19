/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />


const template = `
<div style="display:flex"> 
	<div class="title" data-bind="i18n:header"></div>
	<div style="margin-top: 10px;"> 
		<a class="goback" style="width: 100px;" data-bind="ntsLinkButton: { jump: '../a/index.xhtml' },i18n: 'KMK004_224'"></a>
		<button data-bind="click: register,i18n: 'KMK004_225'" class="proceed"></button>
		<button data-bind="click: copy,visible:isShowCopyButton,i18n: 'KMK004_226'"></button>
		<button class="danger" data-bind="click: remove,i18n: 'KMK004_227'"></button>
	</div>
</div>
	`;

const COMPONENT_NAME = 'sidebar-button';

@component({
	name: COMPONENT_NAME,
	template
})

class SidebarButton extends ko.ViewModel {

	isShowCopyButton: boolean;
	header: string;

	created(params?: ISidebarButtonParam) {
		let vm = this;
		if (params.isShowCopyButton) {
			vm.isShowCopyButton = params.isShowCopyButton;
		} else {
			vm.isShowCopyButton = false;
		}

		vm.header = params.header;

	}

	register() {

	}

	copy() {

	}

	remove() { }
}
interface ISidebarButtonParam {
	isShowCopyButton: boolean;
	header: string;
}
