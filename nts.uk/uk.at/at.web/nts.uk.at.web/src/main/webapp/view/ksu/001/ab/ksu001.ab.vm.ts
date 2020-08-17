module nts.uk.at.view.ksu001.ab.viewmodel {
    import setShare = nts.uk.ui.windows.setShared;
    import getShare = nts.uk.ui.windows.getShared;
    import formatById = nts.uk.time.format.byId;
    import alertError = nts.uk.ui.dialog.alertError;
    import getText = nts.uk.resource.getText;

    export class ScreenModel {

        listWorkType: KnockoutObservableArray<ksu001.a.viewmodel.IWorkTypeDto> = ko.observableArray([]);
        listWorkTime: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedWorkTypeCode: KnockoutObservable<string>;
        objWorkTime: any;
        input: any
        dataCell: any; // data để paste vào grid
        isDisableWorkTime : boolean;
        enableWorkTime : KnockoutObservable<boolean> = ko.observable(true);
        workPlaceId: KnockoutObservable<string>;

        constructor() {
            let self = this;
            let workTypeCodeSave = uk.localStorage.getItem('workTypeCodeSelected');
            let workTimeCodeSave = uk.localStorage.getItem('workTimeCodeSelected');
            self.isDisableWorkTime = false;
            self.workPlaceId = ko.observable('');

            self.listWorkType = ko.observableArray([]);
            self.selectedWorkTypeCode = ko.observable(workTypeCodeSave.isPresent() ? workTypeCodeSave.get() : '');
            self.input = {
                fillter: false,
                workPlaceId: self.workPlaceId,
                initiallySelected: [workTimeCodeSave.isPresent() ? workTimeCodeSave.get() : ''],
                displayFormat: '',
                showNone: true,
                showDeferred: true,
                selectMultiple: true
            };

            self.dataCell = {};

            self.selectedWorkTypeCode.subscribe((newValue) => {
                if (newValue == null || newValue == undefined)
                    return;
                uk.localStorage.setItem("workTypeCodeSelected", newValue);

                let workType = _.filter(self.listWorkType(), function(o) { return o.workTypeCode == newValue; });
                console.log(workType);
                if (workType.length > 0) {
                    console.log(workType[0]);
                    // check workTimeSetting 
                    if (workType[0].workTimeSetting == 2) {
                        self.isDisableWorkTime = true;
                        $("#listWorkType").addClass("disabledWorkTime");
                    } else {
                        self.isDisableWorkTime = false;
                        $("#listWorkType").removeClass("disabledWorkTime");
                    }
                }
                self.updateDataCell(self.objWorkTime);
            });
        }

        getDataWorkTime(data, listWorkTime) {
            let self = this;
            self.objWorkTime = data;
            self.updateDataCell(data);
            uk.localStorage.setItem("workTimeCodeSelected", data[0].code);
            self.listWorkTime(listWorkTime);
        }

        updateDataCell(objWorkTime: any) {
            let self = this;

            if (objWorkTime == undefined)
                return;

            let objWorkType = _.filter(self.listWorkType(), function(o) { return o.workTypeCode == self.selectedWorkTypeCode(); });
            // stick data
            self.dataCell = {
                objWorkType : objWorkType[0],
                objWorkTime : objWorkTime[0]
            };
            __viewContext.viewModel.viewA.dataCell = self.dataCell;

            if (__viewContext.viewModel.viewA.selectedModeDisplayInBody() == 'time') {
                let dataWorkType = __viewContext.viewModel.viewA.dataCell;
                $("#extable").exTable("stickData", {
                    workTypeCode: objWorkType[0].workTypeCode,
                    workTypeName: objWorkType[0].name,
                    workTimeCode: (objWorkType[0].workTimeSetting == 2) ? '' : (objWorkTime.length > 0) ? (objWorkTime[0].code) : '',
                    workTimeName: (objWorkType[0].workTimeSetting == 2) ? '' : (objWorkTime.length > 0 && objWorkTime[0].code != '') ? (objWorkTime[0].name) : '',
                    startTime: (objWorkType[0].workTimeSetting == 2) ? '' : (objWorkTime.length > 0 && objWorkTime[0].code != '') ? (objWorkTime[0].tzStart1) : '',
                    endTime: (objWorkType[0].workTimeSetting == 2) ? '' : (objWorkTime.length > 0 && objWorkTime[0].code != '') ? (objWorkTime[0].tzEnd1) : ''
                });
                
            } else if (__viewContext.viewModel.viewA.selectedModeDisplayInBody() == 'shortName') {
                let dataWorkType = __viewContext.viewModel.viewA.dataCell;
                $("#extable").exTable("stickData", {
                    workTypeCode: objWorkType[0].workTypeCode,
                    workTypeName: objWorkType[0].name,
                    workTimeCode: (objWorkType[0].workTimeSetting == 2) ? '' : (objWorkTime.length > 0) ? (objWorkTime[0].code) : '',
                    workTimeName: (objWorkType[0].workTimeSetting == 2) ? '' : (objWorkTime.length > 0 && objWorkTime[0].code != '') ? (objWorkTime[0].name) : ''
                });
            }

            // set style text 貼り付けのパターン1
            if (self.isDisableWorkTime) {
                $("#extable").exTable("stickStyler", function(rowIdx, key, data) {
                    debugger;
                    return { textColor: "red" };
                });
            } else {
                $("#extable").exTable("stickStyler", function(rowIdx, key, data) {
                    debugger;
                    let workInfo = _.filter(self.listWorkType(), function(o) { return o.workTypeCode == self.selectedWorkTypeCode(); });
                    if (__viewContext.viewModel.viewA.selectedModeDisplayInBody() == 'time'
                        || __viewContext.viewModel.viewA.selectedModeDisplayInBody() == 'shortName') {
                        if (workInfo.length > 0) {
                            let workStyle = workInfo[0].workStyle;
                            if (workStyle == AttendanceHolidayAttr.FULL_TIME) {
                                return { textColor: "#0000ff" }; // color-attendance
                            }
                            if (workStyle == AttendanceHolidayAttr.MORNING) {
                                return { textColor: "#FF7F27" };// color-half-day-work
                            }
                            if (workStyle == AttendanceHolidayAttr.AFTERNOON) {
                                return { textColor: "#FF7F27" };// color-half-day-work
                            }
                            if (workStyle == AttendanceHolidayAttr.HOLIDAY) {
                                return { textColor: "#ff0000" };// color-holiday
                            }
                            if (nts.uk.util.isNullOrUndefined(workStyle) || nts.uk.util.isNullOrEmpty(workStyle)) {
                                // デフォルト（黒）  Default (black)
                                return { textColor: "#000000" }
                            }
                        }
                    }
                });
            }
            
            // 貼り付けのパターン2
            if (self.isDisableWorkTime == false && objWorkTime[0].code == '') {
                $("#extable").exTable("stickStyler", function(rowIdx, key, data) {
                    return { textColor: "red" };
                });
            }
            let obj = {};
        }
    }
    
    enum AttendanceHolidayAttr {
        FULL_TIME = 3, //(3, "１日出勤系"),
        MORNING = 1, //(1, "午前出勤系"),
        AFTERNOON = 2, //(2, "午後出勤系"),
        HOLIDAY = 0, //(0, "１日休日系");
    }
}