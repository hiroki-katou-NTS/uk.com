module nts.uk.at.view.ksm005.b {

    import MonthlyPatternDto = service.model.MonthlyPatternDto;
    export module viewmodel {

        export class ScreenModel {
            columnMonthlyPatterns: KnockoutObservableArray<NtsGridListColumn>;
            lstMonthlyPattern: KnockoutObservableArray<MonthlyPatternDto>;
            selectMonthlyPattern: KnockoutObservable<string>;
            monthlyPatternModel: KnockoutObservable<MonthlyPatternModel>;
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
                    { headerText: nts.uk.resource.getText("KSM005_13"), key: 'code', width: 100 },
                    { headerText: nts.uk.resource.getText("KSM005_14"), key: 'name', width: 150 }
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

                self.lstMonthlyPattern = ko.observableArray([]);
                self.monthlyPatternModel = ko.observable(new MonthlyPatternModel());
                self.selectMonthlyPattern = ko.observable('');
                self.selectMonthlyPattern.subscribe(function(monthlyPatternCode: string){
                   self.detailMonthlyPattern(monthlyPatternCode); 
                });
                self.yearMonthPicked = ko.observable(201707);
                self.cssRangerYM = {
                };
                self.optionDates = ko.observableArray([
                    {
                        start: '2017-07-01',
                        textColor: 'red',
                        backgroundColor: 'white',
                        listText: [
                            "Sleep",
                            "Study"
                        ]
                    },
                    {
                        start: '2017-07-05',
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
                        start: '2017-07-10',
                        textColor: '#31859C',
                        backgroundColor: 'white',
                        listText: [
                            "Sleep",
                            "Study"
                        ]
                    },
                    {
                        start: '2017-07-20',
                        textColor: 'blue',
                        backgroundColor: 'white',
                        listText: [
                            "Sleep",
                            "Study",
                            "Play"
                        ]
                    },
                    {
                        start: '2017-07-22',
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
                    console.log(date);
                };
            }

            /**
             * open dialog batch setting (init view model e)
             */
            public openBatchSettingDialog(): void {
                nts.uk.ui.windows.sub.modal("/view/ksm/005/e/index.xhtml");
            }

            /**
            * start page data 
            */
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                service.findAllMonthlyPattern().done(function(data) {
                    self.lstMonthlyPattern(data);
                    self.selectMonthlyPattern(data[0].code);
                    self.monthlyPatternModel().updateData(data[0]);
                    dfd.resolve(self);
                });
                return dfd.promise();
            }
            
            /**
             * detail monthly pattern by selected monthly pattern code
             */
            detailMonthlyPattern(monthlyPatternCode: string): void {
                var self = this;
                service.findByIdWorkMonthlySetting(monthlyPatternCode).done(function(data){
                    service.findByIdMonthlyPattern(monthlyPatternCode).done(function(res){
                        self.monthlyPatternModel().updateData(res);
                        console.log(data);
                    });
                });
            }

        }
        
        export class MonthlyPatternModel{
            code: KnockoutObservable<string>;
            name: KnockoutObservable<string>;
            constructor(){
                this.code = ko.observable('');    
                this.name = ko.observable('');    
            }    
            
            updateData(dto: MonthlyPatternDto) {
                this.code(dto.code);
                this.name(dto.name);
            }
        }

    }

}