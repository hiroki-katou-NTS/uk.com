/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.b {
	const template = `
	<div class="sidebar-content-header">
		<label class="title" data-bind="i18n: 'Com_Company'"></label>
		<a tabindex="1" class="goback" data-bind="ntsLinkButton: { jump: '/view/kmk/004/a/index.xhtml' },i18n: 'KMK004_224'"></a>
		<button tabindex="2" class="proceed" data-bind="i18n: 'KMK004_225', click: add, enable: existYear"></button>
		<button tabindex="3" class="danger" data-bind="i18n: 'KMK004_227', click: remote, enable: existYear"></button>
	</div>
	<div class="view-b">
		<div class="header-b">
			<div class="title" data-bind="i18n: 'KMK004_228'"></div>
			<hr></hr>
			<div class="header_title">
				<div data-bind="ntsFormLabel: {inline: true}, i18n: 'KMK004_229'"></div>
				<button tabindex="4" data-bind="i18n: 'KMK004_231', click: openDialogF"></button>
			</div>
			<div class="header_content">
				<div data-bind="component: {
					name: 'basic-setting',
					params:{
						modeCheckChangeSetting: modeCheckChangeSetting
					}
				}"></div>
			</div>
			<div data-bind="ntsFormLabel: {inline: true}, i18n: 'KMK004_232'"></div>
		</div>
		<div class="content">
			<div>
				<button tabindex="5" data-bind="i18n: 'KMK004_233'"></button>
			</div>
			<div class= "data">
				<div class= "box-year" data-bind="component: {
					name: 'box-year',
					params:{
						selectedYear: selectedYear,
						change: changeYear
					}
				}"></div>
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
	`;

	interface Params {

	}

	@component({
		name: 'view-b',
		template
    })
    
	export class ViewBComponent extends ko.ViewModel {
		
		public modeCheckChangeSetting: KnockoutObservable<string> = ko.observable('');
		public selectedYear: KnockoutObservable<number| null> = ko.observable(null);
		public changeYear: KnockoutObservable<boolean> = ko.observable(true);
		public checkEmployee: KnockoutObservable<boolean> = ko.observable(false);
		public existYear: KnockoutObservable<boolean> = ko.observable(false);

		created(params: Params) {
			const vm = this;

			vm.selectedYear
			.subscribe(() => {
				if(vm.selectedYear != null) {
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
			const vm = this;
			vm.modeCheckChangeSetting.valueHasMutated();

			$(document).ready(function () {
                $('.listbox').focus();
            });
		}

		remote() {
			$(document).ready(function () {
                $('.listbox').focus();
            });
		}

		openDialogF() {
			const vm = this;
			const params = { type: 'Com_Company'};
			vm.$window
				.storage('KMK004F', params)
				.then(() => vm.$window.modal('at', '/view/kmk/004/f/index.xhtml'));
		}
    }
}