module kml002.l.viewmodel {
    export class ScreenModel {
        items: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservableArray<any>;
        currentCodeList: KnockoutObservableArray<any>;

        constructor() {
            var self = this;
            self.items = ko.observableArray([]);
            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KML002_6"), prop: 'totalCountNo', key: 'totalCountNo', width: 55 },
                { headerText: nts.uk.resource.getText("KML002_6"), prop: 'totalTimesName', key: 'totalTimesName', width: 167 },
                { headerText: 'pk', prop: 'primaryKey', key: 'primaryKey', width: 1, hidden: true }
            ]);
            self.currentCodeList = ko.observableArray([]);
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
            service.findAll().done(function(totalTimeArr: Array<ItemModel>) {
                    _.forEach(totalTimeArr, function(res: IItemModel) {
                        var totalTime: IItemModel = {
                            totalCountNo: res.totalCountNo,
                            totalTimesName: res.totalTimesName
                        };
                        self.items.push(new ItemModel(totalTime));
                    });
                });
            return dfd.promise();
        }
           addTotalTime(){
        var self = this;
            var items = []
                    service.addTotalTimes(ko.toJS(self.currentCodeList())).done(function(res) {
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                    }).fail(function(res) {
                        nts.uk.ui.dialog.alertError(res.message);
                    })
            return;
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
      

        constructor(param : IItemModel) {
            this.primaryKey = param.totalCountNo;
            this.totalCountNo = param.totalCountNo;
            this.totalTimesName = param.totalTimesName;
        }
    }

}