module nts.uk.at.view.kdm001.e.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import model     = kdm001.share.model;
    import dialog    = nts.uk.ui.dialog;
    import block     = nts.uk.ui.block;
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
        residualDayDispay: KnockoutObservable<any> = ko.observable('');
        info: any = getShared("KDM001_EFGH_PARAMS");
        constructor() {
            let self = this;
            
            if (self.info) {
                self.workCode(self.info.selectedEmployee.workplaceCode);
                self.workPlaceName(self.info.selectedEmployee.workplaceName);
                self.employeeCode(self.info.selectedEmployee.employeeCode);
                self.employeeName(self.info.selectedEmployee.employeeName);
                self.dateHoliday(self.info.rowValue.dayoffDateSub);
                self.numberDay(self.info.rowValue.requiredDays+' 日');
            }
            self.columns = ko.observableArray([
                { headerText: 'コード', key: 'subOfHDID', width: 100, hidden: true },
                { headerText: nts.uk.resource.getText("KDM001_95"), key: 'dayoffDate', width: 110 },
                { headerText: nts.uk.resource.getText("KDM001_96"), key: 'requiredDays', formatter:model.formatterDay, width: 100 }
            ]);
            
            self.currentCodeList.subscribe(()=> {
                nts.uk.ui.errors.clearAll();
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
                self.caculRemainNumber();
            });
        }
        private caculRemainNumber(): void{
            let sumNum = 0, self = this;
                _.each(self.currentList(), function (x) {
                    if (self.dateHoliday() === x.dayoffDate) {
                        nts.uk.ui.dialog.info({ messageId: "Msg_729" });
                    }
                    
                    sumNum = sumNum + x.requiredDays;
                    
                });
                let day = parseFloat(self.numberDay());
                self.residualDay((day - sumNum));
                self.residualDayDispay((day - sumNum) + ' 日');
        }
        
        public initScreen(): void {
            let self = this;
             block.invisible();
            self.caculRemainNumber();
            service.getBySidDatePeriod(self.info.selectedEmployee.employeeId, self.info.rowValue.id).done((data: Array<ItemModel> )=>{
                if (data && data.length > 0) {
                    self.items(data);
                    let code = _.filter(self.items(), function(currentItem: ItemModel) {
                        return currentItem.linked == true;
                    });
                    if (code) {
                        _.forEach(code, function(item: ItemModel) {
                          self.currentCodeList.push(item.subOfHDID);
                        });
                    }
                }
                block.clear();
            }).fail((res)=>{
                dialog.alertError({ messageId: res.messageId });
                block.clear();
            })
        }
        
        public create(): void {
            let self = this;
           block.invisible();
            let command = new PayoutSubofHDManagementCommand(self.info.selectedEmployee.employeeId, self.info.rowValue.id,self.residualDay(), self.currentList());
                if (!self.validate()){
                    block.clear();
                    return;
                }
                service.insertSubOfHDMan(command).done(()=>{
                    dialog.info({ messageId: "Msg_15" }).then(() => {
                        setShared('KDM001_A_PARAMS', {isSuccess: true});
                        nts.uk.ui.windows.close();
                    });
                    block.clear();
                }).fail((res)=>{
                    $('#multi-list').ntsError('set', { messageId: res.messageId });
                    block.clear();
                })
        }
        
        private validate(): boolean{
            let self = this;
            if (self.currentCodeList().length == 0){
                $('#multi-list').ntsError('set', { messageId: "Msg_738" });
                return false;
            } else if (self.currentCodeList().length >= 3){
                $('#multi-list').ntsError('set', { messageId: "Msg_739" });
                return false;
            } else if (self.currentCodeList().length == 1 && self.currentList()[0].requiredDays !== parseFloat(self.numberDay())){
                $('#multi-list').ntsError('set', { messageId: "Msg_731" });
                return false;
            } else if (self.currentCodeList().length == 2){
                if (self.currentList()[0].requiredDays === 1){
                    $('#multi-list').ntsError('set', { messageId: "Msg_739" });
                    return false;
                }
                if (parseFloat(self.numberDay()) !== (self.currentList()[0].requiredDays + self.currentList()[1].requiredDays)){
                    $('#multi-list').ntsError('set', { messageId: "Msg_731" });
                    return false;
                }
            }
            return true;
        }
        /**
         * closeDialog
         */
        public closeDialog():void {
            setShared('KDM001_A_PARAMS', {isSuccess: false});
            nts.uk.ui.windows.close();
        }
    }

    class ItemModel {
        subOfHDID: string;
        dayoffDate: string;
        requiredDays: number;
        linked: boolean;
        constructor(subOfHDID: string, dayoffDate: string, requiredDays?: number) {
            this.subOfHDID = subOfHDID;
            this.dayoffDate = dayoffDate;
            this.requiredDays = requiredDays;
        }
    }

    class PayoutSubofHDManagementCommand {
        sid: string;
        payoutID: string;
        remainNumber: number;
        subofHD: Array<ItemModel>;
        constructor(sid: string, payoutID: string, remainNumber: number, subofHD: Array<ItemModel>) {
            this.subofHD = subofHD;
            this.sid = sid;
            this.payoutID = payoutID;
            this.remainNumber = remainNumber;
        }
    }
}