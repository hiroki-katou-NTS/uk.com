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
        workTypeCds: KnockoutObservableArray<string> = ko.observableArray([]);
        holidayTypeCds: KnockoutObservableArray<string> = ko.observableArray([]);
        businessTripContent: KnockoutObservable<any> = ko.observable(null);
        businessTripOutput: KnockoutObservable<BusinessTripInfoOutput> = ko.observable(null);
        dataFetch: KnockoutObservable<any> = ko.observable(null);
        mode: number = Mode.New;
        enableInput: boolean = true;

        created(params: any) {
            const vm = this;
            vm.dataFetch = params.dataFetch;
            
            if (params.mode) {
                vm.mode = params.mode;
            }

            switch (params.mode) {
                case Mode.New:
                    vm.startNewMode();
                    break;
                case Mode.Edit:
                    vm.startEditMode();
                    break;
                case Mode.View:
                    vm.startEditMode();
                    vm.enableInput = false;
                    break;
            }
        }

        startNewMode() {
            const vm = this;
            vm.dataFetch.subscribe(value => {
                if (value) {

                    const tripOutput = value.businessTripOutput;
                    const tripContent = value.businessTripContent;
                    const setting = tripOutput.setting;

                    vm.businessTripOutput(tripOutput);
                    vm.workTypeCds(tripOutput.workdays);
                    vm.holidayTypeCds(tripOutput.holidays);

                    if (setting && setting.appCommentSet) {
                        vm.comment({
                            comment: setting.appCommentSet.comment,
                            colorCode: setting.appCommentSet.colorCode,
                            bold: setting.appCommentSet.bold == 1 ? true : false
                        });
                    }

                    let lstContent = _.map(tripOutput.businessTripActualContent, function (content, index) {
                        let eachContent = new TripContentDisp(
                            content.date,
                            content.opAchievementDetail.workTypeCD,
                            content.opAchievementDetail.opWorkTypeName,
                            content.opAchievementDetail.workTimeCD,
                            content.opAchievementDetail.opWorkTimeName,
                            content.opAchievementDetail.opWorkTime,
                            content.opAchievementDetail.opLeaveTime
                        );
                        eachContent.wkTypeCd.subscribe(code => {
                            vm.changeWorkTypeCode(tripOutput, content.date, code, index);
                        });
                        eachContent.wkTimeCd.subscribe(code => {
                            vm.changeWorkTimeCode(tripOutput, content.date, content.opAchievementDetail.workTypeCD, code, index);
                        });
                        eachContent.start.subscribe(startValue => {
                            content.opAchievementDetail.opWorkTime = startValue;
                        });
                        eachContent.end.subscribe(endValue => {
                            content.opAchievementDetail.opLeaveTime = endValue;
                        });
                        return eachContent;
                    });
                    vm.items(lstContent);
                    }
                ;
            });
        }

        startEditMode() {
            const vm = this;
            vm.dataFetch.subscribe(value => {
                if(value) {
                    const tripOutput = value.businessTripOutput;
                    const tripContent = value.businessTripContent;
                    const setting = tripOutput.setting;

                    vm.businessTripOutput(tripOutput);
                    vm.workTypeCds(tripOutput.workdays);
                    vm.holidayTypeCds(tripOutput.holidays);
                    vm.departureTime(tripContent.departureTime);
                    vm.returnTime(tripContent.returnTime);

                    if (setting && setting.appCommentSet) {
                        vm.comment({
                            comment: setting.appCommentSet.comment,
                            colorCode: setting.appCommentSet.colorCode,
                            bold: setting.appCommentSet.bold == 1 ? true : false
                        });
                    }

                    const contentDisp = _.map(tripContent.tripInfos, function (data, index) {

                        let contentTrip = new TripContentDisp(
                            data.date,
                            data.wkTypeCd,
                            data.wkTypeName,
                            data.wkTimeCd,
                            data.wkTimeName,
                            data.startWorkTime,
                            data.endWorkTime
                        );

                        contentTrip.wkTypeCd.subscribe(code => {
                            vm.changeTypeCodeScreenB(tripOutput, data, code, index);
                        });
                        contentTrip.wkTimeCd.subscribe(code => {
                            vm.changeWorkTimeCodeScreenB(tripOutput, data, code, index);
                        });
                        contentTrip.start.subscribe(startValue => {
                            data.startWorkTime = startValue;
                        });
                        contentTrip.end.subscribe(endValue => {
                            data.endWorkTime = endValue;
                        });
                        return contentTrip;
                    });
                    vm.items(contentDisp);
                }
            });
        }

        mounted() {
            const vm = this;

            $("#fixed-table").ntsFixedTable({});

            vm.departureTime.subscribe(value => {
                if (value) {
                  vm.dataFetch().businessTripContent.departureTime = value;
                };
            });

            vm.returnTime.subscribe(value => {
                if (value) {
                    vm.dataFetch().businessTripContent.returnTime = value;
                };
            });
        }

        changeWorkTypeCode(data: BusinessTripOutput, date: string, wkCode: string, index: number) {
            const vm = this;
            let businessTripInfoOutputDto = ko.toJS(data);
            let command = {
                date: date,
                businessTripInfoOutputDto: businessTripInfoOutputDto,
                typeCode: wkCode,
                timeCode: null
            };
            let cloneData = _.clone(vm.dataFetch());
            let contentChanged = cloneData.businessTripOutput.businessTripActualContent[index].opAchievementDetail;

            vm.$validate([
                '#kaf008-share #A10_D2',
                '#kaf008-share #A10_D4'
            ]).then((valid: boolean) => {
                if (valid) {
                    return vm.$ajax(API.changeWorkTypeCode, command);
                }
            }).done(res => {
                if (res) {
                    let workTypeAfterChange = res.infoAfterChange;
                    let InfoChanged = _.findIndex(workTypeAfterChange, {date: date});
                    let workCodeChanged = workTypeAfterChange[InfoChanged].workTypeDto.workTypeCode;
                    let workNameChanged = workTypeAfterChange[InfoChanged].workTypeDto.name;

                    contentChanged.workTypeCD = workCodeChanged;
                    contentChanged.opWorkTypeName = workNameChanged;
                    vm.dataFetch(cloneData);
                }
            }).fail(err => {
                contentChanged.workTypeCD = "";
                contentChanged.opWorkTypeName = "なし";

                vm.dataFetch(cloneData);

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

        changeWorkTimeCode(data: BusinessTripOutput, date: string, wkCode: string, timeCode: string, index: number) {
            const vm = this;
            let businessTripInfoOutputDto = ko.toJS(data);
            let cloneData = _.clone(vm.dataFetch());
            let contentChanged = cloneData.businessTripOutput.businessTripActualContent[index].opAchievementDetail;
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
            }).done(res => {
                if (res) {
                    contentChanged.workTimeCD = timeCode;
                    if (res.name) {
                        contentChanged.opWorkTimeName = res.name;
                    } else {
                        contentChanged.opWorkTimeName = "なし";
                    }
                    vm.dataFetch(cloneData);
                }
            }).fail(err => {
                contentChanged.workTimeCD = timeCode;
                contentChanged.opWorkTimeName = "なし";

                vm.dataFetch(cloneData);

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

        changeTypeCodeScreenB(data: BusinessTripOutput, content: any, codeChanged: string, index: number) {
            const vm = this;
            let businessTripInfoOutputDto = ko.toJS(data);
            let cloneData = _.clone(vm.dataFetch());
            let contentChanged = cloneData.businessTripContent.tripInfos[index];
            let command = {
                date: content.date,
                businessTripInfoOutputDto: businessTripInfoOutputDto,
                typeCode: codeChanged,
                timeCode: null
            };

            vm.$validate([
                '#kaf008-share #A10_D2',
                '#kaf008-share #A10_D4'
            ]).then((valid: boolean) => {
                if (valid) {
                    return vm.$ajax(API.changeWorkTypeCode, command);
                }
            }).done(res => {
                if (res) {
                    let workTypeAfterChange = res.infoAfterChange;
                    let InfoChanged = _.findIndex(workTypeAfterChange, {date: content.date});
                    let workCodeChanged = workTypeAfterChange[InfoChanged].workTypeDto.workTypeCode;
                    let workNameChanged = workTypeAfterChange[InfoChanged].workTypeDto.name;

                    contentChanged.wkTypeCd = workCodeChanged;
                    contentChanged.wkTypeName = workNameChanged;
                    vm.dataFetch(cloneData);
                }
            }).fail(err => {
                let param;

                contentChanged.wkTypeCd = "";
                contentChanged.wkTypeName = "なし";
                vm.dataFetch(cloneData);

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

        changeWorkTimeCodeScreenB(output: BusinessTripOutput, data: any, codeChanged: string, index: number) {
            const vm = this;
            let businessTripInfoOutputDto = ko.toJS(output);
            let cloneData = _.clone(vm.dataFetch());
            let contentChanged = cloneData.businessTripContent.tripInfos[index];
            let command = {
                    date: data.date,
                    businessTripInfoOutputDto: businessTripInfoOutputDto,
                    wkCode: data.wkTypeCd,
                    timeCode: codeChanged
            };

            vm.$validate([
                '#kaf008-share #A10_D2',
                '#kaf008-share #A10_D4'
            ]).then((valid: boolean) => {
                if (valid) {
                    return vm.$ajax(API.changWorkTimeCode, command);
                }
            }).done(res => {
                if (res) {
                    if(res.name) {
                        contentChanged.wkTimeCd = codeChanged;
                        contentChanged.wkTimeName = res.name;
                    } else {
                        contentChanged.wkTimeCd = "";
                        contentChanged.wkTimeName = "なし";
                    }
                    vm.dataFetch(cloneData);
                }
            }).fail(err => {
                let param;
                contentChanged.wkTimeCd = "";
                contentChanged.wkTimeName = "なし";
                vm.dataFetch(cloneData);
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

        openDialogKdl003(data: TripContentDisp) {
            const vm = this;
            let dispFlag: boolean = true;
            let selectedDate = data.date;
            let workTypeCodes = data.wkTypeCd();
            let workTimeCodes = data.wkTimeCd();

            let selectedIndex = _.findIndex(ko.toJS(vm.items), {date: data.date});

            let listWorkCode = _.map(vm.workTypeCds(), function (obj) {
                return obj.workTypeCode
            });

            let listHolidayCode = _.map(vm.holidayTypeCds(), function (obj) {
                return obj.workTypeCode
            });

            let listWkTime = vm.businessTripOutput().appDispInfoStartup.appDispInfoWithDateOutput.opWorkTimeLst;
            let listWkTimeCd = _.map(listWkTime, function (obj) {
                return obj.worktimeCode
            });


            let command = {
                selectedDate: selectedDate,
                businessTripInfoOutputDto: ko.toJS(vm.businessTripOutput)
            }

            vm.$ajax(API.startKDL003, command).then(res => {
                if(res) {
                    dispFlag = res;
                }
            });

            vm.$window.storage('parentCodes', {
                workTypeCodes: _.union(listHolidayCode, listWorkCode),
                selectedWorkTypeCode: workTypeCodes,
                workTimeCodes: listWkTimeCd,
                selectedWorkTimeCode: workTimeCodes,
                showNone: dispFlag
            });

            vm.$window.modal('/view/kdl/003/a/index.xhtml').then((result: any) => {
                vm.$window.storage('childData').then(rs => {
                    if (vm.mode == Mode.New) {
                        let cloneData = _.clone(vm.dataFetch());
                        let currentDetail = cloneData.businessTripOutput.businessTripActualContent[selectedIndex].opAchievementDetail;

                        currentDetail.workTypeCD = rs.selectedWorkTypeCode;
                        currentDetail.opWorkTypeName = rs.selectedWorkTypeName;
                        currentDetail.workTimeCD = rs.selectedWorkTimeCode;
                        currentDetail.opWorkTimeName = rs.selectedWorkTimeName;
                        currentDetail.opWorkTime = rs.first.start;
                        currentDetail.opLeaveTime = rs.first.end;

                        vm.dataFetch(cloneData);
                    } else {
                        let cloneData = _.clone(vm.dataFetch());
                        let contentChanged = cloneData.businessTripContent.tripInfos[selectedIndex];

                        contentChanged.wkTypeCd = rs.selectedWorkTypeCode;
                        contentChanged.wkTypeName = rs.selectedWorkTypeName;
                        contentChanged.wkTimeCd = rs.selectedWorkTimeCode;
                        contentChanged.wkTimeName = rs.selectedWorkTimeName;
                        contentChanged.startWorkTime = rs.first.start;
                        contentChanged.endWorkTime = rs.first.end;

                        vm.dataFetch(cloneData);
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

    export interface BusinessTripOutput {
        setting: Setting;
        appDispInfoStartup: any;
        holidays: any;
        workdays: any;
        businessTripActualContent: any;
        infoBeforeChange: any;
        infoAfterChange: any;
    }

    export interface BusinessTripContent {
        departureTime: number;
        returnTime: number;
        tripInfos: Array<any>;
    }

    export interface TripInfoDetail {
        date: string;
        wkTimeCd: string;
        wkTimeName: string;
        wkTypeCd: string;
        wkTypeName: string;
        startWorkTime: number;
        endWorkTime: number;
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
            this.dateDisp = nts.uk.time.applyFormat("Short_YMDW", [date]);
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
            this.wkTimeName = ko.observable(wkTimeName || "なし");
            this.start = ko.observable(start);
            this.end = ko.observable(end);
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
        changWorkTimeCode: "at/request/application/businesstrip/changeWorkTimeCode",
        startKDL003: "at/request/application/businesstrip/startKDL003"
    }

    export const Mode = {
        New: 1,
        Edit: 2,
        View: 3
    };

}