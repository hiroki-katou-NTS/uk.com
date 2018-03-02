module nts.uk.com.view.cmf001.l.viewmodel {
    import model = nts.uk.com.view.cmf001.share.model;
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    
    export class ScreenModel {
        compareItems: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getCompareTypes());
        acceptScreenConditionSetting: KnockoutObservable<model.AcceptScreenConditionSetting>;
        selectedDataType: number;
        selectComparisonCondition: KnockoutObservable<string>;
        screenMode: number;
        constructor() {
            var self = this;  
            let params = getShared("CMF001lParams");
            self.screenMode = 0;
            self.selectedDataType = params.dataType;
            if (params.condition) {
                let condition = params.condition;
                self.acceptScreenConditionSetting = ko.observable(new model.AcceptScreenConditionSetting(condition.receiptItemName, condition.selectComparisonCondition, condition.timeConditionValue2, condition.timeConditionValue1, condition.timeMomentConditionValue2, condition.timeMomentConditionValue1, condition.dateConditionValue2, condition.dateConditionValue1, condition.characterConditionValue2, condition.characterConditionValue1, condition.numberConditionValue2, condition.numberConditionValue1));
            } else {
                self.acceptScreenConditionSetting = ko.observable(new model.AcceptScreenConditionSetting('条件値', 0 , null, null, null, null, null, null,null, null, null, null));
            }
        }
        
         /**
         * Close dialog.
         */
        cancelSetting(): void {
            setShared('CMF001lCancel', true);
            nts.uk.ui.windows.close();
        }
        
        saveSetting(){
            var self = this;
            setShared('CMF001lOutput', ko.toJS(self.acceptScreenConditionSetting), true);
            nts.uk.ui.windows.close();
            
        }
    }
}