module nts.uk.at.view.ktg030.a.viewmodel {
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
            block.invisible();
            service.getData().done((data) => {
                if (data) {
                    self.text = ko.observable(getText('KTG030_4'));
                    self.visible = ko.observable(true);
                } else {
                    self.text = ko.observable(getText('KTG030_5'));
                    self.visible = ko.observable(false); 
                }
                dfd.resolve();
            }).always(function () {
                nts.uk.ui.block.clear();   
            });
            return dfd.promise();
        }

        monthPerformanceConfirm() {
           window.top.location = window.location.origin + '/nts.uk.at.web/view/kmw/003/a/index.xhtml';
        }
    }
}