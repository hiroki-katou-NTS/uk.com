module nts.uk.at.view.ksu001.o.viewmodel {
    import setShare = nts.uk.ui.windows.setShared;

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

                    let siftCode: string = null;
                    if (self.selectedWorkTimeCode()) {
                        siftCode = self.selectedWorkTimeCode().slice(0, 3);
                    } else {
                        siftCode = self.selectedWorkTimeCode()
                    }

                    let c = _.find(self.listWorkTime(), ['siftCd', siftCode]);
                    if (c) {
                        workTimeName = c.abName;
                        workTimeCode = (c.siftCd == '000' ? '' : c.siftCd);
                        startTime = c.timeNumberCnt == 1 ? nts.uk.time.parseTime(c.start, true).format() : '';
                        endTime = c.timeNumberCnt == 1 ? nts.uk.time.parseTime(c.end, true).format() : '';
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

            //init
            $.when(self.findDataForComboBox()).done(() => {
                //get state of list workTypeCode
                // get data for screen A
                let lstWorkTypeCode = [];
                _.map(self.listWorkType(), (workType: nts.uk.at.view.ksu001.common.viewmodel.WorkType) => {
                    lstWorkTypeCode.push(workType.workTypeCode);
                });
                __viewContext.viewModel.viewA.checkStateWorkTypeCode(lstWorkTypeCode);
            });
        }

        openDialogO1(): void {
            let self = this;

            $('#contain-view').hide();
            //            $("#extable").exTable("viewMode", "shortName", { y: 115 }); 
            setShare('listWorkType', self.listWorkType());
            setShare('listWorkTime', self.listWorkTime());

            self.currentScreen = nts.uk.ui.windows.sub.modeless("/view/ksu/001/o1/index.xhtml");
            self.currentScreen.onClosed(() => {
                self.currentScreen = null;
                if (__viewContext.viewModel.viewA.selectedModeDisplay() == 1) {
                    //                    $("#extable").exTable("viewMode", "shortName", { y: 100 }); 
                    $('#contain-view').show();
                    //when close dialog, copy-paste value of nameWorkTimeType of screen O(not O1) for cell
                    $("#extable").exTable("stickData", self.nameWorkTimeType());
                }
            });
        }
        
        search(): void {
            let self = this;
            if (!self.time1() && !self.time2()) {
                nts.uk.ui.dialog.alertError({ messageId: "Msg_53" });
            }
            if (self.time1() && self.time2() && moment(self.time1(), 'HH:mm').isSameOrAfter(moment(self.time2(), 'HH:mm'))) {
                nts.uk.ui.dialog.alertError({ messageId: "Msg_54" });
            }
            self.listWorkTimeComboBox([]);
            _.forEach(self.listWorkTime(), (obj) => {
                if (self.time1() && self.time2()
                    && (moment.duration(self.time1()).asMinutes() == obj.start)
                    && (moment.duration(self.time2()).asMinutes() == obj.end)) {
                    self.listWorkTimeComboBox.push(obj);
                } else if (!self.time2() && (moment.duration(self.time1()).asMinutes() <= obj.start)) {
                    self.listWorkTimeComboBox.push(obj);
                } else if (!self.time1() && (moment.duration(self.time2()).asMinutes() >= obj.end)) {
                    self.listWorkTimeComboBox.push(obj);
                }
            });

        }
        
        clear(): void {
            let self = this;
            self.listWorkTimeComboBox([]);
            self.listWorkTimeComboBox(self.listWorkTime());
        }
        
        /**
         * Get data workType-workTime for 2 combo-box
         */
        findDataForComboBox(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            service.getDataForComboBox().done(function(data) {
                //set data for listWorkType
                self.listWorkType(data.listWorkType);
                //set data for listWorkTime
                self.listWorkTime.push(new ksu001.common.viewmodel.WorkTime({
                    siftCd: '000',
                    name: nts.uk.resource.getText("KSU001_97"),
                    abName: '',
                    symbolName: '',
                    dailyWorkAtr: undefined,
                    methodAtr: undefined,
                    displayAtr: undefined,
                    note: null,
                    start: undefined,
                    end: undefined,
                    timeNumberCnt: undefined,
                }));
                // insert item 「なし」 with code = '000'
                self.listWorkTime.push(new ksu001.common.viewmodel.WorkTime({
                    siftCd: '000',
                    name: nts.uk.resource.getText("KSU001_98"),
                    abName: '',
                    symbolName: '',
                    dailyWorkAtr: undefined,
                    methodAtr: undefined,
                    displayAtr: undefined,
                    note: null,
                    start: undefined,
                    end: undefined,
                    timeNumberCnt: undefined,
                }));
                // insert item 「個人情報設定」 with code = '000'
                self.listWorkTime.push(new ksu001.common.viewmodel.WorkTime({
                    siftCd: '000',
                    name: nts.uk.resource.getText("KSU001_99"),
                    abName: '',
                    symbolName: '',
                    dailyWorkAtr: undefined,
                    methodAtr: undefined,
                    displayAtr: undefined,
                    note: null,
                    start: undefined,
                    end: undefined,
                    timeNumberCnt: undefined,
                }));
                _.each(data.listWorkTime, function(wT) {
                    let workTimeObj: ksu001.common.viewmodel.WorkTime = _.find(self.listWorkTime(), ['siftCd', wT.siftCd]);
                    if (workTimeObj && wT.timeNumberCnt == 1) {
                        workTimeObj.timeZone1 = nts.uk.time.parseTime(wT.start, true).format() + nts.uk.resource.getText("KSU001_66") + nts.uk.time.parseTime(wT.end, true).format();
                    } else if (workTimeObj && wT.timeNumberCnt == 2) {
                        workTimeObj.timeZone2 = nts.uk.time.parseTime(wT.start, true).format() + nts.uk.resource.getText("KSU001_66") + nts.uk.time.parseTime(wT.end, true).format();
                    } else {
                        self.listWorkTime.push(new ksu001.common.viewmodel.WorkTime({
                            siftCd: wT.siftCd,
                            name: wT.name,
                            abName: wT.abName,
                            symbolName: wT.symbol,
                            dailyWorkAtr: wT.dailyWorkAtr,
                            methodAtr: wT.methodAtr,
                            displayAtr: wT.displayAtr,
                            note: wT.note,
                            start: wT.start,
                            end: wT.end,
                            timeNumberCnt: wT.timeNumberCnt
                        }));
                    }
                });
                dfd.resolve();
                self.listWorkTimeComboBox(self.listWorkTime());

            }).fail(function() {
                dfd.reject();
            });
            return dfd.promise();
        }
    }
}