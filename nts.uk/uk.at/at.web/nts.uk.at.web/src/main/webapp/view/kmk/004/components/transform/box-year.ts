/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.components {

    interface Params {
        selectedYear: KnockoutObservable<number | null>;
        change: KnockoutObservable<boolean>;
    }

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
                    { key: 'year', length: 4 }
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
        public change: KnockoutObservable<boolean> = ko.observable(true);

        created(params: Params) {
            const vm = this;

            vm.selectedYear = params.selectedYear;
            vm.change = params.change;

            vm.reloadData(0);

            vm.change
                .subscribe(() => {
                    ko.unwrap(vm.itemList)[0].statusValue = '＊';
                });
        }

        reloadData(selectedIndex: number = 0) {
            const vm = this;
            const years: IYear[] = [{status: true, statusValue : '＊', year: 2017},
            {status: false, statusValue : '', year: 2016}
            ,{status: true, statusValue : '', year: 2018}
            ,{status: true, statusValue : '', year: 2020}];

            if (years.length > 0) {
                vm.itemList(_.orderBy(years, ['year'], ['desc']));
                vm.selectedYear(ko.unwrap(vm.itemList)[selectedIndex].year);
            } else {
                vm.selectedYear(null);
            }
        }
    }

    export interface IYear {
        status: boolean;
        statusValue: string;
        year: number;
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
