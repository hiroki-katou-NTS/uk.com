module qmm019.h.viewmodel {
    import option = nts.uk.ui.option;


//    export class ItemModel {
//        //        id: any;
//        //        name: any;
//        //        constructor(id, name) {
//        //            var self = this;
//        //            this.id        //            this.name = name;
//
//        companyCode: any;
//        personalWageName: any;
//        constructor(companyCode, personalWageName) {
//            var self = this;
//            this.companyCode = companyCode;
//            this.personalWageName = personalWageName;
//        }
//
//
//    }
//    export class ListBox {
//        
//        itemName: KnockoutObservable<any>;
//        currentCode: KnockoutObservable<any>;
//        selectedCode: KnockoutObservable<any>;
//        selectedName: KnockoutObservable<any>;
//        isEnable: KnockoutObservable<any>;
//        selectedCodes: KnockoutObservableArray<any>;
//        personalWages: KnockoutObservableArray<service.model.PersonalWageNameDto>;
//        
//
//        constructor() {
//            var self = this;
//            self.itemList = ko.observableArray([]);
//            self.personalWages = ko.observableArray([]);
//
//            self.itemName = ko.observable('');
//            self.currentCode = ko.observable('0');
//            self.selectedCode = ko.observable('01');
//            self.isEnable = ko.observable(true);
//            self.selectedCodes = ko.observableArray([]);
//            
//        }
//
//    }
    export class ScreenModel {
        itemList: KnockoutObservableArray<ItemWage>;
        personalWages: KnockoutObservableArray<service.model.PersonalWageNameDto>;
        selectedCode: KnockoutObservable<any>;
        selectedName: KnockoutObservable<any>;       
        categoryAtr : KnockoutObservable<number>;
        constructor() {
            var self = this;
            //self.listBox = new ListBox();
            self.itemList = ko.observableArray([]);   
            self.personalWages = ko.observableArray([]);
            self.selectedCode = ko.observable(null);
        }

      
         buildItemList(): any{
           var self = this;
//            self.itemList.removeAll();
            _.forEach(self.personalWages(), function(personalWage){
                var companyCode = personalWage.companyCode;
                if(companyCode.length == 1){
                    companyCode = "0" + personalWage.companyCode;
                }
                self.itemList.push(new ItemWage(personalWage.personalWageCode, personalWage.personalWageName));
            });          
          
        }
        
         start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();

            //var categoryAtr = ko.observable(nts.uk.ui.windows.getShared('categoryAtr'));
             var categoryAtr = 1;
            service.getPersonalWageNames(categoryAtr).done(function(data: any){
                self.personalWages(data);
                self.buildItemList();
                dfd.resolve();
            }).fail(function(res) {
                alert(res);
            });
             
            // Return.
            return dfd.promise();    
        }

        chooseItem() {
            var self = this;
            var item = _.find(self.itemList(), function(item) {return item.wageCode === self.selectedCode()});
            nts.uk.ui.windows.setShared('selectedName', item.wageName);
            nts.uk.ui.windows.setShared('selectedCode', item.wageCode);
            nts.uk.ui.windows.close();
        }

        closeDialog() {
            nts.uk.ui.windows.close();
        }

    }
    export class ItemWage{
        wageCode: string;
        wageName: string;
        
        constructor(wageCode: string, wageName: string){
            this.wageCode = wageCode;
            this.wageName = wageName;    
        }
    }

}
