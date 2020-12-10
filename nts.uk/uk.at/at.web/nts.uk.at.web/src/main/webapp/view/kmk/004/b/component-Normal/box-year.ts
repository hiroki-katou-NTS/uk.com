/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.b {

    interface Params {
        selectedYear: KnockoutObservable<number | null>;
        change: KnockoutObservable<boolean>;
        type: KnockoutObservable<string>
    }

    const API = {
        GET_YEARS_COM: 'screen/at/kmk004/viewB/com/getListYear'
    };

    export type SIDEBAR_TYPE = null | 'Com_Company' | 'Com_Workplace' | 'Com_Employment' | 'Com_Person';

    const template = `
        <div tabindex="6" class="listbox">
            <div id="list-box" data-bind="ntsListBox: {
                options: itemList,
                optionsValue: 'value',
                optionsText: 'value',
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
        public type: KnockoutObservable<string> = ko.observable('Com_Company');

        created(params: Params) {
            const vm = this;

            vm.selectedYear = params.selectedYear;
            vm.change = params.change;
            vm.type = params.type;

            vm.reloadData(0);

            vm.change
                .subscribe(() => {
                    ko.unwrap(vm.itemList)[0].statusValue = '＊';
                });
        }

        reloadData(selectedIndex: number = 0) {
            const vm = this;
            const faceData: IYear[] = [];
            // const faceData: IYear[] = [];

            if (ko.unwrap(vm.type) === 'Com_Company'){
                vm.$ajax(API.GET_YEARS_COM + / '0')
                .then((data: IWorkTime[]) => {
                    if (data.length > 0) {
                        const data1: IWorkTime[] = [];
                        var check: boolean = true;

                        if (ko.unwrap(vm.checkEmployee)) {
                            check = false;
                        }
                        data.map(m => {
                            const laborTime: ILaborTime = {
                                legalLaborTime: m.laborTime.legalLaborTime,
                                withinLaborTime: m.laborTime.weekAvgTime,
                                weekAvgTime: m.laborTime.weekAvgTime
                            };
                            const s: IWorkTime = { check: check, yearMonth: m.yearMonth, laborTime: laborTime };
                            data1.push(s);
                        });

                        vm.workTimes(data1.map(m => new WorkTime({ ...m, parrent: vm.workTimes })));
                    }
                });
            }

            if (faceData.length > 0) {
                vm.itemList(_.orderBy(faceData, ['value'], ['desc']));
                vm.selectedYear(ko.unwrap(vm.itemList)[selectedIndex].value);
            } else {
                vm.selectedYear(null);
            }
        }
    }

    export interface IYear {
        status: boolean;
        statusValue: string;
        value: number;
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
