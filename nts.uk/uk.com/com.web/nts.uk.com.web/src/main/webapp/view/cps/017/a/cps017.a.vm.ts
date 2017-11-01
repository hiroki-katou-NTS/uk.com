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
        historySelection: KnockoutObservable<HistorySelection> = ko.observable(new HistorySelection({ histId: '', selectionItemId: '' }));

        //Selection:
        listSelection: KnockoutObservableArray<ISelection> = ko.observableArray([]);
        selection: KnockoutObservable<Selection> = ko.observable(new Selection({ selectionID: '', histId: '' }));

        //OrderSelection:
        listOrderSelection: KnockoutObservableArray<IOrderSelection> = ko.observableArray([]);
        orderSelection: KnockoutObservable<OrderSelection> = ko.observable(new OrderSelection({ selectionID: '', histId: '' }));

        //Check insert/upadte
        checkCreate: KnockoutObservable<boolean>;

        constructor() {
            let self = this,
                perInfoSelectionItem: SelectionItem = self.perInfoSelectionItem(),
                historySelection: HistorySelection = self.historySelection(),
                listHistorySelection: Array<HistorySelection> = self.listHistorySelection(),
                //_selectId = _.find(listHistorySelection, x => x.selectionItemId == historySelection.selectionItemId),
                //comand: HistorySelection = ko.toJS(historySelection)
                selection: Selection = self.selection();

            //check insert/update
            self.checkCreate = ko.observable(true);

            //Subscribe: 項目変更→項目のID変更
            perInfoSelectionItem.selectionItemId.subscribe(x => {
                if (x) {
                    //                    nts.uk.ui.errors.clearAll();
                    //                    
                    //                    
                    //                    service.getPerInfoSelectionItem(x).done((_perInfoSelectionItem: ISelectionItem) => {
                    //                        if (_perInfoSelectionItem) {
                    //                            perInfoSelectionItem.selectionItemName(_perInfoSelectionItem.selectionItemName);
                    //                        }
                    //                    });
                    let selectedObject = _.find(self.listItems(), (item) => {
                        return item.selectionItemId == x;
                    });
                    perInfoSelectionItem.selectionItemName(selectedObject.selectionItemName);


                    //history
                    service.getAllPerInfoHistorySelection(x).done((_selectionItemList: IHistorySelection) => {
                        self.listHistorySelection(_selectionItemList);
                        self.historySelection().histId(self.listHistorySelection()[0].histId);
                    });
                }
                // self.checkCreate(false);
            });

            //sub theo historyID:
            historySelection.histId.subscribe(x => {
                self.listSelection.removeAll();
                service.getAllOrderItemSelection(x).done((itemList: Array<ISelection>) => {
                    //self.listSelection(_selectionID);
                    // check !null
                    //                    if (_selectionID.selectionID > 0) { // check lai
                    //                        self.selection().selectionID(self.listSelection()[0].selectionID);
                    //                    }                    if (itemList && itemList.length) {
                        itemList.forEach(x => self.listSelection.push(x));
                        self.selection().selectionID(self.listSelection()[0].selectionID);
                    } else {
                        self.registerData();
                    }

                });
            });

            // sub theo selectionID: 
            selection.selectionID.subscribe(x => {
                //self.listSelection.removeAll();
                if (x) {
                    let selectLists = _.find(self.listSelection(), (item) => {
                        return item.selectionID == x;
                    });
                    selection.selectionCD(selectLists.selectionCD);
                    selection.selectionName(selectLists.selectionName);
                    selection.externalCD(selectLists.externalCD);
                    selection.memoSelection(selectLists.memoSelection);
                }
                //nts.uk.ui.errors.clearAll();
                self.checkCreate(false);
            });

        }

        //開始
        start(): JQueryPromise<any> {
            let self = this,
                historySelection: HistorySelection = self.historySelection(),
                listHistorySelection: Array<HistorySelection> = self.listHistorySelection(),
                _selectId = _.find(listHistorySelection, x => x.selectionItemId == historySelection.selectionItemId),
                comand: HistorySelection = ko.toJS(historySelection),
                dfd = $.Deferred();

            nts.uk.ui.errors.clearAll();

            // ドメインモデル「個人情報の選択項目」をすべて取得する
            service.getAllSelectionItems().done((itemList: Array<ISelectionItem>) => {
                if (itemList && itemList.length > 0) {
                    self.listItems(itemList);
                    self.perInfoSelectionItem().selectionItemId(self.listItems()[0].selectionItemId);

                } else {
                    alertError({ messageId: "Msg_455" });
                    self.registerData();
                }
                dfd.resolve();
            }).fail(error => {
                alertError({ messageId: "Msg_455" });
            });

            return dfd.promise();
        }

        //新規ボタン
        registerData() {
            let self = this,
                selection: Selection = self.selection();
            nts.uk.ui.errors.clearAll();
            selection.selectionID(undefined);
            selection.externalCD('');
            selection.selectionCD('');
            selection.selectionName('');
            selection.memoSelection('');
            self.checkCreate(true);
        }

        //検証チェック 
        validate() {
            $(".nts-editor").trigger("validate");
            if (nts.uk.ui.errors.hasError()) {
                return false;
            }
            return true;
        }

        //登録ボタン
        addData() {
            let self = this;
            if (self.validate()) {
                if (self.checkCreate() == true) {
                    self.add();
                } else {
                    self.update();
                }
            }
        }

        //新規モード
        add() {
            let self = this,
                currentItem: Selection = self.selection(),
                listSelection: Array<Selection> = self.listSelection(),
                _selectionCD = _.find(listSelection, x => x.selectionCD == currentItem.selectionCD());
            //oldIndex = _.findIndex(listSelection, x => x.selectionID == currentItem.selectionID());
            currentItem.histId(self.historySelection().histId());
            command = ko.toJS(currentItem);

            if (_selectionCD) {
                alertError({ messageId: "Msg_3" });
            } else {
                service.saveDataSelection(command).done(function() {
                    self.listSelection.removeAll();
                    service.getAllOrderItemSelection(self.historySelection().histId()).done((itemList: Array<ISelection>) => {
                        if (itemList && itemList.length) {
                            itemList.forEach(x => self.listSelection.push(x));
                            self.selection().selectionID(self.listSelection()[0].selectionID);
                        }
                        //let newItem = itemList[oldIndex];
                        //currentItem.selectionID(newItem.selectionID);
                    });
                    nts.uk.ui.dialog.alert({ messageId: "Msg_15" });
                    self.listSelection.valueHasMutated();

                });
            }

        }

        //更新モード
        update() {
            let self = this,
                currentItem: Selection = self.selection(),
                listSelection: Array<Selection> = self.listSelection(),
                _selectionCD = _.find(listSelection, x => x.selectionCD == currentItem.selectionCD());
            oldIndex = _.findIndex(listSelection, x => x.selectionID == currentItem.selectionID());
            currentItem.histId(self.historySelection().histId());
            command = ko.toJS(currentItem);

            service.updateDataSelection(command).done(function() {
                self.listSelection.removeAll();
                service.getAllOrderItemSelection(self.historySelection().histId()).done((itemList: Array<ISelection>) => {
                    if (itemList && itemList.length) {
                        itemList.forEach(x => self.listSelection.push(x));
                        //self.selection().selectionID(self.listSelection()[0].selectionID);
                    }
                    let newItem = itemList[oldIndex];
                    currentItem.selectionID(newItem.selectionID);
                });
                nts.uk.ui.dialog.alert({ messageId: "Msg_15" });
                self.listSelection.valueHasMutated();

            });
        }

        //削除ボタン
        remove() {
            //alert('remove');  
            let self = this,
                items = ko.unwrap(self.listSelection),
                currentItem: Selection = self.selection(),
                listSelection: Array<Selection> = self.listSelection();

            currentItem.histId(self.historySelection().histId());
            command = ko.toJS(currentItem);
            oldIndex = _.findIndex(listSelection, x => x.selectionID == currentItem.selectionID());
            lastIndex = items.length - 1;

            if (items.length > 0) {
                confirm({ messageId: "Msg_18" }).ifYes(() => {
                    service.removeDataSelection(command).done(function() {
                        self.listSelection.removeAll();

                        service.getAllOrderItemSelection(self.historySelection().histId()).done((itemList: Array<ISelection>) => {
                            if (itemList && itemList.length) {
                                itemList.forEach(x => self.listSelection.push(x));
                                //self.selection().selectionID(self.listSelection()[0].selectionID);
                                if (oldIndex == lastIndex) {
                                    oldIndex--;
                                }
                                let newItem = itemList[oldIndex];
                                currentItem.selectionID(newItem.selectionID);
                            }
                        });
                        self.listItems.valueHasMutated();
                        nts.uk.ui.dialog.alert({ messageId: "Msg_16" });
                    });

                }).ifNo(() => {
                    self.listItems.valueHasMutated();
                    return;
                })
            } else {
                alertError({ messageId: "Msg_521" });
                self.registerDataSelectioItem();
            }
        }

        //ダイアログC画面
        openDialogB() {
            let self = this,
                currentItem: Selection = self.selection();

            //set histID 
            setShared('selectedHisId', self.historySelection().histId());
            block.invisible();
            modal('/view/cps/017/b/index.xhtml', { title: '' }).onClosed(function(): any {
                block.clear();
            });
        }

        //ダイアログC画面
        openDialogC() {
            let self = this;

            //set histID
            setShared('selectedHisId', self.historySelection().histId());

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
        histId?: string;
        selectionItemId?: string;
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

    //Selection
    interface ISelection {
        selectionID?: string;
        histId?: string;
        selectionCD: string;
        selectionName: string;
        externalCD: string;
        memoSelection: string;
        initSelection: number;
    }
    class Selection {
        selectionID: KnockoutObservable<string> = ko.observable('');
        histId: KnockoutObservable<string> = ko.observable('');
        selectionCD: KnockoutObservable<string> = ko.observable('');
        selectionName: KnockoutObservable<string> = ko.observable('');
        externalCD: KnockoutObservable<string> = ko.observable('');
        memoSelection: KnockoutObservable<string> = ko.observable('');
        initSelection: KnockoutObservable<number> = ko.observable();

        constructor(param: ISelection) {
            let self = this;
            self.selectionID(param.selectionID || '');
            self.histId(param.histId || '');
            self.selectionCD(param.selectionCD || '');
            self.selectionName(param.selectionName || '');
            self.externalCD(param.externalCD || '');
            self.memoSelection(param.memoSelection || '');
            self.initSelection(param.initSelection || '');

        }
    }

    //Order Selection
    interface IOrderSelection {
        selectionID?: string;
        histId?: string;
        disporder: number;
        initSelection: number;
    }

    class OrderSelection {
        selectionID: KnockoutObservable<string> = ko.observable('');
        histId: KnockoutObservable<string> = ko.observable('');
        disporder: KnockoutObservable<number> = ko.observable('');
        initSelection: KnockoutObservable<number> = ko.observable('');

        constructor(param: OrderSelection) {
            let self = this;
            self.selectionID(param.selectionID || '');
            self.histId(param.histId || '');
            self.disporder(param.disporder || '');
            self.initSelection(param.initSelection || '');
        }
    }
}

function makeIcon(value, row) {
    if (value == 1)
        return "●";
    return '';
}
