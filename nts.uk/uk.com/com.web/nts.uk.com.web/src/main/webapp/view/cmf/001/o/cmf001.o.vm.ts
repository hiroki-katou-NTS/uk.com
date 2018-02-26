module nts.uk.com.view.cmf001.o.viewmodel {
    import model = cmf001.share.model;
    import getText = nts.uk.resource.getText;
    import lv = nts.layout.validate;
    import vc = nts.layout.validation;
    import dialog = nts.uk.ui.dialog.info;
    
    export class ScreenModel {
        //wizard
        stepList: Array<NtsWizardStep> = [];
        stepSelected: KnockoutObservable<NtsWizardStep> = ko.observable(null);
        activeStep: KnockoutObservable<number> = ko.observable(0);

        listSysType: KnockoutObservableArray<model.ItemModel> = ko.observableArray([]);
        selectedSysType: KnockoutObservable<string> = ko.observable('');

        listCondition: KnockoutObservableArray<model.ExternalAcceptanceCategoryItemData> = ko.observableArray([]);
        selectedCategoryItem: KnockoutObservable<string> = ko.observable('');
        selectedCategoryItemName: KnockoutObservable<string> = ko.observable('');

        //upload file
        stereoType: KnockoutObservable<string> = ko.observable('');
        fileId: KnockoutObservable<string> = ko.observable('');
        filename: KnockoutObservable<string> = ko.observable('');
        fileInfo: KnockoutObservable<any> = ko.observable("");
        textId: KnockoutObservable<string> = ko.observable("CMF001_447");
        accept: KnockoutObservableArray<string> = ko.observableArray(['.csv']);
        asLink: KnockoutObservable<boolean> = ko.observable(false);
        enable: KnockoutObservable<boolean> = ko.observable(true);

        //gridlist step2   
        listAccept: KnockoutObservableArray<AcceptItems> = ko.observableArray([]);
        selectedAccept: KnockoutObservable<any> = ko.observable('');
        count: KnockoutObservable<number> = ko.observable(0);

        constructor() {
            var self = this;
            
            //起動する
            self.stepList = [
                { content: '.step-1' },
                { content: '.step-2' }
            ];
            self.stepSelected = ko.observable({ id: 'step-1', content: '.step-1' });
           
            self.loadSystemType();

            //選択したカレント行の「条件コード/名称」を画面右側の「条件コード/名称」にセットする
            self.selectedCategoryItem.subscribe(function(data: any) {
                //取込情報を選択する
                if (data) {
                    let item = _.find(ko.toJS(self.listCondition), (x: model.ExternalAcceptanceCategoryItemData) => x.itemCode == data);
                    //選択したカレント行の「条件コード/名称」を画面右側の「条件コード/名称」にセットする
                    self.selectedCategoryItemName(item.itemName);
                }
            });
            
            //self.loadListAccept();   
            $("#grd_Accept").ntsFixedTable({ height: 373});
        }

        //次の画面へ遷移する
        next() {
            let self = this;           

            //条件コードは選択されているか判別
            if(self.selectedCategoryItem() == ''){
                //Msg_963　を表示する。受入条件が選択されていません。
                dialog({ messageId: "Msg_963" });
                return;
            }
            
            //受入ファイルがアップロードされているか判別
            if(self.filename() == ''){
                //Msg_964　を表示する。受入ファイルがアップロードされていません。
                dialog({ messageId: "Msg_964" });
                return;
            }            

            //P:外部受入サマリー画面へ遷移する
            $('#ex_accept_wizard').ntsWizard("next");
            self.loadListAccept();
        }
        previous() {
            //受入設定選択に戻る
            $('#ex_accept_wizard').ntsWizard("prev");
        }

        //file upload
        upload() {
            var self = this;
            $("#file-upload").ntsFileUpload({ stereoType: "flowmenu" }).done(function(res) {
                self.fileId(res[0].id);
            }).fail(function(err) {
                nts.uk.ui.dialog.alertError(err);
            });
        }

        test(id) {
            alert("hey");
        }

        loadSystemType() {
            let self = this;

            //Imported(共通)　「システムコード」を取得する

            //「システムコード」の取得結果からシステム種類に変換する

            self.listSysType = ko.observableArray([
                new model.ItemModel(1, '基本給'),
                new model.ItemModel(2, '役職手当'),
                new model.ItemModel(3, '基本給ながい文字列ながい文字列ながい文字列')
            ]);
            
            //1件以上取得できた場合
            if(self.listSysType().length > 0){ 
                //システム種類を画面セットする
                self.selectedSysType(self.listSysType()[0].code);
                
                //ドメインモデル「受入条件設定（定型）」を取得する
                self.loadListCondition(self.selectedSysType());
            }
            //システム種類が取得できない場合
            else{
                //トップページに戻る
                nts.uk.request.jump("/view/cmf/001/a/index.xhtml");
            }            
            
            //システム種類を変更する
            self.selectedSysType.subscribe(function(data: any) {
                //画面上の条件コード/名称をクリアする
                self.selectedCategoryItem('');
                self.selectedCategoryItemName('');
                
                //ドメインモデル「受入条件設定（定型）」を取得する
                self.loadListCondition(data);
            });
        }

        loadListCondition(sysType) {
            let self = this;
            
            //「条件設定一覧」を初期化して取得した設定を表示する
            $('.clear-btn.ntsSearchBox_Component').click();
            self.listCondition([]);
            
            //ドメインモデル「受入条件設定（定型）」を取得する
            if(sysType == 1){
                for (let i = 1; i < 20; i++) {
                    self.listCondition.push(new model.ExternalAcceptanceCategoryItemData(i, 'item' + i));
                } 
            }
            
            //表示するデータがある場合       
            if(self.listCondition().length > 0){    
                //取得した設定を「条件設定一覧」に表示する
                self.selectedCategoryItem(self.listCondition()[0].itemCode());
                self.selectedCategoryItemName(self.listCondition()[0].itemName());     
            }     
            //取得データが0件の場合      
            else{
                //エラーメッセージ表示　Msg_907　外部受入設定が作成されていません。
                dialog({ messageId: "Msg_907" });
            }        
        }

        loadListAccept() {
            let self = this;
            
            //アップロードしたファイルを読み込む
            self.listAccept([]);
            for (let i = 1; i < 8; i++) {
                self.listAccept.push(new AcceptItems('00' + i, '基本給', "description " + i, "基本給" + i, "基本給" + i + i));
            }            
            //ファイルの行数を取得する
            self.count(self.listAccept().length);
        }
        
        editIngestion(itemType){
            console.log(itemType);
            switch(itemType){                
                case 0:  
                    //数値型の場合                    
                    //G:「数値型設定」ダイアログをモーダルで表示する
                    break;               
                case 1:  
                    //文字型の場合
                    //H:「文字型設定」ダイアログをモーダルで表示する
                    break;
                case 2: 
                    //日付型の場合  
                    //I:「日付型設定」ダイアログをモーダルで表示する
                    break;  
                case 3:
                    //時間型の場合, 時刻型の場合 
                    //J:「時刻型・時間型設定」ダイアログをモーダルで表示する
                    break;   
            }
        }
        
        receiveCondition(){
            //L:「受入条件設定ダイアログをモーダルで表示する
        }
        
        exeAccept(){
            //Q:「外部受入処理中ダイアログ」をチェック中で起動する 
        }
    }

    class AcceptItems {
        id: string;
        infoName: string;
        itemName: string;
        sampleData: string;
        itemType: string;
        constructor(id: string, infoName: string, itemName: string, sampleData: string, itemType: string) {
            this.id = id;
            this.infoName = infoName;
            this.itemName = itemName;
            this.sampleData = sampleData;
            this.itemType = itemType;
        }
    }
}