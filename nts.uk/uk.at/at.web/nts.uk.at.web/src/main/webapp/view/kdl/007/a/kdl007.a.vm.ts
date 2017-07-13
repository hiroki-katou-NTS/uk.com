module kdl007.a.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import alertError = nts.uk.ui.dialog.alertError;

    export class ScreenModel {
        isMulti: boolean = true;
        posibleItems: KnockoutObservableArray<string> = ko.observableArray([]);
        currentCodeList: KnockoutObservableArray<string> = ko.observableArray([]);
        dataSources: KnockoutObservableArray<ItemModel> = ko.observableArray([new ItemModel({ code: "", name: getText("KDL007_6") })]);
        lstCodeOld: KnockoutObservableArray<string> = ko.observableArray([]);
        constructor() {
            let self = this;
            self.start();
        }

        //load data
        start() {
            let self = this,
                param: IData = getShared('KDL007_PARAM') || { isMulti: false, workplaceCode: null,standardDate: null, selecteds: [] };

            self.isMulti = param.isMulti || false;

            // approved selected code from param
            self.currentCodeList(param.selecteds || []);
            self.currentCodeList.remove(x => x == '');
            self.lstCodeOld(self.currentCodeList());
            // remove all items when started, except first item
            self.dataSources.remove(x => x.code != '');

            // get all item 
            service.getAllItem().done(function(resp: Array<ItemModel>) {
            // demo data    
//            $.Deferred().resolve([]).promise().done((resp: Array<ItemModel>) => {
                if (resp && resp.length) {
                    let posibleItems: Array<string> = self.posibleItems(),
                        selectedItems: Array<string> = self.currentCodeList();

                    // push item to datasource
                    _.each(resp, x => self.dataSources.push(x));

                    //filter real selected code from source
                    self.currentCodeList(_.filter(resp, x => selectedItems.indexOf(x.code) > -1).map(x => x.code));
                    if(self.currentCodeList().length == 0 ){
                        self.currentCodeList([''])
                    }
                    if(self.isMulti==false && self.currentCodeList().length > 1 ){
                        self.currentCodeList([''])
                    }
                    $("#multi-list").focus();
                }
            });
        }

        // push data to parent screen
        register() {
            if (typeof this.currentCodeList() == 'object' && !this.currentCodeList().length) {
                alertError({ messageId: "Msg_30" });
                return;
            }
            this.currentCodeList.remove(x => x== '');
                let self = this,
                    items: Array<ItemModel> = ko.toJS(self.dataSources),
                    codeList: Array<string> = ko.toJS(self.currentCodeList);     

            setShared('KDL007_VALUES', { selecteds: self.isMulti ? codeList : [codeList] }, true);

            close();
        }

        close() {
           let param: IData = getShared('KDL007_PARAM') || { isMulti: false, workplaceCode: null,standardDate: null, selecteds: [] };
            if(this.isMulti==false && this.lstCodeOld().length > 1 ){
                
                setShared('KDL007_VALUES',null);
            }else{
                setShared('KDL007_VALUES',param.workplaceCode);
            }
            
            close();
        }
    }

    interface IItemModel {
        code: string;
        name: string;
    }

    class ItemModel {
        code: string;
        name: string;

        constructor(param: IItemModel) {
            this.code = param.code;
            this.name = param.name;
        }
    }

    interface IData {
        isMulti: boolean,
        workplaceCode?: any,
        standardDate?: any,
        selecteds: Array<any>
    }
}