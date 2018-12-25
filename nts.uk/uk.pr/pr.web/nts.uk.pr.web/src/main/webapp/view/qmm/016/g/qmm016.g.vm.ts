module nts.uk.pr.view.qmm016.g.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    import service = nts.uk.pr.view.qmm016.g.service;
    import model = nts.uk.pr.view.qmm016.share.model;
    export class ScreenModel {
        statementItemNameList: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedStatementItemName: KnockoutObservable<string> = ko.observable("");
        fixedElements: Array<any> = [
            { code: "M001", name: '雇用' },
            { code: "M002", name: '部門' },
            { code: "M003", name: '分類' },
            { code: "M004", name: '職位' },
            { code: "M005", name: '給与分類' },
            { code: "M006", name: '資格' },
            { code: "M007", name: '精皆勤レベル' },
            { code: "N001", name: '年齢' },
            { code: "N002", name: '勤続年数' },
            { code: "N003", name: '家族人数' }
        ];
        
        constructor() {
            
        }
        
        startPage () : JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            block.invisible();
            let params = getShared("QMM016_G_PARAMS");
            service.getAllStatementItemData().done((data: Array<any>) => {
                let fixedElements: Array<any> = self.fixedElements.map(i => {
                    return {code: i.code, name: i.name, dispname: i.code + " " + i.name}; 
                });
                let optionalElements: Array<any> = data.map(item => {
                    return {code: item.code, name: item.name, dispname: item.code + " " + item.name}; 
                });
                ko.utils.arrayPushAll(self.statementItemNameList, fixedElements);
                ko.utils.arrayPushAll(self.statementItemNameList, optionalElements);
                if (params) {
                    self.selectedStatementItemName(params.selected ? params.selected : "");
                    self.statementItemNameList(_.filter(self.statementItemNameList(), item => {return !params.otherSelected.contains(item.code)}));
                }
                dfd.resolve();
            }).fail((err) => {
                dfd.reject();
                dialog.alertError(err.message);
            }).always(() => {
                block.clear();
            });
            return dfd.promise();
        }
        
        decideSelect() {
            let self = this;
            let elementAttribute: model.IElementAttribute = {
                masterNumericClassification: null,
                fixedElement: null,
                optionalAdditionalElement: null, 
                displayName: null
            };
            let selectedElem = _.find(self.fixedElements, {code: self.selectedStatementItemName()});
            if (selectedElem) {
                elementAttribute.fixedElement = self.selectedStatementItemName();
                if (elementAttribute.fixedElement.charAt(0) == 'M')
                    elementAttribute.masterNumericClassification = model.MASTER_NUMERIC_INFORMATION.MASTER_FIELD;
                else 
                    elementAttribute.masterNumericClassification = model.MASTER_NUMERIC_INFORMATION.NUMERIC_ITEM;
                elementAttribute.displayName = selectedElem.name;
            } else {
                let selectedOpt = _.find(self.statementItemNameList(), {code: self.selectedStatementItemName()});
                elementAttribute.optionalAdditionalElement = self.selectedStatementItemName();
                elementAttribute.masterNumericClassification = model.MASTER_NUMERIC_INFORMATION.NUMERIC_ITEM;
                elementAttribute.displayName = selectedOpt.name;
            }
            setShared('QMM016_G_RES_PARAMS', { selectedElement: elementAttribute});
            nts.uk.ui.windows.close();
        }
        
        cancelSelect() {
            nts.uk.ui.windows.close();
        }
    }
}


