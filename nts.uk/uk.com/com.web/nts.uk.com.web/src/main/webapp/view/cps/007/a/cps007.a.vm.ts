module cps007.a.vm {
    let __viewContext: any = window['__viewContext'] || {};
    export class ViewModel {
        layout: KnockoutObservable<Layout> = ko.observable(new Layout({ layoutID: '', layoutCode: '', layoutName: '' }));
        showMode: KnockoutObservable<number> = ko.observable(0);
        category: KnockoutObservable<string> = ko.observable('');
        items: KnockoutObservableArray<IItemClassification> = ko.observableArray([]);
        item: KnockoutObservable<any> = ko.observable(undefined);
        editable: KnockoutObservable<boolean> = ko.observable(true);
        constructor() {
            let self = this;

            for (let i = 1; i < 10; i++) {
                self.items.push({ cid: i.toString(), cname: '000' + i, typeID: 1, dispOrder: 1 });
            }
        }

        start() {
        }

        addLine() {
            let self = this,
                items = self.items();

            // add new line to list item
            if (!_.last(items).typeID) {
                self.items.push({ cid: (items.length + 1).toString(), cname: '000' + items.length + 1, typeID: 1, dispOrder: 1 });
            }
        }

        removeItem(item) {
            let self = this,
                items = self.items();

            self.items.remove(x => x.cid == item.cid);
            self.items.valueHasMutated();
        }

        preventStopSort(data, evt, ui) {
            let item = data.item,
                index = data.targetIndex,
                source = data.targetParent();

            if (item.typeID == source[index].typeID) {
                data.cancelDrop = true;
            }
        }

        showDialogB() {
            nts.uk.ui.windows.sub.modal('../b/index.xhtml');
        }
    }

    interface IItemDefinition {
        catId: string;
        id: string;
        name: string;
        code: string;
        sysReq: boolean;
        reqChang: boolean;
        isFixed: boolean;
        typeState: number;
    }

    interface IPersonInfoCategory {
        catId: string;
        code: string;
        name: string;
        isUsed?: boolean;
        isFixed: boolean;
        empType: number;
        parrentId: string;
    }

    interface IItemClassification {
        cid: string;
        cname: string;
        dispOrder: number;
        typeID: number;
        itemsDefinition?: Array<IItemDefinition>;
    }

    interface ILayout {
        layoutID: string;
        layoutCode: string;
        layoutName: string;
        itemsClassification?: Array<IItemClassification>;
    }

    class Layout {
        layoutID: KnockoutObservable<string> = ko.observable('');
        layoutCode: KnockoutObservable<string> = ko.observable('');
        layoutName: KnockoutObservable<string> = ko.observable('');
        itemsClassification: KnockoutObservableArray<IItemClassification> = ko.observableArray([]);

        constructor(param: ILayout) {
            let self = this;

            self.layoutID(param.layoutID);
            self.layoutCode(param.layoutCode);
            self.layoutName(param.layoutName);

            // replace x by class that implement this interface
            self.itemsClassification(param.itemsClassification || []);
        }
    }
}