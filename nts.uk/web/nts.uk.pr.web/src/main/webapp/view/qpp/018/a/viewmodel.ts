module nts.uk.pr.view.qpp018.a {
    export module viewmodel {
        import InsuranceOffice = service.model.InsuranceOffice;
        export class ScreenModel {
            
            yearMonth: KnockoutObservable<string>;
            isEqual: KnockoutObservable<boolean>;
            isDeficient: KnockoutObservable<boolean>;
            isRedundant: KnockoutObservable<boolean>;
            insuranceOffice: KnockoutObservable<InsuranceOfficeModel>;
            
            constructor() {
                let self = this;
                self.yearMonth = ko.observable("");
                self.isEqual = ko.observable(true);
                self.isDeficient = ko.observable(false);
                self.isRedundant = ko.observable(false);
                self.insuranceOffice = ko.observable(new InsuranceOfficeModel());
            }
            
            /**
             * start page
             */
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                self.yearMonth(self.getCurrentYearMonth());
                
                // TODO: check for start from menu salary or bonus.
                $.when(self.insuranceOffice().findAllInsuranceOffice()).done(function() {
                    dfd.resolve();
                })
                return dfd.promise();
            }
            
            /**
             *  Export Data
             */
            exportData(): void {
                let self = this;
                let dfd = $.Deferred<void>();
                self.clearAllError();
                if (self.validate()) {
                    return;
                }
                let command = self.toJSObjet();
                service.saveAsPdf(command).done(function() {
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                });
                
            }
            
            /**
             * to JSon Object
             */
            private toJSObjet(): any {
                let self = this;
                let command: any = {};
                command.yearMonth = self.yearMonth();
                command.isEqual = self.isEqual();
                command.isDeficient = self.isDeficient();
                command.isRedundant = self.isRedundant();
                command.insuranceOffices = self.insuranceOffice().selectedOfficeList();
                return command;
            }
            
            /**
             *  Show dialog ChecklistPrintSetting
             */
            showDialogChecklistPrintSetting(): void {
                nts.uk.ui.windows.setShared("socialInsuranceFeeChecklist", null);
                nts.uk.ui.windows.sub.modal("/view/qpp/018/c/index.xhtml", { title: "印刷の設定" }).onClosed(() => {
//                    let returnValue = nts.uk.ui.windows.getShared("printSettingValue");
                });
            }
            
            /**
             * validate
             */
            private validate(): boolean {
                let self = this;
                let isError = false;
                if (self.insuranceOffice().selectedOfficeList().length <= 0) {
                    $('#grid-error').ntsError('set', 'You must choose at least item of grid');
                    isError = true;
                }
                return isError;
            }
            
            private clearAllError(): void {
                $("#grid-error").ntsError('clear');
            }
            
            /**
             * get year and month current.
             */
            private getCurrentYearMonth(): string {
                let today = new Date();
                let month = today.getMonth() + 1; //January is 0!
                let year = today.getFullYear();
                let yearMonth = <string><any>year + '/';
                if (month < 10) {
                    yearMonth += '0' + <string><any>month;
                } else {
                    yearMonth += month.toLocaleString();
                }
                return yearMonth; 
            }

        }
        
        /**
          * Class InsuranceOfficeModel
          */
        export class InsuranceOfficeModel {
            
            columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
            items: KnockoutObservableArray<InsuranceOffice>;
            selectedOfficeList: KnockoutObservableArray<InsuranceOffice>;
            
            constructor() {
                let self = this;
                self.items = ko.observableArray<InsuranceOffice>([]);
                self.selectedOfficeList = ko.observableArray<InsuranceOffice>([]);
                self.columns = ko.observableArray<nts.uk.ui.NtsGridListColumn>([
                    { headerText: 'コード', key: 'code', width: 100 },
                    { headerText: '名称 ', key: 'name', width: 100 }
                ]);
            }
            
            /**
             * find list insurance office.
             */
            findAllInsuranceOffice(): JQueryPromise<InsuranceOffice[]> {
                let self = this;
                let dfd = $.Deferred<InsuranceOffice[]>();
                service.findAllInsuranceOffice().done(function(res: InsuranceOffice[]) {
                    self.items(res);
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                })
                return dfd.promise();
            }
        }
    }
}