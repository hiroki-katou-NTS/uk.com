module nts.uk.pr.view.qmm005.d.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import model = nts.uk.pr.view.qmm005.share.model;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import close=nts.uk.ui.windows.close;


    export class ScreenModel {

        isAdvanceSetting:KnockoutObservable<boolean>=ko.observable(false);
        enableCloseDate: KnockoutObservable<boolean>;
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
        DiscontinueThisProcessClassification: KnockoutObservableArray<model.Abolition>;
        DiscontinueThisProcessClassificationSelectedCode: KnockoutObservable<number>;

        //D4_19
        guaranteedBaseDate: KnockoutObservableArray<model.ItemModel>;
        guaranteedBaseDateSelectedCode: KnockoutObservable<number>;
        //D3_8
        refeDate: KnockoutObservableArray<model.ItemModel>;
        refeDateSelectedCode: KnockoutObservable<number>;

        //D3_11
        refeMonth: KnockoutObservableArray<model.ItemModel>;
        refeMonthSelectedCode: KnockoutObservable<number>;
        //D4_30
        timeCloseDate: KnockoutObservableArray<model.ItemModel>;
        timeCloseDateSelectedCode: KnockoutObservable<number>;

        //D3_20
        disposalDay: KnockoutObservableArray<model.ItemModel>;
        disposalDaySelectedCode: KnockoutObservable<number>;


        processName: KnockoutObservable<string>;
        //D3_3
        datePayment: KnockoutObservableArray<model.ItemModel>;
        datePaymentSelectedCode: KnockoutObservable<number>;

        isEnable: KnockoutObservable<boolean>;


        numberOfWorkingDays: KnockoutObservable<number>;
        selectedId: KnockoutObservable<number>;


        enableCheckBox: KnockoutObservable<boolean>;


        //d3_17
        processingMonth: KnockoutObservableArray<model.ItemModel>;
        processingMonthSelectedCode: KnockoutObservable<number>;

        labelRequired: KnockoutObservable<boolean>;

        //D4_3
        printingMonth: KnockoutObservableArray<model.ItemModel>;
        printingMonthSelectedCode: KnockoutObservable<number>;

        //D4_9
        monthsCollected: KnockoutObservableArray<model.ItemModel>;
        monthsCollectedSelectedCode: KnockoutObservable<number>;

        //D4_15
        guaranteedBaseYear: KnockoutObservableArray<model.ItemModel>;
        guaranteedBaseYearSelectedCode: KnockoutObservable<number>;

        //D4_17
        guaranteedBaseMonth: KnockoutObservableArray<model.ItemModel>;
        guaranteedBaseMonthSelectedCode: KnockoutObservable<number>;


        //D4_23
        employmentInsuranceStandardMonth: KnockoutObservableArray<model.ItemModel>;
        employmentInsuranceStandardMonthSelectedCode: KnockoutObservable<number>;


        //D4_25
        employmentInsuranceStandardDate: KnockoutObservableArray<model.ItemModel>;
        employmentInsuranceStandardDateSelectedCode: KnockoutObservable<number>;

        //D4_34
        timeBaseYear: KnockoutObservableArray<model.ItemModel>;
        timeBaseYearSelectedCode: KnockoutObservable<number>;

        //D4_36
        timeClosingStandardMonth: KnockoutObservableArray<model.ItemModel>;
        timeClosingStandardMonthSelectedCode: KnockoutObservable<number>;

        //D4_38
        timeReferenceStandardDay: KnockoutObservableArray<model.ItemModel>;
        timeReferenceStandardDaySelectedCode: KnockoutObservable<number>;

        //D4_42
        yearSelectClassification: KnockoutObservableArray<model.ItemModel>;
        yearSelectClassificationSelectedCode: KnockoutObservable<number>;

        //D4_44
        incomeTaxBaseMonth: KnockoutObservableArray<model.ItemModel>;
        incomeTaxBaseMonthSelectedCode: KnockoutObservable<number>;

        //D4_46
        incomeTaxBaseDate: KnockoutObservableArray<model.ItemModel>;
        incomeTaxBaseDateSelectedCode: KnockoutObservable<number>;

        processCategoryNoDisPlay:string;

        constructor() {

            var self = this;


            let init=getShared('QMM005_output_D');
            self.mode=init.mode;
            self.processInfomation=new model.ProcessInfomation(ko.toJS(init.processInfomation));
            self.processCategoryNo=self.processInfomation.processCateNo;

            self.processCategoryNoDisPlay=nts.uk.text.format(nts.uk.resource.getText("QMM005_98"), self.processCategoryNo);





            self.numberOfWorkingDays = ko.observable(null);
            self.enableCloseDate = ko.observable(false);
            //d2_2
            self.processName = ko.observable('');

            self.DiscontinueThisProcessClassification = ko.observableArray([
                new model.ItemModel(model.Abolition.Abolition, 'Abolition'),
                new model.ItemModel(model.Abolition.Not_Abolition, 'Not_Abolition')
            ]);


            //D4_42
            self.yearSelectClassification = ko.observableArray([
                new model.ItemModel(model.YearSelectClassification.LAST_YEAR, getText('Enum_YearSelectClassification_PREVIOUS_YEAR')),
                new model.ItemModel(model.YearSelectClassification.THIS_YEAR, getText('Enum_YearSelectClassification_THIS_YEAR')),
                new model.ItemModel(model.YearSelectClassification.AFTER_YEAR, getText('Enum_YearSelectClassification_AFTER_YEAR')),
                new model.ItemModel(model.YearSelectClassification.LEAP_YEAR, getText('Enum_YearSelectClassification_LEAP_YEAR'))
            ]);
            self.yearSelectClassificationSelectedCode = ko.observable(0);

            //D4_44
            self.incomeTaxBaseMonth = ko.observableArray([
                new model.ItemModel(model.MonthSelectionSegment.JANUARY, getText('Enum_MonthSelectionSegment_JANUARY')),
                new model.ItemModel(model.MonthSelectionSegment.FEBRUARY, getText('Enum_MonthSelectionSegment_FEBRUARY')),
                new model.ItemModel(model.MonthSelectionSegment.MARCH, getText('Enum_MonthSelectionSegment_MARCH')),
                new model.ItemModel(model.MonthSelectionSegment.APRIL, getText('Enum_MonthSelectionSegment_APRIL')),
                new model.ItemModel(model.MonthSelectionSegment.MAY, getText('Enum_MonthSelectionSegment_SECOND_MAY')),
                new model.ItemModel(model.MonthSelectionSegment.JUNE, getText('Enum_MonthSelectionSegment_JUNE')),
                new model.ItemModel(model.MonthSelectionSegment.JULY, getText('Enum_MonthSelectionSegment_JULY')),
                new model.ItemModel(model.MonthSelectionSegment.AUGUST, getText('Enum_MonthSelectionSegment_AUGUST')),
                new model.ItemModel(model.MonthSelectionSegment.SEPTEMBER, getText('Enum_MonthSelectionSegment_SEPTEMBER')),
                new model.ItemModel(model.MonthSelectionSegment.OCTOBER, getText('Enum_MonthSelectionSegment_SECOND_OCTOBER')),
                new model.ItemModel(model.MonthSelectionSegment.NOVEMBER, getText('Enum_MonthSelectionSegment_NOVEMBER')),
                new model.ItemModel(model.MonthSelectionSegment.DECEMBER, getText('Enum_MonthSelectionSegment_DECEMBER'))
            ]);
            self.incomeTaxBaseMonthSelectedCode = ko.observable(1);

            //D4_46
            self.incomeTaxBaseDate = ko.observableArray([]);
            self.incomeTaxBaseDateSelectedCode = ko.observable(1);


            //D4_34
            self.timeBaseYear = ko.observableArray([
                new model.ItemModel(model.YearSelectClassification.LAST_YEAR, getText('Enum_YearSelectClassification_PREVIOUS_YEAR')),
                new model.ItemModel(model.YearSelectClassification.THIS_YEAR, getText('Enum_YearSelectClassification_THIS_YEAR')),
                new model.ItemModel(model.YearSelectClassification.AFTER_YEAR, getText('Enum_YearSelectClassification_AFTER_YEAR')),
                new model.ItemModel(model.YearSelectClassification.LEAP_YEAR, getText('Enum_YearSelectClassification_LEAP_YEAR'))
            ]);
            self.timeBaseYearSelectedCode = ko.observable(0);


            //D4_36
            self.timeClosingStandardMonth = ko.observableArray([
                new model.ItemModel(model.SocialInsuColleMonth.BEFORE_MONTH, getText('Enum_SocialInsuColleMonth_BEFORE_MONTH')),
                new model.ItemModel(model.SocialInsuColleMonth.LAST_MONTH, getText('Enum_SocialInsuColleMonth_LAST_MONTH')),
                new model.ItemModel(model.SocialInsuColleMonth.MONTH, getText('Enum_SocialInsuColleMonth_MONTH')),
                new model.ItemModel(model.SocialInsuColleMonth.NEXT_MONTH, getText('Enum_SocialInsuColleMonth_NEXT_MONTH')),
                new model.ItemModel(model.SocialInsuColleMonth.SECOND_FOLLOWING_MONTH, getText('Enum_SocialInsuColleMonth_SECOND_FOLLOWING_MONTH')),
            ]);
            self.timeClosingStandardMonthSelectedCode = ko.observable(0);

            //D4_38
            self.timeReferenceStandardDay = ko.observableArray([]);
            self.timeReferenceStandardDaySelectedCode = ko.observable(1);

            //D4_19
            self.guaranteedBaseDate = ko.observableArray([]);
            self.guaranteedBaseDateSelectedCode = ko.observable(1);


            //D3_3
            self.disposalDay = ko.observableArray([]);
            self.disposalDaySelectedCode = ko.observable(1);

            //D4_23
            self.employmentInsuranceStandardDate = ko.observableArray([]);
            self.employmentInsuranceStandardDateSelectedCode = ko.observable(1);


            //D4_25
            self.employmentInsuranceStandardMonth = ko.observableArray([
                new model.ItemModel(model.MonthSelectionSegment.JANUARY, getText('Enum_MonthSelectionSegment_JANUARY')),
                new model.ItemModel(model.MonthSelectionSegment.FEBRUARY, getText('Enum_MonthSelectionSegment_FEBRUARY')),
                new model.ItemModel(model.MonthSelectionSegment.MARCH, getText('Enum_MonthSelectionSegment_MARCH')),
                new model.ItemModel(model.MonthSelectionSegment.APRIL, getText('Enum_MonthSelectionSegment_APRIL')),
                new model.ItemModel(model.MonthSelectionSegment.MAY, getText('Enum_MonthSelectionSegment_SECOND_MAY')),
                new model.ItemModel(model.MonthSelectionSegment.JUNE, getText('Enum_MonthSelectionSegment_JUNE')),
                new model.ItemModel(model.MonthSelectionSegment.JULY, getText('Enum_MonthSelectionSegment_JULY')),
                new model.ItemModel(model.MonthSelectionSegment.AUGUST, getText('Enum_MonthSelectionSegment_AUGUST')),
                new model.ItemModel(model.MonthSelectionSegment.SEPTEMBER, getText('Enum_MonthSelectionSegment_SEPTEMBER')),
                new model.ItemModel(model.MonthSelectionSegment.OCTOBER, getText('Enum_MonthSelectionSegment_SECOND_OCTOBER')),
                new model.ItemModel(model.MonthSelectionSegment.NOVEMBER, getText('Enum_MonthSelectionSegment_NOVEMBER')),
                new model.ItemModel(model.MonthSelectionSegment.DECEMBER, getText('Enum_MonthSelectionSegment_DECEMBER'))
            ]);
            self.employmentInsuranceStandardMonthSelectedCode = ko.observable(1);

            //D3_20
            self.datePayment = ko.observableArray([]);
            self.datePaymentSelectedCode = ko.observable(1);


            //D3_17
            self.processingMonth = ko.observableArray([
                new model.ItemModel(model.PreviousMonthClassification.THIS_MONTH, getText('Enum_PreviousMonthClassification_THIS_MONTH')),
                new model.ItemModel(model.PreviousMonthClassification.LAST_MONTH, getText('Enum_PreviousMonthClassification_LAST_MONTH'))
            ]);
            self.processingMonthSelectedCode = ko.observable(0);


            //D4_3
            self.printingMonth = ko.observableArray([
                new model.ItemModel(model.PreviousMonthClassification.THIS_MONTH, getText('Enum_PreviousMonthClassification_THIS_MONTH')),
                new model.ItemModel(model.PreviousMonthClassification.LAST_MONTH, getText('Enum_PreviousMonthClassification_LAST_MONTH'))
            ]);
            self.printingMonthSelectedCode = ko.observable(0);

            //D3_20
            self.disposalDay = ko.observableArray([]);
            self.disposalDaySelectedCode = ko.observable(1);


            //D4_30
            self.timeCloseDate = ko.observableArray([
                new model.ItemModel(model.TimeCloseDateClassification.SAME_DATE, getText('QMM005_117')),
                new model.ItemModel(model.TimeCloseDateClassification.APART_FROM_DATE, getText('QMM005_118'))
            ]);
            self.timeCloseDateSelectedCode = ko.observable(0);
            self.timeCloseDateSelectedCode.subscribe(function () {
                if (self.timeCloseDateSelectedCode() == 0)
                    self.enableCloseDate(false);
                else
                    self.enableCloseDate(true);
            });

            //D4_9
            self.monthsCollected = ko.observableArray([
                new model.ItemModel(model.SocialInsuColleMonth.BEFORE_MONTH, getText('Enum_SocialInsuColleMonth_BEFORE_MONTH')),
                new model.ItemModel(model.SocialInsuColleMonth.LAST_MONTH, getText('Enum_SocialInsuColleMonth_LAST_MONTH')),
                new model.ItemModel(model.SocialInsuColleMonth.MONTH, getText('Enum_SocialInsuColleMonth_MONTH')),
                new model.ItemModel(model.SocialInsuColleMonth.NEXT_MONTH, getText('Enum_SocialInsuColleMonth_NEXT_MONTH')),
                new model.ItemModel(model.SocialInsuColleMonth.SECOND_FOLLOWING_MONTH, getText('Enum_SocialInsuColleMonth_SECOND_FOLLOWING_MONTH')),

            ]);
            self.monthsCollectedSelectedCode = ko.observable(0);

            //D4_15
            self.guaranteedBaseYear = ko.observableArray([
                new model.ItemModel(model.YearSelectClassification.LAST_YEAR, getText('Enum_YearSelectClassification_PREVIOUS_YEAR')),
                new model.ItemModel(model.YearSelectClassification.THIS_YEAR, getText('Enum_YearSelectClassification_THIS_YEAR')),
                new model.ItemModel(model.YearSelectClassification.AFTER_YEAR, getText('Enum_YearSelectClassification_AFTER_YEAR')),
                new model.ItemModel(model.YearSelectClassification.LEAP_YEAR, getText('Enum_YearSelectClassification_LEAP_YEAR'))


            ]);
            self.guaranteedBaseYearSelectedCode = ko.observable(0);


            //D3_11
            self.refeDate = ko.observableArray([]);
            self.refeDateSelectedCode = ko.observable(0);

            //D3_8

            self.refeMonth = ko.observableArray([
                new model.ItemModel(model.PreviousMonthClassification.THIS_MONTH, getText('Enum_PreviousMonthClassification_THIS_MONTH')),
                new model.ItemModel(model.PreviousMonthClassification.LAST_MONTH, getText('Enum_PreviousMonthClassification_LAST_MONTH'))
            ]);
            self.refeMonthSelectedCode = ko.observable(0);

            //D4_17
            self.guaranteedBaseMonth = ko.observableArray([
                new model.ItemModel(model.InsuranceStanMonthClassSification.LAST_MONTH, getText('Enum_InsuranceStanMonthClassification_LAST_MONTH')),
                new model.ItemModel(model.InsuranceStanMonthClassSification.MONTH, getText('Enum_InsuranceStanMonthClassification_MONTH')),
                new model.ItemModel(model.InsuranceStanMonthClassSification.JANUARY, getText('Enum_InsuranceStanMonthClassification_JANUARY')),
                new model.ItemModel(model.InsuranceStanMonthClassSification.FEBRUARY, getText('Enum_InsuranceStanMonthClassification_FEBRUARY')),
                new model.ItemModel(model.InsuranceStanMonthClassSification.MARCH, getText('Enum_InsuranceStanMonthClassification_MARCH')),
                new model.ItemModel(model.InsuranceStanMonthClassSification.APRIL, getText('Enum_InsuranceStanMonthClassification_APRIL')),
                new model.ItemModel(model.InsuranceStanMonthClassSification.MAY, getText('Enum_InsuranceStanMonthClassification_MAY')),
                new model.ItemModel(model.InsuranceStanMonthClassSification.JUNE, getText('Enum_InsuranceStanMonthClassification_JUNE')),
                new model.ItemModel(model.InsuranceStanMonthClassSification.JULY, getText('Enum_InsuranceStanMonthClassification_JULY')),
                new model.ItemModel(model.InsuranceStanMonthClassSification.AUGUST, getText('Enum_InsuranceStanMonthClassification_AUGUST')),
                new model.ItemModel(model.InsuranceStanMonthClassSification.SEPTEMBER, getText('Enum_InsuranceStanMonthClassification_SEPTEMBER')),
                new model.ItemModel(model.InsuranceStanMonthClassSification.OCTOBER, getText('Enum_InsuranceStanMonthClassification_OCTOBER')),
                new model.ItemModel(model.InsuranceStanMonthClassSification.NOVEMBER, getText('Enum_InsuranceStanMonthClassification_NOVEMBER')),
                new model.ItemModel(model.InsuranceStanMonthClassSification.DECEMBER, getText('Enum_InsuranceStanMonthClassification_DECEMBER'))

            ]);
            self.guaranteedBaseMonthSelectedCode = ko.observable(0);


            self.pushDaytoList(self.disposalDay, model.DateSelectClassification);
            self.pushDaytoList(self.datePayment, model.DateSelectClassification);
            self.pushDaytoList(self.refeDate, model.DateSelectClassification);
            self.pushDaytoList(self.employmentInsuranceStandardDate, model.DateSelectClassification);
            self.pushDaytoList(self.timeReferenceStandardDay, model.DateSelectClassification);
            self.pushDaytoList(self.guaranteedBaseDate, model.DateSelectClassification);
            self.pushDaytoList(self.incomeTaxBaseDate, model.DateSelectClassification);

            self.selectedId = ko.observable(0);
            self.labelRequired = ko.observable(true);
            self.isEnable = ko.observable(true);

            self.enableCheckBox = ko.observable(false);
            if(self.mode==0){
                self.DiscontinueThisProcessClassificationSelectedCode = ko.observable(self.enableCheckBox() ? 1:0);
            }

            self.DiscontinueThisProcessClassificationSelectedCode=ko.observable(0);

           // closeDate: {timeCloseDate: 0, baseMonth: null, baseYear: null, refeDate: null}

          //  detailPrintingMon: {printingMonth: 0}

           // empInsurStanDate: {refeDate: 1, baseMonth: 1}

          //  incomTaxBaseYear: {refeDate: 1, baseYear: 1, baseMonth: 1}

          //  salaryInsuColMon: {monthCollected: 0}

          //  sociInsuStanDate: {baseMonth: 0, baseYear: 1, refeDate: 1}






        }

        saveCharacterSetting(): void {
            let self = this;
            this.saveToDB();
        }

        cancelCharacterSetting(): void {
            let self=this;
            let param={
                action:3,
                processInfomationUpdate:self.processInfomation
            }

            setShared("QMM005_output_A",param);
            nts.uk.ui.windows.close();

        }

        reSize(): void {
            let self=this;
            let windowSize = nts.uk.ui.windows.getSelf();
            let element = document.getElementById('openAdvanceSetting');
            let expanClass =document.getElementsByClassName("ui-dialog")[0];
            
            if (!self.isAdvanceSetting()) {
                windowSize.$dialog.height(500);
                self.isAdvanceSetting(true);
                
                //expanClass.addClass('expanDialog');
                //element.style.display = "block";
               // windowSize.setHeight(windowSize.parent.globalContext.innerHeight + 100);

                //element.attr('style','top: 0px!important');
                //$('.ui-dialog').attr('style','top: 0px!important');
            }else {
                self.isAdvanceSetting(false);
                windowSize.$dialog.height(410);
                //element.style.display = "none";
            }
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            dfd.resolve();
            if(self.mode==0){
                service.findDisplayRegister(this.processCategoryNo).done(function (data) {
                    console.log(data);
                    self.valPayDateSet=new model.ValPayDateSet(data);
                    console.log(self.valPayDateSet)

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


                        self.processCategoryNo=self.processInfomation.processCateNo;
                        self.processName(self.processInfomation.processDivisionName());
                        self.DiscontinueThisProcessClassificationSelectedCode(self.processInfomation.deprecatCate);
                        self.enableCheckBox(self.processInfomation.deprecatCate ==0 ? false:true);

                })
                
                

            }
            return dfd.promise();
        }


        //初期表示処理
        onInitScreen(): void {

        }




        saveToDB():void{
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

            }

            let bassicSettingParam = {
                monthlyPaymentDate: self.monthlyPaymentDate,
                employeeExtractionReferenceDate: self.employeeExtractionReferenceDate,
                accountingClosureDate: self.accountingClosureDate,
                workDay: self.numberOfWorkingDays()


            }

            self.basicSetting = new model.BasicSetting(bassicSettingParam);
            self.advancedSetting = new model.AdvancedSetting(advancedSettingParam);

            let ValPayDateSetParam = {

                processCateNo: self.processCategoryNo,
                basicSetting: self.basicSetting,
                advancedSetting: self.advancedSetting
            }

            self.valPayDateSet = new model.ValPayDateSet(ValPayDateSetParam);

            let processInfomationParam = {

                processCateNo: self.processCategoryNo,
                processDivisionName: self.processName(),
                deprecatCate: self.DiscontinueThisProcessClassificationSelectedCode()

            }

            self.processInfomation = new model.ProcessInfomation(processInfomationParam);
            console.log(self.processInfomation);
            console.log(self.valPayDateSet);
            
            if(self.mode==1){
                service.registerprocessingsegment({
                    processInformation: ko.toJS(self.processInfomation),
                    valPayDateSet: ko.toJS(self.valPayDateSet)
                }).done(function () {
                    let param={
                        action:1,
                        processInfomationUpdate:self.processInfomation
                    }

                    setShared("QMM005_output_A",param);
                    nts.uk.ui.windows.close();
                })
             }

            if(self.mode==0){
                self.enableCheckBox() ? self.processInfomation.deprecatCate=1:self.processInfomation.deprecatCate=0;

                service.updateprocessingsegment({
                    processInformation: ko.toJS(self.processInfomation),
                    valPayDateSet: ko.toJS(self.valPayDateSet)
                }).done(function () {
                    let param={
                        action:0,
                        processInfomationUpdate:self.processInfomation
                    }

                    setShared("QMM005_output_A",ko.toJS(param));
                    nts.uk.ui.windows.close();
                })
            }
        }




        pushDaytoList(itemList: KnockoutObservableArray<model.ItemModel>, codeEnum: any): void {
            let items = itemList;
            let code = codeEnum;
            for(let i=1;i<31;i++){
                itemList.push(new model.ItemModel(i,i+'日'));
            }
            itemList.push(new model.ItemModel(31,'末日'));

//            let i = 1;
//            for (let data in codeEnum) {
//                if (isNaN(data)) continue;
//                itemList.push(new model.ItemModel(data, i + '日'));
//                i++;
//            }
        }


    }

}

