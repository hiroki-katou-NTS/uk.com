module nts.uk.com.view.cps016.a.viewmodel {
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import textUK = nts.uk.text;
    import block = nts.uk.ui.block;
    export class ScreenModel {
        listItems: KnockoutObservableArray<ISelectionItem> = ko.observableArray([]);
        perInfoSelectionItem: KnockoutObservable<SelectionItem> = ko.observable(new SelectionItem({ selectionItemId: '', selectionItemName: '' }));
        rulesFirst: KnockoutObservableArray<IRule>;
        checkCreate: KnockoutObservable<boolean>;
        
        constructor() {
            let self = this,
                perInfoSelectionItem: SelectionItem = self.perInfoSelectionItem(),
                formatSelection = perInfoSelectionItem.formatSelection();
            self.checkCreate = ko.observable(true);
            self.rulesFirst = ko.observableArray([
                { id: 0, name: "数値型" },
                { id: 1, name: "英数型" }
            ]);

            //selectionItemIdのsubscribe
            perInfoSelectionItem.selectionItemId.subscribe(x => {
                if (x) {
                    nts.uk.ui.errors.clearAll();
                    service.getPerInfoSelectionItem(x).done((_perInfoSelectionItem: ISelectionItem) => {
                        if (_perInfoSelectionItem) {
                            perInfoSelectionItem.selectionItemName(_perInfoSelectionItem.selectionItemName);
                            perInfoSelectionItem.memo(_perInfoSelectionItem.memo);
                            perInfoSelectionItem.integrationCode(_perInfoSelectionItem.integrationCode);
                            perInfoSelectionItem.selectionItemClassification(_perInfoSelectionItem.selectionItemClassification === 1 ? true : false);

                            let iformat = _perInfoSelectionItem.formatSelection;
                            formatSelection.selectionCode(iformat.selectionCode);
                            formatSelection.selectionCodeCharacter(iformat.selectionCodeCharacter);
                            formatSelection.selectionName(iformat.selectionName);
                            formatSelection.selectionExternalCode(iformat.selectionExternalCode);
                        }
                    });
                }
                self.checkCreate(false);
            });
        }

        //開始
        start(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();

            nts.uk.ui.errors.clearAll();
            service.getAllSelectionItems().done((itemList: Array<ISelectionItem>) => {
                if (itemList && itemList.length) {
                    itemList.forEach(x => self.listItems.push(x));
                    self.perInfoSelectionItem().selectionItemId(self.listItems()[0].selectionItemId);
                } else {
                    self.registerDataSelectioItem();
                }
                dfd.resolve();
            });

            return dfd.promise();
        }

        //新規ボタン
        registerDataSelectioItem() {
            let self = this,
                perInfoSelectionItem: SelectionItem = self.perInfoSelectionItem(),
                formatSelection = perInfoSelectionItem.formatSelection();

            nts.uk.ui.errors.clearAll();
            perInfoSelectionItem.selectionItemName('');
            perInfoSelectionItem.memo('');
            perInfoSelectionItem.integrationCode('');
            perInfoSelectionItem.selectionItemClassification(false);
            formatSelection.selectionCode('');
            formatSelection.selectionName('');
            formatSelection.selectionExternalCode('');
            formatSelection.selectionCodeCharacter(false);
            self.checkCreate(true);
        }

        validate() {
            $(".nts-editor").trigger("validate");
            if (nts.uk.ui.errors.hasError()) {
                return false;
            }
            return true;
        }

        //登録ボタン
        addDataSelectioItem() {
            var self = this;

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
                currentItem: SelectionItem = self.perInfoSelectionItem(),
                listItems: Array<SelectionItem> = self.listItems(),
                formatSelection = currentItem.formatSelection(),
                command = ko.toJS(currentItem),// truyen vao tat ca thuoc tinh cua 1 Item: Su dung ToJS()
                _selectionItemName = _.find(listItems, x => x.selectionItemName == currentItem.selectionItemName());

            service.saveDataSelectionItem(command).done(function(selectId) {
                self.listItems.removeAll();
                service.getAllSelectionItems().done((itemList: Array<ISelectionItem>) => {
                    if (itemList && itemList.length) {
                        itemList.forEach(x => self.listItems.push(x));
                    }
                });
                self.listItems.valueHasMutated();
                self.perInfoSelectionItem().selectionItemId(selectId);
            }).fail(error => {
                alertError({ messageId: "Msg_513" });
            });
        }

        //更新モード
        update() {
            let self = this,
                currentItem: SelectionItem = self.perInfoSelectionItem(),
                formatSelection = currentItem.formatSelection(),
                oldIndex = _.findIndex(self.listItems(), function(o) { return o.selectionItemId == currentItem.selectionItemId(); }),
                command = ko.toJS(currentItem);

            service.updateDataSelectionItem(command).done(function() {
                self.listItems.removeAll();
                confirm({ messageId: "Msg_15" }).ifYes(() => {
                    service.getAllSelectionItems().done((itemList: Array<ISelectionItem>) => {
                        if (itemList && itemList.length) {
                            itemList.forEach(x => self.listItems.push(x));
                        }

                        let newItem = itemList[oldIndex];
                        currentItem.selectionItemId(newItem.selectionItemId);
                    });
                    self.listItems.valueHasMutated();
                }).ifNo(() => {
                    return;
                })
            });
        }

        //削除ボタン
        removeDataSelectioItem() {
            let self = this,
                items = ko.unwrap(self.listItems),
                currentItem: SelectionItem = self.perInfoSelectionItem(),
                formatSelection = currentItem.formatSelection(),
                oldIndex = _.findIndex(self.listItems(), function(o) { return o.selectionItemId == currentItem.selectionItemId(); }),
                command = ko.toJS(currentItem),
                lastIndex = items.length - 1;

            if (items.length > 0) {
                confirm({ messageId: "Msg_551" }).ifYes(() => {
                    service.removeDataSelectionItem(command).done(function() {
                        self.listItems.removeAll();
                        service.getAllSelectionItems().done((itemList: Array<ISelectionItem>) => {
                            if (itemList && itemList.length) {
                                itemList.forEach(x => self.listItems.push(x));
                                if (oldIndex == lastIndex) {
                                    oldIndex--;
                                }
                                let newItem = itemList[oldIndex];
                                currentItem.selectionItemId(newItem.selectionItemId);
                            } else {
                                self.registerDataSelectioItem();
                            }
                        });
                        self.listItems.valueHasMutated();
                    });
                }).ifNo(() => {
                    return;
                })
            } else {
                alert('Not data!!');
                self.registerDataSelectioItem();
            }
        }
    }

    interface ISelectionItem {
        selectionItemId: string;
        selectionItemName: string;
        memo?: string;
        selectionItemClassification?: number;
        contractCode?: string;
        integrationCode?: string;
        formatSelection?: IFormatSelection;
    }

    class SelectionItem {
        selectionItemId: KnockoutObservable<string> = ko.observable('');
        selectionItemName: KnockoutObservable<string> = ko.observable('');
        memo: KnockoutObservable<string> = ko.observable('');
        selectionItemClassification: KnockoutObservable<boolean> = ko.observable(false);
        contractCode: KnockoutObservable<string> = ko.observable('');
        integrationCode: KnockoutObservable<string> = ko.observable('');
        formatSelection: KnockoutObservable<FormatSelection> = ko.observable(new FormatSelection(undefined));

        constructor(param: ISelectionItem) {
            let self = this;
            self.selectionItemId(param.selectionItemId || '');
            self.selectionItemName(param.selectionItemName || '');
            self.memo(param.memo || '');
            self.selectionItemClassification(param.selectionItemClassification === 1 ? true : false);
            self.contractCode(param.contractCode || '');
            self.integrationCode(param.integrationCode || '');
            
            let _format = self.formatSelection(),
                _iformat = param.formatSelection;
            if (_iformat) {
                _format.selectionCode(_iformat.selectionCode);
                _format.selectionCodeCharacter(_iformat.selectionCodeCharacter);
                _format.selectionName(_iformat.selectionName);
                _format.selectionExternalCode(_iformat.selectionExternalCode);
            }
        }
    }

    interface IFormatSelection {
        selectionCode: number;
        selectionCodeCharacter?: number;
        selectionName: number;
        selectionExternalCode: number;
    }

    class FormatSelection {
        selectionCode: KnockoutObservable<number> = ko.observable('');
        selectionCodeCharacter: KnockoutObservable<boolean> = ko.observable(false);
        selectionName: KnockoutObservable<number> = ko.observable('');
        selectionExternalCode: KnockoutObservable<number> = ko.observable('');

        constructor(param: IFormatSelection) {
            let self = this;
            if (param) {
                self.selectionCode(param.selectionCode || '');
                self.selectionCodeCharacter(param.selectionCodeCharacter === 1 ? true : false);
                self.selectionName(param.selectionName || '');
                self.selectionExternalCode(param.selectionExternalCode || '');
            }
        }
    }

    interface IRule {
        id: number;
        name: string;
    }
}