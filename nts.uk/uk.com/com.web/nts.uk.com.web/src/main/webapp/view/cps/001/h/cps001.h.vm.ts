module cps001.h.vm {
    import text = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import error = nts.uk.ui.dialog.alertError;
    import alertError = nts.uk.ui.dialog.alertError;
    import confirm = nts.uk.ui.dialog.confirm;
    import close = nts.uk.ui.windows.close;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import clearError = nts.uk.ui.errors.clearAll;

    let __viewContext: any = window['__viewContext'] || {},
        block = window["nts"]["uk"]["ui"]["block"]["grayout"],
        unblock = window["nts"]["uk"]["ui"]["block"]["clear"],
        invisible = window["nts"]["uk"]["ui"]["block"]["invisible"];

    export class ViewModel {
        columns: KnockoutObservableArray<NtsGridListColumn>;
        items: KnockoutObservableArray<IResvLeaGrantRemNum> = ko.observableArray([]);
        currentItem: KnockoutObservable<string> = ko.observable("");
        leaveExpirationStatus: KnockoutObservableArray<any>;
        resvLeaGrantRemNum: KnockoutObservable<ResvLeaGrantRemNum> = ko.observable(new ResvLeaGrantRemNum(<IResvLeaGrantRemNum>{}));
        enableRemoveBtn: KnockoutObservable<boolean> = ko.observable(true);
        isCreate: KnockoutObservable<boolean> = ko.observable(false);
        ckbAll: KnockoutObservable<boolean> = ko.observable(false);
        itemDefs: any = [];

        nameGrantDate: KnockoutObservable<string> = ko.observable('');
        nameDeadline: KnockoutObservable<string> = ko.observable('');
        nameOverLimitDays: KnockoutObservable<string> = ko.observable('');

        constructor() {
            let self = this;
            self.ckbAll.subscribe((data) => {
                clearError();
                service.getAll(__viewContext.user.employeeId, data).done((data) => {
                    if (data && data.length > 0) {
                        self.items(data);
                        self.currentItem(self.items()[0].id);
                    } else {
                        self.items([]);
                        self.create();
                    }
                    $("#grantDate").focus();
                });
            });
            self.currentItem.subscribe((id: string) => {

                service.getByGrantDate(id).done((curItem) => {
                    self.clearError();
                    self.resvLeaGrantRemNum(new ResvLeaGrantRemNum(<IResvLeaGrantRemNum>curItem));
                    self.setDef();
                    $("#grantDate").focus();
                    if (curItem) {
                        self.enableRemoveBtn(true);
                        self.isCreate(false);
                    }
                });
            });
            self.columns = ko.observableArray([
                { headerText: "", key: 'id', hidden: true },
                { headerText: text("CPS001_118"), key: 'grantDate', width: 100, isDateColumn: true, format: 'YYYY/MM/DD' },
                { headerText: text("CPS001_119"), key: 'deadline', width: 100, isDateColumn: true, format: 'YYYY/MM/DD' },
                { headerText: text("CPS001_120"), key: 'grantDays', width: 70, formatter: self.formatterDate },
                { headerText: text("CPS001_121"), key: 'useDays', width: 70, formatter: self.formatterDate },
                { headerText: text("CPS001_130"), key: 'overLimitDays', width: 70, formatter: self.formatterDate },
                { headerText: text("CPS001_123"), key: 'remainingDays', width: 70, formatter: self.formatterDate },
                { headerText: text("CPS001_129"), key: 'expirationStatus', width: 70, formatter: self.formatterExState }
            ]);
        }
        load(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred(),
                sId = __viewContext.user.employeeId;
            service.getAll(sId, self.ckbAll()).done((data) => {
                if (data && data.length > 0) {
                    self.items(data);
                } else {
                    self.items([]);
                    self.create();
                }
                dfd.resolve();
            });
            return dfd.promise();
        }

        start(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            self.setDef();
            self.load().done(() => {
                if (self.items().length > 0) {
                    self.currentItem(self.items()[0].id);
                }
                dfd.resolve();
            });
            return dfd.promise();
        }

        setDef() {
            let self = this;
            if (self.itemDefs.length > 0) {
                self.setItemValue(self.itemDefs);
            } else {
                service.getItemDef().done((data) => {
                    self.itemDefs = data;
                    self.setItemValue(self.itemDefs);
                });
            }
        }

        setItemValue(data: any): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            $("td[data-itemCode]").each(function() {
                let itemCodes = $(this).attr('data-itemCode');
                if (itemCodes) {
                    let itemCodeArray = itemCodes.split(" ");
                    _.forEach(itemCodeArray, (itemCode) => {
                        let itemDef = _.find(data, (item) => {
                            return item.itemCode == itemCode;
                        });
                        if (itemDef) {
                            if (itemDef.display) {
                                $(this).children().first().html("<label>" + itemDef.itemName + "</label>");
                                let timeType = itemCodeArray[itemCodeArray.length - 1];
                                switch (timeType) {
                                    case "grantDate":
                                        self.nameGrantDate(itemDef.itemName);
                                        break;
                                    case "deadline":
                                        self.nameDeadline(itemDef.itemName);
                                        break;
                                    case "overLimitDays":
                                        self.nameOverLimitDays(itemDef.itemName);
                                        break;
                                }
                            }
                        }
                    });
                }
                dfd.resolve();
            });
            return dfd.promise();
        }

        close() {
            close();
        }

        remove() {
            let self = this;
            nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                block();
                let delItemIndex = _.findIndex(self.items(), (item) => {
                    return item.id == self.resvLeaGrantRemNum().id();
                });
                let selectedId;
                if (delItemIndex == self.items().length - 1) {
                    if (self.items().length > 1) {
                        selectedId = self.items()[delItemIndex - 1].id;
                    }
                } else {
                    selectedId = self.items()[delItemIndex + 1].id;
                }
                service.remove(self.resvLeaGrantRemNum().id()).done(() => {
                    self.load().done(() => {
                        if (self.items().length == 0) {
                            self.create();
                        } else {
                            self.currentItem(selectedId);
                        }
                        self.ckbAll(false);
                        alert({ messageId: "Msg_16" });
                        unblock();
                    });

                }).fail((mes) => {
                    unblock();
                });
            });
        }
        clearError() {
            $("#grantDate").ntsError("clear");
            $("#deadline").ntsError("clear");
            $("#grantDays").ntsError("clear");
            $("#useDays").ntsError("clear");
            $("#overLimitDays").ntsError("clear");
            $("#remainingDays").ntsError("clear");
        }
        register() {
            let self = this;

            $("#grantDate").trigger("validate");
            $("#deadline").trigger("validate");
            $("#grantDays").trigger("validate");
            $("#useDays").trigger("validate");
            $("#overLimitDays").trigger("validate");
            $("#remainingDays").trigger("validate");

            if (!$(".nts-input").ntsError("hasError")) {
                let item = self.resvLeaGrantRemNum(),
                    grantDate = moment.utc(item.grantDate(), "YYYY/MM/DD"),
                    deadline = moment.utc(item.deadline(), "YYYY/MM/DD");
                if ((new Date(deadline._d)) < (new Date(grantDate._d))) {
                    error({ messageId: "Msg_1023" });
                    return;
                }
                if (self.isCreate()) {
                    let currItem = self.items();
                    service.create(grantDate, deadline, item.expirationStatus(),
                        item.grantDays(), item.useDays(), item.overLimitDays(), item.remainingDays()).done(() => {
                            self.load().done(() => {
                                if (self.items().length > 0) {
                                    let newId = _.difference(_.map(self.items(), (i) => { return i.id; }), _.map(currItem, (i) => { return i.id; }));
                                    self.currentItem(newId);
                                }
                                self.ckbAll(false);
                                alert({ messageId: "Msg_15" });
                                unblock();
                            });

                        }).fail((mes) => {
                            unblock();
                        });
                } else {
                    service.update(item.id(), grantDate, deadline, item.expirationStatus(),
                        item.grantDays(), item.useDays(), item.overLimitDays(), item.remainingDays()).done(() => {
                            self.load().done(() => {
                                if (self.items().length > 0) {
                                    self.currentItem(item.id());
                                }
                                self.ckbAll(false);
                                alert({ messageId: "Msg_15" });
                                unblock();
                            });

                        }).fail((mes) => {
                            unblock();
                        });
                }
            }

        }

        create() {
            let self = this;
            self.currentItem("-1");
            self.enableRemoveBtn(false);
            self.isCreate(true);
        }

        formatterDate(value) {
            return value + "日";
        }

        formatterExState(value: number) {
            if (value == 1) {
                return "使用可能";
            } else {
                return "期限切れ";
            }

        }

    }
    class ResvLeaGrantRemNum {
        id: KnockoutObservable<string> = ko.observable("");
        grantDate: KnockoutObservable<string> = ko.observable("");
        deadline: KnockoutObservable<string> = ko.observable("");
        expirationStatus: KnockoutObservable<number> = ko.observable(1);
        grantDays: KnockoutObservable<string> = ko.observable("");
        useDays: KnockoutObservable<string> = ko.observable("");
        overLimitDays: KnockoutObservable<string> = ko.observable("");
        remainingDays: KnockoutObservable<string> = ko.observable("");
        constructor(data: IResvLeaGrantRemNum) {
            let self = this;
            self.id(data && data.id || "");
            self.grantDate(data && data.grantDate || "");
            self.deadline(data && data.deadline || "");
            self.expirationStatus(data == undefined ? 1 : (data.expirationStatus == undefined ? 1 : data.expirationStatus));
            self.grantDays(data && data.grantDays || "");
            self.useDays(data && data.useDays || "");
            self.overLimitDays(data && data.overLimitDays || "");
            self.remainingDays(data && data.remainingDays || "");

            self.grantDate.subscribe((data) => {
                if (!nts.uk.ui.errors.hasError() && data) {
                    service.generateDeadline(moment.utc(data, "YYYY/MM/DD")).done((item) => {
                        self.deadline(item);
                    });
                }
            });
        }
    }

    interface IResvLeaGrantRemNum {
        id: string;
        grantDate: string;
        deadline: string;
        expirationStatus: number;
        grantDays: string;
        useDays: string;
        overLimitDays: string;
        remainingDays: string;
    }

}