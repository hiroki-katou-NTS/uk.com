module nts.uk.at.view.kdp002.a {

    export module viewmodel {

        export class ScreenModel {
            stampSetting: KnockoutObservable<StampSetting> = ko.observable({});
            stampClock: StampClock = new StampClock();
            stampTab: KnockoutObservable<StampTab> = ko.observable(new StampTab());
            stampGrid: KnockoutObservable<EmbossGridInfo> = ko.observable({});
            stampToSuppress: KnockoutObservable<StampToSuppress> = ko.observable({});
            constructor() {
                let self = this;
            }

            public startPage(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                service.startPage()
                    .done((res: IStartPage) => {
                        self.stampSetting(res.stampSetting);
                        self.stampTab().bindData(res.stampSetting.pageLayouts);
                        self.stampGrid(new EmbossGridInfo(res));
                        self.stampGrid().yearMonth.subscribe((val) => {
                           self.getTimeCardData();
                        });
                        let stampToSuppress = res.stampToSuppress;
                        stampToSuppress.isUse = res.stampSetting.buttonEmphasisArt;
                        self.stampToSuppress(stampToSuppress);
                        dfd.resolve();
                    }).fail((res) => {
                        nts.uk.ui.dialog.alertError({ messageId: res.messageId }).then(() => {
                            nts.uk.request.jump("com", "/view/ccg/008/a/index.xhtml");
                        });
                    });

                return dfd.promise();
            }

            public getTimeCardData() {
                let self = this;
                nts.uk.ui.block.grayout();
                let data = {
                    date: self.stampGrid().yearMonth() + '/15'
                };
                service.getTimeCardData(data).done((timeCard) => {
                    self.stampGrid().bindItemData(timeCard.listAttendances);
                }).fail((res) => {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId });
                }).always(() => {
                    nts.uk.ui.block.clear();
                });
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
                        btn.onClick = self.clickBtn1;
                    });
                    layout.buttonSettings = btnSettings;
                }
        
                return layout;
            }
        
            public clickBtn1(vm) {
                let button = this;
                let data = {
                    datetime: moment().format('YYYY/MM/DD HH:mm:ss'),
                    authcMethod:0,
                    stampMeans:3,
                    reservationArt: button.btnReservationArt,
                    changeHalfDay: button.changeHalfDay,
                    goOutArt: button.goOutArt,
                    setPreClockArt: button.setPreClockArt,
                    changeClockArt: button.changeClockArt,
                    changeCalArt: button.changeCalArt
                };
                service.stampInput(data).done((res) => {
                    nts.uk.ui.windows.setShared("resultDisplayTime",  vm().stampSetting().resultDisplayTime);
                    nts.uk.ui.windows.sub.modal('/view/kdp/002/b/index.xhtml').onClosed(() => {
                    //    $('#get-stamp-data').trigger('click');
                        vm().getStampData();
                    }); 
                }).fail((res) => {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId });
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