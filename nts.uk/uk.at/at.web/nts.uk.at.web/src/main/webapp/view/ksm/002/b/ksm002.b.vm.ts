module ksm002.b.viewmodel {
    import UnitModel = kcp.share.tree.UnitModel;
    import TreeComponentOption = kcp.share.tree.TreeComponentOption;
    import TreeType = kcp.share.tree.TreeType;
    import SelectType = kcp.share.tree.SelectionType;
    import UnitAlreadySettingModel = kcp.share.tree.UnitAlreadySettingModel; 
    
    export class ScreenModel {
        itemList: KnockoutObservableArray<any>;
        selectedIds: KnockoutObservableArray<number>;
        enable: KnockoutObservable<boolean>;
        workplaceInfo: KnockoutObservable<string>;
        //Calendar
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
        //Workplace List 
        multiSelectedWorkplaceId: KnockoutObservable<string>;
        baseDate: KnockoutObservable<Date>;
        alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
        treeGrid: TreeComponentOption;
        
        constructor() {
            var self = this;
            self.itemList = ko.observableArray([
                new BoxModel(1, '特定日1'),
                new BoxModel(2, '特定日2'),
                new BoxModel(3, '特定日3'),
                new BoxModel(4, '特定日4'),
                new BoxModel(5, '特定日5'),
                new BoxModel(6, '特定日6'),
                new BoxModel(7, '特定日7'),
                new BoxModel(8, '特定日8'),
                new BoxModel(9, '特定日9'),
                new BoxModel(10, '特定日10')
            ]);
            self.workplaceInfo = ko.observable(nts.uk.text.format(nts.uk.resource.getText("KSM002_61"),'4545'));
            
            //nts.uk.resource.getText('Com_Workplace')
            self.selectedIds = ko.observableArray([1,2]);
            self.enable = ko.observable(true);
            //CALENDAR
            self.yearMonthPicked = ko.observable(200006);
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
            nts.uk.at.view.kcp006.a.CellClickEvent = function(date){
                alert(date);
            };
            //WORKPLACE LIST
            self.baseDate = ko.observable(new Date());
            self.multiSelectedWorkplaceId = ko.observableArray([]);
            self.alreadySettingList = ko.observableArray([]);
            self.treeGrid = {
                    isShowAlreadySet: false,
                    isMultiSelect: false,
                    treeType: 2,
                    selectedWorkplaceId: self.multiSelectedWorkplaceId,
                    baseDate: self.baseDate,
                    selectType: 1,  
                    isShowSelectButton: false,
                    isDialog: false,
                    alreadySettingList: self.alreadySettingList
            };
            //$('#tree-grid').ntsTreeComponent(self.treeGrid);
        }

        /** Start page */
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();
            dfd.resolve();
            return dfd.promise();
        }
    }

    class BoxModel {
        id: number;
        name: string;
        constructor(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
        }
    }
    class UnitAlreadySettingModel {
        workplaceId: string;
        isAlreadySetting: boolean;
    }
}