module qmm023.a.viewmodel {
    export class ScreenModel {

        items: KnockoutObservableArray<TaxModel>;
        columns: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        currentTax: KnockoutObservable<TaxModel>;
        isUpdate: KnockoutObservable<boolean>;
        allowEditCode: KnockoutObservable<boolean>;
        isEnableDeleteBtn: KnockoutObservable<boolean>;
        constructor() {
            var self = this;
            self._init();
            //get event when hover on table by subcribe
            self.currentCode.subscribe(function(codeChanged) {
                if (!nts.uk.text.isNullOrEmpty(codeChanged)) {
                    self.currentTax(ko.mapping.fromJS(self.getTax(codeChanged)));
                    self.allowEditCode(false);
                    self.isUpdate(true);
                    self.isEnableDeleteBtn(true);
                } else {
                    self.refreshLayout();
                }
            });
        }

        _init(): void {
            let self = this;
            self.items = ko.observableArray([]);
            this.columns = ko.observableArray([
                { headerText: 'コード', prop: 'code', width: 50 },
                { headerText: '名称', prop: 'name', width: 120 },
                { headerText: '限度額', prop: 'taxLimit', width: 170 }
            ]);
            self.currentCode = ko.observable(null);
            self.currentTax = ko.observable(new TaxModel('', '', 0));
            self.isUpdate = ko.observable(true);
            self.allowEditCode = ko.observable(false);
            self.isEnableDeleteBtn = ko.observable(true);
            if (self.items.length <= 0) {
                self.allowEditCode = ko.observable(true);
                self.isUpdate = ko.observable(false);
                self.isEnableDeleteBtn = ko.observable(false);
            }

        }

        getTax(codeNew: string): TaxModel {
            let self = this;
            let tax: TaxModel = _.find(self.items(), function(item) {
                return item.code === codeNew;
            });
            return tax;
        }

        refreshLayout(): void {
            let self = this;
            self.allowEditCode(true);
            self.currentTax(ko.mapping.fromJS(new TaxModel('', '', 0)));
            self.currentCode(null);
            self.isUpdate(false);
            self.isEnableDeleteBtn(false);
            $('.save-error').ntsError('clear');
        }

        insertUpdateData(): void {
            let self = this;
            let newCode = ko.mapping.toJS(self.currentTax().code);
            if (nts.uk.text.isNullOrEmpty(newCode)) {
                return;
            }
            let insertUpdateModel = new InsertUpdateModel(nts.uk.text.padLeft(newCode, '0', 2), self.currentTax().name, self.currentTax().taxLimit);
            service.insertUpdateData(self.isUpdate(), insertUpdateModel).done(function() {
                $.when(self.getCommuteNoTaxLimitList()).done(function() {
                    self.currentCode(nts.uk.text.padLeft(newCode, '0', 2));
                });
                if (self.isUpdate() === false) {
                    self.isUpdate(true);
                    self.allowEditCode(false);
                }
            }).fail(function(error) {
                if(error.message === '1'){
                     $('#INP_002').ntsError('set',nts.uk.text.format('{0}が入力されていません。', 'コード'));
                } else if(error.message === '2'){
                     $('#INP_003').ntsError('set',nts.uk.text.format('{0}が入力されていません。', '名称'));
                } else {
                     $('#INP_002').ntsError('set',nts.uk.text.format('入力した{0}は既に存在しています。\r\n{1}を確認してください。', 'コード' , 'コード'));
                }
            });

        }

        deleteData(): void {
            let self = this;
            let deleteCode = ko.mapping.toJS(self.currentTax().code);
            service.deleteData(new DeleteModel(deleteCode)).done(function() {
                let indexItemDelete = _.findIndex(self.items(), function(item) { return item.code == deleteCode; });
                $.when(self.getCommuteNoTaxLimitList()).done(function() {
                    if (self.items().length === 0) {
                        self.allowEditCode(true);
                        self.isUpdate(false);
                        self.refreshLayout();
                        return;
                    }
                    if (self.items().length == indexItemDelete) {
                        self.currentCode(self.items()[indexItemDelete - 1].code);
                        return;
                    }

                    if (self.items().length < indexItemDelete) {
                        self.currentCode(self.items()[0].code);
                        return;
                    }

                    if (self.items().length > indexItemDelete) {
                        self.currentCode(self.items()[indexItemDelete].code);
                        return;
                    }
                });

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
        startPage(): any {
            let self = this;
            let dfd = $.Deferred();
            $.when(self.getCommuteNoTaxLimitList()).done(function() {
                self.currentTax(ko.mapping.fromJS(new TaxModel('', '', 0)));
                if (self.items().length > 0) {
                    self.currentTax(_.first(self.items()));
                    self.currentCode(self.currentTax().code)
                }
                dfd.resolve();
            }).fail(function(error) {
                alert(error.message);
            });
            return dfd.promise();
        }

        getCommuteNoTaxLimitList(): any {
            let self = this;
            let dfd = $.Deferred();
            service.getCommutelimitsByCompanyCode().done(function(data) {
                self.items([]);
                _.forEach(data, function(item) {
                    self.items.push(new TaxModel(item.commuNoTaxLimitCode, item.commuNoTaxLimitName, item.commuNoTaxLimitValue));
                });
                dfd.resolve(data);
            }).fail(function(error) {
                alert(error.message);
            });
            return dfd.promise();
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