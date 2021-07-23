module nts.uk.at.view.kdw002.a {
    export module viewmodel {
        import href = nts.uk.request.jump;
        import getText = nts.uk.resource.getText;
        import infor = nts.uk.ui.dialog.info;
        export class ScreenModel {
            headerColorValue: KnockoutObservable<string>;
            unitRoundings: KnockoutObservableArray<any>;
            selectedOption: KnockoutObservable<any>;
            // A2_3
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
            // ver 8 A5_4, A5_5
            moneyUnits: KnockoutObservableArray<any>;
            moneyUnit: KnockoutObservable<number> = ko.observable(1);
            // ver 8 A5_6, A5_7
            numberOfTimesUnits: KnockoutObservableArray<any>;
            numberOfTimesUnit: KnockoutObservable<number> = ko.observable(0.01);
            // ver 8 A5_8, A5_9
            timeUnits: KnockoutObservableArray<any>;
            timeUnit: KnockoutObservable<number> = ko.observable(1);
            // ver 8 A8_2, A8_3
            lineBreakPositions: KnockoutObservableArray<any>;
            lineBreakPosition: KnockoutObservable<number> = ko.observable(0);
            //  ver8 A7_2
            displayName: KnockoutObservable<string> = ko.observable("");
            displayNameEnable: KnockoutObservable<boolean> = ko.observable(false);

            gridHeight = window.innerHeight - 250;
            
            constructor(dataShare: any) {
                var self = this;
                // ver 8
                self.moneyUnits = ko.observableArray([
                    new Items(1, nts.uk.resource.getText('KDW002_49')),
                    new Items(10, nts.uk.resource.getText('KDW002_50')),
                    new Items(100, nts.uk.resource.getText('KDW002_51')),
                    new Items(1000, nts.uk.resource.getText('KDW002_52')),
                    new Items(10000, nts.uk.resource.getText('KDW002_53'))
                ]);
                self.numberOfTimesUnits = ko.observableArray([
                    new Items(0.01, nts.uk.resource.getText('KDW002_54')),
                    new Items(0.1, nts.uk.resource.getText('KDW002_55')),
                    new Items(0.5, nts.uk.resource.getText('KDW002_56')),
                    new Items(1, nts.uk.resource.getText('KDW002_57'))
                ]);
                self.timeUnits = ko.observableArray([
                    new Items(1, nts.uk.resource.getText('KDW002_58')),
                    new Items(5, nts.uk.resource.getText('KDW002_59')),
                    new Items(10, nts.uk.resource.getText('KDW002_60')),
                    new Items(15, nts.uk.resource.getText('KDW002_61')),
                    new Items(30, nts.uk.resource.getText('KDW002_62')),
                    new Items(60, nts.uk.resource.getText('KDW002_63'))
                ]);
                self.lineBreakPositions = ko.observableArray([
                    new Items(0, nts.uk.resource.getText('KDW002_68')),
                    new Items(1, nts.uk.resource.getText('KDW002_69', '2')),
                    new Items(2, nts.uk.resource.getText('KDW002_69', '3')),
                    new Items(3, nts.uk.resource.getText('KDW002_69', '4')),
                    new Items(4, nts.uk.resource.getText('KDW002_69', '5')),
                    new Items(5, nts.uk.resource.getText('KDW002_69', '6')),
                    new Items(6, nts.uk.resource.getText('KDW002_69', '7')),
                    new Items(7, nts.uk.resource.getText('KDW002_69', '8')),
                    new Items(8, nts.uk.resource.getText('KDW002_69', '9')),
                    new Items(9, nts.uk.resource.getText('KDW002_69', '10'))
                ]);

                // --

                //
                self.isSave = ko.observable(true);
                self.isDaily = dataShare === undefined ? false : dataShare.ShareObject;
                self.sideBar = ko.observable(1);
                self.headerColorValue = ko.observable('');
                self.linebreak = ko.observable(0);
                self.unitRoundings = ko.observableArray([]);
                self.timeInputCurrentCode = ko.observable();
                self.txtItemId = ko.observable(null);
                self.txtItemName = ko.observable(''); 
                self.displayName = ko.observable('');// ver8
                self.displayNameEnable = ko.observable(false);// ver8
                self.lineBreakPosition = ko.observable(0);
                self.attendanceItems = ko.observableArray([]);
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
                        // ver8
                        if(attendanceItem.frameCategory){
                            self.displayName(attendanceItem.attendanceItemName);
                            self.displayNameEnable(false);
                        } else {
                            self.displayName(attendanceItem.displayName);
                            self.displayNameEnable(true);
                        }
                        self.lineBreakPosition(attendanceItem.nameLineFeedPosition); // ver8
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
                                    switch(self.unit()){
                                        case 0:
                                            self.timeUnit(cAttendanceItem.inputUnitOfTimeItem);
                                            break;
                                        case 1:
                                            self.numberOfTimesUnit(cAttendanceItem.inputUnitOfTimeItem);
                                            break;
                                        case 2:
                                            self.moneyUnit(cAttendanceItem.inputUnitOfTimeItem);
                                            break;
                                    }
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
                                    switch(self.unit()){
                                        case 0:
                                            self.timeUnit(cAttendanceItem.inputUnitOfTimeItem);
                                            break;
                                        case 1:
                                            self.numberOfTimesUnit(cAttendanceItem.inputUnitOfTimeItem);
                                            break;
                                        case 2:
                                            self.moneyUnit(cAttendanceItem.inputUnitOfTimeItem);
                                            break;
                                    }
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
                            self.displayName(null);
                            self.lineBreakPosition(0);
                            self.displayNameEnable(false);
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
                var attendanceItems: Array<any> = [];
                if (self.isDaily) {
                    service.getDailyAttdItem().done(data => {
                        _.each(data, item => {
                            attendanceItems.push({
                                attendanceItemId: item.attendanceItemId,
                                attendanceItemName: item.attendanceItemName,
                                displayName: item.displayName,
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
                    });
                } else {
                    service.getMonthlyAttdItem().done(data => {
                        _.each(data, item => {
                            attendanceItems.push({
                                attendanceItemId: item.attendanceItemId,
                                attendanceItemName: item.attendanceItemName,
                                displayName: item.displayName,
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
                    });
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

            jumpToHome(): void {
                let self = this;
                nts.uk.request.jump("/view/kdw/006/a/index.xhtml");
            }

            submitData(): void {
                let self = this,
                AtItems: any = {};
                // if ((self.roundingUnitValue() === null || self.roundingUnitValue() === "") && self.frameCategory() === 8) {
                //     nts.uk.ui.dialog.error({ messageId: "Msg_1713" }).then(() => nts.uk.ui.block.clear());
                //     // nts.uk.ui.block.clear();
                //     return;
                // }
                nts.uk.ui.errors.clearAll();          	
                if($("#A7_2").ntsError("hasError")){
                    self.isSave(false);
                    return;
                } else {
                    self.isSave(true);
                }
                switch(self.unit()){
                    case 0:
                        if(self.timeUnit() == null && self.frameCategory() === 8){
                            nts.uk.ui.dialog.error({ messageId: "Msg_1713" }).then(() => nts.uk.ui.block.clear());
                            return;
                        }
                        break;
                    case 1:
                        if (self.numberOfTimesUnit() == null && self.frameCategory() === 8){
                            nts.uk.ui.dialog.error({ messageId: "Msg_1713" }).then(() => nts.uk.ui.block.clear());
                            return;
                        }
                        break;
                    case 2:
                        if(self.moneyUnit() == null && self.frameCategory() === 8){
                            nts.uk.ui.dialog.error({ messageId: "Msg_1713" }).then(() => nts.uk.ui.block.clear());
                            return;
                        }
                        break;
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
                    let command : any = {};
                    command.attendanceItemId = attendanceItem.attendanceItemId;
                    command.displayName = self.displayNameEnable() ? self.displayName() : null;
                    command.nameLineFeedPosition = self.lineBreakPosition();
                    AtItems.updateDailyAttendanceItemCommand = command;
                    if (self.headerColorValue()) {
                        AtItems.headerBgColorOfDailyPer = self.headerColorValue();
                    }
                    switch(self.unit()){
                        case 0:
                            AtItems.inputUnitOfTimeItem = self.timeUnit();
                            break;
                        case 1:
                            AtItems.inputUnitOfTimeItem = self.numberOfTimesUnit();
                            break;
                        case 2:
                            AtItems.inputUnitOfTimeItem = self.moneyUnit();
                            break;
                    }             
                    nts.uk.ui.block.invisible();
                    service.updateDaily(AtItems).done(x => {
                        nts.uk.ui.dialog.info({ messageId: 'Msg_15' }).then(() => {
                            let attendanceItems: Array<any> = [];
                            service.getDailyAttdItem().done(data => {
                                _.each(data, item => {
                                    attendanceItems.push({
                                        attendanceItemId: item.attendanceItemId,
                                        attendanceItemName: item.attendanceItemName,
                                        displayName: item.displayName,
                                        attendanceAtr: item.typeOfAttendanceItem,
                                        nameLineFeedPosition: item.nameLineFeedPosition,
                                        displayNumber: item.attendanceItemDisplayNumber,
                                        optionalItemAtr: item.optionalItemAtr,
                                        frameCategory: item.frameCategory
                                    });
                                })
                                self.attendanceItems(_.sortBy(attendanceItems, 'displayNumber'));
                                $("#colorID").focus();
                            });
                        });
                    }).fail((fail) => {
                        if (fail) {
                          this.$dialog.error({ messageId: fail.messageId })  
                        };
                    }).always(() => {
                        nts.uk.ui.block.clear();
                    });
                } else {
                    AtItems.itemMonthlyID = attendanceItem.attendanceItemId;
                    let command : any = {};
                    command.attendanceItemId = attendanceItem.attendanceItemId;
                    command.displayName = self.displayNameEnable() ? self.displayName() : null;
                    command.nameLineFeedPosition = self.lineBreakPosition();
                    AtItems.updateMonthlyAttendanceItemCommand = command;
                    if (self.headerColorValue()) {
                        AtItems.headerBgColorOfMonthlyPer = self.headerColorValue();
                    }
                    switch(self.unit()){
                        case 0:
                            AtItems.inputUnitOfTimeItem = self.timeUnit();
                            break;
                        case 1:
                            AtItems.inputUnitOfTimeItem = self.numberOfTimesUnit();
                            break;
                        case 2:
                            AtItems.inputUnitOfTimeItem = self.moneyUnit();
                            break;
                    } 

                    nts.uk.ui.block.invisible();
                    service.updateMonthly(AtItems).done(x => {
                        nts.uk.ui.dialog.info({ messageId: 'Msg_15' }).then(() => {
                            let attendanceItems: Array<any> = [];
                            service.getMonthlyAttdItem().done(data => {
                                _.each(data, item => {
                                    attendanceItems.push({
                                        attendanceItemId: item.attendanceItemId,
                                        attendanceItemName: item.attendanceItemName,
                                        displayName: item.displayName,
                                        attendanceAtr: item.typeOfAttendanceItem,
                                        nameLineFeedPosition: item.nameLineFeedPosition,
                                        displayNumber: item.attendanceItemDisplayNumber,
                                        optionalItemAtr: item.optionalItemAtr,
                                        frameCategory: item.frameCategory
                                    });
                                })
                                self.attendanceItems(_.sortBy(attendanceItems, 'displayNumber'));
                                $("#colorID").focus();
                            });  
                        });
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
        class Items {
            code: number;
            name: string;
    
            constructor(code: number, name: string) {
                this.code = code;
                this.name = name;
            }
        }
    }
}
