module nts.uk.at.view.kal003.a.tab {
    import windows = nts.uk.ui.windows;
    import dialog = nts.uk.ui.dialog;
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import model = nts.uk.at.view.kal003.share.model;
    import shareutils = nts.uk.at.view.kal003.share.kal003utils;
    import info = nts.uk.ui.dialog.info;

    export class CheckConditionTab {
        listWorkRecordExtractingConditions: KnockoutObservableArray<model.WorkRecordExtractingCondition> = ko.observableArray([]);
        isAllCheckCondition: KnockoutObservable<boolean> = ko.observable(false);
        currentRowSelected: KnockoutObservable<number> = ko.observable(0);

        itemList: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getSchedule4WeekAlarmCheckCondition());
       // schedule4WeekCheckCondition: KnockoutObservable<number> = ko.observable(model.SCHEDULE_4_WEEK_CHECK_CONDITION.FOR_ACTUAL_RESULTS_ONLY);
        schedule4WeekCheckCondition : KnockoutObservable<number>;
        list4weekClassEnum : KnockoutObservableArray<any>;

        category: KnockoutObservable<number>;

        constructor(category: number, listWorkRecordExtractingConditions?: Array<model.WorkRecordExtractingCondition>, schedule4WeekCheckCondition?: number) {
            let self = this;
            self.category = ko.observable(category);

            if (listWorkRecordExtractingConditions) {
                self.listWorkRecordExtractingConditions(listWorkRecordExtractingConditions);
//                for (var i = 0; i < self.listWorkRecordExtractingConditions().length; i++) {
//                    self.listWorkRecordExtractingConditions()[i].rowId(i + 1);
//                }
            }
            
            self.listWorkRecordExtractingConditions.subscribe(() => {
                for (var i = 0; i < self.listWorkRecordExtractingConditions().length; i++) {
                    self.listWorkRecordExtractingConditions()[i].rowId(i + 1);
                }
            });
            // schedule4WeekCheckCondition
            self.list4weekClassEnum = ko.observableArray(__viewContext.enums.FourW4DCheckCond);
            self.schedule4WeekCheckCondition = ko.observable(0);            
            if (schedule4WeekCheckCondition) {
                self.schedule4WeekCheckCondition(schedule4WeekCheckCondition);
            }

            $("#check-condition-table").ntsFixedTable({ height: 300 });
            // MinhVV add
            $("#check-condition-table_category9").ntsFixedTable({ height: 300 });

            self.isAllCheckCondition = ko.pureComputed({
                read: function () {
                    let l = self.listWorkRecordExtractingConditions().length;
                    if (self.listWorkRecordExtractingConditions().filter((x) => {return x.useAtr()}).length == l && l > 0) {
                        return true;
                    } else {
                        return false;
                    }
                },
                write: function (value) {
                    for (var i = 0; i < self.listWorkRecordExtractingConditions().length; i++) {
                        self.listWorkRecordExtractingConditions()[i].useAtr(value);
                    }
                },
                owner: self
            });
            self.currentRowSelected.subscribe((data) => {
                // MinhVV edit check category 5 or 9
                if(self.category()==5){
                    $("#check-condition-table tr").removeClass("ui-state-active");
                    $("#check-condition-table tr[data-id='" + data + "']").addClass("ui-state-active");
                }else if(self.category()==9){
                    $("#check-condition-table_category9 tr").removeClass("ui-state-active");
                    $("#check-condition-table_category9 tr[data-id='" + data + "']").addClass("ui-state-active");
                }
            });
            
        }

        /**
         *Create new WorkRecordExtractingCondition 
         */
        private createNewCheckCondition_click() {
            let self = this;
            if (self.listWorkRecordExtractingConditions == null || self.listWorkRecordExtractingConditions == undefined) {
                self.listWorkRecordExtractingConditions = ko.observableArray([]);
            }
            if (self.listWorkRecordExtractingConditions().length == 50) {
                dialog.alertError({ messageId: "Msg_833" });
                return;
            }
            let workRecordExtractingCondition = shareutils.getDefaultWorkRecordExtractingCondition(0);
            workRecordExtractingCondition.rowId(self.listWorkRecordExtractingConditions().length + 1);

            self.listWorkRecordExtractingConditions.push(workRecordExtractingCondition);
            self.currentRowSelected(self.listWorkRecordExtractingConditions().length);
            // MinhVV edit
            if(self.category()==5){
                $("#check-condition-table tr")[self.listWorkRecordExtractingConditions().length - 1].scrollIntoView();
            }else if(self.category()==9){
                $("#check-condition-table_category9 tr")[self.listWorkRecordExtractingConditions().length - 1].scrollIntoView();
            }
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
            let workRecordExtractingCondition = self.listWorkRecordExtractingConditions()[rowId() - 1];
            if (workRecordExtractingCondition) {
                if (_.isEmpty(workRecordExtractingCondition.errorAlarmCondition().atdItemCondition().group1().lstErAlAtdItemCon())) {
                    workRecordExtractingCondition.errorAlarmCondition().atdItemCondition().group1().lstErAlAtdItemCon([shareutils.getDefaultCondition(0), shareutils.getDefaultCondition(1), shareutils.getDefaultCondition(2)]);
                }
                self.showDialogKal003B(workRecordExtractingCondition, rowId());
            }
        }

        /**
         * Open dialog Kal003B
         * @param errorAlamCondition
         * @param rowId
         */
        private showDialogKal003B(workRecordExtractingCondition: model.WorkRecordExtractingCondition, rowId: number) {
            let self = this;
            let sendData = ko.toJS(workRecordExtractingCondition);
            //sendData = shareutils.convertArrayOfWorkRecordExtractingConditionToJS(sendData, workRecordExtractingCondition);
            sendData = { data: shareutils.convertArrayOfWorkRecordExtractingConditionToJS(sendData, workRecordExtractingCondition), category: self.category() };
            windows.setShared('inputKal003b', sendData);
            windows.sub.modal('/view/kal/003/b/index.xhtml', { height: 600, width: 1020 }).onClosed(function(): any {
                // get data from share window    
                let data = windows.getShared('outputKal003b');
                if (data != null && data != undefined) {
                    if (rowId > 0 && rowId <= self.listWorkRecordExtractingConditions().length) {
                        self.listWorkRecordExtractingConditions()[rowId - 1] = shareutils.convertTransferDataToWorkRecordExtractingCondition(data);
                        self.listWorkRecordExtractingConditions.valueHasMutated();
                    }
                }
                block.clear();
            });
        }

        /**
         * Execute deleting the selected WorkRecordExtractingCondition on screen 
         */
        private deleteCheckCondition_click() {
            let self = this;
            block.invisible();
            if (self.currentRowSelected() < 1 || self.currentRowSelected() > self.listWorkRecordExtractingConditions().length || _.filter(self.listWorkRecordExtractingConditions(), function(o) { return o.useAtr(); }).length==0) {
                block.clear();
                return;
            }
            //self.listWorkRecordExtractingConditions.remove(function(item) { return item.rowId() == (self.currentRowSelected()); })
            self.listWorkRecordExtractingConditions.remove(function(item) { return item.useAtr(); })
            nts.uk.ui.errors.clearAll();
            for (var i = 0; i < self.listWorkRecordExtractingConditions().length; i++) {
                self.listWorkRecordExtractingConditions()[i].rowId(i + 1);
            }
            if (self.currentRowSelected() >= self.listWorkRecordExtractingConditions().length) {
                self.currentRowSelected(self.listWorkRecordExtractingConditions().length);
            }
            self.currentRowSelected.valueHasMutated();
            if (self.currentRowSelected() > 0) 
               // MinhVV edit
                if(self.category()==5){
                    $("#check-condition-table tr")[self.currentRowSelected() - 1].scrollIntoView();
                }else{
                    $("#check-condition-table_category9 tr")[self.currentRowSelected() - 1].scrollIntoView();
                }
            info({ messageId: "Msg_16" }).then(() => {
                block.clear();
            });
        }
    }
}

$(function() {
    // MinhVV edit
    $("#check-condition-table").on("click", "tr", function() {
        var id = $(this).attr("data-id");
        nts.uk.ui._viewModel.content.tabCheckCondition.currentRowSelected(id);
    })
    $("#check-condition-table_category9").on("click", "tr", function() {
        var id = $(this).attr("data-id");
        nts.uk.ui._viewModel.content.tabCheckCondition.currentRowSelected(id);
    })
})

