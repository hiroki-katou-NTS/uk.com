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

        constructor() {
            var self = this;
            self.callService();
            self.columns = ko.observableArray([
                { headerText: 'コード', key: 'comDayOffID', width: 100, hidden: true },
                { headerText: nts.uk.resource.getText("KDM001_95"), key: 'dayOff', width: 110 },
                { headerText: nts.uk.resource.getText("KDM001_96"), key: 'remainDays', width: 100 }
            ]);
            self.initScreen();
            
            console.log(getShared("KDM001_J_PARAMS"));
            
            
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
            self.workCode('100');
            self.workPlaceName('営業部');
            self.employeeCode('A000001');
            self.employeeName('日通　太郎');
            self.dateHoliday('2016/10/2');
            self.numberDay('1.0日');
            self.residualDay('0日');
            for (let i = 1; i < 3; i++) {
                self.items.push(new ItemModel('00' + i, "2016/10/23", "0.5日"));
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
            service.update(new UpdateModel("1","1",self.itemsSelected())).done(function(data) {
                if (data.length > 0) {
                        let messageId = data[0];
                        $('#multi-list').ntsError('set', { messageId: messageId });
                        block.clear();
                        return;
                    }
            }).fail(function(error) {
                alertError(error);
                
            }).always(() => {
                
            });
        }
        
        
        public callService():void {
            service.getAll('1').done(function(data) {
                
            }).fail(function(error) {
                alertError(error);
                
            }).always(() => {
                
            });
        
        }
        
        
    }

    class ItemModel {
        comDayOffID: string;
        dayOff: string;
        remainDays: string;
        constructor(comDayOffID: string, dayOff: string, remainDays: string) {
            this.comDayOffID = comDayOffID;
            this.dayOff = dayOff;
            this.remainDays = remainDays;
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