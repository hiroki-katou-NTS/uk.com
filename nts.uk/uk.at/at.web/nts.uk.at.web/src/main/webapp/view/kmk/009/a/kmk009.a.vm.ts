module nts.uk.at.view.kmk009.a.viewmodel {
    import Enum = service.model.Enum;
    export class ScreenModel {
        itemTotalTimes: KnockoutObservableArray<model.TotalTimes>;
        itemTotalTimesDetail: KnockoutObservable<model.TotalTimesDetail>;
        totalClsEnums: Array<Enum>;
        valueEnum: KnockoutObservable<number>;
        currentCode: KnockoutObservable<any>;





        alarmTime: KnockoutObservable<number>;
        columns: KnockoutObservableArray<any>;
        dataSource: KnockoutObservableArray<model.DivergenceTime>;
        useSet: KnockoutObservableArray<any>;
        selectUse: KnockoutObservable<any>;
        selectSel: KnockoutObservable<any>;
        selectInp: KnockoutObservable<any>;
        divTimeName: KnockoutObservable<string>;
        timeItemName: KnockoutObservable<string>;
        errTime: KnockoutObservable<number>;
        checkErrInput: KnockoutObservable<boolean>;
        checkErrSelect: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;
        divTimeId: KnockoutObservable<number>;
        itemDivTime: KnockoutObservable<model.DivergenceTime>;
        listDivItem: KnockoutObservableArray<model.DivergenceItem>;
        lstItemSelected: KnockoutObservableArray<model.DivergenceItem>;
        list: KnockoutObservableArray<number>;
        use: KnockoutObservable<string>;
        notUse: KnockoutObservable<string>;
        enableUse: KnockoutObservable<boolean>;
        enableSelect: KnockoutObservable<boolean>;
        enableInput: KnockoutObservable<boolean>;
        objectOld: any;
        check: boolean;
        constructor() {
            var self = this;
            self.itemTotalTimes = ko.observableArray([]);
            self.itemTotalTimesDetail = ko.observable(null);
            self.totalClsEnums = [];;
            self.valueEnum = ko.observable(null);
            self.currentCode = ko.observable(1);






            self.check = false;
            self.list = ko.observableArray([]);
            self.enableUse = ko.observable(false);
            self.enableSelect = ko.observable(false);
            self.enableInput = ko.observable(false);
            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText('KMK009_4'), key: 'totalCountNo', width: 50 },
                { headerText: nts.uk.resource.getText('KMK009_5'), key: 'summaryAtr', width: 80 },
                { headerText: nts.uk.resource.getText('KMK009_6'), key: 'useAtr', formatter: _.escape, width: 150 },
                { headerText: nts.uk.resource.getText('KMK009_14'), key: 'totalTimesName', formatter: _.escape, width: 100 }
            ]);
            self.dataSource = ko.observableArray([]);
            self.useSet = ko.observableArray([
                { code: '1', name: nts.uk.resource.getText("KMK009_12") },
                { code: '0', name: nts.uk.resource.getText("KMK009_13") },
            ]);
            self.divTimeName = ko.observable('');
            self.timeItemName = ko.observable('');
            self.checkErrSelect = ko.observable(true);
            self.enable = ko.observable(true);
            self.divTimeId = ko.observable(null);
            self.itemDivTime = ko.observable(null);
            self.selectUse = ko.observable(0);
            self.alarmTime = ko.observable(0);
            self.errTime = ko.observable(0);
            self.selectSel = ko.observable(0);
            self.selectInp = ko.observable(0);
            self.checkErrInput = ko.observable(false);
            self.checkErrSelect = ko.observable(false);
            self.listDivItem = ko.observableArray([]);
            self.lstItemSelected = ko.observableArray([]);
            //subscribe currentCode
            self.currentCode.subscribe(function(codeChanged) {
                self.clearError();
                if (codeChanged == 0) { return; }
                self.selectUse(null);
                self.loadAllTotalTimesDetail(codeChanged);
                //                if (self.itemDivTime().inputSet.cancelErrSelReason == 1) {
                //                    self.checkErrInput(true);
                //                } else {
                //                    self.checkErrInput(false);
                //                }
                //                if (self.itemDivTime().selectSet.cancelErrSelReason == 1) {
                //                    self.checkErrSelect(true);
                //                } else {
                //                    self.checkErrSelect(false);
                //                }

                self.check = false;
                //                $("#itemname").focus();
            });
            //subscribe selectUse
            self.selectUse.subscribe(function(codeChanged) {
                if (codeChanged == 1) {
                    self.enableUse(true);
                    if (self.selectSel() == 1) {
                        self.enableSelect(true);
                    } else {
                        self.enableSelect(false);
                    }
                    if (self.selectInp() == 1) {
                        self.enableInput(true);
                    } else {
                        self.enableInput(false);
                    }
                } else {
                    self.enableUse(false);
                    self.enableSelect(false);
                    self.enableInput(false);
                }
            });
            //subscribe selectSel
            self.selectSel.subscribe(function(codeChanged) {
                if (codeChanged == 1 && self.selectUse() == 1) {
                    self.enableSelect(true);
                } else {
                    self.enableSelect(false);
                    if ($('#inpDialog').ntsError("hasError") == true) {
                        $('#inpDialog').ntsError('clear');
                    }
                }
            });
            //subscribe selectInp
            self.selectInp.subscribe(function(codeChanged) {
                if (codeChanged == 1 && self.selectUse() == 1) {
                    self.enableInput(true);
                } else {
                    self.enableInput(false);
                }
            });

        }
        /**
         * start page
         * get all divergence time
         * get all divergence name
         */
        startPage(): JQueryPromise<any> {
            var self = this;
            //            nts.uk.ui.block.invisible();
            var dfd = $.Deferred();

            self.loadAllTotalTimesDetail(1);

            self.loadTotalClsEnum().done(function() {
                if (self.totalClsEnums.length > 0) {
                    self.valueEnum(self.totalClsEnums[0].value);
                }

                dfd.resolve();
            });

            self.loadAllTotalTimes();



            return dfd.promise();
        }

        private loadAllTotalTimes(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();

            nts.uk.ui.block.grayout();


            service.getAllTotalTimes().done(function(lstTotalTimes: Array<model.TotalTimes>) {
                nts.uk.ui.block.clear();
                //                if (lstTotalTimes === undefined || lstTotalTimes.length == 0) {
                //                    self.();
                //                } else {
                //                self.currentCode(0);
                self.itemTotalTimes(lstTotalTimes);
                //                    let rdivTimeFirst = _.first(lstDivTime);
                //                    self.currentCode(rdivTimeFirst.divTimeId);
                //                }
                dfd.resolve();
            })

            return dfd.promise();
        }

        // loadAllTotalTimesDetail
        private loadAllTotalTimesDetail(codeChanged: number): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();

            nts.uk.ui.block.grayout();


            service.getAllTotalTimesDetail(codeChanged).done(function(item: model.TotalTimesDetail) {
                nts.uk.ui.block.clear();
                if (item == null || item === undefined) {
                    self.itemTotalTimesDetail(null);
                } else {
                    self.itemTotalTimesDetail(new model.TotalTimesDetail(item.totalCountNo, item.countAtr, item.useAtr, item.totalTimesName,
                        item.totalTimesABName, item.summaryAtr, item.totalCondition, item.listTotalSubjects));
                    self.selectUse(self.itemTotalTimesDetail().useAtr);
                }
            });

            return dfd.promise();
        }

        // load enum
        private loadTotalClsEnum(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();

            nts.uk.ui.block.grayout();

            // get setting
            service.getTotalClsEnum().done(function(dataRes: Array<Enum>) {

                self.totalClsEnums = dataRes;

                nts.uk.ui.block.clear();

                dfd.resolve();
            });

            return dfd.promise();
        }

        openKDL001Dialog() {
            var self = this;
            nts.uk.ui.block.grayout();

            var listWorkType = [];
            var listWorkCode = [];
            for (let i = 0; i < self.itemTotalTimesDetail().listTotalSubjects.length; i++) {
                if (self.itemTotalTimesDetail().listTotalSubjects()[i].workTypeAtr() === 0) {
                    listWorkType[i] = self.itemTotalTimesDetail().listTotalSubjects()[i].workTypeCode;
                } else {
                    listWorkCode[i] = self.itemTotalTimesDetail().listTotalSubjects()[i].workTypeCode;
                }
            }
            nts.uk.ui.windows.setShared('KDL001_listWorkCode', listWorkCode, true);
            nts.uk.ui.windows.sub.modal('/view/kdl/001/a/index.xhtml', { title: '選択肢の設定', }).onClosed(function(): any {
                nts.uk.ui.block.clear();
                if ($('#inpDialog').ntsError("hasError") == true) {
                    $('#inpDialog').ntsError('clear');
                }
                $("#itemname").focus();
            });
        }

        openKDL002Dialog() {
            var self = this;
            nts.uk.ui.block.grayout();
            var listWorkType = [];
            var listWorkCode = [];
            for (let i = 0; i < self.itemTotalTimesDetail().listTotalSubjects.length; i++) {
                if (self.itemTotalTimesDetail().listTotalSubjects()[i].workTypeAtr() === 0) {
                    listWorkType[i] = self.itemTotalTimesDetail().listTotalSubjects()[i].workTypeCode;
                } else {
                    listWorkCode[i] = self.itemTotalTimesDetail().listTotalSubjects()[i].workTypeCode;
                }
            }
            nts.uk.ui.windows.setShared('KDL002_SelectedItemId', listWorkType, true);
            nts.uk.ui.windows.sub.modal('/view/kdl/002/a/index.xhtml', { title: '選択肢の設定', }).onClosed(function(): any {
                nts.uk.ui.block.clear();
                if ($('#inpDialog').ntsError("hasError") == true) {
                    $('#inpDialog').ntsError('clear');
                }
                $("#itemname").focus();
            });
        }















        /**
         * find Divergence Time is selected
         */
        findDivTime(value: number): any {
            let self = this;
            var itemModel = null;
            return _.find(self.dataSource(), function(obj: model.DivergenceTime) {
                return obj.divTimeId == value;
            })
        }
        openBDialog() {
            var self = this;
            nts.uk.ui.block.grayout();
            nts.uk.ui.windows.setShared('KMK011_divTimeId', self.divTimeId(), true);
            nts.uk.ui.windows.sub.modal('/view/kmk/011/b/index.xhtml', { title: '選択肢の設定', }).onClosed(function(): any {
                nts.uk.ui.block.clear();
                if ($('#inpDialog').ntsError("hasError") == true) {
                    $('#inpDialog').ntsError('clear');
                }
                $("#itemname").focus();
            });
        }

        openDialog021() {
            var self = this;
            nts.uk.ui.block.grayout();
            service.getAllAttItem(1).done(function(lstAllItem: Array<model.AttendanceType>) {
                var listAllId = [];
                for (let j = 0; j < lstAllItem.length; j++) {
                    listAllId[j] = lstAllItem[j].attendanceItemId;
                }
                var listIdSelect = [];
                for (let i = 0; i < self.lstItemSelected().length; i++) {
                    listIdSelect[i] = self.lstItemSelected()[i].attendanceItemId;
                }
                nts.uk.ui.windows.setShared('AllAttendanceObj', listAllId, true);
                nts.uk.ui.windows.setShared('SelectedAttendanceId', listIdSelect, true);
                nts.uk.ui.windows.setShared('Multiple', true, true);
                nts.uk.ui.windows.sub.modal('../../../kdl/021/a/index.xhtml', { title: '乖離時間の登録＞対象項目', }).onClosed(function(): any {
                    nts.uk.ui.block.clear();
                    $("#itemname").focus();
                    var list = nts.uk.ui.windows.getShared('selectedChildAttendace');
                    if (list == null || list === undefined) return;
                    self.list(list);
                    var listUpdate = new Array<model.DivergenceTimeItem>();
                    for (let i = 0; i < list.length; i++) {
                        let itemUpdate = new model.DivergenceTimeItem(self.divTimeId(), list[i]);
                        listUpdate.push(itemUpdate);
                    }
                    service.getNameItemSelected(list).done(function(lstName: Array<model.DivergenceItem>) {
                        self.lstItemSelected(lstName);
                        self.findTimeName(self.divTimeId());
                        if (self.timeItemName() != '') {
                            if ($('#inpName').ntsError("hasError") == true) {
                                $('#inpName').ntsError('clear');
                            }
                        }
                    })
                })
            });
        }
        Registration() {
            var self = this;
            nts.uk.ui.block.invisible();
            $('.nts-input').trigger("validate");
            _.defer(() => {
                if (nts.uk.ui.errors.hasError() === false) {
                    var dfd = $.Deferred();
                    if (self.divTimeId() == null) {
                        return;
                    }
                    var select = new model.SelectSet(self.selectSel(), self.convert(self.checkErrSelect()));
                    var input = new model.SelectSet(self.selectInp(), self.convert(self.checkErrInput()));
                    var divTime = new model.DivergenceTime(self.divTimeId(), 0, self.divTimeName(), self.selectUse(), self.alarmTime(), self.errTime(), select, input);
                    var listAdd = new Array<model.TimeItemSet>();
                    if (self.list() != null) {
                        for (let k = 0; k < self.list().length; k++) {
                            let add = new model.TimeItemSet(self.divTimeId(), self.list()[k]);
                            listAdd.push(add);
                        }
                    }

                    var Object = new model.ObjectDivergence(divTime, listAdd, self.timeItemName());
                    service.updateDivTime(Object).done(function() {
                        self.getAllDivTimeNew();
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                        self.list([]);
                        self.check = false;
                        $("#itemname").focus();
                        nts.uk.ui.block.clear();

                    }).fail(function(error) {
                        nts.uk.ui.block.clear();
                        self.check = true;
                        if (error.messageId == 'Msg_82') {
                            $('#inpAlarmTime').ntsError('set', error);
                        }
                        if (error.messageId == 'Msg_32') {
                            $('#inpDialog').ntsError('set', error);
                        } else {
                            $('#inpName').ntsError(error.message);
                        }
                    })
                    dfd.resolve();
                    return dfd.promise();
                }
            })
            nts.uk.ui.block.clear();
        }
        clearError(): void {
            if ($('.nts-validate').ntsError("hasError") == true) {
                $('.nts-validate').ntsError('clear');
            }
            if ($('.nts-editor').ntsError("hasError") == true) {
                $('.nts-input').ntsError('clear');
            }
        }
        convert(value: boolean): number {
            if (value == true) {
                return 1;
            } else
                if (value == false) {
                    return 0;
                }
        }
        //get all divergence time new
        getAllDivTimeNew() {
            var self = this;
            var dfd = $.Deferred<any>();
            self.dataSource();
            service.getAllDivTime().done(function(lstDivTime: Array<model.DivergenceTime>) {
                self.currentCode('');
                self.dataSource(lstDivTime);
                self.currentCode(self.divTimeId());
                dfd.resolve();
            }).fail(function(error) {
                nts.uk.ui.dialog.alert(error.message);
            })
            dfd.resolve();
            return dfd.promise();
        }
        //ghep ten hien thi 
        findTimeName(divTimeId: number) {
            var self = this;
            self.timeItemName('');
            var strName = '';
            if (self.lstItemSelected().length < 1) {
                self.timeItemName('');
            } else {
                strName = self.lstItemSelected()[0].attendanceItemName;
                if (self.lstItemSelected().length > 1) {
                    for (let j = 1; j < self.lstItemSelected().length; j++) {
                        strName = strName + ' + ' + self.lstItemSelected()[j].attendanceItemName;
                    }
                }
                self.timeItemName(strName);
                strName = '';
            }
        }
    }

    export module model {
        export class TotalTimes {
            totalCountNo: number;
            summaryAtr: KnockoutObservable<number>;
            useAtr: KnockoutObservable<number>;
            totalTimesName: KnockoutObservable<string>;

            constructor(totalCountNo: number, summaryAtr: number, useAtr: number, totalTimesName: string) {
                this.totalCountNo = totalCountNo;
                this.summaryAtr = ko.observable(summaryAtr);
                this.useAtr = ko.observable(useAtr);
                this.totalTimesName = ko.observable(totalTimesName);
            }
        }

        export class TotalTimesDetail {
            totalCountNo: number;
            countAtr: KnockoutObservable<number>;
            useAtr: KnockoutObservable<number>;
            totalTimesName: KnockoutObservable<string>;
            totalTimesABName: KnockoutObservable<string>;
            summaryAtr: KnockoutObservable<number>;
            totalCondition: KnockoutObservable<model.TotalCondition>;
            listTotalSubjects: KnockoutObservableArray<model.TotalSubjects>;
            constructor(totalCountNo: number, countAtr: number, useAtr: number, totalTimesName: string, totalTimesABName: string, summaryAtr: number, totalCondition: model.TotalCondition, listTotalSubjects: Array<model.TotalSubjects>) {
                this.totalCountNo = totalCountNo;
                this.countAtr = ko.observable(countAtr);
                this.useAtr = ko.observable(useAtr);
                this.totalTimesName = ko.observable(totalTimesName);
                this.totalTimesABName = ko.observable(totalTimesABName);
                this.summaryAtr = ko.observable(summaryAtr);
                this.totalCondition = ko.observable(new model.TotalCondition(totalCondition.upperLimitSettingAtr, totalCondition.lowerLimitSettingAtr,
                    totalCondition.thresoldUpperLimit, totalCondition.thresoldLowerLimit));
                this.listTotalSubjects = ko.observableArray(listTotalSubjects);
            }
        }

        export class TotalCondition {
            upperLimitSettingAtr: KnockoutObservable<number>;
            lowerLimitSettingAtr: KnockoutObservable<number>;
            thresoldUpperLimit: KnockoutObservable<number>;
            thresoldLowerLimit: KnockoutObservable<number>;
            constructor(upperLimitSettingAtr: number, lowerLimitSettingAtr: number, thresoldUpperLimit: number, thresoldLowerLimit: number) {
                this.upperLimitSettingAtr = ko.observable(upperLimitSettingAtr);
                this.lowerLimitSettingAtr = ko.observable(lowerLimitSettingAtr);
                this.thresoldUpperLimit = ko.observable(thresoldUpperLimit);
                this.thresoldLowerLimit = ko.observable(thresoldLowerLimit);
            }
        }

        export class TotalSubjects {
            workTypeCode: KnockoutObservable<string>;
            workTypeAtr: KnockoutObservable<number>;
            workTypeInfo: KnockoutObservable<string>;
            workingInfo: KnockoutObservable<string>;
            constructor(workTypeCode: string, workTypeAtr: number) {
                this.workTypeCode = ko.observable(workTypeCode);
                this.workTypeAtr = ko.observable(workTypeAtr);
                if (workTypeAtr === 0) {
                    this.workTypeInfo = ko.observable(workTypeCode);
                } else {
                    this.workingInfo = ko.observable(workTypeCode);
                }
            }
            public setWorkTypeName(workTypeName: string): void {
                this.workTypeInfo(this.workTypeCode() + ' ' + workTypeName);
            }

            public setWorkTimeName(workTimeName: string) {
                this.workingInfo(this.workTypeCode() + ' ' + workTimeName);
            }

        }
















        export class DivergenceTime {
            divTimeId: number;
            attendanceId: number;
            divTimeUseSet: number;
            divTimeName: string;
            alarmTime: number;
            errTime: number;
            selectSet: SelectSet;
            inputSet: SelectSet;
            constructor(divTimeId: number,
                attendanceId: number,
                divTimeName: string,
                divTimeUseSet: number,
                alarmTime: number, errTime: number,
                selectSet: SelectSet,
                inputSet: SelectSet) {
                var self = this;
                self.divTimeId = divTimeId;
                self.divTimeName = divTimeName;
                self.divTimeUseSet = divTimeUseSet;
                self.alarmTime = alarmTime;
                self.errTime = errTime;
                self.selectSet = selectSet;
                self.inputSet = inputSet;
            }
        }
        export class SelectSet {
            selectUseSet: number;
            cancelErrSelReason: number;
            constructor(selectUseSet: number, cancelErrSelReason: number) {
                this.selectUseSet = selectUseSet;
                this.cancelErrSelReason = cancelErrSelReason;
            }
        }
        export class DivergenceTimeItem {
            divTimeId: number;
            attendanceId: number;
            constructor(divTimeId: number, attendanceId: number) {
                this.divTimeId = divTimeId;
                this.attendanceId = attendanceId;
            }
        }
        export class ItemSelected {
            id: number;
            name: string;
            constructor(id: number, name: string) {
                this.id = id;
                this.name = name;
            }
        }
        export class AttendanceType {
            attendanceItemId: number;
        }
        export class DivergenceItem {
            attendanceItemId: number;
            attendanceItemName: string;
            displayNumber: number;
            useAtr: number;
            attendanceAtr: number;
        }
        export class TimeItemSet {
            divTimeId: number;
            attendanceId: number;
            constructor(divTimeId: number, attendanceId: number) {
                this.divTimeId = divTimeId;
                this.attendanceId = attendanceId;
            }
        }
        export class ObjectDivergence {
            divTime: DivergenceTime;
            timeItem: List<TimeItemSet>;
            timeItemName: string;
            constructor(divTime: DivergenceTime, item: List<TimeItemSet>, timeItemName: string) {
                this.divTime = divTime;
                this.timeItem = item;
                this.timeItemName = timeItemName;
            }
        }
    }
}