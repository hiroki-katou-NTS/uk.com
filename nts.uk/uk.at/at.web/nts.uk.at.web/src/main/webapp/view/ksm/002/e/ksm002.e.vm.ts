module ksm002.e{
    export module viewmodel {
        import getShared = nts.uk.ui.windows.getShared;
        import setShared = nts.uk.ui.windows.setShared;
        export class ScreenModel {
            dataSource: KnockoutObservableArray<SpecificDateItemDto>;
            date: string;
            selectedCodes: Array<string>;
            size: number;
            constructor() {
                var self = this;
                self.dataSource = ko.observableArray([]);
                self.size = 780;
                self.startPage();
            }
            /**
             * start page: getShared param and load data
             */
            startPage(){
                nts.uk.ui.block.invisible();
                var self = this;
                var dfd = $.Deferred();
                let param: IData  = getShared('KSM002_E_PARAM') || { date: null, selectable: [],selecteds: [] };
                self.date = param.date;
                self.selectedCodes = param.selecteds;
                service.getSpecificdateByListCode(param.selectable).done(function(data){
                    let lstData =  _.orderBy(data, ["specificDateItemNo"], ["asc"]);
                    _.each(lstData, item => {
                        self.dataSource.push(new SpecificDateItemDto(
                                item.specificDateItemNo, 
                                param.selecteds.indexOf(item.specificDateItemNo) >= 0 || param.selecteds.indexOf(item.specificName) >= 0 ? 1 : 0,
                                item.specificDateItemNo,
                                item.specificName));
                        
                    });
                    if(param.selecteds != []){
                        _.each(param.selecteds, selectedCode =>{
                            _.find(self.dataSource(), function(items){
                                if(items.specificDateItemNo() == selectedCode) items.useAtr(1);
                            });
                        });
                    }
                    $('#specificItem_0').focus();
                    nts.uk.ui.block.clear();
                    dfd.resolve();
                });    
            }
            /**
             * process parameter and close dialog 
             */
            submitAndCloseDialog(): void {
                var self = this;
                let selectedCodes: any = [];
                _.each(self.dataSource(), item => {
                    if(item.useAtr()== 1){
                        selectedCodes.push(item.specificDateItemNo());
                    }
                });
                setShared('KSM002E_VALUES', {date: self.date,selecteds: selectedCodes});
                nts.uk.ui.windows.close(); 
            }
            
            /**
             * close dialog and setShared
             */
            closeDialog(): void {
                var self = this;
                setShared('KSM002E_VALUES', {date: self.date,selecteds: self.selectedCodes});
                nts.uk.ui.windows.close();   
            }
        }
        interface IData {
            date: any,
            selectable: Array<any>,
            selecteds: Array<any>
        }
        class SpecificDateItemDto{
            timeItemId : KnockoutObservable<string>;
            useAtr : KnockoutObservable<number>;
            specificDateItemNo : KnockoutObservable<number>;
            specificName : KnockoutObservable<string>;
            constructor(timeItemId : string, useAtr : number,specificDateItemNo : number,specificName : string){
                this.timeItemId = ko.observable(timeItemId);
                this.useAtr = ko.observable(useAtr);
                this.specificDateItemNo = ko.observable(specificDateItemNo);
                this.specificName = ko.observable(specificName);
            }
        }
    }
}