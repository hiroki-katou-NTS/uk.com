module nts.uk.com.view.cps009.b.viewmodel {
    import error = nts.uk.ui.errors;
    import text = nts.uk.resource.getText;
    import close = nts.uk.ui.windows.close;
    import dialog = nts.uk.ui.dialog;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import block = nts.uk.ui.block;

    export class ViewModel {
        itemInitLst: KnockoutObservableArray<any> = ko.observableArray([]);
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;
        categoryName: KnockoutObservable<string> = ko.observable('');
        itemColumns: Array<any> = [];
        currentItem: KnockoutObservableArray<any> = ko.observableArray([]);
        constructor() {

            let self = this;
            self.roundingRules = ko.observableArray([
                { code: '1', name: ReferenceMethodType.NOSETTING },
                { code: '2', name: ReferenceMethodType.FIXEDVALUE },
                { code: '3', name: ReferenceMethodType.SAMEASLOGIN }
            ]);

            self.itemColumns = [
                { headerText: 'perInfoItemDefId', key: 'perInfoItemDefId', width: 10, hidden: true },
                { headerText: 'isRequired', key: 'isRequired', width: 10, hidden: true },
                { headerText: nts.uk.resource.getText('CPS009_33'), key: 'itemName', width: 200 }];
            self.selectedRuleCode = ko.observable(1);
        }
        /**
         * get data from db when start
         */
        start(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            self.itemInitLst([]);
            let param = getShared('CPS009B_PARAMS') || { ctgName: '', settingId: '', categoryId: '' };
            self.categoryName(param.ctgName);
            service.getAllItemByCtgId(param.settingId, param.categoryId).done(function(data) {
                //ドメインモデル「個人情報項目定義」を取得できているかどうかをチェック (Kiểm tra 「個人情報項目定義」 có lấy được hay không)
                if (data == null || data == undefined || data.length == 0) {
                    //データ件数＝０(Không)
                    //メッセージ(#Msg_353#)を表示、トップページへ遷移する (Hiển thị ErrorMessage Msg_353, Chuyển đến TopPage) 
                    nts.uk.ui.dialog.alertError({ messageId: 'Msg_353' }).then(function() {
                        close();
                    });
                } else {
                    
                    self.itemInitLst(data);


                }
                dfd.resolve();
            });

            return dfd.promise();
        }
        /**
         * send data to screen main when click button 決定
         */
        registerItems() {
            let self = this,
                obj = {
                    isCancel: false,
                    refMethodType: self.selectedRuleCode(),
                    lstItem: self.currentItem()
                };;
            if (self.currentItem().length == 0) {
                //メッセージ（Msg_362)を表示 (Hiển thị Error Message Msg_362)
                nts.uk.ui.dialog.alertError({ messageId: 'Msg_362' });
                return;
            }

            setShared('CPS009B_DATA', obj);

            close();
        }
        /**
         * close dialog when click button キャンセル
         */
        closeDialog() {
            let self = this,
                obj = {
                    isCancel: true,
                    refMethodType: 0,
                    lstItem: []

                };
            setShared('CPS009B_DATA', obj);
            close();
        }

    }

    export enum ReferenceMethodType {
        NOSETTING = '設定なし',
        FIXEDVALUE = '固定値',
        SAMEASLOGIN = 'ログイン者と同じ'
    }
}