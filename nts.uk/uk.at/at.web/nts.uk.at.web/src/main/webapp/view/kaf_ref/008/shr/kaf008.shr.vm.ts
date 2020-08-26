/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.kaf008_ref.shr.viewmodel {
    import BusinessTripInfoOutput = nts.uk.at.view.kaf008_ref.a.viewmodel.BusinessTripInfoOutput;

    @component({
        name: 'kaf008-share',
        template: '/nts.uk.at.web/view/kaf_ref/008/shr/index.html'
    })
    class Kaf008ShareViewModel extends ko.ViewModel {
        mode: string = 'edit';
        departureTime: KnockoutObservable<number> = ko.observable(null);
        returnTime: KnockoutObservable<number> = ko.observable(null);
        comment: KnockoutObservable<Comment> = ko.observable(null);
        items: KnockoutObservableArray<TripContentDisp> = ko.observableArray([]);
        model: KnockoutObservable<any>;
        businessTripOutput: KnockoutObservable<BusinessTripInfoOutput>;
        workTypeCds: KnockoutObservableArray<string> = ko.observableArray([]);
        holidayTypeCds: KnockoutObservableArray<string> = ko.observableArray([]);

        created(params: any) {
            const vm = this;

            vm.businessTripOutput = params.businessTripOutput;
            vm.model = params.businessTripContent;

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
                };
            });



        }

        mounted() {
            const vm = this;

            $("#fixed-table").ntsFixedTable({ });

            vm.departureTime.subscribe(value => {
                vm.model().departureTime(value);
            });

            vm.returnTime.subscribe(value => {
                vm.model().returnTime(value);
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
                if(valid) {
                    return vm.$ajax(API.changeWorkTypeCode, command);
                }
            }).done(data => {
               if (data) {
                   let contentAfterChange = data;
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
                if(valid) {
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
                };
                vm.$dialog.error(param);
            }).always(() => vm.$blockui("hide"));
        }

        openDialogKdl003(data: TripContentDisp) {
            const vm = this;
            let workTypeCodes = data.wkTypeCd();
            let workTimeCodes = data.wkTimeCd();
            let selectedIndex = _.findIndex(ko.toJS(vm.items), {date: data.date});
            let listWorkCode = _.map(vm.workTypeCds(), function(obj) {return obj.workTypeCode});
            let listHolidayCode = _.map(vm.holidayTypeCds(), function(obj) {return obj.holidayTypeCds});
            let listWkTime = vm.businessTripOutput().appDispInfoStartup.appDispInfoWithDateOutput.opWorkTimeLst;
            let listWkTimeCd = _.map(listWkTime, function(obj) {return obj.worktimeCode});
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
                    let currentDetail = cloneOutput.businessTripActualContent[selectedIndex].opAchievementDetail;
                    currentDetail.workTypeCD = rs.selectedWorkTypeCode ;
                    currentDetail.opWorkTypeName = rs.selectedWorkTypeName;
                    currentDetail.workTimeCD = rs.selectedWorkTimeCode;
                    currentDetail.opWorkTimeName = rs.selectedWorkTimeName;
                    currentDetail.opWorkTime = rs.first.start;
                    currentDetail.opLeaveTime = rs.first.end;
                    vm.businessTripOutput(cloneOutput);
                });
            });

        }

    }

    interface Comment {
        comment: string;
        colorCode: string;
        bold: boolean;
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
        constructor(date: string, wkTypeCd: string, wkTypeName: string, wkTimeCd: string, wkTimeName: string, start:number, end: number ) {
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

}