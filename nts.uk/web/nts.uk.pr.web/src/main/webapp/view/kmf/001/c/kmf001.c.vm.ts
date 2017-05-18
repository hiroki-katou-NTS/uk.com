module nts.uk.pr.view.kmf001.c {
    export module viewmodel {
        
        import EnumertionModel = service.model.EnumerationModel;
        
        export class ScreenModel {
            
            manageStatus: KnockoutObservableArray<EnumertionModel>;
            selectedManage: KnockoutObservable<number>;
            
            constructor() {
                let self = this;
                self.manageStatus = ko.observableArray([
                    {value: 0, name: "管理する"},
                    {value: 1, name: "管理しない"}
                ]);
                self.selectedManage = ko.observable(0);
            }
            
            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                dfd.resolve();
                return dfd.promise();
            }
        }
    }
}