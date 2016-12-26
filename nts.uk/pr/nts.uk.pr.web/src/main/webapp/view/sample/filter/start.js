__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            var self = this;
            self.dataSource = ko.observableArray([new Node('0001', 'Hanoi Vietnam', []),
                new Node('0003', 'Bangkok Thailand', []),
                new Node('0004', 'Tokyo Japan', []),
                new Node('0005', 'Jakarta Indonesia', []),
                new Node('0002', 'Seoul Korea', [])]);
            self.searchTerm = ko.observable('');
            self.filteredData = ko.observableArray(self.dataSource());
            self.singleSelectedCode = ko.observable(null);
            self.index = 0;
            self.headers = ko.observableArray(["Item Value Header", "Item Text Header", "Auto generated Field"]);
        }
        ScreenModel.prototype.resetSelection = function () {
            var self = this;
            self.searchTerm('');
            self.filteredData(self.dataSource());
            self.singleSelectedCode('0002');
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
            self.dataSource(newArrays);
            self.filteredData(newArrays);
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
