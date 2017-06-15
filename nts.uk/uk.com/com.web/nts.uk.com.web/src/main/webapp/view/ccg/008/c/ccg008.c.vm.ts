module nts.uk.com.view.ccg008.c {
    export module viewmodel {
        export class ScreenModel {
            index: number;
            dataItems: KnockoutObservableArray<model.Node>;
            selectedCode: KnockoutObservable<string>;
            columns: KnockoutObservableArray<any>;
            item: KnockoutObservable<model.Node>;
            itemSelected: KnockoutObservable<model.TopPageSelfSet>;
            constructor() {
                var self = this;
                self.columns = ko.observableArray([
                    { headerText: '', prop: 'code', hidden: "hidden" },
                    { headerText: nts.uk.resource.getText("CCG008_8"), prop: 'name', width: 70 }
                ]);
                self.dataItems = ko.observableArray([]);
                self.itemSelected = ko.observable(null);
                self.selectedCode = ko.observable(null);
                self.item = ko.observable(null);
            }

            start(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<void>();
                service.getSelectMyTopPage().done(function(lst: Array<model.Node>) {
                    if (lst === null || lst === undefined || lst.length == 0) {
                        self.dataItems([]);
                        self.selectedCode();
                    } else {
                        var items = _.map(lst, function(item: any) {
                            return new model.Node(item.code, item.name);
                        });
                        self.dataItems(items);
                        service.getTopPageSelfSet().done(function(topPageSelfSet: model.TopPageSelfSet) {
                            let itemSelected = new model.TopPageSelfSet(topPageSelfSet.code, topPageSelfSet.division);
                            self.itemSelected(self.find(itemSelected.code));
                            self.item(self.find(itemSelected.code));

                            self.selectedCode(itemSelected.code);
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
           * find top page self set selected
           */
            find(value: string): model.Node {
                let self = this;
                var itemModel = null;
                return _.find(self.dataItems(), function(obj: model.Node) {
                    return obj.code == value;
                })
            }
            register(): void {
                var self = this;
                let data = new model.TopPageSelfSet(self.selectedCode(), self.itemSelected().division);
                service.save(data).done(function(res) {
                }).fail(function(err) {
                    nts.uk.ui.dialog.alert(err);
                });
            }

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
                var self = this;
                self.code = code;
                self.name = name;
            }
        }
        export class TopPageSelfSet {
            code: string;
            division: number;
            constructor(code: string, division: number) {
                this.code = code;
                this.division = division;
            }
        }
    }
}