module nts.uk.at.view.kdp002.a {
    export module viewmodel {

        type STATE = 'state1' | 'state2' | 'state3';

        export class ScreenModel {
            stampSetting: KnockoutObservable<StampSetting> = ko.observable({});
            stampClock: StampClock = new StampClock();
            stampTab: KnockoutObservable<StampTab> = ko.observable(new StampTab());
            stampGrid: KnockoutObservable<EmbossGridInfo> = ko.observable({});
            stampToSuppress: KnockoutObservable<StampToSuppress> = ko.observable({});
            stampResultDisplay: KnockoutObservable<IStampResultDisplay> = ko.observable({});
            serverTime: KnockoutObservable<any> = ko.observable('');
            workManagementMultiple: KnockoutObservable<boolean> = ko.observable(false);
            checkerShowWork2: KnockoutObservable<boolean> = ko.observable(false);
            kdp002: KnockoutObservable<boolean> = ko.observable(true);

            pageComment: KnockoutObservable<string> = ko.observable('');
            commentColor: KnockoutObservable<string> = ko.observable('');

            workUse: KnockoutObservable<boolean> = ko.observable(false);
            workGroup: KnockoutObservable<WorkGroup> = ko.observable(null);

            regionalTime: KnockoutObservable<number> = ko.observable(0);
            workLocationCD: string = null;

            state!: KnockoutComputed<STATE>;
            constructor() {
                const vm = this;

                vm.state = ko.computed({
                    read: () => {
                        const wmm = ko.unwrap(vm.workManagementMultiple);
                        const stampGrid = ko.unwrap(vm.stampGrid);
                        const displayMethod = ko.unwrap(stampGrid.displayMethod);

                        if (displayMethod === 1) {
                            return 'state2';
                        }
                        if (displayMethod === 2 && wmm) {
                            return 'state1';
                        }
                        if (!wmm && displayMethod === 2) {
                            return 'state2';

                        }
                        return 'state3';
                    }
                });
            }

            public startPage(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                nts.uk.ui.block.grayout();

                self.getRegionalTime();
                setTimeout(() => {
                    service.getWorkManagementMultiple().done((result: boolean) => {
                        self.workManagementMultiple(!result);
                        service.startPage({ regionalTimeDifference: ko.unwrap(self.regionalTime) }).done((res: IStartPage) => {
                            self.stampSetting(res.stampSetting);

                            self.stampTab().bindData(res.stampSetting.pageLayouts);
                            self.stampGrid(new EmbossGridInfo(res, ko.unwrap(self.workManagementMultiple), ko.unwrap(self.regionalTime)));

                            self.stampGrid().yearMonth.subscribe((val) => {
                                if (_.get(res, 'stampSetting.historyDisplayMethod') == 2)
                                    self.getTimeCardData();
                            });

                            let stampToSuppress = res.stampToSuppress ? res.stampToSuppress : {};
                            stampToSuppress.isUse = res.stampSetting ? res.stampSetting.buttonEmphasisArt : false;
                            self.stampToSuppress(stampToSuppress);
                            self.stampResultDisplay(res.stampResultDisplay);
                            // add correction interval
                            self.stampClock.addCorrectionInterval(self.stampSetting().correctionInterval);
                            dfd.resolve();
                        }).fail((res) => {
                            nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds }).then(() => {
                                nts.uk.request.jump("com", "/view/ccg/008/a/index.xhtml");
                            });
                        }).always(() => {
                            nts.uk.ui.block.clear();
                        });
                    })
                }, 300);

                return dfd.promise();
            }

            public getRegionalTime() {
                const self = this;
                const vm = new ko.ViewModel();

                vm.$ajax('at/record/stamp/finger/get-ip-address', { contractCode: vm.$user.contractCode }) .done((response) => {
                    var param = { contractCode: vm.$user.contractCode, ipv4Address: response.ipaddress };
                    vm.$ajax('at', 'at/record/kdp/common/get-work-location-regional-time', param)
                        .then((data: GetWorkPlaceRegionalTime) => {
                            if (data.workLocationName !== null && data.workLocationName !== '') {
                                self.regionalTime(data.regional);
                                self.workLocationCD = data.workLocationCD;
                            } else {
                                let inputWorkPlace = {
                                    contractCode: vm.$user.contractCode,
                                    cid: vm.$user.companyCode,
                                    sid: vm.$user.employeeId,
                                    workPlaceId: ""
                                };
                                vm.$ajax('at', 'at/record/kdp/common/get-work-place-regional-time', inputWorkPlace)
                                    .then((data: GetWorkPlaceRegionalTime) => {
                                        if (data) {
                                            self.regionalTime(data.regional);
                                            if (data.workLocationCD !== null && data.workLocationCD !== '') {
                                                self.workLocationCD = data.workLocationCD;
                                            }
                                        }
                                    });
                            }
                        });
                });
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
                    reCalGridWidthHeight();
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
                const { employeeId } = __viewContext.user;
                const { startDate, endDate } = self.stampGrid().dateValue();

                service.getStampData({ startDate, endDate, employeeId }).done((stampDatas) => {
                    self.stampGrid().bindItemData(stampDatas);
                    reCalGridWidthHeight();
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

            public clickBtn1(btn: any, layout: any) {
                const vm = this;
                const view = new ko.ViewModel();

                let stampTime = moment(moment(view.$date.now()).add(ko.unwrap(vm.regionalTime), 'm').toDate()).format("HH:mm");

                nts.uk.ui.block.invisible();
                        let data = {
                            datetime: moment(moment(view.$date.now()).add(ko.unwrap(vm.regionalTime), 'm').toDate()).format('YYYY/MM/DD HH:mm:ss'),
                            authcMethod: 0,
                            stampMeans: 3,
                            reservationArt: btn.btnReservationArt,
                            changeHalfDay: btn.changeHalfDay,
                            goOutArt: btn.goOutArt,
                            setPreClockArt: btn.setPreClockArt,
                            changeClockArt: btn.changeClockArt,
                            changeCalArt: btn.changeCalArt,
                            workGroup: ko.unwrap(vm.workGroup),
                            workLocationCD: vm.workLocationCD
                        };

                        service.getSettingStampCommon().done((result: any) => {
                            vm.workUse(!!result.workUse);

                            service.getEmployeeWorkByStamping({ sid: __viewContext.user.employeeId, workFrameNo: 1, upperFrameWorkCode: '' }).done((res: any) => {
                                if (vm.workUse() == true && res.task.length > 0 && btn.taskChoiceArt == 1) {
                                    view.$window.modal('at', '/view/kdp/002/l/index.xhtml', { employeeId: __viewContext.user.employeeId }).then((works: IWorkGroup) => {

                                        vm.workGroup(works);
                                        data.workGroup = ko.unwrap(vm.workGroup);

                                    }).then(() => {
                                        service.stampInput(data).done((res) => {
                                            if (vm.stampResultDisplay().notUseAttr == 1 && btn.changeClockArt == 1) {
                                                vm.openScreenC(btn, layout);
                                            } else {
                                                vm.openScreenB(btn, layout, stampTime);
                                            }

                                        }).fail((res) => {
                                            nts.uk.ui.dialog.alertError({ messageId: res.messageId });
                                        });
                                    })

                                } else {
                                    service.stampInput(data).done((res) => {
                                        if (vm.stampResultDisplay().notUseAttr == 1 && btn.changeClockArt == 1) {
                                            vm.openScreenC(btn, layout);
                                        } else {
                                            vm.openScreenB(btn, layout, stampTime);
                                        }


                                    }).fail((res) => {
                                        nts.uk.ui.dialog.alertError({ messageId: res.messageId });
                                    });
                                }
                            });
                        })
                        data.workGroup = null;
            }

            public openScreenB(button, layout, stampTime) {
                let self = this;
                const vm = new ko.ViewModel();

                nts.uk.ui.windows.setShared("resultDisplayTime", self.stampSetting().resultDisplayTime);
                nts.uk.ui.windows.setShared("infoEmpToScreenB", {
                    employeeId: __viewContext.user.employeeId,
                    employeeCode: __viewContext.user.employeeCode,
                    mode: Mode.Personal,
                });
                nts.uk.ui.windows.setShared("screenB", {
                    screen: "KDP002"
                });
                vm.$window.modal('at', '/view/kdp/002/b/index.xhtml', { stampTime: stampTime, regionalTime: ko.unwrap(self.regionalTime) }).then(() => {
                    if (self.stampGrid().displayMethod() === 1) {
                        self.getStampData();
                    } else {
                        self.getTimeCardData();
                    }
                    self.reloadHighLight();
                    self.stampToSuppress.valueHasMutated();
                    self.openKDP002T(button, layout);
                });
            }

            public openScreenC(button, layout) {
                const self = this;
                const vm = new ko.ViewModel();

                let data = {
                    pageNo: layout.pageNo,
                    buttonDisNo: button.btnPositionNo
                }

                service.getError(data).done((res) => {
                    nts.uk.ui.windows.setShared('KDP010_2C', self.stampResultDisplay().displayItemId, true);
                    nts.uk.ui.windows.setShared("infoEmpToScreenC", {
                        employeeId: __viewContext.user.employeeId,
                        employeeCode: __viewContext.user.employeeCode,
                        mode: Mode.Personal,
                        error: res,
                        regionalTime: ko.unwrap(self.regionalTime)
                    });
                    nts.uk.ui.windows.setShared("screenC", {
                        screen: "KDP002"
                    });
                    nts.uk.ui.windows.sub.modal('/view/kdp/002/c/index.xhtml')
                        .onClosed(function (): any {
                            if (self.stampGrid().displayMethod() === 1) {
                                self.getStampData();
                            } else {
                                self.getTimeCardData();
                            }
                            self.reloadHighLight();
                            self.stampToSuppress.valueHasMutated();
                            self.openKDP002T(button, layout);
                        });
                });
            }

            public openKDP002T(button: ButtonSetting, layout) {
                const vm = new ko.ViewModel();
                let data = {
                    pageNo: layout.pageNo,
                    buttonDisNo: button.btnPositionNo
                }

                service.getError(data).done((res) => {
                    if (res && res.dailyAttdErrorInfos && res.dailyAttdErrorInfos.length > 0) {
                        nts.uk.ui.windows.setShared('KDP010_2T', res, true);

                        nts.uk.ui.windows.sub.modal('/view/kdp/002/t/index.xhtml').onClosed(function (): any {
                            let returnData = nts.uk.ui.windows.getShared('KDP010_T');
                            if (!returnData.isClose && returnData.errorDate) {
                                // T1	??????????????????????????????????????????
                                // ????????????????????????
                                let transfer = returnData.btn.transfer;
                                vm.$jump('at', returnData.btn.screen, { baseDate: transfer.appDate });
                                // nts.uk.request.jump(returnData.btn.screen, transfer);
                            }
                        });
                    }
                });
            }

            public reloadHighLight() {
                let self = this;
                if (self.stampToSuppress().isUse) {
                    service.getHighlightSetting().done((res) => {
                        res.isUse = self.stampToSuppress().isUse;
                        self.stampToSuppress(res);
                    });
                }
            }

        }
    }
    export enum Mode {
        Personal = 1, // ??????
        Shared = 2  // ?????? 
    }

}
var paramSize = 0;
let resize = () => {

    var bottomMasterWrapper = $('#master-wrapper')[0].getBoundingClientRect().bottom;
    var topStampInfo = $('#stamp-info')[0].getBoundingClientRect().top;
    var h = bottomMasterWrapper - topStampInfo - 98;

    let stampBtnHeight = (h < 48 ? 48 : h) + 'px';
    const $hgrid = $('#stamp-history-list');
    const $cgrid = $('#time-card-list');
    if (paramSize !== h) {
        paramSize = h;
        if ($hgrid.data('igGrid')) {
            $hgrid.igGrid("option", "height", stampBtnHeight);
            $hgrid.data("height", stampBtnHeight);
        }
        if ($cgrid.data('igGrid')) {
            $cgrid.igGrid("option", "height", stampBtnHeight);
            $cgrid.data("height", stampBtnHeight);
        }
        return true;
    } else {
        return false;
    }
}

let reCalGridWidthHeight = () => {
    let dfd = $.Deferred<void>();

    if ($('#stamp-info')[0]) {
        setTimeout(() => {
            dfd.resolve(resize());
        });

    }
    return dfd.promise();
}

interface GetWorkPlaceRegionalTime {
    workPlaceId: string;
    workLocationCD: string;
    workLocationName: string;
    regional: number;
}