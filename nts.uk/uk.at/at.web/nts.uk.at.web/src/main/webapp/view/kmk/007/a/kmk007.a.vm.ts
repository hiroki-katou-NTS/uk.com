module nts.uk.at.view.kmk007.a.viewmodel {
    export class ScreenModel {
        columns: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        currentWorkType: KnockoutObservable<WorkType>;
        enable: KnockoutObservable<boolean>;
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;
        itemListOneDay: KnockoutObservableArray<ItemModel>;
        isEnable: KnockoutObservable<boolean>;
        isEditable: KnockoutObservable<boolean>;
        listWorkType: KnockoutObservableArray<any>;
        oneDay: KnockoutObservable<WorkTypeSet>;
        morning: KnockoutObservable<WorkTypeSet>;
        afternoon: KnockoutObservable<WorkTypeSet>;
        itemHodidayAtr: KnockoutObservableArray<ItemModel>;
        itemCloseAtr: KnockoutObservableArray<ItemModel>;
        itemListHaftDay: KnockoutObservableArray<ItemModel>;


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

            self.enable = ko.observable(false);
            self.selectedRuleCode = ko.observable(1);
            self.listWorkType = ko.observableArray([]);
            self.oneDay = ko.observable(new WorkTypeSet(iwork));
            self.morning = ko.observable(new WorkTypeSet(iwork));
            self.afternoon = ko.observable(new WorkTypeSet(iwork));

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
                afternoon: ko.toJS(self.oneDay)
            }));


            //1日-勤務種類の分類 
            self.itemListOneDay = ko.observableArray([
                new ItemModel(0, '出勤'),
                new ItemModel(1, '休日'),
                new ItemModel(2, '年休'),
                new ItemModel(3, '積立年休'),
                new ItemModel(4, '特別休暇'),
                new ItemModel(5, '欠勤'),
                new ItemModel(6, '代休'),
                new ItemModel(7, '振出'),
                new ItemModel(8, '振休'),
                new ItemModel(9, '時間消化休暇'),
                new ItemModel(10, '連続勤務'),
                new ItemModel(11, '休日出勤'),
                new ItemModel(12, '休職'),
                new ItemModel(13, '休業')
            ]);

            //午前と午後-勤務種類の分類 
            self.itemListHaftDay = ko.observableArray([
                new ItemModel(0, '出勤'),
                new ItemModel(1, '休日'),
                new ItemModel(2, '年休'),
                new ItemModel(3, '積立年休'),
                new ItemModel(4, '特別休暇'),
                new ItemModel(5, '欠勤'),
                new ItemModel(6, '代休'),
                new ItemModel(7, '振出'),
                new ItemModel(8, '振休'),
                new ItemModel(9, '時間消化休暇')
            ]);


            //休日区分
            self.itemHodidayAtr = ko.observableArray([
                new ItemModel(0, '法定内休日'),
                new ItemModel(1, '法定外休日'),
                new ItemModel(2, '祝日')
            ]);

            //休業区分
            self.itemCloseAtr = ko.observableArray([
                new ItemModel(0, '産前休業'),
                new ItemModel(1, '産後休業'),
                new ItemModel(2, '育児休業'),
                new ItemModel(3, '介護休業')
            ]);


            self.isEnable = ko.observable(true);
            self.isEditable = ko.observable(true);

            self.roundingRules = ko.observableArray([
                { code: '0', name: nts.uk.resource.getText('KMK007_19') },
                { code: '1', name: nts.uk.resource.getText('KMK007_20') }
            ]);


            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText('KMK007_7'), key: 'workTypeCode', width: 80 },
                { headerText: nts.uk.resource.getText('KMK007_8'), key: 'name', width: 150 },
                { headerText: '廃止', key: 'abolishAtr', width: 50 }
            ]);

            self.currentCode = ko.observable();

            self.currentCode.subscribe(function(newValue) {
                var itemWorkType = _.find(self.listWorkType(), function(item: IWorkType) {
                    return item.workTypeCode == newValue;
                });

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

                self.currentWorkType().oneDay().workTypeCode(itemWorkType.oneDay.workTypeCode);
                self.currentWorkType().oneDay().attendanceTime(itemWorkType.oneDay.attendanceTime);
                self.currentWorkType().oneDay().closeAtr(itemWorkType.oneDay.closeAtr);
                self.currentWorkType().oneDay().countHodiday(itemWorkType.oneDay.countHodiday);
                self.currentWorkType().oneDay().dayNightTimeAsk(itemWorkType.oneDay.dayNightTimeAsk);
                self.currentWorkType().oneDay().digestPublicHd(itemWorkType.oneDay.digestPublicHd);
                self.currentWorkType().oneDay().genSubHodiday(itemWorkType.oneDay.genSubHodiday);
                self.currentWorkType().oneDay().holidayAtr(itemWorkType.oneDay.holidayAtr);
                self.currentWorkType().oneDay().sumAbsenseNo(itemWorkType.oneDay.sumAbsenseNo);
                self.currentWorkType().oneDay().sumSpHodidayNo(itemWorkType.oneDay.sumSpHodidayNo);
                self.currentWorkType().oneDay().timeLeaveWork(itemWorkType.oneDay.timeLeaveWork);
                self.currentWorkType().oneDay().workAtr(itemWorkType.oneDay.workAtr);

                self.currentWorkType().morning().workTypeCode(itemWorkType.workTypeCode);
                self.currentWorkType().morning().attendanceTime(itemWorkType.morning.attendanceTime);
                self.currentWorkType().morning().closeAtr(itemWorkType.morning.closeAtr);
                self.currentWorkType().morning().countHodiday(itemWorkType.morning.countHodiday);
                self.currentWorkType().morning().dayNightTimeAsk(itemWorkType.morning.dayNightTimeAsk);
                self.currentWorkType().morning().digestPublicHd(itemWorkType.morning.digestPublicHd);
                self.currentWorkType().morning().genSubHodiday(itemWorkType.morning.genSubHodiday);
                self.currentWorkType().morning().holidayAtr(itemWorkType.morning.holidayAtr);
                self.currentWorkType().morning().sumAbsenseNo(itemWorkType.morning.sumAbsenseNo);
                self.currentWorkType().morning().sumSpHodidayNo(itemWorkType.morning.sumSpHodidayNo);
                self.currentWorkType().morning().timeLeaveWork(itemWorkType.morning.timeLeaveWork);
                self.currentWorkType().morning().workAtr(itemWorkType.morning.workAtr);

                self.currentWorkType().afternoon().workTypeCode(itemWorkType.workTypeCode);
                self.currentWorkType().afternoon().attendanceTime(itemWorkType.afternoon.attendanceTime);
                self.currentWorkType().afternoon().closeAtr(itemWorkType.afternoon.closeAtr);
                self.currentWorkType().afternoon().countHodiday(itemWorkType.afternoon.countHodiday);
                self.currentWorkType().afternoon().dayNightTimeAsk(itemWorkType.afternoon.dayNightTimeAsk);
                self.currentWorkType().afternoon().digestPublicHd(itemWorkType.afternoon.digestPublicHd);
                self.currentWorkType().afternoon().genSubHodiday(itemWorkType.afternoon.genSubHodiday);
                self.currentWorkType().afternoon().holidayAtr(itemWorkType.afternoon.holidayAtr);
                self.currentWorkType().afternoon().sumAbsenseNo(itemWorkType.afternoon.sumAbsenseNo);
                self.currentWorkType().afternoon().sumSpHodidayNo(itemWorkType.afternoon.sumSpHodidayNo);
                self.currentWorkType().afternoon().timeLeaveWork(itemWorkType.afternoon.timeLeaveWork);
                self.currentWorkType().afternoon().workAtr(itemWorkType.afternoon.workAtr);
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
            self.getWorkType();
            dfd.resolve();
            return dfd.promise();
        }
        
        openDiablogC() {
            var self = this;
                        
            nts.uk.ui.windows.setShared("KMK007_WORK_TYPES", self.listWorkType());
            
            nts.uk.ui.windows.sub.modal("/view/kmk/007/c/index.xhtml").onClosed(() => {
                
            });    
        }

        addWorkType(): any {
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

            workType.oneDay().workTypeCode(workType.workTypeCode());

            let command: any = ko.toJS(workType);
            command.oneDay.digestPublicHd = Number(command.oneDay.digestPublicHd);
            command.oneDay.attendanceTime = Number(command.oneDay.attendanceTime);
            command.oneDay.countHodiday = Number(command.oneDay.countHodiday);
            command.oneDay.dayNightTimeAsk = Number(command.oneDay.dayNightTimeAsk);
            command.oneDay.genSubHodiday = Number(command.oneDay.genSubHodiday);
            command.oneDay.timeLeaveWork = Number(command.oneDay.timeLeaveWork);

            service.addWebMenu(command).done(function() {

            }).fail(function(error) {
                nts.uk.ui.dialog.alertError(error.message);
            });
        }

        removeWorkType(): any {
            let self = this;
            let workTypeCd = self.currentCode();
            service.deleteWebMenu(workTypeCd);

        }

        private getWorkType(): any {
            var self = this;
            var dfd = $.Deferred();
            service.loadWorkType().done(function(data) {
                if (data.length != 0) {
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
                            oneDay: {
                                workTypeCode: item.workTypeCode,
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
                            },
                            morning: {
                                workTypeCode: item.workTypeCode,
                                workAtr: 1,
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
                            },
                            afternoon: {
                                workTypeCode: item.workTypeCode,
                                workAtr: 2,
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
                            },
                        });

                        // one day
                        if (workType.workAtr() == 0 && item.workTypeSets.length > 0) {
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
        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;
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
    }

    export class WorkType {
        workTypeCode: KnockoutObservable<string>;
        name: KnockoutObservable<string>;
        abbreviationName: KnockoutObservable<string>;
        symbolicName: KnockoutObservable<string>;
        abolishAtr: KnockoutObservable<number>;
        memo: KnockoutObservable<string>;
        workAtr: KnockoutObservable<number>;
        oneDayCls: KnockoutObservable<number>;
        morningCls: KnockoutObservable<number>;
        afternoonCls: KnockoutObservable<number>;
        calculatorMethod: KnockoutObservable<number>;
        oneDay: KnockoutObservable<WorkTypeSet>;
        morning: KnockoutObservable<WorkTypeSet>;
        afternoon: KnockoutObservable<WorkTypeSet>;

        constructor(param: IWorkType) {
            this.workTypeCode = ko.observable(param.workTypeCode || '');
            this.name = ko.observable(param.name);
            this.abbreviationName = ko.observable(param.abbreviationName);
            this.symbolicName = ko.observable(param.symbolicName);
            this.abolishAtr = ko.observable(param.abolishAtr);
            this.memo = ko.observable(param.memo);
            this.workAtr = ko.observable(param.workAtr);
            this.oneDayCls = ko.observable(param.oneDayCls);
            this.morningCls = ko.observable(param.morningCls);
            this.afternoonCls = ko.observable(param.afternoonCls);
            this.calculatorMethod = ko.observable(param.calculatorMethod);
            this.oneDay = ko.observable(new WorkTypeSet(param.oneDay));
            this.morning = ko.observable(new WorkTypeSet(param.morning));
            this.afternoon = ko.observable(new WorkTypeSet(param.afternoon));
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