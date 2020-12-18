/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.b {
	const template = `
	<div class="sidebar-content-header">
		<div class="title" data-bind="i18n: 'Com_Person'"></div>
		<a tabindex="1" class="goback" data-bind="ntsLinkButton: { jump: '/view/kmk/004/a/index.xhtml' },i18n: 'KMK004_224'"></a>
		<button tabindex="2" class="proceed" data-bind="i18n: 'KMK004_225', click: add, enable: existYear"></button>
		<button tabindex="3" data-bind="i18n: 'KMK004_226', click: copy, enable: existYear"></button>
		<button tabindex="4" class="danger" data-bind="i18n: 'KMK004_227', click: remote, enable: existYear"></button>
	</div>
	<div class="cpn-ccg001"
			style="margin-left: 1px;"
			data-bind="component: {
				name: 'ccg001',
				params:{
					employees: employees
				}
			}"></div>
	<div class="view-e-kmk004">
		<div class="left-content">
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
				<p class="title" data-bind="i18n: 'KMK004_228'"></p>
				<hr></hr>
				<div class="name" data-bind="i18n: model.name"></div>
				<div>
					<div data-bind="ntsFormLabel: {inline: true}, i18n: 'KMK004_229'"></div>
					<!-- ko if: model.isAlreadySetting -->
						<button tabindex="5" data-bind="i18n: 'KMK004_241', click: openDialogF"></button>
					<!-- /ko -->
					<!-- ko ifnot: model.isAlreadySetting -->
						<button tabindex="5" data-bind="i18n: 'KMK004_240', click: openDialogF"></button>
					<!-- /ko -->
				</div>
				<!-- ko if: model.isAlreadySetting -->
					<div class ="setting" data-bind="component: {
						name: 'basic-setting',
						params:{
							type: type,
							selectId: model.id,
							change: change
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
								type: type,
								years: years,
								selectId: model.id
							}
						}"></div>
					</div>
					<div tabindex="7" class= "time-work" data-bind="component: {
						name: 'time-work',
						params:{
							selectedYear: selectedYear,
							checkEmployee: checkEmployee,
							type: type,
							years: years,
							selectId: model.id,
							workTimes: workTimes
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
		public years: KnockoutObservableArray<IYear> = ko.observableArray([]);
		public employees: KnockoutObservableArray<IEmployee> = ko.observableArray([]);
		public selectedCode: KnockoutObservable<string> = ko.observable('');
		public selectedYear: KnockoutObservable<number | null> = ko.observable(null);
		public checkEmployee: KnockoutObservable<boolean> = ko.observable(true);
		public existYear: KnockoutObservable<boolean> = ko.observable(false);
		public type: SIDEBAR_TYPE = 'Com_Person';
		public model: Employee = new Employee();
		public workTimes: KnockoutObservableArray<WorkTime> = ko.observableArray([]);
		public change: KnockoutObservable<string> = ko.observable('');

		created(params: Params) {
			const vm = this;
			vm.years
				.subscribe(() => {
					if (ko.unwrap(vm.years).length == 0) {
						vm.existYear(false);
					} else {
						vm.existYear(true);
					}
				})

			vm.selectedCode
				.subscribe(() => {
					const employee: IEmployee = _.find(ko.unwrap(vm.employees), e => e.code === ko.unwrap(vm.selectedCode));
					if (employee) {
						vm.model.update(employee);
						vm.selectedYear.valueHasMutated();
					}
				})
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

		openDialogF() {
			const vm = this;
			const params = { type: vm.type, selectId: ko.unwrap(vm.model.id) };
			vm.$window.modal('/view/kmk/004/f/index.xhtml', params).then(() => {
				vm.change.valueHasMutated();
			});
		}

		remote() {
			$(document).ready(function () {
				$('.listbox').focus();
			});
		}
	}
}