// TreeGrid Node
module qpp014.h.viewmodel {
    export class ScreenModel {
        h_INP_001: KnockoutObservable<Date>;
        h_LST_001_items: KnockoutObservableArray<ItemModel_H_LST_001>;
        h_LST_001_itemsSelected: KnockoutObservable<any>;
        yearMonthDateInJapanEmpire: any;
        processingDate: any;
        processingDateInJapanEmprire: any;
        processingNo: any;
        processingName: any;
        
        constructor(data: any) {
            let self = this;

            //viewmodel H
            self.h_INP_001 = ko.observable(new Date('2016/12/01'));
            self.h_LST_001_items = ko.observableArray([]);
            for (let i = 1; i < 100; i++) {
                self.h_LST_001_items.push(new ItemModel_H_LST_001('00' + i, '基本給', "description " + i));
            }
            self.h_LST_001_itemsSelected = ko.observable();
            self.yearMonthDateInJapanEmpire = ko.computed(function() {
                return "(" + nts.uk.time.yearInJapanEmpire(moment(self.h_INP_001()).format('YYYY')).toString() +
                    moment(self.h_INP_001()).format('MM') + "月" + moment(self.h_INP_001()).format('DD') + "日)";
            });
            self.processingDate = ko.observable(nts.uk.time.formatYearMonth(data.currentProcessingYm));
            self.processingDateInJapanEmprire = ko.computed(function() {
                return "("+nts.uk.time.yearmonthInJapanEmpire(self.processingDate()).toString()+")";
            });
            self.processingNo = ko.observable(data.processingNo + ' : ');
            self.processingName = ko.observable(data.processingName + ' )');            
        }
    }
    export class ItemModel_H_LST_001 {
        code: string;
        name: string;
        description: string;

        constructor(code: string, name: string, description: string) {
            this.code = code;
            this.name = name;
            this.description = description;
        }
    }

};
