module nts.uk.at.view.kdl023.viewmodel {

    import baseService = nts.uk.at.view.kdl023.base.service;
    import PatternReflection = nts.uk.at.view.kdl023.base.viewmodel.ReflectionSetting;

    export class ScreenModel {
        patternList: KnockoutObservableArray<any>;
        enableB: KnockoutComputed<boolean>;
        isReflectionMethodEnable: KnockoutComputed<boolean>;

        reflectionSetting: PatternReflection;
        listWorkType: KnockoutObservableArray<baseService.model.WorkType>;
        returnedSetting: PatternReflection;

        constructor() {
            let self = this;
            self.patternList = ko.observableArray([]);

            // Default pattern reflection setting
            self.reflectionSetting = PatternReflection.newSetting();
            self.returnedSetting = PatternReflection.newSetting();

            self.enableB = ko.computed(() => {
                if (self.reflectionSetting.calendarStartDate() && self.reflectionSetting.calendarEndDate()) {
                    if ($('.nts-input').ntsError('hasError')) {
                        return false;
                    }
                    return true;
                }
                return false;
            });
            self.listWorkType = ko.observableArray(new Array<baseService.model.WorkType>());
            self.isReflectionMethodEnable = ko.computed(() => {
                return self.reflectionSetting.statutorySetting.useClassification() ||
                    self.reflectionSetting.nonStatutorySetting.useClassification() ||
                    self.reflectionSetting.holidaySetting.useClassification();
            }).extend({ notify: 'always' });
        }

        /**
         * Start page.
         */
        public startPage(): JQueryPromise<void> {
            nts.uk.ui.block.invisible();
            let dfd = $.Deferred<any>();
            let self = this;
            baseService.findAllPattern().done(res => {
                if (res) {
                    self.patternList(res);
                }
                self.loadWorktypeList()
                    .done(() => dfd.resolve())
                    .always(() => nts.uk.ui.block.clear());
            }).fail(res => {
                nts.uk.ui.dialog.alertError(res);
            }).always(() => {
            });
            return dfd.promise();
        }

        /**
         * Load worktype list.
         */
        private loadWorktypeList(): JQueryPromise<void> {
            let self = this;
            let dfd = $.Deferred<void>();
            baseService.getAllWorkType().done(list => {
                if (list && list.length > 0) {
                    self.listWorkType(list);
                }
                dfd.resolve();
            }).fail(res => {
                nts.uk.ui.dialog.alertError(res);
                dfd.fail();
            });
            return dfd.promise();
        }

        /**
         * Validate input date.
         */
        private isInvalidDate(): boolean {
            let self = this;
            let startDate = moment(self.reflectionSetting.calendarStartDate());
            let endDate = moment(self.reflectionSetting.calendarEndDate());

            if (startDate.isSameOrAfter(endDate)) {
                return true;
            }

            return false;
        }

        /**
         * Open dialog A
         */
        public gotoA(): void {
            let self = this;
            let data: baseService.model.ReflectionSetting = ko.toJS(self.reflectionSetting);
            data.calendarStartDate = null;
            data.calendarEndDate = null;
            nts.uk.ui.windows.setShared('reflectionSetting', data);
            nts.uk.ui.windows.sub.modal('/view/kdl/023/a/index.xhtml');
        }

        /**
         * Open dialog B
         */
        public gotoB(): void {
            let self = this;

            if (self.isInvalidDate()) {
                nts.uk.ui.dialog.alertError('- Start date must be before end date');
                return;
            }

            nts.uk.ui.windows.setShared('reflectionSetting', ko.toJS(self.reflectionSetting));
            nts.uk.ui.windows.sub.modal('/view/kdl/023/b/index.xhtml').onClosed(() => {
                let dto = nts.uk.ui.windows.getShared('returnedData');
                if (dto) {
                    self.returnedSetting.fromDto(dto);
                }
            });
        }
    }
}