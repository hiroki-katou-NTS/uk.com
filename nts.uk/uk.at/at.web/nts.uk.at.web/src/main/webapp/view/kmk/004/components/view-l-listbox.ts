/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004{
    
    interface Params {
    }

    const template = `
        <div class="listbox">
            <div id="list-box" data-bind="ntsListBox: {
                options: itemList,
                optionsValue: 'value',
                optionsText: 'value',
                multiple: false,
                value: selectedYear,
                enable: false,
                rows: 5,
                columns: [
                    { key: 'statusValue', length: 1 },
                    { key: 'value', length: 4 }
                ]}"></div>
        </div>
        <style type="text/css" rel="stylesheet">
		.listbox {
			padding-right: 10px;
			position: relative;
    		bottom: 114px;
		}
        </style>
        <style type="text/css" rel="stylesheet" data-bind="html: $component.style"></style>
    `;

    @component({
        name: 'view-l-listbox',
        template
    })

    class ListBox extends ko.ViewModel {

        public itemList: KnockoutObservableArray<IListYear> = ko.observableArray([]);
        public selectedYear: KnockoutObservable<string> = ko.observable('');

        created() {
            const vm = this;
            const data: IListYear[] = [{status: true, statusValue : '', value: '2021年度'}
            ,{status: true, statusValue : '*', value: '2020年度'}
            ,{status: false, statusValue : '', value: '2019年度'}
            ,{status: false, statusValue : '', value: '2018年度'}
            ,{status: false, statusValue : '', value: '2017年度'}];

            vm.itemList(_.orderBy(data, ['value'],['desc']));
            
            console.log(vm.selectedYear);
        }

    }

  /*  export interface IListYear {
        status: boolean;
        statusValue: string;
        value: string;
    }*/

    // export class ListYear {
    //     status: KnockoutObservable<boolean> = ko.observable(true);
    //     statusValue: KnockoutObservable<string> = ko.observable('');
    //     value: KnockoutObservable<string> = ko.observable('');

    //     public create(params?: IListYear) {
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
