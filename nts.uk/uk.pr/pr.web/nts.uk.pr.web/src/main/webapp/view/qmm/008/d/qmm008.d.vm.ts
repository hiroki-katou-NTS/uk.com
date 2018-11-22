module nts.uk.pr.view.qmm008.d {
    export module viewmodel {
        import text = nts.uk.resource.getText;
        import confirm = nts.uk.ui.dialog.confirm;
        import block = nts.uk.ui.block;
        export class ScreenModel {
            items: KnockoutObservableArray<SocialOfficeOverView>;
            columns2: KnockoutObservableArray<NtsGridListColumn>;
            currentCode: KnockoutObservable<any> = ko.observable('');
            count: number = 100;
            switchOptions: KnockoutObservableArray<any>;

            tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            selectedTab: KnockoutObservable<string>;

            simpleValue: KnockoutObservable<string>;

            detail: KnockoutObservable<SocialOfficeDetail> = ko.observable(null);

            // combobox
            itemList: KnockoutObservableArray<ItemModelComBoBox>;
            selectedNoD38: KnockoutObservable<any> = ko.observable('');
            selectedNoD35: KnockoutObservable<any> = ko.observable('');
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
                self.itemList = ko.observableArray([]);
                nts.uk.pr.view.qmm008.d.service.defaultData().done(function(response) {
                    block.invisible();

                    for (let i = 0; i < response.listCodeName.length; i++) {
                        self.items.push(new SocialOfficeOverView(response.listCodeName[i].code, response.listCodeName[i].name));
                        self.isEnableBtnDelete(true);
                        self.isEnableBtnCreate(true);
                    }
                    self.detail(new SocialOfficeDetail(response.sociaInsuOfficeDetail));
                    self.currentCode(response.sociaInsuOfficeDetail.code);
                    for (let i = 0; i < response.sociaInsuPreInfos.length; i++) {
                        self.itemList.push(new ItemModelComBoBox(response.sociaInsuPreInfos[i].no, response.sociaInsuPreInfos[i].prefectureName));
                        if (response.sociaInsuOfficeDetail.healthInsurancePrefectureNo == response.sociaInsuPreInfos[i].no) {
                            self.selectedNoD35(response.sociaInsuPreInfos[i].no);
                        }
                        if (response.sociaInsuOfficeDetail.welfarePensionPrefectureNo == response.sociaInsuPreInfos[i].no) {
                            self.selectedNoD38(response.sociaInsuPreInfos[i].no);
                        }
                    }
                    if (response.listCodeName.length > 0) {
                        _.defer(function() {
                            $("#D4_3").focus();
                        });
                    } else {
                        _.defer(function() {
                            $("#D4_2").focus();
                        });
                    }
                    block.clear();
                });

                this.columns2 = ko.observableArray([
                    { headerText: text('QMM008_110'), key: 'code', width: 120, formatter: _.escape},
                    { headerText: text('QMM008_111'), key: 'name', width: 200, formatter: _.escape }
                ]);

                self.tabs = ko.observableArray([
                    { id: 'tab-1', title: text('QMM008_112'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: text('QMM008_113'), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) }
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
                        nts.uk.pr.view.qmm008.d.service.findByCode(codeId).done(function(response) {
                            self.isEnableBtnDelete(true);
                            self.isEnableBtnCreate(true);
                            self.detail(new SocialOfficeDetail(response));
                            let selectedNo35 = _.find(self.itemList(), { no: response.healthInsurancePrefectureNo });
                            if (response.healthInsurancePrefectureNo)
                                self.selectedNoD35(selectedNo35.no);
                            let selectedNo38 = _.find(self.itemList(), { no: response.welfarePensionPrefectureNo });
                            if (response.welfarePensionPrefectureNo)
                                self.selectedNoD38(selectedNo38.no);
                            self.isEnableCode(false);
                            _.defer(function() {
                                $("#D4_3").focus();
                            });
                            nts.uk.ui.errors.clearAll();
                        });
                        setTimeout(function () {
                            $("tr[data-id="+ self.currentCode()+"] ").focus();
                            _.defer(function() {
                                $("#D4_3").focus();
                            });
                        }, 500);
                        setTimeout(function () {
                            _.defer(function() {
                                $("#D4_3").focus();
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
                self.detail().healthInsurancePrefectureNo(self.selectedNoD35);
                self.detail().welfarePensionPrefectureNo(self.selectedNoD38);
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

                    nts.uk.pr.view.qmm008.d.service.create(ko.toJS(self.detail)).done(function(response) {

                        if (response.msg == 'Msg_3') {
                            nts.uk.ui.dialog.error({ messageId: "Msg_3" }).then(function() {
                                self.isEnableBtnDelete(false);
                            });
                        } else {
                            self.isEnableBtnCreate(true);
                            self.items([]);
                            for (let i = 0; i < response.dataOffice.length; i++) {
                                self.items.push(new SocialOfficeOverView(response.dataOffice[i].code, response.dataOffice[i].name));
                            }
                            nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {

                                self.currentCode(response.code);
                                self.isEnableCode(false);

                            });
                            _.defer(function() {
                                $("#D4_3").focus();
                            });
                        }
                        block.clear();
                    });
                } else {
                    nts.uk.pr.view.qmm008.d.service.update(ko.toJS(self.detail)).done(function(response) {
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                            /**
                             *  selected
                             */
                            for (let i = 0; i < self.items().length; i++) {
                                if (self.items()[i].code == response[0]) {
                                    self.items()[i].name = response[1];
                                    self.items.valueHasMutated()
                                }
                            }
                            self.isEnableCode(false);
                            $("#D4_3").focus();

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
                self.detail(new SocialOfficeDetail());
                self.isEnableCode(true);
                self.currentCode(null);
                if (self.itemList().length > 0) {
                    self.selectedNoD35(self.itemList()[0].code);
                    self.selectedNoD38(self.itemList()[0].code);
                }
                self.isEnableBtnDelete(false);
                self.isEnableBtnCreate(false);
                $("#D4_2").focus();
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
            private deleteOffice(): void {
                block.invisible();
                confirm({ messageId: "Msg_18" }).ifYes(() => {
                    let self = this;
                    let command = { code: self.currentCode() };
                    nts.uk.pr.view.qmm008.d.service.deleteOffice(command).done(function(response) {
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
                                $("tr[data-id=" + self.currentCode() + "] ").focus();
                                _.defer(function () {
                                    if (self.items().length > 0) {
                                        $("#D4_3").focus();
                                    }
                                });
                            }, 500);
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
                    $('#D4_23').keydown(function (e) {
                        var code = e.keyCode || e.which;
                        if (code === 9 && !e.shiftKey) {
                            e.preventDefault();
                            self.selectedTab("tab-2");
                            $('#D5_4').focus();
                        }
                    });
                    $('#D5_4').keydown(function (e) {
                        if (e.shiftKey && e.key === 'Tab') {
                            e.preventDefault();
                            self.selectedTab("tab-1");
                            $('#D4_23').focus();
                        }
                    });
                }, 2000);
            }
        }

        class SocialOfficeOverView {
            code: KnockoutObservable<string> = ko.observable(null);
            name: KnockoutObservable<string> = ko.observable(null);
            constructor(code: KnockoutObservable<string>, name: KnockoutObservable<string>) {
                this.code = code;
                this.name = name;
            }
        }

        class ItemModelComBoBox {
            no: KnockoutObservable<string> = ko.observable(null);
            prefectureName: KnockoutObservable<string> = ko.observable(null);

            constructor(no: KnockoutObservable<string>, prefectureName: KnockoutObservable<string>) {
                this.no = no;
                this.prefectureName = prefectureName;
            }
        }

        interface ISocialOfficeDetail {
            code: KnockoutObservable<string>, name: KnockoutObservable<string>, shortName: KnockoutObservable<string>, representativeName: KnockoutObservable<string>,
            representativePosition: KnockoutObservable<string>, memo: KnockoutObservable<string>, postalCode: KnockoutObservable<string>, address1: KnockoutObservable<string>,
            addressKana1: KnockoutObservable<string>, address2: KnockoutObservable<string>, addressKana2: KnockoutObservable<string>, phoneNumber: KnockoutObservable<string>,
            welfarePensionFundNumber: KnockoutObservable<string>, welfarePensionOfficeNumber: KnockoutObservable<string>, healthInsuranceOfficeNumber: KnockoutObservable<string>,
            healthInsuranceUnionOfficeNumber: KnockoutObservable<string>, healthInsuranceOfficeNumber1: KnockoutObservable<string>, healthInsuranceOfficeNumber2: KnockoutObservable<string>,
            welfarePensionOfficeNumber1: KnockoutObservable<string>, welfarePensionOfficeNumber2: KnockoutObservable<string>, healthInsuranceCityCode: KnockoutObservable<string>,
            healthInsuranceOfficeCode: KnockoutObservable<string>, welfarePensionCityCode: KnockoutObservable<string>, welfarePensionOfficeCode: KnockoutObservable<string>, healthInsurancePrefectureNo: KnockoutObservable<string>,
            welfarePensionPrefectureNo: KnockoutObservable<string>
        }

        class SocialOfficeDetail {
            code: KnockoutObservable<string> = ko.observable(null);
            name: KnockoutObservable<string> = ko.observable(null);
            shortName: KnockoutObservable<string> = ko.observable(null);
            representativeName: KnockoutObservable<string> = ko.observable(null);
            representativePosition: KnockoutObservable<string> = ko.observable(null);
            memo: KnockoutObservable<string> = ko.observable(null);
            postalCode: KnockoutObservable<string> = ko.observable(null);
            address1: KnockoutObservable<string> = ko.observable(null);
            addressKana1: KnockoutObservable<string> = ko.observable(null);
            address2: KnockoutObservable<string> = ko.observable(null);
            addressKana2: KnockoutObservable<string> = ko.observable(null);
            phoneNumber: KnockoutObservable<string> = ko.observable(null);
            welfarePensionFundNumber: KnockoutObservable<string> = ko.observable(null);
            welfarePensionOfficeNumber: KnockoutObservable<string> = ko.observable(null);
            healthInsuranceOfficeNumber: KnockoutObservable<string> = ko.observable(null);
            healthInsuranceUnionOfficeNumber: KnockoutObservable<string> = ko.observable(null);
            healthInsuranceOfficeNumber1: KnockoutObservable<string> = ko.observable(null);
            healthInsuranceOfficeNumber2: KnockoutObservable<string> = ko.observable(null);
            welfarePensionOfficeNumber1: KnockoutObservable<string> = ko.observable(null);
            welfarePensionOfficeNumber2: KnockoutObservable<string> = ko.observable(null);
            healthInsuranceCityCode: KnockoutObservable<string> = ko.observable(null);
            healthInsuranceOfficeCode: KnockoutObservable<string> = ko.observable(null);
            welfarePensionCityCode: KnockoutObservable<string> = ko.observable(null);
            welfarePensionOfficeCode: KnockoutObservable<string> = ko.observable(null);
            healthInsurancePrefectureNo: KnockoutObservable<string> = ko.observable(null);
            welfarePensionPrefectureNo: KnockoutObservable<string> = ko.observable(null);
            constructor(parameter?: ISocialOfficeDetail) {
                this.code(parameter ? parameter.code : '');
                this.name(parameter ? parameter.name : '');
                this.shortName(parameter ? parameter.shortName : null);
                this.representativeName(parameter ? parameter.representativeName : null);
                this.representativePosition(parameter ? parameter.representativePosition : '');
                this.memo(parameter ? parameter.memo : null);
                this.postalCode(parameter ? parameter.postalCode : null);
                this.address1(parameter ? parameter.address1 : null);
                this.addressKana1(parameter ? parameter.addressKana1 : null);
                this.address2(parameter ? parameter.address2 : null);
                this.addressKana2(parameter ? parameter.addressKana2 : null);
                this.phoneNumber(parameter ? parameter.phoneNumber : null);
                this.welfarePensionFundNumber(parameter ? parameter.welfarePensionFundNumber : null);
                this.welfarePensionOfficeNumber(parameter ? parameter.welfarePensionOfficeNumber : null);
                this.healthInsuranceOfficeNumber(parameter ? parameter.healthInsuranceOfficeNumber : null);
                this.healthInsuranceUnionOfficeNumber(parameter ? parameter.healthInsuranceUnionOfficeNumber : null);
                this.healthInsuranceOfficeNumber1(parameter ? parameter.healthInsuranceOfficeNumber1 : null);
                this.healthInsuranceOfficeNumber2(parameter ? parameter.healthInsuranceOfficeNumber2 : null);
                this.welfarePensionOfficeNumber1(parameter ? parameter.welfarePensionOfficeNumber1 : null);
                this.welfarePensionOfficeNumber2(parameter ? parameter.welfarePensionOfficeNumber2 : null);
                this.healthInsuranceCityCode(parameter ? parameter.healthInsuranceCityCode : null);
                this.healthInsuranceOfficeCode(parameter ? parameter.healthInsuranceOfficeCode : null);
                this.welfarePensionCityCode(parameter ? parameter.welfarePensionCityCode : null);
                this.welfarePensionOfficeCode(parameter ? parameter.welfarePensionOfficeCode : null);
                this.healthInsurancePrefectureNo(parameter ? parameter.healthInsurancePrefectureNo : null);
                this.welfarePensionPrefectureNo(parameter ? parameter.welfarePensionPrefectureNo : null);
            }

        }


    }
}