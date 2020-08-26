/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.kaf008_ref.shr.viewmodel {
    import BusinessTripInfoOutput = nts.uk.at.view.kaf008_ref.a.viewmodel.BusinessTripInfoOutput;

    @component({
        name: 'kaf008-share',
        template: '/nts.uk.at.web/view/kaf_ref/008/shr/index.html'
    })
    class Kaf008ShareViewModel extends ko.ViewModel {

        departureTime: KnockoutObservable<number> = ko.observable(null);
        returnTime: KnockoutObservable<number> = ko.observable(null);
        comment: KnockoutObservable<Comment> = ko.observable(null);
        items: KnockoutObservableArray<TripContentDisp> = ko.observableArray([]);
        model: KnockoutObservable<any>;
        businessTripOutput: KnockoutObservable<BusinessTripInfoOutput>;
        workTypeCds: KnockoutObservableArray<string> = ko.observableArray([]);
        holidayTypeCds: KnockoutObservableArray<string> = ko.observableArray([]);
        tripDetail: KnockoutObservable<TripDetail> = ko.observable(null);
        mode: number = Mode.New;

        created(params: any) {
            const vm = this;

            vm.businessTripOutput = params.businessTripOutput;
            vm.model = params.businessTripContent;
            if (params.mode) {
                vm.mode = params.mode;
            }
            if (vm.mode == Mode.New) {
                vm.startNewMode();
            } else {
                vm.startEditMode();
            }
        }

        startNewMode() {
            const vm = this;
            vm.businessTripOutput.subscribe(value => {
                if (value) {
                    const appSet = value.setting.appCommentSet;
                    vm.workTypeCds(value.workdays);
                    vm.holidayTypeCds(value.holidays);
                    vm.comment({
                        comment: appSet.comment,
                        colorCode: appSet.colorCode,
                        bold: appSet.bold == 1 ? true : false
                    });

                    let actualContent = _.map(value.businessTripActualContent, function (content, index) {
                        let contentTrip = new TripContentDisp(
                            content.date,
                            content.opAchievementDetail.workTypeCD,
                            content.opAchievementDetail.opWorkTypeName,
                            content.opAchievementDetail.workTimeCD,
                            content.opAchievementDetail.opWorkTimeName,
                            content.opAchievementDetail.opWorkTime,
                            content.opAchievementDetail.opLeaveTime
                        );
                        contentTrip.wkTypeCd.subscribe(code => {
                            vm.changeWorkTypeCode(vm.businessTripOutput(), content.date, code, index);
                        });
                        contentTrip.wkTimeCd.subscribe(code => {
                            vm.changeWorkTimeCode(vm.businessTripOutput(), content.date, content.opAchievementDetail.workTypeCD, code, index);
                        });
                        return contentTrip;
                    });
                    vm.items(actualContent);
                }
                ;
            });
        }

        startEditMode() {
            const vm = this;

            vm.businessTripOutput.subscribe(value => {
                if (value) {
                    let setting = value.setting.appCommentSet;
                    let wkTimeSet = value.appDispInfoStartup.appDispInfoWithDateOutput.opWorkTimeLst;
                    let wkDaySet = value.workdays;
                    vm.comment({
                        comment: setting.comment,
                        colorCode: setting.colorCode,
                        bold: setting.bold == 1 ? true : false
                    });
                    let contents = _.clone(vm.model());
                    let start = contents.departureTime;
                    let end = contents.returnTime;
                    vm.departureTime(start);
                    vm.returnTime(end);
                    let lstContent = _.map(contents.tripInfos, function (data, index) {
                        let date = data.date;
                        let wkTypeCode = data.wkTypeCd;
                        let wkTimeCode = data.wkTimeCd;
                        let statWork = data.startWorkTime;
                        let endWork = data.endWorkTime;
                        let wkTimeName = data.wkTimeName;
                        let wkTypeName = data.wkTypeName;

                        if (!wkTimeName) {
                            let wkTimeInfo = _.filter(ko.toJS(wkTimeSet), function (item) {
                                return item.worktimeCode == wkTimeCode
                            });
                            if (wkTimeInfo.length != 0) {
                                wkTimeName = wkTimeInfo[0].workTimeDisplayName.workTimeName;
                            }
                        }

                        if (!wkTypeName) {
                            let wkDayInfo = _.filter(ko.toJS(wkDaySet), function (item) {
                                return item.workTypeCode == wkTypeCode
                            });
                            if (wkDayInfo.length != 0) {
                                wkTypeName = wkDayInfo[0].name;
                            }
                        }

                        let convertDisp = new TripContentDisp(
                            date,
                            wkTypeCode,
                            wkTypeName,
                            wkTimeCode,
                            wkTimeName,
                            statWork,
                            endWork
                        );
                        convertDisp.wkTypeCd.subscribe(value => {
                            vm.changeTypeCodeScreenB(vm.businessTripOutput(), data.date, value, index);
                        });
                        convertDisp.wkTimeCd.subscribe(value => {
                            vm.changeWorkTimeCodeScreenB(vm.businessTripOutput(), data.date, data.wkTypeCd, value, index)
                        })
                        return convertDisp;
                    });
                    vm.items(lstContent);
                }
            });
        }

        mounted() {
            const vm = this;

            $("#fixed-table").ntsFixedTable({});

            vm.departureTime.subscribe(value => {
                if (vm.mode == Mode.New) {
                    vm.model().departureTime(value);
                } else {
                    vm.departureTime(value);
                }

            });

            vm.returnTime.subscribe(value => {
                if (vm.mode == Mode.New) {
                    vm.model().returnTime(value);
                } else {
                    vm.returnTime(value);
                }
            });
        }

        changeWorkTypeCode(data: BusinessTripInfoOutput, date: string, wkCode: string, index: number) {
            const vm = this;
            let businessTripInfoOutputDto = ko.toJS(data);
            let command = {
                date, businessTripInfoOutputDto, wkCode
            };
            let cloneOutput = _.clone(vm.businessTripOutput());
            let contentChanged = cloneOutput.businessTripActualContent[index].opAchievementDetail;

            vm.$validate([
                '#kaf008-share #A10_D2',
                '#kaf008-share #A10_D4'
            ]).then((valid: boolean) => {
                if (valid) {
                    return vm.$ajax(API.changeWorkTypeCode, command);
                }
            }).done(data => {
                if (data) {
                    vm.businessTripOutput(data);

                    let workTypeAfterChange = data.infoAfterChange;
                    let InfoChanged = _.findIndex(workTypeAfterChange, {date: date});
                    let workCodeChanged = workTypeAfterChange[InfoChanged].workTypeDto.workTypeCode;
                    let workNameChanged = workTypeAfterChange[InfoChanged].workTypeDto.name;

                    contentChanged.workTypeCD = workCodeChanged;
                    contentChanged.opWorkTypeName = workNameChanged;
                    vm.businessTripOutput(cloneOutput);
                }
            }).fail(err => {
                contentChanged.workTypeCD = "";
                contentChanged.opWorkTypeName = "なし";
                vm.businessTripOutput(cloneOutput);
                let param;
                if (err.message && err.messageId) {
                    param = {messageId: err.messageId};
                } else {
                    if (err.message) {
                        param = {message: err.message};
                    } else {
                        param = {messageId: err.messageId};
                    }
                }
                vm.$dialog.error(param);
            }).always(() => vm.$blockui("hide"));
        }

        changeWorkTimeCode(data: BusinessTripInfoOutput, date: string, wkCode: string, timeCode: string, index: number) {
            const vm = this;
            let businessTripInfoOutputDto = ko.toJS(data);
            let cloneOutput = _.clone(vm.businessTripOutput());
            let contentChanged = cloneOutput.businessTripActualContent[index].opAchievementDetail;
            let command = {
                date, businessTripInfoOutputDto, wkCode, timeCode
            };
            vm.$validate([
                '#kaf008-share #A10_D2',
                '#kaf008-share #A10_D4'
            ]).then((valid: boolean) => {
                if (valid) {
                    return vm.$ajax(API.changWorkTimeCode, command);
                }
            }).done(data => {
                contentChanged.workTimeCD = timeCode;
                if (data) {
                    contentChanged.opWorkTimeName = data.name;
                } else {
                    contentChanged.opWorkTimeName = "なし";
                }
                vm.businessTripOutput(cloneOutput);
            }).fail(err => {
                contentChanged.workTimeCD = timeCode;
                contentChanged.opWorkTimeName = "なし";
                vm.businessTripOutput(cloneOutput);
                let param;
                if (err.message && err.messageId) {
                    param = {messageId: err.messageId};
                } else {
                    if (err.message) {
                        param = {message: err.message};
                    } else {
                        param = {messageId: err.messageId};
                    }
                }
                ;
                vm.$dialog.error(param);
            }).always(() => vm.$blockui("hide"));
        }

        changeTypeCodeScreenB(data: BusinessTripInfoOutput, date: string, wkCode: string, index: number) {
            const vm = this;
            let businessTripInfoOutputDto = ko.toJS(data);
            let command = {
                date, businessTripInfoOutputDto, typeCode: wkCode, timeCode: null
            };
            let cloneModel = _.clone(vm.model());
            let contentChanged = cloneModel.tripInfos[index];

            vm.$validate([
                '#kaf008-share #A10_D2',
                '#kaf008-share #A10_D4'
            ]).then((valid: boolean) => {
                if (valid) {
                    return vm.$ajax(API.changeWorkTypeCode, command);
                }
            }).done(data => {
                if (data) {
                    let workTypeAfterChange = data.infoAfterChange;
                    let InfoChanged = _.findIndex(workTypeAfterChange, {date: date});
                    let workCodeChanged = workTypeAfterChange[InfoChanged].workTypeDto.workTypeCode;
                    let workNameChanged = workTypeAfterChange[InfoChanged].workTypeDto.name;

                    contentChanged.wkTypeCd = workCodeChanged;
                    contentChanged.wkTypeName = workNameChanged;
                    vm.model(cloneModel);
                    vm.businessTripOutput(data);
                }
            }).fail(err => {
                let param;

                contentChanged.wkTypeCd = "";
                contentChanged.wkTypeName = "なし";
                vm.model(cloneModel);
                vm.businessTripOutput(businessTripInfoOutputDto);

                if (err.message && err.messageId) {
                    param = {messageId: err.messageId};
                } else {
                    if (err.message) {
                        param = {message: err.message};
                    } else {
                        param = {messageId: err.messageId};
                    }
                }
                vm.$dialog.error(param);
            }).always(() => vm.$blockui("hide"));
        }

        changeWorkTimeCodeScreenB(data: BusinessTripInfoOutput, date: string, wkCode: string, timeCode: string, index: number) {
            const vm = this;
            let businessTripInfoOutputDto = ko.toJS(data);
            let cloneModel = _.clone(vm.model());
            let contentChanged = cloneModel.tripInfos[index];
            let command = {
                date, businessTripInfoOutputDto, wkCode, timeCode
            };
            vm.$validate([
                '#kaf008-share #A10_D2',
                '#kaf008-share #A10_D4'
            ]).then((valid: boolean) => {
                if (valid) {
                    return vm.$ajax(API.changWorkTimeCode, command);
                }
            }).done(data => {
                contentChanged.workTimeCD = timeCode;
                if (data) {
                    contentChanged.opWorkTimeName = data.name;
                } else {
                    contentChanged.opWorkTimeName = "なし";
                }
                vm.model(cloneModel);
                vm.businessTripOutput(data);
            }).fail(err => {
                let param;

                contentChanged.wkTimeCd = "";
                contentChanged.wkTimeName = "なし";
                vm.model(cloneModel);
                vm.businessTripOutput(businessTripInfoOutputDto);

                if (err.message && err.messageId) {
                    param = {messageId: err.messageId};
                } else {
                    if (err.message) {
                        param = {message: err.message};
                    } else {
                        param = {messageId: err.messageId};
                    }
                }
                ;
                vm.$dialog.error(param);
            }).always(() => vm.$blockui("hide"));
        }

        openDialogKdl003(data: TripContentDisp) {
            const vm = this;
            let workTypeCodes = data.wkTypeCd();
            let workTimeCodes = data.wkTimeCd();
            let selectedIndex = _.findIndex(ko.toJS(vm.items), {date: data.date});
            let listWorkCode = _.map(vm.workTypeCds(), function (obj) {
                return obj.workTypeCode
            });
            let listHolidayCode = _.map(vm.holidayTypeCds(), function (obj) {
                return obj.holidayTypeCds
            });
            let listWkTime = vm.businessTripOutput().appDispInfoStartup.appDispInfoWithDateOutput.opWorkTimeLst;
            let listWkTimeCd = _.map(listWkTime, function (obj) {
                return obj.worktimeCode
            });
            let cloneOutput = _.clone(vm.businessTripOutput());

            vm.$window.storage('parentCodes', {
                workTypeCodes: _.merge(listWorkCode, listHolidayCode),
                selectedWorkTypeCode: workTypeCodes,
                workTimeCodes: listWkTimeCd,
                selectedWorkTimeCode: workTimeCodes
            });

            vm.$window.modal('/view/kdl/003/a/index.xhtml').then((result: any) => {
                vm.$window.storage('childData').then(rs => {
                    // const startTime = nts.uk.time.format.byId("Clock_Short_HM", rs.first.start);
                    // const endTime = nts.uk.time.format.byId("Clock_Short_HM", rs.first.end);
                    if (vm.mode == Mode.New) {

                        let currentDetail = cloneOutput.businessTripActualContent[selectedIndex].opAchievementDetail;
                        currentDetail.workTypeCD = rs.selectedWorkTypeCode;
                        currentDetail.opWorkTypeName = rs.selectedWorkTypeName;
                        currentDetail.workTimeCD = rs.selectedWorkTimeCode;
                        currentDetail.opWorkTimeName = rs.selectedWorkTimeName;
                        currentDetail.opWorkTime = rs.first.start;
                        currentDetail.opLeaveTime = rs.first.end;
                        vm.businessTripOutput(cloneOutput);
                    } else {
                        let cloneModel = _.clone(vm.model());
                        let contentChanged = cloneModel.tripInfos[selectedIndex];
                        contentChanged.wkTypeCd = rs.selectedWorkTypeCode;
                        contentChanged.wkTypeName = rs.selectedWorkTypeName;
                        contentChanged.wkTimeCd = rs.selectedWorkTimeCode;
                        contentChanged.wkTimeName = rs.selectedWorkTimeName;
                        contentChanged.startWorkTime = rs.first.start;
                        contentChanged.endWorkTime = rs.first.end;
                        vm.model(cloneModel);
                        vm.businessTripOutput(cloneOutput)
                    }
                });
            });

        }

    }

    interface Comment {
        comment: string;
        colorCode: string;
        bold: boolean;
    }

    interface EachDetail {
        date: string;
        opAchievementDetail: EachContentDetail;
    }

    interface EachContentDetail {
        workTypeCD: KnockoutObservable<string>;
        workTimeCD: KnockoutObservable<string>;
        opWorkTypeName: KnockoutObservable<string>;
        opWorkTimeName: KnockoutObservable<string>;
        opWorkTime: KnockoutObservable<number>;
        opLeaveTime: KnockoutObservable<number>;
    }

    interface TripDetail {
        date: KnockoutObservable<number>;
        wkTypeCd: KnockoutObservable<string>;
        wkTimeCd: KnockoutObservable<string>;
        startWorkTime: KnockoutObservable<number>;
        endWorkTime: KnockoutObservable<number>;
        wkTypeName: KnockoutObservable<string>;
        wkTimeName: KnockoutObservable<string>
    }

    export class TripContentDisp {
        date: string;
        dateDisp: string;
        dateColor: string = "initial";
        wkTypeCd: KnockoutObservable<string>;
        wkTypeName: KnockoutObservable<string>;
        wkTimeCd: KnockoutObservable<string>;
        wkTimeName: KnockoutObservable<string>;
        start: KnockoutObservable<number>;
        end: KnockoutObservable<number>;

        constructor(date: string, wkTypeCd: string, wkTypeName: string, wkTimeCd: string, wkTimeName: string, start: number, end: number) {
            this.date = date;
            //  moment(date, "YYYY/MM/DD").format('YYYY-MM-DD(ddd)');
            this.dateDisp = nts.uk.time.applyFormat("Short_YMDW", [date])
            let day = moment(moment.utc(date, "YYYY/MM/DD").toISOString()).format('dddd');
            if (day == "土曜日") {
                this.dateColor = "#0000FF";
            }
            if (day == "日曜日") {
                this.dateColor = "#FF0000";
            }
            this.wkTypeCd = ko.observable(wkTypeCd);
            this.wkTypeName = ko.observable(wkTypeName);
            this.wkTimeCd = ko.observable(wkTimeCd);
            this.wkTimeName = ko.observable(wkTimeName);
            var startTime = nts.uk.time.format.byId("ClockDay_Short_HM", start);
            var endTime = nts.uk.time.format.byId("ClockDay_Short_HM", end);
            this.start = ko.observable(startTime);
            this.end = ko.observable(endTime);
        }
    }

    export interface BusinessTripInfoDetail {
        date: string;
        wkTypeCd: string;
        wkTimeCd: string;
        startWorkTime: number;
        endWorkTime: number;
    }

    const API = {
        changeWorkTypeCode: "at/request/application/businesstrip/changeWorkTypeCode",
        changWorkTimeCode: "at/request/application/businesstrip/changeWorkTimeCode"
    }

    const Mode = {
        New: 1,
        Edit: 2
    };

}