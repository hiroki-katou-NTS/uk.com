module nts.uk.com.view.cps009.c.viewmodel {
    import error = nts.uk.ui.errors;
    import text = nts.uk.resource.getText;
    import close = nts.uk.ui.windows.close;
    import dialog = nts.uk.ui.dialog;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import block = nts.uk.ui.block;

    export class ViewModel {

        
        currentInitVal: KnockoutObservable<ItemInitValue> = ko.observable(new ItemInitValue(
            {
                id: "",
                itemCode: "",
                itemName: ""
            }));

        isCopy: KnockoutObservable<boolean> = ko.observable(false);
        codeCtg: KnockoutObservable<string> = ko.observable('001');
        nameCtg: KnockoutObservable<string> = ko.observable('Category');
        codeInput: KnockoutObservable<string> = ko.observable('');
        nameInput: KnockoutObservable<string> = ko.observable('');
        constructor() {
            let param = getShared('CPS009C_PARAM') || {id: '', code: '', name: ''};
        }

        copyInitValue() {

            let self = this,
                copyObj = {
                    isCopy: self.isCopy(),
                    itemCode: self.currentInitVal().itemCode(),
                    itemName: self.currentInitVal().itemName()
                }
            console.log(copyObj)
            close();

        }

        cancelCopyInitValue() {

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

        itemCode: KnockoutObservable<string>;

        itemName: KnockoutObservable<string>;

        constructor(params: IItemInitValue) {

            let self = this;

            self.id = ko.observable(params.id || "");

            self.itemCode = ko.observable(params.itemCode || "");

            self.itemName = ko.observable(params.itemName || "");

        }

        setData(params: any) {

            let self = this;

            self.id(params.id || "");

            self.itemCode(params.itemCode || "");

            self.itemName(params.itemName || "");
        }

    }
    export class DataCopy{
        id: string;
        codeNew: string;
        nameNew: string;
        copy: boolean;    
    }
}