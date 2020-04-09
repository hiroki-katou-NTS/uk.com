module nts.uk.at.view.kdp002.a {

    export module viewmodel {

        export class ScreenModel {
            stampSetting: KnockoutObservable<StampSetting> = ko.observable({});
            stampClock: StampClock = new StampClock();
            stampTab: KnockoutObservable<StampTab> = ko.observable(new StampTab());
            stampGrid: KnockoutObservable<EmbossGridInfo> = ko.observable({});

            constructor() {
                let self = this;
            }

            public startPage(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                service.startPage()
                    .done((res) => {
                        self.stampSetting(res.stampSetting);
                        self.stampTab().bindData(res.stampSetting.pageLayouts);
                        self.stampGrid(new EmbossGridInfo(res));
                        dfd.resolve();
                    }).fail((res) => {
                        nts.uk.ui.dialog.alertError({ messageId: res.messageId }).then(() => {
                            nts.uk.request.jump("com", "/view/ccg/008/a/index.xhtml");
                        });
                    });

                return dfd.promise();
            }

            public getStampData() {
                nts.uk.ui.errors.clearAll();
                $(".nts-input").trigger("validate");
                if (nts.uk.ui.errors.hasError()) {
                    return;
                }

                let self = this;
                nts.uk.ui.block.grayout();
                service.getStampData(self.stampGrid().dateValue()).done((stampDatas) => {
                    self.stampGrid().bindItemData(stampDatas);
                }).fail((res) => {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId });
                }).always(() => {
                    nts.uk.ui.block.clear();
                });
            }

            public getPageLayout(pageNo: number) {
                let self = this;
                let layout = _.find(self.stampTab().layouts(), (ly) => { return ly.pageNo === pageNo }); 
        
                if(layout) {
                    let btnSettings = layout.buttonSettings;
                    btnSettings.forEach(btn => {
                        if(btn.btnPositionNo == 1) {
                            btn.onClick = self.clickBtn1;
                        }
                        if(btn.btnPositionNo == 2) {
                            btn.onClick = self.clickBtn2;
                        }
                    });
                    layout.buttonSettings = btnSettings;
                }
        
                return layout;
            }
        
            public clickBtn1() {
                nts.uk.ui.windows.sub.modal('/view/kdp/002/b/index.xhtml').onClosed(function (): any {
                    console.log("Lam cai gi thi lam di ko thi thoi ko lam nua");
                }); 
            }
        
            public clickBtn2() {
                nts.uk.ui.windows.sub.modal('/view/kdp/002/c/index.xhtml').onClosed(function (): any {
                    console.log("Lam cai gi thi lam di ko thi thoi ko lam nua");
                }); 
            }

        }

    }
}