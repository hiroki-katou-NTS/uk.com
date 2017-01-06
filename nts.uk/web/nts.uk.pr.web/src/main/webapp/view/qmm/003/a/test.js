__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            this.firtSingleCode = " ";
            this.editMode = true; // true là mode thêm mới, false là mode sửa 
            this.mutiMode = false; // 
            var self = this;
            // 青森市
            // itemList == RemoveData()
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
            self.items = ko.observableArray([
                new Node('1', '東北', [
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
            self.test = ko.observable(null);
            self.nameBySelectedCode = ko.observable(null);
            self.curentNode = ko.observable(new Node("", "", []));
            self.index = 0;
            self.labelSubsub = ko.observable(null);
            self.filteredData = ko.observableArray(nts.uk.util.flatArray(self.items(), "childs"));
            self.selectedCodes = ko.observableArray([]);
            FilterData(self.filteredData());
            RemoveData(self.arrayAfterFilter());
            self.singleSelectedCode.subscribe(function (newValue) {
                if (self.editMode) {
                    var count_1 = 0;
                    // tim theo Code
                    function findObjByCode(items) {
                        var _node;
                        _.find(items, function (_obj) {
                            if (!_node) {
                                if (_obj.code == newValue) {
                                    _node = _obj;
                                    count_1 = count_1 + 1;
                                }
                                else {
                                    _node = findObjByCode(_obj.childs);
                                }
                            }
                        });
                        return _node;
                    }
                    ;
                    //tim theo Name
                    function findObjByName(items) {
                        var _node;
                        _.find(items, function (_obj) {
                            if (!_node) {
                                if (_obj.name == self.test().name) {
                                    _node = _obj;
                                }
                                else {
                                    _node = findObjByName(_obj.childs);
                                }
                            }
                        });
                        return _node;
                    }
                    ;
                    self.curentNode(findObjByCode(self.items()));
                    self.test = ko.observable(self.curentNode());
                    self.nameBySelectedCode(findObjByName(self.itemList()));
                    if (count_1 == 1) {
                        self.selectedCode(self.nameBySelectedCode().code);
                    }
                    var co_1 = 0, co1_1 = 0;
                    _.each(self.arrayAfterFilter(), function (obj) {
                        if (obj.code != self.curentNode().code) {
                            co_1++;
                        }
                        else {
                            co1_1 = co_1 + 1;
                        }
                    });
                    self.labelSubsub(self.arrayAfterFilter()[co1_1]);
                }
                else {
                    self.editMode = true;
                }
            });
            // change tree -> array include: root, sub, subsub;
            function FilterData(items1) {
                var node;
                _.each(items1, function (obj) {
                    if (!node) {
                        if (obj.childs != null) {
                            self.arrayAfterFilter = ko.observableArray(nts.uk.util.flatArray(items1, "childs"));
                        }
                    }
                });
            }
            // remove data: return array of subsub tree
            function RemoveData(items1) {
                var self = this;
                self.ttt = _.remove(items1, function (obj) {
                    return _.size(obj.code) < 3;
                });
            }
        }
        ScreenModel.prototype.resetData = function () {
            var self = this;
            self.editMode = false;
            self.curentNode(new Node("", "", []));
            self.singleSelectedCode("");
            self.selectedCode("");
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
    this.bind(new ScreenModel());
});
function OpenModalSubWindow(option) {
    nts.uk.ui.windows.sub.modal("/view/qmm/003/b/test.xhtml", option);
}
