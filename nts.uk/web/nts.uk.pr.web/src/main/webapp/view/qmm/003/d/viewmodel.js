var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm003;
                (function (qmm003) {
                    var d;
                    (function (d) {
                        var ScreenModel = (function () {
                            function ScreenModel() {
                                this.editMode = true; // true là mode thêm mới, false là mode sửa 
                                var self = this;
                                self.init();
                                self.singleSelectedCode.subscribe(function (newValue) {
                                });
                                self.selectedCode.subscribe(function (newValue) {
                                    console.log(self.selectedCode());
                                });
                                // console.log(self.selectedCode());
                            }
                            ScreenModel.prototype.findByCode = function (items, newValue) {
                                var self = this;
                                var _node;
                                _.find(items, function (_obj) {
                                    if (!_node) {
                                        if (_obj.code == newValue) {
                                            _node = _obj;
                                        }
                                    }
                                });
                                return _node;
                            };
                            ;
                            ScreenModel.prototype.clickButton = function () {
                                var self = this;
                                nts.uk.ui.windows.setShared('items', self.items(), true);
                                nts.uk.ui.windows.close();
                            };
                            ScreenModel.prototype.cancelButton = function () {
                                nts.uk.ui.windows.close();
                            };
                            //        removeNodeByCode(items: Array<Node>): any{
                            //            let self = this;
                            //            _.remove(items,function(obj: Node){
                            //                if(obj.code == self.Value()){
                            //                    return obj.code == self.Value();
                            //                }else{
                            //                    return self.removeNodeByCode(obj.childs);
                            //                   
                            //                }
                            //                })
                            //            
                            //            };
                            ScreenModel.prototype.removeData1 = function (items) {
                                _.remove(items, function (obj) {
                                    return _.size(obj.code) == 1;
                                });
                            };
                            ScreenModel.prototype.removeData2 = function (items) {
                                _.remove(items, function (obj) {
                                    return _.size(obj.code) == 2;
                                });
                            };
                            ScreenModel.prototype.removeData3 = function (items) {
                                _.remove(items, function (obj) {
                                    return _.size(obj.code) > 3;
                                });
                            };
                            ScreenModel.prototype.delete = function (items) {
                                var self = this;
                                _.each(self.filteredData(), function (obj) {
                                    if (_.size(obj.code) == 1) {
                                        self.removeData1(self.filteredData());
                                    }
                                });
                            };
                            ScreenModel.prototype.init = function () {
                                var self = this;
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
                                self.singleSelectedCode = ko.observable("11");
                                self.curentNode = ko.observable(new Node("", "", []));
                                self.index = 0;
                                self.selectedCode = ko.observableArray([]);
                                self.filteredData = ko.observableArray(nts.uk.util.flatArray(self.items(), "childs"));
                            };
                            ;
                            //       findByCode(items: Array<Node>, newValue: string, count: number): Node{
                            //            let self = this;
                            //            let node : Node;
                            //            _.find(items, function(obj: Node){
                            //                if(!node){
                            //                    if(obj.code == newValue){
                            //                        node = obj;
                            //                        count = count + 1;
                            //                    }
                            //                    }
                            //                });
                            //            return node;
                            //        };
                            //        
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
                            return ScreenModel;
                        }());
                        d.ScreenModel = ScreenModel;
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
                        d.Node = Node;
                    })(d = qmm003.d || (qmm003.d = {}));
                })(qmm003 = view.qmm003 || (view.qmm003 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
