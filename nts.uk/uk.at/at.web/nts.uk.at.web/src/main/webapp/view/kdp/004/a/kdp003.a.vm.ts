module nts.uk.at.view.kdp003.a {

    export module viewmodel {
        import modal = nts.uk.ui.windows.sub.modal;
        import setShared = nts.uk.ui.windows.setShared;
        import getShared = nts.uk.ui.windows.getShared;
        import showDialog = nts.uk.ui.dialog;
        import text = nts.uk.resource.getText;
        import format = nts.uk.text.format;
        import subModal = nts.uk.ui.windows.sub.modal;
        import hasError = nts.uk.ui.errors.hasError;
        import info = nts.uk.ui.dialog.info;
        import alert = nts.uk.ui.dialog.alert;
        import confirm = nts.uk.ui.dialog.confirm;
        import jump = nts.uk.request.jump;

        const __viewContext: any = window['__viewContext'] || {},
            block = window["nts"]["uk"]["ui"]["block"]["grayout"],
            unblock = window["nts"]["uk"]["ui"]["block"]["clear"],
            invisible = window["nts"]["uk"]["ui"]["block"]["invisible"];
        
        export class ScreenModel {
            stampSetting: KnockoutObservable<StampSetting> = ko.observable({});
            stampClock: StampClock = new StampClock();
            stampTab: KnockoutObservable<StampTab> = ko.observable(new StampTab());
            stampGrid: KnockoutObservable<EmbossGridInfo> = ko.observable({});
            stampToSuppress: KnockoutObservable<StampToSuppress> = ko.observable({});
            stampResultDisplay: KnockoutObservable<IStampResultDisplay> = ko.observable({});
            serverTime: KnockoutObservable<any> = ko.observable('');
            input: any;
            administratorMode: boolean;

            constructor() {
                let self = this;
                self.input = {};
            }

            public seletedEmployee(data): void {
                console.log(data);
            }

            public startPage(infoAdministrator): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                console.log('start ' + infoAdministrator);
                if (infoAdministrator) {
                    self.administratorMode = true;
                } else {
                    self.administratorMode = false;
                    subModal('/view/kdp/004/f/index.xhtml', { title: '' }).onClosed(() => {
                        dfd.resolve();
                    });
                }

                /*service.startPage().done((res: IStartPage) => {
                        self.stampSetting(res.stampSetting);
                        self.stampTab().bindData(res.stampSetting.pageLayouts);
                        self.stampGrid(new EmbossGridInfo(res));
                        self.stampGrid().yearMonth.subscribe((val) => {
                            self.getTimeCardData();
                        });
                        let stampToSuppress = res.stampToSuppress;
                        stampToSuppress.isUse = res.stampSetting.buttonEmphasisArt;
                        self.stampToSuppress(stampToSuppress);
                        self.stampResultDisplay(res.stampResultDisplay);
                        // add correction interval
                        self.stampClock.addCorrectionInterval(self.stampSetting().correctionInterval);
                        dfd.resolve();
                    }).fail((res) => {
                        nts.uk.ui.dialog.alertError({ messageId: res.messageId }).then(() => {
                            nts.uk.request.jump("com", "/view/ccg/008/a/index.xhtml");
                        });
                    });*/

                return dfd.promise();
            }

            public getTimeCardData() {
                nts.uk.ui.errors.clearAll();
                $(".nts-input").trigger("validate");
                if (nts.uk.ui.errors.hasError()) {
                    return;
                }

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

                if (layout) {
                    let btnSettings = layout.buttonSettings;
                    btnSettings.forEach(btn => {
                        btn.onClick = self.clickBtn1;
                    });
                    layout.buttonSettings = btnSettings;
                }

                return layout;
            }

            public clickBtn1(vm, layout) {
                let button = this;
                nts.uk.request.syncAjax("com", "server/time/now/").done((res) => {
                    let data = {
                        datetime: moment.utc(res).format('YYYY/MM/DD HH:mm:ss'),
                        authcMethod: 0,
                        stampMeans: 3,
                        reservationArt: button.btnReservationArt,
                        changeHalfDay: button.changeHalfDay,
                        goOutArt: button.goOutArt,
                        setPreClockArt: button.setPreClockArt,
                        changeClockArt: button.changeClockArt,
                        changeCalArt: button.changeCalArt
                    };
                    service.stampInput(data).done((res) => {
                        if (vm.stampResultDisplay().notUseAttr == 1 && (button.changeClockArt == 1 || button.changeClockArt == 9)) {
                            vm.openScreenC(button, layout);
                        } else {
                            vm.openScreenB(button, layout);
                        }
                    }).fail((res) => {
                        nts.uk.ui.dialog.alertError({ messageId: res.messageId });
                    });
                });
            }

            public openScreenB(button, layout) {
                let self = this;


            }

            public openScreenC(button, layout) {
                let self = this;

            }

            public openKDP002T(button: ButtonSetting, layout) {
                let data = {
                    pageNo: layout.pageNo,
                    buttonDisNo: button.btnPositionNo
                }
                service.getError(data).done((res) => {
                    if (res && res.dailyAttdErrorInfos && res.dailyAttdErrorInfos.length > 0) {
                        nts.uk.ui.windows.setShared('KDP010_2T', res, true);
                        nts.uk.ui.windows.sub.modal('/view/kdp/002/t/index.xhtml').onClosed(function(): any {
                            let returnData = nts.uk.ui.windows.getShared('KDP010_T');
                            if (!returnData.isClose && returnData.errorDate) {
                                console.log(returnData);
                                // T1   打刻結果の取得対象項目の追加
                                // 残業申請（早出）
                                let transfer = returnData.btn.transfer;
                                nts.uk.request.jump(returnData.btn.screen, transfer);
                            }
                        });
                    }
                });
            }

            public reCalGridWidthHeight() {
                let windowHeight = window.innerHeight - 250;
                $('#stamp-history-list').igGrid("option", "height", windowHeight);
                $('#time-card-list').igGrid("option", "height", windowHeight);
                $('#content-area').css('height', windowHeight + 109);
            }

        }

    }
    enum Mode {
        Personal = 1, // 個人
        Shared = 2  // 共有 
    }
}