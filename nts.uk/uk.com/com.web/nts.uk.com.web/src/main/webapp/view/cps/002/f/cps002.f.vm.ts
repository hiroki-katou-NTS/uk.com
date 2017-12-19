module cps002.f.vm {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import close = nts.uk.ui.windows.close;
    import modal = nts.uk.ui.windows.sub.modal;
    import alert = nts.uk.ui.dialog.alert;
    import alertError = nts.uk.ui.dialog.alertError;


    export class ViewModel {
        lstCategory: KnockoutObservableArray<PerInfoCtg>;
        currentPerInfoCtg: KnockoutObservable<string> = ko.observable("");
        lstPerInfoItemDef: KnockoutObservableArray<PerInforItemDef>;
        columns: KnockoutObservableArray<any>;
        columnPerInfoItemDef: KnockoutObservableArray<any>;
        currentPerInfoItem: KnockoutObservableArray<string> = ko.observableArray([]);
        txtSearch: KnockoutObservable<string> = ko.observable("");

        constructor() {
            let self = this;
            self.lstCategory = ko.observableArray([]);
            self.lstPerInfoItemDef = ko.observableArray([]);

            self.columns = ko.observableArray([
                { headerText: "", key: 'id', width: 45, hidden: true },
                { headerText: nts.uk.resource.getText("CPS002_70"), key: 'alreadyCopy', width: 45, formatter: setPerInfoCtgFormat },
                { headerText: nts.uk.resource.getText("CPS002_71"), key: 'categoryName', width: 150 }
            ]);

            self.columnPerInfoItemDef = ko.observableArray([
                { headerText: "", key: 'id', width: 45, hidden: true },
                { headerText: nts.uk.resource.getText("CPS002_75"), key: 'itemName', width: 250 }
            ]);


            self.currentPerInfoCtg.subscribe(id => {
                service.getPerInfoItemDef(id).done(function(data) {
                    self.lstPerInfoItemDef(data);
                    let perItemCopy = _.filter(data, function(item: IPerInfoItemDef) { return item.alreadyItemDefCopy == true; }).map(function(item) { return item.id; });
                    self.currentPerInfoItem(perItemCopy);
                });
                //                let dataArr = _.filter(self.data.lstPerItem, function(item){ return item.perCtgId == id;});
                //                let perItemCopy = _.filter(dataArr, function(item){ return item.alreadyItemDefCopy == true;}).map(function(item){return item.id;});
                //                self.currentPerInfoItem(perItemCopy);
                //                self.lstPerInfoItemDef(dataArr);  
            });

            self.start();

        }
        register() {
            let self = this;
            // let dataBind = self.lstPerInfoItemDef();
            let itemIds = self.currentPerInfoItem();
            let categoryId = self.currentPerInfoCtg();

            service.updatePerInfoItemCopy(categoryId, itemIds).done(() => {
                alertError({ messageId: "Msg_15" }).then(() => {
                    self.start(self.currentPerInfoCtg());
                });
            })


        }
        close() {
            close();
        }
        start(ctgid?) {
            let self = this;
            //let dataArr = self.data.listPerCtg;
            //self.lstCategory(dataArr);
            //self.currentPerInfoCtg(dataArr[0].id);
            let ctgName = "";
            service.getPerInfoCtgHasItems(ctgName).done(function(data) {
                self.lstCategory(data);
                self.currentPerInfoCtg(ctgid ? ctgid : data[0].id);
                $("#searchCtg input").focus();
            }).fail(function() {
                alertError({ messageId: "Msg_352" });
            });

        }

        searchByName() {
            let self = this;
            service.getPerInfoCtgHasItems(self.txtSearch()).done(function(data) {
                self.lstCategory(data);
            }).fail(function() {
                alertError({ messageId: "Msg_352" });
            });
        }

    }

    interface IPerInfoCtg {
        id: string,
        alreadyCopy: boolean,
        categoryName: string
    }
    class PerInfoCtg {
        id: KnockoutObservable<string> = ko.observable("");
        alreadyCopy: KnockoutObservable<boolean> = ko.observable(false);
        categoryName: KnockoutObservable<string> = ko.observable("");
        constructor(param: IPerInfoCtg) {
            let self = this;
            self.id(param.id || "");
            self.alreadyCopy(param.alreadyCopy || false);
            self.categoryName(param.categoryName || "");
        }
    }

    interface IPerInfoItemDef {
        id: string,
        itemName: string,
        perCtgId: string,
        alreadyItemDefCopy: boolean
    }
    class PerInforItemDef {
        id: KnockoutObservable<string> = ko.observable("");
        itemName: KnockoutObservable<string> = ko.observable("");
        perCtgId: KnockoutObservable<string> = ko.observable("");
        alreadyItemDefCopy: KnockoutObservable<boolean> = ko.observable(false);
        constructor(param: IPerInfoItemDef) {
            let self = this;
            self.id(param.id || "");
            self.itemName(param.itemName || "");
            self.itemName(param.perCtgId || "");
            self.alreadyItemDefCopy(param.alreadyItemDefCopy || false);
        }
    }
}

function setPerInfoCtgFormat(value) {
    if (value == "true")
        return '<i class="icon icon-dot setStyleDot"></i>';
    return '';
}
