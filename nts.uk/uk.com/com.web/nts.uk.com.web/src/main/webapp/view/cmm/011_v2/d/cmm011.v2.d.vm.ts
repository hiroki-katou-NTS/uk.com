module nts.uk.com.view.cmm011.v2.d.viewmodel {
    import getText = nts.uk.resource.getText;
    import modal = nts.uk.ui.windows.sub.modal;

    export class ScreenModel {
        itemList: KnockoutObservableArray<any>;
        selectedId: KnockoutObservable<number>;
        enable: KnockoutObservable<boolean>;

        code: KnockoutObservable<string> = ko.observable(null);
        name: KnockoutObservable<string> = ko.observable(null);
        displayName: KnockoutObservable<string> = ko.observable(null);
        genericName: KnockoutObservable<string> = ko.observable(null);
        externalCode: KnockoutObservable<string> = ko.observable(null);

        screenMode: number = 1;

        constructor() {
            var self = this;
            self.itemList = ko.observableArray([
                new BoxModel(1,  getText('CMM011-1_11')),
                new BoxModel(2, getText('CMM011-1_12')),
                new BoxModel(3, getText('CMM011-1_13'))
            ]);
            self.selectedId = ko.observable(1);
            self.enable = ko.observable(true);
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred<any>();
            dfd.resolve();
            return dfd.promise();
        }
        openScreenCDialog(){
            modal("/view/cmm/011_v2/c/index.xhtml").onClosed(() => {

            });
        }
    }

    class BoxModel {
        id: number;
        name: string;

        constructor(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
        }
    }
}