module nts.uk.com.view.cmm011.v2.c.viewmodel {
    import getText = nts.uk.resource.getText;
    import modal = nts.uk.ui.windows.sub.modal;

    export class ScreenModel {
        itemList: KnockoutObservableArray<any>;
        selectedId: KnockoutObservable<number>;

        inputList: KnockoutObservableArray<inputItems>;
        listColums: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;

        screenMode: number = 1;

        constructor() {
            var self = this;
            self.itemList = ko.observableArray([
                new BoxModel(1, ko.computed(function () {
                        if (self.screenMode == 1)
                            return getText('CMM011-1_8');
                        if (self.screenMode == 2)
                            return getText('CMM011-2_8');
                    })
                ),
                new BoxModel(2, ko.computed(function () {
                        if (self.screenMode == 1)
                            return getText('CMM011-1_9');
                        if (self.screenMode == 2)
                            return getText('CMM011-2_9');
                    })
                )
            ]);
            self.selectedId = ko.observable(2);

            //Grid list item
            this.inputList = ko.observableArray([]);
            //Grid list columns
            this.listColums = ko.observableArray([
                {headerText: getText('CMM011-0_30'), key: 'code', width: 100},
                {headerText: getText('CMM011-0_31'), key: 'name', width: 100, formatter: _.escape},
                {headerText: getText('CMM011-0_32'), key: 'date', width: 150, formatter: _.escape}
            ]);
            this.currentCode = ko.observable();
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred<any>();
            dfd.resolve();
            return dfd.promise();
        }
        openDialogD(){
            modal("/view/cmm/011_v2/d/index.xhtml").onClosed(() => {

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

    class inputItems {
        code: string;
        name: string;
        date: string;

        constructor(code: string, name: string, date: string) {
            this.code = code;
            this.name = name;
            this.date = date;
        }
    }

}