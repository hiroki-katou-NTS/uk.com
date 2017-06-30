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

        constructor() {
            let self = this;


            self.start();
        }

        //load data
        start() {
            let self = this,
                param: IData = getShared('KDL007_PARAM') || { isMulti: false, posibles: [], selecteds: [] };

            self.isMulti = param.isMulti || false;

            //all possible attendance items
            self.posibleItems(param.posibles || []);

            //selected items
            self.currentCodeList(param.selecteds || []);

            // remove all items when started, except first item
            self.dataSources.remove(x => x.code != '');

            // get all item 
            service.getAllItem().done(function(resp: Array<ItemModel>) {
                if (resp && resp.length) {
                    _.map(resp, x => self.dataSources.push(new ItemModel({ code: x.code, name: x.name })));
                }
            });
        }

        // push data to parent screen
        register() {
            let self = this,
                items: Array<ItemModel> = ko.toJS(self.dataSources),
                codeList: Array<string> = ko.toJS(self.currentCodeList);

            if (typeof codeList == 'object' && !codeList.length) {
                alertError({ messageId: "Msg_30" });
                return;
            }
            
            setShared('KDL007_VALUES', { selecteds: self.isMulti ? codeList : [codeList] }, true);

            self.close();
        }

        close() {
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
        posibles: Array<any>,
        selecteds: Array<any>
    }
}