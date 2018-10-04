module nts.uk.pr.view.qmm005.e.viewmodel {
    import model = nts.uk.pr.view.qmm005.share.model;
    import format = nts.uk.text.format;
    import getText = nts.uk.resource.getText;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    const ENUM_DATE_SELECT_CLASSIFICATION = [
        "Enum_DateSelectClassification_DAY_1",
        "Enum_DateSelectClassification_DAY_2",
        "Enum_DateSelectClassification_DAY_3",
        "Enum_DateSelectClassification_DAY_4",
        "Enum_DateSelectClassification_DAY_5",
        "Enum_DateSelectClassification_DAY_6",
        "Enum_DateSelectClassification_DAY_7",
        "Enum_DateSelectClassification_DAY_8",
        "Enum_DateSelectClassification_DAY_9",
        "Enum_DateSelectClassification_DAY_10",
        "Enum_DateSelectClassification_DAY_11",
        "Enum_DateSelectClassification_DAY_12",
        "Enum_DateSelectClassification_DAY_13",
        "Enum_DateSelectClassification_DAY_14",
        "Enum_DateSelectClassification_DAY_15",
        "Enum_DateSelectClassification_DAY_16",
        "Enum_DateSelectClassification_DAY_17",
        "Enum_DateSelectClassification_DAY_18",
        "Enum_DateSelectClassification_DAY_19",
        "Enum_DateSelectClassification_DAY_20",
        "Enum_DateSelectClassification_DAY_21",
        "Enum_DateSelectClassification_DAY_22",
        "Enum_DateSelectClassification_DAY_23",
        "Enum_DateSelectClassification_DAY_24",
        "Enum_DateSelectClassification_DAY_25",
        "Enum_DateSelectClassification_DAY_26",
        "Enum_DateSelectClassification_DAY_27",
        "Enum_DateSelectClassification_DAY_28",
        "Enum_DateSelectClassification_DAY_29",
        "Enum_DateSelectClassification_DAY_30",
        "Enum_DateSelectClassification_LAST_DAY_MONTH"
    ];
    const ENUM_PREVIOUS_MONTH_CLASSIFICATION = [
        'Enum_PreviousMonthClassification_THIS_MONTH',
        'Enum_PreviousMonthClassification_LAST_MONTH'
    ];
    const ENUM_SOCIAL_INSU_COLLE_MONTH = [
        'Enum_SocialInsuColleMonth_BEFORE_MONTH',
        'Enum_SocialInsuColleMonth_LAST_MONTH',
        'Enum_SocialInsuColleMonth_MONTH',
        'Enum_SocialInsuColleMonth_NEXT_MONTH',
        'Enum_SocialInsuColleMonth_SECOND_FOLLOWING_MONTH'
    ];
    const ENUM_INSURANCE_STANMONTH_CLASSIFICATION = [
        "Enum_InsuranceStanMonthClassification_LAST_MONTH",
        "Enum_InsuranceStanMonthClassification_MONTH",
        "Enum_InsuranceStanMonthClassification_JANUARY",
        "Enum_InsuranceStanMonthClassification_FEBRUARY",
        "Enum_InsuranceStanMonthClassification_MARCH",
        "Enum_InsuranceStanMonthClassification_APRIL",
        "Enum_InsuranceStanMonthClassification_MAY",
        "Enum_InsuranceStanMonthClassification_JUNE",
        "Enum_InsuranceStanMonthClassification_JULY",
        "Enum_InsuranceStanMonthClassification_AUGUST",
        "Enum_InsuranceStanMonthClassification_SEPTEMBER",
        "Enum_InsuranceStanMonthClassification_OCTOBER",
        "Enum_InsuranceStanMonthClassification_NOVEMBER",
        "Enum_InsuranceStanMonthClassification_DECEMBER"];
    const ENUM_YEAR_SELECT_CLASSIFICATION = [
        "Enum_YearSelectClassification_PREVIOUS_YEAR",
        "Enum_YearSelectClassification_THIS_YEAR",
        "Enum_YearSelectClassification_AFTER_YEAR",
        "Enum_YearSelectClassification_LEAP_YEAR"
    ];
    const ENUM_MONTH_SELECTION_SEGMENT = [
        "Enum_MonthSelectionSegment_JANUARY",
        "Enum_MonthSelectionSegment_FEBRUARY",
        "Enum_MonthSelectionSegment_MARCH",
        "Enum_MonthSelectionSegment_APRIL",
        "Enum_MonthSelectionSegment_SECOND_MAY",
        "Enum_MonthSelectionSegment_JUNE",
        "Enum_MonthSelectionSegment_JULY",
        "Enum_MonthSelectionSegment_AUGUST",
        "Enum_MonthSelectionSegment_SEPTEMBER",
        "Enum_MonthSelectionSegment_SECOND_OCTOBER",
        "Enum_MonthSelectionSegment_NOVEMBER",
        "Enum_MonthSelectionSegment_DECEMBER"
    ];
    export class ScreenModel {
        valPayDateSet: any;
        processingClassification: KnockoutObservable<string>;
        processingYearAD: KnockoutObservable<string>;
        treatmentYearJapaneseCalendar: KnockoutObservable<string>;
        reflectionStartYear: KnockoutObservable<string>;
        reflectionEndYear: KnockoutObservable<string>;
        dailyPaymentDate: KnockoutObservable<string>;
        empExtractionRefDate: KnockoutObservable<string>;
        socialInsuranceCollectionMonthSetting: KnockoutObservable<string>;
        specificationPrintoutYearMonthSetting: KnockoutObservable<string>;
        numberOfWorkingDaysSetting: KnockoutObservable<string>;
        socialInsuranceStandardDateSetting: KnockoutObservable<string>;
        employmentInsuranceStandardDateSetting: KnockoutObservable<string>;
        settingOfStandardReferenceDayForClosingTime: KnockoutObservable<string>;
        incomeTaxBaseDateSetting: KnockoutObservable<string>;
        accountingClosingDateSetting: KnockoutObservable<string>;
        reflectionStartMonthList: KnockoutObservableArray<ItemModel>;
        startMonth: KnockoutObservable<string>;
        inline: KnockoutObservable<boolean>;
        required: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;
        // E2_7, E2_9, E2_11, E2_13, E4_1, E2_16, E2_18, E2_20 E2_22, E2_24
        dailyPaymentDateCheck = ko.observable(true);
        empExtractionRefDateCheck = ko.observable(true);
        socialInsuranceMonthCheck = ko.observable(true);
        specPrintDateCheck = ko.observable(true);
        numWorkingDaysCheck = ko.observable(true);
        socialInsuranceDateCheck = ko.observable(true);
        empInsuranceStandardDateCheck = ko.observable(true);
        timeClosingDateCheck = ko.observable(true);
        incomeTaxReferenceCheck = ko.observable(true);
        accountingClosureDateCheck = ko.observable(true);

        constructor() {
            var self = this;
            // E2_3
            self.reflectionStartMonthList = ko.observableArray([]);
            let tempMonth: Array<any> = [];
            for (i = 1; i < 13; i++) {
                tempMonth.push(new ItemModel(i, i));
            }
            self.reflectionStartMonthList(tempMonth);
            self.startMonth = ko.observable('1');
            self.inline = ko.observable(true);
            self.required = ko.observable(true);
            self.dailyPaymentDateCheck.subscribe(function (newValue) {
                if(newValue){
                    nts.uk.ui.errors.clearAll();
                }
            });
            self.empExtractionRefDateCheck.subscribe(function (newValue) {
                if(newValue){
                    nts.uk.ui.errors.clearAll();
                }
            });
            self.socialInsuranceMonthCheck.subscribe(function (newValue) {
                if(newValue){
                    nts.uk.ui.errors.clearAll();
                }
            });
            self.specPrintDateCheck.subscribe(function (newValue) {
                if(newValue){
                    nts.uk.ui.errors.clearAll();
                }
            });
            self.numWorkingDaysCheck.subscribe(function (newValue) {
                if(newValue){
                    nts.uk.ui.errors.clearAll();
                }
            });
            self.socialInsuranceDateCheck.subscribe(function (newValue) {
                if(newValue){
                    nts.uk.ui.errors.clearAll();
                }
            });
            self.empInsuranceStandardDateCheck.subscribe(function (newValue) {
                if(newValue){
                    nts.uk.ui.errors.clearAll();
                }
            });
            self.timeClosingDateCheck.subscribe(function (newValue) {
                if(newValue){
                    nts.uk.ui.errors.clearAll();
                }
            });
            self.incomeTaxReferenceCheck.subscribe(function (newValue) {
                if(newValue){
                    nts.uk.ui.errors.clearAll();
                }
            });
            self.accountingClosureDateCheck.subscribe(function (newValue) {
                if(newValue){
                    nts.uk.ui.errors.clearAll();
                }
            });
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            let params = getShared("QMM005bParams");
            service.findReflectSystemReferenceDateInfo(params.processCateNo, params.processingYear).done(function (data) {
                self.valPayDateSet = data.valPayDateSetDto;
                var basicSetting = data.valPayDateSetDto.basicSetting;
                var advancedSetting = data.valPayDateSetDto.advancedSetting;
                let tranferModel = {
                    E1_3_0: params.processCateNo,
                    E1_3_1: params.processInfomation.processCls,
                    E1_5_0: params.processingYear,
                    E2_2: params.processingYear,
                    E2_8_0: getText(ENUM_DATE_SELECT_CLASSIFICATION[basicSetting.monthlyPaymentDate.datePayMent - 1]),
                    E2_10_0: getText(ENUM_PREVIOUS_MONTH_CLASSIFICATION[basicSetting.employeeExtractionReferenceDate.refeMonth]),
                    E2_10_1: getText(ENUM_DATE_SELECT_CLASSIFICATION[basicSetting.employeeExtractionReferenceDate.refeDate - 1]),
                    E2_12_0: getText(ENUM_SOCIAL_INSU_COLLE_MONTH[advancedSetting.salaryInsuColMon.monthCollected]),
                    E2_14_0: getText(ENUM_PREVIOUS_MONTH_CLASSIFICATION[advancedSetting.detailPrintingMon.printingMonth]),
                    E4_2_0: basicSetting.workDay,
                    E2_17_0: getText(ENUM_YEAR_SELECT_CLASSIFICATION[advancedSetting.sociInsuStanDate.baseYear]),
                    E2_17_1: getText(ENUM_INSURANCE_STANMONTH_CLASSIFICATION[advancedSetting.sociInsuStanDate.baseMonth]),
                    E2_17_2: getText(ENUM_DATE_SELECT_CLASSIFICATION[advancedSetting.sociInsuStanDate.refeDate - 1]),
                    E2_19_0: getText(ENUM_MONTH_SELECTION_SEGMENT[advancedSetting.empInsurStanDate.baseMonth]),
                    E2_19_1: getText(ENUM_DATE_SELECT_CLASSIFICATION[advancedSetting.empInsurStanDate.refeDate - 1]),
                    E2_21_0: (advancedSetting.closeDate.timeCloseDate == model.TimeCloseDateClassification.SAME_DATE) ? params.processingYear + '年' : getText(ENUM_YEAR_SELECT_CLASSIFICATION[advancedSetting.closeDate.baseYear]),
                    E2_21_1: (advancedSetting.closeDate.timeCloseDate == model.TimeCloseDateClassification.SAME_DATE) ? getText(ENUM_PREVIOUS_MONTH_CLASSIFICATION[basicSetting.employeeExtractionReferenceDate.refeMonth]) : getText(ENUM_SOCIAL_INSU_COLLE_MONTH[advancedSetting.closeDate.baseMonth]),
                    E2_21_2: (advancedSetting.closeDate.timeCloseDate == model.TimeCloseDateClassification.SAME_DATE) ? getText(ENUM_DATE_SELECT_CLASSIFICATION[basicSetting.employeeExtractionReferenceDate.refeDate - 1]) : getText(ENUM_DATE_SELECT_CLASSIFICATION[advancedSetting.closeDate.refeDate - 1]),
                    E2_23_0: getText(ENUM_YEAR_SELECT_CLASSIFICATION[advancedSetting.incomTaxBaseYear.baseYear]),
                    E2_23_1: getText(ENUM_MONTH_SELECTION_SEGMENT[advancedSetting.incomTaxBaseYear.baseMonth]),
                    E2_23_2: getText(ENUM_DATE_SELECT_CLASSIFICATION[advancedSetting.incomTaxBaseYear.refeDate - 1]),
                    E2_25_0: getText(ENUM_PREVIOUS_MONTH_CLASSIFICATION[basicSetting.accountingClosureDate.processMonth]),
                    E2_25_1: getText(ENUM_DATE_SELECT_CLASSIFICATION[basicSetting.accountingClosureDate.disposalDay - 1]),
                }
                self.mapLabel(tranferModel);
                dfd.resolve();
                if ($('#E2_3')) {
                    setTimeout(function () {
                        $('#E2_3').focus();
                    }, 350);
                }
            });
            return dfd.promise();
        }

        mapLabel(tranferModel) {
            var self = this;
            // E1_3
            self.processingClassification = ko.observable(format(getText("QMM005_97"), tranferModel.E1_3_0, tranferModel.E1_3_1));
            // E1_5
            self.processingYearAD = ko.observable(format(getText("QMM005_109"), tranferModel.E1_5_0));
            // E1_6
            self.treatmentYearJapaneseCalendar = ko.observable(' (' + nts.uk.time.yearmonthInJapanEmpire(tranferModel.E1_5_0 + '01').toString().split(' ').slice(0, 3).join('') + ')');
            // E2_2
            self.reflectionStartYear = ko.observable(tranferModel.E2_2);
            //E2_5
            self.reflectionEndYear = ko.observable(format(getText("QMM005_110"), tranferModel.E2_2));
            // E2_8
            self.dailyPaymentDate = ko.observable(format(getText("QMM005_102"), tranferModel.E2_8_0));
            //E2_10
            self.empExtractionRefDate = ko.observable(format(getText("QMM005_103"), tranferModel.E2_10_0, tranferModel.E2_10_1));
            // E2_12
            self.socialInsuranceCollectionMonthSetting = ko.observable(format(getText("QMM005_104"), tranferModel.E2_12_0));
            // E2_14
            self.specificationPrintoutYearMonthSetting = ko.observable(format(getText("QMM005_104"), tranferModel.E2_14_0));
            //E4_2
            self.numberOfWorkingDaysSetting = ko.observable(format(getText("QMM005_108"), tranferModel.E4_2_0));
            //E2_17
            self.socialInsuranceStandardDateSetting = ko.observable(format(getText("QMM005_106"), tranferModel.E2_17_0, tranferModel.E2_17_1, tranferModel.E2_17_2));
            //E2_19
            self.employmentInsuranceStandardDateSetting = ko.observable(format(getText("QMM005_107"), tranferModel.E2_19_0, tranferModel.E2_19_1));
            // E2_21
            self.settingOfStandardReferenceDayForClosingTime = ko.observable(format(getText("QMM005_106"), tranferModel.E2_21_0, tranferModel.E2_21_1, tranferModel.E2_21_2));
            //E2_23
            self.incomeTaxBaseDateSetting = ko.observable(format(getText("QMM005_106"), tranferModel.E2_23_0, tranferModel.E2_23_1, tranferModel.E2_23_2));
            //E2_25
            self.accountingClosingDateSetting = ko.observable(format(getText("QMM005_103"), tranferModel.E2_25_0, tranferModel.E2_25_1));
        }

        reflect() {
            var self = this;
            if (!self.dailyPaymentDateCheck() &&
                !self.empExtractionRefDateCheck() &&
                !self.socialInsuranceMonthCheck() &&
                !self.specPrintDateCheck() &&
                !self.numWorkingDaysCheck() &&
                !self.socialInsuranceDateCheck() &&
                !self.empInsuranceStandardDateCheck() &&
                !self.timeClosingDateCheck() &&
                !self.incomeTaxReferenceCheck() &&
                !self.accountingClosureDateCheck()) {
                $('#E2_6').ntsError('set', {messageId: "MsgQ_8"});
                return;
            }
            setShared("QMM005eParams", {
                reflect: true,
                valPayDateSet: self.valPayDateSet,
                startMonth: self.startMonth(),
                checkbox: {
                    dailyPaymentDateCheck: self.dailyPaymentDateCheck(),
                    empExtractionRefDateCheck: self.empExtractionRefDateCheck(),
                    socialInsuranceMonthCheck: self.socialInsuranceMonthCheck(),
                    specPrintDateCheck: self.specPrintDateCheck(),
                    numWorkingDaysCheck: self.numWorkingDaysCheck(),
                    socialInsuranceDateCheck: self.socialInsuranceDateCheck(),
                    empInsuranceStandardDateCheck: self.empInsuranceStandardDateCheck(),
                    timeClosingDateCheck: self.timeClosingDateCheck(),
                    incomeTaxReferenceCheck: self.incomeTaxReferenceCheck(),
                    accountingClosureDateCheck: self.accountingClosureDateCheck(),
                }
            });
            nts.uk.ui.windows.close();
        }

        cancel() {
            setShared("QMM005eParams", {
                reflect: false,
            });
            nts.uk.ui.windows.close();
        }
    }


    class ItemModel {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
}