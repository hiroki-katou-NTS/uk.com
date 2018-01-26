module kml002.l.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    export class ScreenModel {
        items: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservableArray<any>;
        currentCodeList: KnockoutObservableArray<any>;
        fixVerticalId: KnockoutObservable<number>;
        rootItems: KnockoutObservableArray<ItemModel>;
        constructor() {
            var self = this;
            self.items = ko.observableArray([]);
            self.rootItems = ko.observableArray([]);
            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KML002_6"), prop: 'totalCountNo', key: 'totalCountNo', width: 55 },
                { headerText: nts.uk.resource.getText("KML002_7"), prop: 'totalTimesName', key: 'totalTimesName', width: 167 },
                { headerText: 'pk', prop: 'primaryKey', key: 'primaryKey', width: 1, hidden: true }
            ]);
            self.currentCodeList = ko.observableArray([]);
            self.currentCodeList.subscribe((newList) => {
                let sortedList = _.sortBy(newList, ["primaryKey"]);
                if (!_.isEqual(newList, sortedList))
                    self.currentCodeList(sortedList);
            });
            self.fixVerticalId = ko.observable(getShared("KML002H_VERTICAL_ID"))();
        }
        start() {
            var self = this,
                dfd = $.Deferred();
            self.getAllTotalTime();
            dfd.resolve();
            return dfd.promise();
        }
        closeDialog(): void {
            nts.uk.ui.windows.close();
        }
        getAllTotalTime(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            $.when(self.findTotalTimes(), self.findVertialCnts()).done(function() {
                var verticalNoList = _.map(self.currentCodeList(), function(item) {
                    return item.totalCountNo;
                });
                var rootItem = _.filter(self.rootItems(), function(item: IItemModel) {
                    return _.indexOf(verticalNoList, item.totalCountNo) < 0;
                });
                self.items(rootItem);

            });
            return dfd.promise();
        }

        findTotalTimes(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            service.findAll().done(function(totalTimeArr: Array<ItemModel>) {
                let lstRootItems = [];
                _.forEach(totalTimeArr, function(res: IItemModel) {
                    if (res.useAtr == 1) {
                        var totalTime: IItemModel = {
                            totalCountNo: res.totalCountNo,
                            totalTimesName: res.totalTimesName
                        };
                        lstRootItems.push(new ItemModel(totalTime));
                    }
                });
                self.rootItems(lstRootItems);
                dfd.resolve();
            });
            return dfd.promise();
        }

        findVertialCnts(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            service.findAllCountNo(self.fixVerticalId).done(function(data) {
                if (data && data.length > 0) {
                    _.forEach(data, function(item) {
                        var countNo = _.find(self.rootItems(), function(currentItem: ItemModel) {
                            return currentItem.totalCountNo == item.verticalCountNo;
                        });
                        if (countNo) {
                            self.currentCodeList.push(countNo);
                        }
                    });
                }

                dfd.resolve();
            });
            return dfd.promise();
        }

        addTotalTime(): JQueryPromise<any> {

            var self = this;
            var dfd = $.Deferred();
            nts.uk.ui.block.invisible();
            if (!nts.uk.util.isNullOrUndefined(self.currentCodeList())) {
                var items = [];
                _.each(self.currentCodeList(), function(x) {
                    items.push(x.totalCountNo);
                })

                var dataTranfer = {
                    fixedItemAtr: self.fixVerticalId,
                    verticalCountNo: items,
                }
                service.addTotalTimes(dataTranfer).done(function(res) {
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId });
                }).always(() => {
                    nts.uk.ui.block.clear();
                });
                return dfd.promise();
            }

            dfd.resolve();
            nts.uk.ui.block.clear();
            return dfd.promise();
        }
    }

    export interface IItemModel {
        primaryKey?: number;
        totalCountNo?: number;
        totalTimesName?: String;

    }
    export class ItemModel {
        primaryKey: number;
        totalCountNo: number;
        totalTimesName: String;


        constructor(param: IItemModel) {
            this.primaryKey = param.totalCountNo;
            this.totalCountNo = param.totalCountNo;
            this.totalTimesName = param.totalTimesName;
        }
    }

}