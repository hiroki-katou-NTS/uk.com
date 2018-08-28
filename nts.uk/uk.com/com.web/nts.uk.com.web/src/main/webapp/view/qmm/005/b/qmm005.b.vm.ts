module nts.uk.com.view.qmm005.b.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import model = nts.uk.com.view.qmm005.share.model;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    export class ScreenModel {
        
        processingYearList:KnockoutObservableArray<model.ItemModel>; 
        processingYearListSelectedCode:KnockoutObservable<number>;
        processingYear:KnockoutObservable<number>;
        
        show: KnockoutObservable<boolean>;
        
        
        
        itemList:KnockoutObservableArray<model.PaymentDateSettingList>; 
        
        
        constructor(){
            var self=this;
            
            self.show = ko.observable(true);
            self.processingYear=ko.observable(2016);           
            self.processingYearList=ko.observableArray([                          
            ]);
            self.processingYearListSelectedCode=ko.observable(-1);


            self.itemList = ko.observableArray([

                new model.PaymentDateSettingList('b4_8', '20180827', 'b4_11', '20151224', 201612, 'b4_14', 201712, 20, 'day','20161224','20161224','20161224','20161224','20161224'),
                new model.PaymentDateSettingList('b4_8', '20180827', 'b4_11', '20151224', 201612, 'b4_14', 201712, 20, 'day','20161224','20161224','20161224','20161224','20161224'),
                new model.PaymentDateSettingList('b4_8', '20180827', 'b4_11', '20151224', 201612, 'b4_14', 201712, 20, 'day','20161224','20161224','20161224','20161224','20161224')


            ]);
            
            
        }
        
        
        toggle():void{
            this.show(!this.show());    
        }
        
        reflectSystemReferenceDateInformation():void{}
        
        reflect():void{}             
        
        newCharacterSetting():void{}
        
        saveCharacterSetting():void{}
        
        cancelCharacterSetting():void{}

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }

    }
    
   
    
}
