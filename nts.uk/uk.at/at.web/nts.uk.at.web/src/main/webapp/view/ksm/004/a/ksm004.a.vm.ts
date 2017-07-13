module nts.uk.at.view.ksm004.a {
    import flat = nts.uk.util.flatArray;
    import getText = nts.uk.resource.getText;
    import aService = nts.uk.at.view.ksm004.a.service;
    export module viewmodel {
        export class ScreenModel {
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
            listComponentOption: ListComponentOption;
            
            calendarPanel: ICalendarPanel = {
                optionDates: ko.observableArray([]),
                yearMonth: ko.observable(moment(new Date()).format('YYYYMM')),
                firstDay: 0,
                startDate: 1,
                endDate: 31,
                workplaceId: ko.observable("0"),
                workplaceName: ko.observable(""),
                eventDisplay: ko.observable(true),
                eventUpdatable: ko.observable(true),
                holidayDisplay: ko.observable(true),
                cellButtonDisplay: ko.observable(true)
            }
            
            kcpTreeGrid: ITreeGrid = {
                treeType: 1,
                selectType: 1,
                isDialog: false,
                isMultiSelect: false,
                isShowAlreadySet: true,
                isShowSelectButton: false,
                baseDate: ko.observable(new Date()),
                selectedWorkplaceId: undefined,
                alreadySettingList: ko.observableArray([])
            };
            
            kcpGridlist: IGridList = {
                listType: 4,
                selectType: 1,
                isDialog: false,
                isMultiSelect: false,
                isShowAlreadySet: true,
                isShowNoSelectRow: false,
                isShowWorkPlaceName: true,
                isShowSelectAllButton: false,
                selectedCode: ko.observableArray([]),
                employeeInputList: ko.observableArray([]),
                alreadySettingList: ko.observableArray([])
            };
            
            currentCalendarCompany: KnockoutObservable<CalendarCompany> = ko.observable(new CalendarCompany('',0));
            currentCalendarWorkPlace: KnockoutObservable<CalendarWorkPlace> = ko.observable(new CalendarWorkPlace('','',0));
            currentCalendarClass: KnockoutObservable<CalendarClass> = ko.observable(new CalendarClass('','',0));
            constructor() {
                var self = this;
                self.kcpTreeGrid.selectedWorkplaceId = self.currentCalendarWorkPlace().workPlaceID;
                self.yearMonthPicked = ko.observable(moment(new Date()).format('YYYYMM'));
                self.cssRangerYM = {};
                self.currentCalendarWorkPlace().workPlaceID.subscribe(value => {
                    let data: Array<any> = flat($('#tree-grid')['getDataList'](), 'childs');
                    let item = _.find(data, m => m.workplaceId == value);
                    if (item) {
                        self.currentCalendarWorkPlace().name(item.name);
                    } else {
                        self.currentCalendarWorkPlace().name('');
                    }    
                });
                self.calendarPanel.optionDates([
                    {
                        start: '2017-07-01',
                        textColor: '#589CAE',
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
                    }
                ]);
                nts.uk.at.view.kcp006.a.CellClickEvent = function(date){
                    nts.uk.ui._viewModel.content.calendarPanel.optionDates.push(
                        {
                            start: '2017-07-08',
                            textColor: 'blue',
                            backgroundColor: 'red',
                            listText: [
                                "Sleep",
                                "Study"
                            ]
                        }
                    );    
                }; 
                $('#tree-grid').ntsTreeComponent(self.kcpTreeGrid).done(() => {
                    $('#classification-list-setting').ntsListComponent(self.kcpGridlist).done(() => {
                        self.start();
                    });
                });
                self.calendarPanel.optionDates.valueHasMutated();
            }
            
            settingDayAtr(date){
                console.log(date);    
            }
            
            start() {
                var self = this; 
                aService.getAllCalendarCompany().done((data) => {
                    self.    
                }).fail((res) => {
                    
                });
                self.currentCalendarWorkPlace().workPlaceID(_.first($('#tree-grid')['getDataList']()).workplaceId);    
                
            }
            
            changeTab() {
                var self = this;
                self.calendarPanel.optionDates([]);
                self.calendarPanel.yearMonth(moment(new Date()).format('YYYYMM'));
                self.calendarPanel.firstDay = 0;
                self.calendarPanel.startDate = 1;
                self.calendarPanel.endDate = 31;
                self.calendarPanel.workplaceId("0");
                self.calendarPanel.workplaceName("");
                self.calendarPanel.eventDisplay(true);
                self.calendarPanel.eventUpdatable(true);
                self.calendarPanel.holidayDisplay(true);
                self.calendarPanel.cellButtonDisplay(true);
            }
            
            openDialogC() {
                var self = this;
                nts.uk.ui.windows.setShared('date', '2000');
                nts.uk.ui.windows.sub.modal("/view/ksm/004/c/index.xhtml", { title: "割増項目の設定", dialogClass: "no-close" });   
            }
            
            openDialogD() {
                var self = this;
                nts.uk.ui.windows.setShared('classification', 0);
                nts.uk.ui.windows.setShared('startTime', '200007');
                nts.uk.ui.windows.setShared('endTime', '200008');
                nts.uk.ui.windows.sub.modal("/view/ksm/004/d/index.xhtml", { title: "割増項目の設定", dialogClass: "no-close" }); 
            }
        }


        interface ITabModel {
            id: string;
            name: string;
            active?: boolean;
        }

        class TabModel {
            id: string;
            name: string;
            active: KnockoutObservable<boolean> = ko.observable(false);
            
            constructor(param: ITabModel) {
                this.id = param.id;
                this.name = param.name;
                this.active(param.active || false);
            }
        }
        
        interface ICalendarPanel{
            optionDates: KnockoutObservableArray<any>; 
            yearMonth: KnockoutObservable<number>; 
            firstDay: number;
            startDate: number; 
            endDate: number;
            workplaceId: KnockoutObservable<string>;
            workplaceName: KnockoutObservable<string>;
            eventDisplay: KnockoutObservable<boolean>;
            eventUpdatable: KnockoutObservable<boolean>;
            holidayDisplay: KnockoutObservable<boolean>;
            cellButtonDisplay: KnockoutObservable<boolean>;  
        }
        
        interface ITreeGrid {
            treeType: number;
            selectType: number;
            isDialog: boolean;
            isMultiSelect: boolean;
            isShowAlreadySet: boolean;
            isShowSelectButton: boolean;
            baseDate: KnockoutObservable<any>;
            selectedWorkplaceId: KnockoutObservable<any>;
            alreadySettingList: KnockoutObservableArray<any>;
        }
        
        interface IGridList {
            listType: number;
            selectType: number;
            isDialog: boolean;
            isMultiSelect: boolean;
            isShowAlreadySet: boolean;
            isShowNoSelectRow: boolean;
            isShowWorkPlaceName: boolean;
            isShowSelectAllButton: boolean;
            selectedCode: KnockoutObservable<string>;
            employeeInputList: KnockoutObservableArray<any>;
            alreadySettingList: KnockoutObservableArray<any>;
        }
        
        class CalendarCompany {
            ymd: KnockoutObservable<string>;
            workingDayAtr: KnockoutObservable<number>;   
            constructor(ymd: string, workingDayAtr: number){
                this.ymd = ko.observable(ymd);   
                this.workingDayAtr = ko.observable(workingDayAtr);
            } 
        }
        
        class CalendarWorkPlace {
            workPlaceID: KnockoutObservable<string>;
            ymd: KnockoutObservable<string>;
            workingDayAtr: KnockoutObservable<number>;   
            name?: KnockoutObservable<string>;
            constructor(workPlaceID: string, ymd: string, workingDayAtr: number, name?: string){
                this.workPlaceID = ko.observable(workPlaceID);
                this.ymd = ko.observable(ymd);   
                this.workingDayAtr = ko.observable(workingDayAtr);
                this.name = ko.observable(name);
            }      
        }
        
        class CalendarClass {
            classCD: KnockoutObservable<string>;
            ymd: KnockoutObservable<string>;
            workingDayAtr: KnockoutObservable<number>;   
            name?: KnockoutObservable<string>;
            constructor(classCD: string, ymd: string, workingDayAtr: number, name?: string){
                this.classCD = ko.observable(classCD);
                this.ymd = ko.observable(ymd);   
                this.workingDayAtr = ko.observable(workingDayAtr);
                this.name = ko.observable(name);
            }   
        }
        
        class CalendarItem {
            start: string;
            textColor: string;
            backgroundColor: string;
            listText: string;
            constructor(start: number, listText: number) {
                this.start = start.toString();
                this.backgroundColor = 'white';
                switch(listText) {
                    case 1:
                        this.textColor = '#FF1D1D';
                        this.listText = WorkingDayAtr.WorkingDayAtr_WorkPlace;
                        break;
                    case 2:
                        this.textColor = '#FF1D1D';
                        this.listText = WorkingDayAtr.WorkingDayAtr_Class;
                        break;
                    default:
                        this.textColor = '#589CAE';
                        this.listText = WorkingDayAtr.WorkingDayAtr_Company;
                        break;
                }
                listText: string;        
            }
        }
        
        export enum WorkingDayAtr {
            WorkingDayAtr_Company = '稼働日',
            WorkingDayAtr_WorkPlace = '非稼働日（法内）',
            WorkingDayAtr_Class = '非稼働日（法外）'
        }
    }
}