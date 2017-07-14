module nts.uk.at.view.ksm004.a {
    import flat = nts.uk.util.flatArray;
    import getText = nts.uk.resource.getText;
    import aService = nts.uk.at.view.ksm004.a.service;
    export module viewmodel {
        export class ScreenModel {
            yearMonthPicked: KnockoutObservable<number> = ko.observable(moment(new Date()).format('YYYYMM'));
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
                listType: 2,
                selectType: 2,
                isDialog: false,
                isMultiSelect: false,
                isShowAlreadySet: true,
                isShowNoSelectRow: false,
                selectedCode: ko.observableArray([]),
                alreadySettingList: ko.observableArray([])
            };
            currentCalendarWorkPlace: KnockoutObservable<SimpleObject> = ko.observable(new SimpleObject('',''));
            currentCalendarClass: KnockoutObservable<SimpleObject> = ko.observable(new SimpleObject('',''));
            currentWorkingDayAtr: number = 0;
            constructor() {
                var self = this;
                self.kcpTreeGrid.selectedWorkplaceId = self.currentCalendarWorkPlace().key;
                self.kcpGridlist.selectedCode =  self.currentCalendarClass().key;
                self.calendarPanel.yearMonth(self.yearMonthPicked());
                self.currentCalendarWorkPlace().key.subscribe(value => {
                    let data: Array<any> = flat($('#tree-grid')['getDataList'](), 'childs');
                    let item = _.find(data, m => m.workplaceId == value);
                    if (item) {
                        self.currentCalendarWorkPlace().name(item.name);
                    } else {
                        self.currentCalendarWorkPlace().name('');
                    }    
                    self.getCalenderWorkPlaceByCode(value);
                });
                self.currentCalendarClass().key.subscribe(value => {
                    let data: Array<any> = $('#classification-list-setting')['getDataList']();
                    let item = _.find(data, m => m.code == value);
                    if (item) {
                        self.currentCalendarClass().name(item.name);
                    } else {
                        self.currentCalendarClass().name('');
                    }    
                    self.getCalendarClassById(value);
                });
                nts.uk.at.view.kcp006.a.CellClickEvent = function(date){
                    nts.uk.ui._viewModel.content.calendarPanel.optionDates.removeAll();
                }; 
                $('#tree-grid').ntsTreeComponent(self.kcpTreeGrid).done(() => {
                    $('#classification-list-setting').ntsListComponent(self.kcpGridlist).done(() => {
                        self.currentCalendarWorkPlace().key(_.first($('#tree-grid')['getDataList']()).workplaceId);
                        self.currentCalendarClass().key(_.first($('#classification-list-setting')['getDataList']()).code);
                        self.start();
                    });
                });
                $("#sidebar").ntsSideBar("init", {
                    active: 0,
                    activate: (event, info) => {
                        switch(info.newIndex) {
                            case 1:
                                self.getCalenderWorkPlaceByCode(self.currentCalendarWorkPlace().key().toString());
                                break;
                            case 2:
                                self.getCalendarClassById(self.currentCalendarClass().key().toString());
                                break;
                            default:
                                self.getAllCalendarCompany();
                        }
                    }
                });
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
                return dfd.promise();    
            }
            
            getCalenderWorkPlaceByCode(value): JQueryPromise<any>{
                var self = this; 
                var dfd = $.Deferred();
                aService.getCalendarWorkPlaceByCode(value)
                    .done((dataWorkPlace) => {
                        let a = [];
                        _.forEach(dataWorkPlace,(workPlaceItem)=>{
                            a.push(new CalendarItem(workPlaceItem.dateId,workPlaceItem.workingDayAtr));
                        });   
                        self.calendarPanel.optionDates(a);
                        dfd.resolve();  
                    }).fail((res) => {
                    
                    });
                return dfd.promise();  
            }
            
            getCalendarClassById(value): JQueryPromise<any>{
                var self = this; 
                var dfd = $.Deferred();
                aService.getCalendarClassById(value)
                    .done((dataClass) => {
                        let a = [];
                        _.forEach(dataClass,(companyItem)=>{
                            a.push(new CalendarItem(companyItem.dateId,companyItem.workingDayAtr));
                        });   
                        self.calendarPanel.optionDates(a);
                        dfd.resolve();  
                    }).fail((res) => {
                    
                    });
                return dfd.promise();
            }
            
            changeWorkingDayAtr(value){
                var self = this;
                self.currentWorkingDayAtr = value;
                $('.labelSqr'+value).css()
                console.log(value);    
            }
            
            openDialogC() {
                var self = this;
                nts.uk.ui.windows.setShared('date', '2000');
                nts.uk.ui.windows.sub.modal("/view/ksm/004/c/index.xhtml", { title: "割増項目の設定", dialogClass: "no-close" });   
            }
            
            openDialogD() {
                var self = this;
                nts.uk.ui.windows.setShared('classification', 0);
                nts.uk.ui.windows.setShared('workPlaceId', self.currentCalendarWorkPlace().key());
                nts.uk.ui.windows.setShared('classCD',  self.currentCalendarClass().key());
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
            selectedCode: KnockoutObservable<string>;
            alreadySettingList: KnockoutObservableArray<any>;
        }
        
        class SimpleObject {
            key: KnockoutObservable<string>;
            name: KnockoutObservable<string>;
            constructor(key: string, name: string){
                this.key = ko.observable(key);
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
                        this.textColor = '#FF3B3B';
                        this.listText = WorkingDayAtr.WorkingDayAtr_WorkPlace.toString();
                        break;
                    case 2:
                        this.textColor = '#FF3B3B';
                        this.listText = WorkingDayAtr.WorkingDayAtr_Class.toString();
                        break;
                    default:
                        this.textColor = '#31859C';
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