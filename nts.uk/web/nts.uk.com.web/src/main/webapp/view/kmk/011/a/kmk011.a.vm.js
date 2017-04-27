var kmk011;
(function (kmk011) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.label_002 = ko.observable(new model.Labels());
                    self.label_003 = ko.observable(new model.Labels());
                    self.label_004 = ko.observable(new model.Labels());
                    self.label_005 = ko.observable(new model.Labels());
                    self.label_006 = ko.observable(new model.Labels());
                    self.sel_002 = ko.observable('');
                    self.currentCode = ko.observable();
                    self.columns = ko.observableArray([
                        { headerText: 'コード', key: 'code', width: 100, hidden: true },
                        { headerText: '名称', key: 'name', width: 150 },
                        { headerText: '説明', key: 'description', width: 150 },
                        { headerText: '説明1', key: 'other1', width: 150 },
                        { headerText: '説明2', key: 'other2', width: 150 },
                        { headerText: 'Switch', key: 'switchValue', width: 300, controlType: 'switch' }
                    ]);
                    self.dataSource = ko.observableArray([
                        new DivTime(1, "休み時間"),
                        new DivTime(2, "休み時間"),
                        new DivTime(3, "休み時間")
                    ]);
                }
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    self.currentCode(1);
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var model;
            (function (model) {
                var Labels = (function () {
                    function Labels() {
                        this.constraint = 'LayoutCode';
                        var self = this;
                        self.inline = ko.observable(true);
                        self.required = ko.observable(true);
                        self.enable = ko.observable(true);
                    }
                    return Labels;
                }());
                model.Labels = Labels;
                var BoxModel = (function () {
                    function BoxModel(id, name) {
                        var self = this;
                        self.id = id;
                        self.name = name;
                    }
                    return BoxModel;
                }());
                model.BoxModel = BoxModel;
                var DivTime = (function () {
                    function DivTime(id, name) {
                        var self = this;
                        self.id = ko.observable(id);
                        self.name = ko.observable(name);
                    }
                    return DivTime;
                }());
                model.DivTime = DivTime;
            })(model = viewmodel.model || (viewmodel.model = {}));
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = kmk011.a || (kmk011.a = {}));
})(kmk011 || (kmk011 = {}));
//# sourceMappingURL=kmk011.a.vm.js.map