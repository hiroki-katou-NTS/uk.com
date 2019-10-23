module nts.uk.com.view.cps003.c.viewmodel {
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import textUK = nts.uk.text;
    import block = nts.uk.ui.block;
    export class ScreenModel {
        listItems: KnockoutObservableArray<IItem> = ko.observableArray([]);
        item: KnockoutObservable<Item> = ko.observable(new Item({ id: '', name: '' }));

        constructor() {
            let self = this,
                item: Item = self.item();

            item.id.subscribe(x => {
                if (x) {
                    service.getItem(x).done((_item: IItem) => {
                        if (_item) {
                            item.name(_item.name);
                            item.columnSetting(_item.columnSetting);
                            item.notes(_item.notes);
                        }
                    });
                }
            });

            item.id(1);
            self.start();
        }

        start() {
            let self = this,
                listItems: KnockoutObservableArray<IItem> = self.listItems;

            listItems.removeAll();
            service.getItems().done((_items: Array<IItem>) => {
                if (_items && _items.length) {
                    _items.forEach(x => listItems.push(x));
                }
            });
        }
    }

    interface ITem {
        id: number;
        name: string;
        columnSetting: string;
        notes: string;
    }

    class Item {
        id: KnockoutObservable<number> = ko.observable('');
        name: KnockoutObservable<string> = ko.observable('');
        columnSetting: KnockoutObservable<string> = ko.observable('');
        notes: KnockoutObservable<string> = ko.observable('');

        constructor(param: IItem) {
            let self = this;
            self.id(param.id);
            self.name(param.name || '');
            self.columnSetting(param.columnSetting || '');
            self.notes(param.notes || '');
        }
    }
}
