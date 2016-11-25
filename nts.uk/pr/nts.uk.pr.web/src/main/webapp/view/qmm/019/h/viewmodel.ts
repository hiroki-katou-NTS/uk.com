module qmm019.h.viewmodel {
    
    export class ScreenModel {
        itemList: KnockoutObservableArray<ItemModel>;
        itemName: KnockoutObservable<string>;
        currentCode: KnockoutObservable<number>
        selectedCode: KnockoutObservable<number>;
        selectedCodes: KnockoutObservableArray<string>;
        isEnable: KnockoutObservable<boolean>;
        selectLayoutCode: KnockoutObservable<string>;
         selectedPersonalWageName: KnockoutObservableArray<any>;

        /**
         * Init screen model.
         */
        constructor() {
            var self = this;
            self.itemList = ko.observableArray([
                new ItemModel("01","レーザープリンタ"),
                new ItemModel("02","レーザープリンタ"),
                new ItemModel("03","レーザープリンタ"),
                new ItemModel("04","レーザープリンタ"),
                new ItemModel("05","レーザー（圧着式）"),
                new ItemModel("06","レーザー（圧着式）"),
                new ItemModel("07","ドットプリンタ7"),
                new ItemModel("08","ドットプリンタ8"),
                new ItemModel("09","ドットプリンタ9"),
                new ItemModel("10","ドットプリンタ10"),
                new ItemModel("11","ドットプリンタ11"),
                new ItemModel("12","ドットプリンタ12"),
                new ItemModel("13","ドットプリンタ13"),
                new ItemModel("14","ドットプリンタ14"),
                new ItemModel("15","ドットプリンタ15"),
                new ItemModel("16","ドットプリンタ16"),
                new ItemModel("17","ドットプリンタ17"),
                new ItemModel("18","ドットプリンタ18"),
                new ItemModel("19","ドットプリンタ19"),
                new ItemModel("20","ドットプリンタ20"),
                new ItemModel("21","ドットプリンタ21")
            ]);
                 self.itemName = ko.observable("");
            self.currentCode = ko.observable(3);
            self.selectedCode = ko.observable(null);
            self.isEnable = ko.observable(true);
            self.selectedCodes = ko.observableArray([]);
            
            $("#SEL_002").on("selectionChanging", function(event) {
                console.log("Selecting value:" + (<any>event.originalEvent).detail);
            })
            $("#SEL_002").on("selectionChanged", function(event: any) {
                console.log("Selected value:" + (<any>event.originalEvent).detail)
            })
            
        
            self.selectLayoutCode = ko.observable("001");           
        } 
    }
        
    export class ItemModel {
        companyCode: string;
        personalWaveName: string;
      
        
        constructor(companyCode: string, personalWaveName: string) {
            this.companyCode = companyCode;
            this.personalWaveName = personalWaveName;
         
        }
    }
}