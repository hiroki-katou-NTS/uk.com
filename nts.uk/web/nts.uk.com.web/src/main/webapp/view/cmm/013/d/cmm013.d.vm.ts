module cmm013.d.viewmodel {
    export class ScreenModel {
        inp_003: KnockoutObservable<string>;
        inp_003_enable: KnockoutObservable<boolean>;
        //startDateUpdate: KnockoutObservable<string>;
        endDateUpdate: KnockoutObservable<string>;
        //startDateNew: KnockoutObservable<string>;
        //D_SEL_001
        itemList: KnockoutObservableArray<any>;
        selectedId: KnockoutObservable<number>;
        enable: KnockoutObservable<boolean>;
        //deleteChecker: KnockoutObservable<number>;
        //dudt
        //startDateLast: KnockoutObservable<string>;
        //message
        lstMessage: KnockoutObservableArray<ItemMessage>;
        //dudt
        histIdUpdate: KnockoutObservable<string>;
        
        
        
        //dudt
        oldStartDate:  KnockoutObservable<string>;
        //dudt
        constructor() {
            var self = this;
            self.inp_003 = ko.observable(null);
            self.inp_003_enable = ko.observable(true);
            //self.startDateNew = ko.observable('');
            //self.startDateUpdate = ko.observable('');
            self.endDateUpdate = ko.observable('');
            //D_SEL_001
            self.enable = ko.observable(true); 
            //self.deleteChecker = ko.observable(0);
            //dudt
            //self.startDateLast = ko.observable('');
            self.oldStartDate = ko.observable('');
            self.lstMessage = ko.observableArray([]);
            //dudt
            self.histIdUpdate = ko.observable('');
        }
        
        startPage(): JQueryPromise<any>{
            var self = this;
            var dfd = $.Deferred();
            //self.deleteChecker(nts.uk.ui.windows.getShared('delete'));
            //self.startDateUpdate(nts.uk.ui.windows.getShared('startUpdate').split('/').join("-"));
            //thay doi lai ten shared quy tac cho them ma chuc nang vao
            self.endDateUpdate(nts.uk.ui.windows.getShared('cmm013EndDate'));
            //dudt
            //self.startDateLast(nts.uk.ui.windows.getShared('startDateLast'));
            self.oldStartDate(nts.uk.ui.windows.getShared('cmm013StartDate'))
            //dudt
            self.histIdUpdate(nts.uk.ui.windows.getShared('cmm013HistoryId'));
            //dudt
//            if(self.deleteChecker()==1){//option delete
//                self.itemList = ko.observableArray([
//                    new BoxModel(1, '履歴を削除する '),
//                    new BoxModel(2, '履歴を修正する')
//                ]);
//                self.selectedId = ko.observable(2);
//                self.selectedId.subscribe((function(codeChanged){
//                    if(codeChanged==1){
//                        self.inp_003_enable(false);  
//                    }
//                    else{
//                        self.inp_003_enable(true);    
//                    }  
//                }));
//            }
//            if(self.deleteChecker()==2){//not option delete
//                self.itemList = ko.observableArray([
//                    new BoxModel(1, '履歴を修正する')
//                ]);
//                self.selectedId = ko.observable(1);
//            }
//            self.inp_003(self.startDateUpdate());
            self.listMessage();
            self.setValueForRadio();
            self.selectedId.subscribe(function(newValue){
                if(newValue == 1){
                    self.inp_003_enable(false);
                }else{
                    self.inp_003_enable(true);    
                }
            })
            self.inp_003(self.oldStartDate());
            //neu khong phai lich su moi nhat thi khong duoc xoa va chỉ mặc định được sửa thôi
            if(self.endDateUpdate() === "9999/12/31"){
                self.enable(false);     
            }else{
                self.enable(true);
            }
            
            //dudt
            dfd.resolve();
            return dfd.promise();      
        }
        
        //dudt
        setValueForRadio() : any{
            var self = this;
            self.itemList = ko.observableArray([
                new BoxModel(1, '履歴を削除する '),
                new BoxModel(2, '履歴を修正する')
            ]);
            self.selectedId = ko.observable(2);
        }
                //list  message
        listMessage(): any{
            var self = this;
            self.lstMessage.push(new ItemMessage("ER001", "*が入力されていません。"));
            self.lstMessage.push(new ItemMessage("ER005","入力した*は既に存在しています。\r\n*を確認してください。"));
            self.lstMessage.push(new ItemMessage("ER010","対象データがありません。"));
            self.lstMessage.push(new ItemMessage("AL001","変更された内容が登録されていません。\r\nよろしいですか。"));
            self.lstMessage.push(new ItemMessage("AL002", "データを削除します。\r\nよろしいですか？"));
            self.lstMessage.push(new ItemMessage("ER026", "更新対象のデータが存在しません。"));
            self.lstMessage.push(new ItemMessage("ER023","履歴の期間が重複しています。"));
        }
        //clear du lieu dua sang dialog
        clearShared(): any{
            nts.uk.ui.windows.setShared('cmm013StartDate', '', true);
            nts.uk.ui.windows.setShared('cmm013EndDate', '', true);
            nts.uk.ui.windows.setShared('cmm013HistoryId', '', true);
        }
        //dudt        
         closeDialog(){
             var self = this;
             //neu chua co lich su can chuyen ve man hinh top
             //can bo sung them xu ly
             
             self.clearShared();
             nts.uk.ui.windows.close();   
        }
        positionHis(){
            let self = this;
            //dudt
//            if(self.selectedId()==2 && self.deleteChecker()==1){
//                if(self.inp_003() >= self.endDateUpdate()||self.inp_003()<=self.startDateUpdate()){
//                    alert("Re Input");    
//                    return;
//                }
//            }
//            let dfd = $.Deferred<any>();
//            if(self.selectedId()==1 && self.deleteChecker()==1){   
//                let jobHist = new model.ListHistoryDto(self.startDateUpdate(), '', self.endDateUpdate(), self.histIdUpdate());
//                    
//                var checkDelete ='1';
//                var checkUpdate = '0';
//            }else{
//                checkDelete = '0';
//                var jobHist = new model.ListHistoryDto(self.startDateUpdate(), self.inp_003(), self.endDateUpdate(), self.histIdUpdate());
//                if (self.startDateUpdate()==self.startDateLast()) {
//                    
//                    checkUpdate = '2';
//                } else {
//                   checkUpdate = '1';
//                }
//            }
//            let afterUpdate = new model.AfUpdate(jobHist, checkUpdate, checkDelete);
//            service.updateHist(afterUpdate).done(function() {
//                alert('update thanh cong');
//                nts.uk.ui.windows.setShared('Finish',true, true);
//                nts.uk.ui.windows.close();
//            }).fail(function(res) {
//                dfd.reject(res);
//            })            
            
            //neu xay ra truong hop xoa
            var historyInfo = new model.ListHistoryDto(self.histIdUpdate(), self.oldStartDate(), self.inp_003());
            if(self.selectedId() === 1){                
                // hoi xem có muốn xóa không 
                var AL002 = _.find(self.lstMessage(), function(mess){
                    return  mess.messCode === "AL002";
                })
                nts.uk.ui.dialog.confirm(AL002.messName).ifCancel(function(){
                   return; 
                }).ifYes(function(){
                    service.deleteHistory(historyInfo).fail(function(res: any){
                        var delMess = _.find(self.lstMessage(), function(mess){
                            return  mess.messCode === res.message;
                        })
                        nts.uk.ui.dialog.alert(delMess.messName);
                    }).done(function(){
                        //dong man hinh
                        self.clearShared();
                        nts.uk.ui.windows.close();    
                    })
                })
            //neu xay ra truoong hop sua
            }else{
                //check xem ngay nhap vao co nho hon ngay start hien tai ko
                var originallyStartDate = new Date(self.oldStartDate());
                var newStartDate = new Date(self.inp_003());
                if(originallyStartDate >= newStartDate){
                     var AL023 = _.find(self.lstMessage(), function(mess){
                        return  mess.messCode === "ER023";
                    })
                    nts.uk.ui.dialog.alert(AL023.messName);
                }else{
                    service.updateHistory(historyInfo).fail(function(res: any){
                        var upMess = _.find(self.lstMessage(),function(mess){
                            return mess.messCode === res.message;
                        })    
                    }).done(function(){
                        //dong man hinh
                        self.clearShared();
                        nts.uk.ui.windows.close();    
                    })
                }
            }
            
            //dudt
        }
    }

    class BoxModel{
        id: number;
        name: string;
        constructor(id: number, name: string){
            this.id = id;
            this.name = name;
        }    
    }
        
    export module model {
//        export class AfUpdate{
//            jHist: ListHistoryDto;
//            checkUpdate: string;
//            checkDelete: string;
//            constructor(jHist: ListHistoryDto, checkUpdate: string, checkDelete: string){
//                this.jHist = jHist;
//                this.checkUpdate = checkUpdate;
//                this.checkDelete = checkDelete;
//            }    
//        }
        export class ListHistoryDto{
            historyId: string;
            oldStartDate: string;
            newStartDate: string;
            constructor(historyId: string, oldStartDate: string, newStartDate: string){
                this.historyId = historyId;
                this.oldStartDate = oldStartDate;
                this.newStartDate = newStartDate;
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
