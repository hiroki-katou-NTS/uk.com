module nts.uk.pr.view.qmm005.e.viewmodel {
    import model = nts.uk.pr.view.qmm005.share.model;
    import format = nts.uk.text.format;
    import getText = nts.uk.resource.getText;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    export class ScreenModel {
        valPayDateSet: any;
        processingClassification: KnockoutObservable<string>;
        processingYearAD: KnockoutObservable<string>;
        treatmentYearJapaneseCalendar: KnockoutObservable<string>;
        reflectionStartYear: KnockoutObservable<string>;
        paymentDateSetting: KnockoutObservable<string>;
        employeeExtractionBaseDateSetting: KnockoutObservable<string>;
        socialInsuranceCollectionMonthSetting: KnockoutObservable<string>;
        specificationPrintoutYearMonthSetting: KnockoutObservable<string>;
        numberOfWorkingDaysSetting: KnockoutObservable<string>;
        socialInsuranceStandardDateSetting: KnockoutObservable<string>;
        employmentInsuranceStandardDateSetting: KnockoutObservable<string>;
        settingOfStandardReferenceDayForClosingTime: KnockoutObservable<string>;
        incomeTaxBaseDateSetting: KnockoutObservable<string>;
        accountingClosingDateSetting: KnockoutObservable<string>;
        reflectionStartMonthList: KnockoutObservableArray<ItemModel>;
        reflectionStartMonth: KnockoutObservable<string>;
        referenceDateInformation: KnockoutObservable<model.ReferenceDateInformation>;
        constraint: string = 'LayoutCode';
        inline: KnockoutObservable<boolean>;
        required: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;
        constructor() {
            var self = this;
            // E2_3
            self.reflectionStartMonthList = ko.observableArray([]);
            let tempMonth:Array<any> = [];
            for (i = 1; i < 13; i++) {
                tempMonth.push(new ItemModel(i, i));
            }
            self.reflectionStartMonthList(tempMonth);
            self.reflectionStartMonth = ko.observable('1');
            // E2_7, E2_9, E2_11, E2_13, E4_1, E2_16, E2_18, E2_20 E2_22, E2_24
            let obj = {
                dailyPaymentDateCheck: true,
                empExtractionRefDateCheck: true,
                socialInsuranceMonthCheck: true,
                specPrintDateCheck: true,
                numWorkingDaysCheck: true,
                socialInsuranceDateCheck: true,
                empInsuranceStandardDateCheck: true,
                timeClosingDateCheck: true,
                incomeTaxReferenceCheck: true,
                accountingClosureDateCheck: true,
            }
            self.referenceDateInformation = ko.observable(new model.ReferenceDateInformation(obj));

            self.inline = ko.observable(true);
            self.required = ko.observable(true);
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            let params = getShared("QMM005bParams");
            service.findReflectSystemReferenceDateInfo(params.processCateNo, params.processingYear).done(function (data) {
                self.valPayDateSet = data;
                var basicSetting = data.basicSetting;
                var advancedSetting = data.advancedSetting;
                let tranferModel = {
                    E1_3_0 : params.processCateNo,
                    E1_3_1 : params.processInfomation.processDivisionName,
                    E1_5_0 : params.processingYear,
                    E2_2 : params.processingYear,
                    E2_8_0 : basicSetting.monthlyPaymentDate.datePayMent,
                    E2_10_0 : basicSetting.employeeExtractionReferenceDate.refeMonth,
                    E2_10_1 : basicSetting.employeeExtractionReferenceDate.refeDate,
                    E2_12_0 : advancedSetting.salaryInsuColMon.monthCollected,
                    E2_14_0 : advancedSetting.detailPrintingMon.printingMonth,
                    E4_2_0 : basicSetting.workDay,
                    E2_17_0 : advancedSetting.sociInsuStanDate.baseYear,
                    E2_17_1 : advancedSetting.sociInsuStanDate.baseMonth,
                    E2_17_2 : advancedSetting.sociInsuStanDate.refeDate,
                    E2_19_0 : advancedSetting.empInsurStanDate.baseMonth,
                    E2_19_1 : advancedSetting.empInsurStanDate.refeDate,
                    E2_21_0 : advancedSetting.closeDate.baseYear,
                    E2_21_1 : advancedSetting.closeDate.baseMonth,
                    E2_21_2 : advancedSetting. closeDate.refeDate,
                    E2_23_0 : advancedSetting.incomTaxBaseYear.baseYear,
                    E2_23_1 : advancedSetting.incomTaxBaseYear.baseMonth,
                    E2_23_2 : advancedSetting.incomTaxBaseYear.refeDate,
                    E2_25_0 : basicSetting.accountingClosureDate.processMonth,
                    E2_25_1 : basicSetting.accountingClosureDate.disposalDay,
                }
                dfd.resolve();
            });
            return dfd.promise();
        }

        mapLabel(tranferModel){
            var self = this;
            // E1_3
            self.processingClassification = ko.observable(format(getText("#QMM005_97"), tranferModel.E1_3_0, tranferModel.E1_3_1));
            // E1_5
            self.processingYearAD = ko.observable(format(getText("#QMM005_109"), tranferModel.E1_5_0));
            // E1_6
            self.treatmentYearJapaneseCalendar = ko.observable('heisei calendar');
            // E2_2
            self.reflectionStartYear = ko.observable(tranferModel.E2_2);
            // E2_8
            self.paymentDateSetting = ko.observable(format(getText("#QMM005_102"), tranferModel.E2_8_0));
            //E2_10
            self.employeeExtractionBaseDateSetting = ko.observable(format(getText("#QMM005_102"), tranferModel.E2_10_0, tranferModel.E2_10_1));
            // E2_12
            self.socialInsuranceCollectionMonthSetting = ko.observable(format(getText("#QMM005_104"), tranferModel.E2_12_0));
            // E2_14
            self.specificationPrintoutYearMonthSetting = ko.observable(format(getText("#QMM005_104"), tranferModel.E2_14_0));
            //E4_2
            self.numberOfWorkingDaysSetting = ko.observable(format(getText("#QMM005_108"),tranferModel.E4_2_0));
            //E2_17
            self.socialInsuranceStandardDateSetting = ko.observable(format(getText("#QMM005_106"), tranferModel.E2_17_0, tranferModel.E2_17_1, tranferModel.E2_17_2));
            //E2_19
            self.employmentInsuranceStandardDateSetting = ko.observable(format(getText("#QMM005_107"), tranferModel.E2_19_0, tranferModel.E2_19_1));
            // E2_21
            self.settingOfStandardReferenceDayForClosingTime = ko.observable(format(getText("#QMM005_106"), tranferModel.E2_21_0, tranferModel.E2_21_1, tranferModel.E2_21_2));
            //E2_23
            self.incomeTaxBaseDateSetting = ko.observable(format(getText("#QMM005_106"), tranferModel.E2_23_0, tranferModel.E2_23_1, tranferModel.E2_23_2));
            //E2_25
            self.accountingClosingDateSetting = ko.observable(format(getText("#QMM005_103"), tranferModel.E2_25_0, tranferModel.E2_25_1));
        }

        reflect(){
            var self = this;
            setShared("QMM005eReflect", true);
            setShared("QMM005eValPayDateSet", self.valPayDateSet);
            setShared("QMM005estartMonth", self.reflectionStartMonth);
            setShared("QMM005eParams",self.referenceDateInformation);
        }

        cancel(){

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