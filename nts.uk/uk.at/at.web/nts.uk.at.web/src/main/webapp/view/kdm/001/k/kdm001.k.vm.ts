module nts.uk.at.view.kdm001.k {
    export module viewmodel {
        export class ScreenModel {
        items: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        currentCode: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;
        count: number = 100;
        switchOptions: KnockoutObservableArray<any>;
        workCode: KnockoutObservableArray<any>;
        workPlaceName: KnockoutObservableArray<any>;
        employeeCode: KnockoutObservableArray<any>;    
        employeeName: KnockoutObservableArray<any>;
        dateHoliday: KnockoutObservableArray<any>;
        numberDay: KnockoutObservableArray<any>;      
        constructor() {
            var self = this;
            self.items = ko.observableArray([]);
            self.initScreen();
            self.currentCode = ko.observable();
            self.currentCodeList = ko.observableArray([]);
            
        }
        
        public initScreen(): void {
            var self = this;
            self.workCode = ko.observable('100');
            self.workPlaceName = ko.observable('workPlaceName');
            self.employeeCode = ko.observable('A000001');
            self.employeeName = ko.observable('employeeName');
            self.dateHoliday = ko.observable('2016/10/2');
            self.numberDay = ko.observable('numberDay');
            
            for(let i = 1; i < 100; i++) {
                self.items.push(new ItemModel('00' + i, "2010/1/10", "1.0F"));
            }
            
            self.columns = ko.observableArray([
                { headerText: 'コード', key: 'code', width: 100, hidden: true },
                { headerText: nts.uk.resource.getText("KDM001_95"), key: 'date', width: 100 }, 
                { headerText: nts.uk.resource.getText("KDM001_96"), key: 'useNumberDay', width: 100} 
            ]); 
            
        }
    }
    
    class ItemModel {
        code: string;
        date: string;
        useNumberDay: string;
        constructor(code: string,  date: string, useNumberDay?: string) {
            this.code = code;
            this.date = date;
            this.useNumberDay = useNumberDay;
           
        }
    }
    }
}