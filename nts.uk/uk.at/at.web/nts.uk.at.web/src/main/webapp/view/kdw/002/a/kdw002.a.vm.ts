module nts.uk.at.view.kdw002.a {
    export module viewmodel {
        import href = nts.uk.request.jump;
        import getText = nts.uk.resource.getText;
        import infor = nts.uk.ui.dialog.info;
        export class ScreenModel {
            headerColorValue: KnockoutObservable<string>;
            unitRoundings: KnockoutObservableArray<any>;
            selectedOption: KnockoutObservable<any>;
            attendanceItems: KnockoutObservableArray<any>;
            aICurrentCode: KnockoutObservable<any>;
            attendanceItemColumn: KnockoutObservableArray<any>;
            txtItemId: KnockoutObservable<any>;
            txtItemName: KnockoutObservable<any>;
            timeInputCurrentCode: KnockoutObservable<any>;
            linebreak: KnockoutObservable<any>;
            timeInputEnable: KnockoutObservable<boolean>;
            roundingUnitValue: KnockoutObservable<number>;
            unit: KnockoutObservable<number>;
            frameCategory: KnockoutObservable<number>;
            //
            isDaily: boolean;
            isSave: KnockoutObservable<boolean>;
            sideBar: KnockoutObservable<number>;
            constructor(dataShare: any) {
                var self = this;
                //
                self.isSave = ko.observable(true);
                self.isDaily = dataShare.ShareObject;
                self.sideBar = ko.observable(1);
                if (!self.isDaily) {
                    self.sideBar(2);
                }
                self.headerColorValue = ko.observable('');
                self.linebreak = ko.observable(0);
                self.unitRoundings = ko.observableArray([]);
                self.timeInputCurrentCode = ko.observable();
                self.txtItemId = ko.observable(null);
                self.txtItemName = ko.observable('');
                self.attendanceItems = ko.observableArray([]);
                self.aICurrentCodes = ko.observableArray([]);
                self.timeInputEnable = ko.observable(true);
                self.aICurrentCode = ko.observable(null);
                self.roundingUnitValue = ko.observable(null);
                self.unit = ko.observable(null);
                self.frameCategory = ko.observable(null);
                self.aICurrentCode.subscribe(displayNumber => {
                    if (displayNumber) {
                        self.isSave(true);
                        let attendanceItem = _.find(self.attendanceItems(), { displayNumber: Number(displayNumber) });
                        self.txtItemName(attendanceItem.attendanceItemName);
                        self.txtItemId(displayNumber);
                        self.unit(attendanceItem.optionalItemAtr);
                        self.frameCategory(attendanceItem.frameCategory);
                        // self.txtItemName(cAttendanceItem.attandanceItemName);
                        self.unitRoundings([
                            { timeInputValue: 0, timeInputName: '1分' }, { timeInputValue: 1, timeInputName: '5分' }, { timeInputValue: 2, timeInputName: '10分' },
                            { timeInputValue: 3, timeInputName: '15分' }, { timeInputValue: 4, timeInputName: '30分' }
                            , { timeInputValue: 5, timeInputName: '60分' }]);
                        self.timeInputCurrentCode(0);
                        if (self.isDaily) {
                            if (self.frameCategory() === 8 || (self.frameCategory() !== 8 && attendanceItem.attendanceAtr == 5)) {
                                self.timeInputEnable(true);
                            } else {
                                self.timeInputEnable(false);
                            }
                        } else {
                            if (self.frameCategory() === 8 || (self.frameCategory() !== 8 && attendanceItem.attendanceAtr == 1)) {
                                self.timeInputEnable(true);
                            } else {
                                self.timeInputEnable(false);
                            }
                        }

                        self.linebreak(attendanceItem.nameLineFeedPosition);
                        if (self.isDaily) {
                            service.getControlOfDailyItem(attendanceItem.attendanceItemId).done(cAttendanceItem => {
                                if (!nts.uk.util.isNullOrUndefined(cAttendanceItem)) {
                                    // self.txtItemId(cAttendanceItem.itemDailyID);
                                    self.headerColorValue(cAttendanceItem.headerBgColorOfDailyPer);
                                    self.timeInputCurrentCode(cAttendanceItem.inputUnitOfTimeItem);
                                    self.roundingUnitValue(cAttendanceItem.inputUnitOfTimeItem);
                                } else {
                                    self.headerColorValue(null);
                                    self.timeInputCurrentCode(0);
                                    self.roundingUnitValue(null);
                                }
                            });
                        } else {
                            service.getControlOfMonthlyItem(attendanceItem.attendanceItemId).done(cAttendanceItem => {
                                if (!nts.uk.util.isNullOrUndefined(cAttendanceItem)) {
                                    // self.txtItemId(cAttendanceItem.itemMonthlyId);
                                    self.headerColorValue(cAttendanceItem.headerBgColorOfMonthlyPer);
                                    self.timeInputCurrentCode(cAttendanceItem.inputUnitOfTimeItem);
                                    self.roundingUnitValue(cAttendanceItem.inputUnitOfTimeItem);
                                } else {
                                    self.headerColorValue(null);
                                    self.timeInputCurrentCode(0);
                                    self.roundingUnitValue(null);
                                }
                            });
                        }
                    } else {

                        $(document).on('click', '.search-btn', function(evt) {
                            self.txtItemId(null);
                            self.txtItemName(null);
                            self.headerColorValue(null);
                            self.aICurrentCode.valueHasMutated();
                        });
                        $(document).on('click', '.clear-btn', function(evt) {
                            self.aICurrentCode(null);
                        });
                        //                        self.isSave(false);
                        //                        self.txtItemName("");
                        //                        self.txtItemId(attendanceItemId);
                        //                        self.headerColorValue(null);
                        //                        self.timeInputCurrentCode(0);
                    }

                });

                self.attendanceItemColumn = ko.observableArray([
                    { key: 'attendanceItemId', dataType: "number", hidden: true },
                    { headerText: 'コード', key: 'displayNumber', width: 50, dataType: "number" },
                    { headerText: '名称', key: 'attendanceItemName', width: 230, dataType: "string", formatter: _.escape },
                    { key: 'attendanceAtr', dataType: "number", hidden: true },
                    { key: 'nameLineFeedPosition', dataType: "number", hidden: true }
                ]);
                $(".clear-btn").hide();
                var attendanceItems = [];
                if (self.isDaily) {
                    service.getDailyAttdItem().done(data => {
                        _.each(data, item => {
                            attendanceItems.push({
                                attendanceItemId: item.attendanceItemId,
                                attendanceItemName: item.attendanceItemName,
                                attendanceAtr: item.typeOfAttendanceItem,
                                nameLineFeedPosition: item.nameLineFeedPosition,
                                displayNumber: item.attendanceItemDisplayNumber,
                                optionalItemAtr: item.optionalItemAtr,
                                frameCategory: item.frameCategory
                            });
                        })
                        self.attendanceItems(_.sortBy(attendanceItems, 'displayNumber'));
                        self.aICurrentCode(self.attendanceItems()[0].displayNumber);
                        $("#colorID").focus();
                    })
                } else {
                    service.getMonthlyAttdItem().done(data => {
                        _.each(data, item => {
                            attendanceItems.push({
                                attendanceItemId: item.attendanceItemId,
                                attendanceItemName: item.attendanceItemName,
                                attendanceAtr: item.typeOfAttendanceItem,
                                nameLineFeedPosition: item.nameLineFeedPosition,
                                displayNumber: item.attendanceItemDisplayNumber,
                                optionalItemAtr: item.optionalItemAtr,
                                frameCategory: item.frameCategory
                            });
                        })
                        self.attendanceItems(_.sortBy(attendanceItems, 'displayNumber'));
                        self.aICurrentCode(self.attendanceItems()[0].displayNumber);
                        $("#colorID").focus();
                    })
                }
                /*
                if (self.isDaily) {
                    service.getListDailyAttdItem().done(atItems => {
                        if (!nts.uk.util.isNullOrUndefined(atItems)) {
                            let listAttdID = _.map(atItems, item => { return item.attendanceItemId; });
                            service.getNameDaily(listAttdID).done(function(dataNew) {
                                for (let i = 0; i < atItems.length; i++) {
                                    for (let j = 0; j <= dataNew.length; j++) {
                                        if (atItems[i].attendanceItemId == dataNew[j].attendanceItemId) {
                                            atItems[i].attendanceName = dataNew[j].attendanceItemName;
                                            break;
                                        }
                                    }
                                }

                                atItems.forEach(attendanceItem => {
                                    attendanceItems.push({
                                        attendanceItemId: attendanceItem.attendanceItemId,
                                        attendanceItemName: attendanceItem.attendanceName,
                                        attendanceAtr: attendanceItem.attendanceAtr,
                                        nameLineFeedPosition: attendanceItem.nameLineFeedPosition,
                                        displayNumber: attendanceItem.displayNumber
                                    });
                                });
                                self.attendanceItems(_.sortBy(attendanceItems, 'displayNumber'));
                                self.aICurrentCode(self.attendanceItems()[0].displayNumber);
                                $("#colorID").focus();
                            });

                        }
                    });
                } else {
                    service.getListMonthlyAttdItem().done(atItems => {
                        if (!nts.uk.util.isNullOrUndefined(atItems)) {
                            let listAttdID = _.map(atItems, item => { return item.attendanceItemId; });
                            service.getNameMonthly(listAttdID).done(function(dataNew) {
                                for (let i = 0; i < atItems.length; i++) {
                                    for (let j = 0; j <= dataNew.length; j++) {
                                        if (atItems[i].attendanceItemId == dataNew[j].attendanceItemId) {
                                            atItems[i].attendanceName = dataNew[j].attendanceItemName;
                                            break;
                                        }
                                    }
                                }

                                atItems.forEach(attendanceItem => {
                                    attendanceItems.push({ attendanceItemId: attendanceItem.attendanceItemId, attendanceItemName: attendanceItem.attendanceName, attendanceAtr: attendanceItem.attendanceAtr, nameLineFeedPosition: attendanceItem.nameLineFeedPosition, displayNumber: attendanceItem.attendanceItemDisplayNumber });
                                });
                                self.attendanceItems(_.sortBy(attendanceItems, 'displayNumber'));
                                self.aICurrentCode(self.attendanceItems()[0].displayNumber);
                                $("#colorID").focus();
                            });
                        }
                    });
                }*/

            }

            navigateView(): void {
                var self = this;
                var path = "/view/kdw/006/a/index.xhtml";
                href(path);
            }

            jumpToHome(sidebar): void {
                let self = this;
                nts.uk.request.jump("/view/kdw/006/a/index.xhtml", { ShareObject: sidebar() });
            }

            submitData(): void {
                let self = this,
                AtItems = {
                    companyID: ""
                };
                if ((self.roundingUnitValue() === null || self.roundingUnitValue() === "") && self.frameCategory() === 8) {
                    nts.uk.ui.dialog.error({ messageId: "Msg_1713" }).then(() => nts.uk.ui.block.clear());
                    // nts.uk.ui.block.clear();
                    return;
                }
                let attendanceItem = _.find(self.attendanceItems(), { displayNumber: Number(self.aICurrentCode()) });
                if (self.headerColorValue()) {
                    AtItems.headerBgColorOfDailyPer = self.headerColorValue();
                }
                // if (self.timeInputEnable()) {
                //     AtItems.inputUnitOfTimeItem = self.timeInputCurrentCode();
                // }

                if (self.isDaily) {
                    AtItems.itemDailyID = attendanceItem.attendanceItemId;
                    if (self.headerColorValue()) {
                        AtItems.headerBgColorOfDailyPer = self.headerColorValue();
                    }
                    if (self.roundingUnitValue) {
                        AtItems.inputUnitOfTimeItem = self.roundingUnitValue();
                    }
                    nts.uk.ui.block.invisible();
                    service.updateDaily(AtItems).done(x => {
                        nts.uk.ui.dialog.info({ messageId: 'Msg_15' });
                        $("#colorID").focus();
                    }).fail((fail) => {
                        if (fail) {
                          this.$dialog.error({ messageId: fail.messageId })  
                        };
                    }).always(() => {
                        nts.uk.ui.block.clear();
                    });
                } else {
                    AtItems.itemMonthlyID = attendanceItem.attendanceItemId;
                    if (self.headerColorValue()) {
                        AtItems.headerBgColorOfMonthlyPer = self.headerColorValue();
                    }
                    if (self.roundingUnitValue) {
                        AtItems.inputUnitOfTimeItem = self.roundingUnitValue();
                    }
                    nts.uk.ui.block.invisible();
                    service.updateMonthly(AtItems).done(x => {

                        nts.uk.ui.dialog.info({ messageId: 'Msg_15' });
                        $("#colorID").focus();
                    }).fail((fail) => {
                        if (fail) {
                          this.$dialog.error({ messageId: fail.messageId })  
                        };
                    }).always(() => {
                        nts.uk.ui.block.clear();
                    });
                }
            }

            //            interface IAttendanceItem {
            //                attendanceItemId: number;
            //                attendanceItemName: string;
            //            }
            //            class AttendanceItem {
            //                attendanceItemId: number;
            //                attendanceItemName: string;
            //    
            //                constructor(params: IAttendanceItem) {
            //                    var self = this;
            //                    self.attendanceItemId = params.attendanceItemId;
            //                    self.attendanceItemName = params.attendanceItemName;
            //                }
            //            }
        }
    }
}
