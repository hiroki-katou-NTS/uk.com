/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.b {
	const template = `
	<div class="sidebar-content-header">
		<label class="title" data-bind="i18n: 'Com_Company'"></label>
		<a class="goback"  data-bind="ntsLinkButton: { jump: '../a/index.xhtml' },i18n: 'KMK004_224'"></a>
		<button tabindex="2" class="proceed" data-bind="i18n: 'KMK004_225', click: add, enable: existYear"></button>
		<button tabindex="3" class="danger" data-bind="i18n: 'KMK004_227', click: remote, enable: checkDelete"></button>
	</div>
	<div class="view-b">
		<div class="header-b">
			<div class="title" data-bind="i18n: 'KMK004_228'"></div>
			<hr></hr>
			<div class="header_title">
				<div data-bind="ntsFormLabel: {inline: true}, text: $i18n('KMK004_229')"></div>
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
			<div data-bind="ntsFormLabel: {inline: true}, text: $i18n('KMK004_232')"></div>
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
						selectId: selectId,
						workTimes: workTimes,
						yearDelete: yearDelete,
						startDate: startDate
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
		public checkDelete: KnockoutObservable<boolean> = ko.observable(false);
		public yearDelete: KnockoutObservable<number | null> = ko.observable(null);
		public selectId: KnockoutObservable<string> = ko.observable('');
		public startDate: KnockoutObservable<number> = ko.observable(2020);

		created() {
			const vm = this;
		}

		mounted() {
			const vm = this;

			vm.selectedYear
				.subscribe(() => {
					const exist = _.find(ko.unwrap(vm.years), (m: IYear) => m.year as number == ko.unwrap(vm.selectedYear) as number);

					if (exist) {
						if (ko.unwrap(vm.existYear)) {
							if (exist.isNew) {
								vm.checkDelete(false);
							} else {
								vm.checkDelete(true);
							}
						} else {
							vm.checkDelete(true);
						}
					} else {
						vm.checkDelete(false);
					}
				});

			vm.years
				.subscribe(() => {
					if (ko.unwrap(vm.years).length > 0) {
						vm.existYear(true);
					} else {
						vm.existYear(false);
						vm.checkDelete(false);
					}
				});

			$(document).ready(function () {
				$('#list-box').focus();
			});
		}

		add() {
			const vm = this;
			const times = _.map(ko.unwrap(vm.workTimes), ((value) => {
				return ko.unwrap(value.laborTime);
			}));

			const yearMonth = _.map(ko.unwrap(vm.workTimes), ((value) => {
				return ko.unwrap(value.yearMonth);
			}));
			const input = { yearMonth: yearMonth, laborTime: times };

			vm.$blockui('invisible')
				.then(() => {
					vm.validate()
						.then((valid: boolean) => {
							if (valid) {

								vm.$ajax(API.ADD_WORK_TIME, input)
									.done(() => {
										vm.$dialog.info({ messageId: 'Msg_15' });
										_.remove(ko.unwrap(vm.years), ((value) => {
											return value.year == ko.unwrap(vm.selectedYear) as number;
										}));
										vm.years.push(new IYear(ko.unwrap(vm.selectedYear) as number, false));
										vm.years(_.orderBy(ko.unwrap(vm.years), ['year'], ['desc']));
									})
									.then(() => {
										vm.$errors('clear');
									}).then(() => {
										vm.selectedYear.valueHasMutated();
									});
							}
						});
				})
				.always(() => {
					vm.$blockui('clear');
					$(document).ready(function () {
						$('#list-box').focus();
					});
				});
		}

		remote() {
			const vm = this;
			const param = { year: ko.unwrap(vm.selectedYear), workType: 0 }
			const index = _.map(ko.unwrap(vm.years), m => m.year.toString()).indexOf(ko.unwrap(vm.selectedYear).toString());
			const old_index = index === ko.unwrap(vm.years).length - 1 ? index - 1 : index;

			nts.uk.ui.dialog
				.confirm({ messageId: "Msg_18" })
				.ifCancel(() => {
					$(document).ready(function () {
						$('#list-box').focus();
					});
				})
				.ifYes(() => {
					vm.$blockui("invisible")
						.then(() => vm.$ajax(API.DELETE_WORK_TIME, param))
						.done(() => {
							vm.yearDelete(ko.unwrap(vm.selectedYear));
						})
						.then(() => {
							_.remove(ko.unwrap(vm.years), ((value) => {
								return value.year == ko.unwrap(vm.selectedYear);
							}));
							if (ko.unwrap(vm.years).length > 0) {
								vm.years(ko.unwrap(vm.years));
								vm.selectedYear(ko.unwrap(vm.years)[old_index].year);
							}
						})
						.then(() => {
							vm.$errors('clear');
							vm.years.valueHasMutated();
						})
						.then(() => vm.$dialog.info({ messageId: "Msg_16" }))
						.then(() => {
							vm.selectedYear.valueHasMutated();
							$(document).ready(function () {
								$('#list-box').focus();
							});
						})
						.always(() => vm.$blockui("clear"));
				});
		}

		openDialogF() {
			const vm = this;
			const params = { type: 'Com_Company', selectId: 'Chung dep trai', nameSynthetic: 'Com_Company', isSetting: true };
			vm.$window.modal('/view/kmk/004/f/index.xhtml', params).then(() => {
				vm.change.valueHasMutated();
			});
		}

		openDialogQ() {
			const vm = this;
			const param = { startDate: ko.unwrap(vm.startDate), years: ko.unwrap(vm.years).map((m: IYear) => m.year) };
			vm.$window.modal('/view/kmk/004/q/index.xhtml', param).then((result) => {
				if (result) {
					vm.years.push(new IYear(parseInt(result.year), true));
					vm.years(_.orderBy(ko.unwrap(vm.years), ['year'], ['desc']));
					vm.selectedYear(parseInt(result.year));
					vm.selectedYear.valueHasMutated();
				}
			});
		}

		public validate(action: 'clear' | undefined = undefined) {
			if (action === 'clear') {
				return $.Deferred().resolve()
					.then(() => $('.nts-input').ntsError('clear'));
			} else {
				return $.Deferred().resolve()
					.then(() => $('.nts-input').trigger("validate"))
					.then(() => !$('.nts-input').ntsError('hasError'));
			}
		}
	}
}