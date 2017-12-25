module nts.uk.at.view.kmk013.b {
    export module viewmodel {
        export class ScreenModel {
            itemsB23: KnockoutObservableArray<any>;
            itemsB29: KnockoutObservableArray<any>;
            itemsB215: KnockoutObservableArray<any>;
            selectedB23: KnockoutObservable<number>;
            selectedB29: KnockoutObservable<number>;
            selectedB215: KnockoutObservable<number>;
            enableB217: KnockoutObservable<boolean>;
            readonly: KnockoutObservable<boolean>;
            timeOfDay: KnockoutObservable<number>;
            timeB219: KnockoutObservable<number>;
            timeB221: KnockoutObservable<number>;
            timeB223: KnockoutObservable<number>;
            time2: KnockoutObservable<number>;
            inline: KnockoutObservable<boolean>;
            tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            selectedTab: KnockoutObservable<string>;

            checkedB33: KnockoutObservable<boolean>;
            checkedB34: KnockoutObservable<boolean>;
            checkedB35: KnockoutObservable<boolean>;
            //B5 inner
            selectedValueB54: KnockoutObservable<any>;
            selectedValueB59: KnockoutObservable<any>;
            selectedValueB515: KnockoutObservable<any>;
            enableB1: KnockoutObservable<boolean>;
            checkedB57: KnockoutObservable<boolean>;
            checkedB512: KnockoutObservable<boolean>;
            checkedB513: KnockoutObservable<boolean>;
            checkedB514: KnockoutObservable<boolean>;
            checkedB518: KnockoutObservable<boolean>;
            checkedB519: KnockoutObservable<boolean>;
            checkedB520: KnockoutObservable<boolean>;
            checkedB521: KnockoutObservable<boolean>;
            itemListB59: KnockoutObservableArray<any>;
            selectedIdB59: KnockoutObservable<number>;
            enableB54: KnockoutObservable<boolean>;
            enableB59: KnockoutObservable<boolean>;
            enableB515: KnockoutObservable<boolean>;
            //B6
            itemListB64: KnockoutObservableArray<any>;
            selectedValueB64: KnockoutObservable<any>;
            checkedB67: KnockoutObservable<boolean>;
            checkedB68: KnockoutObservable<boolean>;
            checkedB69: KnockoutObservable<boolean>;
            checkedB610: KnockoutObservable<boolean>;
            checkedB611: KnockoutObservable<boolean>;
            selectedValueB612: KnockoutObservable<any>;
            checkedB615: KnockoutObservable<boolean>;
            checkedB616: KnockoutObservable<boolean>;
            checkedB617: KnockoutObservable<boolean>;
            checkedB618: KnockoutObservable<boolean>;
            checkedB619: KnockoutObservable<boolean>;
            checkedB620: KnockoutObservable<boolean>;
            enableB64: KnockoutObservable<boolean>;
            enableB612: KnockoutObservable<boolean>;
            enableB68: KnockoutObservable<boolean>;
            enableB616: KnockoutObservable<boolean>;
            //B7
            itemListB74: KnockoutObservableArray<any>;
            selectedValueB74: KnockoutObservable<any>;
            checkedB77: KnockoutObservable<boolean>;
            itemListB79: KnockoutObservableArray<any>;
            selectedIdB79: KnockoutObservable<any>;
            checkedB712: KnockoutObservable<boolean>;
            checkedB713: KnockoutObservable<boolean>;
            checkedB714: KnockoutObservable<boolean>;
            selectedValueB715: KnockoutObservable<any>;
            checkedB718: KnockoutObservable<boolean>;
            checkedB719: KnockoutObservable<boolean>;
            checkedB720: KnockoutObservable<boolean>;
            checkedB721: KnockoutObservable<boolean>;
            checkedB722: KnockoutObservable<boolean>;
            enableB74: KnockoutObservable<boolean>;
            enableB79: KnockoutObservable<boolean>;
            enableB715: KnockoutObservable<boolean>;
            constructor() {
                var self = this;
                self.itemsB23 = ko.observableArray([
                    new BoxModel(1, nts.uk.resource.getText('KMK013_5')),
                    new BoxModel(0, nts.uk.resource.getText('KMK013_6')),
                ]);
                self.itemsB29 = ko.observableArray([
                    new BoxModel(1, nts.uk.resource.getText('KMK013_9')),
                    new BoxModel(0, nts.uk.resource.getText('KMK013_10')),
                ]);
                self.itemsB215 = ko.observableArray([
                    new BoxModel(1, nts.uk.resource.getText('KMK013_13')),
                    new BoxModel(0, nts.uk.resource.getText('KMK013_14')),
                ]);
                self.selectedB23 = ko.observable(0);
                self.selectedB29 = ko.observable(0);
                self.selectedB215 = ko.observable(0);

                self.enableB217 = ko.observable(true);
                self.readonly = ko.observable(false);

                self.timeOfDay = ko.observable(0);
                self.timeB219 = ko.observable(0);
                self.timeB221 = ko.observable(0);
                self.timeB223 = ko.observable(0);
                self.inline = ko.observable(false);
                self.tabs = ko.observableArray([
                    { id: 'tab-1', title: nts.uk.resource.getText("KMK013_25"), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: nts.uk.resource.getText("KMK013_26"), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-3', title: nts.uk.resource.getText("KMK013_27"), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) },
                ]);
                self.selectedTab = ko.observable('tab-1');
                self.checkedB33 = ko.observable(false);
                self.checkedB34 = ko.observable(false);
                self.checkedB35 = ko.observable(false);
                //B5 inner
                self.enableB1 = ko.observable(false);
                self.selectedValueB54 = ko.observable(0);
                self.selectedValueB59 = ko.observable(0);
                self.selectedValueB515 = ko.observable(0);
                self.checkedB57 = ko.observable(false);
                self.checkedB512 = ko.observable(false);
                self.checkedB513 = ko.observable(false);
                self.checkedB514 = ko.observable(false);
                self.checkedB518 = ko.observable(false);
                self.checkedB519 = ko.observable(false);
                self.checkedB520 = ko.observable(false);
                self.checkedB521 = ko.observable(false);
                self.itemListB59 = ko.observableArray([
                    new BoxModel(0, nts.uk.resource.getText('KMK013_36')),
                    new BoxModel(1, nts.uk.resource.getText('KMK013_37')),
                ]);
                self.selectedIdB59 = ko.observable(0);
                self.enableB54 = ko.observable(false);
                self.enableB59 = ko.observable(false);
                self.enableB515 = ko.observable(false);
                //B6 inner
                self.selectedValueB64 = ko.observable(0);
                self.checkedB67 = ko.observable(false);
                self.checkedB68 = ko.observable(false);
                self.checkedB69 = ko.observable(false);
                self.checkedB610 = ko.observable(false);
                self.checkedB611 = ko.observable(false);
                self.selectedValueB612 = ko.observable(0);
                self.checkedB615 = ko.observable(false);
                self.checkedB616 = ko.observable(false);
                self.checkedB617 = ko.observable(false);
                self.checkedB618 = ko.observable(false);
                self.checkedB619 = ko.observable(false);
                self.checkedB620 = ko.observable(false);

                self.itemListB64 = ko.observableArray([
                    new BoxModel(0, nts.uk.resource.getText('KMK013_51')),
                    new BoxModel(1, nts.uk.resource.getText('KMK013_52')),
                ]);
                self.enableB64 = ko.observable(false);
                self.enableB612 = ko.observable(false);
                self.enableB68 = ko.observable(false);
                self.enableB616 = ko.observable(false);
                //B7
                self.selectedValueB74 = ko.observable(0);
                self.itemListB74 = ko.observableArray([
                    new BoxModel(0, nts.uk.resource.getText('KMK013_69')),
                    new BoxModel(1, nts.uk.resource.getText('KMK013_70')),
                ]);
                self.itemListB79 = ko.observableArray([
                    new BoxModel(0, nts.uk.resource.getText('KMK013_36')),
                    new BoxModel(1, nts.uk.resource.getText('KMK013_37')),
                ]);
                self.selectedIdB79 = ko.observable(0);
                self.checkedB77 = ko.observable(false);
                self.checkedB712 = ko.observable(false);
                self.checkedB713 = ko.observable(false);
                self.checkedB714 = ko.observable(false);
                self.selectedValueB715 = ko.observable(0);
                self.checkedB718 = ko.observable(false);
                self.checkedB719 = ko.observable(false);
                self.checkedB720 = ko.observable(false);
                self.checkedB721 = ko.observable(false);
                self.checkedB722 = ko.observable(false);
                self.enableB74 = ko.observable(false);
                self.enableB79 = ko.observable(false);
                self.enableB715 = ko.observable(false);
                self.selectedB215.subscribe((newValue) => {
                    if (newValue == 1) {
                        self.enableB217(false);
                    } else {
                        self.enableB217(true);
                    }
                });
                //B5
                self.selectedValueB54.subscribe((newValue) => {
                    if (newValue == 1) {
                        self.selectedValueB515(1);
                        self.enableB54(true);
                    } else {
                        self.enableB54(false);
                    }
                });
                self.selectedValueB515.subscribe((newValue) => {
                    if (newValue == 0) {
                        nts.uk.ui.dialog.info({ messageId: "Msg_826" }).then(() => {
                            self.selectedValueB54(0);
                            self.enableB515(false);
                        });
                    } else {
                        self.enableB515(true);
                    }
                });
                self.checkedB57.subscribe(newValue => {
                    if (newValue == true) {
                        self.checkedB518(true);
                        self.enableB59(true);
                    } else {
                        self.enableB59(false);
                    }
                });
                self.checkedB518.subscribe(newValue => {
                    if (newValue == false) {
                        self.checkedB57(false);
                    }
                });
                self.checkedB512.subscribe(newValue => {
                    if (newValue == true) {
                        self.checkedB519(true);
                    }
                });
                self.checkedB519.subscribe(newValue => {
                    if (newValue == false) {
                        self.checkedB512(false);
                    }
                });
                self.checkedB513.subscribe(newValue => {
                    if (newValue == true) {
                        self.checkedB514(true);
                        self.checkedB520(true);
                    }
                });
                self.checkedB520.subscribe(newValue => {
                    if (newValue == false) {
                        self.checkedB513(false);
                    } else {
                        self.checkedB521(true);
                    }
                });
                self.checkedB514.subscribe(newValue => {
                    if (newValue == true) {
                        self.checkedB521(true);
                    } else {
                        self.checkedB513(false);
                    }
                });
                self.checkedB521.subscribe(newValue => {
                    if (newValue == false) {
                        self.checkedB514(false);
                        self.checkedB520(false);
                    }
                });
                //B6
                self.selectedValueB64.subscribe((newValue) => {
                    if (newValue == 1) {
                        self.selectedValueB612(1);
                        self.enableB64(true);
                    } else {
                        self.enableB64(false);
                    }
                });
                self.selectedValueB612.subscribe((newValue) => {
                    if (newValue == 0) {
                        nts.uk.ui.dialog.info({ messageId: "Msg_826" }).then(() => {
                            self.selectedValueB64(0);
                            self.enableB612(false);
                        });
                    } else {
                        self.enableB612(true);
                    }
                });
                self.checkedB67.subscribe(newValue => {
                    if (newValue == true) {
                        self.checkedB615(true);
                        self.enableB68(true);
                    } else {
                        self.enableB68(false);
                    }
                });
                self.checkedB615.subscribe(newValue => {
                    if (newValue == false) {
                        self.checkedB67(false);
                        self.enableB616(false);
                    } else {
                        self.enableB616(true);
                    }
                });
                self.checkedB68.subscribe(newValue => {
                    if (newValue == true) {
                        self.checkedB616(true);
                    }
                });
                self.checkedB616.subscribe(newValue => {
                    if (newValue == false) {
                        self.checkedB68(false);
                    }
                });
                self.checkedB69.subscribe(newValue => {
                    if (newValue == true) {
                        self.checkedB617(true);
                    }
                });
                self.checkedB617.subscribe(newValue => {
                    if (newValue == false) {
                        self.checkedB69(false);
                    }
                });
                self.checkedB610.subscribe(newValue => {
                    if (newValue == true) {
                        self.checkedB618(true);
                        self.checkedB611(true);
                    }
                });
                self.checkedB618.subscribe(newValue => {
                    if (newValue == false) {
                        self.checkedB610(false);
                    } else {
                        self.checkedB619(true);
                    }
                });
                self.checkedB611.subscribe(newValue => {
                    if (newValue == true) {
                        self.checkedB619(true);
                    } else {
                        self.checkedB610(false);
                    }
                });
                self.checkedB619.subscribe(newValue => {
                    if (newValue == false) {
                        self.checkedB611(false);
                        self.checkedB618(false);
                    }
                });
                //B7
                self.selectedValueB74.subscribe((newValue) => {
                    if (newValue == 1) {
                        self.selectedValueB715(1);
                        self.enableB74(true);
                    } else {
                        self.enableB74(false);
                    }
                });
                self.selectedValueB715.subscribe((newValue) => {
                    if (newValue == 0) {
                        nts.uk.ui.dialog.info({ messageId: "Msg_826" }).then(() => {
                            self.selectedValueB74(0);
                            self.enableB715(false);
                        });
                    } else {
                        self.enableB715(true);
                    }
                });
                self.checkedB77.subscribe(newValue => {
                    if (newValue == true) {
                        self.checkedB718(true);
                        self.enableB79(true);
                    } else {
                        self.enableB79(false);
                    }
                });
                self.checkedB718.subscribe(newValue => {
                    if (newValue == false) {
                        self.checkedB77(false);
                    }
                });
                self.checkedB712.subscribe(newValue => {
                    if (newValue == true) {
                        self.checkedB719(true);
                    }
                });
                self.checkedB719.subscribe(newValue => {
                    if (newValue == false) {
                        self.checkedB712(false);
                    }
                });
                self.checkedB713.subscribe(newValue => {
                    if (newValue == true) {
                        self.checkedB714(true);
                        self.checkedB720(true);
                    }
                });
                self.checkedB720.subscribe(newValue => {
                    if (newValue == false) {
                        self.checkedB713(false);
                    } else {
                        self.checkedB721(true);
                    }
                });
                self.checkedB714.subscribe(newValue => {
                    if (newValue == true) {
                        self.checkedB721(true);
                    } else {
                        self.checkedB713(false);
                    }
                });
                self.checkedB721.subscribe(newValue => {
                    if (newValue == false) {
                        self.checkedB714(false);
                        self.checkedB720(false);
                    }
                });


            }
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                self.initData();
                dfd.resolve();
                return dfd.promise();
            }
            initData(): void {
                let self = this;
                service.findByCompanyId().done((data) => {
                    if (data[0] == null) {
                        return;
                    }
                    let obj = data[0];
                    //実績の就業時間帯を参照する
                    self.selectedB23(obj.referActualWorkHours);
                    //実績を参照しない場合の参照先
                    self.selectedB29(obj.notReferringAch);
                    //会社単位の休暇時間を参照する
                    self.selectedB215(obj.referComHolidayTime);
                    if (obj.referComHolidayTime != 1) {
                        self.enableB217(false);
                    }
                    //加算時間.1日
                    self.timeB219(obj.oneDay);
                    //加算時間.午前
                    self.timeB221(obj.morning);
                    //加算時間.午後
                    self.timeB223(obj.afternoon);

                    //加算休暇設定.年休
                    self.checkedB33(convertToBoolean(obj.annualHoliday));
                    //加算休暇設定.積立年休
                    self.checkedB34(convertToBoolean(obj.yearlyReserved));
                    //加算休暇設定.特別休暇
                    self.checkedB35(convertToBoolean(obj.specialHoliday));

                    //休暇の計算方法の設定.休暇の割増計算方法.実働のみで計算する
                    self.selectedValueB54(obj.regularWork.calcActualOperationPre);
                    //休暇の計算方法の設定.休暇の割増計算方法.詳細設定.休暇分を含める設定.加算する
                    self.checkedB57(convertToBoolean(obj.regularWork.additionTimePre));
                    //休暇の計算方法の設定.休暇の割増計算方法.詳細設定.休暇分を含める設定.通常、変形の所定超過時
                    self.selectedIdB59(obj.regularWork.deformatExcValuePre);
                    //休暇の計算方法の設定.休暇の割増計算方法.詳細設定.育児・介護時間を含めて計算する
                    self.checkedB512(convertToBoolean(obj.regularWork.calcIncludCarePre));
                    //休暇の計算方法の設定.休暇の割増計算方法.詳細設定.遅刻・早退を控除しない
                    self.checkedB513(convertToBoolean(obj.regularWork.notDeductLateleavePre));
                    //休暇の計算方法の設定.休暇の割増計算方法.詳細設定.インターバル免除時間を含めて計算する
                    self.checkedB514(convertToBoolean(obj.regularWork.calcIntervalTimePre));
                    //休暇の計算方法の設定.休暇の就業時間計算方法.実働のみで計算する
                    self.selectedValueB515(obj.regularWork.calcActualOperaWork);
                    //休暇の計算方法の設定.休暇の就業時間計算方法.詳細設定.休暇分を含める設定.加算する
                    self.checkedB518(convertToBoolean(obj.regularWork.additionTimeWork));
                    //休暇の計算方法の設定.休暇の就業時間計算方法.詳細設定.育児・介護時間を含めて計算する
                    self.checkedB519(convertToBoolean(obj.regularWork.calcIncludCareWork));
                    //休暇の計算方法の設定.休暇の就業時間計算方法.詳細設定.遅刻・早退を控除しない
                    self.checkedB520(convertToBoolean(obj.regularWork.notDeductLateleaveWork));
                    //休暇の計算方法の設定.休暇の就業時間計算方法.詳細設定.インターバル免除時間を含めて計算する
                    self.checkedB521(convertToBoolean(obj.regularWork.calsIntervalTimeWork));

                    //休暇の計算方法の設定.休暇の割増計算方法.実働のみで計算する
                    self.selectedValueB64(obj.flexWork.calcActualOperationPre);
                    //休暇の計算方法の設定.休暇の割増計算方法.詳細設定.休暇分を含める設定.加算する
                    self.checkedB67(convertToBoolean(obj.flexWork.additionTimePre));
                    //休暇の計算方法の設定.休暇の割増計算方法.詳細設定.休暇分を含める設定.フレックスの所定超過時
                    self.checkedB68(convertToBoolean(obj.flexWork.predExcessTimeflexPre));
                    //休暇の計算方法の設定.休暇の割増計算方法.詳細設定.育児・介護時間を含めて計算する
                    self.checkedB69(convertToBoolean(obj.flexWork.calcIncludCarePre));
                    //休暇の計算方法の設定.休暇の割増計算方法.詳細設定.遅刻・早退を控除しない
                    self.checkedB610(convertToBoolean(obj.flexWork.notDeductLateleavePre));
                    //休暇の計算方法の設定.休暇の割増計算方法.詳細設定.インターバル免除時間を含めて計算する
                    self.checkedB611(convertToBoolean(obj.flexWork.calcIntervalTimePre));
                    //休暇の計算方法の設定.休暇の就業時間計算方法.実働のみで計算する
                    self.selectedValueB612(obj.flexWork.calcActualOperaWork);
                    //休暇の計算方法の設定.休暇の就業時間計算方法.詳細設定.休暇分を含める設定.加算する
                    self.checkedB615(convertToBoolean(obj.flexWork.additionTimeWork));
                    //休暇の計算方法の設定.休暇の就業時間計算方法.詳細設定.休暇分を含める設定.フレックスの所定不足時
                    self.checkedB616(convertToBoolean(obj.flexWork.predeterminDeficiency));
                    //休暇の計算方法の設定.休暇の就業時間計算方法.詳細設定.育児・介護時間を含めて計算する
                    self.checkedB617(convertToBoolean(obj.flexWork.calcIncludCareWork));
                    //休暇の計算方法の設定.休暇の就業時間計算方法.詳細設定.遅刻・早退を控除しない
                    self.checkedB618(convertToBoolean(obj.flexWork.notDeductLateleaveWork));
                    //休暇の計算方法の設定.休暇の就業時間計算方法.詳細設定.インターバル免除時間を含めて計算する
                    self.checkedB619(convertToBoolean(obj.flexWork.calsIntervalTimeWork));
                    //休暇の計算方法の設定.休暇の就業時間計算方法.詳細設定.欠勤時間をマイナスする
                    self.checkedB620(convertToBoolean(obj.flexWork.minusAbsenceTimeWork));

                    //休暇の計算方法の設定.休暇の割増計算方法.実働のみで計算する
                    self.selectedValueB74(obj.irregularWork.calcActualOperationPre);
                    //休暇の計算方法の設定.休暇の割増計算方法.詳細設定.休暇分を含める設定.加算する
                    self.checkedB77(convertToBoolean(obj.irregularWork.additionTimePre));
                    //休暇の計算方法の設定.休暇の割増計算方法.詳細設定.休暇分を含める設定.通常、変形の所定超過時
                    self.selectedIdB79(obj.irregularWork.deformatExcValuePre);
                    //休暇の計算方法の設定.休暇の割増計算方法.詳細設定.育児・介護時間を含めて計算する
                    self.checkedB712(convertToBoolean(obj.irregularWork.calcIncludCarePre));
                    //休暇の計算方法の設定.休暇の割増計算方法.詳細設定.遅刻・早退を控除しない
                    self.checkedB713(convertToBoolean(obj.irregularWork.notDeductLateleavePre));
                    //休暇の計算方法の設定.休暇の割増計算方法.詳細設定.インターバル免除時間を含めて計算する
                    self.checkedB714(convertToBoolean(obj.irregularWork.calcIntervalTimePre));
                    //休暇の計算方法の設定.休暇の就業時間計算方法.実働のみで計算する
                    self.selectedValueB715(obj.irregularWork.calcActualOperaWork);
                    //休暇の計算方法の設定.休暇の就業時間計算方法.詳細設定.休暇分を含める設定.加算する
                    self.checkedB718(convertToBoolean(obj.irregularWork.additionTimeWork));
                    //休暇の計算方法の設定.休暇の就業時間計算方法.詳細設定.育児・介護時間を含めて計算する
                    self.checkedB719(convertToBoolean(obj.irregularWork.calcIncludCareWork));
                    //休暇の計算方法の設定.休暇の就業時間計算方法.詳細設定.遅刻・早退を控除しない
                    self.checkedB720(convertToBoolean(obj.irregularWork.notDeductLateleaveWork));
                    //休暇の計算方法の設定.休暇の就業時間計算方法.詳細設定.インターバル免除時間を含めて計算する
                    self.checkedB721(convertToBoolean(obj.irregularWork.calsIntervalTimeWork));
                    //休暇の計算方法の設定.休暇の就業時間計算方法.詳細設定.欠勤時間をマイナスする
                    self.checkedB722(convertToBoolean(obj.irregularWork.minusAbsenceTimeWork));
                });
            }
            save(): void {
                let self = this;
                let obj = {};
                obj.referComHolidayTime = self.selectedB215();
                if (self.enableB217()) {
                    obj.oneDay = self.timeB219();
                    obj.morning = self.timeB221();
                    obj.afternoon = self.timeB223()
                } else {
                    obj.oneDay = 0;
                    obj.morning = 0;
                    obj.afternoon = 0;
                }
                obj.referActualWorkHours = self.selectedB23();
                obj.annualHoliday = convertToInt(self.checkedB33());
                obj.specialHoliday = convertToInt(self.checkedB35());
                obj.yearlyReserved = convertToInt(self.checkedB34());
                //regularWork
                obj.regularWork = {};
                obj.regularWork.companyId = "";
                obj.regularWork.calcActualOperationPre = self.selectedValueB54();
                if (self.selectedValueB54() == 1) {
                    obj.regularWork.additionTimePre = convertToInt(self.checkedB57());
                    obj.regularWork.calcIncludCarePre = convertToInt(self.checkedB512());
                    obj.regularWork.notDeductLateleavePre = convertToInt(self.checkedB513());
                    obj.regularWork.calcIntervalTimePre = convertToInt(self.checkedB514());
                } else {
                    obj.regularWork.calcIntervalTimePre = 0;
                    obj.regularWork.calcIncludCarePre = 0;
                    obj.regularWork.additionTimePre = 0;
                    obj.regularWork.notDeductLateleavePre = 0;
                    obj.regularWork.deformatExcValuePre = 0;
                }
                if (self.enableB59() == true) {
                    obj.regularWork.deformatExcValuePre = convertToInt(self.enableB59());
                } else {
                    obj.regularWork.deformatExcValuePre = 0;
                }
                obj.regularWork.calcActualOperaWork = self.selectedValueB515();
                if (self.selectedValueB515() == 1) {
                    obj.regularWork.additionTimeWork = convertToInt(self.checkedB518());
                    obj.regularWork.calcIncludCareWork = convertToInt(self.checkedB519());
                    obj.regularWork.notDeductLateleaveWork = convertToInt(self.checkedB520());
                    obj.regularWork.calsIntervalTimeWork = convertToInt(self.checkedB521());
                } else {
                    obj.regularWork.additionTimeWork = 0;
                    obj.regularWork.calcIncludCareWork = 0;
                    obj.regularWork.notDeductLateleaveWork = 0;
                    obj.regularWork.calsIntervalTimeWork = 0;
                }

                //flexWork
                obj.flexWork = {};
                obj.flexWork.companyId = "";
                obj.flexWork.calcActualOperationPre = self.selectedValueB64();
                if (self.selectedValueB64() == 1) {
                    obj.flexWork.additionTimePre = convertToInt(self.checkedB67());
                    obj.flexWork.calcIncludCarePre = convertToInt(self.checkedB69());
                    obj.flexWork.notDeductLateleavePre = convertToInt(self.checkedB610());
                    obj.flexWork.calcIntervalTimePre = convertToInt(self.checkedB611());
                } else {
                    obj.flexWork.additionTimePre = 0;
                    obj.flexWork.calcIncludCarePre = 0;
                    obj.flexWork.notDeductLateleavePre = 0;
                    obj.flexWork.calcIntervalTimePre = 0;
                }
                if (self.enableB68() == true) {
                    obj.flexWork.predExcessTimeflexPre = convertToInt(self.enableB68());
                } else {
                    obj.flexWork.predExcessTimeflexPre = 0;
                }
                obj.flexWork.calcActualOperaWork = self.selectedValueB612();
                if (self.selectedValueB612() == 1) {
                    obj.flexWork.additionTimeWork = convertToInt(self.checkedB615());
                    obj.flexWork.calcIncludCareWork = convertToInt(self.checkedB617());
                    obj.flexWork.notDeductLateleaveWork = convertToInt(self.checkedB618());
                    obj.flexWork.calsIntervalTimeWork = convertToInt(self.checkedB619());
                    obj.flexWork.minusAbsenceTimeWork = convertToInt(self.checkedB620());
                } else {
                    obj.flexWork.additionTimeWork = 0;
                    obj.flexWork.calcIncludCareWork = 0;
                    obj.flexWork.notDeductLateleaveWork = 0;
                    obj.flexWork.calsIntervalTimeWork = 0;
                    obj.flexWork.minusAbsenceTimeWork = 0;
                }
                if (self.enableB616() == true) {
                    obj.flexWork.predeterminDeficiency = convertToInt(self.checkedB616());
                } else {
                    obj.flexWork.predeterminDeficiency = 0;
                }
                //irregularWork
                obj.irregularWork = {};
                obj.irregularWork.companyId = "";
                obj.irregularWork.calcActualOperationPre = self.selectedValueB74();
                if (self.selectedValueB74() == 1) {
                    obj.irregularWork.additionTimePre = convertToInt(self.checkedB57());
                    obj.irregularWork.calcIncludCarePre = convertToInt(self.checkedB512());
                    obj.irregularWork.notDeductLateleavePre = convertToInt(self.checkedB513());
                    obj.irregularWork.calcIntervalTimePre = convertToInt(self.checkedB514());
                } else {
                    obj.irregularWork.additionTimePre = 0;
                    obj.irregularWork.calcIncludCarePre = 0;
                    obj.irregularWork.notDeductLateleavePre = 0;
                    obj.irregularWork.calcIntervalTimePre = 0;
                }
                if (self.enableB79() == true) {
                    obj.irregularWork.deformatExcValuePre = convertToInt(self.enableB79());
                } else {
                    obj.irregularWork.deformatExcValuePre = 0;
                }
                obj.irregularWork.calcActualOperaWork = self.selectedValueB715();
                if (self.selectedValueB715() == 1) {
                    obj.irregularWork.additionTimeWork = convertToInt(self.checkedB718());
                    obj.irregularWork.calcIncludCareWork = convertToInt(self.checkedB719());
                    obj.irregularWork.notDeductLateleaveWork = convertToInt(self.checkedB720());
                    obj.irregularWork.calsIntervalTimeWork = convertToInt(self.checkedB721());
                    obj.irregularWork.minusAbsenceTimeWork = convertToInt(self.checkedB722());
                } else {
                    obj.irregularWork.additionTimeWork = 0;
                    obj.irregularWork.calcIncludCareWork = 0;
                    obj.irregularWork.notDeductLateleaveWork = 0;
                    obj.irregularWork.calsIntervalTimeWork = 0;
                    obj.irregularWork.minusAbsenceTimeWork = 0;
                }
                service.save(obj).done();
            }



        }
        class BoxModel {
            id: number;
            name: string;
            constructor(id, name) {
                var self = this;
                self.id = id;
                self.name = name;
            }
        }
        function convertToBoolean(x: number) {
            if (x == 1)
                return true;
            else
                return false;
        }
        function convertToInt(x: boolean) {
            if (x == true) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}