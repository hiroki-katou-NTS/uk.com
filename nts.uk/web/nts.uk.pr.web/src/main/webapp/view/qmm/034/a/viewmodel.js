var qmm034;
(function (qmm034) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.constraint = 'LayoutCode';
                    this.isUpdate = ko.observable(true);
                    var self = this;
                    self.date = ko.observable(new Date('2016/01/02'));
                    self.items = ko.observableArray([
                        new EraModel('明明', 'M', "1999/01/25"),
                        new EraModel('大正', 'T', "1912/07/30"),
                        new EraModel('大明', 'S', "1926/12/25"),
                        new EraModel('大元', 'H', "1989/01/08"),
                        new EraModel('大記', 'N', "2016/02/18"),
                    ]);
                    self.columns = ko.observableArray([
                        { headerText: '元号', prop: 'code', width: 50 },
                        { headerText: '記号', prop: 'name', width: 50 },
                        { headerText: '開始年月日', prop: 'startDateText', width: 80 },
                    ]);
                    self.currentCode = ko.observable(null);
                    self.currentCodeList = ko.observableArray([]);
                    //Tim object dau tien
                    self.currentEra = ko.observable(_.first(self.items()));
                    self.currentCode.subscribe(function (codeChanged) {
                        self.currentEra(self.getEra(codeChanged));
                    });
                }
                ScreenModel.prototype.register = function () {
                    var self = this;
                    if (self.isUpdate() === false) {
                        self.insertData();
                    }
                    else {
                        self.update;
                    }
                };
                ScreenModel.prototype.refreshLayout = function () {
                    var self = this;
                    self.currentEra(new EraModel('', '', new Date().toString()));
                };
                ScreenModel.prototype.insertData = function () {
                    var self = this;
                    var newData = self.currentEra();
                    var newEradata = self;
                    var x = self.items();
                    x.push(newData);
                    self.items(x);
                    // alert('insert ok');
                };
                ScreenModel.prototype.deleteData = function () {
                    var self = this;
                    var newDel = self.currentEra();
                    var y = self.items();
                    y.pop(newDel);
                    self.items(y);
                };
                ScreenModel.prototype.getEra = function (codeNew) {
                    var self = this;
                    var era = _.find(self.items(), function (item) {
                        return item.code === codeNew;
                    });
                    return era;
                };
                ScreenModel.prototype.update = function (eraCodeNew) {
                    var self = this;
                    //let newCurrentEra = self.currentEra;
                    self.currentCode.subscribe(function (codeChanged) {
                        self.currentEra(self.getEra(codeChanged));
                    });
                    var newCurrentEra = _.findIndex(self.items(), function (item) {
                        return item.code === eraCodeNew;
                    });
                    //            var x = self.items();
                    //            x.push(newCurrentEra);
                    //            self.items(x);
                    return eraCodeNew;
                };
                ScreenModel.prototype.selectSomeItems = function () {
                    this.currentCode('150');
                    this.currentCodeList.removeAll();
                    this.currentCodeList.push('001');
                    this.currentCodeList.push('ABC');
                };
                ScreenModel.prototype.deselectAll = function () {
                    this.currentCode(null);
                    this.currentCodeList.removeAll();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var EraModel = (function () {
                function EraModel(code, name, startDate) {
                    this.code = code;
                    this.name = name;
                    this.startDate = ko.observable(new Date(startDate));
                    this.startDateText = this.startDate().toDateString();
                }
                return EraModel;
            }());
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qmm034.a || (qmm034.a = {}));
})(qmm034 || (qmm034 = {}));
