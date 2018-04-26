module nts.uk.at.view.kdw003.d.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import windows = nts.uk.ui.windows;
    
    export class ScreenModel {
        gridListColumns: KnockoutObservableArray<any>;
        lstErrorAlarm: KnockoutObservableArray<any>;
        lstSelectedErrorAlarm: KnockoutObservableArray<any>;
        errorParam: ErrorParam = null;
        constructor() {
            let self = this;
            self.gridListColumns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KDW003_58"), key: 'code', width: 45 },
                { headerText: nts.uk.resource.getText("KDW003_59"), key: 'name', width: 280 }
            ]);
            self.lstErrorAlarm = ko.observableArray([]);
            self.lstSelectedErrorAlarm = ko.observableArray([]);
            //パラメータ            
            self.errorParam =  nts.uk.ui.windows.getShared("KDW003D_ErrorParam");
            if(nts.uk.util.isNullOrUndefined(self.errorParam)){
                self.errorParam = new ErrorParam(0,[]);
            }
            //選択済項目
            self.lstSelectedErrorAlarm(self.errorParam.selectedItems);
        }
        
        /**
         * 起動する
         */
        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            //日別
            if(self.errorParam.initMode == 0){
                service.getErrorList().done((lstData) => {
                    let sortedData = _.orderBy(lstData, ['code'], ['asc']);
                    self.lstErrorAlarm(sortedData);
                    dfd.resolve();
                }).fail(function(error) {
                    nts.uk.ui.dialog.alert(error.message);
                    dfd.reject();
                });
            }
            //月別
            else if(self.errorParam.initMode == 1){
                service.getMonthlyErrorList().done((lstData) => {
                    let sortedData = _.orderBy(lstData, ['code'], ['asc']);
                    self.lstErrorAlarm(sortedData);
                    dfd.resolve();
                }).fail(function(error) {
                    nts.uk.ui.dialog.alert(error.message);
                    dfd.reject();
                });
            }
            return dfd.promise();
        }
        
        /**
         * 抽出する
         */
        extract() {
            let self = this;
            //選択されたエラーコード一覧
            setShared('KDW003D_Output', self.lstSelectedErrorAlarm());
            // close dialog.
            windows.close();
        }
        
        /**
         * 終了する
         */
        closeDialog(): void {
            windows.close();
        }
    }  
    /**
     * パラメータ  
     */
    export class ErrorParam{
        //起動モード：日別=0 or 月別=1
        initMode : number;
        //選択済項目：    勤務実績のエラーアラームコード
　　　　　　　//           or 月別実績のエラーアラームコード
        selectedItems : Array<string>;
        constructor(initMode : number, selectedItems : Array<string>){
            let self = this;
            self.initMode = initMode;
            self.selectedItems = selectedItems;
        }
    }
    export class ErrorAlarmWorkRecord {
        /* 会社ID */
        companyId: string;
        /* コード */
        code: string;
        /* 名称 */
        name: string;
        /* システム固定とする */
        fixedAtr: number;
        /* 使用する */
        useAtr: number;
        /* 区分 */
        typeAtr: number;
        /* 表示メッセージ */
        displayMessage: string;
        /* メッセージを太字にする */
        boldAtr: number;
        /* メッセージの色 */
        messageColor: string;
        /* エラーアラームを解除できる */
        cancelableAtr: number;
        /* エラー表示項目 */
        errorDisplayItem: number;

        constructor() {
            this.companyId = '';
            this.code = '';
            this.name = '';
            this.fixedAtr = 0;
            this.useAtr = 0;
            this.typeAtr = 0;
            this.displayMessage = '';
            this.boldAtr = 0;
            this.messageColor = '';
            this.cancelableAtr = 0;
            this.errorDisplayItem = null;
        }
    }
}