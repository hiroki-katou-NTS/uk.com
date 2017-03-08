module nts.uk.pr.view.qpp018.a {
    export module viewmodel {
        export class ScreenModel {

            //date: KnockoutObservable<Date>;
            yearMonth: KnockoutObservable<string>;
            checked: KnockoutObservable<boolean>;
            isEqual: KnockoutObservable<boolean>;
            isDeficent: KnockoutObservable<boolean>;
            isRedundant: KnockoutObservable<boolean>;
            insuranceOffice: KnockoutObservableArray<InsuranceOfficeModel>;
            columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
            selectedOfficeList: KnockoutObservableArray<InsuranceOfficeModel>;
            exportDataDetails: KnockoutObservable<string>;

            constructor() {
                //this.date = ko.observable(new Date('2016/12/01'));
                this.yearMonth = ko.observable('2016/05');
                this.checked = ko.observable(true);
                this.isEqual = ko.observable(true);
                this.isDeficent = ko.observable(true);
                this.isRedundant = ko.observable(true);
                this.insuranceOffice = ko.observableArray<InsuranceOfficeModel>([
                    //                new InsuranceOfficeModel('0001','Insurance Office 1'),
                    //                new InsuranceOfficeModel('0002','Insurance Office 2'),
                    //                new InsuranceOfficeModel('0003','Insurance Office 3')
                ]);
                this.columns = ko.observableArray<nts.uk.ui.NtsGridListColumn>([
                    { headerText: 'コード', prop: 'code', width: 100 },
                    { headerText: '名称 ', prop: 'name', width: 100 }
                ]);
                this.selectedOfficeList = ko.observableArray<InsuranceOfficeModel>([]);
                this.exportDataDetails = ko.observable('Something');
            }

            /**
             *  Open Print Setting Screen
             */
            openPrintSettingScr() {
                // Set parent value
                nts.uk.ui.windows.setShared("socialInsuranceFeeChecklist", null);
                //            nts.uk.ui.windows.setShared("isTransistReturnData", this.isTransistRetu            
                nts.uk.ui.windows.sub.modal("/view/qpp/018/c/index.xhtml", { title: "印刷の設定" }).onClosed(() => {
                    // Get child value
                    var returnValue = nts.uk.ui.windows.getShared("printSettingValue");
                });
            }

            /**
             *  Export Data
             */

            exportData(): any {
                var self = this;
                var dfd = $.Deferred<any>();
                if ((this.yearMonth() == '') || (this.selectedOfficeList() == null) || (!(this.isEqual()) && !(this.isDeficent()) && !(this.isRedundant()))) {
                    alert("Something is not right");
                    return;
                }
//                nts.uk.ui.windows.setShared("exportDataDetails", this.exportDataDetails(), true);
//                nts.uk.ui.windows.close();
//                alert("Exported: " + this.exportDataDetails());
                service.saveAsPdf().done(function() {
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                    dfd.reject();
                });
            }
            public start(): JQueryPromise<any> {
                var dfd = $.Deferred<any>();
                var self = this;
                // TODO: check for start from menu salary or bonus.
                $.when(self.loadAllInsuranceOffice()).done(function() {
                    dfd.resolve();
                })
                return dfd.promise();
            }

            public loadAllInsuranceOffice(): JQueryPromise<any> {
                var dfd = $.Deferred<any>();
                var self = this;
                service.getAllInsuranceOffice().done(function(data: service.model.InsuranceOffice[]) {
                    self.insuranceOffice(data);
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                    dfd.reject();
                })
                return dfd.promise();
            }

        }

        /**
     * Class InsuranceOfficeMo    */      

        export class InsuranceOfficeModel {
            code: string;
            name: string;

            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }
    }
}