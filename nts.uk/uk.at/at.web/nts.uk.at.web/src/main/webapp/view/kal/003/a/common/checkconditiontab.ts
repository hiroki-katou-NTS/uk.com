module nts.uk.at.view.kal003{
    import windows  = nts.uk.ui.windows;
    import getText  = nts.uk.resource.getText;
    import block    = nts.uk.ui.block;
    import model    = nts.uk.at.view.kal003.share.model;
    import shareutils = nts.uk.at.view.kal003.share.kal003utils;

    export class CheckConditionTab {
        listWorkRecordExtractingConditions :    KnockoutObservableArray<model.WorkRecordExtractingCondition> = ko.observableArray([]);
        columnsWorkRecordExtractingCondition:   KnockoutObservableArray<NtsGridListColumn>;
        isAllCheckCondistion :                  KnockoutObservable<boolean> =  ko.observable(false);
        currentRowSelected  :                   KnockoutObservable<number>  =  ko.observable(0);

        constructor() {
            let self = this;
            self.isAllCheckCondistion.subscribe((data) => {
                alert("isAllCheckCondistion:" + data);
                for(var i = 0; i < self.listWorkRecordExtractingConditions().length; i++) {
                    self.listWorkRecordExtractingConditions()[i].useAtr (data);
                }
            });
            /*
            self.isAllCheckCondistion = ko.pureComputed({

                read: function () {
                    alert("read");
                    return self.listWorkRecordExtractingConditions().length === self.listWorkRecordExtractingConditions().filter(
                            function(item) {return item.useAtr();}).length;
                },
                write: function (value) {
                    alert("write");
                    for(var i = 0; i < self.listWorkRecordExtractingConditions().length; i++) {
                        self.listWorkRecordExtractingConditions()[i].useAtr(value);
                    }
                },
                //owner: this
            });
            */
        }
        
        /**
         * Init Check Condition Tab
         * @param listWorkRecordExtractingConditions
         */
        initialCheckConditionTab(listWorkRecordExtractingConditions : Array<model.WorkRecordExtractingCondition>) {
            let self = this;
            self.listWorkRecordExtractingConditions.removeAll();
            self.listWorkRecordExtractingConditions(listWorkRecordExtractingConditions);
            for(var i = 0; i < self.listWorkRecordExtractingConditions().length; i++) {
                self.listWorkRecordExtractingConditions()[i].rowId (i + 1);
            }
        }

        /**
         *Create new WorkRecordExtractingCondition 
         */
        private createNewCheckCondition_click() {
            let self = this;
            if (self.listWorkRecordExtractingConditions == null || self.listWorkRecordExtractingConditions == undefined) {
                self.listWorkRecordExtractingConditions = ko.observableArray([]);
            }
            if (self.listWorkRecordExtractingConditions().length > 50) {
                return;
            }
            let workRecordExtractingCondition = shareutils.getDefaultWorkRecordExtractingCondition(0);
            workRecordExtractingCondition.rowId(self.listWorkRecordExtractingConditions().length + 1);

            self.listWorkRecordExtractingConditions.push(workRecordExtractingCondition);
            self.currentRowSelected(self.listWorkRecordExtractingConditions().length);
            let errorAlarmCondition = self.listWorkRecordExtractingConditions()[self.currentRowSelected() - 1].errorAlarmCondition();
        }
        
        /**
         * Open dialog Kal003 B for setting the Setting Check Condition.
         * @param rowId
         */
        private btnSettingCheckCondition_click(rowId) {
            let self = this;
            if (rowId() < 1 || rowId() > self.listWorkRecordExtractingConditions().length) {
                return;
            }
            let WorkRecordExtractingCondition = self.listWorkRecordExtractingConditions()[rowId() - 1];
            if (WorkRecordExtractingCondition) {
                let errorAlarmCondition = WorkRecordExtractingCondition.errorAlarmCondition();
                self.showDialogKal003B(errorAlarmCondition, rowId());
            }
        }

        /**
         * Open dialog Kal003B
         * @param errorAlamCondition
         * @param rowId
         */
        private showDialogKal003B(errorAlarmCondition : model.ErrorAlarmCondition, rowId : number) {
            let self = this;
            windows.setShared('inputKal003b', errorAlarmCondition);
            windows.sub.modal('/view/kal/003/b/index.xhtml', { height: 315, width: 1020 }).onClosed(function(): any {
              // get data from share window    
                let data = windows.getShared('outputKal003b');
                if (data != null && data != undefined) {
                    if (rowId > 0 && rowId <= self.listWorkRecordExtractingConditions().length) {
                        self.listWorkRecordExtractingConditions()[rowId - 1].errorAlarmCondition(data);
                    }
                }
                block.clear();
            });
        }
        
        /**
         * Execute deleting the selected WorkRecordExtractingCondition on screen 
         */
        private deleteCheckCondition_click() {
            let self = this,
            listWorkRecordExtractingConditions = self.listWorkRecordExtractingConditions();
            if (self.currentRowSelected() < 1 || self.currentRowSelected() > listWorkRecordExtractingConditions.length) {
                return;
            }
            listWorkRecordExtractingConditions.splice(self.currentRowSelected() - 1, 1);
            if (self.currentRowSelected() >= listWorkRecordExtractingConditions.length) {
                self.currentRowSelected(listWorkRecordExtractingConditions.length);
            }
            for(var i = 0; i < listWorkRecordExtractingConditions.length; i++) {
                listWorkRecordExtractingConditions[i].rowId (i + 1);
            }
        }
    }
}

$(function(){
    $("#check-condition-table").on("click","tr", function() {
        var id = $(this).data("id");
        nts.uk.ui._viewModel.content.checkConditionTab.currentRowSelected(id);
    })
})


