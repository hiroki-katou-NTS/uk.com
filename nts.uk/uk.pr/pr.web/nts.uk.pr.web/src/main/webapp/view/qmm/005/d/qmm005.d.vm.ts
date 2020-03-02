module nts.uk.pr.view.qmm005.d.viewmodel {
    import getText = nts.uk.resource.getText;
    import model = nts.uk.pr.view.qmm005.share.model;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        isAdvanceSetting: KnockoutObservable<boolean> = ko.observable(false);
        enableCloseDate: KnockoutObservable<boolean> = ko.observable(false);
        mode: number;

        valPayDateSet: model.ValPayDateSet;
        basicSetting: model.BasicSetting;
        advancedSetting: model.AdvancedSetting;
        processInfomation: model.ProcessInfomation;
        processCategoryNo: number;

        //basicSetting
        monthlyPaymentDate: model.MonthlyPaymentDate;
        employeeExtractionReferenceDate: model.EmployeeExtractionReferenceDate;
        accountingClosureDate: model.AccountingClosureDate;

        //advancedSetting
        detailPrintingMonth: model.DetailPrintingMonth;
        salaryInsuranceCollecMonth: model.SalaryInsuranceCollecMonth;
        employmentInsuranceStanDate: model.EmploymentInsuranceStanDate;
        incomeTaxBaseYear: model.IncomeTaxBaseYear;
        socialInsuranceStanDate: model.SocialInsuranceStanDate;
        closeDate: model.CloseDate;

        //D2_3
        DiscontinueThisProcessClassification: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getAbolitionItems());
        discontinueThisProcessClassificationSelectedCode: KnockoutObservable<number>;

        //D4_19
        guaranteedBaseDate: KnockoutObservableArray<model.ItemModel>  = ko.observableArray([]);
        guaranteedBaseDateSelectedCode: KnockoutObservable<number>   = ko.observable(1);
        //D3_8
        refeDate: KnockoutObservableArray<model.ItemModel> = ko.observableArray([]);
        refeDateSelectedCode: KnockoutObservable<number>  = ko.observable(0);

        //D3_11
        refeMonth: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getPreviousMonthClassificationItems());
        refeMonthSelectedCode: KnockoutObservable<number>  = ko.observable(0);
        //D4_30
        timeCloseDate: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getTimeCloseDateClassificationItems());
        timeCloseDateSelectedCode: KnockoutObservable<number>  = ko.observable(0);

        //D3_20
        disposalDay: KnockoutObservableArray<model.ItemModel> = ko.observableArray([]);
        disposalDaySelectedCode: KnockoutObservable<number>  = ko.observable(1);

        processName: KnockoutObservable<string> = ko.observable('');
        //D3_3
        datePayment: KnockoutObservableArray<model.ItemModel>       = ko.observableArray([]);
        datePaymentSelectedCode: KnockoutObservable<number>        = ko.observable(1);
        isEnableDiscontinueProcessCls: KnockoutObservable<boolean> = ko.observable(true);

        numberOfWorkingDays: KnockoutObservable<number>            = ko.observable(null);
        selectedId: KnockoutObservable<number>                     = ko.observable(0);

        enableCheckBox: KnockoutObservable<boolean>                = ko.observable(false);

        //d3_17
        processingMonth: KnockoutObservableArray<model.ItemModel>  = ko.observableArray(model.getPreviousMonthClassificationItems());
        processingMonthSelectedCode: KnockoutObservable<number>   = ko.observable(0);

        //D4_3
        printingMonth: KnockoutObservableArray<model.ItemModel>    = ko.observableArray(model.getPreviousMonthClassificationItems());
        printingMonthSelectedCode: KnockoutObservable<number>     = ko.observable(0);

        //D4_9
        monthsCollected: KnockoutObservableArray<model.ItemModel>  = ko.observableArray(model.getSocialInsuColleMonthItems());
        monthsCollectedSelectedCode: KnockoutObservable<number>   = ko.observable(0);

        //D4_15
        guaranteedBaseYear: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getYearSelectClassificationItems());
        guaranteedBaseYearSelectedCode: KnockoutObservable<number>  = ko.observable(0);

        //D4_17
        guaranteedBaseMonth: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getInsuranceStanMonthClassSificationItems());
        guaranteedBaseMonthSelectedCode: KnockoutObservable<number>  = ko.observable(0);

        //D4_23
        employmentInsuranceStandardMonth: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getMonthSelectionSegmentItems());
        employmentInsuranceStandardMonthSelectedCode: KnockoutObservable<number>  = ko.observable(1);

        //D4_25
        employmentInsuranceStandardDate: KnockoutObservableArray<model.ItemModel> = ko.observableArray([]);
        employmentInsuranceStandardDateSelectedCode: KnockoutObservable<number>  = ko.observable(1);

        //D4_34
        timeBaseYear: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getYearSelectClassificationItems());
        timeBaseYearSelectedCode: KnockoutObservable<number>  = ko.observable(0);

        //D4_36
        timeClosingStandardMonth: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getSocialInsuColleMonthItems());
        timeClosingStandardMonthSelectedCode: KnockoutObservable<number>  = ko.observable(0);

        //D4_38
        timeReferenceStandardDay: KnockoutObservableArray<model.ItemModel> = ko.observableArray([]);
        timeReferenceStandardDaySelectedCode: KnockoutObservable<number>  = ko.observable(1);

        //D4_42
        yearSelectClassification: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getYearSelectClassificationItems());
        yearSelectClassificationSelectedCode: KnockoutObservable<number>  = ko.observable(0);

        //D4_44
        incomeTaxBaseMonth: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getMonthSelectionSegmentItems());
        incomeTaxBaseMonthSelectedCode: KnockoutObservable<number>  = ko.observable(1);

        //D4_46
        incomeTaxBaseDate: KnockoutObservableArray<model.ItemModel>  = ko.observableArray([]);
        incomeTaxBaseDateSelectedCode: KnockoutObservable<number>   = ko.observable(1);

        processCategoryNoDisPlay: string;

        constructor() {
            let self = this,
                init = getShared('QMM005_output_D');

            self.mode = init.mode;
            self.processInfomation = new model.ProcessInfomation(ko.toJS(init.processInfomation));
            self.processCategoryNo = self.processInfomation.processCateNo;
            self.processCategoryNoDisPlay = nts.uk.text.format(nts.uk.resource.getText("QMM005_98"), self.processCategoryNo);
            if (self.processCategoryNo === 1) {
                self.isEnableDiscontinueProcessCls(false)
            }

            self.timeCloseDateSelectedCode.subscribe(function () {
                if (self.timeCloseDateSelectedCode() == 0)
                    self.enableCloseDate(false);
                else
                    self.enableCloseDate(true);
            });

            self.pushDaytoList(self.disposalDay, model.DateSelectClassification);
            self.pushDaytoList(self.datePayment, model.DateSelectClassification);
            self.pushDaytoList(self.refeDate, model.DateSelectClassification);
            self.pushDaytoList(self.employmentInsuranceStandardDate, model.DateSelectClassification);
            self.pushDaytoList(self.timeReferenceStandardDay, model.DateSelectClassification);
            self.pushDaytoList(self.guaranteedBaseDate, model.DateSelectClassification);
            self.pushDaytoList(self.incomeTaxBaseDate, model.DateSelectClassification);

            if (self.mode == 0) {
                self.discontinueThisProcessClassificationSelectedCode = ko.observable(self.enableCheckBox() ? model.Abolition.ABOLITION : model.Abolition.NOT_ABOLITION);
            } else {
                self.discontinueThisProcessClassificationSelectedCode = ko.observable(model.Abolition.NOT_ABOLITION);
            }
        }

        saveCharacterSetting(): void {
            let self = this;
            $('.nts-input').trigger("validate");
            if (nts.uk.ui.errors.hasError()) {
                return;
            }
            this.saveToDB();
        }

        cancelCharacterSetting(): void {
            let self = this;
            let param = {
                action: 3,
                processInfomationUpdate: self.processInfomation
            };

            setShared("QMM005_output_A", param);
            nts.uk.ui.windows.close();

        }

        reSize(): void {
            let self = this;
            let windowSize = nts.uk.ui.windows.getSelf();

            if (!self.isAdvanceSetting()) {
                windowSize.$dialog.height(550);
                self.isAdvanceSetting(true);
            } else {
                self.isAdvanceSetting(false);
                windowSize.$dialog.height(460);
            }

            $('html').css('overflow', 'hidden');
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            dfd.resolve();
            if (self.mode == 0) {
                service.findDisplayRegister(this.processCategoryNo).done(function (data) {
                    console.log(data);
                    self.valPayDateSet = new model.ValPayDateSet(data);
                    console.log(self.valPayDateSet);

                    //basic
                    self.datePaymentSelectedCode(self.valPayDateSet.basicSetting.monthlyPaymentDate.datePayMent);
                    self.refeDateSelectedCode(self.valPayDateSet.basicSetting.employeeExtractionReferenceDate.refeDate);
                    self.refeMonthSelectedCode(self.valPayDateSet.basicSetting.employeeExtractionReferenceDate.refeMonth);
                    self.disposalDaySelectedCode(self.valPayDateSet.basicSetting.accountingClosureDate.disposalDay);
                    self.processingMonthSelectedCode(self.valPayDateSet.basicSetting.accountingClosureDate.processMonth);
                    self.numberOfWorkingDays(self.valPayDateSet.basicSetting.workDay);
                    //advan

                    self.timeCloseDateSelectedCode(self.valPayDateSet.advancedSetting.closeDate.timeCloseDate);
                    self.timeBaseYearSelectedCode(self.valPayDateSet.advancedSetting.closeDate.baseYear);
                    self.timeReferenceStandardDaySelectedCode(self.valPayDateSet.advancedSetting.closeDate.refeDate);
                    self.timeClosingStandardMonthSelectedCode(self.valPayDateSet.advancedSetting.closeDate.baseMonth);

                    self.yearSelectClassificationSelectedCode(self.valPayDateSet.advancedSetting.incomTaxBaseYear.baseYear);
                    self.incomeTaxBaseMonthSelectedCode(self.valPayDateSet.advancedSetting.incomTaxBaseYear.baseMonth);
                    self.incomeTaxBaseDateSelectedCode(self.valPayDateSet.advancedSetting.incomTaxBaseYear.refeDate);
                    self.printingMonthSelectedCode(self.valPayDateSet.advancedSetting.detailPrintingMon.printingMonth);
                    self.monthsCollectedSelectedCode(self.valPayDateSet.advancedSetting.salaryInsuColMon.monthCollected);
                    self.guaranteedBaseYearSelectedCode(self.valPayDateSet.advancedSetting.sociInsuStanDate.baseYear);
                    self.guaranteedBaseMonthSelectedCode(self.valPayDateSet.advancedSetting.sociInsuStanDate.baseMonth);
                    self.guaranteedBaseDateSelectedCode(self.valPayDateSet.advancedSetting.sociInsuStanDate.refeDate);
                    self.employmentInsuranceStandardDateSelectedCode(self.valPayDateSet.advancedSetting.empInsurStanDate.refeDate);
                    self.employmentInsuranceStandardMonthSelectedCode(self.valPayDateSet.advancedSetting.empInsurStanDate.baseMonth);


                    self.processCategoryNo = self.processInfomation.processCateNo;
                    self.processName(self.processInfomation.processCls());
                    self.discontinueThisProcessClassificationSelectedCode(self.processInfomation.deprecatCate);
                    self.enableCheckBox(self.processInfomation.deprecatCate != model.Abolition.NOT_ABOLITION);
                })
            }
            return dfd.promise();
        }

        saveToDB(): void {
            let self = this;

            //basic
            self.monthlyPaymentDate = new model.MonthlyPaymentDate(self.datePaymentSelectedCode());
            self.employeeExtractionReferenceDate = new model.EmployeeExtractionReferenceDate(self.refeDateSelectedCode(), self.refeMonthSelectedCode());
            self.accountingClosureDate = new model.AccountingClosureDate(self.disposalDaySelectedCode(), self.processingMonthSelectedCode());
            //advan
            self.closeDate = new model.CloseDate(self.timeCloseDateSelectedCode(), self.timeBaseYearSelectedCode(), self.timeReferenceStandardDaySelectedCode(), self.timeClosingStandardMonthSelectedCode());
            self.incomeTaxBaseYear = new model.IncomeTaxBaseYear(self.yearSelectClassificationSelectedCode(), self.incomeTaxBaseMonthSelectedCode(), self.incomeTaxBaseDateSelectedCode());
            self.detailPrintingMonth = new model.DetailPrintingMonth(self.printingMonthSelectedCode());
            self.salaryInsuranceCollecMonth = new model.SalaryInsuranceCollecMonth(self.monthsCollectedSelectedCode());
            self.socialInsuranceStanDate = new model.SocialInsuranceStanDate(self.guaranteedBaseYearSelectedCode(), self.guaranteedBaseMonthSelectedCode(), self.guaranteedBaseDateSelectedCode());
            self.employmentInsuranceStanDate = new model.EmploymentInsuranceStanDate(self.employmentInsuranceStandardDateSelectedCode(), self.employmentInsuranceStandardMonthSelectedCode());


            let advancedSettingParam = {
                closeDate: self.closeDate,
                incomTaxBaseYear: self.incomeTaxBaseYear,
                detailPrintingMon: self.detailPrintingMonth,
                sociInsuStanDate: self.socialInsuranceStanDate,
                salaryInsuColMon: self.salaryInsuranceCollecMonth,
                empInsurStanDate: self.employmentInsuranceStanDate,
            };

            let bassicSettingParam = {
                monthlyPaymentDate: self.monthlyPaymentDate,
                employeeExtractionReferenceDate: self.employeeExtractionReferenceDate,
                accountingClosureDate: self.accountingClosureDate,
                workDay: nts.uk.ntsNumber.getDecimal(self.numberOfWorkingDays(), 2)
            };

            self.basicSetting = new model.BasicSetting(bassicSettingParam);
            self.advancedSetting = new model.AdvancedSetting(advancedSettingParam);

            let ValPayDateSetParam = {

                processCateNo: self.processCategoryNo,
                basicSetting: self.basicSetting,
                advancedSetting: self.advancedSetting
            };

            self.valPayDateSet = new model.ValPayDateSet(ValPayDateSetParam);

            let processInfomationParam = {

                processCateNo: self.processCategoryNo,
                processCls: self.processName(),
                deprecatCate: self.discontinueThisProcessClassificationSelectedCode()

            };

            self.processInfomation = new model.ProcessInfomation(processInfomationParam);

            if (self.mode == 1) {
                service.registerprocessingsegment({
                    processInformation: ko.toJS(self.processInfomation),
                    valPayDateSet: ko.toJS(self.valPayDateSet)
                }).done(function () {
                    let param = {
                        action: 1,
                        processInfomationUpdate: self.processInfomation
                    };

                    setShared("QMM005_output_A", param);
                    nts.uk.ui.windows.close();
                })
            }

            if (self.mode == 0) {
                self.enableCheckBox() ? self.processInfomation.deprecatCate = model.Abolition.ABOLITION : self.processInfomation.deprecatCate = model.Abolition.NOT_ABOLITION;

                service.updateprocessingsegment({
                    processInformation: ko.toJS(self.processInfomation),
                    valPayDateSet: ko.toJS(self.valPayDateSet)
                }).done(function () {
                    let param = {
                        action: 0,
                        processInfomationUpdate: self.processInfomation
                    };

                    setShared("QMM005_output_A", ko.toJS(param));
                    nts.uk.ui.windows.close();
                })
            }
        }


        pushDaytoList(itemList: KnockoutObservableArray<model.ItemModel>, codeEnum: any): void {
            for (let i = 1; i < 31; i++) {
                itemList.push(new model.ItemModel(i, getText('Enum_DateSelectClassification_DAY_' + i)));
            }
            itemList.push(new model.ItemModel(31, getText('Enum_DateSelectClassification_LAST_DAY_MONTH')));
        }
    }
}