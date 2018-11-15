module nts.uk.pr.view.cmm015.a.viewmodel {

    import SalaryClsInfoDto = nts.uk.pr.view.cmm015.a.viewmodel.model.SalaryClsInfoDto;

    export class ScreenModel {
        dataSource: KnockoutObservableArray<model.SalaryClsInfoDto>;
        currentCode: KnockoutObservable<number>;
        currentItem: KnockoutObservable<model.SalaryClsInfoModel>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        memo: KnockoutObservable<string>;
        isDeleteEnable: KnockoutObservable<boolean>;

        constructor() {
            var self = this;
            self.dataSource = ko.observableArray([]);
            self.currentCode = ko.observable();
            self.columns = ko.observableArray([
                {headerText: 'コード', key: 'salaryClsCode', width: 90},
                {headerText: '名称', key: 'salaryClsName'}
            ]);
            self.memo = ko.observable();
            self.isDeleteEnable = ko.observable(false);
            self.currentItem = ko.observable(new model.SalaryClsInfoModel(new model.SalaryClsInfoDto(), false));
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            service.findAllSalaryClsInfo().done(function (data) {
                self.dataSource(data);
                if(self.dataSource().length === 0) {
                    self.setNewMode();
                } else {
                    self.setUpdateMode();
                    self.currentCode(self.dataSource()[0].salaryClsCode);
                    self.currentItem(self.find(self.currentCode));
                }
                dfd.resolve();
            }).fail(function (res) {

            });
            return dfd.promise();
        }

        setNewMode() {
            var self = this;
            self.currentItem().reset();
            self.currentCode(null);
            self.isDeleteEnable(false);
            $('#inp-01-code').focus();
        }

        setUpdateMode() {
            var self = this;
            self.isDeleteEnable(true);
            $('#inp-02-name').focus();
        }

        registerSalaryClsInfo() {
            var self = this;

        }

        deleteSalaryClsInfo() {
            var self = this;
        }

        getSalaryClsInfo(salaryClsCode: string) {
            var self = this;
            service.getSalaryClsInfo(salaryClsCode).done(function(salaryClsInfo: viewmodel.model.SalaryClsInfoDto) {
                if (salaryClsInfo) {
                    self.currentCode(salaryClsInfo.salaryClsCode);
                    self.currentItem(salaryClsInfo, true);
                }
            });
        }

        find(code: string) {
            var self = this;
            return _.find(self.dataSource, function (salaryClsInfo: SalaryClsInfoDto) {
                return salaryClsInfo.salaryClsCode == code;
            })
        }
    }

    export module model {
        export class SalaryClsInfoModel {
            salaryClsCode: KnockoutObservable<string>;
            salaryClsName: KnockoutObservable<string>;
            memo: KnockoutObservable<string>;
            isCodeEnable: KnockoutObservable<boolean>;

            constructor(dto: SalaryClsInfoDto, enable: boolean) {
                var self = this;
                self.salaryClsCode = ko.observable(dto.salaryClsCode);
                self.salaryClsName = ko.observable(dto.salaryClsName);
                self.memo = ko.observable(dto.memo);
                self.isCodeEnable = ko.observable(enable);
            }

            reset() {
                var self = this;
                self.salaryClsCode("");
                self.salaryClsName("");
                self.memo("");
                self.isCodeEnable(true);
            }
        }

        export class SalaryClsInfoDto {
            salaryClsCode: string;
            salaryClsName: string;
            memo: string;

            constructor(salaryClsCode?: string, salaryClsName?: string, memo?: string) {
                var self = this;
                self.salaryClsCode = salaryClsCode;
                self.salaryClsName = salaryClsName;
                self.memo = memo;
            }
        }
    }

}