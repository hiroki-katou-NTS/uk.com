module nts.uk.com.view.cmf001.e.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import model = cmf001.share.model;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        listCsvItem: KnockoutObservableArray<model.MappingListData> = ko.observableArray([]);
        selectedCsvItemNumber: KnockoutObservable<number> = ko.observable(null);
        constructor() {
            var self = this;
            let params = getShared("CMF001eParams");
            if (!nts.uk.util.isNullOrUndefined(params)) {
                let listCsvItem = params.listCsvItem;
                let selectedCsvItemNumber = params.selectedCsvItemNumber;
                if (listCsvItem.length > 0){
                    // リスト表示するためのドメインモデル「マッピング用一覧データ（Work）」（D画面で生成）から下記を取得する
                    self.listCsvItem.push.apply(self.listCsvItem, listCsvItem);
                    // 一覧表の該当行にフォーカスする
                    if (!nts.uk.util.isNullOrUndefined(selectedCsvItemNumber)) {
                        self.selectedCsvItemNumber(selectedCsvItemNumber);
                    }
                }else{
                    // データ0件＝CSVが未読込の場合
                    alertError({ messageId: "Msg_905"});
                }
            }else{
                alertError({ messageId: "Msg_905"});
            }
        }
        cancel() {
            nts.uk.ui.windows.close();
        }
        save() {
            var self = this;
            let selectedCsvItem = _.find(ko.toJS(self.listCsvItem), (x: model.MappingListData) => x.dispCsvItemNumber == ko.toJS(self.selectedCsvItemNumber));
            setShared("CMF001eOutput", {selectedCsvItem: ko.toJS(selectedCsvItem)});
            nts.uk.ui.windows.close();
        }
    }
}