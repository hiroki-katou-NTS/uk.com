module cps003.f.vm {
    import text = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import close = nts.uk.ui.windows.close;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    let __viewContext: any = window['__viewContext'] || {};

    export class ViewModel {
        currentItem: ICurrentItem = {
            id: ko.observable(''),
            name: ko.observable('')
        };
        dataSources: KnockoutObservableArray<any> = ko.observableArray([]);

        constructor() {
            let self = this,
                data: IModelDto = getShared('CPS003F_PARAM') || { id: '' };

            // sample data
            if (data.id) {
                service.fetch.setting(data.id).done(resp => {
                    self.dataSources(_.orderBy(resp.perInfoData, ['dispOrder', 'itemCD']).map(m => ({ id: m.perInfoItemDefID, name: m.itemName })));
                });
            }
        }

        pushData() {
            let self = this;

            setShared('CPS003F_VALUE', {});
            self.close();
        }

        close() {
            close();
        }
    }

    export class HtmlElement {
        element = document.createElement('div');
        toString() {
            return this.element;
        }
    }

    interface ICurrentItem {
        id: KnockoutObservable<string>;
        name: KnockoutObservable<string>;
        // and more prop
    }

    interface IModelDto {
        id: string;
    }
}