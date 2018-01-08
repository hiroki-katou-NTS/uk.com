module nts.uk.at.view.kal003{
    import windows  = nts.uk.ui.windows;
    import getText  = nts.uk.resource.getText;
    import block    = nts.uk.ui.block;
    import model    = nts.uk.at.view.kal003.share.model;
    import shareutils = nts.uk.at.view.kal003.share.kal003utils;

    export class CheckConditionTab {
        listWorkRecordExtractingConditions :    KnockoutObservableArray<model.WorkRecordExtractingCondition> = new ko.observableArray([]);
        columnsWorkRecordExtractingCondition:   KnockoutObservableArray<NtsGridListColumn>;
        isAllCheckCondistion :                  KnockoutObservable<boolean> =  ko.observable(false);
        currentRowSelected  :                   KnockoutObservable<number>  =  ko.observable(0);
        constructor() {
            let self = this, 
            listWorkRecordExtractingConditions = self.listWorkRecordExtractingConditions;
            
            
            self.columnsWorkRecordExtractingCondition = ko.observableArray([
                { headerText: '', key: 'errorAlarmCheckID', width: 5, hidden: true },
                { headerText: '<div data-bind="ntsCheckBox: { checked: $parent.isAllCheckCondistion, enable: true }"></div>', key: 'useAtr', width: 15, unbound: true, dataType: "boolean", template: '<div data-bind="ntsCheckBox: { checked: useAtr, enable: true }"></div>'},
                { headerText: getText('KAL003_A11_5'), key: 'rowId', width: 35},
                { headerText: getText('KAL003_A11_6'), key: 'nameWKRecord', unbound: true, dataType: "string", width: 230, template:"<input class='nts-input lab_txt_width' data-bind='ntsTextEditor:{value: errorAlamCondition().message,required: false, enable: true}'/>},"
                },
                { headerText: getText('KAL003_A11_7'), width: 50, unbound: true, dataType: "string", template: '<button data-bind="click : function() {btnSettingCheckCondition_click(rowId)}, enable: true">#{i18n.getText("KAL003_A11_12")}</button>',
                },
                { headerText: getText('KAL003_A11_8'), prop: 'errorAlamCondition().message', width: 230, unbound: true, dataType: "string", template: "<input class='nts-input lab_txt_width' data-bind='ntsTextEditor:{value: errorAlamCondition().message,required: false, enable: true}'/>},"
                }
            ]);
            

            listWorkRecordExtractingConditions.subscribe((data) => {
                for(var i = 0; i < listWorkRecordExtractingConditions.length; i++) {
                    listWorkRecordExtractingConditions[i].rowId (i + 1);
                }
            })
            self.isAllCheckCondistion = ko.pureComputed({
                read: function () {
                    return listWorkRecordExtractingConditions.length === listWorkRecordExtractingConditions.filter(
                            function(item) {return item.useAtr();}).length;
                },
                write: function (value) {
                    for(var i = 0; i < listWorkRecordExtractingConditions.length; i++) {
                        listWorkRecordExtractingConditions[i].useAtr(value);
                    }
                },
                //owner: this
            });
            
        }
        
        /**
         * Init Check Condition Tab
         * @param listWorkRecordExtractingConditions
         */
        initialCheckConditionTab(listWorkRecordExtractingConditions : Array<model.WorkRecordExtractingCondition>) {
            let self = this;
            self.listWorkRecordExtractingConditions().removeAll();
            self.listWorkRecordExtractingConditions.push(listWorkRecordExtractingConditions);
        }

        /**
         *Create new WorkRecordExtractingCondition 
         */
        private createNewCheckCondition_click() {
            let self = this;
            if (self.listWorkRecordExtractingConditions == null || self.listWorkRecordExtractingConditions == undefined) {
                self.listWorkRecordExtractingConditions = ([]);
            }
            if (self.listWorkRecordExtractingConditions().length > 50) {
                return;
            }
            self.listWorkRecordExtractingConditions.push(shareutils.getDefaultWorkRecordExtractingCondition(0));
            self.currentRowSelected(self.listWorkRecordExtractingConditions.length);
            let errorAlamCondition = self.listWorkRecordExtractingConditions[self.currentRowSelected() - 1].errorAlamCondition();
            self.showDialogKal003B(errorAlamCondition, self.currentRowSelected());
        }
        
        /**
         * Open dialog Kal003 B for setting the Setting Check Condition.
         * @param rowId
         */
        private btnSettingCheckCondition_click(rowId : number) {
            let self = this;
            if (rowId < 1 || rowId > self.listWorkRecordExtractingConditions.length) {
                return;
            }
            let errorAlamCondition = self.listWorkRecordExtractingConditions[rowId - 1].errorAlamCondition();
            self.showDialogKal003B(errorAlamCondition(), rowId);
        }

        /**
         * Open dialog Kal003B
         * @param errorAlamCondition
         * @param rowId
         */
        private showDialogKal003B(errorAlamCondition : model.IErrorAlamCondition, rowId : number) {
            let self = this;
            windows.setShared('inputKal003b', errorAlamCondition);
            windows.sub.modal('/view/kal/003/b/index.xhtml', {}).onClosed(function(): any {
              // get data from share window    
                let data = windows.getShared('outputKal003b');
                if (data != null && data != undefined) {
                    self.listWorkRecordExtractingConditions[rowId].errorAlamCondition(data);
                }
                block.clear();
            });
        }
        
        /**
         * Execute deleting the selected WorkRecordExtractingCondition on screen 
         */
        private deleteCheckCondition_click() {
            let self = this;
            if (self.currentRowSelected() < 1 || self.currentRowSelected() > self.listWorkRecordExtractingConditions.length) {
                return;
            }
            self.listWorkRecordExtractingConditions().splice(self.currentRowSelected() - 1, 1);
            if (self.currentRowSelected() >= self.listWorkRecordExtractingConditions.length) {
                self.currentRowSelected(self.listWorkRecordExtractingConditions.length);
            }
        }
    }
}