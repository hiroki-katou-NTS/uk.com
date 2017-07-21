module ksm002.d{
    export module viewmodel{
        import getShared = nts.uk.ui.windows.getShared;
        
        export class ScreenModel { 
            // radio button group D2_2 
            itemList: KnockoutObservableArray<any>;
            selectedId: KnockoutObservable<number>;
            // follow start date and end date D1_4
            startMonth: KnockoutObservable<number>;
            endMonth: KnockoutObservable<number>;
            // follow checkbox D1_15
            enable: KnockoutObservable<boolean>;
            // checkbox for items block D1_16
            specificDateItem: KnockoutObservableArray<SpecificDateItem>;
            // checkbox for 7 days in a week D1_7
            dayInWeek: KnockoutObservableArray<DayInWeekItem>;
            // label to display work place D1_2
            workPlace: KnockoutObservable<string>; 
            // check choose any day or item
            countDay: KnockoutObservable<number>;
            countItem: KnockoutObservable<number>;
            // data receive from mother screen
            param: IData;
            constructor() {
                let self=this;
                self.startMonth = ko.observable(null);
                self.endMonth = ko.observable(null);
                self.specificDateItem = ko.observableArray([]);
                self.dayInWeek = ko.observableArray([]);
                self.enable = ko.observable(false);
                // Radio button
                self.itemList = ko.observableArray([
                new BoxModel(1, nts.uk.resource.getText('KSM002_54')),
                new BoxModel(2, nts.uk.resource.getText('KSM002_55'))
                ]);
                self.selectedId = ko.observable(1);
                self.workPlace = ko.observable("");
                self.countDay = ko.observable(0);
                self.countItem = ko.observable(0);
                self.param = null; 
            }
    
            
    
            /** get data when start dialog **/
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                self.param = getShared('KSM002_D_PARAM') || { util: 0, workplaceObj: null, startDate: null, endDate: null};
                self.endMonth(self.param.endDate);
                self.startMonth(self.param.startDate);
                // label to display work place D1_2
                if(self.param.util == 1){
                    self.workPlace(nts.uk.resource.getText('Com_Company'))    
                }
                else if(self.param.util == 2){
                    self.workPlace(self.param.workplaceObj.name);    
                }
                self.dayInWeek([new DayInWeekItem(nts.uk.resource.getText('KSM002_45'),0), new DayInWeekItem(nts.uk.resource.getText('KSM002_46'),0), new DayInWeekItem(nts.uk.resource.getText('KSM002_47'),0), new DayInWeekItem(nts.uk.resource.getText('KSM002_48'),0), new DayInWeekItem(nts.uk.resource.getText('KSM002_49'),0), new DayInWeekItem(nts.uk.resource.getText('KSM002_50'),0), new DayInWeekItem(nts.uk.resource.getText('KSM002_51'),0)]);
                service.getSpecificDateByIsUse(1).done(function(data) {
                    let dataSource =  _.orderBy(data, ["specificDateItemNo"], ["asc"]);
                    dataSource.forEach(function(item){
                        self.specificDateItem.push(
                            new SpecificDateItem(
                                item.timeItemId,
                                0,
                                item.specificDateItemNo,
                                item.specificName
                            ));
                    });
                    if(self.specificDateItem().length==0){
                        nts.uk.ui.dialog.info({ messageId: "Msg_135" }).then(function(){nts.uk.ui.windows.close();});
                    }
                    dfd.resolve();
                }).fail(function(res) { 
                    nts.uk.ui.dialog.alertError(res.message).then(function(){nts.uk.ui.block.clear();});
                    dfd.reject(res); 
                });
                return dfd.promise();
        }
            
            /** close Dialog **/
            closeDialog() {   
                nts.uk.ui.windows.close(); 
            }
            convert(value: string): number{
                switch(value){
                    case '日曜日': return 7;
                    case '月曜日': return 1;
                    case '火曜日': return 2;
                    case '水曜日': return 3; 
                    case '木曜日': return 4;
                    case '金曜日': return 5;
                    case '土曜日': return 6;
                    default : return 0;
                }
            }
            /** submit dialog **/
            submitDialog(){
                let self = this;
                let listDayToUpdate: Array<number> = [];
                let listTimeItemToUpdate: Array<number> =[];
                self.countDay(0);
                self.countItem(0);
                 nts.uk.ui.errors.clearAll();
                // check start date <= end date
                if(self.startMonth() > self.endMonth()){
                    $('#startMonth').ntsError('set', {messageId:"Msg_136"});
                }
                // check not choose any day in a week
                _.each(self.dayInWeek(), function(obj: viewmodel.DayInWeekItem) {
                    if(obj.choose() == 0){
                        self.countDay(self.countDay()+1);
                    }
                });
                if(self.countDay() == self.dayInWeek().length && self.enable() == false){
                    $('#day_0').ntsError('set', {messageId:"Msg_137"});
                }
                // check not choose any item
                _.each(self.specificDateItem(), function(obj1: viewmodel.SpecificDateItem) {
                    if(obj1.useAtr() == 0){
                        self.countItem(self.countItem()+1);
                    }
                    else{
                        listTimeItemToUpdate.push(obj1.specificDateItemNo());
                    }
                });
                if(self.countItem() == self.specificDateItem().length){
                    $('#item_0').ntsError('set', {messageId:"Msg_138"});
                }
                for(let i = 0; i < self.dayInWeek().length; i++){
                    if(self.dayInWeek()[i].choose()==1){
                        listDayToUpdate.push(self.convert(self.dayInWeek()[i].day()));
                    }
                }
                if(self.enable()==true){
                    listDayToUpdate.push(0);     
                }
                let object = new ObjectToUpdate(self.param.util, self.startMonth(), self.endMonth(), listDayToUpdate, listTimeItemToUpdate, self.selectedId(), self.param.workplaceObj.id);
                service.updateSpecificDateSet(object).done(function(data) {
                    nts.uk.ui.windows.close(); 
                }).fail(function(res) { 
                    nts.uk.ui.dialog.alertError(res.message).then(function(){nts.uk.ui.block.clear();});
                    dfd.reject(res); 
                    });
            }
        }
        // item for radio button
        class BoxModel {
            id: number;
            name: string;
            constructor(id, name){
                var self = this;
                self.id = id;
                self.name = name;
            }
        }
        // A item D1_16 
        export class SpecificDateItem{
            timeItemId: KnockoutObservable<string>;
            useAtr: KnockoutObservable<number>;
            specificDateItemNo: KnockoutObservable<number>;
            specificName: KnockoutObservable<string>;
            constructor(timeItemId: string, useAtr: number, specificDateItemNo: number, specificName: string){
                this.timeItemId = ko.observable(timeItemId);
                this.useAtr = ko.observable(useAtr);
                this.specificDateItemNo = ko.observable(specificDateItemNo);
                this.specificName = ko.observable(specificName);
            }
        }
        // A day in a week D1_7
        export class DayInWeekItem{
            day: KnockoutObservable<string>;
            choose: KnockoutObservable<number>;
            constructor(day: string, choose: number){
                this.day = ko.observable(day);
                this.choose = ko.observable(choose);
            }
        }
        // A object to update 
        export class ObjectToUpdate{
            util: number;
            strDate: number;
            endDate: number;
            dayofWeek: Array<number>;
            lstTimeItemId: Array<number>;
            setUpdate: number;
            workplaceId: string;
            constructor(util: number, strDate: number, endDate:number, dayofWeek: Array<number>, lstTimeItemId: Array<number>, setUpdate: number, workplaceId: string){
                this.util = util;
                this.strDate = strDate;
                this.endDate =endDate;
                this.dayofWeek = dayofWeek;
                this.lstTimeItemId = lstTimeItemId;
                this.setUpdate = setUpdate;
                this.workplaceId = workplaceId;
            }
        } 
        // a object was received from mother screen
        interface IData {
            util: number,
            workplaceObj?: any,
            startDate: number,
            endDate: number
        }
    }
}


