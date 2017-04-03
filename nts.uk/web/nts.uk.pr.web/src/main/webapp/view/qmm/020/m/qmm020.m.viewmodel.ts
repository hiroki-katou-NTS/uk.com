module qmm020.m.viewmodel {
    export class ScreenModel {
        items: KnockoutObservableArray<ItemModel>;
        histItem: KnockoutObservableArray<HistModel>;
        columns: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        //currentBaseYM : KnockoutObservable<number>;
        currentCodeList: KnockoutObservable<string>;

        constructor() {
            var self = this;
            self.items = ko.observableArray([]);
            self.histItem = ko.observableArray([]);

            self.columns = ko.observableArray([
                { headerText: 'コード', prop: 'code', width: 50 },
                { headerText: '名称', prop: 'name', width: 150 },
                { headerText: '有効期間', prop: 'time', width: 150 }
            ]);
            self.currentCode = ko.observable();
            self.currentCodeList = ko.observable('');
            self.start();
        }
        //event when click to 選択 Button
        selectStmtCode(): any {
            var self = this;
            //alert(self.currentCodeList());
            var stmtCode = self.currentCodeList();
            nts.uk.ui.windows.setShared('stmtCodeSelected', stmtCode);
            nts.uk.ui.windows.close();
        }
        //event when close dialog
        closeDialog(): any {
            nts.uk.ui.windows.close();
        };
        // start function
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();

            var currentBaseYM = parseInt(nts.uk.ui.windows.getShared('valMDialog'));
            //debugger;
            //alert(self.currentBaseYM());
            //console.log(self.currentBaseYM());
            //get Allot History
            service.getAllAllotLayoutHist(currentBaseYM).done(function(layoutHistory: Array<service.model.LayoutHistoryDto>) {
                if (layoutHistory.length > 0) {
                    console.log(layoutHistory);
                    let _histItems: Array<HistModel> = [];
                    _.forEach(layoutHistory, function(layoutHist, i) {
                        _histItems.push(new HistModel(layoutHist.startYm, layoutHist.endYm, layoutHist.stmtCode));
                    });
                    let _items: Array<ItemModel> = [];
                    _.forEach(_histItems, function(layoutSelect, i) {
                        if (_histItems.length > 0) {
                            //get Payment layout name
                            service.getAllotLayoutName(layoutSelect.stmtCode).done(function(stmtName: string) {
                                _items.push(new ItemModel(layoutSelect.stmtCode, stmtName, layoutSelect.startYm + '~' + layoutSelect.endYm));
                                self.items(_items);
                            }).fail(function(res) {
                                alert(res);
                            });
                        }
                    });
                    console.log(_items);
                } else {
                    dfd.resolve();
                }
            }).fail(function(res) {
                alert(res);
            });
            return dfd.promise();
        }
    }


    class ItemModel {
        code: string;
        name: string;
        time: string;
        constructor(code: string, name: string, time: string) {
            this.code = code;
            this.name = name;
            this.time = time;
        }
    }
    class HistModel {
        startYm: string;
        endYm: string;
        stmtCode: string;
        constructor(startYm: string, endYm: string, stmtCode: string) {
            this.startYm = startYm;
            this.endYm = endYm;
            this.stmtCode = stmtCode;
        }

    }
}