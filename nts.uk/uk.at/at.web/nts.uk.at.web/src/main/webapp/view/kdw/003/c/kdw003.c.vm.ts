module nts.uk.at.view.kdw003.c.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import windows = nts.uk.ui.windows;
    
    export class ScreenModel {
        gridListColumns: KnockoutObservableArray<any>;
        lstFormatCodes: KnockoutObservableArray<any>;
        selectedFormatCode: KnockoutObservable<string>;
        formatParam : FormatParam = null;
        constructor() {
            let self = this;
            self.gridListColumns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KDW003_2"), key: 'dailyPerformanceFormatCode', width: 45 },
                { headerText: nts.uk.resource.getText("KDW003_3"), key: 'dailyPerformanceFormatName', width: 280 }
            ]);
            self.lstFormatCodes = ko.observableArray([]);
            self.selectedFormatCode = ko.observable('');
            self.formatParam = nts.uk.ui.windows.getShared('KDW003C_Param');
            if(nts.uk.util.isNullOrUndefined(self.formatParam)){
                self.formatParam = new FormatParam(0, '');
            }
            self.selectedFormatCode(self.formatParam.selectedItem);
        }
        
        /**
         * 起動する
         */
        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            
            if(self.formatParam.initMode == 0){
                service.getDailyFormatList().done((lstData) => {
                    let sortedData = _.orderBy(lstData, ['dailyPerformanceFormatCode'], ['asc']);
                    self.lstFormatCodes(sortedData);
                    dfd.resolve();
                }).fail(function(error) {
                    nts.uk.ui.dialog.alert(error.message);
                    dfd.reject();
                });               
            }else if(self.formatParam.initMode == 1){
                service.getMonthlyFormatList().done((lstData) => {
                    let sortedData = _.orderBy(lstData, ['dailyPerformanceFormatCode'], ['asc']);
                    self.lstFormatCodes(sortedData);
                    dfd.resolve();
                }).fail(function(error) {
                    nts.uk.ui.dialog.alert(error.message);
                    dfd.reject();
                });       
            }
            return dfd.promise();
        }
        
        /**
         * 選択する
         */
        select() {
            let self = this;
            // set return value
            setShared('KDW003C_Output', self.selectedFormatCode());
            // close dialog.
            windows.close();
        }
        
        /**
         * キャンセル
         */
        closeDialog(): void {
            windows.close();
        }
    }
    /**
     * パラメータ  
     */
    export class FormatParam{
        //起動モード：日別=0 or 月別=1
        initMode : number;
        //選択済項目：    日別実績のフォーマットコード
　　　　　　　//           or 月別実績のフォーマットコード
        selectedItem : string;
        constructor(initMode : number, selectedItem : string){
            let self = this;
            self.initMode = initMode;
            self.selectedItem = selectedItem;
        }
    }
    export class DailyPerformanceFormat {
        /* 会社ID */
        companyId: string;
        /* コード */
        dailyPerformanceFormatCode: string;
        /* 名称 */
        dailyPerformanceFormatName: string;

        constructor() {
            this.companyId = '';
            this.dailyPerformanceFormatCode = '';
            this.dailyPerformanceFormatName = '';
        }
    }
}