module nts.uk.at.view.kaf022.o.viewmodel {
    import alert = nts.uk.ui.dialog.alert;
    import getText = nts.uk.resource.getText;

    export class ScreenModelO {
        flexWorkOptions: KnockoutObservableArray<ItemModel>;

        // フレックス勤務者区分
        selectedFlexWorkAtr: KnockoutObservable<number>;

        overtimeAppOptions: KnockoutObservableArray<ItemModel>;

        // 残業申請区分
        selectedOvertimeAppAtr: KnockoutObservable<number>;

        columns = ko.observableArray([
            { headerText: "No", key: 'code', width: 150, hidden: true },
            { headerText: getText("KAF022_691"), key: 'name', width: 150 }
        ]);

        // 残業枠
        overtimeWorkFrames: KnockoutObservableArray<OTWorkFrame>;
        overTimeQuotaSettings: KnockoutObservableArray<OTQuota>;

        manualChange: boolean = false;

        allChecked: KnockoutObservable<boolean>;

        enableRegister: KnockoutObservable<boolean>;

        constructor() {
            let self = this;
            self.flexWorkOptions = ko.observableArray([
                new ItemModel(1, "フレックス勤務者"),
                new ItemModel(0, "フレックス勤務者以外")
            ]);
            self.selectedFlexWorkAtr = ko.observable(1);

            self.overtimeAppOptions = ko.observableArray([
                new ItemModel(0, "早出残業"),
                new ItemModel(1, "通常残業"),
                new ItemModel(2, "早出残業・通常残業")
            ]);
            self.selectedOvertimeAppAtr = ko.observable(0);

            self.overtimeWorkFrames = ko.observableArray([]);

            self.overTimeQuotaSettings = ko.observableArray([]);

            $("#fixed-table-o4").ntsFixedTable({});

            self.selectedOvertimeAppAtr.subscribe(value => {
                self.manualChange = true;
                // const targetFrames = self.overTimeQuotaSettings().filter(q => q.overtimeAtr == value && q.flexAtr == self.selectedFlexWorkAtr()).map(q => q.overTimeFrame);
                nts.uk.ui.block.invisible();
                self.getData(value, self.selectedFlexWorkAtr()).done(() => {
                    self.overtimeWorkFrames().forEach(frame => {
                        frame.checked(self.overTimeQuotaSettings().map(q => q.overTimeFrame).indexOf(frame.no) >= 0);
                    });
                    self.manualChange = false;
                }).fail(() => {
                    self.manualChange = false;
                }).always(() => {
                    nts.uk.ui.block.clear();
                });
            });
            self.selectedFlexWorkAtr.subscribe(value => {
                self.manualChange = true;
                // const targetFrames = self.overTimeQuotaSettings().filter(q => q.overtimeAtr == self.selectedOvertimeAppAtr() && q.flexAtr == value).map(q => q.overTimeFrame);
                nts.uk.ui.block.invisible();
                self.getData(self.selectedOvertimeAppAtr(), value).done(() => {
                    self.overtimeWorkFrames().forEach(frame => {
                        frame.checked(self.overTimeQuotaSettings().map(q => q.overTimeFrame).indexOf(frame.no) >= 0);
                    });
                    self.manualChange = false;
                }).fail(() => {
                    self.manualChange = false;
                }).always(() => {
                    nts.uk.ui.block.clear();
                });
            });

            self.allChecked = ko.pureComputed({
                read: function () {
                    return self.overtimeWorkFrames().length > 0 && self.overtimeWorkFrames().filter(m => m.checked()).length == self.overtimeWorkFrames().length;
                },
                write: function (value) {
                    if (value) {
                        self.overtimeWorkFrames().forEach(m => m.checked(true));
                    } else {
                        self.overtimeWorkFrames().forEach(m => m.checked(false));
                    }
                },
                owner: self
            });

            self.enableRegister = ko.computed(() => {
                return self.overTimeQuotaSettings()
                    .filter(q => q.overtimeAtr == self.selectedOvertimeAppAtr() && q.flexAtr == self.selectedFlexWorkAtr())
                    .length > 0;
            });
        }

        handleCheck = (checked: boolean, frameNo: number): void => {
            const self = this;
            if (!self.manualChange) {
                if (checked) {
                    self.overTimeQuotaSettings.push(new OTQuota(self.selectedOvertimeAppAtr(), self.selectedFlexWorkAtr(), frameNo));
                } else {
                    self.overTimeQuotaSettings.remove(q => {
                        return q.overtimeAtr == self.selectedOvertimeAppAtr()
                            && q.flexAtr == self.selectedFlexWorkAtr()
                            && q.overTimeFrame == frameNo;
                    });
                }
            }
        };

        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            nts.uk.ui.block.invisible();
            self.getData(self.selectedOvertimeAppAtr(), self.selectedFlexWorkAtr()).done(() => {
                service.getOTFrames().done((otFrames: Array<any>) => {
                    self.overtimeWorkFrames(otFrames.map(f => {
                        return new OTWorkFrame(!!_.find(self.overTimeQuotaSettings(), s => s.overTimeFrame == f.overtimeWorkFrNo), f.overtimeWorkFrNo, f.overtimeWorkFrName, self.handleCheck);
                    }));
                    dfd.resolve();
                }).fail((error: any) => {
                    dfd.reject();
                    alert(error);
                }).always(() => {
                    nts.uk.ui.block.clear();
                });
            });
            return dfd.promise();
        }

        getData(overtimeAtr: number, flexWorkAtr: number): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            service.getOTQuota(overtimeAtr, flexWorkAtr).done((otQuotaSettings: Array<any>) => {
                self.overTimeQuotaSettings(otQuotaSettings.map(q => new OTQuota(q.overtimeAtr, q.flexAtr, q.overTimeFrame)));
                dfd.resolve();
            }).fail((error: any) => {
                dfd.reject();
                alert(error);
            });
            return dfd.promise();
        }

        saveOTQuotaSet() {
            const self = this;
            nts.uk.ui.block.invisible();
            service.registerOTQuota(ko.toJS(self.overTimeQuotaSettings)).done(() => {
                nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                    // self.closeDialog();
                });
            }).fail(error => {
                alert(error);
            }).always(() => {
                nts.uk.ui.block.clear();
            });
        }

        closeDialog() {
            nts.uk.ui.windows.close();
        }
    }

    class ItemModel {
        code: number;
        name: string;
        constructor(value: number, label: string) {
            this.code = value;
            this.name = label;
        }
    }

    class OTWorkFrame {
        checked: KnockoutObservable<boolean>;
        no: number;
        name: string; // 残業枠名称
        constructor(checked: boolean, no: number, name: string, handleCheck: any) {
            this.checked = ko.observable(checked);
            this.no = no;
            this.name = name;
            this.checked.subscribe(value => {
                handleCheck(value, this.no);
            });
        }
    }

    class OTQuota {
        overtimeAtr: number; // 残業申請区分
        flexAtr: number; // フレックス勤務者区分
        overTimeFrame: number; // 対象残業枠
        constructor(overtimeAtr: number, flexAtr: number, overTimeFrame: number) {
            this.overtimeAtr = overtimeAtr;
            this.flexAtr = flexAtr;
            this.overTimeFrame = overTimeFrame;
        }
    }

}




