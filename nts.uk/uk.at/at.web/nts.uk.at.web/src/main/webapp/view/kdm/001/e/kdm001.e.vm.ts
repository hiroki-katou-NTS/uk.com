module nts.uk.at.view.kdm001.e.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import model     = kdm001.share.model;
    import dialog    = nts.uk.ui.dialog;
    import block     = nts.uk.ui.block;
    import getText   = nts.uk.resource.getText;
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
        residualDay: KnockoutObservable<any> = ko.observable(0);
        residualDayDisplay: KnockoutObservable<any> = ko.observable(model.formatterDay('0'));
        info: any = getShared("KDM001_EFGH_PARAMS");
        disables: KnockoutObservableArray<any> = ko.observableArray([]);
        constructor() {
            let self = this;
            
            if (self.info) {
                self.workCode(self.info.selectedEmployee.workplaceCode);
                self.workPlaceName(self.info.selectedEmployee.workplaceName);
                self.employeeCode(self.info.selectedEmployee.employeeCode);
                self.employeeName(self.info.selectedEmployee.employeeName);
                self.dateHoliday(self.info.rowValue.dayoffDatePyout);
                self.numberDay(model.formatterDay(self.info.rowValue.occurredDays));
            }
            self.columns = ko.observableArray([
                { headerText: 'コード', dataType: 'string', key: 'subOfHDID', width: 100, hidden: true },
                { headerText: getText("KDM001_95"), key: 'dayoffDate', width: 110 },
                { headerText: getText("KDM001_96"), key: 'remainDays', formatter:model.formatterDay, width: 100 },
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
            let sumNum = 0, self = this, day = parseFloat(self.numberDay());
            self.residualDayDisplay(model.formatterDay(day));
            self.residualDay(day);
            _.each(self.currentList(), function (x) {
                if (self.dateHoliday() === x.dayoffDate && self.dateHoliday() != null && x.dayoffDate != null) {
                    $('#multi-list').ntsError('set', { messageId: "Msg_729" });
                } else {                
                    sumNum = sumNum + x.remainDays;
                    let residualValue = (day - sumNum);
                    self.residualDay(residualValue);
                    self.residualDayDisplay(model.formatterDay(residualValue));
                    
                }
            });
           if (self.residualDay() < 0) {
                $("#E7_2").css("color", "red");
            } else {
                $("#E7_2").css("color", "black");
            }
        }
        
        public initScreen(): void {
            let self = this;
            block.invisible();
            self.caculRemainNumber();
            service.getBySidDatePeriod(self.info.selectedEmployee.employeeId, self.info.rowValue.id).done((data: Array<ItemModel> )=>{
                if (data && data.length > 0) {
                    _.forEach(data, function(item) {
                        self.items.push(new ItemModel(item.subOfHDID, item.dayoffDate,item.remainDays));
                        if (item.linked){
                            self.currentCodeList.push(item.subOfHDID);
                        }
                    });
                    let sortData = _.sortBy(self.items(), o => o.dayoffDate,'asc');
                    self.items(sortData);
                    _.forEach(self.items(), function(item: ItemModel) {
                        if(parseFloat(item.remainDays) > parseFloat(self.info.rowValue.occurredDays)) {
                            self.disables.push(item.subOfHDID);    
                        }    
                    })
                } else {
                    dialog.info({messageId: 'Msg_1068'});  
                }
                block.clear();
            }).fail((res)=>{
                dialog.alertError({ messageId: res.messageId });
                block.clear();
            })
        }

        public create(): void {
            nts.uk.ui.errors.clearAll();
            let self = this;
            
            let command = new PayoutSubofHDManagementCommand(self.info.selectedEmployee.employeeId, self.info.rowValue.id,Math.abs(self.residualDay()), self.currentList());
            if (!self.validate()){
                return;
            }
            if (!nts.uk.ui.errors.hasError()) {
                block.invisible();
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
        }
        
        private validate(): boolean{
            let self = this;

            if (self.currentCodeList().length >= 3){
                $('#multi-list').ntsError('set', { messageId: "Msg_739" });
                return false;
            } else if (self.currentCodeList().length == 1 && self.currentList()[0].remainDays > parseFloat(self.numberDay())){
                $('#multi-list').ntsError('set', { messageId: "Msg_731" });
                return false;
            } else if (self.currentCodeList().length == 2){
                if (self.currentList()[0].remainDays === 1){
                    $('#multi-list').ntsError('set', { messageId: "Msg_739" });
                    return false;
                }
                if (parseFloat(self.numberDay()) !== (self.currentList()[0].remainDays + self.currentList()[1].remainDays)){
                    $('#multi-list').ntsError('set', { messageId: "Msg_739" });
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
        remainDays: string;
        constructor(subOfHDID: string, dayoffDate: string, remainDays: string) {
            this.subOfHDID = subOfHDID;
            this.dayoffDate = dayoffDate;
            this.remainDays = remainDays;
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