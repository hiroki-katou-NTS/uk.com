/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.b {
	const template = `
	<div class="sidebar-content-header">
		<div class="title" data-bind="i18n: 'Com_Person'"></div>
		<a class="goback"  data-bind="ntsLinkButton: { jump: '../a/index.xhtml' },i18n: 'KMK004_224'"></a>
		<button tabindex="2" class="proceed" data-bind="i18n: 'KMK004_225', click: add, enable: checkAdd"></button>
		<button tabindex="3" data-bind="i18n: 'KMK004_226', click: copy, enable: checkDelete"></button>
		<button tabindex="4" class="danger" data-bind="i18n: 'KMK004_227', click: remote, enable: checkDelete"></button>
	</div>
	<div class="cpn-ccg001"
			style="margin-left: 1px;"
			data-bind="component: {
				name: 'ccg001',
				params:{
					employees: employees,
					selectedCode: selectedCode,
					isReload: isReload
				}
			}"></div>
	<div class="view-e-kmk004">
		<table>
			<tbody>
				<tr>
					<td style="vertical-align: baseline; border: solid 1px white;">
						<div class="left-content">
							<div class="cpn-kcp005" data-bind="component: {
								name: 'kcp005',
								params:{
									selectedCode: selectedCode,
									employees: employees,
									isChange: change,
									isReload: isReload
								}
							}"></div>
						</div>
					</td>
					<td style="border: hidden;">
						<div class="right-content">
							<div>
								<p class="title" data-bind="i18n: 'KMK004_228'"></p>
								<hr></hr>
								<div class="name" data-bind="i18n: model.nameSynthetic"></div>
								<div style="height: 35px;">
									<div data-bind="ntsFormLabel: {inline: true}, text: $i18n('KMK004_229')"></div>
									<!-- ko if: checkSeting -->
										<button tabindex="5" data-bind="i18n: 'KMK004_343', click: openDialogF, enable: existEmployee"></button>
									<!-- /ko -->
									<!-- ko ifnot: checkSeting -->
										<button tabindex="5" data-bind="i18n: 'KMK004_342', click: openDialogF, enable: existEmployee"></button>
									<!-- /ko -->
								</div>
								<div class ="setting" data-bind="component: {
									name: 'basic-setting',
									params:{
										type: type,
										selectId: model.id,
										change: change,
										checkSeting: checkSeting
									}
								}"></div>
								<div style="height: 35px;" class="label1" data-bind="ntsFormLabel: {inline: true}, text: $i18n('KMK004_232')"></div>
								<div class="content-data">
									<div style="height: 35px;">
										<button tabindex="6" data-bind="i18n: 'KMK004_233', click: openDialogQ, enable: existEmployee"></button>
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
											workTimes: workTimes,
											yearDelete: yearDelete,
											startDate: startDate,
											newYearQ: newYearQ
										}
									}"></div>
								</div>
							</div>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	<div>
	`;

	interface Params {

	}

	const API = {
		ADD_WORK_TIME: 'screen/at/kmk004/viewd/sha/monthlyWorkTime/add',
		DELETE_WORK_TIME: 'screen/at/kmk004/viewd/sha/monthlyWorkTime/delete',
		GET_EMPLOYEEIDS: 'screen/at/kmk004/viewe/sha/getEmployeeId',
		DELETE_BY_YM: 'screen/at/kmk004/viewd/sha/monthlyWorkTime/deleteByYearMonth'
	};

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
		public existEmployee: KnockoutObservable<boolean> = ko.observable(false);
		public checkDelete: KnockoutObservable<boolean> = ko.observable(false);
		public checkAdd: KnockoutObservable<boolean> = ko.observable(false);
		public yearDelete: KnockoutObservable<number | null> = ko.observable(null);
		public checkSeting: KnockoutObservable<boolean> = ko.observable(false);
		public startDate: KnockoutObservable<number> = ko.observable(2020);
		public isReload: KnockoutObservable<string> = ko.observable('');
		public newYearQ: KnockoutObservable<boolean> = ko.observable(false);

		created(params: Params) {
			const vm = this;
			vm.years
				.subscribe(() => {
					if (ko.unwrap(vm.years).length == 0) {
						vm.existYear(false);
					} else {
						vm.existYear(true);
					}
				});

			vm.selectedCode
				.subscribe(() => {
					const employee: IEmployee = _.find(ko.unwrap(vm.employees), e => e.code === ko.unwrap(vm.selectedCode));
					if (employee) {
						vm.model.update(employee);
						vm.selectedYear.valueHasMutated();
					}
					vm.updateCheck();

					$(document).ready(function () {
						$('#list-box').focus();
					});
				});

			vm.employees
				.subscribe(() => {
					if (ko.unwrap(vm.employees).length == 0) {
						vm.existEmployee(false);
					} else {
						vm.existEmployee(true);
						vm.checkDelete(false);
					}
				});

			vm.workTimes
				.subscribe(() => {
					let check = false;
					_.forEach(ko.unwrap(vm.workTimes), ((value) => {
						if (ko.unwrap(value.check)) {
							check = true;
						}
					}));
					if (ko.unwrap(vm.existYear)) {
						vm.checkAdd(check);
					} else {
						vm.checkAdd(false);
					}
				});

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
		}

		mounted() {
			const vm = this;

			$(document).ready(function () {
			// 	$('#list-box').focus();
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
			const input = { sid: ko.unwrap(vm.model.id), yearMonth: yearMonth, laborTime: times };

			const yearMonthDelete = _.map(ko.unwrap(vm.workTimes), ((value) => {
				if (!ko.unwrap(value.check)) {
					return ko.unwrap(value.yearMonth);
				}
			}));

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
										_.forEach(yearMonthDelete, ((value) => {
											if (value) {
												const input = { empId: ko.unwrap(vm.model.id), laborAttr: 0, yearMonth: value };

												vm.$ajax(API.DELETE_BY_YM, input);
											}
										}));
									})
									.then(() => {
										vm.selectedYear.valueHasMutated();
										vm.change.valueHasMutated();
									})
									.then(() => {
										vm.$errors('clear');
										$(document).ready(function () {
											$('#list-box').focus();
										});
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
				data: ko.unwrap(vm.employees),
				selected: ko.unwrap(vm.model.code),
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

		openDialogF() {
			const vm = this;
			const params = {
				type: vm.type,
				selectId: ko.unwrap(vm.model.id),
				nameSynthetic: ko.unwrap(vm.model.nameSynthetic),
				isSetting: !ko.unwrap(vm.checkSeting)
			};
			vm.$window.modal('/view/kmk/004/f/index.xhtml', params).then(() => {
				vm.change.valueHasMutated();
			});
		}

		remote() {
			const vm = this;
			const param = {
				sid: ko.unwrap(vm.model.id),
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
								vm.change.valueHasMutated();
							}
						})
						.then(() => {
							vm.$errors('clear');
						}).then(() => {
							$(document).ready(function () {
								$('#list-box').focus();
							});
						})
						.then(() => vm.$dialog.info({ messageId: "Msg_16" }))
						.always(() => vm.$blockui("clear"));
				})
		}

		openDialogQ() {
			const vm = this;
			const param = { startDate: ko.unwrap(vm.startDate), years: ko.unwrap(vm.years).map((m: IYear) => m.year) };
			vm.$window.modal('/view/kmk/004/q/index.xhtml', param).then((result) => {
				if (result) {
					vm.years.push(new IYear(parseInt(result.year), true));
					vm.years(_.orderBy(ko.unwrap(vm.years), ['year'], ['desc']));
					if (parseInt(result.year) == ko.unwrap(vm.selectedYear) as number) {
						vm.selectedYear.valueHasMutated();
					} else {
						vm.selectedYear(parseInt(result.year));
					}
					vm.newYearQ(true);
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

		public updateCheck() {
			const vm = this;
			vm.$ajax(API.GET_EMPLOYEEIDS)
				.then((data: any) => {
					const exist = _.find(data, (m: any) => m.employeeId === ko.unwrap(vm.model.id));
					if (exist) {
						vm.model.updateStatus(true);
					} else {
						vm.model.updateStatus(false);
					}
				});
		}
	}
}