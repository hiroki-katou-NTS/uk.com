module nts.uk.com.view.cps017.b.viewmodel {
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import close = nts.uk.ui.windows.close;
    export class ScreenModel {
        listSelection: KnockoutObservableArray<any> = ko.observableArray([]);
        constructor() {
        }

        //開始
        start(): JQueryPromise<any> {
            let self = this,
            selectedHisId = getShared('selectedHisId');//get histId ben screen A
            let dfd = $.Deferred();
            nts.uk.ui.errors.clearAll();
            
            self.listSelection.subscribe(function(newSource){
                if(!nts.uk.util.isNullOrEmpty(newSource) && !nts.uk.util.isNullOrEmpty($('#item_register_grid2').children())){
                    let source = ko.toJS(newSource);
                    $('#item_register_grid2').igGrid("option", "dataSource", source);
                    $('#item_register_grid2').igGrid("dataBind");    
                }
            });
            service.getAllOrderSetting(selectedHisId).done((itemList: Array<ISelection>) => {
                if (itemList && itemList.length > 0) {
                    let i = 1;
                    itemList.forEach(x => {
                        self.listSelection.push({id: i, 
                            selectionID: x.selectionID,
                            histId: x.histId,
                            selectionCD: x.selectionCD,
                            selectionName: x.selectionName,
                            externalCD: x.externalCD,
                            memoSelection: x.memoSelection,
                            initSelection: x.initSelection == 1? true : false });
                        i++;
                    });
                }
                dfd.resolve();
            }).fail(error => {
                alertError({ messageId: "Msg_455" });
            });

            return dfd.promise();
        }
        /**
         * register
         */
        register(){
            let self = this;
            let row = $("#item_register_grid2").igGridSelection("selectedRow");
            if(row == null || row == undefined){
                return;
            }
            let itemSeleted: ISelection = self.findItemSelected(row.id);
            let data = {
                selectionID: itemSeleted.selectionID,
                histId: itemSeleted.histId,
                selectionCD: itemSeleted.selectionCD,
                selectionName: itemSeleted.selectionName,
                externalCD: itemSeleted.externalCD,
                memoSelection: itemSeleted.memoSelection
            };
//            service.updateDataSelection(data).done(function(){
//                //情報メッセージ（#Msg_15）を表示する (Hiển thị InfoMessage Msg_15)
//                info({ messageId: "Msg_15" }).then(function() {
//                    //close dialog
////                    close();
//                });
//            }).always(() => {
//                block.clear();
//            });;
        }
        /**
         * find item is selected.
         */
        findItemSelected(id: any): any{
            let self = this;
            return _.find(self.listSelection(), function(item){
                return item.id = id;
            })
        }
        
        close(){
            close();
        }
    }
    
    
    //Selection
    interface ISelection {
        id?: number;
        selectionID?: string;
        histId?: string;
        selectionCD: string;
        selectionName: string;
        externalCD: string;
        memoSelection: string;
        initSelection: any;
    }
    class Selection {
        selectionID: KnockoutObservable<string> = ko.observable('');
        histId: KnockoutObservable<string> = ko.observable('');
        selectionCD: KnockoutObservable<string> = ko.observable('');
        selectionName: KnockoutObservable<string> = ko.observable('');
        externalCD: KnockoutObservable<string> = ko.observable('');
        memoSelection: KnockoutObservable<string> = ko.observable('');
        initSelection: KnockoutObservable<boolean> = ko.observable(false);

        constructor(param: ISelection) {
            let self = this;
            self.selectionID(param.selectionID || '');
            self.histId(param.histId || '');
            self.selectionCD(param.selectionCD || '');
            self.selectionName(param.selectionName || '');
            self.externalCD(param.externalCD || '');
            self.memoSelection(param.memoSelection || '');
            self.initSelection(param.initSelection|| false);
        }
    }
}

