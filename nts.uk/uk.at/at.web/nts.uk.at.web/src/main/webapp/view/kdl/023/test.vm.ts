module nts.uk.at.view.kdl023.viewmodel {

    import baseService = nts.uk.at.view.kdl023.base.service;

    export class ScreenModel {
        patternList: KnockoutObservableArray<any>;
        selectedPatternCode: KnockoutObservable<string>;
        start: KnockoutObservable<string>;
        end: KnockoutObservable<string>;
        returnedList: KnockoutObservableArray<any>;
        enableB: KnockoutComputed<boolean>;

        constructor() {
            let self = this;
            self.patternList = ko.observableArray([]);
            self.selectedPatternCode = ko.observable('');
            self.start = ko.observable('');
            self.end = ko.observable('');
            self.enableB = ko.computed(() => {
                if (self.start() && self.end()) {
                    if ($('.nts-input').ntsError('hasError')) {
                        return false;
                    }
                    return true;
                }
                return false;
            });
            self.returnedList = ko.observableArray([{ start: 'NONE', listText: ['NONE'] }]);
        }

        private startPage(): JQueryPromise<any> {
            let dfd = $.Deferred<any>();
            let self = this;
            baseService.findAllPattern().done(res => {
                self.patternList(res);
                dfd.resolve();
            });
            return dfd.promise();
        }

        public gotoA(): void {
            let self = this;
            nts.uk.ui.windows.setShared('patternCode', self.selectedPatternCode());
            nts.uk.ui.windows.sub.modal('/view/kdl/023/a/index.xhtml');
            nts.uk.ui.windows.setShared('startDate', undefined);
            nts.uk.ui.windows.setShared('endDate', undefined);
        }
        public gotoB(): void {
            let self = this;
            nts.uk.ui.windows.setShared('patternCode', self.selectedPatternCode());
            nts.uk.ui.windows.setShared('startDate', self.start());
            nts.uk.ui.windows.setShared('endDate', self.end());
            nts.uk.ui.windows.sub.modal('/view/kdl/023/b/index.xhtml').onClosed(() => {
                let abc = nts.uk.ui.windows.getShared("listDateSetting");
                if (abc) {
                    self.returnedList(abc);
                } else {
                    self.returnedList([{ start: 'NONE', listText: ['NONE'] }]);
                }
            });
        }
    }
}