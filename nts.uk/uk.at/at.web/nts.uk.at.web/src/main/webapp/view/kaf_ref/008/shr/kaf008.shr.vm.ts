/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.kaf008_ref.shr.viewmodel {
    import BusinessTripInfoOutputDto = nts.uk.at.view.kaf008_ref.a.viewmodel.BusinessTripInfoOutputDto;

    @component({
        name: 'kaf008-share',
        template: '/nts.uk.at.web/view/kaf_ref/008/shr/index.html'
    })
    class Kaf008ShareViewModel extends ko.ViewModel {
        mode: string = 'edit';
        departureTime: KnockoutObservable<number> = ko.observable(null);
        returnTime: KnockoutObservable<number> = ko.observable(null);
        comment: Comment = {comment: 'This is comment', bold: true, colorCode: '#FF0000'};
        items: KnockoutObservableArray<TripContentDisp> = ko.observableArray([]);
        model: KnockoutObservable<any>;
        businessTripOutput: KnockoutObservable<BusinessTripInfoOutputDto>;
        workTypeCds: KnockoutObservableArray<string> = ko.observableArray([]);
        holidayTypeCds: KnockoutObservableArray<string> = ko.observableArray([]);

        created(params: any) {
            const vm = this;
            $("#fixed-table").ntsFixedTable({ });
            vm.businessTripOutput = params.businessTripOutput;
            vm.model = params.businessTripContent;
            vm.businessTripOutput.subscribe(value => {
                if (value) {
                    vm.workTypeCds(value.workdays);
                    vm.holidayTypeCds(value.holidays);
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
            })
            vm.departureTime.subscribe(value => {
                vm.model().departureTime(value);
            })
            vm.returnTime.subscribe(value => {
                vm.model().returnTime(value);
            })
        }

        mounted() {
            const vm = this;

        }

        changeWorkTypeCode(data: BusinessTripInfoOutputDto, date: string, wkCode: string, index: number) {
            const vm = this;
            let businessTripInfoOutputDto = ko.toJS(data);
            let typeCode = wkCode;
            let command = {
                date, businessTripInfoOutputDto, typeCode
            }
            let cloneOutput = _.clone(vm.businessTripOutput());
            vm.$ajax(API.changeWorkTypeCode, command).done(data => {
                let contentAfterChange = data;
                vm.businessTripOutput(data);
                let workTypeAfterChange = data.infoAfterChange;
                let InfoChanged = _.findIndex(workTypeAfterChange, {date: date});
                let workCodeChanged = workTypeAfterChange[InfoChanged].workTypeDto.workTypeCode;
                let workNameChanged = workTypeAfterChange[InfoChanged].workTypeDto.name;
                cloneOutput.businessTripActualContent[index].opAchievementDetail.workTypeCD = workCodeChanged;
                cloneOutput.businessTripActualContent[index].opAchievementDetail.opWorkTypeName = workNameChanged;
                vm.businessTripOutput(cloneOutput);
            }).fail(err => {
                cloneOutput.businessTripActualContent[index].opAchievementDetail.workTypeCD = "";
                cloneOutput.businessTripActualContent[index].opAchievementDetail.opWorkTypeName = "なし";
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

        changeWorkTimeCode(data: BusinessTripInfoOutputDto, date: string, wkCode: string, timeCode: string, index: number) {
            const vm = this;
            let inputDate = date;
            let businessTripInfoOutputDto = ko.toJS(data);
            let typeCode = wkCode;
            let cloneOutput = _.clone(vm.businessTripOutput());
            let command = {
                date, businessTripInfoOutputDto, typeCode, timeCode
            }
            vm.$ajax(API.changWorkTimeCode, command).done(data => {
                cloneOutput.businessTripActualContent[index].opAchievementDetail.workTimeCD = timeCode;
                cloneOutput.businessTripActualContent[index].opAchievementDetail.opWorkTimeName = data;
                vm.businessTripOutput(cloneOutput);
            }).fail(err => {
                cloneOutput.businessTripActualContent[index].opAchievementDetail.workTimeCD = "";
                cloneOutput.businessTripActualContent[index].opAchievementDetail.opWorkTimeName = "";
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
            }).always(() => vm.$blockui("hide"));;
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
                    let currentDetail = vm.businessTripOutput().businessTripActualContent[selectedIndex].opAchievementDetail;
                    cloneOutput.businessTripActualContent[selectedIndex].opAchievementDetail.workTypeCD = rs.selectedWorkTypeCode ;
                    cloneOutput.businessTripActualContent[selectedIndex].opAchievementDetail.opWorkTypeName = rs.selectedWorkTypeName;
                    cloneOutput.businessTripActualContent[selectedIndex].opAchievementDetail.workTimeCD = rs.selectedWorkTimeCode;
                    cloneOutput.businessTripActualContent[selectedIndex].opAchievementDetail.opWorkTimeName = rs.selectedWorkTimeName;
                    cloneOutput.businessTripActualContent[selectedIndex].opAchievementDetail.opWorkTime = rs.first.start;
                    cloneOutput.businessTripActualContent[selectedIndex].opAchievementDetail.opLeaveTime = rs.first.end;
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

    interface Data {
        departureTime: number;
        returnTime: number;
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
            this.dateDisp = moment(date, "YYYY/MM/DD").format('YYYY-MM-DD(ddd)');
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
            var startTime = nts.uk.time.format.byId("Clock_Short_HM", start);
            var endTime = nts.uk.time.format.byId("Clock_Short_HM", end);
            this.start = ko.observable(startTime);
            this.end = ko.observable(endTime);
        }
    }

    export interface BusinessTripContent {
        date: string;
        opAchievementDetail: TripAchivementDetail;
    }

    export interface TripAchivementDetail {
        wkTypeCd: string;
        wkTypeName: string;
        wkTimeCd: string;
        wkTimeName: string;
        startTime: number;
        endTime: number;
    }

    export interface BusinessTripInfo {
        departureTime: number;
        returnTime: number;
        infos: Array<BusinessTripInfoDetail>
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