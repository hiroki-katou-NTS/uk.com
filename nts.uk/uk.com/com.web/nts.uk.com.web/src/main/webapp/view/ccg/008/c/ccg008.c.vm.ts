module nts.uk.com.view.ccg008.c {
    export module viewmodel {
        export class ScreenModel {
            dataItems: KnockoutObservableArray<model.Node>;
            selectedCode: KnockoutObservable<string>;
            columns: KnockoutObservableArray<any>;
            itemSelected: KnockoutObservable<model.TopPageSelfSet>;
            constructor() {
                var self = this;
                self.columns = ko.observableArray([
                    { headerText: 'コード', key: 'code', width: 100, hidden: true},
                    { headerText: nts.uk.resource.getText("CCG008_8"), key: 'name' }
                ]);
                self.dataItems = ko.observableArray([]);
                self.itemSelected = ko.observable(null);
                self.selectedCode = ko.observable(null);
            }
            /**
             * Khi khoi dong dialog
             * Lay du lieu tu domain トップページ va 標準メニュー
             * Lay du lieu tu domain 本人トップページ設定 
             */
            start(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<void>();
                //Lay du lieu tu domain トップページ
                service.getSelectMyTopPage().done(function(lst: Array<model.Node>) {
                    if (lst === null || lst === undefined || lst.length == 0) {
                        self.dataItems([]);
                        self.selectedCode();
                    } else {
                        var items = [];
                        _.map(lst, function(item: any) {
                            items.push({"code":item.code , "name": item.name});
                        });
                        self.dataItems(items);
                        //Lay du lieu tu domain 本人トップページ設定 
                        service.getTopPageSelfSet().done(function(topPageSelfSet: model.TopPageSelfSet) {
                            if(topPageSelfSet===null|| topPageSelfSet===undefined){
                                //Truong hop khong co du lieu
                                self.selectedCode();
                            }else{
                                self.selectedCode(topPageSelfSet.code);
                            }
                        })
                        dfd.resolve();
                    }
                    dfd.resolve();
                }).fail(function(err) {
                    nts.uk.ui.dialog.alert(err);
                });
                return dfd.promise();
            }
            /**
             * tim item dang duoc lua chon
             */
            find(code: string): any {
                let self = this;
                var itemModel = null;
                return _.find(self.dataItems(), function(obj: any) {
                return  obj.code == code;
                })
            }
            /**
             * Khi click button 決定
             * Xu ly dang ky top page self set
             */
            register(): void {
                var self = this;
                let test = self.find(self.selectedCode());
                //khong co item nao duoc chon
                if(test===null||test===undefined){
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_128"});
                }else{
                    //co item duoc chon
                    let data = new model.TopPageSelfSet(self.selectedCode());
                    service.save(data).done(function(res) {
                        nts.uk.ui.windows.close();
                    }).fail(function(err) {
                        nts.uk.ui.dialog.alert(err);
                    });
                }
            }
            /**
             * Khi click button キャンセル
             * Dong man hinh
             */
            closeDialog() {
                nts.uk.ui.windows.close();
            }
        }
    }
    export module model {
        export class Node {
            code: string;
            name: string;
            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }
        export class TopPageSelfSet {
            code: string;
            constructor(code: string) {
                this.code = code;
            }
        }
    }
}