module nts.uk.com.view.qmm008.j {
    export module viewmodel {
       export class ScreenModel {
           
           dataList: KnockoutObservableArray<RowData>;
           
           constructor(){
               var self = this;
               self.dataList = ko.observableArray([]);
               self.dataList.push(new RowData("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1","1"));
               $("#fixed-table").ntsFixedTable({ height: 300, width: 1366 });
           }
       }
    }
    
    
    export class RowData {
        col1: KnockoutObservable<string>;
        col2: KnockoutObservable<string>;
        col3: KnockoutObservable<string>;
        col4: KnockoutObservable<string>;
        col5: KnockoutObservable<string>;
        col6: KnockoutObservable<string>;
        col7: KnockoutObservable<string>;
        col8: KnockoutObservable<string>;
        col9: KnockoutObservable<string>;
        col10: KnockoutObservable<string>;
        col11: KnockoutObservable<string>;
        col12: KnockoutObservable<string>;
        constructor(col1: string, col2: string, col3: string, col4: string, col5: string, col6: string, col7: string, col8: string, col9: string, col10: string, col11: string, col12: string) {
            this.col1 = ko.observable(col1);
            this.col2 = ko.observable(col2);
            this.col3 = ko.observable(col3);
            this.col4 = ko.observable(col4);
            this.col5 = ko.observable(col5);
            this.col6 = ko.observable(col6);
            this.col7 = ko.observable(col7);
            this.col8 = ko.observable(col8);
            this.col9 = ko.observable(col9);
            this.col10 = ko.observable(col10);
            this.col11 = ko.observable(col11);
            this.col12 = ko.observable(col12);
        }
    }
    
}