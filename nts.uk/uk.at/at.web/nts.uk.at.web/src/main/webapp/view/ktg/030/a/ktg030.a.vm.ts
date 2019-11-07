module nts.uk.at.view.ktg030.a.viewmodel {
    import block = nts.uk.ui.block;
    import windows = nts.uk.ui.windows;
    import getText = nts.uk.resource.getText;
    import character = nts.uk.characteristics;
    export class ScreenModel {
        text: KnockoutObservable<string>;
        visible: KnockoutObservable<boolean>;
        selectedSwitch : KnockoutObservable<any>;
        constructor() {
            let self = this;
            self.text = ko.observable("");
            self.visible = ko.observable(false);
            self.selectedSwitch =ko.observable(0);
        }

        /**
         * startPage
         */
        public startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            block.invisible();
            let cacheCcg008 = windows.getShared("cache");
            let closureId = 1;
            if(!cacheCcg008 || !cacheCcg008.currentOrNextMonth){
                self.selectedSwitch(0);
            }else{
                self.selectedSwitch(cacheCcg008.currentOrNextMonth);
                closureId = cacheCcg008.closureId;
            }
            let param = {
                ym: self.selectedSwitch(),
                closureId: closureId
            };
            service.getData(param).done((data) => {
                if (data.approve) {
                    self.text = ko.observable(getText('KTG030_4'));
                    self.visible = ko.observable(true);
                } else {
                    self.text = ko.observable(getText('KTG030_5'));
                    self.visible = ko.observable(false);
                }
                dfd.resolve();
            }).always(function() {
                nts.uk.ui.block.clear();
            });
           
            return dfd.promise();
        }

        monthPerformanceConfirm() {
           window.top.location = window.location.origin + '/nts.uk.at.web/view/kmw/003/a/index.xhtml?initmode=2';
        }
        
       
    }
}