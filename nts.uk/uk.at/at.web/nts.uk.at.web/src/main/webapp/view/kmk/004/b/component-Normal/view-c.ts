/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.b {
	const template = `
		<div class="sidebar-content-header">
			<div class="title" data-bind="i18n: 'Com_Workplace'"></div>
			<a class="goback"  data-bind="ntsLinkButton: { jump: '../a/index.xhtml' },i18n: 'KMK004_224'"></a>
			<button tabindex="2" class="proceed" data-bind="i18n: 'KMK004_225', enable: existYear, click: add"></button>
			<button tabindex="3" data-bind="i18n: 'KMK004_226', enable: checkDelete, click: copy"></button>
			<button tabindex="4" class="danger" data-bind="i18n: 'KMK004_227', enable: checkDelete, click: remote"></button>
		</div>
		<div class="view-c-kmk004">
			<table>
				<tbody>
					<tr>
						<td style="vertical-align: baseline; border: solid 1px white;">
							<div class="left-content">
								<div data-bind="component: {
									name: 'kcp004',
									params:{
										selectedId: selectedId,
										model: model,
										isChange: change
									}
								}"></div>
						</td>
						<td style="border: hidden;">
							<div class="right-content">
								<div>
									<p class="title" data-bind="i18n: 'KMK004_228'"></p>
									<hr></hr>
									<div class="name" data-bind="i18n: model.name"></div>
									<div>
										<div data-bind="ntsFormLabel: {inline: true}, text: $i18n('KMK004_229')"></div>
										<!-- ko if: checkSeting -->
											<button tabindex="5" data-bind="i18n: 'KMK004_339', click: openDialogF"></button>
										<!-- /ko -->
										<!-- ko ifnot: checkSeting -->
											<button tabindex="5" data-bind="i18n: 'KMK004_338', click: openDialogF"></button>
										<!-- /ko -->
									</div>
									<div class ="setting" data-bind="component: {
										name: 'basic-setting',
										params:{
											type: type,
											selectId: selectedId,
											change: change,
											checkSeting: checkSeting
										}
									}"></div>
									<div class="label1" data-bind="ntsFormLabel: {inline: true}, text: $i18n('KMK004_232')"></div>
									<div class="content-data">
										<div style="height: 30px;">
											<button tabindex="6" data-bind="i18n: 'KMK004_233', click: openDialogQ"></button>
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
												selectId: selectedId,
												workTimes: workTimes,
												yearDelete: yearDelete,
												startDate: startDate
											}
										}"></div>
									</div>
								</div>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
			<div class = "cf"></div>
		<div>
	`;

	interface Params {

	}

	const API = {
		ADD_WORK_TIME: 'screen/at/kmk004/viewc/wkp/monthlyWorkTime/add',
		DELETE_WORK_TIME: 'screen/at/kmk004/viewc/wkp/monthlyWorkTime/delete'
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
		public workTimes: KnockoutObservableArray<WorkTime> = ko.observableArray([]);
		public change: KnockoutObservable<string> = ko.observable('');
		public checkDelete: KnockoutObservable<boolean> = ko.observable(false);
		public yearDelete: KnockoutObservable<number | null> = ko.observable(null);
		public checkSeting: KnockoutObservable<boolean> = ko.observable(false);
		public startDate: KnockoutObservable<number> = ko.observable(2020);

		created(params: Params) {
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
				// Fix bug render edge
			});

			vm.selectedId
				.subscribe(() => {
					$(document).ready(function () {
						$('#list-box').focus();
					});
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
			const input = { workPlaceId: ko.unwrap(vm.selectedId), yearMonth: yearMonth, laborTime: times };

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
										vm.selectedYear.valueHasMutated();
										vm.change.valueHasMutated();
									})
									.then(() => {
										vm.$errors('clear');
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

		copy() {
			const vm = this;

			vm.$window.modal('/view/kmk/004/r/index.xhtml', {
				screenMode: vm.type,
				data: [],
				selected: ko.unwrap(vm.selectedId),
				year: ko.unwrap(vm.selectedYear),
				laborAttr: 0,
			})
				.then(() => {
					vm.change.valueHasMutated();
				})
				.then(() => {
					$(document).ready(function () {
						$('#list-box').focus();
					});
				});
		}

		remote() {
			const vm = this;
			const param = {
				workplaceId: ko.unwrap(vm.selectedId),
				startMonth: ko.unwrap(ko.unwrap(vm.workTimes)[0].yearMonth),
				endMonth: ko.unwrap(ko.unwrap(vm.workTimes)[ko.unwrap(vm.workTimes).length - 1].yearMonth)
			}
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
							vm.years(ko.unwrap(vm.years));
							if (ko.unwrap(vm.years).length > 0) {
								vm.selectedYear(ko.unwrap(vm.years)[old_index].year);
							} else {
								vm.selectedYear.valueHasMutated();
							}
						})
						.then(() => {
							vm.$errors('clear');
						}).then(() => {
							vm.change.valueHasMutated();
							$(document).ready(function () {
								$('#list-box').focus();
							});
						})
						.then(() => vm.$dialog.info({ messageId: "Msg_16" }))
						.always(() => vm.$blockui("clear"));
				})
		}

		openDialogF() {
			const vm = this;
			const params = {
				type: vm.type,
				selectId: ko.unwrap(vm.selectedId),
				nameSynthetic: ko.unwrap(vm.model.name),
				isSetting: !ko.unwrap(vm.checkSeting)
			};
			vm.$window.modal('/view/kmk/004/f/index.xhtml', params).then(() => {
				vm.change.valueHasMutated();
			});
		}

		openDialogQ() {
			const vm = this;
			const param = { startDate: ko.unwrap(vm.startDate), years: ko.unwrap(vm.years).map((m: IYear) => m.year) };
			vm.$window.modal('/view/kmk/004/q/index.xhtml', param)
				.then((result) => {
					if (result) {
						vm.years.push(new IYear(parseInt(result.year), true));
						vm.years(_.orderBy(ko.unwrap(vm.years), ['year'], ['desc']));
						vm.selectedYear(parseInt(result.year));
					}
				})
				.then(() => {
					vm.selectedYear.valueHasMutated();
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