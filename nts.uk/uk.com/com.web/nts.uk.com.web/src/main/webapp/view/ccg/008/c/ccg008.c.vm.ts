module nts.uk.com.view.ccg008.c {
    export module viewmodel {
        export class ScreenModel {
            index: number;
            dataItems: any;
            selectedCode: any;
            singleSelectedCode: any;
            headers: any;
            
            constructor() {
                var self = this;
                self.dataItems = ko.observableArray([]);
                self.selectedCode = ko.observableArray([]);
                self.singleSelectedCode = ko.observable(null);
                self.index = 0;
                self.headers = ko.observableArray([nts.uk.resource.getText("CCG008_8")]);
            }
            
            start(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                service.getSelectMyTopPage().done(function(res) {
                    var items = _.map(res, function(item: any) {
                        return new Node(item.code, item.name);
                    });
                    self.dataItems(items);
                    dfd.resolve();
                }).fail(function(err){
                    nts.uk.ui.dialog.alert(err);
                });
                
                return dfd.promise();
            }
                
            save(): void {
                var self = this;
                var data = {
                    employeeId: 0,
                    code: self.singleSelectedCode(), 
                    division: 0    
                };
                
                service.save(data).done(function(res) {
                    
                }).fail(function(err){
                    nts.uk.ui.dialog.alert(err);
                });
            }
            
            closeDialog() {
                nts.uk.ui.windows.close();   
            }  
        }  
        
        class Node {
            code: string;
            name: string;
            nodeText: string;
            custom: string;
            constructor(code: string, name: string) {
                var self = this;
                self.code = code;
                self.name = name;
                self.nodeText = self.code + ' ' + self.name;
                self.custom = 'Random' + new Date().getTime();
            }
        }
    }
}