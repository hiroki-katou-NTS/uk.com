var kdl024;
(function (kdl024) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.constraint = ['ResidenceCode', 'PersonId'];
                    //            for (let i = 1; i < 100; i++) {
                    //                this.items.push(new ItemModel('00' + i, '基本給', "description " + i, i % 3 === 0, "other" + i));
                    //            }
                    self.items = ko.observableArray([]);
                    self.start();
                }
                //start
                ScreenModel.prototype.start = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    var _items = [];
                    _items.push(new Item({
                        code: "",
                        name: "",
                        attribute: 0,
                        unit: 0
                    }));
                    self.items(_items);
                    return dfd.promise();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            //item 
            var Item = (function () {
                function Item(code, name, attribute, unit) {
                    this.code = code;
                    this.name = name;
                    this.attribute = attribute;
                    this.unit = unit;
                }
                return Item;
            }());
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = kdl024.a || (kdl024.a = {}));
})(kdl024 || (kdl024 = {}));
//# sourceMappingURL=kdl024.vm.js.map