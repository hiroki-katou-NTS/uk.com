module ksu001.o.viewmodel {
    import setShare = nts.uk.ui.windows.setShared;

    export class ScreenModel {
        listWorkType: KnockoutObservableArray<WorkType>;
        listWorkTime: KnockoutObservableArray<WorkTime>;
        itemName: KnockoutObservable<string>;
        currentCode: KnockoutObservable<number>
        selectedWorkTypeCode: KnockoutObservable<string>;
        selectedWorkTimeCode: KnockoutObservable<string>;
        time1: KnockoutObservable<string>;
        time2: KnockoutObservable<string>;
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;
        nameWorkTimeType: KnockoutComputed<ExCell>;
        currentScreen: any = null;

        constructor() {
            let self = this;
            self.listWorkType = ko.observableArray([]);
            self.listWorkTime = ko.observableArray([]);

            self.roundingRules = ko.observableArray([
                { code: '1', name: nts.uk.resource.getText("KSU001_71") },
                { code: '2', name: nts.uk.resource.getText("KSU001_72") }
            ]);
            self.selectedRuleCode = ko.observable(1);
            self.itemName = ko.observable('');
            self.currentCode = ko.observable(3);
            self.selectedWorkTypeCode = ko.observable('');
            self.selectedWorkTimeCode = ko.observable('');
            self.time1 = ko.observable('12:00');
            self.time2 = ko.observable('15:00');

            self.findWorkType();
            self.findWorkTime();

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
                        workTimeCode = c.siftCd;
                        startTime = c.start;
                        endTime = c.end;
                    } else {
                        workTimeName = null;
                        workTimeCode = null;
                        startTime = null;
                        endTime = null;
                    }
                }
                return new ExCell({
                    workTypeCode: workTypeCode,
                    workTypeName: workTypeName,
                    workTimeCode: workTimeCode,
                    workTimeName: workTimeName,
                    symbol: null,
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

        /**
         * find data in DB WORK_TYPE
         */
        findWorkType(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            let arrWorkType: WorkType[] = [];
            service.getWorkType().done(function(data: WorkType[]) {
                self.listWorkType(data);
                dfd.resolve();
            }).fail(function() {
                dfd.reject();
            });
            dfd.resolve();
            return dfd.promise();
        }

        /**
         * find data in DB WORK_TIME
         */
        findWorkTime(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            service.getWorkTime().done(function(data: WorkTime[]) {
                // insert item「据え置き」 with code = '000'
                self.listWorkTime.push(new WorkTime({
                    siftCd: '000',
                    name: nts.uk.resource.getText("KSU001_97"),
                    abName: '',
                    symbol: '',
                    dailyWorkAtr: undefined,
                    methodAtr: undefined,
                    displayAtr: undefined,
                    note: null,
                    start: undefined,
                    end: undefined,
                    timeNumberCnt: undefined,
                }));
                // insert item 「なし」 with code = '000'
                self.listWorkTime.push(new WorkTime({
                    siftCd: '000',
                    name: nts.uk.resource.getText("KSU001_98"),
                    abName: '',
                    symbol: '',
                    dailyWorkAtr: undefined,
                    methodAtr: undefined,
                    displayAtr: undefined,
                    note: null,
                    start: undefined,
                    end: undefined,
                    timeNumberCnt: undefined,
                }));
                // insert item 「個人情報設定」 with code = '000'
                self.listWorkTime.push(new WorkTime({
                    siftCd: '000',
                    name: nts.uk.resource.getText("KSU001_99"),
                    abName: '',
                    symbol: '',
                    dailyWorkAtr: undefined,
                    methodAtr: undefined,
                    displayAtr: undefined,
                    note: null,
                    start: undefined,
                    end: undefined,
                    timeNumberCnt: undefined,
                }));

                if (data.length > 0) {
                    _.each(data, function(wT) {
                        let workTimeObj: WorkTime = _.find(self.listWorkTime(), ['siftCd', wT.siftCd]);
                        if (workTimeObj && wT.timeNumberCnt == 1) {
                            workTimeObj.timeZone1 = nts.uk.time.parseTime(wT.start, true).format() + nts.uk.resource.getText("KSU001_66") + nts.uk.time.parseTime(wT.end, true).format();
                        } else if (workTimeObj && wT.timeNumberCnt == 2) {
                            workTimeObj.timeZone2 = nts.uk.time.parseTime(wT.start, true).format() + nts.uk.resource.getText("KSU001_66") + nts.uk.time.parseTime(wT.end, true).format();
                        } else {
                            self.listWorkTime.push(new WorkTime({
                                siftCd: wT.siftCd,
                                name: wT.name,
                                abName: wT.abName,
                                symbol: wT.symbol,
                                dailyWorkAtr: wT.dailyWorkAtr,
                                methodAtr: wT.methodAtr,
                                displayAtr: wT.dailyWorkAtr,
                                note: wT.note,
                                start: wT.start,
                                end: wT.end,
                                timeNumberCnt: wT.timeNumberCnt
                            }));
                        }
                    });
                }
                dfd.resolve();
            }).fail(function() {
                dfd.reject();
            });
            return dfd.promise();
        }
    }

    interface IWorkType {
        workTypeCode: string,
        symbolicName: string,
        name: string,
        abbreviationName: string,
        memo: string,
    }

    class WorkType {
        workTypeCode: string;
        symbolicName: string;
        name: string;
        abbreviationName: string;
        memo: string;

        constructor(params: IWorkType) {
            this.workTypeCode = params.workTypeCode;
            this.symbolicName = params.symbolicName;
            this.name = params.name;
            this.abbreviationName = params.abbreviationName;
            this.memo = params.memo;
        }
    }

    interface IWorkTime {
        siftCd: string,
        name: string,
        abName: string,
        symbol: string,
        dailyWorkAtr: number,
        methodAtr: number,
        displayAtr: number,
        note: string,
        start: number,
        end: number,
        timeNumberCnt: number,
    }

    class WorkTime {
        siftCd: string;
        name: string;
        abName: string;
        symbol: string;
        dailyWorkAtr: number;
        methodAtr: number;
        displayAtr: number;
        note: string;
        codeName: string;
        start: number;
        end: number;
        timeNumberCnt: number;
        timeZone1: string;
        timeZone2: string;

        constructor(params: IWorkTime) {
            this.siftCd = params.siftCd;
            this.name = params.name;
            this.abName = params.abName;
            this.symbol = params.symbol || '';
            this.dailyWorkAtr = params.dailyWorkAtr;
            this.methodAtr = params.methodAtr;
            this.displayAtr = params.displayAtr;
            this.note = params.note || '';
            this.codeName = this.siftCd + this.name;
            this.start = params.start;
            this.end = params.end;
            this.timeNumberCnt = params.timeNumberCnt;
            this.timeZone1 = this.timeNumberCnt == 1 ? nts.uk.time.parseTime(this.start, true).format() + nts.uk.resource.getText("KSU001_66") + nts.uk.time.parseTime(this.end, true).format() : '';
            this.timeZone2 = this.timeNumberCnt == 2 ? nts.uk.time.parseTime(this.start, true).format() + nts.uk.resource.getText("KSU001_66") + nts.uk.time.parseTime(this.end, true).format() : '';
        }
    }

    interface IExCell {
        workTypeCode: string,
        workTypeName: string,
        workTimeCode: string,
        workTimeName: string,
        symbol: string,
        startTime: any,
        endTime: any
    }

    class ExCell {
        workTypeCode: string;
        workTypeName: string;
        workTimeCode: string;
        workTimeName: string;
        symbol: string;
        startTime: any;
        endTime: any;
        constructor(params: IExCell) {
            this.workTypeCode = params.workTypeCode;
            this.workTypeName = params.workTypeName;
            this.workTimeCode = params.workTimeCode;
            this.workTimeName = params.workTimeName;
            this.symbol = params.symbol;
            this.startTime = params.startTime;
            this.endTime = params.endTime;
        }
    }
}