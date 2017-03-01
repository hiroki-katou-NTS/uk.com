module qmm023.a.viewmodel {
    export class ScreenModel {

        items: KnockoutObservableArray<TaxModel>;
        columns: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        currentTax: KnockoutObservable<TaxModel>;
        isUpdate: KnockoutObservable<boolean>;
        allowEditCode: KnockoutObservable<boolean>;

        constructor() {
            var self = this;
            self.unit(self);
            //get event when hover on table by subcribe
            self.currentCode.subscribe(function(codeChanged) {
                self.currentTax(ko.mapping.fromJS(self.getTax(codeChanged)));
                if (self.currentTax()) {
                    self.allowEditCode(false);
                    self.isUpdate(true);
                }
            });
        }

        unit(self: any): void {
            self.items = ko.observableArray([]);
            this.columns = ko.observableArray([
                { headerText: 'コード', prop: 'code', width: 50 },
                { headerText: '名称', prop: 'name', width: 120 },
                { headerText: '説明', prop: 'taxLimit', width: 170 }
            ]);
            self.currentCode = ko.observable(null);
            self.currentTax = ko.observable(null);
            self.isUpdate = ko.observable(true);
            self.allowEditCode = ko.observable(false);

        }

        getTax(codeNew: string): TaxModel {
            let self = this;
            var tax: TaxModel = _.find(self.items(), function(item) {
                return item.code === codeNew;
            });
            return tax;
        }

        refreshLayout(): void {
            let self = this;
            self.allowEditCode(true);
            self.currentTax(ko.mapping.fromJS(new TaxModel('', '', null)));
            self.isUpdate(false);
        }

        insertUpdateData(): void {
            let self = this;
            let insertUpdateModel = new InsertUpdateModel(self.currentTax().code, self.currentTax().name, self.currentTax().taxLimit);
            service.insertUpdateData(self.isUpdate(), insertUpdateModel).done(function() {
                if (self.isUpdate() === false) {
                    self.items.push(_.cloneDeep(ko.mapping.toJS(self.currentTax())));
                    self.isUpdate(true);
                    self.allowEditCode(false);
                    self.currentCode(self.currentTax().code());
                } else {
                    let indexItemUpdate = _.findIndex(self.items(), function(item) { return item.code == self.currentTax().code(); });
                    self.items().splice(indexItemUpdate, 1, _.cloneDeep(ko.mapping.toJS(self.currentTax())));
                    self.items.valueHasMutated();
                }
            }).fail(function(error) {
                alert(error.message);
            });

        }

        deleteData(): void {
            let self = this;
            let deleteCode = self.currentTax().code();
            service.deleteData(new DeleteModel(deleteCode)).done(function() {
                let indexItemDelete = _.findIndex(self.items(), function(item) { return item.code == self.currentTax().code(); });
                self.items.remove(function(item) {
                    console.log(item.code + "===" + deleteCode);
                    return item.code === deleteCode;
                });
                self.items.valueHasMutated();
                if (self.items().length === 0) {
                    self.allowEditCode(true);
                    self.isUpdate(false);
                } else if (self.items().length === indexItemDelete) {
                    self.currentCode(self.items()[indexItemDelete - 1].code);
                } else {
                    self.currentCode(self.items()[indexItemDelete].code);
                }
            }).fail(function(error) {
                alert(error.message);
            });
        }

        alertDelete() {
            let self = this;
            if (confirm("do you wanna delete") === true) {
                self.deleteData();
            } else {
                alert("you didn't delete!");
            }
        }

        // startpage
        startPage(): JQueryPromise<Array<any>> {
            var self = this;
            return self.getCommuteNoTaxLimitList();
        }

        getCommuteNoTaxLimitList(): any {
            var self = this;
            var dfd = $.Deferred();
            service.getCommutelimitsByCompanyCode().done(function(data) {
                self.buildItemList(data);
                if (self.items().length > 0) {
                    self.currentTax(ko.mapping.fromJS(_.first(self.items())));
                    self.currentCode(self.currentTax().code())
                }
                dfd.resolve(data);
            }).fail(function(res) {

            });
            return dfd.promise();
        }

        buildItemList(data: any): any {
            var self = this;
            _.forEach(data, function(item) {
                self.items.push(new TaxModel(item.commuNoTaxLimitCode, item.commuNoTaxLimitName, item.commuNoTaxLimitValue));
            });
        }

    }

    class TaxModel {
        code: string;
        name: string;
        taxLimit: number;

        constructor(code: string, name: string, taxLimit: number) {
            this.code = code;
            this.name = name;
            this.taxLimit = taxLimit;
        }
    }


    export class InsertUpdateModel {
        commuNoTaxLimitCode: string;
        commuNoTaxLimitName: string;
        commuNoTaxLimitValue: number;

        constructor(commuNoTaxLimitCode: string, commuNoTaxLimitName: string,
            commuNoTaxLimitValue: number) {
            this.commuNoTaxLimitCode = commuNoTaxLimitCode;
            this.commuNoTaxLimitName = commuNoTaxLimitName;
            this.commuNoTaxLimitValue = commuNoTaxLimitValue;
        }
    }
    export class DeleteModel {
        commuNoTaxLimitCode: string;
        constructor(commuNoTaxLimitCode: string) {
            this.commuNoTaxLimitCode = commuNoTaxLimitCode;
        }
    }
}