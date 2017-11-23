module nts.uk.at.view.kdw003.d.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import windows = nts.uk.ui.windows;
    
    export class ScreenModel {
        gridListColumns: KnockoutObservableArray<any>;
        lstErrorAlarm: KnockoutObservableArray<any>;

        constructor() {
            let self = this;
            self.gridListColumns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KDW003_58"), key: 'code', width: 45 },
                { headerText: nts.uk.resource.getText("KDW003_59"), key: 'name', width: 280 }
            ]);
            self.lstErrorAlarm = ko.observableArray([]);
        }
        
        /**
         * 起動する
         */
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            service.getErrorList().done((lstData) => {
                let sortedData = _.orderBy(lstData, ['code'], ['asc']);
                self.lstErrorAlarm(sortedData);
                dfd.resolve();
            });
            return dfd.promise();
        }
        
        /**
         * 抽出する
         */
        extract() {
            let self = this;

            // set return value
            setShared('errorAlarmList', self.lstErrorAlarm());

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