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
            vm.mode = params.mode;

            vm.departureTime = vm.dataFetch().businessTripContent.departureTime;
            vm.returnTime = vm.dataFetch().businessTripContent.returnTime;

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
                    const { setting } = tripOutput ;

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
                            vm.$errors("clear").then(() =>{
                                vm.changeWorkTypeCode(tripOutput, content.date, code, index);
                            });
                        });
                        eachContent.wkTimeCd.subscribe(code => {
                            vm.$errors("clear").then(() =>{
                                vm.changeWorkTimeCode(tripOutput, content.date, content.opAchievementDetail.workTypeCD, code, index);
                            });
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
                    vm.departureTime(tripContent.departureTime());
                    vm.returnTime(tripContent.returnTime());

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
                            vm.$errors("clear").then(() =>{
                                vm.changeTypeCodeScreenB(tripOutput, data, code, index);
                            });
                        });
                        contentTrip.wkTimeCd.subscribe(code => {
                            vm.$errors("clear").then(() =>{
                                vm.changeWorkTimeCodeScreenB(tripOutput, data, code, index);
                            });
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
            let currentRow = vm.dataFetch().businessTripOutput.businessTripActualContent[index].opAchievementDetail;

            vm.$blockui("show");
            vm.$validate([
                '#kaf008-share #A10_D2',
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

                    currentRow.workTypeCD = workCodeChanged;
                    currentRow.opWorkTypeName = workNameChanged;

                    vm.dataFetch.valueHasMutated();
                }
            }).fail(err => {
                currentRow.workTypeCD = "";
                currentRow.opWorkTypeName = "なし";

                vm.dataFetch.valueHasMutated();

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
            let currentRow = vm.dataFetch().businessTripOutput.businessTripActualContent[index].opAchievementDetail;
            let command = {
                date, businessTripInfoOutputDto, wkCode, timeCode
            };
            vm.$blockui("show");
            vm.$validate([
                '#kaf008-share #A10_D4'
            ]).then((valid: boolean) => {
                if (valid) {
                    return vm.$ajax(API.changWorkTimeCode, command);
                }
            }).done(res => {
                if (res) {
                    currentRow.workTimeCD = timeCode;

                    if (res.name) {
                        currentRow.opWorkTimeName = res.name;
                    } else {
                        currentRow.opWorkTimeName = "なし";
                    }

                    vm.dataFetch.valueHasMutated();
                }
            }).fail(err => {
                currentRow.workTimeCD = "";
                currentRow.opWorkTimeName = "なし";

                vm.dataFetch.valueHasMutated();

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

        changeTypeCodeScreenB(data: BusinessTripOutput, content: any, codeChanged: string, index: number) {
            const vm = this;
            let businessTripInfoOutputDto = ko.toJS(data);
            let currentRow = vm.dataFetch().businessTripContent.tripInfos[index];
            let command = {
                date: content.date,
                businessTripInfoOutputDto: businessTripInfoOutputDto,
                typeCode: codeChanged,
                timeCode: null
            };

            vm.$blockui("show");
            vm.$validate([
                '#kaf008-share #A10_D2'
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

                    currentRow.wkTypeCd = workCodeChanged;
                    currentRow.wkTypeName = workNameChanged;
                    vm.dataFetch.valueHasMutated();
                }
            }).fail(err => {
                let param;

                currentRow.wkTypeCd = "";
                currentRow.wkTypeName = "なし";
                vm.dataFetch.valueHasMutated();

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
            let contentChanged = vm.dataFetch().businessTripContent.tripInfos[index];
            let command = {
                    date: data.date,
                    businessTripInfoOutputDto: businessTripInfoOutputDto,
                    wkCode: data.wkTypeCd,
                    timeCode: codeChanged
            };

            vm.$blockui("show");
            vm.$validate([
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
                    vm.dataFetch.valueHasMutated();
                }
            }).fail(err => {
                let param;
                contentChanged.wkTimeCd = "";
                contentChanged.wkTimeName = "なし";
                vm.dataFetch.valueHasMutated();
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
            let selectedWorkTypeCode = data.wkTypeCd();
            let selectedWorkTimeCode = data.wkTimeCd();
            let listWorkCode = [];

            let selectedIndex = _.findIndex(ko.toJS(vm.items), {date: data.date});

            let listWkTime = vm.businessTripOutput().appDispInfoStartup.appDispInfoWithDateOutput.opWorkTimeLst;
            let listWkTimeCd = _.map(listWkTime, function (obj) {
                return obj.worktimeCode
            });


            let command = {
                selectedDate: selectedDate,
                businessTripInfoOutputDto: ko.toJS(vm.businessTripOutput)
            };

            vm.$ajax(API.startKDL003, command).done(res => {
                console.log(res);
                dispFlag = res;
            }).then(() =>{
                if (dispFlag) {
                    listWorkCode = _.map(vm.workTypeCds(), function (obj) {
                        return obj.workTypeCode
                    });
                } else {
                    listWorkCode = _.map(vm.holidayTypeCds(), function (obj) {
                        return obj.workTypeCode
                    });
                }

                vm.$window.storage('parentCodes', {
                    workTypeCodes: listWorkCode,
                    selectedWorkTypeCode: selectedWorkTypeCode,
                    workTimeCodes: listWkTimeCd,
                    selectedWorkTimeCode: selectedWorkTimeCode,
                    showNone: !dispFlag
                });

                nts.uk.ui.windows.setShared( 'parentCodes', {
                    workTypeCodes: listWorkCode,
                    selectedWorkTypeCode: selectedWorkTypeCode,
                    workTimeCodes: listWkTimeCd,
                    selectedWorkTimeCode: selectedWorkTimeCode,
                    showNone: !dispFlag
                }, true);

                vm.$errors("clear");

                nts.uk.ui.windows.sub.modal( '/view/kdl/003/a/index.xhtml' ).onClosed( function(): any {
                    //view all code of selected item
                    let rs = nts.uk.ui.windows.getShared( 'childData' );
                    if (rs) {
                        let currentRow;
                        if (vm.mode == Mode.New) {
                            currentRow = vm.dataFetch().businessTripOutput.businessTripActualContent[selectedIndex].opAchievementDetail;

                            currentRow.workTypeCD = rs.selectedWorkTypeCode;
                            currentRow.opWorkTypeName = rs.selectedWorkTypeName;
                            currentRow.workTimeCD = rs.selectedWorkTimeCode;
                            currentRow.opWorkTimeName = rs.selectedWorkTimeName;
                            currentRow.opWorkTime = rs.first.start;
                            currentRow.opLeaveTime = rs.first.end;
                        } else {
                            currentRow = vm.dataFetch().businessTripContent.tripInfos[selectedIndex];

                            currentRow.wkTypeCd = rs.selectedWorkTypeCode;
                            currentRow.wkTypeName = rs.selectedWorkTypeName;
                            currentRow.wkTimeCd = rs.selectedWorkTimeCode;
                            currentRow.wkTimeName = rs.selectedWorkTimeName;
                            currentRow.startWorkTime = rs.first.start;
                            currentRow.endWorkTime = rs.first.end;
                        }
                        vm.dataFetch.valueHasMutated();
                    }
                });

                $('#' + data.id).focus();

            });

        }

    }

    interface Comment {
        comment: string;
        colorCode: string;
        bold: boolean;
    }

    export interface BusinessTripOutput {
        setting: Comment;
        appDispInfoStartup: any;
        holidays: any;
        workdays: any;
        businessTripActualContent: any;
        infoBeforeChange: any;
        infoAfterChange: any;
    }

    export interface BusinessTripContent {
        departureTime: KnockoutObservable<number>;
        returnTime: KnockoutObservable<number>;
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
        id: string;
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
            this.id = date.replace(/\//g, "");
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
            this.wkTypeCd = ko.observable(wkTypeCd || "");
            this.wkTypeName = ko.observable(wkTypeName || "なし");
            this.wkTimeCd = ko.observable(wkTimeCd || "");
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