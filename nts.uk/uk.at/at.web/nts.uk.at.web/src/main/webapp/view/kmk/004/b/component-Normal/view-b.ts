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
						type: type,
						selectId: ko.observable(''),
						change: change
					}
				}"></div>
			</div>
			<div data-bind="ntsFormLabel: {inline: true}, i18n: 'KMK004_232'"></div>
		</div>
		<div class="content">
			<div>
				<button tabindex="5" data-bind="i18n: 'KMK004_233', click: openDialogQ"></button>
			</div>
			<div class= "data">
				<div class= "box-year" data-bind="component: {
					name: 'box-year',
					params:{
						selectedYear: selectedYear,
						param: ko.observable(''),
						type: type,
						years: years
					}
				}"></div>
				<div tabindex="7" class= "time-work" data-bind="component: {
					name: 'time-work',
					params:{
						selectedYear: selectedYear,
						years: years,
						type: type,
						selectId: ko.observable(''),
						workTimes: workTimes
					}
				}"></div>
			</div>
		</div>
	</div>
	`;

	interface Params {

	}

	const API = {
		ADD_WORK_TIME: 'screen/at/kmk004/viewB/com/monthlyWorkTime/add',
		DELETE_WORK_TIME: 'screen/at/kmk004/viewB/com/monthlyWorkTime/delete'
	};

	@component({
		name: 'view-b',
		template
	})

	export class ViewBComponent extends ko.ViewModel {

		public years: KnockoutObservableArray<IYear> = ko.observableArray([]);
		public selectedYear: KnockoutObservable<number | null> = ko.observable(null);
		public existYear: KnockoutObservable<boolean> = ko.observable(false);
		public type: SIDEBAR_TYPE = 'Com_Company';
		public workTimes: KnockoutObservableArray<WorkTime> = ko.observableArray([]);
		public change: KnockoutObservable<string> = ko.observable('');

		created() {
			const vm = this;

			vm.years
				.subscribe(() => {
					if (ko.unwrap(vm.years).length > 0) {
						vm.existYear(true);
					} else {
						vm.existYear(false);
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

			_.remove(ko.unwrap(vm.years), ((value) => {
				return value.year == ko.unwrap(vm.selectedYear) as number;
			}));
			vm.years.push(new IYear(ko.unwrap(vm.selectedYear) as number, false));
			vm.years(_.orderBy(ko.unwrap(vm.years), ['year'], ['desc']));

			$(document).ready(function () {
				$('.listbox').focus();
			});
		}

		remote() {
			const vm = this;
			const param = { year: ko.unwrap(vm.selectedYear), workType: 0 }
			const index = _.map(ko.unwrap(vm.years), m => m.year).indexOf(ko.unwrap(vm.selectedYear));

			vm.$ajax(API.DELETE_WORK_TIME, param)
				.done(() => {
					_.remove(ko.unwrap(vm.years), ((value) => {
						return value.year == ko.unwrap(vm.selectedYear);
					}));
					vm.years(ko.unwrap(vm.years));
					vm.selectedYear(ko.unwrap(vm.years)[index].year);
				})

			$(document).ready(function () {
				$('.listbox').focus();
			});
		}

		openDialogF() {
			const vm = this;
			const params = { type: 'Com_Company', selectId: 'Chung dep trai' };
			vm.$window.modal('/view/kmk/004/f/index.xhtml', params).then(() => {
				vm.change.valueHasMutated();
			});
		}

		openDialogQ() {
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

		public validate(action: 'clear' | undefined = undefined) {
			if (action === 'clear') {
				return $.Deferred().resolve()
					.then(() => $('.nts-input').ntsError('clear'));
			} else {
				return $.Deferred().resolve()
					/** Gọi xử lý validate của kiban */
					.then(() => $('.nts-input').trigger("validate"))
					/** Nếu có lỗi thì trả về false, không thì true */
					.then(() => !$('.nts-input').ntsError('hasError'));
			}
		}
	}
}