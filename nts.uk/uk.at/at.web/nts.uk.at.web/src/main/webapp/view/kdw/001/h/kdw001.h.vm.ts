module nts.uk.at.view.kdw001.h {
    import getText = nts.uk.resource.getText;
    export module viewmodel {
        export class ScreenModel {
            //list
            listClassification: KnockoutObservableArray<any>;
            columns: KnockoutObservableArray<any>;//nts.uk.ui.NtsGridListColumn
            currentSelectedRow: KnockoutObservable<any>;
            constructor() {
                let self = this;
                //list
                self.listClassification = ko.observableArray([]);
                self.currentSelectedRow = ko.observable(null);
                for (let i = 1; i <= 15; i++) {
                    self.listClassification.push({code: "A000000" + i, 
                         name :"name (" + i+")",
                         handle :"handle "+i ,
                         errorcontent :"error "+i
                         });
                }
                self.columns = ko.observableArray([
                    { headerText: getText('KDW001_33'), key: 'code', width: 100 },
                    { headerText: getText('KDW001_35'), key: 'name', width: 100 },
                    { headerText: getText('KDW001_36'), key: 'handle', width: 100 },
                    { headerText: getText('KDW001_37'), key: 'errorcontent', width: 100 }
                ]);
            }
            
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }//end start page
            
            closeDialog(): void {

                nts.uk.ui.windows.close();
    
            }
        }//end screenModel
    }//end viewmodel    
}//end module
    