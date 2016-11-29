

module qmm019.h.viewmodel {
 import option = nts.uk.ui.option;
    

    export class ItemModel {
        id: any;
        name: any;
        constructor(id, name) {
            var self = this;
            this.id = id;
            this.name = name;
        }
    }
    

    export class ListBox {
        itemList: KnockoutObservableArray<any>;
        itemName: KnockoutObservable<any>;
        currentCode: KnockoutObservable<any>;
        selectedCode: KnockoutObservable<any>;
        selectedName: KnockoutObservable<any>;
        isEnable: KnockoutObservable<any>;
        selectedCodes: KnockoutObservableArray<any>;

        constructor() {
            var self = this;
            self.itemList = ko.observableArray([
                new ItemModel("01", "レーザープリンタ"),
                new ItemModel("02", "レーザープリンタ"),
                new ItemModel("03", "レーザープリンタ"),
                new ItemModel("04", "レーザープリンタ"),
                new ItemModel("05", "レーザー（圧着式）"),
                new ItemModel("06", "レーザー（圧着式）"),
                new ItemModel("07", "ドットプリンタ7"),
                new ItemModel("08", "ドットプリンタ8"),
                new ItemModel("09", "ドットプリンタ9"),
                new ItemModel("10", "ドットプリンタ10"),
                new ItemModel("11", "ドットプリンタ11"),
                new ItemModel("12", "ドットプリンタ12"),
                new ItemModel("13", "ドットプリンタ13"),
                new ItemModel("14", "ドットプリンタ14"),
                new ItemModel("15", "ドットプリンタ15"),
                new ItemModel("16", "ドットプリンタ16"),
                new ItemModel("17", "ドットプリンタ17"),
                new ItemModel("18", "ドットプリンタ18"),
                new ItemModel("19", "ドットプリンタ19"),
                new ItemModel("20", "ドットプリンタ20"),
            ]);
            self.itemName = ko.observable('');
            self.currentCode = ko.observable('');
            self.selectedCode = ko.observable('01');
            self.itemName = ko.observable('');


            self.itemList();
            $('#list-box').on('selectionChanging', function(event) {
                console.log('Selecting value:' + (<any>event.originalEvent).detail);
            })
            $('#list-box').on('selectionChanged', function(event: any) {
                console.log('Selected value:' + (<any>event.originalEvent).detail)
            });
            self.isEnable = ko.observable(true);
            self.selectedCodes = ko.observableArray([]);



        }
    }


    export class ScreenModel {
        listBox: ListBox;

        constructor() {
            var self = this;
            self.listBox = new ListBox();

        }
        
        chooseItem() {
            var self = this;
            nts.uk.ui.windows.setShared('selectedCode', self.listBox.selectedCode());
            nts.uk.ui.windows.close();
        }
        
      closeDialog() {
             nts.uk.ui.windows.close();
       }

    }
    
    
 
}
