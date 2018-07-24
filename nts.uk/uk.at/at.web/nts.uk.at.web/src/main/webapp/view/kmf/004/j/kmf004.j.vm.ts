module nts.uk.at.view.kmf004.j.viewmodel {
    export class ScreenModel {
        items: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        currentCodeList: KnockoutObservableArray<any>;
        selectedItems: KnockoutObservableArray<any>;
        listSpecialHlFrame: KnockoutObservableArray<any>;
        listAbsenceFrame: KnockoutObservableArray<any>;
        
        constructor() {
            let self = this;
            
            self.selectedItems = nts.uk.ui.windows.getShared("KMF004_A_TARGET_ITEMS");
            
            self.listSpecialHlFrame = ko.observableArray([]);
            self.listAbsenceFrame = ko.observableArray([]);
            
            self.items = ko.observableArray([]);
            
            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText('KMF004_148'), key: 'code', width: 100, hidden: true },
                { headerText: nts.uk.resource.getText('KMF004_148'), key: 'name', width: 320 }
            ]); 
            
            self.currentCodeList = ko.observableArray([]);
            
            $("#data-items").focus();
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            
            $.when(self.getAbsenceFrame(), self.getSpecialHolidayFrame()).done(function() {
                  
                if(self.listAbsenceFrame().length > 0) {
                    _.forEach(self.listAbsenceFrame(), function(item) {
                        self.items.push(item);
                    });
                }
                
                if(self.listSpecialHlFrame().length > 0) {
                    _.forEach(self.listSpecialHlFrame(), function(item) {
                        self.items.push(item);
                    });
                }
                
                if(self.items().length > 0) {
                    if(self.selectedItems.length > 0) {
                        self.currentCodeList.removeAll();
                        _.forEach(self.selectedItems, function(item) {
                            self.currentCodeList.push(item);
                        });
                    }
                } else {
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_1267" });
                }
                
                $("#data-items").focus();
                
                dfd.resolve();
            }).fail(function(res) {
                dfd.reject(res);    
            });
            
            return dfd.promise();
        }
        
        /**
         * Get data absence frame from database
         */
        getAbsenceFrame(): any {
            var self = this;
            var dfd = $.Deferred();
            service.getAllAbsenceFrame().done(function(data) {
                if (data.length != 0) {
                    self.listAbsenceFrame.removeAll();
                    _.forEach(data, function(item) {
                        if (item.deprecateAbsence == 0) {
                            var absenceFrame = new ItemModel("a" + item.absenceFrameNo, item.absenceFrameName, 1)
                            self.listAbsenceFrame.push(ko.toJS(absenceFrame));
                        }
                    });
                }
                dfd.resolve();
            }).fail((res) => { });
            return dfd.promise();
        }
        
        /**
         * Get data special holiday frame form database
         */
        getSpecialHolidayFrame(): any {
            var self = this;
            var dfd = $.Deferred();
            service.getAllSpecialHolidayFrame().done(function(data) {
                if (data.length != 0) {
                    self.listSpecialHlFrame.removeAll();
                    _.forEach(data, function(item) {
                        if (item.deprecateSpecialHd == 0) {
                            var specialHlFrame = new ItemModel("b" + item.specialHdFrameNo, item.specialHdFrameName, 2)
                            self.listSpecialHlFrame.push(ko.toJS(specialHlFrame));
                        }
                    });
                }
                dfd.resolve();
            }).fail((res) => { });
            return dfd.promise();
        }
        
        submit() {
            let self = this;
            
            if(self.currentCodeList().length > 0) {
                nts.uk.ui.windows.setShared("KMF004_J_SELECTED_ITEMS", self.currentCodeList());
                nts.uk.ui.windows.close();
            } else {
                nts.uk.ui.dialog.alertError({ messageId: "Msg_1268" });
            }
        }
        
        cancel() {
            nts.uk.ui.windows.close();
        }
    }
    
    class ItemModel {
        code: string;
        name: string;
        types: number;

        constructor(code: string, name: string, types: number) {
            this.code = code;
            this.name = name;
            this.types = types;
        }
    }
}