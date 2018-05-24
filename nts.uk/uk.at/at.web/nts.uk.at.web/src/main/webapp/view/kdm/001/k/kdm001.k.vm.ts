module nts.uk.at.view.kdm001.k.viewmodel {
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
        comDayOffId: KnockoutObservable<any> = ko.observable('');
        employeeId: KnockoutObservable<any> = ko.observable('');
        
        
        constructor() {
            var self = this;
            
            info = getShared("KDM001_K_PARAMS");
            if (info) {
                self.workCode(info.selectedEmployee.workplaceCode);
                self.workPlaceName(info.selectedEmployee.workplaceName);
                self.employeeId(info.selectedEmployee.employeeId);
                self.employeeCode(info.selectedEmployee.employeeCode);
                self.employeeName(info.selectedEmployee.employeeName);
                self.comDayOffId(info.row.id);
            }
            
            self.callService(self.comDayOffId(),self.employeeId());
            
            self.columns = ko.observableArray([
                { headerText: 'コード', dataType: 'string', key: 'leaveManaID', width: 100, hidden: true },
                { headerText: nts.uk.resource.getText("KDM001_95"), key: 'dayOff', width: 100 },
                { headerText: nts.uk.resource.getText("KDM001_96"), key: 'remainDays', width: 100 }
            ]);
            self.initScreen();
            
            
            self.currentCodeList.subscribe(function(codesSelect) {
                self.itemsSelected.removeAll();
                var sumNum = 0;
                if(codesSelect.length > 0) {
                    _.each(codesSelect, x => {
                        let item = _.find(self.items(), x1 => { return x === x1.leaveManaID });
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
                        self.residualDay((day-sumNum)+' 日');
                    });
                } else {
                   var day = parseFloat(self.numberDay());
                   self.residualDay(parseFloat(self.numberDay())+' 日');
                }
            });
            
        }

        public initScreen(): void {
            var self = this;
            
            self.dateHoliday('2016/10/2');
            self.numberDay('1.0日');
            self.residualDay('0日');
            
        }

        /**
         * closeDialog
         */
        public closeDialog(): void {
            nts.uk.ui.windows.close();
        }
        
        
        /**
         * update
         */
        public update():void {
            var self = this;
            service.update(new UpdateModel(self.employeeId(),self.comDayOffId(),self.itemsSelected())).done(function(data) {
                if (data.length > 0) {
                        let messageId = data[0];
                        $('#multi-list').ntsError('set', { messageId: messageId });
                        block.clear();
                        return;
                    }
            }).fail(function(error) {
               
                
            }).always(() => {
                
            });
        }
        
         public callService(comDayOffId,employeeId):void {
            console.log(comDayOffId);
             var self = this;
            service.getAll(comDayOffId,employeeId).done(function(data) {
                console.log(data.length); 
                for (let i = 0; i < data.length; i++) {
                    self.items.push(new ItemModel(data[i].leaveManaID, data[i].dateHoliday, data[i].numberDay+" 日"));
                    if(data[i].usedDay == true) {
                        console.log(data[i].leaveManaID);
                        self.currentCodeList.push(data[i].leaveManaID);
                    }
                    
                }
            }).fail(function(error) {
                
            }).always(() => {
                
            });
        
        }
        
        
    }

    class ItemModel {
        leaveManaID: string;
        dayOff: string;
        remainDays: string;
        constructor(leaveManaID: string, dayOff: string, remainDays: string) {
            this.leaveManaID = leaveManaID;
            this.dayOff = dayOff;
            this.remainDays = remainDays;
        }
    }
    
    class UpdateModel {
        employeeId: string;
        comDayOffID: string;
        leaveManaDtos: Array<ItemModel>;
        constructor(employeeId: string,comDayOffID: string, leaveManaDtos: Array<ItemModel>) {
            this.employeeId = employeeId;
            this.comDayOffID = comDayOffID;
            this.leaveManaDtos = leaveManaDtos;
        }
    }
    
}