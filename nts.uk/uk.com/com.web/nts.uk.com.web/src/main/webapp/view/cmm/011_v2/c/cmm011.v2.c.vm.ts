module nts.uk.com.view.cmm011.v2.c.viewmodel {
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;

    export class ScreenModel {
        itemList: KnockoutObservableArray<any>;
        selectedId: KnockoutObservable<number>;
        inputList: KnockoutObservableArray<InputItems>;
        listColums: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        screenMode: number = 1;

        constructor() {
            var self = this;
            self.itemList = ko.observableArray([
                new BoxModel(1, ko.computed(function () {
                        if (self.screenMode == 1)
                            return getText('CMM011-1_8');
                        if (self.screenMode == 2)
                            return getText('CMM011-2_8');
                    })
                ),
                new BoxModel(2, ko.computed(function () {
                        if (self.screenMode == 1)
                            return getText('CMM011-1_9');
                        if (self.screenMode == 2)
                            return getText('CMM011-2_9');
                    })
                )
            ]);
            self.selectedId = ko.observable(2);

            //Grid list item
            self.inputList = ko.observableArray([]);

            self.inputList.push(new InputItems("001","targetID1", "A", "カラム1", "2018/04/01", "histryID1"));
            self.inputList.push(new InputItems("002","targetID2", "A", "カラム2", "2018/04/02", "histryID2"));

            self.inputList(_.orderBy(self.inputList(),['date'],['desc']));
            //Grid list columns
            self.listColums = ko.observableArray([
                {headerText: 'id' ,key: 'id', hidden: true},
                {headerText: getText('CMM011-0_30'), key: 'code', width: 100, formatter: _.escape},
                {headerText: getText('CMM011-0_31'), key: 'name', width: 100, formatter: _.escape},
                {headerText: getText('CMM011-0_32'), key: 'date', width: 150, format: 'YYYY/MM/DD', formatter: _.escape}
            ]);
            this.currentCode = ko.observable();
            //get param
            let param = nts.uk.ui.windows.getShared("cmm001Param");
            if (param) {
                self.inputList(param.outputList);
            }
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred<any>();
            dfd.resolve();
            return dfd.promise();
        }

        Create() {
            var self = this;
            if (self.selectedId() == 2 && !self.currentCode()) {
                nts.uk.ui.dialog.info({messageId: "Msg_1504"});
            }
            else {
                if (self.currentCode()) {
                    var item = _.find(self.inputList(), x => x.id == self.currentCode());
                    setShared("cmm001v2Output", {
                        targetID: item.targetID,
                        historyID: item.historyID
                    });
                    nts.uk.ui.windows.close();
                }
                else {
                    setShared("cmm001v2Output", {targetID: null, historyID: null});
                    nts.uk.ui.windows.close();
                }
            }
        }

        Close() {
            nts.uk.ui.windows.close();
        }
    }

    class BoxModel {
        id: number;
        name: string;

        constructor(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
        }
    }

    class InputItems {
        id:string;
        targetID: string;
        code: string;
        name: string;
        date: string;
        historyID: string;

        constructor(id:string, targetID: string, code: string, name: string, date: string, historyID: string) {
            this.id=id;
            this.targetID = targetID;
            this.code = code;
            this.name = name;
            this.date = date;
            this.historyID = historyID;
        }
    }
}