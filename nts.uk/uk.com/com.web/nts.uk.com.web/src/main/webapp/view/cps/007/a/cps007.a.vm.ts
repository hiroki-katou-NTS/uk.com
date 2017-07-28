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
                self.items.push({ id: i, name: '000' + i });
            }
        }

        start() {
        }

        removeItem(item) {
            let self = this,
                items = self.items();
            
            self.items.remove(x => x.id == item.id);
            self.items.valueHasMutated();
        }

        showDialogB() {
            nts.uk.ui.windows.sub.modal('../b/index.xhtml');
        }
    }
}