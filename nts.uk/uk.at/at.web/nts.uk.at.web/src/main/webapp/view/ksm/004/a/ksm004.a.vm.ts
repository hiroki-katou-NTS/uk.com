module nts.uk.at.view.ksm004.a {
    import flat = nts.uk.util.flatArray;
    import getText = nts.uk.resource.getText;
    import aService = nts.uk.at.view.ksm004.a.service;
    export module viewmodel {
        export class ScreenModel {
            calendarData: KnockoutObservable<any>;
            yearMonthPicked: KnockoutObservable<number> = ko.observable(moment(new Date()).format('YYYYMM'));
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
                yearMonth: this.yearMonthPicked,
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
                self.calendarPanel.yearMonth(self.yearMonthPicked());
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
                nts.uk.at.view.kcp006.a.CellClickEvent = function(date){
                    nts.uk.ui._viewModel.content.calendarPanel.optionDates.removeAll();
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
            
            start(): JQueryPromise<any> { 
                var self = this;
                return self.getAllCalendarCompany();
            }
            
            getAllCalendarCompany(): JQueryPromise<any>{
                var self = this; 
                var dfd = $.Deferred();
                aService.getAllCalendarCompany()
                    .done((dataCompany) => {
                        let a = [];
                        _.forEach(dataCompany,(companyItem)=>{
                            a.push(new CalendarItem(companyItem.dateId,companyItem.workingDayAtr));
                        });   
                        self.calendarPanel.optionDates(a);
                        dfd.resolve();  
                    }).fail((res) => {
                    
                    });
                //self.currentCalendarWorkPlace().workPlaceID(_.first($('#tree-grid')['getDataList']()).workplaceId);    
                return dfd.promise();    
            }
            
            getCalenderWorkPlaceByCode(): JQueryPromise<any>{
                var self = this; 
                var dfd = $.Deferred();
                aService.getAllCalendarCompany()
                    .done((dataCompany) => {
                        let a = [];
                        _.forEach(dataCompany,(companyItem)=>{
                            a.push(new CalendarItem(companyItem.dateId,companyItem.workingDayAtr));
                        });   
                        self.calendarPanel.optionDates(a);
                        dfd.resolve();  
                    }).fail((res) => {
                    
                    });
                //self.currentCalendarWorkPlace().workPlaceID(_.first($('#tree-grid')['getDataList']()).workplaceId);    
                return dfd.promise();  
            }
            
            getCalendarClassById(): JQueryPromise<any>{
                
            }
            
            changeTab() {
                var self = this;
                let currentTab : number = $("#sidebar").ntsSideBar("getCurrent");
                switch(currentTab) {
                    case 1:
                        self.getCalenderWorkPlaceByCode();
                        break;
                    case 2:
                        self.getCalendarClassById();
                        break;
                    default:
                        self.getAllCalendarCompany();
                }
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
                this.start = moment(start.toString()).format('YYYY-MM-DD');
                this.backgroundColor = 'white';
                switch(listText) {
                    case 1:
                        this.textColor = '#FF1D1D';
                        this.listText = WorkingDayAtr.WorkingDayAtr_WorkPlace.toString();
                        break;
                    case 2:
                        this.textColor = '#FF1D1D';
                        this.listText = WorkingDayAtr.WorkingDayAtr_Class.toString();
                        break;
                    default:
                        this.textColor = '#589CAE';
                        this.listText = WorkingDayAtr.WorkingDayAtr_Company.toString();
                        break;
                }        
            }
        }
        
        export enum WorkingDayAtr {
            WorkingDayAtr_Company = '稼働日',
            WorkingDayAtr_WorkPlace = '非稼働日（法内）',
            WorkingDayAtr_Class = '非稼働日（法外）'
        }
    }
}