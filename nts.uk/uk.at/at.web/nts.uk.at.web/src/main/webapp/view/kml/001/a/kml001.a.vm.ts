module nts.uk.at.view.kml001.a {
    export module viewmodel {
        import servicebase = kml001.shr.servicebase;
        import vmbase = kml001.shr.vmbase;
        export class ScreenModel extends ko.ViewModel {
            gridPersonCostList: KnockoutObservableArray<vmbase.GridPersonCostCalculation>;
            currentGridPersonCost: KnockoutObservable<string>;
            personCostList: KnockoutObservableArray<vmbase.PersonCostCalculation>;
            currentPersonCost: KnockoutObservable<vmbase.PersonCostCalculation> = ko.observable(null);
            premiumItems: KnockoutObservableArray<vmbase.PremiumItem>;
            lastStartDate: string;
            isInsert: KnockoutObservable<Boolean>;
            newStartDate: KnockoutObservable<string>;
            viewAttendanceItems: KnockoutObservableArray<KnockoutObservable<string>>;
            textKML001_40 = nts.uk.resource.getText("KML001_40");
            isLastItem: KnockoutObservable<Boolean> = ko.observable(false);
            standardDate: KnockoutObservable<string> = ko.observable(null);
            langId: KnockoutObservable<string> = ko.observable('ja');/* 

            calculationSetting: KnockoutObservable<number> = ko.observable(1);
            roundingUnitPrice: KnockoutObservable<number> = ko.observable(0);
            roundingAmount: KnockoutObservable<number> = ko.observable(1);
            inUnits: KnockoutObservable<number> = ko.observable(0); */
            unitPriceOpt: KnockoutObservaleArray<any> = ko.observableArray([]);
            _calculationSetting: KnockoutObservable<number> = ko.observable(1);
            constructor() {
                super();

                $('#formula-child-1').html(nts.uk.resource.getText('KML001_7').replace(/\n/g, '<br/>'));
                var self = this;
                self.personCostList = ko.observableArray([]);
                self.currentPersonCost = ko.observable(
                    new vmbase.PersonCostCalculation('', '', "", "9999/12/31", 0, '', [], 1, 0, 1, 0)
                );
                console.log(self.currentPersonCost());
                self.newStartDate = ko.observable(null);
                self.gridPersonCostList = ko.observableArray([]);
                self.currentGridPersonCost = ko.observable(null);
                self.premiumItems = ko.observableArray([]);
                self.isInsert = ko.observable(true);
                self.lastStartDate = "1900/01/01";
                self.viewAttendanceItems = ko.observableArray([
                    ko.observable(''),
                    ko.observable(''),
                    ko.observable(''),
                    ko.observable(''),
                    ko.observable(''),
                    ko.observable(''),
                    ko.observable(''),
                    ko.observable(''),
                    ko.observable(''),
                    ko.observable('')
                ]);

                self.unitPriceOpt([
                    { code: 0, name: self.$i18n('KML001_22') },
                    { code: 1, name: self.$i18n('KML001_23') },
                    { code: 2, name: self.$i18n('KML001_24') },
                    { code: 3, name: self.$i18n('KML001_25') },
                    { code: 4, name: self.$i18n('KML001_26') }
                ]);

                self.currentPersonCost().unitPrice.subscribe((newValue) => {
                    self.changeUnitPrice(newValue);
                });

                self.currentPersonCost().calculationSetting.subscribe((newValue) => {
                    self._calculationSetting(newValue);
                });
            }

            /**
             * get data on start page
             */

            startPage(): JQueryPromise<any> {
                nts.uk.ui.block.invisible();
                var self = this;
                var dfd = $.Deferred();
                for (let i = 0; i < 5; i++) {
                    self.currentPersonCost().premiumSets.push(
                        new vmbase.PremiumSetting(self.uuid(), self.uuid(), i, 2.5, '表示数 ' + i.toString(), i, [], 1)
                    );
                }
                /* var dfdPremiumItemSelect = servicebase.premiumItemSelect();
                var dfdPersonCostCalculationSelect = servicebase.personCostCalculationSelect();
                $.when(dfdPremiumItemSelect, dfdPersonCostCalculationSelect).done((premiumItemSelectData, dfdPersonCostCalculationSelectData) => {
                    // Premium Item Select: Done
                    premiumItemSelectData.forEach(function(item) {
                        self.premiumItems.push(
                            new vmbase.PremiumItem(
                                item.companyID,
                                item.displayNumber,
                                item.name,
                                item.useAtr, 
                                false
                            ));
                    });
                    let sum = 0;
                    self.premiumItems().forEach(function(item){
                        if(item.useAtr()) {
                            self.currentPersonCost().premiumSets.push(
                                new vmbase.PremiumSetting("", "", item.displayNumber(), 100, item.name(), item.useAtr(), []));
                        }
                        sum+=item.useAtr();    
                    });
                    if(sum==0){ // open premiumItem dialog when no premium is used
                        self.premiumDialog();        
                    }
                    // PersonCostCalculationSelect: Done
                    if (dfdPersonCostCalculationSelectData.length) {
                        self.loadData(dfdPersonCostCalculationSelectData, 0).then(()=>{
                            nts.uk.ui.block.clear();    
                            self.currentGridPersonCost.subscribe(function(value) { // change current personCostCalculation when grid is selected
                                if(value!=null) {
                                    //nts.uk.ui.block.invisible();
                                    let historyID = _.find(self.personCostList(), function(o) { return o.startDate() == _.split(value, self.textKML001_40, 1)[0]; }).historyID();
                                    servicebase.findByHistoryID(historyID).done(data => {
                                        self.currentPersonCost(vmbase.ProcessHandler.createPersonCostCalFromValue(data, self.premiumItems()));   
                                        self.checkLastItem();
                                        self.newStartDate(self.currentPersonCost().startDate());
                                        _.defer(() => {$("#startDateInput").ntsError('clear');}); 
                                        nts.uk.ui.errors.clearAll();
                                        let allRequest = [];
                                        ko.utils.arrayForEach(self.currentPersonCost().premiumSets(), function(premiumSet, index) {
                                            let iDList = [];
                                            self.currentPersonCost().premiumSets()[index].attendanceItems().forEach(function(item) {
                                                iDList.push(item.shortAttendanceID);
                                            });
                                            let request = self.getItem(iDList, index);
                                            allRequest.push(request);
                                        });
                                        $.when.apply($, allRequest).then(()=>{
                                            nts.uk.ui.block.clear();            
                                        });   
                                    }).fail(res => {
                                            
                                    }).always(() => {
                                        nts.uk.ui.block.clear();
                                    });
                                    self.isInsert(false);
                                    $("#memo").focus(); 
                                } else {
                                    $("#startDateInput").focus();    
                                }
                            });    
                        });
                        self.isInsert(false);
                    } else nts.uk.ui.block.clear();
                    dfd.resolve();
                }).fail((res1, res2) => {
                    nts.uk.ui.dialog.alertError(res1.message+'\n'+res2.message).then(function(){nts.uk.ui.block.clear();});
                    dfd.reject();
                }); */
                nts.uk.ui.block.clear();
                dfd.resolve();
                return dfd.promise();
            }
            private uuid() {
                return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
                    var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
                    return v.toString(16);
                });
            }
            /**
             * set new data to element on screen
             */
            private loadData(res: Array<any>, index: number) {
                var self = this;
                var dfd = $.Deferred();
                let descRes = _.orderBy(res, ['startDate'], ['desc']);
                // set data to currrent PersonCostCalculation
                descRes.forEach(function (personCostCalc) {
                    self.personCostList.push(vmbase.ProcessHandler.createPersonCostCalFromValue(personCostCalc, self.premiumItems()));
                });
                // set data to grid list
                let a = [];
                self.personCostList().forEach(function (item) { a.push(new vmbase.GridPersonCostCalculation(item.startDate() + self.textKML001_40 + item.endDate())) });
                self.gridPersonCostList(a);

                if (self.personCostList() != null) {
                    let historyID = self.personCostList()[index].historyID();
                    let lastHistoryID = _.first(self.personCostList()).historyID();
                    $.when(servicebase.findByHistoryID(historyID), servicebase.findByHistoryID(lastHistoryID))
                        .done((currentData, lastData) => {
                            let allRequest = [];
                            self.currentPersonCost(vmbase.ProcessHandler.createPersonCostCalFromValue(currentData, self.premiumItems()));
                            self.personCostList()[0] = vmbase.ProcessHandler.createPersonCostCalFromValue(lastData, self.premiumItems());
                            self.personCostList.valueHasMutated();
                            self.checkLastItem();
                            self.newStartDate(self.currentPersonCost().startDate());
                            self.currentGridPersonCost(self.currentPersonCost().startDate() + self.textKML001_40 + self.currentPersonCost().endDate());
                            ko.utils.arrayForEach(self.currentPersonCost().premiumSets(), function (premiumSet, i) {
                                let iDList = [];
                                self.currentPersonCost().premiumSets()[i].attendanceItems().forEach(function (item) {
                                    iDList.push(item.shortAttendanceID);
                                });
                                let request = self.getItem(iDList, i);
                                allRequest.push(request);
                            });
                            ko.utils.arrayForEach(self.personCostList()[0].premiumSets(), function (premiumSet, i) {
                                let iDList = [];
                                self.personCostList()[0].premiumSets()[i].attendanceItems().forEach(function (item) {
                                    iDList.push(item.shortAttendanceID);
                                });
                                let request = self.getItem(iDList, i);
                                allRequest.push(request);
                            });
                            $.when.apply($, allRequest).then(() => {
                                dfd.resolve();
                            });
                            if (_.size(self.personCostList()) == 0) {
                                self.lastStartDate = "1900/01/01";
                            } else {
                                self.lastStartDate = _.first(self.personCostList()).startDate();
                            }
                        }).fail(res => {
                            dfd.reject();
                        });
                } else {
                    dfd.resolve();
                }
                return dfd.promise();
            }

            /**
             * get list item for each premium setting
             */
            private getItem(iDList: Array<number>, index: number) {
                var self = this;
                var dfd = $.Deferred();
                if (iDList.length != 0) {
                    self.currentPersonCost().premiumSets()[index].attendanceItems.removeAll();
                    servicebase.getAttendanceItems(iDList)
                        .done(function (res: Array<any>) {
                            let newList = [], arrSort = [];
                            // fixbug 105897: sort base displayNumber
                            arrSort = _.orderBy(res, ['attendanceItemDisplayNumber'], ['asc']);
                            arrSort.forEach(function (item) {
                                newList.push(new vmbase.AttendanceItem(item.attendanceItemId, item.attendanceItemName));
                            });
                            self.currentPersonCost().premiumSets()[index].attendanceItems(newList);
                            self.createViewAttendanceItems(newList, index);
                            dfd.resolve();
                        })
                        .fail(function (res) {
                            nts.uk.ui.dialog.alertError(res.message);
                            dfd.reject();
                        });
                } else {
                    self.createViewAttendanceItems([], index);
                    dfd.resolve();
                }
                return dfd.promise();
            }

            /**
             * insert/update new person cost calculation 
             */
            saveData(): void {
                nts.uk.ui.block.invisible();
                var self = this;
                $("#startDateInput").trigger("validate");
                $("#memo").trigger("validate");
                $(".premiumPercent").trigger("validate");
                if (!nts.uk.ui.errors.hasError()) {
                    if (self.isInsert()) {
                        if (moment(self.newStartDate()).isAfter(moment(self.lastStartDate))) {
                            // insert new data if startDate have no error
                            let ymd = self.newStartDate();
                            self.currentPersonCost().startDate(ymd);
                            servicebase.personCostCalculationInsert(vmbase.ProcessHandler.toObjectPersonCost(self.currentPersonCost()))
                                .done(function (res: Array<any>) {
                                    servicebase.personCostCalculationSelect()
                                        .done(function (newData: Array<any>) {
                                            // refresh data list
                                            self.personCostList.removeAll();
                                            self.gridPersonCostList.removeAll();
                                            self.isInsert(false);
                                            self.loadData(newData, 0);
                                            nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function () { nts.uk.ui.block.clear(); });
                                        }).fail(function (res) {
                                            nts.uk.ui.dialog.alertError(res.message).then(function () { nts.uk.ui.block.clear(); });
                                        });
                                }).fail(function (res) {
                                    nts.uk.ui.dialog.alertError(res.message).then(function () { nts.uk.ui.block.clear(); });
                                });

                        } else {
                            $("#startDateInput").ntsError('set', { messageId: "Msg_65" });
                            nts.uk.ui.block.clear();
                        }
                    } else {
                        // update new data for current personCostCalculation
                        let index = _.findIndex(self.personCostList(), function (item) { return item.historyID() == self.currentPersonCost().historyID() });
                        self.currentPersonCost().startDate(self.newStartDate());
                        servicebase.personCostCalculationUpdate(vmbase.ProcessHandler.toObjectPersonCost(self.currentPersonCost()))
                            .done(function (res: Array<any>) {
                                servicebase.personCostCalculationSelect()
                                    .done(function (newData: Array<any>) {
                                        // refresh data list
                                        self.personCostList.removeAll();
                                        self.gridPersonCostList.removeAll();
                                        self.loadData(newData, index);
                                        nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function () { nts.uk.ui.block.clear(); });
                                    }).fail(function (res) {
                                        nts.uk.ui.dialog.alertError(res.message).then(function () { nts.uk.ui.block.clear(); });
                                    });
                            }).fail(function (res) {
                                nts.uk.ui.dialog.alertError(res.message).then(function () { nts.uk.ui.block.clear(); });
                            });

                    }
                } else nts.uk.ui.block.clear();
            }

            /**
             * open premium dialog
             */
            premiumDialog(): void {
                nts.uk.ui.block.invisible();
                var self = this;
                let currentIndex = _.findIndex(self.personCostList(), function (item) { return item.historyID() == self.currentPersonCost().historyID() });
                let index = currentIndex ? currentIndex : 0;
                let oldPremiumSets = self.clonePersonCostCalculation(self.currentPersonCost()).premiumSets();
                nts.uk.ui.windows.setShared('isInsert', self.isInsert());
                nts.uk.ui.windows.sub.modal("/view/kml/001/b/index.xhtml", { title: "割増項目の設定", dialogClass: "no-close" }).onClosed(function () {
                    nts.uk.ui.block.invisible();
                    self.langId(nts.uk.ui.windows.getShared("KML001_B_LANGID"));
                    if (nts.uk.ui.windows.getShared('updatePremiumSeting') == true) {
                        nts.uk.ui.errors.clearAll();
                        var dfdPremiumItemSelect = servicebase.premiumItemSelect();
                        var dfdPersonCostCalculationSelect = servicebase.personCostCalculationSelect();
                        $.when(dfdPremiumItemSelect, dfdPersonCostCalculationSelect).done((premiumItemSelectData, dfdPersonCostCalculationSelectData) => {
                            // Premium Item Select: Done
                            self.premiumItems.removeAll();
                            premiumItemSelectData.forEach(function (item) {
                                self.premiumItems.push(
                                    new vmbase.PremiumItem(
                                        item.companyID,
                                        item.displayNumber,
                                        item.name,
                                        item.useAtr,
                                        false
                                    ));
                            });
                            // PersonCostCalculationSelect: Done
                            if (!dfdPersonCostCalculationSelectData.length) {
                                self.currentPersonCost().premiumSets.removeAll();
                                self.premiumItems().forEach(function (item) {
                                    if (item.useAtr()) {
                                        self.currentPersonCost().premiumSets.push(
                                            new vmbase.PremiumSetting("", "", item.displayNumber(), 100, item.name(), item.useAtr(), []));
                                    }
                                });
                                $("#startDateInput").focus();
                            } else {
                                if (self.isInsert()) {
                                    self.currentPersonCost().premiumSets.removeAll();
                                    self.premiumItems().forEach(function (item) {
                                        if (item.useAtr()) {
                                            let currentIndexSet = _.find(oldPremiumSets, function (o) { return o.displayNumber() == item.displayNumber(); });
                                            if (nts.uk.util.isNullOrUndefined(currentIndexSet)) {
                                                self.currentPersonCost().premiumSets.push(
                                                    new vmbase.PremiumSetting("", "", item.displayNumber(), 100, item.name(), item.useAtr(), [], item.unitPrice));
                                            } else {
                                                self.currentPersonCost().premiumSets.push(currentIndexSet);
                                            }
                                        }
                                    });
                                    self.currentPersonCost().premiumSets.valueHasMutated();
                                    self.currentPersonCost().premiumSets().forEach((item, index) => {
                                        self.createViewAttendanceItems(item.attendanceItems(), index);
                                    });
                                    $("#startDateInput").focus();
                                } else {
                                    self.personCostList.removeAll();
                                    self.gridPersonCostList.removeAll();
                                    self.loadData(dfdPersonCostCalculationSelectData, index);
                                    $("#memo").focus();
                                }
                            }
                            nts.uk.ui.block.clear();
                        }).fail((res1, res2) => {
                            nts.uk.ui.dialog.alertError(res1.message + '\n' + res2.message).then(function () { nts.uk.ui.block.clear(); });
                        });
                    } else {
                        nts.uk.ui.block.clear();
                    }
                    self.setTabindex();
                });
            }

            /**
             * open create dialog
             */
            createDialog(): void {
                nts.uk.ui.block.invisible();
                var self = this;
                let lastestHistory = _.first(self.personCostList());
                nts.uk.ui.windows.setShared('lastestStartDate', lastestHistory == null ? "1900/01/01" : lastestHistory.startDate());
                nts.uk.ui.windows.setShared('size', _.size(self.personCostList()));
                nts.uk.ui.windows.sub.modal("/view/kml/001/c/index.xhtml", { title: "履歴の追加", dialogClass: "no-close" }).onClosed(function () {
                    let newStartDate: string = nts.uk.ui.windows.getShared('newStartDate');
                    if (newStartDate != null) {
                        nts.uk.ui.errors.clearAll();
                        newStartDate = newStartDate;
                        let copyDataFlag: boolean = nts.uk.ui.windows.getShared('copyDataFlag');
                        if (_.size(self.personCostList()) != 0) { // when PersonCostCalculation list not empty
                            if (copyDataFlag) { // when new data is copy
                                let lastItem = self.clonePersonCostCalculation(_.first(self.personCostList()));
                                self.currentPersonCost(
                                    new vmbase.PersonCostCalculation(
                                        lastItem.companyID(),
                                        '',
                                        newStartDate,
                                        "9999/12/31",
                                        lastItem.unitPrice(),
                                        lastItem.memo(),
                                        [], 1, 0, 1, 0)
                                    );
                                self.currentPersonCost().premiumSets(lastItem.premiumSets());
                                ko.utils.arrayForEach(self.currentPersonCost().premiumSets(), function (premiumSet, i) {
                                    let iDList = [];
                                    self.currentPersonCost().premiumSets()[i].attendanceItems().forEach(function (item) {
                                        iDList.push(item.shortAttendanceID);
                                    });
                                    self.getItem(iDList, i);
                                });
                                self.newStartDate(self.currentPersonCost().startDate());
                            } else {
                                self.currentPersonCost(
                                    new vmbase.PersonCostCalculation(
                                        '',
                                        '',
                                        newStartDate,
                                        "9999/12/31",
                                        0,
                                        '',
                                        [], 1, 0, 1, 0));
                                let newPremiumSets = [];
                                self.premiumItems().forEach(function (item, index) {
                                    if (item.useAtr()) {
                                        newPremiumSets.push(
                                            new vmbase.PremiumSetting(
                                                "",
                                                "",
                                                item.displayNumber(),
                                                100,
                                                item.name(),
                                                item.useAtr(),
                                                [], 0));
                                    }
                                });
                                self.currentPersonCost().premiumSets(newPremiumSets);
                                self.viewAttendanceItems([
                                    ko.observable(''),
                                    ko.observable(''),
                                    ko.observable(''),
                                    ko.observable(''),
                                    ko.observable(''),
                                    ko.observable(''),
                                    ko.observable(''),
                                    ko.observable(''),
                                    ko.observable(''),
                                    ko.observable('')
                                ]);
                                self.newStartDate(self.currentPersonCost().startDate());
                            }
                            self.isInsert(true);
                        } else { // when PersonCostCalculation list empty
                            self.currentPersonCost(
                                new vmbase.PersonCostCalculation(
                                    '',
                                    '',
                                    newStartDate,
                                    "9999/12/31",
                                    0,
                                    '',
                                    [], 1, 0, 1, 0));
                            let newPremiumSets = [];
                            self.premiumItems().forEach(function (item, index) {
                                if (item.useAtr()) {
                                    newPremiumSets.push(
                                        new vmbase.PremiumSetting(
                                            "",
                                            "",
                                            item.displayNumber(),
                                            100,
                                            item.name(),
                                            item.useAtr(),
                                            [], 0));
                                }
                            });
                            self.currentPersonCost().premiumSets(newPremiumSets);
                            self.viewAttendanceItems([
                                ko.observable(''),
                                ko.observable(''),
                                ko.observable(''),
                                ko.observable(''),
                                ko.observable(''),
                                ko.observable(''),
                                ko.observable(''),
                                ko.observable(''),
                                ko.observable(''),
                                ko.observable('')
                            ]);
                            self.newStartDate(self.currentPersonCost().startDate());
                        }
                        self.currentGridPersonCost(null);
                        $("#startDateInput").focus();
                    } else {
                        if (self.isInsert()) {
                            $("#startDateInput").focus();
                        } else {
                            $("#memo").focus();
                        }
                    }
                    nts.uk.ui.block.clear();
                    self.setTabindex();
                });
            }

            /**
             * open edit dialog 
             */
            editDialog(): void {
                nts.uk.ui.block.invisible();
                var self = this;
                let index = _.findIndex(self.personCostList(), function (o) { return self.currentPersonCost().startDate() == o.startDate(); });
                nts.uk.ui.windows.setShared('personCostList',
                    _.map(self.personCostList(), item => vmbase.ProcessHandler.toObjectPersonCost(item))
                );
                nts.uk.ui.windows.setShared('currentPersonCost', vmbase.ProcessHandler.toObjectPersonCost(self.currentPersonCost()));
                nts.uk.ui.windows.sub.modal("/view/kml/001/d/index.xhtml", { title: "履歴の編集", dialogClass: "no-close" }).onClosed(function () {
                    let editedIndex = nts.uk.ui.windows.getShared('isEdited');
                    if (editedIndex != null) { // when data is edited
                        nts.uk.ui.errors.clearAll();
                        if (editedIndex == 1) index -= 1; // when edit is delete, set index to last item
                        servicebase.personCostCalculationSelect()
                            .done(function (res: Array<any>) {
                                // refresh data list
                                self.personCostList.removeAll();
                                self.gridPersonCostList.removeAll();
                                self.loadData(res, 0);
                                nts.uk.ui.block.clear();
                            }).fail(function (res) {
                                nts.uk.ui.dialog.alertError(res.message).then(function () { nts.uk.ui.block.clear(); });
                            });
                    } else {
                        nts.uk.ui.block.clear();
                    }
                    $("#memo").focus();
                    self.setTabindex();
                });;
            }

            /**
             * open select item dialog
             */
            selectDialog(data, index): void {
                nts.uk.ui.block.invisible();
                var self = this;
                let currentList = [];
                ko.utils.arrayForEach(data.attendanceItems(), function (attendanceItem: vmbase.AttendanceItem) {
                    currentList.push(attendanceItem.shortAttendanceID);
                });
                nts.uk.ui.windows.setShared('SelectedAttendanceId', currentList, true);
                nts.uk.ui.windows.setShared('Multiple', true);
                servicebase.getAttendanceItemByType(0)
                    .done(function (res: Array<any>) {
                        nts.uk.ui.windows.setShared('AllAttendanceObj', _.map(res, function (item) { return item.attendanceItemId }));
                        nts.uk.ui.windows.sub.modal("/view/kdl/021/a/index.xhtml", { title: "割増項目の設定", dialogClass: "no-close" }).onClosed(function () {
                            let newList = nts.uk.ui.windows.getShared('selectedChildAttendace');
                            if (newList != null) {
                                nts.uk.ui.errors.clearAll();
                                if (newList.length != 0) {
                                    if (!_.isEqual(newList, currentList)) {
                                        //clone Knockout Object
                                        self.currentPersonCost().startDate(self.newStartDate());
                                        self.currentPersonCost(self.clonePersonCostCalculation(self.currentPersonCost()));
                                        self.newStartDate(self.currentPersonCost().startDate());
                                        self.getItem(newList, index);
                                    }
                                } else {
                                    self.currentPersonCost().premiumSets()[index].attendanceItems([]);
                                }
                            }

                            nts.uk.ui.block.clear();
                            self.setTabindex();
                        });
                    }).fail(function (res) {
                        nts.uk.ui.dialog.alertError(res.message).then(function () { nts.uk.ui.block.clear(); });
                    });

            }

            checkLastItem() {
                var self = this;
                let index = _.findIndex(self.personCostList(), function (o) { return self.currentPersonCost().startDate() == o.startDate(); });
                if (index == 0) {
                    self.isLastItem(true);
                } else {
                    self.isLastItem(false);
                }
            }

            /**
             * clone PersonCostCalculation Object
             */
            private clonePersonCostCalculation(object: vmbase.PersonCostCalculation): vmbase.PersonCostCalculation {
                var self = this;
                let result = vmbase.ProcessHandler.fromObjectPerconCost(
                    _.cloneDeep(
                        vmbase.ProcessHandler.toObjectPersonCost(object)));
                return result;
            }

            private createViewAttendanceItems(attendances: Array<vmbase.AttendanceItem>, index: number): void {
                var self = this;
                let a = [];
                let s = '';
                ko.utils.arrayForEach(attendances, (attendanceItem) => {
                    if (nts.uk.util.isNullOrEmpty(s)) { s = attendanceItem.name; } else { s += ' + ' + attendanceItem.name; }
                });
                a.push(s);
                self.viewAttendanceItems()[index](a);
            }

            private setTabindex(): void {
                /* $('.ui-widget.ui-helper-clearfix.ui-corner-all').attr('tabindex', -1);
                $("#functions-area > button:NTH-CHILD(1)").attr('tabindex', 1);
                $("#functions-area > button:NTH-CHILD(2)").attr('tabindex', 2);
                $(".dateControlBtn:NTH-CHILD(1)").attr('tabindex', 3);
                $(".dateControlBtn:NTH-CHILD(2)").attr('tabindex', 4);
                $("#dateRange-list").attr('tabindex', 5);
                $("#startDateInput").attr('tabindex', 6); 
                $("#combo-box").attr('tabindex', 7); 
                $("#memo").attr('tabindex', 8);
                $("#premium-set-tbl > tbody > tr > td:NTH-CHILD(2) input").each(function (i) { $(this).attr('tabindex', i*2 + 9); });
                $("#premium-set-tbl > tbody > tr > td:NTH-CHILD(3) button").each(function (i) { $(this).attr('tabindex', i*2 + 10); });  */
            }

            showExportBtn() {
                if (nts.uk.util.isNullOrUndefined(__viewContext.user.role.attendance)
                    && nts.uk.util.isNullOrUndefined(__viewContext.user.role.payroll)
                    && nts.uk.util.isNullOrUndefined(__viewContext.user.role.officeHelper)
                    && nts.uk.util.isNullOrUndefined(__viewContext.user.role.personnel)) {
                    $("#print-button").hide();
                } else {
                    $("#print-button").show();
                }
            }

            /**
          * closeDialog
          */
            public opencdl028Dialog() {
                var self = this;
                let params = {
                    //    date: moment(new Date()).toDate(),
                    mode: 1 //basedate
                };
                nts.uk.ui.windows.setShared("CDL028_INPUT", params);
                nts.uk.ui.windows.sub.modal("com", "/view/cdl/028/a/index.xhtml").onClosed(function () {
                    var params = nts.uk.ui.windows.getShared("CDL028_A_PARAMS");
                    if (!nts.uk.util.isNullOrUndefined(params) && !nts.uk.util.isNullOrUndefined(params.standardDate)) {
                        self.exportExcel(params.standardDate);
                    }
                });

            }

            public exportExcel(param): void {
                var self = this;
                let params = ({
                    baseDate: param,
                    languageId: _.isNil(self.langId()) ? "ja" : self.langId()
                });
                nts.uk.ui.block.grayout();
                servicebase.saveAsExcel(params).done(function () {
                }).fail(function (error) {
                    nts.uk.ui.dialog.alertError({ messageId: error.messageId });
                }).always(function () {
                    nts.uk.ui.block.clear();
                });

            }

            changeUnitPrice(newPrice: number) {
                const self = this;
                _.forEach(self.currentPersonCost().premiumSets(), (item, index) => {
                    self.currentPersonCost().premiumSets()[index].unitPrice(newPrice);
                });
            }

        }
    }

    //--------------------------------------------
    //人件費単価端数処理
    export enum UnitPriceRounding {
        // 切り上げ
        ROUND_UP = 0, //Enum_UnitPriceRounding_roundUp
        //切り捨て
        TRUNCATION = 1,//Enum_UnitPriceRounding_truncation
        //四捨五入
        DOWN_4_UP_5 = 2,//Enum_UnitPriceRounding_down4Up5
    }
    // 端数処理位置
    export enum AmountUnit {

        /** The one yen. */
        // 1円
        ONE_YEN = 1,//Enum_AmountUnit_oneYen

        /** The ten yen. */
        // 10円
        TEN_YEN = 10, //Enum_AmountUnit_tenYen

        /** The one hundred yen. */
        // 100円
        ONE_HUNDRED_YEN = 100,//Enum_AmountUnit_oneHundredYen

        /** The one thousand yen. */
        // 1000円
        ONE_THOUSAND_YEN = 1000, //Enum_AmountUnit_oneThousandYen
    }
    // 端数処理
    export enum Rounding {

        /** The rounding down. */
        // 切り捨て
        ROUNDING_DOWN = 0, //"切り捨て", "Enum_Rounding_Down"),

        /** The rounding up. */
        // 切り上げ
        ROUNDING_UP = 1, //"切り上げ", "Enum_Rounding_Up"),

        /** The rounding down over. */
        // 未満切捨、以上切上
        ROUNDING_DOWN_OVER = 2, //"未満切捨、以上切上", "Enum_Rounding_Down_Over");
    }
}