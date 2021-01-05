/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.components {

	import IYear = nts.uk.at.view.kmk004.components.transform.IYear;
	import SIDEBAR_TYPE = nts.uk.at.view.kmk004.p.SIDEBAR_TYPE;

	interface Params {
		selectedYear: KnockoutObservable<number | null>;
		checkEmployee?: KnockoutObservable<boolean>;
		years: KnockoutObservableArray<IYear>;
		workTimes: KnockoutObservableArray<WorkTimeL>;
		selectedId: KnockoutObservable<string>;
		type: SIDEBAR_TYPE;
		yearDelete: KnockoutObservable<number | null>;
	}

	const API = {
		GET_WORK_TIME_BY_COM: 'screen/at/kmk004/viewL/getWorkingHoursByCompany',
		GET_WORK_TIME_BY_WKP: 'screen/at/kmk004/viewM/getWorkingHoursByWkp',
		GET_WORK_TIME_BY_EMP: 'screen/at/kmk004/viewN/getWorkingHoursByEmp',
		GET_WORK_TIME_BY_SHA: 'screen/at/kmk004/viewO/getWorkingHoursByEmployee'
	};

	const DEFOR_TYPE = 1;

	const template = `
        <div class="list-time">
            <table>
                <tbody>
                    <tr>
                        <!-- ko if: checkEmployee -->
                            <td class= "check-row1"></td>
                        <!-- /ko -->
                        <td class= "label-row1">
                            <div data-bind="i18n: 'KMK004_221'"></div>
                        </td>
                        <td class= "label-row2">
                            <div data-bind="i18n: 'KMK004_222'"></div>
                        </td>
                    </tr>
                    <!-- ko foreach: workTimes -->
                    <tr>
                        <!-- ko if: $parent.checkEmployee -->
                            <td class= "check-column1">
                                <div data-bind="ntsCheckBox: { checked: $data.check }"></div>
                            </td>
                        <!-- /ko -->
                        <td class="label-column1" data-bind="text: $data.nameMonth"></td>
                        <td class="label-column2">
                            <!-- ko if: $parent.checkEmployee -->
                                <input class="lable-input" 
                                    data-bind="ntsTimeEditor: {
                                        value: $data.laborTime,
                                        enable: $data.check,
                                        inputFormat: 'time',
                                        option: {
                                            width: '60px',
                                            textalign: 'center'}, 
                                        mode: 'time'}" />
                            <!-- /ko -->
                            <!-- ko ifnot: $parent.checkEmployee -->
                                <input class="lable-input" 
                                    data-bind="ntsTimeEditor: {
                                        value: $data.laborTime,
                                        enable: $data.check, 
                                        inputFormat: 'time',
                                        option: {
                                            width: '60px',
                                            textalign: 'center'}, 
                                        mode: 'time'}"/>
                            <!-- /ko -->
                        </td>
                    </tr>
                    <!-- /ko -->
                    <!-- ko ifnot: checkEmployee -->
                        <tr>
                            <td class="label-column1">
                                <div data-bind="i18n: 'KMK004_223'"></div>
                            </td>
                            <td class="label-column2">
                                <div data-bind="text: total"></div>
                            </td>
                        </tr>
                    <!-- /ko -->
                </tbody>
            </table>
        </div>
    `;

	@component({
		name: 'time-work',
		template
	})

	class ListTimeWork extends ko.ViewModel {

		public workTimes: KnockoutObservableArray<WorkTimeL> = ko.observableArray([]);
		public total: KnockoutObservable<string> = ko.observable('');
		public selectedYear: KnockoutObservable<number | null> = ko.observable(null);
		public checkNullYear: KnockoutObservable<boolean> = ko.observable(false);
		public checkEmployee: KnockoutObservable<boolean> = ko.observable(false);
		public years: KnockoutObservableArray<IYear>;
		public type: SIDEBAR_TYPE;
		public mode: KnockoutObservable<'New' | 'Update'> = ko.observable('New');
		public selectedId: KnockoutObservable<string> = ko.observable('');
		public workTimeSaves: KnockoutObservableArray<WorkTimeSaveL> = ko.observableArray([]);
		public yearDelete: KnockoutObservable<number | null> = ko.observable(null);

		created(params: Params) {
			const vm = this;
			vm.selectedYear = params.selectedYear;
			vm.years = params.years;
			vm.checkEmployee = params.checkEmployee;
			vm.workTimes = params.workTimes;
			vm.selectedId = params.selectedId;
			vm.type = params.type;
			vm.yearDelete = params.yearDelete;

			vm.initList();

			vm.workTimes.subscribe((wts) => {
				const total: number = wts.reduce((p, c) => p += Number(c.laborTime()), 0);
				const first: string = Math.floor(total / 60) + '';
				var last: string = total % 60 + '';

				if (last.length < 2) {
					last = '0' + last;
				}

				vm.total(first + ':' + last);

				if (ko.unwrap(vm.selectedYear) != null) {
					const index = _.map(ko.unwrap(vm.years), m => m.year.toString()).indexOf(ko.unwrap(vm.selectedYear).toString());

					if (ko.unwrap(vm.years).length > 0) {
						if (ko.unwrap(vm.mode) === 'Update') {
							if (!ko.unwrap(vm.years)[index]) {
								_.remove(ko.unwrap(vm.years), ((value) => {
									return value.year == ko.unwrap(vm.selectedYear);
								}));
								vm.years.push(new IYear(ko.unwrap(vm.selectedYear), true));
								vm.years(_.orderBy(ko.unwrap(vm.years), ['year'], ['desc']));
							}
						}
					}
					vm.updateListSave();
				}

				_.forEach(ko.unwrap(vm.workTimes), ((value) => {
					if (ko.unwrap(value.check)) {
						if (ko.unwrap(value.laborTime) == null) {
							value.updateLaborTime(0);
						}
					} else {
						value.updateLaborTime(null);
					}
				}));

			});

			vm.selectedYear
				.subscribe(() => {
					vm.mode("New")
					vm.reloadData();
				});

			vm.selectedId
				.subscribe(() => {
					vm.workTimeSaves([]);
					setTimeout(() => {
						vm.selectedYear.valueHasMutated();
					}, 100);
				});

			vm.years
				.subscribe(() => {
					if (ko.unwrap(vm.years).length == 0) {
						vm.workTimeSaves([]);
						vm.initList();

					} else {
						if (ko.unwrap(vm.workTimeSaves).length > ko.unwrap(vm.years).length) {
							_.remove(ko.unwrap(vm.workTimeSaves), ((value) => {
								return value.year == ko.unwrap(vm.yearDelete);
							}))
						}
					}
				});
		}

		reloadData() {
			const vm = this;
			const comInput = { workType: DEFOR_TYPE, year: vm.selectedYear() };
			const wkpInput = { workplaceId: vm.selectedId(), workType: DEFOR_TYPE, year: vm.selectedYear() };
			const empInput = { employmentCode: vm.selectedId(), workType: DEFOR_TYPE, year: vm.selectedYear() };
			const shaInput = { sId: vm.selectedId(), workType: DEFOR_TYPE, year: vm.selectedYear() };

			if (!vm.selectedId) {
				return;
			}

			if (ko.unwrap(vm.selectedYear) != null) {
				vm.checkNullYear(true);
			}

			const exist = _.find(ko.unwrap(vm.years), (emp: IYear) => emp.year as number == ko.unwrap(vm.selectedYear) as number);
			if (exist) {
				switch (vm.type) {
					case 'Com_Company':
						const exist = _.find(ko.unwrap(vm.workTimeSaves), (m: WorkTimeSaveL) => m.year as number == ko.unwrap(vm.selectedYear) as number);

						if (exist) {
							vm.workTimes(exist.worktimes.map(m => new WorkTimeL({ ...m, parent: vm.workTimes })));
							vm.mode('Update');

						} else {
							vm.$ajax(API.GET_WORK_TIME_BY_COM, comInput)
								.then((data: IWorkTimeL[]) => {
									if (data.length > 0) {
										const workTime: IWorkTimeL[] = [];

										data.map(m => {
											const s: IWorkTimeL = { check: true, yearMonth: m.yearMonth, laborTime: m.laborTime };
											workTime.push(s);
										});

										vm.workTimes(workTime.map(m => new WorkTimeL({ ...m, parent: vm.workTimes })));
										vm.mode('Update');
									}
								});
						}

						break;

					case 'Com_Workplace':
						if (ko.unwrap(vm.selectedId) !== '') {
							const exist = _.find(ko.unwrap(vm.workTimeSaves), (m: WorkTimeSaveL) => m.year as number == ko.unwrap(vm.selectedYear) as number);
							if (exist) {
								vm.workTimes(exist.worktimes.map(m => new WorkTimeL({ ...m, parent: vm.workTimes })));
								vm.mode('Update');
							} else {
								vm.$ajax(API.GET_WORK_TIME_BY_WKP, ko.toJS(wkpInput))
									.then((data: IWorkTimeL[]) => {
										if (data.length > 0) {
											const workTime: IWorkTimeL[] = [];

											data.map(m => {
												const s: IWorkTimeL = { check: true, yearMonth: m.yearMonth, laborTime: m.laborTime };
												workTime.push(s);
											});

											vm.workTimes(workTime.map(m => new WorkTimeL({ ...m, parent: vm.workTimes })));
										}
										vm.mode('Update');
									});

							}
						}

						break;

					case 'Com_Employment':
						if (ko.unwrap(vm.selectedId) !== '') {
							const exist = _.find(ko.unwrap(vm.workTimeSaves), (m: WorkTimeSaveL) => m.year as number == ko.unwrap(vm.selectedYear) as number);

							if (exist) {
								vm.workTimes(exist.worktimes.map(m => new WorkTimeL({ ...m, parent: vm.workTimes })));
								vm.mode('Update');
							} else {
								vm.$ajax(API.GET_WORK_TIME_BY_EMP, ko.toJS(empInput))
									.then((data: IWorkTimeL[]) => {
										if (data.length > 0) {
											const workTime: IWorkTimeL[] = [];

											data.map(m => {
												const s: IWorkTimeL = { check: true, yearMonth: m.yearMonth, laborTime: m.laborTime };
												workTime.push(s);
											});

											vm.workTimes(workTime.map(m => new WorkTimeL({ ...m, parent: vm.workTimes })));
										}
										vm.mode('Update');
									});
							}
						}
						break;

					case 'Com_Person':
						if (ko.unwrap(vm.selectedId) !== '') {
							const exist = _.find(ko.unwrap(vm.workTimeSaves), (m: WorkTimeSaveL) => m.year as number == ko.unwrap(vm.selectedYear) as number);

							if (exist) {
								vm.workTimes(exist.worktimes.map(m => new WorkTimeL({ ...m, parent: vm.workTimes })));
								vm.mode('Update');
							} else {
								vm.$ajax(API.GET_WORK_TIME_BY_SHA, ko.toJS(shaInput))
									.then((data: IWorkTimeL[]) => {
										if (data.length > 0) {
											const workTime: IWorkTimeL[] = [];

											data.map(m => {
												const s: IWorkTimeL = { check: true, yearMonth: m.yearMonth, laborTime: m.laborTime };
												workTime.push(s);
											});

											vm.workTimes(workTime.map(m => new WorkTimeL({ ...m, parent: vm.workTimes })));
										}
										vm.mode('Update');
									});
							}

						}

						break;
				}
			} else {
				vm.initList();
			}

		}

		initList() {
			const vm = this
			var check: boolean = false;
			const comInput = { workType: DEFOR_TYPE, year: 9998 };

			if (vm.type === 'Com_Person') {
				check = true;
			}

			vm.$ajax(API.GET_WORK_TIME_BY_COM, comInput)
				.done((data: IWorkTimeL[]) => {
					if (data.length > 0) {
						const data1: IWorkTimeL[] = [];

						if (ko.unwrap(vm.checkEmployee)) {
							check = false;
						}
						data.map(m => {
							const s: IWorkTimeL = { check: check, yearMonth: m.yearMonth, laborTime: null };
							data1.push(s);
						});
						vm.workTimes(data1.map(m => new WorkTimeL({ ...m, parent: vm.workTimes })));
						vm.mode('Update');
					}
				});
		}

		updateListSave() {
			const vm = this;
			const exist = _.find(ko.unwrap(vm.workTimeSaves), (m: WorkTimeSaveL) => m.year as number == ko.unwrap(vm.selectedYear) as number);

			if (exist) {
				_.remove(ko.unwrap(vm.workTimeSaves), ((value) => {
					return value.year == ko.unwrap(vm.selectedYear) as number;
				}));

				let s: IWorkTimeL[] = [];

				_.map(ko.unwrap(vm.workTimes), ((value) => {
					const t = { check: ko.unwrap(value.check), yearMonth: ko.unwrap(value.yearMonth), laborTime: ko.unwrap(value.laborTime) };
					s.push(t);
				}));

				vm.workTimeSaves.push(new WorkTimeSaveL(ko.unwrap(vm.selectedYear), s));

			} else {
				let s: IWorkTimeL[] = [];

				_.map(ko.unwrap(vm.workTimes), ((value) => {
					const t = { check: ko.unwrap(value.check), yearMonth: ko.unwrap(value.yearMonth), laborTime: ko.unwrap(value.laborTime) };
					s.push(t);
				}));

				vm.workTimeSaves.push(new WorkTimeSaveL(ko.unwrap(vm.selectedYear) as number, s));
			}

			if (ko.unwrap(vm.years).length == 0) {
				vm.workTimeSaves([]);
			}
		}
	}
}

class WorkTimeSaveL {
	year: number;
	worktimes: IWorkTimeL[] = [];

	constructor(year: number, workTimes: IWorkTimeL[]) {
		this.year = year;
		this.worktimes = workTimes;
	}
}

interface IWorkTimeL {
	check: boolean;
	yearMonth: number;
	laborTime: number;
}

class WorkTimeL {
	check: KnockoutObservable<boolean> = ko.observable(false);
	yearMonth: KnockoutObservable<number | null> = ko.observable(null);
	nameMonth: KnockoutObservable<string> = ko.observable('');
	laborTime: KnockoutObservable<number> = ko.observable(0);

	constructor(params?: IWorkTimeL & { parent: KnockoutObservableArray<WorkTimeL> }) {
		const md = this;

		md.create(params);
		this.laborTime.subscribe(c => params.parent.valueHasMutated());
		this.check.subscribe(c => params.parent.valueHasMutated());
	}

	public create(param?: IWorkTimeL) {
		const md = this;
		md.check(param.check);
		md.yearMonth(param.yearMonth);
		md.laborTime(param.laborTime);

		if (param.check) {
			md.laborTime(param.laborTime);
		}

		if (!md.check) {
			md.laborTime(0);
		}

		switch (param.yearMonth.toString().substring(4, 6)) {
			case "01":
				md.nameMonth('1月度')
				break
			case "02":
				md.nameMonth('2月度')
				break
			case "03":
				md.nameMonth('3月度')
				break
			case "04":
				md.nameMonth('4月度')
				break
			case "05":
				md.nameMonth('5月度')
				break
			case "06":
				md.nameMonth('6月度')
				break
			case "07":
				md.nameMonth('7月度')
				break
			case "08":
				md.nameMonth('8月度')
				break
			case "09":
				md.nameMonth('9月度')
				break
			case "10":
				md.nameMonth('10月度')
				break
			case "11":
				md.nameMonth('11月度')
				break
			case "12":
				md.nameMonth('12月度')
				break
		}
	}
	public updateLaborTime(value: number | null) {
		this.laborTime(value);
	}
}

