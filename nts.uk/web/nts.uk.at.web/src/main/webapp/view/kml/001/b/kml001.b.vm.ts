module kml001.b.viewmodel {
    export class ScreenModel {
        extraTimeItemList: KnockoutObservableArray<ExtraTimeItem>;
        constructor() {
            var self = this;
            self.extraTimeItemList = ko.observableArray(
//                [ new ExtraTimeItem('','1','Item1','0001',1),
//                new ExtraTimeItem('','2','Item2','0002',1),
//                new ExtraTimeItem('','3','Item3','0003',1),
//                new ExtraTimeItem('','4','Item4','0004',0),
//                new ExtraTimeItem('','5','Item5','0005',0),
//                new ExtraTimeItem('','6','Item6','0006',0),
//                new ExtraTimeItem('','7','Item7','0007',0),
//                new ExtraTimeItem('','8','Item8','0008',0),
//                new ExtraTimeItem('','9','Item9','0009',0),
//                new ExtraTimeItem('','10','Item10','0010',0)]
//                nts.uk.ui.windows.getShared('extraTimeItemList')
            );   
        }
        
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            kml001.b.service.extraTimeSelect()
                .done(function(data) {
                    self.extraTimeItemList(data);
                    dfd.resolve();
                })
                .fail(function(res) { 
                    dfd.reject(res); 
                });
            return dfd.promise();
        }
        
        submitAndCloseDialog(): void {
            var self = this;
            let extraItemListCommand = [];
            ko.utils.arrayForEach(self.extraTimeItemList(), function(item) { extraItemListCommand.push(ko.toJSON(item)); });
            kml001.b.service.extraTimeUpdate(extraItemListCommand);
            nts.uk.ui.windows.close();
        }
        
        closeDialog(): void {
            nts.uk.ui.windows.close();   
        }
        
    }
    
    class ExtraTimeItem {
        companyID: KnockoutObservable<string>;
        extraItemID: KnockoutObservable<string>; 
        name: KnockoutObservable<string>;
        timeItemID: KnockoutObservable<string>;
        useClassification: KnockoutObservable<number>;
        constructor(companyID: string, extraItemID: string, name: string, timeItemID: string, useClassification: number) {
            var self = this;
            self.extraItemID = ko.observable(extraItemID);
            self.companyID = ko.observable(companyID);
            self.useClassification = ko.observable(useClassification);
            self.timeItemID = ko.observable(timeItemID);
            self.name = ko.observable(name);
        }
    }
}