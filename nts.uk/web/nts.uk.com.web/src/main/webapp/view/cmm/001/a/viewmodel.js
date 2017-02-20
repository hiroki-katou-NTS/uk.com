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
                console.log(self.items());
                console.log(self.companys());
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
                console.log(nts.uk.text.allHalfKatakana("ｱｱｱ"));
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
                        $(document).ready(function () {
                            $("#single-list").igGrid({
                                height: 450,
                                width: 390,
                                options: self.items(),
                                columns: self.columns2()
                            });
                        });
                    }
                });
                self.selectedRuleCode1.subscribe(function (value) {
                    console.log(value);
                });
            }
            ScreenModel.prototype.getDetailCompany = function (items, newValue) {
                var self = this;
                var node = new cmm001.a.service.model.CompanyDto();
                //            $(document).ready(function(data) {
                //                $("#A_BTN_002").attr('disabled', 'true');
                //            });
                _.find(items, function (obj) {
                    if (obj.companyCode.toString() == newValue) {
                        console.log(obj);
                        $(document).ready(function (data) {
                            $("#A_INP_002").attr('disabled', 'true');
                            $("#A_INP_002").attr('readonly', 'true');
                        });
                        node.companyCode = obj.companyCode;
                        node.companyName = obj.companyName;
                        node.companyNameAbb = obj.companyNameAbb;
                        node.companyNameKana = obj.companyNameKana;
                        node.corporateMyNumber = obj.corporateMyNumber;
                        node.presidentName = obj.presidentName;
                        node.presidentJobTitle = obj.presidentJobTitle;
                        node.address1 = obj.address1;
                        node.address2 = obj.address2;
                        node.addressKana1 = obj.addressKana1;
                        node.addressKana2 = obj.addressKana2;
                        node.telephoneNo = obj.telephoneNo;
                        node.faxNo = obj.faxNo;
                        node.postal = obj.postal;
                        node.use_Jj_Set = obj.use_Jj_Set;
                        node.use_kt_Set = obj.use_kt_Set;
                        node.use_Qy_Set = obj.use_Qy_Set;
                        node.depWorkPlaceSet = obj.depWorkPlaceSet;
                        self.selectedRuleCode(obj.use_Jj_Set);
                        self.selectedRuleCode1(obj.use_kt_Set);
                        self.selectedRuleCode2(obj.use_Qy_Set);
                        self.selectedRuleCode3(obj.depWorkPlaceSet);
                        self.testCompany(ko.mapping.fromJS(node));
                        self.currentCompanyDto(node);
                    }
                });
                return node;
            };
            ScreenModel.prototype.resetData = function () {
                var self = this;
                self.editMode = false;
                //self.currentCode({});
                var companyDto = new cmm001.a.service.model.CompanyDto();
                companyDto.companyCode = "";
                companyDto.companyName = "";
                companyDto.companyNameGlobal = "";
                companyDto.companyNameAbb = "";
                companyDto.companyNameKana = "";
                companyDto.corporateMyNumber = "";
                companyDto.address1 = "";
                companyDto.address2 = "";
                companyDto.addressKana1 = "";
                companyDto.addressKana2 = "";
                companyDto.depWorkPlaceSet = 0;
                companyDto.displayAttribute = 0;
                companyDto.faxNo = "";
                companyDto.postal = "";
                companyDto.presidentName = "";
                companyDto.presidentJobTitle = "";
                companyDto.telephoneNo = '';
                companyDto.termBeginMon = 0;
                companyDto.use_Gr_Set = 0;
                companyDto.use_kt_Set = 0;
                companyDto.use_Qy_Set = 0;
                companyDto.use_Jj_Set = 0;
                self.testCompany(ko.mapping.fromJS(companyDto));
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
                    new RoundingRule("1", '利用する'),
                    new RoundingRule('0', '利用しない')
                ]);
                self.selectedRuleCode = ko.observable(self.roundingRules()[1].code);
                self.roundingRules1 = ko.observableArray([
                    new RoundingRule("1", '利用する'),
                    new RoundingRule('0', '利用しない')
                ]);
                self.selectedRuleCode1 = ko.observable("1");
                self.roundingRules2 = ko.observableArray([
                    new RoundingRule("1", '利用する'),
                    new RoundingRule('0', '利用しない')
                ]);
                self.selectedRuleCode2 = ko.observable(self.roundingRules2()[1].code);
                self.roundingRules3 = ko.observableArray([
                    new RoundingRule("1", '区別する'),
                    new RoundingRule('0', '区別しない')
                ]);
                self.selectedRuleCode3 = ko.observable(self.roundingRules3()[0].code);
                //self.selectedRuleCode1 = ko.observable(1);
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
                //self.currentCompanyDto = ko.observable(new cmm001.a.service.model.CompanyDto());
                var companyDto = new cmm001.a.service.model.CompanyDto();
                //companyDto.companyCode = "0001";
                var dfd = $.Deferred();
                cmm001.a.service.getAllCompanys().done(function (companies) {
                    if (companies.length > 0) {
                        _.find(companies, function (obj) {
                            if (companyDto.companyCode == obj.companyCode) {
                                companyDto = obj;
                            }
                        });
                    }
                });
                console.log(companyDto);
                companyDto.companyCode = "0001";
                companyDto.companyName = "samsung";
                companyDto.companyNameGlobal = "1111";
                companyDto.companyNameAbb = "11111";
                companyDto.companyNameKana = "heheeh";
                companyDto.corporateMyNumber = "21111111";
                companyDto.address1 = "toshiba";
                companyDto.address2 = "nagoya";
                companyDto.addressKana1 = "toshiba";
                companyDto.addressKana2 = "nagoya";
                companyDto.depWorkPlaceSet = 1;
                companyDto.displayAttribute = 2;
                companyDto.faxNo = "11111111";
                companyDto.postal = "22222222222";
                companyDto.presidentName = "Lan";
                companyDto.presidentJobTitle = "Dir";
                companyDto.telephoneNo = '00285894396';
                companyDto.termBeginMon = 2;
                companyDto.use_Gr_Set = 1;
                companyDto.use_kt_Set = 1;
                companyDto.use_Qy_Set = 1;
                companyDto.use_Jj_Set = 1;
                //            
                //companyDto.companyName = "s";
                self.testCompany = ko.observable(ko.mapping.fromJS(companyDto));
                self.currentCompanyDto = ko.observable(ko.mapping.fromJS(companyDto));
                self.item1s = ko.observableArray([]);
            };
            // register company information
            //BTN-002 
            ScreenModel.prototype.ClickRegister = function () {
                var self = this;
                var dfd = $.Deferred();
                self.currentCompanyDto().companyCode = $("#A_INP_002").val();
                var companyNameGlobal;
                self.currentCompanyDto().address1 = $("#C_INP_002").val();
                self.currentCompanyDto().address2 = $("#C_INP_003").val();
                self.currentCompanyDto().addressKana1 = $("#C_INP_004").val();
                self.currentCompanyDto().addressKana2 = $("#C_INP_005").val();
                self.currentCompanyDto().companyNameAbb = $("#A_INP_005").val();
                self.currentCompanyDto().corporateMyNumber = $("#B_INP_001").val();
                self.currentCompanyDto().faxNo = $("#C_INP_007").val();
                self.currentCompanyDto().postal = $("#C_INP_001").val();
                self.currentCompanyDto().presidentName = $("#B_INP_002").val();
                self.currentCompanyDto().presidentJobTitle = $("#B_INP_003").val();
                self.currentCompanyDto().telephoneNo = $("#C_INP_006").val();
                var termBeginMon;
                self.currentCompanyDto().use_kt_Set = self.selectedRuleCode1();
                self.currentCompanyDto().use_Qy_Set = self.selectedRuleCode2();
                self.currentCompanyDto().depWorkPlaceSet = self.selectedRuleCode3();
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
                var items;
                var error;
                console.log(nts.uk.util.isNullOrUndefined($("#A_INP_003").val()));
                //console.log(self.currentCompanyDto().companyName);
                if (nts.uk.text.allHalfKatakana($("#A_INP_004").val()) === true) {
                    $('#A_INP_004').ntsError('clear');
                    self.currentCompanyDto().companyNameKana = $("#A_INP_004").val();
                    error = true;
                }
                else {
                    $('#A_INP_004').ntsError('set', 'this text must be half katakana type');
                    error = false;
                }
                if ($("#A_INP_003").val() !== "") {
                    self.currentCompanyDto().companyName = $("#A_INP_003").val();
                    error = true;
                }
                else {
                    $("#A_INP_003").ntsError('set', 'this text must be not null');
                    error = false;
                }
                // if(nts.uk.text.)
                if (self.currentCompanyDto().companyCode != self.currentCode()) {
                    if (error === true) {
                        cmm001.a.service.addData(self.currentCompanyDto()).done(function () {
                            self.start();
                            self.currentCode("0001");
                            self.currentCode(self.currentCompanyDto().companyCode);
                        });
                        self.currentCode(self.currentCompanyDto().companyCode);
                    }
                }
                else {
                    if (error == true) {
                        cmm001.a.service.updateData(self.currentCompanyDto()).done(function () {
                            self.start();
                            console.log(self.items());
                            //self.currentCode("0001");
                            $(document).ready(function (data) {
                                $("#A_BTN_002").attr('disabled', 'true');
                            });
                        });
                        self.currentCode(self.currentCompanyDto().companyCode);
                    }
                }
                self.currentCode(self.currentCompanyDto().companyCode);
            };
            ScreenModel.prototype.update = function (items, companyCode) {
                var self = this;
                _.forEach(items, function (obj) {
                    if (obj.code === companyCode) {
                        return _.update(obj, "obj.name", function (companyname) { return companyname = self.currentCompanyDto().companyName; });
                    }
                });
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
                        self.buildGridDataSource(companies);
                        self.currentCode("0001");
                    }
                    dfd.resolve();
                });
                return dfd.promise();
            };
            ScreenModel.prototype.buildGridDataSource = function (items) {
                var self = this;
                self.items = ko.observableArray([]);
                _.forEach(items, function (obj) {
                    self.items().push(new Company(obj.companyCode.toString(), obj.companyName, ""));
                });
                self.currentCode(self.currentCompanyDto().companyCode);
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
        var GridList = (function () {
            function GridList() {
            }
            GridList.prototype.contructor = function (height, options, optionsValue, columns, value) {
                this.height = height;
                this.options = options;
                this.optionsValue = optionsValue;
                this.columns = columns;
                this.value = value;
            };
            return GridList;
        }());
        a.GridList = GridList;
        var RoundingRule = (function () {
            function RoundingRule(code, name) {
                this.code = code;
                this.name = name;
            }
            return RoundingRule;
        }());
        a.RoundingRule = RoundingRule;
    })(a = cmm001.a || (cmm001.a = {}));
})(cmm001 || (cmm001 = {}));
;
