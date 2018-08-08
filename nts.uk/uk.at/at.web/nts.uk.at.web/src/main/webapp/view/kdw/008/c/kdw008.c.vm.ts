module nts.uk.at.view.kdw008.c {
    export module viewmodel {
        import getText = nts.uk.resource.getText;
        export class ScreenModel {
            idList: KnockoutObservable<string>;
            businessTypeSortedList: KnockoutObservableArray<BusinessTypeSortedModel>;
            columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
            testSingle: KnockoutObservableArray<any>;
            isDaily: KnockoutObservable<boolean>;

            constructor() {
                var self = this;
                self.idList = ko.observable('');
                self.businessTypeSortedList = ko.observableArray([]);
                let param  = nts.uk.ui.windows.getShared("openC");
                self.isDaily = ko.observable(param);
                self.columns = ko.observableArray([]);
                if (self.isDaily()) {
                    self.columns([
                        { headerText: getText('KDW008_7'), key: 'dislayNumber', width: 60 },
                        { headerText: '', key: 'attendanceItemId', hidden: true, width: 120 },
                        { headerText: getText('KDW008_8'), key: 'attendanceItemName', width: 220, formatter: _.escape }
                    ]);
                } else {
                    self.columns([
                        { headerText: getText('KDW008_7'), key: 'attendanceItemDisplayNumber', width: 60 },
                        { headerText: '', key: 'attendanceItemId', hidden: true, width: 120 },
                        { headerText: getText('KDW008_8'), key: 'attendanceItemName', width: 220, formatter: _.escape }
                    ]);
                }
                self.testSingle = ko.observableArray([]);
            }

            update(): void {
                let self = this;
                //                let dfd = $.Deferred();
                if (self.isDaily()) {
                    var businessTypeSortedUpdateList = _.map(self.businessTypeSortedList(), item => {
                        var indexOfDaily = _.findIndex(self.businessTypeSortedList(), { attendanceItemId: item.attendanceItemId });
                        return new BusinessTypeSortedModel(item.attendanceItemId,item.dislayNumber,item.attendanceItemName,indexOfDaily);
                    });
                    nts.uk.ui.block.grayout();
                    new service.Service().updateBusinessTypeSorted(businessTypeSortedUpdateList).done(function(data) {
                        //self.findAll();
                        nts.uk.ui.block.clear();
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                            //nts.uk.ui.windows.close();
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

                        nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                            //nts.uk.ui.windows.close();
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
                        
                        self.businessTypeSortedList(data);
                        self.getAllBusiness();
                        dfd.resolve();
                    }).fail(error => {
                        dfd.resolve();
                    });
                    return dfd.promise();
                }
            }
            
            getAllBusiness(){
                let self = this;
                let dfd = $.Deferred();      
                new service.Service().getAllBusiness().done(function(data) {
                        if(data){
                           for(let i =0;i<self.businessTypeSortedList().length;i++){
                                for(let j = 0;j<data.listOrderReferWorkType.length;j++){
                                    if(self.businessTypeSortedList()[i].attendanceItemId == data.listOrderReferWorkType[j].attendanceItemID){
                                        self.businessTypeSortedList()[i].displayNumber = data.listOrderReferWorkType[j].order
                                    }     
                                }    
                           }
                           self.businessTypeSortedList(_.sortBy(self.businessTypeSortedList(), ["displayNumber"], ['asc'])); 
                        }
                        dfd.resolve();
                    }).fail(error => {
                        dfd.resolve();
                    });
                return dfd.promise();              
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
            dislayNumber: number;
            attendanceItemName: string;
            order: number;
            constructor(attendanceItemId: number,
            dislayNumber: number,
            attendanceItemName: string,
            order: number) {
                this.attendanceItemId = attendanceItemId;
                this.dislayNumber = dislayNumber;
                this.attendanceItemName = attendanceItemName;
                this.order = order;
            }
        }

    }
}
