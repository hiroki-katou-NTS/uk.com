module nts.uk.pr.view.qpp018.c {
    //import CheckListPrintSettingDto = nts.uk.pr.view.qpp018.c.service.model.CheckListPrintSettingDto;
    export module viewmodel {
        export class ScreenModel {
            required: KnockoutObservable<boolean>;
            showHealthInsuranceType: KnockoutObservableArray<HealthInsuranceType>;
            enable: KnockoutObservable<boolean>;
            selectedCode: KnockoutObservable<string>;
            printSettingValue: KnockoutObservable<string>;
            checkListPrintSettingModel: KnockoutObservable<CheckListPrintSettingModel>;

            constructor() {
                var self = this;
                self.required = ko.observable(true);
                self.showHealthInsuranceType = ko.observableArray<HealthInsuranceType>([
                    new HealthInsuranceType('1', '表示する'),
                    new HealthInsuranceType('2', '表示しない'),
                ]);

                self.selectedCode = ko.observable('1');
                self.enable = ko.observable(true);
                self.printSettingValue = ko.observable('PrintSetting Value');
                self.checkListPrintSettingModel = ko.observable(new CheckListPrintSettingModel());
            }
            closePrintSetting() {
                // Set child value
                nts.uk.ui.windows.setShared("printSettingValue", this.printSettingValue(), true);
                nts.uk.ui.windows.close();
            }
            setupPrintSetting() {
                var self = this;
                if (!(self.checkListPrintSettingModel().showCategoryInsuranceItem())
                    && !(self.checkListPrintSettingModel().showDeliveryNoticeAmount())
                    && !(self.checkListPrintSettingModel().showDetail())
                    && !(self.checkListPrintSettingModel().showOffice())) {
                    alert("Something is not right");
                } else {
                    nts.uk.ui.windows.setShared("printSettingValue", this.printSettingValue(), true);
                    service.saveCheckListPrintSetting(self.checkListPrintSettingModel().toDto()).done(data => {
                        alert("Something is not right YES");
                    }).fail(function(res) {
                        alert("Something is not right NO");
                    })
                    nts.uk.ui.windows.close();
                }
            }

            public start(): JQueryPromise<any> {
                var dfd = $.Deferred<any>();
                var self = this;
                self.loadAllCheckListPrintSetting().done(data => {
                    dfd.resolve(self);
                });
                return dfd.promise();
            }

            public loadAllCheckListPrintSetting(): JQueryPromise<any> {
                var dfd = $.Deferred<any>();
                var self = this;
                service.findCheckListPrintSetting().done(data => {
                    self.checkListPrintSettingModel().setData(data);
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                    dfd.reject();
                })
                return dfd.promise();
            }
            private saveCheckListPrintSetting() {
                var self = this;

            }

        }
        /**
         * Class HealthInsuranceType
         */
        export class HealthInsuranceType {
            code: string;
            name: string;

            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }

        /**
         * Class CheckListPrintSettingModel 
         */
        export class CheckListPrintSettingModel {
            showCategoryInsuranceItem: KnockoutObservable<boolean>;
            showDeliveryNoticeAmount: KnockoutObservable<boolean>;
            showDetail: KnockoutObservable<boolean>;
            showOffice: KnockoutObservable<boolean>;
            constructor() {
                this.showCategoryInsuranceItem = ko.observable(false);
                this.showDeliveryNoticeAmount = ko.observable(false);
                this.showDetail = ko.observable(false);
                this.showOffice = ko.observable(false);
            }
            setData(checkListPrintSettingDto: service.model.CheckListPrintSettingDto) {
                this.showCategoryInsuranceItem(checkListPrintSettingDto.showCategoryInsuranceItem);
                this.showDeliveryNoticeAmount(checkListPrintSettingDto.showDeliveryNoticeAmount);
                this.showDetail(checkListPrintSettingDto.showDetail);
                this.showOffice(checkListPrintSettingDto.showOffice);
            }
            resetValue() {
                this.showCategoryInsuranceItem(false);
                this.showDeliveryNoticeAmount(false);
                this.showDetail(false);
                this.showOffice(false);
            }
            toDto(): service.model.CheckListPrintSettingDto {
                var checkListPrintSettingDto: service.model.CheckListPrintSettingDto;
                checkListPrintSettingDto = new service.model.CheckListPrintSettingDto();
                checkListPrintSettingDto.showCategoryInsuranceItem = this.showCategoryInsuranceItem();
                checkListPrintSettingDto.showDeliveryNoticeAmount = this.showDeliveryNoticeAmount();
                checkListPrintSettingDto.showDetail = this.showDetail();
                checkListPrintSettingDto.showOffice = this.showOffice();
                return checkListPrintSettingDto;

            }
        }
    }

}