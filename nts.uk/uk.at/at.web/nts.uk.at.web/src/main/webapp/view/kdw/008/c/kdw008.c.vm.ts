module nts.uk.at.view.kdw008.c {
    export module viewmodel {
        export class ScreenModel {
            idList: KnockoutObservable<string>;
            businessTypeSortedList: KnockoutObservableArray<BusinessTypeSortedModel>;
            columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
            testSingle: KnockoutObservable<any>;
            isDaily: KnockoutObservable<boolean>;

            constructor() {
                var self = this;
                this.idList = ko.observable('');
                this.businessTypeSortedList = ko.observableArray([]);
                this.isDaily = ko.observable(true);

                if (self.isDaily()) {
                    this.columns = ko.observableArray([
                        { headerText: 'コード', key: 'dislayNumber', width: 100 },
                        { headerText: '', key: 'attendanceItemId', hidden: true, width: 150 },
                        { headerText: '名称', key: 'attendanceItemName', width: 150 }
                    ]);
                } else {
                    this.columns = ko.observableArray([
                        { headerText: 'コード', key: 'attendanceItemDisplayNumber', width: 100 },
                        { headerText: '', key: 'attendanceItemId', hidden: true, width: 150 },
                        { headerText: '名称', key: 'attendanceItemName', width: 150 }
                    ]);
                }
                this.testSingle = ko.observable(null);
            }

            update(): void {
                let self = this;
                //                let dfd = $.Deferred();
                if (self.isDaily()) {
                    var businessTypeSortedUpdateList = _.map(self.businessTypeSortedList(), item => {
                        var indexOfDaily = _.findIndex(self.businessTypeSortedList(), { attendanceItemId: item.attendanceItemId });
                        var update = {
                            attendanceItemID: item.attendanceItemId,
                            displayNumber: item.displayNumber,
                            attendanceItemName: item.attendanceItemName,
                            order: indexOfDaily
                        }
                        return new BusinessTypeSortedModel(update);
                    });
                    nts.uk.ui.block.grayout();
                    new service.Service().updateBusinessTypeSorted(businessTypeSortedUpdateList).done(function(data) {
                        //self.findAll();
                        nts.uk.ui.block.clear();
                        nts.uk.ui.dialog.alert({ messageId: "Msg_15" }).then(function() {
                            nts.uk.ui.windows.close();
                        });
                        //                                    
                    });
                    //                return dfd.promise();
                } else {
                    var businessTypeSortedUpdateList = _.map(self.businessTypeSortedList(), item => {
                        var indexOfDaily = _.findIndex(self.businessTypeSortedList(), { attendanceItemId: item.attendanceItemId });
                        return new OrderReferWorkType(item.attendanceItemId,indexOfDaily);
                    });
                    var command = {
                        companyID: '123',
                        listOrderReferWorkType: businessTypeSortedUpdateList,
                    }
                    nts.uk.ui.block.grayout();
                    new service.Service().updateMonth(command).done(function() {
                        //self.findAll();

                        nts.uk.ui.dialog.alert({ messageId: "Msg_15" }).then(function() {
                            nts.uk.ui.windows.close();
                        });
                        //                                    
                    }).fail(function(res) {
                        nts.uk.ui.dialog.alert(res.message);
                        nts.uk.ui.block.clear();
                    }).always(function() {
                        nts.uk.ui.block.clear();
                    });
                    //                return dfd.promise();
                }
            }

            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                self.businessTypeSortedList([]);
                if (self.isDaily()) {
                    new service.Service().findAll().done(function(data) {
                        data = _.sortBy(data, ["order"]);
                        self.businessTypeSortedList(data);
                        dfd.resolve();
                    }).fail(error => {
                        dfd.resolve();
                    });
                    return dfd.promise();
                } else {
                    new service.Service().findAllMonth().done(function(data) {
                        data = _.sortBy(data, ["attendanceItemDisplayNumber"], ['asc']);
                        self.businessTypeSortedList(data);
                        dfd.resolve();
                    }).fail(error => {
                        dfd.resolve();
                    });
                    return dfd.promise();
                }
            }

            closeDialog() {
                nts.uk.ui.windows.close();
            }
        }
        
        export class BusinessTypeSortedMon{
            companyID :string;
            listOrderReferWorkType: Array<OrderReferWorkType>;
            constructor(companyID :string,
                listOrderReferWorkType: Array<OrderReferWorkType>){
                this.companyID = companyID;
                this.listOrderReferWorkType = listOrderReferWorkType;    
            }
                  
        }
        
        export class OrderReferWorkType{
            attendanceItemID : number;
            order : number;
            constructor(attendanceItemID : number,
            order : number){
                this.attendanceItemID = attendanceItemID;
                this.order = order;   
            }
            
        }


        export class BusinessTypeSortedModel {
            attendanceItemId: number;
            displayNumber: number;
            attendanceItemName: string;
            order: number;
            constructor(data: any) {
                if (!data) return;
                this.attendanceItemId = data.attendanceItemId;
                this.displayNumber = data.dislayNumber;
                this.attendanceItemName = data.attendanceItemName;
                this.order = data.order;
            }
        }

    }
}
