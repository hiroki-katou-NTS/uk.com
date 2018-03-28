module cps001.h.vm {
    import text = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import error = nts.uk.ui.dialog.alertError;
    import alertError = nts.uk.ui.dialog.alertError;
    import confirm = nts.uk.ui.dialog.confirm;
    import close = nts.uk.ui.windows.close;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    let __viewContext: any = window['__viewContext'] || {},
        block = window["nts"]["uk"]["ui"]["block"]["grayout"],
        unblock = window["nts"]["uk"]["ui"]["block"]["clear"],
        invisible = window["nts"]["uk"]["ui"]["block"]["invisible"];

    export class ViewModel {
        columns: KnockoutObservableArray<NtsGridListColumn>;
        items: KnockoutObservableArray<IResvLeaGrantRemNum> = ko.observableArray([]);
        currentItem: KnockoutObservable<string> = ko.observable("");
        leaveExpirationStatus:  KnockoutObservableArray<any>;
        resvLeaGrantRemNum: KnockoutObservable<ResvLeaGrantRemNum> = ko.observable(new ResvLeaGrantRemNum(<IResvLeaGrantRemNum>{}));
        enableRemoveBtn: KnockoutObservable<boolean> = ko.observable(true);
        isCreate: KnockoutObservable<boolean> = ko.observable(false);
        ckbAll: KnockoutObservable<boolean> = ko.observable(true);
        
        constructor() {
            let self = this;
            self.ckbAll.subscribe((data) => {
                service.getAll(data).done((data) => {
                 if(data && data.length > 0){
                    self.items(data);   
                   self.currentItem(self.items()[0].id);
                 }else{
                     self.create();
                 }
            });  
            });
            self.currentItem.subscribe((id: string)=>{                 
                    service.getByGrantDate(id).done((curItem) => {
                        self.resvLeaGrantRemNum(new ResvLeaGrantRemNum(<IResvLeaGrantRemNum>curItem));
                        if(curItem){
                            self.enableRemoveBtn(true);
                            self.isCreate(false);
                            }
                    });                 
                });
            self.columns = ko.observableArray([
               { headerText: "", key: 'id', hidden: true},
               { headerText: text("CPS001_118"), key: 'grantDate', width: 100, isDateColumn: true, format: 'YYYY/MM/DD'},
               { headerText: text("CPS001_119"), key: 'deadline', width: 100, isDateColumn: true, format: 'YYYY/MM/DD'}, 
               { headerText: text("CPS001_120"), key: 'expirationStatus', width: 70}, 
               { headerText: text("CPS001_121"), key: 'grantDays', width: 70},
               { headerText: text("CPS001_130"), key: 'useDays', width: 70},
               { headerText: text("CPS001_123"), key: 'overLimitDays', width: 70},
               { headerText: text("CPS001_129"), key: 'remainingDays', width: 70}
           ]); 
        }
        load() :JQueryPromise<any>{
            let self = this, dfd = $.Deferred();
             service.getAll(self.ckbAll()).done((data) => {
                 if(data && data.length > 0){
                    self.items(data);       
                 }else{
                     self.create();
                 }
                 dfd.resolve();
            });  
            return dfd.promise();
        }
        start() {
           let self  = this;
           self.setDef();
           self.load().done(()=>{
               if(self.items().length > 0){
                   self.currentItem(self.items()[0].id);
               }
           });
        }
        setDef(){
            let self = this;
            service.getItemDef().done((data) => {
                $("td[data-itemCode]").each(function(){ 
                    let itemCodes = $(this).attr('data-itemCode');
                    if(itemCodes){
                        let itemCodeArray = itemCodes.split(" ");
                        _.forEach(itemCodeArray, (itemCode) => {
                            let itemDef = _.find(data, (item)=>{
                                return item.itemCode == itemCode;
                            });
                            if(itemDef){
                                if(itemDef.display){
                                    $(this).children().first().html("<label>" + itemDef.itemName + "</label>");
                                }
                            }
                        });
                    }
                });
                 
                
            });
            }
        
        close(){
            close();
        }
        
        remove(){
            let self = this;
            nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                block();
                let delItemIndex = _.findIndex(self.items(), (item)=>{
                    return item.id == self.resvLeaGrantRemNum().id();
                });
                let selectedId;
                if(delItemIndex == self.items().length-1){
                    if(self.items().length > 1){
                        selectedId = self.items()[delItemIndex-1].id;
                    }
                }else{
                    selectedId = self.items()[delItemIndex+1].id;
                }
                service.remove(self.resvLeaGrantRemNum().id()).done(() => {
                    self.load().done(()=>{
                        if(self.items().length == 0){
                             self.create();
                        }else{
                                self.currentItem(selectedId);
                        }
                    });
                    alert({ messageId: "Msg_16" });
                    unblock();
                }).fail((mes) => {
                    unblock();
                });
            });
        }
        
        register(){
            let self = this;
            
            $("#grantDate").trigger("validate");
            $("#deadline").trigger("validate");
            $("#grantDays").trigger("validate");
            $("#useDays").trigger("validate");
            $("#overLimitDays").trigger("validate");
            $("#remainingDays").trigger("validate");
            
            if (!$(".nts-input").ntsError("hasError")) {
                let item = self.resvLeaGrantRemNum(), 
                grantDate = moment.utc(item.grantDate(),"YYYY/MM/DD"),
                deadline = moment.utc(item.deadline(),"YYYY/MM/DD");
                if(grantDate > deadline){
                    error({ messageId: "Msg_1023", messageParams: [] });
                    return;
                }
                if(self.isCreate()){
                    service.create(grantDate, deadline, item.expirationStatus(),
                    item.grantDays(), item.useDays(), item.overLimitDays(), item.remainingDays()).done(() => {
                        self.load();
                        alert({ messageId: "Msg_15" });
                        unblock();
                    }).fail((mes) => {
                        unblock();
                    });
                }else{
                   service.update(item.id(), grantDate, deadline, item.expirationStatus(),
                    item.grantDays(), item.useDays(), item.overLimitDays(), item.remainingDays()).done(() => {
                        self.load();
                        alert({ messageId: "Msg_15" });
                        unblock();
                    }).fail((mes) => {
                        unblock();
                    }); 
                }
            }
           
        }
        
        create(){
            let self = this;
            self.currentItem("-1");
            self.enableRemoveBtn(false);
            self.isCreate(true);
            self.resvLeaGrantRemNum(new ResvLeaGrantRemNum(<IResvLeaGrantRemNum>{}));
        }

    }
    class ResvLeaGrantRemNum{
        id:  KnockoutObservable<string> = ko.observable("");
        grantDate:  KnockoutObservable<string> = ko.observable("");
        deadline:  KnockoutObservable<string> = ko.observable("");
        expirationStatus:  KnockoutObservable<number> = ko.observable(1);
        grantDays:  KnockoutObservable<string> = ko.observable("");
        useDays:  KnockoutObservable<string> = ko.observable("");
        overLimitDays:  KnockoutObservable<string> = ko.observable("");
        remainingDays:  KnockoutObservable<string> = ko.observable("");       
        constructor(data: IResvLeaGrantRemNum){
            this.id(data && data.id || "");
            this.grantDate(data && data.grantDate || "");
            this.deadline(data && data.deadline || "");
            this.expirationStatus(data == undefined ? 1 : (data.expirationStatus == undefined ? 1: data.expirationStatus));
            this.grantDays(data &&data.grantDays || "");
            this.useDays(data &&data.useDays || "");
            this.overLimitDays(data &&data.overLimitDays || "");
            this.remainingDays(data &&data.remainingDays || "");
        }
    }
    
    interface IResvLeaGrantRemNum{
        id: string;
        grantDate: string;
        deadline: string;
        expirationStatus: number;
        grantDays: string;
        useDays: string;
        overLimitDays: string;
        remainingDays: string;
    }
  
}