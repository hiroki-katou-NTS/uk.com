module kdl010.a.viewmodel {
    export class ScreenModel {
        workLocationList: KnockoutObservableArray<any>;
        columns: KnockoutObservableArray<any>;
        selectCode: KnockoutObservable<any>;
        constructor() {
            var self = this;
            this.selectCode = ko.observable([]);
            this.workLocationList = ko.observableArray([]);
            this.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KDL010_8"), prop: 'workLocationCD', width: 100 },
                { headerText: nts.uk.resource.getText("KDL010_2"), prop: 'workLocationName', width: 230 }
            ]);

        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            /** Get list WorkLocation*/
            service.getAllWorkLocation().done(function(workLocationList: Array<viewmodel.WorkLocation>) {
                self.workLocationList(workLocationList);
                console.log(self.workLocationList());
                dfd.resolve();
            }).fail(function(error) {
                dfd.fail();
                alert(error.message);
            });
            return dfd.promise();
        }
        
        cancel_Dialog(): any {
            nts.uk.ui.windows.close();
        }

        submit() {
            var self = this;
            var selectWorkLocation = _.find(self.workLocationList(), ['workLocationCD', self.selectCode()]);
            if (selectWorkLocation !== undefined) {
                nts.uk.ui.windows.setShared("workLocation", selectWorkLocation, false);
            }
             else {
                nts.uk.ui.windows.setShared("workLocation", null, false);
                }
            self.cancel_Dialog();
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