module nts.uk.at.view.ksu001.m.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        listRank: KnockoutObservableArray<ItemModel>;
        selectedRank: KnockoutObservable<string>;
        constructor() {
            let self = this;
            self.listRank = ko.observableArray([
                new ItemModel('1', '基本給'),
                new ItemModel('2', '役職手当'),
                new ItemModel('3', '基本給ながい文字列ながい文字列ながい文字列')
            ]);
            self.selectedRank = ko.observable('1');



        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            //dfd.resolve(); -- đéo có thì nó disable luôn vcc
            dfd.resolve();
            return dfd.promise();
        }

        cancel_Dialog(): any {
            let self = this;  
            nts.uk.ui.windows.close();
        }


    }
    class ItemModel {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }

}