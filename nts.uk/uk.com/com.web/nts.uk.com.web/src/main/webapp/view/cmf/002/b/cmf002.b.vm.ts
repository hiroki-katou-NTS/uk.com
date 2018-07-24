module nts.uk.com.view.cmf002.b.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import dialog  = nts.uk.ui.dialog;
    import model = cmf002.share.model;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import modal = nts.uk.ui.windows.sub.modal;
    export class ScreenModel {
        isUpdateMode:                   KnockoutObservable<boolean> = ko.observable();
        isNewMode:                      KnockoutObservable<boolean> = ko.observable();
        standType:                      KnockoutObservable<number> = ko.observable();
        index:                          KnockoutObservable<number> = ko.observable();  
        conditionSettingList:           KnockoutObservableArray<IConditionSet> = ko.observableArray([]);
        outputItemList:                 KnockoutObservableArray<IOutputItem>   = ko.observableArray([]);
        selectedConditionSetting:       KnockoutObservable<IConditionSet> = ko.observable();
        selectedConditionSettingCode:   KnockoutObservable<string> = ko.observable('');
        notUseAtrItems:                 KnockoutObservableArray<model.ItemModel> = ko.observableArray(getNotUseAtrItems());
        delimiterItems:                 KnockoutObservableArray<model.ItemModel> = ko.observableArray(getDelimiterItems());
        stringFormatItems:              KnockoutObservableArray<model.ItemModel> = ko.observableArray(getStringFormatItems());
        categoryName:                   KnockoutObservable<string>       = ko.observable('名名名名名名名名名名');
        conditionSetData:               KnockoutObservable<ConditionSet> = ko.observable(new ConditionSet ({
            cId: '',
            conditionSetCode: '',
            conditionSetName: '',
            categoryId: '',
            conditionOutputName: 0,
            automaticExecution: 0,
            delimiter: 0,
            stringFormat: 0,
            outItemCd: ''
        }));
        

        constructor() {
            let self = this;
            self.index(0);
            self.initScreen();
            self.selectedConditionSettingCode.subscribe((data) => {
                self.selectedConditionSetting(self.getConditionName(data));
                if(self.selectedConditionSetting) {
                    self.getOutItem(data);
                    self.settingCurrentCondition();
                    self.settingMode("update");
                }
            });
        }
        
        /**
         * 起動する
         * アルゴリズム「外部出力条件設定一覧」を実行する
         */
        initScreen(conditionSetCode: string){
            block.invisible();
            let self = this;
            let itemList: Array<IConditionSet> = [];
            let outputItemList: Array<IOutputItem> = [];
            let conditionSetCodeParam: string = '';
            self.standType(1);
            //アルゴリズム「外部出力取得設定一覧」を実行する
            //TODO: Tạo domain ngoài
            service.getCndSet().done((itemList: Array<IConditionSet>) =>{
                if (itemList && itemList.length > 0) {
                    self.conditionSettingList(itemList);
                    self.selectedConditionSetting(self.conditionSettingList()[self.index()]);
                    self.selectedConditionSettingCode(self.conditionSettingList()[self.index()].conditionSetCode);
                    self.settingMode("update");
                } else {
                    self.settingMode("new");
                }
            }).fail(function(res: any) {
               
            });
            
            block.clear();
        }

        /**
         * アルゴリズム「外部出力設定選択」
         */
        selectExternalOutputSetting(conditionSetCode: string) {

        }

        /**
         * Setting each item on screen
         */
        settingCurrentCondition() {
            let self = this;
            
            if (!self.conditionSettingList()) {
                return;
            }
            let condSet: IConditionSet = self.conditionSettingList()[self.index()];
            self.conditionSetData().cId(condSet.cId);
            self.conditionSetData().conditionSetCode(condSet.conditionSetCode);
            self.conditionSetData().conditionSetName(condSet.conditionSetName);
            self.conditionSetData().categoryId(condSet.categoryId);
            self.conditionSetData().conditionOutputName(condSet.conditionOutputName);
            self.conditionSetData().automaticExecution(condSet.automaticExecution);
            self.conditionSetData().delimiter(condSet.delimiter);
            self.conditionSetData().stringFormat(condSet.stringFormat);
            self.conditionSetData().outItemCd(condSet.outItemCd);
        }
        
        getOutItem(selectedConditionSettingCode: string){
            let self = this;
            let itemList: Array<IOutputItem> = [];
            service.getOutItem(selectedConditionSettingCode).done((itemList: Array<IOutputItem>) =>{
                self.outputItemList(itemList);
            });
        }
        
        getConditionName(conditionCode){
            let self = this;
            for (let i = 0 ; i < self.conditionSettingList().length ; i++) {
                if ( conditionCode == self.conditionSettingList()[i].conditionSetCode){
                    self.index(i);
                    return self.conditionSettingList()[i];
                }
            }
        }
        
        
       
        
        deleteCnd() {
            let self = this;
            dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                let data :any= {
                    conditionSetCd: self.selectedConditionSettingCode()
                }
                service.deleteCnd(data).done(result => {
                    dialog.info({ messageId: "Msg_16" }).then(() => {
                        self.initScreen();
                    }); 
                });
             });
        }
        
        openCopyScreen() {
            let self = this;
            setShared('CMF002_T_PARAMS', {
                    standType:self.standType() , 
                    conditionSetCd: self.selectedConditionSetting().conditionSetCode , 
                    conditionName: self.selectedConditionSetting().conditionSetName});
            
            modal("/view/cmf/002/t/index.xhtml").onClosed(function() {
                let params = getShared('CMF002_T_Output');
                
                if (params) {
                   // block.invisible();
                    let override = params.overWrite;
                    let destinationCode = params.copyDestinationCode;
                    let destinationName = params.destinationName;
                    let result = params.result;
                    let copyParams: any = {
                                            standType:self.standType(),
                                            destinationCode: destinationCode,
                                            destinationName: destinationName,
                                            categoryId: self.conditionSetData().categoryId(),
                                            conditionSetCode: self.conditionSetData().conditionSetCode(),
                                            conditionSetName: self.conditionSetData().conditionSetName(),
                                            conditionOutputName: self.conditionSetData().conditionOutputName(),
                                            automaticExecution: self.conditionSetData().automaticExecution(),
                                            delimiter: self.conditionSetData().delimiter(),
                                            outItemCd: self.conditionSetData().outItemCd(),
                                            stringFormat: self.conditionSetData().stringFormat()
                    };
                    service.copy(copyParams).done({
                        initScreen();
                    });
                }
                
               
            });
        }
        
        
        openVScreen(){
            let self = this;
            setShared('CMF002_T_PARAMS', {
                    categoryName: self.categoryName()});
            
            modal("/view/cmf/002/v1/index.xhtml").onClosed(function() {
                let params = getShared('CMF002_B_PARAMS');
                if (params.seletion) {
                    self.conditionSetData().categoryId(params.categoryId);
                    self.categoryName(params.categoryName);
                }     
            });
           
        }

        openDscreen(){
            let self = this;
            setShared('CMF002_D_PARAMS', {
                    categoryName: self.categoryName(),
                    categoryId: self.conditionSetData().categoryId(),
                    cndSetCd: self.conditionSetData().conditionSetCode(),
                    cndSetName: self.conditionSetData().conditionSetName()
                    });
            modal("/view/cmf/002/d/index.xhtml");
            
        }
        
        openCscreen(){
            let self = this;
            setShared('CMF002_C_PARAMS', {
                    conditionSetCode: self.conditionSetData().conditionSetCode(),
                    conditionSetName: self.conditionSetData().conditionSetName(),
                    categoryId: self.conditionSetData().categoryId(),
                    categoryName: self.categoryName(),
                    standType: self.standType()
            });
            
            modal("/view/cmf/002/c/index.xhtml").onClosed(function() {
                let params = getShared('CMF002_B_PARAMS');
                let data :any = {
                    conditionSetCode: self.conditionSetData().conditionSetCode(),
                    standType: self.standType()
                }
                if (params.update) {
                    service.outSetContent(data).done((itemList: Array<IOutputItem>) =>{
                        self.outputItemList(itemList);
                    })
                }
                
            });
        }
        
        createNewCondition() {
            let self = this;
            nts.uk.ui.errors.clearAll();
            self.selectedConditionSettingCode('');
            self.selectedConditionSetting(null);
            self.conditionSetData(new ConditionSet ({
                                                    cId: '',
                                                    conditionSetCode: '',
                                                    conditionSetName: '',
                                                    categoryId: '',
                                                    conditionOutputName: 0,
                                                    automaticExecution: 0,
                                                    delimiter: 0,
                                                    stringFormat: 0,
                                                    outItemCd: ''
                                                    }));
            self.settingMode("new");
            $("#B4_3").focus();
        }
           
    
        register(){
            let self = this;
            let data :any = {
                             conditionSetCd: self.conditionSetData().conditionSetCode(),
                             // wait params from V1 screen categoryId: self.conditionSetData().categoryId(),
                             categoryId: 102,
                             delimiter: self.conditionSetData().delimiter(),
                             //itemOutputName: self.conditionSetData().conditionSetCode,
                             autoExecution: self.conditionSetData().automaticExecution(),
                             conditionSetName: self.conditionSetData().conditionSetName(),
                             stringFormat: self.conditionSetData().stringFormat(),
                             standType: self.standType(),
                             newMode: true
            };
            service.register(data).done(result => {
                self.isNewMode(false);
                self.initScreen();
            }).fail(function(res: any) {
                if(res)
                    dialog.info({ messageId: res.messageId });
            });
      
        }


        settingMode(mode :string) {
            let self = this;
            if (mode == "new") {
                self.isNewMode(true);
                self.isUpdateMode(false);
            } else {
                self.isNewMode(false);
                self.isUpdateMode(true);
            }
        }

        start(): JQueryPromise<any> {
            let self = this;
            var dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }
    
}
    //条件名出力選択, 項目名出力選択
    export function getNotUseAtrItems(): Array<model.ItemModel> {
        return [
            new model.ItemModel(0, getText('CMF002_47')),
            new model.ItemModel(1, getText('CMF002_48'))
        ];
    }

    //区切り文字選択
    export function getDelimiterItems(): Array<model.ItemModel> {
        return [
            new model.ItemModel(0, getText('CMF002_358')),
            new model.ItemModel(1, getText('CMF002_359')),
            new model.ItemModel(2, getText('CMF002_360')),
            new model.ItemModel(3, getText('CMF002_361')),
            new model.ItemModel(4, getText('CMF002_362'))
        ];
    }

    //文字列形式選択
    export function getStringFormatItems(): Array<model.ItemModel> {
        return [
            new model.ItemModel(2, getText('CMF002_363')),
            new model.ItemModel(3, getText('CMF002_364')),
            new model.ItemModel(4, getText('CMF002_365'))
        ];
    }

    export interface IConditionSet {
        cId: string;
        conditionSetCode: string;
        conditionSetName: string;
        categoryId: string;
        conditionOutputName: number;
        automaticExecution: number;
        delimiter: number;
        stringFormat: number;
        outItemCd:string;
    }

    export class ConditionSet {
        cId:            KnockoutObservable<string> = ko.observable('');
        conditionSetCode:     KnockoutObservable<string> = ko.observable('');
        conditionSetName:     KnockoutObservable<string> = ko.observable('');
        categoryId:           KnockoutObservable<string> = ko.observable('');
        conditionOutputName:  KnockoutObservable<number> = ko.observable(0);
        automaticExecution:   KnockoutObservable<number> = ko.observable(0);
        delimiter:            KnockoutObservable<number> = ko.observable(0);
        stringFormat:         KnockoutObservable<number> = ko.observable(0);
        outItemCd:       KnockoutObservable<string> = ko.observable('');
        constructor(param: IConditionSet) {
            let self = this;
            self.cId(param.cId);
            self.conditionSetCode(param.conditionSetCode || '');
            self.conditionSetName(param.conditionSetName || '');
            self.categoryId(param.categoryId || '');
            self.conditionOutputName(param.conditionOutputName || 0);
            self.automaticExecution(param.automaticExecution || 0);
            self.delimiter(param.delimiter || 0);
            self.stringFormat(param.stringFormat || 0);
            self.outItemCd(param.outItemCd || '');
        }
    }

    export interface IOutputItem {
        outItemCd: string;
        outItemName: string;
    }
    
    
    export class OutputItem {
        outItemCd: KnockoutObservable<string> = ko.observable('');
        outItemName: KnockoutObservable<string> = ko.observable('');
        constructor(param: IOutputItem) {
            let self = this;
            self.outItemCd(param.outItemCd || '');
            self.outItemName(param.outItemName || '');
        }
    }
    }
