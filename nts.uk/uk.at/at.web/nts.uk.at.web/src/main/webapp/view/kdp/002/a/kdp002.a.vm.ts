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

            public startPage(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                service.startPage()
                    .done((res) => {
                        self.stampSetting(res.stampSetting);
                        self.stampTab().bindData(res.stampSetting.pageLayouts);
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
                service.getStampData(self.embossModel().dateValue()).done((stampDatas) => {
                    let idx = 1;
                    stampDatas.forEach(stampData => {
                        stampData.code = ++idx;
                        stampData.stampDate = nts.uk.time.applyFormat("Short_YMDW", stampData.stampDate);
                        stampData.stampHowAndTime = "<div class='inline-bl'>" + stampData.stampHow + "</div>" + stampData.stampTime;
                    });
                    self.stampGrid().items(stampDatas);
                }).fail((res) => {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId });
                }).always(() => {
                    nts.uk.ui.block.clear();
                });
            }

            clickProcess(data: any): any {
                console.log("CLICKKKKKKKKKKKKK");
            }

        }

    }
}