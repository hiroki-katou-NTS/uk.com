module nts.uk.com.view.cps017.d.viewmodel {
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import textUK = nts.uk.text;
    import block = nts.uk.ui.block;

    export class ScreenModel {
        Items: KnockoutObservableArray<IItem> = ko.observableArray([]);
        item: KnockoutObservable<Item> = ko.observable(new Item({ id: '', name: '' }));
        date: KnockoutObservable<string>;

        constructor() {
            let self = this,
                item: Item = self.item();
            
            self.date = ko.observable("1990/01/01");
            self.start();
        }

        //
        start() {
            let self = this,
                items: KnockoutObservableArray<IItem> = self.Items;
            
            service.getItems().done((_items: Array<IItem>) => {
                if (_items && _items.length) {
                    _items.forEach(x => items.push(x));
                }
            });
        }
        
        //
        closeDialog(){
            nts.uk.ui.windows.close();    
        }
    }

    //
    class Item {
        id: KnockoutObservable<number> = ko.observable('');
        name: KnockoutObservable<string> = ko.observable('');

        constructor(param: IItem) {
            let self = this;
            self.id(param.id);
            self.name(param.name || '');
        }
    }

    //
    interface IItem {
        id: number;
        name: string;
    }


}
