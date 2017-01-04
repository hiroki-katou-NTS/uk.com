__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            var self = this;
            // 青森市
            self.itemList = ko.observableArray([
                new Node('1', '青森市', []),
                new Node('2', '秋田市', []),
                new Node('3', '山形市', []),
                new Node('4', '福島市', []),
                new Node('5', '水戸市', []),
                new Node('6', '宇都宮市', []),
                new Node('7', '川越市', []),
                new Node('8', '熊谷市', []),
                new Node('9', '浦和市', [])
            ]);
            self.items = ko.observableArray([new Node('1', '東北', [
                    new Node('11', '青森県', [
                        new Node('022012', '青森市', []),
                        new Node('052019', '秋田市', [])
                    ]),
                    new Node('12', '東北', [
                        new Node('062014', '山形市', [])
                    ]),
                    new Node('13', '福島県', [
                        new Node('062015', '福島市', [])
                    ])
                ]),
                new Node('2', '北海道', []),
                new Node('3', '東海', []),
                new Node('4', '関東', [
                    new Node('41', '茨城県', [
                        new Node('062016', '水戸市', []),
                    ]),
                    new Node('42', '栃木県', [
                        new Node('062017', '宇都宮市', [])
                    ]),
                    new Node('43', '埼玉県', [
                        new Node('062019', '川越市', []),
                        new Node('062020', '熊谷市', []),
                        new Node('062022', '浦和市', []),
                    ])
                ]),
                new Node('5', '東海', [])
            ]);
            self.itemName = ko.observable('');
            self.currentCode = ko.observable(null);
            self.selectedCode = ko.observable(null);
            self.isEnable = ko.observable(true);
            self.isEditable = ko.observable(true);
            self.singleSelectedCode = ko.observable(null);
            self.text1 = ko.observable(null);
            self.test = ko.observable(null);
            self.name1 = ko.observable(null);
            self.curentNode = ko.observable(new Node("", "", []));
            self.index = 0;
            self.singleSelectedCode.subscribe(function (newValue) {
                function findObj(items) {
                    var _node;
                    _.find(items, function (_obj) {
                        if (!_node) {
                            if (_obj.code == newValue) {
                                _node = _obj;
                            }
                            else {
                                _node = findObj(_obj.childs);
                            }
                        }
                    });
                    return _node;
                }
                ;
                self.curentNode(findObj(self.items()));
                self.test = ko.observable(self.curentNode());
                function findObj1(items) {
                    var _node;
                    _.find(items, function (_obj) {
                        if (!_node) {
                            if (_obj.name == self.test().name) {
                                _node = _obj;
                            }
                            else {
                                _node = findObj1(_obj.childs);
                            }
                        }
                    });
                    console.log(_node);
                    return _node;
                }
                ;
                self.name1(findObj1(self.itemList()));
                self.selectedCode(self.name1().code);
            });
            function findObj(items) {
                var _node;
                _.find(items, function (_obj) {
                    if (!_node) {
                        if (_obj.name == self.singleSelectedCode()) {
                            _node = _obj;
                        }
                        else {
                            _node = findObj(_obj.childs);
                        }
                    }
                });
                return _node;
            }
            ;
        }
        ScreenModel.prototype.resetSelection = function () {
            var self = this;
            self.curentNode(new Node("", "", []));
            console.log(self.curentNode());
            self.items = ko.observableArray([new Node('1', '東北', [
                    new Node('11', '青森県', [
                        new Node('022012', '青森市', []),
                        new Node('052019', '秋田市', [])
                    ]),
                    new Node('12', '東北', [
                        new Node('062014', '山形市', [])
                    ]),
                    new Node('13', '福島県', [
                        new Node('062015', '福島市', [])
                    ])
                ]),
                new Node('2', '北海道', []),
                new Node('3', '東海', []),
                new Node('4', '関東', [
                    new Node('41', '茨城県', [
                        new Node('062016', '水戸市', []),
                    ]),
                    new Node('42', '栃木県', [
                        new Node('062017', '宇都宮市', [])
                    ]),
                    new Node('43', '埼玉県', [
                        new Node('062019', '川越市', []),
                        new Node('062020', '熊谷市', []),
                        new Node('062022', '浦和市', []),
                    ])
                ]),
                new Node('5', '東海', [])
            ]);
            console.log(self.items());
            self.singleSelectedCode(" ");
            console.log(self.singleSelectedCode());
        };
        ScreenModel.prototype.findName = function (items) {
            var node;
            return node;
        };
        return ScreenModel;
    }());
    var Node = (function () {
        function Node(code, name, childs) {
            var self = this;
            self.code = code;
            self.name = name;
            self.nodeText = self.code + ' ' + self.name;
            self.childs = childs;
        }
        return Node;
    }());
    var ItemModel = (function () {
        function ItemModel(code, name) {
            this.code = code;
            this.name = name;
        }
        return ItemModel;
    }());
    this.bind(new ScreenModel());
});
