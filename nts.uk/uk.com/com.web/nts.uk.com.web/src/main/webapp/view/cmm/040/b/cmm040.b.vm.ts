module nts.uk.com.view.cmm040.b.viewmodel {
    export class ScreenModel {
        selectCode: KnockoutObservable<any>;
        workLocationList: KnockoutObservableArray<any>;
        constructor() {
            var self = this;
            this.selectCode = ko.observable([]);
            
            this.workLocationList = ko.observableArray([]);
            this.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KDL010_8"), prop: 'workLocationCD', width: 60 },
                { headerText: nts.uk.resource.getText("KDL010_2"), prop: 'workLocationName', width: 290 }
            ]); 
           
        }
        
        startPage(): JQueryPromise<any> {
            var self = this;
            
            var dfd = $.Deferred();
                dfd.resolve();
           
            return dfd.promise();
        }
    }
     export class WorkLocation {
        workLocationCD: string;
        workLocationName: string;
        constructor(workLocationCD: string, workLocationName: string) {
            this.workLocationCD = workLocationCD;
            this.workLocationName = workLocationName
        }
    }
}