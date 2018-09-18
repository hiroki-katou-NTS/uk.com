module nts.uk.com.view.kwr002.d {
    import blockUI = nts.uk.ui.block;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    const DAILY_ATRRIBUTE: number = 1;
    export module viewmodel {
        export class ScreenModel {

            allAttendanceDaily: KnockoutObservableArray<viewmodel.model.AttendanceRecordItem>;
            columns: KnockoutObservableArray<any>;
            currentCodeAttendance: KnockoutObservable<number>;
            dataSource: KnockoutObservableArray<viewmodel.model.AttendanceRecordItem>;
            typeAttendanceCodeSelected: KnockoutObservable<number>;
            listAttendanceType: KnockoutObservableArray<viewmodel.model.AttendaceType>;
            columnsSelected: KnockoutObservable<any>;
            dataSelected: KnockoutObservableArray<viewmodel.model.AttendanceRecordItem>;
            codeSingleAttendanceSelected: KnockoutObservable<number>;
            singleAttendanceRecord: KnockoutObservable<viewmodel.model.SingleAttendanceRecord>;
            attendanceRecordName: KnockoutObservable<string>;
            attendanceRecordExport: KnockoutObservable<viewmodel.model.AttendanceRecordExport>;
            layoutCode: KnockoutObservable<string>;
            layoutName: KnockoutObservable<string>;
            position: KnockoutObservable<string>;
            columnIndex: KnockoutObservable<number>;
            exportAtr: KnockoutObservable<number>;
            directText: KnockoutObservable<string>;
            codeChoosen: KnockoutObservable<number>;
            constructor() {
                var self = this;
                self.allAttendanceDaily = ko.observableArray([]);
                self.codeChoosen = ko.observable(0);
                self.listAttendanceType = ko.observableArray([
                    new viewmodel.model.AttendaceType(13, nts.uk.resource.getMessage("Msg_1206", [])),
                    new viewmodel.model.AttendaceType(14, nts.uk.resource.getMessage("Msg_1207", [])),
                    new viewmodel.model.AttendaceType(15, nts.uk.resource.getMessage("Msg_1208", [])),
                ]);
                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText('KWR002_146'), key: 'attendanceItemId', width: 80 },
                    { headerText: nts.uk.resource.getText('KWR002_147'), key: 'attendanceItemName', formatter: _.escape, width: 150 }
                ]);
                self.currentCodeAttendance = ko.observable(0);
                self.attendanceRecordExport = ko.observable(null);
                self.dataSource = ko.observableArray([]);
                self.typeAttendanceCodeSelected = ko.observable(0);
                self.attendanceRecordName = ko.observable('');
                self.layoutCode = ko.observable('');
                self.layoutName = ko.observable('');
                self.position = ko.observable('');
                self.columnIndex = ko.observable(0);
                self.exportAtr = ko.observable(0);
                self.columnsSelected = ko.observableArray([
                    { headerText: nts.uk.resource.getText('KWR002_149'), key: 'attendanceItemId', width: 80 },
                    { headerText: nts.uk.resource.getText('KWR002_150'), key: 'attendanceItemName', formatter: _.escape, width: 150 }
                ]);
                self.dataSelected = ko.observableArray([]);
                self.directText = ko.observable('');
                self.codeSingleAttendanceSelected = ko.observable(0);
                self.singleAttendanceRecord = ko.observable(null);

                //typeAttendanceCode subscribe
                self.typeAttendanceCodeSelected.subscribe(function(codeChanged) {
                    if (codeChanged == 0) {
                        return;
                    }
                    var exportAttribute = self.exportAtr() ? self.exportAtr() : DAILY_ATRRIBUTE;  //1 is type daily
                    var attendanceTypeKey: model.AttendanceTypeKey = new model.AttendanceTypeKey(codeChanged, exportAttribute);
                    service.getAllAttndByAtrAndType(attendanceTypeKey).done(function(listAttendanceRecordItem: Array<viewmodel.model.AttendanceRecordItem>) {
                        //check attendance selected existed 
                        if (self.codeSingleAttendanceSelected() != 0 && self.codeSingleAttendanceSelected() != undefined) {
                            let attendanceSelected: viewmodel.model.AttendanceRecordItem = listAttendanceRecordItem.filter(e => e.attendanceItemId == self.codeSingleAttendanceSelected())[0];
                            if (attendanceSelected != null && attendanceSelected != undefined) {
                                //remove attendanceSelected in dataSource
                                let indexAttendance = listAttendanceRecordItem.indexOf(attendanceSelected);
                                listAttendanceRecordItem.splice(indexAttendance, 1);
                            }
                        }
                        self.dataSource(listAttendanceRecordItem);
                    });
                });
            }
            public startPage(): JQueryPromise<any> {
                blockUI.invisible();
                var self = this;
                var dfd = $.Deferred();
                let attendanceRecordExportShared: viewmodel.model.AttendanceRecordExport = getShared('attendanceItem');
                if (attendanceRecordExportShared != null && attendanceRecordExportShared != undefined) {
                    self.attendanceRecordExport(attendanceRecordExportShared);
                    self.setHeaderDisplay(attendanceRecordExportShared);

                }
                self.directText(nts.uk.resource.getText('KWR002_131') + self.columnIndex() + nts.uk.resource.getText('KWR002_132') +
                    self.position() + nts.uk.resource.getText('KWR002_133'));
                service.getAllAttendanceDaily().done(function(allAttendanceDaily: Array<model.AttendanceRecordItem>) {
                    self.allAttendanceDaily(allAttendanceDaily);
                });
                //set init attendanceTypeCode
                var attendanceTypeCode = self.listAttendanceType()[0].attendanceTypeCode;//init attendance type code
                self.typeAttendanceCodeSelected(attendanceTypeCode);
                var attendanceTypeKey: model.AttendanceTypeKey = new model.AttendanceTypeKey(attendanceTypeCode, DAILY_ATRRIBUTE);

                //get singleAttendanceRecord selected (by shared or in db)
                if (!nts.uk.util.isNullOrUndefined(self.attendanceRecordExport())) {
                    var attendanceRecord: viewmodel.model.AttendanceRecordExport = self.attendanceRecordExport();
                    var attendanceId = attendanceRecord.attendanceId;
                    //get singleAttendance by shared
                    if (attendanceId != undefined && attendanceId != 0) {
                        //set value display
                        let singleAttendanceSelected = new model.SingleAttendanceRecord(attendanceRecord.attendanceItemName, attendanceId, attendanceRecord.attribute);
                        attendanceTypeCode = singleAttendanceSelected.attribute;
                        attendanceTypeKey.screenUseAtr = attendanceTypeCode;
                        self.typeAttendanceCodeSelected(attendanceTypeCode);

                        service.getAllAttndByAtrAndType(attendanceTypeKey).done(function(listAttendanceRecordItem: Array<viewmodel.model.AttendanceRecordItem>) {
                            self.handleLoadListAttendanceSelect(singleAttendanceSelected, listAttendanceRecordItem);
                        });

                    } else {
                        //get SingleAttendance in DB
                        let attendanceRecordKey: viewmodel.model.AttendanceRecordKey = new viewmodel.model.AttendanceRecordKey(attendanceRecord.layoutCode,
                            attendanceRecord.columnIndex, attendanceRecord.position, attendanceRecord.exportAtr);
                        service.findSingleAttendanceRecord(attendanceRecordKey).done(function(singleAttendanceRecord: viewmodel.model.SingleAttendanceRecord) {
                            if (!nts.uk.text.isNullOrEmpty(singleAttendanceRecord) && singleAttendanceRecord.itemId != null && singleAttendanceRecord.itemId != 0) {
                                attendanceTypeCode = singleAttendanceRecord.attribute;
                                attendanceTypeKey.screenUseAtr = attendanceTypeCode;
                                self.typeAttendanceCodeSelected(attendanceTypeCode);
                                service.getAllAttndByAtrAndType(attendanceTypeKey).done(function(listAttendanceRecordItem: Array<viewmodel.model.AttendanceRecordItem>) {
                                    self.handleLoadListAttendanceSelect(singleAttendanceRecord, listAttendanceRecordItem);
                                });
                            }
                        });
                    }
                    blockUI.clear();
                } else {
                    service.getAllAttndByAtrAndType(attendanceTypeKey).done(function(listAttendanceRecordItem: Array<viewmodel.model.AttendanceRecordItem>) {
                        blockUI.clear();
                        self.dataSource(listAttendanceRecordItem);
                    });
                }
                dfd.resolve();
                return dfd.promise();
            }

            //click arrow-right-button
            public selectAttendanceRec() {
                var self = this;
                //not select yet left element
                var currentCodeAttendance = self.currentCodeAttendance();
                if (!currentCodeAttendance) {
                    nts.uk.ui.dialog.alertError({ messageId: 'Msg_1140' });
                    return;
                }
                //check dataSelected
                var singleAttendance = self.singleAttendanceRecord();
                if (singleAttendance != null && singleAttendance.itemId != null) {
                    return;
                }
                self.codeSingleAttendanceSelected(currentCodeAttendance);
                //add to right-list
                var listSource: Array<viewmodel.model.AttendanceRecordItem> = self.dataSource(),
                    attendanceSelected = listSource.filter(e => e.attendanceItemId == currentCodeAttendance)[0],
                    listSelected: Array<viewmodel.model.AttendanceRecordItem> = [attendanceSelected];
                self.dataSelected(listSelected);
                var singleAttendanceSelected: viewmodel.model.SingleAttendanceRecord = new viewmodel.model.SingleAttendanceRecord(attendanceSelected.attendanceItemName, self.codeSingleAttendanceSelected(), self.typeAttendanceCodeSelected());
                self.singleAttendanceRecord(singleAttendanceSelected);
                //remove left list
                var index = listSource.indexOf(attendanceSelected);
                listSource.splice(index, 1);
                self.dataSource(listSource);
                //reset data current code
                self.currentCodeAttendance(0);
            }
            //click arrow-left-button
            public removeAttendanceSelected() {
                var self = this;
                if (!self.codeSingleAttendanceSelected()) {
                    return;
                }
                //return left-list
                var typeAttendanceCodeCurrent = self.typeAttendanceCodeSelected();
                self.typeAttendanceCodeSelected(typeAttendanceCodeCurrent);
                var exportAttribute = self.exportAtr() ? self.exportAtr() : DAILY_ATRRIBUTE;  //1 is type daily
                var attendanceTypeKey: model.AttendanceTypeKey = new model.AttendanceTypeKey(typeAttendanceCodeCurrent, exportAttribute);
                service.getAllAttndByAtrAndType(attendanceTypeKey).done(function(listAttendanceRecordItem: Array<viewmodel.model.AttendanceRecordItem>) {
                    //remove right-list
                    self.clearSelected();
                    self.dataSource(listAttendanceRecordItem);
                });
            }

            private clearSelected() {
                var self = this;
                self.dataSelected([]);
                self.codeSingleAttendanceSelected(0);
                self.singleAttendanceRecord(null);
            }
            private findNameAttendanceById(attendanceId: number): string {
                var self = this;
                var allAttendanceDaily = self.allAttendanceDaily();
                if (allAttendanceDaily != undefined && allAttendanceDaily.length != 0) {
                    var result = allAttendanceDaily.filter(e => e.attendanceItemId == attendanceId).map(e => e.attendanceItemName)[0]
                    return result;
                }
                return null;
            }

            // function click btn-decide
            public decideButtonClick() {
                var self = this;
                $('.nts-input').trigger("validate");
                _.defer(() => {
                    if (!$('.nts-editor').ntsError("hasError") && !$('.ntsControl').ntsError("hasError")) {
                        if (nts.uk.text.isNullOrEmpty(self.singleAttendanceRecord()) || self.singleAttendanceRecord() == null) {
                            nts.uk.ui.dialog.alertError({ messageId: 'Msg_1141' });
                            return;
                        }
                        var attendanceRecord: model.AttendanceRecordExport = new model.AttendanceRecordExport(
                            self.attendanceRecordName(),
                            self.attendanceRecordExport().layoutCode,
                            self.layoutName(),
                            self.attendanceRecordExport().columnIndex,
                            self.attendanceRecordExport().position,
                            1,
                            self.singleAttendanceRecord().itemId,
                            self.singleAttendanceRecord().attribute);
                        setShared('attendanceRecordExport', attendanceRecord);
                        nts.uk.ui.windows.close();
                    }
                });

            }
            //function click btn-close
            public closeDialog() {
                nts.uk.ui.windows.close();
            }
            /**
             * load list Attendance selected
             */
            private handleLoadListAttendanceSelect(singleAttendanceSelected: model.SingleAttendanceRecord, listData: Array<model.AttendanceRecordItem>) {
                var self = this;
                self.singleAttendanceRecord(singleAttendanceSelected);
                self.codeSingleAttendanceSelected(singleAttendanceSelected.itemId);
                self.attendanceRecordName(singleAttendanceSelected.name);
                var attendanceName = self.findNameAttendanceById(self.codeSingleAttendanceSelected());
                let listDataSelected: Array<viewmodel.model.AttendanceRecordItem> = [new viewmodel.model.AttendanceRecordItem(singleAttendanceSelected.itemId, attendanceName, singleAttendanceSelected.attribute, 1)];//value 1 is exportAtr: Daily
                self.dataSelected(listDataSelected);
                //remove element selected if this element in default listAttendanceRecord init
                let attendanceSelected: viewmodel.model.AttendanceRecordItem = listData.filter(e => e.attendanceItemId == self.codeSingleAttendanceSelected())[0];
                if (attendanceSelected != null && attendanceSelected != undefined) {
                    //remove attendanceSelected in dataSource
                    let indexAttendance = listData.indexOf(attendanceSelected);
                    listData.splice(indexAttendance, 1);
                }
                self.dataSource(listData);
            }
            /**
             * set header display with attendanceRecord
             */
            private setHeaderDisplay(attendanceRecord: model.AttendanceRecordExport) {
                var self = this;
                self.attendanceRecordName(attendanceRecord.attendanceItemName);
                var layouCodeText = attendanceRecord.layoutCode < 10 ? "0" + attendanceRecord.layoutCode : "" + attendanceRecord.layoutCode;
                self.layoutCode(layouCodeText);
                self.layoutName(attendanceRecord.layoutName);
                var positionText = attendanceRecord.position == 1 ? "上" : "下";
                self.position(positionText);
                self.columnIndex(attendanceRecord.columnIndex);
                self.exportAtr(attendanceRecord.exportAtr);
            }
        }
        export module model {
            export class SingleAttendanceRecord {
                name: string;
                itemId: number;
                attribute: number;
                constructor(name: string, itemId: number, attribute: number) {
                    this.name = name;
                    this.itemId = itemId;
                    this.attribute = attribute;
                }
            }

            //demo class
            export class SingleAttendanceCommand {
                name: string;
                exportSettingCode: number;
                useAtr: boolean;
                exportAtr: number;
                columnIndex: number;
                position: number;
                timeItemId: number;
                attribute: number;
                constructor(name: string, exportSettingCode: number, useAtr: boolean, exportAtr: number, columnIndex: number, position: number, timeItemId: number, attribute: number) {
                    this.name = name;
                    this.exportSettingCode = exportSettingCode;
                    this.useAtr = useAtr;
                    this.exportAtr = exportAtr;
                    this.columnIndex = columnIndex;
                    this.position = position;
                    this.timeItemId = timeItemId;
                    this.attribute = attribute;
                }
            }
            export class TimeItemDto {
                formulaType: number;
                timeItemId: number;
                constructor(formulaType: number, timeItemId: number) {
                    this.formulaType = formulaType;
                    this.timeItemId = timeItemId;
                }

            }
            export class AttendanceRecordItem {
                attendanceItemId: number;
                attendanceItemName: string;
                screenUseItem: number;
                typeOfAttendanceItem: number;
                constructor(attendanceItemId: number, attendanceItemName: string, screenUseItem: number, typeOfAttendanceItem: number) {
                    this.attendanceItemId = attendanceItemId;
                    this.attendanceItemName = attendanceItemName;
                    this.screenUseItem = screenUseItem;
                    this.typeOfAttendanceItem = typeOfAttendanceItem;
                }
            }
            export class AttendanceRecordKey {
                code: number;
                columnIndex: number;
                position: number;
                exportAtr: number;
                constructor(code: number, columnIndex: number, position: number, exportAtr: number) {
                    this.code = code;
                    this.columnIndex = columnIndex;
                    this.position = position;
                    this.exportAtr = exportAtr;
                }
            }
            export class AttendanceRecordExport {
                attendanceItemName: string;
                layoutCode: number;
                layoutName: string;
                columnIndex: number;
                position: number;
                exportAtr: number;
                attendanceId: number;
                attribute: number;
                constructor(attendanceItemName: string, layoutCode: number, layoutName: string, indexColumn: number, position: number, exportAtr: number, attendanceId: number, attribute: number) {
                    this.attendanceItemName = attendanceItemName;
                    this.layoutCode = layoutCode;
                    this.layoutName = layoutName;
                    this.columnIndex = indexColumn;
                    this.position = position;
                    this.exportAtr = exportAtr;
                    this.attendanceId = attendanceId;
                    this.attribute = attribute;
                }
            }
            export class AttendaceType {
                attendanceTypeCode: number;
                attendanceTypeName: string;
                constructor(attendanceTypeCode: number, attendanceTypeName: string) {
                    this.attendanceTypeCode = attendanceTypeCode;
                    this.attendanceTypeName = attendanceTypeName;
                }
            }
            export class AttendanceTypeKey {
                screenUseAtr: number;
                attendanceType: number;
                constructor(screenUseAtr: number, attendanceType: number) {
                    this.screenUseAtr = screenUseAtr;
                    this.attendanceType = attendanceType;
                }
            }
        }
    }
}