module nts.uk.com.view.cps016.a.viewmodel {
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import textUK = nts.uk.text;
    import block = nts.uk.ui.block;
    export class ScreenModel {
        listItems: KnockoutObservableArray<ISelectionItem> = ko.observableArray([]);
        //listItems: KnockoutObservableArray<SelectionItem> = ko.observableArray([]); //Chua phan biet duoc ->Suport:
        selectedItemId: KnockoutObservable<String> = ko.observable('');
        currentItem: KnockoutObservable<SelectionItem> = ko.observable(new SelectionItem({ selectionItemId: '', selectionItemName: '' }));
        
       
        item: KnockoutObservable<Item> = ko.observable(new Item({ id: '', name: '' }));
        rulesFirst: KnockoutObservableArray<IRule> = ko.observableArray([]);
        
        constructor() {
            let self = this,
                item: Item = self.item(),
                classs = self.rulesFirst;

            classs([
                { id: 1, name: "数値型" },
                { id: 2, name: "英数型" }
            ]);

            item.id.subscribe(x => {
                if (x) {
                    service.getItem(x).done((_item: IItem) => {
                        if (_item) {
                            /*
                            item.name(_item.name);
                            item.numberCodeDigits(_item.numberCodeDigits);
                            item.numberDigits(_item.numberDigits);
                            item.numberExternalCodeDigits(_item.numberExternalCodeDigits);
                            item.integrationCode(_item.integrationCode);
                            item.share(_item.share);
                            item.notes(_item.notes);
                            item.enable(_item.enable);
                            */
                        }
                    });
                }
            });
            //item.id(1);
        }

        start(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            // listItems: KnockoutObservableArray<IItem> = self.listItems;
            // listItems.removeAll();


            //            service.getItems().done((_items: Array<IItem>) => {
            //                if (_items && _items.length) {
            //                    _items.forEach(x => listItems.push(x));
            //}
            //            });
            self.listItems.removeAll();

            /*
            service.getAllSelectionItems().done(
                function(itemList: Array<ISelectionItem>) {
                    for (let item of itemList) {
                        self.listItems().push(new SelectionItem(item));
                    }
                    dfd.resolve();
                }
            ).fail(function() {
                dfd.resolve();
            });

            return dfd.promise();
            */

            service.getAllSelectionItems().done((itemList: Array<ISelectionItem>) => {
                if (itemList && itemList.length) {
                    itemList.forEach(x => self.listItems.push(x));
                } dfd.resolve();
            });
            return dfd.resolve();
            
        }

        register() {
            let self = this,
                item: Item = self.item();

            item.name('');
            item.numberCodeDigits(1);
            item.numberDigits('');
            item.numberExternalCodeDigits('');
            item.integrationCode('');
            item.share('');
            item.notes('');
            item.enable('');

        }

        add() {
            let self = this,
                item: Item = self.item(),
                listItems: Array<Item> = self.listItems(),
                _item: IItem = ko.toJS(item);

            self.listItems.push(_item);
            self.listItems.valueHasMutated();
        }

        remove() {
            let self = this,
                item: Item = self.item(),
                listItems: Array<Item> = self.listItems(),
                _id = _.find(listItems, x => x.id == item.id());

            if (!_id) {
                alert('ko co item de delete!!');
            } else {
                var cmt = confirm("You want to delete?");
                if (cmt == true) {
                    _.remove(listItems, x => x.id == item.id());
                    self.register();
                    self.listItems.valueHasMutated();
                }
            }
        }
    }

    interface IItem {
        id: number;
        code?: string;
        name: string;
        numberCodeDigits: number;
        numberDigits: number;
        numberExternalCodeDigits: number;
        integrationCode: number;
        notes: string;
        share: number;
        checked: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;
    }

    class Item {
        id: KnockoutObservable<number> = ko.observable('');
        code: KnockoutObservable<string> = ko.observable('');
        name: KnockoutObservable<string> = ko.observable('');
        numberCodeDigits: KnockoutObservable<number> = ko.observable(1);
        numberDigits: KnockoutObservable<number> = ko.observable(1);
        numberExternalCodeDigits: KnockoutObservable<number> = ko.observable(1);
        integrationCode: KnockoutObservable<number> = ko.observable(1);
        share: KnockoutObservable<number> = ko.observable(1);
        notes: KnockoutObservable<string> = ko.observable('');
        checked: KnockoutObservable<boolean> = ko.observable(true);
        enable: KnockoutObservable<boolean> = ko.observable(true);

        constructor(param: IItem) {
            let self = this;
            self.id(param.id);
            self.code(param.code || '');
            self.name(param.name || '');
            self.numberCodeDigits(param.numberCodeDigits || '');
            self.numberDigits(param.numberDigits || '');
            self.numberExternalCodeDigits(param.numberExternalCodeDigits || '');
            self.integrationCode(param.integrationCode || '');
            self.notes(param.notes || '');
            self.checked(param.checked);
            self.enable(param.enable);
            self.share(param.share);
        }
    }



    interface ISelectionItem {
        selectionItemId: string;
        selectionItemName: string;
        Memo: string;
        selectionItemClassification: number;
        contractCode: string;
        integrationCode: string;
        formatSelection: any;
    }

    class SelectionItem {
        selectionItemId: string;
        selectionItemName: string;
        Memo: string;
        selectionItemClassification: number;
        contractCode: string;
        integrationCode: string;
        formatSelection: any;
        constructor(param: ISelectionItem) {
            this.selectionItemId = param.selectionItemId;
            this.selectionItemName = param.selectionItemName;
            this.Memo = param.Memo;
            this.selectionItemClassification = param.selectionItemClassification;
            this.contractCode = param.contractCode;
            this.integrationCode = param.integrationCode;
            this.formatSelection = param.formatSelection;
        }
    }





    interface IRule {
        id: number;
        name: string;
    }
}