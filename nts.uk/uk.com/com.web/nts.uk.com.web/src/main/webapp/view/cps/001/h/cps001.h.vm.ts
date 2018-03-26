module cps001.h.vm {
    import text = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
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
        
        constructor() {
            let self = this;
            self.currentItem.subscribe((id: string)=>{                 
                    service.getByGrantDate(id).done((curItem) => {
                        self.resvLeaGrantRemNum(new ResvLeaGrantRemNum(<IResvLeaGrantRemNum>curItem));
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
        start() {
           let self  = this;
             self.setDef();
           service.getAll().done((data) => {
                self.items(data);
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
        
        create(){
            let self = this;
            self.currentItem("");
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