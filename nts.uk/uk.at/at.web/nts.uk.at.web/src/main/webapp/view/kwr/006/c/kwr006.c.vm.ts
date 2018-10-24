module nts.uk.at.view.kwr006.c {

    import service = nts.uk.at.view.kwr006.c.service;
    import blockUI = nts.uk.ui.block;

    export module viewmodel {
        const DEFAULT_DATA_FIRST = 0;
        export class ScreenModel {
            items: KnockoutObservableArray<ItemModel>;
            outputItemList: KnockoutObservableArray<ItemModel>;
            selectedCodeC2_3: KnockoutObservable<any>;
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

            storeCurrentCodeBeforeCopy: KnockoutObservable<string>;

            //c8-5
            remarkInputContents: KnockoutObservableArray<ItemModel>;
            currentRemarkInputContent: KnockoutObservable<number>;
            isEnableRemarkInputContents: KnockoutComputed<boolean>;

            // store map to convert id and code attendance item
            mapIdCodeAtd: any;
            mapCodeIdAtd: any;

            constructor() {
                var self = this;
                self.C3_2_value = ko.observable("");
                self.C3_3_value = ko.observable("");

                self.allMainDom = ko.observable();
                self.outputItemPossibleLst = ko.observableArray([]);

                self.currentCodeListSwap = ko.observableArray([]);
                self.outputItemList = ko.observableArray([]);
                self.selectedCodeC2_3 = ko.observable();

                self.enableBtnDel = ko.observable(false);
                self.enableCodeC3_2 = ko.observable(false);
                self.selectedCodeC2_3.subscribe(function(value) {
                    let codeChoose = _.find(self.allMainDom(), function(o: any) {
                        return value == o.itemCode;
                    });
                    if (!_.isNil(codeChoose)) {
                        nts.uk.ui.errors.clearAll();
                        self.C3_2_value(codeChoose.itemCode);
                        self.C3_3_value(codeChoose.itemName);
                        let outputItemMonthlyWorkSchedule = _.find(self.allMainDom(), o => codeChoose.itemCode == o.itemCode);
                        self.getOutputItemMonthlyWorkSchedule(outputItemMonthlyWorkSchedule.lstDisplayedAttendance);
                        self.enableBtnDel(true);
                        self.enableCodeC3_2(false);
                        self.selectedCodeA8_2(outputItemMonthlyWorkSchedule.printSettingRemarksColumn);
                        self.currentRemarkInputContent(outputItemMonthlyWorkSchedule.remarkInputContent);
                        $('#C3_3').focus();
                    } else {
                        self.newMode();
                    }
                });
                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText("KWR006_40"), prop: 'code', width: 70 },
                    { headerText: nts.uk.resource.getText("KWR006_41"), prop: 'name', width: 180, formatter: _.escape }
                ]);
                self.itemListConditionSet = ko.observableArray([
                    new BoxModel(0, nts.uk.resource.getText("KWR006_56")),
                    new BoxModel(1, nts.uk.resource.getText("KWR006_57"))
                ]);
                self.items = ko.observableArray([]);
                self.selectedCodeA8_2 = ko.observable(0);
                self.isEnableRemarkInputContents = ko.computed(function() {
                    return self.selectedCodeA8_2() == 1;
                });
                self.remarkInputContents = ko.observableArray([]);
                self.storeCurrentCodeBeforeCopy = ko.observable('');
                self.currentRemarkInputContent = ko.observable(0);

                self.mapIdCodeAtd = {};
                self.mapCodeIdAtd = {};
            }

            /*
             * set data to C7_2, C7_8 
            */
            private getOutputItemMonthlyWorkSchedule(lstDisplayedAttendance?: any): void {
                let self = this;

                const lstSwapLeft = _.sortBy(self.outputItemPossibleLst(), i => i.code);
                let lstSwapRight = [];
                if (lstDisplayedAttendance) {
                    lstSwapRight = lstDisplayedAttendance.map(item => {
                        return { code: self.mapIdCodeAtd[item.attendanceDisplay], name: item.attendanceName, id: item.attendanceDisplay };
                    });
                }

                // refresh data for C7_8
                self.currentCodeListSwap(lstSwapRight);
                // refresh data for C7_2
                self.items(lstSwapLeft);
            }

            public sortItems(): void {
                let self = nts.uk.ui._viewModel.content;
                self.items(_.sortBy(self.items(), item => item.code));
            }

            /*
            Open D screen
            */
            openScreenD() {
                var self = this;
                nts.uk.ui.windows.setShared('KWR006_D', self.outputItemPossibleLst(), true);
                if (!_.isEmpty(self.selectedCodeC2_3())) {
                    self.storeCurrentCodeBeforeCopy(self.selectedCodeC2_3());
                }
                nts.uk.ui.windows.sub.modal('/view/kwr/006/d/index.xhtml').onClosed(function(): any {
                    nts.uk.ui.errors.clearAll();
                    const KWR006DOutput = nts.uk.ui.windows.getShared('KWR006_D');
                    if (!_.isNil(KWR006DOutput)) {
                        self.selectedCodeC2_3('');
                        if (!_.isEmpty(KWR006DOutput.lstAtdChoose)) {
                            _.forEach(KWR006DOutput.lstAtdChoose, (value) => {
                                value.code = self.mapIdCodeAtd[value.id];
                            })
                            const chosen = _.filter(self.outputItemPossibleLst(), item => _.some(KWR006DOutput.lstAtdChoose, atd => atd.itemDaily == item.id));
                            if (!_.isEmpty(chosen)) {
                                self.items(self.outputItemPossibleLst());
                                self.currentCodeListSwap(chosen);
                                $('#C3_3').focus();
                            } else {
                                nts.uk.ui.dialog.alertError({ messageId: "Msg_1411" });
                                $('#C3_3').focus();
                            }
                        } else {
                            nts.uk.ui.dialog.alertError({ messageId: "Msg_1411" });
                            $('#C3_2').focus();
                        }

                        self.C3_2_value(nts.uk.ui.windows.getShared('KWR006_D').codeCopy);
                        self.C3_3_value(nts.uk.ui.windows.getShared('KWR006_D').nameCopy);
                        self.saveData();
                    } else {
                        self.selectedCodeC2_3(self.storeCurrentCodeBeforeCopy());
                    }
                });
            }

            /*
                New mode
            */
            private newMode(): void {
                let self = this;
                self.selectedCodeC2_3('');
                self.C3_2_value('');
                self.C3_3_value('');
                nts.uk.ui.errors.clearAll();
                $('#C3_2').focus();
                self.getOutputItemMonthlyWorkSchedule();
                self.enableBtnDel(false);
                self.enableCodeC3_2(true);
                self.selectedCodeA8_2(0);
                self.currentRemarkInputContent(0);
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

                blockUI.grayout();
                let dfd = $.Deferred();
                let command: any = {};
                command.itemCode = self.C3_2_value();
                command.itemName = self.C3_3_value();
                command.lstDisplayedAttendance = [];
                command.printSettingRemarksColumn = self.selectedCodeA8_2();

                _.map(self.currentCodeListSwap(), function(value, index) {
                    command.lstDisplayedAttendance.push({ sortBy: index, itemToDisplay: self.mapCodeIdAtd[value.code] });
                });

                if (self.selectedCodeA8_2() == 1) {
                    command.remarkInputNo = self.currentRemarkInputContent();
                } else {
                    let outputItemMonthlyWorkSchedule: any = _.find(self.allMainDom(), function(o: any) {
                        return self.selectedCodeC2_3() == o.itemCode;
                    });
                    command.remarkInputNo = _.isEmpty(outputItemMonthlyWorkSchedule) ? DEFAULT_DATA_FIRST : outputItemMonthlyWorkSchedule.remarkInputNo;
                    self.currentRemarkInputContent(command.remarkInputNo);
                }

                command.newMode = (_.isUndefined(self.selectedCodeC2_3()) || _.isNull(self.selectedCodeC2_3()) || _.isEmpty(self.selectedCodeC2_3())) ? true : false;
                service.save(command).done(function() {
                    self.getDataService().done(function() {
                        self.selectedCodeC2_3(self.C3_2_value());
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                            $('#C3_3').focus();
                        });
                        blockUI.clear();
                        dfd.resolve();
                    })

                }).fail(function(err) {
                    blockUI.clear();
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
                    blockUI.grayout();
                    service.remove(self.selectedCodeC2_3()).done(function() {
                        let indexCurrentCode = _.findIndex(self.outputItemList(), function(value, index) {
                            return self.selectedCodeC2_3() == value.code;
                        })

                        // self.currentCodeList only have 1 element in list
                        if (self.outputItemList().length == 1) {
                            self.selectedCodeC2_3(null);
                        }
                        // when current code was selected is last element in list self.currentCodeList
                        else if (indexCurrentCode == (self.outputItemList().length - 1)) {
                            self.selectedCodeC2_3(self.outputItemList()[indexCurrentCode - 1].code);
                        }
                        // when current code was selected in place middle in list self.currentCodeList
                        else {
                            self.selectedCodeC2_3(self.outputItemList()[indexCurrentCode + 1].code);
                        }
                        self.getDataService().done(function() {
                            blockUI.clear();
                            nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(function() {
                                if (_.isEmpty(self.selectedCodeC2_3())) {
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
                let self = this;
                nts.uk.ui.windows.setShared('selectedCodeScreenC', self.selectedCodeC2_3(), true)
                nts.uk.ui.windows.close();
            }

            public startPage(): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                let self = this;

                $.when(self.getDataService(), self.getEnumSettingPrint(), self.getEnumRemarkInputContent()).done(function() {
                    self.selectedCodeC2_3(nts.uk.ui.windows.getShared('selectedCode'));
                    if (_.isNil(self.selectedCodeC2_3()))
                        self.newMode();
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
                    self.allMainDom(data.outputItemMonthlyWorkSchedule);

                    // variable temporary 
                    self.outputItemPossibleLst(data.monthlyAttendanceItem);

                    let arrCodeName = _.map(data.outputItemMonthlyWorkSchedule, value => {
                        return { code: value.itemCode, name: value.itemName };
                    });
                    self.outputItemList(arrCodeName);

                    _.forEach(data.monthlyAttendanceItem, (value) => {
                        self.mapCodeIdAtd[value.code] = value.id;
                        self.mapIdCodeAtd[value.id] = value.code;
                    })

                    self.items(_.isEmpty(data.monthlyAttendanceItem) ? [] : _.sortBy(data.monthlyAttendanceItem, o => o.code));
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

            /*
             * get enum EnumRemarkInputContent
            */
            private getEnumRemarkInputContent(): JQueryPromise<void> {
                let dfd = $.Deferred<void>();
                let self = this;
                service.getEnumRemarkInputContent().done(function(data: any) {
                    self.remarkInputContents(data);
                    dfd.resolve();
                })
                return dfd.promise();
            }

            /*
              *  convert value remark input to DB       
              */
            private convertValueRemarkInputToDB(args: string): number {
                return _.parseInt(args) - 1;
            }

            /*
              *  convert from DB remark input to value client       
              */
            private convertDBRemarkInputToValue(args: number): string {
                return _.toString(args + 1);
            }

            private convertBoolToNum(value: boolean): number {
                return value ? 1 : 0;
            }

            private convertNumToBool(value: number): boolean {
                return value == 1 ? true : false;
            }
        }

        class ItemModel {
            code: string;
            name: string;
            id: string;
            constructor(code: string, name: string, id: string) {
                this.code = code;
                this.name = name;
                this.id = id;
            }
        }

        class BoxModel {
            code: number;
            name: string;
            constructor(code: number, name: string) {
                this.code = code;
                this.name = name;
            }
        }

    }
}