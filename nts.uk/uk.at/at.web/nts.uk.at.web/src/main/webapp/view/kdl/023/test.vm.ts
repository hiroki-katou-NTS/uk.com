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
                if (res) {
                    self.patternList(res);
                }
            }).fail(res => {
                nts.uk.ui.dialog.alertError(res);
            }).always(() => {
                dfd.resolve();
            });
            return dfd.promise();
        }

        private isInvalidDate(): boolean {
            let self = this;
            let startDate = moment(self.start());
            let endDate = moment(self.end());

            if (startDate.isAfter(endDate)) {
                return true;
            }

            let range = moment.duration(endDate.diff(startDate));
            if (range.asDays() > 31) {
                return true;
            }

            return false;
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

            if (self.isInvalidDate()) {
                nts.uk.ui.dialog.alertError('- Start date must be before end date \n - Date range must be less than or equal 31 days.');
                return;
            }

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