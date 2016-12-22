__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            var self = this;
            self.items1 = ko.observableArray([new Node('0001', 'Hanoi Vietnam', []),
                new Node('0003', 'Bangkok Thailand', []),
                new Node('0004', 'Tokyo Japan', []),
                new Node('0005', 'Jakarta Indonesia', []),
                new Node('0002', 'Seoul Korea', [])]);
            self.searchTerm = ko.observable('');
            self.filteredItems1 = ko.computed(function () {
                //if items is empty return empty array
                if (!self.items1()) {
                    return [];
                }
                var filter = self.searchTerm().toLowerCase();
                //if filter is empty return all the items
                if (!filter) {
                    return self.items1();
                }
                //filter data
                var filtered = ko.utils.arrayFilter(self.items1(), function (item) {
                    var fields = ["name"]; //we can filter several properties
                    var i = fields.length;
                    while (i--) {
                        var prop = fields[i];
                        var strProp = ko.unwrap(item[prop]).toLocaleLowerCase();
                        if (strProp.indexOf(filter) !== -1) {
                            return true;
                        }
                        ;
                    }
                    return false;
                });
                return filtered;
            });
            self.items2 = ko.observableArray(self.items1());
            self.filteredItems2 = ko.computed(function () {
                //if items is empty return empty array
                if (!self.items2()) {
                    return [];
                }
                var filter = self.searchTerm().toLowerCase();
                //if filter is empty return all the items
                if (!filter) {
                    return self.items2();
                }
                //filter data
                var filtered = ko.utils.arrayFilter(self.items2(), function (item) {
                    var fields = ["name"]; //we can filter several properties
                    var i = fields.length;
                    while (i--) {
                        var prop = fields[i];
                        var strProp = ko.unwrap(item[prop]).toLocaleLowerCase();
                        if (strProp.indexOf(filter) !== -1) {
                            return true;
                        }
                        ;
                    }
                    return false;
                });
                return filtered;
            });
            self.selectedCode = ko.observableArray([]);
            self.singleSelectedCode = ko.observable(null);
            self.index = 0;
            self.headers = ko.observableArray(["Item Value Header", "Item Text Header", "Auto generated Field"]);
        }
        ScreenModel.prototype.resetSelection = function () {
            var self = this;
            self.searchTerm('');
            self.items2(self.items1());
            self.singleSelectedCode('0002');
            self.selectedCode(['0001', '0005']);
        };
        ScreenModel.prototype.changeDataSource = function () {
            var self = this;
            var i = 0;
            var newArrays = new Array();
            while (i < 50) {
                self.index++;
                i++;
                newArrays.push(new Node(self.index.toString(), 'Name ' + self.index.toString(), []));
            }
            ;
            self.items1(newArrays);
            self.items2(newArrays);
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
            self.custom = 'Random' + new Date().getTime();
        }
        return Node;
    }());
    this.bind(new ScreenModel());
});
