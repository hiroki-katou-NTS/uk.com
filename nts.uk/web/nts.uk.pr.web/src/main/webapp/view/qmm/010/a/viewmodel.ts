module nts.uk.pr.view.qmm010.a {
    import option = nts.uk.ui.option;
    import LaborInsuranceOfficeDto = service.model.LaborInsuranceOfficeDto;
    import LaborInsuranceOfficeFindOutDto = service.model.LaborInsuranceOfficeFindOutDto;
    import LaborInsuranceOfficeFindInDto = service.model.LaborInsuranceOfficeFindInDto;
    import TypeActionLaborInsuranceOffice = service.model.TypeActionLaborInsuranceOffice;
    export module viewmodel {
        export class ScreenModel {
            //ojbect value binding
            laborInsuranceOfficeModel: KnockoutObservable<LaborInsuranceOfficeModel>;
            lstlaborInsuranceOfficeModel: KnockoutObservableArray<LaborInsuranceOfficeFindOutDto>
            columnsLstlaborInsuranceOffice: KnockoutObservableArray<any>;
            selectCodeLstlaborInsuranceOffice: KnockoutObservable<string>;
            textSearch: any;
            enableButton: KnockoutObservable<boolean>;
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
            }
            private resetValueLaborInsurance() {
                var self = this;
                self.laborInsuranceOfficeModel().resetAllValue();
                self.typeAction(TypeActionLaborInsuranceOffice.add);
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
                    self.findInsuranceOffice(self.selectCodeLstlaborInsuranceOffice()).done(
                        data => { dfd.resolve(self); }
                    );
                });
                return dfd.promise();
            }
            //Connection service find All InsuranceOffice
            private findAllInsuranceOffice(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                service.findAllLaborInsuranceOffice("companyCode").done(data => {
                    self.lstlaborInsuranceOfficeModel = ko.observableArray<LaborInsuranceOfficeFindOutDto>(data);
                    self.selectCodeLstlaborInsuranceOffice = ko.observable(data[0].code);
                    self.selectCodeLstlaborInsuranceOffice.subscribe(function(selectCodeLstlaborInsuranceOffice: string) {
                        self.showInsuranceOffice(selectCodeLstlaborInsuranceOffice);
                    });
                    dfd.resolve(self);
                });
                return dfd.promise();
            }
            //Connection service find All InsuranceOffice
            private findInsuranceOffice(code: string): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                var laborInsuranceOfficeFindInDto: LaborInsuranceOfficeFindInDto;
                laborInsuranceOfficeFindInDto = new LaborInsuranceOfficeFindInDto();
                laborInsuranceOfficeFindInDto.code = code;
                laborInsuranceOfficeFindInDto.companyCode = "companyCode001";
                service.findLaborInsuranceOffice(laborInsuranceOfficeFindInDto).done(data => {
                    self.laborInsuranceOfficeModel = ko.observable(new LaborInsuranceOfficeModel(data));
                    dfd.resolve(null);
                });
                return dfd.promise();
            }
            //show InsuranceOffice
            private showInsuranceOffice(code: string) {
                var self = this;
                var laborInsuranceOfficeFindInDto: LaborInsuranceOfficeFindInDto;
                laborInsuranceOfficeFindInDto = new LaborInsuranceOfficeFindInDto();
                laborInsuranceOfficeFindInDto.code = code;
                laborInsuranceOfficeFindInDto.companyCode = "companyCode001";
                service.findLaborInsuranceOffice(laborInsuranceOfficeFindInDto).done(data => {
                    self.laborInsuranceOfficeModel(new LaborInsuranceOfficeModel(data));
                });
            }
            private addLaborInsuranceOffice() {
                var self = this;
                service.addLaborInsuranceOffice(self.collectData(), "000001");
            }
            private updateLaborInsuranceOffice() {
                var self = this;
                service.updateLaborInsuranceOffice(self.collectData(), "000001");
            }
            private saveLaborInsuranceOffice() {
                var self = this;
                if (self.typeAction() == TypeActionLaborInsuranceOffice.add) {
                    self.addLaborInsuranceOffice();
                } else {
                    self.updateLaborInsuranceOffice();
                }
            }
            private deleteLaborInsuranceOffice() {
                var self = this;
                service.deleteLaborInsuranceOffice(self.laborInsuranceOfficeModel().code(), "00001");
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
                laborInsuranceOffice.memo = self.laborInsuranceOfficeModel().memo();
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
            memo: KnockoutObservable<string>;
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
                this.memo = ko.observable(officeInfo.memo);
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
                this.memo('');
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
        }

    }
}
