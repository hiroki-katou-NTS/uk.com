module nts.uk.pr.view.qmm041.d.viewmodel {
    import getText = nts.uk.resource.getText;
    import getShared = nts.uk.ui.windows.getShared;
    import format = nts.uk.text.format;

    export class ScreenModel {
        employeeCode: string = null;
        employeeName: string = null;
        employeeId: string = null;
        employmentCode: string = null;
        baseYearMonth: KnockoutObservable<number> = ko.observable(parseInt(moment().format("YYYY/MM")));
        individualUnitPriceList: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        columns: any;
        currentCode: KnockoutObservable<any> = ko.observable(null);

        constructor() {
            let self = this;
            self.columns = [
                {key:'empId', length: 0, hidden: true},
                {headerText: getText('QMM041_8'), key: 'code', width: 70, formatter: _.escape },
                {headerText: getText('QMM041_9'), key: 'name', width: 180, formatter: _.escape},
                {headerText: getText('QMM041_16'), key: 'period', width: 150, formatter: _.escape},
                {headerText: getText('QMM041_18'), key: 'amount', width: 120, formatter: _.escape, template: "<div style='text-align: right'>${amount}</div>"}
            ];
        }

        cancel() {
            nts.uk.ui.errors.clearAll();
            nts.uk.ui.windows.close();
        }

        extract() {
            let self = this;
            let dto = {
                employeeId: self.employeeId,
                baseYearMonth: self.baseYearMonth()
            };
            self.individualUnitPriceDisplay(dto);
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            let params = getShared("QMM041_D_PARAMS");
            self.employeeCode = params.employeeCode;
            self.employeeName = params.employeeName;
            self.employeeId = params.employeeId;
            self.employmentCode = params.employmentCode;
            service.processYearFromEmp(self.employmentCode).done((yearMonth) => {
                if (yearMonth != 0) self.baseYearMonth(yearMonth);
                let dto = {
                    employeeId: self.employeeId,
                    baseYearMonth: self.baseYearMonth()
                };
                self.individualUnitPriceDisplay(dto).done(() => {
                    dfd.resolve();
                });
            });
            return dfd.promise();
        }

        individualUnitPriceDisplay(dto): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            service.individualUnitPriceDisplay(dto).done((data) => {
                    let array = [];
                    if (data.length > 0) {
                        for (let i = 0; i < data.length; i++) {
                            array.push(new ItemModel(data[i].employeeId, data[i].perUnitPriceCode, data[i].perUnitPriceName,
                                format(getText("QMM041_13"), self.formatYM(data[i].startYearMonth), self.formatYM(data[i].endYearMonth)),
                                data[i].amountOfMoney
                            ));
                        }
                    }
                    self.individualUnitPriceList(array);
                dfd.resolve();
                }
            );
            return dfd.promise();
        }

        formatYM(intYM) {
            return nts.uk.time.parseYearMonth(intYM).format();
        }
    }

    class ItemModel {
        empId: string;
        code: string;
        name: string;
        period: string;
        amount: string;

        constructor(empId: string, code: string, name: string, period: string, amount: string) {
            this.empId = empId;
            this.code = code;
            this.name = name;
            this.period = period;
            this.amount = amount;
        }
    }
}
