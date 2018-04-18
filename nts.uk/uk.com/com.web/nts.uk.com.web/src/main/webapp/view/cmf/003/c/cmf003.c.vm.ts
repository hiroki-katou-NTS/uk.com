module nts.uk.com.view.cmf001.a {
    import getText = nts.uk.resource.getText;
    export module viewmodel {
        export class ScreenModel {
            
            // swapList
           itemsSwap: KnockoutObservableArray<ItemModel>;
           columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
           currentCodeListSwap: KnockoutObservableArray<any>;
           test: KnockoutObservableArray<any>;
           
           // comboBox
           itemList: KnockoutObservableArray<ItemModelCombo>;
           selectedCode: KnockoutObservable<string>;
           isEnable: KnockoutObservable<boolean>;
           isEditable: KnockoutObservable<boolean>;
           selected_comobox: string = getText("CMF003_59");
           constructor() {
               
               var self = this;
               
                self.itemList = ko.observableArray([
                    new ItemModelCombo('1',self.selected_comobox),
                    new ItemModelCombo('2', ' 役職手当'),
                    new ItemModelCombo('3', ' 基本給ながい文字列ながい文字列ながい文字列')
                ]);
        
                self.selectedCode = ko.observable('1');
                self.isEnable = ko.observable(true);
                self.isEditable = ko.observable(true);
               
               self.itemsSwap = ko.observableArray([]);
               
               var array = [];
               for (var i = 0; i < 10000; i++) {
                   array.push(new ItemModel(i, '基本給', "description"));
               }
               self.itemsSwap(array);
    
               self.columns = ko.observableArray([
                   { headerText: 'コード', key: 'code', width: 70 },
                   { headerText: '名称', key: 'name', width: 250 }
               ]);
    
               self.currentCodeListSwap = ko.observableArray([]);
               self.test = ko.observableArray([]);
           }
       
           remove(){
               self.itemsSwap.shift();            
           }
        }
    }

        class ItemModel {
           code: number;
           name: string;
           description: string;
           deletable: boolean;
           constructor(code: number, name: string, description: string) {
               this.code = code;
               this.name = name;
               this.description = description;
               this.deletable = code % 3 === 0;
           }
       }

        class ItemModelCombo {
            code: string;
            name: string;
        
            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }
    
    
}