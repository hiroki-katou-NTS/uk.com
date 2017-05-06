module qmm020.m.viewmodel {
    export class ScreenModel {
        listItemSelected: KnockoutObservable<string> = ko.observable('');
        listItemDataSources: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        listItemColumns: KnockoutObservableArray<any> = ko.observableArray([
            { headerText: 'コード', prop: 'code', width: 50 },
            { headerText: '名称', prop: 'name', width: 175 },
            { headerText: '有効期間', prop: 'time', width: 175 }
        ]);

        constructor() {
            let self = this;
            self.start();
        }

        //event when click to 選択 Button
        selectStmtCode() {
            let self = this;
            nts.uk.ui.windows.setShared('stmtCodeSelected', self.listItemSelected());
            self.closeDialog();
        }

        //event when close dialog
        closeDialog() {
            nts.uk.ui.windows.close();
        };

        // start function
        start(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred(),
                currentBaseYM = parseInt(nts.uk.ui.windows.getShared('valMDialog'));
            // Đậu, hàm này cần viết lại, nó kéo hiệu năng app xuống.
            if (!!currentBaseYM) {
                //get Allot History
                service.getAllAllotLayoutHist(currentBaseYM).done(function(resp: Array<ILayoutHistoryModel>) {
                    if (resp.length > 0) {
                        let _items: Array<ItemModel> = [];
                        _.forEach(resp, function(item) {
                            if (resp.length > 0) {
                                //get Payment layout name
                                service.getAllotLayoutName(item.stmtCode).done(function(stmtName: string) {
                                    _items.push(new ItemModel({ code: item.stmtCode, name: stmtName, time: item.startYm + '~' + item.endYm }));
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
            }
            
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