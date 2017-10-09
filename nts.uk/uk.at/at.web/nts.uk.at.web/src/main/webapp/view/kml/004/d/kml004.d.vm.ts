import setSharedD = nts.uk.ui.windows.setShared;
import getSharedD = nts.uk.ui.windows.getShared;
module nts.uk.at.view.kmk004.d.viewmodel {
    
        export class ScreenModel {
            // list item time no received from  
            itemsSwap: KnockoutObservableArray<TotalTime>;
            // list columns 
            columns: KnockoutObservableArray<any>;
            currentCodeListSwap: KnockoutObservableArray<TotalSet>;
            constructor() {
                let self = this;
                self.itemsSwap = ko.observableArray([]);
                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText("KML004_40"), key: 'totalCountNo', width: 70 },
                    { headerText: nts.uk.resource.getText("KML004_41"), key: 'totalTimesName', width: 250, formatter: _.escape}
                ]);
                self.currentCodeListSwap = ko.observableArray([]);
            }


            /**
             * Event on start page.
             */
            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                service.getAll().done(function(data) {
                    self.itemsSwap(data);
                    console.log(self.itemsSwap());
                    dfd.resolve();
                });
                return dfd.promise();
            }

            /**
             * click register button, send cal day set to screen A 
             */
            public save(): void {
                var self = this;

            }

            /**
             * Event on click cancel button.
             */
            public cancel(): void {
                nts.uk.ui.windows.close();
            }
        }
    
    export class TotalSet{
        categoryCode: string;
        totalItemNo: number;
        totalTimeNo: number;
        totalTimeName: string;
        constructor(categoryCode: string, totalItemNo: number, totalTimeNo: number, totalTimeName: string){
            this.categoryCode = categoryCode;
            this.totalItemNo = totalItemNo;
            this.totalTimeNo = totalTimeNo;
            this.totalTimeName = totalTimeName;
        }  
    }
    
    export class TotalTime{
        totalCountNo: number;
        totalTimesName: string;
        constructor(totalCountNo: number, totalTimesName: string){
            this.totalCountNo = totalCountNo;
            this.totalTimesName = totalTimesName;     
        }
    }
}