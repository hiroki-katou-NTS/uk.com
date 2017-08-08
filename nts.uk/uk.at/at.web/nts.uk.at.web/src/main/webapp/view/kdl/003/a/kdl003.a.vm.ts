module nts.uk.at.view.kdl003.a {
    import Enum = service.model.Enum;
    export module viewmodel {
        export class ScreenModel {
            columns: KnockoutObservableArray<NtsGridListColumn>;
            listWorkTime: KnockoutObservableArray<WorkTimeSet>;
            selectedWorkTimeCode: KnockoutObservable<string>;
            searchOption: KnockoutObservable<number>;
            startTimeOption: KnockoutObservable<number>;
            startTime: KnockoutObservable<number>;
            endTimeOption: KnockoutObservable<number>;
            endTime: KnockoutObservable<number>;

            listWorkType: KnockoutObservableArray<WorkType>;
            selectedWorkTypeCode: KnockoutObservable<string>;

            workTypeColumns: KnockoutObservableArray<NtsGridListColumn>;

            callerParameter: CallerParameter;

            lstTimeDayAtrEnum: KnockoutObservableArray<Enum>;
            constructor(parentData: CallerParameter) {
                var self = this;
                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText('KDL001_12'), prop: 'code', width: 50 },
                    { headerText: nts.uk.resource.getText('KDL001_13'), prop: 'name', width: 100 },
                    { headerText: nts.uk.resource.getText('KDL001_14'), prop: 'workTime1', width: 200 },
                    { headerText: nts.uk.resource.getText('KDL001_15'), prop: 'workTime2', width: 200 },
                    { headerText: nts.uk.resource.getText('KDL001_16'), prop: 'workAtr', width: 100 },
                    { headerText: nts.uk.resource.getText('KDL001_17'), prop: 'remark', template: '<span>${remark}</span>' }
                ]);
                self.selectedWorkTimeCode = ko.observable('');
                self.searchOption = ko.observable(0);
                self.startTimeOption = ko.observable(1);
                self.startTime = ko.observable('');
                self.endTimeOption = ko.observable(1);
                self.endTime = ko.observable('');
                self.listWorkTime = ko.observableArray([]);

                self.listWorkType = ko.observableArray([]);
                self.selectedWorkTypeCode = ko.observable('');
                self.workTypeColumns = ko.observableArray([
                    { headerText: nts.uk.resource.getText('KDL003_5'), prop: 'workTypeCode', width: 50 },
                    { headerText: nts.uk.resource.getText('KDL003_6'), prop: 'name', width: 100 },
                    { headerText: nts.uk.resource.getText('KDL003_7'), prop: 'memo', width: 130 }
                ]);

                //parent data
                self.callerParameter = parentData;

                self.lstTimeDayAtrEnum = ko.observableArray([]);
                self.selectedWorkTypeCode.subscribe(function(data, data2) {

                });
            }

            private startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                nts.uk.ui.block.invisible();
                $.when(self.loadWorkTime(),
                    self.loadWorkType())
                    .done(() => {
                        dfd.resolve();
                    })
                    .fail(function(res) {
                        nts.uk.ui.dialog.alertError({ messageId: res.messageId });
                    }).always(() => {
                        nts.uk.ui.block.clear();
                    });
                return dfd.promise();
            }

            private loadWorkTime(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();

                // find worktime by list code.
                if (self.callerParameter.workTimeCodes && self.callerParameter.workTimeCodes.length > 0) {
                    service.findByCodeList(self.callerParameter.workTimeCodes.split(','))
                        .done(function(data) {
                            self.addFirstItem(data);
                            self.initWorkTimeSelection();
                            dfd.resolve();
                        });
                }

                // Find all work time
                else {
                    service.findAllWorkTime()
                        .done(function(data) {
                            self.addFirstItem(data);
                            self.initWorkTimeSelection();
                            dfd.resolve();
                        });
                }
                return dfd.promise();
            }

            private loadWorkType(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();

                // find worktype by list code.
                if (self.callerParameter.workTypeCodes && self.callerParameter.workTypeCodes.length > 0) {
                    service.findWorkTypeByCodes(self.callerParameter.workTypeCodes.split(','))
                        .done(function(workTypeList: Array<WorkType>) {
                            self.listWorkType(workTypeList);
                            self.initWorkTypeSelection();
                            dfd.resolve();
                        });
                }

                // find all work type.
                else {
                    service.findAllWorkType()
                        .done(function(workTypeList: Array<WorkType>) {
                            self.listWorkType(workTypeList);
                            self.initWorkTypeSelection();
                            dfd.resolve();
                        });
                }
                return dfd.promise();
            }

            private addFirstItem(data): void {
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

            private initWorkTypeSelection(): void {
                let self = this;
                // Selected code from caller screen.
                if (self.callerParameter.selectedWorkTypeCode) {
                    self.selectedWorkTypeCode(self.callerParameter.selectedWorkTypeCode);
                }
                // Select first item.
                else {
                    self.selectedWorkTypeCode(_.first(self.listWorkType()).workTypeCode);
                }
            }

            private initWorkTimeSelection(): void {
                let self = this;
                // Selected code from caller screen.
                if (self.callerParameter.selectedWorkTimeCode) {
                    self.selectedWorkTimeCode(self.callerParameter.selectedWorkTimeCode);
                }
                // Select first item.
                else {
                    self.selectedWorkTimeCode(_.first(self.listWorkTime()).code);
                }
            }

            private getTimeDayAtrEnum(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                service.findTimeDayAtrEnum().done(
                    function(data) {
                        let map = _.reduce(data, function(hash, value) {
                            let key = value['value'];
                            hash[key] = value['localizedName'];
                            return hash;
                        }, {});
                        self.lstTimeDayAtrEnum(map);
                        dfd.resolve();
                    }
                );
                return dfd.promise();
            }

            public search() {
                nts.uk.ui.block.invisible();
                var self = this;
                let command = {
                    codelist: _.map(self.listWorkTime, function(item) { return item.code }),
                    startAtr: self.startTimeOption(),
                    startTime: nts.uk.util.isNullOrEmpty(self.startTime()) ? -1 : self.startTime(),
                    endAtr: self.endTimeOption(),
                    endTime: nts.uk.util.isNullOrEmpty(self.endTime()) ? -1 : self.endTime()
                }
                service.findByTime(command)
                    .done(function(data) {
                        self.listWorkTime(data);
                    })
                    .fail(function(res) {
                        nts.uk.ui.dialog.alertError({ messageId: res.messageId });
                    }).always(() => {
                        nts.uk.ui.block.clear();
                    });
            }

            private returnData() {
                nts.uk.ui.block.invisible();
                var self = this;
                self.startTimeOption(1);
                self.startTime('');
                self.endTimeOption(1);
                self.endTime('');
                service.findByCodeList(self.callerParameter.workTimeCodes.split(','))
                    .done(function(data) {
                        self.addFirstItem(data);
                        self.initWorkTimeSelection();
                    })
                    .fail(function(res) {
                        nts.uk.ui.dialog.alertError({ messageId: res.messageId });
                    }).always(() => {
                        nts.uk.ui.block.clear();
                    });
                $("#inputStartTime").focus();
            }

            private convertItemForview(item: any) {
                var self = this;
                item.startDay = self.lstTimeDayAtrEnum()[item.startDay];
                item.endDay = self.lstTimeDayAtrEnum()[item.endDay];
                item.startTime = nts.uk.time.parseTime(item.startTime, true).format();
                item.endTime = nts.uk.time.parseTime(item.endTime, true).format();
                return item;
            }

            public submitAndCloseDialog() {
                nts.uk.ui.block.invisible();
                let self = this;
                let workTypeName = self.getWorkTypeName(self.selectedWorkTypeCode());
                let workTimeName = self.getWorkTimeName(self.selectedWorkTimeCode());
                let returneData: ReturnedData = {
                    selectedWorkTypeCode: self.selectedWorkTypeCode(),
                    selectedWorkTypeName: workTypeName,
                    selectedWorkTimeCode: self.selectedWorkTimeCode(),
                    selectedWorkTimeName: workTimeName
                };
                nts.uk.ui.windows.setShared("childData", returneData, true);
                nts.uk.ui.block.clear();
                nts.uk.ui.windows.close();
            }

            private getWorkTypeName(workTypeCode: string): string {
                let self = this;
                let workType: WorkType;
                if (self.listWorkTime()) {
                    workType = _.find(self.listWorkType(), workType => workType.workTypeCode == workTypeCode);
                }
                return workType.name;
            }

            private getWorkTimeName(workTimeCode: string): string {
                let self = this;
                let workTime: WorkTimeSet;
                if (self.listWorkTime()) {
                    workTime = _.find(self.listWorkTime(), workTime => workTime.code == workTimeCode);
                }
                return workTime.name;
            }

            closeDialog() {
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
        interface RestTime {
            startDay: string;
            startTime: string;
            endDay: string;
            endTime: string;
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
    }
}