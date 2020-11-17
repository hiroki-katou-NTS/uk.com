/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.b {
	const template = `
	<div class="sidebar-content-header">
	<div class="title" data-bind="i18n: 'Com_Person'"></div>
	<a class="goback" data-bind="ntsLinkButton: { jump: '/view/kmk/004/a/index.xhtml' },i18n: 'KMK004_224'"></a>
	<button class="proceed" data-bind="i18n: 'KMK004_225'"></button>
	<button data-bind="i18n: 'KMK004_226'"></button>
	<button class="danger" data-bind="i18n: 'KMK004_227'"></button>
	</div>
	<div class="view-e-kmk004">
		<div class="left-content">
			<div data-bind="component: {
				name: 'ccg001',
				params:{
					employees: employees
				}
			}"></div>
			<div class="cpn-kcp005" data-bind="component: {
				name: 'kcp005',
				params:{
					selectedCode: selectedCode,
					employees: employees
				}
			}"></div>
		</div>
		<div class="right-content">
			<div>
				<p data-bind="i18n: 'KMK004_228'"></p>
				<hr></hr>
				<div data-bind="i18n: 'Chung dep trai'"></div>
				<div>
					<div data-bind="ntsFormLabel: {}, i18n: 'KMK004_229'"></div>
					<!-- ko if: modeCheckSetting -->
						<button data-bind="i18n: 'KMK004_241'"></button>
					<!-- /ko -->
					<!-- ko if: !modeCheckSetting -->
						<button data-bind="i18n: 'KMK004_240'"></button>
					<!-- /ko -->
				</div>
				<!-- ko if: modeCheckSetting -->
					<div class ="setting" data-bind="component: {
						name: 'basic-setting',
						params:{
							modeCheckChangeSetting: modeCheckChangeSetting
						}
					}"></div>
				<!-- /ko -->
				<div class="label1" data-bind="ntsFormLabel: {}, i18n: 'KMK004_232'"></div>
				<div class="content-data">
					<div class="year">
						<div>
							<button data-bind="i18n: 'KMK004_233'"></button>
						</div>
						<div class= "box-year" data-bind="component: {
							name: 'box-year',
							params:{
							}
						}"></div>
					</div>
					<div class= "time-work" data-bind="component: {
						name: 'time-work',
						params:{
						}
					}"></div>
				</div>
			</div>
		</div>
	<div>
	`;

	interface Params {

	}

	@component({
		name: 'view-e',
		template
    })
    
	export class ViewEComponent extends ko.ViewModel {
		
		public modeCheckSetting: KnockoutObservable<boolean> = ko.observable(true);
		public modeCheckChangeSetting: KnockoutObservable<string> = ko.observable('');
		public employees: KnockoutObservableArray<IEmployee> = ko.observableArray([]);
		public selectedCode: KnockoutObservable<string> = ko.observable('');

		created(params: Params) {
			const vm = this;


		}

		mounted() {
			
		}

    }
}