module nts.uk.at.view.ksm005.b {

    import MonthlyPatternDto = service.model.MonthlyPatternDto;
    export module viewmodel {

        export class ScreenModel {
            columnMonthlyPatterns: KnockoutObservableArray<NtsGridListColumn>;
            lstMonthlyPattern: KnockoutObservableArray<MonthlyPatternDto>;
            selectMonthlyPattern: KnockoutObservable<string>;
            code: KnockoutObservable<string>;
            name: KnockoutObservable<string>;
            textEditorOption: KnockoutObservable<any>;
            textEditorOptionName: KnockoutObservable<any>;

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
                var self = this;
                self.columnMonthlyPatterns = ko.observableArray([
                    { headerText: nts.uk.resource.getText("KSM005_13"), key: 'monthlyPatternCode', width: 100 },
                    { headerText: nts.uk.resource.getText("KSM005_14"), key: 'monthlyPatternName', width: 150 }
                ]);
                self.textEditorOption = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    width: "50px",
                    textmode: "text",
                    textalign: "left"
                }));
                self.textEditorOptionName = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    width: "150px",
                    textmode: "text",
                    textalign: "left"
                }));

                var monthlyPatterns: MonthlyPatternDto[] = [];
                var monthlyPatternDto001: MonthlyPatternDto = { monthlyPatternCode: '001', monthlyPatternName: '001' };
                var monthlyPatternDto002: MonthlyPatternDto = { monthlyPatternCode: '002', monthlyPatternName: '002' };
                var monthlyPatternDto003: MonthlyPatternDto = { monthlyPatternCode: '003', monthlyPatternName: '003' };
                var monthlyPatternDto004: MonthlyPatternDto = { monthlyPatternCode: '004', monthlyPatternName: '004' };
                var monthlyPatternDto005: MonthlyPatternDto = { monthlyPatternCode: '005', monthlyPatternName: '005' };
                var monthlyPatternDto006: MonthlyPatternDto = { monthlyPatternCode: '006', monthlyPatternName: '006' };
                var monthlyPatternDto007: MonthlyPatternDto = { monthlyPatternCode: '007', monthlyPatternName: '007' };
                var monthlyPatternDto008: MonthlyPatternDto = { monthlyPatternCode: '008', monthlyPatternName: '008' };
                var monthlyPatternDto009: MonthlyPatternDto = { monthlyPatternCode: '009', monthlyPatternName: '009' };
                var monthlyPatternDto010: MonthlyPatternDto = { monthlyPatternCode: '010', monthlyPatternName: '010' };
                monthlyPatterns.push(monthlyPatternDto001);
                monthlyPatterns.push(monthlyPatternDto002);
                monthlyPatterns.push(monthlyPatternDto003);
                monthlyPatterns.push(monthlyPatternDto004);
                monthlyPatterns.push(monthlyPatternDto005);
                monthlyPatterns.push(monthlyPatternDto006);
                monthlyPatterns.push(monthlyPatternDto007);
                monthlyPatterns.push(monthlyPatternDto008);
                monthlyPatterns.push(monthlyPatternDto009);
                monthlyPatterns.push(monthlyPatternDto010);
                self.lstMonthlyPattern = ko.observableArray(monthlyPatterns);
                self.selectMonthlyPattern = ko.observable(monthlyPatternDto001.monthlyPatternCode);
                self.code = ko.observable(monthlyPatternDto001.monthlyPatternCode);
                self.name = ko.observable(monthlyPatternDto001.monthlyPatternCode);

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
                nts.uk.at.view.kcp006.a.CellClickEvent = function(date) {
                    alert(date);
                };
            }

            /**
             * open dialog batch setting (init view model e)
             */
            public openBatchSettingDialog(): void {
                nts.uk.ui.windows.sub.modal("/view/ksm/005/e/index.xhtml");
            }

        }

    }

}