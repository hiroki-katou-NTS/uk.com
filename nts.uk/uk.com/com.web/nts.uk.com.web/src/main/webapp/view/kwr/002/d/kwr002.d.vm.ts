module nts.uk.com.view.kwr002.d {
    import blockUI = nts.uk.ui.block;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    export module viewmodel {
        export class ScreenModel {
            columns: KnockoutObservableArray<any>;
            currentCodeAttendace: KnockoutObservable<number>;
            dataSource: KnockoutObservableArray<viewmodel.model.AttendanceRecordItem>;
            typeAttendanceCodeSelected: KnockoutObservable<number>;
            listAttendanceType: KnockoutObservableArray<viewmodel.model.AttendaceType>;
            columnsSelected: KnockoutObservable<any>;
            dataSelected: KnockoutObservableArray<viewmodel.model.AttendanceRecordItem>;
            codeSingleAttendanceSelected: KnockoutObservable<number>;
            singleAttendanceRecord: KnockoutObservable<viewmodel.model.SingleAttendanceRecord>;


            attendanceRecordName: KnockoutObservable<String>;
            attendanceRecordExport: KnockoutObservable<viewmodel.model.AttendanceRecordExport>;
            layoutCode: KnockoutObservable<number>;
            layoutName: KnockoutObservable<string>;
            position: KnockoutObservable<number>;
            columnIndex: KnockoutObservable<number>;
            exportAtr: KnockoutObservable<number>;
            directText: KnockoutObservable<string>;



            constructor() {
                var self = this;
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
                self.layoutCode = ko.observable(0);
                self.layoutName = ko.observable('');
                self.position = ko.observable(0);
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
                let attendanceRecordExportShared: any = getShared('attendanceRecordExport');
                if (attendanceRecordExportShared != null && attendanceRecordExportShared != undefined) {
                    self.attendanceRecordExport(attendanceRecordExportShared);
                    self.attendanceRecordName(self.attendanceRecordExport().attendanceItemName);
                    self.layoutCode(self.attendanceRecordExport().layoutCode);
                    self.layoutName(self.attendanceRecordExport().layoutName);
                    self.position(self.attendanceRecordExport().position);
                    self.columnIndex(self.attendanceRecordExport().columnIndex);
                    self.exportAtr(self.attendanceRecordExport().exportAtr);
                }
                self.directText(nts.uk.resource.getText('KWR002_131') + ">" + self.columnIndex() + nts.uk.resource.getText('KWR002_132') + ">" +
                    self.position() + nts.uk.resource.getText('KWR002_133'));
                var attendanceTypeCodeInit = self.listAttendanceType()[0].attendanceTypeCode;//init attendance type code
                self.typeAttendanceCodeSelected(attendanceTypeCodeInit);
                service.getAttendanceRecordItemsByScreenUseAtr(attendanceTypeCodeInit).done(function(listAttendanceRecordItem: Array<viewmodel.model.AttendanceRecordItem>) {

                    blockUI.clear();
                    if (listAttendanceRecordItem.length == 0 || listAttendanceRecordItem == undefined) {
                        self.dataSource();
                    } else {
                        self.dataSource(listAttendanceRecordItem);
                        //get singleAttendanceRecord selected
                        if (self.attendanceRecordExport() != null && self.attendanceRecordExport() != undefined) {
                            let attendanceRecordKey: viewmodel.model.AttendanceRecordKey = new viewmodel.model.AttendanceRecordKey(self.attendanceRecordExport().layoutCode,
                                self.attendanceRecordExport().columnIndex, self.attendanceRecordExport().position, self.attendanceRecordExport().exportAtr);
                            service.findSingleAttendanceRecord(attendanceRecordKey).done(function(singleAttendanceRecord: viewmodel.model.SingleAttendanceRecord) {
                                self.codeSingleAttendanceSelected(singleAttendanceRecord.itemId);
                                self.singleAttendanceRecord(singleAttendanceRecord);

                                //kiem tra xem thang nay co thang nao dc chon chua => new co roi thi kiem tra co trong cai list13 ko, neu co  thang list13 phai xoa no di, neu ko co thi thoi

                            });
                        }

                    }
                    dfd.resolve();
                });
                return dfd.promise();
            }
            private clearError(): void {
                if ($('.nts-validate').ntsError("hasError") == true) {
                    $('.nts-validate').ntsError('clear');
                }
                if ($('.nts-editor').ntsError("hasError") == true) {
                    $('.nts-input').ntsError('clear');
                }
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
                let listSource: Array<viewmodel.model.AttendanceRecordItem> = self.dataSource();
                let attendanceSelected = listSource.filter(e => e.attendanceItemId == self.currentCodeAttendace())[0];
                let listSelected: Array<viewmodel.model.AttendanceRecordItem> = [attendanceSelected];
                self.dataSelected(listSelected);
                //remove left list
                let index = listSource.indexOf(attendanceSelected);
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
                alert(typeAttendanceCodeCurrent);
                service.getAttendanceRecordItemsByScreenUseAtr(typeAttendanceCodeCurrent).done(function(listAttendanceRecordItem: Array<viewmodel.model.AttendanceRecordItem>) {
                    //remove right-list
                    self.clearSelected();
                    self.dataSource(listAttendanceRecordItem);
                });



            }
            public findNameAttendance(attendanceId: number): string {
                var self = this;
                let listAttendance: Array<viewmodel.model.AttendanceRecordItem> = self.dataSource();
                return listAttendance.filter(e => e.attendanceItemId == attendanceId)[0].attendanceItemName;
            }
            private clearSelected() {
                var self = this;
                self.dataSelected([]);
                self.codeSingleAttendanceSelected(0);
                self.singleAttendanceRecord(null);
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
            export class AttendanceRecordItem {
                attendanceItemId: number;
                attendanceItemName: string;
                screenUseItem: number;
                constructor(attendanceItemId: number, attendanceItemName: string, screenUseItem: number) {
                    this.attendanceItemId = attendanceItemId;
                    this.attendanceItemName = attendanceItemName;
                    this.screenUseItem = screenUseItem;
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
                constructor(attendanceItemName: string, layoutCode: number, layoutName: string, indexColumn: number, position: number, exportAtr: number) {
                    this.attendanceItemName = attendanceItemName;
                    this.layoutCode = layoutCode;
                    this.layoutName = layoutName;
                    this.columnIndex = indexColumn;
                    this.position = position;
                    this.exportAtr = exportAtr;
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