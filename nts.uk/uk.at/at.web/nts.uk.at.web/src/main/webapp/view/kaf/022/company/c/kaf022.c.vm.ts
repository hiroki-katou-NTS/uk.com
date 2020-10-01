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
        itemListCC1: KnockoutObservableArray<ItemModel> = ko.observableArray([
            {code: 1, name: text('KAF022_75')},
            {code: 0, name: text('KAF022_82')},
        ]);
        itemListC28: KnockoutObservableArray<ItemModel> = ko.observableArray([
            { code: 0, name: text('KAF022_173') },
            { code: 1, name: text('KAF022_175') }
        ]);

        // 就業時間帯を反映する
        reflectWorkHour: KnockoutObservable<number>;
        // 出退勤を反映する
        reflectAttendance: KnockoutObservable<number>;
        // 1日休暇の場合は出退勤を削除
        oneDayLeaveDeleteAttendance: KnockoutObservable<number>;

        // 時間年休
        annualVacationTime: KnockoutObservable<number>;
        // 60H超休
        superHoliday60H: KnockoutObservable<number>;
        // 時間代休
        substituteLeaveTime: KnockoutObservable<number>;
        // 介護
        nursing: KnockoutObservable<number>;
        // 子看護
        childNursing: KnockoutObservable<number>;
        // 時間特別休暇
        specialVacationTime: KnockoutObservable<number>;

        // 半日年休の使用上限チェック
        halfDayAnnualLeaveUsageLimitCheck: KnockoutObservable<number>;

        // 休暇申請種類表示名
        holidayAppTypeDispNames: KnockoutObservableArray<HolidayAppTypeDispName>;

        constructor() {
            const self = this;

            self.oneDayLeaveDeleteAttendance = ko.observable(1);
            self.reflectAttendance = ko.observable(1);
            self.reflectWorkHour = ko.observable(1);

            self.annualVacationTime = ko.observable(1);
            self.superHoliday60H = ko.observable(1);
            self.substituteLeaveTime = ko.observable(1);
            self.nursing = ko.observable(1);
            self.childNursing = ko.observable(1);
            self.specialVacationTime = ko.observable(1);

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
                self.halfDayAnnualLeaveUsageLimitCheck(dataSetting.halfDayAnnualLeaveUsageLimitCheck);
                const dispNames = dataSetting.dispNames || [];
                self.holidayAppTypeDispNames().forEach(d => {
                    const disp = _.find(dispNames, i => i.holidayAppType == d.holidayAppType);
                    d.displayName(disp.displayName || "");
                });
            }

            const dataReflect = allData.holidayApplicationReflect;
            if (dataReflect) {
                if (dataReflect.workAttendanceReflect) {
                    self.oneDayLeaveDeleteAttendance(dataReflect.workAttendanceReflect.oneDayLeaveDeleteAttendance);
                    self.reflectAttendance(dataReflect.workAttendanceReflect.reflectAttendance);
                    self.reflectWorkHour(dataReflect.workAttendanceReflect.reflectWorkHour);
                }
                if (dataReflect.timeLeaveReflect) {
                    self.annualVacationTime(dataReflect.timeLeaveReflect.annualVacationTime);
                    self.childNursing(dataReflect.timeLeaveReflect.childNursing);
                    self.nursing(dataReflect.timeLeaveReflect.nursing);
                    self.specialVacationTime(dataReflect.timeLeaveReflect.specialVacationTime);
                    self.substituteLeaveTime(dataReflect.timeLeaveReflect.substituteLeaveTime);
                    self.superHoliday60H(dataReflect.timeLeaveReflect.superHoliday60H);

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