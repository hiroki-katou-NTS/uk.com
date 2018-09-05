module nts.uk.com.view.qmm005.e.viewmodel {
    import model = nts.uk.com.view.qmm005.share.model;
    export class ScreenModel {
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

            let tranferModel = {
                E1_3_0 : 'processingDivision',
                E1_3_1 : 'ProcessingDivisionBasicInformation',
                E1_5_0 : 'Standard information reflection target _ processing year',
                E2_2 : 'Standard information reflection target _ processing year',
                E2_8_0 : 'Daily payment date',
                E2_10_0 : 'Employee extraction reference date_Reference month',
                E2_10_1 : 'Employee extraction reference date_Reference date',
                E2_12_0 : 'monthsCollected: socialInsuColleMonth',
                E2_14_0 : 'detailPrintingMonth->printingMonth',
                E4_2_0 : 'basicsetting -> Number of working days',
                E2_17_0 : 'socialInsuranceStanDate->Base year',
                E2_17_1 : 'socialInsuranceStanDate->Standard month',
                E2_17_2 : 'socialInsuranceStanDate->Reference date',
                E2_19_0 : 'employmentInsuranceStanDate->base month',
                E2_19_1 : 'employmentInsuranceStanDate->Reference date',
                E2_21_0 : 'closeDate->Base year',
                E2_21_1 : 'closeDate->base month',
                E2_21_2 : 'closeDate->Reference date',
                E2_23_0 : 'IncomeTaxBaseYear->Reference date',
                E2_23_1 : 'IncomeTaxBaseYear->Reference date',
                E2_23_2 : 'IncomeTaxBaseYear->Reference date',
                E2_25_0 : 'Processing month',
                E2_25_1 : 'disposal day',
            }
            // E1_3
            self.processingClassification = ko.observable(nts.uk.text.format(nts.uk.resource.getText("#QMM005_97"), tranferModel.E1_3_0, tranferModel.E1_3_1));
            // E1_5
            self.processingYearAD = ko.observable(nts.uk.text.format(nts.uk.resource.getText("#QMM005_109"), tranferModel.E1_5_0));
            // E1_6
            self.treatmentYearJapaneseCalendar = ko.observable('heisei calendar');
            // E2_2
            self.reflectionStartYear = ko.observable(tranferModel.E2_2);
            // E2_8
            self.paymentDateSetting = ko.observable(nts.uk.text.format(nts.uk.resource.getText("#QMM005_102"), tranferModel.E2_8_0));
            //E2_10
            self.employeeExtractionBaseDateSetting = ko.observable(nts.uk.text.format(nts.uk.resource.getText("#QMM005_102"), tranferModel.E2_10_0, tranferModel.E2_10_1));
            // E2_12
            self.socialInsuranceCollectionMonthSetting = ko.observable(nts.uk.text.format(nts.uk.resource.getText("#QMM005_104"), tranferModel.E2_12_0));
            // E2_14
            self.specificationPrintoutYearMonthSetting = ko.observable(nts.uk.text.format(nts.uk.resource.getText("#QMM005_104"), tranferModel.E2_14_0));
            //E4_2
            self.numberOfWorkingDaysSetting = ko.observable(nts.uk.text.format(nts.uk.resource.getText("#QMM005_108"),tranferModel.E4_2_0));
            //E2_17
            self.socialInsuranceStandardDateSetting = ko.observable(nts.uk.text.format(nts.uk.resource.getText("#QMM005_106"), tranferModel.E2_17_0, tranferModel.E2_17_1, tranferModel.E2_17_2));
            //E2_19
            self.employmentInsuranceStandardDateSetting = ko.observable(nts.uk.text.format(nts.uk.resource.getText("#QMM005_107"), tranferModel.E2_19_0, tranferModel.E2_19_1));
            // E2_21
            self.settingOfStandardReferenceDayForClosingTime = ko.observable(nts.uk.text.format(nts.uk.resource.getText("#QMM005_106"), tranferModel.E2_21_0, tranferModel.E2_21_1, tranferModel.E2_21_2));
            //E2_23
            self.incomeTaxBaseDateSetting = ko.observable(nts.uk.text.format(nts.uk.resource.getText("#QMM005_106"), tranferModel.E2_23_0, tranferModel.E2_23_1, tranferModel.E2_23_2));
            //E2_25
            self.accountingClosingDateSetting = ko.observable(nts.uk.text.format(nts.uk.resource.getText("#QMM005_103"), tranferModel.E2_25_0, tranferModel.E2_25_1));
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
                timeClosingCheck: true,
                incomeTaxReferenceCheck: true,
                accountingClosureDateCheck: true,
            }
            self.referenceDateInformation = ko.observable(new model.ReferenceDateInformation(obj));

            self.inline = ko.observable(true);
            self.required = ko.observable(true);
        }

        startPage(): JQueryPromise<any> {
            var dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }

        reflect(){

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