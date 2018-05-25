module nts.uk.at.view.kdm001.j.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import block     = nts.uk.ui.block;
    export class ScreenModel {
        itemsSelected: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        items: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        sumDay: KnockoutObservable<any> = ko.observable('');
        columns: KnockoutObservableArray<NtsGridListColumn>;
        currentCodeList: KnockoutObservableArray<any> = ko.observableArray([]);
        workCode: KnockoutObservable<string> = ko.observable('');
        workPlaceName: KnockoutObservable<string> = ko.observable('');
        employeeCode: KnockoutObservable<string> = ko.observable('');
        employeeName: KnockoutObservable<string> = ko.observable('');
        dateHoliday: KnockoutObservable<any> = ko.observable('');
        numberDay: KnockoutObservable<any> = ko.observable('');
        residualDay: KnockoutObservable<any> = ko.observable('');
        leaveId: KnockoutObservable<any> = ko.observable('');
        employeeId: KnockoutObservable<any> = ko.observable('');
        
        constructor() {
            var self = this,
            info = getShared("KDM001_J_PARAMS");
            self.initScreen(info);
            
            self.callService(self.leaveId(),self.employeeId());
            self.columns = ko.observableArray([
                { headerText: 'コード', dataType: 'string', key: 'comDayOffID', width: 100, hidden: true },
                { headerText: nts.uk.resource.getText("KDM001_95"), key: 'dayOff', width: 110 },
                { headerText: nts.uk.resource.getText("KDM001_96"), key: 'remainDaysString', width: 100 },
                { headerText: nts.uk.resource.getText("KDM001_96"), key: 'remainDays', width: 100, hidden: true }
            ]);
            
            
            
            
            self.currentCodeList.subscribe(function(codesSelect) {
                self.itemsSelected.removeAll();
                var sumNum = 0;
                if(codesSelect.length > 0) {
                    _.each(codesSelect, x => {
                        let item = _.find(self.items(), x1 => { return x === x1.comDayOffID });
                        if (item) {
                            self.itemsSelected.push(item);
                        }
                    });
                    _.each(self.itemsSelected(), x => {
                        
                        if(self.dateHoliday() === x.dayOff){
                            nts.uk.ui.dialog.info({ messageId: "Msg_730" });
                        }  
                        var iNum = parseFloat(x.remainDays);
                        var day = parseFloat(self.numberDay());
                        sumNum = sumNum + iNum;
                        self.residualDay(parseFloat((day-sumNum)).toFixed(1)+' 日');
                    });
                } else {
                   var day = parseFloat(self.numberDay());
                   self.residualDay(parseFloat(self.numberDay()).toFixed(1)+' 日');
                }
            });
            
            
        }
        
        
        public initScreen(info): void {
            var self = this;
            if (info) {
                self.workCode(info.selectedEmployee.workplaceCode);
                self.workPlaceName(info.selectedEmployee.workplaceName);
                self.employeeId(info.selectedEmployee.employeeId);
                self.employeeCode(info.selectedEmployee.employeeCode);
                self.employeeName(info.selectedEmployee.employeeName);
                self.leaveId(info.row.id);
                self.dateHoliday(info.row.dayOffDate);
                self.numberDay(parseFloat(info.row.occurredDays).toFixed(1)+' 日');
            }
           
        }

        /**
         * closeDialog
         */
        public closeDialog():void {
            nts.uk.ui.windows.close();
        }
        
        /**
         * update
         */
        public update():void {
            var self = this;
            
            service.update(new UpdateModel(self.employeeId(),self.leaveId(),self.itemsSelected())).done(function(data) {
                if (data.length > 0) {
                        let messageId = data[0];
                        if(messageId === 'Msg_15') {
                             nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                        }else {
                                $('#multi-list').ntsError('set', { messageId: messageId });
                            }
                        
                        block.clear();
                        return;
                    }
            }).fail(function(error) {
               
                
            }).always(() => {
                
            });
        }
        
        
        public callService(leaveId,employeeId):void {
            
             var self = this;
            service.getAll(leaveId,employeeId).done(function(data) {

                for (let i = 0; i < data.length; i++) {
                    self.items.push(new ItemModel(data[i].comDayOffId, data[i].dateHoliday,data[i].numberDay, data[i].numberDay+" 日"));
                    if(data[i].usedDay == true) {
                        console.log(data[i].comDayOffId);
                        self.currentCodeList.push(data[i].comDayOffId);
                    }
                    
                }
            }).fail(function(error) {
                
            }).always(() => {
                
            });
        
        }
        
        
    }

    class ItemModel {
        comDayOffID: string;
        dayOff: string;
        remainDays: string;
        remainDaysString: string;
        constructor(comDayOffID: string, dayOff: string, remainDays: string, remainDaysString: string) {
            this.comDayOffID = comDayOffID;
            this.dayOff = dayOff;
            this.remainDays = remainDays;
            this.remainDaysString = remainDaysString;
        }
        
    }
    
    class UpdateModel {
        employeeId: string;
        leaveId: string;
        comDayOffManaDtos: Array<ItemModel>;
        constructor(employeeId: string,leaveId: string, comDayOffManaDtos: Array<ItemModel>) {
            this.employeeId = employeeId;
            this.leaveId = leaveId;
            this.comDayOffManaDtos = comDayOffManaDtos;
        }
    }
    
}