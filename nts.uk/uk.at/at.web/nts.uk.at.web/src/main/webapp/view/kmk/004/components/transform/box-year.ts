/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.components.transform {

	import SIDEBAR_TYPE = nts.uk.at.view.kmk004.p.SIDEBAR_TYPE;
	import KMK004_API = nts.uk.at.view.kmk004.KMK004_API;

	interface Params {
		selectedYear: KnockoutObservable<number | null>;
		type: SIDEBAR_TYPE;
		selectedId: KnockoutObservable<string>;
		years: KnockoutObservableArray<IYear>;
		startYM: KnockoutObservable<number>;
		isNewYear: KnockoutObservable<boolean>;
	}

	const template = `
		<button id = "btn_year" data-bind="enable: initBtnEnable , click: $component.openQDialog, i18n: 'KMK004_233'"></button>
        <div tabindex="6" class="listbox">
            <div id="year-list" data-bind="ntsListBox: {
                options: $component.params.years,
                optionsValue: 'year',
                optionsText: 'yearName',
                multiple: false,
                value: $component.params.selectedYear,
                rows: 5,
                columns: [
                    { key: 'isChanged', length: 1 },
                    { key: 'yearName', length: 4 }
                ]}"></div>
        </div>
        <div class="note color-attendance" data-bind="i18n: 'KMK004_212'"></div>
    `;

	@component({
		name: 'box-year',
		template
	})
	export class BoxYear extends ko.ViewModel {

		initBtnEnable: KnockoutObservable<boolean> = ko.observable(false);

		constructor(private params: Params) {
			super();
		}

		created() {
			const vm = this;
			const { params } = vm;

			if (params.type !== 'Com_Person') {
				vm.initBtnEnable(true);
			}
		}

		mounted() {
			const vm = this;
			const { params } = vm;
			const { selectedId, type } = params;

			vm.initData(0);

			selectedId
				.subscribe(() => {
					vm.loadData(0)
					if (type == 'Com_Person' && selectedId() != '') {
						vm.initBtnEnable(true);
					}
				});

			selectedId.valueHasMutated();
		}

		initData(selectedIndex: number = 0) {
			const vm = this;
			const { } = vm;
			const { type, years, selectedId, selectedYear } = vm.params

			switch (type) {
				case 'Com_Company':
					vm.$ajax(KMK004_API.COM_INIT_SCREEN)
						.then((data: any) => {
							data = _.orderBy(data.years, ['year'], ['desc']);
							const _years: IYear[] = [];
							_.forEach(data, ((value: any) => {
								const y: IYear = new IYear(value.year);
								_years.push(y);
							}));

							years(_years);
						})
						.then(() => {
							const _years = ko.unwrap(years);

							if (!!_years.length) {
								const exist = _years[selectedIndex];

								selectedYear(exist.year);
							} else {
								selectedYear(null);
							}
						});
					break;

				case 'Com_Workplace':
					if (ko.unwrap(selectedId) != '') {
						vm.$ajax(KMK004_API.WKP_INIT_SCREEN)
							.then((data: any) => {
								data = _.orderBy(data.years, ['year'], ['desc']);
								const _years: IYear[] = [];

								_.forEach(data, ((value: any) => {
									const y: IYear = new IYear(value.year);
									_years.push(y);
								}));

								years(_years);
							})
							.then(() => {
								const _years = ko.unwrap(years);

								if (!!_years.length) {
									const exist = _years[selectedIndex];

									selectedYear(exist.year);
								} else {
									selectedYear(null);
								}
							});
					}
					break;

				case 'Com_Employment':
					if (ko.unwrap(selectedId) != '') {
						vm.$ajax(KMK004_API.EMP_INIT_SCREEN)
							.then((data: any) => {
								data = _.orderBy(data.years, ['year'], ['desc']);
								const _years: IYear[] = [];
								_.forEach(data, ((value: any) => {
									const y: IYear = new IYear(value.year);
									_years.push(y);
								}));

								years(_years);
							})
							.then(() => {
								const _years = ko.unwrap(years);

								if (!!_years.length) {
									const exist = _years[selectedIndex];
									selectedYear(exist.year);
								} else {
									selectedYear(null);
								}
							});
					}
					break;

				case 'Com_Person':
					break;
			}

		}

		loadData(selectedIndex: number = 0) {
			const vm = this;
			const { type, years, selectedId, selectedYear } = vm.params

			switch (type) {
				case 'Com_Company':
					break;

				case 'Com_Workplace':
					if (ko.unwrap(selectedId) != '') {
						vm.$ajax(KMK004_API.WKP_SELECT + '/' + ko.toJS(selectedId))
							.then((data: any) => {
								data = _.orderBy(data.years, ['year'], ['desc']);
								const _years: IYear[] = [];
								_.forEach(data, ((value: any) => {
									const y: IYear = new IYear(value.year);
									_years.push(y);
								}));

								years(_years);
							})
							.then(() => {
								const _years = ko.unwrap(years);

								if (!!_years.length) {
									const exist = _years[selectedIndex];

									selectedYear(exist.year);
								} else {
									selectedYear(null);
								}
							});
					}
					break;

				case 'Com_Employment':
					if (ko.unwrap(selectedId) != '') {
						vm.$ajax(KMK004_API.EMP_SELECT + '/' + ko.toJS(selectedId))
							.then((data: any) => {
								data = _.orderBy(data.years, ['year'], ['desc']);
								const _years: IYear[] = [];
								_.forEach(data, ((value: any) => {
									const y: IYear = new IYear(value.year);
									_years.push(y);
								}));

								years(_years);
							})
							.then(() => {
								const _years = ko.unwrap(years);

								if (!!_years.length) {
									const exist = _years[selectedIndex];

									selectedYear(exist.year);
								} else {
									selectedYear(null);
								}
							});
					}
					break;

				case 'Com_Person':
					if (!!ko.unwrap(selectedId)) {

						selectedYear(null);

						vm.$nextTick(() => {
							vm.$ajax(KMK004_API.SHA_SELECT + '/' + ko.unwrap(selectedId))
								.then((data: any) => {
									data = _.orderBy(data.years, ['year'], ['desc']);
									years([]);

									const _years: IYear[] = [];
									_.forEach(data, ((value: any) => {
										const y: IYear = new IYear(value.year);
										_years.push(y);
									}));

									years(_years);

								})
								.then(() => {
									const _years = ko.unwrap(years);

									if (!!_years.length) {
										const exist = _years[selectedIndex];

										selectedYear(exist.year);
									} else {
										selectedYear(null);
									}
								});
						});
					}
					break;
			}

		}

		openQDialog() {
			const vm = this;
			const { startYM, years, selectedYear, isNewYear } = vm.params;
			const param = { startDate: startYM(), years: ko.unwrap(years).map((m: IYear) => m.year) };

			vm.$window.modal('/view/kmk/004/q/index.xhtml', param).then((result) => {
				if (result) {
					let year = Number(result.year);
					const _years = ko.unwrap(years);
					_years.push(new IYear(parseInt(result.year), true));

					years(_.orderBy(_years, ['year'], ['desc']));

					if (ko.unwrap(selectedYear) !== year) {
						selectedYear(year);
					} else {
						selectedYear.valueHasMutated();
					}

					isNewYear(true);
				}
			});
		}
	}

	export class IYear {
		isNew: boolean = false;
		isChanged: string;
		year: number;
		yearName: string;

		constructor(year: number, isNew?: boolean) {
			this.year = year;
			this.yearName = year.toString() + '年度';
			if (isNew) {
				this.isNew = isNew;
				this.isChanged = '＊';
			}
		}
	}

}
