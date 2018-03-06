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
            let firstItem = new model.AcceptanceCodeConvert("", getText('CMF001_268'), 0);
            self.listConvertCode = ko.observableArray([firstItem]);
            self.selectedConvertCode = ko.observable("");
            let params = getShared("CMF001kParams");
            if (!nts.uk.util.isNullOrUndefined(params)) {
                let selectedConvertCode = params.selectedConvertCode;
                let convertCode = selectedConvertCode.dispConvertCode;
                let convertName = selectedConvertCode.dispConvertName;
                self.selectedConvertCode(convertCode);
            }
        }
        displayScreen() {
            var self = this;
            //ドメインモデル「受入コード変換」を取得する
            service.getCodeConvert().done(function(result: Array<any>) {
                let x = result;
                if (result && result.length) {
                    let _listConvertCode: Array<model.AcceptanceCodeConvert> = _.map(result, x => {
                        return new model.AcceptanceCodeConvert(x.convertCd, x.convertName, x.acceptWithoutSetting);
                    });
                    let abc = _listConvertCode;
                    //取得した受入コード変換を「コード変換一覧」（グリッドリスト）に表示する
                    self.listConvertCode.push.apply(self.listConvertCode, _listConvertCode);
                }
            }).fail(function(error) {
                alertError(error);
            });
        }
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.displayScreen();
            dfd.resolve();
            return dfd.promise();
        }
        selectConvertCode() {
            var self = this;
            let codeSelected = ko.toJS(self.selectedConvertCode);
            let codeConvert = new model.AcceptanceCodeConvert("", "", 0);
            if (!_.isEqual(codeSelected, "")){
                codeConvert = _.find(ko.toJS(self.listConvertCode), (x: model.AcceptanceCodeConvert) => x.dispConvertCode == codeSelected);
            }
            setShared("CMF001kOutput", { selectedConvertCodeShared: codeConvert});
            nts.uk.ui.windows.close();
        }
        cancelSelectConvertCode() {
            nts.uk.ui.windows.close();
        }
    }

}