module nts.uk.at.view.kal003.a.tab {
    import windows = nts.uk.ui.windows;
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import model = nts.uk.at.view.kal003.share.model;
    import shareutils = nts.uk.at.view.kal003.share.kal003utils;

    export class CheckConditionTab {
        listWorkRecordExtractingConditions: KnockoutObservableArray<model.WorkRecordExtractingCondition> = ko.observableArray([]);
        isAllCheckCondition: KnockoutObservable<boolean> = ko.observable(false);
        currentRowSelected: KnockoutObservable<number> = ko.observable(0);

        itemList: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getSchedule4WeekAlarmCheckCondition());
        schedule4WeekCheckCondition: KnockoutObservable<number> = ko.observable(model.SCHEDULE_4_WEEK_CHECK_CONDITION.FOR_ACTUAL_RESULTS_ONLY);

        category: KnockoutObservable<number>;

        constructor(category: number, listWorkRecordExtractingConditions?: Array<model.WorkRecordExtractingCondition>, schedule4WeekCheckCondition?: number) {
            let self = this;

            self.category = ko.observable(category);

            if (listWorkRecordExtractingConditions) {
                self.listWorkRecordExtractingConditions.removeAll();
                self.listWorkRecordExtractingConditions(listWorkRecordExtractingConditions);
                for (var i = 0; i < self.listWorkRecordExtractingConditions().length; i++) {
                    self.listWorkRecordExtractingConditions()[i].rowId(i + 1);
                }
            }
            
            if (schedule4WeekCheckCondition) {
                self.schedule4WeekCheckCondition(schedule4WeekCheckCondition);
            }

            $("#check-condition-table").ntsFixedTable({ height: 300 });

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
                $("#check-condition-table tr").removeClass("ui-state-active");
                $("#check-condition-table tr[data-id='" + data + "']").addClass("ui-state-active");
                //$("#check-condition-table tr").get(data - 1).scrollIntoView();
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
                return;
            }
            let workRecordExtractingCondition = shareutils.getDefaultWorkRecordExtractingCondition(0);
            workRecordExtractingCondition.rowId(self.listWorkRecordExtractingConditions().length + 1);

            self.listWorkRecordExtractingConditions.push(workRecordExtractingCondition);
            self.currentRowSelected(self.listWorkRecordExtractingConditions().length);
            //let errorAlarmCondition = self.listWorkRecordExtractingConditions()[self.currentRowSelected() - 1].errorAlarmCondition();
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
                self.showDialogKal003B(workRecordExtractingCondition(), rowId());
            }
        }

        /**
         * Open dialog Kal003B
         * @param errorAlamCondition
         * @param rowId
         */
        private showDialogKal003B(workRecordExtractingCondition, rowId) {
            let self = this;
            windows.setShared('inputKal003b', workRecordExtractingCondition);
            windows.sub.modal('/view/kal/003/b/index.xhtml', { height: 500, width: 1020 }).onClosed(function(): any {
                // get data from share window    
                let data = windows.getShared('outputKal003b');
                if (data != null && data != undefined) {
                    if (rowId > 0 && rowId <= self.listWorkRecordExtractingConditions().length) {
                        let workRecordExtractingCondition = self.listWorkRecordExtractingConditions()[rowId - 1];
                        workRecordExtractingCondition(data);
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
            if (self.currentRowSelected() < 1 || self.currentRowSelected() > self.listWorkRecordExtractingConditions().length) {
                return;
            }
            self.listWorkRecordExtractingConditions.remove(function(item) { return item.rowId() == (self.currentRowSelected()); })
            for (var i = 0; i < self.listWorkRecordExtractingConditions().length; i++) {
                self.listWorkRecordExtractingConditions()[i].rowId(i + 1);
            }
            if (self.currentRowSelected() >= self.listWorkRecordExtractingConditions().length) {
                self.currentRowSelected(self.listWorkRecordExtractingConditions().length);
            }
            self.currentRowSelected.valueHasMutated();
        }
    }
}

$(function() {
    $("#check-condition-table").on("click", "tr", function() {
        var id = $(this).attr("data-id");
        nts.uk.ui._viewModel.content.tabCheckCondition.currentRowSelected(id);
    })
})


