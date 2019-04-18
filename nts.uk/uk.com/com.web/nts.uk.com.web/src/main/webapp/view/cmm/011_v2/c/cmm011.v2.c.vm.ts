module nts.uk.com.view.cmm011.v2.c.viewmodel {
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        itemList: KnockoutObservableArray<any>;
        selectedId: KnockoutObservable<number>;
        inputList: KnockoutObservableArray<InputItems>;
        listColums: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        screenMode: number = 0;

        constructor() {
            let self = this;
            self.inputList = ko.observableArray([]);
            let params = getShared("CMM011DParams");
            if (params) {
                self.screenMode = params.initMode;
                self.inputList(params.listDuplicate);
                self.inputList(_.orderBy(self.inputList(),['date'],['desc']));
            }
            self.itemList = ko.observableArray([
                new BoxModel(1, self.screenMode == 0 ? getText('CMM011_208') : getText('CMM011_308')),
                new BoxModel(2, self.screenMode == 0 ? getText('CMM011_209') : getText('CMM011_309'))
            ]);
            self.selectedId = ko.observable(2);
         
            //Grid list columns
            self.listColums = ko.observableArray([
                {headerText: 'historyId' ,key: 'historyId', hidden: true},
                {headerText: getText('CMM011_130'), key: 'targetCode', width: 100, formatter: _.escape},
                {headerText: getText('CMM011_131'), key: 'targetName', width: 100, formatter: _.escape},
                {headerText: getText('CMM011_132'), key: 'deleteDate', width: 150, format: 'YYYY/MM/DD', formatter: _.escape}
            ]);
            this.currentCode = ko.observable(null);
        }

        create() {
            let self = this;
            if (self.selectedId() == 2 && !self.currentCode()) {
                nts.uk.ui.dialog.info({messageId: "Msg_1504"});
            }
            else {
                if (self.currentCode()) {
                    let item = _.find(self.inputList(), x => x.historyId == self.currentCode());
                    setShared("CMM011CParams", {
                        targetId: item.targetId,
                        historyId: item.historyId
                    });
                    nts.uk.ui.windows.close();
                }
                else {
                    setShared("CMM011CParams", {targetId: null, historyId: null});
                    nts.uk.ui.windows.close();
                }
            }
        }

        close() {
            nts.uk.ui.windows.close();
        }
    }

    class BoxModel {
        id: number;
        name: string;

        constructor(id, name) {
            let self = this;
            self.id = id;
            self.name = name;
        }
    }

    class InputItems {
        targetId: string;
        targetCode: string;
        targetName: string;
        deleteDate: string;
        historyId: string;

        constructor(targetId: string, code: string, name: string, date: string, historyId: string) {
            this.targetId = targetId;
            this.targetCode = code;
            this.targetName = name;
            this.deleteDate = date;
            this.historyId = historyId;
        }
    }
}