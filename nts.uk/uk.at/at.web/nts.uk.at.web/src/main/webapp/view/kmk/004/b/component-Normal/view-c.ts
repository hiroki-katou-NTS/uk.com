/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.b {
	const template = `
		<div class="sidebar-content-header">
			<div class="title" data-bind="i18n: 'Com_Workplace'"></div>
			<a tabindex="1" class="goback" data-bind="ntsLinkButton: { jump: '/view/kmk/004/a/index.xhtml' },i18n: 'KMK004_224'"></a>
			<button tabindex="2" class="proceed" data-bind="i18n: 'KMK004_225', enable: existYear, click: add"></button>
			<button tabindex="3" data-bind="i18n: 'KMK004_226', enable: existYear, click: copy"></button>
			<button tabindex="4" class="danger" data-bind="i18n: 'KMK004_227', enable: existYear, click: remote"></button>
		</div>
		<div class="view-c-kmk004">
			<div class="left-content">
				<div data-bind="component: {
					name: 'kcp004',
					params:{
						selectedId: selectedId,
						model: model
					}
				}"></div>
			</div>
			<div class="right-content">
				<div>
					<p class="title" data-bind="i18n: 'KMK004_228'"></p>
					<hr></hr>
					<div class="name" data-bind="i18n: model.name"></div>
					<div>
						<div data-bind="ntsFormLabel: {inline: true}, i18n: 'KMK004_229'"></div>
						<!-- ko if: model.isAlreadySetting -->
							<button tabindex="5" data-bind="i18n: 'KMK004_239'"></button>
						<!-- /ko -->
						<!-- ko ifnot: model.isAlreadySetting -->
							<button tabindex="5" data-bind="i18n: 'KMK004_238'"></button>
						<!-- /ko -->
					</div>
					<!-- ko if: model.isAlreadySetting -->
						<div class ="setting" data-bind="component: {
							name: 'basic-setting',
							params:{
								type: type,
								selectId: selectedId
							}
						}"></div>
					<!-- /ko -->
					<div class="label1" data-bind="ntsFormLabel: {inline: true}, i18n: 'KMK004_232'"></div>
					<div class="content-data">
						<div>
							<button tabindex="6" data-bind="i18n: 'KMK004_233', click: openDialogS"></button>
						</div>
						<div class="year">
							<div class= "box-year" data-bind="component: {
								name: 'box-year',
								params:{
									selectedYear: selectedYear,
									type: type,
									years: years,
									selectId: selectedId
								}
							}"></div>
						</div>
						<div tabindex="7" class= "time-work" data-bind="component: {
							name: 'time-work',
							params:{
								selectedYear: selectedYear,
								checkEmployee: ko.observable(false),
								type: type,
								years: years,
								selectId: selectedId
							}
						}"></div>
					</div>
				</div>
			</div>
			<div class = "cf"></div>
		<div>
	`;

	interface Params {

	}

	const API = {

	};

	@component({
		name: 'view-c',
		template
	})

	export class ViewCComponent extends ko.ViewModel {

		public years: KnockoutObservableArray<IYear> = ko.observableArray([]);
		public existYear: KnockoutObservable<boolean> = ko.observable(false);
		public selectedYear: KnockoutObservable<number | null> = ko.observable(null);
		public type: SIDEBAR_TYPE = 'Com_Workplace';
		public selectedId: KnockoutObservable<string> = ko.observable('');
		public model: Model = new Model();

		created(params: Params) {
			const vm = this;

		}

		mounted() {
			const vm = this;

			vm.selectedId
				.subscribe(() => {
					vm.selectedYear.valueHasMutated();
				})

			$(document).ready(function () {
				$('.listbox').focus();
			});

			vm.years
				.subscribe(() => {
					vm.$errors('clear');
					if (ko.unwrap(vm.years).length > 0) {
						vm.existYear(true);
					}else{
						vm.existYear(false);
					}
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

		openDialogS() {
			const vm = this;
			const param = { years: ko.unwrap(vm.years).map((m: IYear) => m.year) };
			vm.$window.modal('/view/kmk/004/q/index.xhtml', param).then((result) => {
				if (result) {
					vm.years.push(new IYear(parseInt(result.year), true));
					vm.years(_.orderBy(ko.unwrap(vm.years), ['year'], ['desc']));
					vm.selectedYear(ko.unwrap(vm.years)[0].year);
				}
			});
		}
	}
}