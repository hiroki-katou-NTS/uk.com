module cps007.a.vm {
    let __viewContext: any = window['__viewContext'] || {};
    export class ViewModel {
        showMode: KnockoutObservable<number> = ko.observable(0);
        category: KnockoutObservable<string> = ko.observable('');
        items: KnockoutObservableArray<any> = ko.observableArray([]);
        item: KnockoutObservable<any> = ko.observable(undefined);

        constructor() {
            let self = this;

            for (let i = 1; i < 10; i++) {
                self.items.push({ id: i, name: '000' + i, 'type': !!(i % 2) });
            }
        }

        start() {
        }

        addLine() {
            let self = this,
                items = self.items();

            // add new line to list item
            if (!_.last(items).type) {
                self.items.push({ id: items.length + 1, name: '000' + items.length + 1, 'type': true });
            }
        }

        removeItem(item) {
            let self = this,
                items = self.items();

            self.items.remove(x => x.id == item.id);
            self.items.valueHasMutated();
        }

        preventStopSort(data, evt, ui) {
            let item = data.item,
                index = data.targetIndex,
                source = data.targetParent();

            if (item.type == source[index].type) {
                data.cancelDrop = true;
            }
        }

        showDialogB() {
            nts.uk.ui.windows.sub.modal('../b/index.xhtml');
        }
    }

    interface IItemClassification {
        cid: string;
        dispOrder: number;

    }

    interface IItemDefinition {
        
    }
}