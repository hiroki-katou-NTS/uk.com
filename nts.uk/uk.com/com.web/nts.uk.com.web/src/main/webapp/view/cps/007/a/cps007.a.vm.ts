module cps007.a.vm {
    let __viewContext: any = window['__viewContext'] || {};
    export class ViewModel {
        layout: KnockoutObservable<Layout> = ko.observable(new Layout({ id: '', code: '', name: '' }));

        constructor() {
            let self = this,
                layout = self.layout();

            self.start();
        }

        start() {
            let self = this,
                layout = self.layout();

            service.getData().done((x: ILayout) => {
                layout.id(x.id);
                layout.code(x.code);
                layout.name(x.name);
                layout.itemsClassification(x.itemsClassification);
            });
        }

        saveData() {
            let self = this,
                layout: ILayout = ko.toJS(self.layout);

            debugger;
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
        id: string;
        code: string;
        name: string;
        editable?: boolean;
        itemsClassification?: Array<IItemClassification>;
    }

    class Layout {
        id: KnockoutObservable<string> = ko.observable('');
        code: KnockoutObservable<string> = ko.observable('');
        name: KnockoutObservable<string> = ko.observable('');
        editable: KnockoutObservable<boolean> = ko.observable(true);
        itemsClassification: KnockoutObservableArray<IItemClassification> = ko.observableArray([]);

        constructor(param: ILayout) {
            let self = this;

            self.id(param.id);
            self.code(param.code);
            self.name(param.name);

            if (param.editable != undefined) {
                self.editable(param.editable);
            }

            // replace x by class that implement this interface
            self.itemsClassification(param.itemsClassification || []);
        }
    }
}