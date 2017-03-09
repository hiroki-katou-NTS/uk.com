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
            enableButton: KnockoutObservable<boolean>;
            isEmpty: KnockoutObservable<boolean>;
            isEnableDelete: KnockoutObservable<boolean>;
            //update add LaborInsuranceOffice
            typeAction: KnockoutObservable<number>;

            constructor() {
                var self = this;
                self.columnsLstlaborInsuranceOffice = ko.observableArray([
                    { headerText: 'コード', prop: 'code', width: 120 },
                    { headerText: '名称', prop: 'name', width: 120 }
                ]);
                self.enableButton = ko.observable(true);
                self.typeAction = ko.observable(TypeActionLaborInsuranceOffice.update);
                self.isEmpty = ko.observable(true);
                self.laborInsuranceOfficeModel = ko.observable(new LaborInsuranceOfficeModel());
                self.selectCodeLstlaborInsuranceOffice = ko.observable('');
                self.isEnableDelete = ko.observable(true);
            }

            //function reset value viewmodel
            private resetValueLaborInsurance() {
                var self = this;
                self.laborInsuranceOfficeModel().resetAllValue();
                //set type action (ismode) add
                self.typeAction(TypeActionLaborInsuranceOffice.add);
                //reset value model
                self.selectCodeLstlaborInsuranceOffice('');
                self.laborInsuranceOfficeModel().setReadOnly(false);
                if (!self.isEmpty()) self.clearErrorSave();
                self.isEnableDelete(false);
            }

            //function clear message error
            private clearErrorSave() {
                var self = this;
                $('.save-error').ntsError('clear');
                $('#btn_save').ntsError('clear');
            }

            //function read all SocialTnsuranceOffice
            private readFromSocialTnsuranceOffice() {
                var self = this;
                self.enableButton(false);
                //call service find all SocialTnsuranceOffice
                service.findAllSocialInsuranceOffice().done(data => {
                    if (data != null && data.length > 0) {
                        //set data fw /b
                        nts.uk.ui.windows.setShared("dataInsuranceOffice", data);
                        //open dialog /b/index.xhtml
                        nts.uk.ui.windows.sub.modal("/view/qmm/010/b/index.xhtml", { height: 700, width: 450, title: "社会保険事業所から読み込み" }).onClosed(() => {
                            self.enableButton(true);
                            self.reloadDataByAction('');
                        });
                    } else {
                        //show message
                        alert("ER010");
                        self.enableButton(true);
                    }
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
                        //data not null length > 0
                        //reset List Labor Insurance Office
                        self.lstlaborInsuranceOfficeModel = ko.observableArray<LaborInsuranceOfficeFindOutDto>(data);
                        self.selectCodeLstlaborInsuranceOffice(data[0].code);
                        self.selectCodeLstlaborInsuranceOffice.subscribe(function(selectCodeLstlaborInsuranceOffice: string) {
                            self.showchangeLaborInsuranceOfficep(selectCodeLstlaborInsuranceOffice);
                        });
                        self.detailLaborInsuranceOffice(data[0].code).done(data => {
                            dfd.resolve(self);
                        });
                        self.isEnableDelete(true);
                    } else {
                        //new reset data value
                        self.newmodelEmptyData();
                        dfd.resolve(self);
                    }
                });
                return dfd.promise();
            }

            //Function show message error message
            private showMessageSave(message: string) {
                $('#btn_save').ntsError('set', message);
            }

            //Function action button save Onclick
            private saveLaborInsuranceOffice() {
                var self = this;
                //get ismode
                if (self.typeAction() == TypeActionLaborInsuranceOffice.add) {
                    //is mode is add
                    //call service add labor insurance office
                    service.addLaborInsuranceOffice(self.collectData()).done(data => {
                        //reload labor insurance office
                        self.reloadDataByAction(self.laborInsuranceOfficeModel().code());
                        //clear error
                        self.clearErrorSave();
                    }).fail(function(res) {
                        //show error message error
                        self.showMessageSave(res.message);
                    })
                } else {
                    //is mode is update
                    //call service update labor insurance office
                    service.updateLaborInsuranceOffice(self.collectData()).done(data => {
                        self.reloadDataByAction(self.laborInsuranceOfficeModel().code());
                    });
                }
            }

            //Function show view by change selection
            private showchangeLaborInsuranceOfficep(selectionCodeLstLstLaborInsuranceOffice: string) {
                var self = this;
                if (selectionCodeLstLstLaborInsuranceOffice != null
                    && selectionCodeLstLstLaborInsuranceOffice != undefined
                    && selectionCodeLstLstLaborInsuranceOffice != '') {
                    self.typeAction(TypeActionLaborInsuranceOffice.update);
                    self.detailLaborInsuranceOffice(selectionCodeLstLstLaborInsuranceOffice);
                }
            }

            //Function detail
            private detailLaborInsuranceOffice(code: string): JQueryPromise<any> {
                var dfd = $.Deferred<any>();
                if (code != null && code != undefined && code != '') {
                    var self = this;
                    //call service find labor insurance office
                    service.findLaborInsuranceOffice(code).done(data => {
                        if (self.isEmpty()) {
                            self.selectCodeLstlaborInsuranceOffice.subscribe(function(selectionCodeLstLstLaborInsuranceOffice: string) {
                                self.showchangeLaborInsuranceOfficep(selectionCodeLstLstLaborInsuranceOffice);
                            });
                            self.isEmpty(false);
                        }
                        //set data labor insurance office
                        self.selectCodeLstlaborInsuranceOffice(code);
                        self.laborInsuranceOfficeModel().updateData(data);
                        self.laborInsuranceOfficeModel().setReadOnly(true);
                        self.isEnableDelete(true);
                    });
                    dfd.resolve(null);
                }
                return dfd.promise();
            }

            //reload action
            private reloadDataByAction(code: string) {
                var self = this;
                //call service findAll
                service.findAllLaborInsuranceOffice().done(data => {
                    //reset list data
                    if (self.lstlaborInsuranceOfficeModel == null || self.lstlaborInsuranceOfficeModel == undefined) {
                        self.lstlaborInsuranceOfficeModel = ko.observableArray<LaborInsuranceOfficeFindOutDto>(data);
                    } else {
                        self.lstlaborInsuranceOfficeModel(data);
                    }
                    //set data view
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

            //Function empty data respone
            private newmodelEmptyData() {
                var self = this;
                //reset list data
                if (self.lstlaborInsuranceOfficeModel == null || self.lstlaborInsuranceOfficeModel == undefined) {
                    self.lstlaborInsuranceOfficeModel = ko.observableArray<LaborInsuranceOfficeFindOutDto>([]);
                } else {
                    self.lstlaborInsuranceOfficeModel([]);
                }
                //reset value
                self.resetValueLaborInsurance();
                self.selectCodeLstlaborInsuranceOffice('');
                self.isEmpty(true);
            }

            private deleteLaborInsuranceOffice() {
                var self = this;
                var laborInsuranceOfficeDeleteDto: LaborInsuranceOfficeDeleteDto = new LaborInsuranceOfficeDeleteDto();
                laborInsuranceOfficeDeleteDto.code = self.laborInsuranceOfficeModel().code();
                laborInsuranceOfficeDeleteDto.version = 11;
                if (self.selectCodeLstlaborInsuranceOffice != null && self.selectCodeLstlaborInsuranceOffice() != '') {
                    nts.uk.ui.dialog.confirm("Do you delete Item?").ifYes(function() {
                        service.deleteLaborInsuranceOffice(laborInsuranceOfficeDeleteDto).done(data => {
                            self.reloadDataByAction('');
                        });
                    }).ifNo(function() {
                        self.reloadDataByAction(self.selectCodeLstlaborInsuranceOffice());
                    })
                }
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
            isReadOnly: KnockoutObservable<boolean>;
            isEnable: KnockoutObservable<boolean>;

            constructor() {
                this.code = ko.observable('');
                this.name = ko.observable('');
                this.shortName = ko.observable('');
                this.picName = ko.observable('');
                this.picPosition = ko.observable('');
                this.postalCode = ko.observable('');
                this.address1st = ko.observable('');
                this.kanaAddress1st = ko.observable('');
                this.address2nd = ko.observable('');
                this.kanaAddress2nd = ko.observable('');
                this.phoneNumber = ko.observable('');
                this.citySign = ko.observable('');
                this.officeMark = ko.observable('');
                this.officeNoA = ko.observable('');
                this.officeNoB = ko.observable('');
                this.officeNoC = ko.observable('');
                this.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                this.multilineeditor = ko.observable({
                    memo: ko.observable(''),
                    readonly: false,
                    constraint: 'ResidenceCode',
                    option: ko.mapping.fromJS(new nts.uk.ui.option.MultilineEditorOption({
                        resizeable: true,
                        placeholder: "",
                        width: "",
                        textalign: "left"
                    })),
                });
                this.isReadOnly = ko.observable(true);
                this.isEnable = ko.observable(true);
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
                        placeholder: "",
                        width: "",
                        textalign: "left"
                    })),
                });
                this.isReadOnly(false);
                this.isEnable(true);
            }

            updateData(officeInfo: LaborInsuranceOfficeDto) {
                if (officeInfo != null) {
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
                            placeholder: "",
                            width: "",
                            textalign: "left"
                        })),
                    });
                }
            }

            setReadOnly(readonly: boolean) {
                this.isReadOnly(readonly);
                this.isEnable(!readonly);
            }
        }
    }
}
