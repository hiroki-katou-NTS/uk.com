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
        numberDayParam: KnockoutObservable<any> = ko.observable('');
        
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
                self.numberDayParam(parseFloat(info.row.occurredDays).toFixed(1));
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
            
            service.update(new UpdateModel(self.employeeId(),self.leaveId(),self.itemsSelected(),self.numberDayParam())).done(function(data) {
                if (data.length > 0) {
                        let messageId = data[0];
                        if(messageId === 'Msg_15') {
                             nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                             nts.uk.ui.windows.close();
                        }else {
                                $('#multi-list').ntsError('set', { messageId: messageId });
                            }
                        block.clear();
                    }
            }).fail(function(error) {
               
                
            }).always(() => {
                
            });
        }
        
        
        public callService(leaveId,employeeId):void {
            
             var self = this;
            service.getAll(leaveId,employeeId).done(function(data) {

                for (let i = 0; i < data.listDayOff.length; i++) {
                    self.items.push(new ItemModel(data.listDayOff[i].comDayOffId, data.listDayOff[i].dateHoliday,data.listDayOff[i].numberDay, data.listDayOff[i].numberDay+" 日"));
                    if(data.listDayOff[i].usedDay == true) {
                        self.currentCodeList.push(data.listDayOff[i].comDayOffId);
                    }
                    
                }
                
                if(data.errorCode != null) {
                    nts.uk.ui.dialog.alertError({ messageId: data.errorCode });
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
        numberDayParam: string;
        constructor(employeeId: string,leaveId: string, comDayOffManaDtos: Array<ItemModel>, numberDayParam: string) {
            this.employeeId = employeeId;
            this.leaveId = leaveId;
            this.comDayOffManaDtos = comDayOffManaDtos;
            this.numberDayParam = numberDayParam;
        }
    }
    
}