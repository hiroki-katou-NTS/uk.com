/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.ui.at.ksu002.a {
	import m = nts.uk.ui.memento;
	import c = nts.uk.ui.calendar;

	const API = {
		UNAME: '/sys/portal/webmenu/username'
	};

	const memento: m.Options = {
		size: 20,
		/*replace: function (data: c.DayData[]) {
			_.each(data, (d: c.DayData) => {

				d.
			});
		}*/
	};

	const defaultBaseDate = (): c.DateRange => ({
		begin: moment().startOf('month').toDate(),
		finish: moment().endOf('month').toDate()
	});

	@bean()
	export class ViewModel extends ko.ViewModel {
		currentUser!: KnockoutComputed<string>;
		showC: KnockoutObservable<boolean> = ko.observable(true);

		baseDate: KnockoutObservable<c.DateRange | null> = ko.observable(defaultBaseDate());
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
				.subscribe(() => {
					vm.$blockui('show');
					setTimeout(() => {
						const clones: c.DayData[] = ko.toJS(vm.schedules);

						_.each(clones, (d) => {
							d.data = {
								wtype: 'wtype',
								wtime: 'wtime',
								value: {
									begin: 128,
									finish: 640
								}
							};

							d.className = [
								d.date.getDate() === 16 ? c.COLOR_CLASS.SPECIAL : undefined,
								d.date.getDate() === 18 ? c.COLOR_CLASS.HOLIDAY : undefined,
								d.date.getDate() === 19 ? c.COLOR_CLASS.HOLIDAY : undefined,
								d.date.getDate() === 19 ? c.COLOR_CLASS.SPECIAL : undefined,
							].filter(f => !!f);
						});

						vm.schedules.reset(clones);

						vm.$blockui('hide');
					}, 3000);
				});
		}

		mounted() {
			const vm = this;

			vm.$nextTick(() => vm.schedules.reset());

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

			vm.schedules.memento(wrap);
		}
	}
}