module cps003.d.vm {
    import text = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import close = nts.uk.ui.windows.close;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    let __viewContext: any = window['__viewContext'] || {};

    export class ViewModel {
        ctgName: KnockoutObservable<string> = ko.observable('');
        selecteds: KnockoutObservableArray<string> = ko.observableArray([]);
        dataSources: KnockoutObservableArray<any> = ko.observableArray([]);

        constructor() {
            let self = this,
                data: IModelDto = getShared('CPS003D_PARAM') || {};

            if (!data.id || !data.name) {
                self.close();
            } else {
                self.ctgName(data.name);

                service.fetch.setting(data.id).done(resp => {
                    self.dataSources(resp.perInfoData);
                });
            }
        }

        pushData() {
            let self = this;

            setShared('CPS003D_VALUE', ko.toJS(self.selecteds));

            self.close();
        }

        close() {
            close();
        }
    }

    interface IModelDto {
        id: string;
        name: string;
    }
}