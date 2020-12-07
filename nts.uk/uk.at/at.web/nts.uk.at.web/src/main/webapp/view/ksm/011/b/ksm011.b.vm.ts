module nts.uk.at.view.ksm011.b.viewmodel {
    import blockUI = nts.uk.ui.block;

    export class ScreenModel {
        qualificationMark: KnockoutObservable<string>;
        qualificationMarkRequired: KnockoutObservable<boolean>;
        qualificationMarkEnable: KnockoutObservable<boolean>;
        openDialogEnable: KnockoutObservable<boolean>;
        credentialListEnable: KnockoutObservable<boolean>;
        credentialList: KnockoutObservable<string>;
        leftItems: KnockoutObservableArray<any>;
        rightItems: KnockoutObservableArray<any>;
        leftColumns: KnockoutObservableArray<any>;
        rightColumns: KnockoutObservableArray<any>;
        currentLCodeList: KnockoutObservableArray<any>;
        currentRCodeList: KnockoutObservableArray<any>;

        addAllEnable: KnockoutObservable<boolean>;
        singleAddEnable: KnockoutObservable<boolean>;
        singleRemoveEnable: KnockoutObservable<boolean>;
        removeAllEnable: KnockoutObservable<boolean>;

        halfDayCls: KnockoutObservableArray<any>;
        selectedHalfDayCls: KnockoutObservable<number>;
        empSignCls: KnockoutObservableArray<any>;
        selectedEmpSignCls: KnockoutObservable<number>;

        obtainCls: KnockoutObservableArray<any>;
        selectedObtain: KnockoutObservable<number>;
        obtainEnable: KnockoutObservable<boolean>;
        insufficientCls: KnockoutObservableArray<any>;
        selectedInsufficient: KnockoutObservable<number>;
        insufficientEnable: KnockoutObservable<boolean>;
        isManageComPublicHd: KnockoutObservable<number>;

        dataB: any;
        personalInforData: any;
        constructor() {
            var self = this;
            self.qualificationMark = ko.observable("");
            self.qualificationMarkEnable = ko.observable(false);
            self.qualificationMarkRequired = ko.observable(false);
            self.openDialogEnable = ko.observable(false);
            self.credentialListEnable = ko.observable(false);

            self.credentialList = ko.observable("");
            self.leftItems = ko.observableArray([]);
            self.rightItems = ko.observableArray([]);

            self.dataB = null;

            self.personalInforData = [
                { code: 0, name: nts.uk.resource.getText("Com_Employment") },
                { code: 1, name: nts.uk.resource.getText("Com_Workplace") },
                { code: 2, name: nts.uk.resource.getText("Com_Class") },
                { code: 3, name: nts.uk.resource.getText("Com_Jobtitle") },
                { code: 4, name: nts.uk.resource.getText("KSM011_55") },
                { code: 5, name: nts.uk.resource.getText("KSM011_56") },
                { code: 6, name: nts.uk.resource.getText("KSM011_57") },
                { code: 7, name: nts.uk.resource.getText("KSM011_58") },
                { code: 8, name: nts.uk.resource.getText("KSM011_59") }
            ];

            self.leftColumns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KSM011_78"), prop: 'code', width: 10, hidden: true },
                { headerText: nts.uk.resource.getText("KSM011_78"), prop: 'name', width: 120, formatter: _.escape }
            ]);

            self.currentLCodeList = ko.observableArray([]);

            self.rightColumns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KSM011_79"), prop: 'code', width: 10, hidden: true },
                { headerText: nts.uk.resource.getText("KSM011_79"), prop: 'name', width: 120, formatter: _.escape }
            ]);

            self.currentRCodeList = ko.observableArray([]);

            self.addAllEnable = ko.observable(true);
            self.singleAddEnable = ko.observable(false);
            self.singleRemoveEnable = ko.observable(false);
            self.removeAllEnable = ko.observable(true);
            self.obtainEnable = ko.observable(true);
            self.insufficientEnable = ko.observable(true);
            self.isManageComPublicHd = ko.observable(0);
            if (self.leftItems().length == 0) {
                self.addAllEnable(false);
            }

            if (self.rightItems().length == 0) {
                self.removeAllEnable(false);
            }

            self.halfDayCls = ko.observableArray([
                { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                { code: 1, name: nts.uk.resource.getText("KSM011_9") }
            ]);

            self.selectedHalfDayCls = ko.observable(1);

            self.empSignCls = ko.observableArray([
                { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                { code: 1, name: nts.uk.resource.getText("KSM011_9") }
            ]);

            self.selectedEmpSignCls = ko.observable(1);

            self.obtainCls = ko.observableArray([
                { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                { code: 1, name: nts.uk.resource.getText("KSM011_9") }
            ]);

            self.selectedObtain = ko.observable(1);

            self.insufficientCls = ko.observableArray([
                { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                { code: 1, name: nts.uk.resource.getText("KSM011_9") }
            ]);

            self.selectedInsufficient = ko.observable(1);

            self.currentLCodeList.subscribe(function(items) {
                if (items.length > 0) {
                    self.singleAddEnable(true);
                } else {
                    self.singleAddEnable(false);
                }
            });

            self.currentRCodeList.subscribe(function(items) {
                if (items.length > 0) {
                    self.singleRemoveEnable(true);
                } else {
                    self.singleRemoveEnable(false);
                }
            });

            self.leftItems = ko.observableArray(_.clone(self.personalInforData));
            self.displayQualMark();
        }

        /**
         * Show or hide Qualification Mark.
         */
        displayQualMark() {
            var self = this;

            if (self.rightItems().length > 0) {
                self.qualificationMarkRequired(true);
                self.qualificationMarkEnable(true);
            } else {
                self.qualificationMarkRequired(false);
                self.qualificationMarkEnable(false);
            }
        }

        /**
         * Start page.
         */
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();

            let teamDivision = nts.uk.ui.windows.getShared("KSM011_A_TEAMDIVISION");
            let rank = nts.uk.ui.windows.getShared("KSM011_A_RANK");

            $.when(service.findAllPublicHD()).done(function(data: any) {
                self.isManageComPublicHd(data.managePublicHoliday);
                if (data.managePublicHoliday == 0) {
                    $('#hidden-form').hide();
                } else {
                    $('#hidden-form').show();
                }
            });
            $.when(self.getData()).done(function() {
                if (self.dataB != null) {
                    if (self.dataB.schePerInfoAtr.length > 0) {
                        var rightItems = [];
                        _.forEach(self.dataB.schePerInfoAtr, function(item) {
                            var temp = _.find(self.personalInforData, function(obj) {
                                return item.personInfoAtr == obj.code;
                            });

                            rightItems.push({
                                code: item.personInfoAtr,
                                name: temp.name
                            });

                            var evens = _.remove(self.leftItems(), function(item) {
                                return item.code == temp.code;
                            });
                        });

                        let sortedLItems = _.sortBy(self.leftItems(), [function(o) { return o.code; }]);
                        let sortedRItems = _.sortBy(rightItems, [function(o) { return o.code; }]);

                        let leftItem = _.clone(self.personalInforData);

                        self.leftItems(leftItem);
                        if (rightItems.length >= self.rightItems().length) {
                            if (teamDivision == 1 && rank == 1) {
                                _.remove(sortedRItems, function(newRItem) {
                                    return newRItem.code == 6 || newRItem.code == 5;
                                })
                                _.remove(leftItem, function(newItem) {
                                    return newItem.code == 6 || newItem.code == 5;
                                })
                            }
                            if (teamDivision == 0 && rank == 0) {
                                sortedRItems = _.sortBy(rightItems, function(o) { return o.code; });
                                self.leftItems(_.clone(self.personalInforData))
                            }
                            if (teamDivision == 1 && rank == 0) {
                                _.remove(sortedRItems, function(newRItem) {
                                    return newRItem.code == 5;
                                })

                                _.remove(leftItem, function(newItem) {
                                    return newItem.code == 5;
                                })
                            }
                            if (teamDivision == 0 && rank == 1) {
                                _.remove(sortedRItems, function(newRItem) {
                                    return newRItem.code == 6;
                                })

                                _.remove(leftItem, function(newItem) {
                                    return newItem.code == 6;
                                })
                            }

                        } else {
                            
                            if (teamDivision == 1 && rank == 1) {
                                _.remove(leftItem, function(newLItem) {
                                    return newLItem.code == 6 || newLItem.code == 5;
                                })
                                _.remove(rightItems, function(newLItem) {
                                    return newLItem.code == 6 || newLItem.code == 5;
                                })
                                self.leftItems(leftItem);
                                self.rightItems(rightItems);
                            } else {
                                self.leftItems(leftItem);
                                self.rightItems(rightItems);
                            }

                        }
                        self.rightItems(sortedRItems);
                        _.forEach(sortedRItems, function(item) {
                            var lEvens = _.remove(leftItem, function(newItem) {
                                return newItem.code == item.code;
                            });
                        });
                        self.leftItems(leftItem);

                        self.singleRemoveEnable(false);
                        self.currentRCodeList([]);

                        if (self.leftItems().length > 0) {
                            self.addAllEnable(true);
                        } else {
                            self.addAllEnable(false);
                        }

                        if (self.rightItems().length > 0) {
                            self.removeAllEnable(true);
                        } else {
                            self.removeAllEnable(false);
                        }

                        self.displayQualMark();
                    }

                    self.qualificationMark(self.dataB.personSyQualify);
                    self.selectedHalfDayCls(self.dataB.symbolHalfDayAtr);
                    self.selectedEmpSignCls(self.dataB.symbolAtr);
                    self.selectedObtain(self.dataB.pubHolidayExcessAtr);
                    self.selectedInsufficient(self.dataB.pubHolidayShortageAtr);
                } else {

                }

                dfd.resolve();
            }).fail(function(res) {
                dfd.reject(res);
            });

            return dfd.promise();
        }

        /**
         * Get data from db.
         */
        getData(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();

            service.getScheDispControl().done(function(data) {
                if (data != null) {
                    var dataItems = new ScheDispControlDto({
                        personSyQualify: data.personSyQualify,
                        symbolHalfDayAtr: data.symbolHalfDayAtr,
                        symbolAtr: data.symbolAtr,
                        pubHolidayExcessAtr: data.pubHolidayExcessAtr,
                        pubHolidayShortageAtr: data.pubHolidayShortageAtr,
                        symbolHalfDayName: data.symbolHalfDayName,
                        schePerInfoAtr: data.schePerInfoAtr,
                        scheQualifySet: data.scheQualifySet
                    });

                    self.dataB = dataItems;
                }

                dfd.resolve(data);
            }).fail(function(res) {
                dfd.reject(res);
            });

            return dfd.promise();
        }

        /**
         * Registration function.
         */
        registration() {
            var self = this;

            // clear all error
            nts.uk.ui.errors.clearAll();

            $("#qualMark").trigger("validate");

            if (self.rightItems().length <= 0) {
                nts.uk.ui.dialog.alertError({ messageId: "Msg_752" });
                return;
            }


            if (nts.uk.ui.errors.hasError()) {
                return;
            }

            var schePerInfoAtrData = [];
            _.forEach(self.rightItems(), function(item) {
                schePerInfoAtrData.push({
                    personInfoAtr: item.code
                });
            });

            var scheQualifySetData = [];

            var data = new ScheDispControlDto({
                personSyQualify: self.qualificationMark(),
                symbolHalfDayAtr: self.selectedHalfDayCls(),
                symbolAtr: self.selectedEmpSignCls(),
                pubHolidayExcessAtr: self.selectedObtain(),
                pubHolidayShortageAtr: self.selectedInsufficient(),
                symbolHalfDayName: " ",
                schePerInfoAtr: schePerInfoAtrData.length > 0 ? schePerInfoAtrData : null,
                scheQualifySet: scheQualifySetData.length > 0 ? scheQualifySetData : null
            });

            service.saveScheDispControl(data).done(function() {
                nts.uk.ui.dialog.info({ messageId: "Msg_15" });
            }).fail(function(error) {
                nts.uk.ui.dialog.alertError({ messageId: error.messageId, messageParams: error.parameterIds });
            });
        }

        /**
         * Add all items from left to right.
         */
        addAll() {
            var self = this;

            if (self.leftItems().length > 0) {
                if (self.rightItems().length == 0) {
                    self.rightItems(self.leftItems());
                    self.leftItems([]);
                    self.addAllEnable(false);
                    self.removeAllEnable(true);
                    self.currentRCodeList([]);
                } else {
                    var rightItems = self.rightItems();
                    _.forEach(self.leftItems(), function(item) {
                        rightItems.push({
                            code: item.code,
                            name: item.name
                        });
                    });

                    var sortedItems = _.sortBy(rightItems, [function(o) { return o.code; }]);
                    self.leftItems([]);
                    self.rightItems([]);
                    self.rightItems(sortedItems);
                }
            }

            self.currentRCodeList([]);
            self.singleAddEnable(false);
            self.displayQualMark();

            if (self.leftItems().length == 0) {
                self.addAllEnable(false);
                self.removeAllEnable(true);
            }
        }

        /**
         * Return an item from left to right.
         */
        singleAdd() {
            var self = this;

            if (self.currentLCodeList().length > 0) {
                var leftItems = self.leftItems();
                _.forEach(self.currentLCodeList(), function(item) {
                    var temp = _.find(leftItems, function(obj) {
                        return item == obj.code;
                    });

                    self.rightItems.push({
                        code: temp.code,
                        name: temp.name
                    });

                    var evens = _.remove(leftItems, function(item) {
                        return item.code == temp.code;
                    });
                });

                self.leftItems([]);
                var sortedLItems = _.sortBy(leftItems, [function(o) { return o.code; }]);
                var sortedRItems = _.sortBy(self.rightItems(), [function(o) { return o.code; }]);
                self.leftItems(sortedLItems);
                self.rightItems([]);
                self.rightItems(sortedRItems);
                self.singleAddEnable(false);
                self.currentRCodeList([]);

                if (self.rightItems().length > 0) {
                    self.removeAllEnable(true);

                    if (self.leftItems().length > 0) {
                        self.addAllEnable(true);
                    } else {
                        self.addAllEnable(false);
                    }
                }

                self.displayQualMark();
            }

            self.currentLCodeList.removeAll();
        }

        /**
         * Return an item from right to left.
         */
        singleRemove() {
            var self = this;

            if (self.currentRCodeList().length > 0) {
                var rightItems = self.rightItems();
                _.forEach(self.currentRCodeList(), function(item) {
                    var temp = _.find(rightItems, function(obj) {
                        return item == obj.code;
                    });

                    self.leftItems.push({
                        code: temp.code,
                        name: temp.name
                    });

                    var evens = _.remove(rightItems, function(item) {
                        return item.code == temp.code;
                    });
                });

                self.rightItems([]);
                var sortedLItems = _.sortBy(self.leftItems(), [function(o) { return o.code; }]);
                var sortedRItems = _.sortBy(rightItems, [function(o) { return o.code; }]);
                self.rightItems(sortedRItems);
                self.leftItems([]);
                self.leftItems(sortedLItems);
                self.singleRemoveEnable(false);
                self.currentLCodeList([]);

                if (self.leftItems().length > 0) {
                    self.addAllEnable(true);

                    if (self.rightItems().length > 0) {
                        self.removeAllEnable(true);
                    } else {
                        self.removeAllEnable(false);
                    }
                }

                self.displayQualMark();
            }

            self.currentRCodeList.removeAll();
        }

        /**
         * 
         * 
         * Return all items from right to left.
         */
        removeAll() {
            var self = this;

            if (self.rightItems().length > 0) {
                if (self.leftItems().length == 0) {
                    self.leftItems(self.rightItems());
                    self.rightItems([]);
                    self.removeAllEnable(false);
                    self.addAllEnable(true);
                    self.currentLCodeList([]);
                } else {
                    var leftItems = self.leftItems();
                    _.forEach(self.rightItems(), function(item) {
                        leftItems.push({
                            code: item.code,
                            name: item.name
                        });
                    });

                    var sortedItems = _.sortBy(leftItems, [function(o) { return o.code; }]);
                    self.rightItems([]);
                    self.leftItems([]);
                    self.leftItems(sortedItems);
                }
            }

            self.currentLCodeList([]);
            self.singleRemoveEnable(false);
            self.displayQualMark();

            if (self.rightItems().length == 0) {
                self.removeAllEnable(false);
                self.addAllEnable(true);
            }
        }

        /**
         * Open dialog function.
         */
        openDialog() {
            var self = this;

        }
    }

    class ScheDispControlDto {
        personSyQualify: string;
        symbolHalfDayAtr: number;
        symbolAtr: number;
        pubHolidayExcessAtr: number;
        pubHolidayShortageAtr: number;
        symbolHalfDayName: string;
        schePerInfoAtr: Array<SchePerInfoAtrDto>;
        scheQualifySet: Array<ScheQualifySetDto>;

        constructor(param: IScheDispControlDto) {
            this.personSyQualify = param.personSyQualify;
            this.symbolHalfDayAtr = param.symbolHalfDayAtr;
            this.symbolAtr = param.symbolAtr;
            this.pubHolidayExcessAtr = param.pubHolidayExcessAtr;
            this.pubHolidayShortageAtr = param.pubHolidayShortageAtr;
            this.symbolHalfDayName = param.symbolHalfDayName;
            this.schePerInfoAtr = param.schePerInfoAtr;
            this.scheQualifySet = param.scheQualifySet;
        }
    }

    interface IScheDispControlDto {
        personSyQualify: string,
        symbolHalfDayAtr: number,
        symbolAtr: number,
        pubHolidayExcessAtr: number,
        pubHolidayShortageAtr: number,
        symbolHalfDayName: string,
        schePerInfoAtr: Array<SchePerInfoAtrDto>,
        scheQualifySet: Array<ScheQualifySetDto>
    }

    class SchePerInfoAtrDto {
        personInfoAtr: number;

        constructor(param: ISchePerInfoAtrDto) {
            this.personInfoAtr = param.personInfoAtr;
        }
    }

    interface ISchePerInfoAtrDto {
        personInfoAtr: number;
    }

    class ScheQualifySetDto {
        qualifyCode: string;

        constructor(param: IScheQualifySetDto) {
            this.qualifyCode = param.qualifyCode;
        }
    }

    interface IScheQualifySetDto {
        qualifyCode: string;
    }
}
