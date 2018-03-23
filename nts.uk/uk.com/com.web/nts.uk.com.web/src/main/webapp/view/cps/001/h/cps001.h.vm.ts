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
        gDh: KnockoutObservable<boolean> = ko.observable(false);
        
        constructor() {
            let self = this;
            service.getItemDef().done((data) => {
                
            
            self.currentItem.subscribe((gDate)=>{
                service.getByGrantDate(moment.utc(gDate, "YYYY/MM/DD")).done((curItem) => {
                    self.resvLeaGrantRemNum(new ResvLeaGrantRemNum(<IResvLeaGrantRemNum>curItem));
                });
                
            });
            
            self.leaveExpirationStatus = ko.observableArray([
                 { code: '1', name: '使用可能'},
                 { code: '0', name: '期限切れ'}
             ]);
        }
        start() {
            let self  = this;
           service.getAll().done((data) => {
                self.items(data);
               self.setDef();
            });    
        }
        setDef(){
            let self = this;
            service.getItemDef().done((data) => {
                $(".detailContent td").each(function(){ 
                    let itemCodes = $(this).attr('data-itemcode');
                    if(itemCodes){
                        let itemCodeArray = itemCodes.split(" ");
                        _.forEach(itemCodeArray, (itemCode) => {
                            let itemDef = _.find(data, (item)=>{
                                return item.itemCode == itemCode;
                            });
                            if(itemDef){
                                if(itemDef.display){
                                    $(this).children().first().html("<label>" + itemDef.itemName + "</label>");
                                }else{
                                    $(this).parent().css("display", "none");
                                   self.gDh(true);
                                    self.columns = ko.observableArray([
                                       { headerText: text("CPS001_118"), key: 'grantDate', width: 100, isDateColumn: true, format: 'YYYY/MM/DD', hidden: self.gDh()},
                                       { headerText: text("CPS001_119"), key: 'deadline', width: 100, isDateColumn: true, format: 'YYYY/MM/DD'}, 
                                       { headerText: text("CPS001_120"), key: 'expirationStatus', width: 70}, 
                                       { headerText: text("CPS001_121"), key: 'grantDays', width: 70},
                                       { headerText: text("CPS001_130"), key: 'useDays', width: 70},
                                       { headerText: text("CPS001_123"), key: 'overLimitDays', width: 70},
                                       { headerText: text("CPS001_129"), key: 'remainingDays', width: 70}
                                   ]); 
                                let table: string = '<table tabindex="5" id="sel_item_grid" data-bind="ntsGridList: { height: 282, options: items, primaryKey:\'grantDate\',showNumbering: true,columns:columns,multiple: false, value: currentItem}"></table>';
                                $("#tbl").html(table);
                                ko.applyBindings(self, $("#tbl")[0]);
                                });
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
        grantDate:  KnockoutObservable<string> = ko.observable("");
        deadline:  KnockoutObservable<string> = ko.observable("");
        expirationStatus:  KnockoutObservable<string> = ko.observable("");
        grantDays:  KnockoutObservable<string> = ko.observable("");
        useDays:  KnockoutObservable<string> = ko.observable("");
        overLimitDays:  KnockoutObservable<string> = ko.observable("");
        remainingDays:  KnockoutObservable<string> = ko.observable("");       
        constructor(data: IResvLeaGrantRemNum){
            this.grantDate(data && data.grantDate || "");
            this.deadline(data &&data.deadline || "");
            this.expirationStatus(data == undefined ? "1" : (data.expirationStatus == undefined ? "1":data.expirationStatus));
            this.grantDays(data &&data.grantDays || "");
            this.useDays(data &&data.useDays || "");
            this.overLimitDays(data &&data.overLimitDays || "");
            this.remainingDays(data &&data.remainingDays || "");
        }
    }
    
    interface IResvLeaGrantRemNum{
        grantDate: string;
        deadline: string;
        expirationStatus: string;
        grantDays: string;
        useDays: string;
        overLimitDays: string;
        remainingDays: string;
    }
  
}