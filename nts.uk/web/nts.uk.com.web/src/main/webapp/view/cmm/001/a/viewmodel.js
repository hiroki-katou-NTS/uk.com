var cmm001;
(function (cmm001) {
    var a;
    (function (a) {
        var ScreenModel = (function () {
            function ScreenModel() {
                this.editMode = true;
                //search box
                this.textSearch = "";
                var self = this;
                var node;
                self.init();
                self.A_INP_002 = ko.observable(new TextEditor(self.currentCode, 'CompanyCode', ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption()), true, false));
                self.A_INP_003 = ko.observable(new TextEditor("hehhehe", 'CompanyName', ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption()), true, false));
                self.currentCode.subscribe(function (newValue) {
                    var dfd = $.Deferred();
                    cmm001.a.service.getAllCompanys().done(function (companies) {
                        if (companies.length > 0) {
                            self.companys(companies);
                            if (self.editMode) {
                                self.getDetailCompany(self.companys(), newValue);
                            }
                            else {
                                self.editMode = true;
                            }
                        }
                        dfd.resolve();
                    });
                });
                self.checked2.subscribe(function (newValue) {
                    self.columns2()[2]._initiallyHidden = false;
                    self.columns2()[2].hidden = false;
                    // chu y khi code chinh phai lay lai datasource khong se bi thay doi source
                    self.item1s = ko.observableArray([
                        new Company('01', '日通システム株式会社', ''),
                        new Company('02', '有限会社日通ベトナム', ''),
                        new Company('03', 'UKシステム株式会社', ''),
                        new Company('04', '○○株式会社', '<i class="icon icon-close"></i>')
                    ]);
                    if (!newValue) {
                        for (var i = 0; i < _.size(self.items()); i++) {
                            if (self.items()[i].description === '<i class="icon icon-close"></i>') {
                                node = self.items()[i];
                                self.items()[i] = new Company('', '', '');
                                self.items()[i] = new Company(node.code, node.name, '');
                                console.log(self.items()[i]);
                                self.currentCode(self.items()[i].code);
                            }
                        }
                    }
                    else {
                        // console.log(self.item1s());
                        self.items([]);
                        self.items(self.item1s());
                        self.currentCode(self.items()[0].code);
                    }
                });
                self.checked1.subscribe(function (newValue) {
                    if (newValue) {
                        self.columns2()[2].hidden = true;
                        $(function () {
                            $("#single-list").igGrid({
                                heigth: 450,
                                width: 350,
                                options: self.items(),
                                columns: self.columns2()
                            });
                        });
                    }
                    else {
                        //  self.columns2()[2].hidden = false;
                        self.columns2 = ko.observableArray([
                            { headerText: '会社コード', prop: 'code', width: 80 },
                            { headerText: '名称', prop: 'name', width: 200 },
                            { headerText: '廃止', prop: 'description', width: 50, hidden: false }
                        ]);
                        $(function () {
                            $("#single-list").igGrid({
                                height: 450,
                                width: 390,
                                options: self.items(),
                                columns: self.columns2()
                            });
                        });
                    }
                });
            }
            ScreenModel.prototype.getObj = function (items, newValue) {
                var self = this;
                var node;
                _.find(items, function (obj) {
                    if (!node) {
                        if (obj.code == newValue) {
                            node = obj;
                        }
                    }
                });
            };
            ScreenModel.prototype.getDetailCompany = function (items, newValue) {
                var self = this;
                var node;
                _.find(items, function (obj) {
                    if (obj.companyCode.toString() == newValue) {
                        node = obj;
                        self.currentCompanyDto(node);
                    }
                });
                return node;
            };
            ScreenModel.prototype.resetData = function () {
                var self = this;
                self.editMode = false;
                self.currentCode({});
                // self.currentNode(new Company("", "", ""));
                self.currentCompanyDto({});
                //self.currentCompanyDto({});
            };
            ScreenModel.prototype.init = function () {
                var self = this;
                self.checked1 = ko.observable(false);
                self.items = ko.observableArray([]);
                self.columns2 = ko.observableArray([
                    { headerText: '会社コード', prop: 'code', width: 80 },
                    { headerText: '名称', prop: 'name', width: 200 },
                    { headerText: '廃止', prop: 'description', width: 50, hidden: false }
                ]);
                self.currentCode = ko.observable("0001");
                self.currentCodeList = ko.observableArray(null);
                //tabpanel
                self.tabs = ko.observableArray([
                    { id: 'tab-1', title: '会社基本情報', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: '会社所在地・連絡先', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-3', title: 'システム設定', content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) }
                ]);
                self.selectedTab = ko.observable('tab-1');
                //SWITCH
                self.roundingRules = ko.observableArray([
                    { code: '1', name: '区別する' },
                    { code: '2', name: '区別しない' }
                ]);
                self.selectedRuleCode = ko.observable(2);
                self.roundingRule1s = ko.observableArray([
                    { code: '1', name: '利用する' },
                    { code: '2', name: '利用しない' }
                ]);
                self.selectedRuleCode1 = ko.observable(1);
                //COMBOX 
                self.itemList = ko.observableArray([
                    new Company('1', '1月', ''),
                    new Company('2', '2月', ''),
                    new Company('3', '3月', ''),
                    new Company('4', '4月', ''),
                    new Company('5', '5月', ''),
                    new Company('6', '6月', ''),
                    new Company('7', '7月', ''),
                    new Company('8', '8月', ''),
                    new Company('9', '9月', ''),
                    new Company('10', '10月', '')
                ]);
                self.companys = ko.observableArray([]);
                self.itemName = ko.observable('');
                self.currentCodeCombox = ko.observable(4);
                self.selectedCode = ko.observable('4');
                self.isEnable = ko.observable(true);
                self.isEditable = ko.observable(true);
                //check
                self.checked2 = ko.observable(true);
                self.currentCompanyDto = ko.observable(new cmm001.a.service.model.CompanyDto());
            };
            // register company information
            //BTN-002 
            ScreenModel.prototype.ClickRegister = function () {
                var self = this;
                var dfd = $.Deferred();
                self.currentCompanyDto().companyCode = $("#A_INP_002").val();
                self.currentCompanyDto().companyName = $("#A_INP_003").val();
                var companyNameGlobal;
                self.currentCompanyDto().address1 = $("#C_INP_002").val();
                self.currentCompanyDto().address2 = $("#C_INP_003").val();
                self.currentCompanyDto().addressKana1 = $("#C_INP_004").val();
                self.currentCompanyDto().addressKana2 = $("#C_INP_005").val();
                self.currentCompanyDto().companyNameAbb = $("#A_INP_005").val();
                self.currentCompanyDto().companyNameKana = $("#A_INP_004").val();
                self.currentCompanyDto().corporateMyNumber = $("#B_INP_001").val();
                var depWorkPlaceSet;
                var displayAttribute;
                self.currentCompanyDto().faxNo = $("#C_INP_007").val();
                self.currentCompanyDto().postal = $("#C_INP_001").val();
                self.currentCompanyDto().presidentName = $("#B_INP_002").val();
                self.currentCompanyDto().presidentJobTitle = $("#B_INP_003").val();
                self.currentCompanyDto().telephoneNo = $("#C_INP_006").val();
                var termBeginMon;
                var use_Gr_Set;
                var use_kt_Set;
                var use_Qy_Set;
                var use_Jj_Set;
                var use_Ac_Set;
                var use_Gw_Set;
                var use_Hc_Set;
                var use_Lc_Set;
                var use_Bi_Set;
                var use_Rs01_Set;
                var use_Rs02_Set;
                var use_Rs03_Set;
                var use_Rs04_Set;
                var use_Rs05_Set;
                var use_Rs06_Set;
                var use_Rs07_Set;
                var use_Rs08_Set;
                var use_Rs10_Set;
                cmm001.a.service.addData(self.currentCompanyDto()).done(function () {
                    self.items.push(new Company(self.currentCompanyDto().companyCode, self.currentCompanyDto().companyName, ""));
                });
                console.log("lan");
                console.log(self.items());
            };
            //BTN-003 -Setting cac thong so ban dau
            ScreenModel.prototype.ClickSetting = function () {
                alert("Settting");
            };
            //BTN-004- Master correction log 
            ScreenModel.prototype.ClickLog = function () {
                alert("Log");
            };
            //SEL-Btn-001
            ScreenModel.prototype.ClickSel001 = function () {
                alert("Sel001");
            };
            //SEL-Btn-001
            ScreenModel.prototype.ClickSel002 = function () {
                var self = this;
                var dfd = $.Deferred();
                var item;
                item = ko.observable(new Company('01', '日通システム株式会社', ''));
                return dfd.promise();
            };
            //SEL-Btn-001
            ScreenModel.prototype.Browse = function () {
                alert("Browse!");
            };
            ScreenModel.prototype.start = function () {
                var self = this;
                var dfd = $.Deferred();
                cmm001.a.service.getAllCompanys().done(function (companies) {
                    if (companies.length > 0) {
                        self.companys(companies);
                        self.buildGridDataSource(self.companys());
                    }
                    dfd.resolve();
                });
                return dfd.promise();
            };
            ScreenModel.prototype.buildGridDataSource = function (items) {
                var self = this;
                _.forEach(items, function (obj) {
                    self.items().push(new Company(obj.companyCode.toString(), obj.companyName, ""));
                });
            };
            return ScreenModel;
        }());
        a.ScreenModel = ScreenModel;
        var Company = (function () {
            function Company(code, name, description) {
                this.code = code;
                this.name = name;
                this.description = description;
            }
            return Company;
        }());
        a.Company = Company;
        var TextEditor = (function () {
            function TextEditor(value, constraint, option, enable, readonly) {
                this.value = value;
                this.constraint = constraint;
                this.option = option;
                this.enable = enable;
                this.readonly = readonly;
            }
            return TextEditor;
        }());
        a.TextEditor = TextEditor;
    })(a = cmm001.a || (cmm001.a = {}));
})(cmm001 || (cmm001 = {}));
;
