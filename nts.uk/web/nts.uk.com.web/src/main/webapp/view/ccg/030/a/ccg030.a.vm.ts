module ccg030.a.viewmodel {
    export class ScreenModel {
        // Grid list
        items: KnockoutObservableArray<model.ItemList>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        currentCode: KnockoutObservable<string>;
        toppagepartcode: any;
        toppagepartname: any;
        width: any;
        height: any;
        fileName: KnockoutObservable<string>;
        enableDeleteMenu: KnockoutObservable<boolean>;
        enableDeleteFile: KnockoutObservable<boolean>;
        enableDownload: KnockoutObservable<boolean>;
        flowMenuInfor : KnockoutObservableArray<model.ItemModel>;
        //message
        lstMessage: KnockoutObservableArray<ItemMessage>;
        constructor() {
            var self = this;
            self.fileName = ko.observable("未設定");
            self.enableDeleteMenu = ko.observable(true);
            self.enableDeleteFile = ko.observable(true);
            self.enableDownload = ko.observable(true);
            self.flowMenuInfor = ko.observableArray([]);
            self.lstMessage = ko.observableArray([]);
            //Grid list
            self.items = ko.observableArray([]);
            self.currentCode = ko.observable("");
                        
            self.columns = ko.observableArray([
                { headerText: '既定', key: 'default', width: 50 },
                { headerText: 'コード', key: 'code', width: 80　},
                { headerText: '名称', key: 'name', width: 230　}                
            ]);
            
            //editor  Toppage part Code, Toppage part Name
            self.toppagepartcode = {
                value: ko.observable(''),
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    filldirection: "right",
                    fillcharacter: "0",
                    textmode: "text",
                    placeholder: "",
                    width: "30px",
                    textalign: "left"
                })),
                enableCode: ko.observable(true),
            };
            self.toppagepartname = {
                value: ko.observable(''),
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    filldirection: "right",
                    fillcharacter: "0",
                    textmode: "text",
                    placeholder: "",
                    width: "100px",
                    textalign: "left"
                })),
            };
            //横のサイズ
            self.width = {
                value: ko.observable(''),
            };
            //縦のサイズ
            self.height = {
                value: ko.observable(''),
            };

        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.listMessage();
            self.dataShow();
            self.currentCode.subscribe(function(newValue){
                var flowMenuInf = _.find(self.flowMenuInfor(), function(infor){
                    return infor.topPageCode === newValue;   
                })
                if(flowMenuInf !== undefined){
                    self.enableDeleteMenu(true);
                    self.toppagepartcode.enableCode(false);
                    self.toppagepartcode.value(flowMenuInf.topPageCode);
                    self.toppagepartname.value(flowMenuInf.topPageName);
                    self.fileName(flowMenuInf.fileName);
                    if(self.fileName() !== "未設定"){
                        self.enableDeleteFile(true);
                        self.enableDownload(true);
                    }else{
                        self.enableDeleteFile(false);
                        self.enableDownload(false);
                    }
                    self.width.value(flowMenuInf.widthSize);
                    self.height.value(flowMenuInf.heightSize);
                }
            })
            
            dfd.resolve();
            return dfd.promise();
        }
        
        //初期データ取得処理
        dataShow(){
            var self = this;
            var dfd = $.Deferred<any>();
            self.items([]);
            self.flowMenuInfor([]);
            service.fillAllFlowMenu().done(function(lstResult: Array<viewmodel.model.ItemModel>){
                if(lstResult.length === 0){
                    self.createNewFlowMenu();
                }else{
                    self.enableDeleteMenu(true);
                    self.flowMenuInfor(lstResult);
                    _.forEach(lstResult, function(flowMenuInf){
                        var item = new model.ItemList("", "", "");
                        if(flowMenuInf.defClassAtr === 1){
                            item.defClassStr = "<span style='color: #00B050; font-size: 18px'>●</span>";
                        }
                        item.code = flowMenuInf.topPageCode;
                        item.name = flowMenuInf.topPageName;
                        self.items.push(item);
                    })
                    if(nts.uk.text.isNullOrEmpty(self.toppagepartcode.value())){
                        let firstItem = _.first(self.items());
                        self.currentCode(firstItem.code);
                    }
                }
                dfd.resolve(lstResult);
            }) 
            return dfd.promise();
        }
        
        
        // Open ccg030 B Dialog
        open030B_Dialog() {
            nts.uk.ui.windows.sub.modal("/view/ccg/030/b/index.xhtml", { title: 'プレビュー', dialogClass: "no-close" }).onClosed();
        }
        closeDialog(): any {
            nts.uk.ui.windows.close();
        }
        //新規ボタンをクリックする時
        createNewFlowMenu(){
            var self = this;
            self.toppagepartcode.value("");
            self.toppagepartcode.enableCode(true);
            self.toppagepartname.value("");
            self.currentCode("");
            self.fileName("未設定");
            self.enableDeleteFile(false);
            self.enableDeleteMenu(false);
            self.enableDownload(false);
            self.width.value("");
            self.height.value("");
            $('#inpCode').focus();
        }
        
        //削除ボタンを押す時
        deleteNewFlowMenu(){
            var self = this;
            var dfd = $.Deferred<any>();
            let indexItemDelete = _.findIndex(self.flowMenuInfor(), function(item) { return item.topPageCode == self.toppagepartcode.value(); });
            var item = _.find(self.flowMenuInfor(), function(item){return item.topPageCode == self.toppagepartcode.value();})
            service.deleteFlowMenu(item).done(function(){
                $.when(self.dataShow()).done(function(){
                    if(self.flowMenuInfor().length === 0){
                        self.createNewFlowMenu();
                        self.enableDeleteMenu(false);
                    }else{
                        self.enableDeleteMenu(true);
                        if(self.flowMenuInfor().length === indexItemDelete){
                            self.currentCode(self.items()[indexItemDelete - 1].code);    
                        }else{
                            self.currentCode(self.items()[indexItemDelete].code);    
                        }
                    }
                })
            }).fail(function(res){
                var Msg_76 = _.find(self.lstMessage(), function(mess){
                    return  mess.messCode === res.messageId;
                })
                if(nts.uk.text.isNullOrEmpty(Msg_76)){
                    nts.uk.ui.dialog.alert(res.message);
                }else{
                    nts.uk.ui.dialog.alert(Msg_76.messName);
                }
            })
            dfd.resolve();
            return dfd.promise();
        }
        
        //登録ボタンを押す時
        registryFlowMenu(){
            var self = this;
            var dfd = $.Deferred<any>();
            //check input value
            $('.nts-input').trigger("validate");
            _.defer(() => {
                if (nts.uk.ui.errors.hasError() === false) {
                    var flowMenu = new model.ItemModel();
                    flowMenu.topPageCode = self.toppagepartcode.value();
                    flowMenu.topPageName = self.toppagepartname.value();
                    flowMenu.fileName = self.fileName();
                    flowMenu.widthSize = +self.width.value();
                    flowMenu.heightSize = +self.height.value();
                    //新規の時
                    if(self.toppagepartcode.enableCode()){
                        service.craeteFlowMenu(flowMenu).done(function(){
                            self.toppagepartcode.enableCode(false);
                            self.dataShow();
                            self.currentCode(self.toppagepartcode.value());
                        }).fail(function(res){
                            var Msg_3 = _.find(self.lstMessage(), function(mess){
                                return  mess.messCode === res.messageId;
                            })
                            if(nts.uk.text.isNullOrEmpty(Msg_3)){
                                nts.uk.ui.dialog.alert(res.message);
                            }else{
                                nts.uk.ui.dialog.alert(Msg_3.messName);
                            }
                        })
                        dfd.resolve();
                    }else{
                        //更新の時
                        var item = _.find(self.flowMenuInfor(), function(item){return item.topPageCode == self.toppagepartcode.value();})
                        flowMenu.toppagePartID = item.toppagePartID;
                        flowMenu.defClassAtr = item.defClassAtr;
                        service.updateFlowMenu(flowMenu).done(function(){
                            self.dataShow();
                            self.currentCode(self.toppagepartcode.value());
                        }).fail(function(res){
                            nts.uk.ui.dialog.alert(res.messageId);
                        })
                        dfd.resolve();
                    }
                    return dfd.promise();
                }
            });
        }
        //list  message
        listMessage(): any{
            var self = this;
            self.lstMessage.push(new ItemMessage("Msg_76", "既定フローメニューは削除できません。"));
            self.lstMessage.push(new ItemMessage("Msg_3","入力したコードは、既に登録されています。"));
            self.lstMessage.push(new ItemMessage("ER010","対象データがありません。"));
            self.lstMessage.push(new ItemMessage("AL001","変更された内容が登録されていません。\r\nよろしいですか。"));
            self.lstMessage.push(new ItemMessage("AL002", "データを削除します。\r\nよろしいですか？"));
            self.lstMessage.push(new ItemMessage("ER026", "更新対象のデータが存在しません。"));
        }
    }
    
    
    export module model{        
        export class ItemModel {
            toppagePartID: string;
            fileName: string;
            defClassAtr: number;
            topPageCode: string;
            topPageName: string;
            widthSize: number;
            heightSize: number;
            
            constructor(toppagePartID: string, fileName: string, defClassAtr: number, topPageCode: string, topPageName: string, widthSize: number, heightSize: number) {
                this.toppagePartID = toppagePartID;
                this.fileName = fileName;
                this.defClassAtr = defClassAtr;
                this.topPageCode = topPageCode;
                this.topPageName = topPageName;
                this.widthSize = widthSize;
                this.heightSize =  heightSize;
            }
        }
        
        export class ItemList{
            defClassStr: string;
            code: string;
            name: string
            constructor (defClassStr: string, code: string, name: string){
                this.defClassStr = defClassStr;
                this.code = code;
                this.name = name;    
            }
        }
   }
   export class ItemMessage{
        messCode: string;
        messName: string;
        constructor(messCode: string, messName: string){
            this.messCode = messCode;
            this.messName = messName;    
        }    
    }
}