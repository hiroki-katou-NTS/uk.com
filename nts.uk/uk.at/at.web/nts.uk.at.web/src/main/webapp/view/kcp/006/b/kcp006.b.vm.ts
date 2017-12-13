module nts.uk.at.view.kcp006.b.viewmodel {
    export class ScreenModelB {
        eventName: KnockoutObservable<string>;
        date: string;
        companyEvent: KnockoutObservable<string>;
        workplaceEvent: KnockoutObservable<string>;
        workplaceId: string;
        workplaceName: string;

        constructor(data) {
            let self = this;
            self.date = moment(data.date, "YYYY-MM-DD").format("YYYY/MM/DD");
            self.workplaceId = data.workplaceId;
            self.workplaceName = data.workplaceName;
            self.companyEvent = ko.observable("");
            self.workplaceEvent = ko.observable("");
        }

        start() {
            let self = this;
            let dfdGetEvent = $.Deferred<any>();
            if (self.workplaceId == "0") {
                service.getCompanyEvent([moment(self.date, "YYYY/MM/DD").format("YYYYMMDD")])
                    .done((res: model.EventObj) => {
                        if (res && res.length > 0) {
                            self.companyEvent(res[0].name);
                        } else {
                            self.companyEvent("");
                        }
                        dfdGetEvent.resolve();
                    })
                    .fail((res) => {

                    });
            } else {
                service.getCompanyEvent([moment(self.date, "YYYY/MM/DD").format("YYYYMMDD")])
                    .done((res: model.EventObj) => {
                        if (res && res.length > 0) {
                            self.companyEvent(res[0].name);
                        } else {
                            self.companyEvent("");
                        }
                    })
                    .fail((res) => {

                    }).then(() => {
                        service.getWorkplaceEvent({ workplaceId: self.workplaceId, lstDate: [moment(self.date, "YYYY/MM/DD").format("YYYYMMDD")] })
                            .done((res: model.EventObj) => {
                                if (res && res.length > 0) {
                                    self.workplaceEvent(res[0].name)
                                } else {
                                    self.workplaceEvent("");
                                }
                                dfdGetEvent.resolve();
                            })
                            .fail((res) => {

                            })
                    });
            }
            return dfdGetEvent.promise();
        }

        addEvent() {
            let self = this;
            if (self.workplaceId == "0") {
                if (self.companyEvent() && self.companyEvent().trim() != "") {
                    let command = {
                        date: moment(self.date, "YYYY/MM/DD").utc().toISOString(),
                        eventName: self.companyEvent()
                    };
                    service.addCompanyEvent(command).done(() => {
                        self.start();
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                    }).fail((res) => {

                    });
                } else {
                    $("#companyEvent").focus();
                }
            } else {
                if (self.workplaceEvent() && self.workplaceEvent().trim() != "") {
                    let command = {
                        workplaceId: self.workplaceId,
                        date: moment(self.date, "YYYY/MM/DD").utc().toISOString(),
                        eventName: self.workplaceEvent()
                    };
                    service.addWorkplaceEvent(command).done(() => {
                        self.start();
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                    }).fail((res) => {

                    });
                } else {
                    $("#workplaceEvent").focus();
                }
            }
        }

        removeEvent() {
            let self = this;
            nts.uk.ui.dialog.confirm({ messageId: "Msg_18" })
                .ifYes(() => {
                    if (self.workplaceId == "0") {
                        let command = {
                            date: moment(self.date, "YYYY/MM/DD").utc().toISOString(),
                            eventName: self.companyEvent()
                        };
                        service.removeCompanyEvent(command).done(() => {
                            self.start();
                            nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                        }).fail((res) => {

                        });
                    } else {
                        let command = {
                            workplaceId: self.workplaceId,
                            date: moment(self.date, "YYYY/MM/DD").utc().toISOString(),
                            eventName: self.workplaceEvent()
                        };
                        service.removeWorkplaceEvent(command).done(() => {
                            self.start();
                            nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                        }).fail((res) => {

                        });
                    }
                });
        }

        closeDialog() {
            nts.uk.ui.windows.close();
        }
    }
}