module nts.uk.com.view.ksu001.n.viewmodel {
    import error = nts.uk.ui.errors;
    import text = nts.uk.resource.getText;
    import close = nts.uk.ui.windows.close;
    import dialog = nts.uk.ui.dialog;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import block = nts.uk.ui.block;

    export class ViewModel {
        listRank: KnockoutObservableArray<any> = ko.observableArray([]);
        currentCode: KnockoutObservable<any>;
        singleSelectedCode: KnockoutObservable<any> = ko.observable();
        comboItems = [new ItemModel('1', 'A'),
            new ItemModel('2', 'B'),
            new ItemModel('3', 'C')];
        comboColumns = [{ prop: 'name', length: 8 }];
        items = _(new Array(10)).map((x, i) => new RankItems(i + 1)).value();
        constructor() {
            let self = this;
            for (let i = 1; i < 20; i++) {
                self.listRank.push(new RankItems(i));
            }
            self.start();
        }
        start(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            return dfd.promise();
        }
            /**
             * Close dialog
             */
            closeDialog(): void {
                nts.uk.ui.windows.close();
            }
    }

    export class ItemModel {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
    export  class RankItems {
        rankCode: string;
        rankName: string;
        rank: string;
        constructor(index:number) {
            this.rankCode = '00000000'+index;
            this.rankName = 'ABC';
            this.rank = String(index % 3 +1) ;
        }
    }   
}