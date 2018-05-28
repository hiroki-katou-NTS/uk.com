module nts.uk.at.view.kal004.f.viewmodel {
    export class ScreenModel {
        list4weekClassEnum: KnockoutObservableArray<any>;
        textlabel: KnockoutObservable<string>;
        selectedId: KnockoutObservable<number>;
        enable: KnockoutObservable<boolean>;
        constructor() {
            var self = this;
            self.list4weekClassEnum = ko.observableArray(__viewContext.enums.SegmentationOfCycle);
            self.selectedId = ko.observable(nts.uk.ui.windows.getShared("segmentationOfCycle"));
            self.enable = ko.observable(true);
            self.textlabel = ko.observable(nts.uk.ui.windows.getShared("categoryName"));

        }
 
        startPage(): JQueryPromise<any> {
            var self = this;

            var dfd = $.Deferred();

            dfd.resolve();

            return dfd.promise();
        }
        submit() {
            let self = this;
            let dataSetShare = {
                decisionEnum: self.selectedId()
            };
            nts.uk.ui.windows.setShared('segmentationOfCycle', dataSetShare);
            nts.uk.ui.windows.close();
        }
        closeDialog(): void {
            nts.uk.ui.windows.close();
        }
    }
    class BoxModel {
        id: number;
        name: string;
        constructor(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
        }
    }
}