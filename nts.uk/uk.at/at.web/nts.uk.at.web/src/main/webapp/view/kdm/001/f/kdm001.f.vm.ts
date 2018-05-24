module nts.uk.at.view.kdm001.f.viewmodel {
    export class ScreenModel {
        items: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        columns: KnockoutObservableArray<any>;
        currentCodeList: KnockoutObservableArray<any> = ko.observableArray([]);
        currentList: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        workCode: KnockoutObservable<string> = ko.observable('');
        workPlaceName: KnockoutObservable<string> = ko.observable('');
        employeeCode: KnockoutObservable<string> = ko.observable('');
        employeeName: KnockoutObservable<string> = ko.observable('');
        dateHoliday: KnockoutObservable<any> = ko.observable('');
        numberDay: KnockoutObservable<any> = ko.observable('');
        residualDay: KnockoutObservable<any> = ko.observable('');
        constructor() {
            var self = this;
            self.columns = ko.observableArray([
                { headerText: 'コード', key: 'payoutId', width: 100, hidden: true },
                { headerText: nts.uk.resource.getText("KDM001_95"), key: 'dayoffDate', width: 100 },
                { headerText: nts.uk.resource.getText("KDM001_96"), key: 'occurredDays', width: 100 }
            ]);
            self.initScreen();
            
            self.currentCodeList.subscribe(function (codesSelect) {
                if (self.currentList().length > 0) {
                     self.currentList.removeAll();
                 }
                _.forEach(self.currentCodeList(),function(item){
                    let code = _.find(self.items(), function(currentItem: ItemModel) {
                                return currentItem.subOfHDID === item;
                     });
                    if (code) {
                        
                        self.currentList.push(code);
                    }
                })
                let sumNum = 0;
                _.each(self.currentList(), function (x) {
                    if (self.dateHoliday() === x.dayoffDate) {
                        nts.uk.ui.dialog.info({ messageId: "Msg_729" });
                    }
                    let iNum = parseFloat(x.remainDays);
                    
                    sumNum = sumNum + iNum;
                    
                });
                let day = parseFloat(self.residualDay());
                self.residualDay((day - sumNum) + ' 日');
            });
        }

        public initScreen(): void {
            var self = this;
            self.workCode('100');
            self.workPlaceName('営業部');
            self.employeeCode('A000001');
            self.employeeName('日通　太郎');
            self.dateHoliday('2016/10/2');
            self.numberDay('0.5日');
            self.residualDay('0日');
//            for (let i = 1; i < 100; i++) {
//                self.items.push(new ItemModel('00' + i, "2010/1/10", "1.0F"));
//            }
            
            service.getBySidDatePeriod(/*employee.employeeId*/ 'ae7fe82e-a7bd-4ce3-adeb-5cd403a9d570', /*payoutItem.payoutId*/'de1b6c4f-833c-4bca-b257-4e44269ed230').done((data: Array<ItemModel> )=>{
                if (data && data.length > 0) {
                    self.items(data);
                    _.forEach(data, function(item) {
                        let code = _.find(self.items(), function(currentItem: ItemModel) {
                            return currentItem.linked == true;
                        });
                        if (code) {
                            self.currentCodeList.push(code.subOfHDID);
                        }
                    });
                }
            }).fail(()=>{
                
            })
            
            
        }
        
        public create(): void {
            let self = this;
           
            let command = new SubOfHDPayoutManaDataCommand(/*self.info.selectedEmployee.employeeId, self.info.payout.payoutId,*/'ae7fe82e-a7bd-4ce3-adeb-5cd403a9d570', /*payoutItem.payoutId*/'de1b6c4f-833c-4bca-b257-4e44269ed230',self.residualDay(), self.currentList());
                
                if (self.currentCodeList().length == 0){
                    $('#multi-list').ntsError('set', { messageId: "Msg_738" });
                    return;
                } else if (self.currentCodeList().length >= 3){
                    $('#multi-list').ntsError('set', { messageId: "Msg_739" });
                    return;
                } else if (self.currentCodeList().length == 1 && self.currentList()[0].remainDays !== 1){
                    $('#multi-list').ntsError('set', { messageId: "Msg_731" });
                    return;
                } else if (self.currentCodeList().length == 2){
                    if (self.currentList()[0].remainDays === 1){
                        $('#multi-list').ntsError('set', { messageId: "Msg_739" });
                        return;
                    }
                    if (self.currentList()[0].remainDays === 0.5 && self.currentList()[0].remainDays !== 0.5){
                        $('#multi-list').ntsError('set', { messageId: "Msg_731" });
                        return;
                    }
                }
        }

        /**
         * closeDialog
         */
        public closeDialog(): void {
            nts.uk.ui.windows.close();
        }
    }

    class ItemModel {
        payoutId: string;
        dayoffDate: string;
        occurredDays: number;
        constructor(code: string, date: string, occurredDays?: number) {
            this.payoutId = code;
            this.dayoffDate = date;
            this.occurredDays = occurredDays;
        }
    }
    
    class SubOfHDPayoutManaDataCommand {
        sid: string;
        subOfHDID: string;
        remainNumber: number;
        payout: Array<ItemModel>;
        constructor(sid: string, subOfHDID: string, remainNumber: number, payout: Array<ItemModel>) {
            this.subOfHDID = subOfHDID;
            this.sid = sid;
            this.payout = payout;
            this.remainNumber = remainNumber;
        }
    }
}