module nts.uk.at.view.kwr006.c {

    import service = nts.uk.at.view.kwr006.c.service;
    export module viewmodel {
        export class ScreenModel {
            items: KnockoutObservableArray<ItemModel>;
            outputItemList: KnockoutObservableArray<ItemModel>;
            currentCodeList: KnockoutObservable<any>;
            columns: KnockoutObservableArray<any>;


            allMainDom: KnockoutObservable<any>;
            outputItemPossibleLst: KnockoutObservableArray<ItemModel>;

            currentCodeListSwap: KnockoutObservableArray<ItemModel>;
            C3_2_value: KnockoutObservable<string>;
            C3_3_value: KnockoutObservable<string>;

            enableBtnDel: KnockoutObservable<boolean>;
            enableCodeC3_2: KnockoutObservable<boolean>;
            //a8-2
            itemListConditionSet: KnockoutObservableArray<any>;
            selectedCodeA8_2: KnockoutObservable<number>;
            enableConfigErrCode: KnockoutObservable<boolean>;
            
            storeCurrentCodeBeforeCopy: KnockoutObservable<string>;

            constructor() {
                var self = this;
                self.C3_2_value = ko.observable("");
                self.C3_3_value = ko.observable("");

                self.allMainDom = ko.observable();
                self.outputItemPossibleLst = ko.observableArray([]);

                self.currentCodeListSwap = ko.observableArray([]);
                self.currentCodeListSwap.subscribe(function(value) {
                    console.log(value);
                })
                self.outputItemList = ko.observableArray([]);
                self.currentCodeList = ko.observable();

                self.enableBtnDel = ko.observable(false);
                self.enableCodeC3_2 = ko.observable(false);
                self.currentCodeList.subscribe(function(value) {
                    let codeChoose = _.find(self.allMainDom(), function(o: any) {
                        return value == o.itemCode;
                    });
                    if (!_.isUndefined(codeChoose) && !_.isNull(codeChoose)) {
                        nts.uk.ui.errors.clearAll();
                        self.C3_2_value(codeChoose.itemCode);
                        self.C3_3_value(codeChoose.itemName);
                        self.getOutputItemMonthlyWorkSchedule(_.find(self.allMainDom(), function(o: any) {
                            return codeChoose.itemCode == o.itemCode;
                        }));
                        self.enableBtnDel(true);
                        self.enableCodeC3_2(false);
                    } else {
                        self.C3_3_value('');
                        self.C3_2_value('');
                        self.getOutputItemMonthlyWorkSchedule([]);
                        self.enableBtnDel(false);
                        self.enableCodeC3_2(true);
                    }
                });
                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText("KWR006_40"), prop: 'code', width: 70 },
                    { headerText: nts.uk.resource.getText("KWR006_41"), prop: 'name', width: 180 }
                ]);
                self.itemListConditionSet = ko.observableArray([
                    new BoxModel('0', nts.uk.resource.getText("KWR006_56")),
                    new BoxModel('1', nts.uk.resource.getText("KWR006_57"))
                ]);
                self.items = ko.observableArray([]);
                self.selectedCodeA8_2 = ko.observable(0);
                self.enableConfigErrCode = ko.observable(true);
                self.selectedCodeA8_2.subscribe(function(value) {
                    if (value == 0) {
                        self.enableConfigErrCode(true);
                    } else {
                        self.enableConfigErrCode(false);
                    }
                })
                self.selectedCodeA8_2.valueHasMutated();
                
                self.storeCurrentCodeBeforeCopy = ko.observable('');
            }

            /*
             * set data to C7_2, C7_8 
            */
            private getOutputItemMonthlyWorkSchedule(data: any): void {
                let self = this;

                // variable temporary                
                let temp1: any[] = [];
                let temp2: any[] = [];
                self.items.removeAll();
                self.currentCodeListSwap.removeAll();
                _.forEach(data.lstDisplayedAttendance, function(value, index) {
                    temp1.push({ code: value.attendanceDisplay + "", name: value.attendanceName });
                })
                _.forEach(self.outputItemPossibleLst(), function(value) {
                    temp2.push(value);
                })
                // refresh data for C7_2
                self.items(temp2);
                // refresh data for C7_8
                self.currentCodeListSwap(temp1);

            }

            /*
            Open D screen
            */
            openScreenD() {
                var self = this;
                nts.uk.ui.windows.setShared('KWR006_D', self.outputItemPossibleLst(), true);
                if (!_.isEmpty(self.currentCodeList())) {
                    self.storeCurrentCodeBeforeCopy(self.currentCodeList());
                }
                nts.uk.ui.windows.sub.modal('/view/kwr/006/d/index.xhtml').onClosed(function(): any {
                    nts.uk.ui.errors.clearAll();
                    if (!_.isEmpty(nts.uk.ui.windows.getShared('KWR006_D'))) {
                        self.currentCodeList('');
                        if (!_.isUndefined(nts.uk.ui.windows.getShared('KWR006_D').lstAtdChoose) && !_.isEmpty(nts.uk.ui.windows.getShared('KWR006_D').lstAtdChoose)) {
                            self.currentCodeListSwap(nts.uk.ui.windows.getShared('KWR006_D').lstAtdChoose);
                            $('#C3_3').focus();
                        } else {
                            $('#C3_2').focus();
                        }

                        self.C3_2_value(nts.uk.ui.windows.getShared('KWR006_D').codeCopy);
                        self.C3_3_value(nts.uk.ui.windows.getShared('KWR006_D').nameCopy);
                    } else {
                        self.currentCodeList(self.storeCurrentCodeBeforeCopy());
                    }
                });
            }

            /*
                New mode
            */
            private newMode(): void {
                let self = this;
                self.currentCodeList('');
                self.C3_2_value('');
                self.C3_3_value('');
                nts.uk.ui.errors.clearAll();
                $('#C3_2').focus();
                self.getOutputItemMonthlyWorkSchedule([]);
                self.enableBtnDel(false);
            }

            /*
                Save data
            */
            private saveData(): JQueryPromise<any> {
                let self = this;

                $('.save-error').ntsError('check');
                if (nts.uk.ui.errors.hasError()) {
                    return;
                }

                let dfd = $.Deferred();
                let command: any = {};
                command.itemCode = self.C3_2_value();
                command.itemName = self.C3_3_value();
                command.lstDisplayedAttendance = [];
                _.forEach(self.currentCodeListSwap(), function(value, index) {
                    command.lstDisplayedAttendance.push({ sortBy: index, itemToDisplay: value.code });
                });

                command.newMode = (_.isUndefined(self.currentCodeList()) || _.isNull(self.currentCodeList()) || _.isEmpty(self.currentCodeList())) ? true : false;
                service.save(command).done(function() {
                    self.getDataService().done(function() {
                        self.currentCodeList(self.C3_2_value());
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                            $('#C3_3').focus();
                        });
                        dfd.resolve();
                    })

                }).fail(function(err) {
                    nts.uk.ui.dialog.alertError(err);
                    dfd.reject();
                })

                return dfd.promise();
            }

            /*
                Remove data
            */
            private removeData(): void {
                let self = this;
                nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                    service.remove(self.currentCodeList()).done(function() {
                        let indexCurrentCode = _.findIndex(self.outputItemList(), function(value, index) {
                            return self.currentCodeList() == value.code;
                        })

                        // self.currentCodeList only have 1 element in list
                        if (self.outputItemList().length == 1) {
                            self.currentCodeList(null);
                        }
                        // when current code was selected is last element in list self.currentCodeList
                        else if (indexCurrentCode == (self.outputItemList().length - 1)) {
                            self.currentCodeList(self.outputItemList()[indexCurrentCode - 1].code);
                        }
                        // when current code was selected in place middle in list self.currentCodeList
                        else {
                            self.currentCodeList(self.outputItemList()[indexCurrentCode + 1].code);
                        }
                        self.getDataService().done(function() {
                            nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(function() {
                                if (_.isEmpty(self.currentCodeList())) {
                                    $('#C3_2').focus();
                                } else {
                                    $('#C3_3').focus();
                                }
                            });
                        })
                    })
                })

            }

            /*
                Close C screen
            */
            public closeScreenC(): void {
                nts.uk.ui.windows.close();
            }

            public startPage(): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                let self = this;

                $.when(self.getDataService(), self.getEnumSettingPrint()).done(function(data1: any) {
                    if (_.isUndefined(nts.uk.ui.windows.getShared('KWR001_C'))) {
                        self.currentCodeList(null);
                    } else {
                        self.currentCodeList(nts.uk.ui.windows.getShared('KWR001_C'));
                    }

                    dfd.resolve();
                })
                return dfd.promise();
            }

            /*
             *  get data from server
            */
            private getDataService(): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                var self = this;
                service.getDataStartPage().done(function(data: any) {
                    // variable global store data from service 
                    self.allMainDom(data.outputItemDailyWorkSchedule);

                    // variable temporary 
                    let temp: any[] = [];
                    _.forEach(data.dailyAttendanceItem, function(value) {
                        temp.push(value);
                    })
                    self.outputItemPossibleLst(temp);

                    let arrCodeName: ItemModel[] = [];
                    _.forEach(data.outputItemDailyWorkSchedule, function(value, index) {
                        arrCodeName.push({ code: value.itemCode + "", name: value.itemName });
                    });
                    self.outputItemList(arrCodeName);
                    self.items(data.dailyAttendanceItem);
                    dfd.resolve();
                })

                return dfd.promise();
            }

            /*
                get Enum Setting Print
            */
            private getEnumSettingPrint(): JQueryPromise<void> {
                let dfd = $.Deferred<void>();
                let self = this;
                service.getEnumSettingPrint().done(function(data: any) {
                    console.log(data);
                    dfd.resolve();
                })
                
                return dfd.promise();
            }
        }

        class ItemModel {
            code: string;
            name: string;
            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }

        class BoxModel {
            code: string;
            name: string;
            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }

    }
}