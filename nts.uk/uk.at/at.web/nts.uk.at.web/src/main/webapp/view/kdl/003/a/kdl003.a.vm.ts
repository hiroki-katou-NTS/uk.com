module nts.uk.at.view.kdl003.a {
    export module viewmodel {
        export class ScreenModel {

            // Search options.
            startTimeOption: KnockoutObservable<number>;
            startTime: KnockoutObservable<number>;
            endTimeOption: KnockoutObservable<number>;
            endTime: KnockoutObservable<number>;

            // Data list & selected code.
            listWorkTime: KnockoutObservableArray<WorkTimeSet>;
            selectedWorkTimeCode: KnockoutObservable<string>;
            listWorkType: KnockoutObservableArray<WorkType>;
            selectedWorkTypeCode: KnockoutObservable<string>;

            // Define columns.
            workTimeColumns: KnockoutObservableArray<NtsGridListColumn>;
            workTypeColumns: KnockoutObservableArray<NtsGridListColumn>;

            // Initial work time code list..
            initialWorkTimeCodes: Array<String>;

            // Parameter from caller screen.
            callerParameter: CallerParameter;

            constructor(parentData: CallerParameter) {
                var self = this;

                // Search options
                self.startTimeOption = ko.observable(1);
                self.startTime = ko.observable(null);
                self.endTimeOption = ko.observable(1);
                self.endTime = ko.observable(null);

                self.listWorkTime = ko.observableArray([]);
                self.selectedWorkTimeCode = ko.observable('');
                self.listWorkType = ko.observableArray([]);
                self.selectedWorkTypeCode = ko.observable('');

                // Define gridlist's columns
                self.workTypeColumns = ko.observableArray([
                    { headerText: nts.uk.resource.getText('KDL003_5'), prop: 'workTypeCode', width: 50 },
                    { headerText: nts.uk.resource.getText('KDL003_6'), prop: 'name', width: 100 },
                    { headerText: nts.uk.resource.getText('KDL003_7'), prop: 'memo', width: 130 }
                ]);
                self.workTimeColumns = ko.observableArray([
                    { headerText: nts.uk.resource.getText('KDL003_5'), prop: 'code', width: 50 },
                    { headerText: nts.uk.resource.getText('KDL003_6'), prop: 'name', width: 100 },
                    { headerText: nts.uk.resource.getText('KDL003_19'), prop: 'workTime1', width: 200 },
                    { headerText: nts.uk.resource.getText('KDL003_20'), prop: 'workTime2', width: 200 },
                    { headerText: nts.uk.resource.getText('KDL003_21'), prop: 'workAtr', width: 100 },
                    { headerText: nts.uk.resource.getText('KDL003_7'), prop: 'remark', template: '<span>${remark}</span>' }
                ]);

                //parent data
                self.callerParameter = parentData;

            }

            /**
             * Start page.
             */
            private startPage(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                nts.uk.ui.block.invisible();
                $.when(self.loadWorkTime(),
                    self.loadWorkType())
                    .done(() => {
                        // Set initial selection.
                        self.initWorkTypeSelection();
                        self.setWorkTimeSelection();

                        // On selectedWorkTypeCode changed event.
                        self.selectedWorkTypeCode.subscribe(code => {
                            if (nts.uk.util.isNullOrEmpty(self.listWorkTime())) {
                                return;
                            }
                            service.isWorkTimeSettingNeeded(code).done(val => {
                                switch (val) {
                                    case SetupType.REQUIRED:
                                        self.setWorkTimeSelection();
                                        break;
                                    case SetupType.OPTIONAL:
                                        // Do nothing.
                                        break;
                                    default: self.selectedWorkTimeCode('000');
                                }
                            });
                        });

                        // Set initial work time list.
                        self.initialWorkTimeCodes = _.map(self.listWorkTime(), function(item) { return item.code })

                        dfd.resolve();
                    })
                    .fail(function(res) {
                        nts.uk.ui.dialog.alertError({ messageId: res.messageId });
                    }).always(() => {
                        nts.uk.ui.block.clear();
                    });
                return dfd.promise();
            }

            /**
             * Load work time.
             */
            private loadWorkTime(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();

                // Find work time by list code if caller's parameters exist.
                if (!nts.uk.util.isNullOrEmpty(self.callerParameter.workTimeCodes)) {
                    service.findWorkTimeByCodes(self.callerParameter.workTimeCodes)
                        .done(function(data) {
                            self.addFirstItem(data);
                            dfd.resolve();
                        });
                } else {
                    // Find all work time
                    service.findAllWorkTime()
                        .done(function(data) {
                            self.addFirstItem(data);
                            dfd.resolve();
                        });
                }
                return dfd.promise();
            }

            /**
             * Load work type.
             */
            private loadWorkType(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();

                // Find work type by list code if caller's parameters exist.
                if (!nts.uk.util.isNullOrEmpty(self.callerParameter.workTypeCodes)) {
                    service.findWorkTypeByCodes(self.callerParameter.workTypeCodes)
                        .done(function(workTypeList: Array<WorkType>) {
                            self.listWorkType(workTypeList);
                            dfd.resolve();
                        });
                } else {
                    // Find all work type.
                    service.findAllWorkType()
                        .done(function(workTypeList: Array<WorkType>) {
                            self.listWorkType(workTypeList);
                            dfd.resolve();
                        });
                }
                return dfd.promise();
            }

            /**
             * Add first work time item.
             */
            private addFirstItem(data: Array<WorkTimeSet>): void {
                let self = this;
                //add item　なし
                data.unshift({
                    code: "000",
                    name: "なし",
                    workTime1: "",
                    workTime2: "",
                    workAtr: "",
                    remark: ""
                });
                self.listWorkTime(data);
            }

            /**
             * Initial work type selection 
             */
            private initWorkTypeSelection(): void {
                let self = this;
                // Selected code from caller screen.
                if (self.callerParameter.selectedWorkTypeCode) {
                    self.selectedWorkTypeCode(self.callerParameter.selectedWorkTypeCode);
                } else {
                    // Select first item.
                    self.selectedWorkTypeCode(_.first(self.listWorkType()).workTypeCode);
                }
            }

            /**
             * Set work time selection.
             */
            private setWorkTimeSelection(): void {
                let self = this;
                // Selected code from caller screen.
                if (self.callerParameter.selectedWorkTimeCode) {
                    self.selectedWorkTimeCode(self.callerParameter.selectedWorkTimeCode);
                } else {
                    // Select first item.
                    self.selectedWorkTimeCode(_.first(self.listWorkTime()).code);
                }
            }

            /**
             * Search work time.
             */
            public search(): void {
                nts.uk.ui.block.invisible();
                var self = this;

                // Search command.
                let command = {
                    codelist: self.initialWorkTimeCodes,
                    startAtr: self.startTimeOption(),
                    startTime: nts.uk.util.isNullOrEmpty(self.startTime()) ? -1 : self.startTime(),
                    endAtr: self.endTimeOption(),
                    endTime: nts.uk.util.isNullOrEmpty(self.endTime()) ? -1 : self.endTime()
                }

                // Search & display data.
                service.findByTime(command)
                    .done(function(data) {
                        self.listWorkTime(data);
                        if (!nts.uk.util.isNullOrEmpty(data)) {
                            self.selectedWorkTimeCode(data[0].code);
                        }
                    })
                    .fail(function(res) {
                        nts.uk.ui.dialog.alertError({ messageId: res.messageId });
                    }).always(() => {
                        // Set focus.
                        $("[tabindex='10']").focus();

                        nts.uk.ui.block.clear();
                    });
            }

            /**
             * Clear search condition.
             */
            public clearSearchCondition(): void {
                nts.uk.ui.block.invisible();
                var self = this;

                // Reset search conditions
                self.startTimeOption(1);
                self.startTime(null);
                self.endTimeOption(1);
                self.endTime(null);

                // Reload list work time.
                self.loadWorkTime().always(() => {
                    nts.uk.ui.block.clear();
                });

                // Set focus.
                $("[tabindex='10']").focus();
            }

            /**
             * Submit.
             */
            public submit() {
                nts.uk.ui.block.invisible();
                let self = this;
                let dfd = $.Deferred<void>();

                let workTypeCode = self.selectedWorkTypeCode();
                let workTimeCode = self.selectedWorkTimeCode();

                // Set work time = なし if list work time is empty.
                if (nts.uk.util.isNullOrEmpty(self.listWorkTime())) {
                    workTimeCode = '000';
                }

                // Check pair work type & work time.
                service.checkPairWorkTypeWorkTime(workTypeCode, workTimeCode).done(() => {

                    // Set shared data.
                    let workTypeName = self.getWorkTypeName(workTypeCode);
                    let workTimeName = self.getWorkTimeName(workTimeCode);
                    let returnedData = {
                        selectedWorkTypeCode: workTypeCode,
                        selectedWorkTypeName: workTypeName,
                        selectedWorkTimeCode: workTimeCode,
                        selectedWorkTimeName: workTimeName
                    };
                    nts.uk.ui.windows.setShared("childData", returnedData, true);

                    // Close dialog.
                    self.closeDialog();

                }).fail(error => {
                    nts.uk.ui.dialog.alertError(error);
                }).always(() => {
                    nts.uk.ui.block.clear();
                });
                return dfd.promise();
            }

            /**
             * Get work type name.
             */
            private getWorkTypeName(workTypeCode: string): string {
                let self = this;
                let name: string = '';
                if (self.listWorkTime()) {
                    let workType = _.find(self.listWorkType(), workType => workType.workTypeCode == workTypeCode);
                    name = workType ? workType.name : '';
                }
                return name;
            }

            /**
             * Get work time name.
             */
            private getWorkTimeName(workTimeCode: string): string {
                let self = this;
                let name: string = '';
                if (self.listWorkTime()) {
                    let workTime = _.find(self.listWorkTime(), workTime => workTime.code == workTimeCode);
                    name = workTime ? workTime.name : '';
                }
                return name;
            }

            /**
             * Close dialog.
             */
            public closeDialog(): void {
                nts.uk.ui.windows.close();
            }
        }

        interface WorkTimeSet {
            code: string;
            name: string;
            workTime1: string;
            workTime2: string;
            workAtr: string;
            remark: string;
        }
        export class WorkType {
            abbreviationName: string;
            companyId: string;
            displayAtr: number;
            memo: string;
            name: string;
            sortOrder: number;
            symbolicName: string;
            workTypeCode: string;
        }
        interface CallerParameter {
            workTypeCodes: Array<string>;
            selectedWorkTypeCode: string;
            workTimeCodes: Array<string>;
            selectedWorkTimeCode: string;
        }
        interface ReturnedData {
            selectedWorkTypeCode: string;
            selectedWorkTypeName: string;
            selectedWorkTimeCode: string;
            selectedWorkTimeName: string;
        }
        enum SetupType {
            REQUIRED = 0,
            OPTIONAL = 1,
            NOT_REQUIRED = 2
        }
    }
}