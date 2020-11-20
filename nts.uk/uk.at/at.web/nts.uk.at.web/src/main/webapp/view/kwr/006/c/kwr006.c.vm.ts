module nts.uk.at.view.kwr006.c {

    import service = nts.uk.at.view.kwr006.c.service;
    import blockUI = nts.uk.ui.block;

    export module viewmodel {
        const DEFAULT_DATA_FIRST = 0;
        const LIMIT_BIG_SIZE = 48;
        const LIMIT_SMALL_SIZE = 60;
        export class ScreenModel {
            items: KnockoutObservableArray<ItemModel>;
            outputItemList: KnockoutObservableArray<ItemModel>;
            selectedCodeC2_3: KnockoutObservable<any>;
            columns: KnockoutObservableArray<any>;
            layoutId:  KnockoutObservable<string>;
            // switch button C9_2
            dataOutputType: KnockoutObservableArray<ItemModel>;
            C9_2_value: KnockoutObservable<number> = ko.observable(FontSizeEnum.BIG);

            // dropdown list C5_4
            itemListAttribute: KnockoutObservableArray<ItemModel>;
            C5_4_value: KnockoutObservable<number>;

            allMainDom: KnockoutObservable<any>;
            outputItemPossibleLst: KnockoutObservableArray<ItemModel>;

            currentCodeListSwap: KnockoutObservableArray<ItemModel>;
            C3_2_value: KnockoutObservable<string>;
            C3_3_value: KnockoutObservable<string>;
            enableBtnDel: KnockoutObservable<boolean>;
            enableCodeC3_2: KnockoutObservable<boolean>;

            storeCurrentCodeBeforeCopy: KnockoutObservable<string>;

            //c8-5
            remarkInputContents: KnockoutObservableArray<ItemModel>;
            currentRemarkInputContent: KnockoutObservable<number>;
            isEnableRemarkInputContents: KnockoutObservable<boolean>;

            // store map to convert id and code attendance item
            mapIdCodeAtd: any;
            mapCodeIdAtd: any;
            limitAttendanceItem: any = {
                right: LIMIT_BIG_SIZE
            };
            loadSwapLst: KnockoutObservable<boolean> = ko.observable(true);
            sizeClassificationLabel: KnockoutObservable<string> = ko.observable(nts.uk.resource.getText("KWR006_53"));

            constructor() {
                var self = this;
                self.C3_2_value = ko.observable("");
                self.C3_3_value = ko.observable("");
                self.layoutId = ko.observable("");
                self.allMainDom = ko.observable();
                self.outputItemPossibleLst = ko.observableArray([]);

                self.currentCodeListSwap = ko.observableArray([]);
                self.outputItemList = ko.observableArray([]);
                self.selectedCodeC2_3 = ko.observable();

                self.enableBtnDel = ko.observable(false);
                self.enableCodeC3_2 = ko.observable(false);
                self.selectedCodeC2_3.subscribe(function (value) {
                    let codeChoose = _.find(self.allMainDom(), function (o: any) {
                        return value == o.itemCode;
                    });
                    if (!_.isNil(codeChoose)) {
                        nts.uk.ui.errors.clearAll();
                        self.C3_2_value(codeChoose.itemCode);
                        self.C3_3_value(codeChoose.itemName);
                        let outputItemMonthlyWorkSchedule = _.find(self.allMainDom(), (o: any) => codeChoose.itemCode == o.itemCode);
                        self.getOutputItemMonthlyWorkSchedule(outputItemMonthlyWorkSchedule.lstDisplayedAttendance);
                        self.enableBtnDel(true);
                        self.enableCodeC3_2(false);
                        self.C5_4_value();
                        self.currentRemarkInputContent(codeChoose.remarkInputContent);
                        self.C9_2_value(codeChoose.textSize);
                        self.layoutId(codeChoose.layoutID);
                        self.isEnableRemarkInputContents(codeChoose.remarkPrinted);
                        $('#C3_3').focus();
                    } else {
                        self.newMode();
                    }
                });
                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText("KWR006_40"), prop: 'code', width: 70 },
                    { headerText: nts.uk.resource.getText("KWR006_41"), prop: 'name', width: 180, formatter: _.escape }
                ]);
                self.items = ko.observableArray([]);
                self.isEnableRemarkInputContents = ko.observable(false);
                self.remarkInputContents = ko.observableArray([]);
                self.storeCurrentCodeBeforeCopy = ko.observable('');
                self.currentRemarkInputContent = ko.observable(0);

                self.mapIdCodeAtd = {};
                self.mapCodeIdAtd = {};

                self.dataOutputType = ko.observableArray([
                    new ItemModel(3, nts.uk.resource.getText("KWR006_92"), null),
                    new ItemModel(1, nts.uk.resource.getText("KWR006_93"), null)
                ]);

                self.itemListAttribute = ko.observableArray([
                    new ItemModel(0, nts.uk.resource.getText("KWR006_105"), null),
                    new ItemModel(1, nts.uk.resource.getText("KWR006_106"), null),
                    new ItemModel(2, nts.uk.resource.getText("KWR006_107"), null),
                    new ItemModel(3, nts.uk.resource.getText("KWR006_110"), null),
                    new ItemModel(4, nts.uk.resource.getText("KWR006_111"), null),
                    new ItemModel(5, nts.uk.resource.getText("KWR006_108"), null)
                ]);

                self.C5_4_value = ko.observable(0);
                self.C5_4_value.subscribe((value) => {
                    self.fillterByAttendanceType(value);
                });

                self.C9_2_value.subscribe(value => {
                    self.loadSwapLst(false);
                    if (value === FontSizeEnum.SMALL) {
                        self.limitAttendanceItem = {
                            right: LIMIT_SMALL_SIZE
                        };
                        self.sizeClassificationLabel(nts.uk.resource.getText("KWR006_104"));
                    } else {
                        self.limitAttendanceItem = {
                            right: LIMIT_BIG_SIZE
                        };
                        self.sizeClassificationLabel(nts.uk.resource.getText("KWR006_53"));
                    }
                    setTimeout(() => {
                        self.loadSwapLst(true);
                    }, 10);
                });
            }

            /*
             * set data to C7_2, C7_8 
            */
            private getOutputItemMonthlyWorkSchedule(lstDisplayedAttendance?: any): void {
                let self = this;
                let lstSwapLeft =_.sortBy(self.outputItemPossibleLst(), i => i.code);
                let lstSwapRight = [];
                if (lstDisplayedAttendance) {
                    _.forEach(lstDisplayedAttendance, (item, index) => {
                        if (item.attendanceName) {
                            lstSwapRight.push({ code: self.mapIdCodeAtd[item.attendanceDisplay], name: item.attendanceName, id: item.attendanceDisplay });
                        }
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
                let itemType: number = nts.uk.ui.windows.getShared('itemSelection');
                nts.uk.ui.windows.setShared('itemSelectionCopy', itemType);
                if (!_.isEmpty(self.selectedCodeC2_3())) {
                    self.storeCurrentCodeBeforeCopy(self.selectedCodeC2_3());
                }
                nts.uk.ui.windows.sub.modal('/view/kwr/006/d/index.xhtml').onClosed(function (): any {
                    nts.uk.ui.errors.clearAll();
                    const KWR006DOutput = nts.uk.ui.windows.getShared('KWR006_D');
                    if (!_.isNil(KWR006DOutput)) {
                        self.selectedCodeC2_3('');
                        if (!_.isEmpty(KWR006DOutput.lstAtdChoose.lstDisplayTimeItem)) {
                            _.forEach(KWR006DOutput.lstAtdChoose.lstDisplayTimeItem, (value) => {
                                value.code = self.mapIdCodeAtd[value.id];
                            })
                            KWR006DOutput.lstAtdChoose.lstDisplayTimeItem = _.sortBy(KWR006DOutput.lstAtdChoose.lstDisplayTimeItem, o => o.displayOrder);
                            let chosen = _.filter(self.outputItemPossibleLst(), item => _.some(KWR006DOutput.lstAtdChoose.lstDisplayTimeItem, atd => atd.itemDaily == item.id));

                            var sortArr = _.map(KWR006DOutput.lstAtdChoose.lstDisplayTimeItem, 'itemDaily');
                            chosen = _.sortBy(chosen, function (item) {
                                return sortArr.indexOf(item.id)
                            });

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
                        if (_.size(KWR006DOutput.lstAtdChoose.errorList)) {
                            nts.uk.ui.dialog.alertError({ messageId: KWR006DOutput.lstAtdChoose.errorList }).then(() => {
                                self.saveData();
                            });
                        }
                        else {
                            self.saveData();
                        }
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
                // self.selectedCodeA8_2(0);
                self.currentRemarkInputContent(0);
                self.isEnableRemarkInputContents(false);
                self.C5_4_value(0);
                self.C9_2_value(FontSizeEnum.BIG);
                self.layoutId("");
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
                let itemType: number = nts.uk.ui.windows.getShared('itemSelection');
                command.itemCode = self.C3_2_value();
                command.itemName = self.C3_3_value();
                command.lstDisplayedAttendance = [];
                // command.printSettingRemarksColumn = self.C5_4_value();
                command.itemType = itemType;
                command.textSize = self.C9_2_value();
                command.layoutID = self.layoutId();
                command.isRemarkPrinted = self.isEnableRemarkInputContents();
                _.map(self.currentCodeListSwap(), function (value, index) {
                    command.lstDisplayedAttendance.push({ sortBy: index, itemToDisplay: self.mapCodeIdAtd[value.code] });
                });
                
                 if (self.isEnableRemarkInputContents() == true) {
                     command.remarkInputNo = self.currentRemarkInputContent();
                 } else {
                     let outputItemMonthlyWorkSchedule: any = _.find(self.allMainDom(), function (o: any) {
                         return self.selectedCodeC2_3() == o.itemCode;
                     });
                     command.remarkInputNo = _.isEmpty(outputItemMonthlyWorkSchedule) ? DEFAULT_DATA_FIRST : outputItemMonthlyWorkSchedule.remarkInputContent;
                     self.currentRemarkInputContent(command.remarkInputNo);
                 }
                command.newMode = (_.isUndefined(self.selectedCodeC2_3()) || _.isNull(self.selectedCodeC2_3()) || _.isEmpty(self.selectedCodeC2_3())) ? true : false;
                service.save(command).done(function () {
                    self.getDataService().done(function () {
                        self.selectedCodeC2_3(self.C3_2_value());
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function () {
                            $('#C3_3').focus();
                        });
                        blockUI.clear();
                        dfd.resolve();
                    })

                }).fail(function (err) {
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
                    let command: any = {};
                    command.itemCode = self.selectedCodeC2_3();
                    command.itemType = nts.uk.ui.windows.getShared('itemSelection');
                    service.remove(command).done(function () {
                        let indexCurrentCode = _.findIndex(self.outputItemList(), function (value, index) {
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
                        self.getDataService().done(function () {
                            blockUI.clear();
                            nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(function () {
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
                nts.uk.ui.windows.setShared('selectedCodeScreenC', self.selectedCodeC2_3(), true);
                nts.uk.ui.windows.close();
            }

            public startPage(): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                let self = this;

                $.when(self.getDataService(), self.getEnumRemarkInputContent()).done(function () {
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
                service.getDataStartPage(nts.uk.ui.windows.getShared('itemSelection')).done(function (data: any) {
                    // variable global store data from service 
                    self.allMainDom(data.outputItemMonthlyWorkSchedule);
                    // variable temporary 
                    self.outputItemPossibleLst(data.monthlyAttendanceItem);
                    let arrCodeName = _.map(data.outputItemMonthlyWorkSchedule, (value: any) => {
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
             * get enum EnumRemarkInputContent
            */
            private getEnumRemarkInputContent(): JQueryPromise<void> {
                let dfd = $.Deferred<void>();
                let self = this;
                service.getEnumRemarkInputContent().done(function (data: any) {
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

            private fillterByAttendanceType(code: number) {
                const vm = this;
                const NOT_USE_ATR = 9;  // 日次の勤怠項目に関連するマスタの種類=9:するしない区分
                const CODE = 0;         // 日次勤怠項目の属性=0:コード
                const NUMBEROFTIME = 2; // 日次勤怠項目の属性=2:回数
                const TIME = 5;         //日次勤怠項目の属性=5:時間
                let lstResult = vm.outputItemPossibleLst();
                switch (code) {
                    case -1:
                        // 「全件」⓪の場合は、絞り込み不要とする。
                        lstResult = vm.outputItemPossibleLst();
                        break;
                    case -2:
                        // 「その他」④の場合は、「全体」⓪から時間①、回数②、計算項目③を除いたものを表示する。
                        lstResult = vm.outputItemPossibleLst().filter((item: any) => item.attendanceItemAtt !== NUMBEROFTIME
                                                                                  || item.attendanceItemAtt !== TIME 
                                                                                  || item.attendanceItemAtt !== CODE);
                        break;
                    case CODE:
                        //「計算項目」③の場合は、日次勤怠項目の属性=0:コード　かつ　日次の勤怠項目に関連するマスタの種類=9:するしない区分
                        lstResult = vm.outputItemPossibleLst().filter((item: any) => item.attendanceItemAtt === CODE
                                                                                  && item.masterType
                                                                                  && item.masterType === NOT_USE_ATR);
                    default:
                        //「時間」①の場合は、日次勤怠項目の属性=5:時間
                        //「回数」②の場合は、日次勤怠項目の属性=2:回数
                        lstResult = vm.outputItemPossibleLst().filter((item: any) => item.attendanceItemAtt === code);
                        break;
                }
                vm.items(lstResult);
            }
        }
        }
        class ItemModel {
            code: number;
            name: string;
            id: number;
            constructor(code: number, name: string, id: number) {
                this.code = code;
                this.name = name;
                this.id = id;
            }
        }
        class FontSizeEnum {
            static SMALL = 1;
            static BIG = 3;
        }

    }