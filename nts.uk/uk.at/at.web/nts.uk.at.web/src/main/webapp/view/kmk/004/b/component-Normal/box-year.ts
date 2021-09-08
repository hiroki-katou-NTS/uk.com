/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.b {

    interface Params {
        selectedYear: KnockoutObservable<number | null>;
        type: SIDEBAR_TYPE
        years: KnockoutObservableArray<IYear>;
        selectId?: KnockoutObservable<string>;
    }

    const API = {
        GET_YEARS_COM: 'screen/at/kmk004/viewB/com/getListYear',
        GET_YEARS_WORKPLACE: 'screen/at/kmk004/viewC/workPlace/getListYear',
        GET_YEARS_EMPLOYMENT: 'screen/at/kmk004/viewD/employment/getListYear',
        GET_YEARS_EMPLOYEE: 'screen/at/kmk004/viewE/employee/getListYear'
    };
    export type SIDEBAR_TYPE = null | 'Com_Company' | 'Com_Workplace' | 'Com_Employment' | 'Com_Person';

    const template = `
        <div tabindex="6" class="listbox">
            <div id="year-list" data-bind="ntsListBox: {
                options: itemList,
                optionsValue: 'year',
                optionsText: 'nameYear',
                multiple: false,
                value: selectedYear,
                rows: 5,
                columns: [
                    { key: 'isChange', length: 1 },
                    { key: 'nameYear', length: 4 }
                ]}"></div>
        </div>
        <div class="node" data-bind="i18n: 'KMK004_212'"></div>
    `;

    @component({
        name: 'box-year',
        template
    })

    class BoxYear extends ko.ViewModel {

        public itemList: KnockoutObservableArray<IYear> = ko.observableArray([]);
        public selectedYear: KnockoutObservable<number | null> = ko.observable(null);
        public type: SIDEBAR_TYPE;
        public selectId: KnockoutObservable<string> = ko.observable('');

        created(params: Params) {
            const vm = this;
            vm.selectedYear = params.selectedYear;
            if (params.selectId){
                vm.selectId = params.selectId;
            }
            vm.type = params.type;
            vm.itemList = params.years;

            vm.reloadData(0);
        }

        mounted() {
            const vm = this;
            
            vm.selectId
                .subscribe(() => {
                    vm.selectedYear(null);
                    vm.reloadData(0);
                });
        }

        reloadData(selectedIndex: number = 0) {
            const vm = this;
            // vm.itemList([]);
            switch (vm.type) {
                case 'Com_Company':
                    vm.$ajax(API.GET_YEARS_COM)
                        .then((data: any) => {
                            data = _.orderBy(data, ['year'], ['desc']);
                            const years: IYear[] = [];
                            _.forEach(data, ((value: any) => {
                                const y: IYear = new IYear(value.year);
                                years.push(y);
                            }));
                            vm.itemList(years);
                        })
                        .then(() => {
                            if (ko.unwrap(vm.itemList) != []) {
                                vm.selectedYear(ko.unwrap(vm.itemList)[selectedIndex].year);
                            } else {
                                vm.selectedYear(null);
                            }
                        });
                    break
                case 'Com_Workplace':
                    if (ko.unwrap(vm.selectId) != '') {
                        vm.$ajax(API.GET_YEARS_WORKPLACE + '/' + ko.unwrap(vm.selectId))
                            .then((data: any) => {
                                data = _.orderBy(data, ['year'], ['desc']);
                                const years: IYear[] = [];
                                _.forEach(data, ((value: any) => {
                                    const y: IYear = new IYear(value.year);
                                    years.push(y);
                                }));
                                vm.itemList(years);
                            })
                            .then(() => {
                                if (ko.unwrap(vm.itemList) != []) {
                                    if (ko.unwrap(vm.itemList)[selectedIndex]) {
                                        vm.selectedYear(ko.unwrap(vm.itemList)[selectedIndex].year);
                                    } else {
                                        vm.selectedYear(null);
                                    }
                                } else {
                                    vm.selectedYear(null);
                                }
                            });
                    }
                    break
                case 'Com_Employment':
                    if (ko.unwrap(vm.selectId) != '') {
                        vm.$ajax(API.GET_YEARS_EMPLOYMENT + '/' + ko.unwrap(vm.selectId))
                            .then((data: any) => {
                                data = _.orderBy(data, ['year'], ['desc']);
                                const years: IYear[] = [];
                                _.forEach(data, ((value: any) => {
                                    const y: IYear = new IYear(value.year);
                                    years.push(y);
                                }));
                                vm.itemList(years);
                            })
                            .then(() => {
                                if (ko.unwrap(vm.itemList) != []) {
                                    vm.selectedYear(ko.unwrap(vm.itemList)[selectedIndex].year);
                                } else {
                                    vm.selectedYear(null);
                                }
                            });
                    }
                    break
                case 'Com_Person':
                    if (ko.unwrap(vm.selectId) != null && ko.unwrap(vm.selectId) != '') {
                        vm.$ajax(API.GET_YEARS_EMPLOYEE + '/' + ko.unwrap(vm.selectId))
                            .then((data: any) => {
                                data = _.orderBy(data, ['year'], ['desc']);
                                const years: IYear[] = [];
                                _.forEach(data, ((value: any) => {
                                    const y: IYear = new IYear(value.year);
                                    years.push(y);
                                }));
                                vm.itemList(years);
                            })
                            .then(() => {
                                if (ko.unwrap(vm.itemList) != []) {
                                    if (ko.unwrap(vm.itemList)[selectedIndex]) {
                                        vm.selectedYear(ko.unwrap(vm.itemList)[selectedIndex].year);
                                    } else {
                                        vm.selectedYear(null);
                                    }
                                } else {
                                    vm.selectedYear(null);
                                }
                            });
                    }
                    break
            }
        }
    }

    export class IYear {
        isNew: boolean = false;
        isChange: string;
        year: number;
        nameYear: string;

        constructor(year: number, isNew?: boolean) {
            this.year = year;
            this.nameYear = year.toString() + '年度';
            if (isNew) {
                this.isNew = isNew;
                this.isChange = '＊';
            }
        }
    }
}
