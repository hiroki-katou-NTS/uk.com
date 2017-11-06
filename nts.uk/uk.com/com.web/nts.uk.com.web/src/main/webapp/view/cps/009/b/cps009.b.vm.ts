module nts.uk.com.view.cps009.b.viewmodel {
    import error = nts.uk.ui.errors;
    import text = nts.uk.resource.getText;
    import close = nts.uk.ui.windows.close;
    import dialog = nts.uk.ui.dialog;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import block = nts.uk.ui.block;

    export class ViewModel {
        itemInitLst: Array<any> = [];
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;
        categoryName: KnockoutObservable<string> = ko.observable('');
        dataSource: Array<any> =[];
        constructor() {

            let self = this;
            self.roundingRules = ko.observableArray([
                { code: '1', name: ReferenceMethodType.NOSETTING },
                { code: '2', name: ReferenceMethodType.FIXEDVALUE },
                { code: '3', name: ReferenceMethodType.SAMEASLOGIN }
            ]);
            self.selectedRuleCode = ko.observable(1);
        }
        /**
         * get data from db when start
         */
        start(): JQueryPromise<any> {
            let self = this,
            dfd = $.Deferred();
            self.itemInitLst = [];
            let param = getShared('CPS009B_PARAMS') || { ctgName: '', settingId: '', categoryId: ''};
            self.categoryName(param.ctgName);
            service.getAllItemByCtgId(param.settingId, param.categoryId).done(function(data){
                if(data == null || data == undefined || data.length == 0){
                    self.itemInitLst = [];
                    dfd.resolve();
                    return dfd.promise();
                }
                self.dataSource = data;
                _.each(self.dataSource, function(item){
                     self.itemInitLst.push(new ItemInitValue(item.perInfoItemDefId,item.itemCode, item.itemName, item.isRequired, false));
                });
                dfd.resolve();
            });
            
            return dfd.promise();
        }
        /**
         * send data to screen main when click button 決定
         */
        registerItems() {
            let self = this;
            //対象項目選択があろうかどうかをチェック (Kiểm tra có Item được chọn không)
            let lstIdResult = [];
            let lstItemResult = [];
            _.each(self.itemInitLst, function(item){
                if(item.isCheckBox){
                    lstIdResult.push(item.id);
                }
            });
            if(lstIdResult.length == 0){
                //メッセージ（Msg_362)を表示 (Hiển thị Error Message Msg_362)
                nts.uk.ui.dialog.alertError({ messageId: 'Msg_362'});
                return;    
            }
            _.each(lstIdResult, function(itemId){
                let item = self.findItem(self.dataSource, itemId);
                lstItemResult.push(item);
            });
            setShared('CPS009B_DATA', {
                refMethodType: self.selectedRuleCode(),
                lstItem: lstItemResult
            });
            close();
        }
        /**
         * close dialog when click button キャンセル
         */
        closeDialog() {
            setShared('CPS009B_DATA', null);
            close();
        }
        /**
         * find item by id
         */
        findItem(lstItem: Array<any> , id: string): any{
            return _.find(lstItem, function(obj){
                return obj.perInfoItemDefId == id;
            });
        }
    }

    export class ItemInitValue {
        id: string;
        itemCode: string;
        itemName: string;
        isRequired: number;
        isCheckBox: boolean;
        constructor(id: string, itemCode: string,
            itemName: string, isRequired: number,
            isCheckBox: boolean) {
            let self = this;
            self.id = id;
            self.itemCode = itemCode;
            self.itemName = itemName;
            self.isRequired = isRequired;
            self.isCheckBox = isCheckBox;
        }
    }
    export enum ReferenceMethodType {
        NOSETTING = '設定なし',
        FIXEDVALUE = '固定値',
        SAMEASLOGIN = 'ログイン者と同じ'
    }
}