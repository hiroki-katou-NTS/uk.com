var qmm003;
(function (qmm003) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.editMode = true; // true là mode thêm mới, false là mode sửa 
                    this.testNode = [];
                    var self = this;
                    self.currentNode = ko.observable(null);
                    self.init();
                    self.test = ko.observable('');
                    self.region = ko.observableArray([
                        new Node('1', '北海道', [], null, null, null),
                        new Node('2', '東北', [], null, null, null),
                        new Node('3', '関東', [], null, null, null),
                        new Node('4', '中部', [], null, null, null),
                        new Node('5', '近畿', [], null, null, null),
                        new Node('6', '中国', [], null, null, null),
                        new Node('7', '四国', [], null, null, null),
                        new Node('8', '九州', [], null, null, null)]);
                    //            self.regionPrefect = ko.observableArray([
                    //                new Node('1', '北海道', [new Node('01', '北海道', [])]),
                    //                new Node('2', '東北', [new Node('02', '青森県', []),
                    //                    new Node('03', '岩手県', []),
                    //                    new Node('04', '宮城県', []),
                    //                    new Node('05', '秋田県県', []),
                    //                    new Node('06', '山形県', []),
                    //                    new Node('07', '福島県県', [])]),
                    //                new Node('3', '関東', [new Node('08', '茨城県', []),
                    //                    new Node('09', '栃木県', []),
                    //                    new Node('10', '群馬県', []),
                    //                    new Node('11', '埼玉県', []),
                    //                    new Node('12', '千葉県', []),
                    //                    new Node('13', '東京都', []),
                    //                    new Node('14', '神奈川県', [])]),
                    //                new Node('4', '中部', [new Node('15', '新潟県', []),
                    //                    new Node('16', '富山県', []),
                    //                    new Node('17', '石川県', []),
                    //                    new Node('18', '福井県', []),
                    //                    new Node('19', '山梨県', []),
                    //                    new Node('20', '長野県', []),
                    //                    new Node('21', '岐阜県', []),
                    //                    new Node('22', '静岡県', []),
                    //                    new Node('23', '愛知県', [])]),
                    //                new Node('5', '近畿', [new Node('24', '三重県', []),
                    //                    new Node('25', '滋賀県', []),
                    //                    new Node('26', '京都府', []),
                    //                    new Node('27', '大阪府', []),
                    //                    new Node('28', '兵庫県', []),
                    //                    new Node('29', '奈良県', []),
                    //                    new Node('30', '和歌山県', [])]),
                    //                new Node('6', '中国', [new Node('31', '鳥取県', []),
                    //                    new Node('32', '島根県', []),
                    //                    new Node('33', '岡山県', []),
                    //                    new Node('34', '広島県', []),
                    //                    new Node('35', '山口県', [])]),
                    //                new Node('7', '四国', [new Node('36', '徳島県', []),
                    //                    new Node('37', '香川県', []),
                    //                    new Node('38', '愛媛県', []),
                    //                    new Node('39', '高知県', []),
                    //                    new Node('40', '福岡県', [])]),
                    //                new Node('8', '九州', [new Node('40', '福岡県', []),
                    //                    new Node('41', '佐賀県', []),
                    //                    new Node('42', '長崎県', []),
                    //                    new Node('43', '熊本県', []),
                    //                    new Node('44', '大分県', []),
                    //                    new Node('45', '宮崎県', []),
                    //                    new Node('46', '鹿児島県', []),
                    //                    new Node('47', '沖縄県', [])])]);
                    self.precfecture = ko.observableArray([
                        new Node('01', '北海道', [], '1', '北海道', ''),
                        new Node('02', '青森県', [], '2', '東北', '2'),
                        new Node('03', '岩手県', [], '2', '東北', '2'),
                        new Node('04', '宮城県', [], '2', '東北', '2'),
                        new Node('05', '秋田県県', [], '2', '東北', '2'),
                        new Node('06', '山形県', [], '2', '東北', '2'),
                        new Node('07', '福島県県', [], '2', '東北', '2'),
                        new Node('08', '茨城県', [], '3', '関東', '3'),
                        new Node('09', '栃木県', [], '3', '関東', '3'),
                        new Node('10', '群馬県', [], '3', '関東', '3'),
                        new Node('11', '埼玉県', [], '3', '関東', '3'),
                        new Node('12', '千葉県', [], '3', '関東', '3'),
                        new Node('13', '東京都', [], '3', '関東', '3'),
                        new Node('14', '神奈川県', [], '3', '関東', '3'),
                        new Node('15', '新潟県', [], '3', '関東', '3'),
                        new Node('16', '富山県', [], null, null, null),
                        new Node('17', '石川県', [], null, null, null),
                        new Node('18', '福井県', [], null, null, null),
                        new Node('19', '山梨県', [], null, null, null),
                        new Node('20', '長野県', [], null, null, null),
                        new Node('21', '岐阜県', [], null, null, null),
                        new Node('22', '静岡県', [], null, null, null),
                        new Node('23', '愛知県', [], null, null, null),
                        new Node('24', '三重県', [], null, null, null),
                        new Node('25', '滋賀県', [], null, null, null),
                        new Node('26', '京都府', [], null, null, null),
                        new Node('27', '大阪府', [], null, null, null),
                        new Node('28', '兵庫県', [], null, null, null),
                        new Node('29', '奈良県', [], null, null, null),
                        new Node('30', '和歌山県', [], null, null, null),
                        new Node('31', '鳥取県', [], null, null, null),
                        new Node('32', '島根県', [], null, null, null),
                        new Node('33', '岡山県', [], null, null, null),
                        new Node('34', '広島県', [], null, null, null),
                        new Node('35', '山口県', [], null, null, null),
                        new Node('36', '徳島県', [], null, null, null),
                        new Node('37', '香川県', [], null, null, null),
                        new Node('38', '愛媛県', [], null, null, null),
                        new Node('39', '高知県', [], null, null, null),
                        new Node('40', '福岡県', [], null, null, null),
                        new Node('41', '佐賀県', [], null, null, null),
                        new Node('42', '長崎県', [], null, null, null),
                        new Node('43', '熊本県', [], null, null, null),
                        new Node('44', '大分県', [], null, null, null),
                        new Node('45', '宮崎県', [], null, null, null),
                        new Node('46', '鹿児島県', [], null, null, null),
                        new Node('47', '沖縄県', [], null, null, null),
                    ]);
                    self.regionPrefect = ko.observableArray([
                        new Node('1', '北海道', [new Node('01', '北海道', [], '1', '北海道', '')], null, null, null),
                        new Node('2', '東北', [new Node('02', '青森県', [], '2', '東北', '2'),
                            new Node('03', '岩手県', [], '2', '東北', '2'),
                            new Node('04', '宮城県', [], '2', '東北', '2'),
                            new Node('05', '秋田県県', [], '2', '東北', '2'),
                            new Node('06', '山形県', [], '2', '東北', '2'),
                            new Node('07', '福島県県', [], '2', '東北', '2')], null, null, null),
                        new Node('3', '関東', [new Node('08', '茨城県', [], '3', '関東', '3'),
                            new Node('09', '栃木県', [], '3', '関東', '3'),
                            new Node('10', '群馬県', [], '3', '関東', '3'),
                            new Node('11', '埼玉県', [], '3', '関東', '3'),
                            new Node('12', '千葉県', [], '3', '関東', '3'),
                            new Node('13', '東京都', [], '3', '関東', '3'),
                            new Node('14', '神奈川県', [], '3', '関東', '3')], null, null, null),
                        new Node('4', '中部', [new Node('15', '新潟県', [], '4', '中部', '4'),
                            new Node('16', '富山県', [], '4', '中部', '4'),
                            new Node('17', '石川県', [], '4', '中部', '4'),
                            new Node('18', '福井県', [], '4', '中部', '4'),
                            new Node('19', '山梨県', [], '4', '中部', '4'),
                            new Node('20', '長野県', [], '4', '中部', '4'),
                            new Node('21', '岐阜県', [], '4', '中部', '4'),
                            new Node('22', '静岡県', [], '4', '中部', '4'),
                            new Node('23', '愛知県', [], '4', '中部', '4')], null, null, null),
                        new Node('5', '近畿', [new Node('24', '三重県', [], '5', '近畿', '5'),
                            new Node('25', '滋賀県', [], '5', '近畿', '5'),
                            new Node('26', '京都府', [], '5', '近畿', '5'),
                            new Node('27', '大阪府', [], '5', '近畿', '5'),
                            new Node('28', '兵庫県', [], '5', '近畿', '5'),
                            new Node('29', '奈良県', [], '5', '近畿', '5'),
                            new Node('30', '和歌山県', [], '5', '近畿', '5')], null, null, null),
                        new Node('6', '中国', [new Node('31', '鳥取県', [], '6', '中国', '6'),
                            new Node('32', '島根県', [], '6', '中国', '6'),
                            new Node('33', '岡山県', [], '6', '中国', '6'),
                            new Node('34', '広島県', [], '6', '中国', '6'),
                            new Node('35', '山口県', [], '6', '中国', '6')], null, null, null),
                        new Node('7', '四国', [new Node('36', '徳島県', [], '7', '四国', '7'),
                            new Node('37', '香川県', [], '7', '四国', '7'),
                            new Node('38', '愛媛県', [], '7', '四国', '7'),
                            new Node('39', '高知県', [], '7', '四国', '7'),
                            new Node('40', '福岡県', [], '7', '四国', '7')], null, null, null),
                        new Node('8', '九州', [new Node('40', '福岡県', [], '8', '九州', '8'),
                            new Node('41', '佐賀県', [], '8', '九州', '8'),
                            new Node('42', '長崎県', [], '8', '九州', '8'),
                            new Node('43', '熊本県', [], '8', '九州', '8'),
                            new Node('44', '大分県', [], '8', '九州', '8'),
                            new Node('45', '宮崎県', [], '8', '九州', '8'),
                            new Node('46', '鹿児島県', [], '8', '九州', '8'),
                            new Node('47', '沖縄県', [], '8', '九州', '8')], null, null, null)
                    ]);
                    self.filteredData = ko.observableArray(nts.uk.util.flatArray(self.items(), "childs"));
                    // console.log(self.filteredData());
                    self.filteredData1 = ko.observableArray(nts.uk.util.flatArray(self.items(), "childs"));
                    self.filteredData2 = ko.observableArray(nts.uk.util.flatArray(self.items(), "childs"));
                    self.removeData(self.filteredData2());
                    self.selectedCodes = ko.observableArray([]);
                    self.singleSelectedCode.subscribe(function (newValue) {
                        console.log(newValue);
                        self.Value(newValue);
                        if (self.editMode) {
                            var count = 0;
                            self.curentNode(self.findByCode(self.filteredData2(), newValue, count));
                            self.nameBySelectedCode(self.findByName(self.filteredData2()));
                            self.selectedCode(self.nameBySelectedCode().code);
                            var co_1 = 0, co1_1 = 0;
                            _.each(self.filteredData2(), function (obj) {
                                if (obj.code != self.curentNode().code) {
                                    co_1++;
                                }
                                else {
                                    if (co_1 < ((_.size(self.filteredData2())) - 1)) {
                                        co1_1 = co_1 + 1;
                                    }
                                    else {
                                        co1_1 = co_1;
                                    }
                                }
                            });
                            self.labelSubsub(self.filteredData2()[co1_1]);
                            if (self.labelSubsub() == null) {
                                self.labelSubsub(new Node("11", "22", [], null, null, null));
                            }
                        }
                        else {
                            self.editMode = true;
                        }
                    });
                }
                ScreenModel.prototype.findByCode = function (items, newValue, count) {
                    var self = this;
                    var node;
                    _.find(items, function (obj) {
                        if (!node) {
                            if (obj.code == newValue) {
                                node = obj;
                                count = count + 1;
                            }
                        }
                    });
                    return node;
                };
                ;
                ScreenModel.prototype.findByName = function (items) {
                    var self = this;
                    var node;
                    _.find(items, function (obj) {
                        if (!node) {
                            if (obj.name == self.curentNode().name) {
                                node = obj;
                            }
                        }
                    });
                    return node;
                };
                ;
                ScreenModel.prototype.removeNodeByCode = function (items) {
                    var self = this;
                    _.remove(items, function (obj) {
                        if (obj.code == self.Value()) {
                            return obj.code == self.Value();
                        }
                        else {
                            return self.removeNodeByCode(obj.childs);
                        }
                    });
                };
                ;
                // remove data: return array of subsub tree
                ScreenModel.prototype.removeData = function (items) {
                    _.remove(items, function (obj) {
                        return _.size(obj.code) < 3;
                    });
                };
                ScreenModel.prototype.deleteData = function () {
                    var self = this;
                    self.removeNodeByCode(self.items());
                    self.item1s(self.items());
                    self.items([]);
                    self.items(self.item1s());
                };
                ScreenModel.prototype.Confirm = function () {
                    var self = this;
                    nts.uk.ui.dialog.confirm("Do you want to delete node \"?")
                        .ifYes(function () {
                        self.deleteData();
                    });
                };
                ScreenModel.prototype.resetData = function () {
                    var self = this;
                    self.editMode = false;
                    self.curentNode(new Node("", "", [], null, null, null));
                    self.singleSelectedCode("");
                    self.selectedCode("");
                    self.labelSubsub("");
                    //            self.items([]);
                    //            self.items(self.item1s());
                };
                ScreenModel.prototype.search = function () {
                    var inputSearch = $("#search").find("input.ntsSearchBox").val();
                    if (inputSearch == "") {
                        $('#search').ntsError('set', 'inputSearch が入力されていません。');
                    }
                    else {
                        $('#search').ntsError('clear');
                    }
                    // errror search
                    var error;
                    _.find(this.filteredData2(), function (obj) {
                        if (obj.code !== inputSearch) {
                            error = true;
                        }
                    });
                    if (error = true) {
                        $('#search').ntsError('set', '対象データがありません。');
                    }
                    else {
                        $('#search').ntsError('clear');
                    }
                };
                ScreenModel.prototype.init = function () {
                    var self = this;
                    // 11.初期データ取得処理 11. Initial data acquisition processing [住民税納付先マスタ.SEL-1] 
                    //            if(self.mode() === false){
                    //                self.items([]);
                    //                
                    //            }else{
                    //                self.start();
                    //                
                    //            }
                    //青森市  itemList == RemoveData()
                    self.itemList = ko.observableArray([
                        new Node('1', '青森市', [], null, null, null),
                        new Node('2', '秋田市', [], null, null, null),
                        new Node('3', '山形市', [], null, null, null),
                        new Node('4', '福島市', [], null, null, null),
                        new Node('5', '水戸市', [], null, null, null),
                        new Node('6', '宇都宮市', [], null, null, null),
                        new Node('7', '川越市', [], null, null, null),
                        new Node('8', '熊谷市', [], null, null, null),
                        new Node('9', '浦和市', [], null, null, null)
                    ]);
                    // data of treegrid
                    self.items = ko.observableArray([]);
                    self.mode = ko.observable(null);
                    self.currentCode = ko.observable(null);
                    self.item1s = ko.observable(new Node('', '', [], null, null, null));
                    self.isEnable = ko.observable(true);
                    self.isEditable = ko.observable(true);
                    self.nameBySelectedCode = ko.observable(null);
                    self.Value = ko.observable(null);
                    self.singleSelectedCode = ko.observable("022012");
                    self.curentNode = ko.observable(new Node('022012', '青森市', [], null, null, null));
                    self.labelSubsub = ko.observable(new Node('052019', '秋田市', [], null, null, null));
                    self.selectedCode = ko.observable("1");
                    //self.testNode = ko.observable([]);
                };
                ScreenModel.prototype.openBDialog = function () {
                    var self = this;
                    var singleSelectedCode;
                    var curentNode;
                    nts.uk.ui.windows.sub.modeless('/view/qmm/003/b/index.xhtml', { title: '住民税納付先の登録＞住民税納付先一覧', dialogClass: "no-close" }).onClosed(function () {
                        singleSelectedCode = nts.uk.ui.windows.getShared("singleSelectedCode");
                        curentNode = nts.uk.ui.windows.getShared("curentNode");
                        self.editMode = false;
                        self.singleSelectedCode(singleSelectedCode);
                        self.curentNode(curentNode);
                    });
                };
                ScreenModel.prototype.openCDialog = function () {
                    var self = this;
                    var labelSubsub;
                    nts.uk.ui.windows.sub.modeless("/view/qmm/003/c/index.xhtml", { title: '住民税納付先の登録＞住民税報告先一覧', dialogClass: "no-close" }).onClosed(function () {
                        labelSubsub = nts.uk.ui.windows.getShared('labelSubsub');
                        //             self.editMode = false;
                        self.labelSubsub(labelSubsub);
                        console.log(labelSubsub);
                    });
                };
                ScreenModel.prototype.openDDialog = function () {
                    var self = this;
                    var items;
                    nts.uk.ui.windows.sub.modeless("/view/qmm/003/d/index.xhtml", { title: '住民税納付先の登録　＞　一括削除', dialogClass: "no-close" }).onClosed(function () {
                        items = nts.uk.ui.windows.getShared('items');
                        self.items([]);
                        self.items(items);
                        console.log(items);
                        console.log(self.items());
                    });
                };
                ScreenModel.prototype.openEDialog = function () {
                    var self = this;
                    var labelSubsub;
                    nts.uk.ui.windows.sub.modeless("/view/qmm/003/e/index.xhtml", { title: '住民税納付先の登録＞納付先の統合', dialogClass: "no-close" }).onClosed(function () {
                        labelSubsub = nts.uk.ui.windows.getShared('labelSubsub');
                        self.labelSubsub(labelSubsub);
                        console.log(labelSubsub);
                    });
                };
                //11.初期データ取得処理 11. Initial data acquisition processing
                ScreenModel.prototype.start = function () {
                    var dfd = $.Deferred();
                    var self = this;
                    //let t: any;
                    self.regionPrefect = ko.observableArray([
                        new Node('1', '北海道', [new Node('01', '北海道', [], '1', '北海道', '1')], null, null, null),
                        new Node('2', '東北', [new Node('02', '青森県', [], '2', '東北', '2'),
                            new Node('03', '岩手県', [], '2', '東北', '2'),
                            new Node('04', '宮城県', [], '2', '東北', '2'),
                            new Node('05', '秋田県県', [], '2', '東北', '2'),
                            new Node('06', '山形県', [], '2', '東北', '2'),
                            new Node('07', '福島県県', [], '2', '東北', '2')], null, null, null),
                        new Node('3', '関東', [new Node('08', '茨城県', [], '3', '関東', '3'),
                            new Node('09', '栃木県', [], '3', '関東', '3'),
                            new Node('10', '群馬県', [], '3', '関東', '3'),
                            new Node('11', '埼玉県', [], '3', '関東', '3'),
                            new Node('12', '千葉県', [], '3', '関東', '3'),
                            new Node('13', '東京都', [], '3', '関東', '3'),
                            new Node('14', '神奈川県', [], '3', '関東', '3')], null, null, null),
                        new Node('4', '中部', [new Node('15', '新潟県', [], '4', '中部', '4'),
                            new Node('16', '富山県', [], '4', '中部', '4'),
                            new Node('17', '石川県', [], '4', '中部', '4'),
                            new Node('18', '福井県', [], '4', '中部', '4'),
                            new Node('19', '山梨県', [], '4', '中部', '4'),
                            new Node('20', '長野県', [], '4', '中部', '4'),
                            new Node('21', '岐阜県', [], '4', '中部', '4'),
                            new Node('22', '静岡県', [], '4', '中部', '4'),
                            new Node('23', '愛知県', [], '4', '中部', '4')], null, null, null),
                        new Node('5', '近畿', [new Node('24', '三重県', [], '5', '近畿', '5'),
                            new Node('25', '滋賀県', [], '5', '近畿', '5'),
                            new Node('26', '京都府', [], '5', '近畿', '5'),
                            new Node('27', '大阪府', [], '5', '近畿', '5'),
                            new Node('28', '兵庫県', [], '5', '近畿', '5'),
                            new Node('29', '奈良県', [], '5', '近畿', '5'),
                            new Node('30', '和歌山県', [], '5', '近畿', '5')], null, null, null),
                        new Node('6', '中国', [new Node('31', '鳥取県', [], '6', '中国', '6'),
                            new Node('32', '島根県', [], '6', '中国', '6'),
                            new Node('33', '岡山県', [], '6', '中国', '6'),
                            new Node('34', '広島県', [], '6', '中国', '6'),
                            new Node('35', '山口県', [], '6', '中国', '6')], null, null, null),
                        new Node('7', '四国', [new Node('36', '徳島県', [], '7', '四国', '7'),
                            new Node('37', '香川県', [], '7', '四国', '7'),
                            new Node('38', '愛媛県', [], '7', '四国', '7'),
                            new Node('39', '高知県', [], '7', '四国', '7'),
                            new Node('40', '福岡県', [], '7', '四国', '7')], null, null, null),
                        new Node('8', '九州', [new Node('40', '福岡県', [], '8', '九州', '8'),
                            new Node('41', '佐賀県', [], '8', '九州', '8'),
                            new Node('42', '長崎県', [], '8', '九州', '8'),
                            new Node('43', '熊本県', [], '8', '九州', '8'),
                            new Node('44', '大分県', [], '8', '九州', '8'),
                            new Node('45', '宮崎県', [], '8', '九州', '8'),
                            new Node('46', '鹿児島県', [], '8', '九州', '8'),
                            new Node('47', '沖縄県', [], '8', '九州', '8')], null, null, null)
                    ]);
                    //console.log(self.regionPrefect());
                    (qmm003.a.service.getResidentalTax()).done(function (data) {
                        if (data.length > 0) {
                            var resiTax = [];
                            var childs = void 0;
                            var i = 0;
                            _.forEach(data, function (obj) {
                                var nodePre;
                                nodePre = _.find(self.precfecture(), function (objPrefecture) {
                                    return objPrefecture.code === obj.prefectureCode;
                                });
                                if (nodePre !== undefined) {
                                    var test = new Node(obj.resiTaxCode, obj.resiTaxAutonomy, [], nodePre.parentCode, nodePre.parentName, nodePre.treeCode);
                                    //
                                    self.findObj(self.regionPrefect(), obj.prefectureCode, test);
                                }
                            });
                            console.log(self.testNode);
                            self.filteredData = ko.observableArray(nts.uk.util.flatArray(self.testNode, "childs"));
                            console.log(self.filteredData());
                            self.removeData(self.filteredData());
                            console.log(_.intersection(self.testNode, self.region()));
                            self.items(self.testNode);
                            self.mode(true); // true, update mode 
                        }
                        else {
                            self.mode(false); // false, new mode
                        }
                        dfd.resolve();
                    }).fail(function (res) {
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.findObj = function (items, prefectureCode, objPre) {
                    var node;
                    var self = this;
                    _.find(items, function (obj) {
                        if (obj.code === prefectureCode) {
                            //node = obj;
                            // obj.childs.push(objPre);
                            var x = [];
                            var childs = void 0;
                            childs = new Node(obj.code, obj.name, [objPre], '', '', '');
                            self.testNode.push(new Node(obj.parentCode, obj.parentName, [childs], '', '', ''));
                            self.test = prefectureCode;
                        }
                        else {
                            self.findObj(obj.childs, prefectureCode, objPre);
                        }
                    });
                    return node;
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var Node = (function () {
                function Node(code, name, childs, parentCode, parentName, treeCode) {
                    var self = this;
                    self.code = code;
                    self.name = name;
                    self.nodeText = self.code + ' ' + self.name;
                    self.parentCode = parentCode;
                    self.parentName = parentName;
                    self.treeCode = treeCode;
                    self.childs = childs;
                }
                return Node;
            }());
            viewmodel.Node = Node;
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qmm003.a || (qmm003.a = {}));
})(qmm003 || (qmm003 = {}));
;
