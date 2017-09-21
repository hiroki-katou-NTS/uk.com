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
        rulesFirst: KnockoutObservableArray<IRule> = ko.observableArray([]);

        checkCreate: KnockoutObservable<boolean>;

        constructor() {
            let self = this,
                perInfoSelectionItem: SelectionItem = self.perInfoSelectionItem(),
                formatSelection = perInfoSelectionItem.formatSelection(),
                classs = self.rulesFirst;

            /* Check add/update*/
            self.checkCreate = ko.observable(true);

            classs([
                { id: 1, name: "数値型" },
                { id: 2, name: "英数型" }
            ]);

            perInfoSelectionItem.selectionItemId.subscribe(x => {
                if (x) {
                    service.getPerInfoSelectionItem(x).done((_perInfoSelectionItem: ISelectionItem) => {
                        if (_perInfoSelectionItem) {
                            perInfoSelectionItem.selectionItemName(_perInfoSelectionItem.selectionItemName);
                            perInfoSelectionItem.memo(_perInfoSelectionItem.memo);
                            perInfoSelectionItem.integrationCode(_perInfoSelectionItem.integrationCode);

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

            /*
            perInfoSelectionItem.formatSelection.subscribe(x => {
                if (x == SELECTION_ENUM.NUMBER) {
                    
                } else if (x == SELECTION_ENUM.STRING) {
                    
                }
            });
            */
        }

        start(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();

            self.listItems.removeAll();
            service.getAllSelectionItems().done((itemList: Array<ISelectionItem>) => {
                if (itemList && itemList.length) {
                    itemList.forEach(x => self.listItems.push(x));
                } else {
                    self.registerDataSelectioItem();
                }
                self.perInfoSelectionItem().selectionItemId(self.listItems()[0].selectionItemId);
                dfd.resolve();
            });

            return dfd.resolve();
        }

        registerDataSelectioItem() {
            let self = this,
                perInfoSelectionItem: SelectionItem = self.perInfoSelectionItem(),
                formatSelection = perInfoSelectionItem.formatSelection();

            nts.uk.ui.errors.clearAll();
            perInfoSelectionItem.selectionItemName('');
            perInfoSelectionItem.memo('');
            perInfoSelectionItem.integrationCode('');
            formatSelection.selectionCode('');
            formatSelection.selectionName('');
            formatSelection.selectionExternalCode('');

            self.checkCreate(true);
        }

        addDataSelectioItem() {
            var self = this;
            if (self.checkCreate() == true) {
                self.add();
            } else {
                self.update();
            }
        }

        add() {
            let self = this,
                dfd = $.Deferred(),
                currentItem: SelectionItem = self.perInfoSelectionItem(),
                formatSelection = currentItem.formatSelection(),
                command = {
                    selectionItemId: currentItem.selectionItemId(),
                    selectionItemName: currentItem.selectionItemName(),
                    memo: currentItem.memo(),
                    selectionItemClassification: currentItem.selectionItemClassification(),
                    contractCode: currentItem.contractCode(),
                    integrationCode: currentItem.integrationCode(),
                    formatSelection: {
                        selectionCode: currentItem.formatSelection().selectionCode(),
                        selectionCodeCharacter: currentItem.formatSelection().selectionCodeCharacter(),
                        selectionName: currentItem.formatSelection().selectionName(),
                        selectionExternalCode: currentItem.formatSelection().selectionExternalCode()
                    }
                };

            service.saveDataSelectionItem(command).done(function() {
                service.getAllSelectionItems().done((itemList: Array<ISelectionItem>) => {
                    if (itemList && itemList.length) {
                        itemList.forEach(x => self.listItems.push(x));
                    }
                    self.listItems.removeAll();
                });
                self.listItems.valueHasMutated();
            });
            self.perInfoSelectionItem().selectionItemId(self.listItems()[0].selectionItemId);
            return dfd.resolve();
        }

        update() {
            //alert('update');
            let self = this,
                dfd = $.Deferred(),
                currentItem: SelectionItem = self.perInfoSelectionItem(),
                formatSelection = currentItem.formatSelection(),
                command = {
                    selectionItemId: currentItem.selectionItemId(),
                    selectionItemName: currentItem.selectionItemName(),
                    memo: currentItem.memo(),
                    selectionItemClassification: currentItem.selectionItemClassification(),
                    contractCode: currentItem.contractCode(),
                    integrationCode: currentItem.integrationCode(),
                    formatSelection: {
                        selectionCode: currentItem.formatSelection().selectionCode(),
                        selectionCodeCharacter: currentItem.formatSelection().selectionCodeCharacter(),
                        selectionName: currentItem.formatSelection().selectionName(),
                        selectionExternalCode: currentItem.formatSelection().selectionExternalCode()
                    }
                };

            service.updateDataSelectionItem(command).done(function() {
                service.getAllSelectionItems().done((itemList: Array<ISelectionItem>) => {
                    if (itemList && itemList.length) {
                        itemList.forEach(x => self.listItems.push(x));
                    }
                    //self.listItems.removeAll();
                });
                self.listItems.valueHasMutated();
            });
            self.perInfoSelectionItem().selectionItemId(self.listItems()[0].selectionItemId);
            return dfd.resolve();
        }


        removeDataSelectioItem() {
            let self = this;
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
        //formatSelection: SELECTION_ENUM;
        enable: KnockoutObservable<boolean>;
    }

    class SelectionItem {
        selectionItemId: KnockoutObservable<string> = ko.observable('');
        selectionItemName: KnockoutObservable<string> = ko.observable('');
        memo: KnockoutObservable<string> = ko.observable('');
        selectionItemClassification: KnockoutObservable<number> = ko.observable('');
        contractCode: KnockoutObservable<string> = ko.observable('');
        integrationCode: KnockoutObservable<string> = ko.observable('');
        formatSelection: KnockoutObservable<FormatSelection> = ko.observable(new FormatSelection(undefined));
        enable: KnockoutObservable<boolean> = ko.observable(true);
        //formatSelection: KnockoutObservable<forSel> = ko.observable(SELECTION_ENUM.NUMBER);

        constructor(param: ISelectionItem) {
            let self = this;
            self.selectionItemId(param.selectionItemId || '');
            self.selectionItemName(param.selectionItemName || '');
            self.memo(param.memo || '');
            self.selectionItemClassification(param.selectionItemClassification || '');
            self.contractCode(param.contractCode || '');
            self.integrationCode(param.integrationCode || '');
            self.enable(param.enable || '');


            let _format = self.formatSelection(),
                _iformat = param.formatSelection;

            if (_iformat) {
                _format.selectionCode(_iformat.selectionCode);
                _format.selectionCodeCharacter(_iformat.selectionCodeCharacter);
                _format.selectionName(_iformat.selectionName);
                _format.selectionExternalCode(_iformat.selectionExternalCode);
            }
            //self.formatSelection(new FormatSelection(param.formatSelection));
        }
    }

    interface IFormatSelection {
        selectionCode: number;
        selectionCodeCharacter: number;
        selectionName: number;
        selectionExternalCode: number;
    }

    class FormatSelection {
        selectionCode: KnockoutObservable<number> = ko.observable('');
        selectionCodeCharacter: KnockoutObservable<number> = ko.observable('');
        selectionName: KnockoutObservable<number> = ko.observable('');
        selectionExternalCode: KnockoutObservable<number> = ko.observable('');

        constructor(param: IFormatSelection) {
            let self = this;
            if (param) {
                self.selectionCode(param.selectionCode || '');
                self.selectionCodeCharacter(param.selectionCodeCharacter || '');
                self.selectionName(param.selectionName || '');
                self.selectionExternalCode(param.selectionExternalCode || '');
            }
        }
    }

    interface IRule {
        id: number;
        name: string;
    }

    /*
    enum SELECTION_ENUM {
        NUMBER = 1,
        STRING = 2,
        BOOLEAN = 3
    }
    */
}