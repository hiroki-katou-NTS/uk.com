module kdl010.a.viewmodel {
    export class ScreenModel {
        workLocationList: KnockoutObservableArray<any>;
        columns: KnockoutObservableArray<any>;
        selectCode: KnockoutObservable<any>;
        selectedCode: string;
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
            /** Get list WorkLocation*/
            $(".ntsSearchBox.nts-editor.ntsSearchBox_Component").focus();
            service.getAllWorkLocation().done(function(workLocationList: Array<viewmodel.WorkLocation>) {
                 workLocationList = _.orderBy(workLocationList, ["workLocationCD"], ["asc"]);
                self.workLocationList(workLocationList);
                self.workLocationList().unshift(new WorkLocation( "", nts.uk.resource.getText("KDL010_9")));
                self.selectedCode = nts.uk.ui.windows.getShared('KDL010SelectWorkLocation'); 
                self.selectCode(self.selectedCode);
                dfd.resolve();
            }).fail(function(error) {
                dfd.fail();
                alert(error.message);
            });
            return dfd.promise();
        }
        
        cancel_Dialog(): any {
            let self = this;
            nts.uk.ui.windows.setShared("KDL010workLocation", self.selectedCode);
            nts.uk.ui.windows.close();
        }

        submit() {
            var self = this;
            var selectWorkLocation = _.find(self.workLocationList(), ['workLocationCD', self.selectCode()]);
            if (selectWorkLocation !== undefined) {
                nts.uk.ui.windows.setShared("KDL010workLocation", selectWorkLocation.workLocationCD);
                nts.uk.ui.windows.setShared("KDL010workLocationName", selectWorkLocation.workLocationName);
            }
            else {
                nts.uk.ui.windows.setShared("KDL010workLocation", null, true);
                nts.uk.ui.windows.setShared("KDL010workLocationName", null, true);
            }
            nts.uk.ui.windows.close();
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