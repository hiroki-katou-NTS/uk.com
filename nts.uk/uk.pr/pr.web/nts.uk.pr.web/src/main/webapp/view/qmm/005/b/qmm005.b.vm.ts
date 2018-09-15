module nts.uk.pr.view.qmm005.b.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import model = nts.uk.pr.view.qmm005.share.model;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import getSetDaySupports = nts.uk.pr.view.qmm005.a.service.getSetDaySupports;
    import modal = nts.uk.ui.windows.sub.modal;

    export class ScreenModel {
        processCateNo: any;
        processInfomationDto: any;
        setDaySupportDtoList: any;
        processingYear: KnockoutObservable<number>;
        processingDivisionName: KnockoutObservable<string>;
        settingPaymentList: KnockoutObservableArray<model.PaymentDateItem>;
        processingYearList: KnockoutObservableArray<model.ItemModel>;
        btnText: any;
        isNewMode: KnockoutObservable<boolean>;
        processingYearListSelectedCode: KnockoutObservable<number>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        show: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;

        constructor() {
            var self = this;
            self.processCateNo = 333;
            self.processingDivisionName = ko.observable();
            self.settingPaymentList = ko.observableArray([]);
            self.processingYearList = ko.observableArray([]);
            self.show = ko.observable(false);
            self.btnText = ko.computed(function () {
                if (self.show()) return "-";
                return "+";
            });
            self.isNewMode = ko.observable(false);
            //B2_3 処理年
            this.columns = ko.observableArray([
                {headerText: '', key: 'code', width: 0, hidden: true},
                {headerText: nts.uk.resource.getText('#QMM005_25'), key: 'name', width: 150},
            ]);
            this.enable = ko.observable(true);
            self.processingYearListSelectedCode = ko.observable(-1);
            self.processingYear = ko.observable(2018);
            self.processingYear.subscribe(function (newValue) {
                self.selectProcessingYear(newValue);
            })

        }

        toggle(): void {
            this.show(!this.show());
        }

        cancel() {
            var self = this;
            if (self.processingYearList().length > 0){
                self.selectProcessingYear(self.processingYearList()[0].code);
            }
        }

        startPage(params): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.startupScreen();
            dfd.resolve();
            return dfd.promise();
        }

        startupScreen() {
            // get domain 処理区分基本情報 (ProcessInfomation), 給与支払日設定 (SetDaySupport)
            var self = this;
            service.getProcessInfomation(self.processCateNo).done(function (data) {
                if (data) {
                    self.processInfomationDto = data;
                    // B3_2
                    self.processingDivisionName = ko.observable(nts.uk.text.format(nts.uk.resource.getText("#QMM005_97"), self.processCateNo, data.processDivisionName));
                }
            });
            service.getSetDaySupport(self.processCateNo).done(function (data) {
                // B2_2
                // 詳細設定対象_処理年 processingYearList
                var array = [];
                _.forEach(data, function (setDaySupport) {
                    _.forEach(setDaySupport, function (key, value) {
                        if (key == "processDate") {
                            array = _.union(array, [new model.ItemModel(value.toString().substr(0, 4), value.toString().substr(0, 4))]);
                        }
                    });
                });
                self.processingYearList = ko.observableArray(array);
                if (array.length > 0) {
                    self.selectProcessingYear(array[0].code);
                }
            });
        }

        selectProcessingYear(year) {
            /* phần lớn dữ liệu lấy từ setDaySupport*/
            // B3_4				処理年
            var self = this;
            self.processingYear = ko.observable(year);
            service.getSelectProcessingYear(self.processCateNo, year).done(function (data) {
                var firstArray = [];
                _.forEach(data.setDaySupportDtoList, function (setDaySupport) {
                    var obj = {};
                    // B4_10 支払年月日
                    obj["paymentDate"] = ko.observable(setDaySupport.paymentDate);
                    // B4_11 支払曜日
                    // B4_12				社員抽出基準日
                    obj["employeeExtractionReferenceDate"] = ko.observable(setDaySupport.empExtraRefeDate);
                    // B4_13 社会保険徴収月
                    obj["socialInsuranceCollectionMonth"] = ko.observable(setDaySupport.socialInsurdCollecMonth);
                    // B4_16 要勤務日数
                    obj["numberOfWorkingDays"] = ko.observable(setDaySupport.numberWorkDay);
                    // B6_7	社会保険基準日
                    obj["socialInsuranceStandardDate"] = ko.observable(setDaySupport.socialInsurdStanDate);
                    // B6_8	雇用保険基準日
                    obj["employmentInsuranceStandardDate"] = ko.observable(setDaySupport.empInsurdStanDate);
                    // B6_9	勤怠締め日
                    obj["timeClosingDate"] = ko.observable(setDaySupport.closeDateTime);
                    // B6_10 所得税基準日
                    obj["incomeTaxReferenceDate"] = ko.observable(setDaySupport.incomeTaxDate);
                    // B6_11
                    obj["accountingClosureDate"] = ko.observable(setDaySupport.closureDateAccounting)
                    firstArray.push(obj);
                });
                var secondArray = [];
                _.forEach(data.specPrintYmSetDtoList, function (specPrintYmSet) {
                    secondArray.push({specificationPrintDate: ko.observable(specPrintYmSet.printDate)});
                });
                var i;
                for (i = 0; i < firstArray.length; i++) {
                    firstArray[i]["specificationPrintDate"] = secondArray[i]["specificationPrintDate"];
                }
                self.settingPaymentList(firstArray);
            });
        }

        creatNewProcessYear() {
            var self = this;
            self.isNewMode(true);
            self.processingYear = ko.observable();
            self.reflectionPressingProcess();
        }

        //反映ボタン押下時処理
        //screen E in insert reflect
        reflectionPressingProcess() {
            var self = this;
            // check processingYear valid
            if (self.processingYear()) {
                var array = [];
                service.getValPayDateSet(self.processCateNo).done(function (data) {
                        for (index = 0; index <= 12; index++) {
                            let objItem = {
                                targetMonth: index,
                                paymentDate: ko.observable(self.processingYear() + '/' + index + '/' + data.basicSetting.monthlyPaymentDate.datePayMent),
                                employeeExtractionReferenceDate: ko.observable(self.processingYear() + '/' + index + '/' + data.basicSetting.employeeExtractionReferenceDate.refeDate),
                                socialInsuranceCollectionMonth: ko.observable(self.processingYear() + '/' + index),
                                specificationPrintDate: ko.observable(self.processingYear() + '/' + index),
                                numberOfWorkingDays: ko.observable(data.basicSetting.workDay),
                                socialInsuranceStandardDate: ko.observable(self.processingYear() + '/' + index + '/' + data.advancedSetting.sociInsuStanDate.refeDate),
                                employmentInsuranceStandardDate: ko.observable((self.processingYear() - 1) + '/' + index + '/' + data.advancedSetting.empInsurStanDate.refeDate),
                                timeClosingDate: ko.observable(self.processingYear() + '/' + index + '/' + data.advancedSetting.closeDate.refeDate),
                                incomeTaxReferenceDate: ko.observable(self.processingYear() + '/' + data.advancedSetting.incomTaxBaseYear.baseMonth + '/' + data.advancedSetting.incomTaxBaseYear.refeDate),
                                accountingClosureDate: ko.observable(self.processingYear() + '/' + index + '/' + data.basicSetting.accountingClosureDate.disposalDay)
                            }
                            array.push(objItem);
                        }
                    }
                );
                self.settingPaymentList(array);
                self.reflectSystemReference();
            }
        }

        //screen E mode update
        reflectSystemReference() {
            var self = this;
            setShared("QMM005bParams", {
                processCateNo: self.processCateNo(),
                processingYear: self.processingYear(),
                processInfomation: self.processInfomationDto()
            });
            modal("/view/qmm/005/e/index.xhtml").onClosed(() => {
                self.eScreenReflect();
            });
        }

        eScreenReflect() {
            var self = this;
            var reflect = getShared("QMM005eReflect");
            if (reflect) {
                var params = getShared("QMM005eParams");
                var valPayDateSet = getShared("QMM005eValPayDateSet");
                _.forEach(self.settingPaymentList, function (settingPayment) {
                    var index = 0;
                    var startMonth = getShared("QMM005estartMonth");
                    if (++index >= startMonth) {
                        var basicSetting = valPayDateSet.basicSetting;
                        var advancedSetting = valPayDateSet.advancedSetting;
                        /* B4_10	支払年月日
                         ※1　支払日チェックが入っている場合のみ更新する
                         ※10 勤怠締め日チェックが入っている場合のみ更新する
                         */
                        if (params.dailyPaymentDateCheck && params.timeClosingDateCheck) {
                            // not exist
                            settingPayment.paymentDate = ko.observable(self.processingYear() + '/' + index + '/' + basicSetting.monthlyPaymentDate.datePayMent);
                        }
                        // B4_11    支払曜日
                        // ※1　支払日チェックが入っている場合のみ更新する
                        if (params.dailyPaymentDateCheck) {

                        }
                        // B4_12	社員抽出基準日
                        // ※2　対象社員抽出基準日チェックが入っている場合のみ更新する
                        if (params.empExtractionRefDateCheck) {
                            settingPayment.employeeExtractionReferenceDate = ko.observable(self.processingYear() + '/' + basicSetting.employeeExtractionReferenceDate.refeMonth + '/' + basicSetting.employeeExtractionReferenceDate.refeDate);
                        }

                        // B4_13	社会保険徴収月
                        // ※3　社会保険徴収月チェックが入っている場合のみ更新する
                        if (params.socialInsuranceMonthCheck) {
                            settingPayment.socialInsuranceCollectionMonth = ko.observable(self.processingYear() + '/' + advancedSetting.salaryInsuColMon.monthCollected);
                        }
                        // B4_15	明細書印字年月
                        // ※4　要勤務日数チェックが入っている場合のみ更新する
                        if (params.specPrintDateCheck) {
                            settingPayment.specificationPrintDate = ko.observable(self.processingYear() + '/' + advancedSetting.detailPrintingMon.printingMonth);
                        }
                        // B4_16	要勤務日数
                        // ※5　明細書印字年月チェックが入っている場合のみ更新する
                        if (params.numWorkingDaysCheck) {
                            settingPayment.numberOfWorkingDays = ko.observable(basicSetting.workDay);
                        }
                        // B6_7		社会保険基準日
                        // ※6　社会保険基準日チェックが入っている場合のみ更新する
                        if (params.socialInsuranceDateCheck) {
                            settingPayment.socialInsuranceStandardDate = ko.observable(advancedSetting.sociInsuStanDate.baseYear + '/' + advancedSetting.sociInsuStanDate.baseMonth + '/' + advancedSetting.sociInsuStanDate.baseYear);
                        }
                        // B6_8		雇用保険基準日
                        // ※7　雇用保険基準日チェックが入っている場合のみ更新する
                        if (params.empInsuranceStandardDateCheck) {
                            settingPayment.employmentInsuranceStandardDate = ko.observable((self.processingYear() - 1) + '/' + advancedSetting.empInsurStanDate.baseMonth + advancedSetting.empInsurStanDate.refeDate);
                        }
                        // B6_9		勤怠締め日
                        // ※10 勤怠締め日チェックが入っている場合のみ更新する
                        if (params.timeClosingDateCheck) {
                            settingPayment.timeClosingDate = ko.observable(self.processingYear() + '/' + advancedSetting.closeDate.baseMonth + '/' + advancedSetting.closeDate.refeDate);
                        }

                        /*B6_10	所得税基準日
                         ※8　所得税基準日チェックが入っている場合のみ更新する*/
                        if (params.incomeTaxReferenceCheck) {
                            settingPayment.incomeTaxReferenceDate = ko.observable(self.processingYear() + '/' + advancedSetting.incomTaxBaseYear.baseMonth + '/' + advancedSetting.incomTaxBaseYear.refeDate);
                        }
                        /*B6_11	経理締め日
                         ※9　経理締め日チェックが入っている場合のみ更新する*/
                        if (params.accountingClosureDateCheck) {
                            settingPayment.accountingClosureDate = ko.observable(self.processingYear() + '/' + basicSetting.accountingClosureDate.processMonth + basicSetting.accountingClosureDate.disposalDay);
                        }
                    }
                });
            }
        }

        registration() {
            var self = this;
            //    check input year valid
            var command = []
            var index = 0;
            _.forEach(self.settingPaymentList(), function (setting) {
                index++;
                command.push({
                    setDaySupportCommand: {
                        processCateNo: self.processCateNo,
                        paymentDate: setting.paymentDate(),
                        processDate: self.processingYear() + (index < 10 ? '0' + index : index),
                        closeDateTime: setting.timeClosingDate(),
                        empInsurdStanDate: setting.employmentInsuranceStandardDate(),
                        closureDateAccounting: setting.accountingClosureDate(),
                        empExtraRefeDate: setting.employeeExtractionReferenceDate(),
                        socialInsurdStanDate: setting.socialInsuranceStandardDate(),
                        socialInsurdCollecMonth: setting.socialInsuranceCollectionMonth(),
                        incomeTaxDate: setting.incomeTaxReferenceDate(),
                        numberWorkDay: setting.numberOfWorkingDays()
                    },
                    specPrintYmSetCommand: {
                        printDate: setting.printDate()
                    }
                })
            });
            if (self.isNewMode()) {
                service.addDomainModel(command).done(function () {
                    self.transactionSuccess();
                }).fail(function (error) {
                    nts.uk.ui.dialog.alertError({messageId: error.messageId});
                })
            } else {
                service.updateDomainModel(command).done(function () {
                    self.transactionSuccess();
                }).fail(function (error) {
                    nts.uk.ui.dialog.alertError({messageId: error.messageId});
                })
            }
        }

        transactionSuccess() {
            nts.uk.ui.dialog.info({messageId: "Msg_15"});
            this.processingYear(this.processingYear());
        }
    }
}
