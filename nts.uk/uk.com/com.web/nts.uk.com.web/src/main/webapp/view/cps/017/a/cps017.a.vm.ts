module nts.uk.com.view.cps017.a.viewmodel {
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import textUK = nts.uk.text;
    import block = nts.uk.ui.block;
    let writeConstraint = window['nts']['uk']['ui']['validation']['writeConstraint'],
        invisible = window["nts"]["uk"]["ui"]["block"]["invisible"],
        unblock = window["nts"]["uk"]["ui"]["block"]["clear"];
    
    export class ScreenModel {
        //listSelectionItem
        listItems: KnockoutObservableArray<SelectionItem> = ko.observableArray([]);
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
        checkCreateaaa: KnockoutObservable<boolean>;
        closeUp: KnockoutObservable<boolean> = ko.observable(false);
        isDialog: KnockoutObservable<boolean> = ko.observable(false);

        selHistId: KnockoutObservable<string> = ko.observable('');
        isGroupManager: boolean;

        // status of function-button
        enableRegister: KnockoutObservable<boolean> = ko.observable(false);
        enableRemove: KnockoutObservable<boolean> = ko.observable(false);
        enableOpenDialogB: KnockoutObservable<boolean> = ko.observable(false);
        enableCreateNew: KnockoutObservable<boolean> = ko.observable(false);
        showRefecToAll: KnockoutObservable<boolean> = ko.observable(true);

        // status of history-function-button
        enableAddUpdateHist: KnockoutObservable<boolean> = ko.observable(false);
        enableDelHist: KnockoutObservable<boolean> = ko.observable(false);

        // status of selection-name, selection-code
        enableSelectionCd: KnockoutObservable<boolean> = ko.observable(true);
        enableSelectionName: KnockoutObservable<boolean> = ko.observable(true);

        // constraints
        constraints: KnockoutObservable<any> = ko.observable();

        constructor() {
            let self = this;
            let historySelection: HistorySelection = self.historySelection();
            let selection: Selection = self.selection();
            let perInfoSelectionItem: ISelectionItem1 = self.perInfoSelectionItem();

            let groupCompanyAdmin = __viewContext.user.role.groupCompanyAdmin;
            self.isGroupManager = groupCompanyAdmin !== 'null';
            //check insert/update
            self.checkCreate = ko.observable(true);
            self.checkCreateaaa = ko.observable(true);

            //Subscribe: 項目変更→項目のID変更
            perInfoSelectionItem.selectionItemId.subscribe(id => {
                if (id) {

                    let selectedObject: ISelectionItem1 = _.find(self.listItems(), (item) => {
                        return item.selectionItemId == id;
                    });

                    if (selectedObject != undefined) {

                        //self.perInfoSelectionItem(new SelectionItem(selectedObject));
                        perInfoSelectionItem.selectionItemName(selectedObject.selectionItemName);
                        perInfoSelectionItem.characterType(selectedObject.characterType ? 1 : 0);
                        perInfoSelectionItem.selectionItemClassification(selectedObject.shareChecked ? 0 : 1);

                        self.constraints.selectionCode = selectedObject.codeLength;
                        self.constraints.selectionName = selectedObject.nameLength;
                        self.constraints.selectionExternalCode = selectedObject.extraCodeLength;

                        let constraint = __viewContext.primitiveValueConstraints;

                        writeConstraint("SelectionCdNumeric", {
                            charType: constraint.SelectionCdNumeric.charType,
                            maxLength: self.constraints.selectionCode,
                            valueType: constraint.SelectionCdNumeric.valueType
                        });

                        writeConstraint("SelectionCdAlphaNumeric", {
                            charType: constraint.SelectionCdAlphaNumeric.charType,
                            maxLength: self.constraints.selectionCode,
                            valueType: constraint.SelectionCdAlphaNumeric.valueType
                        });
                        writeConstraint("SelectionName", {
                            charType: "Any",
                            maxLength: self.constraints.selectionName,
                            valueType: constraint.SelectionName.valueType
                        });

                        writeConstraint("ExternalCdAlphalNumeric", {
                            charType: constraint.ExternalCdAlphalNumeric.charType,
                            maxLength: self.constraints.selectionExternalCode,
                            valueType: constraint.ExternalCdAlphalNumeric.valueType
                        });
                        writeConstraint("ExternalCdNumeric", {
                            charType: constraint.ExternalCdNumeric.charType,
                            maxLength: self.constraints.selectionExternalCode,
                            valueType: constraint.ExternalCdNumeric.valueType
                        });

                    }
                    // システム管理者　かつ　選択している選択項目の「選択項目区分」＝社員のとき
                    if (self.isGroupManager === true && perInfoSelectionItem.selectionItemClassification() === 1) {
                        self.showRefecToAll(true);
                    } else {
                        self.showRefecToAll(false);
                    }

                    // history
                    service.getAllPerInfoHistorySelection(id).done((_selectionItemList: IHistorySelection) => {
                        let changeData: Array<IHistorySelection> = _.each(_selectionItemList, (item) => {
                            item.displayDate = item.startDate + "  " + getText('CPS017_12') + "  " + item.endDate;
                            return item;
                        });
                        self.listHistorySelection(changeData);
                        //self.historySelection(self.listHistorySelection()[0]);
                        self.historySelection().histId(self.listHistorySelection()[0].histId);
                        self.focusToInput();
                    });

                } else {
                    //historySelection.histId(undefined);
                    self.createNewData();
                }

            });


            historySelection.histId.subscribe(x => {
                if (x) {
                    let histCur = _.find(self.listHistorySelection(), a => a.histId == x);
                    if (histCur.endDate !== '9999/12/31') {
                        self.setEnableDisplay5(false);
                        self.enableSelectionName(false);
                    } else {
                        self.setEnableDisplay5(true);
                        self.enableSelectionName(true);
                        // if it has only one history
                        if (self.listHistorySelection().length === 1) {
                            self.enableDelHist(false);
                        }
                    }
                    // fill startDate and endDate
                    self.historySelection().startDate(histCur.startDate);
                    self.historySelection().endDate(histCur.endDate);

                    self.listSelection.removeAll();
                    service.getAllOrderItemSelection(x).done((itemList: Array<ISelection>) => {                        if (itemList && itemList.length > 0) {
                            self.checkCreateaaa(false);

                            // fix responsive bug
                            ko.utils.arrayPushAll(self.listSelection, itemList);

                            self.selection().selectionID(self.listSelection()[0].selectionID);
                            self.focusToInput();
                        } else {
                            self.createNewData();
                        }
                        self.listSelection.valueHasMutated();
                    });

                    self.focusToInput();
                }
            });

            // sub theo selectionID: 
            selection.selectionID.subscribe(x => {
                if (x) {
                    nts.uk.ui.errors.clearAll();
                    let selectLists: ISelection1 = _.find(self.listSelection(), (item) => {
                        return item.selectionID == x;
                    });
                    selection.selectionCD(selectLists.selectionCD);
                    selection.selectionName(selectLists.selectionName);
                    selection.externalCD(selectLists.externalCD);
                    selection.memoSelection(selectLists.memoSelection);

                    // Chu y: Tim hieu them ham _.defer
                    selection.codeType(99);
                    _.defer(() => {
                        selection.codeType(selectLists.codeType);
                    });
                    self.focusToInput();
                    // $("#name").focus();
                } else {
                    self.createNewData();
                    //$("#code").focus();
                    //$("#name").focus();
                }

                if (x == undefined && self.enableSelectionName() == true) {
                    self.enableSelectionCd(true);
                    //$("#name").focus();
                    self.focusToInput();
                } else {
                    self.enableSelectionCd(false);
                    //$("#name").focus();
                    self.focusToInput();
                }
                //$("#name").focus();
                self.focusToInput();

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
            //xu ly dialog: 
            let param = getShared('CPS017_PARAMS');

            // ドメインモデル「個人情報の選択項目」をすべて取得する
            service.getAllSelectionItems().done((itemList: Array<ISelectionItem1>) => {
                if (itemList && itemList.length > 0) {

                    self.checkCreate(true);
                    self.listItems(itemList);
                    if (param != null && param != undefined) {
                        self.isDialog(param.isDialog);
                        self.closeUp(true);
                        let isContain = _.findIndex(self.listItems(), (x) => {
                            return x.selectionItemId == param.selectionItemId;
                        });
                        if (isContain > 0) {
                            self.perInfoSelectionItem().selectionItemId(param.selectionItemId);
                        } else {
                            self.perInfoSelectionItem().selectionItemId(self.listItems()[0].selectionItemId);
                        }
                    } else {
                        self.perInfoSelectionItem().selectionItemId(self.listItems()[0].selectionItemId);
                    }

                } else {
                    self.checkCreate(false);
                    alertError({ messageId: "Msg_455" });
                    self.createNewData();
                    self.enableSelectionName(false);
                    self.perInfoSelectionItem().selectionItemId(self.listItems()[0].selectionItemId);
                }
                dfd.resolve();
            }).fail(error => {
                alertError({ messageId: "Msg_455" });
            });
            //self.checkCreate(false);

            return dfd.promise();
        }

        //新規ボタン
        createNewData() {
            let self = this;
            let selection: Selection = self.selection();
            let perSelection: any = ko.toJS(self.perInfoSelectionItem);

            nts.uk.ui.errors.clearAll();
            selection.selectionID(undefined);
            selection.externalCD('');
            selection.selectionCD('');
            selection.selectionName('');
            selection.memoSelection('');


            selection.codeType(99);
            _.defer(() => {
                selection.codeType(perSelection.characterType);
            });
            self.checkCreateaaa(true);

            if (self.enableSelectionName() == true) {
                self.enableSelectionCd(true);
            } else {
                self.enableSelectionCd(false);
            }

            self.enableCreateNew(false);
            self.enableRegister(true);
            self.enableRemove(false);
            self.enableOpenDialogB(false);

            self.focusToInput();
        }

        // enableFuctionArea
        setEnableFuctionArea(value: boolean) {
            let self = this;
            self.enableCreateNew(value);
            self.enableRemove(value);
            self.enableRegister(value);
            self.enableOpenDialogB(value);
        }

        setEnableDisplay5(value: boolean) {
            let self = this;
            self.enableCreateNew(value);
            self.enableRemove(value);
            self.enableRegister(value);
            self.enableOpenDialogB(value);

            self.enableAddUpdateHist(value);
            self.enableDelHist(value);
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
        register() {
            let self = this;
            if (self.validate()) {
                if (self.checkCreateaaa() == true) {
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
                listSelection: Array<ISelection> = self.listSelection(),
                _selectionCD = _.find(listSelection, x => x.selectionCD == currentItem.selectionCD()),
                histId = self.historySelection().histId(),
                oldIds = listSelection.map(m => m.selectionID),
                histList: HistorySelection = self.historySelection(),
                perInfoSelectionItem: SelectionItem = self.perInfoSelectionItem();
            if (!self.checkSelectionConstraints()) return;
            let oldIndex = _.find(listSelection, x => x.selectionID == currentItem.selectionID());

            currentItem.histId(self.historySelection().histId());
            let command = ko.toJS(currentItem);

            if (_selectionCD) {
                $('#code').ntsError('set', { messageId: "Msg_3" });

            } else {
                service.saveDataSelection(command).done(function() {
                    self.checkCreateaaa(false);
                    self.listSelection.removeAll();

                    service.getAllOrderItemSelection(histId)
                        .done((itemList: Array<ISelection>) => {
                            if (itemList && itemList.length) {
                                itemList.forEach(x => self.listSelection.push(x));
                                //
                                let itemSelected = _.find(itemList, item => _.indexOf(oldIds, item.selectionID) == -1);

                                if (itemSelected) {
                                    self.selection().selectionID(itemSelected.selectionID);
                                    self.enableRemove(true);
                                    self.enableOpenDialogB(true);
                                    self.enableCreateNew(true);
                                }

                                nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                                    if (itemList.length == 1) {
                                        nts.uk.ui.dialog.info({ messageId: "Msg_530" }).then(() => {
                                        });
                                    }
                                    self.listSelection.valueHasMutated();
                                    perInfoSelectionItem.selectionItemId.valueHasMutated();
                                    self.focusToInput();
                                });
                            }
                        });
                });
            }

        }

        //更新モード
        update() {
            let self = this,
                currentItem: Selection = self.selection(),
                listSelection: Array<Selection> = self.listSelection(),
                perInfoSelectionItem: SelectionItem = self.perInfoSelectionItem(),
                _selectionCD = _.find(listSelection, x => x.selectionCD == currentItem.selectionCD());
            if (!self.checkSelectionConstraints()) return;
            currentItem.histId(self.historySelection().histId());
            let command = ko.toJS(currentItem);

            service.updateDataSelection(command).done(function() {
                self.checkCreateaaa(false);
                self.listSelection.removeAll();
                service.getAllOrderItemSelection(self.historySelection().histId()).done((itemList: Array<ISelection>) => {
                    if (itemList && itemList.length) {
                        itemList.forEach(x => self.listSelection.push(x));
                    }
                    let oldIndex = _.findIndex(itemList, x => x.selectionID == currentItem.selectionID());
                    let newItem = itemList[oldIndex];
                    currentItem.selectionID(newItem.selectionID);
                    //self.focusToInput();
                    //$("#name").focus();
                });
                //self.focusToInput();
                //nts.uk.ui.dialog.alert({ messageId: "Msg_15" });
                nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(() => {
                    self.listSelection.valueHasMutated();
                    perInfoSelectionItem.selectionItemId.valueHasMutated();
                    self.focusToInput();
                });

                //$("#name").focus();
                //self.focusToInput();

            });
            //            self.listSelection.valueHasMutated();
            //            self.focusToInput();
        }

        //削除ボタン
        remove() {
            let self = this,
                items = ko.unwrap(self.listSelection),
                currentItem: Selection = self.selection(),
                listSelection: Array<Selection> = self.listSelection(),
                histList: HistorySelection = self.historySelection(),
                perInfoSelectionItem: SelectionItem = self.perInfoSelectionItem();

            currentItem.histId(self.historySelection().histId());
            let command = ko.toJS(currentItem);
            let oldIndex = _.findIndex(listSelection, x => x.selectionID == currentItem.selectionID());
            let lastIndex = items.length - 1;

            if (items.length > 0) {
                confirm({ messageId: "Msg_18" }).ifYes(() => {
                    service.removeDataSelection(command).done(function() {

                        nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(() => {
                            self.listSelection.removeAll();
                            service.getAllOrderItemSelection(self.historySelection().histId()).done((itemList: Array<ISelection>) => {
                                if (itemList && itemList.length) {
                                    itemList.forEach(x => self.listSelection.push(x));
                                    if (oldIndex == lastIndex) {
                                        oldIndex--;
                                    }
                                    let newItem = itemList[oldIndex];
                                    currentItem.selectionID(newItem.selectionID);
                                    //self.focusToInput();

                                } else {
                                    //self.createNewData();
                                    histList.histId.valueHasMutated();
                                    //self.focusToInput();
                                }
                            });
                            self.focusToInput();
                        });
                        self.listSelection.valueHasMutated();
                        perInfoSelectionItem.selectionItemId.valueHasMutated();
                    });
                }).ifNo(() => {
                    self.listItems.valueHasMutated();
                    //$("#name").focus();
                    //self.focusToInput();
                    return;
                })
            } else {
                alertError({ messageId: "Msg_521" });
                self.registerDataSelectioItem();
                self.focusToInput();
            }
        }

        // 履歴削除をする
        removeHistory() {
            let self = this;
            let perInfoSelectionItem = self.perInfoSelectionItem();
            
            let command = {
                selectionItemId : perInfoSelectionItem.selectionItemId(),
                histId : self.historySelection().histId()
            };
            
            confirm({ messageId: "Msg_18" }).ifYes(() => {
                service.removeHistory(command).done(function() {
                    perInfoSelectionItem.selectionItemId.valueHasMutated();
                    nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                });
                self.focusToInput();
            }).ifNo(() => {
                self.listItems.valueHasMutated();
                self.focusToInput();
                return;
            })
        }

        // 選択肢未登録会社へ反映する
        ReflUnrComp() {
            let self = this;
            let command = { 'selectionItemId' : self.perInfoSelectionItem().selectionItemId() };

            confirm({ messageId: "Msg_532", messageParams: ["1"] }).ifYes(() => {
                invisible();
                service.reflUnrComp(command).done(function() {
                    unblock();
                    nts.uk.ui.dialog.info({ messageId: "Msg_81" }).then(() => {
                        self.focusToInput();
                    });
                });
            }).ifNo(() => {
                return;
            })
        }

        //ダイアログC画面
        openDialogB() {
            let self = this,
                hist: HistorySelection = self.historySelection(),
                currentItem: OrderSelection = self.orderSelection();

            setShared('selectedHisId', self.historySelection().histId());
            block.invisible();
            modal('/view/cps/017/b/index.xhtml', { title: '' }).onClosed(function(): any {

                hist.histId.valueHasMutated();

                block.clear();
            });
        }

        //ダイアログC画面
        openDialogC() {
            let self = this;
            let perInfoSelectionItem = self.perInfoSelectionItem();
            let selectionItemName = perInfoSelectionItem.selectionItemName();
            let selectionItemId = perInfoSelectionItem.selectionItemId();
            let histId = self.historySelection().histId();

            setShared('CPS017C_PARAMS', { id: selectionItemId, name: selectionItemName, histId: histId });

            block.invisible();
            modal('/view/cps/017/c/index.xhtml', { title: '' }).onClosed(function(): any {
                //reload lai History:
                perInfoSelectionItem.selectionItemId.valueHasMutated();
                block.clear();
            });
        }

        //ダイアログD画面
        openDialogD() {
            let self = this;
            let perInfoSelectionItem = self.perInfoSelectionItem();
            let selectingHistory = self.historySelection();

            let param = {
                id: perInfoSelectionItem.selectionItemId(),
                name: perInfoSelectionItem.selectionItemName(),
                histId: selectingHistory.histId(),
                startDate: selectingHistory.startDate()
            }

            setShared('CPS017D_PARAMS', param);

            block.invisible();
            modal('/view/cps/017/d/index.xhtml', { title: '' }).onClosed(function(): any {
                //reload lai History:
                perInfoSelectionItem.selectionItemId.valueHasMutated();
                block.clear();
                self.focusToInput();
            });
        }
        close() {
            nts.uk.ui.windows.close();
        }

        //check constraints from cps 016
        checkSelectionConstraints() {
            let self = this,
                selCD = self.selection().selectionCD(),
                selName = self.selection().selectionName(),
                exCd = self.selection().externalCD(),

                //fibux: 23.2.2018
                selIteClass = self.perInfoSelectionItem().selectionItemClassification(),

                allValid = true;
            if (!self.constraints) return false;
            if (selCD.length > self.constraints.selectionCode) {
                allValid = false;
                $('#code').ntsError('set', getText('CPS017_21') + "は" + self.constraints.selectionCode + "桁を超えない");
            }
            if (selName.length > self.constraints.selectionName) {
                allValid = false;
                $('#name').ntsError('set', getText('CPS017_22') + "は" + self.constraints.selectionName + "桁を超えない");
            }
            if (exCd.length > self.constraints.selectionExternalCode && exCd != "") {
                allValid = false;
                $('#exCode').ntsError('set', getText('CPS017_24') + "は" + self.constraints.selectionExternalCode + "桁を超えない");
            }
            return allValid;
        }

        focusToInput(): void {
            _.defer(() => {
                if (this.checkCreateaaa())
                    $('#code').focus();
                else
                    $('#name').focus();
            });
        }
    }

    //SelectionItem
    interface ISelectionItem {
        selectionItemId: string;
        selectionItemName: string;
        formatSelection: any;
        reflectedToAllCompanies: number;

        //fixbug: 23.2.2018
        selectionItemClassification: number;
    }

    interface ISelectionItem1 {

        selectionItemId: string;
        selectionItemName: string;

        characterType: number;
        codeLength: number;
        nameLength: number;
        extraCodeLength: number;

        shareChecked: boolean;

        integrationCode?: string;
        memo?: string;

    }

    class SelectionItem {

        selectionItemId: KnockoutObservable<string> = ko.observable('');
        selectionItemName: KnockoutObservable<string> = ko.observable('');

        characterType: KnockoutObservable<number> = ko.observable(1);
        codeLength: KnockoutObservable<number> = ko.observable(null);
        nameLength: KnockoutObservable<number> = ko.observable(null);
        extraCodeLength: KnockoutObservable<number> = ko.observable(null);

        selectionItemClassification: KnockoutObservable<number> = ko.observable();

        constructor(param: ISelectionItem1) {
            let self = this;
            self.selectionItemId(param.selectionItemId || '');
            self.selectionItemName(param.selectionItemName || '');

            self.characterType(param.characterType ? 1 : 0);
            self.codeLength(param.codeLength);
            self.nameLength(param.nameLength);
            self.extraCodeLength(param.extraCodeLength);

            self.selectionItemClassification((param.shareChecked ? 0 : 1) || '');
        }
    }

    //history:
    interface IHistorySelection {
        histId?: string;
        selectionItemId?: string;
        companyId: string;
        startDate: string;
        endDate: string;
    }

    class HistorySelection {
        histId: KnockoutObservable<string> = ko.observable('');
        selectionItemId: KnockoutObservable<string> = ko.observable('');
        companyId: KnockoutObservable<string> = ko.observable('');
        startDate: KnockoutObservable<string> = ko.observable('');
        endDate: KnockoutObservable<string> = ko.observable('');

        constructor(param: IHistorySelection) {
            let self = this;
            self.histId(param.histId || '');
            self.selectionItemId(param.selectionItemId || '');
            self.companyId(param.companyId || '');
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
        codeType: number;
    }
    class Selection {
        selectionID: KnockoutObservable<string> = ko.observable('');
        histId: KnockoutObservable<string> = ko.observable('');
        selectionCD: KnockoutObservable<string> = ko.observable('');
        selectionName: KnockoutObservable<string> = ko.observable('');
        externalCD: KnockoutObservable<string> = ko.observable('');
        memoSelection: KnockoutObservable<string> = ko.observable('');
        initSelection: KnockoutObservable<number> = ko.observable();
        codeType: KnockoutObservable<number> = ko.observable(0);
        codeTypeConstraint: KnockoutObservable<string> = ko.observable('');
        constructor(param: ISelection) {
            let self = this;
            self.selectionID(param.selectionID || '');
            self.histId(param.histId || '');
            self.selectionCD(param.selectionCD || '');
            self.selectionName(param.selectionName || '');
            self.externalCD(param.externalCD || '');
            self.memoSelection(param.memoSelection || '');
            self.initSelection(param.initSelection || '');
            self.codeType(param.codeType || 0);
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
