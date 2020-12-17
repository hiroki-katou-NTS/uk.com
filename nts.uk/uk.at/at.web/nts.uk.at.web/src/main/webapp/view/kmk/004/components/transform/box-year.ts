/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.components.transform {

	import SIDEBAR_TYPE = nts.uk.at.view.kmk004.p.SIDEBAR_TYPE;
	import KMK004_API = nts.uk.at.view.kmk004.KMK004_API;

	interface Params {
		selectedYear: KnockoutObservable<number | null>;
		param: KnockoutObservable<string>;
		type: SIDEBAR_TYPE
		years: KnockoutObservableArray<IYear>;
	}

	const API = {
		GET_YEARS_COM: 'screen/at/kmk004/viewL/getListYear',
		GET_YEARS_WORKPLACE: 'screen/at/kmk004/viewM/getListYear',
		GET_YEARS_EMPLOYMENT: 'screen/at/kmk004/viewN/getListYear',
		GET_YEARS_EMPLOYEE: 'screen/at/kmk004/viewO/getListYear'
	};

	const template = `
		<button id = "btn_year" data-bind="click: openQDialog, i18n: 'KMK004_233'"></button>
        <div tabindex="6" class="listbox">
            <div id="list-box" data-bind="ntsListBox: {
                options: itemList,
                optionsValue: 'year',
                optionsText: 'yearName',
                multiple: false,
                value: selectedYear,
                rows: 5,
                columns: [
                    { key: 'statusValue', length: 1 },
                    { key: 'yearName', length: 4 }
                ]}"></div>
        </div>
        <div class="note color-attendance" data-bind="i18n: 'KMK004_212'"></div>
    `;

	@component({
		name: 'box-year',
		template
	})

	class BoxYear extends ko.ViewModel {

		public itemList: KnockoutObservableArray<IYear> = ko.observableArray([]);
		public selectedYear: KnockoutObservable<number | null> = ko.observable(null);
		public param: KnockoutObservable<string> = ko.observable('');
		public type: SIDEBAR_TYPE;
		public years: KnockoutObservableArray<IYear> = ko.observableArray([]);
		
		created(params: Params) {
			const vm = this;

			vm.selectedYear = params.selectedYear;
			vm.param = params.param;
			vm.type = params.type;
			vm.itemList = params.years;
		}

		mounted() {
			const vm = this;
			vm.initData(0);
			vm.param
				.subscribe(() => {
					vm.loadData(0);
				});

			vm.param.valueHasMutated();
		}

		initData(selectedIndex: number = 0) {
			
            const vm = this;
            vm.itemList([]);
            switch (vm.type) {
                case 'Com_Company':
                    vm.$ajax(KMK004_API.COM_INIT_SCREEN)
                        .then((data: any) => {
                            var years = _.orderBy(data.years, ['year'], ['desc']);

                            _.forEach(years, ((value: any) => {
                                const y: IYear = new IYear(value.year);
                                vm.itemList.push(y);
                            }));
                        })
                        .then(() => {
                            if(ko.unwrap(vm.itemList) != []){
                                vm.selectedYear(ko.unwrap(vm.itemList)[selectedIndex].year);
                            }else {
                                vm.selectedYear(null);
                            }
                        });
                    break;

                case 'Com_Workplace':
                    if (ko.unwrap(vm.param) != '') {
                        vm.$ajax(KMK004_API.WKP_INIT_SCREEN + '/' + ko.toJS(vm.param()))
                            .then((data: any) => {
                                var years = _.orderBy(data.years, ['year'], ['desc']);
                                _.forEach(years, ((value: any) => {
                                    const y: IYear = new IYear(value.year);
                                    vm.itemList.push(y);
                                }));
                            })
                            .then(() => {
                                if(ko.unwrap(vm.itemList) != []){
                                    vm.selectedYear(ko.unwrap(vm.itemList)[selectedIndex].year);
                                }else {
                                    vm.selectedYear(null);
                                }
                            });
                    }
                    break;

                case 'Com_Employment':
                    if (ko.unwrap(vm.param) != '') {
                        vm.$ajax(KMK004_API.EMP_INIT_SCREEN  + '/' + ko.toJS(vm.param()))
                            .then((data: any) => {
                                  var years = _.orderBy(data.years, ['year'], ['desc']);
                                _.forEach(years, ((value: any) => {
                                    const y: IYear = new IYear(value.year);
                                    vm.itemList.push(y);
                                }));
                            })
                            .then(() => {
                                if(ko.unwrap(vm.itemList) != []){
                                    vm.selectedYear(ko.unwrap(vm.itemList)[selectedIndex].year);
                                }else {
                                    vm.selectedYear(null);
                                }
                            });
                    }
                    break;

                case 'Com_Person':
                    if (ko.unwrap(vm.param) != null && ko.unwrap(vm.param) != '') {
                        vm.$ajax(KMK004_API.SHA_SELECT + '/' + ko.toJS(vm.param()))
                            .then((data: any) => {
                                var years = _.orderBy(data.years, ['year'], ['desc']);
                                _.forEach(years, ((value: any) => {
                                    const y: IYear = new IYear(value.year);
                                    vm.itemList.push(y);
                                }));
                            })
                            .then(() => {
                                
                                if(ko.unwrap(vm.itemList) != []){
                                    vm.selectedYear(ko.unwrap(vm.itemList)[selectedIndex].year);
                                }else {
                                    vm.selectedYear(null);
                                }
                            });
                    }
                    break;
            }
        
		}
		
		loadData(selectedIndex: number = 0) {
			
            const vm = this;
            vm.itemList([]);
            switch (vm.type) {
                case 'Com_Company':
                    break;

                case 'Com_Workplace':
                    if (ko.unwrap(vm.param) != '') {
                        vm.$ajax(KMK004_API.WKP_SELECT + '/' + ko.toJS(vm.param()))
                            .then((data: any) => {
                                var years = _.orderBy(data.years, ['year'], ['desc']);
                                _.forEach(years, ((value: any) => {
                                    const y: IYear = new IYear(value.year);
                                    vm.itemList.push(y);
                                }));
                            })
                            .then(() => {
                                if(ko.unwrap(vm.itemList) != []){
                                    vm.selectedYear(ko.unwrap(vm.itemList)[selectedIndex].year);
                                }else {
                                    vm.selectedYear(null);
                                }
                            });
                    }
                    break;

                case 'Com_Employment':
                    if (ko.unwrap(vm.param) != '') {
                        vm.$ajax(KMK004_API.EMP_SELECT  + '/' + ko.toJS(vm.param()))
                            .then((data: any) => {
                                  var years = _.orderBy(data.years, ['year'], ['desc']);
                                _.forEach(years, ((value: any) => {
                                    const y: IYear = new IYear(value.year);
                                    vm.itemList.push(y);
                                }));
                            })
                            .then(() => {
                                if(ko.unwrap(vm.itemList) != []){
                                    vm.selectedYear(ko.unwrap(vm.itemList)[selectedIndex].year);
                                }else {
                                    vm.selectedYear(null);
                                }
                            });
                    }
                    break;

                case 'Com_Person':
                    if (ko.unwrap(vm.param) != null && ko.unwrap(vm.param) != '') {
                        vm.$ajax(KMK004_API.SHA_SELECT + '/' + ko.toJS(vm.param()))
                            .then((data: any) => {
                                var years = _.orderBy(data.years, ['year'], ['desc']);
                                _.forEach(years, ((value: any) => {
                                    const y: IYear = new IYear(value.year);
                                    vm.itemList.push(y);
                                }));
                            })
                            .then(() => {
                                
                                if(ko.unwrap(vm.itemList) != []){
                                    vm.selectedYear(ko.unwrap(vm.itemList)[selectedIndex].year);
                                }else {
                                    vm.selectedYear(null);
                                }
                            });
                    }
                    break;
            }
        
		}

		openQDialog() {
			const vm = this;
			const param = {years: ko.unwrap(vm.itemList).map((m: IYear) => m.year)};
			vm.$window.modal('/view/kmk/004/q/index.xhtml', param).then((result) => {
				if (result) {
					vm.years.push(new IYear(parseInt(result.year), true));
					vm.years(_.orderBy(ko.unwrap(vm.years), ['year'], ['desc']));
					vm.selectedYear(ko.unwrap(vm.years)[0].year);
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
