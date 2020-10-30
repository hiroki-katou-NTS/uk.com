
module nts.uk.com.view.kwr002.b {
    import block = nts.uk.ui.block;
    import errors = nts.uk.ui.errors;
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import modal = nts.uk.ui.windows.sub.modal;
    import windows = nts.uk.ui.windows;
    import alertError = nts.uk.ui.dialog.alertError;
    import confirm = nts.uk.ui.dialog.confirm;
    import dialogInfo = nts.uk.ui.dialog.info;

    let newModeFlag = false;

    export class ScreenModel {
        aRES: KnockoutObservableArray<AttendanceRecordExportSetting>;
        currentARES: KnockoutObservable<AttendanceRecordExportSetting>;
        currentARESCode: KnockoutObservable<string>;
        selectedARESCode: KnockoutObservable<string>;
        indexOfDelete: KnockoutObservable<any>;
        sealUseAtrSwitchs: KnockoutObservableArray<SealUseAtrSwitch>;
        newMode: KnockoutObservable<boolean>;
        fontSizeSwitch: KnockoutObservableArray<SealUseAtrSwitch>;
        inputKWR002B: KnockoutObservable<DataInputScreenB>;
        selectionType: number;
        layoutId: string;

        constructor() {
            let self = this;

            self.aRES = ko.observableArray([]);
            self.currentARES = ko.observable(null);
            self.currentARESCode = ko.observable('');
            self.indexOfDelete = ko.observable('');
            self.selectedARESCode = ko.observable('');

            self.sealUseAtrSwitchs = ko.observableArray([
                new SealUseAtrSwitch(1, getText("KWR002_45")),
                new SealUseAtrSwitch(2, getText("KWR002_46"))
            ]);

            self.fontSizeSwitch = ko.observableArray([
                new SealUseAtrSwitch(2, getText("KWR002_50")),
                new SealUseAtrSwitch(1, getText("KWR002_51")),
                new SealUseAtrSwitch(0, getText("KWR002_52")),
            ]);

            self.newMode = ko.observable(false);

            self.currentARESCode.subscribe((value) => {
                _.defer(() => {
                    errors.clearAll();
                });
                self.resetShare();
                if (value) {
                    service.getARESByCode(value, self.selectionType).done((aRESData: any) => {
                        self.currentARES(new AttendanceRecordExportSetting(aRESData));
                        // choose default font size large
                        if (_.isNil(self.currentARES().exportFontSize())) {
                            self.currentARES().exportFontSize(2);
                        }

                        self.layoutId = aRESData.layoutId;
                        self.newMode(false);
                        newModeFlag = false;
                    })
                } else {
                    this.onNew(true);
                }
            });
        };

        resetShare() {
            setShared('attendanceRecExpDaily', null, true);
            setShared('attendanceRecExpMonthly', null, true);
            setShared('attendanceRecItemList', null, true);
            setShared('sealStamp', null, true);
            setShared('useSeal', null, true);
        };

        onClose() {
            let self = this;
            setShared('currentARESCode', self.currentARESCode(), true);
            windows.close();
        };

        onDelete() {
            let self = this;
            errors.clearAll();

            confirm({ messageId: 'Msg_18' }).ifYes(() => {
                let currentData = self.currentARES();
                let delARESCmd = {
                    code: currentData.code(),
                    exportCode: currentData.code(),
                    exportSettingCode: currentData.code(),
                    name: currentData.name(),
                    layoutId: currentData.layoutId
                };

                let cmd = {
                    delARESCmd: delARESCmd,
                    delARECmd: delARESCmd,
                    delARICmd: delARESCmd
                };
                self.indexOfDelete(_.findIndex(self.aRES(), (e: any) => {
                    return e.code == self.currentARESCode()
                }));
                service.delARES(cmd).done(() => {
                    dialogInfo({ messageId: "Msg_16" });
                    let newVal = _.reject(self.aRES(), ['code', currentData.code()]);
                    self.aRES(newVal);

                    if (_.isEmpty(newVal)) {
                        this.onNew(true)
                    } else {
                        self.aRES(newVal);
                        self.setCurrentCodeAfterDelete();
                    }
                });
            });
        }

        /** set current code after delete */
        setCurrentCodeAfterDelete() {
            block.invisible();
            let self = this;
            let dfd = $.Deferred<any>();
            service.getAllARES().done((response: AttendanceRecordExportSettingWrapper) => {
                block.clear();
                let data: any;
                if (!!response && response.isFreeSetting) {
                    data = response.freeSettingLst;
                } else {
                    data = response.standardSettingLst;
                }

                if (data.length > 0) {
                    _.map(data, (item: any) => {
                        item.code = _.padStart(item.code, 2, '0');
                        // new AttendanceRecordExportSetting(item);
                    });
                    data = _.orderBy(data, [(item: any) => item.code], ['asc']);
                    self.aRES(data);
                    if (self.indexOfDelete() == self.aRES().length) {
                        self.currentARESCode(self.aRES()[self.indexOfDelete() - 1].code);
                    } else {
                        self.currentARESCode(self.aRES()[self.indexOfDelete()].code);
                    }

                } else {
                    self.onNew(true);
                }
                dfd.resolve();
            });
        }

        /** new mode */
        onNew(isFocus: boolean) {
            let self = this;
            errors.clearAll();
            let params = {
                code: "",
                name: "",
                sealUseAtr: false,
                nameUseAtr: 1,
                exportFontSize: 2,
                itemSelType: self.selectionType,
                layoutId: null,
                sealStamp: []
            };

            self.currentARES(new AttendanceRecordExportSetting(params));
            self.currentARESCode("");
            self.newMode(true);
            newModeFlag = true;
            if (isFocus) $("#code").focus();
        }

        onRegister() {
            if (nts.uk.ui.errors.hasError()) {
                return;
            }

            block.invisible();
            let self = this;
            let currentData = self.currentARES();

            $("#code").trigger("validate");
            $("#name").trigger("validate");

            let rcdExport = {
                attendanceRecExpDaily: getShared('attendanceRecExpDaily'),//=9
                attendanceRecExpMonthly: getShared('attendanceRecExpMonthly'),//>=9
                attendanceRecItemList: getShared('attendanceRecItemList'),
                sealStamp: _.isNil(getShared('sealStamp')) ? currentData.sealStamp : getShared('sealStamp'),
                useSeal: _.isNil(getShared('useSeal')) ? currentData.sealUseAtr() : getShared('useSeal'),
                monthlyDisplay: _.isNil(getShared('monthlyConfirmedDisplay')) ? currentData.monthlyDisplay() : getShared('monthlyConfirmedDisplay'),

                isInvalid: function() {
                    return ((!_.isArray(this.attendanceRecExpDaily) && !_.isArray(this.attendanceRecExpMonthly))
                        || (!this.isListValid(this.attendanceRecExpDaily) && !this.isListValid(this.attendanceRecExpMonthly)));
                },

                isListValid: function(list) {
                    return _.find(list, (item: any) => !(_.isEmpty(item.upperPosition) && _.isEmpty(item.lowwerPosition)));
                }
            };

            if (!self.newMode()) { //in update mode
                let data = self.createTransferData(currentData, rcdExport);
                //update ARES
                service.addARES(data).done(() => {
                    dialogInfo({ messageId: "Msg_15" });
                    self.callGetAll(self, currentData);
                });
            } else { // in new mode
                service.getARESByCode(currentData.code(), self.selectionType).done((rs) => {
                    if (!_.isNull(rs.code)) {
                        alertError({ messageId: 'Msg_3' });
                        block.clear();
                    } else {
                        if (rcdExport.isInvalid()) {
                            alertError({ messageId: 'Msg_1130' });
                            block.clear();
                        } else {
                            let data = self.createTransferData(currentData, rcdExport);
                            //add new ARES
                            service.addARES(data).done(() => {

                                dialogInfo({ messageId: "Msg_15" });
                                self.callGetAll(self, currentData);
                            });
                        }
                    }
                })
            }
        };

        callGetAll(self, currentData) {
            service.getAllARES().done((response: AttendanceRecordExportSettingWrapper) => {
                let data: any;
                if (!!response && self.selectionType === ItemSelectionType.FREE_SETTING) {
                    data = response.freeSettingLst;
                } else {
                    data = response.standardSettingLst;
                }

                if (data.length > 0) {
                    _.map(data, (item: any) => {
                        item.code = _.padStart(item.code, 2, '0');
                    });
                    data = _.orderBy(data, [(e: any) => e.code], ['asc']);
                    self.aRES(data);
                    if (currentData) {
                        self.currentARESCode(currentData.code());
                    } else {
                        let firstData: any = _.first(data);
                        self.currentARESCode(firstData.code);
                    }
                } else {
                    self.onNew(true);
                }
            }).always(() => {
                block.clear();
            });
        };

        createTransferData(currentData, rcdExport) {
            let self = this;
            let cmd = {
                code: currentData.code(),
                name: currentData.name(),
                sealUseAtr: rcdExport.useSeal,
                sealStamp: rcdExport.sealStamp,
                nameUseAtr: currentData.nameUseAtr(),
                exportFontSize: currentData.exportFontSize(),
                itemSelType : self.selectionType,
                layoutId: currentData.layoutId,
                monthlyDisplay: rcdExport.monthlyDisplay
            };

            let itemCmd = {
                singleList: [],
                calculateList: []
            };

            _.forEach(rcdExport.attendanceRecItemList, (o) => {
                let code = currentData.code();
                let name = o.attendanceItemName;
                let timeItemIds = o.attendanceId;
                let columnIndex = Number(o.columnIndex);
                if (_.isArray(timeItemIds)) {
                    let timeItems = _.map(timeItemIds, (item: any) => {
                        const action = _.isEqual(_.trim(item.action), getText('KDL048_8')) ? getText('KWR002_178') : getText('KWR002_179');
                        let type = _.isEqual(action, getText('KWR002_178')) ? 1 : 2; //KWR002_178
                        return new TimeItem(type, item.code);
                    });

                    let item = new CalculateAttendanceRecordExportCommand(code, o.useAtr, o.exportAtr,
                        columnIndex, o.position, timeItems, o.attribute, name);
                    itemCmd.calculateList.push(item);
                } else {
                    let item = new SingleAttendanceRecordExportCommand(code, o.useAtr, o.exportAtr,
                        columnIndex, o.position, timeItemIds, o.attribute, name);
                    itemCmd.singleList.push(item)
                }
            });

            let data = {
                cmd: cmd,
                itemCmd: itemCmd,
            };

            return data;
        };

        public openDialogF() {
            const vm = this;
            let currentData = vm.currentARES();
            const data = new OpenDialogFParam({
                itemSelectedType: vm.selectionType,
                code: vm.currentARES().code(),
                name: vm.currentARES().name(),
                layoutId: vm.layoutId
            });

            setShared("dataFromScreenB", data, true);
            modal("/view/kwr/002/f/index.xhtml").onClosed(() => {
                const duplicateItem = getShared('duplicateItem');
                if (duplicateItem) {
                    currentData.code(duplicateItem.code);
                    currentData.name(duplicateItem.name);
                    vm.layoutId = duplicateItem.layoutId;
                    vm.callGetAll(vm, currentData);
                }
            });
        }

        start() {
            block.invisible();
            let self = this;
            let dfd = $.Deferred<any>();
            let dataFromScreenA = getShared("dataTranferScreenB");
            self.selectionType = dataFromScreenA.selectionType;

            service.getAllARES().done((data: AttendanceRecordExportSettingWrapper) => {
                let lstItem = [];
                if (self.selectionType === ItemSelectionType.FREE_SETTING) {
                    lstItem = data.freeSettingLst !== null ? data.freeSettingLst : [];
                }

                if (self.selectionType === ItemSelectionType.STANDARD_SETTING) {
                    lstItem = data.standardSettingLst !== null ? data.standardSettingLst : [];
                }
                if (lstItem.length > 0) {
                    _.map(lstItem, (item:any) => {
                        item.code = _.padStart(item.code, 2, '0');
                    });
                    lstItem = _.orderBy(lstItem, [item => item.code], ['asc']);
                    self.aRES(lstItem);
                    self.currentARESCode(getShared("currentARESSelectCode"));
                } else {
                    self.onNew(true);
                }
                dfd.resolve();
            }).fail(function(error) {
                dfd.reject();
                alert(error.message);
            }).always(() => {
                block.clear();
            });

            return dfd.promise();
        }
    }

    class SingleAttendanceRecordExportCommand {
        exportSettingCode: number;
        useAtr: boolean;
        exportAtr: number;
        columnIndex: number;
        position: number;
        timeItemId: number;
        attribute: number;
        name: string;


        constructor(exportSettingCode: number, useAtr: boolean, exportAtr: number, columnIndex: number, position: number, timeItemId: number, attribute: number, name: string) {
            this.exportSettingCode = exportSettingCode;
            this.useAtr = useAtr;
            this.exportAtr = exportAtr;
            this.columnIndex = columnIndex;
            this.position = position;
            this.timeItemId = timeItemId;
            this.attribute = attribute;
            this.name = name;
        }
    }

    class CalculateAttendanceRecordExportCommand {
        exportSettingCode: number;
        useAtr: boolean;
        exportAtr: number;
        columnIndex: number;
        position: number;
        timeItems: Array<TimeItem>;
        attribute: number;
        name: string;

        constructor(exportSettingCode: number, useAtr: boolean, exportAtr: number, columnIndex: number, position: number, timeItems: Array<nts.uk.com.view.kwr002.b.TimeItem>, attribute: number, name: string) {
            this.exportSettingCode = exportSettingCode;
            this.useAtr = useAtr;
            this.exportAtr = exportAtr;
            this.columnIndex = columnIndex;
            this.position = position;
            this.timeItems = timeItems;
            this.attribute = attribute;
            this.name = name;
        }
    }

    export class TimeItem {
        formulaType: number;
        timeItemId: number;

        constructor(formulaType: number, timeItemId: number) {
            this.formulaType = formulaType;
            this.timeItemId = timeItemId;
        }
    }

    interface IARES {
        code: string;
        name: string;
        sealUseAtr: boolean;
        nameUseAtr: number;
        exportFontSize: number;
        layoutId: string;
        itemSelType: number;
        sealStamp: any;
        monthlyDisplay: any;
    }

    export class AttendanceRecordExportSettingWrapper {
        isFreeSetting: boolean;

        freeSettingLst: AttendanceRecordExportSetting[];

        standardSettingLst: AttendanceRecordExportSetting[];
    }

    export class AttendanceRecordExportSetting {
        code: KnockoutObservable<string>;
        name: KnockoutObservable<string>;
        sealUseAtr: KnockoutObservable<boolean>;
        nameUseAtr: KnockoutObservable<number>;
        exportFontSize: KnockoutObservable<number>;
        layoutId: string | null;
        itemSelType: number;
        sealStamp: any;
        monthlyDisplay: KnockoutObservable<number>;

        constructor(param: IARES) {
            let self = this;
            self.code = ko.observable(param.code);
            self.name = ko.observable(param.name);
            self.sealUseAtr = ko.observable(param.sealUseAtr);
            self.nameUseAtr = ko.observable(param.nameUseAtr);
            self.exportFontSize = ko.observable(param.exportFontSize);
            self.layoutId = param.layoutId;
            self.itemSelType = param.itemSelType;
            self.sealStamp = param.sealStamp;
            self.monthlyDisplay = ko.observable(param.monthlyDisplay);
        };


        public openDialogC() {
            let self = this;
            let settingCode = self.code();
            setShared('attendanceRecExpSetCode', settingCode, true);
            setShared('attendanceRecExpSetName', self.name(), true);
            setShared('useSeal', self.sealUseAtr(), true);
            setShared('exportFontSize', self.exportFontSize());
            setShared('selectionType', self.itemSelType);
            setShared('layoutId', self.layoutId);

            modal('../c/index.xhtml', {});
        }

        public openScreenC() {
            $("#code").trigger("validate");
            $("#name").trigger("validate");

            let self = this;

            if (nts.uk.ui.errors.hasError()) {
                return;
            }
            block.grayout();
            if (!newModeFlag) {
                this.openDialogC();
            } else {
                service.getARESByCode(this.code(), self.itemSelType).done((data) => {
                    if (_.isEmpty(data.code)) {
                        this.openDialogC()
                    } else {
                        alertError({ messageId: 'Msg_3' });
                        block.clear();
                    }
                });
            }
        }
    }

    class SealUseAtrSwitch {
        code: number;
        name: string;

        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    class DataInputScreenB {
        selectionType: number; //設定区分（自由設定）
        companyId: string; //会社ID
        employeeId: string; //社員ID
        selectedOutputLayoutId: string; //選択出力レイアウトID
        selectedCode: string;// 選択コード

        constructor(selectionType: number, companyId: string,employeeId: string,selectedOutputLayoutId: string,selectedCode: string) {
            this.selectionType = selectionType;
            this.companyId = companyId;
            this.employeeId = employeeId;
            this.selectedOutputLayoutId = selectedOutputLayoutId;
            this.selectedCode = selectedCode;
        }
    }

    class OpenDialogFParam {
        itemSelectedType: number;
        code: string;
        name: string;
        layoutId: string;
        employeeId?: string;

        constructor(init?: Partial<OpenDialogFParam>) {
            $.extend(this, init);
      }
    }

    class ItemSelectionType {
        static STANDARD_SETTING = 0;
        static FREE_SETTING = 1;
    }
}