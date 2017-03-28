var cmm001;
(function (cmm001) {
    var a;
    (function (a) {
        var ViewModel = (function () {
            function ViewModel() {
                this.isUpdate = ko.observable(null);
                var self = this;
                self.init();
                self.checked.subscribe(function (newValue) {
                    var $grid = $("#A_LST_001");
                    var currentColumns = $grid.igGrid("option", "columns");
                    var width = $grid.igGrid("option", "width");
                    if (newValue) {
                        $('#A_SEL_001').ntsError('clear');
                        currentColumns[2].hidden = false;
                        $grid.igGrid("option", "width", "400px");
                        self.sel001Data([]);
                        self.start(undefined);
                    }
                    else {
                        self.sel001Data([]);
                        currentColumns[2].hidden = true;
                        $grid.igGrid("option", "width", "400px");
                        a.service.getAllCompanys().done(function (data) {
                            if (data.length > 0) {
                                _.each(data, function (obj) {
                                    var companyModel;
                                    companyModel = ko.mapping.fromJS(obj);
                                    if (obj.displayAttribute === 0) {
                                        companyModel.displayAttribute('');
                                        self.sel001Data.push(ko.toJS(companyModel));
                                    }
                                });
                                var companyCheckExist = _.find(self.sel001Data(), function (obj) {
                                    var x = ko.toJS(obj.companyCode);
                                    var y = (ko.toJS(self.currentCompany().companyCode));
                                    return x === y;
                                });
                                if (self.sel001Data().length > 0) {
                                    if (!companyCheckExist) {
                                        self.currentCompany().companyCode(ko.toJS(self.sel001Data()[0].companyCode));
                                    }
                                }
                                else {
                                    self.resetData();
                                    self.isUpdate(true);
                                }
                            }
                        });
                    }
                    $grid.igGrid("option", "columns", currentColumns);
                });
            }
            ViewModel.prototype.init = function () {
                var self = this;
                self.tabs = ko.observableArray([
                    { id: 'tab-1', title: '会社基本情報', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: '会社所在地・連絡先', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-3', title: 'システム設定', content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) }
                ]);
                self.checked = ko.observable(true);
                self.selectedTab = ko.observable('tab-1');
                self.gridColumns = ko.observableArray([
                    { headerText: '会社コード', prop: 'companyCode', width: 80 },
                    { headerText: '名称', prop: 'companyName', width: 200 },
                    { headerText: '廃止', prop: 'displayAttribute', width: 50, hidden: false }
                ]);
                self.currentCompany = ko.observable(new CompanyModel({
                    companyCode: '',
                    address1: '',
                    companyName: '',
                    companyNameGlobal: '',
                    corporateMyNumber: '',
                    depWorkPlaceSet: 0,
                    displayAttribute: '',
                    termBeginMon: 0,
                    companyUseSet: null
                }));
                self.sel001Data = ko.observableArray([]);
            };
            ViewModel.prototype.start = function (currentCode) {
                var self = this;
                var items = [];
                a.service.getAllCompanys().done(function (data) {
                    if (data.length > 0) {
                        self.isUpdate(true);
                        _.each(data, function (obj) {
                            var companyModel;
                            companyModel = ko.mapping.fromJS(obj);
                            if (obj.displayAttribute === 1) {
                                companyModel.displayAttribute('<i style="margin-left: 15px" class="icon icon-close"></i>');
                            }
                            else {
                                companyModel.displayAttribute('');
                            }
                            self.sel001Data.push(ko.toJS(companyModel));
                        });
                        if (currentCode === undefined) {
                            self.currentCompany().setList(data, ko.toJS(self.sel001Data()[0].companyCode));
                        }
                        else {
                            self.currentCompany().setList(data, currentCode);
                        }
                    }
                    else {
                        self.isUpdate(false);
                    }
                });
            };
            ViewModel.prototype.resetData = function () {
                var self = this;
                self.currentCompany().companyCode(null);
                self.currentCompany().address1("");
                self.currentCompany().addressKana1("");
                self.currentCompany().address2("");
                self.currentCompany().addressKana2("");
                self.currentCompany().companyName("");
                self.currentCompany().companyNameGlobal("");
                self.currentCompany().companyNameAbb('');
                self.currentCompany().companyNameKana('');
                self.currentCompany().corporateMyNumber('');
                self.currentCompany().companyUseSet(new CompanyUseSet(0, 0, 0));
                self.currentCompany().depWorkPlaceSet(0);
                self.currentCompany().displayAttribute('');
                self.currentCompany().faxNo('');
                self.currentCompany().telephoneNo('');
                self.currentCompany().postal('');
                self.currentCompany().presidentName('');
                self.currentCompany().presidentJobTitle('');
                self.currentCompany().termBeginMon(1);
                self.currentCompany().selectedRuleCode('0');
                self.currentCompany().selectedRuleCode1('0');
                self.currentCompany().selectedRuleCode2('0');
                self.currentCompany().selectedRuleCode3('0');
                self.currentCompany().isDelete(true);
                self.currentCompany().editMode = true;
                self.currentCompany().isEnableCompanyCode(true);
                self.currentCompany().hasFocus(true);
                self.isUpdate(false);
            };
            ViewModel.prototype.clickRegister = function () {
                var self = this;
                var dfd = $.Deferred();
                var currentCompany;
                currentCompany = ko.toJS(self.currentCompany);
                if (nts.uk.text.isNullOrEmpty(ko.toJS(currentCompany.companyCode))) {
                    $('#A_INP_002').ntsError('set', nts.uk.text.format('{0}が入力されていません。', 'コード'));
                    return;
                }
                if (nts.uk.text.isNullOrEmpty(ko.toJS(currentCompany.companyName))) {
                    $('#A_INP_003').ntsError('set', nts.uk.text.format('{0}が入力されていません。', '名称'));
                    return;
                }
                if (currentCompany.isDelete) {
                    currentCompany.displayAttribute = ko.observable("1");
                }
                else {
                    currentCompany.displayAttribute = ko.observable("0");
                }
                var company = new a.service.model.CompanyDto("", "", "", "", "", "", "", "", "", 0, 0, "", "", "", "", "", 0, 0, 0, 0);
                company = self.convertCompanyDto(currentCompany);
                if (self.isUpdate()) {
                    cmm001.a.service.updateData(company).done(function () {
                        self.sel001Data([]);
                        self.start(company.companyCode);
                    });
                }
                else {
                    cmm001.a.service.addData(company).done(function () {
                        self.sel001Data([]);
                        self.start(company.companyCode);
                    });
                }
            };
            ViewModel.prototype.convertCompanyDto = function (company) {
                var companyDto = new a.service.model.CompanyDto("", "", "", "", "", "", "", "", "", 0, 0, "", "", "", "", "", 0, 0, 0, 0);
                companyDto.companyCode = ko.toJS(company.companyCode);
                companyDto.companyName = ko.toJS(company.companyName);
                companyDto.companyNameGlobal = ko.toJS(company.companyNameGlobal);
                companyDto.companyNameAbb = ko.toJS(company.companyNameAbb);
                companyDto.companyNameKana = ko.toJS(company.companyNameKana);
                companyDto.corporateMyNumber = ko.toJS(company.corporateMyNumber);
                companyDto.use_Jj_Set = Number(ko.toJS(company.selectedRuleCode));
                companyDto.use_Kt_Set = Number(ko.toJS(company.selectedRuleCode1));
                companyDto.use_Qy_Set = Number(ko.toJS(company.selectedRuleCode2));
                companyDto.depWorkPlaceSet = Number(ko.toJS(company.selectedRuleCode3));
                companyDto.displayAttribute = Number(ko.toJS(company.displayAttribute));
                companyDto.termBeginMon = Number(ko.toJS(company.termBeginMon));
                companyDto.address1 = ko.toJS(company.address1);
                companyDto.address2 = ko.toJS(company.address2);
                companyDto.addressKana1 = ko.toJS(company.addressKana1);
                companyDto.addressKana2 = ko.toJS(company.addressKana2);
                companyDto.telephoneNo = ko.toJS(company.telephoneNo);
                companyDto.faxNo = ko.toJS(company.faxNo);
                companyDto.postal = ko.toJS(company.postal);
                companyDto.presidentJobTitle = ko.toJS(company.presidentJobTitle);
                companyDto.presidentName = ko.toJS(company.presidentName);
                return companyDto;
            };
            return ViewModel;
        }());
        a.ViewModel = ViewModel;
        var CompanyModel = (function () {
            function CompanyModel(param) {
                this.isEnableCompanyCode = ko.observable(true);
                this.hasFocus = ko.observable(true);
                this.editMode = true; // mode reset or not reset
                var self = this;
                self.init(param);
                self.companyCode.subscribe(function (newValue) {
                    if (self.editMode) {
                        if (nts.uk.text.isNullOrEmpty(newValue)) {
                            return;
                        }
                        a.service.getCompanyDetail(newValue).done(function (company) {
                            if (company) {
                                if ($('.nts-editor').ntsError("hasError")) {
                                    $('.save-error').ntsError('clear');
                                }
                                self.companyName(company.companyName);
                                self.companyNameGlobal(company.companyNameGlobal);
                                self.companyNameAbb(company.companyNameAbb);
                                self.companyNameKana(company.companyNameKana);
                                self.corporateMyNumber(company.corporateMyNumber);
                                self.address2(company.address2);
                                self.address1(company.address1);
                                self.addressKana1(company.addressKana1);
                                self.addressKana2(company.addressKana2);
                                self.depWorkPlaceSet(company.depWorkPlaceSet);
                                self.displayAttribute(company.displayAttribute.toString());
                                if (company.displayAttribute === 1) {
                                    self.isDelete(true);
                                }
                                else {
                                    self.isDelete(false);
                                }
                                self.faxNo(company.faxNo);
                                self.postal(company.postal);
                                self.presidentName(company.presidentName);
                                self.presidentJobTitle(company.presidentJobTitle);
                                self.telephoneNo(company.telephoneNo);
                                self.termBeginMon(company.termBeginMon);
                                self.companyUseSet(new CompanyUseSet(company.use_Kt_Set, company.use_Qy_Set, company.use_Jj_Set));
                                self.selectedRuleCode(company.use_Jj_Set.toString());
                                self.selectedRuleCode1(company.use_Kt_Set.toString());
                                self.selectedRuleCode2(company.use_Qy_Set.toString());
                                self.selectedRuleCode3(company.depWorkPlaceSet.toString());
                                self.isEnableCompanyCode(false);
                            }
                            else {
                                self.editMode = false;
                                self.address1('');
                                self.address2('');
                                self.addressKana1('');
                                self.addressKana2('');
                                self.companyName('');
                                self.companyNameGlobal('');
                                self.companyNameAbb('');
                                self.companyNameKana('');
                                self.corporateMyNumber('');
                                self.depWorkPlaceSet(0);
                                self.displayAttribute('');
                                self.faxNo('');
                                self.postal('');
                                self.presidentName('');
                                self.presidentJobTitle('');
                                self.telephoneNo('');
                                self.termBeginMon(0);
                                self.companyUseSet(new CompanyUseSet(0, 0, 0));
                                self.isDelete(false);
                            }
                        });
                    }
                    else {
                        self.editMode = true;
                    }
                });
            }
            CompanyModel.prototype.setList = function (list, companyCode) {
                this.sources = list || [];
                this.companyCode(companyCode);
            };
            CompanyModel.prototype.init = function (param) {
                var self = this;
                self.sources = param.sources || [];
                self.companyCode = ko.observable(param.companyCode);
                self.address1 = ko.observable(param.address1);
                self.address2 = ko.observable(param.address2);
                self.addressKana1 = ko.observable(param.addressKana1);
                self.addressKana2 = ko.observable(param.addressKana2);
                self.companyName = ko.observable(param.companyName);
                self.companyNameGlobal = ko.observable(param.companyNameGlobal);
                self.companyNameAbb = ko.observable(param.companyNameAbb);
                self.companyNameKana = ko.observable(param.companyNameKana);
                self.corporateMyNumber = ko.observable(param.corporateMyNumber);
                self.depWorkPlaceSet = ko.observable(param.depWorkPlaceSet);
                self.displayAttribute = ko.observable(param.displayAttribute);
                self.faxNo = ko.observable(param.faxNo);
                self.postal = ko.observable(param.postal);
                self.presidentName = ko.observable(param.presidentName);
                self.presidentJobTitle = ko.observable(param.presidentJobTitle);
                self.telephoneNo = ko.observable(param.telephoneNo);
                self.termBeginMon = ko.observable(param.termBeginMon);
                self.companyUseSet = ko.observable(param.companyUseSet);
                self.isDelete = ko.observable(param.isDelete || false);
                //SWITCH
                self.roundingRules = ko.observableArray([
                    new RoundingRule("1", '利用する'),
                    new RoundingRule('0', '利用しない')
                ]);
                self.selectedRuleCode = ko.observable("");
                self.selectedRuleCode1 = ko.observable("");
                self.selectedRuleCode2 = ko.observable('');
                self.roundingRules3 = ko.observableArray([
                    new RoundingRule("1", '区別する'),
                    new RoundingRule('0', '区別しない')
                ]);
                self.selectedRuleCode3 = ko.observable("");
                self.itemList = ko.observableArray([
                    { code: '1', name: '1月' },
                    { code: '2', name: '2月' },
                    { code: '3', name: '3月' },
                    { code: '4', name: '4月' },
                    { code: '5', name: '5月' },
                    { code: '6', name: '6月' },
                    { code: '7', name: '7月' },
                    { code: '8', name: '8月' },
                    { code: '9', name: '9月' },
                    { code: '10', name: '10月' },
                    { code: '11', name: '11月' },
                    { code: '12', name: '12月' }
                ]);
            };
            return CompanyModel;
        }());
        var Address = (function () {
            function Address(address1, address2, addressKana1, addressKana2) {
                this.address1 = ko.observable(address1);
                this.address2 = ko.observable(address2);
                this.addressKana1 = ko.observable(addressKana1);
                this.addressKana2 = ko.observable(addressKana2);
            }
            return Address;
        }());
        a.Address = Address;
        var CompanyUseSet = (function () {
            function CompanyUseSet(useKtSet, useQySet, useJjSet) {
                this.useGrSet = 0;
                this.useKtSet = useKtSet;
                this.useQySet = useQySet;
                this.useJjSet = useJjSet;
                this.useAcSet = 0;
                this.useGwSet = 0;
                this.useHcSet = 0;
                this.useLcSet = 0;
                this.useBiSet = 0;
                this.useRs01Set = 0;
                this.useRs02Set = 0;
                this.useRs03Set = 0;
                this.useRs04Set = 0;
                this.useRs05Set = 0;
                this.useRs06Set = 0;
                this.useRs07Set = 0;
                this.useRs08Set = 0;
                this.useRs09Set = 0;
                this.useRs10Set = 0;
            }
            return CompanyUseSet;
        }());
        a.CompanyUseSet = CompanyUseSet;
        var RoundingRule = (function () {
            function RoundingRule(code, name) {
                this.code = code;
                this.name = name;
            }
            return RoundingRule;
        }());
    })(a = cmm001.a || (cmm001.a = {}));
})(cmm001 || (cmm001 = {}));
