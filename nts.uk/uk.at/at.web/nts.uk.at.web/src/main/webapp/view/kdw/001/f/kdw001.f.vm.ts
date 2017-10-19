module nts.uk.at.view.kdw001.f {
    import getText = nts.uk.resource.getText;
    export module viewmodel {
        export class ScreenModel {
            enable: KnockoutObservable<boolean>;
            required: KnockoutObservable<boolean>;
            dateValue: KnockoutObservable<any>;
            startDateString: KnockoutObservable<any>;
            endDateString: KnockoutObservable<any>;
            //table
            listClassification : KnockoutObservableArray<any>;
            columns: Array<any>;//nts.uk.ui.NtsGridListColumn
            currentSelectedRow: KnockoutObservable<any>;
            constructor() {
                let self = this;
                //
                self.enable = ko.observable(true);
                self.required = ko.observable(true);
                self.dateValue = ko.observable({});
                let today = new Date;
                self.dateValue().startDate = new Date(today.getFullYear()-1, today.getMonth(), today.getDate()+2);
                self.dateValue().endDate =today;
                self.startDateString = ko.observable('');
                self.endDateString = ko.observable(new Date());
                //table
                self.listClassification = ko.observableArray([]);
                self.currentSelectedRow = ko.observable(null);
                var temp = [];
                for (let i = 1; i <= 15; i++) {
                    temp.push({
                         date: "2017/01/0"+i, 
                         code :"A00000"+i,
                         name :"name "+i,
                         timeclose :"time close "+i,
                         menu :"menu"+i,
                         result :"result "+i,
                         detail : "参照 "});
                }
                self.listClassification(temp);
                self.columns = [
                    { headerText: getText('KDW001_73'), key: 'date', width: 100 },
                    { headerText: getText('KDW001_74'), key: 'code', width: 120 },
                    { headerText: getText('KDW001_75'), key: 'name', width: 100 },
                    { headerText: getText('KDW001_76'), key: 'timeclose', width: 150 },
                    { headerText: getText('KDW001_77'), key: 'menu', width: 200 },
                    { headerText: getText('KDW001_78'), key: 'result', width: 160 },
                    { headerText: getText('KDW001_79'), key: 'detail', width: 100, 
                        template: '<button data-bind="click :openDialogI">${detail}</button>', 
                        columnCssClass: "colStyleButton",
                         }
                ];
                
//                self.startDateString.subscribe(function(value){
//                    self.dateValue().startDate = value;
//                    self.dateValue.valueHasMutated();        
//                });
//                
//                self.endDateString.subscribe(function(value) {
//                    self.dateValue().endDate = value;   
//                    self.dateValue.valueHasMutated();      
//                });
            }
            
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }//end start page
            
            //open dialog I
            openDialogI(){
                nts.uk.ui.windows.sub.modal("/view/kdw/001/i/index.xhtml");    
            }
        }//end screenModel
    }//end viewmodel    
}//end module
    