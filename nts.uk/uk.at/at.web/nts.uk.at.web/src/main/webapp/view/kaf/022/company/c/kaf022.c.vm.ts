module nts.uk.at.view.kaf022.c.viewmodel {
    import text = nts.uk.resource.getText;

    let __viewContext: any = window["__viewContext"] || {};

    export class ScreenModelC {
        itemListC27: KnockoutObservableArray<ItemModel> = ko.observableArray([
            {code: 1, name: text('KAF022_100')},
            {code: 0, name: text('KAF022_101')},
            {code: 2, name: text('KAF022_171')}
        ]);
        itemListC51: KnockoutObservableArray<ItemModel> = ko.observableArray([
            {code: 1, name: text('KAF022_36')},
            {code: 0, name: text('KAF022_37')}
        ]);
        itemListC48: KnockoutObservableArray<ItemModel> = ko.observableArray([
            {code: 1, name: text('KAF022_420')},
            {code: 0, name: text('KAF022_421')}
        ]);
        itemListC29: KnockoutObservableArray<ItemModel> = ko.observableArray([
            {code: 0, name: text('KAF022_173')},
            {code: 1, name: text('KAF022_174')},
            {code: 2, name: text('KAF022_175')},
        ]);
        itemListC30: KnockoutObservableArray<ItemModel> = ko.observableArray([
            {code: 0, name: text('KAF022_173')},
            {code: 1, name: text('KAF022_175')},
        ]);

        itemListCC1: KnockoutObservableArray<ItemModel> = ko.observableArray([
            {code: 0, name: text('KAF022_75')},
            {code: 1, name: text('KAF022_82')},
        ]);

        itemListC28: KnockoutObservableArray<ItemModel> = ko.observableArray([
            {code: 0, name: text('KAF022_75')},
            {code: 1, name: text('KAF022_82')},
        ]);

        reflectWorkHour: KnockoutObservable<number>;
        reflectAttendance: KnockoutObservable<number>;
        oneDayLeaveDeleteAttendance: KnockoutObservable<number>;

        annualVacationTime: KnockoutObservable<number>;
        superHoliday60H: KnockoutObservable<number>;
        substituteLeaveTime: KnockoutObservable<number>;
        nursing: KnockoutObservable<number>;
        childNursing: KnockoutObservable<number>;
        specialVacationTime: KnockoutObservable<number>;

        halfDayAnnualLeaveUsageLimitCheck: KnockoutObservable<number>;

        holidayAppTypeDispNames: KnockoutObservableArray<HolidayAppTypeDispName>;

        constructor() {
            const self = this;

            self.oneDayLeaveDeleteAttendance = ko.observable(0);
            self.reflectAttendance = ko.observable(0);
            self.reflectWorkHour = ko.observable(0);

            self.annualVacationTime = ko.observable(0);
            self.superHoliday60H = ko.observable(0);
            self.substituteLeaveTime = ko.observable(0);
            self.nursing = ko.observable(0);
            self.childNursing = ko.observable(0);
            self.specialVacationTime = ko.observable(0);

            self.halfDayAnnualLeaveUsageLimitCheck = ko.observable(0);

            self.holidayAppTypeDispNames = ko.observableArray([]);
            const listHdType = __viewContext.enums.HolidayAppType;
            const labels = ["KAF022_161", "KAF022_162", "KAF022_163", "KAF022_164", "KAF022_165", "KAF022_166", "KAF022_167"];
            listHdType.forEach((hdType: any, index: number) => {
                if (index < 7) {
                    self.holidayAppTypeDispNames.push(new HolidayAppTypeDispName(hdType.value, text(labels[index]), ""));
                }
            });

            $("#fixed-table-c1").ntsFixedTable({});
            $("#fixed-table-c2").ntsFixedTable({});
            $("#fixed-table-c3").ntsFixedTable({});
            $("#fixed-table-c4").ntsFixedTable({});
        }

        initData(allData: any): void {
            const self = this;
            const dataSetting = allData.holidayApplicationSetting;
            if (dataSetting) {
                self.halfDayAnnualLeaveUsageLimitCheck(dataSetting.halfDayAnnualLeaveUsageLimitCheck || 0);
                const dispNames = dataSetting.dispNames || [];
                self.holidayAppTypeDispNames().forEach(d => {
                    const disp = _.find(dispNames, i => i.holidayAppType == d.holidayAppType);
                    d.displayName(disp.displayName || "");
                });
            }

            const dataReflect = allData.holidayApplicationReflect;
            if (dataReflect) {
                if (dataReflect.workAttendanceReflect) {
                    self.oneDayLeaveDeleteAttendance(dataReflect.workAttendanceReflect.oneDayLeaveDeleteAttendance || 0);
                    self.reflectAttendance(dataReflect.workAttendanceReflect.reflectAttendance || 0);
                    self.reflectWorkHour(dataReflect.workAttendanceReflect.reflectWorkHour || 0);
                }
                if (dataReflect.timeLeaveReflect) {
                    self.annualVacationTime(dataReflect.timeLeaveReflect.annualVacationTime || 0);
                    self.childNursing(dataReflect.timeLeaveReflect.childNursing || 0);
                    self.nursing(dataReflect.timeLeaveReflect.nursing || 0);
                    self.specialVacationTime(dataReflect.timeLeaveReflect.specialVacationTime || 0);
                    self.substituteLeaveTime(dataReflect.timeLeaveReflect.substituteLeaveTime || 0);
                    self.superHoliday60H(dataReflect.timeLeaveReflect.superHoliday60H || 0);

                }
            }
        }

        collectData(): any {
            const self = this;
            return {
                holidayApplicationSetting: {
                    halfDayAnnualLeaveUsageLimitCheck: self.halfDayAnnualLeaveUsageLimitCheck(),
                    dispNames: ko.toJS(self.holidayAppTypeDispNames)
                },
                holidayApplicationReflect: {
                    workAttendanceReflect: {
                        oneDayLeaveDeleteAttendance: self.oneDayLeaveDeleteAttendance(),
                        reflectAttendance: self.reflectAttendance(),
                        reflectWorkHour: self.reflectWorkHour()
                    },
                    timeLeaveReflect: {
                        superHoliday60H: self.superHoliday60H(),
                        nursing: self.nursing(),
                        childNursing: self.childNursing(),
                        substituteLeaveTime: self.substituteLeaveTime(),
                        annualVacationTime: self.annualVacationTime(),
                        specialVacationTime: self.specialVacationTime()
                    }
                }
            };
        }

    }

    class ItemModel {
        code: number;
        name: string;

        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    class HolidayAppTypeDispName {
        holidayAppType: number;
        holidayAppTypeLabel: string;
        displayName: KnockoutObservable<string>;

        constructor(hdAppType: number, label: string, displayName: string) {
            this.holidayAppType = hdAppType;
            this.holidayAppTypeLabel = label;
            this.displayName = ko.observable(displayName || "");
        }
    }

}