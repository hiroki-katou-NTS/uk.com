module nts.uk.at.view.kdw001.i {
    export module viewmodel {
        export class ScreenModel {
            //table
            itemList: KnockoutObservableArray<any>;
            constructor() {
                let self = this;
               //table
                self.itemList  = ko.observableArray([]);
                for (let i = 1; i <= 5; i++) {
                    self.itemList.push({column1: "2016/11/06 10:49:16 (" + i+")", 
                         column2 :"2016/11/06 10:50:16 (" + i+")",
                         column3 :"00:0"+i+":00",
                         column4 :"実行内容 "+i,
                         column5 :"実行種別"+i,
                         column6 :"実行詳細内容"+i,
                         column7 : i+i+"人",
                         column8 : i+"人" });
                }
            }
            
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }//end start page
        }//end screenModel
    }//end viewmodel    
}//end module
    