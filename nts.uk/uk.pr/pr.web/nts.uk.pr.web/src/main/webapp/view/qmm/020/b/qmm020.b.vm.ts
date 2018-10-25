module nts.uk.pr.view.qmm020.b.viewmodel {
    export class ScreenModel {

        listStateCorrelationHis: KnockoutObservableArray<ItemModel> =  ko.observableArray([]);
        currentSelect: KnockoutObservable<any> = ko.observable();
        specCode: KnockoutObservable<string> = ko.observable('TaiTT');
        specName: KnockoutObservable<string> = ko.observable('TaiTT');
        constructor(){
            let self = this;

            for(let i = 1; i < 100; i++) {
                self.listStateCorrelationHis.push(new ItemModel('00' + i, '基本給', i + 'TaiTT'));
            }
        }

    }
    export class ItemModel {
        code: string;
        name: string;
        display: string;
        constructor(code: string, name: string, display: string) {
            this.code = code;
            this.name = name;
            this.display = display;
        }
    }

}