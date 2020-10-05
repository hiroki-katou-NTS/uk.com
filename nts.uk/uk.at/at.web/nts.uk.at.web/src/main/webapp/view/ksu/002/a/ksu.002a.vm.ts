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

		mode: KnockoutObservable<EDIT_MODE> = ko.observable('copy');
		baseDate: KnockoutObservable<c.DateRange | null> = ko.observable(null);
		schedules: m.MementoObservableArray<c.DayData<ScheduleData>> = ko.observableArray([]).extend({ memento }) as any;

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
						.then((response: WorkSchedule<string>[]) => _.chain(response)
							.orderBy(['date'])
							.map(m => ({
								...m,
								date: moment(m.date, 'YYYY/MM/DD')
							}))
							.value()
						)
						.then((response: WorkSchedule[]) => {
							if (response && response.length) {
								const clones: c.DayData<ScheduleData>[] = ko.toJS(vm.schedules);

								_.each(response, (d) => {
									const exits = _.find(clones, c => d.date.isSame(c.date, 'date'));

									if (exits) {
										exits.data = {
											...exits.data,
											wtype: d.workTypeName,
											wtime: d.workTimeName,
											value: {
												begin: d.startTime,
												finish: d.endTime
											},
											holiday: exits.date.getDate() === 9 ? '海の日' : exits.date.getDate() === 6 ? 'スポーツの日' : '',
											event: exits.date.getDate() === 5 ? `<pre>${JSON.stringify(d, null, 4)}</pre>` : ''
										};

										exits.className = [
											...(exits.className || []),
											exits.date.getDate() === 5 ? c.COLOR_CLASS.EVENT : '',
											exits.date.getDate() === 6 ? c.COLOR_CLASS.HOLIDAY : '',
											exits.date.getDate() === 9 ? c.COLOR_CLASS.HOLIDAY : ''
										].filter(c => !!c)
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

		clickDayCell(type: c.CLICK_CELL, data: c.DayData<ScheduleData>) {
			const vm = this;
			const mode = ko.unwrap(vm.mode);

			const wrap: c.DayData<ScheduleData>[] = ko.toJS(vm.schedules);

			const exist = _.find(wrap, f => moment(f.date).isSame(data.date, 'date'));

			if (exist) {
				const { data, className } = exist;

				exist.data = { ...data, holiday: 'Holiday' };

				exist.className = [...(className || []), c.COLOR_CLASS.HOLIDAY];

				exist.data.value.begin = 720;
				exist.data.value.finish = 1440;
			}

			if (type === 'info' && mode === 'copy') {
				vm.schedules.memento(wrap);
			}
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
		// 実績か
		achievements: boolean;
		active: boolean;
		// 確定済みか
		confirmed: boolean;
		// 年月日
		date: D;
		edit: boolean;
		// 社員ID
		employeeId: string;
		endTime: null | number;
		endTimeEditState: null | number;
		// データがあるか
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