module nts.uk.pr.view.qmm035.a {
    export module viewmodel {
        import text = nts.uk.resource.getText;
        import confirm = nts.uk.ui.dialog.confirm;
        import block = nts.uk.ui.block;
        export class ScreenModel {
            items: KnockoutObservableArray<CompanyStatutoryWriteOverView>;
            columns2: KnockoutObservableArray<NtsGridListColumn>;
            currentCode: KnockoutObservable<any> = ko.observable('');
            count: number = 100;
            switchOptions: KnockoutObservableArray<any>;

            tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            selectedTab: KnockoutObservable<string>;

            simpleValue: KnockoutObservable<string>;

            detail: KnockoutObservable<CompanyStatutoryWrite> = ko.observable(null);

            value: KnockoutObservable<any> = ko.observable('');
            isEnable: KnockoutObservable<boolean>;
            isEditable: KnockoutObservable<boolean>;

            isEnableCode: KnockoutObservable<boolean> = ko.observable(false);
            isEnableBtnDelete: KnockoutObservable<boolean> = ko.observable(false);
            isEnableBtnPdf: KnockoutObservable<boolean> = ko.observable(false);
            isEnableBtnCreate: KnockoutObservable<boolean> = ko.observable(false);

            values: KnockoutObservable<string>;

            constructor() {
                let self = this;
                self.items = ko.observableArray([]);
                value: ko.observable(''),
                nts.uk.pr.view.qmm035.a.service.defaultData().done(function(response) {
                    block.invisible();

                    for (let i = 0; i < response.length; i++) {
                        self.items.push(new CompanyStatutoryWriteOverView(response[i].code, response[i].name));
                        self.isEnableBtnDelete(true);
                        self.isEnableBtnCreate(true);
                    }

                    self.currentCode(response[0].code);

                    if (response.length > 0) {
                        _.defer(function() {
                            $("#A4_5").focus();
                        });
                    } else {
                        _.defer(function() {
                            $("#A4_4").focus();
                        });
                    }
                    block.clear();
                });

                this.columns2 = ko.observableArray([
                    { headerText: text('QMM035_8'), key: 'code', width: 120, formatter: _.escape},
                    { headerText: text('QMM035_9'), key: 'name', width: 200, formatter: _.escape }
                ]);

                self.tabs = ko.observableArray([
                    { id: 'tab-1', title: text('QMM035_10'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: text('QMM035_11'), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) }
                ]);
                self.selectedTab = ko.observable('tab-1');

                self.isEnable = ko.observable(true);
                self.isEditable = ko.observable(true);
                self.isEnableCode(true);
                self.values = ko.observable('');

                self.currentCode.subscribe(function(codeId) {
                    nts.uk.ui.errors.clearAll();
                    if (codeId) {
                        self.setTabIndex();
                        nts.uk.pr.view.qmm035.a.service.findByCode(codeId).done(function(response) {
                            self.isEnableBtnDelete(true);
                            self.isEnableBtnCreate(true);
                            self.detail(new CompanyStatutoryWrite(response));
                            self.isEnableCode(false);
                            _.defer(function() {
                                $("#A4_5").focus();
                            });
                        });
                        setTimeout(function () {
                            $("tr[data-id="+ self.currentCode()+"] ").focus();
                            _.defer(function() {
                                $("#A4_5").focus();
                            });
                        }, 500);
                        setTimeout(function () {
                            _.defer(function() {
                                $("#A4_5").focus();
                            });
                        }, 800);
                    }

                });

            }

            /**
             * update
             */
            private update(): void {
                let self = this;
                self.isEnableBtnDelete(true);
                block.invisible();
                nts.uk.ui.errors.clearAll();
                $('.nts-input').trigger("validate");
                if (nts.uk.ui.errors.hasError()) {
                    self.isEnableBtnDelete(false);
                    block.clear();
                    return
                }
                if (self.currentCode() == null) {

                    nts.uk.pr.view.qmm035.a.service.create(ko.toJS(self.detail)).done(function(response) {

                        if (response.msg == 'Msg_3') {
                            nts.uk.ui.dialog.error({ messageId: "Msg_3" }).then(function() {
                                self.isEnableBtnDelete(false);
                            });
                        } else {
                            self.isEnableBtnCreate(true);
                            self.items([]);
                            for (let i = 0; i < response.companies.length; i++) {
                                self.items.push(new CompanyStatutoryWriteOverView(response.companies[i].code, response.companies[i].name));
                            }
                            nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {

                                self.currentCode(response.codeSelected);
                                self.isEnableCode(false);

                            });
                            _.defer(function() {
                                $("#A4_4").focus();
                            });
                        }
                        block.clear();
                    });
                } else {
                    nts.uk.pr.view.qmm035.a.service.update(ko.toJS(self.detail)).done(function(response) {
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                            /**
                             *  selected
                             */
                            for (let i = 0; i < self.items().length; i++) {{
                                if (self.items()[i].code == response[0])
                                    self.items()[i].name = response[1];
                                    self.items.valueHasMutated();
                                }
                            }
                            self.isEnableCode(false);
                            $("#A4_5").focus();

                        });
                        block.clear();
                    });
                }

            }

            /**
             * create
             */
            private create(): void {
                let self = this;
                nts.uk.ui.errors.clearAll();
                self.detail(new CompanyStatutoryWrite());
                self.isEnableCode(true);
                self.currentCode(null);
                self.isEnableBtnDelete(false);
                self.isEnableBtnCreate(false);
                $("#A4_4").focus();
                self.setTabIndex();
            }

            /**
             *  close dialog
             */

            private closeDialog(): void {
                nts.uk.ui.windows.close();
            }

            /**
             * delete
             */
            private deleteCompany(): void {
                block.invisible();
                confirm({ messageId: "Msg_18" }).ifYes(() => {
                    let self = this;
                    let command = { code: self.currentCode() };
                    nts.uk.pr.view.qmm035.a.service.deleteCompany(command).done(function(response) {
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(function() {
                            let parameter;
                            for (let i = 0; i < self.items().length; i++) {
                                if (self.items()[i].code == response[0]) {
                                    //delete self.items()[i];
                                    self.items(self.items().filter(x => x.code !== response[0]));
                                    self.items.valueHasMutated();
                                    if (self.items().length == 0) {
                                        self.create();
                                    } else if (self.items().length == i) {
                                         parameter = i - 1;
                                    } else {
                                         parameter = i;

                                    }
                                    if (!self.isEnableCode()) {
                                        self.currentCode(self.items()[parameter].code);
                                    }
                                }
                            }
                            setTimeout(function () {
                                $("tr[data-id="+ self.currentCode()+"] ").focus();
                                _.defer(function() {
                                    $("#A4_5").focus();
                                });
                            }, 500);
                            setTimeout(function () {
                                _.defer(function() {
                                    $("#A4_5").focus();
                                });
                            }, 800);
                        });
                        block.clear();
                    });
                }).ifNo(() => {
                    block.clear();
                });

            }

            public setTabIndex(){
                let self = this;
                setTimeout(function () {
                    $('#A4_35').keydown(function (e) {
                        var code = e.keyCode || e.which;
                        if (code === 9 && !e.shiftKey) {
                            e.preventDefault();
                            self.selectedTab("tab-2");
                            $('#A5_2').focus();
                        }
                    });
                    $('#A5_2').keydown(function (e) {
                        if (e.shiftKey && e.key === 'Tab') {
                            e.preventDefault();
                            self.selectedTab("tab-1");
                            $('#A4_35').focus();
                        }
                    });
                }, 2000);
            }
        }

        class CompanyStatutoryWriteOverView {
            code: KnockoutObservable<string> = ko.observable(null);
            name: KnockoutObservable<string> = ko.observable(null);
            constructor(code: KnockoutObservable<string>, name: KnockoutObservable<string>) {
                this.code = code;
                this.name = name;
            }
        }


        export interface ICompanyStatutoryWrite {
            cID: string;
            code: string;
            name: string;
            kanaName: string;
            address1: string;
            address2: string;
            addressKana1: string;
            addressKana2: string;
            phoneNumber: string;
            postalCode: string;
            notes: string;
            clubRepresentativePosition: string;
            clubRepresentativeName: string;
            linkingDepartment: string;
            corporateNumber: number;
            accountingOfficeTelephoneNumber: string;
            accountingOfficeName: string;
            salaryPaymentMethodAndDueDate1: string;
            salaryPaymentMethodAndDueDate2: string;
            salaryPaymentMethodAndDueDate3: string;
            accountManagerName: string;
            businessLine1: string;
            businessLine2: string;
            businessLine3: string;
            taxOffice: string;
            vibrantLocationFinancialInstitutions: string;
            nameBankTransferInstitution: string;
            contactName: string;
            contactClass: string;
            contactPhoneNumber: string;
        }

        export class CompanyStatutoryWrite {
            cID: KnockoutObservable<string> = ko.observable(null);
            code: KnockoutObservable<string> = ko.observable(null);
            name: KnockoutObservable<string> = ko.observable(null);
            kanaName: KnockoutObservable<string> = ko.observable(null);
            address1: KnockoutObservable<string> = ko.observable(null);
            address2: KnockoutObservable<string> = ko.observable(null);
            addressKana1: KnockoutObservable<string> = ko.observable(null);
            addressKana2: KnockoutObservable<string> = ko.observable(null);
            phoneNumber: KnockoutObservable<string> = ko.observable(null);
            postalCode: KnockoutObservable<string> = ko.observable(null);
            notes: KnockoutObservable<string> = ko.observable(null);
            clubRepresentativePosition: KnockoutObservable<string> = ko.observable(null);
            clubRepresentativeName: KnockoutObservable<string> = ko.observable(null);
            linkingDepartment: KnockoutObservable<string> = ko.observable(null);
            corporateNumber: KnockoutObservable<number> = ko.observable(null);
            accountingOfficeTelephoneNumber: KnockoutObservable<string> = ko.observable(null);
            accountingOfficeName: KnockoutObservable<string> = ko.observable(null);
            salaryPaymentMethodAndDueDate1: KnockoutObservable<string> = ko.observable(null);
            salaryPaymentMethodAndDueDate2: KnockoutObservable<string> = ko.observable(null);
            salaryPaymentMethodAndDueDate3: KnockoutObservable<string> = ko.observable(null);
            accountManagerName: KnockoutObservable<string> = ko.observable(null);
            businessLine1: KnockoutObservable<string> = ko.observable(null);
            businessLine2: KnockoutObservable<string> = ko.observable(null);
            businessLine3: KnockoutObservable<string> = ko.observable(null);
            taxOffice: KnockoutObservable<string> = ko.observable(null);
            vibrantLocationFinancialInstitutions: KnockoutObservable<string> = ko.observable(null);
            nameBankTransferInstitution: KnockoutObservable<string> = ko.observable(null);
            contactName: KnockoutObservable<string> = ko.observable(null);
            contactClass: KnockoutObservable<string> = ko.observable(null);
            contactPhoneNumber: KnockoutObservable<string> = ko.observable(null);
            constructor(params?: ICompanyStatutoryWrite) {
                this.cID(params ? params.cID : null);
                this.code(params ? params.code : null);
                this.name(params ? params.name : null);
                this.kanaName(params ? params.kanaName : null);
                this.address1(params ? params.address1 : null);
                this.address2(params ? params.address2 : null);
                this.addressKana1(params ? params.addressKana1 : null);
                this.addressKana2(params ? params.addressKana2 : null);
                this.phoneNumber(params ? params.phoneNumber : null);
                this.postalCode(params ? params.postalCode : null);
                this.notes(params ? params.notes : null);
                this.clubRepresentativePosition(params ? params.clubRepresentativePosition : null);
                this.clubRepresentativeName(params ? params.clubRepresentativeName : null);
                this.linkingDepartment(params ? params.linkingDepartment : null);
                this.corporateNumber(params ? params.corporateNumber : null);
                this.accountingOfficeTelephoneNumber(params ? params.accountingOfficeTelephoneNumber : null);
                this.accountingOfficeName(params ? params.accountingOfficeName : null);
                this.salaryPaymentMethodAndDueDate1(params ? params.salaryPaymentMethodAndDueDate1 : null);
                this.salaryPaymentMethodAndDueDate2(params ? params.salaryPaymentMethodAndDueDate2 : null);
                this.salaryPaymentMethodAndDueDate3(params ? params.salaryPaymentMethodAndDueDate3 : null);
                this.accountManagerName(params ? params.accountManagerName : null);
                this.businessLine1(params ? params.businessLine1 : null);
                this.businessLine2(params ? params.businessLine2 : null);
                this.businessLine3(params ? params.businessLine3 : null);
                this.taxOffice(params ? params.taxOffice : null);
                this.vibrantLocationFinancialInstitutions(params ? params.vibrantLocationFinancialInstitutions : null);
                this.nameBankTransferInstitution(params ? params.nameBankTransferInstitution : null);
                this.contactName(params ? params.contactName : null);
                this.contactClass(params ? params.contactClass : null);
                this.contactPhoneNumber(params ? params.contactPhoneNumber : null);
            }
        }


    }
}