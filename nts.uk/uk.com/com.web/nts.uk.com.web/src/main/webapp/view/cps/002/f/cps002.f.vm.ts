module cps002.f.vm {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import close = nts.uk.ui.windows.close;
    import modal = nts.uk.ui.windows.sub.modal;
    import alert = nts.uk.ui.dialog.alert;
    import alertError = nts.uk.ui.dialog.alertError;

    export class ViewModel {
        lstCategory: KnockoutObservableArray<PerInfoCtg>;
        lstPerInfoItemDef: KnockoutObservableArray<PerInforItemDef>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        columnPerInfoItemDef: KnockoutObservableArray<NtsGridListColumn>;
        currentCode: KnockoutObservable<any>;
        perInfoItemDefList: KnockoutObservableArray<any>;
        constructor(){
            let self = this;
            self.lstCategory = ko.observableArray([]);
            self.lstPerInfoItemDef = ko.observableArray([]);
            self.currentCode = ko.observable("");
            self.perInfoItemDefList = ko.observableArray([]);
            self.columns = ko.observableArray([
                {headerText: "", key: 'categoryCode', width: 45, hidden: true},
                {headerText: nts.uk.resource.getText("CPS002_70"), key: 'categoryCode', width: 45},
                {headerText: nts.uk.resource.getText("CPS002_71"), key: 'categoryName', width: 150}
            ]);
            self.columnPerInfoItemDef = ko.observableArray([
                {headerText: "", key: 'id', width: 45, hidden: true},
                {headerText: nts.uk.resource.getText("CPS002_74"), key: 'perInfoCtgId', width: 45, hidden: true},
                {headerText: nts.uk.resource.getText("CPS002_74") + "<input type='checkbox' class='txtCheckBox ui-icon ui-icon-check' checked />", 
                key: 'itemName', width: 100, formatter: setFormat},
                {headerText: nts.uk.resource.getText("CPS002_75"), key: 'itemName', width: 200}
            ]);
           
            self.getPerInfoCtgLst();
        }
        getPerInfoCtgLst(){
            let self = this;
            service.getPerInfoCtgHasItems().done(function(data){
                self.lstCategory(data);
               // self.formatHeader();
            }).fail(function(){
                alertError({ messageId: "Msg_352" });
            });
        }
       
        formatHeader(){
          $("#multiList_virtualContainer tr>th:first").prepend(nts.uk.resource.getText("CPS002_74"));
          $("#multiList_virtualContainer tr>th:first").css("width", "100px");
        }
        
    }
    
    interface IPerInfoCtg{
        categoryCode: string,
        categoryName: string
    }
    class PerInfoCtg{
        categoryCode: KnockoutObservable<string> = ko.observable("");
        categoryName: KnockoutObservable<string> = ko.observable("");
        constructor(param: IPerInfoCtg){
            let self = this;
            self.categoryCode(param.categoryCode || "");
            self.categoryName(param.categoryName || "");
        }
    }
    
    interface IPerInfoItemDef{
        id: string, 
        perInfoCtgId: string,
        itemName: string,
        isAbolition: number,
        systemRequired: number
    }
    class PerInforItemDef{
        id: KnockoutObservable<string> = ko.observable("");
        perInfoCtgId: KnockoutObservable<string> = ko.observable("");
        itemName: KnockoutObservable<string> = ko.observable("");
        isAbolition: KnockoutObservable<number> = ko.observable(0);
        systemRequired: KnockoutObservable<number> = ko.observable(0);
        constructor(param: IPerInfoItemDef){
            let self = this;
            self.id(param.id || "");
            self.perInfoCtgId(param.perInfoCtgId || "");
            self.itemName(param.itemName || "");
            self.isAbolition(param.isAbolition || 0);
            self.systemRequired(param.systemRequired || 0);
        }
    }
   
    
}
function setPerInfoCtgFormat(value){
    if (value == "true")
        return '<i class="icon icon-dot"></i>';
    return '';
}
function  setFormat(value){
    if(value == "true")
        return "<input type='checkbox' class='txtCheckBox ui-icon ui-icon-check' checked/>";
    else return "<input type='checkbox' class='txtCheckBox ui-icon ui-icon-check'/>";
}