var qpp004;
(function (qpp004) {
    var b;
    (function (b) {
        var viewmodel;
        (function (viewmodel) {
            var Step = (function () {
                function Step(id, content) {
                    var self = this;
                    this.id = ko.observable(id);
                    this.content = ko.observable(content);
                }
                return Step;
            }());
            viewmodel.Step = Step;
            var ItemModel = (function () {
                function ItemModel(id, name) {
                    var self = this;
                    this.id = id;
                    this.name = name;
                }
                return ItemModel;
            }());
            viewmodel.ItemModel = ItemModel;
            var Listbox = (function () {
                function Listbox() {
                    var self = this;
                    self.itemList = ko.observableArray([
                        new qpp004.b.viewmodel.ItemModel('A00000000001', '日通　社員１'),
                        new qpp004.b.viewmodel.ItemModel('A00000000002', '日通　社員2'),
                        new qpp004.b.viewmodel.ItemModel('A00000000003', '日通　社員3'),
                        new qpp004.b.viewmodel.ItemModel('A00000000004', '日通　社員4'),
                        new qpp004.b.viewmodel.ItemModel('A00000000005', '日通　社員5'),
                        new qpp004.b.viewmodel.ItemModel('A00000000006', '日通　社員6'),
                        new qpp004.b.viewmodel.ItemModel('A00000000007', '日通　社員7'),
                        new qpp004.b.viewmodel.ItemModel('A00000000008', '日通　社員8'),
                        new qpp004.b.viewmodel.ItemModel('A00000000009', '日通　社員9'),
                        new qpp004.b.viewmodel.ItemModel('A000000000010', '日通　社員１0'),
                    ]);
                    self.itemName = ko.observable('');
                    self.currentCode = ko.observable(3);
                    self.selectedCode = ko.observable(null);
                    self.isEnable = ko.observable(true);
                    self.selectedCodes = ko.observableArray([]);
                }
                return Listbox;
            }());
            viewmodel.Listbox = Listbox;
            var Wizard = (function () {
                function Wizard() {
                    var self = this;
                    self.stepList = ko.observableArray([
                        new qpp004.b.viewmodel.Step('step-1', '.step-1'),
                        new qpp004.b.viewmodel.Step('step-2', '.step-2'),
                        new qpp004.b.viewmodel.Step('step-3', '.step-3'),
                        new qpp004.b.viewmodel.Step('step-4', '.step-4')
                    ]);
                    self.stepSelected = ko.observable(new Step('step-2', '.step-2'));
                }
                Wizard.prototype.begin = function () {
                    $('#wizard').begin();
                };
                Wizard.prototype.end = function () {
                    $('#wizard').end();
                };
                Wizard.prototype.next = function () {
                    $('#wizard').steps('next');
                };
                Wizard.prototype.previous = function () {
                    $('#wizard').steps('previous');
                };
                Wizard.prototype.step1 = function () {
                    $('#wizard').setStep(0);
                };
                Wizard.prototype.step2 = function () {
                    $('#wizard').setStep(1);
                };
                Wizard.prototype.step3 = function () {
                    $('#wizard').setStep(2);
                };
                Wizard.prototype.step4 = function () {
                    $('#wizard').setStep(3);
                };
                return Wizard;
            }());
            viewmodel.Wizard = Wizard;
            var ScreenModel = (function () {
                function ScreenModel(data) {
                    var self = this;
                    self.wizard = new qpp004.b.viewmodel.Wizard();
                    self.listbox = new qpp004.b.viewmodel.Listbox();
                    self.currentProcessingYearMonth = ko.observable(data.displayCurrentProcessingYm);
                    self.processingNo = ko.observable(data.processingNo);
                    self.processingYM = ko.observable(data.currentProcessingYm);
                }
                ScreenModel.prototype.processCreateData = function () {
                    var self = this;
                    var result = [];
                    _.forEach(self.listbox.itemList(), function (item) {
                        if (self.listbox.selectedCodes().indexOf(item.id) >= 0) {
                            result.push(item);
                        }
                    });
                    var data = {
                        personIdList: result,
                        processingNo: self.processingNo(),
                        processingYearMonth: self.processingYM()
                    };
                    nts.uk.ui.windows.setShared("data", data);
                    nts.uk.ui.windows.sub.modal("/view/qpp/004/l/index.xhtml", { title: "給与データの作成", dialogClass: "no-close" });
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = b.viewmodel || (b.viewmodel = {}));
    })(b = qpp004.b || (qpp004.b = {}));
})(qpp004 || (qpp004 = {}));
