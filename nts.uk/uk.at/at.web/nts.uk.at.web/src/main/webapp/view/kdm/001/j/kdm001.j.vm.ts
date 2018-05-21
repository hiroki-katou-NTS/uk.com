module nts.uk.at.view.kdm001.j.viewmodel {
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
                { headerText: 'コード', key: 'code', width: 100, hidden: true },
                { headerText: nts.uk.resource.getText("KDM001_95"), key: 'date', width: 110 },
                { headerText: nts.uk.resource.getText("KDM001_96"), key: 'useNumberDay', width: 100 }
            ]);
            self.initScreen();
            
            self.currentCodeList.subscribe(function(codesSelect) {
                self.itemsSelected.removeAll();
                var sumNum = 0;
                if(codesSelect.length > 0) {
                    _.each(codesSelect, x => {
                        let item = _.find(self.items(), x1 => { return x === x1.code });
                        if (item) {
                            self.itemsSelected.push(item);
                        }
                    });
                    _.each(self.itemsSelected(), x => {
                        
                        if(self.dateHoliday() === x.date){
                            nts.uk.ui.dialog.info({ messageId: "Msg_730" });
                        }  
                        var iNum = parseFloat(x.useNumberDay);
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
        
        
        public callService():void {
            service.getAll('1').done(function(data) {
                
            }).fail(function(error) {
                alertError(error);
                
            }).always(() => {
                
            });
        
        }
        
        
    }

    class ItemModel {
        code: string;
        date: string;
        useNumberDay: string;
        constructor(code: string, date: string, useNumberDay?: string) {
            this.code = code;
            this.date = date;
            this.useNumberDay = useNumberDay;
        }
    }
}