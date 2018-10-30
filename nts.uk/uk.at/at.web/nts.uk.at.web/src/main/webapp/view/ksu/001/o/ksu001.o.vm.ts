module nts.uk.at.view.ksu001.o.viewmodel {
    import setShare = nts.uk.ui.windows.setShared;
    import getShare = nts.uk.ui.windows.getShared;
    import formatById = nts.uk.time.format.byId;
    import alertError = nts.uk.ui.dialog.alertError;
    import getText = nts.uk.resource.getText;

    export class ScreenModel {
        listWorkType: KnockoutObservableArray<ksu001.common.viewmodel.WorkType> = ko.observableArray([]);
        listWorkTime: KnockoutObservableArray<ksu001.common.viewmodel.WorkTime> = ko.observableArray([]);
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
        listWorkTimeComboBox: KnockoutObservableArray<ksu001.common.viewmodel.WorkTime> = ko.observableArray([]);
        listTimeZoneForSearch: any[] = [];
        startDateScreenA: any = null;
        endDateScreenA: any = null;
        isEnableClearSearchButton: KnockoutObservable<boolean> = ko.observable(false);
        checkStateWorkTypeCode: any = null;
        checkNeededOfWorkTimeSetting: any = null;
        workEmpCombines: any = null;
        employeeIdLogin: string = null;
        isEnableButton: KnockoutObservable<boolean> = ko.observable(false);
        listCheckNeededOfWorkTime : any[] = [];
        nashi: string = getText("KSU001_98");

        constructor() {
            let self = this;
            self.roundingRules = ko.observableArray([
                { code: '1', name: getText("KSU001_71") },
                { code: '2', name: getText("KSU001_72") }
            ]);
            self.selectedRuleCode = ko.observable(1);
            self.itemName = ko.observable('');
            self.currentCode = ko.observable(3);
            self.selectedWorkTypeCode = ko.observable('');
            self.selectedWorkTimeCode = ko.observable('');
            self.time1 = ko.observable('');
            self.time2 = ko.observable('');
            
            self.selectedWorkTypeCode.subscribe((newValue) => {
                let stateWorkTypeCode = _.find(self.checkNeededOfWorkTimeSetting, ['workTypeCode', newValue]);
                // if workTypeCode is not required(= 2) worktime is needless, something relate to workTime will be disable
                if (stateWorkTypeCode && stateWorkTypeCode.state == 2) {
                    self.isEnableButton(false);
                    self.isEnableClearSearchButton(false);
                    self.selectedWorkTimeCode(self.nashi);
                } else {
                    self.isEnableButton(true);
                }
            });

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
                        workTimeCode = _.isEmpty(c.workTimeCode) ? null : c.workTimeCode;
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
            
            self.time1('');
            self.time2('');
            nts.uk.ui.errors.clearAll();
            $('#contain-view').hide();
            $("#extable").exTable("viewMode", "shortName", { y: 150 });
            setShare('listWorkType', self.listWorkType());
            setShare('selectedWorkTypeCode', self.selectedWorkTypeCode);
            setShare('listWorkTime', self.listWorkTime());
            setShare('selectedWorkTimeCode', self.selectedWorkTimeCode);
            setShare('listTimeZoneForSearch', self.listTimeZoneForSearch);
            setShare('listCheckNeededOfWorkTime', self.listCheckNeededOfWorkTime);
            setShare('isEnableButton', self.isEnableButton);
            
            self.currentScreen = nts.uk.ui.windows.sub.modeless("/view/ksu/001/o1/index.xhtml");
            self.currentScreen.onClosed(() => {
                self.currentScreen = null;
                if (__viewContext.viewModel.viewA.selectedModeDisplay() == 1) {
                    $("#extable").exTable("viewMode", "shortName", { y: 210 });
                    $('#contain-view').show();
                    // when close dialog, copy-paste value of nameWorkTimeType of screen O(not O1) for cell
                    $("#extable").exTable("stickData", self.nameWorkTimeType());
                    $("#combo-box1").focus();
                    
                    self.selectedWorkTypeCode(getShare("selectedWorkTypeCode")());
                    self.selectedWorkTimeCode(getShare("selectedWorkTimeCode")());
                }
            });
        }

        /**
         * search workTime
         */
        search(): void {
            let self = this;
            let listWorkTimeSearch: any[] = [];
            let arrTmp: any[] = [];
            self.isEnableClearSearchButton(true);
            if (self.time1() === '' && self.time2() === '') {
                alertError({ messageId: "Msg_53" });
                self.isEnableClearSearchButton(false);
                self.clear();
                return;
            }
            if (!((self.time1() == '' && self.time2() !== '') || (self.time1() !== '' && self.time2() == '')) && self.time1() > self.time2()) {
                alertError({ messageId: "Msg_54" });
                self.clear();
                return;
            }
            if (nts.uk.ui.errors.hasError()) {
                return;
            }
            self.listWorkTimeComboBox([]);
            
            if(self.time2() === ''){
               listWorkTimeSearch = _.filter(self.listTimeZoneForSearch, {'startTime' : self.time1(), 'useAtr' : 1});
            } else if(self.time1() === ''){
                listWorkTimeSearch = _.filter(self.listTimeZoneForSearch, {'endTime' : self.time2(), 'useAtr' : 1});
            } else {
                listWorkTimeSearch = _.filter(self.listTimeZoneForSearch, { 'startTime': self.time1(), 'endTime': self.time2(), 'useAtr': 1});
            }
            
            if (listWorkTimeSearch.length <= 0) {
                return;
            }
            
            _.each(listWorkTimeSearch, (x) => {
                arrTmp.push(_.find(self.listWorkTime(), { 'workTimeCode': x.workTimeCode }));
            });
            
            self.listWorkTimeComboBox(arrTmp);
            
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
            self.time1('');
            self.time2('');
            nts.uk.ui.errors.clearAll();
        }

        /**
         * get data workType-workTime for 2 combo-box and startDate-endDate
         * get startDate, endDate give to A1_1(CCG001) 
         * get startDate, endDate for screen A
         * checkNeededOfWorkTimeSetting(): get list state of workTypeCode relate to need of workTime
         */
        initScreen(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            service.initScreen().done(function(data) {
                self.employeeIdLogin = data.employeeIdLogin;
                //set date for startDate and endDate
                self.startDateScreenA = data.startDate;
                self.endDateScreenA = data.endDate;
                //set data for listWorkType
                self.listWorkType(data.listWorkType);
                self.checkStateWorkTypeCode = data.checkStateWorkTypeCode;
                self.checkNeededOfWorkTimeSetting = data.checkNeededOfWorkTimeSetting;
                self.workEmpCombines = data.workEmpCombines;
                self.selectedWorkTypeCode(self.listWorkType()[0].workTypeCode);
                self.listTimeZoneForSearch = data.listWorkTime;
                //set data for listWorkTime
                //                self.listWorkTime.push(new ksu001.common.viewmodel.WorkTime({
                //                    workTimeCode: '',
                //                    name: getText("KSU001_97"),
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
                // insert item 「なし」 with code = ''
                self.listWorkTime.push(new ksu001.common.viewmodel.WorkTime({
                    workTimeCode: '',
                    name: self.nashi,
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
                // insert item 「個人情報設定」 with code = ''
                //                self.listWorkTime.push(new ksu001.common.viewmodel.WorkTime({
                //                    workTimeCode: '',
                //                    name: getText("KSU001_99"),
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
                        workTimeObj.timeZone1 = formatById("Clock_Short_HM", wT.startTime) + getText("KSU001_66")
                            + formatById("Clock_Short_HM", wT.endTime);
                    } else if (workTimeObj && wT.workNo == 2) {
                        workTimeObj.timeZone2 = wT.useAtr == 1 ? (formatById("Clock_Short_HM", wT.startTime)
                            + getText("KSU001_66") + formatById("Clock_Short_HM", wT.endTime)) : '';
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