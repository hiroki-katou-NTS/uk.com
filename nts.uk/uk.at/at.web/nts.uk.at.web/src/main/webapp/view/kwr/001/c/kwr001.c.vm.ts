module nts.uk.at.view.kwr001.c {

    import service = nts.uk.at.view.kwr001.c.service;
    import blockUI = nts.uk.ui.block;

    export module viewmodel {

        const DEFAULT_DATA_FIRST = 0;
        const LIMIT_BIG_SIZE = 48;
        const LIMIT_SMALL_SIZE = 60;

        export class ScreenModel {
            data: KnockoutObservable<number>;

            // list
            items: KnockoutObservableArray<ItemModel>;
            outputItemList: KnockoutObservableArray<ItemModel>;
            currentCodeList: KnockoutObservable<any>;
            columns: KnockoutObservableArray<any>;

            C3_2_value: KnockoutObservable<string>;
            C3_3_value: KnockoutObservable<string>;

            // switch button
            roundingRules: KnockoutObservableArray<any>;
            selectedRuleCode: any;

            currentCodeListSwap: KnockoutObservableArray<ItemModel>;

            checkedRemarksInput: KnockoutObservable<boolean>;
            checkedMasterUnregistered: KnockoutObservable<boolean>;
            checkedEngraving: KnockoutObservable<boolean>;
            checkedImprintingOrderNotCorrect: KnockoutObservable<boolean>;
            checkedLeavingEarly: KnockoutObservable<boolean>;
            checkedHolidayStamp: KnockoutObservable<boolean>;
            checkedDoubleEngraved: KnockoutObservable<boolean>;
            checkedAcknowledgment: KnockoutObservable<boolean>;
            checkedManualInput: KnockoutObservable<boolean>;
            checkedNotCalculated: KnockoutObservable<boolean>;
            checkedExceedByApplication: KnockoutObservable<boolean>;
            checkReasonForDivergence: KnockoutObservable<boolean>;
            checkDeviationError: KnockoutObservable<boolean>;
            checkDeviationAlarm: KnockoutObservable<boolean>;

            // start: variable global store data from service 
            allMainDom: KnockoutObservable<any>;
            outputItemPossibleLst: KnockoutObservableArray<any>;
            // end: variable global store data from service

            // store map to convert id and code attendance item
            mapIdCodeAtd: any;
            mapCodeIdAtd: any;

            enableBtnDel: KnockoutObservable<boolean>;
            enableCodeC3_2: KnockoutObservable<boolean>;
            storeCurrentCodeBeforeCopy: KnockoutObservable<string>;

            remarkInputContents: KnockoutObservableArray<ItemModel> = ko.observableArray([
                new ItemModel('1', nts.uk.resource.getText("KWR001_118"), '', 0),
                new ItemModel('2', nts.uk.resource.getText("KWR001_119"), '', 0),
                new ItemModel('3', nts.uk.resource.getText("KWR001_120"), '', 0),
                new ItemModel('4', nts.uk.resource.getText("KWR001_121"), '', 0),
                new ItemModel('5', nts.uk.resource.getText("KWR001_122"), '', 0)
            ]);
            currentRemarkInputContent: KnockoutObservable<string>;
            isEnableRemarkInputContents: KnockoutObservable<boolean>;

            // switch btn A9_2
            dataSizeClassificationType: KnockoutObservableArray<any> = ko.observableArray([
                { code: 1, name: nts.uk.resource.getText("KWR001_153") },
                { code: 0, name: nts.uk.resource.getText("KWR001_154") }
            ]);
            selectedSizeClassificationType: KnockoutObservable<number> = ko.observable(FontSizeEnum.BIG);

            // combobox C5_1
            itemProjectType: KnockoutObservableArray<any> = ko.observableArray([
                { code: -1, name: nts.uk.resource.getText("KWR001_156") },
                { code: 5, name: nts.uk.resource.getText("KWR001_157") },
                { code: 2, name: nts.uk.resource.getText("KWR001_158") },
                { code: 0, name: nts.uk.resource.getText("KWR001_159") },
                { code: -2, name: nts.uk.resource.getText("KWR001_160") },
            ]);
            selectedProjectType: KnockoutObservable<number>;

            // C7_13 label
            sizeClassificationLabel: KnockoutObservable<string> = ko.observable(nts.uk.resource.getText("KWR001_65"));
            limitAttendanceItem: any = {
                right: LIMIT_BIG_SIZE
            };
            loadSwapLst: KnockoutObservable<boolean> = ko.observable(true);

            layoutId: string;
            selectionType: number;
            employeeId?: string;

            constructor() {
                var self = this;
                self.allMainDom = ko.observable();
                self.outputItemPossibleLst = ko.observableArray([]);

                self.items = ko.observableArray([]);
                self.outputItemList = ko.observableArray([]);
                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText("KWR001_52"), prop: 'code', width: 70 },
                    { headerText: nts.uk.resource.getText("KWR001_53"), prop: 'name', width: 180, formatter: _.escape }
                ]);
                self.currentCodeList = ko.observable();
                self.C3_2_value = ko.observable("");
                self.C3_3_value = ko.observable("");

                self.roundingRules = ko.observableArray([]);
                self.selectedRuleCode = ko.observable(0);
                self.currentCodeListSwap = ko.observableArray([]);
                self.selectedProjectType = ko.observable(-1);

                self.currentCodeListSwap.subscribe(function(value) {
                    self.fillterByAttendanceType(self.selectedProjectType());
                })

                self.items.subscribe(function(value) {
                })

                self.enableBtnDel = ko.observable(false);
                self.enableCodeC3_2 = ko.observable(false);

                self.currentCodeList.subscribe(function(value) {
                    self.selectedProjectType(-1);
                    let codeChoose = _.find(self.allMainDom(), function(o: any) {
                        return value == o.code;
                    });
                    if (!_.isUndefined(codeChoose) && !_.isNull(codeChoose)) {
                        blockUI.grayout();
                        service.findByCode(self.currentCodeList(), self.selectionType).done((outputItemDailyWorkSchedule) => {
                            self.C3_2_value(outputItemDailyWorkSchedule.itemCode);
                            self.C3_3_value(outputItemDailyWorkSchedule.itemName);
                            self.getOutputItemDailyWorkSchedule(outputItemDailyWorkSchedule);
                            self.selectedRuleCode(outputItemDailyWorkSchedule.workTypeNameDisplay);
                            self.enableBtnDel(true);
                            self.enableCodeC3_2(false);
                            self.currentRemarkInputContent(self.convertDBRemarkInputToValue(outputItemDailyWorkSchedule.remarkInputNo));
                            blockUI.clear();
                        })
                    } else {
                        self.C3_3_value('');
                        self.C3_2_value('');
                        self.getOutputItemDailyWorkSchedule([]);
                        self.enableBtnDel(false);
                        self.enableCodeC3_2(true);
                    }
                    _.delay(() => {
                        nts.uk.ui.errors.clearAll();
                    }, 400);
                })

                self.checkedRemarksInput = ko.observable(false);
                self.checkedMasterUnregistered = ko.observable(false);
                self.checkedEngraving = ko.observable(false);
                self.checkedImprintingOrderNotCorrect = ko.observable(false);
                self.checkedLeavingEarly = ko.observable(false);
                self.checkedHolidayStamp = ko.observable(false);
                self.checkedDoubleEngraved = ko.observable(false);
                self.checkedAcknowledgment = ko.observable(false);
                self.checkedManualInput = ko.observable(false);
                self.checkedNotCalculated = ko.observable(false);
                self.checkedExceedByApplication = ko.observable(false);
                self.checkReasonForDivergence = ko.observable(false);
                self.checkDeviationError = ko.observable(false);
                self.checkDeviationAlarm = ko.observable(false);

                self.storeCurrentCodeBeforeCopy = ko.observable('');
                self.currentRemarkInputContent = ko.observable('1');

                self.currentRemarkInputContent.subscribe(function(value) {
                })

                self.checkedRemarksInput.subscribe(function(isChecked) {
                    if (isChecked) {
                        self.isEnableRemarkInputContents(true);
                    } else {
                        self.isEnableRemarkInputContents(false);
                    }
                })

                self.isEnableRemarkInputContents = ko.observable(false);
                self.mapIdCodeAtd = {};
                self.mapCodeIdAtd = {};

                self.selectedSizeClassificationType.subscribe(value => {
                    self.loadSwapLst(false);
                    if (value === FontSizeEnum.SMALL) {
                        self.limitAttendanceItem = {
                            right: LIMIT_SMALL_SIZE
                        };
                        self.sizeClassificationLabel(nts.uk.resource.getText("KWR001_142"));
                    } else {
                        self.limitAttendanceItem = {
                            right: LIMIT_BIG_SIZE
                        };
                        self.sizeClassificationLabel(nts.uk.resource.getText("KWR001_65"));
                    }
                    setTimeout(() => {
                        self.loadSwapLst(true);
                    }, 10);
                });

                self.selectedProjectType.subscribe((value) => {
                    self.fillterByAttendanceType(value);
                });
            }

            /*
             * set data to C7_2, C7_8 
            */
            private getOutputItemDailyWorkSchedule(data: any): void {
                let self = this;

                // variable temporary
                let temp2: ItemModel[] = [];
                let temp1: ItemModel[] = [];
                self.items.removeAll();
                self.currentCodeListSwap.removeAll();
                _.forEach(data.lstDisplayedAttendance, function(value, index) {
                    if (value.attendanceName) {
                        temp1.push({ code: self.mapIdCodeAtd[value.attendanceDisplay]
                            , name: value.attendanceName
                            , id: value.attendanceDisplay
                            , att: value.attendanceItemAtt });
                    }
                });
                _.forEach(self.outputItemPossibleLst(), function(value) {
                    temp2.push(value);
                });
                // refresh data for C7_2
                self.items(temp2);
                // refresh data for C7_8
                self.currentCodeListSwap(temp1);

                if (!_.isEmpty(data)) {
                    self.checkedRemarksInput(self.convertNumToBool(data.lstRemarkContent[0].usedClassification));
                    self.checkedMasterUnregistered(self.convertNumToBool(data.lstRemarkContent[1].usedClassification));
                    self.checkedEngraving(self.convertNumToBool(data.lstRemarkContent[2].usedClassification));
                    self.checkedImprintingOrderNotCorrect(self.convertNumToBool(data.lstRemarkContent[3].usedClassification));
                    self.checkedLeavingEarly(self.convertNumToBool(data.lstRemarkContent[4].usedClassification));
                    self.checkedHolidayStamp(self.convertNumToBool(data.lstRemarkContent[5].usedClassification));
                    self.checkedDoubleEngraved(self.convertNumToBool(data.lstRemarkContent[6].usedClassification));
                    self.checkedAcknowledgment(self.convertNumToBool(data.lstRemarkContent[7].usedClassification));
                    self.checkedManualInput(self.convertNumToBool(data.lstRemarkContent[8].usedClassification));
                    self.checkedNotCalculated(self.convertNumToBool(data.lstRemarkContent[9].usedClassification));
                    self.checkedExceedByApplication(self.convertNumToBool(data.lstRemarkContent[10].usedClassification));
                    self.checkReasonForDivergence(self.convertNumToBool(data.lstRemarkContent[11].usedClassification));
                    self.checkDeviationError(self.convertNumToBool(data.lstRemarkContent[12].usedClassification));
                    self.checkDeviationAlarm(self.convertNumToBool(data.lstRemarkContent[13].usedClassification));
                    self.selectedRuleCode(data.workTypeNameDisplay);
                    self.selectedSizeClassificationType(data.fontSize);
                    self.layoutId = data.layoutId;
                } else {
                    self.setRemarksContentDefault();
                }
                self.fillterByAttendanceType(self.selectedProjectType());
            }

            public startPage(): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                let self = this;

                let dataTransfer = nts.uk.ui.windows.getShared('KWR001_C');
                self.layoutId = dataTransfer.layoutId ? dataTransfer.layoutId : '';
                self.selectionType = dataTransfer.selection;

                $.when(self.getDataService(), self.getEnumName()).done(function() {
                    if (!dataTransfer.codeChoose) {
                        self.selectedSizeClassificationType(FontSizeEnum.BIG);
                        self.currentCodeList(null);
                    } else {
                        self.currentCodeList(dataTransfer.codeChoose);
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
                service.getDataStartPage(self.selectionType, self.C3_2_value()).then(function(data: any) {
                    // variable global store data from service 
                    self.allMainDom(data.outputItemDailyWorkSchedule);

                    // variable temporary 
                    let temp: any[] = [];
                    if (data.selectedItem) {
                        _.forEach(data.selectedItem.lstCanUsed, function(value) {
                            temp.push(value);
                        });
                    }

                    self.outputItemPossibleLst(temp);

                    let arrCodeName: ItemModel[] = [];
                    _.forEach(data.outputItemDailyWorkSchedule, function(value, index) {
                        arrCodeName.push({ code: value.code + "", name: value.name, id: "", att: value.attendanceItemAtt });
                    });
                    self.outputItemList(arrCodeName);

                    _.forEach(data.selectedItem.lstCanUsed, (value) => {
                        self.mapCodeIdAtd[value.code] = value.id;
                        self.mapIdCodeAtd[value.id] = value.code;
                    })

                    self.items(data.selectedItem.lstCanUsed);
                    self.employeeId = data.employeeId ? data.employeeId : undefined;

                    dfd.resolve();
                })

                return dfd.promise();
            }

            /*
             * get enum name
            */
            private getEnumName(): JQueryPromise<void> {
                let dfd = $.Deferred<void>();
                let self = this;
                service.getEnumName().done(function(data: any) {
                    let arr: any[] = [];
                    arr.push({ code: '0', name: data[0].localizedName });
                    arr.push({ code: '1', name: data[1].localizedName });
                    self.roundingRules(arr);
                    dfd.resolve();
                })
                return dfd.promise();
            }

            /*
             *  open screen D
            */
            openScreenD() {
                var self = this;
                let dataScrD: any;
                // update ver34 set shared data
                nts.uk.ui.windows.setShared('KWR001_D', {
                    fontSize: self.selectedSizeClassificationType(),
                    selecttionType: self.selectionType,
                }, true);

                if (!_.isEmpty(self.currentCodeList())) {
                    self.storeCurrentCodeBeforeCopy(self.currentCodeList());
                }
                nts.uk.ui.windows.sub.modal('/view/kwr/001/d/index.xhtml').onClosed(function(): any {
                    nts.uk.ui.errors.clearAll();
                    self.layoutId = '';
                    dataScrD = nts.uk.ui.windows.getShared('KWR001_D');
                    if (!_.isEmpty(dataScrD)) {
                        if (!_.isEmpty(dataScrD.error)) {
                            nts.uk.ui.dialog.alertError(dataScrD.error);
                        } else {
                            self.currentCodeList('');
                            if (!_.isUndefined(dataScrD.lstAtdChoose) && !_.isEmpty(dataScrD.lstAtdChoose.dataInforReturnDtos)) {
                                $('#C3_3').focus();
                            } else {
                                $('#C3_2').focus();
                            }
                            let arrTemp: any[] = [];
                            _.forEach(self.outputItemPossibleLst(), function(value) {
                                arrTemp.push(value);
                            });
                            self.currentCodeListSwap(dataScrD.lstAtdChoose.dataInforReturnDtos);
                            self.items(arrTemp);
                            self.C3_2_value(dataScrD.codeCopy);
                            self.C3_3_value(dataScrD.nameCopy);
                            self.layoutId = null;
                            if (_.size(dataScrD.lstAtdChoose.msgErr)) {
                                nts.uk.ui.dialog.error({ messageId: "Msg_1476" }).then(function() {
                                    self.saveData(dataScrD);
                                });
                            } else {
                                self.saveData(dataScrD);
                            }

                        }
                    } else {
                        self.currentCodeList(self.storeCurrentCodeBeforeCopy());
                    }
                });
            }

            /*
             *  save data to server
            */
            private saveData(dataScrD: any): JQueryPromise<any> {
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
                _.forEach(self.currentCodeListSwap(), function(value, index) {
                    command.lstDisplayedAttendance.push({ sortBy: index, itemToDisplay: self.mapCodeIdAtd[value.code] });
                });
                command.lstRemarkContent = [];
                command.lstRemarkContent.push({ usedClassification: self.convertBoolToNum(self.checkedRemarksInput()), printItem: 0 });
                command.lstRemarkContent.push({ usedClassification: self.convertBoolToNum(self.checkedMasterUnregistered()), printItem: 1 });
                command.lstRemarkContent.push({ usedClassification: self.convertBoolToNum(self.checkedEngraving()), printItem: 2 });
                command.lstRemarkContent.push({ usedClassification: self.convertBoolToNum(self.checkedImprintingOrderNotCorrect()), printItem: 3 });
                command.lstRemarkContent.push({ usedClassification: self.convertBoolToNum(self.checkedLeavingEarly()), printItem: 4 });
                command.lstRemarkContent.push({ usedClassification: self.convertBoolToNum(self.checkedHolidayStamp()), printItem: 5 });
                command.lstRemarkContent.push({ usedClassification: self.convertBoolToNum(self.checkedDoubleEngraved()), printItem: 6 });
                command.lstRemarkContent.push({ usedClassification: self.convertBoolToNum(self.checkedAcknowledgment()), printItem: 7 });
                command.lstRemarkContent.push({ usedClassification: self.convertBoolToNum(self.checkedManualInput()), printItem: 8 });
                command.lstRemarkContent.push({ usedClassification: self.convertBoolToNum(self.checkedNotCalculated()), printItem: 9 });
                command.lstRemarkContent.push({ usedClassification: self.convertBoolToNum(self.checkedExceedByApplication()), printItem: 10 });
                command.lstRemarkContent.push({ usedClassification: self.convertBoolToNum(self.checkReasonForDivergence()), printItem: 11 });
                command.lstRemarkContent.push({ usedClassification: self.convertBoolToNum(self.checkDeviationError()), printItem: 12 });
                command.lstRemarkContent.push({ usedClassification: self.convertBoolToNum(self.checkDeviationAlarm()), printItem: 13 });
                command.workTypeNameDisplay = self.selectedRuleCode();
                command.newMode = (_.isUndefined(self.currentCodeList()) || _.isNull(self.currentCodeList()) || _.isEmpty(self.currentCodeList())) ? true : false;
                command.fontSize = self.selectedSizeClassificationType();
                command.selectionType = self.selectionType;
                command.layoutId = self.layoutId;
                command.employeeId = self.employeeId;

                // check to get data old from DB or current interface when it was disable
                if (self.checkedRemarksInput()) {
                    command.remarkInputNo = self.convertValueRemarkInputToDB(self.currentRemarkInputContent());
                } else {
                    let outputItemDailyWorkSchedule: any = _.find(self.allMainDom(), function(o: any) {
                        return self.currentCodeList() == o.itemCode;
                    });
                    command.remarkInputNo = _.isEmpty(outputItemDailyWorkSchedule) ? DEFAULT_DATA_FIRST : outputItemDailyWorkSchedule.remarkInputNo;
                    self.currentRemarkInputContent(self.convertDBRemarkInputToValue(command.remarkInputNo));
                }
                service.save(command).then(function() {
                    self.getDataService().then(function() {
                        self.currentCodeList(self.C3_2_value());
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function () {
                           $('#C3_3').focus(); 
                        });
                        blockUI.clear();
                        dfd.resolve();
                    })

                }).fail(function(err) {
                    blockUI.clear();
                    if (err.messageId == "Msg_3") {
                        $("#C3_2").ntsError('set', { messageId: "Msg_3" });
                    } else {
                        nts.uk.ui.dialog.alertError(err);
                    }
                    dfd.reject();
                })

                return dfd.promise();
            }

            private newMode(): void {
                let self = this;
                self.currentCodeList('');
                self.C3_2_value('');
                self.getDataService();
                self.C3_3_value('');
                $('#C3_2').focus();
                self.selectedSizeClassificationType(FontSizeEnum.BIG);
                self.getOutputItemDailyWorkSchedule([]);
                self.enableBtnDel(false);
                self.layoutId  = null;
                self.selectedRuleCode(0);
                self.selectedProjectType(-1);
                _.delay(() => {
                    nts.uk.ui.errors.clearAll();
                }, 400);
            }

            private convertBoolToNum(value: boolean): number {
                return value ? 1 : 0;
            }

            private convertNumToBool(value: number): boolean {
                return value == 1 ? true : false;
            }

            // return to screen A
            closeScreenC(): void {
                let self = this;
                nts.uk.ui.windows.setShared('KWR001_C', self.currentCodeList(), true);
                nts.uk.ui.windows.close();
            }

            /*
             *  set default data
            */
            private setRemarksContentDefault(): void {
                let self = this;
                self.checkedRemarksInput(false);
                self.checkedMasterUnregistered(false);
                self.checkedEngraving(false);
                self.checkedImprintingOrderNotCorrect(false);
                self.checkedLeavingEarly(false);
                self.checkedHolidayStamp(false);
                self.checkedDoubleEngraved(false);
                self.checkedAcknowledgment(false);
                self.checkedManualInput(false);
                self.checkedNotCalculated(false);
                self.checkedExceedByApplication(false);
                self.checkReasonForDivergence(false);
                self.checkDeviationError(false);
                self.checkDeviationAlarm(false);
            }

            /*
             *  remove data       
            */
            private removeData(): void {
                let self = this;
                nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                    blockUI.grayout();
                    service.remove(self.layoutId, self.selectionType.toString()).done(function() {
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
                            blockUI.clear();
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

            private fillterByAttendanceType(code: number) {
                const vm = this;
                const NOT_USE_ATR = 9;  // 日次の勤怠項目に関連するマスタの種類=9:するしない区分
                const CODE = 0;         // 日次勤怠項目の属性=0:コード
                const NUMBEROFTIME = 2; // 日次勤怠項目の属性=2:回数
                const TIME = 5;         //日次勤怠項目の属性=5:時間
                let lstResult: any[] = [];
                let lstTemp: any[] = [];
                _.forEach(vm.outputItemPossibleLst(), function(value) {
                    lstTemp.push(value);
                });
                switch (code) {
                    case -1:
                        // 「全件」⓪の場合は、絞り込み不要とする。
                       lstResult = lstTemp;
                        break;
                    case -2:
                        // 「その他」④の場合は、「全体」⓪から時間①、回数②、計算項目③を除いたものを表示する。
                        lstResult = _.filter(lstTemp, (item: any) => item.attendanceItemAtt !== NUMBEROFTIME
                                                                                  || item.attendanceItemAtt !== TIME 
                                                                                  || item.attendanceItemAtt !== CODE);
                        break;
                    case CODE:
                        //「計算項目」③の場合は、日次勤怠項目の属性=0:コード　かつ　日次の勤怠項目に関連するマスタの種類=9:するしない区分
                        lstResult = _.filter(lstTemp, (item: any) => item.attendanceItemAtt === CODE
                                                                                  && item.masterType
                                                                                  && item.masterType === NOT_USE_ATR);
                    default:
                        //「時間」①の場合は、日次勤怠項目の属性=5:時間
                        //「回数」②の場合は、日次勤怠項目の属性=2:回数
                        lstResult = _.filter(lstTemp, (item: any) => item.attendanceItemAtt === code);
                        break;
                }
                vm.items(lstResult);
            }
        }
        class ItemModel {
            code: string;
            name: string;
            id: string;
            att: number;
            constructor(code: string, name: string, id: string, att: number) {
                this.code = code;
                this.name = name;
                this.id = id;
                this.att = att;
            }
        }

        class FontSizeEnum {
            static BIG = 1;
            static SMALL = 0;
        }
    }
}