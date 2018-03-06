module nts.uk.com.view.cmf001.q.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import model = cmf001.share.model;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    
    export class ScreenModel {
        isCheckMode: KnockoutObservableArray<boolean>;
        isStopMode: KnockoutObservableArray<boolean>;
        ankenCode: KnockoutObservableArray<any>;
        ankenField: KnockoutObservableArray<any>;
        keikazikan: KnockoutObservableArray<any>;
        shorizyotai: KnockoutObservableArray<any>;
        kensuu: KnockoutObservableArray<any>;
        kugiri: KnockoutObservableArray<any>;
        sou: KnockoutObservableArray<any>;
        ken: KnockoutObservableArray<any>;
        
        exAcOpManage: ExAcOpManage;
        constructor() {
            let self = this;
            
            exAcOpManage = new ExAcOpManage('1', '001', 5, 6, 7, 8, 1);
            
            self.isCheckMode = ko.observable(true);
            self.isStopMode = ko.observable(false);
            self.ankenCode = ko.observable('　001');
            self.ankenField = ko.observable('　A社人事管理情報');
            self.keikazikan = ko.observable('　00:01:27');
            self.shorizyotai = ko.observable('　チェック中。。。       ');
            self.kensuu = ko.observable('　56');
            self.kugiri = ko.observable('/');
            self.sou = ko.observable('100');
            self.ken = ko.observable(' 11');
            
        }
        // 中断ボタン
        stop() {
            let self = this;
            self.isStopMode(true);
            if (self.isCheckMode()) {
                self.shorizyotai('　チェック完了');
            }
             if (!self.isCheckMode()) {
                self.shorizyotai('　完了');
            }
        }
        
        // 受け入れボタン
        importFile() {
            let self = this;
            self.isCheckMode(false);
            self.isStopMode(false);
            self.shorizyotai('　受入中。。。');
        }
        
        //エラーボタン
        gotoErrorList(){
            let self = this;
            setShared('CMF001-R', {
                // add after test
                imexProcessId: '001',
                nameSetting: 'A社人事管理情報'
            }, true);
            
            nts.uk.ui.windows.sub.modal("/view/cmf/001/r/index.xhtml");
        }
        
        // 閉じる
        close(){
             nts.uk.ui.windows.close();
        }
     }
    
    class ExAcOpManage{
        /**
        * 会社ID
        */
        cid: string;
        
        /**
        * 外部受入処理ID
        */
        processId: string;
        
        /**
        * エラー件数
        */
        errorCount: number;
        
        /**
        * 中断するしない
        */
        interruption: number;
        
        /**
        * 処理カウント
        */
        processCount: number;
        
        /**
        * 処理トータルカウント
        */
        processTotalCount: number;
        
        /**
        * 動作状態
        */
        stateBehavior: number;
        
        constructor(cid: string,processId: string, errorCount: number, interruption: number, processCount: number, processTotalCount: number, stateBehavior: number ) {
            let self = this;
            self.cid = cid;
            self.processId =  processId;
            self.errorCount = errorCount;
            self.interruption = interruption;
            self.processCount = processCount;
            self.processTotalCount = processCount;
            self.stateBehavior = stateBehavior;
        }
    }
}