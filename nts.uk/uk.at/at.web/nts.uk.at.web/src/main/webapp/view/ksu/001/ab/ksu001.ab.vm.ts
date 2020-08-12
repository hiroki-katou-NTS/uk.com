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
                selectMultiple: true,
                isEnable: true
            };
            
            self.dataCell = {};

            self.selectedWorkTypeCode.subscribe((newValue) => {
                if (newValue == null || newValue == undefined)
                    return;

                uk.localStorage.setItem("workTypeCodeSelected", newValue);

                let workType = _.filter(self.listWorkType(), function(o) { return o.workTypeCode == newValue; });
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
                workTypeCode: objWorkType[0].workTypeCode,
                workTypeName: objWorkType[0].name,
                workTimeCode: (objWorkType[0].workTimeSetting == 2) ? '' : (objWorkTime.length > 0) ? (objWorkTime[0].code) : '',
                workTimeName: (objWorkType[0].workTimeSetting == 2) ? '' : (objWorkTime.length > 0 && objWorkTime[0].code != '') ? (objWorkTime[0].name) : '',
                startTime: (objWorkType[0].workTimeSetting == 2) ? '' : (objWorkTime.length > 0 && objWorkTime[0].code != '') ? (objWorkTime[0].tzStart1) : '',
                endTime: (objWorkType[0].workTimeSetting == 2) ? '' : (objWorkTime.length > 0 && objWorkTime[0].code != '') ? (objWorkTime[0].tzEnd1) : '',
            };
            __viewContext.viewModel.viewA.dataCell = self.dataCell;

            if (__viewContext.viewModel.viewA.selectedModeDisplayInBody() == 'time') {
                let dataWorkType = __viewContext.viewModel.viewA.dataCell;
                $("#extable").exTable("stickData", [{ workTypeCode: dataWorkType.workTypeCode, workTypeName: dataWorkType.workTypeName, workTimeCode: dataWorkType.workTimeCode, workTimeName: dataWorkType.workTimeName, startTime: dataWorkType.startTime, endTime: dataWorkType.endTime }]);
            } else if (__viewContext.viewModel.viewA.selectedModeDisplayInBody() == 'shortName') {
                let dataWorkType = __viewContext.viewModel.viewA.dataCell;
                $("#extable").exTable("stickData", [{ workTypeCode: dataWorkType.workTypeCode, workTypeName: dataWorkType.workTypeName, workTimeCode: dataWorkType.workTimeCode, workTimeName: dataWorkType.workTimeName }]);
            }

            // set style text 貼り付けのパターン1
            if (self.isDisableWorkTime) {
                $("#extable").exTable("stickStyler", function(rowIdx, key, data) {
                    return { textColor: "red" };
                });
            } else {
                $("#extable").exTable("stickStyler", function(rowIdx, key, data) {
                    return { textColor: "blue" };
                });
            }
            
            // 貼り付けのパターン2
            if (self.isDisableWorkTime == false && objWorkTime[0].code == '') {
                $("#extable").exTable("stickStyler", function(rowIdx, key, data) {
                    return { textColor: "red" };
                });
            }
        }
    }
}