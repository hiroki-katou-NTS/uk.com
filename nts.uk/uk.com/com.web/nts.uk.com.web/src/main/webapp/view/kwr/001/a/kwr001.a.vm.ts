module nts.uk.at.view.kwr001.a {
    export module viewmodel {
        export class ScreenModel {
            data: KnockoutObservable<number>;
            
            // A1_6
            enable: KnockoutObservable<boolean>;
            required: KnockoutObservable<boolean>;
            dateValue: KnockoutObservable<any>;
            startDateString: KnockoutObservable<string>;
            endDateString: KnockoutObservable<string>;
            
            // switch button A6_2
            roundingRules: KnockoutObservableArray<any>;
            selectedRuleCode: any;
            
            // dropdownlist A7_3
            itemList: KnockoutObservableArray<ItemModel>;
            selectedCode: KnockoutObservable<string>;
            isEnable: KnockoutObservable<boolean>;
            isEditable: KnockoutObservable<boolean>;
            
            constructor() {
                var self = this;
                self.data = ko.observable(1);
                
                self.enable = ko.observable(true);
                self.required = ko.observable(true);
                
                self.startDateString = ko.observable("");
                self.endDateString = ko.observable("");
                self.dateValue = ko.observable({});
                
                self.startDateString.subscribe(function(value){
                    self.dateValue().startDate = value;
                    self.dateValue.valueHasMutated();        
                });
                
                self.endDateString.subscribe(function(value){
                    self.dateValue().endDate = value;   
                    self.dateValue.valueHasMutated();      
                });
                
                self.roundingRules = ko.observableArray([
                    { code: '1', name: nts.uk.resource.getText("#KWR001_10") },
                    { code: '2', name: nts.uk.resource.getText("#KWR001_11") }
                ]);
                self.selectedRuleCode = ko.observable(1);
                
                // value of radio button group A13_1's name
                self.itemList = ko.observableArray([
                    new ItemModel('1', nts.uk.resource.getText("#KWR001_38")),
                    new ItemModel('2', nts.uk.resource.getText("#KWR001_39"))
                ]);
        
                self.selectedCode = ko.observable('1');
                self.isEnable = ko.observable(true);
                self.isEditable = ko.observable(true);
            }
            
            public startPage(): JQueryPromise<void>  {
                var dfd = $.Deferred<void>();
                var self = this;
                return dfd.promise();
            }
            openScreenB () {
                var self = this;
                nts.uk.ui.windows.setShared('KWR001_B', self.data(), true);
                nts.uk.ui.windows.sub.modal('/view/kwr/001/b/index.xhtml').onClosed(function(): any {
                    nts.uk.ui.windows.getShared('KWR001_B');
                });
            }
            openScreenC () {
                var self = this;
                nts.uk.ui.windows.setShared('KWR001_C', self.data(), true);
                nts.uk.ui.windows.sub.modal('/view/kwr/001/c/index.xhtml').onClosed(function(): any {
                    nts.uk.ui.windows.getShared('KWR001_C');
                });
            }
        }
        
        class ItemModel {
            code: string;
            name: string;
        
            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }
    }
}