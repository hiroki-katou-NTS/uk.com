var kml001;
(function (kml001) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var servicebase = kml001.shr.servicebase;
            var vmbase = kml001.shr.vmbase;
            var ScreenModel = (function () {
                function ScreenModel() {
                    $('#formula-child-1').html(nts.uk.resource.getText('KML001_7'));
                    var self = this;
                    self.personCostList = ko.observableArray([]);
                    self.currentPersonCost = ko.observable(new vmbase.PersonCostCalculation('', '', "", "9999/12/31", 0, '', []));
                    self.newStartDate = ko.observable(null);
                    self.gridPersonCostList = ko.observableArray([]);
                    self.currentGridPersonCost = ko.observable(null);
                    self.premiumItems = ko.observableArray([]);
                    self.isInsert = ko.observable(true);
                }
                /**
                 * get data on start page
                 */
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    var dfdPremiumItemSelect = servicebase.premiumItemSelect();
                    var dfdPersonCostCalculationSelect = servicebase.personCostCalculationSelect();
                    $.when(dfdPremiumItemSelect, dfdPersonCostCalculationSelect).done(function (premiumItemSelectData, dfdPersonCostCalculationSelectData) {
                        // Premium Item Select: Done
                        premiumItemSelectData.forEach(function (item) {
                            self.premiumItems.push(new vmbase.PremiumItem(item.companyID, item.id, item.attendanceID, item.name, item.displayNumber, item.useAtr));
                        });
                        var sum = 0;
                        self.premiumItems().forEach(function (item) {
                            sum += item.useAtr();
                        });
                        if (sum == 0) {
                            self.premiumDialog();
                        }
                        // PersonCostCalculationSelect: Done
                        if (dfdPersonCostCalculationSelectData.length) {
                            self.loadData(dfdPersonCostCalculationSelectData, 0);
                            self.currentGridPersonCost.subscribe(function (value) {
                                self.currentPersonCost(_.find(self.personCostList(), function (o) { return o.startDate() == _.split(value, ' ', 1)[0]; }));
                                self.newStartDate(self.currentPersonCost().startDate());
                                $("#startDateInput").ntsError('clear');
                                $(".premiumInput").ntsError('clear');
                                nts.uk.ui.errors.clearAll();
                                ko.utils.arrayForEach(self.currentPersonCost().premiumSets(), function (premiumSet, index) {
                                    var iDList = [];
                                    self.currentPersonCost().premiumSets()[index].attendanceItems().forEach(function (item) {
                                        iDList.push(item.shortAttendanceID);
                                    });
                                    self.getItem(iDList, index);
                                });
                                self.isInsert(false);
                            });
                            self.isInsert(false);
                        }
                        self.newStartDate.subscribe(function (value) {
                            if (self.isInsert()) {
                                if (_.size(self.personCostList()) != 0) {
                                    if ((self.newStartDate() == "") || vmbase.ProcessHandler.validateDateInput(self.newStartDate(), _.last(self.personCostList()).startDate())) {
                                        $("#startDateInput").ntsError('set', { messageId: "Msg_65" });
                                    }
                                    else {
                                        $("#startDateInput").ntsError('clear');
                                    }
                                }
                                else {
                                    if ((self.newStartDate() == "") || vmbase.ProcessHandler.validateDateInput(self.newStartDate(), "1900/01/01")) {
                                        $("#startDateInput").ntsError('set', { messageId: "Msg_65" });
                                    }
                                    else {
                                        $("#startDateInput").ntsError('clear');
                                    }
                                }
                            }
                        });
                        dfd.resolve();
                    }).fail(function (res1, res2) {
                        nts.uk.ui.dialog.alert(res1.message + '\n' + res2.message);
                        dfd.reject();
                    });
                    return dfd.promise();
                };
                /**
                 * set new data to element on screen
                 */
                ScreenModel.prototype.loadData = function (res, index) {
                    var self = this;
                    // set data to currrent PersonCostCalculation
                    res.forEach(function (personCostCalc) {
                        self.personCostList.push(vmbase.ProcessHandler.fromObjectPerconCost(personCostCalc));
                    });
                    self.currentPersonCost((_.first(self.personCostList()) == null) ? new vmbase.PersonCostCalculation('', '', "", "9999/12/31", 0, '', []) : self.personCostList()[index]);
                    self.newStartDate(self.currentPersonCost().startDate());
                    // set data to grid list
                    self.personCostList().forEach(function (item) { self.gridPersonCostList.push(new vmbase.GridPersonCostCalculation(item.startDate() + " ~ " + item.endDate())); });
                    self.currentGridPersonCost(self.currentPersonCost().startDate() + " ~ " + self.currentPersonCost().endDate());
                    ko.utils.arrayForEach(self.currentPersonCost().premiumSets(), function (premiumSet, index) {
                        var iDList = [];
                        self.currentPersonCost().premiumSets()[index].attendanceItems().forEach(function (item) {
                            iDList.push(item.shortAttendanceID);
                        });
                        self.getItem(iDList, index);
                    });
                    if (_.size(self.personCostList()) == 0) {
                        self.lastStartDate = "1900/01/01";
                    }
                    else {
                        self.lastStartDate = _.last(self.personCostList()).startDate();
                    }
                };
                /**
                 * get list item for each premium setting
                 */
                ScreenModel.prototype.getItem = function (iDList, index) {
                    var self = this;
                    var dfd = $.Deferred();
                    if (iDList.length != 0) {
                        self.currentPersonCost().premiumSets()[index].attendanceItems.removeAll();
                        servicebase.getAttendanceItems(iDList)
                            .done(function (res) {
                            var newList = [];
                            res.forEach(function (item) {
                                newList.push(new vmbase.AttendanceItem(item.attendanceItemId, item.attendanceItemName));
                            });
                            self.currentPersonCost().premiumSets()[index].attendanceItems(newList);
                        })
                            .fail(function (res) {
                            nts.uk.ui.dialog.alert(res.message);
                        });
                    }
                };
                /**
                 * insert/update new person cost calculation
                 */
                ScreenModel.prototype.saveData = function () {
                    var self = this;
                    if (self.isInsert()) {
                        var index_1 = _.findLastIndex(self.personCostList()) + 1;
                        if ((self.newStartDate() != "") && !vmbase.ProcessHandler.validateDateInput(self.currentPersonCost().startDate(), self.lastStartDate)) {
                            // insert new data if startDate have no error
                            self.currentPersonCost().startDate(self.newStartDate());
                            servicebase.personCostCalculationInsert(vmbase.ProcessHandler.toObjectPersonCost(self.currentPersonCost()))
                                .done(function (res) {
                                servicebase.personCostCalculationSelect()
                                    .done(function (newData) {
                                    // refresh data list
                                    self.personCostList.removeAll();
                                    self.gridPersonCostList.removeAll();
                                    self.loadData(newData, index_1);
                                    self.isInsert(false);
                                    nts.uk.ui.dialog.alert(vmbase.MSG.MSG015);
                                }).fail(function (res) {
                                    nts.uk.ui.dialog.alert(res.message);
                                });
                            }).fail(function (res) {
                                nts.uk.ui.dialog.alert(res.message);
                            });
                        }
                        else {
                            $("#startDateInput").ntsError('set', { messageId: "Msg_65" });
                        }
                    }
                    else {
                        // update new data for current personCostCalculation
                        var index_2 = _.findIndex(self.personCostList(), function (item) { return item.historyID() == self.currentPersonCost().historyID(); });
                        servicebase.personCostCalculationUpdate(vmbase.ProcessHandler.toObjectPersonCost(self.currentPersonCost()))
                            .done(function (res) {
                            servicebase.personCostCalculationSelect()
                                .done(function (newData) {
                                // refresh data list
                                self.personCostList.removeAll();
                                self.gridPersonCostList.removeAll();
                                self.loadData(newData, index_2);
                                nts.uk.ui.dialog.alert(vmbase.MSG.MSG015);
                            }).fail(function (res) {
                                nts.uk.ui.dialog.alert(res.message);
                            });
                        }).fail(function (res) {
                            nts.uk.ui.dialog.alert(res.message);
                        });
                    }
                };
                /**
                 * open premium dialog
                 */
                ScreenModel.prototype.premiumDialog = function () {
                    var self = this;
                    var index = _.findIndex(self.personCostList(), self.currentPersonCost());
                    nts.uk.ui.windows.setShared('isInsert', self.isInsert());
                    nts.uk.ui.windows.sub.modal("/view/kml/001/b/index.xhtml", { title: "割増項目の設定", dialogClass: "no-close" }).onClosed(function () {
                        if (nts.uk.ui.windows.getShared('updatePremiumSeting') == true) {
                            // refresh and set new data if premiumSetting change
                            servicebase.personCostCalculationSelect()
                                .done(function (res) {
                                var processItem;
                                if (self.isInsert()) {
                                    processItem = self.currentPersonCost();
                                }
                                self.personCostList.removeAll();
                                self.gridPersonCostList.removeAll();
                                self.loadData(res, index);
                                if (self.isInsert()) {
                                    self.currentPersonCost(processItem);
                                    var lastItem_1 = _.last(self.personCostList());
                                    self.currentPersonCost().premiumSets().forEach(function (item, index) {
                                        item.useAtr(lastItem_1.premiumSets()[index].useAtr());
                                    });
                                }
                            }).fail(function (res) {
                                nts.uk.ui.dialog.alert(res.message);
                            });
                            if (_.size(self.personCostList()) == 0) {
                                servicebase.premiumItemSelect()
                                    .done(function (res) {
                                    self.premiumItems.removeAll();
                                    res.forEach(function (item) {
                                        self.premiumItems.push(new vmbase.PremiumItem(item.companyID, item.id, item.attendanceID, item.name, item.displayNumber, item.useAtr));
                                    });
                                }).fail(function (res) {
                                    nts.uk.ui.dialog.alert(res.message);
                                });
                            }
                        }
                    });
                };
                /**
                 * open create dialog
                 */
                ScreenModel.prototype.createDialog = function () {
                    var self = this;
                    var lastestHistory = _.last(self.personCostList());
                    nts.uk.ui.windows.setShared('lastestStartDate', lastestHistory == null ? "1900/01/01" : lastestHistory.startDate());
                    nts.uk.ui.windows.setShared('size', _.size(self.personCostList()));
                    nts.uk.ui.windows.sub.modal("/view/kml/001/c/index.xhtml", { title: "履歴の追加", dialogClass: "no-close" }).onClosed(function () {
                        var newStartDate = nts.uk.ui.windows.getShared('newStartDate');
                        if (newStartDate != null) {
                            var copyDataFlag = nts.uk.ui.windows.getShared('copyDataFlag');
                            if (_.size(self.personCostList()) != 0) {
                                if (copyDataFlag) {
                                    var lastItem = self.clonePersonCostCalculation(_.last(self.personCostList()));
                                    self.currentPersonCost(new vmbase.PersonCostCalculation(lastItem.companyID(), '', newStartDate, "9999/12/31", lastItem.unitPrice(), lastItem.memo(), []));
                                    self.currentPersonCost().premiumSets(lastItem.premiumSets());
                                    self.newStartDate(self.currentPersonCost().startDate());
                                }
                                else {
                                    self.currentPersonCost(new vmbase.PersonCostCalculation('', '', newStartDate, "9999/12/31", 0, '', []));
                                    var newPremiumSets = [];
                                    for (var i = 1; i <= 10; i++) {
                                        newPremiumSets.push(new vmbase.PremiumSetting("", "", i, 0, i, "", i, self.premiumItems()[i - 1].useAtr(), []));
                                    }
                                    self.currentPersonCost().premiumSets(newPremiumSets);
                                    self.newStartDate(self.currentPersonCost().startDate());
                                }
                                self.isInsert(true);
                            }
                            else {
                                self.currentPersonCost(new vmbase.PersonCostCalculation('', '', newStartDate, "9999/12/31", 0, '', []));
                                var newPremiumSets = [];
                                for (var i = 1; i <= 10; i++) {
                                    newPremiumSets.push(new vmbase.PremiumSetting("", "", i, 0, i, "", i, self.premiumItems()[i - 1].useAtr(), []));
                                }
                                self.currentPersonCost().premiumSets(newPremiumSets);
                                self.newStartDate(self.currentPersonCost().startDate());
                                self.isInsert(true);
                            }
                        }
                    });
                };
                /**
                 * open edit dialog
                 */
                ScreenModel.prototype.editDialog = function () {
                    var self = this;
                    var index = _.findIndex(self.personCostList(), self.currentPersonCost());
                    nts.uk.ui.windows.setShared('personCostList', self.personCostList());
                    nts.uk.ui.windows.setShared('currentPersonCost', self.currentPersonCost());
                    nts.uk.ui.windows.sub.modal("/view/kml/001/d/index.xhtml", { title: "履歴の編集", dialogClass: "no-close" }).onClosed(function () {
                        var editedIndex = nts.uk.ui.windows.getShared('isEdited');
                        if (editedIndex != null) {
                            if (editedIndex == 1)
                                index -= 1; // when edit is delete, set index to last item
                            servicebase.personCostCalculationSelect()
                                .done(function (res) {
                                // refresh data list
                                self.personCostList.removeAll();
                                self.gridPersonCostList.removeAll();
                                self.loadData(res, index);
                            }).fail(function (res) {
                                nts.uk.ui.dialog.alert(res.message);
                            });
                        }
                    });
                    ;
                };
                /**
                 * open select item dialog
                 */
                ScreenModel.prototype.selectDialog = function (data, index) {
                    var self = this;
                    var currentList = [];
                    ko.utils.arrayForEach(data.attendanceItems(), function (attendanceItem) {
                        currentList.push(attendanceItem.shortAttendanceID);
                    });
                    nts.uk.ui.windows.setShared('SelectedAttendanceId', currentList, true);
                    nts.uk.ui.windows.setShared('Multiple', true);
                    servicebase.getAttendanceItemByType(0)
                        .done(function (res) {
                        nts.uk.ui.windows.setShared('AllAttendanceObj', _.map(res, function (item) { return item.attendanceItemId; }));
                        nts.uk.ui.windows.sub.modal("/view/kdl/021/a/index.xhtml", { title: "割増項目の設定", dialogClass: "no-close" }).onClosed(function () {
                            var newList = nts.uk.ui.windows.getShared('selectedChildAttendace');
                            if (newList != null) {
                                if (newList.length != 0) {
                                    if (!_.isEqual(newList, currentList)) {
                                        //clone Knockout Object
                                        self.currentPersonCost(self.clonePersonCostCalculation(self.currentPersonCost()));
                                        self.getItem(newList, index);
                                    }
                                }
                                else {
                                    self.currentPersonCost().premiumSets()[index].attendanceItems([]);
                                }
                            }
                        });
                    }).fail(function (res) {
                        nts.uk.ui.dialog.alert(res.message);
                    });
                };
                /**
                 * clone PersonCostCalculation Object
                 */
                ScreenModel.prototype.clonePersonCostCalculation = function (object) {
                    return vmbase.ProcessHandler.fromObjectPerconCost(_.cloneDeep(vmbase.ProcessHandler.toObjectPersonCost(object)));
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = kml001.a || (kml001.a = {}));
})(kml001 || (kml001 = {}));
//# sourceMappingURL=kml001.a.vm.js.map