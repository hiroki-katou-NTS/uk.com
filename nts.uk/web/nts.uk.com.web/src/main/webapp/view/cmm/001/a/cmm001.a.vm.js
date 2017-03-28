var cmm001;
(function (cmm001) {
    var a;
    (function (a) {
        var ViewModel = (function () {
            function ViewModel() {
                this.mode = ko.observable(null);
                this.editmode = ko.observable(null);
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
                    address: null,
                    companyName: '',
                    companyNameGlobal: '',
                    corporateMyNumber: '',
                    depWorkPlaceSet: 0,
                    displayAttribute: '',
                    termBeginMon: 0,
                    companyUseSet: null
                }));
                self.sel001Data = ko.observableArray([]);
                self.dirtyStart = new nts.uk.ui.DirtyChecker(self.currentCompany);
                self.dirtySelectData = new nts.uk.ui.DirtyChecker(self.sel001Data);
                self.currentCompany.subscribe(function (data) {
                    console.log(data);
                });
                self.checked.subscribe(function (newValue) {
                    var $grid = $("#A_LST_001");
                    var currentColumns = $grid.igGrid("option", "columns");
                    var width = $grid.igGrid("option", "width");
                    if (newValue) {
                        $('#A_SEL_001').ntsError('clear');
                        currentColumns[2].hidden = false;
                        $grid.igGrid("option", "width", "400px");
                    }
                    else {
                        currentColumns[2].hidden = true;
                        $grid.igGrid("option", "width", "400px");
                    }
                    $grid.igGrid("option", "columns", currentColumns);
                    self.sel001Data([]);
                    self.start(undefined);
                    nts.uk.ui.dialog.alert("変更された内容が登録されていません。\r\nよろしいですか。");
                });
            }
            ViewModel.prototype.start = function (currentCode) {
                var self = this;
                var items = [];
                a.service.getAllCompanys().done(function (data) {
                    if (data.length > 0) {
                        self.mode(true);
                        _.each(data, function (obj) {
                            var node;
                            if (obj.displayAttribute === 1) {
                                node = ko.mapping.fromJS(obj);
                                node.displayAttribute('<i style="margin-left: 15px" class="icon icon-close"></i>');
                                self.sel001Data.push(ko.toJS(node));
                            }
                            else {
                                node = ko.mapping.fromJS(obj);
                                node.displayAttribute('');
                                self.sel001Data.push(ko.toJS(node));
                            }
                        });
                        if (currentCode === undefined) {
                            self.currentCompany().setList(data, ko.toJS(self.sel001Data()[0].companyCode));
                        }
                        else {
                            self.currentCompany().setList(data, currentCode);
                        }
                    }
                    else {
                        self.mode(false);
                    }
                });
            };
            ViewModel.prototype.resetData = function () {
                var self = this;
                if (self.dirtyStart.isDirty()) {
                    nts.uk.ui.dialog.confirm("Do you want to change data?").ifYes(function () {
                        self.currentCompany().companyCode(null);
                        self.currentCompany().address(new Address('', '', '', ''));
                        self.currentCompany().companyName("");
                        self.currentCompany().companyNameGlobal("");
                        self.currentCompany().companyNameAbb('');
                        self.currentCompany().companyNameKana('');
                        self.currentCompany().corporateMyNumber('');
                        self.currentCompany().companyUseSet(new CompanyUseSet(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
                        self.currentCompany().depWorkPlaceSet(0);
                        self.currentCompany().displayAttribute('');
                        self.currentCompany().faxNo('');
                        self.currentCompany().telephoneNo('');
                        self.currentCompany().postal('');
                        self.currentCompany().presidentName('');
                        self.currentCompany().presidentJobTitle('');
                        self.currentCompany().termBeginMon(0);
                        self.currentCompany().isDelete(true);
                        self.currentCompany().editMode = true;
                        self.mode(false);
                    });
                }
            };
            ViewModel.prototype.clickRegister = function () {
                var self = this;
                var dfd = $.Deferred();
                var currentCompany;
                currentCompany = ko.toJS(self.currentCompany);
                console.log(currentCompany);
                if (currentCompany.isDelete) {
                    currentCompany.displayAttribute = "1";
                }
                else {
                    currentCompany.displayAttribute = "0";
                }
                var error;
                if (nts.uk.text.allHalfKatakana(ko.toJS(currentCompany.companyNameKana)) === true) {
                    $('#A_INP_004').ntsError('clear');
                    error = true;
                }
                else {
                    $('#A_INP_004').ntsError('set', 'this text must be half katakana type');
                    error = false;
                }
                var error1;
                if (ko.toJS(currentCompany.companyName) !== "") {
                    $('#A_INP_003').ntsError('clear');
                    error1 = true;
                }
                else {
                    $("#A_INP_003").ntsError('set', 'this text must be not null');
                    error1 = false;
                }
                var error2;
                if (currentCompany.address.addressKana1 === "") {
                    $('#C_INP_002').ntsError('clear');
                    error2 = true;
                }
                else {
                    $("#C_INP_002").ntsError('set', 'this address must not null');
                    error2 = false;
                }
                var error4;
                if (nts.uk.text.allHalfKatakana(ko.toJS(currentCompany.address.addressKana1)) === true) {
                    $('#C_INP_004').ntsError('clear');
                    error4 = true;
                }
                else {
                    $('#C_INP_004').ntsError('set', 'this text must be half katakana type');
                    error4 = false;
                }
                var error5;
                if (nts.uk.text.allHalfKatakana(ko.toJS(currentCompany.address.addressKana2)) === true && (ko.toJS(currentCompany.address.addressKana2) === "" || ko.toJS(currentCompany.address.addressKana2) !== "")) {
                    $('#C_INP_005').ntsError('clear');
                    error5 = true;
                }
                else {
                    $('#C_INP_005').ntsError('set', 'this text must be half katakana type');
                    error5 = false;
                }
                var errorCheck1;
                if (self.checked()) {
                    $('#A_SEL_001').ntsError('clear');
                    errorCheck1 = true;
                }
                else {
                    $('#A_SEL_001').ntsError('set', 'this check must be true');
                    errorCheck1 = false;
                }
                var allerror;
                allerror = error && error1 && error2 && error4 && error5 && errorCheck1;
                var company = new a.service.model.CompanyDto(null);
                company.companyCode = ko.toJS(currentCompany.companyCode);
                company.companyName = ko.toJS(currentCompany.companyName);
                company.companyNameGlobal = ko.toJS(currentCompany.companyNameGlobal);
                company.companyNameAbb = ko.toJS(currentCompany.companyNameAbb);
                company.companyNameKana = ko.toJS(currentCompany.companyNameKana);
                company.corporateMyNumber = ko.toJS(currentCompany.corporateMyNumber);
                company.use_Jj_Set = Number(ko.toJS(currentCompany.selectedRuleCode));
                company.use_Kt_Set = Number(ko.toJS(currentCompany.selectedRuleCode1));
                company.use_Qy_Set = Number(ko.toJS(currentCompany.selectedRuleCode2));
                company.depWorkPlaceSet = Number(ko.toJS(currentCompany.selectedRuleCode3));
                company.displayAttribute = Number(ko.toJS(currentCompany.displayAttribute));
                company.termBeginMon = Number(ko.toJS(currentCompany.termBeginMon));
                company.address1 = ko.toJS(currentCompany.address.address1);
                company.address2 = ko.toJS(currentCompany.address.address2);
                company.addressKana1 = ko.toJS(currentCompany.address.addressKana1);
                company.addressKana2 = ko.toJS(currentCompany.address.addressKana2);
                company.telephoneNo = ko.toJS(currentCompany.telephoneNo);
                company.faxNo = ko.toJS(currentCompany.faxNo);
                company.postal = ko.toJS(currentCompany.postal);
                company.presidentJobTitle = ko.toJS(currentCompany.presidentJobTitle);
                company.presidentName = ko.toJS(currentCompany.presidentName);
                if (allerror && self.dirtySelectData.isDirty()) {
                    nts.uk.ui.dialog.confirm("Do you want to change data?").ifYes(function () {
                        if (self.dirtyStart.isDirty()) {
                            if (self.mode()) {
                                cmm001.a.service.updateData(company).done(function (data) {
                                    console.log(company);
                                    self.sel001Data([]);
                                    self.start(company.companyCode);
                                    nts.uk.ui.dialog.alert("変更された内容が登録されていません。\r\nよろしいですか。");
                                });
                            }
                            else {
                                cmm001.a.service.addData(company).done(function () {
                                    self.sel001Data([]);
                                    self.start(company.companyCode);
                                    nts.uk.ui.dialog.alert("変更された内容が登録されていません。\r\nよろしいですか。");
                                });
                            }
                        }
                    }).ifNo(function () {
                        if (!self.dirtyStart.isDirty()) {
                            self.dirtyStart.reset();
                            self.dirtySelectData.reset();
                        }
                    });
                }
            };
            ViewModel.prototype.clickSetting = function () {
            };
            ViewModel.prototype.clickLog = function () {
            };
            ViewModel.prototype.Browse = function () { };
            ViewModel.prototype.clickSel002 = function () { };
            return ViewModel;
        }());
        a.ViewModel = ViewModel;
        var CompanyModel = (function () {
            function CompanyModel(param) {
                this.editMode = true;
                var self = this;
                self.sources = param.sources || [];
                self.companyCode = ko.observable(param.companyCode);
                self.address = ko.observable(new Address("", "", "", ""));
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
                self.companyCode.subscribe(function (newValue) {
                    if (self.editMode) {
                        var company = _.find(self.sources, function (item) { return item.companyCode == newValue; });
                        if (company) {
                            $(document).ready(function (data) {
                                $("#A_INP_002").attr('disabled', 'true');
                                $("#A_INP_002").attr('readonly', 'true');
                            });
                            self.address(new Address(company.address1, company.address2, company.addressKana1, company.addressKana2));
                            self.companyName(company.companyName);
                            self.companyNameGlobal(company.companyNameGlobal);
                            self.companyNameAbb(company.companyNameAbb);
                            self.companyNameKana(company.companyNameKana);
                            self.corporateMyNumber(company.corporateMyNumber);
                            self.depWorkPlaceSet(company.depWorkPlaceSet);
                            self.displayAttribute(company.displayAttribute);
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
                            self.companyUseSet(new CompanyUseSet(company.use_Gr_Set, company.use_Kt_Set, company.use_Qy_Set, company.use_Jj_Set, company.use_Ac_Set, company.use_Gw_Set, company.use_Hc_Set, company.use_Lc_Set, company.use_Bi_Set, company.use_Rs01_Set, company.use_Rs02_Set, company.use_Rs03_Set, company.use_Rs04_Set, company.use_Rs05_Set, company.use_Rs06_Set, company.use_Rs07_Set, company.use_Rs08_Set, company.use_Rs09_Set, company.use_Rs10_Set));
                            self.selectedRuleCode(company.use_Jj_Set.toString());
                            self.selectedRuleCode1(company.use_Kt_Set.toString());
                            self.selectedRuleCode2(company.use_Qy_Set.toString());
                            self.selectedRuleCode3(company.depWorkPlaceSet.toString());
                        }
                        else {
                            self.editMode = false;
                            self.address(new Address("", "", "", ""));
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
                            self.companyUseSet(new CompanyUseSet(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
                            self.isDelete(false);
                        }
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
            CompanyModel.prototype.clickSel001 = function () { };
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
            function CompanyUseSet(useGrSet, useKtSet, useQySet, useJjSet, useAcSet, useGwSet, useHcSet, useLcSet, useBiSet, useRs01Set, useRs02Set, useRs03Set, useRs04Set, useRs05Set, useRs06Set, useRs07Set, useRs08Set, useRs09Set, useRs10Set) {
                this.useGrSet = useGrSet;
                this.useKtSet = useKtSet;
                this.useQySet = useQySet;
                this.useJjSet = useJjSet;
                this.useAcSet = useAcSet;
                this.useGwSet = useGwSet;
                this.useHcSet = useHcSet;
                this.useLcSet = useLcSet;
                this.useBiSet = useBiSet;
                this.useRs01Set = useRs01Set;
                this.useRs02Set = useRs02Set;
                this.useRs03Set = useRs03Set;
                this.useRs04Set = useRs04Set;
                this.useRs05Set = useRs05Set;
                this.useRs06Set = useRs06Set;
                this.useRs07Set = useRs07Set;
                this.useRs08Set = useRs08Set;
                this.useRs09Set = useRs09Set;
                this.useRs10Set = useRs10Set;
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
//# sourceMappingURL=cmm001.a.vm.js.map