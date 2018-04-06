module nts.uk.at.view.ktg002.a.viewmodel {
    import block = nts.uk.ui.block;
    import windows = nts.uk.ui.windows;
    import getText = nts.uk.resource.getText;
    export class ScreenModel {

        text: KnockoutObservable<string>;
        visible: KnockoutObservable<boolean>;
        constructor() {
            let self = this;
            self.text = ko.observable("");
            self.visible = ko.observable(false);
        }
        /**
         * startPage
         */
        public startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            service.getData().done((data) => {
                //console.log(data);
                if(data){
                    self.text = ko.observable(getText('KTG002_4'));
                    self.visible = ko.observable(true);
                }else{
                    self.text = ko.observable(getText('KTG002_5'));
                    self.visible = ko.observable(false);
                }
            dfd.resolve();
            });
            return dfd.promise();
        }
        dailyPerformanceConfirm() {
            nts.uk.request.jump("/view/cmm/045/a/index.xhtml?a=1", {});
        }
    }
}