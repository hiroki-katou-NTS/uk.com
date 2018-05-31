module nts.uk.com.view.cmf001.k.viewmodel {
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

        listConvertCode: KnockoutObservableArray<model.AcceptanceCodeConvert>
        selectedConvertCode: KnockoutObservable<string>
        /* screen */
        constructor() {
            var self = this;
            let firstItem = new model.AcceptanceCodeConvert("", getText('CMF001_381'), 0);
            self.listConvertCode = ko.observableArray([firstItem]);
            self.selectedConvertCode = ko.observable("");
            //ドメインモデル「受入コード変換」を取得する
            let params = getShared("CMF001kParams");
            let selectedConvertCode = params.selectedConvertCode;
            let convertCode = selectedConvertCode.dispConvertCode;
            let convertName = selectedConvertCode.dispConvertName;
            self.selectedConvertCode(convertCode);
        }
        start(): JQueryPromise<any> {
            block.invisible();
            var self = this;
            var dfd = $.Deferred();
            service.getCodeConvert().done(function(result: Array<any>) {
                if (result && result.length) {
                    let _codeConvertResult: Array<any> = _.sortBy(result, ['convertCd']);
                    let _listConvertCode: Array<model.AcceptanceCodeConvert> = _.map(_codeConvertResult, x => {
                        return new model.AcceptanceCodeConvert(x.convertCd, x.convertName, x.acceptWithoutSetting);
                    });
                    //取得した受入コード変換を「コード変換一覧」（グリッドリスト）に表示する
                    self.listConvertCode.push.apply(self.listConvertCode, _listConvertCode);
                }
                block.clear();
                dfd.resolve();
            }).fail(function(error) {
                alertError(error);
                block.clear();
                dfd.reject();
            });
            return dfd.promise();
        }
        // 選択された行のコード/名称を返す
        selectConvertCode() {
            var self = this;
            let codeConvert = new model.AcceptanceCodeConvert("", "", 0);
            if (!_.isEqual(self.selectedConvertCode(), "")){
                codeConvert = _.find(ko.toJS(self.listConvertCode), (x: model.AcceptanceCodeConvert) => x.dispConvertCode == self.selectedConvertCode());
            }
            // 選択された行のコード/名称を返す
            setShared("CMF001kOutput", { selectedConvertCodeShared: codeConvert});
            nts.uk.ui.windows.close();
        }
        // キャンセルして終了する
        cancelSelectConvertCode() {
            nts.uk.ui.windows.close();
        }
    }

}