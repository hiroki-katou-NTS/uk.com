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
        listCsvItemHeight: KnockoutObservable<number> = ko.observable(391);
        constructor() {
            var self = this;
            let params = getShared("CMF001eParams");
            let listCsvItem = params.listCsvItem;
            let selectedCsvItemNumber = params.selectedCsvItemNumber;
            if (listCsvItem.length > 0){
                // リスト表示するためのドメインモデル「マッピング用一覧データ（Work）」（D画面で生成）から下記を取得する
                self.listCsvItem.push.apply(self.listCsvItem, listCsvItem);
                // 一覧表の該当行にフォーカスする
                if (!nts.uk.util.isNullOrUndefined(selectedCsvItemNumber)) {
                    self.selectedCsvItemNumber(selectedCsvItemNumber);
                }else{
                    self.selectedCsvItemNumber(listCsvItem[0].csvItemNumber);
                }
            }else{
                // データ0件＝CSVが未読込の場合
                alertError({ messageId: "Msg_905"});
            }
            // クロムブラウザを検出する
            let browserInfo = navigator.userAgent.toLowerCase();
            if( browserInfo.indexOf('chrome') > -1 && browserInfo .indexOf('edge') == -1 ){
                self.listCsvItemHeight(265);
            }
            else {
                self.listCsvItemHeight(270);
            }
        }
        // キャンセルして終了する
        cancel() {
            nts.uk.ui.windows.close();
        }
        // CSVファイルの項目を設定して戻る
        save() {
            var self = this;
            let selectedCsvItem = _.find(self.listCsvItem(), (x: model.MappingListData) => x.csvItemNumber == self.selectedCsvItemNumber());
            setShared("CMF001eOutput", {selectedCsvItem: selectedCsvItem});
            nts.uk.ui.windows.close();
        }
    }
}