module nts.uk.pr.view.qmm020.c.viewmodel {

    import getText = nts.uk.resource.getText;

    export class ScreenModel {

        listStateCorrelationHis: KnockoutObservableArray<ItemModel> =  ko.observableArray([]);
        items: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        currentSelect: KnockoutObservable<any> = ko.observable();
        currentCodeList: KnockoutObservableArray<any> = ko.observableArray([]);
        columns: KnockoutObservableArray<any>;


        constructor(){
            let self = this;
            for(let i = 1; i < 100; i++) {
                self.items.push(new ItemModel('00' + i, '基本給', '<button>Small</button>' + i + 'TaiTT     ',i + 'TaiTT'));
            }
            this.columns = ko.observableArray([
                { headerText: getText('QMM020_26'), prop: 'empCode', width: 100 },
                { headerText: getText('QMM020_27'), prop: 'empName', width: 150 },
                { headerText: getText('QMM020_20'), prop: 'display1', width: 250 },
                { headerText: getText('QMM020_22'), prop: 'display2', width: 250 },
            ]);
        }

    }
    export  class ItemModel {
        empCode: string;
        empName: string;
        display1: string;
        display2: string;
        constructor(empCode: string, empName: string, display1: string, display2: string) {
            this.empCode = empCode;
            this.empName = empName;
            this.display1 = display1;
            this.display2 = display2;
        }
    }

}