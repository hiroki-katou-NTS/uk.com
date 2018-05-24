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
                new SealUseAtrSwitch(0, getText("KWR002_46"))
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
            confirm({messageId: 'Msg_18'}).ifYes(()=>{
                confirm({messageId: 'Msg_35'}).ifYes(()=> {
                    let currentData = self.currentARES();
                    let aRESCommand = {
                        code: Number(currentData.code()),
                        name: currentData.name(),
                        // sealUseAtr: currentData.sealUseAtr(),
                        // sealStamp: rcdExport.sealStamp
                    };
                    service.delARES(aRESCommand).done(()=>{
                        console.log("ahihi");
                    });
                })
            }).ifNo(()=>{
                confirm({messageId: 'Msg_36'}).ifYes(()=> {
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
                sealUseAtr: false
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

            let rcdExport = {
                attendanceRecExpDaily: getShared('attendanceRecExpDaily'),//=9
                attendanceRecExpMonthly: getShared('attendanceRecExpMonthly'),//>=9
                attendanceRecItemList: getShared('attendanceRecItemList'),
                sealStamp: getShared('useSeal'),

                isInvalid: function () {
                    return this.attendanceRecExpDaily < 9 && this.attendanceRecExpMonthly < 9;
                }
            };

            let aRESCommand = {
                code: currentData.code(),
                name: currentData.name(),
                sealUseAtr: currentData.sealUseAtr(),
                sealStamp: rcdExport.sealStamp
            };

            let sARCommand = {
                exportSettingCode: currentData.code(),
                useAtr: false,
                exportAtr: rcdExport.attendanceRecItemList.exportAtr,
                columnIndex: rcdExport.attendanceRecItemList.columnIndex,
                position: rcdExport.attendanceRecItemList.position,
                timeItemId: rcdExport.attendanceRecItemList.attendanceId,
                attribute: rcdExport.attendanceRecItemList.attribute,
                name: rcdExport.attendanceRecItemList.layoutName,//not sure
            };

            let cARCommand = {
                exportSettingCode: currentData.code(),
                useAtr: false,
                exportAtr: rcdExport.attendanceRecItemList.exportAtr,
                columnIndex: rcdExport.attendanceRecItemList.columnIndex,
                position: rcdExport.attendanceRecItemList.position,
                timeItems: '',
                attribute: rcdExport.attendanceRecItemList.attribute,
                name: rcdExport.attendanceRecItemList.layoutName,//not sure
            };

            let data = {
                aRESCommand: aRESCommand,
                sARCommand: sARCommand,
                cARCommand: cARCommand
            };

            if (self.updateMode()) { //in update mode

            } else { // in new mode
                // insert a company
                service.getARESByCode(currentData.code()).done((rs) => {
                    if (!_.isNull(rs.code)) {
                        alertError({messageId: 'Msg_3'});
                    } else {
                        if (rcdExport.isInvalid) {
                            alertError({messageId: 'Msg_1130'});
                        }

                        //add new ARES
                        service.addARES(data).done((rs) => {
                        });
                    }
                }).fail(error => {

                }).always(() => {
                    nts.uk.ui.block.clear();
                });
            }
        }

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

    interface IARES {
        code: string;
        name: string;
        sealUseAtr: boolean;
    }


    export class AttendanceRecordExportSetting {
        code: KnockoutObservable<string>;
        name: KnockoutObservable<string>;
        sealUseAtr: KnockoutObservable<boolean>;

        constructor(param: IARES) {
            let self = this;
            this.code = ko.observable(param.code);
            this.name = ko.observable(param.name);
            this.sealUseAtr = ko.observable(param.sealUseAtr);
        }

        public openScreenC() {
            let self = this;
            block.grayout();
            //            console.log(self.code());
            setShared('attendanceRecExpSetCode', self.code(), true);
            setShared('attendanceRecExpSetName', self.name(), true);
            setShared('useSeal', self.sealUseAtr(), true);

            modal('../c/index.xhtml', {title: getText('KWR002＿3'),}).onClosed(function (): any {

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
