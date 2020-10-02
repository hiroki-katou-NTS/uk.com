/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.ui.at.ksu002.a {
	import c = nts.uk.ui.calendar;

	const template = `
		<div class="cf" data-bind="ntsDatePicker: { 
			value: yearMonth,
			dateFormat: 'yearmonth' ,
		 	valueFormat: 'YYYYMM',
	  		fiscalMonthsMode: true,
			defaultClass: 'round-orange',
			showJumpButtons: true  }">
		</div>
		
		<div class="title-label">
			<span data-bind="i18n: 'KSU002_23'"></span>
			<span data-bind="i18n: 'KSU002_7'"></span>
		</div>
		
        <div data-bind="ntsComboBox: {
            width: '200px',
            name: $component.$i18n('KSU002_22'),
            value: $component.selectedRangeIndex,
            options: $component.dateRanges,
            optionsValue: 'id',
            optionsText: 'title',
            editable: false,
            selectFirstIfNull: true,
            columns: [
                { prop: 'title', length: 10 },
            ]}"></div>

		<div class="title-label">
			<span data-bind="i18n: 'KSU002_6'"></span>
			<span data-bind="i18n: 'KSU002_7'"></span>
		</div>

		<div class="cf" data-bind="ntsSwitchButton: {
			name: $i18n('KSU002_6'),
			value: ko.observable(1),
			options: [
				{ code: 1, name: $i18n('KSU002_8') },
				{ code: 2, name: $i18n('KSU002_9') }
			],
			optionsText: 'name',
			optionsValue: 'code' }"></div>
					
		<style type="text/css" rel="stylesheet">
            .title-date {
				margin: 5px 0;
				border: 1px solid #cccccc;
				background-color: #edfac2;
				border-radius: 5px;
				padding: 6px;
				display: inline-block;
			}
			.title-date>div {
				float: left;
				display: block;
			}
			.title-date>div.title-label {
				padding: 0 10px;
				line-height: 32px;
			}
			.title-date .nts-switch-button {
				min-width: 60px;
				min-height: 32px;
			}
			.title-date .nts-datepicker-wrapper>input,
			.title-date .nts-datepicker-wrapper>button {
				vertical-align: top;
			}
			.title-date .nts-datepicker-wrapper>input {
				height: 20px;
			}
			.title-date .nts-datepicker-wrapper>button {
				height: 29px;
			}
			.title-date .nts-datepicker-wrapper.arrow-bottom:before,
			.title-date .nts-datepicker-wrapper.arrow-bottom:after {
				left: 45px;
			}
        </style>
        <style type="text/css" rel="stylesheet" data-bind="html: $component.style"></style>
    `;

	const COMPONENT_NAME = 'title-date';

	@handler({
		bindingName: COMPONENT_NAME,
		validatable: true,
		virtual: false
	})
	export class TitleDateComponentBindingHandler implements KnockoutBindingHandler {
		init(element: any, valueAccessor: () => any, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: any, bindingContext: KnockoutBindingContext): void | { controlsDescendantBindings: boolean; } {
			const name = COMPONENT_NAME;
			const dateRange = valueAccessor();
			const mode = allBindingsAccessor.get('mode');
			const params = { mode, dateRange };
			const component = { name, params };

			element.classList.add('cf');
			element.classList.add('title-date');

			ko.applyBindingsToNode(element, { component }, bindingContext);

			return { controlsDescendantBindings: true };
		}
	}

	const API = {
		BASE_DATE: '/screen/ksu/ksu002/getListOfPeriodsClose'
	};

	@component({
		name: COMPONENT_NAME,
		template
	})
	export class TitleDateComponent extends ko.ViewModel {
		public yearMonth: KnockoutObservable<string> = ko.observable(moment().format('YYYYMM'));

		public selectedRangeIndex: KnockoutObservable<number> = ko.observable(1);

		public dateRanges: KnockoutObservableArray<DateOption> = ko.observableArray([]);

		constructor(private params: Params) {
			super();

			const vm = this;
			const baseD = moment();
			const begin = baseD.clone().startOf('month').toDate();
			const finish = baseD.clone().endOf('month').toDate();

			if (!params) {
				vm.params = {
					dateRange: ko.observable({ begin, finish }),
					mode: ko.observable(1)
				};
			}

			const { mode, dateRange } = params;

			if (!ko.unwrap(mode) && !ko.isObservable(mode)) {
				vm.params.mode = ko.observable(ACHIEVEMENT.HIDE);
			}

			if (!ko.unwrap(dateRange) && !ko.isObservable(dateRange)) {
				vm.params.dateRange = ko.observable({ begin, finish });
			}
		}

		created() {
			const vm = this;
			const proccesPeriod = (response: Period) => {
				const MD_FORMAT = 'MM/DD';
				const YMD_FORMAT = 'YYYY/MM/DD';
				const oid = ko.unwrap(vm.selectedRangeIndex);

				if (response) {
					const { yearMonth, periodsClose } = response;

					// vm.dateRanges([]);
					vm.yearMonth(`${yearMonth}`);

					if (periodsClose && periodsClose.length) {
						vm.dateRanges(periodsClose
							.map((m, id) => {
								const mb = moment.utc(m.startDate, YMD_FORMAT);
								const me = moment.utc(m.endDate, YMD_FORMAT);

								return {
									id: id + 1,
									title: `${m.closureName}${vm.$i18n('KSU002_7')}${mb.format(MD_FORMAT)}${vm.$i18n('KSU002_5')}${me.format(MD_FORMAT)}`,
									begin: mb.toDate(),
									finish: me.toDate()
								};
							}));

						vm.$nextTick(() => {
							if (ko.unwrap(vm.selectedRangeIndex) === oid) {
								vm.selectedRangeIndex.valueHasMutated();
							}
						});
					}
				}
			};

			vm.$ajax('at', API.BASE_DATE).then(proccesPeriod);

			vm.yearMonth
				.subscribe((ym: string) => {
					const cmd = { yearMonth: Number(ym) };

					vm.$ajax('at', API.BASE_DATE, cmd).then(proccesPeriod);
				});

			vm.selectedRangeIndex
				.subscribe(c => {
					if (!!c && c > -1) {
						const dateRanges = ko.unwrap(vm.dateRanges);

						const exist = _.find(dateRanges, (f) => f.id === c);

						if (exist) {
							const { finish, begin } = exist;

							vm.params.dateRange({ finish, begin });
						}
					}
				});
		}
	}

	interface Params {
		dateRange: KnockoutObservable<c.DateRange | null>;
		mode: KnockoutObservable<ACHIEVEMENT>;
	}

	interface DateOption extends c.DateRange {
		id: number;
		title: string;
	}

	interface Closure {
		closureName: string;
		endDate: string;
		startDate: string;
	}

	interface Period {
		periodsClose: Closure[];
		yearMonth: number;
	}

	enum ACHIEVEMENT {
		SHOW = 1,
		HIDE = 0
	}
}
