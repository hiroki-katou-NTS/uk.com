module nts.uk.at.view.kal003.a.tab {
    import model = kal003.share.model;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import windows = nts.uk.ui.windows;
    
    export class CheckAlarmTab {
        dataItems: KnockoutObservableArray<any>;
        
        constructor() {
            let self = this;
            
            self.dataItems = ko.observableArray([]);
            
            let temp = [];
            
            for(var i = 1; i <= 10; i++) {
                temp.push({ rowId : i, name: '休暇の優先順位をチェックする', message: '年休を優先して使ってください' });
            }
            
            self.dataItems = temp;
            
            $("#fixed-table2").ntsFixedTable({ height: 200 });
        }
        
        /**
         * Open dialog Kal003 B for setting the Setting Check Condition.
         * @param rowId
         */
        private btnSetting_click(rowId) {
            let self = this;
            
//            if (rowId() < 1 || rowId() > self.listWorkRecordExtractingConditions().length) {
//                return;
//            }
//            
//            let workRecordExtractingCondition = self.listWorkRecordExtractingConditions()[rowId() - 1];
//            
//            if (workRecordExtractingCondition) {
//                self.showDialogKal003B(workRecordExtractingCondition, rowId());
//            }
            
            self.showDialogKal003B(rowId);
        }
        
        /**
         * Open dialog Kal003B
         * @param errorAlamCondition
         * @param rowId
         */
//        private showDialogKal003B(workRecordExtractingCondition: model.WorkRecordExtractingCondition, rowId: number) {
        private showDialogKal003B(rowId: number) {
            let self = this;
//            let sendData = ko.toJS(workRecordExtractingCondition);
            
//            sendData = shareutils.convertArrayOfWorkRecordExtractingConditionToJS(sendData, workRecordExtractingCondition);
//
//            windows.setShared('inputKal003b', sendData);
            
            windows.sub.modal('/view/kal/003/b1/index.xhtml', { height: 600, width: 1020 }).onClosed(function(): any {
                // get data from share window    
//                let data = windows.getShared('outputKal003b');
//                if (data != null && data != undefined) {
//                    if (rowId > 0 && rowId <= self.listWorkRecordExtractingConditions().length) {
//                        self.listWorkRecordExtractingConditions()[rowId - 1] = shareutils.convertTransferDataToWorkRecordExtractingCondition(data);
//                        self.listWorkRecordExtractingConditions.valueHasMutated();
//                    }
//                }
//                block.clear();
            });
        }
    }
}