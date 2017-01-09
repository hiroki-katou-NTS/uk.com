module cmm013.b.viewmodel {
    import option = nts.uk.ui.option;

    export class ScreenModel {
        itemList: KnockoutObservableArray<ItemModel>;
      
        selectedCode: KnockoutObservable<any>;
        selectedName: KnockoutObservable<any>;       
        categoryAtr : KnockoutObservable<number>;
        constructor() {
            var self = this;
            //self.listBox = new ListBox();
            self.itemList = ko.observableArray([]);   
         
            self.selectedCode = ko.observable(null);
        }

      
         buildItemList(): any{
            
           var self = this;
            self.itemList.removeAll();
            self.itemList.push(new ItemModel('1', 'B0005', 'レーザー','1'));
            self.itemList.push(new ItemModel('2', 'B0006', 'プリンタ','1'));
            self.itemList.push(new ItemModel('3', 'B0007', 'プリンタ４','3'));
            self.itemList.push(new ItemModel('4', 'B0008', 'プリンタ5','4'));
            self.itemList.push(new ItemModel('5', 'B0009', 'プリンタ6','5'));
            self.itemList.push(new ItemModel('6', 'B0010', 'プリンタ7','6'));
              self.itemList.push(new ItemModel('7', 'B0011', 'レーザー','7'));
            self.itemList.push(new ItemModel('8', 'B0012', 'プリンタ','8'));
            self.itemList.push(new ItemModel('9', 'B0013', 'プリンタ４','9'));
            self.itemList.push(new ItemModel('10', 'B0014', 'プリンタ5','10'));
            self.itemList.push(new ItemModel('11', 'B0015', 'プリンタ6','11'));
            self.itemList.push(new ItemModel('12', 'B0016', 'プリンタ7','12'));
        }
        
         start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();
         
             self.buildItemList();
      


            dfd.resolve();
            // Return.
            return dfd.promise();
        }

        chooseItem() {
            var self = this;
            var item = _.find(self.itemList(), function(item) {return item.name === self.selectedCode()});
            nts.uk.ui.windows.setShared('selectedName', item.name);
            nts.uk.ui.windows.setShared('selectedCode', item.code);
            nts.uk.ui.windows.close();
        }

        closeDialog() {
            nts.uk.ui.windows.close();
        }

    }
    export class ItemModel{
        stt:string;
        code: string;
        name: string;
        code1:string;
        
        
        constructor(stt:string ,code: string, name: string ,code1 : string){
            this.stt = stt;
            this.code = code;
            this.name = name;
            this.code1 = code1;   
        }
    }

}
