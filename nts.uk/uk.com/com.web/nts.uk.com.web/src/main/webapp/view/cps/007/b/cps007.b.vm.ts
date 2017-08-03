module cps007.b.vm {
    import text = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import close = nts.uk.ui.windows.close;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    let __viewContext: any = window['__viewContext'] || {};

    export class ViewModel {
        allItems: KnockoutObservableArray<IData> = ko.observableArray([]);
        chooseItems: KnockoutObservableArray<IData> = ko.observableArray([]);
        category: KnockoutObservable<ItemCategory> = ko.observable(new ItemCategory({ id: 'ID1' }));

        constructor() {
            let self = this,
                cat = self.category(),
                dto: IModelDto = getShared('CPS007_PARAM') || { chooseItems: [] };

            if (dto.category && dto.category.id) {
                cat.id(dto.category.id);
            } else {
                self.close();
            }

            // paser default choose item
            self.chooseItems(dto.chooseItems);

            // when cat id is change
            // get category info and get all item define in this category
            cat.id.subscribe(x => {
                if (x) {
                    service.getCategory(x).done((data: IData) => {
                        if (data) {
                            cat.code(data.code);
                            cat.name(data.name);

                            // get all item in category
                            service.getItemDefinitions(data.id).done((data: Array<IData>) => self.allItems(data));
                        } else {
                            cat.id(undefined);
                        }
                    });
                } else {
                    // close dialog if category is not present
                    self.close();
                }
            });
            cat.id.valueHasMutated();
        }

        pushData() {
            let self = this,
                data: Array<IData> = ko.unwrap(self.chooseItems);

            if (!data.length) {
                alert(text('Msg_203'));
                return;
            }

            setShared('CPS007_VALUE', { chooseItems: data });
            self.close();
        }

        close() {
            close();
        }
    }

    interface IData {
        id: string;
        code?: string;
        name?: string;
    }

    class ItemCategory {
        id: KnockoutObservable<string> = ko.observable('');
        code: KnockoutObservable<string> = ko.observable('');
        name: KnockoutObservable<string> = ko.observable('');

        constructor(param: IData) {
            let self = this;

            self.id(param.id || '');
            self.code(param.code || '');
            self.name(param.name || '');
        }
    }

    interface IModelDto {
        category: IData;
        allItems?: Array<IData>;
        chooseItems?: Array<IData>;
    }
}