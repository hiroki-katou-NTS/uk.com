module qmm020.m.viewmodel {
    export class ScreenModel {
        listItemSelected: KnockoutObservable<any> = ko.observable(null);
        listItemDataSources: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        listItemColumns: KnockoutObservableArray<any> = ko.observableArray([
            { headerText: 'コード', prop: 'code', width: 50 },
            { headerText: '名称', prop: 'name', width: 175 },
            { headerText: '有効期間', prop: 'time', width: 175 }
        ]);

        constructor() {
            var self = this;
            self.start();
        }

        //event when click to 選択 Button
        selectStmtCode() {
            var self = this;
            nts.uk.ui.windows.setShared('stmtCodeSelected', self.listItemSelected());
            self.closeDialog();
        }

        //event when close dialog
        closeDialog() {
            nts.uk.ui.windows.close();
        };

        // start function
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();

            var currentBaseYM = parseInt(nts.uk.ui.windows.getShared('valMDialog')) || 201710;

            //get Allot History
            service.getAllAllotLayoutHist(currentBaseYM).done(function(layoutHistory: Array<ILayoutHistoryModel>) {
                if (layoutHistory.length > 0) {
                    let _histItems: Array<LayoutHistoryModel> = [];

                    _.forEach(layoutHistory, function(layoutHist, i) {
                        _histItems.push(new LayoutHistoryModel(layoutHist));
                    });

                    let _items: Array<ItemModel> = [];

                    _.forEach(_histItems, function(layoutSelect, i) {
                        if (_histItems.length > 0) {
                            //get Payment layout name
                            service.getAllotLayoutName(layoutSelect.stmtCode).done(function(stmtName: string) {
                                _items.push(new ItemModel({ code: layoutSelect.stmtCode, name: stmtName, time: layoutSelect.startYm + '~' + layoutSelect.endYm }));
                                self.listItemDataSources(_items);
                            }).fail(function(res) {
                                alert(res);
                            });
                        }
                    });
                } else {
                    dfd.resolve();
                }
            }).fail(function(res) {
                alert(res);
            });
            return dfd.promise();
        }
    }

    interface IItemModel {
        code: string;
        name: string;
        time: string;
    }

    class ItemModel {
        code: string;
        name: string;
        time: string;
        constructor(param: IItemModel) {
            this.code = param.code;
            this.name = param.name;
            this.time = param.time;
        }
    }

    interface ILayoutHistoryModel {
        startYm: string;
        endYm: string;
        stmtCode: string;
    }

    class LayoutHistoryModel {
        startYm: string;
        endYm: string;
        stmtCode: string;
        constructor(param: ILayoutHistoryModel) {
            this.startYm = param.startYm;
            this.endYm = param.endYm;
            this.stmtCode = param.stmtCode;
        }

    }
}