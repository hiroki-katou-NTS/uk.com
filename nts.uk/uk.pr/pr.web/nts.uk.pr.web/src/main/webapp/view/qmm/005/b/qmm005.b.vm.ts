module nts.uk.pr.view.qmm005.b.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import model = nts.uk.pr.view.qmm005.share.model;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    export class ScreenModel {
        setPaymentDate: KnockoutObservable<model.SetPaymentDateTransfer>;
        // B3_4	処理年
        processingYear: KnockoutObservable<number>;
        listSettingPayment: KnockoutObservableArray<model.PaymentDateItem>;
        itemList: KnockoutObservableArray<any>;
        // B2_3	処理年
        processingYearList: KnockoutObservableArray<model.ItemModel>;
        btnText: any;
        screenModeCreate: KnockoutObservable<boolean>;
        processingYearListSelectedCode: KnockoutObservable<number>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        switchOptions: KnockoutObservableArray<any>;
        show: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;
        setDaySupportList: KnockoutObservableArray<model.SetDaySupport>;
        processInfomation: KnockoutObservableArray<model.ProcessInfomation>;

        constructor() {
            var self = this;
            self.processingYear = ko.observable(2016);
            let objItem = {
                paymentDate: "09/08/2018",
                employeeExtractionReferenceDate: "09/08/2018",
                socialInsuranceCollectionMonth: "09/08/2018",
                specificationPrintDate: "09/08/2018",
                numberOfWorkingDays: 24,
                targetMonth: "09/08/2018",
                incomeTaxReferenceDate: "09/08/2018",
                accountingClosureDate: "09/08/2018",
                socialInsuranceStandardDate: "09/08/2018",
                employmentInsuranceStandardDate: "09/08/2018",
                timeClosingDate: "09/08/2018",
            }
            self.listSettingPayment = ko.observableArray([
                new model.PaymentDateItem(objItem),
            ]);
            self.setPaymentDate = ko.observable(new model.SetPaymentDateTransfer(self.processingYear, self.listSettingPayment));
            self.show = ko.observable(true);
            self.btnText = ko.computed(function () {
                if (self.show()) return "-";
                return "+";
            });
            self.screenModeCreate = ko.observable(false);
            self.itemList = ko.observableArray([]);
            self.processingYearList = ko.observableArray([
                new model.ItemModel(1, '基本給'),
                new model.ItemModel(2, '役職手当'),
                new model.ItemModel(3, '基本給')
            ]);
            //B2_3 処理年
            this.columns = ko.observableArray([
                { headerText: '', key: 'code', width: 0, hidden: true },
                { headerText: nts.uk.resource.getText('#QMM005_25'), key: 'name', width: 150 },
            ]);
            this.enable = ko.observable(true);
            self.processingYearListSelectedCode = ko.observable(-1);
            self.setDaySupportList = ko.observableArray([]);
            self.processInfomation = ko.observableArray([]);
            self.switchOptions = ko.observableArray([
                {code: "1", name: '四捨五入'},
                {code: "2", name: '切り上げ'},
                {code: "3", name: '切り捨て'}
            ]);

        }


        toggle(): void {
            this.show(!this.show());

        }

        reflectSystemReferenceDateInformation(): void {
        }

        reflect(): void {
        }

        newCharacterSetting(): void {
            this.screenModeCreate(true);

        }

        saveCharacterSetting(): void {
        }

        cancelCharacterSetting(): void {
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            service.initData().done(function (data) {
                console.log(data);
                dfd.resolve();
            });

            return dfd.promise();
        }

    }


}
