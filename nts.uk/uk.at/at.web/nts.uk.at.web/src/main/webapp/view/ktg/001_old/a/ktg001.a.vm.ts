module nts.uk.at.view.ktg001.a.viewmodel {
    import block = nts.uk.ui.block;
    import windows = nts.uk.ui.windows;
    import getText = nts.uk.resource.getText;
    import character = nts.uk.characteristics;
    export class ScreenModel {
        text: KnockoutObservable<string>;
        visible: KnockoutObservable<boolean>;
        selectedSwitch: KnockoutObservable<any>;
        constructor() {
            let self = this;
            self.text = ko.observable("");
            self.visible = ko.observable(false);
            self.selectedSwitch = ko.observable(1);
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
                self.selectedSwitch(1);
            }else{
                self.selectedSwitch(cacheCcg008.currentOrNextMonth);
                closureId = cacheCcg008.closureId;
            }
            let param = {
                ym: self.selectedSwitch(),
                closureId: closureId
            };
            service.getData(param).done((data: any) => {
                if (data.approve) {
                    self.text = ko.observable(getText('KTG001_4'));
                    self.visible = ko.observable(true);
                } else {
                    self.text = ko.observable(getText('KTG001_5'));
                    self.visible = ko.observable(false);
                }
                dfd.resolve();
            }).always(function() {
                nts.uk.ui.block.clear();
            });

            return dfd.promise();
        }

//        private reload() {
//            let self = this;
//            let dfd = $.Deferred();
//            // self.selectedSwitch = windows.getShared("currentOrNextMonth");
//            self.selectedSwitch.subscribe(function(value) {
//                character.save('currentOrNextMonth', value);
//                nts.uk.ui.windows.setShared('currentOrNextMonth', value);
//                if (currentOrNextMonth != selectedSwitch) {
//                    service.getData(selectedSwitch).done((data) => {
//                        console.log(data);
//                        if (data) {
//                            self.text = ko.observable(getText('KTG001_4'));
//                            self.visible = ko.observable(true);
//                        } else {
//                            self.text = ko.observable(getText('KTG001_5'));
//                            self.visible = ko.observable(false);
//                        }
//                        dfd.resolve();
//                    }).always(function() {
//                        nts.uk.ui.block.clear();
//                    });
//                }
//            });
//        }

        dailyPerformanceConfirm() {
            window.top.location = window.location.origin + '/nts.uk.at.web/view/kdw/004/a/index.xhtml';
        }
    }
}