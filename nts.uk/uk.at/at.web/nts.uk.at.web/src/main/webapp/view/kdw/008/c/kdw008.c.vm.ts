module nts.uk.at.view.kdw008.c {
    export module viewmodel {
        export class ScreenModel {
            idList: KnockoutObservable<string>;
            businessTypeSortedList: KnockoutObservableArray<BusinessTypeSortedModel>;
            columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
            testSingle: KnockoutObservable<any>;

            constructor() {
                var self = this;
                this.idList = ko.observable('');
                this.businessTypeSortedList = ko.observableArray([]);

                this.columns = ko.observableArray([
                    { headerText: 'コード', key: 'dislayNumber', width: 100 },
                    { headerText: '', key: 'attendanceItemId', hidden: true, width: 150 },
                    { headerText: '名称', key: 'attendanceItemName', width: 150 }
                ]);
                this.testSingle = ko.observable(null);
            }

            update(): void {
                let self = this;
//                let dfd = $.Deferred();
                var businessTypeSortedUpdateList = _.map(self.businessTypeSortedList(), item => {
                    var indexOfDaily = _.findIndex(self.businessTypeSortedList(), { attendanceItemId: item.attendanceItemId });
                    var update = {
                        attendanceItemId: item.attendanceItemId,
                        dislayNumber: item.dislayNumber,
                        attendanceItemName: item.attendanceItemName,
                        order: indexOfDaily,
                    }
                    return new BusinessTypeSortedModel(update);
                });
                nts.uk.ui.block.grayout();
                new service.Service().updateBusinessTypeSorted(businessTypeSortedUpdateList).done(function(data) {
                    //self.findAll();
                    nts.uk.ui.block.clear();
                    nts.uk.ui.dialog.alert({ messageId: "Msg_15" }).then(function(){
                        nts.uk.ui.windows.close();
                    });
//                    dfd.resolve();
                    
                });
//                return dfd.promise();
            }

            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                self.businessTypeSortedList([]);
                new service.Service().findAll().done(function(data) {
                    data = _.sortBy(data, ["order"]);
                    self.businessTypeSortedList(data);
                    dfd.resolve();
                }).fail(error => {

                });
                return dfd.promise();
            }
            
            closeDialog() {
                nts.uk.ui.windows.close();
            }
        }


        export class BusinessTypeSortedModel {
            attendanceItemId: number;
            dislayNumber: number;
            attendanceItemName: string;
            order: number;
            constructor(data: any) {
                if (!data) return;
                this.attendanceItemId = data.attendanceItemId;
                this.dislayNumber = data.dislayNumber;
                this.attendanceItemName = data.attendanceItemName;
                this.order = data.order;
            }
        }

    }
}
