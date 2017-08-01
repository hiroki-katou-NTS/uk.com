module nts.uk.at.view.kdl023.viewmodel {

    export class ScreenModel {
        patternCode: KnockoutObservable<string>;
        start: KnockoutObservable<string>;
        end: KnockoutObservable<string>;
        returnedList: KnockoutObservableArray<any>;

        constructor() {
            let self = this;
            self.patternCode = ko.observable('');
            self.start = ko.observable('');
            self.end = ko.observable('');
            self.returnedList = ko.observableArray([{ start: '', listText: [] }]);
        }

        private startPage(): JQueryPromise<any> {
            let dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }

        public gotoA(): void {
            let self = this;
            nts.uk.ui.windows.setShared('patternCode', self.patternCode());
            nts.uk.ui.windows.sub.modal('/view/kdl/023/a/index.xhtml');
        }
        public gotoB(): void {
            let self = this;
            nts.uk.ui.windows.setShared('patternCode', self.patternCode());
            nts.uk.ui.windows.setShared('startDate', self.start());
            nts.uk.ui.windows.setShared('endDate', self.end());
            nts.uk.ui.windows.sub.modal('/view/kdl/023/b/index.xhtml').onClosed(() => {
                let abc = nts.uk.ui.windows.getShared("listDateSetting");
                if (abc) {
                    self.returnedList(abc);
                    console.log(abc);
                } else {
                    self.returnedList([{ start: 'NONE', listText: [] }]);
                }
            });
        }
    }
}