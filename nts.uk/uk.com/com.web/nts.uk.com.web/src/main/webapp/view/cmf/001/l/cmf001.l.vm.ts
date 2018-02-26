module nts.uk.com.view.cmf001.l.viewmodel {
    import model = nts.uk.com.view.cmf001.share.model;
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    
    export class ScreenModel {
        constraint: KnockoutObservable<string>;
        inline: KnockoutObservable<boolean>;
        required: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;
        
        date: KnockoutObservable<any>;
        
        itemList: KnockoutObservableArray<model.ItemModel>;
        selectedCode: KnockoutObservable<number>;
        simpleValue1: KnockoutObservable<any>;
        simpleValue2: KnockoutObservable<any>;
        
        acceptScreenConditionSetting: KnockoutObservable<model.AcceptScreenConditionSetting> = ko.observable(new model.AcceptScreenConditionSetting('条件値', 0 , 201801010102, 201801010134, '201801010102', '201801010134', '201801010134', '201801010134', 201801010102, 201801010134));
        compareCondition: KnockoutObservable<number>;
        csvItemName: KnockoutObservable<string>;
        timeVal1: KnockoutObservable<string>;
        timeVal2: KnockoutObservable<string>;
        selectedDataType: KnockoutObservable<number>;
        
        
        constructor() {
            var self = this;  
            self.constraint = ko.observable('LayoutCode');
            self.inline = ko.observable(true);
            self.required = ko.observable(true)
            self.enable = ko.observable(true);
            
            self.itemList = ko.observableArray([
            new model.ItemModel(0, '条件としない'),
            new model.ItemModel(1, '条件値1　＜　値'),
            new model.ItemModel(2, '条件値1　≦　値'),
            new model.ItemModel(3, '値　＜　条件値1'),
            new model.ItemModel(4, '値　≦　条件値1'),
            new model.ItemModel(5, '条件値1　＜　値　かつ　　値　＜　条件値2'),
            new model.ItemModel(6, '条件値1　≦　値　かつ　　値　≦　条件値2'),
            new model.ItemModel(7, '値　＜　条件値1　または　　条件値2　＜　値'),
            new model.ItemModel(8, '値　≦　条件値1　または　　条件値2　≦　値'),
            new model.ItemModel(9, '条件値1　＝　値'),
            new model.ItemModel(10, '条件値1　≠　　値')
            ]);
            
            self.selectedCode = ko.observable(0);
            self.csvItemName = getShared("csvItemName");
            self.selectedDataType = getShared("selectedDataType");
            
            switch(self.selectedDataType) { 
               case 0: { 
                  self.simpleValue1 = ko.observable(self.acceptScreenConditionSetting.numberConditionValue1());
                  self.simpleValue2 = ko.observable(self.acceptScreenConditionSetting.numberConditionValue2());
                  break; 
               } 
               case 1: { 
                  self.simpleValue1 = ko.observable(self.acceptScreenConditionSetting.characterConditionValue1());
                  self.simpleValue2 = ko.observable(self.acceptScreenConditionSetting.characterConditionValue2());
                  break; 
               } 
               case 2: { 
                  self.simpleValue1 = ko.observable(self.acceptScreenConditionSetting.dateConditionValue1());
                  self.simpleValue2 = ko.observable(self.acceptScreenConditionSetting.dateConditionValue2());
                  break; 
               } 
               case 3: { 
                  self.simpleValue1 = ko.observable(self.acceptScreenConditionSetting.timeMomentConditionValue1());
                  self.simpleValue2 = ko.observable(self.acceptScreenConditionSetting.timeMomentConditionValue2());
                  break; 
               } 
               default: { 
                  self.simpleValue1 = ko.observable('');
                  self.simpleValue2 = ko.observable('');
                  break; 
               } 
            } 
            
        }
        
        saveSetting(){
            alert('tranfer D');
        }
        
        cancelSetting(){
            alert('Cancel!');
        }
        
    }
}