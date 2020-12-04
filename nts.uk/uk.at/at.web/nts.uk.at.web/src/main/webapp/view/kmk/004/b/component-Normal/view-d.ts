/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.b {
	const template = `
	<div id="functions-area">
		<div class="sidebar-content-header">
			<div class="title" data-bind="i18n: 'Com_Employment'"></div>
			<a tabindex="1" class="goback" data-bind="ntsLinkButton: { jump: '/view/kmk/004/a/index.xhtml' },i18n: 'KMK004_224'"></a>
			<button tabindex="2" class="proceed" data-bind="i18n: 'KMK004_225', click: add, enable: existYear"></button>
			<button tabindex="3" data-bind="i18n: 'KMK004_226', click: copy, enable: existYear"></button>
			<button tabindex="4" class="danger" data-bind="i18n: 'KMK004_227', click: remote, enable: existYear"></button>
		</div>
	</div>
	<div class="view-d-kmk004">
		<div class="left-content">
			<div data-bind="component: {
				name: 'kcp001',
				params:{
					emloyment: emloyment,
					alreadySettings: alreadySettings
				}
			}"></div>
		</div>
		<div class="right-content">
			<div>
				<p class="title" data-bind="i18n: 'KMK004_228'"></p>
				<hr></hr>
				<div class="name" data-bind="i18n: emloyment.name"></div>
				<div>
					<div data-bind="ntsFormLabel: {inline: true}, i18n: 'KMK004_229'"></div>
					<!-- ko if: modeCheckSetting -->
						<button tabindex="5" data-bind="i18n: 'KMK004_241'"></button>
					<!-- /ko -->
					<!-- ko if: !modeCheckSetting -->
						<button tabindex="5" data-bind="i18n: 'KMK004_240'"></button>
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
				<div class="label1" data-bind="ntsFormLabel: {inline: true}, i18n: 'KMK004_232'"></div>
				<div class="content-data">
					<div>
						<button tabindex="6" data-bind="i18n: 'KMK004_233'"></button>
					</div>
					<div class="year">
						<div class= "box-year" data-bind="component: {
							name: 'box-year',
							params:{
								selectedYear: selectedYear,
								change: changeYear
							}
						}"></div>
					</div>
					<div tabindex="7" class= "time-work" data-bind="component: {
						name: 'time-work',
						params:{
							selectedYear: selectedYear,
							change: changeYear,
							checkEmployee: checkEmployee
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
		name: 'view-d',
		template
	})

	export class ViewDComponent extends ko.ViewModel {

		public modeCheckSetting: KnockoutObservable<boolean> = ko.observable(true);
		public modeCheckChangeSetting: KnockoutObservable<string> = ko.observable('');
		public selectedYear: KnockoutObservable<number | null> = ko.observable(null);
		public changeYear: KnockoutObservable<boolean> = ko.observable(true);
		public checkEmployee: KnockoutObservable<boolean> = ko.observable(false);
		public existYear: KnockoutObservable<boolean> = ko.observable(false);
		public emloyment: Employment = new Employment();
		public alreadySettings: KnockoutObservableArray<AlreadySettingEmployment> = ko.observableArray([]);

		created(params: Params) {
			const vm = this;
			vm.selectedYear
				.subscribe(() => {
					if (vm.selectedYear != null) {
						vm.existYear(true);
					}
				});
		}

		mounted() {
			$(document).ready(function () {
				$('.listbox').focus();
			});
		}

		add() {
			$(document).ready(function () {
				$('.listbox').focus();
			});
		}

		copy() {
			$(document).ready(function () {
				$('.listbox').focus();
			});
		}

		remote() {
			$(document).ready(function () {
				$('.listbox').focus();
			});
		}
	}
}