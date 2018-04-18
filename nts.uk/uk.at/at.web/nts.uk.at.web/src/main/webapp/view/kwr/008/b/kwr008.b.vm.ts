module nts.uk.com.view.kwr008.b.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import model = nts.uk.at.view.kwr008.share.model;
    
    export class ScreenModel {
//        systemTypes: KnockoutObservableArray<model.BoxModel> = ko.observableArray([]);
//        systemType: KnockoutObservable<number>;
        
        //enum mode
        screenMode: KnockoutObservable<number> = ko.observable(model.SCREEN_MODE.NEW);
        
        //B5_1
        checked: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;

        
        //B2_2
        listStandardImportSetting : KnockoutObservableArray<model.ItemModel>;
        selectedCode : KnockoutObservable<any>;
        
        //B3_2 B3_3
        inputSettingCode: KnockoutObservable<string>;
        inputProjectName: KnockoutObservable<string>;
        
        //B5_3
        itemRadio: KnockoutObservableArray<any>;
        selectedItemRadio: KnockoutObservable<number>;

        
        constructor() {
            var self = this;
            
            //B2_2
            self.listStandardImportSetting = ko.observableArray([]);
            self.selectedCode = ko.observable();
                 
            //B5_1
            self.checked = ko.observable(true);
            self.enable = ko.observable(true);
            
            //B3_2 B3_3
            self.inputSettingCode = ko.observable('');
            self.inputProjectName = ko.observable('');
            
            //B5_3
            self.itemRadio = ko.observableArray([
                {id: 0, name : getText('CMF001_37')},
                {id: 1, name : getText('CMF001_38')},
                {id: 2, name : getText('CMF001_39')},
            ]);
            self.selectedItemRadio = ko.observable(1);
          
            
            //Event
            $('#listStandardImportSetting').on('click', ()=>{
                self.activeUpdateMode();
            })
            
            //table fixed
            $("#fixed-table").ntsFixedTable({ height: 304, width: 900 });
        }
        
        startPage(): JQueryPromise<any> {
            var self = this;

            //fill data B2_2
            self.listStandardImportSetting([
                new model.ItemModel(2, "a"),
                new model.ItemModel(3, "b"),
                new model.ItemModel(4, "c"),
                new model.ItemModel(5, "d"),
                new model.ItemModel(6, "e"),
                new model.ItemModel(7, "f"),
                new model.ItemModel(8, "g"),
                new model.ItemModel(9, "h"),
                new model.ItemModel(10, "i"),
                new model.ItemModel(11, "k"),
                new model.ItemModel(12, "l"),
                new model.ItemModel(13, "m"),
                new model.ItemModel(14, "n")
               ]);
            
            if(self.listStandardImportSetting().length == 0){
                self.screenMode(model.SCREEN_MODE.NEW);
                self.registerMode();
            }else{
                self.screenMode(model.SCREEN_MODE.UPDATE);
                self.selectedCode(self.listStandardImportSetting()[0]);
                self.selectedCode()
            }    
            
            var test = $.Deferred();
            return test.promise();            
        }
        
        //mode update
        activeUpdateMode(){
            var self = this;
            
            let selected = $('#listStandardImportSetting').ntsGridList('getSelected');
            
            //B3_2
            self.inputSettingCode(selected.id);
            
            //B3_3
            self.inputProjectName(self.listStandardImportSetting()[selected.index].name);
        }
        
        //mode register
        registerMode(){
            var self = this;
            
            //B3_2
            self.inputSettingCode("");
            
            //B3_3
            self.inputProjectName("");
        }
        
        //action active register mode
        activeRegisterMode(){
            var self = this;
            
            self.registerMode();
        }
    }
}
