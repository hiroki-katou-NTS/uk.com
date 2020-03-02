module nts.uk.pr.view.qmm039.d.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import hasError = nts.uk.ui.errors.hasError;
    import format = nts.uk.text.format;

    export class ScreenModel {
        empCode: KnockoutObservable<string> = ko.observable('');
        empName: KnockoutObservable<string> = ko.observable('');
        itemClassification: KnockoutObservable<string> = ko.observable('');
        params: any;
        referenceYear: KnockoutObservable<number>;
        items: KnockoutObservableArray<ItemModel>;
        currentCode: KnockoutObservable<any>;
        columns: any;

        constructor() {
            var self = this;
            self.referenceYear = ko.observable(parseInt(moment(Date.now()).format("YYYYMM")));
            self.items = ko.observableArray();
            self.currentCode = ko.observable();
            self.columns = [
                {key:'empId', length: 0, hidden: true},
                {headerText: getText('QMM039_13'), key: 'code', width: 70, formatter: _.escape },
                {headerText: getText('QMM039_14'), key: 'name', width: 180, formatter: _.escape},
                {headerText: getText('QMM039_23'), key: 'period', width: 150, formatter: _.escape},
                {headerText: getText('QMM039_25'), key: 'amount', width: 120, formatter: _.escape, template: "<div style='text-align: right'>${amount}</div>"}
            ];

        }

        cancel() {
            nts.uk.ui.errors.clearAll();
            nts.uk.ui.windows.close();
        }

        extract() {
            var self = this;
            let dto = {
                empId: self.params.empId,
                cateIndicator: self.params.cateIndicator,
                salBonusCate: self.params.salBonusCate,
                currentProcessYearMonth: self.referenceYear(),

            }
            self.getSalIndAmountHis(dto);
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.params = getShared('QMM039_D_PARAMS');
            switch(self.params.itemClassification) {
                case 0:
                    self.itemClassification(format(getText('QMM039_21'), '給与支給'));
                    break;
                case 1:
                    self.itemClassification(format(getText('QMM039_21'), '給与控除'));
                    break;
                case 2:
                    self.itemClassification(format(getText('QMM039_21'), '賞与支給'));
                    break;
                case 3:
                    self.itemClassification(format(getText('QMM039_21'), '賞与控除'));
                    break;
                default:
                    break;
            }

            self.empCode(self.params.empCode);
            self.empName(self.params.empName);
            if(self.params.personalValCode) {
                self.startupScreen();
            }
            dfd.resolve();
            return dfd.promise();
        }

        startupScreen() {
            var self = this;
            service.getEmploymentCode(self.params.empId).done((dto) => {
                if (dto) {
                    service.processYearFromEmp(dto.employmentCode).done((data) => {
                        if (data != 0) {
                            self.referenceYear(data);
                        }
                        let dto = {
                            empId: self.params.empId,
                            cateIndicator: self.params.cateIndicator,
                            salBonusCate: self.params.salBonusCate,
                            currentProcessYearMonth: self.referenceYear()
                        };
                        self.getSalIndAmountHis(dto);
                    });
                } else {
                    let dto = {
                        empId: self.params.empId,
                        cateIndicator: self.params.cateIndicator,
                        salBonusCate: self.params.salBonusCate,
                        currentProcessYearMonth: self.referenceYear()
                    };
                    self.getSalIndAmountHis(dto);
                }
            });
        }

        getSalIndAmountHis(dto) {
            let self = this;
            service.salIndAmountHisDisplay(dto).done(function (data) {
                    let array = [];
                    if (data != null) {
                        for (let i = 0; i < data.length; i++) {
                            for (let j = 0; j < data[i].period.length; j++) {
                                array.push(new ItemModel(data[i].empId,data[i].perValCode, data[i].perValName,
                                    format(getText("QMM039_18"), self.formatYM(data[i].period[j].periodStartYm), self.formatYM(data[i].period[j].periodEndYm)), data[i].salIndAmountList[j].amountOfMoney
                                ))
                            }
                        }
                    }
                    self.items(array);
                $('#D2_8').focus();
                }
            ).fail((res) => {
                nts.uk.ui.dialog.alertError(res.message);
            })
        }

        formatYM(intYM) {
            return intYM.toString().substr(0, 4) + '/' + intYM.toString().substr(4, intYM.length);
        }
    }
    class ItemModel {
        empId:string;

        code: string;
        //D3_6 コード
        name: string;
        //D3_7 名称
        period: string;
        //D3_8 期間
        amount: string;
        //D3_9 金額
        constructor(empId:string,code: string, name: string, period: string, amount: string) {
            this.empId=empId;
            this.code = code;
            this.name = name;
            this.period = period;
            this.amount = amount;
        }
    }
}
