module nts.uk.com.view.cps009.b.viewmodel {
    import error = nts.uk.ui.errors;
    import text = nts.uk.resource.getText;
    import close = nts.uk.ui.windows.close;
    import dialog = nts.uk.ui.dialog;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import block = nts.uk.ui.block;

    export class ViewModel {
        itemInitLst: KnockoutObservableArray<any> = ko.observableArray([]);
        currentIdLst: KnockoutObservableArray<any> = ko.observableArray([]);
        itemColumns: KnockoutObservableArray<any> = ko.observableArray([
            { headerText: 'id', key: 'id', width: 100, hidden: true },
            { headerText: text('CPS009_33'), key: 'itemCode', width: 200 },
            { headerText: 'itemCode', key: 'itemName', width: 150, hidden: true }
        ]);

        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;

        constructor() {

            let self = this;
            self.roundingRules = ko.observableArray([
                { code: '1', name: '四捨五入' },
                { code: '2', name: '切り上げ' },
                { code: '3', name: '切り捨て' }
            ]);
            self.selectedRuleCode = ko.observable(1);

            self.start();
        }

        start() {

            let self = this;
            self.itemInitLst.removeAll();
            for (let i = 0; i < 10; i++) {
                self.itemInitLst.push(new ItemInitValue(
                    {
                        id: i.toString(),
                        itemCode: "000" + i.toString(),
                        itemName: "A"
                    }));
            }
        }
        
        registerItems(){
            close();
        
        }
        
        closeDialog(){
            close();
        }




    }

    export interface IItemInitValue {
        id: string;
        itemCode: string;
        itemName: string;

    }

    export class ItemInitValue {
        id: KnockoutObservable<string>;
        itemCode: string;
        itemName: string;

        constructor(params: IItemInitValue) {

            let self = this;

            self.id = ko.observable(params.id || "");

            self.itemCode = params.itemCode || "";

            self.itemName = params.itemName || "";

        }

        setData(params: any) {

            let self = this;

            self.id(params.id || "");

            self.itemCode = params.itemCode || "";

            self.itemName = params.itemName || "";
        }

    }

}