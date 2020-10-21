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
        isRedColor : boolean;
        enableWorkTime : KnockoutObservable<boolean> = ko.observable(true);
        workTimeCode:KnockoutObservable<string>;
        enableListWorkType: KnockoutObservable<boolean> = ko.observable(true);
        reInit =  false;
        KEY: string = 'USER_INFOR';
        
        width: KnockoutObservable<number>;
        tabIndex: KnockoutObservable<number | string>;
        filter: KnockoutObservable<boolean>;
        disabled: KnockoutObservable<boolean>;
        workplaceIdKCP013: KnockoutObservable<string> = ko.observable('');
        selected: KnockoutObservable<string> | KnockoutObservableArray<string>;
        dataSources: KnockoutObservableArray<WorkTimeModel>;
        showMode: KnockoutObservable<SHOW_MODE>;

        constructor(id, listWorkType) { //id : workplaceId || workplaceGroupId; 
            let self = this;
            let workTypeCodeSave = uk.localStorage.getItem('workTypeCodeSelected');
            let workTimeCodeSave = uk.localStorage.getItem('workTimeCodeSelected');
            self.isRedColor = false;
            self.listWorkType = ko.observableArray([]);
            
            if (id != undefined) {
                self.listWorkType(listWorkType);
                self.reInit = true;
            }

            self.width = ko.observable(500);
            self.tabIndex = ko.observable('');
            self.filter = ko.observable(true);
            self.disabled = ko.observable(false);
            self.selected = ko.observable(workTimeCodeSave.isPresent() ? workTimeCodeSave.get() : '');
            self.dataSources = ko.observableArray([]);
            self.showMode = ko.observable(SHOW_MODE.BOTTLE);

            self.dataCell = {};
            
            self.selectedWorkTypeCode = ko.observable(workTypeCodeSave.isPresent() ? workTypeCodeSave.get() : '');
            self.workTimeCode = ko.observable(workTimeCodeSave.isPresent() ? workTimeCodeSave.get() : '');
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
                        self.disabled(true);
                    } else {
                        self.disabled(false);
                    }
                 }
                if (self.reInit == false) {
                    self.updateDataCell(self.objWorkTime);
                }
            });
            
            self.selected.subscribe((wkpTimeCd) => {
                if(_.isNil(wkpTimeCd) || wkpTimeCd == '')
                    return;
                console.log(wkpTimeCd);
                
                uk.localStorage.setItem("workTimeCodeSelected", wkpTimeCd);
                
                let ds = ko.unwrap(self.dataSources);

                let itemSelected;
                if(wkpTimeCd === 'none'){
                    itemSelected = _.find(ds, f => f.code === '');
                }else if(wkpTimeCd === 'deferred'){
                    itemSelected = _.find(ds, f => f.code === ' ');
                }else{
                    itemSelected = _.find(ds, f => f.code === wkpTimeCd);
                }

                if (!itemSelected) {
                    self.updateDataCell(null);
                    self.objWorkTime = null;
                } else {
                    self.updateDataCell(itemSelected);
                    self.objWorkTime = itemSelected;
                }
            });
        }

        // set stick data
        updateDataCell(objWorkTime: any) {
            let self = this;
            
            if (objWorkTime == undefined || objWorkTime == null)
                return;

            let objWorkType = _.filter(self.listWorkType(), function(o) { return o.workTypeCode == self.selectedWorkTypeCode(); });
            
            self.dataCell = {
                objWorkType : objWorkType[0],
                objWorkTime : objWorkTime
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

                    let startTime = objWorkTime.tzStart1 == null ? '' : formatById("Clock_Short_HM", objWorkTime.tzStart1);
                    let endTime   = objWorkTime.tzEnd1   == null ? '' : formatById("Clock_Short_HM", objWorkTime.tzEnd1);
                    
                    $("#extable").exTable("stickData", {
                        workTypeCode: objWorkType[0].workTypeCode,
                        workTypeName: objWorkType[0].name,
                        workTimeCode: (objWorkTime != null)    ? (objWorkTime.code) : null,
                        workTimeName: (objWorkTime != null     && objWorkTime.code != '') ? (objWorkTime.name) : null,
                        startTime   : (objWorkTime != null > 0 && objWorkTime.code != '') ? (startTime) : '',
                        endTime     : (objWorkTime != null > 0 && objWorkTime.code != '') ? (endTime) : ''
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
                        workTimeCode: (objWorkTime != null) ? (objWorkTime.code) : null,
                        workTimeName: (objWorkTime != null &&  objWorkTime.code != '') ? (objWorkTime.name) : null
                    });
                    self.isRedColor = false;
                }
            }

            // set style text 貼り付けのパターン1 
            if (self.disabled() == true || self.isRedColor) {
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

    enum SHOW_MODE {
        // not has any option
        NOT_SET = 0,
        // show none option
        NONE = 1,
        // show deffered option
        DEFFERED = 2,
        // show none & deffered option
        BOTTLE = 3
    }

    interface WorkTimeModel {
        id: string;
        code: string;
        name: string;
        tzStart1: number;
        tzEnd1: number;
        tzStart2: number;
        tzEnd2: number;
        workStyleClassfication: string;
        remark: string;
        useDistintion: number;
        tzStartToEnd1: string;
        tzStartToEnd2: string;
    }
}