/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.b {

    interface Params {
        selectedYear: KnockoutObservable<number | null>;
        change: KnockoutObservable<boolean>;
        type: SIDEBAR_TYPE
    }

    const API = {
        GET_YEARS_COM: 'screen/at/kmk004/viewB/com/getListYear',
        GET_YEARS_WORKPLACE: 'screen/at/kmk004/viewB/workPlace/getListYear',
        GET_YEARS_EMPLOYMENT: 'screen/at/kmk004/viewB/employment/getListYear',
        GET_YEARS_EMPLOYEE: 'screen/at/kmk004/viewB/employee/getListYear'
    };
    export type SIDEBAR_TYPE = null | 'Com_Company' | 'Com_Workplace' | 'Com_Employment' | 'Com_Person';

    const template = `
        <div tabindex="6" class="listbox">
            <div id="list-box" data-bind="ntsListBox: {
                options: itemList,
                optionsValue: 'year',
                optionsText: 'year',
                multiple: false,
                value: selectedYear,
                rows: 5,
                columns: [
                    { key: 'statusValue', length: 1 },
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
        public change: KnockoutObservable<boolean> = ko.observable(true);
        public type: SIDEBAR_TYPE;

        created(params: Params) {
            const vm = this;

            vm.selectedYear = params.selectedYear;
            vm.change = params.change;
            vm.type = params.type;

            vm.reloadData(0);

        }

        reloadData(selectedIndex: number = 0) {
            const vm = this;

            switch (vm.type) {
                case 'Com_Company':
                    vm.$ajax(API.GET_YEARS_COM + "/0")
                        .then((data: any) => {
                            _.forEach(data, ((value: any) => {
                                const y: IYear = { statusValue: '', year: value.year, nameYear: value.year + '年度' };
                                vm.itemList.push(y);
                            }));
                        })
                        .then(() => {
                            vm.selectedYear(ko.unwrap(vm.itemList)[selectedIndex].year);
                        });
                    break
                case 'Com_Workplace':
                    vm.$ajax(API.GET_YEARS_WORKPLACE + "/0")
                        .then((data: any) => {
                            _.forEach(data, ((value: any) => {
                                const y: IYear = { statusValue: '', year: value.year, nameYear: value.year + '年度' };
                                vm.itemList.push(y);
                            }));
                        })
                        .then(() => {
                            vm.selectedYear(ko.unwrap(vm.itemList)[selectedIndex].year);
                        });
                    break
                case 'Com_Employment':
                    vm.$ajax(API.GET_YEARS_EMPLOYMENT + "/0")
                        .then((data: any) => {
                            _.forEach(data, ((value: any) => {
                                const y: IYear = { statusValue: '', year: value.year, nameYear: value.year + '年度' };
                                vm.itemList.push(y);
                            }));
                        })
                        .then(() => {
                            vm.selectedYear(ko.unwrap(vm.itemList)[selectedIndex].year);
                        });
                    break
                case 'Com_Person':
                    vm.$ajax(API.GET_YEARS_EMPLOYEE + "/0")
                        .then((data: any) => {
                            _.forEach(data, ((value: any) => {
                                const y: IYear = { statusValue: '', year: value.year, nameYear: value.year + '年度' };
                                vm.itemList.push(y);
                            }));
                        })
                        .then(() => {
                            vm.selectedYear(ko.unwrap(vm.itemList)[selectedIndex].year);
                        });
                    break
            }
        }
    }

    export interface IYear {
        statusValue: string;
        year: number;
        nameYear: string;
    }

    // export class Year {
    //     status: KnockoutObservable<boolean> = ko.observable(true);
    //     statusValue: KnockoutObservable<string> = ko.observable('');
    //     value: KnockoutObservable<string> = ko.observable('');

    //     constructor(param?: IYear) {
    //         const md = this;
    //         md.create(param);
    //     }

    //     public create(params?: IYear) {
    //         const self = this;

    //         if (params) {
    //             self.status(params.status);
    //             self.value(params.value);
    //             if(params.status) {
    //                 self.statusValue('*');
    //             }
    //         }
    //     }
    // }
}
