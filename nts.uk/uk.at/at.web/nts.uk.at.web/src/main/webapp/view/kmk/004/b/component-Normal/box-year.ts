/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.b {

    interface Params {
        selectedYear: KnockoutObservable<number | null>;
        param: KnockoutObservable<string>;
        type: SIDEBAR_TYPE
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
        public param: KnockoutObservable<string> = ko.observable('');
        public type: SIDEBAR_TYPE;

        created(params: Params) {
            const vm = this;

            vm.selectedYear = params.selectedYear;
            vm.param = params.param;
            vm.type = params.type;

        }

        mounted() {
            const vm = this;

            vm.param
                .subscribe(() => {
                    vm.reloadData(0);
                });

            vm.param.valueHasMutated();
        }

        reloadData(selectedIndex: number = 0) {
            const vm = this;
            vm.itemList([]);
            switch (vm.type) {
                case 'Com_Company':
                    vm.$ajax(API.GET_YEARS_COM)
                        .then((data: any) => {
                            _.forEach(data, ((value: any) => {
                                const y: IYear = { statusValue: '', year: value.year, nameYear: value.year + '年度' };
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
                    break
                case 'Com_Workplace':
                    if (ko.unwrap(vm.param) != '') {
                        vm.$ajax(API.GET_YEARS_WORKPLACE + '/' + ko.unwrap(vm.param))
                            .then((data: any) => {
                                _.forEach(data, ((value: any) => {
                                    const y: IYear = { statusValue: '', year: value.year, nameYear: value.year + '年度' };
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
                    break
                case 'Com_Employment':
                    if (ko.unwrap(vm.param) != '') {
                        vm.$ajax(API.GET_YEARS_EMPLOYMENT + '/' + ko.unwrap(vm.param))
                            .then((data: any) => {
                                _.forEach(data, ((value: any) => {
                                    const y: IYear = { statusValue: '', year: value.year, nameYear: value.year + '年度' };
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
                    break
                case 'Com_Person':
                    if (ko.unwrap(vm.param) != null && ko.unwrap(vm.param) != '') {
                        vm.$ajax(API.GET_YEARS_EMPLOYEE + '/' +  ko.unwrap(vm.param))
                            .then((data: any) => {
                                _.forEach(data, ((value: any) => {
                                    const y: IYear = { statusValue: '', year: value.year, nameYear: value.year + '年度' };
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
