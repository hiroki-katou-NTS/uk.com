module nts.uk.pr.view.qmm005.b.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import model = nts.uk.pr.view.qmm005.share.model;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import modal = nts.uk.ui.windows.sub.modal;
    import hasError = nts.uk.ui.errors.hasError;
    const SOCIAL_INSU_COLLE_MONTH_INDEX = 2;
    const DETAIL_PRINTING_MON_INDEX = 0;
    const SOCI_INSU_BASE_YEAR_INDEX = 1;
    const SOCI_INSU_BASE_MONTH_INDEX = 1;
    const INCOM_TAX_BASEYEAR_YEAR_INDEX = 1;
    const CLOSE_DATE_YEAR_INDEX = 1;
    const CLOSE_DATE_MONTH_INDEX = 2;

    export class ScreenModel {
        processCateNo: any;
        processInfomationDto: any;
        setDaySupportDtoList: any;
        targetMonth: KnockoutObservable<string>;
        processingYear: KnockoutObservable<number>;
        processingYearNative: number;
        processingDivisionName: KnockoutObservable<string>;
        settingPaymentList: KnockoutObservableArray<any>;
        processingYearList: KnockoutObservableArray<model.ItemModel>;
        btnText: any;
        isNewMode: KnockoutObservable<boolean>;
        show: KnockoutObservable<boolean>;

        constructor() {
            var self = this;
            self.targetMonth = ko.observable();
            self.processingDivisionName = ko.observable();
            self.settingPaymentList = ko.observableArray([]);
            self.processingYearList = ko.observableArray([]);
            self.show = ko.observable(false);
            self.btnText = ko.computed(function () {
                if (self.show()) {
                    return "-";
                } else {
                    return "+";
                }
            });
            self.isNewMode = ko.observable(false);
            self.processingYear = ko.observable(null);
            self.processingYearNative = null;
            self.processingYear.subscribe(function (newValue) {
                if (newValue != self.processingYearNative && newValue != '') {
                    self.processingYearNative = newValue;
                    self.selectProcessingYear(newValue);
                    self.processingYear(newValue);
                    nts.uk.ui.errors.clearAll();
                }
            });
        }

        blankData() {
            var self = this;
            for (var i = 0; i < self.settingPaymentList().length; i++) {
                for (k in self.settingPaymentList()[i]) {
                    var key = k.toString();
                    if (key != 'targetMonth') {
                        self.settingPaymentList()[i][key](null);
                    }
                }
            }
        }

        toggle(): void {
            this.show(!this.show());
            if ($('#B5_1')) {
                $('#B5_1').focus();
            }
        }

        cancel() {
            nts.uk.ui.windows.close();
        }

        startPage(params): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.startupScreen(null);
            dfd.resolve();
            return dfd.promise();
        }

        startupScreen(selectItem) {
            // get domain 処理区分基本情報 (ProcessInfomation), 給与支払日設定 (SetDaySupport)
            var self = this;
            self.processCateNo = getShared("QMM005_output_B");
            service.getProcessInfomation(self.processCateNo).done(function (data) {
                if (data) {
                    self.processInfomationDto = data;
                    // B3_2
                    self.processingDivisionName(nts.uk.text.format(nts.uk.resource.getText("QMM005_97"), self.processCateNo, data.processDivisionName));
                }
            });
            service.getSetDaySupport(self.processCateNo).done(function (data) {
                // B2_2
                // 詳細設定対象_処理年 processingYearList
                var array = [];
                _.forEach(data, function (setDaySupport) {
                    _.forEach(setDaySupport, function (value, key) {
                        if (key == "processDate") {
                            let year = value.toString().substr(0, 4);
                            array.push(new model.ItemModel(year, year + '(' + nts.uk.time.yearInJapanEmpire(value.toString()).toString().split(' ').slice(0, 3).join('') + ')'));
                        }
                    });
                });
                self.processingYearList(_.orderBy(_.uniqBy(array, 'code'), ['code'], ['desc']));
                if (array.length > 0) {
                    if(selectItem){
                        self.processingYearNative = parseInt(selectItem);
                        self.processingYear(selectItem);
                        self.selectProcessingYear(selectItem);
                    }else {
                        self.processingYearNative = parseInt(self.processingYearList()[0].code);
                        self.processingYear(self.processingYearList()[0].code);
                        self.selectProcessingYear(self.processingYearList()[0].code);
                    }
                }
            });
        }

        selectProcessingYear(year) {
            /* phần lớn dữ liệu lấy từ setDaySupport*/
            // B3_4				処理年
            var self = this;
            if (year == null) return;
            service.getSelectProcessingYear(self.processCateNo, year).done(function (data) {
                if (data.setDaySupportDtoList.length > 0) {
                    var firstArray = [];
                    var index = 0;
                    _.forEach(data.setDaySupportDtoList, function (setDaySupport) {
                        var obj = {};
                        obj["targetMonth"] = ko.observable(++index + '月の設定');
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
                    if ($('#B2_2_container')) {
                        $('#B2_2_container').focus();
                    }
                } else {
                    self.blankData();
                }
            });
        }

        creatNewProcessYear() {
            var self = this;
            self.isNewMode(true);
            if ($('#B2_2_container')) {
                $('#B2_2_container').focus();
            }
            self.processingYear(null);
            self.blankData();
        }

        //反映ボタン押下時処理
        //screen E in insert reflect
        reflectionPressingProcess() {
            var self = this;
            nts.uk.ui.errors.clearAll();
            $('input#B3_4').ntsError('check');
            if(hasError()){
                return;
            }
            if (self.processingYear()) {
                var array = [];
                service.getValPayDateSet(self.processCateNo).done(function (data) {
                        for (index = 1; index < 13; index++) {
                            let objItem = {
                                targetMonth: ko.observable(index + '月の設定'),
                                paymentDate: ko.observable(self.transDate(self.preDateTime(self.processingYear(), index, data.basicSetting.monthlyPaymentDate.datePayMent))),
                                employeeExtractionReferenceDate: ko.observable(self.preDateTime(self.processingYear(), index, data.basicSetting.employeeExtractionReferenceDate.refeDate)),
                                socialInsuranceCollectionMonth: ko.observable(parseInt(self.processingYear() + self.fullMonth(index))),
                                specificationPrintDate: ko.observable(parseInt(self.processingYear() + '' + index)),
                                numberOfWorkingDays: ko.observable(data.basicSetting.workDay),
                                socialInsuranceStandardDate: ko.observable(self.preDateTime(self.processingYear(), index, data.advancedSetting.sociInsuStanDate.refeDate)),
                                employmentInsuranceStandardDate: ko.observable(self.preDateTime((self.processingYear() - 1), index, data.advancedSetting.empInsurStanDate.refeDate)),
                                timeClosingDate: ko.observable(self.preDateTime(self.processingYear(), index, data.advancedSetting.closeDate.refeDate)),
                                incomeTaxReferenceDate: ko.observable(self.preDateTime(self.processingYear(), data.advancedSetting.incomTaxBaseYear.baseMonth, data.advancedSetting.incomTaxBaseYear.refeDate)),
                                accountingClosureDate: ko.observable(self.preDateTime(self.processingYear(), index, data.basicSetting.accountingClosureDate.disposalDay))
                            }
                            array.push(objItem);
                        }
                        self.settingPaymentList(array);
                    if ($('#B3_6')) {
                        $('#B3_6').focus();
                    }
                    }
                );
            }
        }

        transDate(param) {
            let date = new Date(param);
            let newDate;
            if (date.getDay() == 0) {
                newDate = new Date(date.getFullYear(), date.getMonth(), date.getDate() - 2);
            } else if (date.getDate() == 6) {
                newDate = new Date(date.getFullYear(), date.getMonth(), date.getDate() - 1);
            } else {
                newDate = date;
            }
            return newDate.getFullYear() + '/' + (newDate.getMonth() + 1) + '/' + newDate.getDate();
        }

        //screen E mode update
        reflectSystemReference() {
            var self = this;
            setShared("QMM005bParams", {
                processCateNo: self.processCateNo,
                processingYear: self.processingYear(),
                processInfomation: self.processInfomationDto
            });
            modal("/view/qmm/005/e/index.xhtml").onClosed(() => {
                self.eScreenReflect();
                if ($('#B3_7')) {
                    $('#B3_7').focus();
                }
            });
        }

        preDateTime(year, month, date) {
            if (month == 2 && date > 28) {
                if (year % 4 == 0) {
                    return year + '/02/29';
                } else if (year % 4 != 0) {
                    return year + '/02/28';
                }
            } else {
                return year + '/' + (month < 10 ? '0' + month : month) + '/' + date;
            }
        }

        eScreenReflect() {
            var self = this;
            var params = getShared("QMM005eParams");
            if (params.reflect) {
                for (var index = 0; index < self.settingPaymentList().length;) {
                    var settingPayment = self.settingPaymentList()[index];
                    ++index;
                    var basicSetting = params.valPayDateSet.basicSetting;
                    var advancedSetting = params.valPayDateSet.advancedSetting;
                    if (params.startMonth <= index) {
                        /* B4_10	支払年月日
                         ※1　支払日チェックが入っている場合のみ更新する
                         ※10 勤怠締め日チェックが入っている場合のみ更新する
                         */
                        if (params.checkbox.dailyPaymentDateCheck && params.checkbox.timeClosingDateCheck) {
                            settingPayment.paymentDate(self.preDateTime(self.processingYear(), index, basicSetting.monthlyPaymentDate.datePayMent));
                        }
                        // B4_12	社員抽出基準日
                        // ※2　対象社員抽出基準日チェックが入っている場合のみ更新する
                        if (params.checkbox.empExtractionRefDateCheck) {
                            let month = index - parseInt(basicSetting.employeeExtractionReferenceDate.refeMonth);
                            settingPayment.employeeExtractionReferenceDate(self.preDateTime(self.passYear(self.processingYear(), month, false).year, self.passYear(self.processingYear(), month, false).month, basicSetting.employeeExtractionReferenceDate.refeDate));
                        }
                        // B4_13	社会保険徴収月
                        // ※3　社会保険徴収月チェックが入っている場合のみ更新する
                        if (params.checkbox.socialInsuranceMonthCheck) {
                            let year = parseInt(<string>self.processingYear());
                            let month = index + parseInt(advancedSetting.salaryInsuColMon.monthCollected) - SOCIAL_INSU_COLLE_MONTH_INDEX;
                            settingPayment.socialInsuranceCollectionMonth(self.passYear(year, month, false).year.toString() +  self.fullMonth(self.passYear(year, month, false).month));
                        }
                        // B4_15	明細書印字年月
                        // ※4　要勤務日数チェックが入っている場合のみ更新する
                        if (params.checkbox.specPrintDateCheck) {
                            let year = parseInt(<string>self.processingYear());
                            let month = index + parseInt(advancedSetting.detailPrintingMon.printingMonth) - DETAIL_PRINTING_MON_INDEX;
                            settingPayment.specificationPrintDate(self.passYear(year, month, false).year.toString() + self.fullMonth(self.passYear(year, month, false).month));
                        }
                        // B4_16	要勤務日数
                        // ※5　明細書印字年月チェックが入っている場合のみ更新する
                        if (params.checkbox.numWorkingDaysCheck) {
                            settingPayment.numberOfWorkingDays(basicSetting.workDay);
                        }
                        // B6_7		社会保険基準日
                        // ※6　社会保険基準日チェックが入っている場合のみ更新する
                        if (params.checkbox.socialInsuranceDateCheck) {
                            let year = parseInt(<string>self.processingYear()) + parseInt(advancedSetting.sociInsuStanDate.baseYear) - SOCI_INSU_BASE_YEAR_INDEX;
                            let month = parseInt(advancedSetting.sociInsuStanDate.baseMonth);
                            if (month == 0 || month == 1) {
                                month = index + advancedSetting.sociInsuStanDate.baseMonth - SOCI_INSU_BASE_MONTH_INDEX;
                            } else {
                                month = month - SOCI_INSU_BASE_MONTH_INDEX;
                            }
                            let date = parseInt(advancedSetting.sociInsuStanDate.refeDate);
                            settingPayment.socialInsuranceStandardDate(self.preDateTime(self.passYear(year, month, false).year, self.passYear(year, month, false).month, date));
                        }
                        // B6_8		雇用保険基準日
                        // ※7　雇用保険基準日チェックが入っている場合のみ更新する
                        if (params.checkbox.empInsuranceStandardDateCheck) {
                            settingPayment.employmentInsuranceStandardDate(self.preDateTime(self.processingYear(), advancedSetting.empInsurStanDate.baseMonth, advancedSetting.empInsurStanDate.refeDate));
                        }
                        // B6_9		勤怠締め日
                        // ※10 勤怠締め日チェックが入っている場合のみ更新する
                        if (params.checkbox.timeClosingDateCheck) {
                            if (advancedSetting.closeDate.timeCloseDate == model.TimeCloseDateClassification.SAME_DATE) {
                                settingPayment.timeClosingDate(settingPayment.employeeExtractionReferenceDate());
                            } else {
                                let year = parseInt(<string>self.processingYear()) + parseInt(advancedSetting.closeDate.baseYear) - CLOSE_DATE_YEAR_INDEX;
                                let month = index  + parseInt(advancedSetting.closeDate.baseMonth) - CLOSE_DATE_MONTH_INDEX;
                                settingPayment.timeClosingDate(self.preDateTime(self.passYear(year, month, false).year, self.passYear(year, month, false).month, advancedSetting.closeDate.refeDate));
                            }
                        }
                        /*B6_10	所得税基準日
                         ※8　所得税基準日チェックが入っている場合のみ更新する*/
                        if (params.checkbox.incomeTaxReferenceCheck) {
                            let year = parseInt(<string>self.processingYear()) + parseInt(advancedSetting.incomTaxBaseYear.baseYear) - INCOM_TAX_BASEYEAR_YEAR_INDEX;
                            settingPayment.incomeTaxReferenceDate(self.preDateTime(year, advancedSetting.incomTaxBaseYear.baseMonth, advancedSetting.incomTaxBaseYear.refeDate));
                        }


                        /*B6_11	経理締め日
                         ※9　経理締め日チェックが入っている場合のみ更新する*/
                        if (params.checkbox.accountingClosureDateCheck) {
                            let month = index - parseInt(basicSetting.accountingClosureDate.processMonth); //THIS_MONTH:0 LAST_MONTH :1
                            settingPayment.accountingClosureDate(self.preDateTime(self.passYear(self.processingYear(), month, false).year, self.passYear(self.processingYear(), month, false).month, basicSetting.accountingClosureDate.disposalDay));
                        }
                    }
                }
            }
        }

        passYear(year, month, flag) {
            year = parseInt(year);
            month = parseInt(month);
            if (month > -11 && month < 1) {
                month = month + 12;
                return flag ? (year - 1) + '/' + (month < 10 ? '0' + month : month) : {year: year - 1, month: month};
            } else if (month > 12 && month < 24) {
                month = month - 12;
                return flag ? (year + 1) + '/' + (month < 10 ? '0' + month : month) : {year: year + 1, month: month};
            } else {
                return flag ? year + '/' + (month < 10 ? '0' + month : month) : {year: year, month: month};
            }
        }

        registration() {
            let self = this;
            $('.nts-input').trigger("validate");
            if(hasError()){
                return;
            }
            //    check input year valid
            let arrayItem = [];
            let index = 0;
            _.forEach(self.settingPaymentList(), function (setting) {
                index++;
                arrayItem.push({
                    setDaySupportCommand: {
                        processCateNo: self.processCateNo,
                        paymentDate: setting.paymentDate(),
                        processDate: self.processingYear() + self.fullMonth(index),
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
                        printDate: setting.specificationPrintDate(),
                        processCateNo: self.processCateNo,
                        processDate: self.processingYear() + self.fullMonth(index),
                    }
                })
            });
            let commandData = {paymentDateSettingCommands: arrayItem}
            if (self.isNewMode()) {
                service.addDomainModel(commandData).done(function (data) {
                    self.transactionSuccess(self.processingYear());
                }).fail(function (error) {
                    nts.uk.ui.dialog.alertError({messageId: error.messageId});
                })
            } else {
                service.updateDomainModel(commandData).done(function (data) {
                    self.transactionSuccess(self.processingYear());
                }).fail(function (error) {
                    nts.uk.ui.dialog.alertError({messageId: error.messageId});
                })
            }
        }

        fullMonth(month) {
            return (month < 10 ? '0' + month : month).toString();
        }

        transactionSuccess(year) {
            let self = this;
            if(self.isNewMode()){
                self.startupScreen(year);
            }else {
                nts.uk.ui.dialog.info({messageId: "Msg_15"});
            }
            self.selectProcessingYear(year);
            self.isNewMode(false);
        }
    }
}
