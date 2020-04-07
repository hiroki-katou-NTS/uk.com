module nts.uk.at.view.kdp002.a {
    
    export module viewmodel {
        
        export class ScreenModel {
            stampSetting: KnockoutObservable<StampSetting> = ko.observable({});
            stampClock: StampClock = new StampClock();
            stampTab: KnockoutObservable<StampTab> = ko.observable(new StampTab());
            embossModel: KnockoutObservable<EmbossInfoModel> = ko.observable(new EmbossInfoModel());
            stampHistoryGrid: KnockoutObservable<EmbossGridInfo> = ko.observable(new EmbossGridInfo());
            
            constructor() {
                let self = this;
            }

            public startPage(): JQueryPromise<void>  {
               let self = this;
               let dfd = $.Deferred<void>();
                service.startPage().done((res) => {
                    self.stampSetting(res.stampSetting);
                    self.stampTab().bindData(res.stampSetting.pageLayouts);
                    dfd.resolve();
                }); 

                return dfd.promise();
               }

           clickProcess(data: any) : any{
             console.log("CLICKKKKKKKKKKKKK");        
           }

           logObject(obj) {
                return ko.toJSON(obj);
           }

        }
        
    }
}