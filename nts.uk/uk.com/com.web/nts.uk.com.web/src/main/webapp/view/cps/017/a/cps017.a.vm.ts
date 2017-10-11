module nts.uk.com.view.cps017.a.viewmodel {
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import textUK = nts.uk.text;
    import block = nts.uk.ui.block;
    export class ScreenModel {
        //listSelectionItem
        listItems: KnockoutObservableArray<ISelectionItem> = ko.observableArray([]);
        perInfoSelectionItem: KnockoutObservable<SelectionItem> = ko.observable(new SelectionItem({ selectionItemId: '', selectionItemName: '' }));

        // history:
        listHistorySelection: KnockoutObservableArray<IHistorySelection> = ko.observableArray([]);
        
        constructor() {
            let self = this,
                perInfoSelectionItem: SelectionItem = self.perInfoSelectionItem();

            //Subscribe: 項目変更→項目のID変更
            perInfoSelectionItem.selectionItemId.subscribe(x => {
                if (x) {
                    nts.uk.ui.errors.clearAll();
                    service.getPerInfoSelectionItem(x).done((_perInfoSelectionItem: ISelectionItem) => {
                        if (_perInfoSelectionItem) {
                            perInfoSelectionItem.selectionItemName(_perInfoSelectionItem.selectionItemName);
                        }
                    });
                }
            });
        }

        //開始
        start(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();

            nts.uk.ui.errors.clearAll();

            // ドメインモデル「個人情報の選択項目」をすべて取得する
            service.getAllSelectionItems().done((itemList: Array<ISelectionItem>) => {
                //項目がある場合
                if (itemList && itemList.length > 0) {

                    //取得した選択項目を画面項目「A2_3：選択項目名称一覧」に表示する
                    itemList.forEach(x => self.listItems.push(x));

                    //画面項目「A2_3：選択項目リスト」の先頭を選択状態にする
                    self.perInfoSelectionItem().selectionItemId(self.listItems()[0].selectionItemId);

                } else {
                    //0件の場合: エラーメッセージの表示(#Msg_455)
                    alertError({ messageId: "Msg_455" });
                }
                dfd.resolve();


            }).fail(error => {
                //0件の場合: エラーメッセージの表示(#Msg_455)
                alertError({ messageId: "Msg_455" });
            });
            return dfd.promise();
        }

        //ダイアログC画面
        openDialogC() {
            let self = this,
                obj = {
                    sel_id: "0001",
                    sel_name: " Du DT"
                };

            setShared('historyInfo', obj);

            block.invisible();

            modal('/view/cps/017/c/index.xhtml', { title: '' }).onClosed(function(): any {

                block.clear();
            });
        }

        //ダイアログD画面
        openDialogD() {
            let self = this,
                obj = {
                    sel_id: "0001",
                    sel_name: " Du DT"
                };

            setShared('historyInfo', obj);

            block.invisible();

            modal('/view/cps/017/d/index.xhtml', { title: '' }).onClosed(function(): any {

                block.clear();
            });
        }
    }

    //SelectionItem
    interface ISelectionItem {
        selectionItemId: string;
        selectionItemName: string;
    }

    class SelectionItem {
        selectionItemId: KnockoutObservable<string> = ko.observable('');
        selectionItemName: KnockoutObservable<string> = ko.observable('');

        constructor(param: ISelectionItem) {
            let self = this;
            self.selectionItemId(param.selectionItemId || '');
            self.selectionItemName(param.selectionItemName || '');

        }
    }

    //history:
    interface IHistorySelection {
        histId: string;
        selectionItemId: string;
        companyCode: string;
        startDate: string;
        endDate: string;
    }

    class HistorySelection {
        histId: KnockoutObservable<string> = ko.observable('');
        selectionItemId: KnockoutObservable<string> = ko.observable('');
        companyCode: KnockoutObservable<string> = ko.observable('');
        startDate: KnockoutObservable<string> = ko.observable('');
        endDate: KnockoutObservable<string> = ko.observable('');
        constructor(param: IHistorySelection) {
            let self = this;
            self.histId(param.histId || '');
            self.selectionItemId(param.selectionItemId || '');
            self.companyCode(param.companyCode || '');
            self.startDate(param.startDate || '');
            self.endDate(param.endDate || '');
        }
    }


}







/*
module nts.uk.com.view.cps017.a.viewmodel {
    import error = nts.uk.ui.errors;
    import text = nts.uk.resource.getText;
    import close = nts.uk.ui.windows.close;
    import dialog = nts.uk.ui.dialog;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import block = nts.uk.ui.block;
    import modal = nts.uk.ui.windows.sub.modal;

    export class ViewModel {
        sel_item_lst: KnockoutObservableArray<SelectionItem>;

        currentItemSel: KnockoutObservable<SelectionItem>;

        item_sel_col: KnockoutObservableArray<NtsGridListColumn>;

        comboItems = [new ItemModel('1', '基本給'),
            new ItemModel('2', '役職手当'),
            new ItemModel('3', '基本給2')];

        comboColumns =
        [{ prop: 'code', length: 4 },
            { prop: 'name', length: 8 }];

         items = _(new Array(10)).map((x, i) => new InitAndOrderSelection({
            id: i,
            sel_cd: "000" + i.toString(),
            sel_name: "A" + i.toString(),
            init_sel: true
        })).value();

        constructor() {
            let self = this;

            self.init();

        }

        init() {
            let self = this;

            self.sel_item_lst = ko.observableArray([{
                new SelectionItem({
                    sel_item_id: "0001",
                    sel_item_name: "Success",
                    history_lst: [{
                        new HistorySelection({
                            history_id: "0001",
                            period: "2017/01/01 ~ 2017/09/29"
                        })
                    }]
                })
            }]);
            self.currentItemSel = ko.observable(new SelectionItem({
                sel_item_id: "0001",
                sel_item_name: "Success",
                history_lst: [{
                    new HistorySelection({
                        history_id: "0001",
                        period: "2017/01/01 ~ 2017/09/29"
                    })
                }]
            });
            self.item_sel_col = ko.observableArray([
                { headerText: 'id', key: 'sel_item_id', width: 100, hidden: true },
                { headerText: text('CPS017_7'), key: 'sel_item_name', width: 200 }
            ]);
        }


        //dialog C
        openDialogC() {
            let self = this,
                obj = {
                    sel_id: "0001",
                    sel_name: " Du DT"
                };

            setShared('historyInfo', obj);

            block.invisible();

            modal('/view/cps/017/c/index.xhtml', { title: '' }).onClosed(function(): any {

                block.clear();
            });
        }

        //dialog D
        openDialogD() {
            let self = this,
                obj = {
                    sel_id: "0001",
                    sel_name: " Du DT"
                };

            setShared('historyInfo', obj);

            block.invisible();

            modal('/view/cps/017/d/index.xhtml', { title: '' }).onClosed(function(): any {

                block.clear();
            });
        }
    }


    export interface ISelectionItem {
        sel_item_id: string;
        sel_item_name: string;
        history_lst: Array<HistorySelection> = [];
    }

    // list bên trái trên cùng
    export class SelectionItem {
        current_sel_item_id: KnockoutObservable<string> = ko.observable('');
        sel_item_id: string = "0001";
        sel_item_name: string = "Test1";


        //list history của sel_item_id
        history_lst: KnockoutObservableArray<HistorySelection> = ko.observableArray([{
            new HistorySelection({
                history_id: "0001",
                period: "2017/01/01 ~ 2017/09/29"
            })
        }]);

        //current id của history
        currentId: KnockoutObservable<string> = ko.observable('');
        constructor(params: ISelectionItem) {
            let self = this;

            self.sel_item_id = (params.sel_item_id || "");
            self.sel_item_name = params.sel_item_name || "";
            self.history_lst(params.history_lst);
        }
    }

    // định nghĩa đối tượng history selection
    export interface IHistorySelection {
        history_id: string;
        period: string;

    }

    export class HistorySelection {

        history_id: string = "0001";
        period: string = "2017/01/01 ~ 2017/09/29";
        currentHistory_id: KnockoutObservable<string> = ko.observable('');

        constructor(params: IHistorySelection) {
            let self = this;
            self.history_id = params.history_id || "";
            self.period = params.period || "";
        }

    }

    // định nghĩa list selection ở content giữa bao gồm code, name
    export interface ISelection {
        id: string;
        sel_code: string;
        sel_name: string;

    }

    export class Selection {
        id: KnockoutObservableArray<string>;
        sel_code: string;
        sel_name: string;

        constructor(params: ISelection) {
            let self = this;

            self.id(params.id || " ");
            self.sel_code = params.sel_code;
            self.sel_name = params.sel_name;

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

    export class GridItem {
        id: number;
        flag: boolean;
        ruleCode: string;
        combo: string;
        text1: string;
        constructor(index: number) {
            this.id = index;
            this.flag = index % 2 == 0;
            this.ruleCode = String(index % 3 + 1);
            this.combo = String(index % 3 + 1);
            this.text1 = "TEXT";
        }
    }

    export interface IInitAndOrderSelection {
        id: number;
        sel_cd: string;
        sel_name: string;
        init_sel: boolean;
    }

    export class InitAndOrderSelection {

        id: number; // so thu tu hien thi trong grid
        sel_cd: string;
        sel_name: string;
        init_sel: boolean;

        constructor(params: IInitAndOrderSelection) {
            let self = this;

            self.id = params.id || 0;
            self.sel_cd = params.sel_cd || " ";
            self.sel_name = params.sel_name || " ";
            self.init_sel = params.init_sel || false;
        }

    }

    // interface SelectionOrder
    export interface ISelectionOrder {
        sel_id: string;
        history_id: string;
        orderLst: Array<InitAndOrderSelection> = [];
    }

    // class SelectionOrder
    export class SelectionOrder {
        sel_id: string;
        history_id: string;
        orderLst: Array<InitAndOrderSelection> = [];

        constructor(params: ISelectionOrder) {
            let self = this;

            self.sel_id = params.sel_id || "";
            self.history_id = params.history_id || "";
            self.orderLst = params.orderLst || [];
        }

    }


}
*/