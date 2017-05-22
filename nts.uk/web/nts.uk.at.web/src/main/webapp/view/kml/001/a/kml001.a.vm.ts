module kml001.a.viewmodel {
    import servicebase = kml001.shr.servicebase;
    import vmbase = kml001.shr.vmbase;
    export class ScreenModel {
        gridPersonCostList: KnockoutObservableArray<vmbase.GridPersonCostCalculation>;
        currentGridPersonCost: KnockoutObservable<string>;
        personCostList: KnockoutObservableArray<vmbase.PersonCostCalculation>;
        currentPersonCost: KnockoutObservable<vmbase.PersonCostCalculation>;
        premiumItems: KnockoutObservableArray<vmbase.PremiumItem>;
        lastStartDate: string;
        isInsert: KnockoutObservable<Boolean>;
        constructor() {
            $('#formula-child-1').html(nts.uk.resource.getText('KML001_7'));
            var self = this;
            self.personCostList = ko.observableArray([]);
            self.currentPersonCost = ko.observable(new vmbase.PersonCostCalculation('', '', "", "9999/12/31", 0, '', []));
            self.gridPersonCostList = ko.observableArray([]);
            self.currentGridPersonCost = ko.observable(null);
            self.premiumItems = ko.observableArray([]);
            self.isInsert = ko.observable(true);
            self.currentPersonCost().startDate.subscribe(function(value) {
                if (self.isInsert()) {
                    if (_.size(self.personCostList()) != 0) {
                        if ((self.currentPersonCost().startDate() == "") || vmbase.ProcessHandler.validateDateInput(self.currentPersonCost().startDate(), _.last(self.personCostList()).startDate())) {
                            $("#startDateInput").ntsError('set', vmbase.MSG.MSG065);
                        } else { $("#startDateInput").ntsError('clear'); }
                    } else {
                        if ((self.currentPersonCost().startDate() == "") || vmbase.ProcessHandler.validateDateInput(self.currentPersonCost().startDate(), "1900/01/01")) {
                            $("#startDateInput").ntsError('set', vmbase.MSG.MSG065);
                        } else { $("#startDateInput").ntsError('clear'); }
                    }
                }
            });
        }
        
        /**
         * get data on start page
         */
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            var dfdPremiumItemSelect = servicebase.premiumItemSelect();
            var dfdPersonCostCalculationSelect = servicebase.personCostCalculationSelect();
            $.when(dfdPremiumItemSelect, dfdPersonCostCalculationSelect).done((premiumItemSelectData, dfdPersonCostCalculationSelectData) => {
                // Premium Item Select: Done
                premiumItemSelectData.forEach(function(item) {
                    self.premiumItems.push(
                        new vmbase.PremiumItem(
                            item.companyID,
                            item.id,
                            item.attendanceID,
                            item.name,
                            item.displayNumber,
                            item.useAtr
                        ));
                });
                let sum = 0;
                self.premiumItems().forEach(function(item){
                    sum+=item.useAtr();    
                });
                if(sum==0){
                    self.premiumDialog();        
                }
                // PersonCostCalculationSelect: Done
                if (dfdPersonCostCalculationSelectData.length) {
                    self.loadData(dfdPersonCostCalculationSelectData, 0);
                    self.currentGridPersonCost.subscribe(function(value) {
                        self.currentPersonCost(_.find(self.personCostList(), function(o) { return o.startDate() == _.split(value, ' ', 1)[0]; }));
                        $("#startDateInput").ntsError('clear');
                        ko.utils.arrayForEach(self.currentPersonCost().premiumSets(), function(premiumSet, index) {
                            let iDList = [];
                            self.currentPersonCost().premiumSets()[index].attendanceItems().forEach(function(item) {
                                iDList.push(item.iD);
                            });
                            self.getItem(iDList, index);
                        });
                        self.isInsert(false);
                    });

                    self.isInsert(false);
                }
                dfd.resolve();
            }).fail((res1, res2) => {
                dfd.reject();
            });
            return dfd.promise();
        }
        
        /**
         * set new data to element on screen
         */
        private loadData(res: Array<any>, index: number) {
            var self = this;
            res.forEach(function(personCostCalc) {
                self.personCostList.push(vmbase.ProcessHandler.fromObjectPerconCost(personCostCalc));
            });
            self.currentPersonCost((_.first(self.personCostList()) == null) ? new vmbase.PersonCostCalculation('', '', "", "9999/12/31", 0, '', []) : self.personCostList()[index]);
            self.personCostList().forEach(function(item) { self.gridPersonCostList.push(new vmbase.GridPersonCostCalculation(item.startDate() + " ~ " + item.endDate())) });
            self.currentGridPersonCost(self.currentPersonCost().startDate() + " ~ " + self.currentPersonCost().endDate());
            ko.utils.arrayForEach(self.currentPersonCost().premiumSets(), function(premiumSet, index) {
                let iDList = [];
                self.currentPersonCost().premiumSets()[index].attendanceItems().forEach(function(item) {
                    iDList.push(item.iD);
                });
                self.getItem(iDList, index);
            });
            if (_.size(self.personCostList()) == 0) {
                self.lastStartDate = "1900/01/01";
            } else {
                self.lastStartDate = _.last(self.personCostList()).startDate();
            } 
        }
        
        /**
         * get list item for each premium setting
         */
        private getItem(iDList: Array<number>, index: number) {
            var self = this;
            var dfd = $.Deferred();
            if (iDList.length != 0) {
                servicebase.getAttendanceItems(iDList)
                    .done(function(res: Array<any>) {
                        let newList = [];
                        res.forEach(function(item) {
                            newList.push(new vmbase.AttendanceItem(item.attendanceItemId, item.attendanceItemName));
                        });
                        self.currentPersonCost().premiumSets()[index].attendanceItems(newList);
                    })
                    .fail(function(res) {
                    });
            }
        }
        
        /**
         * insert/update new person cost calculation 
         */
        saveData(): void {
            var self = this;
            if (self.isInsert()) {
                let index = _.findLastIndex(self.personCostList()) + 1;
                if ((self.currentPersonCost().startDate() != "") && !vmbase.ProcessHandler.validateDateInput(self.currentPersonCost().startDate(), self.lastStartDate)) {
                    servicebase.personCostCalculationInsert(vmbase.ProcessHandler.toObjectPersonCost(self.currentPersonCost()))
                        .done(function(res: Array<any>) {
                            servicebase.personCostCalculationSelect()
                                .done(function(newData: Array<any>) {
                                    self.personCostList.removeAll();
                                    self.gridPersonCostList.removeAll();
                                    self.loadData(newData, index);
                                }).fail(function(res) {

                                });
                        }).fail(function(res) {

                        });
                } else {
                    $("#startDateInput").ntsError('set', vmbase.MSG.MSG065);
                }
            } else {
                let index = _.findIndex(self.personCostList(), self.currentPersonCost())
                servicebase.personCostCalculationUpdate(vmbase.ProcessHandler.toObjectPersonCost(self.currentPersonCost()))
                    .done(function(res: Array<any>) {
                        servicebase.personCostCalculationSelect()
                            .done(function(newData: Array<any>) {
                                self.personCostList.removeAll();
                                self.gridPersonCostList.removeAll();
                                self.loadData(newData, index);
                            }).fail(function(res) {

                            });
                    }).fail(function(res) {

                    });
            }
        }

        /**
         * open premium dialog
         */
        premiumDialog(): void {
            var self = this;
            let index = _.findIndex(self.personCostList(), self.currentPersonCost())
            nts.uk.ui.windows.setShared('isInsert', self.isInsert());
            nts.uk.ui.windows.sub.modal("/view/kml/001/b/index.xhtml", { title: "割増項目の設定", dialogClass: "no-close" }).onClosed(function() {
                if (nts.uk.ui.windows.getShared('updatePremiumSeting') == true) {
                    servicebase.personCostCalculationSelect()
                        .done(function(res: Array<any>) {
                            let processItem;
                            if (self.isInsert()) {
                                processItem = self.currentPersonCost();
                            }
                            self.personCostList.removeAll();
                            self.gridPersonCostList.removeAll();
                            self.loadData(res, index);
                            if (self.isInsert()) {
                                self.currentPersonCost(processItem);
                                let lastItem = _.last(self.personCostList());
                                self.currentPersonCost().premiumSets().forEach(function(item, index) {
                                    item.useAtr(lastItem.premiumSets()[index].useAtr());
                                });
                            }
                        }).fail(function(res) {

                        });
                    if (_.size(self.personCostList()) == 0) {
                        servicebase.premiumItemSelect()
                            .done(function(res: Array<any>) {
                                self.premiumItems.removeAll();
                                res.forEach(function(item) {
                                    self.premiumItems.push(
                                        new vmbase.PremiumItem(
                                            item.companyID,
                                            item.id,
                                            item.attendanceID,
                                            item.name,
                                            item.displayNumber,
                                            item.useAtr));
                                });
                            }).fail(function(res) {

                            });
                    }
                }
            });
        }

        /**
         * open create dialog
         */
        createDialog(): void {
            var self = this;
            let lastestHistory = _.last(self.personCostList());
            nts.uk.ui.windows.setShared('lastestStartDate', lastestHistory == null ? "1900/01/01" : lastestHistory.startDate());
            nts.uk.ui.windows.setShared('size', _.size(self.personCostList()));
            nts.uk.ui.windows.sub.modal("/view/kml/001/c/index.xhtml", { title: "履歴の追加", dialogClass: "no-close" }).onClosed(function() {
                let newStartDate: string = nts.uk.ui.windows.getShared('newStartDate');
                if (newStartDate != null) {
                    let copyDataFlag: boolean = nts.uk.ui.windows.getShared('copyDataFlag');
                    if (_.size(self.personCostList()) != 0) {
                        if (copyDataFlag) {
                            let oldPremiumSets = _.last(self.personCostList()).premiumSets();
                            self.currentPersonCost().startDate(newStartDate);
                            self.currentPersonCost().endDate("9999/12/31");
                            self.currentPersonCost().premiumSets(oldPremiumSets);
                        } else {
                            let premiumItemSetting = _.cloneDeep(self.currentPersonCost().premiumSets());
                            self.currentPersonCost().companyID('');
                            self.currentPersonCost().historyID('');
                            self.currentPersonCost().startDate(newStartDate);
                            self.currentPersonCost().endDate("9999/12/31");
                            self.currentPersonCost().unitPrice(0);
                            self.currentPersonCost().memo('');
                            self.currentPersonCost().premiumSets([]);
                            let newPremiumSets = [];
                            for (let i = 1; i <= 10; i++) {
                                newPremiumSets.push(new vmbase.PremiumSetting("", "", i, 0, i, "", i, premiumItemSetting[i - 1].useAtr(), []));
                            }
                            self.currentPersonCost().premiumSets(newPremiumSets);
                        }
                        self.isInsert(true);
                    } else {
                        self.currentPersonCost().companyID('');
                        self.currentPersonCost().historyID('');
                        self.currentPersonCost().startDate(newStartDate);
                        self.currentPersonCost().endDate("9999/12/31");
                        self.currentPersonCost().unitPrice(0);
                        self.currentPersonCost().memo('');
                        self.currentPersonCost().premiumSets([]);
                        let newPremiumSets = [];
                        for (let i = 1; i <= 10; i++) {
                            newPremiumSets.push(new vmbase.PremiumSetting("", "", i, 0, i, "", i, self.premiumItems()[i - 1].useAtr(), []));
                        }
                        self.currentPersonCost().premiumSets(newPremiumSets);
                        self.isInsert(true);
                    }
                }
            });
        }

        /**
         * open edit dialog
         */
        editDialog(): void {
            var self = this;
            let index = _.findIndex(self.personCostList(), self.currentPersonCost())
            nts.uk.ui.windows.setShared('personCostList', self.personCostList());
            nts.uk.ui.windows.setShared('currentPersonCost', self.currentPersonCost());
            nts.uk.ui.windows.sub.modal("/view/kml/001/d/index.xhtml", { title: "履歴の編集", dialogClass: "no-close" }).onClosed(function() {
                let editedIndex = nts.uk.ui.windows.getShared('isEdited');
                if (editedIndex != null) {
                    if (editedIndex == 1) index -= 1;
                    servicebase.personCostCalculationSelect()
                        .done(function(res: Array<any>) {
                            self.personCostList.removeAll();
                            self.gridPersonCostList.removeAll();
                            self.loadData(res, index);
                        }).fail(function(res) {

                        });
                }
            });;
        }
        
        /**
         * open select item dialog
         */
        selectDialog(data, index): void {
            var self = this;
            let currentList = [];
            ko.utils.arrayForEach(data.attendanceItems(), function(attendanceItem: vmbase.AttendanceItem) {
                currentList.push(attendanceItem.iD);
            });
            nts.uk.ui.windows.setShared('SelectedAttendanceId', currentList);
            nts.uk.ui.windows.setShared('Multiple', true);
            servicebase.getAttendanceItemByType(0)
                .done(function(res: Array<any>) {
                    nts.uk.ui.windows.setShared('AllAttendanceObj', _.map(res, function(item) { return item.attendanceItemId }));
                    nts.uk.ui.windows.sub.modal("/view/kdl/021/a/index.xhtml", { title: "割増項目の設定", dialogClass: "no-close" }).onClosed(function() {
                        let newList = nts.uk.ui.windows.getShared('selectedChildAttendace');
                        if (newList != null) {
                            if (newList.length != 0) {
                                if (!_.isEqual(newList, currentList)) {
                                    self.getItem(newList, index);
                                }
                            } else {
                                self.currentPersonCost().premiumSets()[index].attendanceItems([]);
                            }
                        }
                    });
                }).fail(function(res) {

                });

        }
    }
}