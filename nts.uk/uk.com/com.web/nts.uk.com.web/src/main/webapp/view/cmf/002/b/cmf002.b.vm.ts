module nts.uk.com.view.cmf002.b.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import model = cmf002.share.model;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    export class ScreenModel {
        isNewMode:                KnockoutObservable<boolean> = ko.observable(true);
        conditionSettingList:     KnockoutObservableArray<IConditionSet> = ko.observableArray([]);
        outputItemList:           KnockoutObservableArray<IOutputItem>   = ko.observableArray([]);
        selectedConditionSetting: KnockoutObservable<string> = ko.observable('');
        notUseAtrItems:           KnockoutObservableArray<model.ItemModel> = ko.observableArray(getNotUseAtrItems());
        delimiterItems:           KnockoutObservableArray<model.ItemModel> = ko.observableArray(getDelimiterItems());
        stringFormatItems:        KnockoutObservableArray<model.ItemModel> = ko.observableArray(getStringFormatItems());
        categoryName:             KnockoutObservable<string>       = ko.observable('名名名名名名名名名名');
        conditionSetData:         KnockoutObservable<ConditionSet> = ko.observable(new ConditionSet ({
            companyId: '',
            conditionSetCode: '',
            conditionSetName: '',
            categoryId: '',
            conditionOutputName: 0,
            automaticExecution: 0,
            delimiter: 0,
            stringFormat: 0,
            outputItemCode: ''
        }));
        

        constructor() {
            let self = this;
            let domainmode = 1;
            self.initScreen();
        }
        
        /**
         * 起動する
         * アルゴリズム「外部出力条件設定一覧」を実行する
         */
        initScreen(conditionSetCode: string){
            let self = this;
            let itemList: Array<IConditionSet> = [];
            let outputItemList: Array<IOutputItem> = [];
            let conditionSetCodeParam: string = '';
            
            //アルゴリズム「外部出力取得設定一覧」を実行する
            //TODO: Tạo domain ngoài
            service.getCndSet().done((itemList: Array<IConditionSet>) =>{
                if (itemList && itemList.length > 0) {
                    self.conditionSettingList(itemList);
                   
                    let index : number = 0;
                    if (conditionSetCode) {
                        index = _.findIndex(self.conditionSettingList(), function(x: IConditionSet)
                        { return x.conditionSetCode == conditionSetCode });
                        if (index === -1) index = 0;
                    }
                    let _conditionSet = self.conditionSettingList()[index];
                    self.settingCurrentCondition(_conditionSet);
                    self.settingUpdateMode();
                } else {
                    self.settingNewMode();
                }
            });
            
            for (let i = 1; i <= 30; i++) {
                itemList.push(ko.toJS({ companyId: '1', conditionSetCode: i >= 10 ? '0' + i : '00' + i, conditionSetName: '名名名名名名名名名名' }));
                outputItemList.push(ko.toJS({outputItemCode: i >=10 ? '0' + i: '00' + i, outputItemName: '名名名名名名名名名名'}));
            }
            
            self.conditionSettingList(itemList);
            self.outputItemList(outputItemList);
        }

        /**
         * アルゴリズム「外部出力設定選択」
         */
        selectExternalOutputSetting(conditionSetCode: string) {

        }

        /**
         * Setting each item on screen
         */
        settingCurrentCondition(condSet: IConditionSet) {
            let self = this;
            if (self.conditionSetData().conditionSetCode() === condSet.conditionSetCode) {
                return;
            }
            self.conditionSetData().companyId(condSet.companyId);
            self.conditionSetData().conditionSetCode(condSet.conditionSetCode);
            self.conditionSetData().conditionSetName(condSet.conditionSetName);
            self.conditionSetData().categoryId(condSet.categoryId);
            self.conditionSetData().conditionOutputName(condSet.conditionOutputName);
            self.conditionSetData().automaticExecution(condSet.automaticExecution);
            self.conditionSetData().delimiter(condSet.delimiter);
            self.conditionSetData().stringFormat(condSet.stringFormat);
            self.conditionSetData().outputItemCode(condSet.outputItemCode);
        }

        /**
         * 画面モード　＝　新規
         */
        settingNewMode() {
            let self = this;
            self.isNewMode(true);
        }

        /**
         * 画面モード　＝　更新
         */
        settingUpdateMode() {
            let self = this;
            self.isNewMode(false);
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
            new model.ItemModel(0, getText("Enum_Delimeter_NONE")),
            new model.ItemModel(1, getText("Enum_Delimeter_COMMA")),
            new model.ItemModel(2, getText("Enum_StringFormat_SEMICOLON")),
            new model.ItemModel(3, getText("Enum_Delimeter_TAB")),
            new model.ItemModel(4, getText("Enum_Delimeter_SPACE"))
        ];
    }

    //文字列形式選択
    export function getStringFormatItems(): Array<model.ItemModel> {
        return [
            new model.ItemModel(0, getText("Enum_StringFormat_NONE")),
            new model.ItemModel(1, getText("Enum_StringFormat_DOUBLEQUOTATION")),
            new model.ItemModel(2, getText("Enum_StringFormat_SINGLEQUOTATION"))
        ];
    }

    export interface IConditionSet {
        companyId: string;
        conditionSetCode: string;
        conditionSetName: string;
        categoryId: string;
        conditionOutputName: number;
        automaticExecution: number;
        delimiter: number;
        stringFormat: number;
        outputItemCode:string;
    }

    export class ConditionSet {
        companyId:            KnockoutObservable<string> = ko.observable('');
        conditionSetCode:     KnockoutObservable<string> = ko.observable('');
        conditionSetName:     KnockoutObservable<string> = ko.observable('');
        categoryId:           KnockoutObservable<string> = ko.observable('');
        conditionOutputName:  KnockoutObservable<number> = ko.observable(0);
        automaticExecution:   KnockoutObservable<number> = ko.observable(0);
        delimiter:            KnockoutObservable<number> = ko.observable(0);
        stringFormat:         KnockoutObservable<number> = ko.observable(0);
        outputItemCode:       KnockoutObservable<string> = ko.observable('');
        constructor(param: IConditionSet) {
            let self = this;
            self.companyId(param.companyId);
            self.conditionSetCode(param.conditionSetCode || '');
            self.conditionSetName(param.conditionSetName || '');
            self.categoryId(param.categoryId || '');
            self.conditionOutputName(param.conditionOutputName || 0);
            self.automaticExecution(param.automaticExecution || 0);
            self.delimiter(param.delimiter || 0);
            self.stringFormat(param.stringFormat || 0);
            self.outputItemCode(param.outputItemCode || '');
        }
    }

    export interface IOutputItem {
        outputItemCode: string;
        outputItemName: string;
    }
    
    public addNew(){
        
    }
    
    public register(){
        
    }
    export class OutputItem {
        outputItemCode: KnockoutObservable<string> = ko.observable('');
        outputItemname: KnockoutObservable<string> = ko.observable('');
        constructor(param: IOutputItem) {
            let self = this;
            self.outputItemCode(param.outputItemCode || '');
            self.outputItemname(param.outputItemName || '');
        }
    }
}
