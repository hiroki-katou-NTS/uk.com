module nts.uk.at.view.ksu001.o.viewmodel {
    import setShare = nts.uk.ui.windows.setShared;
    import formatById = nts.uk.time.format.byId;

    export class ScreenModel {
        listWorkType: KnockoutObservableArray<ksu001.common.viewmodel.WorkType>;
        listWorkTime: KnockoutObservableArray<ksu001.common.viewmodel.WorkTime>;
        itemName: KnockoutObservable<string>;
        currentCode: KnockoutObservable<number>
        selectedWorkTypeCode: KnockoutObservable<string>;
        selectedWorkTimeCode: KnockoutObservable<string>;
        time1: KnockoutObservable<string>;
        time2: KnockoutObservable<string>;
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;
        nameWorkTimeType: KnockoutComputed<ksu001.common.viewmodel.ExCell>;
        currentScreen: any = null;
        listWorkTimeComboBox: KnockoutObservableArray<ksu001.common.viewmodel.WorkTime>;
        startDateScreenA: any = null;
        endDateScreenA: any = null;
        isEnableClearSearchButton: KnockoutObservable<boolean> = ko.observable(false);
        checkStateWorkTypeCode: any = null;
        checkNeededOfWorkTimeSetting: any = null;
        workEmpCombines: any = null;
        employeeIdLogin: string = null;

        constructor() {
            let self = this;
            self.listWorkType = ko.observableArray([]);
            self.listWorkTime = ko.observableArray([]);
            self.listWorkTimeComboBox = ko.observableArray([]);
            self.roundingRules = ko.observableArray([
                { code: '1', name: nts.uk.resource.getText("KSU001_71") },
                { code: '2', name: nts.uk.resource.getText("KSU001_72") }
            ]);
            self.selectedRuleCode = ko.observable(1);
            self.itemName = ko.observable('');
            self.currentCode = ko.observable(3);
            self.selectedWorkTypeCode = ko.observable('');
            self.selectedWorkTimeCode = ko.observable('');
            self.time1 = ko.observable('');
            self.time2 = ko.observable('');

            //get name of workType and workTime
            self.nameWorkTimeType = ko.pureComputed(() => {
                let workTypeName, workTypeCode, workTimeName, workTimeCode: string;
                let startTime, endTime: any;
                if (self.listWorkType().length > 0 || self.listWorkTime().length > 0) {
                    let d = _.find(self.listWorkType(), ['workTypeCode', self.selectedWorkTypeCode()]);
                    if (d) {
                        workTypeName = d.abbreviationName;
                        workTypeCode = d.workTypeCode;
                    } else {
                        workTypeName = null;
                        workTypeCode = null;
                    }

                    let workTimeCd: string = null;
                    if (self.selectedWorkTimeCode()) {
                        workTimeCd = self.selectedWorkTimeCode().slice(0, 3);
                    } else {
                        workTimeCd = self.selectedWorkTimeCode()
                    }

                    let c = _.find(self.listWorkTime(), ['workTimeCode', workTimeCd]);
                    if (c) {
                        workTimeName = c.abName;
                        workTimeCode = (c.workTimeCode == '000') ? null : c.workTimeCode;
                        //                        startTime = nts.uk.time.parseTime(c.startTime, true).format();
                        //                        endTime = nts.uk.time.parseTime(c.endTime, true).format();
                        startTime = c.startTime ? formatById("Clock_Short_HM", c.startTime) : '';
                        endTime = c.endTime ? formatById("Clock_Short_HM", c.endTime) : '';
                    } else {
                        workTimeName = null;
                        workTimeCode = null;
                        startTime = '';
                        endTime = '';
                    }
                }
                return new ksu001.common.viewmodel.ExCell({
                    workTypeCode: workTypeCode,
                    workTypeName: workTypeName,
                    workTimeCode: workTimeCode,
                    workTimeName: workTimeName,
                    startTime: startTime,
                    endTime: endTime
                });
            });

            self.nameWorkTimeType.subscribe(function(value) {
                //Paste data into cell (set-sticker-single)
                $("#extable").exTable("stickData", value);
            });
        }

        openDialogO1(): void {
            let self = this;

            $('#contain-view').hide();
            $("#extable").exTable("viewMode", "shortName", { y: 150 });
            setShare('listWorkType', self.listWorkType());
            setShare('listWorkTime', self.listWorkTime());

            self.currentScreen = nts.uk.ui.windows.sub.modeless("/view/ksu/001/o1/index.xhtml");
            self.currentScreen.onClosed(() => {
                self.currentScreen = null;
                if (__viewContext.viewModel.viewA.selectedModeDisplay() == 1) {
                    $("#extable").exTable("viewMode", "shortName", { y: 210 });
                    $('#contain-view').show();
                    // when close dialog, copy-paste value of nameWorkTimeType of screen O(not O1) for cell
                    $("#extable").exTable("stickData", self.nameWorkTimeType());
                    $("#combo-box1").focus();
                }
            });
        }

        /**
         * search workTime
         */
        search(): void {
            let self = this;
            self.isEnableClearSearchButton(true);
            if (!self.time1() && !self.time2()) {
                nts.uk.ui.dialog.alertError({ messageId: "Msg_53" });
                self.isEnableClearSearchButton(false);
                self.clear();
                return;
            }
            if (self.time1() && self.time2() && moment(self.time1(), 'HH:mm').isSameOrAfter(moment(self.time2(), 'HH:mm'))) {
                nts.uk.ui.dialog.alertError({ messageId: "Msg_54" });
                self.clear();
                return;
            }
            if (nts.uk.ui.errors.hasError()) {
                return;
            }
            self.listWorkTimeComboBox([]);
            _.forEach(self.listWorkTime(), (obj) => {
                let timezone1 = obj.timeZone1.split("～");
                let timezone2 = obj.timeZone2.split("～");
                if (self.time1() && self.time2()) {
                    if (timezone1.length == 2) {
                        if (moment.duration(self.time1()).asMinutes() == moment.duration(timezone1[0]).asMinutes()
                            && moment.duration(self.time2()).asMinutes() == moment.duration(timezone1[1]).asMinutes()) {
                            self.listWorkTimeComboBox.push(obj);
                        }
                    }
                    if (timezone2.length == 2) {
                        if (moment.duration(self.time1()).asMinutes() == moment.duration(timezone2[0]).asMinutes()
                            && moment.duration(self.time2()).asMinutes() == moment.duration(timezone2[1]).asMinutes()) {
                            self.listWorkTimeComboBox.push(obj);
                        }
                    }

                } else if (!self.time2()) {
                    if (timezone1.length == 2) {
                        if (moment.duration(self.time1()).asMinutes() == moment.duration(timezone1[0]).asMinutes()) {
                            self.listWorkTimeComboBox.push(obj);
                        }
                    }
                    if (timezone2.length == 2) {
                        if (moment.duration(self.time1()).asMinutes() == moment.duration(timezone2[0]).asMinutes()) {
                            self.listWorkTimeComboBox.push(obj);
                        }
                    }

                } else if (!self.time1()) {
                    if (timezone1.length == 2) {
                        if (moment.duration(self.time2()).asMinutes() == moment.duration(timezone1[1]).asMinutes()) {
                            self.listWorkTimeComboBox.push(obj);
                        }
                    }
                    if (timezone2.length == 2) {
                        if (moment.duration(self.time2()).asMinutes() == moment.duration(timezone2[1]).asMinutes()) {
                            self.listWorkTimeComboBox.push(obj);
                        }
                    }
                }
            });

            $('#combo-box2').focus();
        }

        /**
         * clear search time
         */
        clear(): void {
            let self = this;
            self.isEnableClearSearchButton(false);
            self.listWorkTimeComboBox([]);
            self.listWorkTimeComboBox(self.listWorkTime());
        }

        /**
         * get data workType-workTime for 2 combo-box and startDate-endDate
         * get startDate, endDate give to A1_1(CCG001) 
         * becasue CCG001 is not available startDate and endDate
         * so get startDate, endDate for screen A
         * checkNeededOfWorkTimeSetting(): get list state of workTypeCode relate to need of workTime
         */
        getWorkTypeTimeAndStartEndDate(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            service.getWorkTypeTimeAndStartEndDate().done(function(data) {
                self.employeeIdLogin = data.employeeIdLogin;
                //set date for startDate and endDate
                self.startDateScreenA = data.startDate;
                self.endDateScreenA = data.endDate;
                //set data for listWorkType
                self.listWorkType(data.listWorkType);
                self.selectedWorkTypeCode(self.listWorkType()[0].workTypeCode),
                    self.checkStateWorkTypeCode = data.checkStateWorkTypeCode;
                self.checkNeededOfWorkTimeSetting = data.checkNeededOfWorkTimeSetting;
                self.workEmpCombines = data.workEmpCombines;
                //set data for listWorkTime
                //                self.listWorkTime.push(new ksu001.common.viewmodel.WorkTime({
                //                    workTimeCode: '000',
                //                    name: nts.uk.resource.getText("KSU001_97"),
                //                    abName: '',
                //                    symbolName: '',
                //                    dailyWorkAtr: undefined,
                //                    worktimeSetMethod: undefined,
                //                    abolitionAtr: undefined,
                //                    color: null,
                //                    note: null,
                //                    startTime: undefined,
                //                    endTime: undefined,
                //                    workNo: undefined,
                //                    useAtr: undefined
                //                }));
                // insert item 「なし」 with code = '000'
                self.listWorkTime.push(new ksu001.common.viewmodel.WorkTime({
                    workTimeCode: '000',
                    name: nts.uk.resource.getText("KSU001_98"),
                    abName: '',
                    symbolName: '',
                    dailyWorkAtr: undefined,
                    worktimeSetMethod: undefined,
                    abolitionAtr: undefined,
                    color: null,
                    note: null,
                    startTime: undefined,
                    endTime: undefined,
                    workNo: undefined,
                    useAtr: undefined
                }));
                // insert item 「個人情報設定」 with code = '000'
                //                self.listWorkTime.push(new ksu001.common.viewmodel.WorkTime({
                //                    workTimeCode: '000',
                //                    name: nts.uk.resource.getText("KSU001_99"),
                //                    abName: '',
                //                    symbolName: '',
                //                    dailyWorkAtr: undefined,
                //                    worktimeSetMethod: undefined,
                //                    abolitionAtr: undefined,
                //                    color: null,
                //                    note: null,
                //                    startTime: undefined,
                //                    endTime: undefined,
                //                    workNo: undefined,
                //                    useAtr: undefined
                //                }));
                _.each(data.listWorkTime, function(wT) {
                    let workTimeObj: ksu001.common.viewmodel.WorkTime = _.find(self.listWorkTime(), ['workTimeCode', wT.workTimeCode]);
                    if (workTimeObj && wT.workNo == 1) {
                        //                        workTimeObj.timeZone1 = nts.uk.time.parseTime(wT.startTime, true).format() + nts.uk.resource.getText("KSU001_66")
                        //                            + nts.uk.time.parseTime(wT.endTime, true).format();
                        workTimeObj.timeZone1 = formatById("Clock_Short_HM", wT.startTime) + nts.uk.resource.getText("KSU001_66")
                            + formatById("Clock_Short_HM", wT.endTime);
                    } else if (workTimeObj && wT.workNo == 2) {
                        //                        workTimeObj.timeZone2 = wT.useAtr == 1 ? (nts.uk.time.parseTime(wT.startTime, true).format()
                        //                            + nts.uk.resource.getText("KSU001_66") + nts.uk.time.parseTime(wT.endTime, true).format()) : '';
                        workTimeObj.timeZone2 = wT.useAtr == 1 ? (formatById("Clock_Short_HM", wT.startTime)
                            + nts.uk.resource.getText("KSU001_66") + formatById("Clock_Short_HM", wT.endTime)) : '';
                    } else {
                        self.listWorkTime.push(new ksu001.common.viewmodel.WorkTime({
                            workTimeCode: wT.workTimeCode,
                            name: wT.name,
                            abName: wT.abName,
                            symbolName: wT.symbol,
                            dailyWorkAtr: wT.dailyWorkAtr,
                            worktimeSetMethod: wT.worktimeSetMethod,
                            abolitionAtr: wT.abolitionAtr,
                            color: wT.color,
                            note: wT.note,
                            startTime: wT.startTime,
                            endTime: wT.endTime,
                            workNo: wT.workNo,
                            useAtr: wT.useAtr
                        }));
                    }
                });
                dfd.resolve();
                self.listWorkTimeComboBox(self.listWorkTime());
                self.selectedWorkTimeCode(self.listWorkTimeComboBox()[0].codeName);
            }).fail(function() {
                dfd.reject();
            });
            return dfd.promise();
        }
    }
}