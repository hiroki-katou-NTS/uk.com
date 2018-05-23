module nts.uk.at.view.kdm001.e.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    export class ScreenModel {
        items: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        columns: KnockoutObservableArray<NtsGridListColumn>;
        currentCodeList: KnockoutObservableArray<any> = ko.observableArray([]);
        workCode: KnockoutObservable<string> = ko.observable('');
        workPlaceName: KnockoutObservable<string> = ko.observable('');
        employeeCode: KnockoutObservable<string> = ko.observable('');
        employeeName: KnockoutObservable<string> = ko.observable('');
        dateHoliday: KnockoutObservable<any> = ko.observable('');
        numberDay: KnockoutObservable<any> = ko.observable('');
        residualDay: KnockoutObservable<any> = ko.observable('');
        employee : any = getShared('KDM001A_EMPLOYEE');
        payoutItem : any = getShared('KDM001A_PAYOUT');
   
        constructor() {
            let self = this;
            self.workCode('100');
            self.workPlaceName('営業部');
            self.employeeCode('A000001');
            self.employeeName('日通　太郎');
            self.dateHoliday('2016/10/2');
            self.numberDay('1.0日');
            self.residualDay('0日');
            self.columns = ko.observableArray([
                { headerText: 'コード', key: 'subOfHDID', width: 100, hidden: true },
                { headerText: nts.uk.resource.getText("KDM001_95"), key: 'dayoffDate', width: 110 },
                { headerText: nts.uk.resource.getText("KDM001_96"), key: 'remainDays', formatter:self.formatterDay, width: 100 }
            ]);
//            self.initScreen();
        }

//        public initScreen(): void {
//            let self = this;
//            self.workCode('100');
//            self.workPlaceName('営業部');
//            self.employeeCode('A000001');
//            self.employeeName('日通　太郎');
//            self.dateHoliday('2016/10/2');
//            self.numberDay('1.0日');
//            self.residualDay('0日');
//            for (let i = 1; i < 100; i++) {
//                self.items.push(new ItemModel('00' + i, "2010/1/10", "1.0E"));
//            }
//            self.currentCodeList.push('001')
//        }
        public initScreen(annID?: string): JQueryPromise<any> {
            let self = this;
            service.getBySidDatePeriod(/*employee.employeeId*/ 'ae7fe82e-a7bd-4ce3-adeb-5cd403a9d570', /*payoutItem.payoutId*/'de1b6c4f-833c-4bca-b257-4e44269ed230').done((data: ItemModel )=>{
                if (data && data.length > 0) {
                    self.items(data);
                    _.forEach(data, function(item) {
                        let code = _.find(self.items(), function(currentItem: ItemModel) {
                            return currentItem.linked == true;
                        });
                        if (code) {
                            self.currentCodeList.push(code);
                        }
                    });
                }
            }).fail(()=>{
                
            })
        }
        
        public create(): void {
            let self = this,
                command = new PayoutSubofHDManagementCommand(employee.employeeId, payoutItem.payoutId, self.items());
                service.insertSubOfHDMan(command).done(()=>{
                
                }).fail(()=>{
                    
                })
        }
        
        /**
         * closeDialog
         */
        public closeDialog():void {
            nts.uk.ui.windows.close();
        }
        
        formatterDay(value) {
            if (value) {
                return value >= 0 ? "&nbsp;" + value + '日' : value + '日';
            } else {
                return "&nbsp;0日";
            }
        }
    }

    class ItemModel {
        subOfHDID: string;
        dayoffDate: string;
        remainDays: number;
        linked: boolean;
        constructor(subOfHDID: string, dayoffDate: string, remainDays?: number) {
            this.subOfHDID = subOfHDID;
            this.dayoffDate = dayoffDate;
            this.remainDays = remainDays;
        }
    }

    class PayoutSubofHDManagementCommand {
        sid: string;
        payoutID: string;
        subofHD: Array<ItemModel>;
        constructor(sid: string, payoutID: string, subofHD: Array<ItemModel>) {
            this.subofHD = subofHD;
            this.sid = sid;
            this.payoutID = payoutID;
        }
    }
}