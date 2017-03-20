module nts.uk.pr.view.qmm005.e.viewmodel {
    export class viewModel {
        select001: any;
        selectedCode: any;
        selectedValue: any;
        constructor() {
            this.selectedCode = ko.observable(1);
            this.selectedValue = ko.observable(['1']);
            this.select001 = ko.observableArray([
                    new SelectItem(1, '1'),
                    new SelectItem(2, '2'),
                    new SelectItem(3, '3'),
                    new SelectItem(4, '4'),
                    new SelectItem(5, '5'),
                    new SelectItem(6, '6'),
                    new SelectItem(7, '7'),
                    new SelectItem(8, '8'),
                    new SelectItem(9, '9'),
                    new SelectItem(10, '10'),
                    new SelectItem(11, '11'),
                    new SelectItem(12, '12')
                ]);
        }
    }
    
    class SelectItem {
        constructor(public index: number, public label: string) {
        }
    }
}