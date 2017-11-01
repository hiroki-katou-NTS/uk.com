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
        itemColumns: KnockoutObservableArray<any> = ko.observableArray([
            { headerText: 'id', key: 'id', width: 100, hidden: true },
            { headerText: text('CPS009_33'), key: 'itemName', width: 200 },
        ]);

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

        start(): JQueryPromise<any> {
            let self = this,
            dfd = $.Deferred();
            self.itemInitLst = [];
            let param = getShared('CPS009B_PARAMS') || { settingName: '', settingId: '', categoryId: ''};
             self.categoryName(param.settingName);
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

        registerItems() {
            let self = this;
            //対象項目選択があろうかどうかをチェック (Kiểm tra có Item được chọn không)
            let check = 0;
            _.each(self.itemInitLst, function(item){
                if(item.isCheckBox) check++;
            });
            if(check == 0){
                //メッセージ（Msg_362)を表示 (Hiển thị Error Message Msg_362)
                nts.uk.ui.dialog.alertError({ messageId: 'Msg_362'});
                return;    
            }
            setShared('CPS009B_DATA', {
                refMethodType: self.selectedRuleCode(),
                lstItem: self.itemInitLst
            });
            close();
        }

        closeDialog() {
            setShared('CPS009B_DATA', null);
            close();
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