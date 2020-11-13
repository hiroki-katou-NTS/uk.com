/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.b {
	const template = `
		<div class="sidebar-content-header">
			<div class="title" data-bind="i18n: 'Com_Workplace'"></div>
			<a class="goback" data-bind="ntsLinkButton: { jump: '/view/kmk/004/a/index.xhtml' },i18n: 'KMK004_224'"></a>
			<button class="proceed" data-bind="i18n: 'KMK004_225'"></button>
			<button data-bind="i18n: 'KMK004_226'"></button>
			<button class="danger" data-bind="i18n: 'KMK004_227'"></button>
		</div>
		<div class="view-c-kmk004">
			<div class="left-content">
				<div data-bind="component: {
					name: 'kcp004',
					params:{
					}
				}"></div>
			</div>
			<div class="right-content">
				<div>
					<p data-bind="i18n: 'KMK004_228'"></p>
					<hr></hr>
					<div data-bind="i18n: ''"></div>
					<div>
						<div data-bind="ntsFormLabel: {}, i18n: 'KMK004_229'"></div>
						<!-- ko if: modeCheckSetting -->
							<button data-bind="i18n: 'KMK004_239'"></button>
						<!-- /ko -->
						<!-- ko if: !modeCheckSetting -->
							<button data-bind="i18n: 'KMK004_238'"></button>
						<!-- /ko -->
					</div>
				</div>
			</div>
		<div>
	`;

	interface Params {

	}

	@component({
		name: 'view-c',
		template
    })
    
	export class ViewCComponent extends ko.ViewModel {

		public modeCheckSetting: KnockoutObservable<boolean> = ko.observable(true);
		
		created(params: Params) {

		}

		mounted() {
			
		}
    }
}