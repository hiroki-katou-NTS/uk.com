module nts.uk.pr.view.qmm016.g.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    import service = nts.uk.pr.view.qmm016.g.service;
    import model = nts.uk.pr.view.qmm016.share.model;
    import getText = nts.uk.resource.getText;
    
    export class ScreenModel {
        statementItemNameList: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedStatementItemName: KnockoutObservable<string> = ko.observable("");
        fixedElements: Array<any> = [
            { code: "M001", name: getText("Enum_Element_Type_M001") },
            { code: "M002", name: getText("Enum_Element_Type_M002") },
            { code: "M003", name: getText("Enum_Element_Type_M003") },
            { code: "M004", name: getText("Enum_Element_Type_M004") },
            { code: "M005", name: getText("Enum_Element_Type_M005") },
            { code: "M006", name: getText("Enum_Element_Type_M006") },
            { code: "M007", name: getText("Enum_Element_Type_M007") },
            { code: "N001", name: getText("Enum_Element_Type_N001") },
            { code: "N002", name: getText("Enum_Element_Type_N002") },
            { code: "N003", name: getText("Enum_Element_Type_N003") }
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


