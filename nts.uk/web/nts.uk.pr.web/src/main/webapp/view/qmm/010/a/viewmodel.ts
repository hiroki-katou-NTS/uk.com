module nts.uk.pr.view.qmm010.a {
    import option = nts.uk.ui.option;
    import LaborInsuranceOfficeDto = service.model.LaborInsuranceOfficeDto;
    import LaborInsuranceOfficeFindOutDto = service.model.LaborInsuranceOfficeFindOutDto;
    import TypeActionLaborInsuranceOffice = service.model.TypeActionLaborInsuranceOffice;
    import LaborInsuranceOfficeDeleteDto = service.model.LaborInsuranceOfficeDeleteDto;
    export module viewmodel {
        export class ScreenModel {
            //ojbect value binding
            laborInsuranceOfficeModel: KnockoutObservable<LaborInsuranceOfficeModel>;
            lstlaborInsuranceOfficeModel: KnockoutObservableArray<LaborInsuranceOfficeFindOutDto>
            columnsLstlaborInsuranceOffice: KnockoutObservableArray<any>;
            selectCodeLstlaborInsuranceOffice: KnockoutObservable<string>;
            textSearch: any;
            enableButton: KnockoutObservable<boolean>;
            isEmpty: KnockoutObservable<boolean>;
            //update add LaborInsuranceOffice
            typeAction: KnockoutObservable<number>;
            constructor() {
                var self = this;
                self.textSearch = {
                    valueSearch: ko.observable(""),
                    option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                        textmode: "text",
                        placeholder: "コード・名称で検索・・・",
                        width: "270",
                        textalign: "left"
                    }))
                }
                self.columnsLstlaborInsuranceOffice = ko.observableArray([
                    { headerText: 'コード', prop: 'code', width: 120 },
                    { headerText: '名称', prop: 'name', width: 120 }
                ]);
                self.enableButton = ko.observable(true);
                self.typeAction = ko.observable(TypeActionLaborInsuranceOffice.update);
                self.isEmpty = ko.observable(true);
            }
            private resetValueLaborInsurance() {
                var self = this;
                self.laborInsuranceOfficeModel().resetAllValue();
                self.typeAction(TypeActionLaborInsuranceOffice.add);
                self.selectCodeLstlaborInsuranceOffice('');
            }
            private readFromSocialTnsuranceOffice() {
                var self = this;
                self.enableButton(false);
                nts.uk.ui.windows.sub.modal("/view/qmm/010/b/index.xhtml", { height: 800, width: 500, title: "社会保険事業所から読み込み" }).onClosed(() => {
                    self.enableButton(true);
                    //OnClose => call
                });
            }
            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                self.findAllInsuranceOffice().done(data => {
                    dfd.resolve(self);
                });
                return dfd.promise();
            }
            //Connection service find All InsuranceOffice
            private findAllInsuranceOffice(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                service.findAllLaborInsuranceOffice().done(data => {
                    if (data != null && data.length > 0) {
                        self.lstlaborInsuranceOfficeModel = ko.observableArray<LaborInsuranceOfficeFindOutDto>(data);
                        self.selectCodeLstlaborInsuranceOffice = ko.observable(data[0].code);
                        self.selectCodeLstlaborInsuranceOffice.subscribe(function(selectCodeLstlaborInsuranceOffice: string) {
                            self.detailLaborInsuranceOffice(selectCodeLstlaborInsuranceOffice);
                        });
                        service.findLaborInsuranceOffice(data[0].code).done(data => {
                            self.laborInsuranceOfficeModel = ko.observable(new LaborInsuranceOfficeModel(data));
                            dfd.resolve(self);
                        });
                    } else {
                        self.newmodelEmptyData();
                        dfd.resolve(self);
                    }

                });
                return dfd.promise();
            }
            //Connection service find All InsuranceOffice
            private findInsuranceOffice(code: string): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                if (code != null && code != undefined && code != '') {
                    service.findLaborInsuranceOffice(code).done(data => {
                        self.laborInsuranceOfficeModel = ko.observable(new LaborInsuranceOfficeModel(data));
                        dfd.resolve(null);
                    });
                }
                return dfd.promise();
            }
            //show InsuranceOffice
            private showInsuranceOffice(code: string) {
                if (code != null && code != undefined && code != '') {
                    var self = this;
                    service.findLaborInsuranceOffice(code).done(data => {
                        self.laborInsuranceOfficeModel(new LaborInsuranceOfficeModel(data));
                    });
                }

            }
            private saveLaborInsuranceOffice() {
                var self = this;
                if (self.typeAction() == TypeActionLaborInsuranceOffice.add) {
                    service.addLaborInsuranceOffice(self.collectData()).done(data => {
                        self.reloadDataByAction(self.laborInsuranceOfficeModel().code());
                    });
                } else {
                    service.updateLaborInsuranceOffice(self.collectData()).done(data => {
                        self.reloadDataByAction(self.laborInsuranceOfficeModel().code());
                    });
                }
            }
            private showchangeLaborInsuranceOfficep(selectionCodeLstLstLaborInsuranceOffice: string) {
                var self = this;
                self.detailLaborInsuranceOffice(selectionCodeLstLstLaborInsuranceOffice);
            }
            private detailLaborInsuranceOffice(code: string) {
                if (code != null && code != undefined && code != '') {
                    var self = this;
                    service.findLaborInsuranceOffice(code).done(data => {
                        if (self.isEmpty()) {
                            self.selectCodeLstlaborInsuranceOffice.subscribe(function(selectionCodeLstLstLaborInsuranceOffice: string) {
                                self.showchangeLaborInsuranceOfficep(selectionCodeLstLstLaborInsuranceOffice);
                            });
                            self.isEmpty(false);
                        }
                        self.selectCodeLstlaborInsuranceOffice(code);
                        self.laborInsuranceOfficeModel().updateData(data);
                    });
                }
            }
            //reload action
            private reloadDataByAction(code: string) {
                var self = this;
                service.findAllLaborInsuranceOffice().done(data => {
                    if (self.lstlaborInsuranceOfficeModel == null || self.lstlaborInsuranceOfficeModel == undefined) {
                        self.lstlaborInsuranceOfficeModel = ko.observableArray<LaborInsuranceOfficeFindOutDto>(data);
                    } else {
                        self.lstlaborInsuranceOfficeModel(data);
                    }
                    if (code != null && code != undefined && code != '') {
                        self.detailLaborInsuranceOffice(code);
                    } else {
                        if (data != null && data.length > 0) {
                            self.detailLaborInsuranceOffice(data[0].code);
                        } else {
                            self.newmodelEmptyData();
                        }
                    }
                });
            }
            private newmodelEmptyData() {
                var self = this;
                if (self.lstlaborInsuranceOfficeModel == null || self.lstlaborInsuranceOfficeModel == undefined) {
                    self.lstlaborInsuranceOfficeModel = ko.observableArray<LaborInsuranceOfficeFindOutDto>([]);
                } else {
                    self.lstlaborInsuranceOfficeModel([]);
                }
                if (self.laborInsuranceOfficeModel == null || self.laborInsuranceOfficeModel == undefined) {
                    self.laborInsuranceOfficeModel = ko.observable(new LaborInsuranceOfficeModel(new LaborInsuranceOfficeDto()));
                } else {
                    self.resetValueLaborInsurance();
                }
                self.selectCodeLstlaborInsuranceOffice = ko.observable('');
                self.isEmpty(true);
            }

            private deleteLaborInsuranceOffice() {
                var self = this;
                var laborInsuranceOfficeDeleteDto: LaborInsuranceOfficeDeleteDto = new LaborInsuranceOfficeDeleteDto();
                laborInsuranceOfficeDeleteDto.code = self.laborInsuranceOfficeModel().code();
                laborInsuranceOfficeDeleteDto.version = 11;
                service.deleteLaborInsuranceOffice(laborInsuranceOfficeDeleteDto).done(data => {
                    self.reloadDataByAction('');
                });
            }
            //Convert Model => DTO
            public collectData(): LaborInsuranceOfficeDto {
                var self = this;
                var laborInsuranceOffice: LaborInsuranceOfficeDto;
                laborInsuranceOffice = new LaborInsuranceOfficeDto();
                laborInsuranceOffice.code = self.laborInsuranceOfficeModel().code();
                laborInsuranceOffice.name = self.laborInsuranceOfficeModel().name();
                laborInsuranceOffice.shortName = self.laborInsuranceOfficeModel().shortName();
                laborInsuranceOffice.picName = self.laborInsuranceOfficeModel().picName();
                laborInsuranceOffice.picPosition = self.laborInsuranceOfficeModel().picPosition();
                laborInsuranceOffice.potalCode = self.laborInsuranceOfficeModel().postalCode();
                laborInsuranceOffice.address1st = self.laborInsuranceOfficeModel().address1st();
                laborInsuranceOffice.address2nd = self.laborInsuranceOfficeModel().address2nd();
                laborInsuranceOffice.kanaAddress1st = self.laborInsuranceOfficeModel().kanaAddress1st();
                laborInsuranceOffice.kanaAddress2nd = self.laborInsuranceOfficeModel().kanaAddress2nd();
                laborInsuranceOffice.phoneNumber = self.laborInsuranceOfficeModel().phoneNumber();
                laborInsuranceOffice.citySign = self.laborInsuranceOfficeModel().citySign();
                laborInsuranceOffice.officeMark = self.laborInsuranceOfficeModel().officeMark();
                laborInsuranceOffice.officeNoA = self.laborInsuranceOfficeModel().officeNoA();
                laborInsuranceOffice.officeNoB = self.laborInsuranceOfficeModel().officeNoB();
                laborInsuranceOffice.officeNoC = self.laborInsuranceOfficeModel().officeNoC();
                laborInsuranceOffice.memo = self.laborInsuranceOfficeModel().multilineeditor().memo();
                return laborInsuranceOffice;
            }

        }

        export class LaborInsuranceOfficeModel {
            code: KnockoutObservable<string>;
            name: KnockoutObservable<string>;
            shortName: KnockoutObservable<string>;
            picName: KnockoutObservable<string>;
            picPosition: KnockoutObservable<string>;
            postalCode: KnockoutObservable<string>;
            address1st: KnockoutObservable<string>;
            kanaAddress1st: KnockoutObservable<string>;
            address2nd: KnockoutObservable<string>;
            kanaAddress2nd: KnockoutObservable<string>;
            phoneNumber: KnockoutObservable<string>;
            citySign: KnockoutObservable<string>;
            officeMark: KnockoutObservable<string>;
            officeNoA: KnockoutObservable<string>;
            officeNoB: KnockoutObservable<string>;
            officeNoC: KnockoutObservable<string>;
            textEditorOption: KnockoutObservable<any>;
            multilineeditor: KnockoutObservable<any>;
            constructor(officeInfo: LaborInsuranceOfficeDto) {
                this.code = ko.observable(officeInfo.code);
                this.name = ko.observable(officeInfo.name);
                this.shortName = ko.observable(officeInfo.shortName);
                this.picName = ko.observable(officeInfo.picName);
                this.picPosition = ko.observable(officeInfo.picPosition);
                this.postalCode = ko.observable(officeInfo.potalCode);
                this.address1st = ko.observable(officeInfo.address1st);
                this.kanaAddress1st = ko.observable(officeInfo.kanaAddress1st);
                this.address2nd = ko.observable(officeInfo.address2nd);
                this.kanaAddress2nd = ko.observable(officeInfo.kanaAddress2nd);
                this.phoneNumber = ko.observable(officeInfo.phoneNumber);
                this.citySign = ko.observable(officeInfo.citySign);
                this.officeMark = ko.observable(officeInfo.officeMark);
                this.officeNoA = ko.observable(officeInfo.officeNoA);
                this.officeNoB = ko.observable(officeInfo.officeNoB);
                this.officeNoC = ko.observable(officeInfo.officeNoC);
                this.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                this.multilineeditor = ko.observable({
                    memo: ko.observable(officeInfo.memo),
                    readonly: false,
                    constraint: 'ResidenceCode',
                    option: ko.mapping.fromJS(new nts.uk.ui.option.MultilineEditorOption({
                        resizeable: true,
                        placeholder: "Placeholder for text editor",
                        width: "",
                        textalign: "left"
                    })),
                });

            }
            //Reset value in view Model
            resetAllValue() {
                this.code('');
                this.name('');
                this.shortName('');
                this.picName('');
                this.picPosition('');
                this.postalCode('');
                this.address1st('');
                this.kanaAddress1st('');
                this.address2nd('');
                this.kanaAddress2nd('');
                this.phoneNumber('');
                this.citySign('');
                this.officeMark('');
                this.officeNoA('');
                this.officeNoB('');
                this.officeNoC('');
                this.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                this.multilineeditor({
                    memo: ko.observable(''),
                    readonly: false,
                    constraint: 'ResidenceCode',
                    option: ko.mapping.fromJS(new nts.uk.ui.option.MultilineEditorOption({
                        resizeable: true,
                        placeholder: "Placeholder for text editor",
                        width: "",
                        textalign: "left"
                    })),
                });
            }
            updateData(officeInfo: LaborInsuranceOfficeDto) {
                this.code(officeInfo.code);
                this.name(officeInfo.name);
                this.shortName(officeInfo.shortName);
                this.picName(officeInfo.picName);
                this.picPosition(officeInfo.picPosition);
                this.postalCode(officeInfo.potalCode);
                this.address1st(officeInfo.address1st);
                this.kanaAddress1st(officeInfo.kanaAddress1st);
                this.address2nd(officeInfo.address2nd);
                this.kanaAddress2nd(officeInfo.kanaAddress2nd);
                this.phoneNumber(officeInfo.phoneNumber);
                this.citySign(officeInfo.citySign);
                this.officeMark(officeInfo.officeMark);
                this.officeNoA(officeInfo.officeNoA);
                this.officeNoB(officeInfo.officeNoB);
                this.officeNoC(officeInfo.officeNoC);
                this.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                this.multilineeditor({
                    memo: ko.observable(officeInfo.memo),
                    readonly: false,
                    constraint: 'ResidenceCode',
                    option: ko.mapping.fromJS(new nts.uk.ui.option.MultilineEditorOption({
                        resizeable: true,
                        placeholder: "Placeholder for text editor",
                        width: "",
                        textalign: "left"
                    })),
                });
            }
        }

    }
}
