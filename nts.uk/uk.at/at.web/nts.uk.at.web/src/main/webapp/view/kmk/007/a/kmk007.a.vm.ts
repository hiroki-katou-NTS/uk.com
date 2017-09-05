module nts.uk.at.view.kmk007.a.viewmodel {
    export class ScreenModel {
        columns: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        currentWorkType: KnockoutObservable<WorkType>;
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;
        itemListOneDay: KnockoutObservableArray<ItemModel>;
        listWorkType: KnockoutObservableArray<any>;
        oneDay: KnockoutObservable<WorkTypeSet>;
        currentOneDayCls: KnockoutObservable<number>;
        currentMorningCls: KnockoutObservable<number>;
        currentAfternoonCls: KnockoutObservable<number>;
        currentOneDay: KnockoutObservable<WorkTypeSet>;
        currentMorning: KnockoutObservable<WorkTypeSet>;
        currentAfternoon: KnockoutObservable<WorkTypeSet>;
        itemHodidayAtr: KnockoutObservableArray<ItemModel>;
        itemCloseAtr: KnockoutObservableArray<ItemModel>;
        itemListHaftDay: KnockoutObservableArray<ItemModel>;
        itemCalculatorMethod: KnockoutObservableArray<ItemModel>;
        enableMethod: KnockoutObservable<boolean>;
        isCreated: KnockoutObservable<boolean>;
        index: KnockoutObservable<number>;


        constructor() {
            var self = this,
                iwork = {
                    workTypeCode: '',
                    workAtr: 0,
                    digestPublicHd: 0,
                    holidayAtr: 0,
                    countHodiday: 0,
                    closeAtr: 0,
                    sumAbsenseNo: 0,
                    sumSpHodidayNo: 0,
                    timeLeaveWork: 0,
                    attendanceTime: 0,
                    genSubHodiday: 0,
                    dayNightTimeAsk: 0
                };

            self.selectedRuleCode = ko.observable(1);
            self.listWorkType = ko.observableArray([]);
            self.oneDay = ko.observable(new WorkTypeSet(iwork));
            self.currentOneDay = ko.observable(new WorkTypeSet(iwork));
            self.currentMorning = ko.observable(new WorkTypeSet(iwork));
            self.currentAfternoon = ko.observable(new WorkTypeSet(iwork));
            self.enableMethod = ko.observable(false);
            self.isCreated = ko.observable(false);
            self.index = ko.observable(0);
            self.currentOneDayCls = ko.observable(0);
            self.currentMorningCls = ko.observable(0);
            self.currentAfternoonCls = ko.observable(0);
            self.currentWorkType = ko.observable(new WorkType({
                workTypeCode: '',
                name: '',
                abbreviationName: '',
                symbolicName: '',
                abolishAtr: 0,
                memo: '',
                workAtr: 0,
                oneDayCls: 0,
                morningCls: 0,
                afternoonCls: 0,
                calculatorMethod: 0,
                oneDay: ko.toJS(self.oneDay),
                morning: ko.toJS(self.oneDay),
                afternoon: ko.toJS(self.oneDay),
            }));


            //1日-勤務種類の分類 
            self.itemListOneDay = ko.observableArray([
                new ItemModel(0, '出勤', 1),
                new ItemModel(1, '休日', 6),
                new ItemModel(2, '年休', 1),
                new ItemModel(3, '積立年休', 1),
                new ItemModel(4, '特別休暇', 2),
                new ItemModel(5, '欠勤', 3),
                new ItemModel(6, '代休', 5),
                new ItemModel(7, '振出', 1),
                new ItemModel(8, '振休', 6),
                new ItemModel(9, '時間消化休暇', 4),
                new ItemModel(10, '連続勤務', 7),
                new ItemModel(11, '休日出勤', 7),
                new ItemModel(12, '休職', 7),
                new ItemModel(13, '休業', 7)
            ]);

            //午前と午後-勤務種類の分類 
            self.itemListHaftDay = ko.observableArray([
                new ItemModel(0, '出勤', 1),
                new ItemModel(1, '休日', 6),
                new ItemModel(2, '年休', 1),
                new ItemModel(3, '積立年休', 1),
                new ItemModel(4, '特別休暇', 2),
                new ItemModel(5, '欠勤', 3),
                new ItemModel(6, '代休', 5),
                new ItemModel(7, '振出', 1),
                new ItemModel(8, '振休', 6),
                new ItemModel(9, '時間消化休暇', 4)
            ]);


            //休日区分
            self.itemHodidayAtr = ko.observableArray([
                new ItemModel(0, '法定内休日', 0),
                new ItemModel(1, '法定外休日', 0),
                new ItemModel(2, '祝日', 0)
            ]);

            //休業区分
            self.itemCloseAtr = ko.observableArray([
                new ItemModel(0, '産前休業', 0),
                new ItemModel(1, '産後休業', 0),
                new ItemModel(2, '育児休業', 0),
                new ItemModel(3, '介護休業', 0)
            ]);

            //出勤率の計算方法
            self.itemCalculatorMethod = ko.observableArray([
                new ItemModel(0, '出勤としない（分子に加算しない）', 0),
                new ItemModel(1, '出勤日とする（分子に加算する）', 0),
                new ItemModel(2, '労働日から除外する（分母から減算）', 0),
                new ItemModel(3, '時間消化休暇', 0),
            ]);

            self.roundingRules = ko.observableArray([
                { code: '0', name: nts.uk.resource.getText('KMK007_19') },
                { code: '1', name: nts.uk.resource.getText('KMK007_20') }
            ]);


            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText('KMK007_7'), key: 'workTypeCode', width: 80, formatter: _.escape },
                { headerText: nts.uk.resource.getText('KMK007_8'), key: 'name', width: 150, formatter: _.escape },
                { headerText: '廃止', key: 'icon', width: 50 }
            ]);

            self.currentCode = ko.observable();

            self.currentWorkType().oneDayCls.subscribe(function(newOneDayCls) {
                self.checkCalculatorMethod(newOneDayCls);

                if (newOneDayCls == self.currentOneDayCls()) {
                    self.setWorkTypeSet(self.currentWorkType().oneDay(), ko.toJS(self.currentOneDay()));
                } else {
                    self.setWorkTypeSet(self.currentWorkType().oneDay(), ko.toJS(self.oneDay));
                }
            });

            self.currentWorkType().morningCls.subscribe(function(newOneDayCls) {

                let afternoonCode = self.currentWorkType().afternoonCls();
                if (self.itemListOneDay()[newOneDayCls].priority < self.itemListOneDay()[afternoonCode].priority) {
                    self.checkCalculatorMethod(newOneDayCls);
                } else {
                    self.checkCalculatorMethod(afternoonCode);
                }

                if (newOneDayCls == self.currentMorningCls()) {
                    self.setWorkTypeSet(self.currentWorkType().morning(), ko.toJS(self.currentMorning));
                } else {
                    self.setWorkTypeSet(self.currentWorkType().morning(), ko.toJS(self.oneDay));
                }
            });

            self.currentWorkType().afternoonCls.subscribe(function(newOneDayCls) {
                let morningCode = self.currentWorkType().morningCls();
                if (self.itemListOneDay()[newOneDayCls].priority < self.itemListOneDay()[morningCode].priority) {
                    self.checkCalculatorMethod(newOneDayCls);
                } else {
                    self.checkCalculatorMethod(morningCode);
                }

                if (newOneDayCls == self.currentAfternoonCls()) {
                    self.setWorkTypeSet(self.currentWorkType().afternoon(), ko.toJS(self.currentAfternoon));
                } else {
                    self.setWorkTypeSet(self.currentWorkType().afternoon(), ko.toJS(self.oneDay));
                }
            });

            self.currentCode.subscribe(function(newValue) {
                if (newValue) {
                    self.isCreated(false);
                    var index = _.findIndex(self.listWorkType(), function(item: IWorkType) {
                        return item.workTypeCode == newValue;
                    });
                    self.index(index);

                    var itemWorkType = _.find(self.listWorkType(), function(item: IWorkType) {
                        return item.workTypeCode == newValue;
                    });

                    //set current worktypeset
                    self.currentOneDay(itemWorkType.oneDay);
                    self.currentMorning(itemWorkType.morning);
                    self.currentAfternoon(itemWorkType.afternoon);

                    //set current code 
                    self.currentOneDayCls(itemWorkType.oneDayCls);
                    self.currentMorningCls(itemWorkType.morningCls);
                    self.currentAfternoonCls(itemWorkType.afternoonCls);


                    self.currentWorkType().workTypeCode(itemWorkType.workTypeCode);
                    self.currentWorkType().name(itemWorkType.name);
                    self.currentWorkType().abbreviationName(itemWorkType.abbreviationName);
                    self.currentWorkType().symbolicName(itemWorkType.symbolicName);
                    self.currentWorkType().abolishAtr(itemWorkType.abolishAtr);
                    self.currentWorkType().memo(itemWorkType.memo);
                    self.currentWorkType().workAtr(itemWorkType.workAtr);
                    self.currentWorkType().oneDayCls(itemWorkType.oneDayCls);
                    self.currentWorkType().morningCls(itemWorkType.morningCls);
                    self.currentWorkType().afternoonCls(itemWorkType.afternoonCls);
                    self.currentWorkType().calculatorMethod(itemWorkType.calculatorMethod);

                    self.setWorkTypeSet(self.currentWorkType().oneDay(), itemWorkType.oneDay);
                    self.setWorkTypeSet(self.currentWorkType().morning(), itemWorkType.morning);
                    self.setWorkTypeSet(self.currentWorkType().afternoon(), itemWorkType.afternoon);

                } else {
                    self.isCreated(true);
                }

            });
        }


        startPage(): JQueryPromise<any> {
            var self = this;
            // switch language
            $("#switch-language").ntsSwitchMasterLanguage();
            $("#switch-language").on("selectionChanged", function(event, arg1, arg2) {
                alert(event.detail.languageId);
            });
            var dfd = $.Deferred();
            self.getWorkType().done(function() {
                if (self.listWorkType().length > 0) {
                    self.currentCode(self.listWorkType()[0].workTypeCode);
                }
                else {
                    self.cleanForm();
                }
                dfd.resolve();
            });
            return dfd.promise();
        }


        private setWorkTypeSet(worktypeset: WorkTypeSet, itemWorkType: IWorkTypeSet): void {

            worktypeset.workTypeCode(itemWorkType.workTypeCode);
            worktypeset.attendanceTime(itemWorkType.attendanceTime);
            worktypeset.closeAtr(itemWorkType.closeAtr);
            worktypeset.countHodiday(itemWorkType.countHodiday);
            worktypeset.dayNightTimeAsk(itemWorkType.dayNightTimeAsk);
            worktypeset.digestPublicHd(itemWorkType.digestPublicHd);
            worktypeset.genSubHodiday(itemWorkType.genSubHodiday);
            worktypeset.holidayAtr(itemWorkType.holidayAtr);
            worktypeset.sumAbsenseNo(itemWorkType.sumAbsenseNo);
            worktypeset.sumSpHodidayNo(itemWorkType.sumSpHodidayNo);
            worktypeset.timeLeaveWork(itemWorkType.timeLeaveWork);
            worktypeset.workAtr(itemWorkType.workAtr);
        }

        private openDiablogC() {
            var self = this;

            nts.uk.ui.windows.setShared("KMK007_WORK_TYPES", self.listWorkType());

            nts.uk.ui.windows.sub.modal("/view/kmk/007/c/index.xhtml").onClosed(() => {

            });
        }

        private addWorkType(): any {
            var self = this,
                workType = self.currentWorkType(),
                length = workType.workTypeCode().length,
                worktypeCode = workType.workTypeCode();

            if (length < 3) {
                if (length == 1) {
                    workType.workTypeCode('00' + worktypeCode);
                } else {
                    workType.workTypeCode('0' + worktypeCode);
                }
            }
            if (workType.workAtr() == WorkAtr.ONE_DAY) {
                workType.morningCls(0);
                workType.afternoonCls(0);
            } else if (workType.workAtr() == WorkAtr.MORNING) {
                workType.oneDayCls(0);
            }
            workType.oneDay().workTypeCode(workType.workTypeCode());
            workType.morning().workTypeCode(workType.workTypeCode());
            workType.afternoon().workTypeCode(workType.workTypeCode());
            let command: any = ko.toJS(workType);
            command.abolishAtr = Number(command.abolishAtr);
            self.changeBooleanToNumber(command.oneDay);
            self.changeBooleanToNumber(command.morning);
            self.changeBooleanToNumber(command.afternoon);

            service.addWorkType(self.isCreated(), command).done(function() {
                nts.uk.ui.dialog.info(nts.uk.resource.getMessage('Msg_15'));
                self.isCreated(false);
                self.getWorkType().done(function() {

                });
            }).fail(function(error) {
                nts.uk.ui.dialog.alertError(error.message);
            });
        }

        private changeBooleanToNumber(workTypeSet: IWorkTypeSet): void {
            workTypeSet.digestPublicHd = Number(workTypeSet.digestPublicHd);
            workTypeSet.attendanceTime = Number(workTypeSet.attendanceTime);
            workTypeSet.countHodiday = Number(workTypeSet.countHodiday);
            workTypeSet.dayNightTimeAsk = Number(workTypeSet.dayNightTimeAsk);
            workTypeSet.genSubHodiday = Number(workTypeSet.genSubHodiday);
            workTypeSet.timeLeaveWork = Number(workTypeSet.timeLeaveWork);
        }

        private removeWorkType(): any {
            let self = this;
            let workTypeCd = self.currentCode();
            nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                service.deleteWorkType(workTypeCd).done(function() {
                    nts.uk.ui.dialog.info(nts.uk.resource.getMessage('Msg_16'));
                    self.getWorkType().done(function() {
                        if (self.listWorkType().length == 0) {
                            self.cleanForm();
                        } else if (self.index() == self.listWorkType().length) {
                            self.currentCode(self.listWorkType()[self.index() - 1].workTypeCode);
                        } else {
                            self.currentCode(self.listWorkType()[self.index()].workTypeCode);
                        }
                    });
                }).fail(function(error) {
                    self.isCreated(false);
                    nts.uk.ui.dialog.alertError(error.message);
                });;
            });
        }

        private checkCalculatorMethod(workTypeSetCode: number): void {
            let self = this;
            if (workTypeSetCode == 1 || workTypeSetCode == 8 || workTypeSetCode == 12 || workTypeSetCode == 13) {
                self.currentWorkType().calculatorMethod(0);
                self.enableMethod(false);
            } else if (workTypeSetCode == 0 || workTypeSetCode == 2 || workTypeSetCode == 3 || workTypeSetCode == 7 || workTypeSetCode == 10) {
                self.currentWorkType().calculatorMethod(1);
                self.enableMethod(false);
            } else if (workTypeSetCode == 6) {
                self.currentWorkType().calculatorMethod(2);
                self.enableMethod(false);
            } else if (workTypeSetCode == 9) {
                self.currentWorkType().calculatorMethod(3);
                self.enableMethod(false);
            } else if (workTypeSetCode == 4) {
                self.currentWorkType().calculatorMethod(1);
                self.enableMethod(true);
            } else {
                self.currentWorkType().calculatorMethod(0);
                self.enableMethod(true);
            }
        }

        private cleanForm(): void {
            var self = this;
            self.currentWorkType(new WorkType({
                workTypeCode: '',
                name: '',
                abbreviationName: '',
                symbolicName: '',
                abolishAtr: 0,
                memo: '',
                workAtr: 0,
                oneDayCls: 0,
                morningCls: 0,
                afternoonCls: 0,
                calculatorMethod: 0,
                oneDay: ko.toJS(self.oneDay),
                morning: ko.toJS(self.oneDay),
                afternoon: ko.toJS(self.oneDay)
            }));
            self.currentCode("");
            if (self.listWorkType().length > 0) {
                nts.uk.ui.errors.clearAll();
            }
        }

        private getWorkType(): any {
            var self = this;
            var dfd = $.Deferred();
            service.loadWorkType().done(function(data) {
                if (data.length != 0) {
                    self.listWorkType.removeAll();
                    _.forEach(data, function(item) {
                        var workType = new WorkType({
                            workTypeCode: item.workTypeCode,
                            name: item.name,
                            abbreviationName: item.abbreviationName,
                            symbolicName: item.symbolicName,
                            abolishAtr: item.abolishAtr,
                            memo: item.memo,
                            workAtr: item.workAtr,
                            oneDayCls: item.oneDayCls,
                            morningCls: item.morningCls,
                            afternoonCls: item.afternoonCls,
                            calculatorMethod: item.calculatorMethod,
                            oneDay: ko.toJS(self.oneDay),
                            morning: ko.toJS(self.oneDay),
                            afternoon: ko.toJS(self.oneDay),
                            dispOrder: item.dispOrder
                        });

                        // one day
                        if (item.workTypeSets.length > 0) {
                            //var wtSetRes = item.workTypeSets[0];
                            _.forEach(item.workTypeSets, function(itemWorkTypeSet) {
                                if (itemWorkTypeSet.workAtr == WorkAtr.ONE_DAY) {
                                    var workTypeSet = new WorkTypeSet({
                                        workTypeCode: item.workTypeCode,
                                        workAtr: itemWorkTypeSet.workAtr,
                                        digestPublicHd: itemWorkTypeSet.digestPublicHd,
                                        holidayAtr: itemWorkTypeSet.holidayAtr,
                                        countHodiday: itemWorkTypeSet.countHodiday,
                                        closeAtr: itemWorkTypeSet.closeAtr,
                                        sumAbsenseNo: itemWorkTypeSet.sumAbsenseNo,
                                        sumSpHodidayNo: itemWorkTypeSet.sumSpHodidayNo,
                                        timeLeaveWork: itemWorkTypeSet.timeLeaveWork,
                                        attendanceTime: itemWorkTypeSet.attendanceTime,
                                        genSubHodiday: itemWorkTypeSet.genSubHodiday,
                                        dayNightTimeAsk: itemWorkTypeSet.dayNightTimeAsk
                                    })
                                    workType.oneDay(workTypeSet);
                                } else if (itemWorkTypeSet.workAtr == WorkAtr.MORNING) {
                                    var workTypeSet = new WorkTypeSet({
                                        workTypeCode: item.workTypeCode,
                                        workAtr: itemWorkTypeSet.workAtr,
                                        digestPublicHd: itemWorkTypeSet.digestPublicHd,
                                        holidayAtr: itemWorkTypeSet.holidayAtr,
                                        countHodiday: itemWorkTypeSet.countHodiday,
                                        closeAtr: itemWorkTypeSet.closeAtr,
                                        sumAbsenseNo: itemWorkTypeSet.sumAbsenseNo,
                                        sumSpHodidayNo: itemWorkTypeSet.sumSpHodidayNo,
                                        timeLeaveWork: itemWorkTypeSet.timeLeaveWork,
                                        attendanceTime: itemWorkTypeSet.attendanceTime,
                                        genSubHodiday: itemWorkTypeSet.genSubHodiday,
                                        dayNightTimeAsk: itemWorkTypeSet.dayNightTimeAsk
                                    })
                                    workType.morning(workTypeSet);
                                } else if (itemWorkTypeSet.workAtr == WorkAtr.AFTERNOON) {
                                    var workTypeSet = new WorkTypeSet({
                                        workTypeCode: item.workTypeCode,
                                        workAtr: itemWorkTypeSet.workAtr,
                                        digestPublicHd: itemWorkTypeSet.digestPublicHd,
                                        holidayAtr: itemWorkTypeSet.holidayAtr,
                                        countHodiday: itemWorkTypeSet.countHodiday,
                                        closeAtr: itemWorkTypeSet.closeAtr,
                                        sumAbsenseNo: itemWorkTypeSet.sumAbsenseNo,
                                        sumSpHodidayNo: itemWorkTypeSet.sumSpHodidayNo,
                                        timeLeaveWork: itemWorkTypeSet.timeLeaveWork,
                                        attendanceTime: itemWorkTypeSet.attendanceTime,
                                        genSubHodiday: itemWorkTypeSet.genSubHodiday,
                                        dayNightTimeAsk: itemWorkTypeSet.dayNightTimeAsk
                                    })
                                    workType.afternoon(workTypeSet);
                                }
                            });
                        }
                        self.listWorkType.push(ko.toJS(workType));
                    });
                    self.listWorkType(_.orderBy(self.listWorkType(), ['dispOrder', 'workTypeCode'], ['asc', 'asc']));
                } else {
                    
                }
                dfd.resolve();
            }).fail((res) => { });
            return dfd.promise();
        }
    }

    export enum WorkAtr {
        ONE_DAY = 0,
        MORNING = 1,
        AFTERNOON = 2
    }

    export class ItemWorkTypeModel {
        workTypeCode: string;
        workTypeName: string;
        defaultMenu: number;
        icon: string;
        constructor(workTypeCode: string, workTypeName: string, defaultMenu: number) {
            this.workTypeCode = workTypeCode;
            this.workTypeName = workTypeName;
            this.defaultMenu = defaultMenu;
            if (defaultMenu == 0) {
                this.icon = "";
            } else {
                this.icon = '<i class="icon icon-dot"></i>';
            }
        }
    }

    class ItemModel {
        code: number;
        name: string;
        priority: number;
        constructor(code: number, name: string, priority: number) {
            this.code = code;
            this.name = name;
            this.priority = priority;
        }
    }

    export interface IWorkType {
        workTypeCode: string;
        name: string;
        abbreviationName: string;
        symbolicName: string;
        abolishAtr: number;
        memo: string;
        workAtr: number;
        oneDayCls: number;
        morningCls: number;
        afternoonCls: number;
        calculatorMethod: number;
        oneDay?: IWorkTypeSet;
        morning?: IWorkTypeSet;
        afternoon?: IWorkTypeSet;
        dispOrder?: number;
    }

    export class WorkType {
        workTypeCode: KnockoutObservable<string>;
        name: KnockoutObservable<string>;
        abbreviationName: KnockoutObservable<string>;
        symbolicName: KnockoutObservable<string>;
        abolishAtr: KnockoutObservable<any>;
        memo: KnockoutObservable<string>;
        workAtr: KnockoutObservable<number>;
        oneDayCls: KnockoutObservable<number>;
        morningCls: KnockoutObservable<number>;
        afternoonCls: KnockoutObservable<number>;
        calculatorMethod: KnockoutObservable<number>;
        icon: string;
        oneDay: KnockoutObservable<WorkTypeSet>;
        morning: KnockoutObservable<WorkTypeSet>;
        afternoon: KnockoutObservable<WorkTypeSet>;
        dispOrder: KnockoutObservable<number>;

        constructor(param: IWorkType) {
            this.workTypeCode = ko.observable(param.workTypeCode || '');
            this.name = ko.observable(param.name);
            this.abbreviationName = ko.observable(param.abbreviationName);
            this.symbolicName = ko.observable(param.symbolicName);
            this.abolishAtr = ko.observable(!!param.abolishAtr);
            this.memo = ko.observable(param.memo);
            this.workAtr = ko.observable(param.workAtr);
            this.oneDayCls = ko.observable(param.oneDayCls);
            this.morningCls = ko.observable(param.morningCls);
            this.afternoonCls = ko.observable(param.afternoonCls);
            this.calculatorMethod = ko.observable(param.calculatorMethod);
            this.oneDay = ko.observable(new WorkTypeSet(param.oneDay));
            this.morning = ko.observable(new WorkTypeSet(param.morning));
            this.afternoon = ko.observable(new WorkTypeSet(param.afternoon));
            if (param.dispOrder != 0) {
                this.dispOrder = ko.observable(param.dispOrder);
            } else {
                this.dispOrder = ko.observable(null);
            }
            if (param.abolishAtr == 0) {
                this.icon = "";
            } else {
                this.icon = '<i class="icon icon-dot"></i>';
            }
        }
    }

    export interface IWorkTypeSet {
        workTypeCode?: string;
        workAtr?: number;
        digestPublicHd?: number;
        holidayAtr?: number;
        countHodiday?: number;
        closeAtr?: number;
        sumAbsenseNo?: number;
        sumSpHodidayNo?: number;
        timeLeaveWork?: number;
        attendanceTime?: number;
        genSubHodiday?: number;
        dayNightTimeAsk?: number;
    }

    export class WorkTypeSet {
        workTypeCode: KnockoutObservable<string>;
        workAtr: KnockoutObservable<any>;
        digestPublicHd: KnockoutObservable<any>;
        holidayAtr: KnockoutObservable<any>;
        countHodiday: KnockoutObservable<any>;
        closeAtr: KnockoutObservable<any>;
        sumAbsenseNo: KnockoutObservable<any>;
        sumSpHodidayNo: KnockoutObservable<any>;
        timeLeaveWork: KnockoutObservable<any>;
        attendanceTime: KnockoutObservable<any>;
        genSubHodiday: KnockoutObservable<any>;
        dayNightTimeAsk: KnockoutObservable<any>;

        constructor(param: IWorkTypeSet) {
            if (param) {
                this.workTypeCode = ko.observable(param.workTypeCode || '');
                this.workAtr = ko.observable(param.workAtr);
                this.digestPublicHd = ko.observable(!!param.digestPublicHd);
                this.holidayAtr = ko.observable(param.holidayAtr);
                this.countHodiday = ko.observable(!!param.countHodiday);
                this.closeAtr = ko.observable(param.closeAtr);
                this.sumAbsenseNo = ko.observable(param.sumAbsenseNo);
                this.sumSpHodidayNo = ko.observable(param.sumSpHodidayNo);
                this.timeLeaveWork = ko.observable(!!param.timeLeaveWork);
                this.attendanceTime = ko.observable(!!param.attendanceTime);
                this.genSubHodiday = ko.observable(!!param.genSubHodiday);
                this.dayNightTimeAsk = ko.observable(!!param.dayNightTimeAsk);
            }
        }
    }
}