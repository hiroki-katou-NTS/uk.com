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

    export class ScreenModel {

        aRES: KnockoutObservableArray<IARES>;
        currentARES: KnockoutObservable<AttendanceRecordExportSetting>;
        currentARESCode: KnockoutObservable<string>;
        currentARESName: KnockoutObservable<string>;

        selectedARESCode: KnockoutObservable<string>;
        aRESCode: KnockoutObservable<string>;
        aRESName: KnockoutObservable<string>;

        //B3_2 B3_3
        inputARESCode: KnockoutObservable<string>;
        inputARESName: KnockoutObservable<string>;

        sealUseAtrSwitchs: KnockoutObservableArray<SealUseAtrSwitch>;
        updateMode: KnockoutObservable<boolean>;

        constructor() {
            let self = this;

            self.aRES = ko.observableArray([]);
            self.currentARES = ko.observable(null);
            self.currentARESCode = ko.observable('');
            self.currentARESName = ko.observable('');


            self.selectedARESCode = ko.observable('');
            self.aRESCode = ko.observable('');
            self.aRESName = ko.observable('');

            //test
            self.inputARESCode = ko.observable('');
            self.inputARESName = ko.observable('');

            self.sealUseAtrSwitchs = ko.observableArray([
                new SealUseAtrSwitch(1, getText("KWR002_45")),
                new SealUseAtrSwitch(2, getText("KWR002_46"))
            ]);

            self.updateMode = ko.observable(true);

            self.currentARESCode.subscribe((value) => {
                if (value) {
                    console.log(value);
                    let foundItem: IARES = _.find(self.aRES(), (item: IARES) => {
                        return item.code == value;
                    });

                    service.getARESByCode(foundItem.code).done((aRESData) => {
                        console.log(self.currentARES());
                        self.currentARES(new AttendanceRecordExportSetting(aRESData));
                        self.updateMode(true);
                        console.log(self.currentARES());
                    }).fail((error) => {
                        alert(error.message);
                    })

                }
            });
        }

        //Close Dialog
        onClose() {
            windows.close();
        }

        onDelete() {
            let self = this;
            errors.clearAll();
            confirm({messageId: 'Msg_18'}).ifYes(() => {
                confirm({messageId: 'Msg_35'}).ifYes(() => {
                    let currentData = self.currentARES();
                    let delARESCmd = {
                        code: Number(currentData.code()),
                        exportCode: Number(currentData.code()),
                        exportSettingCode: Number(currentData.code()),
                        name: currentData.name()
                    };

                    let cmd = {
                        delARESCmd: delARESCmd,
                        delARECmd: delARESCmd,
                        delARICmd: delARESCmd
                    };

                    service.delARES(cmd).done(() => {
                        let newVal = _.reject(self.aRES(), ['code', currentData.code()]);
                        // console.log(newVal);
                        self.aRES(newVal);
                        let firstData = _.first(self.aRES());
                        self.currentARESCode(firstData.code);
                    });
                })
            }).ifNo(() => {
                confirm({messageId: 'Msg_36'}).ifYes(() => {
                    return;
                })
            });

        }

        /** new mode */
        onNew() {
            let self = this;
            errors.clearAll();

            let params = {
                code: "",
                name: "",
                sealUseAtr: false,
                nameUseAtr: 1
            };
            self.currentARES(new AttendanceRecordExportSetting(params));
            self.currentARESCode("");

            $("#code").focus();
            self.updateMode(false);
        }

        onRegister() {
            block.invisible();
            let self = this;
            let currentData = self.currentARES();

            $("#code").trigger("validate");
            $("#name").trigger("validate");

            let rcdExport = {
                attendanceRecExpDaily: getShared('attendanceRecExpDaily'),//=9
                attendanceRecExpMonthly: getShared('attendanceRecExpMonthly'),//>=9
                attendanceRecItemList: getShared('attendanceRecItemList'),
                sealStamp: getShared('sealStamp'),
                useSeal: getShared('useSeal'),

                isInvalid: function () {
                    return ((!_.isArray(this.attendanceRecExpDaily) || !_.isArray(this.attendanceRecExpMonthly))
                        ||(this.attendanceRecExpDaily.length < 9 && this.attendanceRecExpMonthly.length < 9));

                }
            };

            console.log("is Invalid:" + rcdExport.isInvalid());

            if (self.updateMode()) { //in update mode
                if (rcdExport.isInvalid()) {
                    alertError({messageId: 'Msg_1130'});
                } else {
                    let data = self.createTransferData(currentData, rcdExport);
                    //update ARES
                    service.addARES(data).done((rs) => {
                        self.callGetAll();
                    });
                }
            } else { // in new mode
                service.getARESByCode(currentData.code()).done((rs) => {
                    if (!_.isNull(rs.code)) {
                        alertError({messageId: 'Msg_3'});
                    } else {
                        if (rcdExport.isInvalid()) {
                            alertError({messageId: 'Msg_1130'});
                        } else {
                            let data = self.createTransferData(currentData, rcdExport);
                            //add new ARES
                            service.addARES(data).done((rs) => {
                                self.callGetAll(self);
                            });
                        }
                    }
                })
            }

            nts.uk.ui.block.clear();
        };
        
        callGetAll(self) {
            service.getAllARES().done((data) => {
                if (data.length > 0) {
                    self.aRES(data);
                    let firstData = _.first(data);
                    self.currentARESCode(firstData.code);
                } else {
                    self.onNew();
                }
            }).always(() => {
                nts.uk.ui.block.clear();
            });
        };

        createTransferData(currentData, rcdExport) {
            let cmd = {
                code: Number(currentData.code()),
                name: currentData.name(),
                sealUseAtr: currentData.sealUseAtr(),
                sealStamp: rcdExport.sealStamp,
                nameUseAtr: currentData.nameUseAtr(),
            };

            let itemCmd = {
                singleList: [],
                calculateList: []
            };

            _.forEach(rcdExport.attendanceRecItemList, (o) => {
                let code = Number(currentData.code());
                let name = o.attendanceItemName;
                let timeItems = o.attendanceId;
                let columnIndex = Number(o.columnIndex);
                if (_.isArray(timeItems)) {
                    let item = new CalculateAttendanceRecordExportCommand(code, o.useAtr, o.exportAtr,
                        columnIndex, o.position, timeItems, o.attribute, name);
                    itemCmd.calculateList.push(item);
                } else {
                    let item = new SingleAttendanceRecordExportCommand(code, o.useAtr, o.exportAtr,
                        columnIndex, o.position, timeItems, o.attribute, name);
                    itemCmd.singleList.push(item)
                }
            });

            // let sARCommand = {
            //     exportSettingCode: currentData.code(),
            //     useAtr: false,
            //     exportAtr: rcdExport.attendanceRecItemList.exportAtr,
            //     columnIndex: rcdExport.attendanceRecItemList.columnIndex,
            //     position: rcdExport.attendanceRecItemList.position,
            //     timeItemId: rcdExport.attendanceRecItemList.attendanceId,
            //     attribute: rcdExport.attendanceRecItemList.attribute,
            //     name: rcdExport.attendanceRecItemList.layoutName,//not sure
            // };
            //
            // let cARCommand = {
            //     exportSettingCode: currentData.code(),
            //     useAtr: false,
            //     exportAtr: rcdExport.attendanceRecItemList.exportAtr,
            //     columnIndex: rcdExport.attendanceRecItemList.columnIndex,
            //     position: rcdExport.attendanceRecItemList.position,
            //     timeItems: rcdExport.attendanceRecItemList.attendanceId,
            //     attribute: rcdExport.attendanceRecItemList.attribute,
            //     name: rcdExport.attendanceRecItemList.layoutName,//not sure
            // };

            let data = {
                cmd: cmd,
                itemCmd: itemCmd,
                // cARCommand: cARCommand
            };

            return data;
        };

        start() {
            block.invisible();
            let self = this;
            let dfd = $.Deferred<any>();

            service.getAllARES().done((data) => {
                if (data.length > 0) {
                    self.aRES(data);
                    let firstData = _.first(data);
                    self.currentARESCode(firstData.code);
                } else {
                    self.onNew();
                }
                dfd.resolve();
            }).fail(function (error) {
                dfd.reject();
                alert(error.message);
            }).always(() => {
                nts.uk.ui.block.clear();
            });

            return dfd.promise();
        }

    }

    class SingleAttendanceRecordExportCommand {
        exportSettingCode: number;
        useAtr: false;
        exportAtr: number;
        columnIndex: number;
        position: number;
        timeItemId: number;
        attribute: number;
        name: string;


        constructor(exportSettingCode: number, useAtr: false, exportAtr: number, columnIndex: number, position: number, timeItemId: number, attribute: number, name: string) {
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
        useAtr: false;
        exportAtr: number;
        columnIndex: number;
        position: number;
        timeItems: Array<TimeItem>;
        attribute: number;
        name: string;

        constructor(exportSettingCode: number, useAtr: false, exportAtr: number, columnIndex: number, position: number, timeItems: Array<nts.uk.com.view.kwr002.b.TimeItem>, attribute: number, name: string) {
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
    }

    export class AttendanceRecordExportSetting {
        code: KnockoutObservable<string>;
        name: KnockoutObservable<string>;
        sealUseAtr: KnockoutObservable<boolean>;
        nameUseAtr: KnockoutObservable<number>;

        constructor(param: IARES) {
            let self = this;
            this.code = ko.observable(param.code);
            this.name = ko.observable(param.name);
            this.sealUseAtr = ko.observable(param.sealUseAtr);
            this.nameUseAtr = ko.observable(param.nameUseAtr);
        }

        public openScreenC() {
            let self = this;
            block.grayout();
            //            console.log(self.code());
            setShared('attendanceRecExpSetCode', self.code(), true);
            setShared('attendanceRecExpSetName', self.name(), true);
            setShared('useSeal', self.sealUseAtr(), true);
            

            if (_.isArray(getShared('attendanceRecItemList')) && _.first(getShared('attendanceRecItemList')).layoutCode == self.code) {
                setShared('attendanceRecExpDaily', getShared('attendanceRecExpDaily'), true);
                setShared('attendanceRecExpMonthly', getShared('attendanceRecExpMonthly'), true);
                setShared('attendanceRecItemList', getShared('attendanceRecItemList'), true);
                setShared('sealStamp', getShared('sealStamp'), true);
            }

            modal('../c/index.xhtml', {title: getText('KWR002�ｿｽ�ｽｼ�ｽｿ3'),}).onClosed(function (): any {

            })
        }
    }

    class SealUseAtrSwitch {
        code: number;
        name: string;

        constructor(code: number, name: string) {
            this.code = code
            this.name = name;
        }
    }


}
