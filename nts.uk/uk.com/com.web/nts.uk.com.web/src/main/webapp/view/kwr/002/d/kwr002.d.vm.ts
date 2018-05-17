module nts.uk.com.view.kwr002.d {
    import blockUI = nts.uk.ui.block;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    export module viewmodel {
        export class ScreenModel {
            columns: KnockoutObservableArray<any>;
            currentCodeAttendace: KnockoutObservable<string>;
            dataSource: KnockoutObservableArray<model.AttendanceRecordItem>;
            typeAttendanceSelected: KnockoutObservable<number>;
            listAttendanceType: KnockoutObservableArray<model.AttendaceType>;
            columnsSelected: KnockoutObservable<any>;
            dataSelected:KnockoutObservableArray<model.AttendanceRecordItem>;


            attendanceRecordName: KnockoutObservable<String>;
            attendanceRecordExport: KnockoutObservable<model.AttendanceRecordExport>;
            layoutCode: KnockoutObservable<string>;
            layoutName: KnockoutObservable<string>;
            position: KnockoutObservable<string>;
            columnIndex: KnockoutObservable<number>;
            exportAtr: KnockoutObservable<number>;
            directText:KnockoutObservable<string>;



            constructor() {
                var self = this;
                self.listAttendanceType = ko.observableArray([
                    new model.AttendaceType(13, nts.uk.resource.getMessage("Msg_1206", [])),
                    new model.AttendaceType(14, nts.uk.resource.getMessage("Msg_1207", [])),
                    new model.AttendaceType(15, nts.uk.resource.getMessage("Msg_1208", [])),
                ]);
                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText('KWR002_146'), key: 'attendanceItemId', width: 80 },
                    { headerText: nts.uk.resource.getText('KWR002_147'), key: 'attendanceItemName', formatter: _.escape, width: 150 }
                ]);
                self.currentCodeAttendace = ko.observable(null);
                self.dataSource = ko.observableArray([]);
                self.typeAttendanceSelected = ko.observable(0);
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
            }
            public startPage(): JQueryPromise<any> {
                blockUI.invisible();
                var self = this;
                var dfd = $.Deferred();
                let attendanceRecordExport: any = getShared('attendanceRecordExport');
                if (attendanceRecordExport != null && attendanceRecordExport != undefined) {
                    self.attendanceRecordExport(attendanceRecordExport);
                    self.attendanceRecordName(self.attendanceRecordExport().attendanceItemName);
                    self.layoutCode(self.attendanceRecordExport().layoutCode);
                    self.layoutName(self.attendanceRecordExport().layoutName);
                    self.position(self.attendanceRecordExport().position);
                    self.columnIndex(self.attendanceRecordExport().columnIndex);
                    self.exportAtr(self.attendanceRecordExport().exportAtr);
                }
                self.directText( nts.uk.resource.getText('KWR002_131')+">"+self.columnIndex()+nts.uk.resource.getText('KWR002_132')+">"+
                                                                        self.position()+nts.uk.resource.getText('KWR002_133'));


                blockUI.clear();
                dfd.resolve();
                return dfd.promise();
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
                layoutCode: string;
                layoutName: string;
                columnIndex: number;
                position: string;
                exportAtr: number;
                constructor(attendanceItemName: string, layoutCode: string, layoutName: string, indexColumn: number, position: string, exportAtr: number) {
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