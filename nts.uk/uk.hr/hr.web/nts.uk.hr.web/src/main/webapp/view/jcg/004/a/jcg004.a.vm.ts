module nts.uk.at.view.jcg004.a.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import info = nts.uk.ui.dialog.info;
    export var STORAGE_KEY_TRANSFER_DATA = "nts.uk.request.STORAGE_KEY_TRANSFER_DATA";
    export class ScreenModel {
        currentMonth: KnockoutObservable<period>;
        data1: KnockoutObservable<boolean>;
        data2: KnockoutObservable<boolean>;
        data3: KnockoutObservable<boolean>;
        data4: KnockoutObservable<boolean>;
        data5: KnockoutObservable<boolean>;
        data6: KnockoutObservable<boolean>;
        data7: KnockoutObservable<boolean>;
        data8: KnockoutObservable<boolean>;
        constructor() {
            var self = this;
            self.currentMonth = ko.observable(new period("",""));
            self.data1 = ko.observable(true); 
            self.data2 = ko.observable(true); 
            self.data3 = ko.observable(true); 
            self.data4 = ko.observable(true); 
            self.data5 = ko.observable(true); 
            self.data6 = ko.observable(true); 
            self.data7 = ko.observable(true); 
            self.data8 = ko.observable(true); 
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            block.invisible();
                 
            dfd.resolve();
            block.clear();
            return dfd.promise();
        }
        
        getInfor(): void{
            var self = this;
            block.invisible();
            new service.Service().getOptionalWidgetInfo(param).done(function(data: any){
                block.clear();
            });           
        }
        
        openJHN003A(): void{
            var self = this;
            block.invisible();
            block.clear();
        }
        
        openJHN007B(): void{
            var self = this;
            block.invisible();
            block.clear();
        }
        
        
    }
    export class period{
        strMonth: Date;
        endMonth: Date;
        constructor(strMonth: string, endMonth: string){
            this.strMonth = new Date(strMonth);
            this.endMonth = new Date(endMonth);
        }    
    }
    
}

