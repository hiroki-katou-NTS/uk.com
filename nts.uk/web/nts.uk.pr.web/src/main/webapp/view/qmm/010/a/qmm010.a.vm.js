var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm010;
                (function (qmm010) {
                    var a;
                    (function (a) {
                        var option = nts.uk.ui.option;
                        var LaborInsuranceOfficeDto = a.service.model.LaborInsuranceOfficeDto;
                        var TypeActionLaborInsuranceOffice = a.service.model.TypeActionLaborInsuranceOffice;
                        var LaborInsuranceOfficeDeleteDto = a.service.model.LaborInsuranceOfficeDeleteDto;
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
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
                                    self.messageList = ko.observableArray([
                                        { messageId: "ER001", message: "＊が入力されていません。" },
                                        { messageId: "ER005", message: "入力した＊は既に存在しています。\r\n ＊を確認してください。" }
                                    ]);
                                }
                                //function reset value viewmodel
                                ScreenModel.prototype.resetValueLaborInsurance = function () {
                                    var self = this;
                                    self.laborInsuranceOfficeModel().resetAllValue();
                                    //set type action (ismode) add
                                    self.typeAction(TypeActionLaborInsuranceOffice.add);
                                    //reset value model
                                    self.selectCodeLstlaborInsuranceOffice('');
                                    self.laborInsuranceOfficeModel().setReadOnly(false);
                                    if (!self.isEmpty())
                                        self.clearErrorSave();
                                    self.isEnableDelete(false);
                                };
                                //function clear message error
                                ScreenModel.prototype.clearErrorSave = function () {
                                    $('.save-error').ntsError('clear');
                                    $('#btn_save').ntsError('clear');
                                };
                                //function read all SocialTnsuranceOffice
                                ScreenModel.prototype.readFromSocialTnsuranceOffice = function () {
                                    var self = this;
                                    self.enableButton(false);
                                    //call service find all SocialTnsuranceOffice
                                    a.service.findAllSocialInsuranceOffice().done(function (data) {
                                        if (data != null && data.length > 0) {
                                            //set data fw /b
                                            nts.uk.ui.windows.setShared("dataInsuranceOffice", data);
                                            //open dialog /b/index.xhtml
                                            nts.uk.ui.windows.sub.modal("/view/qmm/010/b/index.xhtml", { height: 700, width: 450, title: "社会保険事業所から読み込み" }).onClosed(function () {
                                                self.enableButton(true);
                                                self.reloadDataByAction('');
                                            });
                                        }
                                        else {
                                            //show message
                                            alert("ER010");
                                            self.enableButton(true);
                                        }
                                    });
                                };
                                ScreenModel.prototype.startPage = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    self.findAllInsuranceOffice().done(function (data) {
                                        dfd.resolve(data);
                                    });
                                    return dfd.promise();
                                };
                                //Connection service find All InsuranceOffice
                                ScreenModel.prototype.findAllInsuranceOffice = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    a.service.findAllLaborInsuranceOffice().done(function (data) {
                                        if (data != null && data.length > 0) {
                                            //data not null length > 0
                                            //reset List Labor Insurance Office
                                            self.lstlaborInsuranceOfficeModel = ko.observableArray(data);
                                            self.selectCodeLstlaborInsuranceOffice(data[0].code);
                                            self.selectCodeLstlaborInsuranceOffice.subscribe(function (selectCodeLstlaborInsuranceOffice) {
                                                self.showchangeLaborInsuranceOffice(selectCodeLstlaborInsuranceOffice);
                                            });
                                            self.detailLaborInsuranceOffice(data[0].code).done(function () {
                                                dfd.resolve(self);
                                            });
                                            self.isEnableDelete(true);
                                        }
                                        else {
                                            //new reset data value
                                            self.newmodelEmptyData();
                                            dfd.resolve(self);
                                        }
                                    });
                                    return dfd.promise();
                                };
                                //Function show message error message
                                ScreenModel.prototype.showMessageSave = function (messageId) {
                                    var self = this;
                                    if (self.messageList()[0].messageId === messageId) {
                                        //001
                                        var message = self.messageList()[0].message;
                                        if (!self.laborInsuranceOfficeModel().code()) {
                                            $('#inp_code').ntsError('set', message);
                                        }
                                        if (!self.laborInsuranceOfficeModel().name()) {
                                            $('#inp_name').ntsError('set', message);
                                        }
                                        if (!self.laborInsuranceOfficeModel().picPosition()) {
                                            $('#inp_picPosition').ntsError('set', message);
                                        }
                                    }
                                    if (self.messageList()[1].messageId === messageId) {
                                        var message = self.messageList()[1].message;
                                        $('#inp_code').ntsError('set', message);
                                    }
                                };
                                //Function action button save Onclick
                                ScreenModel.prototype.saveLaborInsuranceOffice = function () {
                                    var self = this;
                                    //get ismode
                                    if (self.typeAction() == TypeActionLaborInsuranceOffice.add) {
                                        //is mode is add
                                        //call service add labor insurance office
                                        a.service.addLaborInsuranceOffice(self.collectData()).done(function () {
                                            //reload labor insurance office
                                            self.reloadDataByAction(self.laborInsuranceOfficeModel().code());
                                            //clear error
                                            self.clearErrorSave();
                                        }).fail(function (res) {
                                            //show error message error
                                            self.showMessageSave(res.messageId);
                                        });
                                    }
                                    else {
                                        //is mode is update
                                        //call service update labor insurance office
                                        a.service.updateLaborInsuranceOffice(self.collectData()).done(function () {
                                            self.reloadDataByAction(self.laborInsuranceOfficeModel().code());
                                        });
                                    }
                                };
                                //Function show view by change selection
                                ScreenModel.prototype.showchangeLaborInsuranceOffice = function (selectionCodeLstLstLaborInsuranceOffice) {
                                    var self = this;
                                    if (selectionCodeLstLstLaborInsuranceOffice
                                        && selectionCodeLstLstLaborInsuranceOffice != '') {
                                        self.typeAction(TypeActionLaborInsuranceOffice.update);
                                        self.detailLaborInsuranceOffice(selectionCodeLstLstLaborInsuranceOffice);
                                    }
                                };
                                //Function detail
                                ScreenModel.prototype.detailLaborInsuranceOffice = function (code) {
                                    var dfd = $.Deferred();
                                    if (code && code != '') {
                                        var self = this;
                                        //call service find labor insurance office
                                        a.service.findLaborInsuranceOffice(code).done(function (data) {
                                            if (self.isEmpty()) {
                                                self.selectCodeLstlaborInsuranceOffice.subscribe(function (selectionCodeLstLstLaborInsuranceOffice) {
                                                    self.showchangeLaborInsuranceOffice(selectionCodeLstLstLaborInsuranceOffice);
                                                });
                                                self.isEmpty(false);
                                            }
                                            //set data labor insurance office
                                            self.selectCodeLstlaborInsuranceOffice(code);
                                            self.laborInsuranceOfficeModel().updateData(data);
                                            self.laborInsuranceOfficeModel().setReadOnly(true);
                                            self.isEnableDelete(true);
                                            self.clearErrorSave();
                                        });
                                        dfd.resolve();
                                    }
                                    return dfd.promise();
                                };
                                //reload action
                                ScreenModel.prototype.reloadDataByAction = function (code) {
                                    var self = this;
                                    //call service findAll
                                    a.service.findAllLaborInsuranceOffice().done(function (data) {
                                        //reset list data
                                        if (self.lstlaborInsuranceOfficeModel == null || self.lstlaborInsuranceOfficeModel == undefined) {
                                            self.lstlaborInsuranceOfficeModel = ko.observableArray(data);
                                        }
                                        else {
                                            self.lstlaborInsuranceOfficeModel(data);
                                        }
                                        //set data view
                                        if (code != null && code != undefined && code != '') {
                                            self.detailLaborInsuranceOffice(code);
                                        }
                                        else {
                                            if (data != null && data.length > 0) {
                                                self.detailLaborInsuranceOffice(data[0].code);
                                            }
                                            else {
                                                self.newmodelEmptyData();
                                            }
                                        }
                                    });
                                };
                                //Function empty data respone
                                ScreenModel.prototype.newmodelEmptyData = function () {
                                    var self = this;
                                    //reset list data
                                    if (self.lstlaborInsuranceOfficeModel == null || self.lstlaborInsuranceOfficeModel == undefined) {
                                        self.lstlaborInsuranceOfficeModel = ko.observableArray([]);
                                    }
                                    else {
                                        self.lstlaborInsuranceOfficeModel([]);
                                    }
                                    //reset value
                                    self.resetValueLaborInsurance();
                                    self.selectCodeLstlaborInsuranceOffice('');
                                    self.isEmpty(true);
                                };
                                ScreenModel.prototype.deleteLaborInsuranceOffice = function () {
                                    var self = this;
                                    var laborInsuranceOfficeDeleteDto = new LaborInsuranceOfficeDeleteDto();
                                    laborInsuranceOfficeDeleteDto.code = self.laborInsuranceOfficeModel().code();
                                    laborInsuranceOfficeDeleteDto.version = 11;
                                    if (self.selectCodeLstlaborInsuranceOffice != null && self.selectCodeLstlaborInsuranceOffice() != '') {
                                        nts.uk.ui.dialog.confirm("Do you delete Item?").ifYes(function () {
                                            a.service.deleteLaborInsuranceOffice(laborInsuranceOfficeDeleteDto).done(function () {
                                                self.reloadDataByAction('');
                                            });
                                        }).ifNo(function () {
                                            self.reloadDataByAction(self.selectCodeLstlaborInsuranceOffice());
                                        });
                                    }
                                };
                                //Convert Model => DTO
                                ScreenModel.prototype.collectData = function () {
                                    var self = this;
                                    var laborInsuranceOffice;
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
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var LaborInsuranceOfficeModel = (function () {
                                function LaborInsuranceOfficeModel() {
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
                                LaborInsuranceOfficeModel.prototype.resetAllValue = function () {
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
                                };
                                LaborInsuranceOfficeModel.prototype.updateData = function (officeInfo) {
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
                                };
                                LaborInsuranceOfficeModel.prototype.setReadOnly = function (readonly) {
                                    this.isReadOnly(readonly);
                                    this.isEnable(!readonly);
                                };
                                return LaborInsuranceOfficeModel;
                            }());
                            viewmodel.LaborInsuranceOfficeModel = LaborInsuranceOfficeModel;
                        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
                    })(a = qmm010.a || (qmm010.a = {}));
                })(qmm010 = view.qmm010 || (view.qmm010 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
