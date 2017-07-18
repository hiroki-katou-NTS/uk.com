module ksm002.d{
    export module viewmodel{
        import getShared = nts.uk.ui.windows.getShared;
        
        export class ScreenModel { 
            itemList: KnockoutObservableArray<any>;
            selectedId: KnockoutObservable<number>;
            startMonth: KnockoutObservable<number>;
            endMonth: KnockoutObservable<number>;
            specificDateItem: KnockoutObservableArray<SpecificDateItem>;
            constructor() {
                let self=this;
                self.startMonth = ko.observable(null);
                self.endMonth = ko.observable(null);
                self.specificDateItem = ko.observableArray([]);
                // Radio button
                self.itemList = ko.observableArray([
                new BoxModel(1, nts.uk.resource.getText('KSM002_54')),
                new BoxModel(2, nts.uk.resource.getText('KSM002_55'))
            ]);
                self.selectedId = ko.observable(1);
            }
    
            
    
            /** get data when start dialog **/
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                let param: IData = getShared('KSM002A_PARAM') || { util: 0, workplaceId: '', startDate: 20160202, endDate: 20160302};
                self.endMonth(param.endDate);
                self.startMonth(param.startDate);
                service.getAllSpecificDate().done(function(data) {
                    data.forEach(function(item){
                        self.specificDateItem.push(
                            new SpecificDateItem(
                                item.timeItemId,
                                item.useAtr,
                                item.specificName
                            ));
                    });
                    console.log( self.specificDateItem());
                    dfd.resolve();
                }).fail(function(res) { 
                    nts.uk.ui.dialog.alertError(res.message).then(function(){nts.uk.ui.block.clear();});
                    dfd.reject(res); 
                });
                return dfd.promise();
        }
            
            /** close Dialog **/
            closeDialog() {   
                nts.uk.ui.windows.close(); 
            }
                
            submitDialog(){
            }
        }
        
        class BoxModel {
            id: number;
            name: string;
            constructor(id, name){
                var self = this;
                self.id = id;
                self.name = name;
            }
        }
        
        export class DateTimePicker{
    
        } 
        
        export class SpecificDateItem{
            timeItemId: KnockoutObservable<string>;
            useAtr: KnockoutObservable<number>;
            specificName: KnockoutObservable<string>;
            constructor(timeItemId: string, useAtr: number, specificName: string){
                this.timeItemId = ko.observable(timeItemId);
                this.useAtr = ko.observable(useAtr);
                this.specificName = ko.observable(specificName);
            }
        }
        
        interface IData {
            util: number,
            workplaceId: string,
            startDate: number,
            endDate: number
        }
    }
}


