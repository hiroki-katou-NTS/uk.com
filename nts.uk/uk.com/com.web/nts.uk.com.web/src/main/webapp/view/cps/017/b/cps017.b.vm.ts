module nts.uk.com.view.cps017.b.viewmodel {
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import textUK = nts.uk.text;
    import block = nts.uk.ui.block;
    export class ScreenModel {
        listSelection: KnockoutObservableArray<ISelection> = ko.observableArray([]);
        selection: KnockoutObservable<Selection> = ko.observable(new Selection({ selectionID: '', histId: '' }));

        currentSelList: KnockoutObservableArray<any>;

        constructor() {
            let self = this;
            self.currentSelList = ko.observableArray([]);

        }

        //開始
        start(): JQueryPromise<any> {
            let self = this,
                currentItem: Selection = self.selection(),
                listSelection: Array<Selection> = self.listSelection(),
                selectedHisId = getShared('selectedHisId');//get histId ben screen A


            //comand: Selection = ko.toJS(currentItem);

            dfd = $.Deferred();
            nts.uk.ui.errors.clearAll();

            service.getAllOrderSetting(selectedHisId).done((itemList: Array<ISelection>) => {
                if (itemList && itemList.length > 0) {
                    itemList.forEach(x => {
                        self.listSelection.push(x)
                        if (x.initSelection === 1) {
                            self.currentSelList().push(x.selectionID);
                        }
                    });
                    //test:
                    self.currentSelList().push(itemList[0].selectionID);
//
//                    self.currentSelList().push(itemList[0].selectionID);
//                     self.currentSelList().push(itemList[3].selectionID);

                }
                dfd.resolve();
            }).fail(error => {
                alertError({ messageId: "Msg_455" });
            });

            return dfd.promise();
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
}

