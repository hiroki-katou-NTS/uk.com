module nts.uk.pr.view.qmm016.g.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    import service = nts.uk.pr.view.qmm016.g.service;
    import model = nts.uk.pr.view.qmm016.share.model;
    export class ScreenModel {
        statementItemNameList: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedStatementItemName: KnockoutObservable<string> = ko.observable(null);
        fixedElement: Array<any>;
        constructor() {
        }
        startPage () : JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            block.invisible();
            // カテゴリ区分＝勤怠項目 (5)
            // 廃止区分＝廃止しない (false)
            service.getAllStatementItemData(5, false).done(function(data) {
                let fixedElementObj = nts.uk.pr.view.qmm016.share.model.ELEMENT_TYPE;
                let fixedElement: Array<any> = Object.keys(fixedElementObj).map(key => new model.StringItemModel(key, key + " " + fixedElementObj[key]));
                let optionalElement: Array<any> = data.map(item => new model.StringItemModel(item.itemNameCd, item.itemNameCd + " " + item.name));
                self.fixedElement = fixedElement;
                self.statementItemNameList(fixedElement.concat(optionalElement));
                block.clear();
                dfd.resolve();
            }).fail(function(err) {
                dfd.reject();
                block.clear();
                dialog.alertError(err.message);
            });
            return dfd.promise();
        }
        decideSelect() {
            let self = this;
            let elementAttribute = {
                masterNumericClassification: null,
                fixedElement: null,
                optionalAdditionalElement: null
            };
            if (_.find(self.fixedElement, {name: self.selectedStatementItemName()})) {
                elementAttribute.fixedElement = self.selectedStatementItemName();
                elementAttribute.masterNumericClassification = model.MASTER_NUMERIC_INFORMATION.MASTER_FIELD;
            } else {
                // first 4 digit
                elementAttribute.optionalAdditionalElement = self.selectedStatementItemName().substring(0, 4);
                elementAttribute.masterNumericClassification = model.MASTER_NUMERIC_INFORMATION.NUMERIC_ITEM;
            }
            setShared('QMM016_G_RES_PARAMS', { selectedElement: elementAttribute});
            nts.uk.ui.windows.close();
        }
        cancelSelect() {
            nts.uk.ui.windows.close();
        }
    }
}


