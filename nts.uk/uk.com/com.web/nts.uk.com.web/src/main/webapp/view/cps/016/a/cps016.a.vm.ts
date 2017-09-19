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
        formatSelection: KnockoutObservable<FormatSelection> = ko.observable(new FormatSelection(undefined));

        constructor() {
            let self = this,
                perInfoSelectionItem: SelectionItem = self.perInfoSelectionItem(),
                
                formatSelection: FormatSelection = self.formatSelection(),
                _formatSelection: ISelectionItem = perInfoSelectionItem.formatSelection();// sai thi fai:

            perInfoSelectionItem.selectionItemId.subscribe(x => {
                if (x) {
                    service.getPerInfoSelectionItem(x).done((_perInfoSelectionItem: ISelectionItem) => {
                        if (_perInfoSelectionItem) {
                            perInfoSelectionItem.selectionItemName(_perInfoSelectionItem.selectionItemName);
                            perInfoSelectionItem.memo(_perInfoSelectionItem.memo);
                            perInfoSelectionItem.integrationCode(_perInfoSelectionItem.integrationCode);
                            
                            formatSelection.selectionCode(_formatSelection.
                        }
                    });
                }
            });

//            perInfoSelectionItem.formatSelection.subscribe(x => {
//                let _format = self.formatSelection(),
//                    _iformat = param.formatSelection;
//            });



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
                } dfd.resolve();
            });

            return dfd.resolve();
        }

        register() {
            let self = this;

        }

        add() {
            let self = this;
        }

        remove() {
            let self = this;
        }
    }

    interface ISelectionItem {
        selectionItemId: string;
        selectionItemName: string;
        memo: string;
        selectionItemClassification: number;
        contractCode: string;
        integrationCode: string;
        formatSelection: IFormatSelection;

        //formatSelection: SELECTION_ENUM;
    }

    interface IFormatSelection {
        selectionCode: number;
        selectionCodeCharacter: number;
        selectionName: number;
        selectionExternalCode: number;
    }

    class FormatSelection {
        selectionCode: KnockoutObservable<number> = ko.observable(0);
        selectionCodeCharacter: KnockoutObservable<number> = ko.observable(0);
        selectionName: KnockoutObservable<number> = ko.observable(0);
        selectionExternalCode: KnockoutObservable<number> = ko.observable(0);

        constructor(param: IFormatSelection) {
            let self = this;
            if (param) {
                self.selectionCode(param.selectionCode || 0);
                self.selectionCodeCharacter(param.selectionCodeCharacter || 0);
                self.selectionName(param.selectionName || 0);
                self.selectionExternalCode(param.selectionExternalCode || 0);
            }
        }
    }

    /*
    enum SELECTION_ENUM {
        NUMBER = 1,
        STRING = 2,
        BOOLEAN = 3
    }
    */

    class SelectionItem {
        selectionItemId: KnockoutObservable<string> = ko.observable('');
        selectionItemName: KnockoutObservable<string> = ko.observable('');
        memo: KnockoutObservable<string> = ko.observable('');
        selectionItemClassification: KnockoutObservable<number> = ko.observable('');
        contractCode: KnockoutObservable<string> = ko.observable('');
        integrationCode: KnockoutObservable<string> = ko.observable('');

        formatSelection: KnockoutObservable<FormatSelection> = ko.observable(new FormatSelection(undefined));
        //formatSelection: KnockoutObservable<forSel> = ko.observable(SELECTION_ENUM.NUMBER);

        constructor(param: ISelectionItem) {
            let self = this;
            self.selectionItemId(param.selectionItemId || '');
            self.selectionItemName(param.selectionItemName || '');
            self.memo(param.memo || '');
            self.selectionItemClassification(param.selectionItemClassification || '');
            self.contractCode(param.contractCode || '');
            self.integrationCode(param.integrationCode || '');

            let _format = self.formatSelection(),
                _iformat = param.formatSelection;

            _format.selectionCode(_iformat.selectionCode);
            _format.selectionCodeCharacter(_iformat.selectionCodeCharacter);
            _format.selectionName(_iformat.selectionName);
            _format.selectionExternalCode(_iformat.selectionExternalCode);
            //self.formatSelection(new FormatSelection(param.formatSelection));
        }
    }
}