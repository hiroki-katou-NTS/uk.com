/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.ui.at.ksu002.a {
	import m = nts.uk.ui.memento;
	import c = nts.uk.ui.calendar;

	const API = {
		UNAME: '/sys/portal/webmenu/username',
		GSCHE: '/screen/ksu/ksu002/displayInWorkInformation'
	};

	const memento: m.Options = {
		size: 20,
		/*replace: function (data: c.DayData[]) {
			_.each(data, (d: c.DayData) => {

				d.
			});
		}*/
	};

	@bean()
	export class ViewModel extends ko.ViewModel {
		currentUser!: KnockoutComputed<string>;
		showC: KnockoutObservable<boolean> = ko.observable(true);

		baseDate: KnockoutObservable<c.DateRange | null> = ko.observable(null);
		schedules: m.MementoObservableArray<c.DayData> = ko.observableArray([]).extend({ memento }) as any;

		created() {
			const vm = this;
			const bussinesName: KnockoutObservable<string> = ko.observable('');

			vm.currentUser = ko.computed({
				read: () => {
					const bName = ko.unwrap(bussinesName);

					return `${vm.$i18n('KSU002_1')}&nbsp;&nbsp;&nbsp;&nbsp;${vm.$user.employeeCode}&nbsp;&nbsp;${bName}`;
				},
				owner: vm
			});

			vm.$ajax('com', API.UNAME)
				.then((name: string) => bussinesName(name));

			// call to api and get data
			vm.baseDate
				.subscribe((d: c.DateRange) => {
					if (!d) {
						return;
					}

					const { begin, finish } = d;

					if (!begin || !finish) {
						return;
					}

					const command = {
						listSid: [vm.$user.employeeId],
						startDate: moment(begin).toISOString(),
						endDate: moment(finish).toISOString()
					};

					vm.$blockui('show')
						.then(() => vm.$ajax('at', API.GSCHE, command))
						.then((response: ResponseData) => {
							if (response) {
								const { listWorkScheduleWorkInfor } = response;

								if (listWorkScheduleWorkInfor && listWorkScheduleWorkInfor.length) {
									return (listWorkScheduleWorkInfor)
										.map((m) => ({
											...m,
											date: moment(m.date, 'YYYY/MM/DD')
										}));
								}
							}

							return [];
						})
						.then((response: WorkSchedule[]) => {
							if (response && response.length) {
								const clones: c.DayData[] = ko.toJS(vm.schedules);

								_.each(response, (d) => {
									const exits = _.find(clones, c => d.date.isSame(c.date, 'date'));

									if (exits) {
										exits.data = {
											wtype: d.workTypeName,
											wtime: d.workTimeName,
											value: {
												begin: d.startTime,
												finish: d.endTime
											}
										};
									}
								});

								vm.schedules.reset(clones);
							}
						})
						.always(() => vm.$blockui('hide'));
				});
		}

		mounted() {
			const vm = this;

			$(vm.$el).find('[data-bind]').removeAttr('data-bind');
		}

		undoOrRedo(action: 'undo' | 'redo') {
			const vm = this;

			if (action === 'undo') {
				vm.schedules.undo();
			} else if (action === 'redo') {
				vm.schedules.redo();
			}
		}

		clickDayCell(type: string, data: c.DayData) {
			const vm = this;

			const wrap: c.DayData[] = ko.toJS(vm.schedules);

			const exist = _.find(wrap, f => moment(f.date).isSame(data.date, 'date'));

			if (exist) {
				const { data, className } = exist;

				exist.data = { ...data, holiday: 'Holiday' };

				exist.className = [...(className || []), c.COLOR_CLASS.HOLIDAY];
			}

			// vm.schedules.memento(wrap);
		}

		changeDayCell(dayData: c.DayData) {
			const vm = this;

			const wrap: c.DayData[] = _.cloneDeep(ko.toJS(vm.schedules));

			const exist = _.find(wrap, f => moment(f.date).isSame(dayData.date, 'date'));

			if (exist) {
				const { data } = dayData;

				exist.data = { ...data };
			}

			vm.schedules.memento(wrap);
		}
	}

	interface ResponseData {
		listWorkTypeInfo: WorkType[];
		listWorkScheduleWorkInfor: WorkSchedule<string>[];
	}

	interface WorkSchedule<D = moment.Moment> {
		achievements: boolean;
		active: boolean;
		confirmed: boolean;
		date: D;
		edit: boolean;
		employeeId: string;
		endTime: null | number;
		endTimeEditState: null | number;
		haveData: boolean;
		isActive: boolean;
		isEdit: boolean;
		needToWork: boolean;
		startTime: null | number;
		startTimeEditState: null | number;
		supportCategory: number;
		workHolidayCls: null | string;
		workTimeCode: string;
		workTimeEditStatus: null | string;
		workTimeName: string;
		workTypeCode: string;
		workTypeEditStatus: null | string;
		workTypeName: string;
	}

	interface WorkType {
		workStyle: WorkTypeRequired;
		workTimeSetting: number;
		workTypeDto: WorkTypeData;
	}

	interface WorkTypeData {
		memo: string;
		name: string;
		workTypeCode: string;
	}

	enum WorkTypeRequired {
		REQUIRED = 1,
		OPTIONAL = 2,
		NON_REQUIRED = 2
	}
}