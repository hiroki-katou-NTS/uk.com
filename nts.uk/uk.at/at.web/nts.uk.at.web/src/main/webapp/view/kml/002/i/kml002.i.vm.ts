module kml002.i.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    export class ScreenModel {
        useCls: KnockoutObservableArray<any>;

        dataTime: KnockoutObservable<number>;
        displayAtr: KnockoutObservable<number>;
        startClock: KnockoutObservable<number>;
        items: KnockoutObservableArray<any>;
        currentItem: KnockoutObservable<VerticalTime>
        fixVerticalId: KnockoutObservable<number>;
        constructor() {
            var self = this;
            self.useCls = ko.observableArray([
                { code: '1', name: nts.uk.resource.getText("KML002_99") },
                { code: '0', name: nts.uk.resource.getText("KML002_100") }
            ]);


            self.displayAtr = ko.observable(0);

            self.items = ko.observableArray([]);
            self.currentItem = ko.observable(new VerticalTime({}));
            self.fixVerticalId = ko.observable(getShared("KML002H_VERTICAL_ID"))();
        }
        start() {
            var self = this,
                dfd = $.Deferred();
            $("#fixed-table").ntsFixedTable({ height: 300 });
            self.getAllVerticalTime();
            dfd.resolve();

            return dfd.promise();
        }

        closeDialog(): void {
            nts.uk.ui.windows.close();

        }
        openKDialog() {

            let self = this;
            nts.uk.ui.windows.sub.modal('/view/kml/002/k/index.xhtml').onClosed(function(): any {
                var dataTime = (getShared("KML002K_TIME"));
                if (!dataTime) {
                    return;
                }
                nts.uk.ui.block.invisible();
                var endTimeM = dataTime.endTime / 60;
                var startTimeM = dataTime.startTime / 60;
                var index = self.items().length;
                var item = self.items();
                for (var i = startTimeM; i <= endTimeM; i++) {
                    var verticalTime: IVerticalTime = {
                        verticalTimeNo: index,
                        displayAtr: DispayAtr.DO_NOT_USE,
                        startClock: i * 60
                    };
                    item.push(new VerticalTime(verticalTime));
                    var sortedItems = _.sortBy(item, [function(o) { return o.startClock(); }]);
                    index++;
                    self.items(sortedItems);
                }
                nts.uk.ui.block.clear();
            });
            
        }

        addTime() {
            nts.uk.ui.block.invisible();
            let self = this;
            var index = self.items().length;
            var item = self.items();
            var verticalTime: IVerticalTime = {
                verticalTimeNo: index,
                displayAtr: DispayAtr.DO_NOT_USE,
                startClock: null
            };
            item.push(new VerticalTime(verticalTime));
            var sortedItemsTime = _.sortBy(item, [function(o) { return o.startClock(); }]);
            index++;
            self.items(sortedItemsTime);
            nts.uk.ui.block.clear();
        }

        getAllVerticalTime(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            service.findVerticalSet(self.fixVerticalId).done(function(verticalTimeArr: Array<IVerticalTime>) {
                _.forEach(verticalTimeArr, function(res: IVerticalTime) {
                    var verticalTime: IVerticalTime = {
                        verticalTimeNo: res.verticalTimeNo,
                        displayAtr: res.displayAtr,
                        startClock: res.startClock
                    };
                    self.items.push(new VerticalTime(verticalTime));
                });
            });

            return dfd.promise();
        }
        addVerticalTime(): JQueryPromise<any> {
            nts.uk.ui.block.invisible();
            var self = this;
            var dfd = $.Deferred();
            var data = {
                fixedItemAtr: self.fixVerticalId,
                verticalTimes: ko.toJS(self.items()
                )
            }
            service.addVerticalTime(data).done(function(any) {
                nts.uk.ui.dialog.info({ messageId: "Msg_15" });
            });
            nts.uk.ui.block.clear();
            return dfd.promise();

        }
        deleteVerticalTime(verticalTimeNo: number) {
            var self = this;
            var items = self.items();
            nts.uk.ui.errors.clearAll();
            _.remove(self.items(), function(item: IVerticalTime) {
                return verticalTimeNo === item.verticalTimeNo();
            });
            self.items(items);
        }
    }
    export interface IVerticalTime {
        fixedItemAtr?: number;
        verticalTimeNo?: any;
        displayAtr?: number;
        startClock?: number;
    }
    class VerticalTime {
        fixedItemAtr: KnockoutObservable<number>;
        verticalTimeNo: KnockoutObservable<any>;
        displayAtr: KnockoutObservable<number>;
        startClock: KnockoutObservable<number>;
        constructor(param: IVerticalTime) {
            this.fixedItemAtr = ko.observable(param.fixedItemAtr || 0);
            this.verticalTimeNo = ko.observable(param.verticalTimeNo || 0);
            this.displayAtr = ko.observable(param.displayAtr || DispayAtr.DO_NOT_USE);
            this.startClock = ko.observable(param.startClock || 0);
        }
    }
    export enum DispayAtr {
        /* 0- 利用しない */
        DO_NOT_USE = 0,
        /* 1- 利用する */
        USE = 1
    }

}