module nts.uk.at.view.kdl032.a.viewmodel {
    export class ScreenModel {
        deviationTimeList: KnockoutObservableArray<any>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        companyID:  KnockoutObservable<string>;
        deviationTimeID: KnockoutObservable<string>;
        
        
        constructor() {
            let self = this;
            self.deviationTimeList = ko.observableArray([]);
    

          
            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KDL032_3"), prop: 'divReasonCode', width: 100 },
                { headerText: nts.uk.resource.getText("KDL032_4"), prop: 'divReasonContent', width: 150 }
               
            ]);
              self.currentCode = ko.observable();
            self.currentCodeList = ko.observableArray([]);
            
            //Parameter 
             // self.companyID(nts.uk.ui.windows.getShared('KDL032'));
            
        }

        startPage(): JQueryPromise<any> {
            var self = this;
             var parameter = nts.uk.ui.windows.getShared("KDL032");
            self.reasonCD = parameter.reasonCD;
            self.divergenceTimeID = parameter.divergenceTimeID;
            var dfd = $.Deferred();
          
            
           service.getData(parameter.divergenceTimeID).done(function(deviationTimeList: Array<DeviationTime>){
                 deviationTimeList = _.orderBy(deviationTimeList, ["deviationTimeCD"], ["asc"]);
                self.deviationTimeList(deviationTimeList);
                self.deviationTimeList().unshift(new DeviationTime( "", nts.uk.resource.getText("KDL032_7")));
              
            
            }); 

            dfd.resolve();

            return dfd.promise();
        }
    }
    class DeviationTime {
        deviationTimeCD: string;
        reason: string;
      
        constructor(deviationTimeCD: string, reason: string,  ) {
            this.deviationTimeCD = deviationTimeCD;
            this.reason = reason;
            
                      
        }
}
    }