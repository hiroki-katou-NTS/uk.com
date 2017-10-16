module kml002.i.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    export class ScreenModel {
        useCls: KnockoutObservableArray<any>;
        
        dataTime: KnockoutObservable<number>;
        displayAtr: KnockoutObservable<number>;
        startClock: KnockoutObservable<number>;
        items: KnockoutObservableArray<any>;
        currentItem: KnockoutObservable<VerticalTime>
        fixVerticalId: any;
        constructor() {
            var self = this;
            self.useCls = ko.observableArray([
                { code: '0', name: nts.uk.resource.getText("KML002_99") },
                { code: '1', name: nts.uk.resource.getText("KML002_100") }
            ]);
            

            self.displayAtr = ko.observable(0);
            self.startClock = ko.observable(null);

            self.items = ko.observableArray([]);
            self.currentItem = ko.observable(new VerticalTime({}));
            self.fixVerticalId = (getShared("KML002K_TIME"));
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
                var endTimeM = dataTime.endTime / 60;
                var startTimeM = dataTime.startTime / 60;
                var index = self.items().length;
                for (var i = startTimeM; i < endTimeM; i++) {
                    var verticalTime: IVerticalTime = {
                        verticalTimeNo: index,
                        displayAtr: 0,
                        startClock: i*60
                    };
                    self.items.push(new VerticalTime(verticalTime));
                    index++;
                }
            });
        }

        getAllVerticalTime(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            service.findVerticalSet(self.fixVerticalId).done(function(verticalTimeArr: Array<IVerticalTime>) {
                _.forEach(verticalTimeArr, function(res: IVerticalTime) {
                    var verticalTime: IVerticalTime = {
                        displayAtr: res.displayAtr,
                        startClock: res.startClock
                    };
                    self.items.push(new VerticalTime(verticalTime));
                });
            });

            return dfd.promise();
        }
        addVerticalTime(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            var data = {
                fixedItemAtr: self.fixVerticalId,
                verticalTimes: ko.toJS(self.items()
                )
            }
            service.addVerticalTime(data).done(function(any) {
            
            });
            return dfd.promise();
        }
        deleteVerticalTime(verticalTimeNo : number){
            var self = this;
            var items = self.items();
            _.remove(self.items(), function(item: IVerticalTime) {
                return verticalTimeNo == item.verticalTimeNo();
            });
            self.items(items);
        }
    }
    export interface IVerticalTime {
        fixedItemAtr?: number;
        verticalTimeNo?: number;
        displayAtr?: number;
        startClock?: number;
    }
    class VerticalTime {
        fixedItemAtr: KnockoutObservable<number>;
        verticalTimeNo: KnockoutObservable<number>;
        displayAtr: KnockoutObservable<number>;
        startClock: KnockoutObservable<number>;
        constructor(param: IVerticalTime) {
            this.fixedItemAtr = ko.observable(param.fixedItemAtr || 0);
            this.verticalTimeNo = ko.observable(param.verticalTimeNo || 0);
            this.displayAtr = ko.observable(param.displayAtr || 0);
            this.startClock = ko.observable(param.startClock || null);
        }
    }

}