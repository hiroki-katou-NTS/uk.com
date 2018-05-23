module nts.uk.com.view.kwr002.d {
    import blockUI = nts.uk.ui.block;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    export module viewmodel {
        export class ScreenModel {
            allAttendanceDaily: KnockoutObservableArray<viewmodel.model.AttendanceRecordItem>;
            columns: KnockoutObservableArray<any>;
            currentCodeAttendace: KnockoutObservable<number>;
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
                self.currentCodeAttendace = ko.observable(0);
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
                    service.getAttendanceRecordItemsByScreenUseAtr(codeChanged).done(function(listAttendanceRecordItem: Array<viewmodel.model.AttendanceRecordItem>) {
                        //filter for exportAttr = 1 (DAILY) - condition in workFlow
                        let listAttendanceRecordItemFillExportAtr: Array<viewmodel.model.AttendanceRecordItem> = listAttendanceRecordItem.filter(e => e.typeOfAttendanceItem == 1);
                        //check attendance selected existed 
                        if (self.codeSingleAttendanceSelected() != 0 && self.codeSingleAttendanceSelected() != undefined) {
                            let attendanceSelected: viewmodel.model.AttendanceRecordItem = listAttendanceRecordItemFillExportAtr.filter(e => e.attendanceItemId == self.codeSingleAttendanceSelected())[0];
                            if (attendanceSelected != null && attendanceSelected != undefined) {
                                //remove attendanceSelected in dataSource
                                let indexAttendance = listAttendanceRecordItemFillExportAtr.indexOf(attendanceSelected);
                                listAttendanceRecordItemFillExportAtr.splice(indexAttendance, 1);
                            }
                        }
                        self.dataSource(listAttendanceRecordItemFillExportAtr);
                    });
                });
            }
            public startPage(): JQueryPromise<any> {
                blockUI.invisible();
                var self = this;
                var dfd = $.Deferred();
                let attendanceRecordExportShared: viewmodel.model.AttendanceRecordExport = getShared('attendanceItem');

                //                let attendanceRecordExportSharedDemo: viewmodel.model.AttendanceRecordExport = new viewmodel.model.AttendanceRecordExport('Item Top1', 1, 'LayoutNameDemo', 1, 1, 1);

                if (attendanceRecordExportShared != null && attendanceRecordExportShared != undefined) {
                    self.attendanceRecordExport(attendanceRecordExportShared);
                    self.attendanceRecordName(self.attendanceRecordExport().attendanceItemName);
                    var layouCodeText = self.attendanceRecordExport().layoutCode < 10 ? "0" + self.attendanceRecordExport().layoutCode : "" + self.attendanceRecordExport().layoutCode;
                    self.layoutCode(layouCodeText);
                    self.layoutName(self.attendanceRecordExport().layoutName);
                    var positionText = self.attendanceRecordExport().position == 1 ? "上" : "下";
                    self.position(positionText);
                    self.columnIndex(self.attendanceRecordExport().columnIndex);
                    self.exportAtr(self.attendanceRecordExport().exportAtr);
                }

                self.directText(nts.uk.resource.getText('KWR002_131') + ">" + self.columnIndex() + nts.uk.resource.getText('KWR002_132') + ">" +
                    self.position() + nts.uk.resource.getText('KWR002_133'));
                service.getAllAttendanceDaily().done(function(allAttendanceDaily: Array<model.AttendanceRecordItem>) {
                    self.allAttendanceDaily(allAttendanceDaily);
                });

                var attendanceTypeCodeInit = self.listAttendanceType()[0].attendanceTypeCode;//init attendance type code
                self.typeAttendanceCodeSelected(attendanceTypeCodeInit);
                //get list AttendanceItem
                service.getAttendanceRecordItemsByScreenUseAtr(attendanceTypeCodeInit).done(function(listAttendanceRecordItem: Array<viewmodel.model.AttendanceRecordItem>) {
                    blockUI.clear();
                    if (listAttendanceRecordItem == undefined || listAttendanceRecordItem.length == 0) {
                        self.dataSource();
                    } else {
                        //filter for exportAttr = 1 (DAILY) - condition in workFlow
                        let listAttendanceRecordItemFillExportAtr: Array<viewmodel.model.AttendanceRecordItem> = listAttendanceRecordItem.filter(e => e.typeOfAttendanceItem == 1);

                        //get singleAttendanceRecord selected
                        if (self.attendanceRecordExport() != null && self.attendanceRecordExport() != undefined) {
                            let attendanceRecordKey: viewmodel.model.AttendanceRecordKey = new viewmodel.model.AttendanceRecordKey(self.attendanceRecordExport().layoutCode,
                                self.attendanceRecordExport().columnIndex, self.attendanceRecordExport().position, self.attendanceRecordExport().exportAtr);
                            //get singleAttendaceItem form shared
                            var attendanceId = self.attendanceRecordExport().attendanceId;
                            if (attendanceId != undefined && attendanceId != 0) {
                                //set value display
                                var singleAttendanceSelected = new model.SingleAttendanceRecord(self.attendanceRecordExport().attendanceItemName, attendanceId, self.attendanceRecordExport().attribute);
                                self.handleLoadListAttendanceSelect(singleAttendanceSelected, listAttendanceRecordItemFillExportAtr);
                            } else {
                                self.dataSource(listAttendanceRecordItemFillExportAtr);
                                //find SingleAttendanceRecord Selected
                                service.findSingleAttendanceRecord(attendanceRecordKey).done(function(singleAttendanceRecord: viewmodel.model.SingleAttendanceRecord) {
                                    //check value existed 
                                    if (singleAttendanceRecord != null && singleAttendanceRecord.itemId != 0) {
                                        self.handleLoadListAttendanceSelect(singleAttendanceRecord, listAttendanceRecordItemFillExportAtr);
                                    }
                                });
                            }
                        }

                    }
                    
                    dfd.resolve();
                });
                
                return dfd.promise();
            }

            //click arrow-right-button
            public selectAttendanceRec() {
                var self = this;
                //not select yet left element
                if (self.currentCodeAttendace() == 0 || self.currentCodeAttendace() == undefined) {
                    nts.uk.ui.dialog.alertError({ messageId: 'Msg_1140' });
                    return;
                }
                //check dataSelected
                if (self.dataSelected != undefined && self.dataSelected().length > 0) {
                    return;
                }
                self.codeSingleAttendanceSelected(self.currentCodeAttendace());
                //add to right-list
                var listSource: Array<viewmodel.model.AttendanceRecordItem> = self.dataSource(),
                    attendanceSelected = listSource.filter(e => e.attendanceItemId == self.currentCodeAttendace())[0],
                    listSelected: Array<viewmodel.model.AttendanceRecordItem> = [attendanceSelected];
                self.dataSelected(listSelected);
                var singleAttendanceSelected: viewmodel.model.SingleAttendanceRecord = new viewmodel.model.SingleAttendanceRecord(attendanceSelected.attendanceItemName, self.codeSingleAttendanceSelected(), self.typeAttendanceCodeSelected());
                self.singleAttendanceRecord(singleAttendanceSelected);
                //remove left list
                var index = listSource.indexOf(attendanceSelected);
                listSource.splice(index, 1);
                self.dataSource(listSource);
                //default currenctCodeAttnd (left)
                self.currentCodeAttendace(0);
            }
            //click arrow-left-button
            public removeAttendanceSelected() {
                /**
                 * neu co item duoc chon o bang D7_1 thi dem no ve list D4_1 lai va xoa ben D7_1 di
                 */
                var self = this;
                if (self.codeSingleAttendanceSelected() == 0 || self.codeSingleAttendanceSelected() == undefined) {
                    return;
                }
                //return left-list
                var typeAttendanceCodeCurrent = self.typeAttendanceCodeSelected();
                self.typeAttendanceCodeSelected(typeAttendanceCodeCurrent);
                service.getAttendanceRecordItemsByScreenUseAtr(typeAttendanceCodeCurrent).done(function(listAttendanceRecordItem: Array<viewmodel.model.AttendanceRecordItem>) {
                    //remove right-list
                    self.clearSelected();
                    //filter for exportAttr = 1 (DAILY) - condition in workFlow
                    let listAttendanceRecordItemFillExportAtr: Array<viewmodel.model.AttendanceRecordItem> = listAttendanceRecordItem.filter(e => e.typeOfAttendanceItem == 1);
                    self.dataSource(listAttendanceRecordItemFillExportAtr);
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
                    var result =  allAttendanceDaily.filter(e => e.attendanceItemId==attendanceId).map(e => e.attendanceItemName)[0]
                    return result;
                }
                return null;
            }

            // function click btn-decide
            public decideButtonClick() {
                var self = this;
                if (self.singleAttendanceRecord() == null || self.singleAttendanceRecord == undefined) {
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
            //function click btn-close
            public closeDialog() {
                nts.uk.ui.windows.close();
            }
            public methodTestWebservice() {
                alert("run test");
                var attendanceRecordPK: model.AttendanceRecordKey = new model.AttendanceRecordKey(1, 1, 1, 1);
                var singleAttendaceSave: model.SingleAttendanceRecordSaveCommand = new model.SingleAttendanceRecordSaveCommand(
                    1,
                    false,
                    1,
                    1,
                    1,
                    237,
                    14,
                    'ItemName update');
                service.testAnotherPath(singleAttendaceSave).done(function() {
                    console.log('updated');
                });
            }
            private handleLoadListAttendanceSelect(singleAttendanceSelected: model.SingleAttendanceRecord, listData: Array<model.AttendanceRecordItem>) {
                var self = this;
                self.singleAttendanceRecord(singleAttendanceSelected);
                self.codeSingleAttendanceSelected(singleAttendanceSelected.itemId);
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
            export class SingleAttendanceRecordSaveCommand {
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
        }
    }
}