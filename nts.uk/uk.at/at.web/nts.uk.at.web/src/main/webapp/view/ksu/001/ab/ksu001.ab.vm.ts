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
        isRedColor : boolean;
        enableWorkTime : KnockoutObservable<boolean> = ko.observable(true);
        workPlaceId: KnockoutObservable<string>      = ko.observable('');
        workTimeCode:KnockoutObservable<string>;
        enableListWorkType: KnockoutObservable<boolean> = ko.observable(true);
        reInit =  false;
        KEY: string = 'USER_INFOR';

        constructor(id, listWorkType) { //id : workplaceId || workplaceGroupId; 
            let self = this;
            let workTypeCodeSave = uk.localStorage.getItem('workTypeCodeSelected');
            let workTimeCodeSave = uk.localStorage.getItem('workTimeCodeSelected');
            self.isDisableWorkTime = false;
            self.isRedColor = false;
            self.workTimeCode = ko.observable(workTimeCodeSave.isPresent() ? workTimeCodeSave.get() : '');
            self.listWorkType = ko.observableArray([]);
            if (id != undefined) {
                self.workPlaceId(id);
                self.listWorkType(listWorkType);
                self.reInit = true;
            }
            self.selectedWorkTypeCode = ko.observable(workTypeCodeSave.isPresent() ? workTypeCodeSave.get() : '');
            self.input = {
                fillter: false,
                workPlaceId: self.workPlaceId,
                initiallySelected: [self.workTimeCode()],
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
                        $("#listWorkTime").addClass("disabledWorkTime");
                    } else {
                        self.isDisableWorkTime = false;
                        $("#listWorkTime").removeClass("disabledWorkTime");
                    }
                 }
                if (self.reInit == false) {
                    self.updateDataCell(self.objWorkTime);
                }
            });
        }

        getDataWorkTime(data, listWorkTime) {
            let self = this;
            self.objWorkTime = data;
            self.updateDataCell(data);
            uk.localStorage.setItem("workTimeCodeSelected", data[0].code);
            self.listWorkTime(listWorkTime);
        }

        // set stick data
        updateDataCell(objWorkTime: any) {
            let self = this;

            if (objWorkTime == undefined)
                return;

            let objWorkType = _.filter(self.listWorkType(), function(o) { return o.workTypeCode == self.selectedWorkTypeCode(); });
            
            self.dataCell = {
                objWorkType : objWorkType[0],
                objWorkTime : objWorkTime[0]
            };
            __viewContext.viewModel.viewA.dataCell = self.dataCell;

            if (__viewContext.viewModel.viewA.selectedModeDisplayInBody() == 'time') {
                //貼り付けのパターン1
                if (objWorkType[0].workTimeSetting == 2) {
                    $("#extable").exTable("stickFields", ["workTypeName","workTimeName", "startTime", "endTime"]);
                    $("#extable").exTable("stickData", {
                        workTypeCode: objWorkType[0].workTypeCode,
                        workTypeName: objWorkType[0].name,
                        workTimeCode: null,
                        workTimeName: null,
                        startTime   : '',
                        endTime     : ''
                    });
                    self.isRedColor = true;
                } else if (objWorkType[0].workTimeSetting != 2 && self.dataCell.objWorkTime.code == '') {
                    //貼り付けのパターン2
                    $("#extable").exTable("stickFields", ["workTypeName","workTimeName", "startTime", "endTime"]);
                    $("#extable").exTable("stickData", {
                        workTypeCode: objWorkType[0].workTypeCode,
                        workTypeName: objWorkType[0].name,
                        workTimeCode: null,
                        workTimeName: null,
                        startTime   : '',
                        endTime     : ''
                    });
                    self.isRedColor = true;
                } else if (objWorkType[0].workTimeSetting != 2 && self.dataCell.objWorkTime.code == ' ') {
                    $("#extable").exTable("stickFields", ["workTypeName"]);
                    // 貼り付けのパターン3
                    $("#extable").exTable("stickData", {
                        workTypeCode: objWorkType[0].workTypeCode,
                        workTypeName: objWorkType[0].name,
                        workTimeCode: null,
                        workTimeName: null,
                        startTime   : '',
                        endTime     : ''
                    });
                    self.isRedColor = true;
                } else {
                    $("#extable").exTable("stickFields", ["workTypeName","workTimeName", "startTime", "endTime"]);
                    $("#extable").exTable("stickData", {
                        workTypeCode: objWorkType[0].workTypeCode,
                        workTypeName: objWorkType[0].name,
                        workTimeCode: (objWorkTime.length > 0) ? (objWorkTime[0].code) : null,
                        workTimeName: (objWorkTime.length > 0 && objWorkTime[0].code != '') ? (objWorkTime[0].name) : null,
                        startTime   : (objWorkTime.length > 0 && objWorkTime[0].code != '') ? (objWorkTime[0].tzStart1) : '',
                        endTime     : (objWorkTime.length > 0 && objWorkTime[0].code != '') ? (objWorkTime[0].tzEnd1) : ''
                    });
                    self.isRedColor = false;
                }

            } else if (__viewContext.viewModel.viewA.selectedModeDisplayInBody() == 'shortName') {
                //貼り付けのパターン1
                if (objWorkType[0].workTimeSetting == 2) {
                    $("#extable").exTable("stickFields", ["workTypeName","workTimeName"]);
                    $("#extable").exTable("stickData", {
                        workTypeCode: objWorkType[0].workTypeCode,
                        workTypeName: objWorkType[0].name,
                        workTimeCode: null,
                        workTimeName: null,
                        startTime   : '',
                        endTime     : ''
                    });
                    self.isRedColor = true;
                } else if (objWorkType[0].workTimeSetting != 2 && self.dataCell.objWorkTime.code == '') {
                    //貼り付けのパターン2
                    $("#extable").exTable("stickFields", ["workTypeName","workTimeName"]);
                    $("#extable").exTable("stickData", {
                        workTypeCode: objWorkType[0].workTypeCode,
                        workTypeName: objWorkType[0].name,
                        workTimeCode: null,
                        workTimeName: null,
                        startTime   : '',
                        endTime     : ''
                    });
                    self.isRedColor = true;
                } else if (objWorkType[0].workTimeSetting != 2 && self.dataCell.objWorkTime.code == ' ') {
                    $("#extable").exTable("stickFields", ["workTypeName"]);
                    // 貼り付けのパターン3
                    $("#extable").exTable("stickData", {
                        workTypeCode: objWorkType[0].workTypeCode,
                        workTypeName: objWorkType[0].name,
                        workTimeCode: null,
                        workTimeName: null,
                        startTime   : '',
                        endTime     : ''
                    });
                    self.isRedColor = true;
                } else {
                    $("#extable").exTable("stickFields", ["workTypeName", "workTimeName"]);
                    $("#extable").exTable("stickData", {
                        workTypeCode: objWorkType[0].workTypeCode,
                        workTypeName: objWorkType[0].name,
                        workTimeCode: (objWorkTime.length > 0) ? (objWorkTime[0].code) : null,
                        workTimeName: (objWorkTime.length > 0 && objWorkTime[0].code != '') ? (objWorkTime[0].name) : null
                    });
                    self.isRedColor = false;
                }
            }

            // set style text 貼り付けのパターン1 
            if (self.isDisableWorkTime || self.isRedColor) {
                $("#extable").exTable("stickStyler", function(rowIdx, key, innerIdx, data) {
                    if (__viewContext.viewModel.viewA.selectedModeDisplayInBody() == 'time') {
                        if (innerIdx === 0 || innerIdx === 1) return { textColor: "red" };
                        else if (innerIdx === 2 || innerIdx === 3) return { textColor: "black" };
                    } else if (__viewContext.viewModel.viewA.selectedModeDisplayInBody() == 'shortName') {
                        if (innerIdx === 0 || innerIdx === 1) return { textColor: "red" };
                    }
                });
            } else {
                $("#extable").exTable("stickStyler", function(rowIdx, key, innerIdx, data) {
                    let workInfo = _.filter(self.listWorkType(), function(o) { return o.workTypeCode == self.selectedWorkTypeCode(); });
                    if (__viewContext.viewModel.viewA.selectedModeDisplayInBody() == 'time') {
                        if (workInfo.length > 0) {
                            let workStyle = workInfo[0].workStyle;
                            if (workStyle == AttendanceHolidayAttr.FULL_TIME) {
                                if (innerIdx === 0 || innerIdx === 1) return { textColor: "#0000ff" }; // color-attendance
                                else if (innerIdx === 2 || innerIdx === 3) return { textColor: "black" };
                            }
                            if (workStyle == AttendanceHolidayAttr.MORNING) {
                                if (innerIdx === 0 || innerIdx === 1) return { textColor: "#FF7F27" }; // color-half-day-work
                                else if (innerIdx === 2 || innerIdx === 3) return { textColor: "black" };
                            }
                            if (workStyle == AttendanceHolidayAttr.AFTERNOON) {
                                if (innerIdx === 0 || innerIdx === 1) return { textColor: "#FF7F27" }; // color-half-day-work
                                else if (innerIdx === 2 || innerIdx === 3) return { textColor: "black" };
                            }
                            if (workStyle == AttendanceHolidayAttr.HOLIDAY) {
                                if (innerIdx === 0 || innerIdx === 1) return { textColor: "#ff0000" }; // color-holiday
                                else if (innerIdx === 2 || innerIdx === 3) return { textColor: "black" };
                            }
                            if (nts.uk.util.isNullOrUndefined(workStyle) || nts.uk.util.isNullOrEmpty(workStyle)) {
                                // デフォルト（黒）  Default (black)
                                if (innerIdx === 0 || innerIdx === 1) return { textColor: "#000000" }; // デフォルト（黒）  Default (black)
                                else if (innerIdx === 2 || innerIdx === 3) return { textColor: "#000000" };
                            }
                        }
                    } else if (__viewContext.viewModel.viewA.selectedModeDisplayInBody() == 'shortName') {
                        if (workInfo.length > 0) {
                            let workStyle = workInfo[0].workStyle;
                            if (workStyle == AttendanceHolidayAttr.FULL_TIME) {
                                if (innerIdx === 0 || innerIdx === 1) return { textColor: "#0000ff" }; // color-attendance
                            }
                            if (workStyle == AttendanceHolidayAttr.MORNING) {
                                if (innerIdx === 0 || innerIdx === 1) return { textColor: "#FF7F27" }; // color-half-day-work
                            }
                            if (workStyle == AttendanceHolidayAttr.AFTERNOON) {
                                if (innerIdx === 0 || innerIdx === 1) return { textColor: "#FF7F27" }; // color-half-day-work
                            }
                            if (workStyle == AttendanceHolidayAttr.HOLIDAY) {
                                if (innerIdx === 0 || innerIdx === 1) return { textColor: "#ff0000" }; // color-holiday
                            }
                            if (nts.uk.util.isNullOrUndefined(workStyle) || nts.uk.util.isNullOrEmpty(workStyle)) {
                                if (innerIdx === 0 || innerIdx === 1) return { textColor: "#000000" }; // デフォルト（黒）  Default (black)
                            }
                        }
                    }
                });
            }
            
            let item = uk.localStorage.getItem(self.KEY);
            if (item.isPresent()) {
                let userInfor = JSON.parse(item.get());
                if (userInfor.updateMode == 'copyPaste') {
                    $("#extable").exTable("stickStyler", function(rowIdx, key,innerIdx, data) {
                        return { textColor: "" };
                    });
                }
            }
        }
    }
    
    enum AttendanceHolidayAttr {
        FULL_TIME = 3, //(3, "１日出勤系"),
        MORNING = 1, //(1, "午前出勤系"),
        AFTERNOON = 2, //(2, "午後出勤系"),
        HOLIDAY = 0, //(0, "１日休日系");
    }
}