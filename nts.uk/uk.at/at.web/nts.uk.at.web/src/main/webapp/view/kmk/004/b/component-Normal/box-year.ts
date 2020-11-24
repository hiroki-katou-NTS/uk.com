/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.b {

    interface Params {
        selectedYear: KnockoutObservable<number | null>;
        change: KnockoutObservable<boolean>;
    }

    const template = `
        <div class="listbox">
            <div id="list-box" data-bind="ntsListBox: {
                options: itemList,
                optionsValue: 'value',
                optionsText: 'value',
                multiple: false,
                value: selectedYear,
                rows: 5,
                columns: [
                    { key: 'statusValue', length: 1 },
                    { key: 'value', length: 2 }
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
            const faceData: IYear[] = [{status: true, statusValue : '＊', value: 2017},
            {status: false, statusValue : '', value: 2016}
            ,{status: true, statusValue : '', value: 2018}
            ,{status: true, statusValue : '', value: 2020}];
            // const faceData: IYear[] = [];

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
