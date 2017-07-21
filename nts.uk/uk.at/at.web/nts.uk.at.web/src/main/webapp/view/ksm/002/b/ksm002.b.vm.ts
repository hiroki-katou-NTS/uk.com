module ksm002.b.viewmodel {
    import flat = nts.uk.util.flatArray;
    import bService = nts.uk.at.view.ksm002.b.service;
    export class ScreenModel {
        checkBoxList: KnockoutObservableArray<CheckBoxItem> = ko.observableArray([]); 
        selectedIds: KnockoutObservableArray<number> = ko.observableArray([]); 
        yearMonthPicked: KnockoutObservable<number> = ko.observable(Number(moment(new Date()).format('YYYY01')));
        workPlaceText: KnockoutObservable<string> = ko.observable(nts.uk.resource.getText('KSM002_61', [nts.uk.resource.getText('Com_Workplace')]));
        currentWorkPlace: KnockoutObservable<WorkPlaceObject> = ko.observable(new WorkPlaceObject('',''));
        rootList: Array<IWorkPlaceDto> = []; // list data from server
        isUpdate: KnockoutObservableArray<boolean> = ko.observableArray(false);
        calendarPanel: ICalendarPanel = {
            optionDates: ko.observableArray([]),
            yearMonth: this.yearMonthPicked,
            firstDay: 0,
            startDate: 1,
            endDate: 31,
            workplaceId: this.currentWorkPlace().id,
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
        
        constructor() {
            var self = this;
            self.kcpTreeGrid.selectedWorkplaceId = self.currentWorkPlace().id;
            
            // get new data when year month change
            self.yearMonthPicked.subscribe(value => {
                self.getCalendarWorkPlaceByCode();        
            });
            
            // get new data when Work Place Code change
            self.currentWorkPlace().id.subscribe(value => {
                let data: Array<any> = flat($('#tree-grid')['getDataList'](), 'childs');
                let item = _.find(data, m => m.workplaceId == value);
                if (item) {
                    self.currentWorkPlace().name(item.name);
                } else {
                    self.currentWorkPlace().name('');
                }    
                self.getCalendarWorkPlaceByCode();
            });
            
            // calendar event handler 
            $("#calendar1").ntsCalendar("init", {
                cellClick: function(date) {
                    nts.uk.ui._viewModel.content.viewModelB.setListText(date);
                },
                buttonClick: function(date) {
                    nts.uk.ui._viewModel.content.viewModelB.openDialogE(date);
                }
            });
            $('#tree-grid').ntsTreeComponent(self.kcpTreeGrid).done(() => {
                self.getSpecDateByIsUse();
                self.currentWorkPlace().id(_.first($('#tree-grid')['getDataList']()).workplaceId);  
            });
        }
        
        /**
         * register button event handler
         */
        submitEventHandler(){
            var self = this;
            if(self.isUpdate()){
                self.updateCalendarWorkPlace();
            } else {
                self.insertCalendarWorkPlace();
            }    
        }
        
        /**
         * delete button event handler
         */
        removeEventHandler(){
            var self = this;
            nts.uk.ui.dialog.confirm({ messageId: 'Msg_18' }).ifYes(function(){
                self.deleteCalendarWorkPlace();        
            }).ifNo(function(){
                // do nothing           
            });
        }
        
        /**
         * get selectable item
         */
        getSpecDateByIsUse(){
            var self = this;
            bService.getSpecificDateByIsUse(1).done(data=>{
                self.checkBoxList.removeAll();
                data.forEach(item => {
                    self.checkBoxList.push(new CheckBoxItem(item.specificDateItemNo, item.specificName));    
                });   
            }).fail(res => {
                nts.uk.ui.dialog.alertError(res.message).then(function(){nts.uk.ui.block.clear();});
            });            
        }
        
        /**
         * get calendar work place spec date by work place id and year month
         */
        getCalendarWorkPlaceByCode(){
            var self = this;
            bService.getCalendarWorkPlaceByCode(self.currentWorkPlace().id(),self.yearMonthPicked(),1).done(data=>{
                self.rootList = data;
                self.calendarPanel.optionDates.removeAll();
                let a = [];
                if(!nts.uk.util.isNullOrEmpty(data)) {
                    data.forEach(item => {
                        a.push(new CalendarItem(item.specificDate, self.convertNumberToName(item.specificDateItemNo)))                    
                    });   
                    self.isUpdate(true);
                } else {
                    self.isUpdate(false);
                }
                self.calendarPanel.optionDates(a);
            }).fail(res => {
                nts.uk.ui.dialog.alertError(res.message).then(function(){nts.uk.ui.block.clear();});
            });
        }
        
        /**
         * insert calendar work place spec date
         */
        insertCalendarWorkPlace(){
            var self = this;
            bService.insertCalendarWorkPlace(self.createCommand()).done(data=>{
                nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                self.getCalendarWorkPlaceByCode();            
            }).fail(res => {
                nts.uk.ui.dialog.alertError(res.message).then(function(){nts.uk.ui.block.clear();});
            });
        }
        
        /**
         * update calendar work place spec date
         */
        updateCalendarWorkPlace(){
            var self = this;
            bService.updateCalendarWorkPlace(self.createCommand()).done(data=>{
                nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                self.getCalendarWorkPlaceByCode();   
            }).fail(res => {
                nts.uk.ui.dialog.alertError(res.message).then(function(){nts.uk.ui.block.clear();});
            });
        }
        
        /**
         * delete calendar work place spec date
         */
        deleteCalendarWorkPlace(){
            var self = this;
            bService.deleteCalendarWorkPlace({
                workPlaceId: self.currentWorkPlace().id(),
                yearMonth: self.yearMonthPicked()   
            }).done(data=>{
                nts.uk.ui.dialog.info({ messageId: "Msg_16" });
               self.getCalendarWorkPlaceByCode(); 
            }).fail(res => {
                nts.uk.ui.dialog.alertError(res.message).then(function(){nts.uk.ui.block.clear();});
            });
        }
        
        /**
         * open dialog C event
         */
        openDialogC() {
            var self = this;
            nts.uk.ui.windows.sub.modal("/view/ksm/002/c/index.xhtml", { title: "割増項目の設定", dialogClass: "no-close" }).onClosed(function() {
                self.getSpecDateByIsUse();    
            });  
        }
        
        /**
         * open dialog D event
         */
        openDialogD() {
            var self = this;
            nts.uk.ui.windows.setShared('KSM002_D_PARAM', 
            {
                util: 2,
                workplaceObj: ko.mapping.toJS(self.currentWorkPlace()),
                startDate: Number(moment(self.yearMonthPicked().toString(),'YYYYMM').startOf('month').format('YYYYMMDD')),
                endDate: Number(moment(self.yearMonthPicked().toString(),'YYYYMM').endOf('month').format('YYYYMMDD'))
            });
            nts.uk.ui.windows.sub.modal("/view/ksm/002/d/index.xhtml", { title: "割増項目の設定", dialogClass: "no-close" }).onClosed(function() {
                self.getCalendarWorkPlaceByCode();    
            });  
        }
        
        /**
         * open dialog E event
         */
        openDialogE(date: string) {
            var self = this;
            nts.uk.ui.windows.setShared('KSM002_E_PARAM', 
            {
                date: Number(moment(date).format("YYYYMMDD")),
                selectable: ko.mapping.toJS(self.checkBoxList()),
                selecteds: ko.mapping.toJS(self.selectedIds())
            });
            nts.uk.ui.windows.sub.modal("/view/ksm/002/e/index.xhtml", { title: "割増項目の設定", dialogClass: "no-close" }).onClosed(function() {});  
        }
        
        /**
         * setting item list event
         */
        setListText(date){
            var self = this;
            if(!nts.uk.util.isNullOrEmpty(self.selectedIds())) {
                let dateData = self.calendarPanel.optionDates();
                let existItem = _.find(dateData, item => item.start == date);   
                if(existItem!=null) {
                    existItem.changeListText(self.convertNumberToName(self.selectedIds()));   
                } else {
                    dateData.push(new CalendarItem(date,self.convertNumberToName(self.selectedIds())));    
                }
            }
            self.calendarPanel.optionDates.valueHasMutated();
        }
        
        /**
         * create command data for insert/update
         */
        createCommand(){
            var self = this;
            let a = [];
            if(self.isUpdate()){
                // update case
                self.calendarPanel.optionDates().forEach(item => {
                    let before = _.find(self.rootList, o => o.specificDate == Number(moment(item.start).format('YYYYMMDD')));
                    if(nts.uk.util.isNullOrUndefined(before)){
                        a.push({
                            workPlaceId: self.currentWorkPlace().id(),
                            specificDate: Number(moment(item.start).format('YYYYMMDD')),
                            specificDateItemNo: self.convertNameToNumber(item.listText),
                            isUpdate: false
                        });
                    } else {
                        let current = {
                            workPlaceId: self.currentWorkPlace().id(),
                            specificDate: Number(moment(item.start).format('YYYYMMDD')),
                            specificDateItemNo: self.convertNameToNumber(item.listText)
                        };   
                        if(!_.isEqual(ko.mapping.toJSON(before),ko.mapping.toJSON(current))) {
                            current["isUpdate"] = true;
                            a.push(current);    
                        }
                    }
                });
            } else {
                // insert case
                self.calendarPanel.optionDates().forEach(item => {
                    a.push({
                        workPlaceId: self.currentWorkPlace().id(),
                        specificDate: Number(moment(item.start).format('YYYYMMDD')),
                        specificDateItemNo: self.convertNameToNumber(item.listText)
                    })    
                });  
            }
            return a;
        }
        
        /**
         * convert list item selected from number to string
         */
        convertNumberToName(inputArray: number[]): string[]{
            var self = this;   
            let a = [];
            inputArray.forEach(item => {
                let rs = _.find(self.checkBoxList(), o => {return o.id == item});
                a.push(rs.name);       
            });
            return a; 
        }
        
        /**
         * convert list item selected from string to number
         */
        convertNameToNumber(inputArray: string[]): number[]{
            var self = this;   
            let a = [];
            inputArray.forEach(item => {
                let rs = _.find(self.checkBoxList(), o => {return o.name == item});
                a.push(rs.id);       
            });
            return a; 
        }
    }
    
    interface IWorkPlaceDto {
        workPlaceId: string;
        specificDate: number;
        specificDateItemNo: number[];
    }
    
    class WorkPlaceObject {
        id: KnockoutObservable<string>;
        name: KnockoutObservable<string>;
        constructor(id: string, name: string) {
            this.id = ko.observable(id);
            this.name = ko.observable(name);   
        }      
    }
    
    class CheckBoxItem {
        id: number;
        name: string; 
        constructor(id: number, name: string) {
            this.id = id;
            this.name = name;
        } 
    }
    
    class CalendarItem {
        start: string;
        textColor: string;
        backgroundColor: string;
        listText: string[];
        constructor(start: number, listText: string[]) {
            this.start = moment(start.toString()).format('YYYY-MM-DD');
            this.backgroundColor = 'white';
            this.textColor = '#31859C';
            this.listText = listText;
        }
        changeListText(value: string[]){
            this.listText = value;     
        }
    }
    
    export enum WorkingDayAtr {
        WorkingDayAtr_Company = '稼働日',
        WorkingDayAtr_WorkPlace = '非稼働日（法内）',
        WorkingDayAtr_Class = '非稼働日（法外）'
    }
    
}