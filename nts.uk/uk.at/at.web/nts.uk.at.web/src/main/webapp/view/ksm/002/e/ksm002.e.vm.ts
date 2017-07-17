module ksm002.e{
    export module viewmodel {
        import getShared = nts.uk.ui.windows.getShared;
        export class ScreenModel {
            dataSource: KnockoutObservableArray<SpecificDateItemDto>;
            size: KnockoutObservable<number>;
            textKML001_47: KnockoutObservable<string>;
            constructor() {
                var self = this;
                self.dataSource = ko.observableArray([]);
                self.size = ko.observable(nts.uk.ui.windows.getShared('size'));
                self.textKML001_47 = ko.observable(nts.uk.resource.getText('KML001_47'));
                self.start();
            }
            start(){
                var self = this;
                let param: IData  = getShared('KSM002E_PARAM') || { date: null, selectable: [],selecteds: [] };
                console.log(param); 
                service.getSpecificdateByListCode(param.selectable).done(function(data){
                    _.each(data, item => {
                        self.dataSource.push(new SpecificDateItemDto(
                                item.timeItemId, 
                                item.useAtr,
                                item.specificDateItemNo,
                                item.specificName));
                        
                    });
                    console.log(self.dataSource());
                });    
            }
            convert(value: number): boolean{
            if (value == 1) {
                    return true;
                } else {
                    return false;
                }    
            }
            /**
             * process parameter and close dialog 
             */
            submitAndCloseDialog(): void {
                var self = this;
//                if(!vmbase.ProcessHandler.validateDateInput(self.newStartDate(),self.beginStartDate())){
//                    $("#startDateInput").ntsError('set', {messageId:"Msg_102"});
//                } else {
//                    nts.uk.ui.windows.setShared('newStartDate', self.newStartDate());
//                    nts.uk.ui.windows.setShared('copyDataFlag', self.copyDataFlag());
//                    nts.uk.ui.windows.close(); 
//                }
            }
            
            /**
             * close dialog and do nothing
             */
            closeDialog(): void {
                $("#startDateInput").ntsError('clear');
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
            useAtr : KnockoutObservable<boolean>;
            specificDateItemNo : KnockoutObservable<number>;
            specificName : KnockoutObservable<string>;
            constructor(timeItemId : string, useAtr : boolean,specificDateItemNo : number,specificName : string){
                this.timeItemId = ko.observable(timeItemId);
                this.useAtr = ko.observable(useAtr);
                this.specificDateItemNo = ko.observable(specificDateItemNo);
                this.specificName = ko.observable(specificName);
            }
        }
    }
}