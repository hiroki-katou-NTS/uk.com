module nts.uk.at.view.kdp002.a {
    
    export module viewmodel {
        
        export class ScreenModel {
            stampSetting: KnockoutObservable<StampSetting> = ko.observable({});
            stampClock: StampClock = new StampClock();
            stampTab: KnockoutObservable<StampTab> = ko.observable(new StampTab());
            embossModel: KnockoutObservable<EmbossInfoModel> = ko.observable(new EmbossInfoModel());
            stampGrid: KnockoutObservable<EmbossGridInfo> = ko.observable(new EmbossGridInfo());

            constructor() {
                let self = this;
            }

            public startPage(): JQueryPromise<void>  {
               let self = this;
               let dfd = $.Deferred<void>();
                service.startPage()
                .done((res) => {
                    self.stampSetting(res.stampSetting);
                    self.stampTab().bindData(res.stampSetting.pageLayouts);
                    dfd.resolve();
                }).fail((res) => {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId }).then(() => {
                        nts.uk.request.jump("com","/view/ccg/008/a/index.xhtml");
                    });
                }); 

                return dfd.promise();
            }

            public getStampData() {
                let self = this;
                service.getStampData(self.embossModel().dateValue()).done((res) => {
                    self.stampGrid().items(res);
                });
            }

           clickProcess(data: any) : any{
             console.log("CLICKKKKKKKKKKKKK");        
           }

        }
        
    }
}