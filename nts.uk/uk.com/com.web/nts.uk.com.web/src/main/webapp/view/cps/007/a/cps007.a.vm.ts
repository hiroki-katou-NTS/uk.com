module cps007.a.vm {
    let __viewContext: any = window['__viewContext'] || {};
    export class ViewModel {
        layout: KnockoutObservable<Layout> = ko.observable(new Layout({ layoutID: '', layoutCode: '', layoutName: '' }));
        constructor() {
            let self = this,
                layout = self.layout();

            /*for (let i = 1; i < 10; i++) {
                layout.itemsClassification.push({ id: 'ID' + i, code: 'COD' + i, name: 'Name ' + i, typeId: 0, dispOrder: 1 });
            }*/
        }

        start() {
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

    interface IItemClassification {
        id: string;
        code: string;
        name: string;
        dispOrder: number;
        typeId: number;
        itemsDefinition?: Array<IItemDefinition>;
    }

    interface ILayout {
        layoutID: string;
        layoutCode: string;
        layoutName: string;
        editable?: boolean;
        itemsClassification?: Array<IItemClassification>;
    }

    class Layout {
        layoutID: KnockoutObservable<string> = ko.observable('');
        layoutCode: KnockoutObservable<string> = ko.observable('');
        layoutName: KnockoutObservable<string> = ko.observable('');
        editable: KnockoutObservable<boolean> = ko.observable(true);
        itemsClassification: KnockoutObservableArray<IItemClassification> = ko.observableArray([]);

        constructor(param: ILayout) {
            let self = this;

            self.layoutID(param.layoutID);
            self.layoutCode(param.layoutCode);
            self.layoutName(param.layoutName);

            if (param.editable != undefined) {
                self.editable(param.editable);
            }

            // replace x by class that implement this interface
            self.itemsClassification(param.itemsClassification || []);
        }
    }
}