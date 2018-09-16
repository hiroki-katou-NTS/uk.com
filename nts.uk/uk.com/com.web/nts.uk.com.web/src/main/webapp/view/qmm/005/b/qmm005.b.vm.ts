module nts.uk.pr.view.qmm005.b.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import model = nts.uk.pr.view.qmm005.share.model;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    export class ScreenModel {

        screenModeCreate:KnockoutObservable<boolean>;
        
        processingYearList:KnockoutObservableArray<model.ItemModel>; 
        processingYearListSelectedCode:KnockoutObservable<number>;
        processingYear:KnockoutObservable<number>;
        
        show: KnockoutObservable<boolean>;



        setDaySupportList:KnockoutObservableArray<model.SetDaySupport>;
        processInfomation:KnockoutObservableArray<model.ProcessInfomation>;
        
        constructor(){

            var self=this;

            self.screenModeCreate=ko.observable(false);



            self.show = ko.observable(true);
            self.processingYear=ko.observable(2016);           
            self.processingYearList=ko.observableArray([                          
            ]);
            self.processingYearListSelectedCode=ko.observable(-1);


            self.setDaySupportList = ko.observableArray([]);

            self.processInfomation = ko.observableArray([]);
            
            
        }
        
        
        toggle():void{
            this.show(!this.show());

        }
        
        reflectSystemReferenceDateInformation():void{}
        
        reflect():void{}             
        
        newCharacterSetting():void{
            this.screenModeCreate(true);

        }

        
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
