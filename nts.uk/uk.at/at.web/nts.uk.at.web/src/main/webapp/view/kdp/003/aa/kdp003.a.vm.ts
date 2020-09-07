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
            showSelectEmpComponent: KnockoutObservable<boolean> = ko.observable(true);
            input: any;
            firstLogin: boolean;
            employeeSelected : any;

            constructor() {
                let self = this;
                self.input = {};
            }

            public seletedEmployee(data): void {
                let self = this;
                console.log(data);
                self.employeeSelected =  data;
            }

            public startPage(infoAdministrator): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                console.log('start ' + infoAdministrator);
                
                
                if (infoAdministrator) {
                    self.firstLogin = false;
                    // sẽ dùng thông tin lưu trong cache để tự động login.
                    
                    
                    
                } else {
                    self.firstLogin = true;
                    setShared('mode', { modeAdmin: true });

                    nts.uk.ui.block.grayout();
                    service.startPage().done((res: IStartPage) => {
                        self.stampSetting(res.stampSetting);
                        // add correction interval
                        self.stampClock.addCorrectionInterval(self.stampSetting().correctionInterval);
                        
                        self.openScreenF();
                        
                        dfd.resolve();
                    }).fail((res) => {
                        nts.uk.ui.dialog.alertError({ messageId: res.messageId }).then(() => {
                            nts.uk.request.jump("com", "/view/ccg/008/a/index.xhtml");
                        });
                    }).always(() => {
                        nts.uk.ui.block.clear();
                    });
                };
                dfd.resolve();
                return dfd.promise();
            }
            
            public openScreenF() {
                let self = this;
                // hiển thị màn hình login.
                subModal('/view/kdp/003/f/index.xhtml', { title: '' }).onClosed(() => {
                    self.openScreenK();
                });

            }

            public openScreenK() {
                let self = this;
                // hiển thị màn hình login.
                subModal('/view/kdp/003/k/index.xhtml', { title: '' }).onClosed(() => {

                });

            }
            
            public loginAuto() {
                let self = this;

            }
            
            public setWidth(){
                let self = this;
                if(!self.showSelectEmpComponent()){
                    $("#tab-1").css("min-width", "945px");
                    $("#tab-2").css("min-width", "945px");
                    $("#tab-3").css("min-width", "945px");
                    $("#tab-4").css("min-width", "945px");
                    $("#tab-5").css("min-width", "945px");
                
                }else{
                    $("#tab-1").css("min-width", "63vmin");
                    $("#tab-2").css("min-width", "63vmin");
                    $("#tab-3").css("min-width", "63vmin");
                    $("#tab-4").css("min-width", "63vmin");
                    $("#tab-5").css("min-width", "63vmin");
                }
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
                nts.uk.ui.windows.setShared("fromScreenBToFScreen", 
                {   passwordRequiredArt : 'se lay tu domain',
                    employeeSelected:     self.employeeSelected,
                    companyCdAndName: '',
                    employeeCodeAndName: ''
                });
                

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