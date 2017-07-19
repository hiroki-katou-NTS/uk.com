module nts.uk.at.view.kdl023.a.viewmodel {
    export class ScreenModel {
        itemList: KnockoutObservableArray<ItemModelCbb1>;
        selectedCode: KnockoutObservable<string>;
        checked: KnockoutObservable<boolean>;
        zxcv: KnockoutObservable<number>;

        patternReflection: PatternReflection;

        // Calendar component
        calendarData: KnockoutObservable<any>;
        yearMonthPicked: KnockoutObservable<number>;
        cssRangerYM: any;
        optionDates: KnockoutObservableArray<any>;
        firstDay: number;
        yearMonth: KnockoutObservable<number>;
        startDate: number;
        endDate: number;
        workplaceId: KnockoutObservable<string>;
        eventDisplay: KnockoutObservable<boolean>;
        eventUpdatable: KnockoutObservable<boolean>;
        holidayDisplay: KnockoutObservable<boolean>;
        cellButtonDisplay: KnockoutObservable<boolean>;
        workplaceName: KnockoutObservable<string>;

        constructor() {
            let self = this;
            self.itemList = ko.observableArray([
                new ItemModelCbb1('1', '基本給'),
                new ItemModelCbb1('2', '役職手当'),
                new ItemModelCbb1('3', '基本給')
            ]);
            self.selectedCode = ko.observable('1');
            self.checked = ko.observable(true);
            self.zxcv = ko.observable(1);

            // Test
            self.patternReflection = new PatternReflection();
            self.patternReflection.employeeId = 'axbc';
            self.patternReflection.reflectionMethod = ReflectionMethod.FillInTheBlank;
            self.patternReflection.patternClassification = PatternClassification.Confirmation;
            self.patternReflection.statutorySetting = new DayOffSetting();
            self.patternReflection.nonStatutorySetting = new DayOffSetting();
            self.patternReflection.holidaySetting = new DayOffSetting();
            nts.uk.characteristics.save(self.patternReflection.employeeId, self.patternReflection);

            // Calendar component
            self.yearMonthPicked = ko.observable(200005);
            self.cssRangerYM = {
            };
            self.optionDates = ko.observableArray([
                {
                    start: '2000-05-01',
                    textColor: 'red',
                    backgroundColor: 'white',
                    listText: [
                        "Sleep",
                        "Study"
                    ]
                },
                {
                    start: '2000-05-05',
                    textColor: '#31859C',
                    backgroundColor: 'white',
                    listText: [
                        "Sleepaaaa",
                        "Study",
                        "Eating",
                        "Woman"
                    ]
                },
                {
                    start: '2000-05-10',
                    textColor: '#31859C',
                    backgroundColor: 'white',
                    listText: [
                        "Sleep",
                        "Study"
                    ]
                },
                {
                    start: '2000-05-20',
                    textColor: 'blue',
                    backgroundColor: 'white',
                    listText: [
                        "Sleep",
                        "Study",
                        "Play"
                    ]
                },
                {
                    start: '2000-06-20',
                    textColor: 'blue',
                    backgroundColor: 'red',
                    listText: [
                        "Sleep",
                        "Study",
                        "Play"
                    ]
                }
            ]);
            self.firstDay = 0;
            self.startDate = 1;
            self.endDate = 31;
            self.workplaceId = ko.observable("0");
            self.workplaceName = ko.observable("");
            self.eventDisplay = ko.observable(true);
            self.eventUpdatable = ko.observable(true);
            self.holidayDisplay = ko.observable(true);
            self.cellButtonDisplay = ko.observable(true);
            $("#calendar").ntsCalendar("init", {
                cellClick: function(date) {
                    alert(date);
                }
            });
        }

        public startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }

        public onBtnApplySettingClicked(): void {
            let self = this;
            nts.uk.characteristics.restore(self.patternReflection.employeeId)
                .done(function(data: PatternReflection) {
                    console.log(data);
                    console.log(data.holidaySetting.useClassification);
                    console.log(data.holidaySetting.workTypeCode);
                });
        }
    }

    class ItemModelCbb1 {
        code: string;
        name: string;

        constructor(codeCbb1: string, nameCbb1: string) {
            this.code = codeCbb1;
            this.name = nameCbb1;
        }
    }
    class PatternReflection {
        employeeId: string;
        reflectionMethod: ReflectionMethod
        patternClassification: PatternClassification;
        statutorySetting: DayOffSetting;
        nonStatutorySetting: DayOffSetting;
        holidaySetting: DayOffSetting;
    }
    class DayOffSetting {
        useClassification: boolean
        workTypeCode: string;
    }
    enum PatternClassification {
        Confirmation,
        Configuration
    }
    enum ReflectionMethod {
        Overwrite,
        FillInTheBlank
    }
}