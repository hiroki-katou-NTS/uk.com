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

        constructor() {
            var self = this;
            self.referenceYear = ko.observable(parseInt(moment(Date.now()).format("YYYYMM")));
            self.items = ko.observableArray();
            this.currentCode = ko.observable();

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

        startPage(params): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.params = getShared('QMM039_D_PARAMS');
            self.itemClassification(self.params.itemClassification);
            self.empCode(self.params.empCode);
            self.empName(self.params.empName);
            self.startupScreen();
            dfd.resolve();
            return dfd.promise();
        }

        startupScreen() {
            var self = this;

            service.processYearFromEmp(self.params.personalValCode).done(function (data) {
                if(data != 0){
                    self.referenceYear(data);
                }
                let dto = {
                    empId: self.params.empId,
                    cateIndicator: self.params.cateIndicator,
                    salBonusCate: self.params.salBonusCate,
                    currentProcessYearMonth: self.referenceYear()
                }
                self.getSalIndAmountHis(dto);
            })
        }

        getSalIndAmountHis(dto) {
            let self = this;
            service.salIndAmountHisDisplay(dto).done(function (data) {
                    let array = [];
                    if (data != null) {
                        for (let i = 0; i < data.length; i++) {
                            for (let j = 0; j < data[i].period.length; j++) {
                                array.push(new ItemModel(data[i].empId,data[i].perValCode, data[i].perValName,
                                    format(getText("QMM039_18"), self.formatYM(data[i].period[j].periodStartYm), self.formatYM(data[i].period[j].periodEndYm)), data[i].salIndAmountList[j].amountOfMoney + "¥"
                                ))
                            }
                        }
                    }
                    self.items(array);
                $('#D2_8').focus();
                }
            )
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
