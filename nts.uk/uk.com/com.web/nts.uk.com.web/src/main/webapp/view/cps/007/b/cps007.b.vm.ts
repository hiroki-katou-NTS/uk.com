module cps007.b.vm {
    import close = nts.uk.ui.windows.close;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    let __viewContext: any = window['__viewContext'] || {};

    export class ViewModel {
        allItems: KnockoutObservableArray<IModel> = ko.observableArray([]);
        chooseItems: KnockoutObservableArray<IModel> = ko.observableArray([]);
        constructor() {
            let self = this,
                dto: IModelDto = getShared('CPS007_PARAM');

            for (let i = 1; i <= 20; i++) {
                self.allItems.push({ code: 'COD00' + i, name: 'Name 00' + i });
            }
        }

        start() {
            let self = this;

            // call service at here

        }

        pushData() {
            let self = this,
                data = ko.unwrap(self.chooseItems);

            setShared('CPS007_VALUE', { chooseItems: data });
            self.close();
        }

        close() {
            close();
        }
    }

    interface IModel {
        code: string;
        name: string;
    }

    interface IModelDto {
        allItems?: Array<IModel>;
        chooseItems?: Array<IModel>;
    }
}