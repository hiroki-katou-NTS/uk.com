module nts.uk.at.view.kdl003.a {
    import Enum = service.model.Enum;
    export module viewmodel {
        export class ScreenModel {
            columns: KnockoutObservableArray<NtsGridListColumn>;
            multiSelectMode: KnockoutObservable<boolean>;
            rootList: Array<WorkTimeSet>;
            selectAbleItemList: KnockoutObservableArray<WorkTimeSet>;
            selectedCodeList: KnockoutObservableArray<string>;
            selectedSiftCode: KnockoutObservable<string>;
            selectedSiftName: KnockoutObservable<string>;
            searchOption: KnockoutObservable<number>;
            startTimeOption: KnockoutObservable<number>;
            startTime: KnockoutObservable<number>;
            endTimeOption: KnockoutObservable<number>;
            endTime: KnockoutObservable<number>;

            lstWorkType: KnockoutObservableArray<WorkType>;
            selectedWorkTypeCode: KnockoutObservable<string>;
            selectedWorkTypeName: KnockoutObservable<string>;

            workTypeColumns: KnockoutObservableArray<NtsGridListColumn>;

            parentData: KnockoutObservable<any>;

            halfDayList1: KnockoutObservableArray<RestTime>;
            halfDayList2: KnockoutObservableArray<RestTime>;
            holidayList1: KnockoutObservableArray<RestTime>;
            holidayList2: KnockoutObservableArray<RestTime>;

            lstTimeDayAtrEnum: KnockoutObservableArray<Enum>;
            constructor(parentData: any) {
                var self = this;
                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText('KDL001_12'), prop: 'code', width: 50 },
                    { headerText: nts.uk.resource.getText('KDL001_13'), prop: 'name', width: 100 },
                    { headerText: nts.uk.resource.getText('KDL001_14'), prop: 'workTime1', width: 200 },
                    { headerText: nts.uk.resource.getText('KDL001_15'), prop: 'workTime2', width: 200 },
                    { headerText: nts.uk.resource.getText('KDL001_16'), prop: 'workAtr', width: 120 },
                    { headerText: nts.uk.resource.getText('KDL001_17'), prop: 'remark', template: '<span>${remark}</span>' }
                ]);
                self.multiSelectMode = nts.uk.ui.windows.getShared('kml001multiSelectMode');
                self.selectedCodeList = ko.observableArray(<Array<string>>nts.uk.ui.windows.getShared('kml001selectedCodeList'));
                self.selectedSiftCode = ko.observable(parentData.selectSiftCode);
                self.selectedSiftName = ko.observable("");
                self.searchOption = ko.observable(0);
                self.startTimeOption = ko.observable(1);
                self.startTime = ko.observable('');
                self.endTimeOption = ko.observable(1);
                self.endTime = ko.observable('');
                self.selectAbleItemList = ko.observableArray([]);

                self.lstWorkType = ko.observableArray([]);
                self.selectedWorkTypeCode = ko.observable(parentData.selectWorkTypeCode);
                self.selectedWorkTypeName = ko.observable("");
                self.workTypeColumns = ko.observableArray([
                    { headerText: nts.uk.resource.getText('KDL003_5'), prop: 'workTypeCode', width: 50 },
                    { headerText: nts.uk.resource.getText('KDL003_6'), prop: 'name', width: 100 },
                    { headerText: nts.uk.resource.getText('KDL003_7'), prop: 'memo', width: 200 }
                ]);

                //parent data
                self.parentData = ko.observable(parentData);

                self.halfDayList1 = ko.observable([]);
                self.halfDayList2 = ko.observable([]);
                self.holidayList1 = ko.observable([]);
                self.holidayList2 = ko.observable([]);

                self.lstTimeDayAtrEnum = ko.observableArray([]);
                self.selectedSiftCode.subscribe((code) => {
                    if (code != null && code != undefined) {
                        service.findAllRestTime(code).done(function(data) {
                            self.bindRestTime(data);
                        });
                    }
                });
                self.selectedWorkTypeCode.subscribe(function(data, data2) {

                });
            }

            private startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                nts.uk.ui.block.invisible();
                //                var command = self.parentData().canSelectWorkTypeCodes.length > 0 ? self.parentData().canSelectWorkTypeCodes.split(',') : null)
                //                              , self.getTimeDayAtrEnum();
                
                //find all worktype
                $.when(service.findAllWorkType()).done(
                    function(workTypeData: Array<WorkType>) {
                        self.lstWorkType(workTypeData);
                    }
                )
                
                service.findByCodeList(self.parentData().canSelectSiftCodes.split(','))
                    .done(function(data) {
                        self.rootList = data;
                        //add item　なし
                        data.unshift({
                            code: "000",
                            name: "なし",
                            workTime1: "",
                            workTime2: "",
                            workAtr: "",
                            remark: ""
                        });
                        self.selectAbleItemList(_.clone(self.rootList));
                        if (!nts.uk.util.isNullOrEmpty(self.selectAbleItemList())) {
                            if (nts.uk.util.isNullOrEmpty(self.selectedCodeList())) {
                                self.selectedCodeList([_.first(self.selectAbleItemList()).code]);
                                self.selectedSiftCode(_.first(self.selectAbleItemList()).code);
                            } else {
                                self.selectedSiftCode(_.first(self.selectedCodeList()));
                            }
                        } else {
                            self.selectedCodeList([]);
                            self.selectedSiftCode(null);
                        }
                        nts.uk.ui.block.clear();
                        dfd.resolve();
                    })
                    .fail(function(res) {
                        nts.uk.ui.dialog.alertError({ messageId: res.messageId }).then(function() { nts.uk.ui.block.clear(); });
                    });
                return dfd.promise();
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
                    codelist: _.map(self.rootList, function(item) { return item.code }),
                    startAtr: self.startTimeOption(),
                    startTime: nts.uk.util.isNullOrEmpty(self.startTime()) ? -1 : self.startTime(),
                    endAtr: self.endTimeOption(),
                    endTime: nts.uk.util.isNullOrEmpty(self.endTime()) ? -1 : self.endTime()
                }
                kdl001.a.service.findByTime(command)
                    .done(function(data) {
                        self.selectAbleItemList(data);
                        if (!nts.uk.util.isNullOrEmpty(self.selectAbleItemList())) {
                            self.selectedCodeList([_.first(self.selectAbleItemList()).code]);
                            self.selectedSiftCode(_.first(self.selectAbleItemList()).code);
                        } else {
                            self.selectedCodeList([]);
                            self.selectedSiftCode(null);
                        }
                        nts.uk.ui.block.clear();
                    })
                    .fail(function(res) {
                        nts.uk.ui.dialog.alertError({ messageId: res.messageId }).then(function() { nts.uk.ui.block.clear(); });
                    });
            }

            private returnData() {
                nts.uk.ui.block.invisible();
                var self = this;
                self.startTimeOption(1);
                self.startTime('');
                self.endTimeOption(1);
                self.endTime('');
                kdl001.a.service.findByCodeList(self.selectAbleCodeList())
                    .done(function(data) {
                        self.rootList = data;
                        self.selectAbleItemList(_.clone(self.rootList));
                        if (!nts.uk.util.isNullOrEmpty(self.selectAbleItemList())) {
                            if (nts.uk.util.isNullOrEmpty(self.selectedCodeList())) {
                                self.selectedCodeList([_.first(self.selectAbleItemList()).code]);
                            }
                            self.selectedSiftCode(_.first(self.selectAbleItemList()).code);
                        } else {
                            self.selectedCodeList([]);
                            self.selectedSiftCode(null);
                        }
                        nts.uk.ui.block.clear();
                    })
                    .fail(function(res) {
                        nts.uk.ui.dialog.alertError({ messageId: res.messageId }).then(function() { nts.uk.ui.block.clear(); });
                    });
                $("#inputStartTime").focus();
            }

            private bindRestTime(data: any) {
                var self = this;
                let listHalfDay1 = [];
                let listHalfDay2 = [];
                let listHoliday1 = [];
                let listHoliday2 = [];
                data.halfDayAttendanceWorkTimeZone.breakTimeZone.lstTimeTable.forEach(function(item, index) {
                    item = self.convertItemForview(item);
                    if (index < 5) {
                        listHalfDay1.push(item);
                    } else {
                        listHalfDay2.push(item);
                    }
                });
                data.holidayAttendanceWorkTimeZone.breakTimeZone.lstTimeTable.forEach(function(item, index) {
                    item = self.convertItemForview(item);
                    if (index < 5) {
                        listHoliday1.push(item);
                    } else {
                        listHoliday2.push(item);
                    }
                });
                self.halfDayList1(listHalfDay1);
                self.halfDayList2(listHalfDay2);
                self.holidayList1(listHoliday1);
                self.holidayList2(listHoliday2);
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
                var self = this;
                self.getWorkTypeName(self.selectedWorkTypeCode());
                self.getSiftName(self.selectedSiftCode());
                var sendData = {
                    selectedWorkTimeCode: self.selectedWorkTypeCode(),
                    selectedWorkTimeName: self.selectedWorkTypeName(),
                    selectedSiftCode: self.selectedSiftCode(),
                    selectedSiftName: self.selectedSiftName()
                };
                nts.uk.ui.windows.setShared("childData", sendData, true);
                nts.uk.ui.block.clear();
                nts.uk.ui.windows.close();
            }

            private getWorkTypeName(code): string {
                var self = this;
                if (self.lstWorkType()) {
                    self.lstWorkType().forEach((item, index) => {
                        if (item.workTypeCode == code) {
                            self.selectedWorkTypeName(item.name);
                        }
                    });
                }
            }

            private getSiftName(siftCode): string {
                var self = this;
                if (self.selectAbleItemList()) {
                    self.selectAbleItemList().forEach((item, index) => {
                        if (item.code == siftCode) {
                            self.selectedSiftName(item.name);
                        }
                    });
                }
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
    }