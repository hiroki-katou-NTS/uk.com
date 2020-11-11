/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.b {
	const template = `
	<div class="sidebar-content-header">
		<div class="title" data-bind="i18n: 'Com_Company'"></div>
		<a class="goback" data-bind="ntsLinkButton: { jump: '/view/kmk/004/a/index.xhtml' },i18n: 'KMK004_224'"></a>
		<button class="proceed" data-bind="i18n: 'KMK004_225', click: add"></button>
		<button class="danger" data-bind="i18n: 'KMK004_227'"></button>
	</div>
	<div class="view-b">
		<div class="header-b">
			<div data-bind="i18n: 'KMK004_228'"></div>
			<hr></hr>
			<div class="header_title">
				<div data-bind="ntsFormLabel: {}, i18n: 'KMK004_229'"></div>
				<button data-bind="i18n: 'KMK004_231'"></button>
			</div>
			<div class="header_content">
				<div data-bind="component: {
					name: 'basic-setting',
					params:{
						modeCheckChangeSetting: modeCheckChangeSetting
					}
				}"></div>
			</div>
			<div data-bind="ntsFormLabel: {}, i18n: 'KMK004_232'"></div>
		</div>
		<div class="content">
			<button data-bind="i18n: 'KMK004_233'"></button>
			<div>
				
			</div>
		</div>
	</div>
	`;

	interface Params {

	}

	@component({
		name: 'view-b',
		template
    })
    
	export class ViewBComponent extends ko.ViewModel {
		
		public modeCheckChangeSetting: KnockoutObservable<string> = ko.observable('');

		created(params: Params) {

		}

		mounted() {
			
		}

		add() {
			const vm = this;
			vm.modeCheckChangeSetting.valueHasMutated();
		}
    }
}